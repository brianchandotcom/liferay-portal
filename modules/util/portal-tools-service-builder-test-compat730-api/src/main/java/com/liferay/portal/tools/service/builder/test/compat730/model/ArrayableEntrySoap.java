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
public class ArrayableEntrySoap implements Serializable {

	public static ArrayableEntrySoap toSoapModel(ArrayableEntry model) {
		ArrayableEntrySoap soapModel = new ArrayableEntrySoap();

		soapModel.setArrayableEntryId(model.getArrayableEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setInteger(model.getInteger());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static ArrayableEntrySoap[] toSoapModels(ArrayableEntry[] models) {
		ArrayableEntrySoap[] soapModels = new ArrayableEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ArrayableEntrySoap[][] toSoapModels(
		ArrayableEntry[][] models) {

		ArrayableEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ArrayableEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ArrayableEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ArrayableEntrySoap[] toSoapModels(
		List<ArrayableEntry> models) {

		List<ArrayableEntrySoap> soapModels = new ArrayList<ArrayableEntrySoap>(
			models.size());

		for (ArrayableEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ArrayableEntrySoap[soapModels.size()]);
	}

	public ArrayableEntrySoap() {
	}

	public long getPrimaryKey() {
		return _arrayableEntryId;
	}

	public void setPrimaryKey(long pk) {
		setArrayableEntryId(pk);
	}

	public long getArrayableEntryId() {
		return _arrayableEntryId;
	}

	public void setArrayableEntryId(long arrayableEntryId) {
		_arrayableEntryId = arrayableEntryId;
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

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public int getInteger() {
		return _integer;
	}

	public void setInteger(int integer) {
		_integer = integer;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	private long _arrayableEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private int _integer;
	private String _name;
	private String _type;

}
// LIFERAY-SERVICE-BUILDER-HASH:-741479636