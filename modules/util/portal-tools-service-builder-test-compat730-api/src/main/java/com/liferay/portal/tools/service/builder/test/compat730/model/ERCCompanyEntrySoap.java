/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.model;

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
public class ERCCompanyEntrySoap implements Serializable {

	public static ERCCompanyEntrySoap toSoapModel(ERCCompanyEntry model) {
		ERCCompanyEntrySoap soapModel = new ERCCompanyEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setErcCompanyEntryId(model.getErcCompanyEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setColumn1(model.getColumn1());

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

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
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

	public int getColumn1() {
		return _column1;
	}

	public void setColumn1(int column1) {
		_column1 = column1;
	}

	private String _uuid;
	private String _externalReferenceCode;
	private long _ercCompanyEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private int _column1;

}
// LIFERAY-SERVICE-BUILDER-HASH:-213576385