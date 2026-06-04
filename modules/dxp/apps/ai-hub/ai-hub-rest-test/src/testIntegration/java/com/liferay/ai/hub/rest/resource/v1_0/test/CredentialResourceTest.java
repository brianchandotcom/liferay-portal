/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.ai.hub.rest.client.dto.v1_0.Credential;
import com.liferay.ai.hub.rest.client.http.HttpInvoker;
import com.liferay.ai.hub.rest.client.resource.v1_0.CredentialResource;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.util.Collections;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class CredentialResourceTest extends BaseCredentialResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_accountEntry = _accountEntryLocalService.addAccountEntry(
			"ai-hub-customer-" + RandomTestUtil.randomString(),
			TestPropsValues.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null, null, null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.ai.hub.site.initializer");

		siteInitializer.initialize(TestPropsValues.getGroupId());

		_aiHubAccountEntry =
			_accountEntryLocalService.getAccountEntryByExternalReferenceCode(
				"L_AI_HUB", TestPropsValues.getCompanyId());

		_oAuth2Application =
			_oAuth2ApplicationLocalService.addOrUpdateOAuth2Application(
				_accountEntry.getAccountEntryId() +
					"-ai-hub-oauth2-application",
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				Collections.singletonList(GrantType.CLIENT_CREDENTIALS),
				"client_secret_post", TestPropsValues.getUserId(),
				"id-" + RandomTestUtil.randomString(),
				ClientProfile.HEADLESS_SERVER.id(),
				"secret-" + RandomTestUtil.randomString(), null,
				Collections.<String>emptyList(),
				"http://localhost:" + PortalUtil.getPortalServerPort(false), 0,
				null, RandomTestUtil.randomString(), null,
				Collections.<String>emptyList(), false,
				Collections.<String>emptyList(), false, serviceContext);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_oAuth2ApplicationLocalService.deleteOAuth2Application(
			_oAuth2Application);

		_accountEntryLocalService.deleteAccountEntry(
			_accountEntry.getAccountEntryId());

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);

		ServiceContextThreadLocal.popServiceContext();
	}

	@Override
	@Test
	public void testGetCredential() throws Exception {
		_testGetCredentialWithAIHubAgentManagerRole();
		_testGetCredentialWithoutAIHubAgentManagerRole();
	}

	private User _addUser() throws Exception {
		return UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			PropsValues.DEFAULT_ADMIN_PASSWORD,
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
	}

	private CredentialResource _createCredentialResource(User user) {
		return CredentialResource.builder(
		).authentication(
			user.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	private void _testGetCredentialWithAIHubAgentManagerRole()
		throws Exception {

		User user = _addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), user.getUserId());
		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_aiHubAccountEntry.getAccountEntryId(), user.getUserId());

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), "AI Hub Agent Manager");

		AccountRole accountRole =
			_accountRoleLocalService.getAccountRoleByRoleId(role.getRoleId());

		_accountRoleLocalService.associateUser(
			_accountEntry.getAccountEntryId(), accountRole.getAccountRoleId(),
			user.getUserId());

		CredentialResource credentialResource = _createCredentialResource(user);

		Credential credential = credentialResource.getCredential();

		Assert.assertEquals(
			_oAuth2Application.getClientId(), credential.getClientId());
		Assert.assertEquals(
			_oAuth2Application.getClientSecret(), credential.getClientSecret());

		_userLocalService.deleteUser(user.getUserId());
	}

	private void _testGetCredentialWithoutAIHubAgentManagerRole()
		throws Exception {

		User user = _addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), user.getUserId());
		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_aiHubAccountEntry.getAccountEntryId(), user.getUserId());

		CredentialResource credentialResource = _createCredentialResource(user);

		HttpInvoker.HttpResponse httpResponse =
			credentialResource.getCredentialHttpResponse();

		Assert.assertEquals(404, httpResponse.getStatusCode());

		_userLocalService.deleteUser(user.getUserId());
	}

	private static AccountEntry _accountEntry;

	@Inject
	private static AccountEntryLocalService _accountEntryLocalService;

	private static AccountEntry _aiHubAccountEntry;
	private static OAuth2Application _oAuth2Application;

	@Inject
	private static OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}