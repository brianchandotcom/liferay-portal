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

package com.liferay.social.kernel.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SocialActivityAchievementSoap implements Serializable {

	public static SocialActivityAchievementSoap toSoapModel(
		SocialActivityAchievement model) {

		SocialActivityAchievementSoap soapModel =
			new SocialActivityAchievementSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setActivityAchievementId(model.getActivityAchievementId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setName(model.getName());
		soapModel.setFirstInGroup(model.isFirstInGroup());

		return soapModel;
	}

	public static SocialActivityAchievementSoap[] toSoapModels(
		SocialActivityAchievement[] models) {

		SocialActivityAchievementSoap[] soapModels =
			new SocialActivityAchievementSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityAchievementSoap[][] toSoapModels(
		SocialActivityAchievement[][] models) {

		SocialActivityAchievementSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SocialActivityAchievementSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new SocialActivityAchievementSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityAchievementSoap[] toSoapModels(
		List<SocialActivityAchievement> models) {

		List<SocialActivityAchievementSoap> soapModels =
			new ArrayList<SocialActivityAchievementSoap>(models.size());

		for (SocialActivityAchievement model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new SocialActivityAchievementSoap[soapModels.size()]);
	}

	public SocialActivityAchievementSoap() {
	}

	public long getPrimaryKey() {
		return _activityAchievementId;
	}

	public void setPrimaryKey(long pk) {
		setActivityAchievementId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getActivityAchievementId() {
		return _activityAchievementId;
	}

	public void setActivityAchievementId(long activityAchievementId) {
		_activityAchievementId = activityAchievementId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public boolean getFirstInGroup() {
		return _firstInGroup;
	}

	public boolean isFirstInGroup() {
		return _firstInGroup;
	}

	public void setFirstInGroup(boolean firstInGroup) {
		_firstInGroup = firstInGroup;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _activityAchievementId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private long _createDate;
	private String _name;
	private boolean _firstInGroup;

}