/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.frontend.token.definition.FrontendToken;
import com.liferay.frontend.token.definition.FrontendTokenCategory;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.frontend.token.definition.FrontendTokenSet;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
@RunWith(Arquillian.class)
public class FrontendTokenDefinitionRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = getClass();

		_frontendTokenDefinitionJSONObject = JSONFactoryUtil.createJSONObject(
			URLUtil.toString(
				clazz.getResource("/WEB-INF/frontend-token-definition.json")));

		_group = GroupTestUtil.addGroup();

		_layoutSet = _layoutSetLocalService.fetchLayoutSet(
			_group.getGroupId(), false);

		_layoutSet.setThemeId(_THEME_ID_TEST_LAYOUT_SET);

		User user = UserTestUtil.addUser();

		_clientExtensionEntry =
			_clientExtensionEntryLocalService.addClientExtensionEntry(
				_THEME_ID_CLIENT_EXTENSION_ENTRY, user.getUserId(),
				StringPool.BLANK,
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				StringPool.BLANK, StringPool.BLANK,
				ClientExtensionEntryConstants.TYPE_THEME_CSS,
				"frontendTokenDefinitionJSON=" +
					_frontendTokenDefinitionJSONObject.toString());

		_clientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
				user.getUserId(), _layoutSet.getGroupId(),
				_portal.getClassNameId(LayoutSet.class),
				_layoutSet.getLayoutSetId(),
				_clientExtensionEntry.getExternalReferenceCode(),
				_clientExtensionEntry.getType(), StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@After
	public void tearDown() throws PortalException {
		_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRel(
			_clientExtensionEntryRel);

		_clientExtensionEntryLocalService.deleteClientExtensionEntry(
			_clientExtensionEntry);
	}

	@Test
	public void testGetFrontendTokenDefinition() {
		_assertFrontendTokenDefinition(
			_THEME_ID_CLIENT_EXTENSION_ENTRY,
			_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				_layoutSet));

		_layoutSet.setLayoutSetId(RandomTestUtil.randomLong());

		_assertFrontendTokenDefinition(
			_THEME_ID_TEST_LAYOUT_SET,
			_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				_layoutSet));
	}

	@Test
	public void testGetFrontendTokenDefinitions() {
		List<FrontendTokenDefinition> frontendTokenDefinitions =
			_frontendTokenDefinitionRegistry.getFrontendTokenDefinitions(
				_layoutSet.getCompanyId());

		Assert.assertTrue(
			ListUtil.exists(
				frontendTokenDefinitions,
				frontendTokenDefinition -> Objects.equals(
					frontendTokenDefinition.getThemeId(),
					_THEME_ID_CLIENT_EXTENSION_ENTRY)));
		Assert.assertTrue(
			ListUtil.exists(
				frontendTokenDefinitions,
				frontendTokenDefinition -> Objects.equals(
					frontendTokenDefinition.getThemeId(),
					_THEME_ID_TEST_LAYOUT_SET)));
	}

	private void _assertFrontendTokenDefinition(
		String expectedFrontendTokenDefinitionThemeId,
		FrontendTokenDefinition frontendTokenDefinition) {

		Collection<FrontendTokenCategory> frontendTokenCategories =
			frontendTokenDefinition.getFrontendTokenCategories();

		Assert.assertEquals(
			frontendTokenCategories.toString(), 1,
			frontendTokenCategories.size());

		Collection<FrontendTokenSet> frontendTokenSets =
			frontendTokenDefinition.getFrontendTokenSets();

		Assert.assertEquals(
			frontendTokenSets.toString(), 1, frontendTokenSets.size());

		List<FrontendToken> frontendTokens = new ArrayList<>(
			frontendTokenDefinition.getFrontendTokens());

		Assert.assertEquals(
			frontendTokens.toString(), 1, frontendTokens.size());

		JSONObject expectedTokenJSONObject = JSONUtil.getValueAsJSONObject(
			_frontendTokenDefinitionJSONObject,
			"JSONArray/frontendTokenCategories", "JSONObject/0",
			"JSONArray/frontendTokenSets", "JSONObject/0",
			"JSONArray/frontendTokens", "JSONObject/0");

		FrontendToken frontendToken = frontendTokens.get(0);

		Assert.assertEquals(
			expectedTokenJSONObject.getString("defaultValue"),
			frontendToken.getDefaultValue());

		Assert.assertEquals(
			expectedTokenJSONObject.getString("name"), frontendToken.getName());

		FrontendToken.Type type = frontendToken.getType();

		Assert.assertEquals(
			expectedTokenJSONObject.getString("type"), type.getValue());

		List<FrontendTokenMapping> frontendTokenMappings = new ArrayList<>(
			frontendToken.getFrontendTokenMappings());

		Assert.assertEquals(
			frontendTokenMappings.toString(), 1, frontendTokenMappings.size());

		FrontendTokenMapping frontendTokenMapping = frontendTokenMappings.get(
			0);

		Assert.assertEquals(
			String.valueOf(
				JSONUtil.getValueAsJSONObject(
					expectedTokenJSONObject, "JSONArray/mappings",
					"JSONObject/0")),
			String.valueOf(
				frontendTokenMapping.getJSONObject(LocaleUtil.ENGLISH)));

		Assert.assertEquals(
			expectedFrontendTokenDefinitionThemeId,
			frontendTokenDefinition.getThemeId());
	}

	private static final String _THEME_ID_CLIENT_EXTENSION_ENTRY =
		RandomTestUtil.randomString();

	private static final String _THEME_ID_TEST_LAYOUT_SET =
		"testfrontendtokendefinition";

	private ClientExtensionEntry _clientExtensionEntry;

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	private ClientExtensionEntryRel _clientExtensionEntryRel;

	@Inject
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	private JSONObject _frontendTokenDefinitionJSONObject;

	@Inject
	private FrontendTokenDefinitionRegistry _frontendTokenDefinitionRegistry;

	private Group _group;
	private LayoutSet _layoutSet;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private Portal _portal;

}