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
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class UserNotificationEventSoap implements Serializable {
	public static UserNotificationEventSoap toSoapModel(
		UserNotificationEvent model) {
		UserNotificationEventSoap soapModel = new UserNotificationEventSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setUserNotificationEventId(model.getUserNotificationEventId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setType(model.getType());
		soapModel.setTimestamp(model.getTimestamp());
		soapModel.setDeliverBy(model.getDeliverBy());
		soapModel.setDelivered(model.getDelivered());
		soapModel.setPayload(model.getPayload());
		soapModel.setArchived(model.getArchived());

		return soapModel;
	}

	public static UserNotificationEventSoap[] toSoapModels(
		UserNotificationEvent[] models) {
		UserNotificationEventSoap[] soapModels = new UserNotificationEventSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static UserNotificationEventSoap[][] toSoapModels(
		UserNotificationEvent[][] models) {
		UserNotificationEventSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new UserNotificationEventSoap[models.length][models[0].length];
		}
		else {
			soapModels = new UserNotificationEventSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static UserNotificationEventSoap[] toSoapModels(
		List<UserNotificationEvent> models) {
		List<UserNotificationEventSoap> soapModels = new ArrayList<UserNotificationEventSoap>(models.size());

		for (UserNotificationEvent model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new UserNotificationEventSoap[soapModels.size()]);
	}

	public UserNotificationEventSoap() {
	}

	public long getPrimaryKey() {
		return _userNotificationEventId;
	}

	public void setPrimaryKey(long pk) {
		setUserNotificationEventId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getUserNotificationEventId() {
		return _userNotificationEventId;
	}

	public void setUserNotificationEventId(long userNotificationEventId) {
		_userNotificationEventId = userNotificationEventId;
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

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public long getTimestamp() {
		return _timestamp;
	}

	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}

	public long getDeliverBy() {
		return _deliverBy;
	}

	public void setDeliverBy(long deliverBy) {
		_deliverBy = deliverBy;
	}

	public boolean getDelivered() {
		return _delivered;
	}

	public boolean isDelivered() {
		return _delivered;
	}

	public void setDelivered(boolean delivered) {
		_delivered = delivered;
	}

	public String getPayload() {
		return _payload;
	}

	public void setPayload(String payload) {
		_payload = payload;
	}

	public boolean getArchived() {
		return _archived;
	}

	public boolean isArchived() {
		return _archived;
	}

	public void setArchived(boolean archived) {
		_archived = archived;
	}

	private String _uuid;
	private long _userNotificationEventId;
	private long _companyId;
	private long _userId;
	private String _type;
	private long _timestamp;
	private long _deliverBy;
	private boolean _delivered;
	private String _payload;
	private boolean _archived;
}