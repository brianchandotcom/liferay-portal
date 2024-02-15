/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.demo.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.roles.admin.demo.data.creator.RoleDemoDataCreator;
import com.liferay.site.demo.data.creator.SiteDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.BasicUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.CompanyAdminUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.SiteAdminUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.SiteMemberUserDemoDataCreator;

import java.util.Calendar;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class UsersDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (company.getCompanyId() == _portal.getDefaultCompanyId()) {
			_populateTestData(company);
		}

		_populateDemoData(company);
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		_basicUserDemoDataCreator.delete();
		_companyAdminUserDemoDataCreator.delete();
		_siteAdminUserDemoDataCreator.delete();
		_siteMemberUserDemoDataCreator.delete();

		_siteDemoDataCreator.delete();
		_siteRoleDemoDataCreator.delete();
	}

	private User _addTestCompanyAdminUser(Company company) throws Exception {
		long companyId = company.getCompanyId();
		String screenName = "test-company-admin";

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

	private Organization _addTestOrganization(Company company)
		throws Exception {

		long companyId = company.getCompanyId();
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
		throws Exception {

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

	private User _addTestUnprivilegedUser(Company company) throws Exception {
		long companyId = company.getCompanyId();
		String screenName = "test-unprivileged";

		User user = _userLocalService.fetchUserByScreenName(
			companyId, screenName);

		if (user != null) {
			return user;
		}

		return _addUser(companyId, screenName, null);
	}

	private User _addUser(long companyId, String screenName, long[] groupIds)
		throws Exception {

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

	private User _getAdminUser(long companyId) throws Exception {
		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		List<User> users = _userLocalService.getRoleUsers(
			role.getRoleId(), 0, 1);

		if (!users.isEmpty()) {
			return users.get(0);
		}

		return null;
	}

	private void _populateDemoData(Company company) throws Exception {
		_basicUserDemoDataCreator.create(
			company.getCompanyId(), "usersn", "userea@liferay.com", "userfn",
			"userln");

		_companyAdminUserDemoDataCreator.create(
			company.getCompanyId(), "bruno.admin@liferay.com");

		Group acmeCorpGroup = _siteDemoDataCreator.create(
			company.getCompanyId(), "Acme’s Corporation");

		_siteAdminUserDemoDataCreator.create(
			acmeCorpGroup.getGroupId(), "helen@liferay.com");

		// Web Content Author role

		String webContentAuthorPermissionsXML = StringUtil.read(
			UsersDemo.class, "dependencies/permissions-web-content-author.xml");

		Role webContentAuthorRole = _siteRoleDemoDataCreator.create(
			company.getCompanyId(), "Web Content Author",
			webContentAuthorPermissionsXML);

		_siteMemberUserDemoDataCreator.create(
			acmeCorpGroup.getGroupId(), "joe@liferay.com",
			new long[] {webContentAuthorRole.getRoleId()});

		// Forum Moderator role

		Group petLoversGroup = _siteDemoDataCreator.create(
			company.getCompanyId(), "Pet Lovers");

		String forumModeratorPermissionsXML = StringUtil.read(
			UsersDemo.class, "dependencies/permissions-forum-moderator.xml");

		Role forumModeratorRole = _siteRoleDemoDataCreator.create(
			company.getCompanyId(), "Forum Moderator",
			forumModeratorPermissionsXML);

		_siteMemberUserDemoDataCreator.create(
			petLoversGroup.getGroupId(), "maria@liferay.com",
			new long[] {forumModeratorRole.getRoleId()});

		// Portal Content Reviewer role

		Role portalContentReviewerRole = _roleLocalService.getRole(
			company.getCompanyId(), RoleConstants.PORTAL_CONTENT_REVIEWER);

		User portalContentReviewerUser = _basicUserDemoDataCreator.create(
			company.getCompanyId(), "reviewersn", "reviewerea@liferay.com",
			"reviewerfn", "reviewerln");

		_roleLocalService.addUserRole(
			portalContentReviewerUser.getUserId(), portalContentReviewerRole);
	}

	private void _populateTestData(Company company) throws Exception {
		Organization organization = _addTestOrganization(company);

		_addTestCompanyAdminUser(company);

		_addTestOrganizationOwnerUser(organization);

		_addTestUnprivilegedUser(company);
	}

	@Reference
	private BasicUserDemoDataCreator _basicUserDemoDataCreator;

	@Reference
	private CompanyAdminUserDemoDataCreator _companyAdminUserDemoDataCreator;

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
	private SiteAdminUserDemoDataCreator _siteAdminUserDemoDataCreator;

	@Reference
	private SiteDemoDataCreator _siteDemoDataCreator;

	@Reference
	private SiteMemberUserDemoDataCreator _siteMemberUserDemoDataCreator;

	@Reference(target = "(role.type=site)")
	private RoleDemoDataCreator _siteRoleDemoDataCreator;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}