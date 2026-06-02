/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.object.action.executor.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.io.Serializable;

import java.util.Dictionary;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Pedro Leite
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class SyncEnvironmentUrlsObjectActionExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		_configuration = _configurationAdmin.createFactoryConfiguration(
			_PORTAL_CORS_CONFIGURATION_PID + ".scoped", "?");

		_configuration.update(
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"filter.mapping.url.pattern", new String[] {"/o/ai-hub/*"}
			).put(
				"headers",
				new String[] {
					"Access-Control-Allow-Origin: http://" +
						RandomTestUtil.randomString()
				}
			).build());
	}

	@After
	public void tearDown() throws Exception {
		_configuration.delete();

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testExecute() throws Exception {
		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.ai.hub.site.initializer");

		siteInitializer.initialize(_group.getGroupId());

		AccountEntry accountEntry1 = _addAccountEntry();

		_addOAuth2Application(accountEntry1);

		ObjectEntry objectEntry1 = _updateObjectEntry(
			accountEntry1, _addObjectEntry(accountEntry1));

		_assertHeader(
			"Access-Control-Allow-Origin: " +
				MapUtil.getString(objectEntry1.getValues(), "environmentUrls"));
		_assertHomePageURL(
			accountEntry1,
			MapUtil.getString(objectEntry1.getValues(), "environmentUrls"));

		AccountEntry accountEntry2 = _addAccountEntry();

		_addOAuth2Application(accountEntry2);

		ObjectEntry objectEntry2 = _updateObjectEntry(
			accountEntry2, _addObjectEntry(accountEntry2));

		_assertHeader(
			StringBundler.concat(
				"Access-Control-Allow-Origin: ",
				MapUtil.getString(objectEntry1.getValues(), "environmentUrls"),
				StringPool.SPACE,
				MapUtil.getString(
					objectEntry2.getValues(), "environmentUrls")));
		_assertHomePageURL(
			accountEntry2,
			MapUtil.getString(objectEntry2.getValues(), "environmentUrls"));
	}

	private AccountEntry _addAccountEntry() throws Exception {
		return _accountEntryLocalService.addAccountEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _addOAuth2Application(AccountEntry accountEntry)
		throws Exception {

		User user = TestPropsValues.getUser();

		_oAuth2ApplicationLocalService.addOrUpdateOAuth2Application(
			accountEntry.getAccountEntryId() + "-ai-hub-oauth2-application",
			user.getUserId(), user.getFullName(),
			List.of(GrantType.CLIENT_CREDENTIALS), "client_secret_post",
			user.getUserId(), OAuth2SecureRandomGenerator.generateClientId(),
			ClientProfile.HEADLESS_SERVER.id(),
			OAuth2SecureRandomGenerator.generateClientSecret(), null, List.of(),
			null, 0, null, accountEntry.getName(), null, null, false, List.of(),
			false, ServiceContextTestUtil.getServiceContext());
	}

	private ObjectEntry _addObjectEntry(AccountEntry accountEntry)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_AI_HUB_CONFIGURATION", TestPropsValues.getCompanyId());

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			HashMapBuilder.<String, Serializable>put(
				"r_accountToAIHubConfigurations_accountEntryId",
				accountEntry.getAccountEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	private void _assertHeader(String expectedHeader) throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(service.factoryPid=%s.scoped)(companyId=%s))",
				_PORTAL_CORS_CONFIGURATION_PID,
				TestPropsValues.getCompanyId()));

		Configuration configuration = configurations[0];

		Dictionary<String, Object> properties = configuration.getProperties();

		String[] headers = GetterUtil.getStringValues(
			properties.get("headers"));

		Assert.assertEquals(expectedHeader, headers[0]);
	}

	private void _assertHomePageURL(
			AccountEntry accountEntry, String environmentUrls)
		throws Exception {

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					accountEntry.getAccountEntryId() +
						"-ai-hub-oauth2-application",
					TestPropsValues.getCompanyId());

		Assert.assertEquals(
			StringUtil.extractFirst(environmentUrls, CharPool.SPACE),
			oAuth2Application.getHomePageURL());
	}

	private ObjectEntry _updateObjectEntry(
			AccountEntry accountEntry, ObjectEntry objectEntry)
		throws Exception {

		return _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			objectEntry.getObjectEntryFolderId(),
			HashMapBuilder.<String, Serializable>put(
				"environmentUrls",
				StringBundler.concat(
					"http://", RandomTestUtil.randomString(), " http://",
					RandomTestUtil.randomString())
			).put(
				"r_accountToAIHubConfigurations_accountEntryId",
				accountEntry.getAccountEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	private static final String _PORTAL_CORS_CONFIGURATION_PID =
		"com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration";

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private Configuration _configuration;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private Group _group;

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

}