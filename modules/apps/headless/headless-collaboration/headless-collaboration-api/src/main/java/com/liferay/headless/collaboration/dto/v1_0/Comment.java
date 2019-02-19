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

package com.liferay.headless.collaboration.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import java.util.Date;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface Comment {

	public Comment[] getComments();

	public Creator getCreator();

	public Date getDateCreated();

	public Date getDateModified();

	public Boolean getHasComments();

	public Long getId();

	public String getText();

	public void setComments(Comment[] comments);

	public void setComments(
		UnsafeSupplier<Comment[], Throwable> commentsUnsafeSupplier);

	public void setCreator(Creator creator);

	public void setCreator(
		UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier);

	public void setDateCreated(Date dateCreated);

	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier);

	public void setDateModified(Date dateModified);

	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier);

	public void setHasComments(Boolean hasComments);

	public void setHasComments(
		UnsafeSupplier<Boolean, Throwable> hasCommentsUnsafeSupplier);

	public void setId(Long id);

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);

	public void setText(String text);

	public void setText(UnsafeSupplier<String, Throwable> textUnsafeSupplier);

}