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

package com.liferay.data.engine.model;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DEDataLayout implements Serializable {

	public Date getCreateDate() {
		return _createDate;
	}

	public long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public String getDataLayoutKey() {
		return _dataLayoutKey;
	}

	public List<DEDataLayoutPage> getDEDataLayoutPages() {
		return _deDataLayoutPages;
	}

	public Map<Locale, String> getDescription() {
		return _description;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getId() {
		return _id;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public Map<Locale, String> getName() {
		return _name;
	}

	public String getPaginationMode() {
		return _paginationMode;
	}

	public long getUserId() {
		return _userId;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setDataDefinitionId(long dataDefinitionId) {
		_dataDefinitionId = dataDefinitionId;
	}

	public void setDataLayoutKey(String dataLayoutKey) {
		_dataLayoutKey = dataLayoutKey;
	}

	public void setDEDataLayoutPages(List<DEDataLayoutPage> deDataLayoutPages) {
		_deDataLayoutPages = deDataLayoutPages;
	}

	public void setDescription(Map<Locale, String> description) {
		_description = description;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setName(Map<Locale, String> name) {
		_name = name;
	}

	public void setPaginationMode(String paginationMode) {
		_paginationMode = paginationMode;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private Date _createDate;
	private long _dataDefinitionId;
	private String _dataLayoutKey;
	private List<DEDataLayoutPage> _deDataLayoutPages;
	private Map<Locale, String> _description;
	private long _groupId;
	private long _id;
	private Date _modifiedDate;
	private Map<Locale, String> _name;
	private String _paginationMode;
	private long _userId;

}