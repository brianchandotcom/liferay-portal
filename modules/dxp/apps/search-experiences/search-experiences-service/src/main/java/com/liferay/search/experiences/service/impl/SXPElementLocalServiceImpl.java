/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.search.experiences.exception.DefaultSXPElementException;
import com.liferay.search.experiences.exception.SXPElementElementDefinitionJSONException;
import com.liferay.search.experiences.exception.SXPElementTitleException;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.base.SXPElementLocalServiceBaseImpl;
import com.liferay.search.experiences.validator.SXPElementValidator;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.search.experiences.model.SXPElement",
	service = AopService.class
)
public class SXPElementLocalServiceImpl extends SXPElementLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.liferay.search.experiences.sxpElements.service.ElementLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.search.experiences.blueprints.service.ElementLocalServiceUtil</code>.
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPElement addSXPElement(
			long userId, long groupId, Map<Locale, String> descriptionMap,
			String elementDefinitionJSON, Map<Locale, String> titleMap,
			int type, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		_validate(sxpElementDefinitionJSON, titleMap, type, serviceContext);

		SXPElement sxpElement = createSXPElement(
			counterLocalService.increment(SXPElement.class.getName()));

		sxpElement.setGroupId(groupId);
		sxpElement.setCompanyId(user.getCompanyId());
		sxpElement.setUserId(user.getUserId());
		sxpElement.setUserName(user.getFullName());
		sxpElement.setCreateDate(serviceContext.getCreateDate(new Date()));
		sxpElement.setModifiedDate(serviceContext.getModifiedDate(new Date()));

		sxpElement.setDescriptionMap(descriptionMap);
		sxpElement.setElementDefinitionJSON(elementDefinitionJSON);
		sxpElement.setHidden(false);
		sxpElement.setReadOnly(_isReadOnly(serviceContext));
		sxpElement.setTitleMap(titleMap);
		sxpElement.setType(type);
		sxpElement.setStatus(WorkflowConstants.STATUS_APPROVED);

		sxpElement = super.addSXPElement(sxpElement);

		_resourceLocalService.addModelResources(sxpElement, serviceContext);

		return sxpElement;
	}

	@Override
	public SXPElement deleteSXPElement(long sxpElementId)
		throws PortalException {

		SXPElement sxpElement = sxpElementPersistence.findByPrimaryKey(
			sxpElementId);

		return deleteElement(sxpElement);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SXPElement deleteSXPElement(SXPElement sxpElement)
		throws PortalException {

		if (sxpElement.isReadOnly()) {
			throw new DefaultSXPElementException(
				"Cannot delete system read-only SXPElement");
		}

		_resourceLocalService.deleteResource(
			sxpElement, ResourceConstants.SCOPE_INDIVIDUAL);

		return super.deleteSXPElement(sxpElement);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SXPElement deleteSystemSXPElement(SXPElement sxpElement)
		throws PortalException {

		_resourceLocalService.deleteResource(
			sxpElement, ResourceConstants.SCOPE_INDIVIDUAL);

		return super.deleteSXPElement(sxpElement);
	}

	@Override
	public int getCompanySXPElementsCount(long companyId, int type) {
		return sxpElementPersistence.councountByC_T(companyId, type);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPElement updateStatus(long userId, long sxpElementId, int status)
		throws PortalException {

		SXPElement sxpElement = getSXPElement(sxpElementId);

		sxpElement.setStatus(status);

		return updateSXPElement(sxpElement);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPElement updateSXPElement(
			long userId, long sxpElementId, Map<Locale, String> descriptionMap,
			String elementDefinitionJSON, boolean hidden,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		SXPElement sxpElement = getSXPElement(sxpElementId);

		_validate(
			elementDefinitionJSON, titleMap, sxpElement.getType(),
			serviceContext);

		sxpElement.setModifiedDate(serviceContext.getModifiedDate(new Date()));

		sxpElement.setDescriptionMap(descriptionMap);
		sxpElement.setElementDefinitionJSON(elementDefinitionJSON);
		sxpElement.setHidden(hidden);
		sxpElement.setTitleMap(titleMap);

		return updateSXPElement(sxpElement);
	}

	private Boolean _isReadOnly(ServiceContext serviceContext) {
		return GetterUtil.getBoolean(
			serviceContext.getAttribute(
				SXPElementLocalServiceImpl.class.getName() + "#_read-only"));
	}

	private void _validate(
			String elementDefinitionJSON, Map<Locale, String> titleMap,
			int type, ServiceContext serviceContext)
		throws SXPElementElementDefinitionJSONException,
			   SXPElementTitleException {

		if (!GetterUtil.getBoolean(
				serviceContext.getAttribute(
					SXPElementLocalServiceImpl.class.getName() + "#_validate"),
				true)) {

			_sxpElementValidator.validate(
				elementDefinitionJSON, titleMap, type);
		}
	}

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private SXPElementValidator _sxpElementValidator;

	@Reference
	private UserLocalService _userLocalService;

}