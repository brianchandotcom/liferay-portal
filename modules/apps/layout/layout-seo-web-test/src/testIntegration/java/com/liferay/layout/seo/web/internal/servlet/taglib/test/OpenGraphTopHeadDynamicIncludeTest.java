/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.seo.web.internal.servlet.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Alicia García
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class OpenGraphTopHeadDynamicIncludeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testIncludeCustomDescription() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"), true,
			Collections.singletonMap(LocaleUtil.US, "customDescription"), 0,
			false, Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()));

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(document, "og:description", "customDescription");
	}

	@Test
	public void testIncludeCustomTitle() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _layout.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			false, Collections.emptyMap(), 0, true,
			Collections.singletonMap(LocaleUtil.US, "customTitle"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()));

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(document, "og:title", "customTitle");
	}

	@Test
	public void testIncludeDefaultTitleAndDescription() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()));

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_assertMetaTag(
			document, "og:description", _layout.getDescription(LocaleUtil.US));
		_assertMetaTag(
			document, "og:title",
			StringBundler.concat(
				_layout.getName(LocaleUtil.US), " - ",
				_group.getDescriptiveName(LocaleUtil.US), " - ",
				company.getName()));
	}

	@Test
	public void testIncludeImage() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		FileEntry fileEntry = _addImageFileEntry(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _layout.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			false, Collections.emptyMap(), fileEntry.getFileEntryId(), false,
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()));

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(
			document, "og:image",
			_dlurlHelper.getImagePreviewURL(fileEntry, _getThemeDisplay()));
	}

	@Test
	public void testIncludeLink() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_dynamicInclude.include(
			_getHttpServletRequest(), mockHttpServletResponse,
			RandomTestUtil.randomString());

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertCanonicalLinkTag(
			document,
			PortalUtil.getCanonicalURL("", _getThemeDisplay(), _layout));
		_assertAlternateLinkTag(document, _group.getAvailableLanguageIds());
	}

	private FileEntry _addImageFileEntry(ServiceContext serviceContext)
		throws Exception {

		return _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.IMAGE_JPEG,
			FileUtil.getBytes(getClass(), "image.jpg"), serviceContext);
	}

	private void _assertAlternateLinkTag(
		Document document, String[] languageIds) {

		Elements elements = document.select("link[rel='alternate']");

		Assert.assertNotNull(elements);

		for (String languageId : languageIds) {
			Elements element = elements.select(
				"[hrefLang='" + LocaleUtil.toW3cLanguageId(languageId) + "']");

			Assert.assertEquals(1, element.size());
		}
	}

	private void _assertCanonicalLinkTag(Document document, String href) {
		Elements elements = document.select("link[rel='canonical']");

		Assert.assertNotNull(elements);
		Assert.assertEquals(1, elements.size());

		Element element = elements.get(0);

		Assert.assertEquals(href, element.attr("href"));
	}

	private void _assertMetaTag(
		Document document, String property, String content) {

		Elements elements = document.select(
			"meta[property='" + property + "']");

		Assert.assertNotNull(elements);
		Assert.assertEquals(1, elements.size());

		Element element = elements.get(0);

		Assert.assertEquals(content, element.attr("content"));
	}

	private HttpServletRequest _getHttpServletRequest() throws PortalException {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());
		mockHttpServletRequest.setRequestURI(
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() + _layout.getFriendlyURL());

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		themeDisplay.setLanguageId(_group.getDefaultLanguageId());
		themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()));
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(
			_layoutSetLocalService.getLayoutSet(_group.getGroupId(), false));
		themeDisplay.setPortalURL(company.getPortalURL(_group.getGroupId()));
		themeDisplay.setPortalDomain("localhost");
		themeDisplay.setServerName("localhost");
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(_group.getGroupId());

		return themeDisplay;
	}

	private void _testWithLayoutSEOCompanyConfiguration(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_LAYOUT_SEO_CONFIGURATION_PID,
					new HashMapDictionary<String, Object>() {
						{
							put("enableOpenGraph", true);
						}
					})) {

			unsafeRunnable.run();
		}
	}

	private static final String _LAYOUT_SEO_CONFIGURATION_PID =
		"com.liferay.layout.seo.internal.configuration." +
			"LayoutSEOCompanyConfiguration";

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLURLHelper _dlurlHelper;

	@Inject(
		filter = "component.name=com.liferay.layout.seo.web.internal.servlet.taglib.OpenGraphTopHeadDynamicInclude"
	)
	private DynamicInclude _dynamicInclude;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

}