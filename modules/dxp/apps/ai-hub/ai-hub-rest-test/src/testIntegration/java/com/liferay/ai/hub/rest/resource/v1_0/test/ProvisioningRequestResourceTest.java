/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.ai.hub.rest.client.dto.v1_0.ProvisioningRequest;
import com.liferay.ai.hub.rest.client.dto.v1_0.UserAccount;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;

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

		_group = GroupLocalServiceUtil.addGroup(
			StringPool.BLANK, TestPropsValues.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			HashMapBuilder.put(
				LocaleUtil.getDefault(), "AI Hub"
			).build(),
			null, GroupConstants.TYPE_SITE_OPEN, null, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize("/ai-hub"),
			true, false, true, ServiceContextTestUtil.getServiceContext());

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.ai.hub.site.initializer");

		siteInitializer.initialize(TestPropsValues.getGroupId());
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
		ProvisioningRequest provisioningRequest = randomProvisioningRequest();

		UserAccount userAccount = new UserAccount() {
			{
				emailAddress =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				firstName = RandomTestUtil.randomString();
				lastName = RandomTestUtil.randomString();
				screenName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};

		UserAccount[] userAccounts = {userAccount};

		provisioningRequest.setUserAccounts(userAccounts);

		String liferayDXPURL =
			"http://localhost:" + PortalUtil.getPortalServerPort(false);

		provisioningRequest.setLiferayDXPURL(liferayDXPURL);

		String accountName = provisioningRequest.getAccountEntryName();

		ProvisioningRequest postProvisioningRequest =
			provisioningRequestResource.postProvisioning(provisioningRequest);

		provisioningRequest.setAccountEntryExternalReferenceCode(
			postProvisioningRequest.getAccountEntryExternalReferenceCode());

		AccountEntry accountEntry =
			_accountEntryLocalService.getAccountEntryByExternalReferenceCode(
				postProvisioningRequest.getAccountEntryExternalReferenceCode(),
				TestPropsValues.getCompanyId());

		provisioningRequest.setAccountEntryId(accountEntry.getAccountEntryId());

		assertEquals(provisioningRequest, postProvisioningRequest);

		AccountEntry aiHubAccountEntry =
			_accountEntryLocalService.getAccountEntryByExternalReferenceCode(
				"L_AI_HUB", TestPropsValues.getCompanyId());

		_assertServiceAccountUser(
			aiHubAccountEntry, accountEntry,
			accountEntry.getAccountEntryId() + "-service-account");
		_assertServiceAccountUser(
			aiHubAccountEntry, accountEntry,
			accountEntry.getAccountEntryId() + "-guest-service-account");

		User user = _userLocalService.getUserByEmailAddress(
			TestPropsValues.getCompanyId(), userAccount.getEmailAddress());

		Assert.assertEquals(UserConstants.TYPE_REGULAR, user.getType());

		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				aiHubAccountEntry.getAccountEntryId(), user.getUserId()));
		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				accountEntry.getAccountEntryId(), user.getUserId()));

		Assert.assertTrue(
			_userLocalService.hasGroupUser(
				_group.getGroupId(), user.getUserId()));

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_AI_HUB_QUOTA", TestPropsValues.getCompanyId());

		long accountEntryId = accountEntry.getAccountEntryId();

		_assertQuotaObjectEntry(
			objectDefinition, "guest-quota-" + accountEntryId);
		_assertQuotaObjectEntry(objectDefinition, "quota-" + accountEntryId);

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					"ai-hub-oauth2-" + accountEntry.getAccountEntryId(),
					TestPropsValues.getCompanyId());

		Assert.assertNotNull(oAuth2Application);

		Assert.assertEquals(accountName, oAuth2Application.getName());
		Assert.assertEquals(
			Collections.singletonList(GrantType.CLIENT_CREDENTIALS),
			oAuth2Application.getAllowedGrantTypesList());
		Assert.assertEquals(
			provisioningRequest.getLiferayDXPURL(),
			oAuth2Application.getHomePageURL());
		Assert.assertEquals(
			Collections.singletonList(provisioningRequest.getLiferayDXPURL()),
			oAuth2Application.getRedirectURIsList());
	}

	private void _assertQuotaObjectEntry(
		ObjectDefinition objectDefinition, String externalReferenceCode) {

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			externalReferenceCode, 0, objectDefinition.getObjectDefinitionId());

		Assert.assertNotNull(externalReferenceCode, objectEntry);

		Map<String, Serializable> values = objectEntry.getValues();

		Assert.assertEquals(
			33333333, GetterUtil.getInteger(values.get("limit")));
		Assert.assertEquals(0, GetterUtil.getInteger(values.get("usage")));
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
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private UserLocalService _userLocalService;

}