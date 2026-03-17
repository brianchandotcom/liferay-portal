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
public class ConvertNullEntrySoap implements Serializable {

	public static ConvertNullEntrySoap toSoapModel(ConvertNullEntry model) {
		ConvertNullEntrySoap soapModel = new ConvertNullEntrySoap();

		soapModel.setConvertNullEntryId(model.getConvertNullEntryId());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static ConvertNullEntrySoap[] toSoapModels(
		ConvertNullEntry[] models) {

		ConvertNullEntrySoap[] soapModels =
			new ConvertNullEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ConvertNullEntrySoap[][] toSoapModels(
		ConvertNullEntry[][] models) {

		ConvertNullEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ConvertNullEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ConvertNullEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ConvertNullEntrySoap[] toSoapModels(
		List<ConvertNullEntry> models) {

		List<ConvertNullEntrySoap> soapModels =
			new ArrayList<ConvertNullEntrySoap>(models.size());

		for (ConvertNullEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ConvertNullEntrySoap[soapModels.size()]);
	}

	public ConvertNullEntrySoap() {
	}

	public long getPrimaryKey() {
		return _convertNullEntryId;
	}

	public void setPrimaryKey(long pk) {
		setConvertNullEntryId(pk);
	}

	public long getConvertNullEntryId() {
		return _convertNullEntryId;
	}

	public void setConvertNullEntryId(long convertNullEntryId) {
		_convertNullEntryId = convertNullEntryId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _convertNullEntryId;
	private String _name;

}
// SB-Hash:-2000242032