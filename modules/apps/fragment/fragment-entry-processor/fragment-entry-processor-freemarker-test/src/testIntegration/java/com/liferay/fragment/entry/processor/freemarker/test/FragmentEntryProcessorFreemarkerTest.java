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

package com.liferay.fragment.entry.processor.freemarker.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class FragmentEntryProcessorFreemarkerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testProcessFragmentEntryLinkHTML() throws Exception {
		FragmentEntry fragmentEntry = _addFragmentEntry(
			"fragment_entry.html", null);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					_getMockHttpServletRequest(), new MockHttpServletResponse(),
					null, null);

		String actualProcessedHTML = _getProcessedHTML(
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, defaultFragmentEntryProcessorContext));

		String expectedProcessedHTML = _getProcessedHTML(
			_getFileAsString("expected_processed_fragment_entry.html"));

		Assert.assertEquals(expectedProcessedHTML, actualProcessedHTML);
	}

	@Test
	public void testProcessFragmentEntryLinkHTMLWithConfiguration()
		throws Exception {

		FragmentEntry fragmentEntry = _addFragmentEntry(
			"fragment_entry_with_configuration.html", "configuration.json");

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());
		fragmentEntryLink.setConfiguration(fragmentEntry.getConfiguration());
		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_with_configuration.json"));

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					_getMockHttpServletRequest(), new MockHttpServletResponse(),
					null, null);

		String actualProcessedHTML = _getProcessedHTML(
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, defaultFragmentEntryProcessorContext));

		String expectedProcessedHTML = _getProcessedHTML(
			_getFileAsString(
				"expected_processed_fragment_entry_with_configuration.html"));

		Assert.assertEquals(expectedProcessedHTML, actualProcessedHTML);
	}

	@Test
	public void testProcessFragmentEntryLinkHTMLWithConfigurationItemSelectorFileEntry()
		throws Exception {

		String fileName = RandomTestUtil.randomString() + ".jpg";

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			ContentTypes.IMAGE_JPEG,
			FileUtil.getBytes(
				FragmentEntryProcessorFreemarkerTest.class,
				"dependencies/image.jpg"),
			new ServiceContext());

		Map<String, String> configurationDefaultValues = HashMapBuilder.put(
			"className", FileEntry.class.getName()
		).put(
			"classNameId",
			String.valueOf(
				_classNameLocalService.getClassNameId(
					fileEntry.getModelClassName()))
		).put(
			"classPK", String.valueOf(fileEntry.getPrimaryKey())
		).build();

		FragmentEntry fragmentEntry = _addFragmentEntry(
			"fragment_entry_with_configuration_itemselector_file_entry.html",
			"configuration_itemselector.json", configurationDefaultValues);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());
		fragmentEntryLink.setConfiguration(fragmentEntry.getConfiguration());
		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_with_configuration_" +
					"itemselector.json"));

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					_getMockHttpServletRequest(), new MockHttpServletResponse(),
					null, null);

		String actualProcessedHTML = _getProcessedHTML(
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, defaultFragmentEntryProcessorContext));

		Map<String, String> expectedValues = HashMapBuilder.put(
			"className", FileEntry.class.getName()
		).put(
			"classNameId",
			String.valueOf(
				_classNameLocalService.getClassNameId(
					fileEntry.getModelClassName()))
		).put(
			"classPK", String.valueOf(fileEntry.getPrimaryKey())
		).put(
			"fileName", fileName
		).build();

		String expectedProcessedHTML = _getProcessedHTML(
			_getFileAsString(
				"expected_processed_fragment_entry_with_configuration_" +
					"itemselector_file_entry.html",
				expectedValues));

		Assert.assertEquals(expectedProcessedHTML, actualProcessedHTML);
	}

	@Test
	public void testProcessFragmentEntryLinkHTMLWithConfigurationItemSelectorJournalArticle()
		throws Exception {

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "t1-es"
		).put(
			LocaleUtil.US, "t1"
		).build();

		Map<Locale, String> contentMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, "c1-es"
		).put(
			LocaleUtil.US, "c1"
		).build();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class), titleMap, null,
			contentMap, LocaleUtil.getSiteDefault(), false, true,
			serviceContext);

		Map<String, String> configurationDefaultValues = HashMapBuilder.put(
			"className", journalArticle.getModelClassName()
		).put(
			"classNameId", String.valueOf(journalArticle.getClassNameId())
		).put(
			"classPK", String.valueOf(journalArticle.getResourcePrimKey())
		).build();

		FragmentEntry fragmentEntry = _addFragmentEntry(
			"fragment_entry_with_configuration_itemselector_journal_article." +
				"html",
			"configuration_itemselector.json", configurationDefaultValues);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());
		fragmentEntryLink.setConfiguration(fragmentEntry.getConfiguration());
		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_with_configuration_" +
					"itemselector.json"));

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					_getMockHttpServletRequest(), new MockHttpServletResponse(),
					null, null);

		String actualProcessedHTML = _getProcessedHTML(
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, defaultFragmentEntryProcessorContext));

		Map<String, String> expectedValues = HashMapBuilder.put(
			"classNameId", String.valueOf(journalArticle.getClassNameId())
		).put(
			"classPK", String.valueOf(journalArticle.getResourcePrimKey())
		).put(
			"contentES", "c1-es"
		).put(
			"contentUS", "c1"
		).put(
			"titleES", "t1-es"
		).put(
			"titleUS", "t1"
		).build();

		String expectedProcessedHTML = _getProcessedHTML(
			_getFileAsString(
				"expected_processed_fragment_entry_with_configuration_" +
					"itemselector_journal_article.html",
				expectedValues));

		Assert.assertEquals(expectedProcessedHTML, actualProcessedHTML);
	}

	@Test
	public void testProcessFragmentEntryLinkHTMLWithConfigurationItemSelectorJournalArticleNondefaultSegmentId()
		throws Exception {

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, RandomTestUtil.randomString(10)
		).put(
			LocaleUtil.US, RandomTestUtil.randomString(10)
		).build();

		Map<Locale, String> contentMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, RandomTestUtil.randomString(10)
		).put(
			LocaleUtil.US, RandomTestUtil.randomString(10)
		).build();

		Map<Locale, String> titleMap2 = HashMapBuilder.put(
			LocaleUtil.SPAIN, "t2-es"
		).put(
			LocaleUtil.US, "t2"
		).build();

		Map<Locale, String> contentMap2 = HashMapBuilder.put(
			LocaleUtil.SPAIN, "c2-es"
		).put(
			LocaleUtil.US, "c2"
		).build();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class), titleMap, null,
			contentMap, LocaleUtil.getSiteDefault(), false, true,
			serviceContext);

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class), titleMap2, null,
			contentMap2, LocaleUtil.getSiteDefault(), false, true,
			serviceContext);

		Map<String, String> configurationDefaultValues = HashMapBuilder.put(
			"classNameId", String.valueOf(journalArticle1.getClassNameId())
		).put(
			"classPK", String.valueOf(journalArticle1.getResourcePrimKey())
		).build();

		Map<String, String> editableValuesValues = HashMapBuilder.put(
			"classNameId", String.valueOf(journalArticle2.getClassNameId())
		).put(
			"classPK", String.valueOf(journalArticle2.getResourcePrimKey())
		).build();

		FragmentEntry fragmentEntry = _addFragmentEntry(
			"fragment_entry_with_configuration_itemselector_journal_article." +
				"html",
			"configuration_itemselector.json", configurationDefaultValues);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());
		fragmentEntryLink.setConfiguration(fragmentEntry.getConfiguration());
		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_with_configuration_" +
					"itemselector_journal_article_nondefault_segment_id.json",
				editableValuesValues));

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					_getMockHttpServletRequest(), new MockHttpServletResponse(),
					null, null);

		defaultFragmentEntryProcessorContext.setSegmentsExperienceIds(
			new long[] {1});

		String actualProcessedHTML = _getProcessedHTML(
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, defaultFragmentEntryProcessorContext));

		Map<String, String> expectedValues = HashMapBuilder.put(
			"classNameId", String.valueOf(journalArticle2.getClassNameId())
		).put(
			"classPK", String.valueOf(journalArticle2.getResourcePrimKey())
		).put(
			"contentES", "c2-es"
		).put(
			"contentUS", "c2"
		).put(
			"titleES", "t2-es"
		).put(
			"titleUS", "t2"
		).build();

		String expectedProcessedHTML = _getProcessedHTML(
			_getFileAsString(
				"expected_processed_fragment_entry_with_configuration_" +
					"itemselector_journal_article.html",
				expectedValues));

		Assert.assertEquals(expectedProcessedHTML, actualProcessedHTML);
	}

	@Test
	public void testProcessFragmentEntryLinkHTMLWithConfigurationNondefaultSegmentId()
		throws Exception {

		FragmentEntry fragmentEntry = _addFragmentEntry(
			"fragment_entry_with_configuration.html", "configuration.json");

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());
		fragmentEntryLink.setConfiguration(fragmentEntry.getConfiguration());
		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_with_configuration_" +
					"nondefault_segment_id.json"));

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					_getMockHttpServletRequest(), new MockHttpServletResponse(),
					null, null);

		defaultFragmentEntryProcessorContext.setSegmentsExperienceIds(
			new long[] {1});

		String actualProcessedHTML = _getProcessedHTML(
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, defaultFragmentEntryProcessorContext));

		String expectedProcessedHTML = _getProcessedHTML(
			_getFileAsString(
				"expected_processed_fragment_entry_with_configuration_" +
					"nondefault_segment_id.html"));

		Assert.assertEquals(expectedProcessedHTML, actualProcessedHTML);
	}

	@Test
	public void testProcessFragmentEntryLinkHTMLWithInvalidFreemarker()
		throws Exception {

		FragmentEntry fragmentEntry = _addFragmentEntry(
			"fragment_entry_invalid_freemarker.html", null);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					_getMockHttpServletRequest(), new MockHttpServletResponse(),
					null, null);

		expectedException.expect(FragmentEntryContentException.class);
		expectedException.expectMessage("FreeMarker syntax is invalid");
		expectedException.expectMessage(
			"Syntax error in template \"template_id\" in line 5, column 12");

		_getProcessedHTML(
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, defaultFragmentEntryProcessorContext));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private FragmentEntry _addFragmentEntry(
			String htmlFile, String configurationFile)
		throws IOException, PortalException {

		return _addFragmentEntry(htmlFile, configurationFile, null);
	}

	private FragmentEntry _addFragmentEntry(
			String htmlFile, String configurationFile,
			Map<String, String> values)
		throws IOException, PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		String configuration = null;

		if (configurationFile != null) {
			configuration = _getFileAsString(configurationFile);

			configuration = StringUtil.replace(
				configuration, "${", "}", values);
		}

		return _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"fragment-entry", "Fragment Entry", null,
			_getFileAsString(htmlFile), null, configuration, 0, 0,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private String _getFileAsString(String fileName) throws IOException {
		return _getFileAsString(fileName, null);
	}

	private String _getFileAsString(String fileName, Map<String, String> values)
		throws IOException {

		Class<?> clazz = getClass();

		String template = StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/fragment/entry/processor/freemarker/test" +
				"/dependencies/" + fileName);

		return StringUtil.replace(template, "${", "}", values);
	}

	private String _getJsonFileAsString(String jsonFileName)
		throws IOException, JSONException {

		return _getJsonFileAsString(jsonFileName, null);
	}

	private String _getJsonFileAsString(
			String jsonFileName, Map<String, String> values)
		throws IOException, JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_getFileAsString(jsonFileName, values));

		return jsonObject.toString();
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockHttpServletRequest;
	}

	private String _getProcessedHTML(String bodyHtml) {
		String processedHTML = bodyHtml;

		for (int i = 1; i <= 2; i++) {
			Document document = Jsoup.parseBodyFragment(processedHTML);

			document.outputSettings(
				new Document.OutputSettings() {
					{
						prettyPrint(true);
					}
				});

			Element bodyElement = document.body();

			processedHTML = bodyElement.html();
		}

		return processedHTML;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			_group.getGroupId(), false);

		Theme theme = _themeLocalService.getTheme(
			_company.getCompanyId(), layoutSet.getThemeId());

		themeDisplay.setLookAndFeel(theme, null);

		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Inject
	private FragmentEntryService _fragmentEntryService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Layout _layout;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private ThemeLocalService _themeLocalService;

}