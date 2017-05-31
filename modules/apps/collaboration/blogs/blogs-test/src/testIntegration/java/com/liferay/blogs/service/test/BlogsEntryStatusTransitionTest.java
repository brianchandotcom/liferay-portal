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

package com.liferay.blogs.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.blogs.exception.EntryTitleException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.blogs.social.BlogsActivityKeys;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.service.SocialActivityLocalServiceUtil;

import java.io.Serializable;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
@Sync
public class BlogsEntryStatusTransitionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		user = UserTestUtil.addUser(group.getGroupId());

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		entry = BlogsTestUtil.addEntryWithWorkflow(
			user.getUserId(), RandomTestUtil.randomString(), false,
			serviceContext);
	}

	@Test
	public void testApprovedToDraft() throws Exception {
		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_DRAFT, getServiceContext(entry),
			new HashMap<String, Serializable>());

		Assert.assertFalse(isAssetEntryVisible(entry.getEntryId()));
		Assert.assertEquals(0, searchBlogsEntriesCount(group.getGroupId()));
	}

	@Test
	public void testApprovedToTrash() throws Exception {
		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_IN_TRASH, getServiceContext(entry),
			new HashMap<String, Serializable>());

		Assert.assertFalse(isAssetEntryVisible(entry.getEntryId()));
		Assert.assertEquals(0, searchBlogsEntriesCount(group.getGroupId()));
	}

	@Test
	public void testDraftToApprovedByAdd() throws Exception {
		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		Assert.assertTrue(isAssetEntryVisible(entry.getEntryId()));
		Assert.assertEquals(1, searchBlogsEntriesCount(group.getGroupId()));

		checkSocialActivity(BlogsActivityKeys.ADD_ENTRY, 1);
	}

	@Test
	public void testDraftToApprovedByUpdate() throws Exception {
		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_DRAFT, getServiceContext(entry),
			new HashMap<String, Serializable>());

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		Assert.assertTrue(isAssetEntryVisible(entry.getEntryId()));
		Assert.assertEquals(1, searchBlogsEntriesCount(group.getGroupId()));

		checkSocialActivity(BlogsActivityKeys.UPDATE_ENTRY, 1);
	}

	@Test(expected = EntryTitleException.class)
	public void testDraftToApprovedWithoutTitle() throws Exception {
		ServiceContext serviceContext = getServiceContext(entry);
		String title = "";

		BlogsEntry entryWithoutTitle = BlogsEntryLocalServiceUtil.addEntry(
			user.getUserId(), title, RandomTestUtil.randomString(),
			serviceContext);

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entryWithoutTitle.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext,
			new HashMap<String, Serializable>());
	}

	@Test
	public void testDraftToScheduledByAdd() throws Exception {
		Calendar displayDate = new GregorianCalendar();

		displayDate.add(Calendar.DATE, 1);

		entry.setDisplayDate(displayDate.getTime());

		BlogsEntryLocalServiceUtil.updateBlogsEntry(entry);

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		Assert.assertFalse(isAssetEntryVisible(entry.getEntryId()));
		Assert.assertEquals(0, searchBlogsEntriesCount(group.getGroupId()));

		checkSocialActivity(BlogsActivityKeys.ADD_ENTRY, 1);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			BlogsEntry.class.getName(), entry.getEntryId());

		Assert.assertNull(assetEntry.getPublishDate());
	}

	@Test
	public void testDraftToScheduledUpdate() throws Exception {
		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		entry = BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_DRAFT, getServiceContext(entry),
			new HashMap<String, Serializable>());

		Calendar displayDate = new GregorianCalendar();

		displayDate.add(Calendar.DATE, 1);

		entry.setDisplayDate(displayDate.getTime());

		BlogsEntryLocalServiceUtil.updateBlogsEntry(entry);

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		Assert.assertFalse(isAssetEntryVisible(entry.getEntryId()));
		Assert.assertEquals(0, searchBlogsEntriesCount(group.getGroupId()));

		checkSocialActivity(BlogsActivityKeys.UPDATE_ENTRY, 1);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			BlogsEntry.class.getName(), entry.getEntryId());

		Assert.assertNotNull(assetEntry.getPublishDate());
	}

	@Test
	public void testDraftToTrash() throws Exception {
		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_IN_TRASH, getServiceContext(entry),
			new HashMap<String, Serializable>());

		Assert.assertFalse(isAssetEntryVisible(entry.getEntryId()));
		Assert.assertEquals(0, searchBlogsEntriesCount(group.getGroupId()));
	}

	@Test
	public void testScheduledByAddToApproved() throws Exception {
		Calendar displayDate = new GregorianCalendar();

		displayDate.add(Calendar.DATE, 1);

		entry.setDisplayDate(displayDate.getTime());

		BlogsEntryLocalServiceUtil.updateBlogsEntry(entry);

		entry = BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		displayDate.add(Calendar.DATE, -2);

		entry.setDisplayDate(displayDate.getTime());

		BlogsEntryLocalServiceUtil.updateBlogsEntry(entry);

		BlogsEntryLocalServiceUtil.checkEntries();

		Assert.assertTrue(isAssetEntryVisible(entry.getEntryId()));
		Assert.assertEquals(1, searchBlogsEntriesCount(group.getGroupId()));
	}

	@Test
	public void testScheduledByUpdateToApproved() throws Exception {
		entry = BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		Calendar displayDate = new GregorianCalendar();

		displayDate.add(Calendar.DATE, 1);

		entry.setDisplayDate(displayDate.getTime());

		entry.setStatus(WorkflowConstants.STATUS_DRAFT);

		BlogsEntryLocalServiceUtil.updateBlogsEntry(entry);

		entry = BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		checkSocialActivity(BlogsActivityKeys.UPDATE_ENTRY, 1);

		displayDate.add(Calendar.DATE, -2);

		entry.setDisplayDate(displayDate.getTime());

		BlogsEntryLocalServiceUtil.updateBlogsEntry(entry);

		BlogsEntryLocalServiceUtil.checkEntries();

		Assert.assertTrue(isAssetEntryVisible(entry.getEntryId()));
		Assert.assertEquals(1, searchBlogsEntriesCount(group.getGroupId()));

		checkSocialActivity(BlogsActivityKeys.UPDATE_ENTRY, 1);
	}

	@Test
	public void testTrashToApproved() throws Exception {
		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_IN_TRASH, getServiceContext(entry),
			new HashMap<String, Serializable>());

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		Assert.assertTrue(isAssetEntryVisible(entry.getEntryId()));
		Assert.assertEquals(1, searchBlogsEntriesCount(group.getGroupId()));

		checkSocialActivity(BlogsActivityKeys.ADD_ENTRY, 1);
	}

	@Test
	public void testTrashToDraft() throws Exception {
		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_IN_TRASH, getServiceContext(entry),
			new HashMap<String, Serializable>());

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_DRAFT, getServiceContext(entry),
			new HashMap<String, Serializable>());

		Assert.assertFalse(isAssetEntryVisible(entry.getEntryId()));
		Assert.assertEquals(0, searchBlogsEntriesCount(group.getGroupId()));

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, getServiceContext(entry),
			new HashMap<String, Serializable>());

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_DRAFT, getServiceContext(entry),
			new HashMap<String, Serializable>());

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_IN_TRASH, getServiceContext(entry),
			new HashMap<String, Serializable>());

		BlogsEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_DRAFT, getServiceContext(entry),
			new HashMap<String, Serializable>());

		Assert.assertFalse(isAssetEntryVisible(entry.getEntryId()));
		Assert.assertEquals(0, searchBlogsEntriesCount(group.getGroupId()));
	}

	protected void checkSocialActivity(int activityType, int expectedCount)
		throws Exception {

		Thread.sleep(500 * TestPropsValues.JUNIT_DELAY_FACTOR);

		List<SocialActivity> socialActivities =
			SocialActivityLocalServiceUtil.getGroupActivities(
				group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int count = 0;

		for (SocialActivity socialActivity : socialActivities) {
			if ((activityType == ACTIVITY_KEY_ANY) ||
				(activityType == socialActivity.getType())) {

				count = count + 1;
			}
		}

		Assert.assertEquals(expectedCount, count);
	}

	protected ServiceContext getServiceContext(BlogsEntry entry)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		String[] trackbacks = StringUtil.split(entry.getTrackbacks());

		serviceContext.setAttribute("trackbacks", trackbacks);

		serviceContext.setCommand(Constants.UPDATE);

		String portletId = PortletProviderUtil.getPortletId(
			BlogsEntry.class.getName(), PortletProvider.Action.VIEW);

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			entry.getGroupId(), portletId);

		serviceContext.setLayoutFullURL(layoutFullURL);

		serviceContext.setScopeGroupId(entry.getGroupId());

		return serviceContext;
	}

	protected boolean isAssetEntryVisible(long blogsEntryId) throws Exception {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			BlogsEntry.class.getName(), blogsEntryId);

		return assetEntry.isVisible();
	}

	protected int searchBlogsEntriesCount(long groupId) throws Exception {
		Indexer<BlogsEntry> indexer = IndexerRegistryUtil.getIndexer(
			BlogsEntry.class);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(new long[] {groupId});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	protected static final int ACTIVITY_KEY_ANY = -1;

	protected BlogsEntry entry;

	@DeleteAfterTestRun
	protected Group group;

	@DeleteAfterTestRun
	protected User user;

}