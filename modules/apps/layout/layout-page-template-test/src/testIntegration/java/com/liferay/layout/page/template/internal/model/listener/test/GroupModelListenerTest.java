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

package com.liferay.layout.page.template.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class GroupModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		Group group = _groupLocalService.fetchGroup(_group.getGroupId());

		if (group != null) {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testDeletingGroupDeletesFragmentCollections() throws Exception {
		FragmentCollection fragmentCollection = _addFragmentCollection();

		_groupLocalService.deleteGroup(_group);

		fragmentCollection =
			_fragmentCollectionLocalService.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId());

		Assert.assertNull(fragmentCollection);
	}

	@Test
	public void testDeletingGroupDeletesFragmentEntryLinks() throws Exception {
		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink();

		_groupLocalService.deleteGroup(_group);

		fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertNull(fragmentEntryLink);
	}

	@Test
	public void testDeletingGroupDeletesLayoutPageTemplateCollections()
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_addLayoutPageTemplateCollection();

		_groupLocalService.deleteGroup(_group);

		layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				fetchLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId());

		Assert.assertNull(layoutPageTemplateCollection);
	}

	@Test
	public void testDeletingGroupDeletesLayoutPageTemplateEntries()
		throws Exception {

		List<LayoutPageTemplateEntry> originalLayoutPageTemplateEntries =
			_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntries(
				_group.getGroupId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_addLayoutPageTemplateCollection();

		_addLayoutPageTemplateEntry(
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId());

		_addLayoutPageTemplateEntry(RandomTestUtil.randomLong());

		_addLayoutPageTemplateEntry(0);

		_groupLocalService.deleteGroup(_group);

		List<LayoutPageTemplateEntry> actualLayoutPageTemplateEntries =
			_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntries(
				_group.getGroupId());

		Assert.assertEquals(
			originalLayoutPageTemplateEntries.toString(),
			originalLayoutPageTemplateEntries.size(),
			actualLayoutPageTemplateEntries.size());
	}

	private FragmentCollection _addFragmentCollection() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return _fragmentCollectionLocalService.addFragmentCollection(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	private FragmentEntryLink _addFragmentEntryLink() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection = _addFragmentCollection();

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), null,
				RandomTestUtil.randomString(), StringPool.BLANK,
				RandomTestUtil.randomString(), StringPool.BLANK,
				StringPool.BLANK, 0, FragmentConstants.TYPE_SECTION,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			fragmentEntry.getFragmentEntryId(),
			PortalUtil.getClassNameId(Layout.class),
			RandomTestUtil.randomLong(), fragmentEntry.getCss(),
			fragmentEntry.getHtml(), fragmentEntry.getJs(),
			fragmentEntry.getConfiguration(), StringPool.BLANK,
			StringPool.BLANK, 0, null, serviceContext);
	}

	private LayoutPageTemplateCollection _addLayoutPageTemplateCollection()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return _layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);
	}

	private LayoutPageTemplateEntry _addLayoutPageTemplateEntry(
			long layoutPageTemplateCollectionId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			layoutPageTemplateCollectionId, RandomTestUtil.randomString(),
			serviceContext);
	}

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}