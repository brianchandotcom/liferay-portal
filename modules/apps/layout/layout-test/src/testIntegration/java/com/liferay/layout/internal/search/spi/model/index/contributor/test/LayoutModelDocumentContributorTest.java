/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.search.spi.model.index.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.layout.model.LayoutLocalization;
import com.liferay.layout.service.LayoutLocalizationLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
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

		String headingText = RandomTestUtil.randomString();

		Layout layout = _addTypeContentLayout(true, headingText);

		_assertReindex(headingText, layout);

		Layout draftLayout = _addHeadingFragmentToLayout(
			RandomTestUtil.randomString(), layout);

		_assertReindexDraftLayout(draftLayout);

		_assertSearch(headingText, layout.getPlid());
	}

	@Test
	public void testReindexPublishedLayoutWithLayoutLocalization()
		throws Exception {

		String headingText = RandomTestUtil.randomString();

		Layout layout = _addTypeContentLayout(true, headingText);

		List<LayoutLocalization> layoutLocalizations1 =
			_layoutLocalizationLocalService.getLayoutLocalizations(
				layout.getPlid());

		Assert.assertFalse(
			layoutLocalizations1.toString(), layoutLocalizations1.isEmpty());

		_assertReindex(headingText, layout);

		List<LayoutLocalization> layoutLocalizations2 =
			_layoutLocalizationLocalService.getLayoutLocalizations(
				layout.getPlid());

		Assert.assertEquals(
			layoutLocalizations2.toString(), layoutLocalizations1,
			layoutLocalizations2);
	}

	@Test
	public void testReindexPublishedLayoutWithoutLayoutLocalization()
		throws Exception {

		String headingText = RandomTestUtil.randomString();

		Layout layout = _addTypeContentLayout(true, headingText);

		_deleteLayoutLocalizations(layout.getPlid());

		List<LogEntry> logEntries = _reindexLogEntries(layout);

		Assert.assertEquals(logEntries.toString(), 0, logEntries.size());

		_assertSearch(headingText, layout.getPlid());
	}

	@Test
	public void testReindexUnpublishedDraftLayout() throws Exception {
		String headingText = RandomTestUtil.randomString();

		Layout layout = _addTypeContentLayout(false, headingText);

		List<LayoutLocalization> layoutLocalizations =
			_layoutLocalizationLocalService.getLayoutLocalizations(
				layout.getPlid());

		Assert.assertTrue(
			layoutLocalizations.toString(), layoutLocalizations.isEmpty());

		_assertReindexDraftLayout(layout);
	}

	private Layout _addHeadingFragmentToLayout(
			String headingText, Layout layout)
		throws Exception {

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		FragmentEntry fragmentEntry =
			_fragmentCollectionContributorRegistry.getFragmentEntry(
				"BASIC_COMPONENT-heading");

		Assert.assertNotNull(fragmentEntry);

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			JSONUtil.put(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
				JSONUtil.put(
					"element-text", JSONUtil.put(_languageId, headingText))
			).put(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
				JSONUtil.put("headingLevel", "h1")
			).toString(),
			fragmentEntry.getCss(), fragmentEntry.getConfiguration(),
			fragmentEntry.getFragmentEntryId(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), draftLayout,
			fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(), null,
			0,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				draftLayout.getPlid()));

		return draftLayout;
	}

	private Layout _addTypeContentLayout(boolean publish, String headingText)
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = _addHeadingFragmentToLayout(headingText, layout);

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

		LayoutLocalization layoutLocalization =
			_layoutLocalizationLocalService.fetchLayoutLocalization(
				layout.getGroupId(), _languageId, layout.getPlid());

		Assert.assertNotNull(layoutLocalization);

		Assert.assertTrue(
			layoutLocalization.getContent(),
			StringUtil.contains(
				layoutLocalization.getContent(), expectedContent));

		_assertSearch(expectedContent, layout.getPlid());
	}

	private void _assertReindexDraftLayout(Layout draftLayout)
		throws Exception {

		List<LogEntry> logEntries = _reindexLogEntries(draftLayout);

		Assert.assertEquals(logEntries.toString(), 0, logEntries.size());

		List<LayoutLocalization> layoutLocalizations =
			_layoutLocalizationLocalService.getLayoutLocalizations(
				draftLayout.getPlid());

		Assert.assertTrue(
			layoutLocalizations.toString(), layoutLocalizations.isEmpty());
	}

	private void _assertSearch(String keywords, long plid) {
		Document document = _layoutIndexerFixture.searchOnlyOne(
			keywords, _locale);

		Assert.assertNotNull(document);

		String content = document.get(
			Field.getLocalizedName(_locale, Field.CONTENT));

		Assert.assertTrue(content, StringUtil.contains(content, keywords));

		Assert.assertEquals(
			document.get(Field.ENTRY_CLASS_PK), String.valueOf(plid));
	}

	private int _deleteLayoutLocalizations(long plid) {
		List<LayoutLocalization> layoutLocalizations =
			_layoutLocalizationLocalService.getLayoutLocalizations(plid);

		Assert.assertFalse(
			layoutLocalizations.toString(), layoutLocalizations.isEmpty());

		for (LayoutLocalization layoutLocalization : layoutLocalizations) {
			_layoutLocalizationLocalService.deleteLayoutLocalization(
				layoutLocalization);
		}

		int originalLayoutLocalizationsSize = layoutLocalizations.size();

		layoutLocalizations =
			_layoutLocalizationLocalService.getLayoutLocalizations(plid);

		Assert.assertTrue(
			layoutLocalizations.toString(), layoutLocalizations.isEmpty());

		return originalLayoutLocalizationsSize;
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
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@DeleteAfterTestRun
	private Group _group;

	private String _languageId;
	private IndexerFixture<Layout> _layoutIndexerFixture;

	@Inject
	private LayoutLocalizationLocalService _layoutLocalizationLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private Locale _locale;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}