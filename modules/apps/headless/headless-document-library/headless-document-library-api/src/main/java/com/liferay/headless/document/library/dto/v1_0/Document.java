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

	public AdaptedImages[] getAdaptedImages();

	public AggregateRating getAggregateRating();

	public Categories[] getCategories();

	public Long[] getCategoryIds();

	public String getContentUrl();

	public Creator getCreator();

	public Date getDateCreated();

	public Date getDateModified();

	public String getDescription();

	public String getEncodingFormat();

	public String getFileExtension();

	public Long getFolderId();

	public Long getId();

	public String[] getKeywords();

	public Number getSizeInBytes();

	public String getTitle();

	public void setAdaptedImages(AdaptedImages[] adaptedImages);

	public void setAdaptedImages(
		UnsafeSupplier<AdaptedImages[], Throwable> adaptedImagesUnsafeSupplier);

	public void setAggregateRating(AggregateRating aggregateRating);

	public void setAggregateRating(
		UnsafeSupplier<AggregateRating, Throwable>
			aggregateRatingUnsafeSupplier);

	public void setCategories(Categories[] categories);

	public void setCategories(
		UnsafeSupplier<Categories[], Throwable> categoriesUnsafeSupplier);

	public void setCategoryIds(Long[] categoryIds);

	public void setCategoryIds(
		UnsafeSupplier<Long[], Throwable> categoryIdsUnsafeSupplier);

	public void setContentUrl(String contentUrl);

	public void setContentUrl(
		UnsafeSupplier<String, Throwable> contentUrlUnsafeSupplier);

	public void setCreator(Creator creator);

	public void setCreator(
		UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier);

	public void setDateCreated(Date dateCreated);

	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier);

	public void setDateModified(Date dateModified);

	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier);

	public void setDescription(String description);

	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier);

	public void setEncodingFormat(String encodingFormat);

	public void setEncodingFormat(
		UnsafeSupplier<String, Throwable> encodingFormatUnsafeSupplier);

	public void setFileExtension(String fileExtension);

	public void setFileExtension(
		UnsafeSupplier<String, Throwable> fileExtensionUnsafeSupplier);

	public void setFolderId(Long folderId);

	public void setFolderId(
		UnsafeSupplier<Long, Throwable> folderIdUnsafeSupplier);

	public void setId(Long id);

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);

	public void setKeywords(String[] keywords);

	public void setKeywords(
		UnsafeSupplier<String[], Throwable> keywordsUnsafeSupplier);

	public void setSizeInBytes(Number sizeInBytes);

	public void setSizeInBytes(
		UnsafeSupplier<Number, Throwable> sizeInBytesUnsafeSupplier);

	public void setTitle(String title);

	public void setTitle(UnsafeSupplier<String, Throwable> titleUnsafeSupplier);

}