/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.playwright.setup.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = {})
public class PlaywrightSetup {

	@Activate
	protected void activate() {
		try {
			Organization organization = _addTestOrganization();

			_addDefaultCompanyAdminUser();

			_addTestOrganizationOwnerUser(organization);

			_addUnprivilegedUser();
		}
		catch (PortalException portalException) {
			_log.error("Unable to setup Playwright objects", portalException);
		}
	}

	private User _addDefaultCompanyAdminUser() throws PortalException {
		long companyId = _portal.getDefaultCompanyId();
		String screenName = "default-company-admin";

		User user = _userLocalService.fetchUserByScreenName(
			companyId, screenName);

		if (user != null) {
			return user;
		}

		Group group = _groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		user = _addUser(companyId, screenName, new long[] {group.getGroupId()});

		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		_userLocalService.addRoleUser(role.getRoleId(), user);

		return user;
	}

	private Organization _addTestOrganization() throws PortalException {
		long companyId = _portal.getDefaultCompanyId();
		String name = "test-organization";

		Organization organization = _organizationLocalService.fetchOrganization(
			companyId, name);

		if (organization != null) {
			return organization;
		}

		User user = _getAdminUser(companyId);

		return _organizationLocalService.addOrganization(
			user.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID, name, false);
	}

	private User _addTestOrganizationOwnerUser(Organization organization)
		throws PortalException {

		long companyId = organization.getCompanyId();
		String screenName = "test-organization-owner";

		User user = _userLocalService.fetchUserByScreenName(
			companyId, screenName);

		if (user != null) {
			return user;
		}

		user = _addUser(
			companyId, screenName, new long[] {organization.getGroupId()});

		_userLocalService.addOrganizationUser(
			organization.getOrganizationId(), user.getUserId());

		Role role = _roleLocalService.getRole(
			organization.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		_userGroupRoleLocalService.addUserGroupRoles(
			new long[] {user.getUserId()}, organization.getGroupId(),
			role.getRoleId());

		return user;
	}

	private User _addUnprivilegedUser() throws PortalException {
		long companyId = _portal.getDefaultCompanyId();
		String screenName = "unprivileged";

		User user = _userLocalService.fetchUserByScreenName(
			companyId, screenName);

		if (user != null) {
			return user;
		}

		return _addUser(companyId, screenName, null);
	}

	private User _addUser(long companyId, String screenName, long[] groupIds)
		throws PortalException {

		User adminUser = _getAdminUser(companyId);

		Company company = _companyLocalService.getCompanyById(companyId);

		boolean autoPassword = false;
		String password1 = "test";
		String password2 = "test";
		String emailAddress = screenName + "@liferay.com";
		String firstName = screenName;
		String middleName = StringPool.BLANK;
		String lastName = screenName;
		long prefixListTypeId = 0;
		long suffixListTypeId = 0;
		boolean male = false;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;
		ServiceContext serviceContext = null;

		User user = _userLocalService.addUser(
			adminUser.getUserId(), companyId, autoPassword, password1,
			password2, Validator.isNull(screenName), screenName, emailAddress,
			company.getLocale(), firstName, middleName, lastName,
			prefixListTypeId, suffixListTypeId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, UserConstants.TYPE_REGULAR,
			groupIds, organizationIds, roleIds, userGroupIds, sendMail,
			serviceContext);

		user.setEmailAddressVerified(true);

		return _userLocalService.updateUser(user);
	}

	private User _getAdminUser(long companyId) throws PortalException {
		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		List<User> users = _userLocalService.getRoleUsers(
			role.getRoleId(), 0, 1);

		if (!users.isEmpty()) {
			return users.get(0);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PlaywrightSetup.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTLETS_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}