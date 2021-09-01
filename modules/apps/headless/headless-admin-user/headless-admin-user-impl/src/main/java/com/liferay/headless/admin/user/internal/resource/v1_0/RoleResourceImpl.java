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

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.Role;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.admin.user.resource.v1_0.RoleResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.RoleAssignmentException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.UserGroupRoleService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/role.properties",
	scope = ServiceScope.PROTOTYPE, service = RoleResource.class
)
public class RoleResourceImpl extends BaseRoleResourceImpl {

	@Override
	public void deleteOrganizationRoleUserAccountAssociation(
			Long roleId, Long userAccountId, Long organizationId)
		throws Exception {

		_checkRoleType(roleId, RoleConstants.TYPE_ORGANIZATION);

		Organization organization = _organizationService.getOrganization(
			organizationId);

		_userGroupRoleService.deleteUserGroupRoles(
			userAccountId, organization.getGroupId(), new long[] {roleId});
	}

	@Override
	public void deleteRoleUserAccountAssociation(
			Long roleId, Long userAccountId)
		throws Exception {

		_userService.deleteRoleUser(roleId, userAccountId);
	}

	@Override
	public void deleteSiteRoleUserAccountAssociation(
			Long roleId, Long userAccountId, Long siteId)
		throws Exception {

		_checkRoleType(roleId, RoleConstants.TYPE_SITE);

		_userGroupRoleService.deleteUserGroupRoles(
			userAccountId, siteId, new long[] {roleId});
	}

	@Override
	public Role getRole(Long roleId) throws Exception {
		com.liferay.portal.kernel.model.Role role = _roleService.fetchRole(
			roleId);

		if (role == null) {
			throw new NoSuchRoleException(
				"No role exists with role ID " + roleId);
		}

		return _toRole(_roleService.getRole(roleId));
	}

	@Override
	public Page<Role> getRolesPage(Integer[] types, Pagination pagination)
		throws Exception {

		if (types == null) {
			types = new Integer[] {
				RoleConstants.TYPE_ORGANIZATION, RoleConstants.TYPE_REGULAR,
				RoleConstants.TYPE_SITE
			};
		}

		return Page.of(
			HashMapBuilder.<String, Map<String, String>>put(
				"get",
				addAction(
					ActionKeys.VIEW, "getRolesPage", Role.class.getName(), 0L)
			).build(),
			transform(
				_roleService.search(
					contextCompany.getCompanyId(), null, types, null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toRole),
			pagination,
			_roleService.searchCount(
				contextCompany.getCompanyId(), null, types, null));
	}

	@Override
	public void postOrganizationRoleUserAccountAssociation(
			Long roleId, Long userAccountId, Long organizationId)
		throws Exception {

		_checkRoleType(roleId, RoleConstants.TYPE_ORGANIZATION);

		Organization organization = _organizationService.getOrganization(
			organizationId);

		_userGroupRoleService.addUserGroupRoles(
			userAccountId, organization.getGroupId(), new long[] {roleId});
	}

	@Override
	public void postRoleUserAccountAssociation(Long roleId, Long userAccountId)
		throws Exception {

		_checkRoleType(roleId, RoleConstants.TYPE_REGULAR);

		_userService.addRoleUsers(roleId, new long[] {userAccountId});
	}

	@Override
	public void postSiteRoleUserAccountAssociation(
			Long roleId, Long userAccountId, Long siteId)
		throws Exception {

		_checkRoleType(roleId, RoleConstants.TYPE_SITE);

		_userGroupRoleService.addUserGroupRoles(
			userAccountId, siteId, new long[] {roleId});
	}

	private void _checkRoleType(long roleId, int type) throws Exception {
		com.liferay.portal.kernel.model.Role serviceBuilderRole =
			_roleService.getRole(roleId);

		if (serviceBuilderRole.getType() != type) {
			throw new RoleAssignmentException(
				StringBundler.concat(
					"Role type ",
					RoleConstants.getTypeLabel(serviceBuilderRole.getType()),
					" is not role type ", RoleConstants.getTypeLabel(type)));
		}
	}

	private Map<String, Map<String, String>> _getActions(Long roleId) {
		return HashMapBuilder.<String, Map<String, String>>put(
			"create-organization-role-user-account-association",
			addAction(
				ActionKeys.ASSIGN_MEMBERS, roleId,
				"postOrganizationRoleUserAccountAssociation",
				_roleModelResourcePermission)
		).put(
			"create-role-user-account-association",
			addAction(
				ActionKeys.ASSIGN_MEMBERS, roleId,
				"postRoleUserAccountAssociation", _roleModelResourcePermission)
		).put(
			"create-site-role-user-account-association",
			addAction(
				ActionKeys.ASSIGN_MEMBERS, roleId,
				"postSiteRoleUserAccountAssociation",
				_roleModelResourcePermission)
		).put(
			"delete-organization-role-user-account-association",
			addAction(
				ActionKeys.ASSIGN_MEMBERS, roleId,
				"deleteOrganizationRoleUserAccountAssociation",
				_roleModelResourcePermission)
		).put(
			"delete-role-user-account-association",
			addAction(
				ActionKeys.ASSIGN_MEMBERS, roleId,
				"deleteRoleUserAccountAssociation",
				_roleModelResourcePermission)
		).put(
			"delete-site-role-user-account-association",
			addAction(
				ActionKeys.ASSIGN_MEMBERS, roleId,
				"deleteSiteRoleUserAccountAssociation",
				_roleModelResourcePermission)
		).put(
			"get",
			addAction(
				ActionKeys.VIEW, roleId, "getRole",
				_roleModelResourcePermission)
		).build();
	}

	private Role _toRole(com.liferay.portal.kernel.model.Role role)
		throws Exception {

		return new Role() {
			{
				actions = _getActions(role.getRoleId());
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					role.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.fetchUser(role.getUserId()));
				dateCreated = role.getCreateDate();
				dateModified = role.getModifiedDate();
				description = role.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				description_i18n = LocalizedMapUtil.getI18nMap(
					contextAcceptLanguage.isAcceptAllLanguages(),
					role.getDescriptionMap());
				id = role.getRoleId();
				name = role.getTitle(
					contextAcceptLanguage.getPreferredLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					contextAcceptLanguage.isAcceptAllLanguages(),
					role.getTitleMap());
				roleType = role.getTypeLabel();
			}
		};
	}

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Role)"
	)
	private ModelResourcePermission<com.liferay.portal.kernel.model.Role>
		_roleModelResourcePermission;

	@Reference
	private RoleService _roleService;

	@Reference
	private UserGroupRoleService _userGroupRoleService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}