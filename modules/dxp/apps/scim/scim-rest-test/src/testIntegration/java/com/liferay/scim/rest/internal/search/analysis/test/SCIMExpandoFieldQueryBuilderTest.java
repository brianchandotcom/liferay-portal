/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.search.analysis.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.scim.rest.client.dto.v1_0.MultiValuedAttribute;
import com.liferay.scim.rest.client.dto.v1_0.Name;
import com.liferay.scim.rest.client.dto.v1_0.User;
import com.liferay.scim.rest.client.dto.v1_0.UserSchemaExtension;
import com.liferay.scim.rest.client.resource.v1_0.UserResource;
import com.liferay.scim.rest.resource.v1_0.test.BaseUserResourceTestCase;
import com.liferay.scim.rest.resource.v1_0.test.util.ScimTestUtil;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge García Jiménez
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@FeatureFlags("LPS-96845")
@RunWith(Arquillian.class)
public class SCIMExpandoFieldQueryBuilderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testExactSearchBySCIMClientId() throws Exception {
		BaseUserResourceTestCase.setUpClass();

		String oauth2AppName = "scim-client-test-cfg";

		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.scim.rest.internal.configuration." +
				"ScimClientOAuth2ApplicationConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"matcherField", "email"
			).put(
				"oAuth2ApplicationName", oauth2AppName
			).put(
				"userId", TestPropsValues.getUserId()
			).build());

		try {
			UserResource.Builder userResourceBuilder = UserResource.builder();

			UserResource userResource = userResourceBuilder.authentication(
				"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
			).locale(
				LocaleUtil.getDefault()
			).build();

			com.liferay.portal.kernel.model.User portalUser1 =
				UserTestUtil.addUser();
			com.liferay.portal.kernel.model.User portalUser2 =
				UserTestUtil.addUser();

			User user1 = _createUser(portalUser1);

			userResource.postV2User(user1);

			User user2 = _createUser(portalUser2);

			userResource.postV2User(user2);

			_assertListResponse(userResource.getV2Users(5, 0), 2, 2);

			ScimTestUtil.saveSCIMClientId(
				com.liferay.portal.kernel.model.User.class.getName(),
				portalUser2.getUserId(), portalUser2.getCompanyId(),
				"SCIM_" + oauth2AppName + "-" + RandomTestUtil.randomString());
			_reindexUser(portalUser2.getUserId());

			_assertListResponse(userResource.getV2Users(5, 0), 1, 1);
		}
		finally {
			ConfigurationTestUtil.deleteConfiguration(pid);
		}
	}

	private void _assertListResponse(
			Object response, long expectedTotalResults,
			long expectedItemsPerPage)
		throws Exception {

		JSONObject listResponseJSONObject = _jsonFactory.createJSONObject(
			response.toString());

		JSONArray schemasJSONArray = listResponseJSONObject.getJSONArray(
			"schemas");

		Assert.assertEquals(
			"urn:ietf:params:scim:api:messages:2.0:ListResponse",
			schemasJSONArray.get(0));

		Assert.assertEquals(
			expectedTotalResults,
			listResponseJSONObject.getLong("totalResults"));
		Assert.assertEquals(
			expectedItemsPerPage,
			listResponseJSONObject.getLong("itemsPerPage"));
	}

	private User _createUser(com.liferay.portal.kernel.model.User portalUser) {
		User user = new User();

		user.setActive(true);
		user.setEmails(
			new MultiValuedAttribute[] {
				new MultiValuedAttribute() {
					{
						primary = true;
						type = "default";
						value = portalUser.getEmailAddress();
					}
				}
			});
		user.setId((String)null);
		user.setName(
			new Name() {
				{
					familyName = portalUser.getLastName();
					givenName = portalUser.getFirstName();
					middleName = portalUser.getMiddleName();
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

		user.setUserName(portalUser.getScreenName());

		return user;
	}

	private void _reindexUser(long userId) throws SearchException {
		Indexer<com.liferay.portal.kernel.model.User> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				com.liferay.portal.kernel.model.User.class);

		indexer.reindex(
			com.liferay.portal.kernel.model.User.class.getName(), userId);
	}

	@Inject
	private JSONFactory _jsonFactory;

}