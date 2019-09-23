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

package com.liferay.mobile.device.rules.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.mobile.device.rules.service.http.MDRRuleGroupInstanceServiceSoap}.
 *
 * @author Edward C. Han
 * @generated
 */
public class MDRRuleGroupInstanceSoap implements Serializable {

	public static MDRRuleGroupInstanceSoap toSoapModel(
		MDRRuleGroupInstance model) {

		MDRRuleGroupInstanceSoap soapModel = new MDRRuleGroupInstanceSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setRuleGroupInstanceId(model.getRuleGroupInstanceId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setRuleGroupId(model.getRuleGroupId());
		soapModel.setPriority(model.getPriority());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static MDRRuleGroupInstanceSoap[] toSoapModels(
		MDRRuleGroupInstance[] models) {

		MDRRuleGroupInstanceSoap[] soapModels =
			new MDRRuleGroupInstanceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MDRRuleGroupInstanceSoap[][] toSoapModels(
		MDRRuleGroupInstance[][] models) {

		MDRRuleGroupInstanceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new MDRRuleGroupInstanceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new MDRRuleGroupInstanceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MDRRuleGroupInstanceSoap[] toSoapModels(
		List<MDRRuleGroupInstance> models) {

		List<MDRRuleGroupInstanceSoap> soapModels =
			new ArrayList<MDRRuleGroupInstanceSoap>(models.size());

		for (MDRRuleGroupInstance model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new MDRRuleGroupInstanceSoap[soapModels.size()]);
	}

	public MDRRuleGroupInstanceSoap() {
	}

	public long getPrimaryKey() {
		return _ruleGroupInstanceId;
	}

	public void setPrimaryKey(long pk) {
		setRuleGroupInstanceId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getRuleGroupInstanceId() {
		return _ruleGroupInstanceId;
	}

	public void setRuleGroupInstanceId(long ruleGroupInstanceId) {
		_ruleGroupInstanceId = ruleGroupInstanceId;
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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
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

	public long getRuleGroupId() {
		return _ruleGroupId;
	}

	public void setRuleGroupId(long ruleGroupId) {
		_ruleGroupId = ruleGroupId;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _ruleGroupInstanceId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _ruleGroupId;
	private int _priority;
	private Date _lastPublishDate;

}