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

package com.liferay.headless.document.library.internal.resource.v1_0;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryService;
import com.liferay.headless.document.library.dto.v1_0.Comment;
import com.liferay.headless.document.library.internal.dto.v1_0.CommentUtil;
import com.liferay.headless.document.library.resource.v1_0.CommentResource;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/comment.properties",
	scope = ServiceScope.PROTOTYPE, service = CommentResource.class
)
public class CommentResourceImpl extends BaseCommentResourceImpl {

	@Override
	public Page<Comment> getDocumentCommentsPage(
			Long fileEntryId, Pagination pagination)
		throws Exception {

		DLFileEntry dlFileEntry = _dlFileEntryService.getFileEntry(fileEntryId);

		int count = _commentManager.getRootCommentsCount(
			dlFileEntry.getModelClassName(), fileEntryId,
			WorkflowConstants.STATUS_APPROVED);

		if (count == 0) {
			return Page.of(Collections.emptyList());
		}

		_checkViewPermission(
			dlFileEntry.getGroupId(), dlFileEntry.getModelClassName(),
			fileEntryId);

		return Page.of(
			transform(
				_commentManager.getRootComments(
					dlFileEntry.getModelClassName(), fileEntryId,
					WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition()),
				CommentUtil::toComment),
			pagination, count);
	}

	private void _checkViewPermission(
			long groupId, String className, long classPK)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(permissionChecker);

		discussionPermission.checkViewPermission(
			permissionChecker.getCompanyId(), groupId, className, classPK);
	}

	@Reference
	private CommentManager _commentManager;

	@Reference
	private DLFileEntryService _dlFileEntryService;

}