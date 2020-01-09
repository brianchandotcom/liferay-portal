/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.app.builder.rest.internal.resource.v1_0;

import com.liferay.app.builder.constants.AppBuilderConstants;
import com.liferay.app.builder.rest.dto.v1_0.AppModelPermission;
import com.liferay.app.builder.rest.resource.v1_0.AppModelPermissionResource;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ValidationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/app-model-permission.properties",
	scope = ServiceScope.PROTOTYPE, service = AppModelPermissionResource.class
)
public class AppModelPermissionResourceImpl
	extends BaseAppModelPermissionResourceImpl {

	@Override
	public Page<AppModelPermission> getAppModelPermissionsPage(String roleNames)
		throws Exception {

		_checkPortletPermissions();

		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(
				AppBuilderConstants.RESOURCE_NAME);

		return Page.of(
			transform(
				_getRoles(
					contextCompany, _roleLocalService,
					StringUtil.split(roleNames)),
				role -> _toAppModelPermission(
					contextCompany.getCompanyId(), contextCompany.getGroupId(),
					resourceActions, AppBuilderConstants.RESOURCE_NAME, role)));
	}

	@Override
	public void putAppModelPermission(AppModelPermission[] appModelPermissions)
		throws Exception {

		_checkPortletPermissions();

		long siteGroupId = _portal.getSiteGroupId(contextCompany.getGroupId());

		_resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(), siteGroupId,
			AppBuilderConstants.RESOURCE_NAME, String.valueOf(siteGroupId),
			_getModelPermissions(
				contextCompany.getCompanyId(), appModelPermissions, siteGroupId,
				AppBuilderConstants.RESOURCE_NAME));

		for (AppModelPermission AppModelPermission : appModelPermissions) {
			if (!ArrayUtil.contains(
					AppModelPermission.getActionIds(), ActionKeys.MANAGE,
					true)) {

				continue;
			}

			String sessionId = CookieKeys.getCookie(
				contextHttpServletRequest, CookieKeys.JSESSIONID);

			com.liferay.data.engine.rest.client.resource.v2_0.
				DataModelPermissionResource dataModelPermissionResource =
					com.liferay.data.engine.rest.client.resource.v2_0.
						DataModelPermissionResource.builder(
						).endpoint(
							_portal.getHost(contextHttpServletRequest),
							contextHttpServletRequest.getServerPort(),
							contextHttpServletRequest.getScheme()
						).header(
							"Cookie", "JSESSIONID=" + sessionId
						).parameter(
							"p_auth",
							AuthTokenUtil.getToken(contextHttpServletRequest)
						).build();

			dataModelPermissionResource.putDataModelPermission(
				_getDataModelPermissions(AppModelPermission.getRoleName()));
		}
	}

	private void _checkPortletPermissions() throws PortalException {
		if (_portletResourcePermission.contains(
				GuestOrUserUtil.getPermissionChecker(),
				contextCompany.getGroupId(), ActionKeys.MANAGE)) {

			return;
		}

		_portletResourcePermission.check(
			GuestOrUserUtil.getPermissionChecker(), contextCompany.getGroupId(),
			ActionKeys.PERMISSIONS);
	}

	private com.liferay.data.engine.rest.client.dto.v2_0.DataModelPermission[]
		_getDataModelPermissions(String dataModelPermissionRoleName) {

		return
			new com.liferay.data.engine.rest.client.dto.v2_0.DataModelPermission
				[] {
			new com.liferay.data.engine.rest.client.dto.v2_0.
				DataModelPermission() {

				{
					actionIds = new String[] {
						"ADD_DATA_DEFINITION", "ADD_DATA_RECORD_COLLECTION",
						ActionKeys.PERMISSIONS
					};
					roleName = dataModelPermissionRoleName;
				}
			}
		};
	}

	private ModelPermissions _getModelPermissions(
			long companyId, AppModelPermission[] appModelPermissions,
			long primKey, String resourceName)
		throws PortalException {

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			new String[0], new String[0], resourceName);

		for (AppModelPermission AppModelPermission : appModelPermissions) {
			String[] actionIds = AppModelPermission.getActionIds();

			if (actionIds.length == 0) {
				List<ResourceAction> resourceActions =
					_resourceActionLocalService.getResourceActions(
						resourceName);

				Role role = _roleLocalService.getRole(
					companyId, AppModelPermission.getRoleName());

				for (ResourceAction resourceAction : resourceActions) {
					_resourcePermissionLocalService.removeResourcePermission(
						companyId, resourceName,
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(primKey), role.getRoleId(),
						resourceAction.getActionId());
				}

				continue;
			}

			modelPermissions.addRolePermissions(
				AppModelPermission.getRoleName(),
				AppModelPermission.getActionIds());
		}

		return modelPermissions;
	}

	private List<Role> _getRoles(
			Company company, RoleLocalService roleLocalService,
			String[] roleNames)
		throws PortalException {

		List<String> invalidRoleNames = new ArrayList<>();
		List<Role> roles = new ArrayList<>();

		for (String roleName : roleNames) {
			try {
				Role role = roleLocalService.getRole(
					company.getCompanyId(), roleName);

				roles.add(role);
			}
			catch (NoSuchRoleException nsre) {
				if (_log.isDebugEnabled()) {
					_log.debug(roleName, nsre);
				}

				invalidRoleNames.add(roleName);
			}
		}

		if (!invalidRoleNames.isEmpty()) {
			throw new ValidationException(
				"Invalid roles: " + ArrayUtil.toStringArray(invalidRoleNames));
		}

		return roles;
	}

	private AppModelPermission _toAppModelPermission(
		Long companyId, Long id, List<ResourceAction> resourceActions,
		String resourceName, Role role) {

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(id), role.getRoleId());

		if (resourcePermission == null) {
			return null;
		}

		Set<String> actionsIdsSet = new HashSet<>();

		long actionIds = resourcePermission.getActionIds();

		for (ResourceAction resourceAction : resourceActions) {
			long bitwiseValue = resourceAction.getBitwiseValue();

			if ((actionIds & bitwiseValue) == bitwiseValue) {
				actionsIdsSet.add(resourceAction.getActionId());
			}
		}

		return new AppModelPermission() {
			{
				actionIds = actionsIdsSet.toArray(new String[0]);
				roleName = role.getName();
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AppModelPermissionResourceImpl.class);

	@Reference
	private Portal _portal;

	@Reference(target = "(resource.name=com.liferay.app.builder)")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}