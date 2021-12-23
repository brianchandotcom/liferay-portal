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
import com.liferay.search.experiences.exception.SXPElementElementDefinitionJSONException;
import com.liferay.search.experiences.exception.SXPElementTitleException;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.base.SXPElementLocalServiceBaseImpl;
import com.liferay.search.experiences.validator.SXPElementValidator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.search.experiences.model.SXPElement",
	service = AopService.class
)
public class SXPElementLocalServiceImpl extends SXPElementLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPElement addSXPElement(
			long userId, Map<Locale, String> descriptionMap,
			String elementDefinitionJSON, boolean readOnly,
			String schemaVersion, Map<Locale, String> titleMap, int type,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(elementDefinitionJSON, titleMap, type, serviceContext);

		SXPElement sxpElement = createSXPElement(
			counterLocalService.increment(SXPElement.class.getName()));

		User user = _userLocalService.getUser(userId);

		sxpElement.setCompanyId(user.getCompanyId());
		sxpElement.setUserId(user.getUserId());
		sxpElement.setUserName(user.getFullName());

		sxpElement.setDescriptionMap(descriptionMap);
		sxpElement.setElementDefinitionJSON(elementDefinitionJSON);
		sxpElement.setHidden(false);
		sxpElement.setReadOnly(readOnly);
		sxpElement.setTitleMap(titleMap);
		sxpElement.setType(type);
		sxpElement.setStatus(WorkflowConstants.STATUS_APPROVED);

		sxpElement = sxpElementPersistence.update(sxpElement);

		_resourceLocalService.addModelResources(sxpElement, serviceContext);

		return sxpElement;
	}

	@Override
	public void deleteCompanySXPElements(long companyId)
		throws PortalException {

		List<SXPElement> sxpElements = sxpElementPersistence.findByCompanyId(
			companyId);

		for (SXPElement sxpElement : sxpElements) {
			sxpElementLocalService.deleteSXPElement(sxpElement);
		}
	}

	@Override
	public SXPElement deleteSXPElement(long sxpElementId)
		throws PortalException {

		SXPElement sxpElement = sxpElementPersistence.findByPrimaryKey(
			sxpElementId);

		return deleteSXPElement(sxpElement);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SXPElement deleteSXPElement(SXPElement sxpElement)
		throws PortalException {

		sxpElement = sxpElementPersistence.remove(sxpElement);

		_resourceLocalService.deleteResource(
			sxpElement, ResourceConstants.SCOPE_INDIVIDUAL);

		return sxpElement;
	}

	@Override
	public List<SXPElement> getSXPElements(long companyId, boolean readOnly) {
		return sxpElementPersistence.findByC_R(companyId, readOnly);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPElement updateStatus(long userId, long sxpElementId, int status)
		throws PortalException {

		SXPElement sxpElement = sxpElementPersistence.findByPrimaryKey(
			sxpElementId);

		if (sxpElement.getStatus() == status) {
			return sxpElement;
		}

		sxpElement.setStatus(status);

		return sxpElementPersistence.update(sxpElement);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPElement updateSXPElement(
			long userId, long sxpElementId, Map<Locale, String> descriptionMap,
			String elementDefinitionJSON, boolean hidden, String schemaVersion,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		SXPElement sxpElement = getSXPElement(sxpElementId);

		_validate(
			elementDefinitionJSON, titleMap, sxpElement.getType(),
			serviceContext);

		sxpElement.setDescriptionMap(descriptionMap);
		sxpElement.setElementDefinitionJSON(elementDefinitionJSON);
		sxpElement.setHidden(hidden);
		sxpElement.setTitleMap(titleMap);

		return updateSXPElement(sxpElement);
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