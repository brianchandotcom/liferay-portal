/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat720.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ERCGroupEntrySoap implements Serializable {

	public static ERCGroupEntrySoap toSoapModel(ERCGroupEntry model) {
		ERCGroupEntrySoap soapModel = new ERCGroupEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setErcGroupEntryId(model.getErcGroupEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());

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

	public long getErcGroupEntryId() {
		return _ercGroupEntryId;
	}

	public void setErcGroupEntryId(long ercGroupEntryId) {
		_ercGroupEntryId = ercGroupEntryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	private String _uuid;
	private String _externalReferenceCode;
	private long _ercGroupEntryId;
	private long _groupId;
	private long _companyId;

}
// SB-Hash:1520740269