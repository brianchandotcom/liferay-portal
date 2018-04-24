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
public class VersionedEntryContentVersionSoap implements Serializable {
	public static VersionedEntryContentVersionSoap toSoapModel(
		VersionedEntryContentVersion model) {
		VersionedEntryContentVersionSoap soapModel = new VersionedEntryContentVersionSoap();

		soapModel.setVersionedEntryContentVersionId(model.getVersionedEntryContentVersionId());
		soapModel.setVersion(model.getVersion());
		soapModel.setVersionedEntryContentId(model.getVersionedEntryContentId());
		soapModel.setVersionedEntryId(model.getVersionedEntryId());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setContent(model.getContent());

		return soapModel;
	}

	public static VersionedEntryContentVersionSoap[] toSoapModels(
		VersionedEntryContentVersion[] models) {
		VersionedEntryContentVersionSoap[] soapModels = new VersionedEntryContentVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static VersionedEntryContentVersionSoap[][] toSoapModels(
		VersionedEntryContentVersion[][] models) {
		VersionedEntryContentVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new VersionedEntryContentVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new VersionedEntryContentVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static VersionedEntryContentVersionSoap[] toSoapModels(
		List<VersionedEntryContentVersion> models) {
		List<VersionedEntryContentVersionSoap> soapModels = new ArrayList<VersionedEntryContentVersionSoap>(models.size());

		for (VersionedEntryContentVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new VersionedEntryContentVersionSoap[soapModels.size()]);
	}

	public VersionedEntryContentVersionSoap() {
	}

	public long getPrimaryKey() {
		return _versionedEntryContentVersionId;
	}

	public void setPrimaryKey(long pk) {
		setVersionedEntryContentVersionId(pk);
	}

	public long getVersionedEntryContentVersionId() {
		return _versionedEntryContentVersionId;
	}

	public void setVersionedEntryContentVersionId(
		long versionedEntryContentVersionId) {
		_versionedEntryContentVersionId = versionedEntryContentVersionId;
	}

	public int getVersion() {
		return _version;
	}

	public void setVersion(int version) {
		_version = version;
	}

	public long getVersionedEntryContentId() {
		return _versionedEntryContentId;
	}

	public void setVersionedEntryContentId(long versionedEntryContentId) {
		_versionedEntryContentId = versionedEntryContentId;
	}

	public long getVersionedEntryId() {
		return _versionedEntryId;
	}

	public void setVersionedEntryId(long versionedEntryId) {
		_versionedEntryId = versionedEntryId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_content = content;
	}

	private long _versionedEntryContentVersionId;
	private int _version;
	private long _versionedEntryContentId;
	private long _versionedEntryId;
	private String _languageId;
	private String _content;
}