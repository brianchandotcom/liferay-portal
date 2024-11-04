/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.upgrade.v0_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.scim.rest.client.dto.v1_0.Group;
import com.liferay.scim.rest.client.dto.v1_0.MultiValuedAttribute;
import com.liferay.scim.rest.client.dto.v1_0.Name;
import com.liferay.scim.rest.client.dto.v1_0.UserSchemaExtension;
import com.liferay.scim.rest.client.http.HttpInvoker;
import com.liferay.scim.rest.client.resource.v1_0.GroupResource;
import com.liferay.scim.rest.client.resource.v1_0.UserResource;
import com.liferay.scim.rest.resource.v1_0.test.BaseUserResourceTestCase;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christian Moura
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@FeatureFlags("LPS-96845")
@RunWith(Arquillian.class)
public class UserManagerUpdateExpandoColumnUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseUserResourceTestCase.setUpClass();

		_pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.scim.rest.internal.configuration." +
				"ScimClientOAuth2ApplicationConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"matcherField", "email"
			).put(
				"oAuth2ApplicationName", "scim-client-test"
			).put(
				"userId", TestPropsValues.getUserId()
			).build());

		GroupResource.Builder groupBuilder = GroupResource.builder();
		UserResource.Builder userBuilder = UserResource.builder();

		String languageId = UpgradeProcessUtil.getDefaultLanguageId(
			TestPropsValues.getCompanyId());

		_groupResource = groupBuilder.authentication(
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).locale(
			LocaleUtil.fromLanguageId(languageId)
		).build();
		_userResource = userBuilder.authentication(
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).locale(
			LocaleUtil.fromLanguageId(languageId)
		).build();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_pid);
	}

	@Test
	public void testUpgradeExpandoFields() throws Exception {
		Group group = new Group() {
			{
				displayName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalId = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};

		HttpInvoker.HttpResponse groupHttpResponse =
			_groupResource.postV2GroupHttpResponse(group);

		Assert.assertEquals(2, groupHttpResponse.getStatusCode() / 100);

		com.liferay.scim.rest.client.dto.v1_0.User user = _randomUser();

		HttpInvoker.HttpResponse userHttpResponse =
			_userResource.postV2UserHttpResponse(user);

		Assert.assertEquals(2, userHttpResponse.getStatusCode() / 100);

		ExpandoTable userExpandoTable = _expandoTableLocalService.fetchTable(
			TestPropsValues.getCompanyId(),
			_classNameLocalService.getClassNameId(User.class.getName()),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		ExpandoColumn userExpandoColumn =
			_expandoColumnLocalService.fetchColumn(
				userExpandoTable.getTableId(), "scimClientId");

		UnicodeProperties userUnicodeProperties =
			userExpandoColumn.getTypeSettingsProperties();

		Assert.assertTrue(
			Boolean.parseBoolean(
				userUnicodeProperties.getProperty(
					ExpandoColumnConstants.PROPERTY_HIDDEN)));

		userUnicodeProperties.setProperty(
			ExpandoColumnConstants.PROPERTY_HIDDEN,
			String.valueOf(Boolean.FALSE));

		userExpandoColumn.setTypeSettingsProperties(userUnicodeProperties);

		userExpandoColumn = _expandoColumnLocalService.updateExpandoColumn(
			userExpandoColumn);

		userUnicodeProperties = userExpandoColumn.getTypeSettingsProperties();

		Assert.assertFalse(
			Boolean.parseBoolean(
				userUnicodeProperties.getProperty(
					ExpandoColumnConstants.PROPERTY_HIDDEN)));

		ExpandoTable userGroupExpandoTable =
			_expandoTableLocalService.fetchTable(
				TestPropsValues.getCompanyId(),
				_classNameLocalService.getClassNameId(
					UserGroup.class.getName()),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);

		ExpandoColumn userGroupExpandoColumn =
			_expandoColumnLocalService.fetchColumn(
				userGroupExpandoTable.getTableId(), "scimClientId");

		UnicodeProperties userGroupUnicodeProperties =
			userGroupExpandoColumn.getTypeSettingsProperties();

		Assert.assertTrue(
			Boolean.parseBoolean(
				userGroupUnicodeProperties.getProperty(
					ExpandoColumnConstants.PROPERTY_HIDDEN)));

		userGroupUnicodeProperties.setProperty(
			ExpandoColumnConstants.PROPERTY_HIDDEN,
			String.valueOf(Boolean.FALSE));

		userGroupExpandoColumn.setTypeSettingsProperties(
			userGroupUnicodeProperties);

		userGroupExpandoColumn = _expandoColumnLocalService.updateExpandoColumn(
			userGroupExpandoColumn);

		userGroupUnicodeProperties =
			userGroupExpandoColumn.getTypeSettingsProperties();

		Assert.assertFalse(
			Boolean.parseBoolean(
				userGroupUnicodeProperties.getProperty(
					ExpandoColumnConstants.PROPERTY_HIDDEN)));

		_runUpgrade();

		userExpandoColumn = _expandoColumnLocalService.fetchColumn(
			userExpandoTable.getTableId(), "scimClientId");

		userUnicodeProperties = userExpandoColumn.getTypeSettingsProperties();

		userGroupExpandoColumn = _expandoColumnLocalService.fetchColumn(
			userGroupExpandoTable.getTableId(), "scimClientId");

		userGroupUnicodeProperties =
			userGroupExpandoColumn.getTypeSettingsProperties();

		Assert.assertTrue(
			Boolean.parseBoolean(
				userUnicodeProperties.getProperty(
					ExpandoColumnConstants.PROPERTY_HIDDEN)));

		Assert.assertTrue(
			Boolean.parseBoolean(
				userGroupUnicodeProperties.getProperty(
					ExpandoColumnConstants.PROPERTY_HIDDEN)));
	}

	private com.liferay.scim.rest.client.dto.v1_0.User _randomUser()
		throws Exception {

		com.liferay.scim.rest.client.dto.v1_0.User user =
			new com.liferay.scim.rest.client.dto.v1_0.User() {
				{
					active = RandomTestUtil.randomBoolean();
					displayName = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					externalId = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					id = StringUtil.toLowerCase(RandomTestUtil.randomString());
					locale = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					nickName = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					password = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					preferredLanguage = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					profileUrl = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					timezone = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					title = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					userName = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					userType = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
				}
			};

		user.setActive(true);
		user.setEmails(
			new MultiValuedAttribute[] {
				new MultiValuedAttribute() {
					{
						primary = true;
						type = "default";
						value = user.getUserName() + "@liferay.com";
					}
				}
			});
		user.setId((String)null);
		user.setName(
			new Name() {
				{
					familyName = RandomTestUtil.randomString();
					givenName = RandomTestUtil.randomString();
					middleName = RandomTestUtil.randomString();
				}
			});
		user.setSchemas(
			new String[] {
				"urn:ietf:params:scim:schemas:core:2.0:User",
				"urn:ietf:params:scim:schemas:extension:liferay:2.0:User"
			});
		user.setUrn_ietf_params_scim_schemas_extension_liferay_2_0_User(
			new UserSchemaExtension() {
				{
					birthday = DateUtils.truncate(new Date(), Calendar.DATE);
					male = true;
				}
			});

		return user;
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private static final String _CLASS_NAME =
		"com.liferay.scim.rest.internal.upgrade.v0_0_1." +
			"UserManagerUpdateExpandoColumnUpgradeProcess";

	private static GroupResource _groupResource;
	private static String _pid;

	@Inject(
		filter = "(&(component.name=com.liferay.scim.rest.internal.upgrade.registry.ScimRestUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private static UserResource _userResource;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

}