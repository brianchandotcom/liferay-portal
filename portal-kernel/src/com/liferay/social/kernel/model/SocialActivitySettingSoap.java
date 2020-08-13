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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.social.service.http.SocialActivitySettingServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SocialActivitySettingSoap implements Serializable {

	public static SocialActivitySettingSoap toSoapModel(
		SocialActivitySetting model) {

		SocialActivitySettingSoap soapModel = new SocialActivitySettingSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setActivitySettingId(model.getActivitySettingId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setActivityType(model.getActivityType());
		soapModel.setName(model.getName());
		soapModel.setValue(model.getValue());

		return soapModel;
	}

	public static SocialActivitySettingSoap[] toSoapModels(
		SocialActivitySetting[] models) {

		SocialActivitySettingSoap[] soapModels =
			new SocialActivitySettingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialActivitySettingSoap[][] toSoapModels(
		SocialActivitySetting[][] models) {

		SocialActivitySettingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SocialActivitySettingSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialActivitySettingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialActivitySettingSoap[] toSoapModels(
		List<SocialActivitySetting> models) {

		List<SocialActivitySettingSoap> soapModels =
			new ArrayList<SocialActivitySettingSoap>(models.size());

		for (SocialActivitySetting model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new SocialActivitySettingSoap[soapModels.size()]);
	}

	public SocialActivitySettingSoap() {
	}

	public long getPrimaryKey() {
		return _activitySettingId;
	}

	public void setPrimaryKey(long pk) {
		setActivitySettingId(pk);
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

	public long getActivitySettingId() {
		return _activitySettingId;
	}

	public void setActivitySettingId(long activitySettingId) {
		_activitySettingId = activitySettingId;
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

	public int getActivityType() {
		return _activityType;
	}

	public void setActivityType(int activityType) {
		_activityType = activityType;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _activitySettingId;
	private long _groupId;
	private long _companyId;
	private long _classNameId;
	private int _activityType;
	private String _name;
	private String _value;

}