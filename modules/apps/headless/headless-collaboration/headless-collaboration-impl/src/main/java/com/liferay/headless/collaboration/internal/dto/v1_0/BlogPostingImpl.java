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

package com.liferay.headless.collaboration.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.collaboration.dto.v1_0.AggregateRating;
import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.dto.v1_0.Categories;
import com.liferay.headless.collaboration.dto.v1_0.Comment;
import com.liferay.headless.collaboration.dto.v1_0.Creator;
import com.liferay.headless.collaboration.dto.v1_0.Image;
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
@GraphQLName("BlogPosting")
@XmlRootElement(name = "BlogPosting")
public class BlogPostingImpl implements BlogPosting {

	public AggregateRating getAggregateRating() {
			return aggregateRating;
	}

	public void setAggregateRating(
			AggregateRating aggregateRating) {

			this.aggregateRating = aggregateRating;
	}

	@JsonIgnore
	public void setAggregateRating(
			UnsafeSupplier<AggregateRating, Throwable>
				aggregateRatingUnsafeSupplier) {

			try {
				aggregateRating =
					aggregateRatingUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected AggregateRating aggregateRating;
	public String getAlternativeHeadline() {
			return alternativeHeadline;
	}

	public void setAlternativeHeadline(
			String alternativeHeadline) {

			this.alternativeHeadline = alternativeHeadline;
	}

	@JsonIgnore
	public void setAlternativeHeadline(
			UnsafeSupplier<String, Throwable>
				alternativeHeadlineUnsafeSupplier) {

			try {
				alternativeHeadline =
					alternativeHeadlineUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String alternativeHeadline;
	public String getArticleBody() {
			return articleBody;
	}

	public void setArticleBody(
			String articleBody) {

			this.articleBody = articleBody;
	}

	@JsonIgnore
	public void setArticleBody(
			UnsafeSupplier<String, Throwable>
				articleBodyUnsafeSupplier) {

			try {
				articleBody =
					articleBodyUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String articleBody;
	public String getCaption() {
			return caption;
	}

	public void setCaption(
			String caption) {

			this.caption = caption;
	}

	@JsonIgnore
	public void setCaption(
			UnsafeSupplier<String, Throwable>
				captionUnsafeSupplier) {

			try {
				caption =
					captionUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String caption;
	public Categories[] getCategories() {
			return categories;
	}

	public void setCategories(
			Categories[] categories) {

			this.categories = categories;
	}

	@JsonIgnore
	public void setCategories(
			UnsafeSupplier<Categories[], Throwable>
				categoriesUnsafeSupplier) {

			try {
				categories =
					categoriesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Categories[] categories;
	public Long[] getCategoryIds() {
			return categoryIds;
	}

	public void setCategoryIds(
			Long[] categoryIds) {

			this.categoryIds = categoryIds;
	}

	@JsonIgnore
	public void setCategoryIds(
			UnsafeSupplier<Long[], Throwable>
				categoryIdsUnsafeSupplier) {

			try {
				categoryIds =
					categoryIdsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long[] categoryIds;
	public Comment[] getComment() {
			return comment;
	}

	public void setComment(
			Comment[] comment) {

			this.comment = comment;
	}

	@JsonIgnore
	public void setComment(
			UnsafeSupplier<Comment[], Throwable>
				commentUnsafeSupplier) {

			try {
				comment =
					commentUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Comment[] comment;
	public Long getContentSpace() {
			return contentSpace;
	}

	public void setContentSpace(
			Long contentSpace) {

			this.contentSpace = contentSpace;
	}

	@JsonIgnore
	public void setContentSpace(
			UnsafeSupplier<Long, Throwable>
				contentSpaceUnsafeSupplier) {

			try {
				contentSpace =
					contentSpaceUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long contentSpace;
	public Creator getCreator() {
			return creator;
	}

	public void setCreator(
			Creator creator) {

			this.creator = creator;
	}

	@JsonIgnore
	public void setCreator(
			UnsafeSupplier<Creator, Throwable>
				creatorUnsafeSupplier) {

			try {
				creator =
					creatorUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Creator creator;
	public Date getDateCreated() {
			return dateCreated;
	}

	public void setDateCreated(
			Date dateCreated) {

			this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
			UnsafeSupplier<Date, Throwable>
				dateCreatedUnsafeSupplier) {

			try {
				dateCreated =
					dateCreatedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date dateCreated;
	public Date getDateModified() {
			return dateModified;
	}

	public void setDateModified(
			Date dateModified) {

			this.dateModified = dateModified;
	}

	@JsonIgnore
	public void setDateModified(
			UnsafeSupplier<Date, Throwable>
				dateModifiedUnsafeSupplier) {

			try {
				dateModified =
					dateModifiedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date dateModified;
	public Date getDatePublished() {
			return datePublished;
	}

	public void setDatePublished(
			Date datePublished) {

			this.datePublished = datePublished;
	}

	@JsonIgnore
	public void setDatePublished(
			UnsafeSupplier<Date, Throwable>
				datePublishedUnsafeSupplier) {

			try {
				datePublished =
					datePublishedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date datePublished;
	public String getDescription() {
			return description;
	}

	public void setDescription(
			String description) {

			this.description = description;
	}

	@JsonIgnore
	public void setDescription(
			UnsafeSupplier<String, Throwable>
				descriptionUnsafeSupplier) {

			try {
				description =
					descriptionUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String description;
	public String getEncodingFormat() {
			return encodingFormat;
	}

	public void setEncodingFormat(
			String encodingFormat) {

			this.encodingFormat = encodingFormat;
	}

	@JsonIgnore
	public void setEncodingFormat(
			UnsafeSupplier<String, Throwable>
				encodingFormatUnsafeSupplier) {

			try {
				encodingFormat =
					encodingFormatUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String encodingFormat;
	public String getFriendlyUrlPath() {
			return friendlyUrlPath;
	}

	public void setFriendlyUrlPath(
			String friendlyUrlPath) {

			this.friendlyUrlPath = friendlyUrlPath;
	}

	@JsonIgnore
	public void setFriendlyUrlPath(
			UnsafeSupplier<String, Throwable>
				friendlyUrlPathUnsafeSupplier) {

			try {
				friendlyUrlPath =
					friendlyUrlPathUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String friendlyUrlPath;
	public String getHeadline() {
			return headline;
	}

	public void setHeadline(
			String headline) {

			this.headline = headline;
	}

	@JsonIgnore
	public void setHeadline(
			UnsafeSupplier<String, Throwable>
				headlineUnsafeSupplier) {

			try {
				headline =
					headlineUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String headline;
	public Long getId() {
			return id;
	}

	public void setId(
			Long id) {

			this.id = id;
	}

	@JsonIgnore
	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier) {

			try {
				id =
					idUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long id;
	public Image getImage() {
			return image;
	}

	public void setImage(
			Image image) {

			this.image = image;
	}

	@JsonIgnore
	public void setImage(
			UnsafeSupplier<Image, Throwable>
				imageUnsafeSupplier) {

			try {
				image =
					imageUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Image image;
	public Long getImageId() {
			return imageId;
	}

	public void setImageId(
			Long imageId) {

			this.imageId = imageId;
	}

	@JsonIgnore
	public void setImageId(
			UnsafeSupplier<Long, Throwable>
				imageIdUnsafeSupplier) {

			try {
				imageId =
					imageIdUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long imageId;
	public String[] getKeywords() {
			return keywords;
	}

	public void setKeywords(
			String[] keywords) {

			this.keywords = keywords;
	}

	@JsonIgnore
	public void setKeywords(
			UnsafeSupplier<String[], Throwable>
				keywordsUnsafeSupplier) {

			try {
				keywords =
					keywordsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String[] keywords;

}