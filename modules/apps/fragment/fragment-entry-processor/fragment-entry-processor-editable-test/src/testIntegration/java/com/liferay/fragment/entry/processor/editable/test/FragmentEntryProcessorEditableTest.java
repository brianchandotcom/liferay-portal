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
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.editable.test.constants.FragmentEntryLinkPortletKeys;
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
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.Portlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hamcrest.CoreMatchers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

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

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_layout = _addLayout(_group.getGroupId());

		_processedHTML = _getProcessedHTML("processed_fragment_entry.html");

		_originalSiteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();

		_originalThemeDisplayDefaultLocale =
			LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.US);

		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);

		ServiceContextThreadLocal.pushServiceContext(
			new MockServiceContext(_layout, _getThemeDisplay()));
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setSiteDefaultLocale(_originalSiteDefaultLocale);
		LocaleThreadLocal.setThemeDisplayLocale(
			_originalThemeDisplayDefaultLocale);

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testCanAddOneNoninstanceableWidget() throws Exception {
		_addFragmentEntry(
			"fragment_entry_with_noninstanceable_widget_tag.html");
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testCannotAddMoreThanOneNoninstanceableWidget()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_duplicate_noninstanceable_widget_tag.html");
	}

	@Test
	public void testFragmentEntryLinkPortletPreferences() throws Exception {
		FragmentEntry fragmentEntry = _addFragmentEntry(
			"fragment_entry_with_instanceable_widget_tag.html");

		ServiceContext serviceContext = new MockServiceContext(
			_layout, _getThemeDisplay());

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), 0, _layout.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, 0, null, serviceContext);

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid());

		List<PortletPreferences> filteredPortletPreferencesList =
			ListUtil.filter(
				portletPreferencesList,
				portletPreferences -> {
					String portletId = portletPreferences.getPortletId();

					return portletId.startsWith(
						FragmentEntryLinkPortletKeys.
							FRAGMENT_ENTRY_LINK_INSTANCEABLE_TEST_PORTLET);
				});

		Assert.assertEquals(
			filteredPortletPreferencesList.toString(), 1,
			filteredPortletPreferencesList.size());

		PortletPreferences portletPreferences = portletPreferencesList.get(0);

		String instanceId = PortletIdCodec.decodeInstanceId(
			portletPreferences.getPortletId());

		Assert.assertEquals(
			fragmentEntryLink.getNamespace() + "widget", instanceId);
	}

	@Test
	public void testFragmentEntryProcessorEditable() throws Exception {
		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_readJSONFileToString("fragment_entry_link_editable_values.json"));

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
				fragmentEntry.getFragmentEntryId(), 0,
				TestPropsValues.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, 0, null,
				ServiceContextTestUtil.getServiceContext());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		String editableValues = _readJSONFileToString(
			"fragment_entry_link_mapped_asset_field.json");

		editableValues = StringUtil.replace(
			editableValues, new String[] {"CLASS_NAME_ID", "CLASS_PK"},
			new String[] {
				String.valueOf(_portal.getClassNameId(JournalArticle.class)),
				String.valueOf(journalArticle.getResourcePrimKey())
			});

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

	@Test
	public void testFragmentEntryProcessorEditableMappedDLImageBackgroundImageFileEntryId()
		throws Exception {

		FileEntry fileEntry = _addImageFileEntry();

		String editableValues = _getEditableFieldValues(
			_portal.getClassNameId(FileEntry.class), fileEntry.getFileEntryId(),
			"fileURL",
			"fragment_entry_link_mapped_asset_field_background_image.json");

		Element element = _getElement(
			"data-lfr-background-image-id", "background-image", editableValues,
			"fragment_entry_background_image.html");

		String style = element.attr("style");

		Assert.assertTrue(
			style.contains(
				"--background-image-file-entry-id: " +
					fileEntry.getFileEntryId() + ";"));
	}

	@Test
	public void testFragmentEntryProcessorEditableMappedDLImageFileEntryId()
		throws Exception {

		FileEntry fileEntry = _addImageFileEntry();

		String editableValues = _getEditableFieldValues(
			_portal.getClassNameId(FileEntry.class), fileEntry.getFileEntryId(),
			"fileURL", "fragment_entry_link_mapped_asset_field_image.json");

		Element element = _getElement(
			"data-lfr-editable-id", "image-square", editableValues,
			"fragment_entry_image.html");

		Assert.assertEquals(
			fileEntry.getFileEntryId(),
			GetterUtil.getLong(element.attr("data-fileentryid")));
	}

	@Test
	public void testFragmentEntryProcessorEditableMappedJournalArticleBackgroundImageFileEntryId()
		throws Exception {

		FileEntry fileEntry = _addImageFileEntry();

		String editableValues = _getJournalArticleEditableFieldValues(
			"fragment_entry_link_mapped_asset_field_background_image.json",
			"fileURL", fileEntry);

		Element element = _getElement(
			"data-lfr-background-image-id", "background-image", editableValues,
			"fragment_entry_background_image.html");

		String style = element.attr("style");

		Assert.assertTrue(
			style.contains(
				"--background-image-file-entry-id: " +
					fileEntry.getFileEntryId() + ";"));
	}

	@Test
	public void testFragmentEntryProcessorEditableMappedJournalArticleImageFileEntryId()
		throws Exception {

		FileEntry fileEntry = _addImageFileEntry();

		String editableValues = _getJournalArticleEditableFieldValues(
			"fragment_entry_link_mapped_asset_field_image.json",
			"ImageFieldName", fileEntry);

		Element element = _getElement(
			"data-lfr-editable-id", "image-square", editableValues,
			"fragment_entry_image.html");

		Assert.assertEquals(
			fileEntry.getFileEntryId(),
			GetterUtil.getLong(element.attr("data-fileentryid")));
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
			_readJSONFileToString(
				"fragment_entry_link_editable_values_empty_string.json"));

		Assert.assertEquals(
			_getProcessedHTML("processed_fragment_entry_empty_string.html"),
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(LocaleUtil.US)));
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithInvalidTypeAttribute()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_invalid_editable_type_attribute.html");
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithInvalidTypeAttributeInImageTag()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_invalid_editable_type_attribute_in_image_" +
				"tag.html");
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithInvalidTypeAttributeInLinkTag()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_invalid_editable_type_attribute_in_link_tag." +
				"html");
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithInvalidTypeAttributeInTextTag()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_invalid_editable_type_attribute_in_text_tag." +
				"html");
	}

	@Test
	public void testFragmentEntryProcessorEditableWithLocalizableImageAlt()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_readJSONFileToString(
				"fragment_entry_link_editable_values_localizable_image_alt." +
					"json"));

		Assert.assertThat(
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(LocaleUtil.US)),
			CoreMatchers.containsString("en_US-alt"));

		Locale currentLocale = LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setThemeDisplayLocale(
			LocaleUtil.fromLanguageId("es_ES"));

		Assert.assertThat(
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(
					LocaleUtil.fromLanguageId("es_ES"))),
			CoreMatchers.containsString("es_ES-alt"));

		LocaleThreadLocal.setThemeDisplayLocale(currentLocale);
	}

	@Test
	public void testFragmentEntryProcessorEditableWithMatchedLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_readJSONFileToString(
				"fragment_entry_link_editable_values_matching_language.json"));

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
	public void testFragmentEntryProcessorEditableWithUnmatchedLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_readJSONFileToString(
				"fragment_entry_link_editable_values_unmatching_language." +
					"json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(LocaleUtil.CHINESE)));
	}

	private DDMStructure _addDDMStructure(Group group, String content)
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				_portal.getClassNameId(JournalArticle.class), group);

		return ddmStructureTestHelper.addStructure(
			_portal.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_deserialize(content), StorageType.DEFAULT.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);
	}

	private FragmentEntry _addFragmentEntry(String htmlFile) throws Exception {
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
			_readFileToString(htmlFile), null, null, 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	private FileEntry _addImageFileEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		byte[] bytes = FileUtil.getBytes(
			FragmentEntryProcessorEditableTest.class,
			"/com/liferay/fragment/entry/processor/editable/test/dependencies" +
				"/image.jpg");

		InputStream inputStream = new UnsyncByteArrayInputStream(bytes);

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(_group.getGroupId());

		return localRepository.addFileEntry(
			null, TestPropsValues.getUserId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			inputStream, bytes.length, null, null, serviceContext);
	}

	private JournalArticle _addJournalArticle(
			DDMStructure ddmStructure, String fieldId, FileEntry fileEntry)
		throws Exception {

		User user = TestPropsValues.getUser();

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String dynamicContent = _readJSONFileToString("dynamic_content.json");

		dynamicContent = StringUtil.replace(
			dynamicContent,
			new String[] {
				"FILE_ENTRY_ID", "GROUP_ID", "RESOURCE_PRIM_KEY", "UUID"
			},
			new String[] {
				String.valueOf(fileEntry.getFileEntryId()),
				String.valueOf(fileEntry.getGroupId()),
				String.valueOf(fileEntry.getPrimaryKey()),
				String.valueOf(fileEntry.getUuid())
			});

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			_portal.getClassNameId(JournalArticle.class));

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

		return _journalArticleLocalService.addArticle(
			user.getUserId(), _group.getGroupId(), 0,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT,
			HashMapBuilder.put(
				defaultLocale, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				defaultLocale, defaultLocale.toString()
			).build(),
			_getStructuredContent(
				fieldId,
				Collections.singletonList(
					HashMapBuilder.put(
						defaultLocale, dynamicContent
					).build()),
				LocaleUtil.toLanguageId(defaultLocale)),
			ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey(), null,
			displayCalendar.get(Calendar.MONTH),
			displayCalendar.get(Calendar.DATE),
			displayCalendar.get(Calendar.YEAR),
			displayCalendar.get(Calendar.HOUR_OF_DAY),
			displayCalendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0, 0,
			0, 0, true, true, false, null, null, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private Layout _addLayout(long groupId) throws Exception {
		String name = RandomTestUtil.randomString();

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

		return _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), groupId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, null,
			RandomTestUtil.randomString(), LayoutConstants.TYPE_PORTLET, false,
			friendlyURL, ServiceContextTestUtil.getServiceContext());
	}

	private com.liferay.portal.kernel.xml.Document _createDocumentContent(
		String availableLocales, String defaultLocale) {

		com.liferay.portal.kernel.xml.Document document =
			SAXReaderUtil.createDocument();

		com.liferay.portal.kernel.xml.Element rootElement = document.addElement(
			"root");

		rootElement.addAttribute("available-locales", availableLocales);
		rootElement.addAttribute("default-locale", defaultLocale);
		rootElement.addElement("request");

		return document;
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	private String _getEditableFieldValues(
			long classNameId, long classPK, String fieldId, String fileName)
		throws Exception {

		String editableValues = _readJSONFileToString(fileName);

		return StringUtil.replace(
			editableValues,
			new String[] {"CLASS_NAME_ID", "CLASS_PK", "FIELD_ID"},
			new String[] {
				String.valueOf(classNameId), String.valueOf(classPK), fieldId
			});
	}

	private Element _getElement(
			String dataAttributeName, String editableId, String editableValues,
			String htmlFileName)
		throws Exception {

		FragmentEntry fragmentEntry = _addFragmentEntry(htmlFileName);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), 0,
				TestPropsValues.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				StringPool.BLANK, editableValues, StringPool.BLANK, 0, null,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		String processedFragmentEntryLinkHTML =
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(LocaleUtil.getSiteDefault()));

		Document document = _getDocument(processedFragmentEntryLinkHTML);

		Element body = document.body();

		Elements elements = body.getElementsByAttributeValue(
			dataAttributeName, editableId);

		return elements.get(0);
	}

	private FragmentEntryProcessorContext _getFragmentEntryProcessorContext()
		throws Exception {

		return _getFragmentEntryProcessorContext(
			LocaleUtil.getMostRelevantLocale());
	}

	private FragmentEntryProcessorContext _getFragmentEntryProcessorContext(
			Locale locale)
		throws Exception {

		return new DefaultFragmentEntryProcessorContext(
			_getHttpServletRequest(), null, FragmentEntryLinkConstants.EDIT,
			locale);
	}

	private HttpServletRequest _getHttpServletRequest() throws Exception {
		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletRenderResponse());

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());

		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setResponse(new MockHttpServletResponse());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return httpServletRequest;
	}

	private String _getJournalArticleEditableFieldValues(
			String editableValuesFileName, String fieldId, FileEntry fileEntry)
		throws Exception {

		String ddmStructureContent = _readJSONFileToString(
			"ddm_structure.json");

		ddmStructureContent = StringUtil.replace(
			ddmStructureContent, "FIELD_NAME", fieldId);

		DDMStructure ddmStructure = _addDDMStructure(
			_group, ddmStructureContent);

		JournalArticle journalArticle = _addJournalArticle(
			ddmStructure, fieldId, fileEntry);

		return _getEditableFieldValues(
			_portal.getClassNameId(JournalArticle.class),
			journalArticle.getResourcePrimKey(), fieldId,
			editableValuesFileName);
	}

	private String _getProcessedHTML(String fileName) throws Exception {
		Document document = Jsoup.parseBodyFragment(
			_readFileToString(fileName));

		document.outputSettings(
			new Document.OutputSettings() {
				{
					prettyPrint(false);
				}
			});

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	private String _getStructuredContent(
		String name, List<Map<Locale, String>> contents, String defaultLocale) {

		StringBundler sb = new StringBundler();

		for (Map<Locale, String> map : contents) {
			for (Locale locale : map.keySet()) {
				sb.append(LocaleUtil.toLanguageId(locale));
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);
		}

		com.liferay.portal.kernel.xml.Document document =
			_createDocumentContent(sb.toString(), defaultLocale);

		com.liferay.portal.kernel.xml.Element rootElement =
			document.getRootElement();

		for (Map<Locale, String> map : contents) {
			com.liferay.portal.kernel.xml.Element dynamicElementElement =
				rootElement.addElement("dynamic-element");

			dynamicElementElement.addAttribute("index-type", "keyword");
			dynamicElementElement.addAttribute("name", name);
			dynamicElementElement.addAttribute("type", "image");

			for (Map.Entry<Locale, String> entry : map.entrySet()) {
				com.liferay.portal.kernel.xml.Element element =
					dynamicElementElement.addElement("dynamic-content");

				element.addAttribute(
					"language-id", LocaleUtil.toLanguageId(entry.getKey()));
				element.addCDATA(entry.getValue());
			}
		}

		return document.asXML();
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			_group.getGroupId(), false);

		themeDisplay.setLayoutSet(layoutSet);

		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());

		Theme theme = _themeLocalService.getTheme(
			_company.getCompanyId(), layoutSet.getThemeId());

		themeDisplay.setLookAndFeel(theme, null);

		themeDisplay.setRequest(_getHttpServletRequest());
		themeDisplay.setResponse(new MockHttpServletResponse());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private String _readFileToString(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/fragment/entry/processor/editable/test/dependencies/" +
				fileName);
	}

	private String _readJSONFileToString(String jsonFileName) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_readFileToString(jsonFileName));

		return jsonObject.toString();
	}

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@Inject(filter = "ddm.form.deserializer.type=json")
	private static DDMFormDeserializer _jsonDDMFormDeserializer;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

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

	@Inject(
		filter = "javax.portlet.name=" + FragmentEntryLinkPortletKeys.FRAGMENT_ENTRY_LINK_INSTANCEABLE_TEST_PORTLET
	)
	private final Portlet _instanceablePortlet = null;

	private Layout _layout;

	@Inject
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject(
		filter = "javax.portlet.name=" + FragmentEntryLinkPortletKeys.FRAGMENT_ENTRY_LINK_NONINSTANCEABLE_TEST_PORTLET
	)
	private final Portlet _noninstanceablePortlet = null;

	private Locale _originalSiteDefaultLocale;
	private Locale _originalThemeDisplayDefaultLocale;

	@Inject
	private Portal _portal;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	private String _processedHTML;

	@Inject
	private ThemeLocalService _themeLocalService;

	private static class MockServiceContext extends ServiceContext {

		public MockServiceContext(Layout layout, ThemeDisplay themeDisplay) {
			_layout = layout;
			_themeDisplay = themeDisplay;
		}

		@Override
		public HttpServletRequest getRequest() {
			HttpServletRequest httpServletRequest =
				new MockHttpServletRequest();

			httpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);
			httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, _themeDisplay);

			return httpServletRequest;
		}

		@Override
		public HttpServletResponse getResponse() {
			return new MockHttpServletResponse();
		}

		@Override
		public ThemeDisplay getThemeDisplay() {
			return _themeDisplay;
		}

		private final Layout _layout;
		private final ThemeDisplay _themeDisplay;

	}

}