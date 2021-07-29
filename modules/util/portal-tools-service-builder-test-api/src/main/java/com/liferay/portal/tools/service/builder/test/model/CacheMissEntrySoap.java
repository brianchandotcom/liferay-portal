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
public class CacheMissEntrySoap implements Serializable {

	public static CacheMissEntrySoap toSoapModel(CacheMissEntry model) {
		CacheMissEntrySoap soapModel = new CacheMissEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setCacheMissEntryId(model.getCacheMissEntryId());

		return soapModel;
	}

	public static CacheMissEntrySoap[] toSoapModels(CacheMissEntry[] models) {
		CacheMissEntrySoap[] soapModels = new CacheMissEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CacheMissEntrySoap[][] toSoapModels(
		CacheMissEntry[][] models) {

		CacheMissEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CacheMissEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CacheMissEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CacheMissEntrySoap[] toSoapModels(
		List<CacheMissEntry> models) {

		List<CacheMissEntrySoap> soapModels = new ArrayList<CacheMissEntrySoap>(
			models.size());

		for (CacheMissEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CacheMissEntrySoap[soapModels.size()]);
	}

	public CacheMissEntrySoap() {
	}

	public long getPrimaryKey() {
		return _cacheMissEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCacheMissEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getCacheMissEntryId() {
		return _cacheMissEntryId;
	}

	public void setCacheMissEntryId(long cacheMissEntryId) {
		_cacheMissEntryId = cacheMissEntryId;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _cacheMissEntryId;

}