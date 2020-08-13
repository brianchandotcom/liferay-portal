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

package com.liferay.portal.kernel.model;

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
public class CompanyInfoSoap implements Serializable {

	public static CompanyInfoSoap toSoapModel(CompanyInfo model) {
		CompanyInfoSoap soapModel = new CompanyInfoSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCompanyInfoId(model.getCompanyInfoId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setKey(model.getKey());

		return soapModel;
	}

	public static CompanyInfoSoap[] toSoapModels(CompanyInfo[] models) {
		CompanyInfoSoap[] soapModels = new CompanyInfoSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CompanyInfoSoap[][] toSoapModels(CompanyInfo[][] models) {
		CompanyInfoSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CompanyInfoSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CompanyInfoSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CompanyInfoSoap[] toSoapModels(List<CompanyInfo> models) {
		List<CompanyInfoSoap> soapModels = new ArrayList<CompanyInfoSoap>(
			models.size());

		for (CompanyInfo model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CompanyInfoSoap[soapModels.size()]);
	}

	public CompanyInfoSoap() {
	}

	public long getPrimaryKey() {
		return _companyInfoId;
	}

	public void setPrimaryKey(long pk) {
		setCompanyInfoId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCompanyInfoId() {
		return _companyInfoId;
	}

	public void setCompanyInfoId(long companyInfoId) {
		_companyInfoId = companyInfoId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	private long _mvccVersion;
	private long _companyInfoId;
	private long _companyId;
	private String _key;

}