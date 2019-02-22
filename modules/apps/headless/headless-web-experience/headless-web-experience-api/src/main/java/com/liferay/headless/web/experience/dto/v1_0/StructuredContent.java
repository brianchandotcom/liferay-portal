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

package com.liferay.headless.web.experience.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import java.util.Date;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface StructuredContent {

	public AggregateRating getAggregateRating();

	public String[] getAvailableLanguages();

	public Categories[] getCategories();

	public Comment[] getComment();

	public Long getContentSpace();

	public Long getContentStructureId();

	public Creator getCreator();

	public Date getDateCreated();

	public Date getDateModified();

	public Date getDatePublished();

	public String getDescription();

	public Long getId();

	public String[] getKeywords();

	public Date getLastReviewed();

	public RenderedContentsURL[] getRenderedContentsURL();

	public String getTitle();

	public Values[] getValues();

	public void setAggregateRating(AggregateRating aggregateRating);

	public void setAggregateRating(
		UnsafeSupplier<AggregateRating, Throwable>
			aggregateRatingUnsafeSupplier);

	public void setAvailableLanguages(String[] availableLanguages);

	public void setAvailableLanguages(
		UnsafeSupplier<String[], Throwable> availableLanguagesUnsafeSupplier);

	public void setCategories(Categories[] categories);

	public void setCategories(
		UnsafeSupplier<Categories[], Throwable> categoriesUnsafeSupplier);

	public void setComment(Comment[] comment);

	public void setComment(
		UnsafeSupplier<Comment[], Throwable> commentUnsafeSupplier);

	public void setContentSpace(Long contentSpace);

	public void setContentSpace(
		UnsafeSupplier<Long, Throwable> contentSpaceUnsafeSupplier);

	public void setContentStructureId(Long contentStructureId);

	public void setContentStructureId(
		UnsafeSupplier<Long, Throwable> contentStructureIdUnsafeSupplier);

	public void setCreator(Creator creator);

	public void setCreator(
		UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier);

	public void setDateCreated(Date dateCreated);

	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier);

	public void setDateModified(Date dateModified);

	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier);

	public void setDatePublished(Date datePublished);

	public void setDatePublished(
		UnsafeSupplier<Date, Throwable> datePublishedUnsafeSupplier);

	public void setDescription(String description);

	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier);

	public void setId(Long id);

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);

	public void setKeywords(String[] keywords);

	public void setKeywords(
		UnsafeSupplier<String[], Throwable> keywordsUnsafeSupplier);

	public void setLastReviewed(Date lastReviewed);

	public void setLastReviewed(
		UnsafeSupplier<Date, Throwable> lastReviewedUnsafeSupplier);

	public void setRenderedContentsURL(
		RenderedContentsURL[] renderedContentsURL);

	public void setRenderedContentsURL(
		UnsafeSupplier<RenderedContentsURL[], Throwable>
			renderedContentsURLUnsafeSupplier);

	public void setTitle(String title);

	public void setTitle(UnsafeSupplier<String, Throwable> titleUnsafeSupplier);

	public void setValues(
		UnsafeSupplier<Values[], Throwable> valuesUnsafeSupplier);

	public void setValues(Values[] values);

}