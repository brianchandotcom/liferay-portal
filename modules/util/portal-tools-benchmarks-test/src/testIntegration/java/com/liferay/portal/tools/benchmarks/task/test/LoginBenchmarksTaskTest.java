/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmarks.task.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.benchmarks.task.LoginBenchmarksTask;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class LoginBenchmarksTaskTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_originalPrincipalName = PrincipalThreadLocal.getName();

		_originalVirtualHostsDefaultSiteName =
			PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME;

		PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME = "Guest";

		_originalVirtualHostsIgnoredHosts =
			ReflectionTestUtil.getAndSetFieldValue(
				PortalInstances.class, "_virtualHostsIgnoreHosts",
				Collections.singleton("localhost"));
	}

	@After
	public void tearDown() {
		PrincipalThreadLocal.setName(_originalPrincipalName);

		PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME =
			_originalVirtualHostsDefaultSiteName;

		ReflectionTestUtil.setFieldValue(
			PortalInstances.class, "_virtualHostsIgnoreHosts",
			_originalVirtualHostsIgnoredHosts);
	}

	@Test
	public void testExecute() throws Exception {
		Company company = CompanyTestUtil.addCompany(true);

		User user = UserTestUtil.getAdminUser(company.getCompanyId());

		try {
			PrincipalThreadLocal.setName(user.getUserId());

			user.setPassword(PasswordEncryptorUtil.encrypt("test"));
			user.setPasswordEncrypted(true);
			user.setPasswordReset(false);
			user.setReminderQueryQuestion("test");
			user.setReminderQueryAnswer("test");
			user.setAgreedToTermsOfUse(true);

			user = _userLocalService.updateUser(user);

			VirtualHost virtualHost = _virtualHostLocalService.getVirtualHost(
				company.getVirtualHostname());

			virtualHost.setHostname("127.0.0.1");

			virtualHost = _virtualHostLocalService.updateVirtualHost(
				virtualHost);

			company.setVirtualHostname(virtualHost.getHostname());

			company = _companyLocalService.updateCompany(company);

			LoginBenchmarksTask loginBenchmarksTask = new LoginBenchmarksTask(
				company.getVirtualHostname(), 8080, user.getEmailAddress(),
				"test");

			loginBenchmarksTask.execute();
		}
		finally {
			_companyLocalService.deleteCompany(company);
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private String _originalPrincipalName;
	private String _originalVirtualHostsDefaultSiteName;
	private Set<String> _originalVirtualHostsIgnoredHosts;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private VirtualHostLocalService _virtualHostLocalService;

}