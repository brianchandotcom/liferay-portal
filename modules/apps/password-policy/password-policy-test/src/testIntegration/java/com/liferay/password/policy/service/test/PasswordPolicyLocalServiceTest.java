/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.password.policy.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PasswordPolicyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Dictionary;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christopher Kian
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class PasswordPolicyLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@After
	public void tearDown() throws Exception {
		_ldapAuthConfigurationProvider.delete(TestPropsValues.getCompanyId());
	}

	@Test
	public void testGetDefaultPasswordPolicyWithLDAPPasswordPolicy()
		throws Exception {

		try (SafeCloseable safeCloseable =
				_updateLDAPAuthConfigurationWithSafeCloseable(true)) {

			Assert.assertNotNull(
				_passwordPolicyLocalService.getDefaultPasswordPolicy(
					TestPropsValues.getCompanyId()));
		}
	}

	@Test
	public void testGetPasswordPolicyByUserIdWithLDAPPasswordPolicy()
		throws Exception {

		try (SafeCloseable safeCloseable =
				_updateLDAPAuthConfigurationWithSafeCloseable(true)) {

			User user = _addUser(true);

			Assert.assertNull(
				_passwordPolicyLocalService.getPasswordPolicyByUserId(
					user.getUserId()));

			user = _addUser(false);

			Assert.assertNotNull(
				_passwordPolicyLocalService.getPasswordPolicyByUserId(
					user.getUserId()));
		}
	}

	@Test
	public void testGetPasswordPolicyByUserIdWithoutLDAPPasswordPolicy()
		throws Exception {

		try (SafeCloseable safeCloseable =
				_updateLDAPAuthConfigurationWithSafeCloseable(false)) {

			User user = _addUser(true);

			Assert.assertNotNull(
				_passwordPolicyLocalService.getPasswordPolicyByUserId(
					user.getUserId()));

			user = _addUser(false);

			Assert.assertNotNull(
				_passwordPolicyLocalService.getPasswordPolicyByUserId(
					user.getUserId()));
		}
	}

	@Test
	public void testGetPasswordPolicyByUserWithLDAPPasswordPolicy()
		throws Exception {

		try (SafeCloseable safeCloseable =
				_updateLDAPAuthConfigurationWithSafeCloseable(true)) {

			Assert.assertNull(
				_passwordPolicyLocalService.getPasswordPolicyByUser(
					_addUser(true)));
			Assert.assertNotNull(
				_passwordPolicyLocalService.getPasswordPolicyByUser(
					_addUser(false)));
		}
	}

	@Test
	public void testGetPasswordPolicyByUserWithoutLDAPPasswordPolicy()
		throws Exception {

		try (SafeCloseable safeCloseable =
				_updateLDAPAuthConfigurationWithSafeCloseable(false)) {

			Assert.assertNotNull(
				_passwordPolicyLocalService.getPasswordPolicyByUser(
					_addUser(true)));
			Assert.assertNotNull(
				_passwordPolicyLocalService.getPasswordPolicyByUser(
					_addUser(false)));
		}
	}

	@Test
	public void testGetPasswordPolicyWithLDAPPasswordPolicy1()
		throws Exception {

		try (SafeCloseable safeCloseable =
				_updateLDAPAuthConfigurationWithSafeCloseable(true)) {

			Assert.assertNotNull(
				_passwordPolicyLocalService.getDefaultPasswordPolicy(
					TestPropsValues.getCompanyId()));
		}
	}

	@Test
	public void testGetPasswordPolicyWithLDAPPasswordPolicy2()
		throws Exception {

		try (SafeCloseable safeCloseable =
				_updateLDAPAuthConfigurationWithSafeCloseable(true)) {

			Assert.assertNotNull(
				_passwordPolicyLocalService.getPasswordPolicy(
					TestPropsValues.getCompanyId(), new long[0]));
		}
	}

	private User _addUser(boolean ldapUser) throws Exception {
		User user = UserTestUtil.addUser();

		user.setLdapServerId(ldapUser ? 1 : -1);

		return _userLocalService.updateUser(user);
	}

	private SafeCloseable _updateLDAPAuthConfigurationWithSafeCloseable(
			boolean passwordPolicyEnabled)
		throws PortalException {

		long companyId = TestPropsValues.getCompanyId();

		Dictionary<String, Object> configurationProperties =
			_ldapAuthConfigurationProvider.getConfigurationProperties(
				companyId);

		Object originalPasswordPolicyEnabled = configurationProperties.put(
			"passwordPolicyEnabled", passwordPolicyEnabled);

		_ldapAuthConfigurationProvider.updateProperties(
			companyId, configurationProperties);

		return () -> {
			if (originalPasswordPolicyEnabled != null) {
				configurationProperties.put(
					"passwordPolicyEnabled", originalPasswordPolicyEnabled);
			}
			else {
				configurationProperties.remove("passwordPolicyEnabled");
			}

			_ldapAuthConfigurationProvider.updateProperties(
				companyId, configurationProperties);
		};
	}

	@Inject(
		filter = "factoryPid=com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration"
	)
	private ConfigurationProvider<LDAPAuthConfiguration>
		_ldapAuthConfigurationProvider;

	@Inject
	private PasswordPolicyLocalService _passwordPolicyLocalService;

	@Inject
	private UserLocalService _userLocalService;

}