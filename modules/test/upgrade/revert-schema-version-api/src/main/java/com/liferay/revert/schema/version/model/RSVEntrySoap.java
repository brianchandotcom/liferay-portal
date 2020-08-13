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

package com.liferay.revert.schema.version.model;

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
public class RSVEntrySoap implements Serializable {

	public static RSVEntrySoap toSoapModel(RSVEntry model) {
		RSVEntrySoap soapModel = new RSVEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setRsvEntryId(model.getRsvEntryId());
		soapModel.setCompanyId(model.getCompanyId());

		return soapModel;
	}

	public static RSVEntrySoap[] toSoapModels(RSVEntry[] models) {
		RSVEntrySoap[] soapModels = new RSVEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RSVEntrySoap[][] toSoapModels(RSVEntry[][] models) {
		RSVEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RSVEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new RSVEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RSVEntrySoap[] toSoapModels(List<RSVEntry> models) {
		List<RSVEntrySoap> soapModels = new ArrayList<RSVEntrySoap>(
			models.size());

		for (RSVEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RSVEntrySoap[soapModels.size()]);
	}

	public RSVEntrySoap() {
	}

	public long getPrimaryKey() {
		return _rsvEntryId;
	}

	public void setPrimaryKey(long pk) {
		setRsvEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getRsvEntryId() {
		return _rsvEntryId;
	}

	public void setRsvEntryId(long rsvEntryId) {
		_rsvEntryId = rsvEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	private long _mvccVersion;
	private long _rsvEntryId;
	private long _companyId;

}