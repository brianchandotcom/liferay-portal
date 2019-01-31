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

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "Keyword")
public class Keyword {

	public ContentSpace getContentSpace() {
		return _contentSpace;
	}

	public UserAccount getCreator() {
		return _creator;
	}

	public String getDateCreated() {
		return _dateCreated;
	}

	public String getDateModified() {
		return _dateModified;
	}

	public Integer getId() {
		return _id;
	}

	public Number getKeywordUsageCount() {
		return _keywordUsageCount;
	}

	public String getName() {
		return _name;
	}

	public String getSelf() {
		return _self;
	}

	public void setContentSpace(ContentSpace contentSpace) {
		_contentSpace = contentSpace;
	}

	public void setCreator(UserAccount creator) {
		_creator = creator;
	}

	public void setDateCreated(String dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(String dateModified) {
		_dateModified = dateModified;
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setKeywordUsageCount(Number keywordUsageCount) {
		_keywordUsageCount = keywordUsageCount;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSelf(String self) {
		_self = self;
	}

	private ContentSpace _contentSpace;
	private UserAccount _creator;
	private String _dateCreated;
	private String _dateModified;
	private Integer _id;
	private Number _keywordUsageCount;
	private String _name;
	private String _self;

}