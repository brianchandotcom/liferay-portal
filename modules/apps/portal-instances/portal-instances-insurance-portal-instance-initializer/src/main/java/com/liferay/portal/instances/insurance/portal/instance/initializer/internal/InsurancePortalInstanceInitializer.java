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

package com.liferay.portal.instances.insurance.portal.instance.initializer.internal;

import com.liferay.portal.instances.exception.InitializationException;
import com.liferay.portal.instances.initializer.PortalInstanceInitializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.site.initializer.SiteInitializer;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "portal.instance.initializer.key=" + InsurancePortalInstanceInitializer.KEY,
	service = PortalInstanceInitializer.class
)
public class InsurancePortalInstanceInitializer
	implements PortalInstanceInitializer {

	public static final String KEY =
		"portal-instances-insurance-portal-instance-initializer";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return "Raylife";
	}

	@Override
	public void initialize(
			long companyId, HttpServletRequest httpServletRequest,
			Map<String, String> payload)
		throws InitializationException {

		try {
			_initializeDefaultSite(companyId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new InitializationException(exception);
		}
	}

	@Override
	public void initialize(String webId, String virtualHostname, String mx)
		throws InitializationException {

		try {
			Company company = _companyLocalService.getCompanyByWebId("webId");

			_initializeDefaultSite(company.getCompanyId());
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new InitializationException(exception);
		}
	}

	@Override
	public boolean isActive() {
		return true;
	}

	private void _initializeDefaultSite(long companyId) throws Exception {
		Group group = _groupLocalService.getGroup(companyId, "Guest");

		_setPermissionChecker(group);

		_siteInsuranceSiteInitializer.initialize(group.getGroupId());
	}

	private void _setPermissionChecker(Group group) throws Exception {
		Company company = _companyLocalService.getCompanyById(
			group.getCompanyId());

		Role role = _roleLocalService.fetchRole(
			company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		List<User> roleUsers = _userLocalService.getRoleUsers(role.getRoleId());

		User user = roleUsers.get(0);

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InsurancePortalInstanceInitializer.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference(
		target = "(site.initializer.key=site-insurance-site-initializer)"
	)
	private SiteInitializer _siteInsuranceSiteInitializer;

	@Reference
	private UserLocalService _userLocalService;

}