/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetLinkSoap implements Serializable {
	public static AssetLinkSoap toSoapModel(AssetLink model) {
		AssetLinkSoap soapModel = new AssetLinkSoap();

		soapModel.setLinkId(model.getLinkId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setEntryId1(model.getEntryId1());
		soapModel.setEntryId2(model.getEntryId2());
		soapModel.setType(model.getType());
		soapModel.setWeight(model.getWeight());

		return soapModel;
	}

	public static AssetLinkSoap[] toSoapModels(AssetLink[] models) {
		AssetLinkSoap[] soapModels = new AssetLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetLinkSoap[][] toSoapModels(AssetLink[][] models) {
		AssetLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetLinkSoap[] toSoapModels(List<AssetLink> models) {
		List<AssetLinkSoap> soapModels = new ArrayList<AssetLinkSoap>(models.size());

		for (AssetLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetLinkSoap[soapModels.size()]);
	}

	public AssetLinkSoap() {
	}

	public long getPrimaryKey() {
		return _linkId;
	}

	public void setPrimaryKey(long pk) {
		setLinkId(pk);
	}

	public long getLinkId() {
		return _linkId;
	}

	public void setLinkId(long linkId) {
		_linkId = linkId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public long getEntryId1() {
		return _entryId1;
	}

	public void setEntryId1(long entryId1) {
		_entryId1 = entryId1;
	}

	public long getEntryId2() {
		return _entryId2;
	}

	public void setEntryId2(long entryId2) {
		_entryId2 = entryId2;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public int getWeight() {
		return _weight;
	}

	public void setWeight(int weight) {
		_weight = weight;
	}

	private long _linkId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _entryId1;
	private long _entryId2;
	private int _type;
	private int _weight;
}