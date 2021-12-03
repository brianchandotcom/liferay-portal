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

package com.liferay.portal.language.override.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.language.override.service.http.PLOEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class PLOEntrySoap implements Serializable {

	public static PLOEntrySoap toSoapModel(PLOEntry model) {
		PLOEntrySoap soapModel = new PLOEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setPloEntryId(model.getPloEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setKey(model.getKey());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setValue(model.getValue());

		return soapModel;
	}

	public static PLOEntrySoap[] toSoapModels(PLOEntry[] models) {
		PLOEntrySoap[] soapModels = new PLOEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PLOEntrySoap[][] toSoapModels(PLOEntry[][] models) {
		PLOEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PLOEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new PLOEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PLOEntrySoap[] toSoapModels(List<PLOEntry> models) {
		List<PLOEntrySoap> soapModels = new ArrayList<PLOEntrySoap>(
			models.size());

		for (PLOEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PLOEntrySoap[soapModels.size()]);
	}

	public PLOEntrySoap() {
	}

	public long getPrimaryKey() {
		return _ploEntryId;
	}

	public void setPrimaryKey(long pk) {
		setPloEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getPloEntryId() {
		return _ploEntryId;
	}

	public void setPloEntryId(long ploEntryId) {
		_ploEntryId = ploEntryId;
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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	private long _mvccVersion;
	private long _ploEntryId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _key;
	private String _languageId;
	private String _value;

}