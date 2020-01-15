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
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.layout.seo.service.LayoutSEOSiteLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.nio.charset.StandardCharsets;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

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
	public void testIncludeCustomCanonicalURL() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(
				LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()),
				"http://example.com"),
			true, Collections.emptyMap(), Collections.emptyMap(), 0, false,
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(document, "og:url", "http://example.com");
	}

	@Test
	public void testIncludeCustomDescription() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"), true,
			Collections.singletonMap(LocaleUtil.US, "customDescription"),
			Collections.emptyMap(), 0, false, Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(document, "og:description", "customDescription");
	}

	@Test
	public void testIncludeCustomMetaTags() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAttribute(
			_getDDMStructureId() + "ddmFormValues",
			new String(
				FileUtil.getBytes(
					getClass(),
					"dependencies/custom_meta_tags_ddm_form_values.json"),
				StandardCharsets.UTF_8));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			_layout.getUserId(), _layout.getGroupId(),
			_layout.isPrivateLayout(), _layout.getLayoutId(), false,
			Collections.emptyMap(), false, Collections.emptyMap(),
			Collections.emptyMap(), 0, false, Collections.emptyMap(),
			serviceContext);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_dynamicInclude.include(
			_getHttpServletRequest(), mockHttpServletResponse,
			RandomTestUtil.randomString());

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(document, "property1", "content1");
		_assertMetaTag(document, "property2", "content2");
	}

	@Test
	public void testIncludeCustomTitle() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _layout.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			false, Collections.emptyMap(), Collections.emptyMap(), 0, true,
			Collections.singletonMap(LocaleUtil.US, "customTitle"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

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
				RandomTestUtil.randomString()),
			true);

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
	public void testIncludeImageAltNoImage() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _layout.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			false, Collections.emptyMap(),
			Collections.singletonMap(LocaleUtil.US, "Image alternative text"),
			0, false, Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertNoOpenGraphMetaProperty(document, "og:image:alt");
	}

	@Test
	public void testIncludeIncompleteCustomMetaTags() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAttribute(
			_getDDMStructureId() + "ddmFormValues",
			new String(
				FileUtil.getBytes(
					getClass(),
					"dependencies" +
						"/incomplete_custom_meta_tags_ddm_form_values.json"),
				StandardCharsets.UTF_8));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			_layout.getUserId(), _layout.getGroupId(),
			_layout.isPrivateLayout(), _layout.getLayoutId(), false,
			Collections.emptyMap(), false, Collections.emptyMap(),
			Collections.emptyMap(), 0, false, Collections.emptyMap(),
			serviceContext);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_dynamicInclude.include(
			_getHttpServletRequest(), mockHttpServletResponse,
			RandomTestUtil.randomString());

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(document, "property1", "content1");
		_assertNoOpenGraphMetaProperty(document, "property2");
		_assertNoOpenGraphMetaContent(document, "content3");
	}

	@Test
	public void testIncludeLayoutOpenGraphImage() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		FileEntry layoutOpenGraphImageFileEntry = _addImageFileEntry(
			"image.jpg",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _layout.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			false, Collections.emptyMap(), Collections.emptyMap(),
			layoutOpenGraphImageFileEntry.getFileEntryId(), false,
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(
			document, "og:image",
			_dlurlHelper.getImagePreviewURL(
				layoutOpenGraphImageFileEntry, _getThemeDisplay()));
	}

	@Test
	public void testIncludeLayoutOpenGraphImageNoImageAlt() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		FileEntry layoutOpenGraphImageFileEntry = _addImageFileEntry(
			"image.jpg",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _layout.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			false, Collections.emptyMap(), Collections.emptyMap(),
			layoutOpenGraphImageFileEntry.getFileEntryId(), false,
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(
			document, "og:image",
			_dlurlHelper.getImagePreviewURL(
				layoutOpenGraphImageFileEntry, _getThemeDisplay()));

		_assertNoOpenGraphMetaProperty(document, "og:image:alt");
	}

	@Test
	public void testIncludeLayoutOpenGraphImageWhenBothDefined()
		throws Exception {

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		FileEntry layoutOpenGraphImageFileEntry = _addImageFileEntry(
			"image.jpg",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _layout.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			false, Collections.emptyMap(), Collections.emptyMap(),
			layoutOpenGraphImageFileEntry.getFileEntryId(), false,
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		FileEntry siteOpenGraphImageFileEntry = _addImageFileEntry(
			"image_site.jpg",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOSiteLocalService.updateLayoutSEOSite(
			TestPropsValues.getUserId(), _layout.getGroupId(), true,
			Collections.emptyMap(),
			siteOpenGraphImageFileEntry.getFileEntryId(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(
			document, "og:image",
			_dlurlHelper.getImagePreviewURL(
				layoutOpenGraphImageFileEntry, _getThemeDisplay()));
	}

	@Test
	public void testIncludeLayoutOpenGraphImageWhenBothDefinedLayoutImageAlt()
		throws Exception {

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		FileEntry imageFileEntry = _addImageFileEntry(
			"image.jpg",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _layout.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			false, Collections.emptyMap(),
			Collections.singletonMap(
				LocaleUtil.US, "Layout image alternative text"),
			imageFileEntry.getFileEntryId(), false, Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOSiteLocalService.updateLayoutSEOSite(
			TestPropsValues.getUserId(), _layout.getGroupId(), true,
			Collections.singletonMap(
				LocaleUtil.US, "Site Image alternative text"),
			imageFileEntry.getFileEntryId(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(
			document, "og:image",
			_dlurlHelper.getImagePreviewURL(
				imageFileEntry, _getThemeDisplay()));

		_assertMetaTag(
			document, "og:image:alt", "Layout image alternative text");
	}

	@Test
	public void testIncludeLayoutOpenGraphImageWithImageAlt() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		FileEntry layoutOpenGraphImageFileEntry = _addImageFileEntry(
			"image.jpg",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _layout.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			false, Collections.emptyMap(),
			Collections.singletonMap(LocaleUtil.US, "Image alternative text"),
			layoutOpenGraphImageFileEntry.getFileEntryId(), false,
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(
			document, "og:image",
			_dlurlHelper.getImagePreviewURL(
				layoutOpenGraphImageFileEntry, _getThemeDisplay()));

		_assertMetaTag(document, "og:image:alt", "Image alternative text");
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
		_assertAlternateLinkTag(
			document, _language.getAvailableLocales(_group.getGroupId()));
	}

	@Test
	public void testIncludeLocales() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_dynamicInclude.include(
			_getHttpServletRequest(), mockHttpServletResponse,
			RandomTestUtil.randomString());

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(document, "og:locale", _group.getDefaultLanguageId());
		_assertAlternateLocalesTag(
			document, _language.getAvailableLocales(_group.getGroupId()));
	}

	@Test
	public void testIncludeOpenGraphNotEnabled() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			false);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertLinkElements(document);

		_assertNoOpenGraphMetaTagElements(document);
	}

	@Test
	public void testIncludeSecureURL() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		FileEntry layoutOpenGraphImageFileEntry = _addImageFileEntry(
			"image.jpg",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _layout.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(LocaleUtil.US, "http://example.com"),
			false, Collections.emptyMap(), Collections.emptyMap(),
			layoutOpenGraphImageFileEntry.getFileEntryId(), false,
			Collections.emptyMap(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(
			document, "og:image:secure_url",
			_dlurlHelper.getImagePreviewURL(
				layoutOpenGraphImageFileEntry, _getThemeDisplay()));
	}

	@Test
	public void testIncludeSiteName() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_dynamicInclude.include(
			_getHttpServletRequest(), mockHttpServletResponse,
			RandomTestUtil.randomString());

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(
			document, "og:site_name",
			_group.getDescriptiveName(
				LocaleUtil.fromLanguageId(_group.getDefaultLanguageId())));
	}

	@Test
	public void testIncludeSiteOpenGraphImage() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		FileEntry siteOpenGraphImageFileEntry = _addImageFileEntry(
			"image_site.jpg",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOSiteLocalService.updateLayoutSEOSite(
			TestPropsValues.getUserId(), _layout.getGroupId(), true,
			Collections.emptyMap(),
			siteOpenGraphImageFileEntry.getFileEntryId(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(
			document, "og:image",
			_dlurlHelper.getImagePreviewURL(
				siteOpenGraphImageFileEntry, _getThemeDisplay()));
	}

	@Test
	public void testIncludeSiteOpenGraphImageWhenOpenGraphDisabled()
		throws Exception {

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		FileEntry siteOpenGraphImageFileEntry = _addImageFileEntry(
			"image_site.jpg",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutSEOSiteLocalService.updateLayoutSEOSite(
			TestPropsValues.getUserId(), _layout.getGroupId(), false,
			Collections.emptyMap(),
			siteOpenGraphImageFileEntry.getFileEntryId(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertNoMetaTag(document, "og:image");
	}

	@Test
	public void testIncludeType() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_testWithLayoutSEOCompanyConfiguration(
			() -> _dynamicInclude.include(
				_getHttpServletRequest(), mockHttpServletResponse,
				RandomTestUtil.randomString()),
			true);

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(document, "og:type", "website");
	}

	@Test
	public void testIncludeUrl() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_dynamicInclude.include(
			_getHttpServletRequest(), mockHttpServletResponse,
			RandomTestUtil.randomString());

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertMetaTag(
			document, "og:url",
			PortalUtil.getCanonicalURL("", _getThemeDisplay(), _layout));
	}

	@Test
	public void testPrivateLayout() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_layout.setPrivateLayout(true);

		_dynamicInclude.include(
			_getHttpServletRequest(), mockHttpServletResponse,
			RandomTestUtil.randomString());

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertNoLinkElements(document, "canonical");
		_assertNoLinkElements(document, "alternate");
	}

	@Test
	public void testSignedIn() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_dynamicInclude.include(
			_getSignedInHttpServletRequest(), mockHttpServletResponse,
			RandomTestUtil.randomString());

		Document document = Jsoup.parse(
			mockHttpServletResponse.getContentAsString());

		_assertNoLinkElements(document, "canonical");
		_assertNoLinkElements(document, "alternate");
	}

	private FileEntry _addImageFileEntry(
			String fileName, ServiceContext serviceContext)
		throws Exception {

		return _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.IMAGE_JPEG,
			FileUtil.getBytes(getClass(), "dependencies/" + fileName),
			serviceContext);
	}

	private void _assertAlternateLinkTag(
		Document document, Set<Locale> locales) {

		Elements elements = document.select("link[rel='alternate']");

		Assert.assertNotNull(elements);

		for (Locale locale : locales) {
			Elements element = elements.select(
				"[hrefLang='" + LocaleUtil.toW3cLanguageId(locale) + "']");

			Assert.assertEquals(1, element.size());
		}
	}

	private void _assertAlternateLocalesTag(
		Document document, Set<Locale> locales) {

		Elements elements = document.select(
			"meta[property='og:locale:alternate']");

		Assert.assertNotNull(elements);
		Assert.assertEquals(
			locales.toString(), elements.size(), locales.size());

		for (Locale locale : locales) {
			Elements element = elements.select(
				"[content='" + LocaleUtil.toLanguageId(locale) + "']");

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

	private void _assertLinkElements(Document document) {
		Elements elements = document.select("link[data-senna-track]");

		Assert.assertNotEquals(0, elements.size());
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

	private void _assertNoLinkElements(Document document, String rel) {
		Elements elements = document.select("link[rel='" + rel + "']");

		Assert.assertEquals(0, elements.size());
	}

	private void _assertNoMetaTag(Document document, String property) {
		Elements elements = document.select(
			"meta[property='" + property + "']");

		Assert.assertEquals(0, elements.size());
	}

	private void _assertNoOpenGraphMetaContent(
		Document document, String content) {

		Elements elements = document.select("meta[content='" + content + "']");

		Assert.assertEquals(0, elements.size());
	}

	private void _assertNoOpenGraphMetaProperty(
		Document document, String property) {

		Elements elements = document.select(
			"meta[property='" + property + "']");

		Assert.assertEquals(0, elements.size());
	}

	private void _assertNoOpenGraphMetaTagElements(Document document) {
		Elements elements = document.select("meta[property^='og:']");

		Assert.assertEquals(0, elements.size());
	}

	private long _getDDMStructureId() throws PortalException {
		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			companyGroup.getGroupId(),
			_classNameLocalService.getClassNameId(
				LayoutSEOEntry.class.getName()),
			"custom-meta-tags");

		return ddmStructure.getStructureId();
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

	private HttpServletRequest _getSignedInHttpServletRequest()
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getSingedInThemeDisplay());
		mockHttpServletRequest.setRequestURI(
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() + _layout.getFriendlyURL());

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getSingedInThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		themeDisplay.setSignedIn(true);

		return themeDisplay;
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
		themeDisplay.setSecure(true);
		themeDisplay.setServerName("localhost");
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(_group.getGroupId());

		return themeDisplay;
	}

	private void _testWithLayoutSEOCompanyConfiguration(
			UnsafeRunnable<Exception> unsafeRunnable, boolean enable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_LAYOUT_SEO_CONFIGURATION_PID,
					new HashMapDictionary<String, Object>() {
						{
							put("enableOpenGraph", enable);
						}
					})) {

			unsafeRunnable.run();
		}
	}

	private static final String _LAYOUT_SEO_CONFIGURATION_PID =
		"com.liferay.layout.seo.internal.configuration." +
			"LayoutSEOCompanyConfiguration";

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

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

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Language _language;

	private Layout _layout;

	@Inject
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Inject
	private LayoutSEOSiteLocalService _layoutSEOSiteLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

}