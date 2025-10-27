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
				"screenName", ""
			)
		).put(
			"users_roles", JSONUtil.put("roles", "")
		).toString();
	}

	@Before
	public void setUp() throws Exception {
		_clientId = RandomTestUtil.randomString();

		_emailAddress = StringUtil.toLowerCase(
			RandomTestUtil.randomString() + "@liferay.com");

		_issuer = RandomTestUtil.randomString();

		_oAuthClientEntryId = RandomTestUtil.randomLong();

		_pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.portal.security.sso.openid.connect.internal." +
				"configuration.OpenIdConnectProviderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"discoveryEndpoint", _DISCOVERY_ENDPOINT
			).put(
				"matcherField", "email"
			).put(
				"openIdConnectClientId", _clientId
			).build());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		_serviceContext.setAttribute("oAuthClientEntryId", _oAuthClientEntryId);

		_uuid = PortalUUIDUtil.generate();
	}

	@After
	public void tearDown() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_pid);
	}

	@FeatureFlag("LPD-20879")
	@Test
	public void testProcessUserInfo() throws Exception {
		try (SafeCloseable safeCloseable = PrefsPropsTestUtil.swapWithSafeCloseable(
			TestPropsValues.getCompanyId(),
			PropsKeys.USERS_EMAIL_ADDRESS_REQUIRED,
			"false");

			ConfigurationTemporarySwapper companyConfigurationTemporarySwapper = new
				ConfigurationTemporarySwapper(
					_pid,
					HashMapDictionaryBuilder.<String, Object>put(
						"companyId", TestPropsValues.getCompanyId()
					).put(
						"discoveryEndpoint", _DISCOVERY_ENDPOINT
					).put(
						"matcherField", "screenName"
					).put(
						"openIdConnectClientId", _clientId
					).build()
				)
		) {

			String originalEmail = _emailAddress;
			_emailAddress = null;

			_testProcessUserInfo(
				"{}", new String[0], new String[0],
				_customOIDCUserInfoMapperJSON, "screenName");

			_emailAddress = originalEmail;
		}

		_testProcessUserInfo(
			"{}", new String[0], new String[0], _customOIDCUserInfoMapperJSON,
			"email");
		_testProcessUserInfo(
			"{}", new String[0], new String[0],
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON, "email");
		_testProcessUserInfo(
			"{}", new String[] {"group1"}, new String[] {"group1"},
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON, "email");

		UserGroup userGroup = _userGroupLocalService.addUserGroup(
			StringPool.BLANK, TestPropsValues.getUserId(),
			TestPropsValues.getCompanyId(), "group2", StringPool.BLANK,
			_serviceContext);

		User user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), _emailAddress);

		_userGroupLocalService.addUserUserGroups(
			user.getUserId(), new long[] {userGroup.getUserGroupId()});

		_testProcessUserInfo(
			"{}", new String[] {"group1", "group2", "group3"},
			new String[] {"group1", "group3"},
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON, "email");
		_testProcessUserInfo(
			"{}", new String[] {"group1", "group2"}, new String[] {"group1"},
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON, "email");
		_testProcessUserInfo(
			"{}", new String[] {"group2"}, new String[0],
			_customOIDCUserInfoMapperJSON, "email");

		_userGroupLocalService.deleteUserUserGroup(
			user.getUserId(), userGroup.getUserGroupId());

		_testProcessUserInfo(
			"{}", new String[0], new String[0], _customOIDCUserInfoMapperJSON,
			"email");
		_testProcessUserInfo(
			"{}", new String[0], new String[0],
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON, "email");
		_testProcessUserInfo(
			"{}", new String[] {"group1"}, new String[] {"group1"},
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON, "email");

		ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
			TestPropsValues.getCompanyId(),
			_classNameLocalService.getClassNameId(User.class.getName()),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		ExpandoColumn phoneNumberVerifiedExpandoColumn =
			_expandoColumnLocalService.addColumn(
				expandoTable.getTableId(), "phoneNumberVerified",
				ExpandoColumnConstants.BOOLEAN);
		ExpandoColumn websiteExpandoColumn =
			_expandoColumnLocalService.addColumn(
				expandoTable.getTableId(), "website",
				ExpandoColumnConstants.STRING);

		_testProcessUserInfo(
			JSONUtil.put(
				phoneNumberVerifiedExpandoColumn.getName(),
				"phone_number_verified"
			).put(
				websiteExpandoColumn.getName(), "website"
			).toString(),
			new String[] {"group1"}, new String[] {"group1"},
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON, "email");
	}

	private void _assertExpandoValue(CTModel<?> ctModel) throws Exception {
		ExpandoTable expandoTable = _expandoTableLocalService.getTable(
			TestPropsValues.getCompanyId(),
			_classNameLocalService.getClassNameId(ctModel.getModelClassName()),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		ExpandoColumn expandoColumn = _expandoColumnLocalService.getColumn(
			expandoTable.getTableId(), "idpId");

		ExpandoValue expandoValue = _expandoValueLocalService.getValue(
			expandoColumn.getTableId(), expandoColumn.getColumnId(),
			ctModel.getPrimaryKey());

		Assert.assertEquals(_oAuthClientEntryId, expandoValue.getLong());
	}

	private void _testProcessUserInfo(
			String customClaimsJSON, String[] expectedUserGroupNames,
			String[] userGroupNames, String userInfoMapperJSON,
			String matcherField)
		throws Exception {

		User existingUser = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), _emailAddress);

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
			"preferred_username", StringUtil.randomString()
		).put(
			"sub", _uuid
		).put(
			"website", "www.test.com"
		);

		OAuthClientEntry oAuthClientEntry =
			_oAuthClientEntryLocalService.fetchOAuthClientEntry(
				TestPropsValues.getCompanyId(), _DISCOVERY_ENDPOINT, _clientId);

		oAuthClientEntry.setCustomClaimsJSON(customClaimsJSON);
		oAuthClientEntry.setOIDCUserInfoMapperJSON(userInfoMapperJSON);

		long userId = ReflectionTestUtil.invoke(
			_oidcUserInfoProcessor, "processUserInfo",
			new Class<?>[] {
				long.class, String.class, OAuthClientEntry.class,
				ServiceContext.class, String.class, String.class
			},
			TestPropsValues.getCompanyId(), _issuer, oAuthClientEntry,
			_serviceContext, RandomTestUtil.randomString(),
			userInfoJSONObject.toString());

		User user = null;

		if (matcherField.equals("email")) {
			user = _userLocalService.fetchUserByEmailAddress(
				TestPropsValues.getCompanyId(), _emailAddress);

			Assert.assertEquals(_emailAddress, user.getEmailAddress());
		}

		if (matcherField.equals("screenName")) {
			user = _userLocalService.fetchUserByScreenName(
				TestPropsValues.getCompanyId(), String.valueOf(userId));
		}

		Assert.assertEquals(userId, user.getUserId());
		Assert.assertEquals(
			expectedUserGroupNames.length,
			_userGroupLocalService.getUserUserGroupsCount(user.getUserId()));

		OpenIdConnectUser openIdConnectUser =
			_openIdConnectUserLocalService.fetchOpenIdConnectUser(
				TestPropsValues.getCompanyId(), _issuer, _uuid);

		Assert.assertEquals(user.getUserId(), openIdConnectUser.getUserId());

		if (existingUser == null) {
			_assertExpandoValue(user);
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
					TestPropsValues.getCompanyId(), userGroupName));
		}

		ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
			TestPropsValues.getCompanyId(),
			_classNameLocalService.getClassNameId(User.class.getName()),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		JSONObject customClaimsJSONObject = _jsonFactory.createJSONObject(
			customClaimsJSON);

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

	private static final String _DISCOVERY_ENDPOINT =
		"https://accounts.google.com/.well-known/openid-configuration";

	private static String _customOIDCUserInfoMapperJSON;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private String _clientId;
	private String _emailAddress;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

	@Inject
	private ExpandoValueLocalService _expandoValueLocalService;

	private String _issuer;

	@Inject
	private JSONFactory _jsonFactory;

	private long _oAuthClientEntryId;

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
	private ServiceContext _serviceContext;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserLocalService _userLocalService;

	private String _uuid;

}