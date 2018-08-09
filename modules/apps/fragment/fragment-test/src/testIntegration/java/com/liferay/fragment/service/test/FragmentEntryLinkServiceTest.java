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

package com.liferay.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.List;

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
public class FragmentEntryLinkServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddFragmentEntryLink() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Fragment Collection", StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Fragment Name",
				StringPool.BLANK, "<div>test</div>", StringPool.BLANK,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class),
				RandomTestUtil.randomLong(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				StringPool.BLANK, 0, serviceContext);

		Assert.assertNotNull(
			FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));
	}

	@Test
	public void testAddMultipleFragmentEntryLinks() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		long classPK = RandomTestUtil.randomLong();

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Fragment Collection", StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Fragment Name",
				StringPool.BLANK, "<div>test</div>", StringPool.BLANK,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		List<FragmentEntryLink> originalFragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
			TestPropsValues.getUserId(), _group.getGroupId(),
			fragmentEntry.getFragmentEntryId(),
			PortalUtil.getClassNameId(Layout.class), classPK,
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), StringPool.BLANK, 0, serviceContext);

		FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
			TestPropsValues.getUserId(), _group.getGroupId(),
			fragmentEntry.getFragmentEntryId(),
			PortalUtil.getClassNameId(Layout.class), classPK,
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), StringPool.BLANK, 1, serviceContext);

		List<FragmentEntryLink> actualFragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			actualFragmentEntryLinks.toString(),
			originalFragmentEntryLinks.size() + 2,
			actualFragmentEntryLinks.size());
	}

	@Test
	public void testDeleteFragmentEntryLink() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		long classPK = RandomTestUtil.randomLong();

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Fragment Collection", StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Fragment Name",
				StringPool.BLANK, "<div>test</div>", StringPool.BLANK,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), classPK,
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), StringPool.BLANK, 0, serviceContext);

		FragmentEntryLinkLocalServiceUtil.deleteFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertNull(
			FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));
	}

	@Test
	public void testDeleteFragmentEntryLinks() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		long classPK = RandomTestUtil.randomLong();

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Fragment Collection", StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Fragment Name",
				StringPool.BLANK, "<div>test</div>", StringPool.BLANK,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLink1 =
			FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), classPK,
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), StringPool.BLANK, 0, serviceContext);

		FragmentEntryLink fragmentEntryLink2 =
			FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), classPK,
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), StringPool.BLANK, 0, serviceContext);

		FragmentEntryLinkLocalServiceUtil.deleteFragmentEntryLinks(
			_group.getGroupId());

		Assert.assertNull(
			FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
				fragmentEntryLink1.getFragmentEntryLinkId()));

		Assert.assertNull(
			FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
				fragmentEntryLink2.getFragmentEntryLinkId()));
	}

	@Test
	public void testUpdateFragmentEntryLinkPosition() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Fragment Collection", StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), "Fragment Name",
				StringPool.BLANK, "<div>test</div>", StringPool.BLANK,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class),
				RandomTestUtil.randomLong(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				StringPool.BLANK, 0, serviceContext);

		fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.updateFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId(), 1);

		Assert.assertEquals(1, fragmentEntryLink.getPosition());
	}

	@DeleteAfterTestRun
	private Group _group;

}