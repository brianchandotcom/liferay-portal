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

package com.liferay.headless.document.library.resource.v1_0.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liferay.headless.document.library.dto.v1_0.Comment;
import com.liferay.headless.document.library.dto.v1_0.Creator;
import com.liferay.petra.function.UnsafeSupplier;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "Comment")
public class CommentImpl implements Comment {

	public Comment[] getComments() {
			return comments;
	}

	public void setComments(Comment[] comments) {
			this.comments = comments;
	}

	public void setCommentsWithSupplier(UnsafeSupplier<Comment[], Throwable> commentsUnsafeSupplier) {
	}

	@JsonProperty
	protected Comment[] comments;
	public Creator getCreator() {
			return creator;
	}

	public void setCreator(Creator creator) {
			this.creator = creator;
	}

	public void setCreatorWithSupplier(UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier) {
	}

	@JsonProperty
	protected Creator creator;
	public Long getId() {
			return id;
	}

	public void setId(Long id) {
			this.id = id;
	}

	public void setIdWithSupplier(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
	}

	@JsonProperty
	protected Long id;
	public String getText() {
			return text;
	}

	public void setText(String text) {
			this.text = text;
	}

	public void setTextWithSupplier(UnsafeSupplier<String, Throwable> textUnsafeSupplier) {
	}

	@JsonProperty
	protected String text;

}