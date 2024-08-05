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
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FormStepContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FormStepLayoutStructureItem;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
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

import java.io.IOException;

import java.util.Collections;
import java.util.List;

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
			null, TestPropsValues.getUserId(), _group.getGroupId(), 0,
			StringUtil.randomString(), StringUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, "{fieldSets: []}", null, 0,
			false, FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		_fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(),
				defaultSegmentsExperienceId, layout.getPlid(),
				_fragmentEntry.getCss(), _fragmentEntry.getHtml(),
				_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(), null,
				StringPool.BLANK, 0, null, _fragmentEntry.getType(),
				serviceContext);
	}

	@Test
	public void testChangeFormTypeMultiStep() throws IOException {
		LayoutStructure layoutStructure = LayoutStructure.of(_readLayoutData());

		layoutStructure.updateFormStyledLayoutStructureItemMultiStep(
			"formId", true, 3);

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)
				layoutStructure.getLayoutStructureItem("formId");

		List<String> childrenItemIds =
			formStyledLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), 1, childrenItemIds.size());

		FormStepContainerStyledLayoutStructureItem
			formStepContainerStyledLayoutStructureItem =
				(FormStepContainerStyledLayoutStructureItem)
					layoutStructure.getLayoutStructureItem(
						childrenItemIds.get(0));

		childrenItemIds =
			formStepContainerStyledLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), 3, childrenItemIds.size());

		FormStepLayoutStructureItem formStepLayoutStructureItem =
			(FormStepLayoutStructureItem)layoutStructure.getLayoutStructureItem(
				childrenItemIds.get(0));

		childrenItemIds = formStepLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals("containerId", childrenItemIds.get(0));
	}

	@Test
	public void testChangeFormTypeNoMultiStep() throws IOException {
		LayoutStructure layoutStructure = LayoutStructure.of(_readLayoutData());

		layoutStructure.updateFormStyledLayoutStructureItemMultiStep(
			"formId", true, 3);

		layoutStructure.updateFormStyledLayoutStructureItemMultiStep(
			"formId", false, 3);

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)
				layoutStructure.getLayoutStructureItem("formId");

		List<String> childrenItemIds =
			formStyledLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), 1, childrenItemIds.size());

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(childrenItemIds.get(0));

		Assert.assertTrue(
			layoutStructureItem instanceof ContainerStyledLayoutStructureItem);

		Assert.assertEquals("containerId", layoutStructureItem.getItemId());
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

	private String _readLayoutData() throws IOException {
		return StringUtil.read(
			LayoutStructureTest.class.getResourceAsStream(
				"dependencies/layout_data_with_form.json"));
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