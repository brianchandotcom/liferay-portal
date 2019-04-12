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
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
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
			FragmentEntryLinkLocalServiceUtil.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString("fragment_entry_link_editable_values.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink));
	}

	@Test
	public void testFragmentEntryProcessorEditableCompatibility()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_compatibility.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink));
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithDuplicateIds()
		throws Exception {

		_addFragmentEntry("fragment_entry_with_duplicate_editable_ids.html");
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithInvalidTypeAttribute()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_invalid_editable_type_attribute.html");
	}

	@Test
	public void testFragmentEntryProcessorEditableWithMatchedDefaultExperienceAndDefaultLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_matching_default_" +
					"segments_experience_and_default_language.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, FragmentEntryLinkConstants.EDIT,
				LocaleUtil.CHINESE, new long[] {2L, 0L}));
	}

	@Test
	public void testFragmentEntryProcessorEditableWithMatchedExperienceAndDefaultLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_matching_segments_" +
					"experience_and_default_language.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, FragmentEntryLinkConstants.EDIT,
				LocaleUtil.CHINESE, new long[] {1L, 0L}));
	}

	@Test
	public void testFragmentEntryProcessorEditableWithMatchedExperienceAndLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_matching_segments_" +
					"experience_and_language.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, FragmentEntryLinkConstants.EDIT,
				LocaleUtil.US, new long[] {1L, 0L}));
	}

	@Test
	public void testFragmentEntryProcessorEditableWithMatchedLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_matching_language.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, FragmentEntryLinkConstants.EDIT,
				LocaleUtil.US, new long[] {0L}));
	}

	@Test
	public void testFragmentEntryProcessorEditableWithMatchedLanguageCompatibility()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_matching_language_" +
					"compatibility.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, FragmentEntryLinkConstants.EDIT,
				LocaleUtil.US));
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithMissingAttributes()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_missing_editable_attributes.html");
	}

	@Test
	public void testFragmentEntryProcessorEditableWithUnmatchedLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_unmatching_language." +
					"json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, FragmentEntryLinkConstants.EDIT,
				LocaleUtil.CHINESE));
	}

	@Test
	public void testFragmentEntryProcessorEditableWithUnmatchedLanguageCompatibility()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_unmatching_language_" +
					"compatibility.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, FragmentEntryLinkConstants.EDIT,
				LocaleUtil.CHINESE));
	}

	private FragmentEntry _addFragmentEntry(String htmlFile)
		throws IOException, PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		return FragmentEntryServiceUtil.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"Fragment Entry", null, _getFileAsString(htmlFile), null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private String _getFileAsString(String fileName) throws IOException {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/fragment/entry/processor/editable/test/dependencies/" +
				fileName);
	}

	private String _getJsonFileAsString(String jsonFileName)
		throws IOException, JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_getFileAsString(jsonFileName));

		return jsonObject.toString();
	}

	private String _getProcessedHTML() throws IOException {
		Document document = Jsoup.parseBodyFragment(
			_getFileAsString("processed_fragment_entry.html"));

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
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@DeleteAfterTestRun
	private Group _group;

	private Locale _originalSiteDefaultLocale;
	private Locale _originalThemeDisplayDefaultLocale;
	private String _processedHTML;

}