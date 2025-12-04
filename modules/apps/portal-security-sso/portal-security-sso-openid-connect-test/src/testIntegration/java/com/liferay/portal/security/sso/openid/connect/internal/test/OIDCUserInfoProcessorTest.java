/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PrefsPropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectUser;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectUserLocalService;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge García Jiménez
 */
@RunWith(Arquillian.class)
public class OIDCUserInfoProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_customOIDCUserInfoMapperJSON = JSONUtil.put(
			"address",
			JSONUtil.put(
				"addressType", ""
			).put(
				"city", "address->locality"
			).put(
				"country", "address->country"
			).put(
				"region", "address->region"
			).put(
				"street", "address->street_address"
			).put(
				"zip", "address->postal_code"
			)
		).put(
			"contact",
			JSONUtil.put(
				"birthdate", "birthdate"
			).put(
				"gender", "gender"
			)
		).put(
			"phone",
			JSONUtil.put(
				"phone", "phone_number"
			).put(
				"phoneType", ""
			)
		).put(
			"user",
			JSONUtil.put(
				"emailAddress", "email"
			).put(
				"firstName", "given_name"
			).put(
				"jobTitle", ""
			).put(
				"languageId", "locale"
			).put(
				"lastName", "family_name"
			).put(
				"middleName", "middle_name"
			).put(
				"screenName", "preferred_username"
			)
		).put(
			"users_roles", JSONUtil.put("roles", "")
		).toString();
	}

	@Before
	public void setUp() throws Exception {
		_emailAddress = StringUtil.toLowerCase(
			RandomTestUtil.randomString() + "@liferay.com");

		ExpandoTable expandoTable = _expandoTableLocalService.addTable(
			TestPropsValues.getCompanyId(),
			_classNameLocalService.getClassNameId(User.class.getName()),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		_pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.portal.security.sso.openid.connect.internal." +
				"configuration.OpenIdConnectProviderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"customClaim",
				() -> {
					ExpandoColumn phoneNumberVerifiedExpandoColumn =
						_getOrAddExpandoColumn(
							expandoTable.getTableId(), "phoneNumberVerified",
							ExpandoColumnConstants.BOOLEAN);

					ExpandoColumn websiteExpandoColumn = _getOrAddExpandoColumn(
						expandoTable.getTableId(), "website",
						ExpandoColumnConstants.STRING);

					return new String[] {
						phoneNumberVerifiedExpandoColumn.getName() +
							"=phone_number_verified",
						websiteExpandoColumn.getName() + "=website"
					};
				}
			).put(
				"discoveryEndpoint", _DISCOVERY_ENDPOINT
			).put(
				"matcherField", "email"
			).put(
				"openIdConnectClientId", _CLIENT_ID
			).build());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId());
		_screenName = RandomTestUtil.randomString();
		_uuid = PortalUUIDUtil.generate();
	}

	@After
	public void tearDown() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_pid);
	}

	@FeatureFlag("LPD-20879")
	@Test
	public void testProcessUserInfo() throws Exception {
		_testProcessUserInfo(
			new String[0], "email", new String[0],
			_customOIDCUserInfoMapperJSON);
		_testProcessUserInfo(
			new String[0], "email", new String[0],
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);
		_testProcessUserInfo(
			new String[] {"group1"}, "email", new String[] {"group1"},
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);

		UserGroup userGroup = _userGroupLocalService.addUserGroup(
			StringPool.BLANK, TestPropsValues.getUserId(),
			TestPropsValues.getCompanyId(), "group2", StringPool.BLANK,
			_serviceContext);

		User user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), _emailAddress);

		_userGroupLocalService.addUserUserGroups(
			user.getUserId(), new long[] {userGroup.getUserGroupId()});

		_testProcessUserInfo(
			new String[] {"group1", "group2", "group3"}, "email",
			new String[] {"group1", "group3"},
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);
		_testProcessUserInfo(
			new String[] {"group1", "group2"}, "email", new String[] {"group1"},
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);
		_testProcessUserInfo(
			new String[] {"group2"}, "email", new String[0],
			_customOIDCUserInfoMapperJSON);

		_userGroupLocalService.deleteUserUserGroup(
			user.getUserId(), userGroup.getUserGroupId());

		_testProcessUserInfo(
			new String[0], "email", new String[0],
			_customOIDCUserInfoMapperJSON);
		_testProcessUserInfo(
			new String[0], "email", new String[0],
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);
		_testProcessUserInfo(
			new String[] {"group1"}, "email", new String[] {"group1"},
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);

		_emailAddress = null;

		user = UserTestUtil.addUser();

		_screenName = user.getScreenName();

		_uuid = PortalUUIDUtil.generate();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_pid,
					HashMapDictionaryBuilder.<String, Object>put(
						"companyId", TestPropsValues.getCompanyId()
					).put(
						"discoveryEndpoint", _DISCOVERY_ENDPOINT
					).put(
						"matcherField", "screenName"
					).put(
						"openIdConnectClientId", _CLIENT_ID
					).build());
			SafeCloseable safeCloseable =
				PrefsPropsTestUtil.swapWithSafeCloseable(
					TestPropsValues.getCompanyId(),
					PropsKeys.USERS_EMAIL_ADDRESS_REQUIRED,
					Boolean.FALSE.toString())) {

			_testProcessUserInfo(
				new String[0], "screenName", new String[0],
				_customOIDCUserInfoMapperJSON);
			_testProcessUserInfo(
				new String[] {"group1"}, "screenName", new String[] {"group1"},
				OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);
		}
	}

	private void _assertExpandoValue(
			CTModel<?> ctModel, long expectedOAUthClientEntryId)
		throws Exception {

		ExpandoTable expandoTable = _expandoTableLocalService.getTable(
			TestPropsValues.getCompanyId(),
			_classNameLocalService.getClassNameId(ctModel.getModelClassName()),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		ExpandoColumn expandoColumn = _expandoColumnLocalService.getColumn(
			expandoTable.getTableId(), "idpId");

		ExpandoValue expandoValue = _expandoValueLocalService.getValue(
			expandoColumn.getTableId(), expandoColumn.getColumnId(),
			ctModel.getPrimaryKey());

		Assert.assertEquals(expectedOAUthClientEntryId, expandoValue.getLong());
	}

	private User _fetchUser(String matcherField) throws Exception {
		if (matcherField.equals("email")) {
			return _userLocalService.fetchUserByEmailAddress(
				TestPropsValues.getCompanyId(), _emailAddress);
		}

		return _userLocalService.fetchUserByScreenName(
			TestPropsValues.getCompanyId(), _screenName);
	}

	private OAuthClientEntry _getOAuthClientEntry() throws Exception {
		return _oAuthClientEntryLocalService.getOAuthClientEntry(
			TestPropsValues.getCompanyId(), _DISCOVERY_ENDPOINT, _CLIENT_ID);
	}

	private ExpandoColumn _getOrAddExpandoColumn(
			long tableId, String columnName, int columnType)
		throws Exception {

		ExpandoColumn expandoColumn = _expandoColumnLocalService.fetchColumn(
			tableId, columnName);

		if (expandoColumn == null) {
			expandoColumn = _expandoColumnLocalService.addColumn(
				tableId, columnName, columnType);
		}

		return expandoColumn;
	}

	private void _testProcessUserInfo(
			String[] expectedUserGroupNames, String matcherField,
			String[] userGroupNames, String userInfoMapperJSON)
		throws Exception {

		User existingUser = _fetchUser(matcherField);

		List<String> newUserGroupNames = new ArrayList<>();

		for (String userGroupName : userGroupNames) {
			UserGroup userGroup = _userGroupLocalService.fetchUserGroup(
				TestPropsValues.getCompanyId(), userGroupName);

			if (userGroup != null) {
				continue;
			}

			newUserGroupNames.add(userGroupName);
		}

		JSONObject userInfoJSONObject = JSONUtil.put(
			"birthdate", String.valueOf(RandomTestUtil.nextDate())
		).put(
			"email", _emailAddress
		).put(
			"email_verified", true
		).put(
			"family_name", StringUtil.randomString()
		).put(
			"given_name", StringUtil.randomString()
		).put(
			"groups", userGroupNames
		).put(
			"middle_name", StringUtil.randomString()
		).put(
			"name", StringUtil.randomString()
		).put(
			"phone_number_verified", "true"
		).put(
			"preferred_username", _screenName
		).put(
			"sub", _uuid
		).put(
			"website", "www.test.com"
		);

		long userId = 0;

		try (SafeCloseable safeCloseable =
				_updateOAuthClientEntryWithSafeCloseable(userInfoMapperJSON)) {

			userId = ReflectionTestUtil.invoke(
				_oidcUserInfoProcessor, "processUserInfo",
				new Class<?>[] {
					long.class, String.class, OAuthClientEntry.class,
					ServiceContext.class, String.class, String.class
				},
				TestPropsValues.getCompanyId(), _ISSUER, _getOAuthClientEntry(),
				_serviceContext, RandomTestUtil.randomString(),
				userInfoJSONObject.toString());
		}

		User user = _fetchUser(matcherField);

		Assert.assertEquals(userId, user.getUserId());
		Assert.assertEquals(
			expectedUserGroupNames.length,
			_userGroupLocalService.getUserUserGroupsCount(user.getUserId()));

		OpenIdConnectUser openIdConnectUser =
			_openIdConnectUserLocalService.fetchOpenIdConnectUser(
				TestPropsValues.getCompanyId(), _ISSUER, _uuid);

		Assert.assertEquals(user.getUserId(), openIdConnectUser.getUserId());

		OAuthClientEntry oAuthClientEntry = _getOAuthClientEntry();

		if (existingUser == null) {
			_assertExpandoValue(user, oAuthClientEntry.getOAuthClientEntryId());
		}

		List<UserGroup> userUserGroups =
			_userGroupLocalService.getUserUserGroups(user.getUserId());

		for (UserGroup userUserGroup : userUserGroups) {
			Assert.assertTrue(
				ArrayUtil.contains(
					expectedUserGroupNames, userUserGroup.getName()));
		}

		for (String userGroupName : newUserGroupNames) {
			_assertExpandoValue(
				_userGroupLocalService.getUserGroup(
					TestPropsValues.getCompanyId(), userGroupName),
				oAuthClientEntry.getOAuthClientEntryId());
		}

		ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
			TestPropsValues.getCompanyId(),
			_classNameLocalService.getClassNameId(User.class.getName()),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		JSONObject customClaimsJSONObject = _jsonFactory.createJSONObject(
			oAuthClientEntry.getCustomClaimsJSON());

		for (String key : customClaimsJSONObject.keySet()) {
			String value = customClaimsJSONObject.getString(key);

			ExpandoColumn expandoColumn =
				_expandoColumnLocalService.fetchColumn(
					expandoTable.getTableId(), key);

			ExpandoValue expandoValue = _expandoValueLocalService.getValue(
				expandoColumn.getTableId(), expandoColumn.getColumnId(),
				user.getUserId());

			Assert.assertEquals(
				userInfoJSONObject.get(value), expandoValue.getData());
		}
	}

	private SafeCloseable _updateOAuthClientEntryWithSafeCloseable(
			String oidcUserInfoMapperJSON)
		throws Exception {

		OAuthClientEntry originalOAuthClientEntry = _getOAuthClientEntry();

		String originalOIDCUserInfoMapperJSON =
			originalOAuthClientEntry.getOIDCUserInfoMapperJSON();

		originalOAuthClientEntry.setOIDCUserInfoMapperJSON(
			oidcUserInfoMapperJSON);

		_oAuthClientEntryLocalService.updateOAuthClientEntry(
			originalOAuthClientEntry);

		return () -> {
			try {
				OAuthClientEntry oAuthClientEntry = _getOAuthClientEntry();

				oAuthClientEntry.setOIDCUserInfoMapperJSON(
					originalOIDCUserInfoMapperJSON);

				_oAuthClientEntryLocalService.updateOAuthClientEntry(
					oAuthClientEntry);
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	private static final String _CLIENT_ID = RandomTestUtil.randomString();

	private static final String _DISCOVERY_ENDPOINT =
		"https://accounts.google.com/.well-known/openid-configuration";

	private static final String _ISSUER = RandomTestUtil.randomString();

	private static String _customOIDCUserInfoMapperJSON;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private String _emailAddress;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

	@Inject
	private ExpandoValueLocalService _expandoValueLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private OAuthClientEntryLocalService _oAuthClientEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.portal.security.sso.openid.connect.internal.OIDCUserInfoProcessor",
		type = Inject.NoType.class
	)
	private Object _oidcUserInfoProcessor;

	@Inject
	private OpenIdConnectUserLocalService _openIdConnectUserLocalService;

	private String _pid;
	private String _screenName;
	private ServiceContext _serviceContext;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserLocalService _userLocalService;

	private String _uuid;

}