/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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