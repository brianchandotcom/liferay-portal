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

package com.liferay.headless.delivery.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("Document")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Document")
public class Document {

	public static enum ViewableBy {

		ANYONE("Anyone"), MEMBERS("Members"), OWNER("Owner");

		@JsonCreator
		public static ViewableBy create(String value) {
			for (ViewableBy viewableBy : values()) {
				if (Objects.equals(viewableBy.getValue(), value)) {
					return viewableBy;
				}
			}

			return null;
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private ViewableBy(String value) {
			_value = value;
		}

		private final String _value;

	}

	@Schema(
		description = "An array of images in several resolutions and sizes, created by the adaptive media framework."
	)
	public AdaptedImage[] getAdaptedImages() {
		return adaptedImages;
	}

	public void setAdaptedImages(AdaptedImage[] adaptedImages) {
		this.adaptedImages = adaptedImages;
	}

	@JsonIgnore
	public void setAdaptedImages(
		UnsafeSupplier<AdaptedImage[], Exception> adaptedImagesUnsafeSupplier) {

		try {
			adaptedImages = adaptedImagesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected AdaptedImage[] adaptedImages;

	@Schema(
		description = "The information of the ratings (average, number) associated to this resource."
	)
	public AggregateRating getAggregateRating() {
		return aggregateRating;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected AggregateRating aggregateRating;

	@Schema(description = "An absolute URL to the binary Document.")
	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	@JsonIgnore
	public void setContentUrl(
		UnsafeSupplier<String, Exception> contentUrlUnsafeSupplier) {

		try {
			contentUrl = contentUrlUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String contentUrl;

	@Schema(description = "The creator of the Document")
	public Creator getCreator() {
		return creator;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Creator creator;

	@Schema(description = "The creation date of the Document.")
	public Date getDateCreated() {
		return dateCreated;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	@Schema(description = "The last time a field of the Document changed.")
	public Date getDateModified() {
		return dateModified;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateModified;

	@Schema(description = "The description of the Document")
	public String getDescription() {
		return description;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String description;

	@Schema(
		description = "The id of the DocumentFolder where this Document is stored."
	)
	public Long getDocumentFolderId() {
		return documentFolderId;
	}

	public void setDocumentFolderId(Long documentFolderId) {
		this.documentFolderId = documentFolderId;
	}

	@JsonIgnore
	public void setDocumentFolderId(
		UnsafeSupplier<Long, Exception> documentFolderIdUnsafeSupplier) {

		try {
			documentFolderId = documentFolderIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long documentFolderId;

	@Schema(description = "The content type (application/pdf) of the Document.")
	public String getEncodingFormat() {
		return encodingFormat;
	}

	public void setEncodingFormat(String encodingFormat) {
		this.encodingFormat = encodingFormat;
	}

	@JsonIgnore
	public void setEncodingFormat(
		UnsafeSupplier<String, Exception> encodingFormatUnsafeSupplier) {

		try {
			encodingFormat = encodingFormatUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String encodingFormat;

	@Schema(description = "The file extension of the binary file.")
	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@JsonIgnore
	public void setFileExtension(
		UnsafeSupplier<String, Exception> fileExtensionUnsafeSupplier) {

		try {
			fileExtension = fileExtensionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String fileExtension;

	@Schema(description = "The identifier of the resource.")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@Schema(description = "A list of keywords describing the Document.")
	public String[] getKeywords() {
		return keywords;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] keywords;

	@Schema(description = "A list of child comments of this content.")
	public Integer getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(Integer numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	@JsonIgnore
	public void setNumberOfComments(
		UnsafeSupplier<Integer, Exception> numberOfCommentsUnsafeSupplier) {

		try {
			numberOfComments = numberOfCommentsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Integer numberOfComments;

	@Schema(description = "The size in bytes of the binary files.")
	public Long getSizeInBytes() {
		return sizeInBytes;
	}

	public void setSizeInBytes(Long sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}

	@JsonIgnore
	public void setSizeInBytes(
		UnsafeSupplier<Long, Exception> sizeInBytesUnsafeSupplier) {

		try {
			sizeInBytes = sizeInBytesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long sizeInBytes;

	@Schema(description = "The categories asociated with this Document.")
	public TaxonomyCategory[] getTaxonomyCategories() {
		return taxonomyCategories;
	}

	public void setTaxonomyCategories(TaxonomyCategory[] taxonomyCategories) {
		this.taxonomyCategories = taxonomyCategories;
	}

	@JsonIgnore
	public void setTaxonomyCategories(
		UnsafeSupplier<TaxonomyCategory[], Exception>
			taxonomyCategoriesUnsafeSupplier) {

		try {
			taxonomyCategories = taxonomyCategoriesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected TaxonomyCategory[] taxonomyCategories;

	@Schema(
		description = "A write only field to add TaxonomyCategory to this resource."
	)
	public Long[] getTaxonomyCategoryIds() {
		return taxonomyCategoryIds;
	}

	public void setTaxonomyCategoryIds(Long[] taxonomyCategoryIds) {
		this.taxonomyCategoryIds = taxonomyCategoryIds;
	}

	@JsonIgnore
	public void setTaxonomyCategoryIds(
		UnsafeSupplier<Long[], Exception> taxonomyCategoryIdsUnsafeSupplier) {

		try {
			taxonomyCategoryIds = taxonomyCategoryIdsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Long[] taxonomyCategoryIds;

	@Schema(description = "The main title/name of the Document.")
	public String getTitle() {
		return title;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String title;

	@Schema(
		description = "Write only property to specify the default permissions."
	)
	public ViewableBy getViewableBy() {
		return viewableBy;
	}

	@JsonIgnore
	public String getViewableByAsString() {
		if (viewableBy == null) {
			return null;
		}

		return viewableBy.toString();
	}

	public void setViewableBy(ViewableBy viewableBy) {
		this.viewableBy = viewableBy;
	}

	@JsonIgnore
	public void setViewableBy(
		UnsafeSupplier<ViewableBy, Exception> viewableByUnsafeSupplier) {

		try {
			viewableBy = viewableByUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected ViewableBy viewableBy;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Document)) {
			return false;
		}

		Document document = (Document)object;

		return Objects.equals(toString(), document.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"adaptedImages\": ");

		if (adaptedImages == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < adaptedImages.length; i++) {
				sb.append(adaptedImages[i]);

				if ((i + 1) < adaptedImages.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"aggregateRating\": ");

		if (aggregateRating == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRating);
		}

		sb.append(", ");

		sb.append("\"contentUrl\": ");

		if (contentUrl == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(contentUrl);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"creator\": ");

		if (creator == null) {
			sb.append("null");
		}
		else {
			sb.append(creator);
		}

		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (dateCreated == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dateCreated);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (dateModified == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dateModified);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (description == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(description);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"documentFolderId\": ");

		if (documentFolderId == null) {
			sb.append("null");
		}
		else {
			sb.append(documentFolderId);
		}

		sb.append(", ");

		sb.append("\"encodingFormat\": ");

		if (encodingFormat == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(encodingFormat);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"fileExtension\": ");

		if (fileExtension == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(fileExtension);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (id == null) {
			sb.append("null");
		}
		else {
			sb.append(id);
		}

		sb.append(", ");

		sb.append("\"keywords\": ");

		if (keywords == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < keywords.length; i++) {
				sb.append("\"");
				sb.append(keywords[i]);
				sb.append("\"");

				if ((i + 1) < keywords.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"numberOfComments\": ");

		if (numberOfComments == null) {
			sb.append("null");
		}
		else {
			sb.append(numberOfComments);
		}

		sb.append(", ");

		sb.append("\"sizeInBytes\": ");

		if (sizeInBytes == null) {
			sb.append("null");
		}
		else {
			sb.append(sizeInBytes);
		}

		sb.append(", ");

		sb.append("\"taxonomyCategories\": ");

		if (taxonomyCategories == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < taxonomyCategories.length; i++) {
				sb.append(taxonomyCategories[i]);

				if ((i + 1) < taxonomyCategories.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"taxonomyCategoryIds\": ");

		if (taxonomyCategoryIds == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < taxonomyCategoryIds.length; i++) {
				sb.append(taxonomyCategoryIds[i]);

				if ((i + 1) < taxonomyCategoryIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"title\": ");

		if (title == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(title);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"viewableBy\": ");

		if (viewableBy == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(viewableBy);
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

}