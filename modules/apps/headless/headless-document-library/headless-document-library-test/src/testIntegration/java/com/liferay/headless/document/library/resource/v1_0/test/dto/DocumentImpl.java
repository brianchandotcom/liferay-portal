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

package com.liferay.headless.document.library.resource.v1_0.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liferay.headless.document.library.dto.v1_0.AdaptedMedia;
import com.liferay.headless.document.library.dto.v1_0.Creator;
import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.petra.function.UnsafeSupplier;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "Document")
public class DocumentImpl implements Document {

	public AdaptedMedia[] getAdaptedMedia() {
			return adaptedMedia;
	}

	public void setAdaptedMedia(AdaptedMedia[] adaptedMedia) {
			this.adaptedMedia = adaptedMedia;
	}

	public void setAdaptedMediaWithSupplier(UnsafeSupplier<AdaptedMedia[], Throwable> adaptedMediaUnsafeSupplier) {
	}

	@JsonProperty
	protected AdaptedMedia[] adaptedMedia;
	public Long[] getCategory() {
			return category;
	}

	public void setCategory(Long[] category) {
			this.category = category;
	}

	public void setCategoryWithSupplier(UnsafeSupplier<Long[], Throwable> categoryUnsafeSupplier) {
	}

	@JsonProperty
	protected Long[] category;
	public String getContentUrl() {
			return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
			this.contentUrl = contentUrl;
	}

	public void setContentUrlWithSupplier(UnsafeSupplier<String, Throwable> contentUrlUnsafeSupplier) {
	}

	@JsonProperty
	protected String contentUrl;
	public Creator getCreator() {
			return creator;
	}

	public void setCreator(Creator creator) {
			this.creator = creator;
	}

	public void setCreatorWithSupplier(UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier) {
	}

	@JsonProperty
	protected Creator creator;
	public Date getDateCreated() {
			return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
			this.dateCreated = dateCreated;
	}

	public void setDateCreatedWithSupplier(UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {
	}

	@JsonProperty
	protected Date dateCreated;
	public Date getDateModified() {
			return dateModified;
	}

	public void setDateModified(Date dateModified) {
			this.dateModified = dateModified;
	}

	public void setDateModifiedWithSupplier(UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {
	}

	@JsonProperty
	protected Date dateModified;
	public String getDescription() {
			return description;
	}

	public void setDescription(String description) {
			this.description = description;
	}

	public void setDescriptionWithSupplier(UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {
	}

	@JsonProperty
	protected String description;
	public String getEncodingFormat() {
			return encodingFormat;
	}

	public void setEncodingFormat(String encodingFormat) {
			this.encodingFormat = encodingFormat;
	}

	public void setEncodingFormatWithSupplier(UnsafeSupplier<String, Throwable> encodingFormatUnsafeSupplier) {
	}

	@JsonProperty
	protected String encodingFormat;
	public String getFileExtension() {
			return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
			this.fileExtension = fileExtension;
	}

	public void setFileExtensionWithSupplier(UnsafeSupplier<String, Throwable> fileExtensionUnsafeSupplier) {
	}

	@JsonProperty
	protected String fileExtension;
	public Long getFolderId() {
			return folderId;
	}

	public void setFolderId(Long folderId) {
			this.folderId = folderId;
	}

	public void setFolderIdWithSupplier(UnsafeSupplier<Long, Throwable> folderIdUnsafeSupplier) {
	}

	@JsonProperty
	protected Long folderId;
	public Long getId() {
			return id;
	}

	public void setId(Long id) {
			this.id = id;
	}

	public void setIdWithSupplier(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
	}

	@JsonProperty
	protected Long id;
	public String[] getKeywords() {
			return keywords;
	}

	public void setKeywords(String[] keywords) {
			this.keywords = keywords;
	}

	public void setKeywordsWithSupplier(UnsafeSupplier<String[], Throwable> keywordsUnsafeSupplier) {
	}

	@JsonProperty
	protected String[] keywords;
	public Number getSizeInBytes() {
			return sizeInBytes;
	}

	public void setSizeInBytes(Number sizeInBytes) {
			this.sizeInBytes = sizeInBytes;
	}

	public void setSizeInBytesWithSupplier(UnsafeSupplier<Number, Throwable> sizeInBytesUnsafeSupplier) {
	}

	@JsonProperty
	protected Number sizeInBytes;
	public String getTitle() {
			return title;
	}

	public void setTitle(String title) {
			this.title = title;
	}

	public void setTitleWithSupplier(UnsafeSupplier<String, Throwable> titleUnsafeSupplier) {
	}

	@JsonProperty
	protected String title;

}