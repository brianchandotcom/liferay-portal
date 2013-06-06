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

package com.liferay.portlet.social.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivitySoap implements Serializable {
	public static SocialActivitySoap toSoapModel(SocialActivity model) {
		SocialActivitySoap soapModel = new SocialActivitySoap();

		soapModel.setActivityId(model.getActivityId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setActivitySetId(model.getActivitySetId());
		soapModel.setMirrorActivityId(model.getMirrorActivityId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setParentClassNameId(model.getParentClassNameId());
		soapModel.setParentClassPK(model.getParentClassPK());
		soapModel.setType(model.getType());
		soapModel.setExtraData(model.getExtraData());
		soapModel.setReceiverUserId(model.getReceiverUserId());

		return soapModel;
	}

	public static SocialActivitySoap[] toSoapModels(SocialActivity[] models) {
		SocialActivitySoap[] soapModels = new SocialActivitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialActivitySoap[][] toSoapModels(SocialActivity[][] models) {
		SocialActivitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialActivitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialActivitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialActivitySoap[] toSoapModels(List<SocialActivity> models) {
		List<SocialActivitySoap> soapModels = new ArrayList<SocialActivitySoap>(models.size());

		for (SocialActivity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialActivitySoap[soapModels.size()]);
	}

	public SocialActivitySoap() {
	}

	public long getPrimaryKey() {
		return _activityId;
	}

	public void setPrimaryKey(long pk) {
		setActivityId(pk);
	}

	public long getActivityId() {
		return _activityId;
	}

	public void setActivityId(long activityId) {
		_activityId = activityId;
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

	public long getActivitySetId() {
		return _activitySetId;
	}

	public void setActivitySetId(long activitySetId) {
		_activitySetId = activitySetId;
	}

	public long getMirrorActivityId() {
		return _mirrorActivityId;
	}

	public void setMirrorActivityId(long mirrorActivityId) {
		_mirrorActivityId = mirrorActivityId;
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

	public long getParentClassNameId() {
		return _parentClassNameId;
	}

	public void setParentClassNameId(long parentClassNameId) {
		_parentClassNameId = parentClassNameId;
	}

	public long getParentClassPK() {
		return _parentClassPK;
	}

	public void setParentClassPK(long parentClassPK) {
		_parentClassPK = parentClassPK;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public String getExtraData() {
		return _extraData;
	}

	public void setExtraData(String extraData) {
		_extraData = extraData;
	}

	public long getReceiverUserId() {
		return _receiverUserId;
	}

	public void setReceiverUserId(long receiverUserId) {
		_receiverUserId = receiverUserId;
	}

	private long _activityId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private long _createDate;
	private long _activitySetId;
	private long _mirrorActivityId;
	private long _classNameId;
	private long _classPK;
	private long _parentClassNameId;
	private long _parentClassPK;
	private int _type;
	private String _extraData;
	private long _receiverUserId;
}