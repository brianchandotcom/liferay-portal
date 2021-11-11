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
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class NullConvertibleEntrySoap implements Serializable {

	public static NullConvertibleEntrySoap toSoapModel(
		NullConvertibleEntry model) {

		NullConvertibleEntrySoap soapModel = new NullConvertibleEntrySoap();

		soapModel.setNullConvertibleEntryId(model.getNullConvertibleEntryId());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static NullConvertibleEntrySoap[] toSoapModels(
		NullConvertibleEntry[] models) {

		NullConvertibleEntrySoap[] soapModels =
			new NullConvertibleEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static NullConvertibleEntrySoap[][] toSoapModels(
		NullConvertibleEntry[][] models) {

		NullConvertibleEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new NullConvertibleEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new NullConvertibleEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static NullConvertibleEntrySoap[] toSoapModels(
		List<NullConvertibleEntry> models) {

		List<NullConvertibleEntrySoap> soapModels =
			new ArrayList<NullConvertibleEntrySoap>(models.size());

		for (NullConvertibleEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new NullConvertibleEntrySoap[soapModels.size()]);
	}

	public NullConvertibleEntrySoap() {
	}

	public long getPrimaryKey() {
		return _nullConvertibleEntryId;
	}

	public void setPrimaryKey(long pk) {
		setNullConvertibleEntryId(pk);
	}

	public long getNullConvertibleEntryId() {
		return _nullConvertibleEntryId;
	}

	public void setNullConvertibleEntryId(long nullConvertibleEntryId) {
		_nullConvertibleEntryId = nullConvertibleEntryId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _nullConvertibleEntryId;
	private String _name;

}