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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RecentLayoutSetBranchSoap implements Serializable {

	public static RecentLayoutSetBranchSoap toSoapModel(
		RecentLayoutSetBranch model) {

		RecentLayoutSetBranchSoap soapModel = new RecentLayoutSetBranchSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setRecentLayoutSetBranchId(
			model.getRecentLayoutSetBranchId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setLayoutSetBranchId(model.getLayoutSetBranchId());
		soapModel.setLayoutSetId(model.getLayoutSetId());

		return soapModel;
	}

	public static RecentLayoutSetBranchSoap[] toSoapModels(
		RecentLayoutSetBranch[] models) {

		RecentLayoutSetBranchSoap[] soapModels =
			new RecentLayoutSetBranchSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RecentLayoutSetBranchSoap[][] toSoapModels(
		RecentLayoutSetBranch[][] models) {

		RecentLayoutSetBranchSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new RecentLayoutSetBranchSoap[models.length][models[0].length];
		}
		else {
			soapModels = new RecentLayoutSetBranchSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RecentLayoutSetBranchSoap[] toSoapModels(
		List<RecentLayoutSetBranch> models) {

		List<RecentLayoutSetBranchSoap> soapModels =
			new ArrayList<RecentLayoutSetBranchSoap>(models.size());

		for (RecentLayoutSetBranch model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new RecentLayoutSetBranchSoap[soapModels.size()]);
	}

	public RecentLayoutSetBranchSoap() {
	}

	public long getPrimaryKey() {
		return _recentLayoutSetBranchId;
	}

	public void setPrimaryKey(long pk) {
		setRecentLayoutSetBranchId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getRecentLayoutSetBranchId() {
		return _recentLayoutSetBranchId;
	}

	public void setRecentLayoutSetBranchId(long recentLayoutSetBranchId) {
		_recentLayoutSetBranchId = recentLayoutSetBranchId;
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

	public long getLayoutSetBranchId() {
		return _layoutSetBranchId;
	}

	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutSetBranchId = layoutSetBranchId;
	}

	public long getLayoutSetId() {
		return _layoutSetId;
	}

	public void setLayoutSetId(long layoutSetId) {
		_layoutSetId = layoutSetId;
	}

	private long _mvccVersion;
	private long _recentLayoutSetBranchId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private long _layoutSetBranchId;
	private long _layoutSetId;

}