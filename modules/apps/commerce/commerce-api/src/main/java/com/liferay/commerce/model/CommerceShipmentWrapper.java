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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceShipment}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipment
 * @generated
 */
public class CommerceShipmentWrapper
	extends BaseModelWrapper<CommerceShipment>
	implements CommerceShipment, ModelWrapper<CommerceShipment> {

	public CommerceShipmentWrapper(CommerceShipment commerceShipment) {
		super(commerceShipment);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commerceShipmentId", getCommerceShipmentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceAccountId", getCommerceAccountId());
		attributes.put("commerceAddressId", getCommerceAddressId());
		attributes.put(
			"commerceShippingMethodId", getCommerceShippingMethodId());
		attributes.put("shippingOptionName", getShippingOptionName());
		attributes.put("carrier", getCarrier());
		attributes.put("trackingNumber", getTrackingNumber());
		attributes.put("shippingDate", getShippingDate());
		attributes.put("expectedDate", getExpectedDate());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

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

		Long commerceAccountId = (Long)attributes.get("commerceAccountId");

		if (commerceAccountId != null) {
			setCommerceAccountId(commerceAccountId);
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

		String shippingOptionName = (String)attributes.get(
			"shippingOptionName");

		if (shippingOptionName != null) {
			setShippingOptionName(shippingOptionName);
		}

		String carrier = (String)attributes.get("carrier");

		if (carrier != null) {
			setCarrier(carrier);
		}

		String trackingNumber = (String)attributes.get("trackingNumber");

		if (trackingNumber != null) {
			setTrackingNumber(trackingNumber);
		}

		Date shippingDate = (Date)attributes.get("shippingDate");

		if (shippingDate != null) {
			setShippingDate(shippingDate);
		}

		Date expectedDate = (Date)attributes.get("expectedDate");

		if (expectedDate != null) {
			setExpectedDate(expectedDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public CommerceShipment cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public CommerceAddress fetchCommerceAddress() {
		return model.fetchCommerceAddress();
	}

	@Override
	public CommerceShippingMethod fetchCommerceShippingMethod() {
		return model.fetchCommerceShippingMethod();
	}

	/**
	 * Returns the carrier of this commerce shipment.
	 *
	 * @return the carrier of this commerce shipment
	 */
	@Override
	public String getCarrier() {
		return model.getCarrier();
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceAccount();
	}

	/**
	 * Returns the commerce account ID of this commerce shipment.
	 *
	 * @return the commerce account ID of this commerce shipment
	 */
	@Override
	public long getCommerceAccountId() {
		return model.getCommerceAccountId();
	}

	@Override
	public String getCommerceAccountName()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceAccountName();
	}

	/**
	 * Returns the commerce address ID of this commerce shipment.
	 *
	 * @return the commerce address ID of this commerce shipment
	 */
	@Override
	public long getCommerceAddressId() {
		return model.getCommerceAddressId();
	}

	/**
	 * Returns the commerce shipment ID of this commerce shipment.
	 *
	 * @return the commerce shipment ID of this commerce shipment
	 */
	@Override
	public long getCommerceShipmentId() {
		return model.getCommerceShipmentId();
	}

	@Override
	public CommerceShippingMethod getCommerceShippingMethod()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceShippingMethod();
	}

	/**
	 * Returns the commerce shipping method ID of this commerce shipment.
	 *
	 * @return the commerce shipping method ID of this commerce shipment
	 */
	@Override
	public long getCommerceShippingMethodId() {
		return model.getCommerceShippingMethodId();
	}

	/**
	 * Returns the company ID of this commerce shipment.
	 *
	 * @return the company ID of this commerce shipment
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce shipment.
	 *
	 * @return the create date of this commerce shipment
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the expected date of this commerce shipment.
	 *
	 * @return the expected date of this commerce shipment
	 */
	@Override
	public Date getExpectedDate() {
		return model.getExpectedDate();
	}

	/**
	 * Returns the external reference code of this commerce shipment.
	 *
	 * @return the external reference code of this commerce shipment
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this commerce shipment.
	 *
	 * @return the group ID of this commerce shipment
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce shipment.
	 *
	 * @return the modified date of this commerce shipment
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce shipment.
	 *
	 * @return the mvcc version of this commerce shipment
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce shipment.
	 *
	 * @return the primary key of this commerce shipment
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the shipping date of this commerce shipment.
	 *
	 * @return the shipping date of this commerce shipment
	 */
	@Override
	public Date getShippingDate() {
		return model.getShippingDate();
	}

	/**
	 * Returns the shipping option name of this commerce shipment.
	 *
	 * @return the shipping option name of this commerce shipment
	 */
	@Override
	public String getShippingOptionName() {
		return model.getShippingOptionName();
	}

	/**
	 * Returns the status of this commerce shipment.
	 *
	 * @return the status of this commerce shipment
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the tracking number of this commerce shipment.
	 *
	 * @return the tracking number of this commerce shipment
	 */
	@Override
	public String getTrackingNumber() {
		return model.getTrackingNumber();
	}

	/**
	 * Returns the user ID of this commerce shipment.
	 *
	 * @return the user ID of this commerce shipment
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce shipment.
	 *
	 * @return the user name of this commerce shipment
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce shipment.
	 *
	 * @return the user uuid of this commerce shipment
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the carrier of this commerce shipment.
	 *
	 * @param carrier the carrier of this commerce shipment
	 */
	@Override
	public void setCarrier(String carrier) {
		model.setCarrier(carrier);
	}

	/**
	 * Sets the commerce account ID of this commerce shipment.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce shipment
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		model.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the commerce address ID of this commerce shipment.
	 *
	 * @param commerceAddressId the commerce address ID of this commerce shipment
	 */
	@Override
	public void setCommerceAddressId(long commerceAddressId) {
		model.setCommerceAddressId(commerceAddressId);
	}

	/**
	 * Sets the commerce shipment ID of this commerce shipment.
	 *
	 * @param commerceShipmentId the commerce shipment ID of this commerce shipment
	 */
	@Override
	public void setCommerceShipmentId(long commerceShipmentId) {
		model.setCommerceShipmentId(commerceShipmentId);
	}

	/**
	 * Sets the commerce shipping method ID of this commerce shipment.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID of this commerce shipment
	 */
	@Override
	public void setCommerceShippingMethodId(long commerceShippingMethodId) {
		model.setCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	 * Sets the company ID of this commerce shipment.
	 *
	 * @param companyId the company ID of this commerce shipment
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce shipment.
	 *
	 * @param createDate the create date of this commerce shipment
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the expected date of this commerce shipment.
	 *
	 * @param expectedDate the expected date of this commerce shipment
	 */
	@Override
	public void setExpectedDate(Date expectedDate) {
		model.setExpectedDate(expectedDate);
	}

	/**
	 * Sets the external reference code of this commerce shipment.
	 *
	 * @param externalReferenceCode the external reference code of this commerce shipment
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this commerce shipment.
	 *
	 * @param groupId the group ID of this commerce shipment
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce shipment.
	 *
	 * @param modifiedDate the modified date of this commerce shipment
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce shipment.
	 *
	 * @param mvccVersion the mvcc version of this commerce shipment
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce shipment.
	 *
	 * @param primaryKey the primary key of this commerce shipment
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the shipping date of this commerce shipment.
	 *
	 * @param shippingDate the shipping date of this commerce shipment
	 */
	@Override
	public void setShippingDate(Date shippingDate) {
		model.setShippingDate(shippingDate);
	}

	/**
	 * Sets the shipping option name of this commerce shipment.
	 *
	 * @param shippingOptionName the shipping option name of this commerce shipment
	 */
	@Override
	public void setShippingOptionName(String shippingOptionName) {
		model.setShippingOptionName(shippingOptionName);
	}

	/**
	 * Sets the status of this commerce shipment.
	 *
	 * @param status the status of this commerce shipment
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the tracking number of this commerce shipment.
	 *
	 * @param trackingNumber the tracking number of this commerce shipment
	 */
	@Override
	public void setTrackingNumber(String trackingNumber) {
		model.setTrackingNumber(trackingNumber);
	}

	/**
	 * Sets the user ID of this commerce shipment.
	 *
	 * @param userId the user ID of this commerce shipment
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce shipment.
	 *
	 * @param userName the user name of this commerce shipment
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce shipment.
	 *
	 * @param userUuid the user uuid of this commerce shipment
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceShipmentWrapper wrap(CommerceShipment commerceShipment) {
		return new CommerceShipmentWrapper(commerceShipment);
	}

}