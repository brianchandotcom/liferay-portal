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

import com.liferay.headless.document.library.dto.v1_0.AdaptedImages;
import com.liferay.headless.document.library.dto.v1_0.AggregateRating;
import com.liferay.headless.document.library.dto.v1_0.Categories;
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

	@Override
	public AdaptedImages[] getAdaptedImages() {
		return new AdaptedImages[0];
	}

	@Override
	public void setAdaptedImages(
		AdaptedImages[] adaptedImages) {
	}

	@Override
	public void setAdaptedImages(
		UnsafeSupplier<AdaptedImages[], Throwable> adaptedImagesUnsafeSupplier) {
	}

	@Override
	public AggregateRating getAggregateRating() {
		return aggregateRating;
	}

	@Override
	public void setAggregateRating(
		AggregateRating aggregateRating) {
	}

	@Override
	public void setAggregateRating(
		UnsafeSupplier<AggregateRating, Throwable> aggregateRatingUnsafeSupplier) {
	}

	@Override
	public Categories[] getCategories() {
		return new Categories[0];
	}

	@Override
	public void setCategories(
		Categories[] categories) {
	}

	@Override
	public void setCategories(
		UnsafeSupplier<Categories[], Throwable> categoriesUnsafeSupplier) {
	}

	@Override
	public String getContentUrl() {
		return contentUrl;
	}

	@Override
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	@Override
	public void setContentUrl(
		UnsafeSupplier<String, Throwable> contentUrlUnsafeSupplier) {
	}

	@Override
	public Creator getCreator() {
		return creator;
	}

	@Override
	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	@Override
	public void setCreator(
		UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier) {

	}

	@Override
	public Date getDateCreated() {
		return dateCreated;
	}

	@Override
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

	}

	@Override
	public Date getDateModified() {
		return dateModified;
	}

	@Override
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@Override
	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {
	}

	@Override
	public String getEncodingFormat() {
		return encodingFormat;
	}

	@Override
	public void setEncodingFormat(String encodingFormat) {
		this.encodingFormat = encodingFormat;
	}

	@Override
	public void setEncodingFormat(
		UnsafeSupplier<String, Throwable> encodingFormatUnsafeSupplier) {
	}

	@Override
	public String getFileExtension() {
		return fileExtension;
	}

	@Override
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Override
	public void setFileExtension(
		UnsafeSupplier<String, Throwable> fileExtensionUnsafeSupplier) {

	}

	@Override
	public Long getFolderId() {
		return folderId;
	}

	@Override
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	@Override
	public void setFolderId(
		UnsafeSupplier<Long, Throwable> folderIdUnsafeSupplier) {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setId(
		UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
	}

	@Override
	public String[] getKeywords() {
		return keywords;
	}

	@Override
	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	@Override
	public void setKeywords(
		UnsafeSupplier<String[], Throwable> keywordsUnsafeSupplier) {

	}

	@Override
	public Number getSizeInBytes() {
		return sizeInBytes;
	}

	@Override
	public void setSizeInBytes(Number sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}

	@Override
	public void setSizeInBytes(
		UnsafeSupplier<Number, Throwable> sizeInBytesUnsafeSupplier) {
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setTitle(
		UnsafeSupplier<String, Throwable> titleUnsafeSupplier) {
	}

	protected AggregateRating aggregateRating;
	protected Long[] category;
	protected String contentUrl;
	protected Creator creator;
	protected Date dateCreated;
	protected Date dateModified;
	protected String description;
	protected String encodingFormat;
	protected String fileExtension;
	protected Long folderId;
	protected Long id;
	protected String[] keywords;
	protected Number sizeInBytes;
	protected String title;
}