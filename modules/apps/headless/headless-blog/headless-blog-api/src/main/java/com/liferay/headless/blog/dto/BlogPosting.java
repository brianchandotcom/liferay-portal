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

package com.liferay.headless.blog.dto;

import com.liferay.apio.architect.annotation.Id;
import com.liferay.apio.architect.annotation.Vocabulary;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.headless.content.space.dto.ContentSpace;

import java.util.Date;
import java.util.List;

/**
 * @author Alejandro Hernández
 * @author Víctor Galán
 * @generated
 */
@Vocabulary.Type("BlogPosting")
public interface BlogPosting extends Identifier<Long> {

	@Vocabulary.Field("alternativeHeadline")
	public String getAlternativeHeadline();

	@Vocabulary.Field("articleBody")
	public String getArticleBody();

	@Vocabulary.Field("caption")
	public String getCaption();

	@Vocabulary.BidirectionalModel(
		field = @Vocabulary.Field("blogPosts"), modelClass = ContentSpace.class
	)
	@Vocabulary.Field("contentSpace")
	public Long getContentSpaceId();

	@Vocabulary.Field("dateCreated")
	public Date getCreatedDate();

	@Vocabulary.Field("description")
	public String getDescription();

	@Vocabulary.Field("encodingFormat")
	public String getEncodingFormat();

	@Vocabulary.Field("friendlyUrlPath")
	public String getFriendlyURLPath();

	@Vocabulary.Field("headline")
	public String getHeadline();

	@Id
	public Long getId();

	@Vocabulary.Field("keywords")
	public List<String> getKeywords();

	@Vocabulary.Field("dateModified")
	public Date getModifiedDate();

	@Vocabulary.Field("datePublished")
	public Date getPublishedDate();

}