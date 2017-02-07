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
import com.liferay.comment.demo.data.creator.MultipleCommentDemoDataCreator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(service = MultipleCommentDemoDataCreator.class)
public class MultipleCommentDemoDataCreatorImpl
	implements MultipleCommentDemoDataCreator {

	@Override
	public void create(List<Long> userIds, ClassedModel classedModel)
		throws PortalException {
	}

	@Override
	public void delete() throws PortalException {
	}

	@Reference(unbind = "-")
	protected void setCommentDemoDataCreator(
		CommentDemoDataCreator commentDemoDataCreator) {

		_commentDemoDataCreator = commentDemoDataCreator;
	}

	private CommentDemoDataCreator _commentDemoDataCreator;

}