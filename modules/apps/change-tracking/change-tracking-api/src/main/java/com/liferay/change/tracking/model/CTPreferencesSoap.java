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

package com.liferay.change.tracking.model;

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
public class CTPreferencesSoap implements Serializable {

	public static CTPreferencesSoap toSoapModel(CTPreferences model) {
		CTPreferencesSoap soapModel = new CTPreferencesSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtPreferencesId(model.getCtPreferencesId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setPreviousCtCollectionId(model.getPreviousCtCollectionId());
		soapModel.setConfirmationEnabled(model.isConfirmationEnabled());

		return soapModel;
	}

	public static CTPreferencesSoap[] toSoapModels(CTPreferences[] models) {
		CTPreferencesSoap[] soapModels = new CTPreferencesSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CTPreferencesSoap[][] toSoapModels(CTPreferences[][] models) {
		CTPreferencesSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CTPreferencesSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CTPreferencesSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CTPreferencesSoap[] toSoapModels(List<CTPreferences> models) {
		List<CTPreferencesSoap> soapModels = new ArrayList<CTPreferencesSoap>(
			models.size());

		for (CTPreferences model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CTPreferencesSoap[soapModels.size()]);
	}

	public CTPreferencesSoap() {
	}

	public long getPrimaryKey() {
		return _ctPreferencesId;
	}

	public void setPrimaryKey(long pk) {
		setCtPreferencesId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtPreferencesId() {
		return _ctPreferencesId;
	}

	public void setCtPreferencesId(long ctPreferencesId) {
		_ctPreferencesId = ctPreferencesId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getPreviousCtCollectionId() {
		return _previousCtCollectionId;
	}

	public void setPreviousCtCollectionId(long previousCtCollectionId) {
		_previousCtCollectionId = previousCtCollectionId;
	}

	public boolean getConfirmationEnabled() {
		return _confirmationEnabled;
	}

	public boolean isConfirmationEnabled() {
		return _confirmationEnabled;
	}

	public void setConfirmationEnabled(boolean confirmationEnabled) {
		_confirmationEnabled = confirmationEnabled;
	}

	private long _mvccVersion;
	private long _ctPreferencesId;
	private long _companyId;
	private long _userId;
	private long _ctCollectionId;
	private long _previousCtCollectionId;
	private boolean _confirmationEnabled;

}