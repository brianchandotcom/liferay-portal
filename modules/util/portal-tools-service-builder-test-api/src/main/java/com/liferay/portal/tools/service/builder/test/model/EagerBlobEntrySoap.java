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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.service.http.EagerBlobEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class EagerBlobEntrySoap implements Serializable {

	public static EagerBlobEntrySoap toSoapModel(EagerBlobEntry model) {
		EagerBlobEntrySoap soapModel = new EagerBlobEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setEagerBlobEntryId(model.getEagerBlobEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setBlob(model.getBlob());

		return soapModel;
	}

	public static EagerBlobEntrySoap[] toSoapModels(EagerBlobEntry[] models) {
		EagerBlobEntrySoap[] soapModels = new EagerBlobEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static EagerBlobEntrySoap[][] toSoapModels(
		EagerBlobEntry[][] models) {

		EagerBlobEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new EagerBlobEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new EagerBlobEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static EagerBlobEntrySoap[] toSoapModels(
		List<EagerBlobEntry> models) {

		List<EagerBlobEntrySoap> soapModels = new ArrayList<EagerBlobEntrySoap>(
			models.size());

		for (EagerBlobEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new EagerBlobEntrySoap[soapModels.size()]);
	}

	public EagerBlobEntrySoap() {
	}

	public long getPrimaryKey() {
		return _eagerBlobEntryId;
	}

	public void setPrimaryKey(long pk) {
		setEagerBlobEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getEagerBlobEntryId() {
		return _eagerBlobEntryId;
	}

	public void setEagerBlobEntryId(long eagerBlobEntryId) {
		_eagerBlobEntryId = eagerBlobEntryId;
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
	private long _eagerBlobEntryId;
	private long _groupId;
	private Blob _blob;

}