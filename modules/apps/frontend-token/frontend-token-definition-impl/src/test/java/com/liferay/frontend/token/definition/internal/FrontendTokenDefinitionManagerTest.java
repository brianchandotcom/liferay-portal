/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.internal.util.FrontendTokenDefinitionTestUtils;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ConcurrentHashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Anderson Luiz
 */
public class FrontendTokenDefinitionManagerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws JSONException {
		_jsonFactory = Mockito.mock(JSONFactory.class);
		_companyFrontendTokenDefinitionsMap = new ConcurrentHashMap<>();
		_themeIdFrontendTokenDefinitionsMap = new ConcurrentHashMap<>();
		DCLSingleton<Map<String, FrontendTokenDefinitionImpl>> dclSingleton =
			Mockito.mock(DCLSingleton.class);

		JSONObject jsonObject = JSONUtil.put(
			"frontendTokenCategories", JSONFactoryUtil.createJSONArray());

		Mockito.when(
			_jsonFactory.createJSONObject(Mockito.anyString())
		).thenReturn(
			jsonObject
		);

		Mockito.when(
			_jsonFactory.looseSerializeDeep(Mockito.any())
		).thenReturn(
			""
		);
		_frontendTokenDefinitionManager = new FrontendTokenDefinitionManager(
			_companyFrontendTokenDefinitionsMap, _jsonFactory,
			_themeIdFrontendTokenDefinitionsMap, dclSingleton);
	}

	@Test
	public void testAddThemeCSSCETFrontendTokenDefinition() throws Exception {
		ThemeCSSCET themeCSSCET =
			FrontendTokenDefinitionTestUtils.getDummyThemeCSSCET();

		_frontendTokenDefinitionManager.addFrontendTokenDefinition(
			themeCSSCET.getCompanyId(), themeCSSCET.getExternalReferenceCode(),
			themeCSSCET.getFrontendTokenDefinition());

		Mockito.verify(
			_jsonFactory
		).createJSONObject(
			themeCSSCET.getFrontendTokenDefinition()
		);

		Map<String, FrontendTokenDefinition> frontendTokenDefinitionMap =
			_companyFrontendTokenDefinitionsMap.get(themeCSSCET.getCompanyId());

		Assert.assertTrue(
			frontendTokenDefinitionMap.containsKey(
				themeCSSCET.getExternalReferenceCode()));
	}

	@Test
	public void testAddThemeFrontendTokenDefinition() throws Exception {
		_frontendTokenDefinitionManager.addFrontendTokenDefinition(
			"any-json",
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader(),
			"any-theme-id");

		Mockito.verify(
			_jsonFactory
		).createJSONObject(
			"any-json"
		);

		Assert.assertTrue(
			_themeIdFrontendTokenDefinitionsMap.containsKey("any-theme-id"));
	}

	@Test
	public void testRemoveThemeCSSCETFrontendTokenDefinition() {
		ThemeCSSCET themeCSSCET =
			FrontendTokenDefinitionTestUtils.getDummyThemeCSSCET();

		_companyFrontendTokenDefinitionsMap.put(
			themeCSSCET.getCompanyId(),
			ConcurrentHashMapBuilder.put(
				themeCSSCET.getExternalReferenceCode(),
				Mockito.mock(FrontendTokenDefinition.class)
			).build());

		_frontendTokenDefinitionManager.removeFrontendTokenDefinition(
			themeCSSCET.getCompanyId(), themeCSSCET.getExternalReferenceCode());

		Map<String, FrontendTokenDefinition> frontendTokenDefinitionsMap =
			_companyFrontendTokenDefinitionsMap.get(themeCSSCET.getCompanyId());

		Assert.assertNull(
			frontendTokenDefinitionsMap.get(
				themeCSSCET.getExternalReferenceCode()));

		Assert.assertTrue(_themeIdFrontendTokenDefinitionsMap.isEmpty());
	}

	@Test
	public void testRemoveThemeFrontendTokenDefinition() {
		_themeIdFrontendTokenDefinitionsMap.put(
			"any-theme-id", Mockito.mock(FrontendTokenDefinitionImpl.class));
		_frontendTokenDefinitionManager.removeFrontendTokenDefinition(
			"any-theme-id");

		Assert.assertTrue(_themeIdFrontendTokenDefinitionsMap.isEmpty());
	}

	private Map<Long, Map<String, FrontendTokenDefinition>>
		_companyFrontendTokenDefinitionsMap;
	private FrontendTokenDefinitionManager _frontendTokenDefinitionManager;
	private JSONFactory _jsonFactory;
	private Map<String, FrontendTokenDefinitionImpl>
		_themeIdFrontendTokenDefinitionsMap;

}