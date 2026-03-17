/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.model;

import java.io.Serializable;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.compat700.service.http.EagerBlobEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
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
// SB-Hash:580897604