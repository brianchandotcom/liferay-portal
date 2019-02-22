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

package com.liferay.headless.form.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import java.util.Date;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface Form {

	public String[] getAvailableLanguages();

	public Long getContentSpace();

	public Creator getCreator();

	public Date getDateCreated();

	public Date getDateModified();

	public Date getDatePublished();

	public String getDefaultLanguage();

	public String getDescription();

	public FormRecord[] getFormRecords();

	public Long[] getFormRecordsIds();

	public Long getId();

	public String getName();

	public FormStructure getStructure();

	public Long getStructureId();

	public void setAvailableLanguages(String[] availableLanguages);

	public void setAvailableLanguages(
		UnsafeSupplier<String[], Throwable> availableLanguagesUnsafeSupplier);

	public void setContentSpace(Long contentSpace);

	public void setContentSpace(
		UnsafeSupplier<Long, Throwable> contentSpaceUnsafeSupplier);

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

	public void setDefaultLanguage(String defaultLanguage);

	public void setDefaultLanguage(
		UnsafeSupplier<String, Throwable> defaultLanguageUnsafeSupplier);

	public void setDescription(String description);

	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier);

	public void setFormRecords(FormRecord[] formRecords);

	public void setFormRecords(
		UnsafeSupplier<FormRecord[], Throwable> formRecordsUnsafeSupplier);

	public void setFormRecordsIds(Long[] formRecordsIds);

	public void setFormRecordsIds(
		UnsafeSupplier<Long[], Throwable> formRecordsIdsUnsafeSupplier);

	public void setId(Long id);

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);

	public void setName(String name);

	public void setName(UnsafeSupplier<String, Throwable> nameUnsafeSupplier);

	public void setStructure(FormStructure structure);

	public void setStructure(
		UnsafeSupplier<FormStructure, Throwable> structureUnsafeSupplier);

	public void setStructureId(Long structureId);

	public void setStructureId(
		UnsafeSupplier<Long, Throwable> structureIdUnsafeSupplier);

}