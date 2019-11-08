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

package com.liferay.fragment.entry.processor.editable.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.IOException;

import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class FragmentEntryProcessorEditableTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_processedHTML = _getProcessedHTML();

		_originalSiteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();

		_originalThemeDisplayDefaultLocale =
			LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.US);

		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setSiteDefaultLocale(_originalSiteDefaultLocale);
		LocaleThreadLocal.setThemeDisplayLocale(
			_originalThemeDisplayDefaultLocale);
	}

	@Test
	public void testFragmentEntryProcessorEditable() throws Exception {
		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString("fragment_entry_link_editable_values.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, _getFragmentEntryProcessorContext()));
	}

	@Test
	public void testFragmentEntryProcessorEditableCompatibility()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_compatibility.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, _getFragmentEntryProcessorContext()));
	}

	@Test
	public void testFragmentEntryProcessorEditableMappedAssetField()
		throws Exception {

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				_portal.getClassNameId(Layout.class), TestPropsValues.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, 0, null,
				ServiceContextTestUtil.getServiceContext());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		String editableValues = _getJsonFileAsString(
			"fragment_entry_link_mapped_asset_field.json");

		editableValues = StringUtil.replace(
			editableValues, "CLASS_NAME_ID",
			String.valueOf(_portal.getClassNameId(JournalArticle.class)));

		editableValues = StringUtil.replace(
			editableValues, "CLASS_PK",
			String.valueOf(journalArticle.getResourcePrimKey()));

		_fragmentEntryLinkLocalService.updateFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId(), editableValues);

		int count =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesCount(
					_portal.getClassNameId(JournalArticle.class),
					journalArticle.getResourcePrimKey());

		Assert.assertEquals(1, count);

		_fragmentEntryLinkLocalService.deleteFragmentEntryLink(
			fragmentEntryLink);

		count =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesCount(
					_portal.getClassNameId(JournalArticle.class),
					journalArticle.getResourcePrimKey());

		Assert.assertEquals(0, count);
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithDuplicateIds()
		throws Exception {

		_addFragmentEntry("fragment_entry_with_duplicate_editable_ids.html");
	}

	@Test
	public void testFragmentEntryProcessorEditableWithEmptyString()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_empty_string.json"));

		Assert.assertEquals(
			_getProcessedHTML("processed_fragment_entry_empty_string.html"),
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(
					LocaleUtil.US, new long[] {0L})));
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithInvalidTypeAttribute()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_invalid_editable_type_attribute.html");
	}

	@Test
	public void testFragmentEntryProcessorEditableWithMatchedExperienceAndDefaultLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_matching_segments_" +
					"experience_and_default_language.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(
					LocaleUtil.CHINESE, new long[] {1L, 0L})));
	}

	@Test
	public void testFragmentEntryProcessorEditableWithMatchedExperienceAndLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_matching_segments_" +
					"experience_and_language.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(
					LocaleUtil.US, new long[] {1L, 0L})));
	}

	@Test
	public void testFragmentEntryProcessorEditableWithMatchedLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_matching_language.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(
					LocaleUtil.US, new long[] {0L})));
	}

	@Test
	public void testFragmentEntryProcessorEditableWithMatchedLanguageCompatibility()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_matching_language_" +
					"compatibility.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(LocaleUtil.US)));
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithMissingAttributes()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_missing_editable_attributes.html");
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithNestedEditablesInHTML()
		throws Exception {

		_addFragmentEntry("fragment_entry_with_nested_editable_in_html.html");
	}

	@Test
	public void testFragmentEntryProcessorEditableWithUnmatchedExperience()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_unmatching_segments_" +
					"experience.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(
					LocaleUtil.US, new long[] {2L, 0L})));
	}

	@Test
	public void testFragmentEntryProcessorEditableWithUnmatchedLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_unmatching_language." +
					"json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(LocaleUtil.CHINESE)));
	}

	@Test
	public void testFragmentEntryProcessorEditableWithUnmatchedLanguageCompatibility()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_unmatching_language_" +
					"compatibility.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(LocaleUtil.CHINESE)));
	}

	private FragmentEntry _addFragmentEntry(String htmlFile)
		throws IOException, PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		return _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"fragment-entry", "Fragment Entry", null,
			_getFileAsString(htmlFile), null, null, 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	private String _getFileAsString(String fileName) throws IOException {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/fragment/entry/processor/editable/test/dependencies/" +
				fileName);
	}

	private FragmentEntryProcessorContext _getFragmentEntryProcessorContext() {
		return _getFragmentEntryProcessorContext(
			LocaleUtil.getMostRelevantLocale());
	}

	private FragmentEntryProcessorContext _getFragmentEntryProcessorContext(
		Locale locale) {

		return _getFragmentEntryProcessorContext(locale, new long[0]);
	}

	private FragmentEntryProcessorContext _getFragmentEntryProcessorContext(
		Locale locale, long[] segmentsExperienceIds) {

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					null, null, FragmentEntryLinkConstants.EDIT, locale);

		defaultFragmentEntryProcessorContext.setSegmentsExperienceIds(
			segmentsExperienceIds);

		return defaultFragmentEntryProcessorContext;
	}

	private String _getJsonFileAsString(String jsonFileName)
		throws IOException, JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_getFileAsString(jsonFileName));

		return jsonObject.toString();
	}

	private String _getProcessedHTML() throws IOException {
		return _getFileAsString("processed_fragment_entry.html");
	}

	private String _getProcessedHTML(String fileName) throws IOException {
		Document document = Jsoup.parseBodyFragment(_getFileAsString(fileName));

		document.outputSettings(
			new Document.OutputSettings() {
				{
					prettyPrint(false);
				}
			});

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

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

	@Inject
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	private Locale _originalSiteDefaultLocale;
	private Locale _originalThemeDisplayDefaultLocale;

	@Inject
	private Portal _portal;

	private String _processedHTML;

}