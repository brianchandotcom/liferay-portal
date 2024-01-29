/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
 * @author Iván Zaera
 */
public class FrontendTokenDefinitionManagerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws JSONException {
		_initManager();
	}

	@Test
	public void testAddFrontendTokenDefinitionFromClientExtension()
		throws Exception {

		ThemeCSSCET themeCSSCET =
			FrontendTokenDefinitionTestUtils.newDummyThemeCSSCET();

		_frontendTokenDefinitionManager.addFrontendTokenDefinition(
			themeCSSCET.getCompanyId(), themeCSSCET.getExternalReferenceCode(),
			themeCSSCET.getFrontendTokenDefinition());

		Mockito.verify(
			_jsonFactory
		).createJSONObject(
			themeCSSCET.getFrontendTokenDefinition()
		);
		Assert.assertTrue(
			_allFrontendTokenDefinitions.get(
				themeCSSCET.getCompanyId()
			).containsKey(
				themeCSSCET.getExternalReferenceCode()
			));
	}

	@Test
	public void testAddFrontendTokenDefinitionFromWorkspace() throws Exception {
		_frontendTokenDefinitionManager.addFrontendTokenDefinition(
			"any-theme-id",
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader(),
			"any-json");

		Mockito.verify(
			_jsonFactory
		).createJSONObject(
			"any-json"
		);
		Assert.assertFalse(
			_allFrontendTokenDefinitions.containsKey("any-theme-id"));
		Assert.assertTrue(
			_themeIdFrontendTokenDefinitions.containsKey("any-theme-id"));
	}

	@Test
	public void testRemoveFrontendTokenDefinitionFromClientExtensions()
		throws Exception {

		ThemeCSSCET themeCSSCET =
			FrontendTokenDefinitionTestUtils.newDummyThemeCSSCET();

		_allFrontendTokenDefinitions.put(
			themeCSSCET.getCompanyId(),
			ConcurrentHashMapBuilder.put(
				themeCSSCET.getExternalReferenceCode(),
				Mockito.mock(FrontendTokenDefinition.class)
			).build());

		_frontendTokenDefinitionManager.removeFrontendTokenDefinition(
			themeCSSCET.getCompanyId(), themeCSSCET.getExternalReferenceCode());

		Assert.assertNull(
			_allFrontendTokenDefinitions.get(
				themeCSSCET.getCompanyId()
			).get(
				themeCSSCET.getExternalReferenceCode()
			));

		Assert.assertTrue(_themeIdFrontendTokenDefinitions.isEmpty());
	}

	@Test
	public void testRemoveFrontendTokenDefinitionFromWorkspace()
		throws Exception {

		_themeIdFrontendTokenDefinitions.put(
			"any-theme-id", Mockito.mock(FrontendTokenDefinitionImpl.class));
		_frontendTokenDefinitionManager.removeFrontendTokenDefinition(
			"any-theme-id");

		Assert.assertTrue(_themeIdFrontendTokenDefinitions.isEmpty());
	}

	private void _initManager() throws JSONException {
		_jsonFactory = Mockito.mock(JSONFactory.class);
		_allFrontendTokenDefinitions = new ConcurrentHashMap<>();
		_themeIdFrontendTokenDefinitions = new ConcurrentHashMap<>();
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
			_jsonFactory, dclSingleton, _allFrontendTokenDefinitions,
			_themeIdFrontendTokenDefinitions);
	}

	private Map<Long, Map<String, FrontendTokenDefinition>>
		_allFrontendTokenDefinitions;
	private FrontendTokenDefinitionManager _frontendTokenDefinitionManager;
	private JSONFactory _jsonFactory;
	private Map<String, FrontendTokenDefinitionImpl>
		_themeIdFrontendTokenDefinitions;

}