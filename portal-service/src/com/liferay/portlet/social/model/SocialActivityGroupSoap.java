/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityGroupSoap implements Serializable {
	public static SocialActivityGroupSoap toSoapModel(SocialActivityGroup model) {
		SocialActivityGroupSoap soapModel = new SocialActivityGroupSoap();

		soapModel.setActivityGroupId(model.getActivityGroupId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static SocialActivityGroupSoap[] toSoapModels(
		SocialActivityGroup[] models) {
		SocialActivityGroupSoap[] soapModels = new SocialActivityGroupSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityGroupSoap[][] toSoapModels(
		SocialActivityGroup[][] models) {
		SocialActivityGroupSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialActivityGroupSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialActivityGroupSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityGroupSoap[] toSoapModels(
		List<SocialActivityGroup> models) {
		List<SocialActivityGroupSoap> soapModels = new ArrayList<SocialActivityGroupSoap>(models.size());

		for (SocialActivityGroup model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialActivityGroupSoap[soapModels.size()]);
	}

	public SocialActivityGroupSoap() {
	}

	public long getPrimaryKey() {
		return _activityGroupId;
	}

	public void setPrimaryKey(long pk) {
		setActivityGroupId(pk);
	}

	public long getActivityGroupId() {
		return _activityGroupId;
	}

	public void setActivityGroupId(long activityGroupId) {
		_activityGroupId = activityGroupId;
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

	public long getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(long createDate) {
		_createDate = createDate;
	}

	public long getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(long modifiedDate) {
		_modifiedDate = modifiedDate;
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

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private long _activityGroupId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private long _createDate;
	private long _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private int _type;
}