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

import com.liferay.headless.document.library.dto.v1_0.Comment;
import com.liferay.headless.document.library.dto.v1_0.Creator;
import com.liferay.petra.function.UnsafeSupplier;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "Comment")
public class CommentImpl implements Comment {

	@Override
	public Comment[] getComments() {
		return comments;
	}

	@Override
	public Creator getCreator() {
		return creator;
	}

	@Override
	public Date getDateCreated() {
		return dateCreated;
	}

	@Override
	public Date getDateModified() {
		return dateModified;
	}

	@Override
	public Boolean getHasComments() {
		return hasComments;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setComments(Comment[] comments) {
		this.comments = comments;
	}

	@Override
	public void setComments(
		UnsafeSupplier<Comment[], Throwable> commentsUnsafeSupplier) {
	}

	@Override
	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	@Override
	public void setCreator(
		UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier) {
	}

	@Override
	public void setDateCreated(Date dateCreated) {
	}

	@Override
	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {
	}

	@Override
	public void setDateModified(Date dateModified) {
	}

	@Override
	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {
	}

	@Override
	public void setHasComments(Boolean hasComments) {
	}

	@Override
	public void setHasComments(
		UnsafeSupplier<Boolean, Throwable> hasCommentsUnsafeSupplier) {
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setId(
		UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void setText(
		UnsafeSupplier<String, Throwable> textUnsafeSupplier) {
	}

	protected Date dateCreated;
	protected Date dateModified;
	protected Boolean hasComments;
	protected Comment[] comments;
	protected Creator creator;
	protected Long id;
	protected String text;

}