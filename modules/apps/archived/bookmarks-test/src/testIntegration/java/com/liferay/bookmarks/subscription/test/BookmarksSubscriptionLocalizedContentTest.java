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

package com.liferay.bookmarks.subscription.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.constants.BookmarksConstants;
import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.bookmarks.test.util.BookmarksTestUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.subscription.test.util.BaseSubscriptionLocalizedContentTestCase;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class BookmarksSubscriptionLocalizedContentTest
	extends BaseSubscriptionLocalizedContentTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Override
	protected long addBaseModel(long userId, long containerModelId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), userId);

		BookmarksTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.ADD);

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.addEntry(
			userId, group.getGroupId(), containerModelId,
			RandomTestUtil.randomString(), "http://www.liferay.com",
			RandomTestUtil.randomString(), serviceContext);

		return entry.getEntryId();
	}

	@Override
	protected void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		BookmarksFolderLocalServiceUtil.subscribeFolder(
			user.getUserId(), group.getGroupId(), containerModelId);
	}

	@Override
	protected String getPortletId() {
		return BookmarksPortletKeys.BOOKMARKS;
	}

	@Override
	protected String getServiceName() {
		return BookmarksConstants.SERVICE_NAME;
	}

	@Override
	protected String getSubscriptionAddedBodyPreferenceName() {
		return "emailEntryAddedBody";
	}

	@Override
	protected String getSubscriptionUpdatedBodyPreferenceName() {
		return "emailEntryUpdatedBody";
	}

	@Override
	protected void updateBaseModel(long userId, long baseModelId)
		throws Exception {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(
			baseModelId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), userId);

		BookmarksTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.UPDATE);

		BookmarksEntryLocalServiceUtil.updateEntry(
			userId, entry.getEntryId(), entry.getGroupId(), entry.getFolderId(),
			RandomTestUtil.randomString(), entry.getUrl(),
			entry.getDescription(), serviceContext);
	}

}