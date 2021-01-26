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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.service.http.ERCGroupEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ERCGroupEntrySoap implements Serializable {

	public static ERCGroupEntrySoap toSoapModel(ERCGroupEntry model) {
		ERCGroupEntrySoap soapModel = new ERCGroupEntrySoap();

		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setErcGroupEntryId(model.getErcGroupEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());

		return soapModel;
	}

	public static ERCGroupEntrySoap[] toSoapModels(ERCGroupEntry[] models) {
		ERCGroupEntrySoap[] soapModels = new ERCGroupEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ERCGroupEntrySoap[][] toSoapModels(ERCGroupEntry[][] models) {
		ERCGroupEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ERCGroupEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ERCGroupEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ERCGroupEntrySoap[] toSoapModels(List<ERCGroupEntry> models) {
		List<ERCGroupEntrySoap> soapModels = new ArrayList<ERCGroupEntrySoap>(
			models.size());

		for (ERCGroupEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ERCGroupEntrySoap[soapModels.size()]);
	}

	public ERCGroupEntrySoap() {
	}

	public long getPrimaryKey() {
		return _ercGroupEntryId;
	}

	public void setPrimaryKey(long pk) {
		setErcGroupEntryId(pk);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getErcGroupEntryId() {
		return _ercGroupEntryId;
	}

	public void setErcGroupEntryId(long ercGroupEntryId) {
		_ercGroupEntryId = ercGroupEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	private String _externalReferenceCode;
	private long _ercGroupEntryId;
	private long _companyId;
	private long _groupId;

}