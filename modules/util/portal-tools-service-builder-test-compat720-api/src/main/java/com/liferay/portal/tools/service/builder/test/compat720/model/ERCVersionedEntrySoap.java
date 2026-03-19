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
public class ERCVersionedEntrySoap implements Serializable {

	public static ERCVersionedEntrySoap toSoapModel(ERCVersionedEntry model) {
		ERCVersionedEntrySoap soapModel = new ERCVersionedEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setErcVersionedEntryId(model.getErcVersionedEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());

		return soapModel;
	}

	public static ERCVersionedEntrySoap[] toSoapModels(
		ERCVersionedEntry[] models) {

		ERCVersionedEntrySoap[] soapModels =
			new ERCVersionedEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ERCVersionedEntrySoap[][] toSoapModels(
		ERCVersionedEntry[][] models) {

		ERCVersionedEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ERCVersionedEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ERCVersionedEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ERCVersionedEntrySoap[] toSoapModels(
		List<ERCVersionedEntry> models) {

		List<ERCVersionedEntrySoap> soapModels =
			new ArrayList<ERCVersionedEntrySoap>(models.size());

		for (ERCVersionedEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ERCVersionedEntrySoap[soapModels.size()]);
	}

	public ERCVersionedEntrySoap() {
	}

	public long getPrimaryKey() {
		return _ercVersionedEntryId;
	}

	public void setPrimaryKey(long pk) {
		setErcVersionedEntryId(pk);
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

	public long getErcVersionedEntryId() {
		return _ercVersionedEntryId;
	}

	public void setErcVersionedEntryId(long ercVersionedEntryId) {
		_ercVersionedEntryId = ercVersionedEntryId;
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
	private long _ercVersionedEntryId;
	private long _groupId;
	private long _companyId;

}
// LIFERAY-SERVICE-BUILDER-HASH:-742635123