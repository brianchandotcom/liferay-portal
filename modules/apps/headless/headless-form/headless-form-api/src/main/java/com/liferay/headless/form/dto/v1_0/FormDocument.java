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

package com.liferay.headless.form.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface FormDocument {

	public String getContentUrl();

	public String getEncodingFormat();

	public String getFileExtension();

	public Long getId();

	public Number getSizeInBytes();

	public String getTitle();

	public void setContentUrl(String contentUrl);

	public void setContentUrl(
		UnsafeSupplier<String, Throwable> contentUrlUnsafeSupplier);

	public void setEncodingFormat(String encodingFormat);

	public void setEncodingFormat(
		UnsafeSupplier<String, Throwable> encodingFormatUnsafeSupplier);

	public void setFileExtension(String fileExtension);

	public void setFileExtension(
		UnsafeSupplier<String, Throwable> fileExtensionUnsafeSupplier);

	public void setId(Long id);

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);

	public void setSizeInBytes(Number sizeInBytes);

	public void setSizeInBytes(
		UnsafeSupplier<Number, Throwable> sizeInBytesUnsafeSupplier);

	public void setTitle(String title);

	public void setTitle(UnsafeSupplier<String, Throwable> titleUnsafeSupplier);

}