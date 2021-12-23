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
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.search.experiences.exception.SXPBlueprintConfigurationJSONException;
import com.liferay.search.experiences.exception.SXPBlueprintTitleException;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.base.SXPBlueprintLocalServiceBaseImpl;
import com.liferay.search.experiences.validator.SXPBlueprintValidator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Petteri Karttunen
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.search.experiences.model.SXPBlueprint",
	service = AopService.class
)
public class SXPBlueprintLocalServiceImpl
	extends SXPBlueprintLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPBlueprint addSXPBlueprint(
			long userId, String configurationJSON,
			Map<Locale, String> descriptionMap, String elementInstancesJSON,
			String schemaVersion, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(configurationJSON, titleMap, serviceContext);

		SXPBlueprint sxpBlueprint = sxpBlueprintPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		sxpBlueprint.setCompanyId(user.getCompanyId());
		sxpBlueprint.setUserId(user.getUserId());
		sxpBlueprint.setUserName(user.getFullName());

		sxpBlueprint.setConfigurationJSON(configurationJSON);
		sxpBlueprint.setDescriptionMap(descriptionMap);
		sxpBlueprint.setElementInstancesJSON(elementInstancesJSON);
		sxpBlueprint.setSchemaVersion(schemaVersion);
		sxpBlueprint.setTitleMap(titleMap);
		sxpBlueprint.setStatus(WorkflowConstants.STATUS_DRAFT);
		sxpBlueprint.setStatusByUserId(user.getUserId());
		sxpBlueprint.setStatusDate(serviceContext.getModifiedDate(null));

		sxpBlueprint = sxpBlueprintPersistence.update(sxpBlueprint);

		_resourceLocalService.addModelResources(sxpBlueprint, serviceContext);

		_startWorkflowInstance(userId, sxpBlueprint, serviceContext);

		return sxpBlueprint;
	}

	@Override
	public void deleteCompanySXPBlueprints(long companyId)
		throws PortalException {

		List<SXPBlueprint> sxpBlueprints =
			sxpBlueprintPersistence.findByCompanyId(companyId);

		for (SXPBlueprint sxpBlueprint : sxpBlueprints) {
			sxpBlueprintLocalService.deleteSXPBlueprint(sxpBlueprint);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
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

		sxpBlueprint = sxpBlueprintPersistence.remove(sxpBlueprint);

		_resourceLocalService.deleteResource(
			sxpBlueprint, ResourceConstants.SCOPE_INDIVIDUAL);

		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			sxpBlueprint.getCompanyId(), 0, SXPBlueprint.class.getName(),
			sxpBlueprint.getSXPBlueprintId());

		return sxpBlueprint;
	}

	@Override
	public int getSXPBlueprintsCount(long companyId) {
		return sxpBlueprintPersistence.countByCompanyId(companyId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPBlueprint updateStatus(
			long userId, long sxpBlueprintId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		SXPBlueprint sxpBlueprint = sxpBlueprintPersistence.findByPrimaryKey(
			sxpBlueprintId);

		if (sxpBlueprint.getStatus() == status) {
			return sxpBlueprint;
		}

		sxpBlueprint.setStatus(status);

		User user = _userLocalService.getUser(userId);

		sxpBlueprint.setStatusByUserId(user.getUserId());
		sxpBlueprint.setStatusByUserName(user.getFullName());

		sxpBlueprint.setStatusDate(serviceContext.getModifiedDate(null));

		return sxpBlueprintPersistence.update(sxpBlueprint);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPBlueprint updateSXPBlueprint(
			long userId, long sxpBlueprintId, String configurationJSON,
			Map<Locale, String> descriptionMap, String elementInstancesJSON,
			String schemaVersion, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(configurationJSON, titleMap, serviceContext);

		SXPBlueprint sxpBlueprint = sxpBlueprintPersistence.findByPrimaryKey(
			sxpBlueprintId);

		sxpBlueprint.setConfigurationJSON(configurationJSON);
		sxpBlueprint.setDescriptionMap(descriptionMap);
		sxpBlueprint.setElementInstancesJSON(elementInstancesJSON);
		sxpBlueprint.setTitleMap(titleMap);

		return updateSXPBlueprint(sxpBlueprint);
	}

	private void _startWorkflowInstance(
			long userId, SXPBlueprint sxpBlueprint,
			ServiceContext serviceContext)
		throws PortalException {

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			sxpBlueprint.getCompanyId(), 0, userId,
			SXPBlueprint.class.getName(), sxpBlueprint.getSXPBlueprintId(),
			sxpBlueprint, serviceContext);
	}

	private void _validate(
			String configurationJSON, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws SXPBlueprintConfigurationJSONException,
			   SXPBlueprintTitleException {

		if (!GetterUtil.getBoolean(
				serviceContext.getAttribute(
					SXPBlueprintLocalServiceImpl.class.getName() +
						"#_validate"),
				true)) {

			return;
		}

		_sxpBlueprintValidator.validate(configurationJSON, titleMap);
	}

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private SXPBlueprintValidator _sxpBlueprintValidator;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}