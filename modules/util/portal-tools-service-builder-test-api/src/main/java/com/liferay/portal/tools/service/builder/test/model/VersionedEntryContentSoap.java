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
public class VersionedEntryContentSoap implements Serializable {
	public static VersionedEntryContentSoap toSoapModel(
		VersionedEntryContent model) {
		VersionedEntryContentSoap soapModel = new VersionedEntryContentSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setVersionedEntryContentId(model.getVersionedEntryContentId());
		soapModel.setVersionedEntryId(model.getVersionedEntryId());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setContent(model.getContent());
		soapModel.setHeadId(model.getHeadId());

		return soapModel;
	}

	public static VersionedEntryContentSoap[] toSoapModels(
		VersionedEntryContent[] models) {
		VersionedEntryContentSoap[] soapModels = new VersionedEntryContentSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static VersionedEntryContentSoap[][] toSoapModels(
		VersionedEntryContent[][] models) {
		VersionedEntryContentSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new VersionedEntryContentSoap[models.length][models[0].length];
		}
		else {
			soapModels = new VersionedEntryContentSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static VersionedEntryContentSoap[] toSoapModels(
		List<VersionedEntryContent> models) {
		List<VersionedEntryContentSoap> soapModels = new ArrayList<VersionedEntryContentSoap>(models.size());

		for (VersionedEntryContent model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new VersionedEntryContentSoap[soapModels.size()]);
	}

	public VersionedEntryContentSoap() {
	}

	public long getPrimaryKey() {
		return _versionedEntryContentId;
	}

	public void setPrimaryKey(long pk) {
		setVersionedEntryContentId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
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

	public long getHeadId() {
		return _headId;
	}

	public void setHeadId(long headId) {
		_headId = headId;
	}

	private long _mvccVersion;
	private long _versionedEntryContentId;
	private long _versionedEntryId;
	private String _languageId;
	private String _content;
	private long _headId;
}