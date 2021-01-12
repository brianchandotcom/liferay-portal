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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.tools.service.builder.test.service.http.ERCGroupEntityServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ERCGroupEntitySoap implements Serializable {

	public static ERCGroupEntitySoap toSoapModel(ERCGroupEntity model) {
		ERCGroupEntitySoap soapModel = new ERCGroupEntitySoap();

		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setErcGroupEntityId(model.getErcGroupEntityId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());

		return soapModel;
	}

	public static ERCGroupEntitySoap[] toSoapModels(ERCGroupEntity[] models) {
		ERCGroupEntitySoap[] soapModels = new ERCGroupEntitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ERCGroupEntitySoap[][] toSoapModels(
		ERCGroupEntity[][] models) {

		ERCGroupEntitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ERCGroupEntitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ERCGroupEntitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ERCGroupEntitySoap[] toSoapModels(
		List<ERCGroupEntity> models) {

		List<ERCGroupEntitySoap> soapModels = new ArrayList<ERCGroupEntitySoap>(
			models.size());

		for (ERCGroupEntity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ERCGroupEntitySoap[soapModels.size()]);
	}

	public ERCGroupEntitySoap() {
	}

	public long getPrimaryKey() {
		return _ercGroupEntityId;
	}

	public void setPrimaryKey(long pk) {
		setErcGroupEntityId(pk);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getErcGroupEntityId() {
		return _ercGroupEntityId;
	}

	public void setErcGroupEntityId(long ercGroupEntityId) {
		_ercGroupEntityId = ercGroupEntityId;
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
	private long _ercGroupEntityId;
	private long _companyId;
	private long _groupId;

}