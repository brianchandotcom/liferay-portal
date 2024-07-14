/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ricardo Couso
 */
@RunWith(Arquillian.class)
public class LayoutPublishedSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_setUpLayoutIndexerFixture();
	}

	@Test
	public void testContentLayoutWithoutPublishedVersion() throws Exception {
		String name = RandomTestUtil.randomString();

		LayoutTestUtil.addTypeContentLayout(_group, name);

		_layoutIndexerFixture.searchNoOne(name);
	}

	@Test
	public void testPrivateContentLayoutWithInlineContent() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(
			_group, true, false);

		String content = RandomTestUtil.randomString();

		_updateDraftLayout(layout, content);

		_layoutIndexerFixture.searchNoOne(content);

		ContentLayoutTestUtil.publishLayout(layout.fetchDraftLayout(), layout);

		_layoutIndexerFixture.searchOnlyOne(content);
	}

	@Test
	public void testPublicContentLayoutWithInlineContent() throws Exception {
		String name = RandomTestUtil.randomString();

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group, name);

		_layoutIndexerFixture.searchNoOne(name);

		ContentLayoutTestUtil.publishLayout(layout.fetchDraftLayout(), layout);

		_layoutIndexerFixture.searchOnlyOne(name);
	}

	private void _setUpLayoutIndexerFixture() {
		_layoutIndexerFixture = new IndexerFixture<>(Layout.class);
	}

	private void _updateDraftLayout(Layout layout, String value)
		throws Exception {

		FragmentEntry contributedFragmentEntry =
			_fragmentCollectionContributorRegistry.getFragmentEntry(
				"BASIC_COMPONENT-heading");

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		Layout draftLayout = layout.fetchDraftLayout();

		JSONObject inlineValueJSONObject = JSONUtil.put(
			FragmentEntryProcessorConstants.
				KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			JSONUtil.put(
				"element-text",
				JSONUtil.put(
					"config", JSONFactoryUtil.createJSONObject()
				).put(
					"defaultValue", "default value"
				).put(
					layout.getDefaultLanguageId(), value
				)));

		FragmentEntryLink inlineFragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				null, _group.getGroupId(), 0,
				contributedFragmentEntry.getFragmentEntryId(),
				defaultSegmentsExperienceId, draftLayout.getPlid(),
				contributedFragmentEntry.getCss(),
				contributedFragmentEntry.getHtml(),
				contributedFragmentEntry.getJs(),
				contributedFragmentEntry.getConfiguration(),
				inlineValueJSONObject.toString(), StringPool.BLANK, 0,
				contributedFragmentEntry.getFragmentEntryKey(),
				contributedFragmentEntry.getType(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), draftLayout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

		LayoutStructureItem rowStyledLayoutStructureItem =
			layoutStructure.addRowStyledLayoutStructureItem(
				layoutStructure.getMainItemId(), 0, 1);

		LayoutStructureItem columnLayoutStructureItem =
			layoutStructure.addColumnLayoutStructureItem(
				rowStyledLayoutStructureItem.getItemId(), 0);

		layoutStructure.addFragmentStyledLayoutStructureItem(
			inlineFragmentEntryLink.getFragmentEntryLinkId(),
			columnLayoutStructureItem.getItemId(), 0);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group.getGroupId(), draftLayout.getPlid(),
				defaultSegmentsExperienceId, layoutStructure.toString());
	}

	@Inject
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Inject
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@DeleteAfterTestRun
	private Group _group;

	private IndexerFixture<Layout> _layoutIndexerFixture;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}