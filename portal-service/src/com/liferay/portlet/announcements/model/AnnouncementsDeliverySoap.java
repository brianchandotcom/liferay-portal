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

package com.liferay.portlet.announcements.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.announcements.service.http.AnnouncementsDeliveryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.announcements.service.http.AnnouncementsDeliveryServiceSoap
 * @generated
 */
public class AnnouncementsDeliverySoap implements Serializable {
	public static AnnouncementsDeliverySoap toSoapModel(
		AnnouncementsDelivery model) {
		AnnouncementsDeliverySoap soapModel = new AnnouncementsDeliverySoap();

		soapModel.setDeliveryId(model.getDeliveryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setType(model.getType());
		soapModel.setEmail(model.getEmail());
		soapModel.setSms(model.getSms());
		soapModel.setWebsite(model.getWebsite());

		return soapModel;
	}

	public static AnnouncementsDeliverySoap[] toSoapModels(
		AnnouncementsDelivery[] models) {
		AnnouncementsDeliverySoap[] soapModels = new AnnouncementsDeliverySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AnnouncementsDeliverySoap[][] toSoapModels(
		AnnouncementsDelivery[][] models) {
		AnnouncementsDeliverySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AnnouncementsDeliverySoap[models.length][models[0].length];
		}
		else {
			soapModels = new AnnouncementsDeliverySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AnnouncementsDeliverySoap[] toSoapModels(
		List<AnnouncementsDelivery> models) {
		List<AnnouncementsDeliverySoap> soapModels = new ArrayList<AnnouncementsDeliverySoap>(models.size());

		for (AnnouncementsDelivery model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AnnouncementsDeliverySoap[soapModels.size()]);
	}

	public AnnouncementsDeliverySoap() {
	}

	public long getPrimaryKey() {
		return _deliveryId;
	}

	public void setPrimaryKey(long pk) {
		setDeliveryId(pk);
	}

	public long getDeliveryId() {
		return _deliveryId;
	}

	public void setDeliveryId(long deliveryId) {
		_deliveryId = deliveryId;
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

	private long _deliveryId;
	private long _companyId;
	private long _userId;
	private String _type;
	private boolean _email;
	private boolean _sms;
	private boolean _website;
}