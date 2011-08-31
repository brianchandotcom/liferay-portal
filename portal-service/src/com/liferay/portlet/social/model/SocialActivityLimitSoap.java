/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
public class SocialActivityLimitSoap implements Serializable {
	public static SocialActivityLimitSoap toSoapModel(SocialActivityLimit model) {
		SocialActivityLimitSoap soapModel = new SocialActivityLimitSoap();

		soapModel.setActivityLimitId(model.getActivityLimitId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setActivityKey(model.getActivityKey());
		soapModel.setStatName(model.getStatName());
		soapModel.setValue(model.getValue());

		return soapModel;
	}

	public static SocialActivityLimitSoap[] toSoapModels(
		SocialActivityLimit[] models) {
		SocialActivityLimitSoap[] soapModels = new SocialActivityLimitSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityLimitSoap[][] toSoapModels(
		SocialActivityLimit[][] models) {
		SocialActivityLimitSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialActivityLimitSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialActivityLimitSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityLimitSoap[] toSoapModels(
		List<SocialActivityLimit> models) {
		List<SocialActivityLimitSoap> soapModels = new ArrayList<SocialActivityLimitSoap>(models.size());

		for (SocialActivityLimit model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialActivityLimitSoap[soapModels.size()]);
	}

	public SocialActivityLimitSoap() {
	}

	public long getPrimaryKey() {
		return _activityLimitId;
	}

	public void setPrimaryKey(long pk) {
		setActivityLimitId(pk);
	}

	public long getActivityLimitId() {
		return _activityLimitId;
	}

	public void setActivityLimitId(long activityLimitId) {
		_activityLimitId = activityLimitId;
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

	public int getActivityKey() {
		return _activityKey;
	}

	public void setActivityKey(int activityKey) {
		_activityKey = activityKey;
	}

	public String getStatName() {
		return _statName;
	}

	public void setStatName(String statName) {
		_statName = statName;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	private long _activityLimitId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private long _classNameId;
	private long _classPK;
	private int _activityKey;
	private String _statName;
	private String _value;
}