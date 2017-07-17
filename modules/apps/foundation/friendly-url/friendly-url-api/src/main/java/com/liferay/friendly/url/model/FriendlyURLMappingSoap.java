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

import com.liferay.friendly.url.service.persistence.FriendlyURLMappingPK;

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
public class FriendlyURLMappingSoap implements Serializable {
	public static FriendlyURLMappingSoap toSoapModel(FriendlyURLMapping model) {
		FriendlyURLMappingSoap soapModel = new FriendlyURLMappingSoap();

		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setFriendlyURLId(model.getFriendlyURLId());

		return soapModel;
	}

	public static FriendlyURLMappingSoap[] toSoapModels(
		FriendlyURLMapping[] models) {
		FriendlyURLMappingSoap[] soapModels = new FriendlyURLMappingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FriendlyURLMappingSoap[][] toSoapModels(
		FriendlyURLMapping[][] models) {
		FriendlyURLMappingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FriendlyURLMappingSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FriendlyURLMappingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FriendlyURLMappingSoap[] toSoapModels(
		List<FriendlyURLMapping> models) {
		List<FriendlyURLMappingSoap> soapModels = new ArrayList<FriendlyURLMappingSoap>(models.size());

		for (FriendlyURLMapping model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FriendlyURLMappingSoap[soapModels.size()]);
	}

	public FriendlyURLMappingSoap() {
	}

	public FriendlyURLMappingPK getPrimaryKey() {
		return new FriendlyURLMappingPK(_classNameId, _classPK);
	}

	public void setPrimaryKey(FriendlyURLMappingPK pk) {
		setClassNameId(pk.classNameId);
		setClassPK(pk.classPK);
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getFriendlyURLId() {
		return _friendlyURLId;
	}

	public void setFriendlyURLId(long friendlyURLId) {
		_friendlyURLId = friendlyURLId;
	}

	private long _classNameId;
	private long _classPK;
	private long _friendlyURLId;
}