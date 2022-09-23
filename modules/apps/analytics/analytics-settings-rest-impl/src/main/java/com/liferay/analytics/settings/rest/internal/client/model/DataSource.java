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

package com.liferay.analytics.settings.rest.internal.client.model;

import java.util.Date;

/**
 * @author Riccardo Ferrari
 */
public class DataSource {

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getState() {
		return _state;
	}

	public String getStatus() {
		return _status;
	}

	public String getUrl() {
		return _url;
	}

	public String getWorkspaceURL() {
		return _workspaceURL;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setWorkspaceURL(String workspaceURL) {
		_workspaceURL = workspaceURL;
	}

	private Date _dateCreated;
	private Long _id;
	private String _name;
	private String _state;
	private String _status;
	private String _url;
	private String _workspaceURL;

}