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

package com.liferay.blogs.uad.entity;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.uad.constants.BlogsUADConstants;

import com.liferay.user.associated.data.entity.BaseUADEntity;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BlogsEntryUADEntity extends BaseUADEntity {
	public BlogsEntryUADEntity(long userId, String uadEntityId,
		BlogsEntry blogsEntry) {
		super(userId, uadEntityId, BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY);

		_blogsEntry = blogsEntry;
	}

	public BlogsEntry getBlogsEntry() {
		return _blogsEntry;
	}

	private final BlogsEntry _blogsEntry;
}