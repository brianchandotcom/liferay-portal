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

package com.liferay.headless.blog.api.dto;

import com.liferay.aggregate.rating.apio.architect.identifier.AggregateRatingIdentifier;
import com.liferay.apio.architect.annotation.FieldMode;
import com.liferay.apio.architect.annotation.Id;
import com.liferay.apio.architect.annotation.Vocabulary;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.comment.apio.architect.identifier.CommentIdentifier;
import com.liferay.content.space.apio.architect.model.ContentSpace;
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.identifier.ClassNameClassPK;

import java.util.Date;
import java.util.List;

//clase abstracta con getter y setters en /dto/base
//clase impl con nada en /dto

/**
 * Represents a blog posting exposed through the API. See <a
 * href="https://schema.org/BlogPosting">BlogPosting </a> for more information.
 *
 * @author Alejandro Hernández
 * @author Víctor Galán
 * @review
 */
@Vocabulary.Type("BlogPosting")
public interface BlogPosting extends Identifier<Long> {

	@Vocabulary.Field("aggregateRating")
	@Vocabulary.LinkTo(resource = AggregateRatingIdentifier.class)
	public ClassNameClassPK getAggregateRatingId();

	/**
	 * Returns the blog posting's alternate headline. See <a
	 * href="https://schema.org/alternativeHeadline">alternativeHeadline </a>
	 * for more information.
	 *
	 * @return the alternate headline
	 * @review
	 */
	@Vocabulary.Field("alternativeHeadline")
	public String getAlternativeHeadline();

	/**
	 * Returns the blog posting's body. See <a
	 * href="https://schema.org/articleBody">articleBody </a> for more
	 * information.
	 *
	 * @return the blog posting's body
	 * @review
	 */
	@Vocabulary.Field("articleBody")
	public String getArticleBody();

	/**
	 * Returns the blog posting's cover image caption.
	 *
	 * @return the image caption
	 * @review
	 */
	@Vocabulary.Field("caption")
	public String getCaption();

	/**
	 * Returns a list of the blog posting's categories
	 *
	 * @return the categories
	 * @review
	 */
	@Vocabulary.Field(mode = FieldMode.READ_ONLY, value = "category")
	@Vocabulary.LinkTo(
		resource = CategoryIdentifier.class,
		resourceType = Vocabulary.LinkTo.ResourceType.CHILD_COLLECTION
	)
	public List<Long> getCategories();

	/**
	 * Returns the ID used as a parent for the category collection
	 *
	 * @return the category collection's ID
	 * @review
	 */
	@Vocabulary.Field(mode = FieldMode.WRITE_ONLY, value = "category")
	@Vocabulary.LinkTo(
		resource = CategoryIdentifier.class,
		resourceType = Vocabulary.LinkTo.ResourceType.CHILD_COLLECTION
	)
	public Long getCategoryId();

	/**
	 * Returns the ID used as a parent for the comment collection
	 *
	 * @return the category collection's ID
	 * @review
	 */
	@Vocabulary.Field("comment")
	@Vocabulary.LinkTo(
		resource = CommentIdentifier.class,
		resourceType = Vocabulary.LinkTo.ResourceType.CHILD_COLLECTION
	)
	public Long getCommentId();

	/**
	 * Returns the ID of the content space
	 *
	 * @return the content space's ID
	 * @review
	 */
	@Vocabulary.BidirectionalModel(
		field = @Vocabulary.Field("blogPosts"), modelClass = ContentSpace.class
	)
	@Vocabulary.Field("contentSpace")
	public Long getContentSpaceId();

	/**
	 * Returns the blog posting's creation date. See <a
	 * href="https://schema.org/dateCreated">dateCreated </a> for more
	 * information.
	 *
	 * @return the creation date
	 * @review
	 */
	@Vocabulary.Field("dateCreated")
	public Date getCreatedDate();

	/**
	 * Returns the ID of the blog posting's creator. See <a
	 * href="https://schema.org/creator">creator </a> for more information.
	 *
	 * @return the creator's ID
	 * @review
	 */
	@Vocabulary.Field("creator")
	@Vocabulary.LinkTo(resource = PersonIdentifier.class)
	public Long getCreatorId();

	/**
	 * Returns the blog posting's description. See <a
	 * href="https://schema.org/description">description </a> for more
	 * information.
	 *
	 * @return the headline
	 * @review
	 */
	@Vocabulary.Field("description")
	public String getDescription();

	/**
	 * Returns the encoding format of the blog post
	 *
	 * @return the encoding format
	 * @review
	 */
	@Vocabulary.Field("encodingFormat")
	public String getEncodingFormat();

	/**
	 * Returns the the blog posting's friendly URL
	 *
	 * @return the friendly URL
	 * @review
	 */
	@Vocabulary.Field("friendlyUrlPath")
	public String getFriendlyURLPath();

	/**
	 * Returns the blog posting's headline. See <a
	 * href="https://schema.org/headline">headline </a> for more information.
	 *
	 * @return the headline
	 * @review
	 */
	@Vocabulary.Field("headline")
	public String getHeadline();

	/**
	 * Returns the blog posting's ID
	 *
	 * @return the blog posting's ID
	 * @review
	 */
	@Id
	public Long getId();

	/**
	 * Returns the the blog posting's cover image ID
	 *
	 * @return the keywords
	 * @review
	 */
	@Vocabulary.Field("image")
	@Vocabulary.LinkTo(resource = MediaObjectIdentifier.class)
	public Long getImageId();

	/**
	 * Returns a list of the blog posting's keywords
	 *
	 * @return the keywords
	 * @review
	 */
	@Vocabulary.Field("keywords")
	public List<String> getKeywords();

	/**
	 * Returns the blog posting's modification date. See <a
	 * href="https://schema.org/dateModified">dateModified </a> for more
	 * information.
	 *
	 * @return the modification date
	 * @review
	 */
	@Vocabulary.Field("dateModified")
	public Date getModifiedDate();

	/**
	 * Returns the blog posting's publication date. See <a
	 * href="https://schema.org/datePublished">datePublished </a> for more
	 * information.
	 *
	 * @return the modification date
	 * @review
	 */
	@Vocabulary.Field("datePublished")
	public Date getPublishedDate();

}