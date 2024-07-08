/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.product.menu.display.context;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.product.navigation.applications.menu.configuration.ApplicationsMenuInstanceConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Anderson Luiz
 */
public class ProductMenuDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_applicationsMenuInstanceConfiguration = Mockito.mock(
			ApplicationsMenuInstanceConfiguration.class);

		PanelCategory applicationMenuPanel1 = _createPanelCategory(
			"application-panel1");
		PanelCategory applicationMenuPanel2 = _createPanelCategory(
			"application-panel2");
		PanelCategory applicationMenuPanel3 = _createPanelCategory(
			"application-panel3");

		_applicationsMenuPanelCategories = Arrays.asList(
			applicationMenuPanel1, applicationMenuPanel2,
			applicationMenuPanel3);

		_mockHttpServletRequest = new MockHttpServletRequest();

		_mockPortletRequest = new MockLiferayResourceRequest();

		_mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST, _mockPortletRequest);

		_themeDisplay = Mockito.mock(ThemeDisplay.class);

		_mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _themeDisplay);

		_mockPortletRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST, _mockHttpServletRequest);

		_mockPortletRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		_panelCategoryHelper = Mockito.mock(PanelCategoryHelper.class);

		_mockPortletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_HELPER, _panelCategoryHelper);

		PanelCategory rootPanel1 = _createPanelCategory("root-panel1");
		PanelCategory rootPanel2 = _createPanelCategory("root-panel2");
		PanelCategory rootPanel3 = _createPanelCategory("root-panel3");

		_rootPanelCategories = new ArrayList<>(
			Arrays.asList(rootPanel1, rootPanel2, rootPanel3));
	}

	@Test
	public void testShouldReturnOnlyPanelCategoriesThatContainPanelApps() {
		_assertGetPanelCategories(
			false,
			productMenuDisplayContext -> {
				List<PanelCategory> childPanelCategories =
					productMenuDisplayContext.getChildPanelCategories();

				Assert.assertEquals(
					childPanelCategories.toString(), 5,
					childPanelCategories.size());

				Mockito.verify(
					_panelCategoryHelper
				).getChildPanelCategories(
					PanelCategoryKeys.APPLICATIONS_MENU, _themeDisplay
				);

				Mockito.verify(
					_panelCategoryHelper, Mockito.times(3)
				).getAllPanelApps(
					ArgumentMatchers.matches("application-panel*")
				);
			});
	}

	@Test
	public void testShouldReturnOnlyRootPanelCategoriesWhenApplicationsMenuIsEnabled() {
		_assertGetPanelCategories(
			true,
			productMenuDisplayContext -> {
				List<PanelCategory> childPanelCategories =
					productMenuDisplayContext.getChildPanelCategories();

				Assert.assertEquals(
					childPanelCategories.toString(), 3,
					childPanelCategories.size());

				Mockito.verify(
					_panelCategoryHelper
				).getChildPanelCategories(
					PanelCategoryKeys.ROOT, _themeDisplay
				);

				Mockito.verify(
					_panelCategoryHelper, Mockito.never()
				).getChildPanelCategories(
					PanelCategoryKeys.APPLICATIONS_MENU, _themeDisplay
				);

				Mockito.verify(
					_panelCategoryHelper, Mockito.never()
				).getAllPanelApps(
					ArgumentMatchers.matches("application-panel*")
				);
			});
	}

	private void _assertGetPanelCategories(
		boolean enableApplicationsMenu,
		Consumer<ProductMenuDisplayContext> consumer) {

		try (MockedStatic<PortalUtil> portalUtilMockedStatic =
				Mockito.mockStatic(PortalUtil.class);
			MockedStatic<ConfigurationProviderUtil>
				configurationProviderUtilMockedStatic = Mockito.mockStatic(
					ConfigurationProviderUtil.class)) {

			portalUtilMockedStatic.when(
				() -> PortalUtil.getHttpServletRequest(ArgumentMatchers.any())
			).thenReturn(
				_mockHttpServletRequest
			);

			configurationProviderUtilMockedStatic.when(
				() -> ConfigurationProviderUtil.getCompanyConfiguration(
					ArgumentMatchers.any(), ArgumentMatchers.anyLong())
			).thenReturn(
				_applicationsMenuInstanceConfiguration
			);

			Mockito.when(
				_applicationsMenuInstanceConfiguration.enableApplicationsMenu()
			).thenReturn(
				enableApplicationsMenu
			);

			Mockito.when(
				_panelCategoryHelper.getChildPanelCategories(
					PanelCategoryKeys.ROOT, _themeDisplay)
			).thenReturn(
				_rootPanelCategories
			);

			Mockito.when(
				_panelCategoryHelper.getChildPanelCategories(
					PanelCategoryKeys.APPLICATIONS_MENU, _themeDisplay)
			).thenReturn(
				_applicationsMenuPanelCategories
			);

			Mockito.when(
				_panelCategoryHelper.getAllPanelApps(
					ArgumentMatchers.anyString())
			).thenReturn(
				Collections.singletonList(Mockito.mock(PanelApp.class))
			);

			Mockito.when(
				_panelCategoryHelper.getAllPanelApps(
					ArgumentMatchers.matches("panel2"))
			).thenReturn(
				Collections.emptyList()
			);

			consumer.accept(new ProductMenuDisplayContext(_mockPortletRequest));
		}
	}

	private PanelCategory _createPanelCategory(String key) {
		PanelCategory panelCategory = Mockito.mock(PanelCategory.class);

		Mockito.when(
			panelCategory.getKey()
		).thenReturn(
			key
		);

		return panelCategory;
	}

	private ApplicationsMenuInstanceConfiguration
		_applicationsMenuInstanceConfiguration;
	private List<PanelCategory> _applicationsMenuPanelCategories;
	private MockHttpServletRequest _mockHttpServletRequest;
	private MockLiferayResourceRequest _mockPortletRequest;
	private PanelCategoryHelper _panelCategoryHelper;
	private List<PanelCategory> _rootPanelCategories;
	private ThemeDisplay _themeDisplay;

}