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

package com.liferay.headless.web.experience.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.web.experience.dto.v1_0.AggregateRating;
import com.liferay.headless.web.experience.dto.v1_0.Categories;
import com.liferay.headless.web.experience.dto.v1_0.Comment;
import com.liferay.headless.web.experience.dto.v1_0.Creator;
import com.liferay.headless.web.experience.dto.v1_0.RenderedContentsURL;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.dto.v1_0.Values;
import com.liferay.petra.function.UnsafeSupplier;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("StructuredContent")
@XmlRootElement(name = "StructuredContent")
public class StructuredContentImpl implements StructuredContent {

	public AggregateRating getAggregateRating() {
		return aggregateRating;
	}

	public String[] getAvailableLanguages() {
		return availableLanguages;
	}

	public Categories[] getCategories() {
		return categories;
	}

	public Comment[] getComment() {
		return comment;
	}

	public Long getContentSpace() {
		return contentSpace;
	}

	public Long getContentStructureId() {
		return contentStructureId;
	}

	public Creator getCreator() {
		return creator;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public Date getLastReviewed() {
		return lastReviewed;
	}

	public RenderedContentsURL[] getRenderedContentsURL() {
		return renderedContentsURL;
	}

	public String getTitle() {
		return title;
	}

	public Values[] getValues() {
		return values;
	}

	public void setAggregateRating(AggregateRating aggregateRating) {
		this.aggregateRating = (AggregateRatingImpl)aggregateRating;
	}

	@JsonIgnore
	public void setAggregateRating(
		UnsafeSupplier<AggregateRating, Throwable>
			aggregateRatingUnsafeSupplier) {

		try {
			aggregateRating =
				(AggregateRatingImpl)aggregateRatingUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setAvailableLanguages(String[] availableLanguages) {
		this.availableLanguages = (String[])availableLanguages;
	}

	@JsonIgnore
	public void setAvailableLanguages(
		UnsafeSupplier<String[], Throwable> availableLanguagesUnsafeSupplier) {

		try {
			availableLanguages =
				(String[])availableLanguagesUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setCategories(Categories[] categories) {
		this.categories = (CategoriesImpl[])categories;
	}

	@JsonIgnore
	public void setCategories(
		UnsafeSupplier<Categories[], Throwable> categoriesUnsafeSupplier) {

		try {
			categories = (CategoriesImpl[])categoriesUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setComment(Comment[] comment) {
		this.comment = (CommentImpl[])comment;
	}

	@JsonIgnore
	public void setComment(
		UnsafeSupplier<Comment[], Throwable> commentUnsafeSupplier) {

		try {
			comment = (CommentImpl[])commentUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setContentSpace(Long contentSpace) {
		this.contentSpace = (Long)contentSpace;
	}

	@JsonIgnore
	public void setContentSpace(
		UnsafeSupplier<Long, Throwable> contentSpaceUnsafeSupplier) {

		try {
			contentSpace = (Long)contentSpaceUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setContentStructureId(Long contentStructureId) {
		this.contentStructureId = (Long)contentStructureId;
	}

	@JsonIgnore
	public void setContentStructureId(
		UnsafeSupplier<Long, Throwable> contentStructureIdUnsafeSupplier) {

		try {
			contentStructureId = (Long)contentStructureIdUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setCreator(Creator creator) {
		this.creator = (CreatorImpl)creator;
	}

	@JsonIgnore
	public void setCreator(
		UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier) {

		try {
			creator = (CreatorImpl)creatorUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = (Date)dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = (Date)dateCreatedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = (Date)dateModified;
	}

	@JsonIgnore
	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {

		try {
			dateModified = (Date)dateModifiedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = (Date)datePublished;
	}

	@JsonIgnore
	public void setDatePublished(
		UnsafeSupplier<Date, Throwable> datePublishedUnsafeSupplier) {

		try {
			datePublished = (Date)datePublishedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDescription(String description) {
		this.description = (String)description;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {

		try {
			description = (String)descriptionUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setId(Long id) {
		this.id = (Long)id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = (Long)idUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setKeywords(String[] keywords) {
		this.keywords = (String[])keywords;
	}

	@JsonIgnore
	public void setKeywords(
		UnsafeSupplier<String[], Throwable> keywordsUnsafeSupplier) {

		try {
			keywords = (String[])keywordsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setLastReviewed(Date lastReviewed) {
		this.lastReviewed = (Date)lastReviewed;
	}

	@JsonIgnore
	public void setLastReviewed(
		UnsafeSupplier<Date, Throwable> lastReviewedUnsafeSupplier) {

		try {
			lastReviewed = (Date)lastReviewedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setRenderedContentsURL(
		RenderedContentsURL[] renderedContentsURL) {

		this.renderedContentsURL =
			(RenderedContentsURLImpl[])renderedContentsURL;
	}

	@JsonIgnore
	public void setRenderedContentsURL(
		UnsafeSupplier<RenderedContentsURL[], Throwable>
			renderedContentsURLUnsafeSupplier) {

		try {
			renderedContentsURL =
				(RenderedContentsURLImpl[])
					renderedContentsURLUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setTitle(String title) {
		this.title = (String)title;
	}

	@JsonIgnore
	public void setTitle(
		UnsafeSupplier<String, Throwable> titleUnsafeSupplier) {

		try {
			title = (String)titleUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@JsonIgnore
	public void setValues(
		UnsafeSupplier<Values[], Throwable> valuesUnsafeSupplier) {

		try {
			values = (ValuesImpl[])valuesUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setValues(Values[] values) {
		this.values = (ValuesImpl[])values;
	}

	@GraphQLField
	@JsonProperty
	protected AggregateRatingImpl aggregateRating;

	@GraphQLField
	@JsonProperty
	protected String[] availableLanguages;

	@GraphQLField
	@JsonProperty
	protected CategoriesImpl[] categories;

	@GraphQLField
	@JsonProperty
	protected CommentImpl[] comment;

	@GraphQLField
	@JsonProperty
	protected Long contentSpace;

	@GraphQLField
	@JsonProperty
	protected Long contentStructureId;

	@GraphQLField
	@JsonProperty
	protected CreatorImpl creator;

	@GraphQLField
	@JsonProperty
	protected Date dateCreated;

	@GraphQLField
	@JsonProperty
	protected Date dateModified;

	@GraphQLField
	@JsonProperty
	protected Date datePublished;

	@GraphQLField
	@JsonProperty
	protected String description;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected String[] keywords;

	@GraphQLField
	@JsonProperty
	protected Date lastReviewed;

	@GraphQLField
	@JsonProperty
	protected RenderedContentsURLImpl[] renderedContentsURL;

	@GraphQLField
	@JsonProperty
	protected String title;

	@GraphQLField
	@JsonProperty
	protected ValuesImpl[] values;

}