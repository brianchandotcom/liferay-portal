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
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class SocialActivityAchievementSoap implements Serializable {
	public static SocialActivityAchievementSoap toSoapModel(
		SocialActivityAchievement model) {
		SocialActivityAchievementSoap soapModel = new SocialActivityAchievementSoap();

		soapModel.setActivityAchievementId(model.getActivityAchievementId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setName(model.getName());
		soapModel.setUnlockDate(model.getUnlockDate());
		soapModel.setUnlockedBy(model.getUnlockedBy());
		soapModel.setFirstUnlock(model.getFirstUnlock());

		return soapModel;
	}

	public static SocialActivityAchievementSoap[] toSoapModels(
		SocialActivityAchievement[] models) {
		SocialActivityAchievementSoap[] soapModels = new SocialActivityAchievementSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityAchievementSoap[][] toSoapModels(
		SocialActivityAchievement[][] models) {
		SocialActivityAchievementSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialActivityAchievementSoap[models.length][models[0].length];
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
		List<SocialActivityAchievementSoap> soapModels = new ArrayList<SocialActivityAchievementSoap>(models.size());

		for (SocialActivityAchievement model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialActivityAchievementSoap[soapModels.size()]);
	}

	public SocialActivityAchievementSoap() {
	}

	public long getPrimaryKey() {
		return _activityAchievementId;
	}

	public void setPrimaryKey(long pk) {
		setActivityAchievementId(pk);
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public Date getUnlockDate() {
		return _unlockDate;
	}

	public void setUnlockDate(Date unlockDate) {
		_unlockDate = unlockDate;
	}

	public long getUnlockedBy() {
		return _unlockedBy;
	}

	public void setUnlockedBy(long unlockedBy) {
		_unlockedBy = unlockedBy;
	}

	public boolean getFirstUnlock() {
		return _firstUnlock;
	}

	public boolean isFirstUnlock() {
		return _firstUnlock;
	}

	public void setFirstUnlock(boolean firstUnlock) {
		_firstUnlock = firstUnlock;
	}

	private long _activityAchievementId;
	private long _groupId;
	private long _companyId;
	private String _name;
	private Date _unlockDate;
	private long _unlockedBy;
	private boolean _firstUnlock;
}