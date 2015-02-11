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

package com.liferay.bookmarks.subscription;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.bookmarks.util.BookmarksTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousMailTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.subscriptions.test.BaseSubscriptionBaseModelTestCase;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Roberto Díaz
 */
@Sync
public class BookmarksSubscriptionBaseModelTest
	extends BaseSubscriptionBaseModelTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Override
	protected long addBaseModel(long containerModelId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			containerModelId, true, serviceContext);

		return entry.getEntryId();
	}

	@Override
	protected long addContainerModel(long containerModelId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		_folder = BookmarksTestUtil.addFolder(
			containerModelId, RandomTestUtil.randomString(), serviceContext);

		return _folder.getFolderId();
	}

	@Override
	protected void addSubscriptionBaseModel(long baseModelId) throws Exception {
		BookmarksEntryLocalServiceUtil.subscribeEntry(
			user.getUserId(), baseModelId);
	}

	@Override
	protected void removeContainerModelResourceViewPermission()
		throws Exception {

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, BookmarksFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_folder.getFolderId()), ActionKeys.VIEW);

		RoleTestUtil.removeResourcePermission(
			RoleConstants.SITE_MEMBER, BookmarksFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_folder.getFolderId()), ActionKeys.VIEW);
	}

	@Override
	protected void updateBaseModel(long baseModelId) throws Exception {
		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(
			baseModelId);

		BookmarksTestUtil.updateEntry(entry);
	}

	private BookmarksFolder _folder;

}