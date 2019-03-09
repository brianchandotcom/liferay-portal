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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.Role;
import com.liferay.headless.foundation.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.foundation.resource.v1_0.RoleResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

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
	public Page<Role> getMyUserAccountRolesPage(
			Long myUserAccountId, Pagination pagination)
		throws Exception {

		return _getRolPage(myUserAccountId);
	}

	@Override
	public Role getRole(Long roleId) throws Exception {
		return _toRole(_roleService.getRole(roleId));
	}

	@Override
	public Page<Role> getRolesPage(Pagination pagination) throws Exception {
		Integer[] roleTypes = {
			RoleConstants.TYPE_ORGANIZATION, RoleConstants.TYPE_REGULAR,
			RoleConstants.TYPE_SITE
		};

		return Page.of(
			transform(
				_roleService.search(
					contextCompany.getCompanyId(), null, roleTypes, null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toRole),
			pagination,
			_roleService.searchCount(
				contextCompany.getCompanyId(), null, roleTypes, null));
	}

	@Override
	public Page<Role> getUserAccountRolesPage(
			Long userAccountId, Pagination pagination)
		throws Exception {

		return _getRolPage(userAccountId);
	}

	private Page<Role> _getRolPage(Long myUserAccountId)
		throws PortalException {

		return Page.of(
			transform(
				_roleService.getUserRoles(myUserAccountId), this::_toRole));
	}

	private Role _toRole(com.liferay.portal.kernel.model.Role role)
		throws PortalException {

		return new Role() {
			{
				id = role.getRoleId();
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUserById(role.getUserId()));
				dateCreated = role.getCreateDate();
				dateModified = role.getModifiedDate();
				description = role.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				name = role.getName();
				roleType = role.getTypeLabel();
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					role.getAvailableLanguageIds());
			}
		};
	}

	@Reference
	private Portal _portal;

	@Reference
	private RoleService _roleService;

	@Reference
	private UserLocalService _userLocalService;

}