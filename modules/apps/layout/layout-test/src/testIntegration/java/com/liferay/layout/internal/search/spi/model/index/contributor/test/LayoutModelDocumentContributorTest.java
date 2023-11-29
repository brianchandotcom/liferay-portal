/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.search.spi.model.index.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
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

	private void _addFragmentEntryLinkToLayout(
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

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
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

	private List<LogEntry> _reindexLogEntries(Layout layout) throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.DEBUG)) {

			Indexer<Layout> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				Layout.class);

			indexer.reindex(layout);

			return logCapture.getLogEntries();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.layout.internal.search.spi.model.index.contributor." +
			"LayoutModelDocumentContributor";

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private String _languageId;
	private IndexerFixture<Layout> _layoutIndexerFixture;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private Locale _locale;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}