/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.comment.demo.data.creator;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;

/**
 * @author Alejandro Hernández
 */
public interface CommentDemoDataCreator {

	public Comment create(long userId, ClassedModel classedModel)
		throws PortalException;

	public Comment create(long userId, long parentCommentId)
		throws PortalException;

	public void delete() throws PortalException;

}