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

package com.liferay.commerce.headless.user.apio.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true, service = RoleHelper.class)
public class RoleHelper {

	public Role addRole(
			String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		User user = _userLocalService.getUserById(
			PrincipalThreadLocal.getUserId());

		ServiceContext serviceContext = _getServiceContext(user);

		return _roleLocalService.addRole(
			user.getUserId(), null, 0, name, titleMap, descriptionMap,
			RoleConstants.TYPE_REGULAR, null, serviceContext);
	}

	public void addUser(Role role, List<Long> userIds) throws PortalException {
		if (userIds != null) {
			_removeAllUsers(role);

			for (Long userId : userIds) {
				User userMember = _userLocalService.getUser(userId);

				if (userMember != null) {
					_userLocalService.addRoleUser(role.getRoleId(), userMember);
				}
			}
		}
	}

	public Role updateRole(
			Long roleId, String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		User user = _userLocalService.getUserById(
			PrincipalThreadLocal.getUserId());

		ServiceContext serviceContext = _getServiceContext(user);

		return _roleLocalService.updateRole(
			roleId, name, titleMap, descriptionMap, null, serviceContext);
	}

	private ServiceContext _getServiceContext(User user) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(user.getGroupId());
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private void _removeAllUsers(Role role) {
		_userLocalService.clearRoleUsers(role.getRoleId());
	}

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}