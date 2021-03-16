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

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsActionKeys;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsConstants;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintLocalService;
import com.liferay.portal.search.tuning.blueprints.service.base.BlueprintServiceBaseImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * The implementation of the blueprint remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.portal.search.tuning.blueprints.service.BlueprintService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlueprintServiceBaseImpl
 */
@Component(
	property = {
		"json.web.service.context.name=search",
		"json.web.service.context.path=Blueprint"
	},
	service = AopService.class
)
public class BlueprintServiceImpl extends BlueprintServiceBaseImpl {

	public Blueprint addCompanyBlueprint(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String configuration, String selectedElements, int type,
			ServiceContext serviceContext)
		throws PortalException {

		String actionKey = BlueprintsActionKeys.getActionKeyForBlueprintType(
			type, ActionKeys.ADD_ENTRY);

		long groupId = _getCompanyGroupId(serviceContext);

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, actionKey);

		return blueprintLocalService.addBlueprint(
			getUserId(), groupId, titleMap, descriptionMap, configuration,
			selectedElements, type, serviceContext);
	}

	public Blueprint addGroupBlueprint(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String configuration, String selectedElements, int type,
			ServiceContext serviceContext)
		throws PortalException {

		String actionKey = BlueprintsActionKeys.getActionKeyForBlueprintType(
			type, ActionKeys.ADD_ENTRY);

		long groupId = serviceContext.getScopeGroupId();

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, actionKey);

		return blueprintLocalService.addBlueprint(
			getUserId(), groupId, titleMap, descriptionMap, configuration,
			selectedElements, type, serviceContext);
	}

	public Blueprint deleteBlueprint(long blueprintId) throws PortalException {
		Blueprint blueprint = _blueprintLocalService.getBlueprint(blueprintId);

		String actionKey = BlueprintsActionKeys.getActionKeyForBlueprintType(
			blueprint.getType(), ActionKeys.DELETE);

		_blueprintModelResourcePermission.check(
			getPermissionChecker(), blueprintId, actionKey);

		return blueprintLocalService.deleteBlueprint(blueprintId);
	}

	public Blueprint getBlueprint(long blueprintId) throws PortalException {
		Blueprint blueprint = _blueprintLocalService.getBlueprint(blueprintId);

		String actionKey = BlueprintsActionKeys.getActionKeyForBlueprintType(
			blueprint.getType(), BlueprintsActionKeys.APPLY_BLUEPRINT);

		_blueprintModelResourcePermission.check(
			getPermissionChecker(), blueprint, actionKey);

		return blueprint;
	}

	public List<Blueprint> getGroupBlueprints(
		long companyId, int type, int start, int end) {

		return getGroupBlueprints(
			companyId, WorkflowConstants.STATUS_APPROVED, type, start, end);
	}

	public List<Blueprint> getGroupBlueprints(
		long companyId, int status, int type, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blueprintPersistence.filterFindByG_T(
				companyId, type, start, end);
		}

		return blueprintPersistence.filterFindByG_S_T(
			companyId, status, type, start, end);
	}

	public List<Blueprint> getGroupBlueprints(
		long companyId, int status, int type, int start, int end,
		OrderByComparator<Blueprint> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blueprintPersistence.findByG_T(
				companyId, type, start, end, orderByComparator);
		}

		return blueprintPersistence.filterFindByG_S_T(
			companyId, status, type, start, end, orderByComparator);
	}

	public List<Blueprint> getGroupBlueprints(
		long companyId, int type, int start, int end,
		OrderByComparator<Blueprint> orderByComparator) {

		return getGroupBlueprints(
			companyId, WorkflowConstants.STATUS_APPROVED, type, start, end,
			orderByComparator);
	}

	public int getGroupBlueprintsCount(long companyId, int type) {
		return getGroupBlueprintsCount(
			companyId, WorkflowConstants.STATUS_APPROVED, type);
	}

	public int getGroupBlueprintsCount(long companyId, int status, int type) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return blueprintPersistence.countByG_T(companyId, type);
		}

		return blueprintPersistence.countByG_S_T(companyId, status, type);
	}

	public Blueprint updateBlueprint(
			long blueprintId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String configuration,
			String selectedElements, ServiceContext serviceContext)
		throws PortalException {

		Blueprint blueprint = _blueprintLocalService.getBlueprint(blueprintId);

		String actionKey = BlueprintsActionKeys.getActionKeyForBlueprintType(
			blueprint.getType(), ActionKeys.UPDATE);

		_blueprintModelResourcePermission.check(
			getPermissionChecker(), blueprintId, actionKey);

		return _blueprintLocalService.updateBlueprint(
			getUserId(), blueprintId, titleMap, descriptionMap, configuration,
			selectedElements, serviceContext);
	}

	private long _getCompanyGroupId(ServiceContext serviceContext)
		throws PortalException {

		Company company = _companyLocalService.getCompany(
			serviceContext.getCompanyId());

		return company.getGroupId();
	}

	@Reference
	private BlueprintLocalService _blueprintLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.portal.search.tuning.blueprints.model.Blueprint)"
	)
	private volatile ModelResourcePermission<Blueprint>
		_blueprintModelResourcePermission;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(resource.name=" + BlueprintsConstants.RESOURCE_NAME + ")"
	)
	private volatile PortletResourcePermission _portletResourcePermission;

	@Reference
	private UserLocalService _userLocalService;

}