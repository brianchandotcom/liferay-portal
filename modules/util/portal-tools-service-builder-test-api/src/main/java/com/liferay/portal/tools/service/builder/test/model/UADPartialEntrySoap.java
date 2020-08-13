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
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.service.http.UADPartialEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class UADPartialEntrySoap implements Serializable {

	public static UADPartialEntrySoap toSoapModel(UADPartialEntry model) {
		UADPartialEntrySoap soapModel = new UADPartialEntrySoap();

		soapModel.setUadPartialEntryId(model.getUadPartialEntryId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setMessage(model.getMessage());

		return soapModel;
	}

	public static UADPartialEntrySoap[] toSoapModels(UADPartialEntry[] models) {
		UADPartialEntrySoap[] soapModels =
			new UADPartialEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static UADPartialEntrySoap[][] toSoapModels(
		UADPartialEntry[][] models) {

		UADPartialEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new UADPartialEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new UADPartialEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static UADPartialEntrySoap[] toSoapModels(
		List<UADPartialEntry> models) {

		List<UADPartialEntrySoap> soapModels =
			new ArrayList<UADPartialEntrySoap>(models.size());

		for (UADPartialEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new UADPartialEntrySoap[soapModels.size()]);
	}

	public UADPartialEntrySoap() {
	}

	public long getPrimaryKey() {
		return _uadPartialEntryId;
	}

	public void setPrimaryKey(long pk) {
		setUadPartialEntryId(pk);
	}

	public long getUadPartialEntryId() {
		return _uadPartialEntryId;
	}

	public void setUadPartialEntryId(long uadPartialEntryId) {
		_uadPartialEntryId = uadPartialEntryId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public String getMessage() {
		return _message;
	}

	public void setMessage(String message) {
		_message = message;
	}

	private long _uadPartialEntryId;
	private long _userId;
	private String _userName;
	private String _message;

}