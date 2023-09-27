/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.layout.admin.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.util.SitemapURLProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class LayoutSitemapURLProviderTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layoutSet = _layoutSetLocalService.getLayoutSet(
			_group.getGroupId(), false);

		_initThemeDisplay();

		LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	public void testLayoutSitemapURLProviderContentLayoutTypeNoPublished()
		throws Exception {

		Element rootElement = _getRootElement();

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_layoutSitemapURLProvider.visitLayout(
			rootElement, layout.getUuid(), _themeDisplay.getLayoutSet(),
			_themeDisplay);

		Assert.assertFalse(rootElement.hasContent());
	}

	@Test
	public void testLayoutSitemapURLProviderContentLayoutTypePublished()
		throws Exception {

		Element rootElement = _getRootElement();

		Set<Locale> availableLocales = SetUtil.fromArray(
			LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.BRAZIL);

		Layout layout = LayoutTestUtil.addTypeContentLayout(
			_group,
			HashMapBuilder.put(
				availableLocales, locale -> RandomTestUtil.randomString()
			).build());

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		_layoutLocalService.updateStatus(
			TestPropsValues.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				_group.getCompanyId(), _group.getGroupId(),
				TestPropsValues.getUserId()));

		_layoutSitemapURLProvider.visitLayout(
			rootElement, layout.getUuid(), _layoutSet, _themeDisplay);

		Assert.assertTrue(rootElement.hasContent());

		List<Element> elements = rootElement.elements();

		Assert.assertEquals(
			elements.toString(), availableLocales.size(), elements.size());

		Map<Locale, String> alternateURLsMap = _portal.getAlternateURLs(
			_portal.getCanonicalURL(
				_portal.getLayoutFullURL(layout, _themeDisplay), _themeDisplay,
				layout),
			_themeDisplay, layout);

		for (Element element : elements) {
			String layoutLocalizedURL = element.elementText("loc");

			Assert.assertTrue(
				layoutLocalizedURL,
				alternateURLsMap.containsValue(layoutLocalizedURL));
		}
	}

	@Test
	public void testLayoutSitemapURLProviderPortletLayoutType()
		throws Exception {

		Element rootElement = _getRootElement();

		Set<Locale> availableLocales = SetUtil.fromArray(
			LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.BRAZIL);

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), false,
			HashMapBuilder.put(
				availableLocales, locale -> RandomTestUtil.randomString()
			).build(),
			new HashMap<>());

		_layoutSitemapURLProvider.visitLayout(
			rootElement, layout.getUuid(), _layoutSet, _themeDisplay);

		Assert.assertTrue(rootElement.hasContent());

		List<Element> elements = rootElement.elements();

		Assert.assertEquals(
			elements.toString(), availableLocales.size(), elements.size());

		Map<Locale, String> alternateURLsMap = _portal.getAlternateURLs(
			_portal.getCanonicalURL(
				_portal.getLayoutFullURL(layout, _themeDisplay), _themeDisplay,
				layout),
			_themeDisplay, layout);

		for (Element element : elements) {
			String layoutLocalizedURL = element.elementText("loc");

			Assert.assertTrue(
				layoutLocalizedURL,
				alternateURLsMap.containsValue(layoutLocalizedURL));
		}
	}

	private Element _getRootElement() {
		Document document = _saxReader.createDocument();

		document.setXMLEncoding("UTF-8");

		Element rootElement = document.addElement(
			"urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");

		rootElement.addAttribute(
			"xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		rootElement.addAttribute(
			"xsi:schemaLocation",
			"http://www.w3.org/1999/xhtml " +
				"http://www.w3.org/2002/08/xhtml/xhtml1-strict.xsd");
		rootElement.addAttribute("xmlns:xhtml", "http://www.w3.org/1999/xhtml");

		return rootElement;
	}

	private void _initThemeDisplay() throws Exception {
		_themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompany(
			_group.getCompanyId());

		_themeDisplay.setCompany(company);

		_themeDisplay.setLanguageId(_group.getDefaultLanguageId());
		_themeDisplay.setLayoutSet(_layoutSet);
		_themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()));
		_themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
		_themeDisplay.setPortalDomain(company.getVirtualHostname());
		_themeDisplay.setPortalURL(company.getPortalURL(_group.getGroupId()));
		_themeDisplay.setRequest(new MockHttpServletRequest());
		_themeDisplay.setScopeGroupId(_group.getGroupId());
		_themeDisplay.setServerPort(8080);
		_themeDisplay.setSignedIn(true);
		_themeDisplay.setSiteGroupId(_group.getGroupId());
		_themeDisplay.setUser(TestPropsValues.getUser());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private LayoutSet _layoutSet;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject(
		filter = "component.name=com.liferay.layout.internal.layout.admin.util.LayoutSitemapURLProvider",
		type = SitemapURLProvider.class
	)
	private SitemapURLProvider _layoutSitemapURLProvider;

	@Inject
	private Portal _portal;

	@Inject
	private SAXReader _saxReader;

	private ThemeDisplay _themeDisplay;

}