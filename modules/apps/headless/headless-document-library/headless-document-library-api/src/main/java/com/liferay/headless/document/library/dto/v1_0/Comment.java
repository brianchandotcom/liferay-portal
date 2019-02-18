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

package com.liferay.headless.document.library.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface Comment {

	public Comment[] getComments();

	public void setComments(Comment[] comments);

	public void setCommentsWithSupplier(UnsafeSupplier<Comment[], Throwable> commentsUnsafeSupplier);
	public Creator getCreator();

	public void setCreator(Creator creator);

	public void setCreatorWithSupplier(UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier);
	public Long getId();

	public void setId(Long id);

	public void setIdWithSupplier(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);
	public String getText();

	public void setText(String text);

	public void setTextWithSupplier(UnsafeSupplier<String, Throwable> textUnsafeSupplier);

}