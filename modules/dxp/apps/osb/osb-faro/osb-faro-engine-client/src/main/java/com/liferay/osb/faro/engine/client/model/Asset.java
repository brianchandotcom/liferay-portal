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

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class Asset {

	public String getAssetType() {
		return _assetType;
	}

	public String getCanonicalUrl() {
		return _canonicalUrl;
	}

	public String getDataSourceAssetPK() {
		return _dataSourceAssetPK;
	}

	public String getDataSourceId() {
		return _dataSourceId;
	}

	public Date getDateCreated() {
		if (_dateCreated == null) {
			return null;
		}

		return new Date(_dateCreated.getTime());
	}

	public Date getDateModified() {
		if (_dateModified == null) {
			return null;
		}

		return new Date(_dateModified.getTime());
	}

	public String getDescription() {
		return _description;
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getUrl() {
		return _url;
	}

	public void setAssetType(String assetType) {
		_assetType = assetType;
	}

	public void setCanonicalUrl(String canonicalUrl) {
		_canonicalUrl = canonicalUrl;
	}

	public void setDataSourceAssetPK(String dataSourceAssetPK) {
		_dataSourceAssetPK = dataSourceAssetPK;
	}

	public void setDataSourceId(String dataSourceId) {
		_dataSourceId = dataSourceId;
	}

	public void setDateCreated(Date dateCreated) {
		if (dateCreated != null) {
			_dateCreated = new Date(dateCreated.getTime());
		}
	}

	public void setDateModified(Date dateModified) {
		if (dateModified != null) {
			_dateModified = new Date(dateModified.getTime());
		}
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public enum AssetType {

		Blog, CustomAsset, Document, Form, Journal, Page

	}

	private String _assetType;
	private String _canonicalUrl;
	private String _dataSourceAssetPK;
	private String _dataSourceId;
	private Date _dateCreated;
	private Date _dateModified;
	private String _description;
	private String _id;
	private String _name;
	private String _url;

}