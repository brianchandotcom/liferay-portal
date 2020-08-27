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

package com.liferay.commerce.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.service.http.CommerceSubscriptionEntryServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceSubscriptionEntrySoap implements Serializable {

	public static CommerceSubscriptionEntrySoap toSoapModel(
		CommerceSubscriptionEntry model) {

		CommerceSubscriptionEntrySoap soapModel =
			new CommerceSubscriptionEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCommerceSubscriptionEntryId(
			model.getCommerceSubscriptionEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPInstanceUuid(model.getCPInstanceUuid());
		soapModel.setCProductId(model.getCProductId());
		soapModel.setCommerceOrderItemId(model.getCommerceOrderItemId());
		soapModel.setSubscriptionLength(model.getSubscriptionLength());
		soapModel.setSubscriptionType(model.getSubscriptionType());
		soapModel.setSubscriptionTypeSettings(
			model.getSubscriptionTypeSettings());
		soapModel.setCurrentCycle(model.getCurrentCycle());
		soapModel.setMaxSubscriptionCycles(model.getMaxSubscriptionCycles());
		soapModel.setSubscriptionStatus(model.getSubscriptionStatus());
		soapModel.setLastIterationDate(model.getLastIterationDate());
		soapModel.setNextIterationDate(model.getNextIterationDate());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setDeliverySubscriptionLength(
			model.getDeliverySubscriptionLength());
		soapModel.setDeliverySubscriptionType(
			model.getDeliverySubscriptionType());
		soapModel.setDeliverySubscriptionTypeSettings(
			model.getDeliverySubscriptionTypeSettings());
		soapModel.setDeliveryCurrentCycle(model.getDeliveryCurrentCycle());
		soapModel.setDeliveryMaxSubscriptionCycles(
			model.getDeliveryMaxSubscriptionCycles());
		soapModel.setDeliverySubscriptionStatus(
			model.getDeliverySubscriptionStatus());
		soapModel.setDeliveryLastIterationDate(
			model.getDeliveryLastIterationDate());
		soapModel.setDeliveryNextIterationDate(
			model.getDeliveryNextIterationDate());
		soapModel.setDeliveryStartDate(model.getDeliveryStartDate());

		return soapModel;
	}

	public static CommerceSubscriptionEntrySoap[] toSoapModels(
		CommerceSubscriptionEntry[] models) {

		CommerceSubscriptionEntrySoap[] soapModels =
			new CommerceSubscriptionEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceSubscriptionEntrySoap[][] toSoapModels(
		CommerceSubscriptionEntry[][] models) {

		CommerceSubscriptionEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceSubscriptionEntrySoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceSubscriptionEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceSubscriptionEntrySoap[] toSoapModels(
		List<CommerceSubscriptionEntry> models) {

		List<CommerceSubscriptionEntrySoap> soapModels =
			new ArrayList<CommerceSubscriptionEntrySoap>(models.size());

		for (CommerceSubscriptionEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceSubscriptionEntrySoap[soapModels.size()]);
	}

	public CommerceSubscriptionEntrySoap() {
	}

	public long getPrimaryKey() {
		return _commerceSubscriptionEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceSubscriptionEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCommerceSubscriptionEntryId() {
		return _commerceSubscriptionEntryId;
	}

	public void setCommerceSubscriptionEntryId(
		long commerceSubscriptionEntryId) {

		_commerceSubscriptionEntryId = commerceSubscriptionEntryId;
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

	public String getCPInstanceUuid() {
		return _CPInstanceUuid;
	}

	public void setCPInstanceUuid(String CPInstanceUuid) {
		_CPInstanceUuid = CPInstanceUuid;
	}

	public long getCProductId() {
		return _CProductId;
	}

	public void setCProductId(long CProductId) {
		_CProductId = CProductId;
	}

	public long getCommerceOrderItemId() {
		return _commerceOrderItemId;
	}

	public void setCommerceOrderItemId(long commerceOrderItemId) {
		_commerceOrderItemId = commerceOrderItemId;
	}

	public int getSubscriptionLength() {
		return _subscriptionLength;
	}

	public void setSubscriptionLength(int subscriptionLength) {
		_subscriptionLength = subscriptionLength;
	}

	public String getSubscriptionType() {
		return _subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		_subscriptionType = subscriptionType;
	}

	public String getSubscriptionTypeSettings() {
		return _subscriptionTypeSettings;
	}

	public void setSubscriptionTypeSettings(String subscriptionTypeSettings) {
		_subscriptionTypeSettings = subscriptionTypeSettings;
	}

	public long getCurrentCycle() {
		return _currentCycle;
	}

	public void setCurrentCycle(long currentCycle) {
		_currentCycle = currentCycle;
	}

	public long getMaxSubscriptionCycles() {
		return _maxSubscriptionCycles;
	}

	public void setMaxSubscriptionCycles(long maxSubscriptionCycles) {
		_maxSubscriptionCycles = maxSubscriptionCycles;
	}

	public int getSubscriptionStatus() {
		return _subscriptionStatus;
	}

	public void setSubscriptionStatus(int subscriptionStatus) {
		_subscriptionStatus = subscriptionStatus;
	}

	public Date getLastIterationDate() {
		return _lastIterationDate;
	}

	public void setLastIterationDate(Date lastIterationDate) {
		_lastIterationDate = lastIterationDate;
	}

	public Date getNextIterationDate() {
		return _nextIterationDate;
	}

	public void setNextIterationDate(Date nextIterationDate) {
		_nextIterationDate = nextIterationDate;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public int getDeliverySubscriptionLength() {
		return _deliverySubscriptionLength;
	}

	public void setDeliverySubscriptionLength(int deliverySubscriptionLength) {
		_deliverySubscriptionLength = deliverySubscriptionLength;
	}

	public String getDeliverySubscriptionType() {
		return _deliverySubscriptionType;
	}

	public void setDeliverySubscriptionType(String deliverySubscriptionType) {
		_deliverySubscriptionType = deliverySubscriptionType;
	}

	public String getDeliverySubscriptionTypeSettings() {
		return _deliverySubscriptionTypeSettings;
	}

	public void setDeliverySubscriptionTypeSettings(
		String deliverySubscriptionTypeSettings) {

		_deliverySubscriptionTypeSettings = deliverySubscriptionTypeSettings;
	}

	public long getDeliveryCurrentCycle() {
		return _deliveryCurrentCycle;
	}

	public void setDeliveryCurrentCycle(long deliveryCurrentCycle) {
		_deliveryCurrentCycle = deliveryCurrentCycle;
	}

	public long getDeliveryMaxSubscriptionCycles() {
		return _deliveryMaxSubscriptionCycles;
	}

	public void setDeliveryMaxSubscriptionCycles(
		long deliveryMaxSubscriptionCycles) {

		_deliveryMaxSubscriptionCycles = deliveryMaxSubscriptionCycles;
	}

	public int getDeliverySubscriptionStatus() {
		return _deliverySubscriptionStatus;
	}

	public void setDeliverySubscriptionStatus(int deliverySubscriptionStatus) {
		_deliverySubscriptionStatus = deliverySubscriptionStatus;
	}

	public Date getDeliveryLastIterationDate() {
		return _deliveryLastIterationDate;
	}

	public void setDeliveryLastIterationDate(Date deliveryLastIterationDate) {
		_deliveryLastIterationDate = deliveryLastIterationDate;
	}

	public Date getDeliveryNextIterationDate() {
		return _deliveryNextIterationDate;
	}

	public void setDeliveryNextIterationDate(Date deliveryNextIterationDate) {
		_deliveryNextIterationDate = deliveryNextIterationDate;
	}

	public Date getDeliveryStartDate() {
		return _deliveryStartDate;
	}

	public void setDeliveryStartDate(Date deliveryStartDate) {
		_deliveryStartDate = deliveryStartDate;
	}

	private String _uuid;
	private long _commerceSubscriptionEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _CPInstanceUuid;
	private long _CProductId;
	private long _commerceOrderItemId;
	private int _subscriptionLength;
	private String _subscriptionType;
	private String _subscriptionTypeSettings;
	private long _currentCycle;
	private long _maxSubscriptionCycles;
	private int _subscriptionStatus;
	private Date _lastIterationDate;
	private Date _nextIterationDate;
	private Date _startDate;
	private int _deliverySubscriptionLength;
	private String _deliverySubscriptionType;
	private String _deliverySubscriptionTypeSettings;
	private long _deliveryCurrentCycle;
	private long _deliveryMaxSubscriptionCycles;
	private int _deliverySubscriptionStatus;
	private Date _deliveryLastIterationDate;
	private Date _deliveryNextIterationDate;
	private Date _deliveryStartDate;

}