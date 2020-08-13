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
public class VersionedEntrySoap implements Serializable {

	public static VersionedEntrySoap toSoapModel(VersionedEntry model) {
		VersionedEntrySoap soapModel = new VersionedEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setHeadId(model.getHeadId());
		soapModel.setVersionedEntryId(model.getVersionedEntryId());
		soapModel.setGroupId(model.getGroupId());

		return soapModel;
	}

	public static VersionedEntrySoap[] toSoapModels(VersionedEntry[] models) {
		VersionedEntrySoap[] soapModels = new VersionedEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static VersionedEntrySoap[][] toSoapModels(
		VersionedEntry[][] models) {

		VersionedEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new VersionedEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new VersionedEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static VersionedEntrySoap[] toSoapModels(
		List<VersionedEntry> models) {

		List<VersionedEntrySoap> soapModels = new ArrayList<VersionedEntrySoap>(
			models.size());

		for (VersionedEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new VersionedEntrySoap[soapModels.size()]);
	}

	public VersionedEntrySoap() {
	}

	public long getPrimaryKey() {
		return _versionedEntryId;
	}

	public void setPrimaryKey(long pk) {
		setVersionedEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getHeadId() {
		return _headId;
	}

	public void setHeadId(long headId) {
		_headId = headId;
	}

	public long getVersionedEntryId() {
		return _versionedEntryId;
	}

	public void setVersionedEntryId(long versionedEntryId) {
		_versionedEntryId = versionedEntryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	private long _mvccVersion;
	private long _headId;
	private long _versionedEntryId;
	private long _groupId;

}