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

package com.liferay.document.library.file.version.discussion.web.util;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.util.DLFileVersionPolicy;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true)
public class DiscussionDLFileVersionPolicy implements DLFileVersionPolicy {

	@Override
	public boolean isKeepFileVersionLabel(
			DLFileVersion lastDLFileVersion, DLFileVersion latestDLFileVersion,
			boolean majorVersion, ServiceContext serviceContext)
		throws PortalException {

		int commentsCount = _commentManager.getCommentsCount(
			DLFileVersion.class.getName(),
			latestDLFileVersion.getFileVersionId());

		if (commentsCount == 0) {
			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setCommentManager(CommentManager commentManager) {
		_commentManager = commentManager;
	}

	private CommentManager _commentManager;

}