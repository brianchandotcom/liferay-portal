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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DSLQueryStatusEntrySoap implements Serializable {

	public static DSLQueryStatusEntrySoap toSoapModel(
		DSLQueryStatusEntry model) {

		DSLQueryStatusEntrySoap soapModel = new DSLQueryStatusEntrySoap();

		soapModel.setDslQueryStatusEntryId(model.getDslQueryStatusEntryId());
		soapModel.setDslQueryEntryId(model.getDslQueryEntryId());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static DSLQueryStatusEntrySoap[] toSoapModels(
		DSLQueryStatusEntry[] models) {

		DSLQueryStatusEntrySoap[] soapModels =
			new DSLQueryStatusEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DSLQueryStatusEntrySoap[][] toSoapModels(
		DSLQueryStatusEntry[][] models) {

		DSLQueryStatusEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DSLQueryStatusEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new DSLQueryStatusEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DSLQueryStatusEntrySoap[] toSoapModels(
		List<DSLQueryStatusEntry> models) {

		List<DSLQueryStatusEntrySoap> soapModels =
			new ArrayList<DSLQueryStatusEntrySoap>(models.size());

		for (DSLQueryStatusEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DSLQueryStatusEntrySoap[soapModels.size()]);
	}

	public DSLQueryStatusEntrySoap() {
	}

	public long getPrimaryKey() {
		return _dslQueryStatusEntryId;
	}

	public void setPrimaryKey(long pk) {
		setDslQueryStatusEntryId(pk);
	}

	public long getDslQueryStatusEntryId() {
		return _dslQueryStatusEntryId;
	}

	public void setDslQueryStatusEntryId(long dslQueryStatusEntryId) {
		_dslQueryStatusEntryId = dslQueryStatusEntryId;
	}

	public long getDslQueryEntryId() {
		return _dslQueryEntryId;
	}

	public void setDslQueryEntryId(long dslQueryEntryId) {
		_dslQueryEntryId = dslQueryEntryId;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	private long _dslQueryStatusEntryId;
	private long _dslQueryEntryId;
	private String _status;
	private Date _statusDate;

}