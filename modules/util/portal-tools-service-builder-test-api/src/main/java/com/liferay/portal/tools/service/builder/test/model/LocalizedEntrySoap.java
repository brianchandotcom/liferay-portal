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
public class LocalizedEntrySoap implements Serializable {

	public static LocalizedEntrySoap toSoapModel(LocalizedEntry model) {
		LocalizedEntrySoap soapModel = new LocalizedEntrySoap();

		soapModel.setDefaultLanguageId(model.getDefaultLanguageId());
		soapModel.setLocalizedEntryId(model.getLocalizedEntryId());

		return soapModel;
	}

	public static LocalizedEntrySoap[] toSoapModels(LocalizedEntry[] models) {
		LocalizedEntrySoap[] soapModels = new LocalizedEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LocalizedEntrySoap[][] toSoapModels(
		LocalizedEntry[][] models) {

		LocalizedEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new LocalizedEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new LocalizedEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LocalizedEntrySoap[] toSoapModels(
		List<LocalizedEntry> models) {

		List<LocalizedEntrySoap> soapModels = new ArrayList<LocalizedEntrySoap>(
			models.size());

		for (LocalizedEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LocalizedEntrySoap[soapModels.size()]);
	}

	public LocalizedEntrySoap() {
	}

	public long getPrimaryKey() {
		return _localizedEntryId;
	}

	public void setPrimaryKey(long pk) {
		setLocalizedEntryId(pk);
	}

	public String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	public long getLocalizedEntryId() {
		return _localizedEntryId;
	}

	public void setLocalizedEntryId(long localizedEntryId) {
		_localizedEntryId = localizedEntryId;
	}

	private String _defaultLanguageId;
	private long _localizedEntryId;

}