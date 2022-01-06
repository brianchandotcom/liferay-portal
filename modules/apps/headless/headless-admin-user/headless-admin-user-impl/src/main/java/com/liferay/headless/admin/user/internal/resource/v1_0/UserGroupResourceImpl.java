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

import com.liferay.headless.admin.user.dto.v1_0.UserGroup;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.UserGroupResourceDTOConverter;
import com.liferay.headless.admin.user.resource.v1_0.UserGroupResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/user-group.properties",
	scope = ServiceScope.PROTOTYPE, service = UserGroupResource.class
)
public class UserGroupResourceImpl extends BaseUserGroupResourceImpl {

	@Override
	public void deleteUserGroup(Long userGroupId) throws PortalException {
		_userGroupService.deleteUserGroup(userGroupId);
	}

	@Override
	public UserGroup getUserGroup(Long userGroupId) throws Exception {
		return _toUserGroup(_userGroupService.getUserGroup(userGroupId));
	}

	@Override
	public UserGroup postUserGroup(UserGroup userGroup) throws Exception {
		return _toUserGroup(
			_userGroupService.addUserGroup(
				userGroup.getName(), userGroup.getDescription(), null));
	}

	@Override
	public void postUserGroupUsers(Long userGroupId, Long[] userIds)
		throws Exception {

		_userService.addUserGroupUsers(userGroupId, ArrayUtil.toArray(userIds));
	}

	@Override
	public UserGroup putUserGroup(Long userGroupId, UserGroup userGroup)
		throws Exception {

		return _toUserGroup(
			_userGroupService.updateUserGroup(
				userGroupId, userGroup.getName(), userGroup.getDescription(),
				null));
	}

	private DTOConverterContext _getDTOConverterContext(long userGroupId) {
		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			HashMapBuilder.<String, Map<String, String>>put(
				"delete",
				addAction(
					ActionKeys.DELETE, userGroupId, "deleteUserGroup",
					_userGroupModelResourcePermission)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, userGroupId, "getUserGroup",
					_userGroupModelResourcePermission)
			).put(
				"patch",
				addAction(
					ActionKeys.UPDATE, userGroupId, "patchUserGroup",
					_userGroupModelResourcePermission)
			).put(
				"put",
				addAction(
					ActionKeys.UPDATE, userGroupId, "putUserGroup",
					_userGroupModelResourcePermission)
			).build(),
			null, contextHttpServletRequest, userGroupId,
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private UserGroup _toUserGroup(
			com.liferay.portal.kernel.model.UserGroup userGroup)
		throws Exception {

		return _userGroupResourceDTOConverter.toDTO(
			_getDTOConverterContext(userGroup.getUserGroupId()), userGroup);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.UserGroup)"
	)
	private ModelResourcePermission<com.liferay.portal.kernel.model.UserGroup>
		_userGroupModelResourcePermission;

	@Reference
	private UserGroupResourceDTOConverter _userGroupResourceDTOConverter;

	@Reference
	private UserGroupService _userGroupService;

	@Reference
	private UserService _userService;

}