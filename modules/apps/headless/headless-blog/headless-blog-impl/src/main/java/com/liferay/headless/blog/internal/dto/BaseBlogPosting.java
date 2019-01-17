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

import com.liferay.headless.blog.api.dto.BlogPosting;
import com.liferay.portal.apio.identifier.ClassNameClassPK;

import java.util.Date;
import java.util.List;

/**
 * @author Víctor Galán
 */
public class BaseBlogPosting implements BlogPosting {

	public BaseBlogPosting(
		long id, ClassNameClassPK aggregateRatingId, String alternativeHeadline,
		String articleBody, String caption, List<Long> categories,
		long categoryId, long commentId, long contentSpaceId, Date createDate,
		long creatorId, String description, String encodingFormat,
		String friendlyURLPath, String headline, long imageId,
		List<String> keywords, Date modifiedDate, Date publishedDate) {

		_id = id;
		_aggregateRatingId = aggregateRatingId;
		_alternativeHeadline = alternativeHeadline;
		_articleBody = articleBody;
		_caption = caption;
		_categories = categories;
		_categoryId = categoryId;
		_commentId = commentId;
		_contentSpaceId = contentSpaceId;
		_createDate = createDate;
		_creatorId = creatorId;
		_description = description;
		_encodingFormat = encodingFormat;
		_friendlyURLPath = friendlyURLPath;
		_headline = headline;
		_imageId = imageId;
		_keywords = keywords;
		_modifiedDate = modifiedDate;
		_publishedDate = publishedDate;
	}

	@Override
	public ClassNameClassPK getAggregateRatingId() {
		return _aggregateRatingId;
	}

	@Override
	public String getAlternativeHeadline() {
		return _alternativeHeadline;
	}

	@Override
	public String getArticleBody() {
		return _articleBody;
	}

	@Override
	public String getCaption() {
		return _caption;
	}

	@Override
	public List<Long> getCategories() {
		return _categories;
	}

	@Override
	public Long getCategoryId() {
		return _categoryId;
	}

	@Override
	public Long getCommentId() {
		return _commentId;
	}

	@Override
	public Long getContentSpaceId() {
		return _contentSpaceId;
	}

	@Override
	public Date getCreatedDate() {
		return _createDate;
	}

	@Override
	public Long getCreatorId() {
		return _creatorId;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String getEncodingFormat() {
		return _encodingFormat;
	}

	@Override
	public String getFriendlyURLPath() {
		return _friendlyURLPath;
	}

	@Override
	public String getHeadline() {
		return _headline;
	}

	@Override
	public Long getId() {
		return _id;
	}

	@Override
	public Long getImageId() {
		return _imageId;
	}

	@Override
	public List<String> getKeywords() {
		return _keywords;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public Date getPublishedDate() {
		return _publishedDate;
	}

	public void setAggregateRatingId(ClassNameClassPK aggregateRatingId) {
		_aggregateRatingId = aggregateRatingId;
	}

	public void setAlternativeHeadline(String alternativeHeadline) {
		_alternativeHeadline = alternativeHeadline;
	}

	public void setArticleBody(String articleBody) {
		_articleBody = articleBody;
	}

	public void setCaption(String caption) {
		_caption = caption;
	}

	public void setCategories(List<Long> categories) {
		_categories = categories;
	}

	public void setCategoryId(long categoryId) {
		_categoryId = categoryId;
	}

	public void setCommentId(long commentId) {
		_commentId = commentId;
	}

	public void setContentSpaceId(long contentSpaceId) {
		_contentSpaceId = contentSpaceId;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setCreatorId(long creatorId) {
		_creatorId = creatorId;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setEncodingFormat(String encodingFormat) {
		_encodingFormat = encodingFormat;
	}

	public void setFriendlyURLPath(String friendlyURLPath) {
		_friendlyURLPath = friendlyURLPath;
	}

	public void setHeadline(String headline) {
		_headline = headline;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setImageId(long imageId) {
		_imageId = imageId;
	}

	public void setKeywords(List<String> keywords) {
		_keywords = keywords;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		_publishedDate = publishedDate;
	}

	private ClassNameClassPK _aggregateRatingId;
	private String _alternativeHeadline;
	private String _articleBody;
	private String _caption;
	private List<Long> _categories;
	private long _categoryId;
	private long _commentId;
	private long _contentSpaceId;
	private Date _createDate;
	private long _creatorId;
	private String _description;
	private String _encodingFormat;
	private String _friendlyURLPath;
	private String _headline;
	private long _id;
	private long _imageId;
	private List<String> _keywords;
	private Date _modifiedDate;
	private Date _publishedDate;

}