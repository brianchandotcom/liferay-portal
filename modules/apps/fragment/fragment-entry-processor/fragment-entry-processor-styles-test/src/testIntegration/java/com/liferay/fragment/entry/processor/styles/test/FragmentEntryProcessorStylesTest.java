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

package com.liferay.fragment.entry.processor.styles.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class FragmentEntryProcessorStylesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId());
	}

	@Test
	public void testFragmentWithStylesAttribute() throws Exception {
		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem containerStyledLayoutStructureItem =
			layoutStructure.addContainerStyledLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid());

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
			defaultSegmentsExperienceId);

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)
				layoutStructure.addFragmentStyledLayoutStructureItem(
					fragmentEntryLink.getFragmentEntryLinkId(),
					containerStyledLayoutStructureItem.getItemId(), 0);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group.getGroupId(), _layout.getPlid(),
				defaultSegmentsExperienceId, layoutStructure.toString());

		Document document = _getDocument(
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				new DefaultFragmentEntryProcessorContext(
					null, null, FragmentEntryLinkConstants.EDIT,
					LocaleUtil.getMostRelevantLocale())));

		String layoutStructureItemUniqueCssClass =
			fragmentStyledLayoutStructureItem.getUniqueCssClass();

		Elements elements = document.select(
			StringPool.PERIOD + layoutStructureItemUniqueCssClass);

		Assert.assertEquals(1, elements.size());
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testInvalidFragmentThrows() throws Exception {
		_fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			"fragment-entry", "Fragment Entry", null,
			_read("invalid_styles_fragment_entry.html"), null, false, null,
			null, 0, FragmentConstants.TYPE_SECTION, null,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);
	}

	private FragmentEntryLink _addFragmentEntryLink(long segmentsExperienceId)
		throws Exception {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				"fragment-entry", "Fragment Entry", null,
				_read("valid_styles_fragment_entry.html"), null, false, null,
				null, 0, FragmentConstants.TYPE_SECTION, null,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			fragmentEntry.getFragmentEntryId(), segmentsExperienceId,
			_layout.getPlid(), fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
			StringPool.BLANK, StringPool.BLANK, 0, null,
			fragmentEntry.getType(), _serviceContext);
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		document.outputSettings(
			new Document.OutputSettings() {
				{
					prettyPrint(false);
				}
			});

		return document;
	}

	private String _read(String fileName) {
		return StringUtil.read(getClass(), "dependencies/" + fileName);
	}

	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

}