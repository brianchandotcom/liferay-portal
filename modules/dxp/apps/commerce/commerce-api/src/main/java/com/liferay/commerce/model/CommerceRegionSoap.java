/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceRegionServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.http.CommerceRegionServiceSoap
 * @generated
 */
@ProviderType
public class CommerceRegionSoap implements Serializable {
	public static CommerceRegionSoap toSoapModel(CommerceRegion model) {
		CommerceRegionSoap soapModel = new CommerceRegionSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceRegionId(model.getCommerceRegionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceCountryId(model.getCommerceCountryId());
		soapModel.setName(model.getName());
		soapModel.setCode(model.getCode());
		soapModel.setPriority(model.getPriority());
		soapModel.setActive(model.isActive());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CommerceRegionSoap[] toSoapModels(CommerceRegion[] models) {
		CommerceRegionSoap[] soapModels = new CommerceRegionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceRegionSoap[][] toSoapModels(CommerceRegion[][] models) {
		CommerceRegionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceRegionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceRegionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceRegionSoap[] toSoapModels(List<CommerceRegion> models) {
		List<CommerceRegionSoap> soapModels = new ArrayList<CommerceRegionSoap>(models.size());

		for (CommerceRegion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceRegionSoap[soapModels.size()]);
	}

	public CommerceRegionSoap() {
	}

	public long getPrimaryKey() {
		return _commerceRegionId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceRegionId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceRegionId() {
		return _commerceRegionId;
	}

	public void setCommerceRegionId(long commerceRegionId) {
		_commerceRegionId = commerceRegionId;
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

	public long getCommerceCountryId() {
		return _commerceCountryId;
	}

	public void setCommerceCountryId(long commerceCountryId) {
		_commerceCountryId = commerceCountryId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getCode() {
		return _code;
	}

	public void setCode(String code) {
		_code = code;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
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

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _commerceRegionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceCountryId;
	private String _name;
	private String _code;
	private double _priority;
	private boolean _active;
	private Date _lastPublishDate;
}