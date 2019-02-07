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

package com.liferay.headless.foundation.dto;

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "Vocabulary")
public class Vocabulary {

	public String[] getAvailableLanguages() {
		return _availableLanguages;
	}

	public Long getContentSpace() {
		return _contentSpace;
	}

	public UserAccount getCreator() {
		return _creator;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public String getDescription() {
		return _description;
	}

	public Long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getSelf() {
		return _self;
	}

	public Category[] getVocabularyCategories() {
		return _vocabularyCategories;
	}

	public Long[] getVocabularyCategoriesIds() {
		return _vocabularyCategoriesIds;
	}

	public void setAvailableLanguages(String[] availableLanguages) {
		_availableLanguages = availableLanguages;
	}

	public void setContentSpace(Long contentSpace) {
		_contentSpace = contentSpace;
	}

	public void setCreator(UserAccount creator) {
		_creator = creator;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSelf(String self) {
		_self = self;
	}

	public void setVocabularyCategories(Category[] vocabularyCategories) {
		_vocabularyCategories = vocabularyCategories;
	}

	public void setVocabularyCategoriesIds(Long[] vocabularyCategoriesIds) {
		_vocabularyCategoriesIds = vocabularyCategoriesIds;
	}

	private String[] _availableLanguages;
	private Long _contentSpace;
	private UserAccount _creator;
	private Date _dateCreated;
	private Date _dateModified;
	private String _description;
	private Long _id;
	private String _name;
	private String _self;
	private Category[] _vocabularyCategories;
	private Long[] _vocabularyCategoriesIds;

}