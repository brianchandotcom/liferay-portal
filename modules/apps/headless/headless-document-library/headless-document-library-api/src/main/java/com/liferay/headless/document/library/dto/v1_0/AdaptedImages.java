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
public interface AdaptedImages {

	public String getContentUrl();

	public void setContentUrl(
			String contentUrl);

	public void setContentUrl(
			UnsafeSupplier<String, Throwable>
				contentUrlUnsafeSupplier);
	public Number getHeight();

	public void setHeight(
			Number height);

	public void setHeight(
			UnsafeSupplier<Number, Throwable>
				heightUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public String getResolutionName();

	public void setResolutionName(
			String resolutionName);

	public void setResolutionName(
			UnsafeSupplier<String, Throwable>
				resolutionNameUnsafeSupplier);
	public Number getSizeInBytes();

	public void setSizeInBytes(
			Number sizeInBytes);

	public void setSizeInBytes(
			UnsafeSupplier<Number, Throwable>
				sizeInBytesUnsafeSupplier);
	public Number getWidth();

	public void setWidth(
			Number width);

	public void setWidth(
			UnsafeSupplier<Number, Throwable>
				widthUnsafeSupplier);

}