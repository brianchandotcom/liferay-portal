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

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceShipment}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipment
 * @generated
 */
@ProviderType
public class CommerceShipmentWrapper implements CommerceShipment,
	ModelWrapper<CommerceShipment> {
	public CommerceShipmentWrapper(CommerceShipment commerceShipment) {
		_commerceShipment = commerceShipment;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceShipment.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceShipment.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceShipmentId", getCommerceShipmentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("shipmentUserId", getShipmentUserId());
		attributes.put("commerceAddressId", getCommerceAddressId());
		attributes.put("commerceShippingMethodId", getCommerceShippingMethodId());
		attributes.put("carrier", getCarrier());
		attributes.put("trackingNumber", getTrackingNumber());
		attributes.put("expectedDuration", getExpectedDuration());
		attributes.put("status", getStatus());
		attributes.put("shippingDate", getShippingDate());
		attributes.put("expectedDate", getExpectedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceShipmentId = (Long)attributes.get("commerceShipmentId");

		if (commerceShipmentId != null) {
			setCommerceShipmentId(commerceShipmentId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long shipmentUserId = (Long)attributes.get("shipmentUserId");

		if (shipmentUserId != null) {
			setShipmentUserId(shipmentUserId);
		}

		Long commerceAddressId = (Long)attributes.get("commerceAddressId");

		if (commerceAddressId != null) {
			setCommerceAddressId(commerceAddressId);
		}

		Long commerceShippingMethodId = (Long)attributes.get(
				"commerceShippingMethodId");

		if (commerceShippingMethodId != null) {
			setCommerceShippingMethodId(commerceShippingMethodId);
		}

		String carrier = (String)attributes.get("carrier");

		if (carrier != null) {
			setCarrier(carrier);
		}

		String trackingNumber = (String)attributes.get("trackingNumber");

		if (trackingNumber != null) {
			setTrackingNumber(trackingNumber);
		}

		Integer expectedDuration = (Integer)attributes.get("expectedDuration");

		if (expectedDuration != null) {
			setExpectedDuration(expectedDuration);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Date shippingDate = (Date)attributes.get("shippingDate");

		if (shippingDate != null) {
			setShippingDate(shippingDate);
		}

		Date expectedDate = (Date)attributes.get("expectedDate");

		if (expectedDate != null) {
			setExpectedDate(expectedDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceShipmentWrapper((CommerceShipment)_commerceShipment.clone());
	}

	@Override
	public int compareTo(CommerceShipment commerceShipment) {
		return _commerceShipment.compareTo(commerceShipment);
	}

	@Override
	public CommerceAddress fetchCommerceAddress() {
		return _commerceShipment.fetchCommerceAddress();
	}

	@Override
	public CommerceShippingMethod fetchCommerceShippingMethod() {
		return _commerceShipment.fetchCommerceShippingMethod();
	}

	/**
	* Returns the carrier of this commerce shipment.
	*
	* @return the carrier of this commerce shipment
	*/
	@Override
	public java.lang.String getCarrier() {
		return _commerceShipment.getCarrier();
	}

	/**
	* Returns the commerce address ID of this commerce shipment.
	*
	* @return the commerce address ID of this commerce shipment
	*/
	@Override
	public long getCommerceAddressId() {
		return _commerceShipment.getCommerceAddressId();
	}

	/**
	* Returns the commerce shipment ID of this commerce shipment.
	*
	* @return the commerce shipment ID of this commerce shipment
	*/
	@Override
	public long getCommerceShipmentId() {
		return _commerceShipment.getCommerceShipmentId();
	}

	/**
	* Returns the commerce shipping method ID of this commerce shipment.
	*
	* @return the commerce shipping method ID of this commerce shipment
	*/
	@Override
	public long getCommerceShippingMethodId() {
		return _commerceShipment.getCommerceShippingMethodId();
	}

	/**
	* Returns the company ID of this commerce shipment.
	*
	* @return the company ID of this commerce shipment
	*/
	@Override
	public long getCompanyId() {
		return _commerceShipment.getCompanyId();
	}

	/**
	* Returns the create date of this commerce shipment.
	*
	* @return the create date of this commerce shipment
	*/
	@Override
	public Date getCreateDate() {
		return _commerceShipment.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceShipment.getExpandoBridge();
	}

	/**
	* Returns the expected date of this commerce shipment.
	*
	* @return the expected date of this commerce shipment
	*/
	@Override
	public Date getExpectedDate() {
		return _commerceShipment.getExpectedDate();
	}

	/**
	* Returns the expected duration of this commerce shipment.
	*
	* @return the expected duration of this commerce shipment
	*/
	@Override
	public int getExpectedDuration() {
		return _commerceShipment.getExpectedDuration();
	}

	/**
	* Returns the group ID of this commerce shipment.
	*
	* @return the group ID of this commerce shipment
	*/
	@Override
	public long getGroupId() {
		return _commerceShipment.getGroupId();
	}

	/**
	* Returns the modified date of this commerce shipment.
	*
	* @return the modified date of this commerce shipment
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceShipment.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce shipment.
	*
	* @return the primary key of this commerce shipment
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceShipment.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceShipment.getPrimaryKeyObj();
	}

	/**
	* Returns the shipment user ID of this commerce shipment.
	*
	* @return the shipment user ID of this commerce shipment
	*/
	@Override
	public long getShipmentUserId() {
		return _commerceShipment.getShipmentUserId();
	}

	/**
	* Returns the shipment user uuid of this commerce shipment.
	*
	* @return the shipment user uuid of this commerce shipment
	*/
	@Override
	public java.lang.String getShipmentUserUuid() {
		return _commerceShipment.getShipmentUserUuid();
	}

	/**
	* Returns the shipping date of this commerce shipment.
	*
	* @return the shipping date of this commerce shipment
	*/
	@Override
	public Date getShippingDate() {
		return _commerceShipment.getShippingDate();
	}

	/**
	* Returns the status of this commerce shipment.
	*
	* @return the status of this commerce shipment
	*/
	@Override
	public int getStatus() {
		return _commerceShipment.getStatus();
	}

	/**
	* Returns the tracking number of this commerce shipment.
	*
	* @return the tracking number of this commerce shipment
	*/
	@Override
	public java.lang.String getTrackingNumber() {
		return _commerceShipment.getTrackingNumber();
	}

	/**
	* Returns the user ID of this commerce shipment.
	*
	* @return the user ID of this commerce shipment
	*/
	@Override
	public long getUserId() {
		return _commerceShipment.getUserId();
	}

	/**
	* Returns the user name of this commerce shipment.
	*
	* @return the user name of this commerce shipment
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceShipment.getUserName();
	}

	/**
	* Returns the user uuid of this commerce shipment.
	*
	* @return the user uuid of this commerce shipment
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceShipment.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceShipment.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceShipment.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceShipment.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceShipment.isNew();
	}

	@Override
	public void persist() {
		_commerceShipment.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceShipment.setCachedModel(cachedModel);
	}

	/**
	* Sets the carrier of this commerce shipment.
	*
	* @param carrier the carrier of this commerce shipment
	*/
	@Override
	public void setCarrier(java.lang.String carrier) {
		_commerceShipment.setCarrier(carrier);
	}

	/**
	* Sets the commerce address ID of this commerce shipment.
	*
	* @param commerceAddressId the commerce address ID of this commerce shipment
	*/
	@Override
	public void setCommerceAddressId(long commerceAddressId) {
		_commerceShipment.setCommerceAddressId(commerceAddressId);
	}

	/**
	* Sets the commerce shipment ID of this commerce shipment.
	*
	* @param commerceShipmentId the commerce shipment ID of this commerce shipment
	*/
	@Override
	public void setCommerceShipmentId(long commerceShipmentId) {
		_commerceShipment.setCommerceShipmentId(commerceShipmentId);
	}

	/**
	* Sets the commerce shipping method ID of this commerce shipment.
	*
	* @param commerceShippingMethodId the commerce shipping method ID of this commerce shipment
	*/
	@Override
	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		_commerceShipment.setCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	* Sets the company ID of this commerce shipment.
	*
	* @param companyId the company ID of this commerce shipment
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceShipment.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce shipment.
	*
	* @param createDate the create date of this commerce shipment
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceShipment.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceShipment.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceShipment.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceShipment.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expected date of this commerce shipment.
	*
	* @param expectedDate the expected date of this commerce shipment
	*/
	@Override
	public void setExpectedDate(Date expectedDate) {
		_commerceShipment.setExpectedDate(expectedDate);
	}

	/**
	* Sets the expected duration of this commerce shipment.
	*
	* @param expectedDuration the expected duration of this commerce shipment
	*/
	@Override
	public void setExpectedDuration(int expectedDuration) {
		_commerceShipment.setExpectedDuration(expectedDuration);
	}

	/**
	* Sets the group ID of this commerce shipment.
	*
	* @param groupId the group ID of this commerce shipment
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceShipment.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce shipment.
	*
	* @param modifiedDate the modified date of this commerce shipment
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceShipment.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceShipment.setNew(n);
	}

	/**
	* Sets the primary key of this commerce shipment.
	*
	* @param primaryKey the primary key of this commerce shipment
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceShipment.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceShipment.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the shipment user ID of this commerce shipment.
	*
	* @param shipmentUserId the shipment user ID of this commerce shipment
	*/
	@Override
	public void setShipmentUserId(long shipmentUserId) {
		_commerceShipment.setShipmentUserId(shipmentUserId);
	}

	/**
	* Sets the shipment user uuid of this commerce shipment.
	*
	* @param shipmentUserUuid the shipment user uuid of this commerce shipment
	*/
	@Override
	public void setShipmentUserUuid(java.lang.String shipmentUserUuid) {
		_commerceShipment.setShipmentUserUuid(shipmentUserUuid);
	}

	/**
	* Sets the shipping date of this commerce shipment.
	*
	* @param shippingDate the shipping date of this commerce shipment
	*/
	@Override
	public void setShippingDate(Date shippingDate) {
		_commerceShipment.setShippingDate(shippingDate);
	}

	/**
	* Sets the status of this commerce shipment.
	*
	* @param status the status of this commerce shipment
	*/
	@Override
	public void setStatus(int status) {
		_commerceShipment.setStatus(status);
	}

	/**
	* Sets the tracking number of this commerce shipment.
	*
	* @param trackingNumber the tracking number of this commerce shipment
	*/
	@Override
	public void setTrackingNumber(java.lang.String trackingNumber) {
		_commerceShipment.setTrackingNumber(trackingNumber);
	}

	/**
	* Sets the user ID of this commerce shipment.
	*
	* @param userId the user ID of this commerce shipment
	*/
	@Override
	public void setUserId(long userId) {
		_commerceShipment.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce shipment.
	*
	* @param userName the user name of this commerce shipment
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceShipment.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce shipment.
	*
	* @param userUuid the user uuid of this commerce shipment
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceShipment.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceShipment> toCacheModel() {
		return _commerceShipment.toCacheModel();
	}

	@Override
	public CommerceShipment toEscapedModel() {
		return new CommerceShipmentWrapper(_commerceShipment.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceShipment.toString();
	}

	@Override
	public CommerceShipment toUnescapedModel() {
		return new CommerceShipmentWrapper(_commerceShipment.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceShipment.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceShipmentWrapper)) {
			return false;
		}

		CommerceShipmentWrapper commerceShipmentWrapper = (CommerceShipmentWrapper)obj;

		if (Objects.equals(_commerceShipment,
					commerceShipmentWrapper._commerceShipment)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceShipment getWrappedModel() {
		return _commerceShipment;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceShipment.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceShipment.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceShipment.resetOriginalValues();
	}

	private final CommerceShipment _commerceShipment;
}