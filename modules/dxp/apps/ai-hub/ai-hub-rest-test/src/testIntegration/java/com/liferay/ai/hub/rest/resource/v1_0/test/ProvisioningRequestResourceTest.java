/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.ai.hub.rest.client.dto.v1_0.ProvisioningRequest;
import com.liferay.ai.hub.rest.client.dto.v1_0.UserAccount;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.util.Arrays;
import java.util.Collections;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class ProvisioningRequestResourceTest
	extends BaseProvisioningRequestResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		_group = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, "AI Hub",
			ServiceContextTestUtil.getServiceContext());

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.ai.hub.site.initializer");

		siteInitializer.initialize(_group.getGroupId());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
		PrincipalThreadLocal.setName(_originalName);
		ServiceContextThreadLocal.popServiceContext();
	}

	@Override
	@Test
	public void testPostProvisioning() throws Exception {
		UserAccount[] userAccounts = {
			new UserAccount() {
				{
					emailAddress =
						StringUtil.toLowerCase(RandomTestUtil.randomString()) +
							"@liferay.com";
					firstName = RandomTestUtil.randomString();
					lastName = RandomTestUtil.randomString();
					screenName = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
				}
			},
			new UserAccount() {
				{
					emailAddress = "test@liferay.com";
				}
			}
		};

		ProvisioningRequest provisioningRequest = randomProvisioningRequest(
			userAccounts);

		ProvisioningRequest postProvisioningRequest =
			provisioningRequestResource.postProvisioning(provisioningRequest);

		assertEquals(provisioningRequest, postProvisioningRequest);

		AccountEntry customerAccountEntry =
			_accountEntryLocalService.getAccountEntry(
				postProvisioningRequest.getAccountEntryId());

		_assertAccountEntry(customerAccountEntry, provisioningRequest);

		AccountEntry aiHubAccountEntry =
			_accountEntryLocalService.getAccountEntryByExternalReferenceCode(
				"L_AI_HUB", TestPropsValues.getCompanyId());

		_assertServiceAccountUsers(aiHubAccountEntry, customerAccountEntry);

		_assertOAuth2Application(customerAccountEntry, provisioningRequest);

		_assertUserAccounts(
			aiHubAccountEntry, customerAccountEntry,
			provisioningRequest.getUserAccounts(),
			postProvisioningRequest.getUserAccounts());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"accountEntryExternalReferenceCode", "accountEntryName",
			"liferayDXPURL"
		};
	}

	protected ProvisioningRequest randomProvisioningRequest(
			UserAccount[] userAccounts)
		throws Exception {

		ProvisioningRequest provisioningRequest =
			super.randomProvisioningRequest();

		provisioningRequest.setLiferayDXPURL(
			"http://localhost:" + PortalUtil.getPortalServerPort(false));
		provisioningRequest.setUserAccounts(userAccounts);

		return provisioningRequest;
	}

	private void _assertAccountEntry(
		AccountEntry accountEntry, ProvisioningRequest provisioningRequest) {

		Assert.assertEquals(
			provisioningRequest.getAccountEntryExternalReferenceCode(),
			accountEntry.getExternalReferenceCode());
		Assert.assertEquals(
			provisioningRequest.getAccountEntryName(), accountEntry.getName());
	}

	private void _assertOAuth2Application(
			AccountEntry accountEntry, ProvisioningRequest provisioningRequest)
		throws Exception {

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					accountEntry.getAccountEntryId() +
						"-ai-hub-oauth2-application",
					TestPropsValues.getCompanyId());

		Assert.assertEquals(
			Collections.singletonList(GrantType.CLIENT_CREDENTIALS),
			oAuth2Application.getAllowedGrantTypesList());
		Assert.assertEquals(
			provisioningRequest.getLiferayDXPURL(),
			oAuth2Application.getHomePageURL());
		Assert.assertEquals(
			provisioningRequest.getAccountEntryName(),
			oAuth2Application.getName());
	}

	private void _assertServiceAccountUser(
			AccountEntry aiHubAccountEntry, AccountEntry customerAccountEntry,
			String screenName)
		throws Exception {

		User user = _userLocalService.getUserByScreenName(
			TestPropsValues.getCompanyId(), screenName);

		Assert.assertEquals(UserConstants.TYPE_SERVICE_ACCOUNT, user.getType());

		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				aiHubAccountEntry.getAccountEntryId(), user.getUserId()));
		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				customerAccountEntry.getAccountEntryId(), user.getUserId()));
	}

	private void _assertServiceAccountUsers(
			AccountEntry aiHubAccountEntry, AccountEntry customerAccountEntry)
		throws Exception {

		_assertServiceAccountUser(
			aiHubAccountEntry, customerAccountEntry,
			customerAccountEntry.getAccountEntryId() + "-service-account");
		_assertServiceAccountUser(
			aiHubAccountEntry, customerAccountEntry,
			customerAccountEntry.getAccountEntryId() +
				"-guest-service-account");
	}

	private void _assertUserAccounts(
			AccountEntry aiHubAccountEntry, AccountEntry customerAccountEntry,
			UserAccount[] expectedUserAccounts,
			UserAccount[] actualUserAccounts)
		throws Exception {

		Assert.assertEquals(
			Arrays.toString(actualUserAccounts), expectedUserAccounts.length,
			actualUserAccounts.length);

		Role role = _roleLocalService.getRole(
			aiHubAccountEntry.getCompanyId(), "AI Hub Agent Manager");

		AccountRole accountRole =
			_accountRoleLocalService.getAccountRoleByRoleId(role.getRoleId());

		for (int i = 0; i < actualUserAccounts.length; i++) {
			UserAccount actualUserAccount = actualUserAccounts[i];
			UserAccount expectedUserAccount = expectedUserAccounts[i];

			Assert.assertEquals(
				expectedUserAccount.getEmailAddress(),
				actualUserAccount.getEmailAddress());

			if (Validator.isNull(expectedUserAccount.getScreenName())) {
				Assert.assertNotNull(actualUserAccount.getFirstName());
				Assert.assertNotNull(actualUserAccount.getLastName());
				Assert.assertNotNull(actualUserAccount.getScreenName());
			}
			else {
				Assert.assertEquals(
					expectedUserAccount.getFirstName(),
					actualUserAccount.getFirstName());
				Assert.assertEquals(
					expectedUserAccount.getLastName(),
					actualUserAccount.getLastName());
				Assert.assertEquals(
					expectedUserAccount.getScreenName(),
					actualUserAccount.getScreenName());
			}

			User user = _userLocalService.getUserByEmailAddress(
				TestPropsValues.getCompanyId(),
				actualUserAccount.getEmailAddress());

			Assert.assertEquals(UserConstants.TYPE_REGULAR, user.getType());

			Assert.assertTrue(
				_accountEntryUserRelLocalService.hasAccountEntryUserRel(
					aiHubAccountEntry.getAccountEntryId(), user.getUserId()));
			Assert.assertTrue(
				_accountEntryUserRelLocalService.hasAccountEntryUserRel(
					customerAccountEntry.getAccountEntryId(),
					user.getUserId()));

			Assert.assertTrue(
				_accountRoleLocalService.hasUserAccountRole(
					customerAccountEntry.getAccountEntryId(),
					accountRole.getAccountRoleId(), user.getUserId()));
			Assert.assertTrue(
				_accountRoleLocalService.hasUserAccountRole(
					customerAccountEntry.getAccountEntryId(),
					accountRole.getAccountRoleId(), user.getUserId()));

			Assert.assertTrue(
				_userLocalService.hasGroupUser(
					_group.getGroupId(), user.getUserId()));
		}
	}

	private static Group _group;
	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}