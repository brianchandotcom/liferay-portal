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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserNotificationDeliverySoap implements Serializable {
	public static UserNotificationDeliverySoap toSoapModel(
		UserNotificationDelivery model) {
		UserNotificationDeliverySoap soapModel = new UserNotificationDeliverySoap();

		soapModel.setUserNotificationDeliveryId(model.getUserNotificationDeliveryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setType(model.getType());
		soapModel.setEmail(model.getEmail());
		soapModel.setSms(model.getSms());
		soapModel.setWebsite(model.getWebsite());

		return soapModel;
	}

	public static UserNotificationDeliverySoap[] toSoapModels(
		UserNotificationDelivery[] models) {
		UserNotificationDeliverySoap[] soapModels = new UserNotificationDeliverySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static UserNotificationDeliverySoap[][] toSoapModels(
		UserNotificationDelivery[][] models) {
		UserNotificationDeliverySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new UserNotificationDeliverySoap[models.length][models[0].length];
		}
		else {
			soapModels = new UserNotificationDeliverySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static UserNotificationDeliverySoap[] toSoapModels(
		List<UserNotificationDelivery> models) {
		List<UserNotificationDeliverySoap> soapModels = new ArrayList<UserNotificationDeliverySoap>(models.size());

		for (UserNotificationDelivery model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new UserNotificationDeliverySoap[soapModels.size()]);
	}

	public UserNotificationDeliverySoap() {
	}

	public long getPrimaryKey() {
		return _userNotificationDeliveryId;
	}

	public void setPrimaryKey(long pk) {
		setUserNotificationDeliveryId(pk);
	}

	public long getUserNotificationDeliveryId() {
		return _userNotificationDeliveryId;
	}

	public void setUserNotificationDeliveryId(long userNotificationDeliveryId) {
		_userNotificationDeliveryId = userNotificationDeliveryId;
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

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public boolean getEmail() {
		return _email;
	}

	public boolean isEmail() {
		return _email;
	}

	public void setEmail(boolean email) {
		_email = email;
	}

	public boolean getSms() {
		return _sms;
	}

	public boolean isSms() {
		return _sms;
	}

	public void setSms(boolean sms) {
		_sms = sms;
	}

	public boolean getWebsite() {
		return _website;
	}

	public boolean isWebsite() {
		return _website;
	}

	public void setWebsite(boolean website) {
		_website = website;
	}

	private long _userNotificationDeliveryId;
	private long _companyId;
	private long _userId;
	private long _classNameId;
	private int _type;
	private boolean _email;
	private boolean _sms;
	private boolean _website;
}