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

package com.liferay.commerce.initializer.customer.portal.internal;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.site.initializer.GroupInitializer;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class CustomerPortalCompanySiteInitializer
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company)
		throws PortalException {

		if (_SITE_INITIALIZER_ENABLED == Boolean.FALSE) {
			return;
		}

		long companyId = company.getCompanyId();

		Group guestGroup = _groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		ExpandoBridge groupExpandoBridge = guestGroup.getExpandoBridge();

		if (!groupExpandoBridge.hasAttribute(
				_RUN_CUSTOMER_PORTAL_COMPANY_SITE_INITIALIZER)) {

			groupExpandoBridge.addAttribute(
				_RUN_CUSTOMER_PORTAL_COMPANY_SITE_INITIALIZER,
				ExpandoColumnConstants.BOOLEAN, Boolean.TRUE, Boolean.FALSE);
		}
		else if (groupExpandoBridge.getAttribute(
					_RUN_CUSTOMER_PORTAL_COMPANY_SITE_INITIALIZER,
					Boolean.FALSE) == Boolean.FALSE) {

			return;
		}

		_initializeSite(companyId, guestGroup.getGroupId());

		groupExpandoBridge.setAttribute(
			_RUN_CUSTOMER_PORTAL_COMPANY_SITE_INITIALIZER, Boolean.FALSE,
			Boolean.FALSE);
	}

	@Reference(
		target = "(group.initializer.key=" + CustomerPortalGroupInitializer.KEY + ")",
		unbind = "-"
	)
	protected void setGroupInitializer(GroupInitializer groupInitializer) {
		_groupInitializer = groupInitializer;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private void _initializeSite(long companyId, long groupId)
		throws PortalException {

		Role adminRole = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		List<User> adminUsers = _userLocalService.getRoleUsers(
			adminRole.getRoleId());

		User adminUser = adminUsers.get(0);

		Long threadCompanyId = CompanyThreadLocal.getCompanyId();
		String threadName = PrincipalThreadLocal.getName();
		PermissionChecker threadPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			_setPermissionChecker(companyId, adminUser);

			_groupInitializer.initialize(groupId);
		}
		catch (Exception e) {
			_log.error("Could not initialize company site", e);
		}
		finally {
			CompanyThreadLocal.setCompanyId(threadCompanyId);
			PrincipalThreadLocal.setName(threadName);
			PermissionThreadLocal.setPermissionChecker(threadPermissionChecker);
		}
	}

	private void _setPermissionChecker(long companyId, User user)
		throws Exception {

		CompanyThreadLocal.setCompanyId(companyId);

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	private static final String
		_CUSTOMER_PORTAL_COMPANY_SITE_INITIALIZER_ENABLED =
			"customer.portal.company.site.initializer.enabled";

	private static final String _RUN_CUSTOMER_PORTAL_COMPANY_SITE_INITIALIZER =
		"run-customer-portal-company-site-initializer";

	private static final boolean _SITE_INITIALIZER_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(_CUSTOMER_PORTAL_COMPANY_SITE_INITIALIZER_ENABLED));

	private static final Log _log = LogFactoryUtil.getLog(
		CustomerPortalCompanySiteInitializer.class);

	private GroupInitializer _groupInitializer;
	private GroupLocalService _groupLocalService;
	private RoleLocalService _roleLocalService;
	private UserLocalService _userLocalService;

}