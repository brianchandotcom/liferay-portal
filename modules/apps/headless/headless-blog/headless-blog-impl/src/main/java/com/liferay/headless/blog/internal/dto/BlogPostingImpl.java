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

package com.liferay.headless.blog.internal.dto;

import com.liferay.portal.apio.identifier.ClassNameClassPK;

import java.util.Date;
import java.util.List;

/**
 * @author Víctor Galán
 */
public class BlogPostingImpl extends BaseBlogPosting {

	public BlogPostingImpl(
		long id, ClassNameClassPK aggregateRatingId, String alternativeHeadline,
		String articleBody, String caption, List<Long> categories,
		long categoryId, long commentId, long contentSpaceId, Date createDate,
		long creatorId, String description, String encodingFormat,
		String friendlyURLPath, String headline, long imageId,
		List<String> keywords, Date modifiedDate, Date publishedDate) {

		super(
			id, aggregateRatingId, alternativeHeadline, articleBody, caption,
			categories, categoryId, commentId, contentSpaceId, createDate,
			creatorId, description, encodingFormat, friendlyURLPath, headline,
			imageId, keywords, modifiedDate, publishedDate);
	}

}