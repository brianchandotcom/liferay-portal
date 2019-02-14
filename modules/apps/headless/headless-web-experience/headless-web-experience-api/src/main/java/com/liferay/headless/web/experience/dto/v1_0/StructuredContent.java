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

	public Long[] getCategory() {
		return category;
	}

	public Comment[] getComment() {
		return comment;
	}

	public Long getContentSpace() {
		return contentSpace;
	}

	public ContentStructure getContentStructure() {
		return contentStructure;
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

	public RenderedContentsByTemplate[] getRenderedContentsByTemplate() {
		return renderedContentsByTemplate;
	}

	public String getTitle() {
		return title;
	}

	public Values[] getValues() {
		return values;
	}

	public void setAggregateRating(AggregateRating aggregateRating) {
		this.aggregateRating = aggregateRating;
	}

	public void setAggregateRating(
		UnsafeSupplier<AggregateRating, Throwable>
			aggregateRatingUnsafeSupplier) {

		try {
			aggregateRating = aggregateRatingUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setAvailableLanguages(String[] availableLanguages) {
		this.availableLanguages = availableLanguages;
	}

	public void setAvailableLanguages(
		UnsafeSupplier<String[], Throwable> availableLanguagesUnsafeSupplier) {

		try {
			availableLanguages = availableLanguagesUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setCategory(Long[] category) {
		this.category = category;
	}

	public void setCategory(
		UnsafeSupplier<Long[], Throwable> categoryUnsafeSupplier) {

		try {
			category = categoryUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setComment(Comment[] comment) {
		this.comment = comment;
	}

	public void setComment(
		UnsafeSupplier<Comment[], Throwable> commentUnsafeSupplier) {

		try {
			comment = commentUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setContentSpace(Long contentSpace) {
		this.contentSpace = contentSpace;
	}

	public void setContentSpace(
		UnsafeSupplier<Long, Throwable> contentSpaceUnsafeSupplier) {

		try {
			contentSpace = contentSpaceUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setContentStructure(ContentStructure contentStructure) {
		this.contentStructure = contentStructure;
	}

	public void setContentStructure(
		UnsafeSupplier<ContentStructure, Throwable>
			contentStructureUnsafeSupplier) {

		try {
			contentStructure = contentStructureUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setContentStructureId(Long contentStructureId) {
		this.contentStructureId = contentStructureId;
	}

	public void setContentStructureId(
		UnsafeSupplier<Long, Throwable> contentStructureIdUnsafeSupplier) {

		try {
			contentStructureId = contentStructureIdUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	public void setCreator(
		UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier) {

		try {
			creator = creatorUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	public void setDatePublished(
		UnsafeSupplier<Date, Throwable> datePublishedUnsafeSupplier) {

		try {
			datePublished = datePublishedUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	public void setKeywords(
		UnsafeSupplier<String[], Throwable> keywordsUnsafeSupplier) {

		try {
			keywords = keywordsUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setLastReviewed(Date lastReviewed) {
		this.lastReviewed = lastReviewed;
	}

	public void setLastReviewed(
		UnsafeSupplier<Date, Throwable> lastReviewedUnsafeSupplier) {

		try {
			lastReviewed = lastReviewedUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setRenderedContentsByTemplate(
		RenderedContentsByTemplate[] renderedContentsByTemplate) {

		this.renderedContentsByTemplate = renderedContentsByTemplate;
	}

	public void setRenderedContentsByTemplate(
		UnsafeSupplier<RenderedContentsByTemplate[], Throwable>
			renderedContentsByTemplateUnsafeSupplier) {

		try {
			renderedContentsByTemplate =
				renderedContentsByTemplateUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle(
		UnsafeSupplier<String, Throwable> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setValues(
		UnsafeSupplier<Values[], Throwable> valuesUnsafeSupplier) {

		try {
			values = valuesUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setValues(Values[] values) {
		this.values = values;
	}

	@GraphQLField
	protected AggregateRating aggregateRating;

	@GraphQLField
	protected String[] availableLanguages;

	@GraphQLField
	protected Long[] category;

	@GraphQLField
	protected Comment[] comment;

	@GraphQLField
	protected Long contentSpace;

	@GraphQLField
	protected ContentStructure contentStructure;

	@GraphQLField
	protected Long contentStructureId;

	@GraphQLField
	protected Creator creator;

	@GraphQLField
	protected Date dateCreated;

	@GraphQLField
	protected Date dateModified;

	@GraphQLField
	protected Date datePublished;

	@GraphQLField
	protected String description;

	@GraphQLField
	protected Long id;

	@GraphQLField
	protected String[] keywords;

	@GraphQLField
	protected Date lastReviewed;

	@GraphQLField
	protected RenderedContentsByTemplate[] renderedContentsByTemplate;

	@GraphQLField
	protected String title;

	@GraphQLField
	protected Values[] values;

}