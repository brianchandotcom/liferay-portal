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
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.io.InputStream;

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
@Sync
public class DeleteItemReactMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);

		FragmentCollection fragmentCollection = _addFragmentCollection();

		_fragmentEntry = _addFragmentEntry(
			fragmentCollection.getFragmentCollectionId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());
	}

	@Test
	public void testDeleteContainerItem() throws Exception {
		_addLayoutPageTemplateStructure(
			_read("layout_data_with_children.json"));

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "deleteItemJSONObject",
			new Class<?>[] {
				long.class, long.class, String.class, long.class, long.class
			},
			_group.getCompanyId(), _group.getGroupId(), "newItemId",
			_layout.getPlid(), SegmentsExperienceConstants.ID_DEFAULT);

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		JSONObject itemsJSONObject = layoutDataJSONObject.getJSONObject(
			"items");

		Assert.assertEquals(1, itemsJSONObject.length());

		JSONObject rootJSONObject = itemsJSONObject.getJSONObject("root");

		Assert.assertNotNull(rootJSONObject);

		JSONArray childrenJSONArray = rootJSONObject.getJSONArray("children");

		Assert.assertEquals(0, childrenJSONArray.length());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testDeleteDropZone() throws Exception {
		_addLayoutPageTemplateStructure(
			_read("layout_data_with_drop_zone.json"));

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "deleteItemJSONObject",
			new Class<?>[] {
				long.class, long.class, String.class, long.class, long.class
			},
			_group.getCompanyId(), _group.getGroupId(), "dropZoneItemId",
			_layout.getPlid(), SegmentsExperienceConstants.ID_DEFAULT);
	}

	@Test
	public void testDeleteFragmentEntryLinkItem() throws Exception {
		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), _layout.getPlid(),
				StringPool.BLANK, "html", StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		String layoutData = StringUtil.replace(
			_read("layout_data_with_fragment_entry_link.json"),
			"[$FRAGMENT_ENTRY_LINK_ID$]",
			String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()));

		_addLayoutPageTemplateStructure(layoutData);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "deleteItemJSONObject",
			new Class<?>[] {
				long.class, long.class, String.class, long.class, long.class
			},
			_group.getCompanyId(), _group.getGroupId(), "newItemId",
			_layout.getPlid(), SegmentsExperienceConstants.ID_DEFAULT);

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		JSONObject itemsJSONObject = layoutDataJSONObject.getJSONObject(
			"items");

		Assert.assertEquals(1, itemsJSONObject.length());

		JSONObject rootJSONObject = itemsJSONObject.getJSONObject("root");

		Assert.assertNotNull(rootJSONObject);

		JSONArray childrenJSONArray = rootJSONObject.getJSONArray("children");

		Assert.assertEquals(0, childrenJSONArray.length());

		Assert.assertNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testDeleteItemContainingDropZone() throws Exception {
		_addLayoutPageTemplateStructure(
			_read("layout_data_with_drop_zone.json"));

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "deleteItemJSONObject",
			new Class<?>[] {
				long.class, long.class, String.class, long.class, long.class
			},
			_group.getCompanyId(), _group.getGroupId(), "newItemId",
			_layout.getPlid(), SegmentsExperienceConstants.ID_DEFAULT);
	}

	@Test
	public void testDeleteItemWithNestedFragmentEntryLink() throws Exception {
		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), _layout.getPlid(),
				StringPool.BLANK, "html", StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		String layoutData = StringUtil.replace(
			_read("layout_data_with_fragment_entry_link.json"),
			"[$FRAGMENT_ENTRY_LINK_ID$]",
			String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()));

		_addLayoutPageTemplateStructure(layoutData);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "deleteItemJSONObject",
			new Class<?>[] {
				long.class, long.class, String.class, long.class, long.class
			},
			_group.getCompanyId(), _group.getGroupId(), "newItemId",
			_layout.getPlid(), SegmentsExperienceConstants.ID_DEFAULT);

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		JSONObject itemsJSONObject = layoutDataJSONObject.getJSONObject(
			"items");

		Assert.assertEquals(1, itemsJSONObject.length());

		JSONObject rootJSONObject = itemsJSONObject.getJSONObject("root");

		Assert.assertNotNull(rootJSONObject);

		JSONArray childrenJSONArray = rootJSONObject.getJSONArray("children");

		Assert.assertEquals(0, childrenJSONArray.length());

		Assert.assertNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));
	}

	@Test
	public void testDeleteItemWithSeveralNestedFragmentEntryLink()
		throws Exception {

		FragmentEntryLink fragmentEntryLink1 =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), _layout.getPlid(),
				StringPool.BLANK, "html", StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		FragmentEntryLink fragmentEntryLink2 =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), _layout.getPlid(),
				StringPool.BLANK, "html", StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		String layoutData = StringUtil.replace(
			_read("layout_data_with_several_nested_fragment_entry_link.json"),
			new String[] {
				"[$FRAGMENT_ENTRY_LINK_ID_1$]", "[$FRAGMENT_ENTRY_LINK_ID_2$]"
			},
			new String[] {
				String.valueOf(fragmentEntryLink1.getFragmentEntryLinkId()),
				String.valueOf(fragmentEntryLink2.getFragmentEntryLinkId())
			});

		_addLayoutPageTemplateStructure(layoutData);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "deleteItemJSONObject",
			new Class<?>[] {
				long.class, long.class, String.class, long.class, long.class
			},
			_group.getCompanyId(), _group.getGroupId(), "newItemId",
			_layout.getPlid(), SegmentsExperienceConstants.ID_DEFAULT);

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		JSONObject itemsJSONObject = layoutDataJSONObject.getJSONObject(
			"items");

		Assert.assertEquals(1, itemsJSONObject.length());

		JSONObject rootJSONObject = itemsJSONObject.getJSONObject("root");

		Assert.assertNotNull(rootJSONObject);

		JSONArray childrenJSONArray = rootJSONObject.getJSONArray("children");

		Assert.assertEquals(0, childrenJSONArray.length());

		Assert.assertNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink1.getFragmentEntryLinkId()));
		Assert.assertNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink2.getFragmentEntryLinkId()));
	}

	@Test
	public void testDeleteItemWithTwoLevelNesting() throws Exception {
		FragmentEntryLink fragmentEntryLink1 =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), _layout.getPlid(),
				StringPool.BLANK, "html", StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		FragmentEntryLink fragmentEntryLink2 =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				_fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), _layout.getPlid(),
				StringPool.BLANK, "html", StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, 0, null, _serviceContext);

		String layoutData = StringUtil.replace(
			_read("layout_data_with_two_level_nesting.json"),
			new String[] {
				"[$FRAGMENT_ENTRY_LINK_ID_1$]", "[$FRAGMENT_ENTRY_LINK_ID_2$]"
			},
			new String[] {
				String.valueOf(fragmentEntryLink1.getFragmentEntryLinkId()),
				String.valueOf(fragmentEntryLink2.getFragmentEntryLinkId())
			});

		_addLayoutPageTemplateStructure(layoutData);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "deleteItemJSONObject",
			new Class<?>[] {
				long.class, long.class, String.class, long.class, long.class
			},
			_group.getCompanyId(), _group.getGroupId(), "newItemId",
			_layout.getPlid(), SegmentsExperienceConstants.ID_DEFAULT);

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		JSONObject itemsJSONObject = layoutDataJSONObject.getJSONObject(
			"items");

		Assert.assertEquals(1, itemsJSONObject.length());

		JSONObject rootJSONObject = itemsJSONObject.getJSONObject("root");

		Assert.assertNotNull(rootJSONObject);

		JSONArray childrenJSONArray = rootJSONObject.getJSONArray("children");

		Assert.assertEquals(0, childrenJSONArray.length());

		Assert.assertNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink1.getFragmentEntryLinkId()));
		Assert.assertNull(
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink2.getFragmentEntryLinkId()));
	}

	private FragmentCollection _addFragmentCollection() throws PortalException {
		return _fragmentCollectionLocalService.addFragmentCollection(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), StringPool.BLANK, _serviceContext);
	}

	private FragmentEntry _addFragmentEntry(long fragmentCollectionId)
		throws PortalException {

		return _fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			fragmentCollectionId, StringPool.BLANK,
			RandomTestUtil.randomString(), StringPool.BLANK, "<div>TEST</div>",
			StringPool.BLANK, StringPool.BLANK, 0,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			_serviceContext);
	}

	private void _addLayoutPageTemplateStructure(String layoutData)
		throws Exception {

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(Layout.class.getName()), _layout.getPlid(),
			layoutData, _serviceContext);
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	private FragmentEntry _fragmentEntry;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(filter = "mvc.command.name=/content_layout/delete_item_react")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

}