/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.web.internal.display.context;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.info.item.capability.TemplateInfoItemCapability;

import jakarta.portlet.PortletURL;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class InformationTemplatesManagementToolbarDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_portletPermissionUtilMockedStatic = Mockito.mockStatic(
			PortletPermissionUtil.class);

		Mockito.when(
			PortletPermissionUtil.contains(
				null, 0, null, TemplatePortletKeys.TEMPLATE,
				DDMActionKeys.ADD_TEMPLATE, false, false)
		).thenReturn(
			Boolean.TRUE
		);

		_portletURLUtilMockedStatic = Mockito.mockStatic(
			PortletURLUtil.class,
			invocationOnMock -> Mockito.mock(PortletURL.class));
	}

	@AfterClass
	public static void tearDownClass() {
		_portletPermissionUtilMockedStatic.close();
		_portletURLUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _setUpThemeDisplay());

		InfoItemClassDetails infoItemClassDetails = _mockInfoItemClassDetails();

		Mockito.when(
			_infoItemServiceRegistry.getInfoItemClassDetails(
				0, TemplateInfoItemCapability.KEY, null)
		).thenReturn(
			List.of(infoItemClassDetails)
		);

		Mockito.when(
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				infoItemClassDetails.getClassName())
		).thenReturn(
			_infoItemFormVariationsProvider
		);

		_liferayPortletRequest.setAttribute(
			InfoItemServiceRegistry.class.getName(), _infoItemServiceRegistry);

		_informationTemplatesManagementToolbarDisplayContext =
			new InformationTemplatesManagementToolbarDisplayContext(
				_httpServletRequest,
				Mockito.mock(InformationTemplatesTemplateDisplayContext.class),
				_liferayPortletRequest,
				Mockito.mock(LiferayPortletResponse.class));
	}

	@Test
	@TestInfo("LPD-56468")
	public void testGetItemTypesJSONArray() {
		String key = RandomTestUtil.randomString();

		InfoItemFormVariation informationItemFormVariation =
			_mockInfoItemFormVariation(key, null);

		Mockito.when(
			_infoItemFormVariationsProvider.getInfoItemFormVariations(0)
		).thenReturn(
			List.of(informationItemFormVariation)
		);

		JSONArray jsonArray = ReflectionTestUtil.invoke(
			_informationTemplatesManagementToolbarDisplayContext,
			"_getItemTypesJSONArray", new Class<?>[0]);

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(_CLASS_NAME, jsonObject.getString("value"));
		Assert.assertEquals(_LABEL, jsonObject.getString("label"));

		Assert.assertTrue(jsonObject.has("subtypes"));

		JSONArray subtypesJSONArray = jsonObject.getJSONArray("subtypes");

		Assert.assertEquals(
			subtypesJSONArray.toString(), 1, subtypesJSONArray.length());

		JSONObject subtypesJSONObject = subtypesJSONArray.getJSONObject(0);

		Assert.assertEquals(key, subtypesJSONObject.getString("value"));
		Assert.assertEquals(
			StringPool.BLANK, subtypesJSONObject.getString("label"));
	}

	private InfoItemClassDetails _mockInfoItemClassDetails() {
		InfoItemClassDetails infoItemClassDetails = Mockito.mock(
			InfoItemClassDetails.class);

		Mockito.when(
			infoItemClassDetails.getClassName()
		).thenReturn(
			_CLASS_NAME
		);

		Mockito.when(
			infoItemClassDetails.getLabel(LocaleUtil.CANADA)
		).thenReturn(
			_LABEL
		);

		return infoItemClassDetails;
	}

	private InfoItemFormVariation _mockInfoItemFormVariation(
		String key, String label) {

		InfoItemFormVariation informationItemFormVariation = Mockito.mock(
			InfoItemFormVariation.class);

		Mockito.when(
			informationItemFormVariation.getKey()
		).thenReturn(
			key
		);

		Mockito.when(
			informationItemFormVariation.getLabel(LocaleUtil.CANADA)
		).thenReturn(
			label
		);

		return informationItemFormVariation;
	}

	private ThemeDisplay _setUpThemeDisplay() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.CANADA);

		return themeDisplay;
	}

	private static final String _CLASS_NAME = RandomTestUtil.randomString();

	private static final String _LABEL = RandomTestUtil.randomString();

	private static MockedStatic<PortletPermissionUtil>
		_portletPermissionUtilMockedStatic;
	private static MockedStatic<PortletURLUtil> _portletURLUtilMockedStatic;

	private final HttpServletRequest _httpServletRequest =
		new MockHttpServletRequest();
	private final InfoItemFormVariationsProvider<?>
		_infoItemFormVariationsProvider = Mockito.mock(
			InfoItemFormVariationsProvider.class);
	private final InfoItemServiceRegistry _infoItemServiceRegistry =
		Mockito.mock(InfoItemServiceRegistry.class);
	private InformationTemplatesManagementToolbarDisplayContext
		_informationTemplatesManagementToolbarDisplayContext;
	private final LiferayPortletRequest _liferayPortletRequest =
		new MockLiferayPortletRenderRequest();

}