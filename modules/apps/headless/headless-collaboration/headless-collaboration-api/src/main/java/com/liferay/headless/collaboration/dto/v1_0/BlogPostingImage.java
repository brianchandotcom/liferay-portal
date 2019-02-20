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

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface BlogPostingImage {

	public String getContentUrl();

	public void setContentUrl(
			String contentUrl);

	public void setContentUrl(
			UnsafeSupplier<String, Throwable>
				contentUrlUnsafeSupplier);
	public String getEncodingFormat();

	public void setEncodingFormat(
			String encodingFormat);

	public void setEncodingFormat(
			UnsafeSupplier<String, Throwable>
				encodingFormatUnsafeSupplier);
	public String getFileExtension();

	public void setFileExtension(
			String fileExtension);

	public void setFileExtension(
			UnsafeSupplier<String, Throwable>
				fileExtensionUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public Number getSizeInBytes();

	public void setSizeInBytes(
			Number sizeInBytes);

	public void setSizeInBytes(
			UnsafeSupplier<Number, Throwable>
				sizeInBytesUnsafeSupplier);
	public String getTitle();

	public void setTitle(
			String title);

	public void setTitle(
			UnsafeSupplier<String, Throwable>
				titleUnsafeSupplier);

}