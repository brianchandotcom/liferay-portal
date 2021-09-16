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

import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.search.experiences.exception.SXPBlueprintConfigurationsJSONException;
import com.liferay.search.experiences.exception.SXPBlueprintTitleException;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.base.SXPBlueprintLocalServiceBaseImpl;
import com.liferay.search.experiences.validator.SXPBlueprintValidator;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Petteri Karttunen
 */
@Component(
	property = "model.class.name=com.liferay.search.experiences.model.SXPBlueprint",
	service = AopService.class
)
public class SXPBlueprintLocalServiceImpl
	extends SXPBlueprintLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPBlueprint addSXPBlueprint(
			long userId, long groupId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String configurationsJSON,
			String elementInstancesJSON, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		_validate(titleMap, configuration, serviceContext);

		long sxpBlueprintId = counterLocalService.increment(
			Blueprint.class.getName());

		SXPBlueprint sxpBlueprint = createSXPBlueprint(sxpBlueprintId);

		sxpBlueprint.setGroupId(groupId);
		sxpBlueprint.setCompanyId(user.getCompanyId());
		sxpBlueprint.setUserId(user.getUserId());
		sxpBlueprint.setUserName(user.getFullName());
		sxpBlueprint.setCreateDate(serviceContext.getCreateDate(new Date()));
		sxpBlueprint.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		sxpBlueprint.setUuid(serviceContext.getUuid());

		sxpBlueprint.setTitleMap(titleMap);
		sxpBlueprint.setDescriptionMap(descriptionMap);

		int status = WorkflowConstants.STATUS_DRAFT;

		sxpBlueprint.setStatus(status);

		sxpBlueprint.setStatusByUserId(userId);
		sxpBlueprint.setStatusDate(serviceContext.getModifiedDate(null));

		sxpBlueprint.setConfigurationsJSON(configurationsJSON);
		sxpBlueprint.setElementInstancesJSON(elementInstancesJSON);

		sxpBlueprint = super.addSXPBlueprint(sxpBlueprint);

		_resourceLocalService.addModelResources(sxpBlueprint, serviceContext);

		return startWorkflowInstance(userId, sxpBlueprint, serviceContext);
	}

	@Override
	public SXPBlueprint deleteSXPBlueprint(long sxpBlueprintId)
		throws PortalException {

		SXPBlueprint sxpBlueprint = sxpBlueprintPersistence.findByPrimaryKey(
			sxpBlueprintId);

		return deleteSXPBlueprint(sxpBlueprint);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SXPBlueprint deleteSXPBlueprint(SXPBlueprint sxpBlueprint)
		throws PortalException {

		_resourceLocalService.deleteResource(
			sxpBlueprint, ResourceConstants.SCOPE_INDIVIDUAL);

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			sxpBlueprint.getCompanyId(), sxpBlueprint.getGroupId(),
			SXPBlueprint.class.getName(), sxpBlueprint.getSXPBlueprintId());

		return super.deleteSXPBlueprint(sxpBlueprint);
	}

	public int getCompanySXPBlueprintsCount(long companyId) {
		return sxpBlueprintPersistence.countByCompanyId(companyId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPBlueprint updateStatus(
			long userId, long sxpBlueprintId, int status)
		throws PortalException {

		User user = _userLocalService.getUser(userId);
		Blueprint sxpBlueprint = getSXPBlueprint(sxpBlueprintId);

		sxpBlueprint.setStatus(status);
		sxpBlueprint.setStatusByUserId(userId);
		sxpBlueprint.setStatusByUserName(user.getScreenName());
		sxpBlueprint.setStatusDate(new Date());

		return updateSXPBlueprint(sxpBlueprint);
	}

	@Indexable(type = IndexableType.REINDEX)
	public SXPBlueprint updateSXPBlueprint(
			long userId, long sxpBlueprintId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String configurationsJSON,
			String elementInstancesJSON, ServiceContext serviceContext)
		throws PortalException {

		SXPBlueprint sxpBlueprint = getBlueprint(sxpBlueprintId);

		_validate(titleMap, configurationsJSON, serviceContext);

		sxpBlueprint.setDescriptionMap(descriptionMap);
		sxpBlueprint.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		sxpBlueprint.setTitleMap(titleMap);

		sxpBlueprint.setConfigurationsJSON(configurationsJSON);
		sxpBlueprint.setElementInstancesJSON(elementInstancesJSON);

		return updateSXPBlueprint(sxpBlueprint);
	}

	protected SXPBlueprint startWorkflowInstance(
			long userId, SXPBlueprint sxpBlueprint,
			ServiceContext serviceContext)
		throws PortalException {

		String userPortraitURL = StringPool.BLANK;
		String userURL = StringPool.BLANK;

		if (serviceContext.getThemeDisplay() != null) {
			User user = _userLocalService.getUser(userId);

			userPortraitURL = user.getPortraitURL(
				serviceContext.getThemeDisplay());
			userURL = user.getDisplayURL(serviceContext.getThemeDisplay());
		}

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			sxpBlueprint.getCompanyId(), sxpBlueprint.getGroupId(), userId,
			Blueprint.class.getName(), sxpBlueprint.getSXPBlueprintId(),
			sxpBlueprint, serviceContext,
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_USER_PORTRAIT_URL, userPortraitURL
			).put(
				WorkflowConstants.CONTEXT_USER_URL, userURL
			).build());
	}

	private void _validate(
			Map<Locale, String> titleMap, String configurationsJSON,
			ServiceContext serviceContext)
		throws SXPBlueprintConfigurationsJSONException,
			   SXPBlueprintTitleException {

		if (!GetterUtil.getBoolean(
				serviceContext.getAttribute("skip.sxp_blueprint.validation"))) {

			_sxpBlueprintValidator.validateSXPBlueprint(
				titleMap, configurationsJSON);
		}
	}

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private SXPBlueprintValidator _sxpBlueprintValidator;

	@Reference
	private UserLocalService _userLocalService;

}