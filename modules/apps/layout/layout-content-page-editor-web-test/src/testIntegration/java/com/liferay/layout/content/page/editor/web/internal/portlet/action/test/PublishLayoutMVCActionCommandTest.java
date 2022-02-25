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

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.DeletedLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
public class PublishLayoutMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = new ServiceContext();

		_serviceContext.setScopeGroupId(_group.getGroupId());
		_serviceContext.setUserId(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@Test
	public void testDeletedItemsAreRemovedWhenLayoutIsPublished()
		throws Exception {

		Layout layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false, 0, 0, 0,
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			LayoutConstants.TYPE_CONTENT, StringPool.BLANK, false, false,
			new HashMap<>(), 0, _serviceContext);

		Layout draftLayout = layout.fetchDraftLayout();

		LayoutStructure layoutStructure = _getLayoutStructure(draftLayout);

		LayoutStructureItem layoutStructureItem1 =
			layoutStructure.addContainerStyledLayoutStructureItem(
				layoutStructure.getMainItemId(), 0);

		LayoutStructureItem layoutStructureItem2 =
			layoutStructure.addRowStyledLayoutStructureItem(
				layoutStructure.getMainItemId(), 0, 3);

		LayoutStructureItem layoutStructureItem3 =
			layoutStructure.addRowStyledLayoutStructureItem(
				layoutStructure.getMainItemId(), 0, 3);

		layoutStructure.markLayoutStructureItemForDeletion(
			layoutStructureItem1.getItemId(), Collections.emptyList());

		layoutStructure.markLayoutStructureItemForDeletion(
			layoutStructureItem2.getItemId(), Collections.emptyList());

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group.getGroupId(), draftLayout.getPlid(), 0,
				layoutStructure.toString());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "_publishLayout",
			new Class<?>[] {
				Layout.class, Layout.class, ServiceContext.class, long.class
			},
			draftLayout, layout, _serviceContext, TestPropsValues.getUserId());

		layoutStructure = _getLayoutStructure(draftLayout);

		List<DeletedLayoutStructureItem> deletedLayoutStructureItems =
			layoutStructure.getDeletedLayoutStructureItems();

		Assert.assertEquals(
			deletedLayoutStructureItems.toString(), 0,
			deletedLayoutStructureItems.size());

		Assert.assertNull(
			layoutStructure.getLayoutStructureItem(
				layoutStructureItem1.getItemId()));
		Assert.assertNull(
			layoutStructure.getLayoutStructureItem(
				layoutStructureItem2.getItemId()));
		Assert.assertNotNull(
			layoutStructure.getLayoutStructureItem(
				layoutStructureItem3.getItemId()));
	}

	private LayoutStructure _getLayoutStructure(Layout layout)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), layout.getPlid());

		return LayoutStructure.of(layoutPageTemplateStructure.getData(0));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/publish_layout"
	)
	private MVCActionCommand _mvcActionCommand;

	private ServiceContext _serviceContext;

}