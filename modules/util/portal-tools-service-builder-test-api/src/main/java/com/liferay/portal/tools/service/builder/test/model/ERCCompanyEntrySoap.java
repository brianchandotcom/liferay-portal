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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.service.http.ERCCompanyEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ERCCompanyEntrySoap implements Serializable {

	public static ERCCompanyEntrySoap toSoapModel(ERCCompanyEntry model) {
		ERCCompanyEntrySoap soapModel = new ERCCompanyEntrySoap();

		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setErcCompanyEntryId(model.getErcCompanyEntryId());
		soapModel.setCompanyId(model.getCompanyId());

		return soapModel;
	}

	public static ERCCompanyEntrySoap[] toSoapModels(ERCCompanyEntry[] models) {
		ERCCompanyEntrySoap[] soapModels =
			new ERCCompanyEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ERCCompanyEntrySoap[][] toSoapModels(
		ERCCompanyEntry[][] models) {

		ERCCompanyEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ERCCompanyEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ERCCompanyEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ERCCompanyEntrySoap[] toSoapModels(
		List<ERCCompanyEntry> models) {

		List<ERCCompanyEntrySoap> soapModels =
			new ArrayList<ERCCompanyEntrySoap>(models.size());

		for (ERCCompanyEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ERCCompanyEntrySoap[soapModels.size()]);
	}

	public ERCCompanyEntrySoap() {
	}

	public long getPrimaryKey() {
		return _ercCompanyEntryId;
	}

	public void setPrimaryKey(long pk) {
		setErcCompanyEntryId(pk);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getErcCompanyEntryId() {
		return _ercCompanyEntryId;
	}

	public void setErcCompanyEntryId(long ercCompanyEntryId) {
		_ercCompanyEntryId = ercCompanyEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	private String _externalReferenceCode;
	private long _ercCompanyEntryId;
	private long _companyId;

}