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
public class SocialActivityCounterSoap implements Serializable {

	public static SocialActivityCounterSoap toSoapModel(
		SocialActivityCounter model) {

		SocialActivityCounterSoap soapModel = new SocialActivityCounterSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setActivityCounterId(model.getActivityCounterId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setName(model.getName());
		soapModel.setOwnerType(model.getOwnerType());
		soapModel.setCurrentValue(model.getCurrentValue());
		soapModel.setTotalValue(model.getTotalValue());
		soapModel.setGraceValue(model.getGraceValue());
		soapModel.setStartPeriod(model.getStartPeriod());
		soapModel.setEndPeriod(model.getEndPeriod());
		soapModel.setActive(model.isActive());

		return soapModel;
	}

	public static SocialActivityCounterSoap[] toSoapModels(
		SocialActivityCounter[] models) {

		SocialActivityCounterSoap[] soapModels =
			new SocialActivityCounterSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityCounterSoap[][] toSoapModels(
		SocialActivityCounter[][] models) {

		SocialActivityCounterSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SocialActivityCounterSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialActivityCounterSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialActivityCounterSoap[] toSoapModels(
		List<SocialActivityCounter> models) {

		List<SocialActivityCounterSoap> soapModels =
			new ArrayList<SocialActivityCounterSoap>(models.size());

		for (SocialActivityCounter model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new SocialActivityCounterSoap[soapModels.size()]);
	}

	public SocialActivityCounterSoap() {
	}

	public long getPrimaryKey() {
		return _activityCounterId;
	}

	public void setPrimaryKey(long pk) {
		setActivityCounterId(pk);
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

	public long getActivityCounterId() {
		return _activityCounterId;
	}

	public void setActivityCounterId(long activityCounterId) {
		_activityCounterId = activityCounterId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getOwnerType() {
		return _ownerType;
	}

	public void setOwnerType(int ownerType) {
		_ownerType = ownerType;
	}

	public int getCurrentValue() {
		return _currentValue;
	}

	public void setCurrentValue(int currentValue) {
		_currentValue = currentValue;
	}

	public int getTotalValue() {
		return _totalValue;
	}

	public void setTotalValue(int totalValue) {
		_totalValue = totalValue;
	}

	public int getGraceValue() {
		return _graceValue;
	}

	public void setGraceValue(int graceValue) {
		_graceValue = graceValue;
	}

	public int getStartPeriod() {
		return _startPeriod;
	}

	public void setStartPeriod(int startPeriod) {
		_startPeriod = startPeriod;
	}

	public int getEndPeriod() {
		return _endPeriod;
	}

	public void setEndPeriod(int endPeriod) {
		_endPeriod = endPeriod;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _activityCounterId;
	private long _groupId;
	private long _companyId;
	private long _classNameId;
	private long _classPK;
	private String _name;
	private int _ownerType;
	private int _currentValue;
	private int _totalValue;
	private int _graceValue;
	private int _startPeriod;
	private int _endPeriod;
	private boolean _active;

}