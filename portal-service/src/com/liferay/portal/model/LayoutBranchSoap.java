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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.LayoutBranchServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.http.LayoutBranchServiceSoap
 * @generated
 */
public class LayoutBranchSoap implements Serializable {
	public static LayoutBranchSoap toSoapModel(LayoutBranch model) {
		LayoutBranchSoap soapModel = new LayoutBranchSoap();

		soapModel.setLayoutBranchId(model.getLayoutBranchId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setLayoutSetBranchId(model.getLayoutSetBranchId());
		soapModel.setPlid(model.getPlid());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setMaster(model.getMaster());
		soapModel.setMvccVersion(model.getMvccVersion());

		return soapModel;
	}

	public static LayoutBranchSoap[] toSoapModels(LayoutBranch[] models) {
		LayoutBranchSoap[] soapModels = new LayoutBranchSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutBranchSoap[][] toSoapModels(LayoutBranch[][] models) {
		LayoutBranchSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LayoutBranchSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutBranchSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutBranchSoap[] toSoapModels(List<LayoutBranch> models) {
		List<LayoutBranchSoap> soapModels = new ArrayList<LayoutBranchSoap>(models.size());

		for (LayoutBranch model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LayoutBranchSoap[soapModels.size()]);
	}

	public LayoutBranchSoap() {
	}

	public long getPrimaryKey() {
		return _LayoutBranchId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutBranchId(pk);
	}

	public long getLayoutBranchId() {
		return _LayoutBranchId;
	}

	public void setLayoutBranchId(long LayoutBranchId) {
		_LayoutBranchId = LayoutBranchId;
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

	public long getLayoutSetBranchId() {
		return _layoutSetBranchId;
	}

	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutSetBranchId = layoutSetBranchId;
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public boolean getMaster() {
		return _master;
	}

	public boolean isMaster() {
		return _master;
	}

	public void setMaster(boolean master) {
		_master = master;
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	private long _LayoutBranchId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private long _layoutSetBranchId;
	private long _plid;
	private String _name;
	private String _description;
	private boolean _master;
	private long _mvccVersion;
}