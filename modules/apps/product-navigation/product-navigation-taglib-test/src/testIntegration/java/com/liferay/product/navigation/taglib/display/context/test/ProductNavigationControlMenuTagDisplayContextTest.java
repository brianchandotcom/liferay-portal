/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.taglib.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuCategory;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.product.navigation.taglib.display.context.ProductNavigationControlMenuTagDisplayContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.PageContext;

import java.io.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Gabriel Prates
 */
@RunWith(Arquillian.class)
public class ProductNavigationControlMenuTagDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		Bundle bundle = FrameworkUtil.getBundle(
			ProductNavigationControlMenuTagDisplayContextTest.class);

		_bundleContext = bundle.getBundleContext();

		_serviceRegistrations.add(_registerCategory());
	}

	@After
	public void tearDown() throws Exception {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	@Test
	public void testWriteProductNavigationControlMenuEntriesWithMultipleEntries()
		throws Exception {

		_serviceRegistrations.add(
			_registerEntry(RandomTestUtil.randomString(), 100));
		_serviceRegistrations.add(
			_registerEntry(RandomTestUtil.randomString(), 200));

		String html = _render();

		Assert.assertTrue(
			html,
			html.contains(
				StringBundler.concat(
					"<div class=\"control-menu-nav-category ", _CATEGORY_KEY,
					"-control-group\"><ul class=\"control-menu-nav\">")));
		Assert.assertFalse(
			html,
			html.contains(
				StringBundler.concat(
					"<div class=\"control-menu-nav-category ", _CATEGORY_KEY,
					"-control-group\"><ul role=\"presentation\"")));
	}

	@Test
	public void testWriteProductNavigationControlMenuEntriesWithSingleEntry()
		throws Exception {

		_serviceRegistrations.add(
			_registerEntry(RandomTestUtil.randomString(), 100));

		String html = _render();

		Assert.assertTrue(
			html,
			html.contains(
				StringBundler.concat(
					"<div class=\"control-menu-nav-category ", _CATEGORY_KEY,
					"-control-group\"><ul role=\"presentation\" ",
					"class=\"control-menu-nav\"><li role=\"presentation\" ",
					"class=\"control-menu-nav-item\">")));
	}

	private ThemeDisplay _getThemeDisplay(HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());
		themeDisplay.setLocale(LocaleUtil.ENGLISH);
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSignedIn(true);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private ServiceRegistration<ProductNavigationControlMenuCategory>
		_registerCategory() {

		return _bundleContext.registerService(
			ProductNavigationControlMenuCategory.class,
			new ProductNavigationControlMenuCategory() {

				@Override
				public String getKey() {
					return _CATEGORY_KEY;
				}

				@Override
				public boolean hasAccessPermission(
					HttpServletRequest httpServletRequest) {

					return true;
				}

			},
			HashMapDictionaryBuilder.<String, Object>put(
				"product.navigation.control.menu.category.key",
				ProductNavigationControlMenuCategoryKeys.ROOT
			).put(
				"product.navigation.control.menu.category.order", 1000
			).build());
	}

	private ServiceRegistration<ProductNavigationControlMenuEntry>
		_registerEntry(String key, int order) {

		return _bundleContext.registerService(
			ProductNavigationControlMenuEntry.class,
			new TestProductNavigationControlMenuEntry(key),
			HashMapDictionaryBuilder.<String, Object>put(
				"product.navigation.control.menu.category.key", _CATEGORY_KEY
			).put(
				"product.navigation.control.menu.entry.order", order
			).build());
	}

	private String _render() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(mockHttpServletRequest));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		PageContext pageContext = new MockPageContext(
			new MockServletContext(), mockHttpServletRequest,
			mockHttpServletResponse);

		ProductNavigationControlMenuTagDisplayContext
			productNavigationControlMenuTagDisplayContext =
				new ProductNavigationControlMenuTagDisplayContext(
					mockHttpServletRequest, pageContext);

		Writer writer = pageContext.getOut();

		productNavigationControlMenuTagDisplayContext.
			writeProductNavigationControlMenuEntries(writer);

		writer.flush();

		return mockHttpServletResponse.getContentAsString();
	}

	private static final String _CATEGORY_KEY = RandomTestUtil.randomString();

	private BundleContext _bundleContext;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

	private static class TestProductNavigationControlMenuEntry
		extends BaseProductNavigationControlMenuEntry {

		public TestProductNavigationControlMenuEntry(String key) {
			_key = key;
		}

		@Override
		public String getKey() {
			return _key;
		}

		@Override
		public String getLabel(Locale locale) {
			return _key;
		}

		@Override
		public String getURL(HttpServletRequest httpServletRequest) {
			return StringPool.BLANK;
		}

		private final String _key;

	}

}