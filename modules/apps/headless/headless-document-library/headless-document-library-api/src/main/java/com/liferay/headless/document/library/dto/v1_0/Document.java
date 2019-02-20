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

	public void setAdaptedImages(
			AdaptedImages[] adaptedImages);

	public void setAdaptedImages(
			UnsafeSupplier<AdaptedImages[], Throwable>
				adaptedImagesUnsafeSupplier);
	public AggregateRating getAggregateRating();

	public void setAggregateRating(
			AggregateRating aggregateRating);

	public void setAggregateRating(
			UnsafeSupplier<AggregateRating, Throwable>
				aggregateRatingUnsafeSupplier);
	public Categories[] getCategories();

	public void setCategories(
			Categories[] categories);

	public void setCategories(
			UnsafeSupplier<Categories[], Throwable>
				categoriesUnsafeSupplier);
	public Long[] getCategoryIds();

	public void setCategoryIds(
			Long[] categoryIds);

	public void setCategoryIds(
			UnsafeSupplier<Long[], Throwable>
				categoryIdsUnsafeSupplier);
	public String getContentUrl();

	public void setContentUrl(
			String contentUrl);

	public void setContentUrl(
			UnsafeSupplier<String, Throwable>
				contentUrlUnsafeSupplier);
	public Creator getCreator();

	public void setCreator(
			Creator creator);

	public void setCreator(
			UnsafeSupplier<Creator, Throwable>
				creatorUnsafeSupplier);
	public Date getDateCreated();

	public void setDateCreated(
			Date dateCreated);

	public void setDateCreated(
			UnsafeSupplier<Date, Throwable>
				dateCreatedUnsafeSupplier);
	public Date getDateModified();

	public void setDateModified(
			Date dateModified);

	public void setDateModified(
			UnsafeSupplier<Date, Throwable>
				dateModifiedUnsafeSupplier);
	public String getDescription();

	public void setDescription(
			String description);

	public void setDescription(
			UnsafeSupplier<String, Throwable>
				descriptionUnsafeSupplier);
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
	public Long getFolderId();

	public void setFolderId(
			Long folderId);

	public void setFolderId(
			UnsafeSupplier<Long, Throwable>
				folderIdUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public String[] getKeywords();

	public void setKeywords(
			String[] keywords);

	public void setKeywords(
			UnsafeSupplier<String[], Throwable>
				keywordsUnsafeSupplier);
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