/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.frontend.token.definition.FrontendToken;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author Anderson Luiz
 * @author Thiago Buarque
 */
@FeatureFlags("LPD-10773")
@RunWith(Arquillian.class)
public class FrontendTokenDefinitionRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	private String _frontendTokenDefinitionJson;

	@Before
	public void setUp() throws Exception {
		URL url = getClass().getResource("dependencies/frontend-token-definition.json");
		_frontendTokenDefinitionJson = JSONFactoryUtil.createJSONObject(URLUtil.toString(url)).toString();
		_user = UserTestUtil.addUser();

		_clientExtensionEntry =
			_clientExtensionEntryLocalService.addClientExtensionEntry(
				"any-external-reference", _user.getUserId(), "",
				HashMapBuilder.put(
					LocaleUtil.getDefault(), "Client Extension Name"
				).build(),

				"", "", ClientExtensionEntryConstants.TYPE_THEME_CSS, "frontendTokenDefinition="+_frontendTokenDefinitionJson);
	}

	@After
	public void tearDown() throws PortalException {
		_clientExtensionEntryLocalService.deleteClientExtensionEntry(
			_clientExtensionEntry.getClientExtensionEntryId());
	}

	@Test
	public void testGetClientExtensionFrontendTokenDefinition() throws JSONException {
		FrontendTokenDefinition frontendTokenDefinition = _frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
	_user.getCompanyId(), "any-external-reference", "any-theme");

		_assertFrontendTokenDefinition(frontendTokenDefinition);
	}

	@Test
	public void testGetWorkspaceFrontendTokenDefinition() throws JSONException {
		FrontendTokenDefinition frontendTokenDefinition = _frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				-1, null, "classic");

		_assertFrontendTokenDefinition(frontendTokenDefinition);
	}

	private void _assertFrontendTokenDefinition(FrontendTokenDefinition frontendTokenDefinition) throws JSONException {

		JSONObject expectedFrontendTokenDefinitionAsJson = JSONFactoryUtil.createJSONObject(_frontendTokenDefinitionJson);
		JSONObject expectedToken = expectedFrontendTokenDefinitionAsJson.getJSONArray("frontendTokenCategories")
				.getJSONObject(0).getJSONArray("frontendTokenSets").getJSONObject(0)
				.getJSONArray("frontendTokens").getJSONObject(0);
		FrontendToken token = new ArrayList<>(frontendTokenDefinition.getFrontendTokens()).get(0);

		Assert.assertEquals(1, frontendTokenDefinition.getFrontendTokenCategories().size());
		Assert.assertEquals(1, frontendTokenDefinition.getFrontendTokenSets().size());
		Assert.assertEquals(1, frontendTokenDefinition.getFrontendTokens().size());
		Assert.assertEquals(1, frontendTokenDefinition.getFrontendTokenMappings().size());
		Assert.assertEquals(expectedToken.getString("defaultValue"), token.getDefaultValue().toString());
		Assert.assertEquals(expectedToken.getString("name"), token.getName());
		Assert.assertEquals(expectedToken.getString("type"), token.getType().getValue());
		Assert.assertEquals(expectedToken.getJSONArray("mappings").get(0).toString(), new ArrayList<>(token.getFrontendTokenMappings()).get(0).getJSONObject(Locale.ENGLISH).toString());
	}
	private ClientExtensionEntry _clientExtensionEntry;

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	@Inject
	private FrontendTokenDefinitionRegistry _frontendTokenDefinitionRegistry;

	private User _user;

}