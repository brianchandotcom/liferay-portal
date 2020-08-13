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

package com.liferay.portal.tools.service.builder.test.model;

import java.io.Serializable;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.service.http.LazyBlobEntityServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class LazyBlobEntitySoap implements Serializable {

	public static LazyBlobEntitySoap toSoapModel(LazyBlobEntity model) {
		LazyBlobEntitySoap soapModel = new LazyBlobEntitySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setLazyBlobEntityId(model.getLazyBlobEntityId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setBlob1(model.getBlob1());
		soapModel.setBlob2(model.getBlob2());

		return soapModel;
	}

	public static LazyBlobEntitySoap[] toSoapModels(LazyBlobEntity[] models) {
		LazyBlobEntitySoap[] soapModels = new LazyBlobEntitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LazyBlobEntitySoap[][] toSoapModels(
		LazyBlobEntity[][] models) {

		LazyBlobEntitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new LazyBlobEntitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new LazyBlobEntitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LazyBlobEntitySoap[] toSoapModels(
		List<LazyBlobEntity> models) {

		List<LazyBlobEntitySoap> soapModels = new ArrayList<LazyBlobEntitySoap>(
			models.size());

		for (LazyBlobEntity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LazyBlobEntitySoap[soapModels.size()]);
	}

	public LazyBlobEntitySoap() {
	}

	public long getPrimaryKey() {
		return _lazyBlobEntityId;
	}

	public void setPrimaryKey(long pk) {
		setLazyBlobEntityId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLazyBlobEntityId() {
		return _lazyBlobEntityId;
	}

	public void setLazyBlobEntityId(long lazyBlobEntityId) {
		_lazyBlobEntityId = lazyBlobEntityId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public Blob getBlob1() {
		return _blob1;
	}

	public void setBlob1(Blob blob1) {
		_blob1 = blob1;
	}

	public Blob getBlob2() {
		return _blob2;
	}

	public void setBlob2(Blob blob2) {
		_blob2 = blob2;
	}

	private String _uuid;
	private long _lazyBlobEntityId;
	private long _groupId;
	private Blob _blob1;
	private Blob _blob2;

}