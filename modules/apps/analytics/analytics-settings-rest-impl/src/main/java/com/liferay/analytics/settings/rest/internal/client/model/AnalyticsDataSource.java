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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author Riccardo Ferrari
 */
public class AnalyticsDataSource {

	public Long[] getCommerceChannelIds() {
		return _commerceChannelIds;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public Long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	@JsonProperty("groupIds")
	public Long[] getSiteIds() {
		return _siteIds;
	}

	public String getState() {
		return _state;
	}

	public String getStatus() {
		return _status;
	}

	public String getURL() {
		return _url;
	}

	public String getWorkspaceURL() {
		return _workspaceURL;
	}

	public void setCommerceChannelIds(Long[] commerceChannelIds) {
		_commerceChannelIds = commerceChannelIds;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSiteIds(Long[] siteIds) {
		_siteIds = siteIds;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public void setURL(String url) {
		_url = url;
	}

	public void setWorkspaceURL(String workspaceURL) {
		_workspaceURL = workspaceURL;
	}

	private Long[] _commerceChannelIds;
	private Date _createDate;
	private Long _id;
	private String _name;
	private Long[] _siteIds;
	private String _state;
	private String _status;
	private String _url;
	private String _workspaceURL;

}