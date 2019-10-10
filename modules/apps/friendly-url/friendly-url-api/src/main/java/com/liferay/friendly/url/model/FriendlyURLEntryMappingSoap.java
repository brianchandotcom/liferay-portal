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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FriendlyURLEntryMappingSoap implements Serializable {

	public static FriendlyURLEntryMappingSoap toSoapModel(
		FriendlyURLEntryMapping model) {

		FriendlyURLEntryMappingSoap soapModel =
			new FriendlyURLEntryMappingSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setFriendlyURLEntryMappingId(
			model.getFriendlyURLEntryMappingId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setFriendlyURLEntryId(model.getFriendlyURLEntryId());

		return soapModel;
	}

	public static FriendlyURLEntryMappingSoap[] toSoapModels(
		FriendlyURLEntryMapping[] models) {

		FriendlyURLEntryMappingSoap[] soapModels =
			new FriendlyURLEntryMappingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FriendlyURLEntryMappingSoap[][] toSoapModels(
		FriendlyURLEntryMapping[][] models) {

		FriendlyURLEntryMappingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new FriendlyURLEntryMappingSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new FriendlyURLEntryMappingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FriendlyURLEntryMappingSoap[] toSoapModels(
		List<FriendlyURLEntryMapping> models) {

		List<FriendlyURLEntryMappingSoap> soapModels =
			new ArrayList<FriendlyURLEntryMappingSoap>(models.size());

		for (FriendlyURLEntryMapping model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new FriendlyURLEntryMappingSoap[soapModels.size()]);
	}

	public FriendlyURLEntryMappingSoap() {
	}

	public long getPrimaryKey() {
		return _friendlyURLEntryMappingId;
	}

	public void setPrimaryKey(long pk) {
		setFriendlyURLEntryMappingId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getFriendlyURLEntryMappingId() {
		return _friendlyURLEntryMappingId;
	}

	public void setFriendlyURLEntryMappingId(long friendlyURLEntryMappingId) {
		_friendlyURLEntryMappingId = friendlyURLEntryMappingId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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

	public long getFriendlyURLEntryId() {
		return _friendlyURLEntryId;
	}

	public void setFriendlyURLEntryId(long friendlyURLEntryId) {
		_friendlyURLEntryId = friendlyURLEntryId;
	}

	private long _mvccVersion;
	private long _friendlyURLEntryMappingId;
	private long _companyId;
	private long _classNameId;
	private long _classPK;
	private long _friendlyURLEntryId;

}