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

package com.liferay.wiki.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class WikiPageResourceSoap implements Serializable {

	public static WikiPageResourceSoap toSoapModel(WikiPageResource model) {
		WikiPageResourceSoap soapModel = new WikiPageResourceSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setResourcePrimKey(model.getResourcePrimKey());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setNodeId(model.getNodeId());
		soapModel.setTitle(model.getTitle());

		return soapModel;
	}

	public static WikiPageResourceSoap[] toSoapModels(
		WikiPageResource[] models) {

		WikiPageResourceSoap[] soapModels =
			new WikiPageResourceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static WikiPageResourceSoap[][] toSoapModels(
		WikiPageResource[][] models) {

		WikiPageResourceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new WikiPageResourceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new WikiPageResourceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static WikiPageResourceSoap[] toSoapModels(
		List<WikiPageResource> models) {

		List<WikiPageResourceSoap> soapModels =
			new ArrayList<WikiPageResourceSoap>(models.size());

		for (WikiPageResource model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new WikiPageResourceSoap[soapModels.size()]);
	}

	public WikiPageResourceSoap() {
	}

	public long getPrimaryKey() {
		return _resourcePrimKey;
	}

	public void setPrimaryKey(long pk) {
		setResourcePrimKey(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getResourcePrimKey() {
		return _resourcePrimKey;
	}

	public void setResourcePrimKey(long resourcePrimKey) {
		_resourcePrimKey = resourcePrimKey;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getNodeId() {
		return _nodeId;
	}

	public void setNodeId(long nodeId) {
		_nodeId = nodeId;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _resourcePrimKey;
	private long _groupId;
	private long _companyId;
	private long _nodeId;
	private String _title;

}