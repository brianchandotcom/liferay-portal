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
public interface BlogPosting {

	public AggregateRating getAggregateRating();

	public void setAggregateRating(
			AggregateRating aggregateRating);

	public void setAggregateRating(
			UnsafeSupplier<AggregateRating, Throwable>
				aggregateRatingUnsafeSupplier);
	public String getAlternativeHeadline();

	public void setAlternativeHeadline(
			String alternativeHeadline);

	public void setAlternativeHeadline(
			UnsafeSupplier<String, Throwable>
				alternativeHeadlineUnsafeSupplier);
	public String getArticleBody();

	public void setArticleBody(
			String articleBody);

	public void setArticleBody(
			UnsafeSupplier<String, Throwable>
				articleBodyUnsafeSupplier);
	public String getCaption();

	public void setCaption(
			String caption);

	public void setCaption(
			UnsafeSupplier<String, Throwable>
				captionUnsafeSupplier);
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
	public Long getContentSpace();

	public void setContentSpace(
			Long contentSpace);

	public void setContentSpace(
			UnsafeSupplier<Long, Throwable>
				contentSpaceUnsafeSupplier);
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
	public Date getDatePublished();

	public void setDatePublished(
			Date datePublished);

	public void setDatePublished(
			UnsafeSupplier<Date, Throwable>
				datePublishedUnsafeSupplier);
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
	public String getFriendlyUrlPath();

	public void setFriendlyUrlPath(
			String friendlyUrlPath);

	public void setFriendlyUrlPath(
			UnsafeSupplier<String, Throwable>
				friendlyUrlPathUnsafeSupplier);
	public Boolean getHasComments();

	public void setHasComments(
			Boolean hasComments);

	public void setHasComments(
			UnsafeSupplier<Boolean, Throwable>
				hasCommentsUnsafeSupplier);
	public String getHeadline();

	public void setHeadline(
			String headline);

	public void setHeadline(
			UnsafeSupplier<String, Throwable>
				headlineUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public Image getImage();

	public void setImage(
			Image image);

	public void setImage(
			UnsafeSupplier<Image, Throwable>
				imageUnsafeSupplier);
	public Long getImageId();

	public void setImageId(
			Long imageId);

	public void setImageId(
			UnsafeSupplier<Long, Throwable>
				imageIdUnsafeSupplier);
	public String[] getKeywords();

	public void setKeywords(
			String[] keywords);

	public void setKeywords(
			UnsafeSupplier<String[], Throwable>
				keywordsUnsafeSupplier);

}