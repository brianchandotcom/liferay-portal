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

import java.util.Date;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface Document {

	public AdaptedMedia[] getAdaptedMedia();

	public void setAdaptedMedia(AdaptedMedia[] adaptedMedia);

	public void setAdaptedMediaWithSupplier(UnsafeSupplier<AdaptedMedia[], Throwable> adaptedMediaUnsafeSupplier);
	public Long[] getCategory();

	public void setCategory(Long[] category);

	public void setCategoryWithSupplier(UnsafeSupplier<Long[], Throwable> categoryUnsafeSupplier);
	public String getContentUrl();

	public void setContentUrl(String contentUrl);

	public void setContentUrlWithSupplier(UnsafeSupplier<String, Throwable> contentUrlUnsafeSupplier);
	public Creator getCreator();

	public void setCreator(Creator creator);

	public void setCreatorWithSupplier(UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier);
	public Date getDateCreated();

	public void setDateCreated(Date dateCreated);

	public void setDateCreatedWithSupplier(UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier);
	public Date getDateModified();

	public void setDateModified(Date dateModified);

	public void setDateModifiedWithSupplier(UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier);
	public String getDescription();

	public void setDescription(String description);

	public void setDescriptionWithSupplier(UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier);
	public String getEncodingFormat();

	public void setEncodingFormat(String encodingFormat);

	public void setEncodingFormatWithSupplier(UnsafeSupplier<String, Throwable> encodingFormatUnsafeSupplier);
	public String getFileExtension();

	public void setFileExtension(String fileExtension);

	public void setFileExtensionWithSupplier(UnsafeSupplier<String, Throwable> fileExtensionUnsafeSupplier);
	public Long getFolderId();

	public void setFolderId(Long folderId);

	public void setFolderIdWithSupplier(UnsafeSupplier<Long, Throwable> folderIdUnsafeSupplier);
	public Long getId();

	public void setId(Long id);

	public void setIdWithSupplier(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);
	public String[] getKeywords();

	public void setKeywords(String[] keywords);

	public void setKeywordsWithSupplier(UnsafeSupplier<String[], Throwable> keywordsUnsafeSupplier);
	public Number getSizeInBytes();

	public void setSizeInBytes(Number sizeInBytes);

	public void setSizeInBytesWithSupplier(UnsafeSupplier<Number, Throwable> sizeInBytesUnsafeSupplier);
	public String getTitle();

	public void setTitle(String title);

	public void setTitleWithSupplier(UnsafeSupplier<String, Throwable> titleUnsafeSupplier);

}