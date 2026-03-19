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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.compat700.service.http.LazyBlobEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LazyBlobEntrySoap implements Serializable {

	public static LazyBlobEntrySoap toSoapModel(LazyBlobEntry model) {
		LazyBlobEntrySoap soapModel = new LazyBlobEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setLazyBlobEntryId(model.getLazyBlobEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setBlob1(model.getBlob1());
		soapModel.setBlob2(model.getBlob2());

		return soapModel;
	}

	public static LazyBlobEntrySoap[] toSoapModels(LazyBlobEntry[] models) {
		LazyBlobEntrySoap[] soapModels = new LazyBlobEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LazyBlobEntrySoap[][] toSoapModels(LazyBlobEntry[][] models) {
		LazyBlobEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LazyBlobEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new LazyBlobEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LazyBlobEntrySoap[] toSoapModels(List<LazyBlobEntry> models) {
		List<LazyBlobEntrySoap> soapModels = new ArrayList<LazyBlobEntrySoap>(
			models.size());

		for (LazyBlobEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LazyBlobEntrySoap[soapModels.size()]);
	}

	public LazyBlobEntrySoap() {
	}

	public long getPrimaryKey() {
		return _lazyBlobEntryId;
	}

	public void setPrimaryKey(long pk) {
		setLazyBlobEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLazyBlobEntryId() {
		return _lazyBlobEntryId;
	}

	public void setLazyBlobEntryId(long lazyBlobEntryId) {
		_lazyBlobEntryId = lazyBlobEntryId;
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
	private long _lazyBlobEntryId;
	private long _groupId;
	private Blob _blob1;
	private Blob _blob2;

}
// LIFERAY-SERVICE-BUILDER-HASH:1815139447