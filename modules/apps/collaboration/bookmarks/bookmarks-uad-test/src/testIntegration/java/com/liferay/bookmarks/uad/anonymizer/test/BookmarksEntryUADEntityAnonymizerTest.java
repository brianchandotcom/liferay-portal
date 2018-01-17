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

package com.liferay.bookmarks.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.exception.NoSuchEntryException;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.bookmarks.uad.test.BaseBookmarksEntryUADEntityTestCase;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class BookmarksEntryUADEntityAnonymizerTest
	extends BaseBookmarksEntryUADEntityTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_defaultUser = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAutoAnonymize() throws Exception {
		BookmarksEntry bookmarksEntry = addBookmarksEntry(_user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		_uadEntityAnonymizer.autoAnonymize(uadEntities.get(0));

		bookmarksEntry = bookmarksEntryLocalService.getEntry(
			bookmarksEntry.getEntryId());

		Assert.assertEquals(
			_defaultUser.getUserId(), bookmarksEntry.getUserId());
		Assert.assertEquals(
			_defaultUser.getFullName(), bookmarksEntry.getUserName());
	}

	@Test
	public void testAutoAnonymizeAll() throws Exception {
		BookmarksEntry bookmarksEntry = addBookmarksEntry(
			TestPropsValues.getUserId());
		BookmarksEntry bookmarksEntryAutoAnonymize = addBookmarksEntry(
			_user.getUserId());

		_uadEntityAnonymizer.autoAnonymizeAll(_user.getUserId());

		bookmarksEntryAutoAnonymize = bookmarksEntryLocalService.getEntry(
			bookmarksEntryAutoAnonymize.getEntryId());

		Assert.assertEquals(
			_defaultUser.getUserId(), bookmarksEntryAutoAnonymize.getUserId());
		Assert.assertEquals(
			_defaultUser.getFullName(),
			bookmarksEntryAutoAnonymize.getUserName());

		bookmarksEntry = bookmarksEntryLocalService.getEntry(
			bookmarksEntry.getEntryId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), bookmarksEntry.getUserId());
	}

	@Test
	public void testAutoAnonymizeAllNoBookmarksEntries() throws Exception {
		_uadEntityAnonymizer.autoAnonymizeAll(_user.getUserId());
	}

	@Test
	public void testAutoAnonymizeStatusByUserOnly() throws Exception {
		User user = TestPropsValues.getUser();

		BookmarksEntry bookmarksEntry = addBookmarksEntry(user.getUserId());

		bookmarksEntryLocalService.updateStatus(
			_user.getUserId(), bookmarksEntry,
			WorkflowConstants.STATUS_APPROVED);

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		_uadEntityAnonymizer.autoAnonymize(uadEntities.get(0));

		bookmarksEntry = bookmarksEntryLocalService.getEntry(
			bookmarksEntry.getEntryId());

		Assert.assertEquals(
			_defaultUser.getUserId(), bookmarksEntry.getStatusByUserId());
		Assert.assertEquals(
			_defaultUser.getScreenName(), bookmarksEntry.getStatusByUserName());
		Assert.assertEquals(user.getUserId(), bookmarksEntry.getUserId());
		Assert.assertEquals(user.getFullName(), bookmarksEntry.getUserName());
	}

	@Test
	public void testAutoAnonymizeUserOnly() throws Exception {
		BookmarksEntry bookmarksEntry = addBookmarksEntry(_user.getUserId());

		User user = TestPropsValues.getUser();

		bookmarksEntryLocalService.updateStatus(
			user.getUserId(), bookmarksEntry,
			WorkflowConstants.STATUS_APPROVED);

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		_uadEntityAnonymizer.autoAnonymize(uadEntities.get(0));

		bookmarksEntry = bookmarksEntryLocalService.getEntry(
			bookmarksEntry.getEntryId());

		Assert.assertEquals(
			user.getUserId(), bookmarksEntry.getStatusByUserId());
		Assert.assertEquals(
			user.getScreenName(), bookmarksEntry.getStatusByUserName());
		Assert.assertEquals(
			_defaultUser.getUserId(), bookmarksEntry.getUserId());
		Assert.assertEquals(
			_defaultUser.getFullName(), bookmarksEntry.getUserName());
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDelete() throws Exception {
		BookmarksEntry bookmarksEntry = addBookmarksEntry(_user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		_uadEntityAnonymizer.delete(uadEntities.get(0));

		bookmarksEntryLocalService.getEntry(bookmarksEntry.getEntryId());
	}

	@Test
	public void testDeleteAll() throws Exception {
		BookmarksEntry bookmarksEntry = addBookmarksEntry(
			TestPropsValues.getUserId());
		BookmarksEntry bookmarksEntryDelete = addBookmarksEntry(
			_user.getUserId());

		_uadEntityAnonymizer.deleteAll(_user.getUserId());

		bookmarksEntry = bookmarksEntryLocalService.getEntry(
			bookmarksEntry.getEntryId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), bookmarksEntry.getUserId());

		bookmarksEntryDelete = bookmarksEntryLocalService.fetchBookmarksEntry(
			bookmarksEntryDelete.getEntryId());

		Assert.assertNull(bookmarksEntryDelete);
	}

	@Test
	public void testDeleteAllNoBookmarksEntries() throws Exception {
		_uadEntityAnonymizer.deleteAll(_user.getUserId());
	}

	private User _defaultUser;

	@Inject(
		filter = "model.class.name=" + BookmarksUADConstants.BOOKMARKS_ENTRY
	)
	private UADEntityAggregator _uadEntityAggregator;

	@Inject(
		filter = "model.class.name=" + BookmarksUADConstants.BOOKMARKS_ENTRY
	)
	private UADEntityAnonymizer _uadEntityAnonymizer;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}