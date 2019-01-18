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

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BaseBlogPosting implements BlogPosting {

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
	public Long getContentSpaceId() {
		return _contentSpaceId;
	}

	@Override
	public Date getCreatedDate() {
		return _createDate;
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

	public void setAlternativeHeadline(String alternativeHeadline) {
		_alternativeHeadline = alternativeHeadline;
	}

	public void setArticleBody(String articleBody) {
		_articleBody = articleBody;
	}

	public void setCaption(String caption) {
		_caption = caption;
	}

	public void setContentSpaceId(long contentSpaceId) {
		_contentSpaceId = contentSpaceId;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
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

	public void setKeywords(List<String> keywords) {
		_keywords = keywords;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		_publishedDate = publishedDate;
	}

	private String _alternativeHeadline;
	private String _articleBody;
	private String _caption;
	private long _contentSpaceId;
	private Date _createDate;
	private String _description;
	private String _encodingFormat;
	private String _friendlyURLPath;
	private String _headline;
	private long _id;
	private List<String> _keywords;
	private Date _modifiedDate;
	private Date _publishedDate;

}