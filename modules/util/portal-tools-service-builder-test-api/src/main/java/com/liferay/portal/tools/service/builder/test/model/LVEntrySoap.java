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

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LVEntrySoap implements Serializable {
	public static LVEntrySoap toSoapModel(LVEntry model) {
		LVEntrySoap soapModel = new LVEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setEntryId(model.getEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setHeadId(model.getHeadId());
		soapModel.setDefaultLanguageId(model.getDefaultLanguageId());

		return soapModel;
	}

	public static LVEntrySoap[] toSoapModels(LVEntry[] models) {
		LVEntrySoap[] soapModels = new LVEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LVEntrySoap[][] toSoapModels(LVEntry[][] models) {
		LVEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LVEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new LVEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LVEntrySoap[] toSoapModels(List<LVEntry> models) {
		List<LVEntrySoap> soapModels = new ArrayList<LVEntrySoap>(models.size());

		for (LVEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LVEntrySoap[soapModels.size()]);
	}

	public LVEntrySoap() {
	}

	public long getPrimaryKey() {
		return _entryId;
	}

	public void setPrimaryKey(long pk) {
		setEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getHeadId() {
		return _headId;
	}

	public void setHeadId(long headId) {
		_headId = headId;
	}

	public String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	private long _mvccVersion;
	private long _entryId;
	private long _groupId;
	private long _headId;
	private String _defaultLanguageId;
}