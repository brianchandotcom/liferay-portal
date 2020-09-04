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
public class CacheDisabledEntrySoap implements Serializable {

	public static CacheDisabledEntrySoap toSoapModel(CacheDisabledEntry model) {
		CacheDisabledEntrySoap soapModel = new CacheDisabledEntrySoap();

		soapModel.setCacheDisabledEntryId(model.getCacheDisabledEntryId());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static CacheDisabledEntrySoap[] toSoapModels(
		CacheDisabledEntry[] models) {

		CacheDisabledEntrySoap[] soapModels =
			new CacheDisabledEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CacheDisabledEntrySoap[][] toSoapModels(
		CacheDisabledEntry[][] models) {

		CacheDisabledEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CacheDisabledEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CacheDisabledEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CacheDisabledEntrySoap[] toSoapModels(
		List<CacheDisabledEntry> models) {

		List<CacheDisabledEntrySoap> soapModels =
			new ArrayList<CacheDisabledEntrySoap>(models.size());

		for (CacheDisabledEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CacheDisabledEntrySoap[soapModels.size()]);
	}

	public CacheDisabledEntrySoap() {
	}

	public long getPrimaryKey() {
		return _cacheDisabledEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCacheDisabledEntryId(pk);
	}

	public long getCacheDisabledEntryId() {
		return _cacheDisabledEntryId;
	}

	public void setCacheDisabledEntryId(long cacheDisabledEntryId) {
		_cacheDisabledEntryId = cacheDisabledEntryId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _cacheDisabledEntryId;
	private String _name;

}