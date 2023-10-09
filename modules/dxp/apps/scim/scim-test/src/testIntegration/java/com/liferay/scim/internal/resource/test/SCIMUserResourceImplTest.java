/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.internal.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.scim.client.configuration.SCIMClientOAuth2ApplicationConfiguration;
import com.liferay.scim.resource.SCIMUserResource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@FeatureFlags("LPS-96845")
@RunWith(Arquillian.class)
public class SCIMUserResourceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		ConfigurationTestUtil.saveConfiguration(
			SCIMClientOAuth2ApplicationConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"applicationName", _SCIM_CLIENT_APPLICATION_NAME
			).put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"matcherField", "emailAddress"
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(
			SCIMClientOAuth2ApplicationConfiguration.class.getName());
	}

	@Test
	public void testUpdateSCIMUserWithNonexistingUser() throws Exception {
		String content = StringUtil.read(
			SCIMUserResourceImplTest.class, "dependencies/scim-user.json");

		JSONObject scimUserJSONObject = _jsonFactory.createJSONObject(content);

		JSONArray emailsJSONArray = scimUserJSONObject.getJSONArray("emails");

		JSONObject emailJSONObject = emailsJSONArray.getJSONObject(0);

		User user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), emailJSONObject.getString("value"));

		Assert.assertNull(user);

		_scimUserResource.updateSCIMUser("1234", content);

		user = _userLocalService.fetchUserByEmailAddress(
			TestPropsValues.getCompanyId(), emailJSONObject.getString("value"));

		Assert.assertNotNull(user);
	}

	private static final String _SCIM_CLIENT_APPLICATION_NAME =
		"scim-client-app-test";

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private SCIMUserResource _scimUserResource;

	@Inject
	private UserLocalService _userLocalService;

}