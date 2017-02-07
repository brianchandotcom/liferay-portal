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

package com.liferay.comment.demo.data.creator.internal;

import com.liferay.comment.demo.data.creator.CommentDemoDataCreator;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(service = CommentDemoDataCreator.class)
public class CommentDemoDataCreatorImpl implements CommentDemoDataCreator {

	@Override
	public Comment create(long userId, ClassedModel classedModel)
		throws PortalException {
	}

	@Override
	public Comment create(long userId, long parentCommentId)
		throws PortalException {
	}

	@Override
	public void delete() throws PortalException {
	}

	@Reference(unbind = "-")
	protected void setCommentManager(CommentManager commentManager) {
		_commentManager = commentManager;
	}

	private CommentManager _commentManager;

}