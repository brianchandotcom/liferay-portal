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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

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
public class StructuredContent {

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

	public ContentFieldValue[] getContentFieldValues() {
		return contentFieldValues;
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

	public void setAggregateRating(AggregateRating aggregateRating) {
		this.aggregateRating = aggregateRating;
	}

	@JsonIgnore
	public void setAggregateRating(
		UnsafeSupplier<AggregateRating, Exception>
			aggregateRatingUnsafeSupplier) {

		try {
			aggregateRating = aggregateRatingUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setAvailableLanguages(String[] availableLanguages) {
		this.availableLanguages = availableLanguages;
	}

	@JsonIgnore
	public void setAvailableLanguages(
		UnsafeSupplier<String[], Exception> availableLanguagesUnsafeSupplier) {

		try {
			availableLanguages = availableLanguagesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setCategories(Categories[] categories) {
		this.categories = categories;
	}

	@JsonIgnore
	public void setCategories(
		UnsafeSupplier<Categories[], Exception> categoriesUnsafeSupplier) {

		try {
			categories = categoriesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setComment(Comment[] comment) {
		this.comment = comment;
	}

	@JsonIgnore
	public void setComment(
		UnsafeSupplier<Comment[], Exception> commentUnsafeSupplier) {

		try {
			comment = commentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setContentFieldValues(ContentFieldValue[] contentFieldValues) {
		this.contentFieldValues = contentFieldValues;
	}

	@JsonIgnore
	public void setContentFieldValues(
		UnsafeSupplier<ContentFieldValue[], Exception>
			contentFieldValuesUnsafeSupplier) {

		try {
			contentFieldValues = contentFieldValuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setContentSpace(Long contentSpace) {
		this.contentSpace = contentSpace;
	}

	@JsonIgnore
	public void setContentSpace(
		UnsafeSupplier<Long, Exception> contentSpaceUnsafeSupplier) {

		try {
			contentSpace = contentSpaceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setContentStructureId(Long contentStructureId) {
		this.contentStructureId = contentStructureId;
	}

	@JsonIgnore
	public void setContentStructureId(
		UnsafeSupplier<Long, Exception> contentStructureIdUnsafeSupplier) {

		try {
			contentStructureId = contentStructureIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	@JsonIgnore
	public void setCreator(
		UnsafeSupplier<Creator, Exception> creatorUnsafeSupplier) {

		try {
			creator = creatorUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@JsonIgnore
	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	@JsonIgnore
	public void setDatePublished(
		UnsafeSupplier<Date, Exception> datePublishedUnsafeSupplier) {

		try {
			datePublished = datePublishedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	@JsonIgnore
	public void setKeywords(
		UnsafeSupplier<String[], Exception> keywordsUnsafeSupplier) {

		try {
			keywords = keywordsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setLastReviewed(Date lastReviewed) {
		this.lastReviewed = lastReviewed;
	}

	@JsonIgnore
	public void setLastReviewed(
		UnsafeSupplier<Date, Exception> lastReviewedUnsafeSupplier) {

		try {
			lastReviewed = lastReviewedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setRenderedContentsURL(
		RenderedContentsURL[] renderedContentsURL) {

		this.renderedContentsURL = renderedContentsURL;
	}

	@JsonIgnore
	public void setRenderedContentsURL(
		UnsafeSupplier<RenderedContentsURL[], Exception>
			renderedContentsURLUnsafeSupplier) {

		try {
			renderedContentsURL = renderedContentsURLUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonIgnore
	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(36);

		sb.append("{");

		sb.append("aggregateRating=");

		sb.append(aggregateRating);
		sb.append(", availableLanguages=");

		sb.append(availableLanguages);
		sb.append(", categories=");

		sb.append(categories);
		sb.append(", comment=");

		sb.append(comment);
		sb.append(", contentSpace=");

		sb.append(contentSpace);
		sb.append(", contentStructureId=");

		sb.append(contentStructureId);
		sb.append(", creator=");

		sb.append(creator);
		sb.append(", dateCreated=");

		sb.append(dateCreated);
		sb.append(", dateModified=");

		sb.append(dateModified);
		sb.append(", datePublished=");

		sb.append(datePublished);
		sb.append(", description=");

		sb.append(description);
		sb.append(", id=");

		sb.append(id);
		sb.append(", keywords=");

		sb.append(keywords);
		sb.append(", lastReviewed=");

		sb.append(lastReviewed);
		sb.append(", renderedContentsURL=");

		sb.append(renderedContentsURL);
		sb.append(", title=");

		sb.append(title);
		sb.append(", contentFieldValues=");

		sb.append(contentFieldValues);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected AggregateRating aggregateRating;

	@GraphQLField
	@JsonProperty
	protected String[] availableLanguages;

	@GraphQLField
	@JsonProperty
	protected Categories[] categories;

	@GraphQLField
	@JsonProperty
	protected Comment[] comment;

	@GraphQLField
	@JsonProperty
	protected ContentFieldValue[] contentFieldValues;

	@GraphQLField
	@JsonProperty
	protected Long contentSpace;

	@GraphQLField
	@JsonProperty
	protected Long contentStructureId;

	@GraphQLField
	@JsonProperty
	protected Creator creator;

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
	protected RenderedContentsURL[] renderedContentsURL;

	@GraphQLField
	@JsonProperty
	protected String title;

}