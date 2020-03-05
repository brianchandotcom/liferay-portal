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
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DEDataLayout implements Serializable {

	public Long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public String getDataLayoutKey() {
		return _dataLayoutKey;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public List<DEDataLayoutPage> getDEDataLayoutPages() {
		return _deDataLayoutPages;
	}

	public Map<String, Object> getDescription() {
		return _description;
	}

	public Long getId() {
		return _id;
	}

	public Map<String, Object> getName() {
		return _name;
	}

	public String getPaginationMode() {
		return _paginationMode;
	}

	public Long getSiteId() {
		return _siteId;
	}

	public Long getUserId() {
		return _userId;
	}

	public void setDataDefinitionId(Long dataDefinitionId) {
		_dataDefinitionId = dataDefinitionId;
	}

	public void setDataLayoutKey(String dataLayoutKey) {
		_dataLayoutKey = dataLayoutKey;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setDEDataLayoutPages(List<DEDataLayoutPage> deDataLayoutPages) {
		_deDataLayoutPages = deDataLayoutPages;
	}

	public void setDescription(Map<String, Object> description) {
		_description = description;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setName(Map<String, Object> name) {
		_name = name;
	}

	public void setPaginationMode(String paginationMode) {
		_paginationMode = paginationMode;
	}

	public void setSiteId(Long siteId) {
		_siteId = siteId;
	}

	public void setUserId(Long userId) {
		_userId = userId;
	}

	private Long _dataDefinitionId;
	private String _dataLayoutKey;
	private Date _dateCreated;
	private Date _dateModified;
	private List<DEDataLayoutPage> _deDataLayoutPages;
	private Map<String, Object> _description;
	private Long _id;
	private Map<String, Object> _name;
	private String _paginationMode;
	private Long _siteId;
	private Long _userId;

}