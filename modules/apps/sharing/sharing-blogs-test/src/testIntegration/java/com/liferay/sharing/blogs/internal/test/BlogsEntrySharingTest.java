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

package com.liferay.sharing.blogs.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.contributor.PermissionSQLContributor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.sharing.security.permission.SharingPermissionChecker;
import com.liferay.sharing.test.util.BaseSharingTestCase;

import java.util.Date;

import org.junit.runner.RunWith;

/**
 * @author Sergio González
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class BlogsEntrySharingTest extends BaseSharingTestCase<BlogsEntry> {

	@Override
	protected void deleteModel(BlogsEntry blogsEntry) {
		_blogsEntryLocalService.deleteBlogsEntry(blogsEntry);
	}

	@Override
	protected String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	protected BlogsEntry getModel(User user, Group group)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);
		serviceContext.setScopeGroupId(group.getGroupId());

		return _blogsEntryLocalService.addEntry(
			user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), true, true,
			new String[0], StringPool.BLANK, null, null, serviceContext);
	}

	@Override
	protected int getModelCount(Group group) {
		return _blogsEntryLocalService.getGroupEntriesCount(
			group.getGroupId(),
			new QueryDefinition<>(
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null));
	}

	@Override
	protected ModelResourcePermission<BlogsEntry> getModelResourcePermission() {
		return _modelResourcePermission;
	}

	@Override
	protected BlogsEntry getPendingModel(User user, Group group)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		return _blogsEntryLocalService.addEntry(
			user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), true, true,
			new String[0], StringPool.BLANK, null, null, serviceContext);
	}

	@Override
	protected PermissionSQLContributor getPermissionSQLContributor() {
		return _permissionSQLContributor;
	}

	@Override
	protected SharingPermissionChecker getSharingPermissionChecker() {
		return _sharingPermissionChecker;
	}

	@Override
	protected void moveModelToTrash(BlogsEntry blogsEntry)
		throws PortalException {

		_blogsEntryLocalService.moveEntryToTrash(
			blogsEntry.getUserId(), blogsEntry);
	}

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject(filter = "model.class.name=com.liferay.blogs.model.BlogsEntry")
	private ModelResourcePermission<BlogsEntry> _modelResourcePermission;

	@Inject(filter = "model.class.name=com.liferay.blogs.model.BlogsEntry")
	private PermissionSQLContributor _permissionSQLContributor;

	@Inject(filter = "model.class.name=com.liferay.blogs.model.BlogsEntry")
	private SharingPermissionChecker _sharingPermissionChecker;

}