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

package com.liferay.headless.web.experience.client.dto.v1_0;

import com.liferay.headless.web.experience.client.function.UnsafeSupplier;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentStructure {

	public String[] getAvailableLanguages() {
		return availableLanguages;
	}

	public void setAvailableLanguages(String[] availableLanguages) {
		this.availableLanguages = availableLanguages;
	}

	public void setAvailableLanguages(
		UnsafeSupplier<String[], Exception> availableLanguagesUnsafeSupplier) {

		try {
			availableLanguages = availableLanguagesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] availableLanguages;

	public Long getContentSpaceId() {
		return contentSpaceId;
	}

	public void setContentSpaceId(Long contentSpaceId) {
		this.contentSpaceId = contentSpaceId;
	}

	public void setContentSpaceId(
		UnsafeSupplier<Long, Exception> contentSpaceIdUnsafeSupplier) {

		try {
			contentSpaceId = contentSpaceIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long contentSpaceId;

	public com.liferay.headless.web.experience.dto.v1_0.ContentStructureField[]
		getContentStructureFields() {

		return contentStructureFields;
	}

	public void setContentStructureFields(
		com.liferay.headless.web.experience.dto.v1_0.ContentStructureField[]
			contentStructureFields) {

		this.contentStructureFields = contentStructureFields;
	}

	public void setContentStructureFields(
		UnsafeSupplier
			<com.liferay.headless.web.experience.dto.v1_0.
				ContentStructureField[],
			 Exception> contentStructureFieldsUnsafeSupplier) {

		try {
			contentStructureFields = contentStructureFieldsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected
		com.liferay.headless.web.experience.dto.v1_0.ContentStructureField[]
			contentStructureFields;

	public com.liferay.headless.web.experience.dto.v1_0.Creator getCreator() {
		return creator;
	}

	public void setCreator(
		com.liferay.headless.web.experience.dto.v1_0.Creator creator) {

		this.creator = creator;
	}

	public void setCreator(
		UnsafeSupplier
			<com.liferay.headless.web.experience.dto.v1_0.Creator, Exception>
				creatorUnsafeSupplier) {

		try {
			creator = creatorUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected com.liferay.headless.web.experience.dto.v1_0.Creator creator;

	public java.util.Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(java.util.Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<java.util.Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected java.util.Date dateCreated;

	public java.util.Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(java.util.Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<java.util.Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected java.util.Date dateModified;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

}