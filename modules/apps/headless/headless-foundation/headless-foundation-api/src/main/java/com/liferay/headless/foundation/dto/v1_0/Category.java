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

package com.liferay.headless.foundation.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import java.util.Date;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface Category {

	public String[] getAvailableLanguages();

	public void setAvailableLanguages(
			String[] availableLanguages);

	public void setAvailableLanguages(
			UnsafeSupplier<String[], Throwable>
				availableLanguagesUnsafeSupplier);
	public ParentCategory getParentCategory();

	public void setParentCategory(
			ParentCategory parentCategory);

	public void setParentCategory(
			UnsafeSupplier<ParentCategory, Throwable>
				parentCategoryUnsafeSupplier);
	public Creator getCreator();

	public void setCreator(
			Creator creator);

	public void setCreator(
			UnsafeSupplier<Creator, Throwable>
				creatorUnsafeSupplier);
	public Long getCreatorId();

	public void setCreatorId(
			Long creatorId);

	public void setCreatorId(
			UnsafeSupplier<Long, Throwable>
				creatorIdUnsafeSupplier);
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
	public String getDescription();

	public void setDescription(
			String description);

	public void setDescription(
			UnsafeSupplier<String, Throwable>
				descriptionUnsafeSupplier);
	public Boolean getHasCategories();

	public void setHasCategories(
			Boolean hasCategories);

	public void setHasCategories(
			UnsafeSupplier<Boolean, Throwable>
				hasCategoriesUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public String getName();

	public void setName(
			String name);

	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier);
	public ParentVocabulary getParentVocabulary();

	public void setParentVocabulary(
			ParentVocabulary parentVocabulary);

	public void setParentVocabulary(
			UnsafeSupplier<ParentVocabulary, Throwable>
				parentVocabularyUnsafeSupplier);
	public Long getParentVocabularyId();

	public void setParentVocabularyId(
			Long parentVocabularyId);

	public void setParentVocabularyId(
			UnsafeSupplier<Long, Throwable>
				parentVocabularyIdUnsafeSupplier);

}