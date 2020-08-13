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
public class VersionedEntryVersionSoap implements Serializable {

	public static VersionedEntryVersionSoap toSoapModel(
		VersionedEntryVersion model) {

		VersionedEntryVersionSoap soapModel = new VersionedEntryVersionSoap();

		soapModel.setVersionedEntryVersionId(
			model.getVersionedEntryVersionId());
		soapModel.setVersion(model.getVersion());
		soapModel.setVersionedEntryId(model.getVersionedEntryId());
		soapModel.setGroupId(model.getGroupId());

		return soapModel;
	}

	public static VersionedEntryVersionSoap[] toSoapModels(
		VersionedEntryVersion[] models) {

		VersionedEntryVersionSoap[] soapModels =
			new VersionedEntryVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static VersionedEntryVersionSoap[][] toSoapModels(
		VersionedEntryVersion[][] models) {

		VersionedEntryVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new VersionedEntryVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new VersionedEntryVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static VersionedEntryVersionSoap[] toSoapModels(
		List<VersionedEntryVersion> models) {

		List<VersionedEntryVersionSoap> soapModels =
			new ArrayList<VersionedEntryVersionSoap>(models.size());

		for (VersionedEntryVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new VersionedEntryVersionSoap[soapModels.size()]);
	}

	public VersionedEntryVersionSoap() {
	}

	public long getPrimaryKey() {
		return _versionedEntryVersionId;
	}

	public void setPrimaryKey(long pk) {
		setVersionedEntryVersionId(pk);
	}

	public long getVersionedEntryVersionId() {
		return _versionedEntryVersionId;
	}

	public void setVersionedEntryVersionId(long versionedEntryVersionId) {
		_versionedEntryVersionId = versionedEntryVersionId;
	}

	public int getVersion() {
		return _version;
	}

	public void setVersion(int version) {
		_version = version;
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

	private long _versionedEntryVersionId;
	private int _version;
	private long _versionedEntryId;
	private long _groupId;

}