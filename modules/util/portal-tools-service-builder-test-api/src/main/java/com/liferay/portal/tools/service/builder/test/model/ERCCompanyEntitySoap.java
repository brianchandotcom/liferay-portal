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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.service.http.ERCCompanyEntityServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ERCCompanyEntitySoap implements Serializable {

	public static ERCCompanyEntitySoap toSoapModel(ERCCompanyEntity model) {
		ERCCompanyEntitySoap soapModel = new ERCCompanyEntitySoap();

		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setErcCompanyEntityId(model.getErcCompanyEntityId());
		soapModel.setCompanyId(model.getCompanyId());

		return soapModel;
	}

	public static ERCCompanyEntitySoap[] toSoapModels(
		ERCCompanyEntity[] models) {

		ERCCompanyEntitySoap[] soapModels =
			new ERCCompanyEntitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ERCCompanyEntitySoap[][] toSoapModels(
		ERCCompanyEntity[][] models) {

		ERCCompanyEntitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ERCCompanyEntitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ERCCompanyEntitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ERCCompanyEntitySoap[] toSoapModels(
		List<ERCCompanyEntity> models) {

		List<ERCCompanyEntitySoap> soapModels =
			new ArrayList<ERCCompanyEntitySoap>(models.size());

		for (ERCCompanyEntity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ERCCompanyEntitySoap[soapModels.size()]);
	}

	public ERCCompanyEntitySoap() {
	}

	public long getPrimaryKey() {
		return _ercCompanyEntityId;
	}

	public void setPrimaryKey(long pk) {
		setErcCompanyEntityId(pk);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getErcCompanyEntityId() {
		return _ercCompanyEntityId;
	}

	public void setErcCompanyEntityId(long ercCompanyEntityId) {
		_ercCompanyEntityId = ercCompanyEntityId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	private String _externalReferenceCode;
	private long _ercCompanyEntityId;
	private long _companyId;

}