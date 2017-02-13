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

package com.liferay.friendly.url.model;

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
public class FriendlyURLLocalizationSoap implements Serializable {
	public static FriendlyURLLocalizationSoap toSoapModel(
		FriendlyURLLocalization model) {
		FriendlyURLLocalizationSoap soapModel = new FriendlyURLLocalizationSoap();

		soapModel.setFriendlyURLLocalizationId(model.getFriendlyURLLocalizationId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setFriendlyURLId(model.getFriendlyURLId());
		soapModel.setUrlTitle(model.getUrlTitle());
		soapModel.setLanguageId(model.getLanguageId());

		return soapModel;
	}

	public static FriendlyURLLocalizationSoap[] toSoapModels(
		FriendlyURLLocalization[] models) {
		FriendlyURLLocalizationSoap[] soapModels = new FriendlyURLLocalizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FriendlyURLLocalizationSoap[][] toSoapModels(
		FriendlyURLLocalization[][] models) {
		FriendlyURLLocalizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FriendlyURLLocalizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FriendlyURLLocalizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FriendlyURLLocalizationSoap[] toSoapModels(
		List<FriendlyURLLocalization> models) {
		List<FriendlyURLLocalizationSoap> soapModels = new ArrayList<FriendlyURLLocalizationSoap>(models.size());

		for (FriendlyURLLocalization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FriendlyURLLocalizationSoap[soapModels.size()]);
	}

	public FriendlyURLLocalizationSoap() {
	}

	public long getPrimaryKey() {
		return _friendlyURLLocalizationId;
	}

	public void setPrimaryKey(long pk) {
		setFriendlyURLLocalizationId(pk);
	}

	public long getFriendlyURLLocalizationId() {
		return _friendlyURLLocalizationId;
	}

	public void setFriendlyURLLocalizationId(long friendlyURLLocalizationId) {
		_friendlyURLLocalizationId = friendlyURLLocalizationId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getFriendlyURLId() {
		return _friendlyURLId;
	}

	public void setFriendlyURLId(long friendlyURLId) {
		_friendlyURLId = friendlyURLId;
	}

	public String getUrlTitle() {
		return _urlTitle;
	}

	public void setUrlTitle(String urlTitle) {
		_urlTitle = urlTitle;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	private long _friendlyURLLocalizationId;
	private long _groupId;
	private long _companyId;
	private long _friendlyURLId;
	private String _urlTitle;
	private String _languageId;
}