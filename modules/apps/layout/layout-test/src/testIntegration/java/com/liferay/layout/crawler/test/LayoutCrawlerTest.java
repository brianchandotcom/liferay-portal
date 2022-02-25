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

package com.liferay.layout.crawler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
public class LayoutCrawlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		_layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				StringUtil.randomString(), StringUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false, "{fieldSets: []}", null,
				0, FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		_fragmentEntryLinkService.addFragmentEntryLink(
			_group.getGroupId(), 0, fragmentEntry.getFragmentEntryId(),
			SegmentsExperienceConstants.ID_DEFAULT, _layout.getPlid(),
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(), null,
			StringPool.BLANK, 0, null, serviceContext);

		FragmentEntry contributedFragmentEntry =
			_fragmentCollectionContributorTracker.getFragmentEntry(
				"BASIC_COMPONENT-heading");

		JSONObject inlineValueJSONObject = JSONUtil.put(
			"com.liferay.fragment.entry.processor.editable." +
				"EditableFragmentEntryProcessor",
			JSONUtil.put(
				"element-text",
				JSONUtil.put(
					"config", JSONFactoryUtil.createJSONObject()
				).put(
					"defaultValue", "default value"
				).put(
					"en_US", "test inline value"
				)));

		FragmentEntryLink inlineFragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				_group.getGroupId(), 0,
				contributedFragmentEntry.getFragmentEntryId(),
				SegmentsExperienceConstants.ID_DEFAULT, _layout.getPlid(),
				contributedFragmentEntry.getCss(),
				contributedFragmentEntry.getHtml(),
				contributedFragmentEntry.getJs(),
				contributedFragmentEntry.getConfiguration(),
				inlineValueJSONObject.toString(), StringPool.BLANK, 0,
				contributedFragmentEntry.getFragmentEntryKey(), serviceContext);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_layout.getGroupId(), _layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT));

		LayoutStructureItem rowStyledLayoutStructureItem =
			layoutStructure.addRowStyledLayoutStructureItem(
				layoutStructure.getMainItemId(), 0, 1);

		LayoutStructureItem columnLayoutStructureItem =
			layoutStructure.addColumnLayoutStructureItem(
				rowStyledLayoutStructureItem.getItemId(), 0);

		layoutStructure.addFragmentStyledLayoutStructureItem(
			inlineFragmentEntryLink.getFragmentEntryLinkId(),
			columnLayoutStructureItem.getItemId(), 0);

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), "test mapped value", "test mapped value");

		JSONObject mappedValueJSONObject = JSONUtil.put(
			"com.liferay.fragment.entry.processor.editable." +
				"EditableFragmentEntryProcessor",
			JSONUtil.put(
				"element-text",
				JSONUtil.put(
					"classNameId",
					_portal.getClassNameId(JournalArticle.class.getName())
				).put(
					"classPK", journalArticle.getResourcePrimKey()
				).put(
					"config", JSONFactoryUtil.createJSONObject()
				).put(
					"defaultValue", "default value"
				).put(
					"fieldId", "title"
				)));

		FragmentEntryLink mappedFragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				_group.getGroupId(), 0,
				contributedFragmentEntry.getFragmentEntryId(),
				SegmentsExperienceConstants.ID_DEFAULT, _layout.getPlid(),
				contributedFragmentEntry.getCss(),
				contributedFragmentEntry.getHtml(),
				contributedFragmentEntry.getJs(),
				contributedFragmentEntry.getConfiguration(),
				mappedValueJSONObject.toString(), StringPool.BLANK, 0,
				contributedFragmentEntry.getFragmentEntryKey(), serviceContext);

		layoutStructure.addFragmentStyledLayoutStructureItem(
			mappedFragmentEntryLink.getFragmentEntryLinkId(),
			columnLayoutStructureItem.getItemId(), 1);

		JournalTestUtil.addArticle(
			_group.getGroupId(), "test journal article",
			"test journal article");

		FragmentEntryLink widgetFragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				_group.getGroupId(), 0, 0,
				SegmentsExperienceConstants.ID_DEFAULT, _layout.getPlid(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK,
				JSONUtil.put(
					"instanceid", StringUtil.randomString()
				).put(
					"portletId", AssetPublisherPortletKeys.ASSET_PUBLISHER
				).toString(),
				StringPool.BLANK, 0, StringPool.BLANK, serviceContext);

		layoutStructure.addFragmentStyledLayoutStructureItem(
			widgetFragmentEntryLink.getFragmentEntryLinkId(),
			columnLayoutStructureItem.getItemId(), 2);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_layout.getGroupId(), _layout.getPlid(),
				SegmentsExperienceConstants.ID_DEFAULT,
				layoutStructure.toString());
	}

	@Test
	public void testSearchLayoutContentByInlineFieldValue() throws Exception {
		Indexer<Layout> indexer = IndexerRegistryUtil.getIndexer(
			Layout.class.getName());

		Document document = indexer.getDocument(_layout);

		Assert.assertNotNull(document);

		String content = document.get(
			LocaleUtil.fromLanguageId("en_US"), Field.CONTENT);

		Assert.assertNotNull(content);

		Assert.assertThat(
			content, CoreMatchers.containsString("test inline value"));
	}

	@Test
	public void testSearchLayoutContentByMappedFieldValue() throws Exception {
		Indexer<Layout> indexer = IndexerRegistryUtil.getIndexer(
			Layout.class.getName());

		Document document = indexer.getDocument(_layout);

		Assert.assertNotNull(document);

		String content = document.get(
			LocaleUtil.fromLanguageId("en_US"), Field.CONTENT);

		Assert.assertNotNull(content);

		Assert.assertThat(
			content, CoreMatchers.containsString("test mapped value"));
	}

	@Test
	public void testSearchLayoutContentByWidgetContent() throws Exception {
		Indexer<Layout> indexer = IndexerRegistryUtil.getIndexer(
			Layout.class.getName());

		Document document = indexer.getDocument(_layout);

		Assert.assertNotNull(document);

		String content = document.get(
			LocaleUtil.fromLanguageId("en_US"), Field.CONTENT);

		Assert.assertNotNull(content);

		Assert.assertThat(
			content, CoreMatchers.containsString("test journal article"));
	}

	@Inject
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Inject
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private Portal _portal;

}