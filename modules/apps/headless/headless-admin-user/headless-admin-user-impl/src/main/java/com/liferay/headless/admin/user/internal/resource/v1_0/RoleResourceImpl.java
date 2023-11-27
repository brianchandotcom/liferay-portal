/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.Role;
import com.liferay.headless.admin.user.dto.v1_0.RolePermission;
import com.liferay.headless.admin.user.resource.v1_0.RoleResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.RoleAssignmentException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.ResourcePermissionService;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserGroupRoleService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;
import com.liferay.roles.admin.role.type.contributor.provider.RoleTypeContributorProvider;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 * @author Crescenzo Rega
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

		return _roleDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				true, _getActions(roleId), _dtoConverterRegistry, roleId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			_roleService.getRole(roleId));
	}

	@Override
	public Page<Role> getRolesPage(
			Integer[] types, String search, Pagination pagination)
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
					contextCompany.getCompanyId(), search, types, null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				roleModel -> _roleDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						true, _getActions(roleModel.getRoleId()),
						_dtoConverterRegistry, roleModel.getRoleId(),
						contextAcceptLanguage.getPreferredLocale(),
						contextUriInfo, contextUser),
					roleModel)),
			pagination,
			_roleService.searchCount(
				contextCompany.getCompanyId(), search, types, null));
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
	public Role postRole(Role role) throws Exception {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			contextHttpServletRequest);

		com.liferay.portal.kernel.model.Role roleModel = _roleService.fetchRole(
			contextCompany.getCompanyId(), role.getExternalReferenceCode());

		List<RoleTypeContributor> roleTypeContributors = ListUtil.filter(
			_roleTypeContributorProvider.getRoleTypeContributors(),
			roleTypeContributor -> {
				if (Validator.isNull(role.getRoleType())) {
					return false;
				}

				return StringUtil.equals(
					roleTypeContributor.getTypeLabel(), role.getRoleType());
			});

		int type = 0;
		String className = null;

		if (ListUtil.isNotEmpty(roleTypeContributors)) {
			RoleTypeContributor roleTypeContributor = roleTypeContributors.get(
				0);

			type = roleTypeContributor.getType();
			className = roleTypeContributor.getClassName();
		}

		Map<Locale, String> titleMap = null;

		if (MapUtil.isNotEmpty(role.getName_i18n())) {
			titleMap = LocalizedMapUtil.getLocalizedMap(role.getName_i18n());
		}

		Map<Locale, String> descriptionMap = null;

		if (MapUtil.isNotEmpty(role.getDescription_i18n())) {
			descriptionMap = LocalizedMapUtil.getLocalizedMap(
				role.getDescription_i18n());
		}

		if (roleModel == null) {
			roleModel = _roleService.addRole(
				className, 0, role.getExternalReferenceCode(), titleMap,
				descriptionMap, type, null, serviceContext);
		}
		else {
			roleModel = _roleService.updateRole(
				roleModel.getRoleId(), role.getExternalReferenceCode(),
				titleMap, descriptionMap, null, serviceContext);
		}

		_updateRolePermission(roleModel, role);

		return _roleDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				true, _getActions(roleModel.getRoleId()), _dtoConverterRegistry,
				roleModel.getRoleId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			roleModel);
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

	private void _updateRolePermission(
			com.liferay.portal.kernel.model.Role roleModel, Role roleDto)
		throws Exception {

		for (RolePermission rolePermission : roleDto.getRolePermissions()) {
			if (rolePermission.getScope() ==
					ResourceConstants.SCOPE_INDIVIDUAL) {

				continue;
			}

			for (String actionId : rolePermission.getActionIds()) {
				_resourcePermissionService.addResourcePermission(
					contextUser.getGroupId(), contextCompany.getCompanyId(),
					rolePermission.getResourceName(),
					Math.toIntExact(rolePermission.getScope()),
					rolePermission.getPrimaryKey(), roleModel.getRoleId(),
					actionId);
			}
		}
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private ResourcePermissionService _resourcePermissionService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.user.internal.dto.v1_0.converter.RoleDTOConverter)"
	)
	private DTOConverter<com.liferay.portal.kernel.model.Role, Role>
		_roleDTOConverter;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Role)"
	)
	private ModelResourcePermission<com.liferay.portal.kernel.model.Role>
		_roleModelResourcePermission;

	@Reference
	private RoleService _roleService;

	@Reference
	private RoleTypeContributorProvider _roleTypeContributorProvider;

	@Reference
	private UserGroupRoleService _userGroupRoleService;

	@Reference
	private UserService _userService;

}