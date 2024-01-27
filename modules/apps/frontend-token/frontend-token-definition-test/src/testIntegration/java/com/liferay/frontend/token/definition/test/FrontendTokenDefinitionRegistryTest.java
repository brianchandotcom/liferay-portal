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
import com.liferay.frontend.token.definition.FrontendTokenCategory;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.FrontendTokenSet;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
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

import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		JSONFactory jsonFactory = new JSONFactoryImpl();

		JSONObject frontendTokenDefinitionJSONObject =
			jsonFactory.createJSONObject(
				URLUtil.toString(
					FrontendTokenDefinitionRegistryTest.class.getResource(
						"dependencies/frontend-token-definition.json")));

		_clientExtensionEntry =
			_clientExtensionEntryLocalService.addClientExtensionEntry(
				"any-external-reference", _user.getUserId(), "",
				HashMapBuilder.put(
					LocaleUtil.getDefault(), "Client Extension Name"
				).build(),
				"", "", ClientExtensionEntryConstants.TYPE_THEME_CSS,
				"frontendTokenDefinition=" +
					frontendTokenDefinitionJSONObject.toString());
	}

	@After
	public void tearDown() throws PortalException {
		_clientExtensionEntryLocalService.deleteClientExtensionEntry(
			_clientExtensionEntry.getClientExtensionEntryId());
	}

	@Test
	public void testGetClientExtensionFrontendTokenDefinition() {
		FrontendTokenDefinition frontendTokenDefinition =
			_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				_user.getCompanyId(), "any-external-reference", null);

		Assert.assertNotNull(frontendTokenDefinition);

		Collection<FrontendTokenCategory> frontendTokenCategories =
			frontendTokenDefinition.getFrontendTokenCategories();

		Assert.assertEquals(
			frontendTokenCategories.toString(), 1,
			frontendTokenCategories.size());

		Collection<FrontendTokenSet> frontendTokenSets =
			frontendTokenDefinition.getFrontendTokenSets();

		Assert.assertEquals(
			frontendTokenSets.toString(), 1, frontendTokenSets.size());

		Collection<FrontendToken> frontendTokens =
			frontendTokenDefinition.getFrontendTokens();

		Assert.assertEquals(
			frontendTokens.toString(), 1, frontendTokens.size());
	}

	private ClientExtensionEntry _clientExtensionEntry;

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	@Inject
	private FrontendTokenDefinitionRegistry _frontendTokenDefinitionRegistry;

	private User _user;

}