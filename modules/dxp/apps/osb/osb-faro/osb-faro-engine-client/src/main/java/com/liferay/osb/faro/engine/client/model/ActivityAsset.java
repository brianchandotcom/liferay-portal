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
public class ActivityAsset {

	public long getCount() {
		return _count;
	}

	public String getDataSourceAssetPK() {
		return _dataSourceAssetPK;
	}

	public String getDataSourceName() {
		return _dataSourceName;
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public void setCount(long count) {
		_count = count;
	}

	public void setDataSourceAssetPK(String dataSourceAssetPK) {
		_dataSourceAssetPK = dataSourceAssetPK;
	}

	public void setDataSourceName(String dataSourceName) {
		_dataSourceName = dataSourceName;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _count;
	private String _dataSourceAssetPK;
	private String _dataSourceName;
	private String _id;
	private String _name;

}