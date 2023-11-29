/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.search.spi.model.index.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class LayoutModelDocumentContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_locale = _portal.getSiteDefaultLocale(_group);

		_languageId = LocaleUtil.toLanguageId(_locale);

		_layoutIndexerFixture = new IndexerFixture<>(Layout.class);

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testReindexPublishedDraftLayoutWithLayoutLocalization()
		throws Exception {

		String elementText = RandomTestUtil.randomString();
		String html =
			"<h1 data-lfr-editable-id=\"element-text\" " +
				"data-lfr-editable-type=\"text\">Heading Example</h1>";

		Layout layout = _addTypeContentLayout(elementText, true);

		_assertReindex(elementText, layout);

		String draftElementText = RandomTestUtil.randomString();

		Layout draftLayout = _addFragmentToLayout(
			draftElementText, html, layout);

		_assertReindexDraftLayout(draftElementText, draftLayout);

		_assertSearch(elementText, layout.getPlid());
	}

	@Test
	public void testReindexPublishedLayout() throws Exception {
		String elementText = RandomTestUtil.randomString();

		Layout layout = _addTypeContentLayout(elementText, true);

		List<LogEntry> logEntries = _reindexLogEntries(layout);

		Assert.assertEquals(logEntries.toString(), 0, logEntries.size());

		_assertSearch(elementText, layout.getPlid());
	}

	@Test
	public void testReindexPublishedLayoutFragmentEntryLinkWithPortlet()
		throws Exception {

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		String html = "<lfr-widget-web-content>";

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLinkToLayout(
			"{}", html, draftLayout,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		String portletId = PortletIdCodec.encode(
			JournalContentPortletKeys.JOURNAL_CONTENT,
			fragmentEntryLink.getNamespace());

		String content = RandomTestUtil.randomString();

		DDMFormField ddmFormField = _createDDMFormField(
			DDMFormFieldTypeConstants.TEXT);

		JournalArticle journalArticle = JournalTestUtil.addJournalArticle(
			_dataDefinitionResourceFactory, ddmFormField,
			_ddmFormValuesToFieldsConverter, content, _group.getGroupId(),
			_journalConverter);

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		_setUpPortletPreferences(
			assetEntry, journalArticle, draftLayout, portletId);

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);

		_assertPortletPreferences(
			assetEntry, journalArticle, layout, portletId);

		_assertReindex(content, layout);
	}

	@Test
	public void testReindexPublishedLayoutWithFragmentEntryLinkTypePortlet()
		throws Exception {

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		String portletId = _addJournalContentPortletToLayout(draftLayout);

		String content = RandomTestUtil.randomString();

		DDMFormField ddmFormField = _createDDMFormField(
			DDMFormFieldTypeConstants.TEXT);

		JournalArticle journalArticle = JournalTestUtil.addJournalArticle(
			_dataDefinitionResourceFactory, ddmFormField,
			_ddmFormValuesToFieldsConverter, content, _group.getGroupId(),
			_journalConverter);

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		_setUpPortletPreferences(
			assetEntry, journalArticle, draftLayout, portletId);

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);

		_assertPortletPreferences(
			assetEntry, journalArticle, layout, portletId);

		_assertReindex(content, layout);
	}

	@Test
	public void testReindexPublishedLayoutWithFreemarkerErrors()
		throws Exception {

		String elementText = RandomTestUtil.randomString();

		Layout layout = _addTypeContentLayout(elementText, true);

		String html =
			"[#if /#] <h1 data-lfr-editable-id=\"element-text\" " +
				"data-lfr-editable-type=\"text\">Heading Example</h1>";

		_addFragmentEntryLinkToLayout(
			JSONUtil.put(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
				JSONUtil.put(
					"element-text", JSONUtil.put(_languageId, elementText))
			).toString(),
			html, layout,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_reindexLogEntries(layout);

		Document document = _layoutIndexerFixture.searchOnlyOne(
			layout.getName(_locale), _locale);

		Assert.assertNotNull(document);

		String content = document.get(
			Field.getLocalizedName(_locale, Field.CONTENT));

		Assert.assertEquals(elementText, content);

		Assert.assertEquals(
			document.get(Field.ENTRY_CLASS_PK),
			String.valueOf(layout.getPlid()));
	}

	@Test
	public void testReindexUnpublishedDraftLayout() throws Exception {
		String elementText = RandomTestUtil.randomString();

		Layout layout = _addTypeContentLayout(elementText, false);

		_assertReindexDraftLayout(elementText, layout);
	}

	private FragmentEntryLink _addFragmentEntryLinkToLayout(
			String editableValues, String html, Layout layout,
			ServiceContext serviceContext)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), null, serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), null,
				RandomTestUtil.randomString(), null, html, null, false, null,
				null, 0, FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		return ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			editableValues, fragmentEntry.getCss(),
			fragmentEntry.getConfiguration(),
			fragmentEntry.getFragmentEntryId(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), layout, fragmentEntry.getFragmentEntryKey(),
			fragmentEntry.getType(), null, 0,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));
	}

	private Layout _addFragmentToLayout(
			String elementText, String html, Layout layout)
		throws Exception {

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		_addFragmentEntryLinkToLayout(
			JSONUtil.put(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
				JSONUtil.put(
					"element-text", JSONUtil.put(_languageId, elementText))
			).toString(),
			html, draftLayout,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		return draftLayout;
	}

	private String _addJournalContentPortletToLayout(Layout layout)
		throws Exception {

		JSONObject processAddPortletJSONObject =
			ContentLayoutTestUtil.addPortletToLayout(
				layout, JournalContentPortletKeys.JOURNAL_CONTENT);

		JSONObject fragmentEntryLinkJSONObject =
			processAddPortletJSONObject.getJSONObject("fragmentEntryLink");

		JSONObject editableValuesJSONObject =
			fragmentEntryLinkJSONObject.getJSONObject("editableValues");

		return PortletIdCodec.encode(
			editableValuesJSONObject.getString("portletId"),
			editableValuesJSONObject.getString("instanceId"));
	}

	private Layout _addTypeContentLayout(String elementText, boolean publish)
		throws Exception {

		String html =
			"<h1 data-lfr-editable-id=\"element-text\" " +
				"data-lfr-editable-type=\"text\">Heading Example</h1>";
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = _addFragmentToLayout(elementText, html, layout);

		if (publish) {
			ContentLayoutTestUtil.publishLayout(draftLayout, layout);

			layout = _layoutLocalService.getLayout(layout.getPlid());
		}

		return layout;
	}

	private void _assertPortletPreferences(
		AssetEntry assetEntry, JournalArticle journalArticle, Layout layout,
		String portletId) {

		PortletPreferences portletPreferences =
			_portletPreferencesFactory.getPortletSetup(layout, portletId, null);

		Assert.assertEquals(
			String.valueOf(journalArticle.getArticleId()),
			portletPreferences.getValue("articleId", null));
		Assert.assertEquals(
			String.valueOf(assetEntry.getEntryId()),
			portletPreferences.getValue("assetEntryId", null));
		Assert.assertEquals(
			String.valueOf(journalArticle.getGroupId()),
			portletPreferences.getValue("groupId", null));
	}

	private void _assertReindex(String expectedContent, Layout layout)
		throws Exception {

		List<LogEntry> logEntries = _reindexLogEntries(layout);

		Assert.assertEquals(logEntries.toString(), 0, logEntries.size());

		_assertSearch(expectedContent, layout.getPlid());
	}

	private void _assertReindexDraftLayout(String keywords, Layout layout)
		throws Exception {

		_layoutIndexerFixture.searchNoOne(keywords);

		List<LogEntry> logEntries = _reindexLogEntries(layout);

		Assert.assertEquals(logEntries.toString(), 0, logEntries.size());

		_layoutIndexerFixture.searchNoOne(keywords);
	}

	private void _assertSearch(String keywords, long plid) {
		Document document = _layoutIndexerFixture.searchOnlyOne(
			keywords, _locale);

		Assert.assertNotNull(document);

		String content = document.get(
			Field.getLocalizedName(_locale, Field.CONTENT));

		Assert.assertTrue(
			content, StringUtil.contains(content, keywords, StringPool.BLANK));

		Assert.assertEquals(
			document.get(Field.ENTRY_CLASS_PK), String.valueOf(plid));
	}

	private DDMFormField _createDDMFormField(String type) {
		DDMFormField ddmFormField = new DDMFormField("name", type);

		ddmFormField.setDataType("text");
		ddmFormField.setIndexType("text");
		ddmFormField.setLocalizable(true);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(
			LocaleUtil.US, RandomTestUtil.randomString(10));

		ddmFormField.setLabel(localizedValue);

		return ddmFormField;
	}

	private List<LogEntry> _reindexLogEntries(Layout layout) throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.DEBUG)) {

			Indexer<Layout> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				Layout.class);

			indexer.reindex(layout);

			return logCapture.getLogEntries();
		}
	}

	private void _setUpPortletPreferences(
			AssetEntry assetEntry, JournalArticle journalArticle, Layout layout,
			String portletId)
		throws Exception {

		PortletPreferences portletPreferences =
			_portletPreferencesFactory.getPortletSetup(layout, portletId, null);

		portletPreferences.setValue(
			"articleId", String.valueOf(journalArticle.getArticleId()));
		portletPreferences.setValue(
			"groupId", String.valueOf(journalArticle.getGroupId()));

		portletPreferences.setValue(
			"assetEntryId", String.valueOf(assetEntry.getEntryId()));

		portletPreferences.store();

		_assertPortletPreferences(
			assetEntry, journalArticle, layout, portletId);
	}

	private static final String _CLASS_NAME =
		"com.liferay.layout.internal.search.spi.model.index.contributor." +
			"LayoutModelDocumentContributor";

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Inject
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalConverter _journalConverter;

	private String _languageId;
	private IndexerFixture<Layout> _layoutIndexerFixture;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private Locale _locale;

	@Inject
	private Portal _portal;

	@Inject
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}