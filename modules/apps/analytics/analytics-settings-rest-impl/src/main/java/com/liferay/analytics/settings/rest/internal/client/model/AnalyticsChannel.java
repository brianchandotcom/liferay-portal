/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author Riccardo Ferrari
 */
public class AnalyticsChannel {

	@JsonProperty("dataSources")
	public AnalyticsDataSource[] getAnalyticsDataSources() {
		return _analyticsDataSources;
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

	public void setAnalyticsDataSources(
		AnalyticsDataSource[] analyticsDataSources) {

		_analyticsDataSources = analyticsDataSources;
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

	private AnalyticsDataSource[] _analyticsDataSources;
	private Date _createDate;
	private Long _id;
	private String _name;

}