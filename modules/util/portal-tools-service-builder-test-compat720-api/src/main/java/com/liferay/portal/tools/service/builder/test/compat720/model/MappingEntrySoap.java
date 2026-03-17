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
public class MappingEntrySoap implements Serializable {

	public static MappingEntrySoap toSoapModel(MappingEntry model) {
		MappingEntrySoap soapModel = new MappingEntrySoap();

		soapModel.setMappingEntryId(model.getMappingEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static MappingEntrySoap[] toSoapModels(MappingEntry[] models) {
		MappingEntrySoap[] soapModels = new MappingEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MappingEntrySoap[][] toSoapModels(MappingEntry[][] models) {
		MappingEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new MappingEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new MappingEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MappingEntrySoap[] toSoapModels(List<MappingEntry> models) {
		List<MappingEntrySoap> soapModels = new ArrayList<MappingEntrySoap>(
			models.size());

		for (MappingEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MappingEntrySoap[soapModels.size()]);
	}

	public MappingEntrySoap() {
	}

	public long getPrimaryKey() {
		return _mappingEntryId;
	}

	public void setPrimaryKey(long pk) {
		setMappingEntryId(pk);
	}

	public long getMappingEntryId() {
		return _mappingEntryId;
	}

	public void setMappingEntryId(long mappingEntryId) {
		_mappingEntryId = mappingEntryId;
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

	private long _mappingEntryId;
	private long _companyId;
	private String _name;

}
// SB-Hash:-404312977