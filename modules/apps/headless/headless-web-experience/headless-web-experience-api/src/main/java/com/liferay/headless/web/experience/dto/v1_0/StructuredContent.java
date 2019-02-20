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

	public void setAggregateRating(
			AggregateRating aggregateRating);

	public void setAggregateRating(
			UnsafeSupplier<AggregateRating, Throwable>
				aggregateRatingUnsafeSupplier);
	public String[] getAvailableLanguages();

	public void setAvailableLanguages(
			String[] availableLanguages);

	public void setAvailableLanguages(
			UnsafeSupplier<String[], Throwable>
				availableLanguagesUnsafeSupplier);
	public Categories[] getCategories();

	public void setCategories(
			Categories[] categories);

	public void setCategories(
			UnsafeSupplier<Categories[], Throwable>
				categoriesUnsafeSupplier);
	public Comment[] getComment();

	public void setComment(
			Comment[] comment);

	public void setComment(
			UnsafeSupplier<Comment[], Throwable>
				commentUnsafeSupplier);
	public Long getContentSpace();

	public void setContentSpace(
			Long contentSpace);

	public void setContentSpace(
			UnsafeSupplier<Long, Throwable>
				contentSpaceUnsafeSupplier);
	public Long getContentStructureId();

	public void setContentStructureId(
			Long contentStructureId);

	public void setContentStructureId(
			UnsafeSupplier<Long, Throwable>
				contentStructureIdUnsafeSupplier);
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
	public Date getLastReviewed();

	public void setLastReviewed(
			Date lastReviewed);

	public void setLastReviewed(
			UnsafeSupplier<Date, Throwable>
				lastReviewedUnsafeSupplier);
	public RenderedContentsByTemplate[] getRenderedContentsByTemplate();

	public void setRenderedContentsByTemplate(
			RenderedContentsByTemplate[] renderedContentsByTemplate);

	public void setRenderedContentsByTemplate(
			UnsafeSupplier<RenderedContentsByTemplate[], Throwable>
				renderedContentsByTemplateUnsafeSupplier);
	public String getTitle();

	public void setTitle(
			String title);

	public void setTitle(
			UnsafeSupplier<String, Throwable>
				titleUnsafeSupplier);
	public Values[] getValues();

	public void setValues(
			Values[] values);

	public void setValues(
			UnsafeSupplier<Values[], Throwable>
				valuesUnsafeSupplier);

}