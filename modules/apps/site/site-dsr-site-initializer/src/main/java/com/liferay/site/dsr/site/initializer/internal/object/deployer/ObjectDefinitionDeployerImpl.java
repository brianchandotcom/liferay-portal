/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.object.deployer;

import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.site.dsr.site.initializer.internal.util.SiteInitializerUtil;
import com.liferay.site.initializer.SiteInitializer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(service = ObjectDefinitionDeployer.class)
public class ObjectDefinitionDeployerImpl implements ObjectDefinitionDeployer {

	@Override
	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		if (!Objects.equals(
				objectDefinition.getExternalReferenceCode(), "L_DSR_ROOM")) {

			return Collections.emptyList();
		}

		try {
			LayoutSetPrototype layoutSetPrototype = _addLayoutSetPrototype(
				objectDefinition.getCompanyId());

			_setResourcePermissions(
				objectDefinition.getCompanyId(),
				layoutSetPrototype.getGroupId(), objectDefinition);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return Collections.emptyList();
	}

	private LayoutSetPrototype _addLayoutSetPrototype(long companyId)
		throws PortalException {

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.
				fetchLayoutSetPrototypeByUuidAndCompanyId(
					"L_DSR_LAYOUT_SET_PROTOTYPE", companyId);

		if (layoutSetPrototype != null) {
			return layoutSetPrototype;
		}

		User user = _userLocalService.getGuestUser(companyId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute("addDefaultLayout", Boolean.FALSE);
		serviceContext.setUuid("L_DSR_LAYOUT_SET_PROTOTYPE");

		layoutSetPrototype =
			_layoutSetPrototypeLocalService.addLayoutSetPrototype(
				user.getUserId(), companyId,
				HashMapBuilder.put(
					LocaleUtil.getDefault(), GroupConstants.DSR
				).build(),
				null, true, true, false, serviceContext);

		SiteInitializerUtil.initialize(
			companyId, layoutSetPrototype.getGroup(), _siteInitializer);

		return layoutSetPrototype;
	}

	private void _setResourcePermissions(
			long companyId, long groupId, ObjectDefinition objectDefinition)
		throws PortalException {

		Role role = _roleLocalService.fetchRoleByExternalReferenceCode(
			"L_DSR_CONTRIBUTOR", companyId);

		if (role == null) {
			User user = _userLocalService.getGuestUser(companyId);

			_roleLocalService.addRole(
				"L_DSR_CONTRIBUTOR", user.getUserId(), null, 0,
				"DSR Contributor", null, null, RoleConstants.TYPE_SITE, null,
				null);
		}

		role = _roleLocalService.fetchRoleByExternalReferenceCode(
			"L_DSR_SELLER", companyId);

		if (role == null) {
			User user = _userLocalService.getGuestUser(companyId);

			role = _roleLocalService.addRole(
				"L_DSR_SELLER", user.getUserId(), null, 0, "DSR Seller", null,
				null, RoleConstants.TYPE_REGULAR, null, null);

			_resourcePermissionLocalService.addResourcePermission(
				companyId, PortletKeys.PORTAL, ResourceConstants.SCOPE_COMPANY,
				String.valueOf(companyId), role.getRoleId(),
				ActionKeys.VIEW_CONTROL_PANEL);
			_resourcePermissionLocalService.addResourcePermission(
				role.getCompanyId(), objectDefinition.getResourceName(),
				ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
				role.getRoleId(), ObjectActionKeys.ADD_OBJECT_ENTRY);
		}

		Map<String, String[]> permissionsMap = HashMapBuilder.put(
			RoleConstants.OWNER,
			new String[] {
				ActionKeys.ADD_DOCUMENT, ActionKeys.ADVANCED_UPDATE,
				ActionKeys.UPDATE, ActionKeys.SUBSCRIBE, ActionKeys.VIEW
			}
		).put(
			RoleConstants.SITE_MEMBER,
			new String[] {ActionKeys.SUBSCRIBE, ActionKeys.VIEW}
		).put(
			"DSR Contributor",
			new String[] {
				ActionKeys.ADD_DOCUMENT, ActionKeys.ADVANCED_UPDATE,
				ActionKeys.UPDATE, ActionKeys.SUBSCRIBE, ActionKeys.VIEW
			}
		).build();

		for (Role currentRole :
				_roleLocalService.getGroupRolesAndTeamRoles(
					companyId, null, Arrays.asList(RoleConstants.ADMINISTRATOR),
					null, null,
					new int[] {
						RoleConstants.TYPE_REGULAR, RoleConstants.TYPE_SITE
					},
					0, 0, QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			String[] actionIds = permissionsMap.get(currentRole.getName());

			if (actionIds == null) {
				actionIds = new String[0];
			}

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, "com.liferay.document.library",
				ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(groupId),
				currentRole.getRoleId(), actionIds);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionDeployerImpl.class);

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference(
		target = "(site.initializer.key=com.liferay.digital.sales.room.site.initializer)"
	)
	private SiteInitializer _siteInitializer;

	@Reference
	private UserLocalService _userLocalService;

}