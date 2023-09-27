/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Collections;

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
public class LayoutStructureTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_fragmentEntry = _fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			StringUtil.randomString(), StringUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, "{fieldSets: []}", null, 0,
			FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		_fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(),
				defaultSegmentsExperienceId, layout.getPlid(),
				_fragmentEntry.getCss(), _fragmentEntry.getHtml(),
				_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(), null,
				StringPool.BLANK, 0, null, _fragmentEntry.getType(),
				serviceContext);
	}

	@Test
	public void testMarkLayoutStructureItemForDeletion1() {
		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem containerStyledLayoutStructureItem =
			layoutStructure.addContainerStyledLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

		LayoutStructureItem rowStyledLayoutStructureItem =
			layoutStructure.addRowStyledLayoutStructureItem(
				containerStyledLayoutStructureItem.getItemId(), 0, 1);

		LayoutStructureItem columnLayoutStructureItem =
			layoutStructure.addColumnLayoutStructureItem(
				rowStyledLayoutStructureItem.getItemId(), 0);

		LayoutStructureItem fragmentStyledLayoutStructureItem =
			layoutStructure.addFragmentStyledLayoutStructureItem(
				_fragmentEntryLink.getFragmentEntryLinkId(),
				columnLayoutStructureItem.getItemId(), 0);

		layoutStructure.markLayoutStructureItemForDeletion(
			fragmentStyledLayoutStructureItem.getItemId(),
			Collections.emptyList());

		Assert.assertEquals(
			0,
			_fragmentEntryLinkLocalService.
				getAllFragmentEntryLinksCountByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId()));

		layoutStructure.unmarkLayoutStructureItemForDeletion(
			fragmentStyledLayoutStructureItem.getItemId());

		Assert.assertEquals(
			1,
			_fragmentEntryLinkLocalService.
				getAllFragmentEntryLinksCountByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testMarkLayoutStructureItemForDeletion2() {
		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem containerStyledLayoutStructureItem =
			layoutStructure.addContainerStyledLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

		LayoutStructureItem rowStyledLayoutStructureItem =
			layoutStructure.addRowStyledLayoutStructureItem(
				containerStyledLayoutStructureItem.getItemId(), 0, 1);

		LayoutStructureItem columnLayoutStructureItem =
			layoutStructure.addColumnLayoutStructureItem(
				rowStyledLayoutStructureItem.getItemId(), 0);

		layoutStructure.addFragmentStyledLayoutStructureItem(
			_fragmentEntryLink.getFragmentEntryLinkId(),
			columnLayoutStructureItem.getItemId(), 0);

		layoutStructure.markLayoutStructureItemForDeletion(
			columnLayoutStructureItem.getItemId(), Collections.emptyList());

		Assert.assertEquals(
			0,
			_fragmentEntryLinkLocalService.
				getAllFragmentEntryLinksCountByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId()));

		layoutStructure.unmarkLayoutStructureItemForDeletion(
			columnLayoutStructureItem.getItemId());

		Assert.assertEquals(
			1,
			_fragmentEntryLinkLocalService.
				getAllFragmentEntryLinksCountByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testMarkLayoutStructureItemForDeletion3() {
		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem containerStyledLayoutStructureItem =
			layoutStructure.addContainerStyledLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

		LayoutStructureItem rowStyledLayoutStructureItem =
			layoutStructure.addRowStyledLayoutStructureItem(
				containerStyledLayoutStructureItem.getItemId(), 0, 1);

		LayoutStructureItem columnLayoutStructureItem =
			layoutStructure.addColumnLayoutStructureItem(
				rowStyledLayoutStructureItem.getItemId(), 0);

		layoutStructure.addFragmentStyledLayoutStructureItem(
			_fragmentEntryLink.getFragmentEntryLinkId(),
			columnLayoutStructureItem.getItemId(), 0);

		layoutStructure.markLayoutStructureItemForDeletion(
			rowStyledLayoutStructureItem.getItemId(), Collections.emptyList());

		Assert.assertEquals(
			0,
			_fragmentEntryLinkLocalService.
				getAllFragmentEntryLinksCountByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId()));

		layoutStructure.unmarkLayoutStructureItemForDeletion(
			rowStyledLayoutStructureItem.getItemId());

		Assert.assertEquals(
			1,
			_fragmentEntryLinkLocalService.
				getAllFragmentEntryLinksCountByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testMarkLayoutStructureItemForDeletion4() {
		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem containerStyledLayoutStructureItem =
			layoutStructure.addContainerStyledLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

		LayoutStructureItem rowStyledLayoutStructureItem =
			layoutStructure.addRowStyledLayoutStructureItem(
				containerStyledLayoutStructureItem.getItemId(), 0, 1);

		LayoutStructureItem columnLayoutStructureItem =
			layoutStructure.addColumnLayoutStructureItem(
				rowStyledLayoutStructureItem.getItemId(), 0);

		layoutStructure.addFragmentStyledLayoutStructureItem(
			_fragmentEntryLink.getFragmentEntryLinkId(),
			columnLayoutStructureItem.getItemId(), 0);

		layoutStructure.markLayoutStructureItemForDeletion(
			containerStyledLayoutStructureItem.getItemId(),
			Collections.emptyList());

		Assert.assertEquals(
			0,
			_fragmentEntryLinkLocalService.
				getAllFragmentEntryLinksCountByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId()));

		layoutStructure.unmarkLayoutStructureItemForDeletion(
			containerStyledLayoutStructureItem.getItemId());

		Assert.assertEquals(
			1,
			_fragmentEntryLinkLocalService.
				getAllFragmentEntryLinksCountByFragmentEntryId(
					_group.getGroupId(), _fragmentEntry.getFragmentEntryId()));
	}

	private FragmentEntry _fragmentEntry;
	private FragmentEntryLink _fragmentEntryLink;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}