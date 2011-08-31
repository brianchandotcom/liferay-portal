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
public class SocialActivityStatsEntrySoap implements Serializable {
	public static SocialActivityStatsEntrySoap toSoapModel(
		SocialActivityStatsEntry model) {
		SocialActivityStatsEntrySoap soapModel = new SocialActivityStatsEntrySoap();

		soapModel.setActivityStatsEntryId(model.getActivityStatsEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setClassType(model.getClassType());
		soapModel.setStatName(model.getStatName());
		soapModel.setCurrentValue(model.getCurrentValue());
		soapModel.setOverallValue(model.getOverallValue());
		soapModel.setGraceValue(model.getGraceValue());
		soapModel.setStatPeriodStart(model.getStatPeriodStart());
		soapModel.setStatPeriodEnd(model.getStatPeriodEnd());

		return soapModel;
	}

	public static SocialActivityStatsEntrySoap[] toSoapModels(
		SocialActivityStatsEntry[] models) {
		SocialActivityStatsEntrySoap[] soapModels = new SocialActivityStatsEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityStatsEntrySoap[][] toSoapModels(
		SocialActivityStatsEntry[][] models) {
		SocialActivityStatsEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialActivityStatsEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialActivityStatsEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityStatsEntrySoap[] toSoapModels(
		List<SocialActivityStatsEntry> models) {
		List<SocialActivityStatsEntrySoap> soapModels = new ArrayList<SocialActivityStatsEntrySoap>(models.size());

		for (SocialActivityStatsEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialActivityStatsEntrySoap[soapModels.size()]);
	}

	public SocialActivityStatsEntrySoap() {
	}

	public long getPrimaryKey() {
		return _activityStatsEntryId;
	}

	public void setPrimaryKey(long pk) {
		setActivityStatsEntryId(pk);
	}

	public long getActivityStatsEntryId() {
		return _activityStatsEntryId;
	}

	public void setActivityStatsEntryId(long activityStatsEntryId) {
		_activityStatsEntryId = activityStatsEntryId;
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

	public int getClassType() {
		return _classType;
	}

	public void setClassType(int classType) {
		_classType = classType;
	}

	public String getStatName() {
		return _statName;
	}

	public void setStatName(String statName) {
		_statName = statName;
	}

	public int getCurrentValue() {
		return _currentValue;
	}

	public void setCurrentValue(int currentValue) {
		_currentValue = currentValue;
	}

	public int getOverallValue() {
		return _overallValue;
	}

	public void setOverallValue(int overallValue) {
		_overallValue = overallValue;
	}

	public int getGraceValue() {
		return _graceValue;
	}

	public void setGraceValue(int graceValue) {
		_graceValue = graceValue;
	}

	public int getStatPeriodStart() {
		return _statPeriodStart;
	}

	public void setStatPeriodStart(int statPeriodStart) {
		_statPeriodStart = statPeriodStart;
	}

	public int getStatPeriodEnd() {
		return _statPeriodEnd;
	}

	public void setStatPeriodEnd(int statPeriodEnd) {
		_statPeriodEnd = statPeriodEnd;
	}

	private long _activityStatsEntryId;
	private long _groupId;
	private long _companyId;
	private long _classNameId;
	private long _classPK;
	private int _classType;
	private String _statName;
	private int _currentValue;
	private int _overallValue;
	private int _graceValue;
	private int _statPeriodStart;
	private int _statPeriodEnd;
}