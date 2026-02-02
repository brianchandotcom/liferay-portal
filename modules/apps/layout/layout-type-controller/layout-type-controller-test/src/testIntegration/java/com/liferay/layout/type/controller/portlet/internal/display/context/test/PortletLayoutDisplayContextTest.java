/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.type.controller.portlet.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.test.util.LayoutPageTemplateTestUtil;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.LayoutTypeControllerTracker;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Arrays;

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
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class PortletLayoutDisplayContextTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_group = GroupTestUtil.addGroup();

		_layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_PORTLET);

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	@TestInfo("LPD-77988")
	public void testGetLayoutStructure() throws Exception {
		Layout portletLayout = LayoutTestUtil.addTypePortletLayout(_group);

		_testGetLayoutStructure(
			portletLayout, LayoutDataItemTypeConstants.TYPE_DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_ROOT);

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_group.getGroupId(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
				WorkflowConstants.STATUS_APPROVED);

		_addItemAndPublishLayout(
			_layoutLocalService.getLayout(
				masterLayoutPageTemplateEntry.getPlid()));

		portletLayout =
			_layoutLocalService.updateMasterLayoutPageTemplateEntryERC(
				portletLayout.getGroupId(), portletLayout.isPrivateLayout(),
				portletLayout.getLayoutId(),
				masterLayoutPageTemplateEntry.getExternalReferenceCode());

		_testGetLayoutStructure(
			portletLayout, LayoutDataItemTypeConstants.TYPE_CONTAINER,
			LayoutDataItemTypeConstants.TYPE_DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_ROOT);

		LayoutPageTemplateEntry portletLayoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_group.getGroupId(),
				LayoutPageTemplateEntryTypeConstants.WIDGET_PAGE,
				WorkflowConstants.STATUS_APPROVED);

		Layout portletLayoutPageTemplateEntryLayout =
			_layoutLocalService.getLayout(
				portletLayoutPageTemplateEntry.getPlid());

		_testGetLayoutStructure(
			portletLayoutPageTemplateEntryLayout,
			LayoutDataItemTypeConstants.TYPE_DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_ROOT);

		portletLayoutPageTemplateEntryLayout =
			_layoutLocalService.updateMasterLayoutPageTemplateEntryERC(
				portletLayoutPageTemplateEntryLayout.getGroupId(),
				portletLayoutPageTemplateEntryLayout.isPrivateLayout(),
				portletLayoutPageTemplateEntryLayout.getLayoutId(),
				masterLayoutPageTemplateEntry.getExternalReferenceCode());

		_testGetLayoutStructure(
			portletLayoutPageTemplateEntryLayout,
			LayoutDataItemTypeConstants.TYPE_CONTAINER,
			LayoutDataItemTypeConstants.TYPE_DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_ROOT);
	}

	private void _addItemAndPublishLayout(Layout layout) throws Exception {
		Layout draftLayout = layout.fetchDraftLayout();

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				draftLayout.getPlid());

		ContentLayoutTestUtil.addItemToLayout(
			"{}", LayoutDataItemTypeConstants.TYPE_CONTAINER, draftLayout,
			_layoutStructureProvider, segmentsExperienceId);

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);
	}

	private LayoutStructure _getLayoutStructure(Layout layout)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			ContentLayoutTestUtil.getMockHttpServletRequest(
				_company, _group, layout);

		_layoutTypeController.includeLayoutContent(
			mockHttpServletRequest, new MockHttpServletResponse(), layout);

		return ReflectionTestUtil.invoke(
			mockHttpServletRequest.getAttribute(
				"PORTLET_LAYOUT_DISPLAY_CONTEXT"),
			"getLayoutStructure", new Class<?>[] {Layout.class}, layout);
	}

	private void _testGetLayoutStructure(
			Layout layout, String... layoutStructureItemTypes)
		throws Exception {

		LayoutStructure layoutStructure = _getLayoutStructure(layout);

		String[] actualLayoutStructureItemTypes =
			TransformUtil.transformToArray(
				layoutStructure.getLayoutStructureItems(),
				layoutStructureItem -> layoutStructureItem.getItemType(),
				String.class);

		Arrays.sort(actualLayoutStructureItemTypes);

		Assert.assertArrayEquals(
			layoutStructureItemTypes, actualLayoutStructureItemTypes);
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	private LayoutTypeController _layoutTypeController;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}