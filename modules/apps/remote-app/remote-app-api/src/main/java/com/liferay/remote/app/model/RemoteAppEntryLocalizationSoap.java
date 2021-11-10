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

package com.liferay.remote.app.model;

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
public class RemoteAppEntryLocalizationSoap implements Serializable {

	public static RemoteAppEntryLocalizationSoap toSoapModel(
		RemoteAppEntryLocalization model) {

		RemoteAppEntryLocalizationSoap soapModel =
			new RemoteAppEntryLocalizationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setRemoteAppEntryLocalizationId(
			model.getRemoteAppEntryLocalizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setRemoteAppEntryId(model.getRemoteAppEntryId());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setDescription(model.getDescription());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static RemoteAppEntryLocalizationSoap[] toSoapModels(
		RemoteAppEntryLocalization[] models) {

		RemoteAppEntryLocalizationSoap[] soapModels =
			new RemoteAppEntryLocalizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RemoteAppEntryLocalizationSoap[][] toSoapModels(
		RemoteAppEntryLocalization[][] models) {

		RemoteAppEntryLocalizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new RemoteAppEntryLocalizationSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new RemoteAppEntryLocalizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RemoteAppEntryLocalizationSoap[] toSoapModels(
		List<RemoteAppEntryLocalization> models) {

		List<RemoteAppEntryLocalizationSoap> soapModels =
			new ArrayList<RemoteAppEntryLocalizationSoap>(models.size());

		for (RemoteAppEntryLocalization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new RemoteAppEntryLocalizationSoap[soapModels.size()]);
	}

	public RemoteAppEntryLocalizationSoap() {
	}

	public long getPrimaryKey() {
		return _remoteAppEntryLocalizationId;
	}

	public void setPrimaryKey(long pk) {
		setRemoteAppEntryLocalizationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getRemoteAppEntryLocalizationId() {
		return _remoteAppEntryLocalizationId;
	}

	public void setRemoteAppEntryLocalizationId(
		long remoteAppEntryLocalizationId) {

		_remoteAppEntryLocalizationId = remoteAppEntryLocalizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getRemoteAppEntryId() {
		return _remoteAppEntryId;
	}

	public void setRemoteAppEntryId(long remoteAppEntryId) {
		_remoteAppEntryId = remoteAppEntryId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _mvccVersion;
	private long _remoteAppEntryLocalizationId;
	private long _companyId;
	private long _remoteAppEntryId;
	private String _languageId;
	private String _description;
	private String _name;

}