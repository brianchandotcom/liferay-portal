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

package com.liferay.portal.search.tuning.blueprints.service.impl;

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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.base.BlueprintLocalServiceBaseImpl;
import com.liferay.portal.search.tuning.blueprints.validation.BlueprintValidator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the blueprint local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.portal.search.tuning.blueprints.service.BlueprintLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlueprintLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.portal.search.tuning.blueprints.model.Blueprint",
	service = AopService.class
)
public class BlueprintLocalServiceImpl extends BlueprintLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Blueprint addBlueprint(
			long userId, long groupId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String configuration,
			String selectedElements, int type, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		_blueprintValidator.validate(titleMap, configuration);

		long blueprintId = counterLocalService.increment(
			Blueprint.class.getName());

		Blueprint blueprint = createBlueprint(blueprintId);

		blueprint.setGroupId(groupId);
		blueprint.setCompanyId(user.getCompanyId());
		blueprint.setUserId(user.getUserId());
		blueprint.setUserName(user.getFullName());
		blueprint.setCreateDate(serviceContext.getCreateDate(new Date()));
		blueprint.setModifiedDate(serviceContext.getModifiedDate(new Date()));
		blueprint.setUuid(serviceContext.getUuid());

		blueprint.setTitleMap(titleMap);
		blueprint.setDescriptionMap(descriptionMap);

		int status = WorkflowConstants.STATUS_DRAFT;

		blueprint.setStatus(status);

		blueprint.setStatusByUserId(userId);
		blueprint.setStatusDate(serviceContext.getModifiedDate(null));

		blueprint.setConfiguration(configuration);
		blueprint.setSelectedElements(selectedElements);
		blueprint.setType(type);

		blueprint = super.addBlueprint(blueprint);

		_resourceLocalService.addModelResources(blueprint, serviceContext);

		return startWorkflowInstance(userId, blueprint, serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public Blueprint deleteBlueprint(Blueprint blueprint)
		throws PortalException {

		_resourceLocalService.deleteResource(
			blueprint, ResourceConstants.SCOPE_INDIVIDUAL);

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			blueprint.getCompanyId(), blueprint.getGroupId(),
			Blueprint.class.getName(), blueprint.getBlueprintId());

		return super.deleteBlueprint(blueprint);
	}

	@Override
	public Blueprint deleteBlueprint(long blueprintId) throws PortalException {
		Blueprint blueprint = blueprintPersistence.findByPrimaryKey(
			blueprintId);

		return deleteBlueprint(blueprint);
	}

	public int getCompanyBlueprintsCount(long companyId, int type) {
		return blueprintPersistence.countByC_T(companyId, type);
	}

	public List<Blueprint> getGroupBlueprints(
		long groupId, int type, int start, int end) {

		return getGroupBlueprints(
			groupId, WorkflowConstants.STATUS_APPROVED, type, start, end);
	}

	public List<Blueprint> getGroupBlueprints(
		long groupId, int status, int type, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blueprintPersistence.findByG_T(groupId, type, start, end);
		}

		return blueprintPersistence.findByG_S_T(
			groupId, status, type, start, end);
	}

	public List<Blueprint> getGroupBlueprints(
		long groupId, int status, int type, int start, int end,
		OrderByComparator<Blueprint> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blueprintPersistence.findByG_T(
				groupId, type, start, end, orderByComparator);
		}

		return blueprintPersistence.findByG_S_T(
			groupId, status, type, start, end, orderByComparator);
	}

	public List<Blueprint> getGroupBlueprints(
		long groupId, int type, int start, int end,
		OrderByComparator<Blueprint> orderByComparator) {

		return getGroupBlueprints(
			groupId, WorkflowConstants.STATUS_APPROVED, type, start, end,
			orderByComparator);
	}

	public int getGroupBlueprintsCount(long groupId, int type) {
		return getGroupBlueprintsCount(
			groupId, WorkflowConstants.STATUS_APPROVED, type);
	}

	public int getGroupBlueprintsCount(long groupId, int status, int type) {
		return blueprintPersistence.countByG_S_T(groupId, status, type);
	}

	@Indexable(type = IndexableType.REINDEX)
	public Blueprint updateBlueprint(
			long userId, long blueprintId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String configuration,
			String selectedElements, ServiceContext serviceContext)
		throws PortalException {

		Blueprint blueprint = getBlueprint(blueprintId);

		_blueprintValidator.validate(titleMap, configuration);

		blueprint.setDescriptionMap(descriptionMap);
		blueprint.setModifiedDate(serviceContext.getModifiedDate(new Date()));
		blueprint.setTitleMap(titleMap);

		blueprint.setConfiguration(configuration);
		blueprint.setSelectedElements(selectedElements);

		return updateBlueprint(blueprint);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Blueprint updateStatus(long userId, long blueprintId, int status)
		throws PortalException {

		User user = _userLocalService.getUser(userId);
		Blueprint blueprint = getBlueprint(blueprintId);

		blueprint.setStatus(status);
		blueprint.setStatusByUserId(userId);
		blueprint.setStatusByUserName(user.getScreenName());
		blueprint.setStatusDate(new Date());

		return updateBlueprint(blueprint);
	}

	protected Blueprint startWorkflowInstance(
			long userId, Blueprint blueprint, ServiceContext serviceContext)
		throws PortalException {

		String userPortraitURL = StringPool.BLANK;
		String userURL = StringPool.BLANK;

		if (serviceContext.getThemeDisplay() != null) {
			User user = _userLocalService.getUser(userId);

			userPortraitURL = user.getPortraitURL(
				serviceContext.getThemeDisplay());
			userURL = user.getDisplayURL(serviceContext.getThemeDisplay());
		}

		Map<String, Serializable> workflowContext =
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_USER_PORTRAIT_URL, userPortraitURL
			).put(
				WorkflowConstants.CONTEXT_USER_URL, userURL
			).build();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			blueprint.getCompanyId(), blueprint.getGroupId(), userId,
			Blueprint.class.getName(), blueprint.getBlueprintId(), blueprint,
			serviceContext, workflowContext);
	}

	@Reference
	private BlueprintValidator _blueprintValidator;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}