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

package com.liferay.portal.kernel.model;

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
public class RecentLayoutBranchSoap implements Serializable {
	public static RecentLayoutBranchSoap toSoapModel(RecentLayoutBranch model) {
		RecentLayoutBranchSoap soapModel = new RecentLayoutBranchSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setRecentLayoutBranchId(model.getRecentLayoutBranchId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setLayoutBranchId(model.getLayoutBranchId());
		soapModel.setLayoutSetBranchId(model.getLayoutSetBranchId());
		soapModel.setPlid(model.getPlid());

		return soapModel;
	}

	public static RecentLayoutBranchSoap[] toSoapModels(
		RecentLayoutBranch[] models) {
		RecentLayoutBranchSoap[] soapModels = new RecentLayoutBranchSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RecentLayoutBranchSoap[][] toSoapModels(
		RecentLayoutBranch[][] models) {
		RecentLayoutBranchSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RecentLayoutBranchSoap[models.length][models[0].length];
		}
		else {
			soapModels = new RecentLayoutBranchSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RecentLayoutBranchSoap[] toSoapModels(
		List<RecentLayoutBranch> models) {
		List<RecentLayoutBranchSoap> soapModels = new ArrayList<RecentLayoutBranchSoap>(models.size());

		for (RecentLayoutBranch model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RecentLayoutBranchSoap[soapModels.size()]);
	}

	public RecentLayoutBranchSoap() {
	}

	public long getPrimaryKey() {
		return _recentLayoutBranchId;
	}

	public void setPrimaryKey(long pk) {
		setRecentLayoutBranchId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getRecentLayoutBranchId() {
		return _recentLayoutBranchId;
	}

	public void setRecentLayoutBranchId(long recentLayoutBranchId) {
		_recentLayoutBranchId = recentLayoutBranchId;
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

	public long getLayoutBranchId() {
		return _layoutBranchId;
	}

	public void setLayoutBranchId(long layoutBranchId) {
		_layoutBranchId = layoutBranchId;
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

	private long _mvccVersion;
	private long _recentLayoutBranchId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private long _layoutBranchId;
	private long _layoutSetBranchId;
	private long _plid;
}