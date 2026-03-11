/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MvccEntrySoap implements Serializable {

	public static MvccEntrySoap toSoapModel(MvccEntry model) {
		MvccEntrySoap soapModel = new MvccEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setMvccEntryId(model.getMvccEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static MvccEntrySoap[] toSoapModels(MvccEntry[] models) {
		MvccEntrySoap[] soapModels = new MvccEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MvccEntrySoap[][] toSoapModels(MvccEntry[][] models) {
		MvccEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new MvccEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new MvccEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MvccEntrySoap[] toSoapModels(List<MvccEntry> models) {
		List<MvccEntrySoap> soapModels = new ArrayList<MvccEntrySoap>(
			models.size());

		for (MvccEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MvccEntrySoap[soapModels.size()]);
	}

	public MvccEntrySoap() {
	}

	public long getPrimaryKey() {
		return _mvccEntryId;
	}

	public void setPrimaryKey(long pk) {
		setMvccEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getMvccEntryId() {
		return _mvccEntryId;
	}

	public void setMvccEntryId(long mvccEntryId) {
		_mvccEntryId = mvccEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _mvccVersion;
	private long _mvccEntryId;
	private long _companyId;
	private String _name;

}