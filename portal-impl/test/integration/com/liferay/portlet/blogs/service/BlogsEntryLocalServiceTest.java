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

package com.liferay.portlet.blogs.service;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.blogs.kernel.exception.EntryContentException;
import com.liferay.blogs.kernel.exception.EntryTitleException;
import com.liferay.blogs.kernel.exception.NoSuchEntryException;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.service.BlogsEntryLocalServiceUtil;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.blogs.constants.BlogsConstants;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;

import java.io.InputStream;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 * @author Manuel de la Peña
 */
@Sync
public class BlogsEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = TestPropsValues.getUser();
	}

	@Test
	public void testAddEntry() throws Exception {
		int initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _statusApprovedQueryDefinition);

		addEntry(false);

		int actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _statusApprovedQueryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test(expected = EntryContentException.class)
	public void testAddEntryWithVeryLongContent() throws Exception {
		int maxLength = ModelHintsUtil.getMaxLength(
			BlogsEntry.class.getName(), "content");

		String content = _repeat("0", maxLength + 1);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), RandomTestUtil.randomString(), content,
			new Date(), serviceContext);
	}

	@Test(expected = EntryTitleException.class)
	public void testAddEntryWithVeryLongTitle() throws Exception {
		int maxLength = ModelHintsUtil.getMaxLength(
			BlogsEntry.class.getName(), "title");

		String title = _repeat("0", maxLength + 1);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), title, RandomTestUtil.randomString(), new Date(),
			serviceContext);
	}

	@Test
	public void testAddOriginalImageInVisibleImageFolder() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), serviceContext);

		FileEntry tempFileEntry = getTempFileEntry(
			_user.getUserId(), _group.getGroupId(), "image.jpg");

		ImageSelector imageSelector = new ImageSelector(
			FileUtil.getBytes(tempFileEntry.getContentStream()),
			tempFileEntry.getTitle(), tempFileEntry.getMimeType(),
			StringPool.BLANK);

		long originalImageFileEntryId =
			BlogsEntryLocalServiceUtil.addOriginalImageFileEntry(
				_user.getUserId(), _group.getGroupId(), blogsEntry.getEntryId(),
				imageSelector);

		FileEntry portletFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				originalImageFileEntryId);

		Folder folder = portletFileEntry.getFolder();

		Assert.assertEquals(BlogsConstants.SERVICE_NAME, folder.getName());
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDeleteEntry() throws Exception {
		BlogsEntry entry = addEntry(false);

		BlogsEntryLocalServiceUtil.deleteEntry(entry);

		BlogsEntryLocalServiceUtil.getEntry(entry.getEntryId());
	}

	@Test
	public void testGetCompanyEntriesCountInTrash() throws Exception {
		testGetCompanyEntriesCount(true);
	}

	@Test
	public void testGetCompanyEntriesCountNotInTrash() throws Exception {
		testGetCompanyEntriesCount(false);
	}

	@Test
	public void testGetCompanyEntriesInTrash() throws Exception {
		testGetCompanyEntries(true);
	}

	@Test
	public void testGetCompanyEntriesNotInTrash() throws Exception {
		testGetCompanyEntries(false);
	}

	@Test
	public void testGetDiscussionMessageDisplay() throws Exception {
		BlogsEntry entry = addEntry(false);

		MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
			_user.getUserId(), _group.getGroupId(), BlogsEntry.class.getName(),
			entry.getEntryId(), WorkflowConstants.STATUS_ANY);
	}

	@Test
	public void testGetEntriesPrevAndNextByDisplayDate() throws Exception {
		BlogsEntry firstEntry = addEntry(false, 1);

		BlogsEntry thirdEntry = addEntry(false, 3);

		BlogsEntry secondEntry = addEntry(false, 2);

		BlogsEntry[] entries = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
			secondEntry.getEntryId());

		Assert.assertNotNull(
			"The previous entry relative to entry " + secondEntry.getEntryId() +
				" should be " + firstEntry.getEntryId() + " but is null",
			entries[0]);
		Assert.assertNotNull(
			"The current entry relative to entry " + secondEntry.getEntryId() +
				" should be " + secondEntry.getEntryId() + " but is null",
			entries[1]);
		Assert.assertNotNull(
			"The next entry relative to entry " + secondEntry.getEntryId() +
				" should be " + thirdEntry.getEntryId() + " but is null",
			entries[2]);
		Assert.assertEquals(
			"The previous entry relative to entry " + secondEntry.getEntryId() +
				" should be " + firstEntry.getEntryId(),
			entries[0].getEntryId(), firstEntry.getEntryId());
		Assert.assertEquals(
			"The current entry relative to entry " + secondEntry.getEntryId() +
				" should be " + secondEntry.getEntryId(),
			entries[1].getEntryId(), secondEntry.getEntryId());
		Assert.assertEquals(
			"The next entry relative to entry " + secondEntry.getEntryId() +
				" should be " + thirdEntry.getEntryId(),
			entries[2].getEntryId(), thirdEntry.getEntryId());
	}

	@Test
	public void testGetEntriesPrevAndNextRelativeToCurrentEntry()
		throws Exception {

		BlogsEntry previousEntry = addEntry(false);

		BlogsEntry currentEntry = addEntry(false);

		BlogsEntry nextEntry = addEntry(false);

		BlogsEntry[] entries = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
			currentEntry.getEntryId());

		Assert.assertNotNull(
			"The previous entry relative to entry " +
				currentEntry.getEntryId() + " should be " +
					previousEntry.getEntryId() + " but is null",
			entries[0]);
		Assert.assertNotNull(
			"The current entry relative to entry " + currentEntry.getEntryId() +
				" should be " + currentEntry.getEntryId() + " but is null",
			entries[1]);
		Assert.assertNotNull(
			"The next entry relative to entry " + currentEntry.getEntryId() +
				" should be " + nextEntry.getEntryId() + " but is null",
			entries[2]);
		Assert.assertEquals(
			"The previous entry relative to entry" + currentEntry.getEntryId() +
				" should be " +
					previousEntry.getEntryId(),
			entries[0].getEntryId(), previousEntry.getEntryId());
		Assert.assertEquals(
			"The current entry relative to entry " + currentEntry.getEntryId() +
				" should be " + currentEntry.getEntryId(),
			entries[1].getEntryId(), currentEntry.getEntryId());
		Assert.assertEquals(
			"The next entry relative to entry " + currentEntry.getEntryId() +
				" should be " + nextEntry.getEntryId(),
			entries[2].getEntryId(), nextEntry.getEntryId());
	}

	@Test
	public void testGetEntriesPrevAndNextRelativeToNextEntry()
		throws Exception {

		addEntry(false);

		BlogsEntry currentEntry = addEntry(false);

		BlogsEntry nextEntry = addEntry(false);

		BlogsEntry[] entries = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
			nextEntry.getEntryId());

		Assert.assertNull(
			"The next entry relative to entry " + nextEntry.getEntryId() +
				" should be null",
			entries[2]);
		Assert.assertNotNull(
			"The current entry relative to entry " + nextEntry.getEntryId() +
				" should be " + nextEntry.getEntryId() + " but is null",
			entries[1]);
		Assert.assertNotNull(
			"The previous entry relative to entry " + nextEntry.getEntryId() +
				" should be " + currentEntry.getEntryId() + " but is null",
			entries[0]);
		Assert.assertEquals(
			"The previous entry relative to entry " + nextEntry.getEntryId() +
				" should be " + currentEntry.getEntryId(),
			entries[0].getEntryId(), currentEntry.getEntryId());
		Assert.assertEquals(
			"The current entry relative to entry" + nextEntry.getEntryId() +
				" should be " + nextEntry.getEntryId(),
			entries[1].getEntryId(), nextEntry.getEntryId());
	}

	@Test
	public void testGetEntriesPrevAndNextRelativeToPreviousEntry()
		throws Exception {

		BlogsEntry previousEntry = addEntry(false);

		BlogsEntry currentEntry = addEntry(false);

		addEntry(false);

		BlogsEntry[] entries = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
			previousEntry.getEntryId());

		Assert.assertNull(
			"The previous entry relative to entry " +
				previousEntry.getEntryId() + " should be null",
			entries[0]);
		Assert.assertNotNull(
			"The current entry relative to entry " +
				previousEntry.getEntryId() + " should be " +
					previousEntry.getEntryId() + " but is null",
			entries[1]);
		Assert.assertNotNull(
			"The next entry relative to entry " + previousEntry.getEntryId() +
				" should be " + currentEntry.getEntryId() + " but is null",
			entries[2]);
		Assert.assertEquals(
			"The current entry relative to entry " +
				previousEntry.getEntryId() + " should be " +
					previousEntry.getEntryId(),
			entries[1].getEntryId(), previousEntry.getEntryId());
		Assert.assertEquals(
			"The next entry relative to entry " + previousEntry.getEntryId() +
				" should be " + currentEntry.getEntryId(),
			entries[2].getEntryId(), currentEntry.getEntryId());
	}

	@Test
	public void testGetEntryByGroupAndUrlTitle() throws Exception {
		BlogsEntry expectedEntry = addEntry(false);

		BlogsEntry actualEntry = BlogsEntryLocalServiceUtil.getEntry(
			expectedEntry.getGroupId(), expectedEntry.getUrlTitle());

		BlogsTestUtil.assertEquals(expectedEntry, actualEntry);
	}

	@Test
	public void testGetGroupEntriesCountInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(true, true);
	}

	@Test
	public void testGetGroupEntriesCountInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(true, false);
	}

	@Test
	public void testGetGroupEntriesCountNotInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(false, true);
	}

	@Test
	public void testGetGroupEntriesCountNotInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(false, false);
	}

	@Test
	public void testGetGroupEntriesInTrashWithDisplayDate() throws Exception {
		testGetGroupEntries(true, true);
	}

	@Test
	public void testGetGroupEntriesInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntries(true, false);
	}

	@Test
	public void testGetGroupEntriesNotInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntries(false, true);
	}

	@Test
	public void testGetGroupEntriesNotInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntries(false, false);
	}

	@Test
	public void testGetGroupsEntries() throws Exception {
		List<BlogsEntry> groupsEntries =
			BlogsEntryLocalServiceUtil.getGroupsEntries(
				_user.getCompanyId(), _group.getGroupId(), new Date(),
				_statusInTrashQueryDefinition);

		int initialCount = groupsEntries.size();

		addEntry(false);
		addEntry(true);

		List<BlogsEntry> groupsEntriesInTrash =
			BlogsEntryLocalServiceUtil.getGroupsEntries(
				_user.getCompanyId(), _group.getGroupId(), new Date(),
				_statusInTrashQueryDefinition);

		Assert.assertEquals(initialCount + 1, groupsEntriesInTrash.size());

		for (BlogsEntry groupsEntry : groupsEntriesInTrash) {
			Assert.assertEquals(
				"Entry " + groupsEntry.getEntryId() + " is not in trash",
				WorkflowConstants.STATUS_IN_TRASH, groupsEntry.getStatus());
			Assert.assertEquals(
				"Entry belongs to company " + groupsEntry.getCompanyId() +
					" but should belong to company " + _user.getCompanyId(),
				_user.getCompanyId(), groupsEntry.getCompanyId());
		}
	}

	@Test
	public void testGetGroupUserEntriesCountInTrash() throws Exception {
		testGetGroupUserEntriesCount(true);
	}

	@Test
	public void testGetGroupUserEntriesCountNotInTrash() throws Exception {
		testGetGroupUserEntriesCount(false);
	}

	@Test
	public void testGetGroupUserEntriesInTrash() throws Exception {
		testGetGroupUserEntries(true);
	}

	@Test
	public void testGetGroupUserEntriesNotInTrash() throws Exception {
		testGetGroupUserEntries(false);
	}

	@Test
	public void testGetNoAssetEntries() throws Exception {
		BlogsEntry entry = addEntry(false);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			BlogsEntry.class.getName(), entry.getEntryId());

		Assert.assertNotNull(assetEntry);

		AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);

		List<BlogsEntry> entries =
			BlogsEntryLocalServiceUtil.getNoAssetEntries();

		Assert.assertEquals(1, entries.size());
		Assert.assertEquals(entry, entries.get(0));
	}

	@Test
	public void testGetOrganizationEntriesCountInTrash() throws Exception {
		testGetOrganizationEntriesCount(true);
	}

	@Test
	public void testGetOrganizationEntriesCountNotInTrash() throws Exception {
		testGetOrganizationEntriesCount(false);
	}

	@Test
	public void testGetOrganizationEntriesInTrash() throws Exception {
		testGetOrganizationEntries(true);
	}

	@Test
	public void testGetOrganizationEntriesNotInTrash() throws Exception {
		testGetOrganizationEntries(false);
	}

	@Test
	public void testSubscribe() throws Exception {
		int initialCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				_user.getUserId());

		BlogsEntryLocalServiceUtil.subscribe(
			_user.getUserId(), _group.getGroupId());

		int actualCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				_user.getUserId());

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testUnsubscribe() throws Exception {
		int initialCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				_user.getUserId());

		BlogsEntryLocalServiceUtil.subscribe(
			_user.getUserId(), _group.getGroupId());

		BlogsEntryLocalServiceUtil.unsubscribe(
			_user.getUserId(), _group.getGroupId());

		int actualCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				_user.getUserId());

		Assert.assertEquals(initialCount, actualCount);
	}

	@Test
	public void testUpdateEntryResources() throws Exception {
		BlogsEntry entry = addEntry(false);

		BlogsEntryLocalServiceUtil.updateEntryResources(
			entry, new String[] {ActionKeys.ADD_DISCUSSION}, null);
	}

	protected BlogsEntry addEntry(boolean statusInTrash) throws Exception {
		return addEntry(_user.getUserId(), statusInTrash);
	}

	protected BlogsEntry addEntry(boolean statusInTrash, int date)
		throws Exception {

		return addEntry(_user.getUserId(), statusInTrash, date);
	}

	protected BlogsEntry addEntry(long userId, boolean statusInTrash)
		throws Exception {

		return addEntry(userId, statusInTrash, 1);
	}

	protected BlogsEntry addEntry(long userId, boolean statusInTrash, int date)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), userId);

		Calendar displayDateCalendar = CalendarFactoryUtil.getCalendar(
			2012, 1, date);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), displayDateCalendar.getTime(),
			serviceContext);

		if (statusInTrash) {
			entry = BlogsEntryLocalServiceUtil.moveEntryToTrash(userId, entry);
		}

		return entry;
	}

	protected void assertBlogsEntriesStatus(
		List<BlogsEntry> entries, boolean statusInTrash) {

		for (BlogsEntry entry : entries) {
			if (statusInTrash) {
				Assert.assertEquals(
					"The entry " + entry.getEntryId() + " should be in trash",
					WorkflowConstants.STATUS_IN_TRASH, entry.getStatus());
			}
			else {
				Assert.assertNotEquals(
					"The entry " + entry.getEntryId() +
						" should not be in trash",
					WorkflowConstants.STATUS_IN_TRASH, entry.getStatus());
			}
		}
	}

	protected FileEntry getTempFileEntry(
			long userId, long groupId, String title)
		throws PortalException {

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/portal/util/dependencies/test.jpg");

		return TempFileEntryUtil.addTempFileEntry(
			groupId, userId, BlogsEntry.class.getName(), title, inputStream,
			MimeTypesUtil.getContentType(title));
	}

	protected void testGetCompanyEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		List<BlogsEntry> initialEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				_user.getCompanyId(), new Date(), queryDefinition);

		int initialCount = initialEntries.size();

		addEntry(false);
		addEntry(true);

		List<BlogsEntry> actualEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				_user.getCompanyId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetCompanyEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		int initialCount = BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
			_user.getCompanyId(), new Date(), queryDefinition);

		addEntry(false);
		addEntry(true);

		int actualCount = BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
			_user.getCompanyId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	protected void testGetGroupEntries(
			boolean statusInTrash, boolean displayDate)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		List<BlogsEntry> initialEntries = null;

		if (displayDate) {
			initialEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			initialEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), queryDefinition);
		}

		int initialCount = initialEntries.size();

		addEntry(false);
		addEntry(true);

		List<BlogsEntry> actualEntries = null;

		if (displayDate) {
			actualEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			actualEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), queryDefinition);
		}

		Assert.assertEquals(initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetGroupEntriesCount(
			boolean statusInTrash, boolean displayDate)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		int initialCount = 0;

		if (displayDate) {
			initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), queryDefinition);
		}

		addEntry(false);
		addEntry(true);

		int actualCount = 0;

		if (displayDate) {
			actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), queryDefinition);
		}

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	protected void testGetGroupUserEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		List<BlogsEntry> initialEntries =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				_group.getGroupId(), _user.getUserId(), new Date(),
				queryDefinition);

		int initialCount = initialEntries.size();

		addEntry(false);
		addEntry(true);

		List<BlogsEntry> actualEntries =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				_group.getGroupId(), _user.getUserId(), new Date(),
				queryDefinition);

		Assert.assertEquals(initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetGroupUserEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		int initialCount = BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
			_group.getGroupId(), _user.getUserId(), new Date(),
			queryDefinition);

		addEntry(false);
		addEntry(true);

		int actualCount = BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
			_group.getGroupId(), _user.getUserId(), new Date(),
			queryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	protected void testGetOrganizationEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		Organization organization = OrganizationTestUtil.addOrganization();

		User user = UserTestUtil.addOrganizationOwnerUser(organization);

		List<BlogsEntry> initialEntries =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				organization.getOrganizationId(), new Date(), queryDefinition);

		int initialCount = initialEntries.size();

		addEntry(user.getUserId(), false);
		addEntry(user.getUserId(), true);

		List<BlogsEntry> actualEntries =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				organization.getOrganizationId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetOrganizationEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition<BlogsEntry> queryDefinition =
			_statusInTrashQueryDefinition;

		if (!statusInTrash) {
			queryDefinition = _statusAnyQueryDefinition;
		}

		Organization organization = OrganizationTestUtil.addOrganization();

		User user = UserTestUtil.addOrganizationOwnerUser(organization);

		int initialCount =
			BlogsEntryLocalServiceUtil.getOrganizationEntriesCount(
				organization.getOrganizationId(), new Date(), queryDefinition);

		addEntry(user.getUserId(), false);
		addEntry(user.getUserId(), true);

		int actualCount =
			BlogsEntryLocalServiceUtil.getOrganizationEntriesCount(
				organization.getOrganizationId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	private static String _repeat(String string, int times) {
		StringBundler sb = new StringBundler(times);

		for (int i = 0; i < times; i++) {
			sb.append(string);
		}

		return sb.toString();
	}

	@DeleteAfterTestRun
	private Group _group;

	private final QueryDefinition<BlogsEntry> _statusAnyQueryDefinition =
		new QueryDefinition<BlogsEntry>(
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	private final QueryDefinition<BlogsEntry> _statusApprovedQueryDefinition =
		new QueryDefinition<BlogsEntry>(
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	private final QueryDefinition<BlogsEntry> _statusInTrashQueryDefinition =
		new QueryDefinition<BlogsEntry>(
			WorkflowConstants.STATUS_IN_TRASH, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	private User _user;

}