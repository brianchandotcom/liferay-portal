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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.service.http.EagerBlobEntityServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class EagerBlobEntitySoap implements Serializable {

	public static EagerBlobEntitySoap toSoapModel(EagerBlobEntity model) {
		EagerBlobEntitySoap soapModel = new EagerBlobEntitySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setEagerBlobEntityId(model.getEagerBlobEntityId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setBlob(model.getBlob());

		return soapModel;
	}

	public static EagerBlobEntitySoap[] toSoapModels(EagerBlobEntity[] models) {
		EagerBlobEntitySoap[] soapModels =
			new EagerBlobEntitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static EagerBlobEntitySoap[][] toSoapModels(
		EagerBlobEntity[][] models) {

		EagerBlobEntitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new EagerBlobEntitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new EagerBlobEntitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static EagerBlobEntitySoap[] toSoapModels(
		List<EagerBlobEntity> models) {

		List<EagerBlobEntitySoap> soapModels =
			new ArrayList<EagerBlobEntitySoap>(models.size());

		for (EagerBlobEntity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new EagerBlobEntitySoap[soapModels.size()]);
	}

	public EagerBlobEntitySoap() {
	}

	public long getPrimaryKey() {
		return _eagerBlobEntityId;
	}

	public void setPrimaryKey(long pk) {
		setEagerBlobEntityId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getEagerBlobEntityId() {
		return _eagerBlobEntityId;
	}

	public void setEagerBlobEntityId(long eagerBlobEntityId) {
		_eagerBlobEntityId = eagerBlobEntityId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public Blob getBlob() {
		return _blob;
	}

	public void setBlob(Blob blob) {
		_blob = blob;
	}

	private String _uuid;
	private long _eagerBlobEntityId;
	private long _groupId;
	private Blob _blob;

}