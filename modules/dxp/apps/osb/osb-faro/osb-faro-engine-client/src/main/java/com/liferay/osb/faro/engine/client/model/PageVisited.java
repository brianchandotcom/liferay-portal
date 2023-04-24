/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Matthew Kong
 */
public class PageVisited {

	public String getDataSourceId() {
		return _dataSourceId;
	}

	public String getTitle() {
		return _title;
	}

	public int getUniqueVisitsCount() {
		return _uniqueVisitsCount;
	}

	public String getUrl() {
		return _url;
	}

	public void setDataSourceId(String dataSourceId) {
		_dataSourceId = dataSourceId;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setUniqueVisitsCount(int uniqueVisitsCount) {
		_uniqueVisitsCount = uniqueVisitsCount;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private String _dataSourceId;
	private String _title;
	private int _uniqueVisitsCount;
	private String _url;

}