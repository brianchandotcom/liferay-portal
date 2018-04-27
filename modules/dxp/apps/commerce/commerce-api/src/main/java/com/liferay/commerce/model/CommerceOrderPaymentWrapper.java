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
 * This class is a wrapper for {@link CommerceOrderPayment}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPayment
 * @generated
 */
@ProviderType
public class CommerceOrderPaymentWrapper implements CommerceOrderPayment,
	ModelWrapper<CommerceOrderPayment> {
	public CommerceOrderPaymentWrapper(
		CommerceOrderPayment commerceOrderPayment) {
		_commerceOrderPayment = commerceOrderPayment;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceOrderPayment.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceOrderPayment.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceOrderPaymentId", getCommerceOrderPaymentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put("commercePaymentMethodId", getCommercePaymentMethodId());
		attributes.put("status", getStatus());
		attributes.put("content", getContent());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceOrderPaymentId = (Long)attributes.get(
				"commerceOrderPaymentId");

		if (commerceOrderPaymentId != null) {
			setCommerceOrderPaymentId(commerceOrderPaymentId);
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

		Long commerceOrderId = (Long)attributes.get("commerceOrderId");

		if (commerceOrderId != null) {
			setCommerceOrderId(commerceOrderId);
		}

		Long commercePaymentMethodId = (Long)attributes.get(
				"commercePaymentMethodId");

		if (commercePaymentMethodId != null) {
			setCommercePaymentMethodId(commercePaymentMethodId);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}
	}

	@Override
	public Object clone() {
		return new CommerceOrderPaymentWrapper((CommerceOrderPayment)_commerceOrderPayment.clone());
	}

	@Override
	public int compareTo(CommerceOrderPayment commerceOrderPayment) {
		return _commerceOrderPayment.compareTo(commerceOrderPayment);
	}

	/**
	* Returns the commerce order ID of this commerce order payment.
	*
	* @return the commerce order ID of this commerce order payment
	*/
	@Override
	public long getCommerceOrderId() {
		return _commerceOrderPayment.getCommerceOrderId();
	}

	/**
	* Returns the commerce order payment ID of this commerce order payment.
	*
	* @return the commerce order payment ID of this commerce order payment
	*/
	@Override
	public long getCommerceOrderPaymentId() {
		return _commerceOrderPayment.getCommerceOrderPaymentId();
	}

	/**
	* Returns the commerce payment method ID of this commerce order payment.
	*
	* @return the commerce payment method ID of this commerce order payment
	*/
	@Override
	public long getCommercePaymentMethodId() {
		return _commerceOrderPayment.getCommercePaymentMethodId();
	}

	/**
	* Returns the company ID of this commerce order payment.
	*
	* @return the company ID of this commerce order payment
	*/
	@Override
	public long getCompanyId() {
		return _commerceOrderPayment.getCompanyId();
	}

	/**
	* Returns the content of this commerce order payment.
	*
	* @return the content of this commerce order payment
	*/
	@Override
	public String getContent() {
		return _commerceOrderPayment.getContent();
	}

	/**
	* Returns the create date of this commerce order payment.
	*
	* @return the create date of this commerce order payment
	*/
	@Override
	public Date getCreateDate() {
		return _commerceOrderPayment.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceOrderPayment.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce order payment.
	*
	* @return the group ID of this commerce order payment
	*/
	@Override
	public long getGroupId() {
		return _commerceOrderPayment.getGroupId();
	}

	/**
	* Returns the modified date of this commerce order payment.
	*
	* @return the modified date of this commerce order payment
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceOrderPayment.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce order payment.
	*
	* @return the primary key of this commerce order payment
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceOrderPayment.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceOrderPayment.getPrimaryKeyObj();
	}

	/**
	* Returns the status of this commerce order payment.
	*
	* @return the status of this commerce order payment
	*/
	@Override
	public int getStatus() {
		return _commerceOrderPayment.getStatus();
	}

	/**
	* Returns the user ID of this commerce order payment.
	*
	* @return the user ID of this commerce order payment
	*/
	@Override
	public long getUserId() {
		return _commerceOrderPayment.getUserId();
	}

	/**
	* Returns the user name of this commerce order payment.
	*
	* @return the user name of this commerce order payment
	*/
	@Override
	public String getUserName() {
		return _commerceOrderPayment.getUserName();
	}

	/**
	* Returns the user uuid of this commerce order payment.
	*
	* @return the user uuid of this commerce order payment
	*/
	@Override
	public String getUserUuid() {
		return _commerceOrderPayment.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceOrderPayment.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceOrderPayment.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceOrderPayment.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceOrderPayment.isNew();
	}

	@Override
	public void persist() {
		_commerceOrderPayment.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceOrderPayment.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce order ID of this commerce order payment.
	*
	* @param commerceOrderId the commerce order ID of this commerce order payment
	*/
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderPayment.setCommerceOrderId(commerceOrderId);
	}

	/**
	* Sets the commerce order payment ID of this commerce order payment.
	*
	* @param commerceOrderPaymentId the commerce order payment ID of this commerce order payment
	*/
	@Override
	public void setCommerceOrderPaymentId(long commerceOrderPaymentId) {
		_commerceOrderPayment.setCommerceOrderPaymentId(commerceOrderPaymentId);
	}

	/**
	* Sets the commerce payment method ID of this commerce order payment.
	*
	* @param commercePaymentMethodId the commerce payment method ID of this commerce order payment
	*/
	@Override
	public void setCommercePaymentMethodId(long commercePaymentMethodId) {
		_commerceOrderPayment.setCommercePaymentMethodId(commercePaymentMethodId);
	}

	/**
	* Sets the company ID of this commerce order payment.
	*
	* @param companyId the company ID of this commerce order payment
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceOrderPayment.setCompanyId(companyId);
	}

	/**
	* Sets the content of this commerce order payment.
	*
	* @param content the content of this commerce order payment
	*/
	@Override
	public void setContent(String content) {
		_commerceOrderPayment.setContent(content);
	}

	/**
	* Sets the create date of this commerce order payment.
	*
	* @param createDate the create date of this commerce order payment
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceOrderPayment.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceOrderPayment.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceOrderPayment.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceOrderPayment.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce order payment.
	*
	* @param groupId the group ID of this commerce order payment
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceOrderPayment.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce order payment.
	*
	* @param modifiedDate the modified date of this commerce order payment
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceOrderPayment.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceOrderPayment.setNew(n);
	}

	/**
	* Sets the primary key of this commerce order payment.
	*
	* @param primaryKey the primary key of this commerce order payment
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceOrderPayment.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceOrderPayment.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the status of this commerce order payment.
	*
	* @param status the status of this commerce order payment
	*/
	@Override
	public void setStatus(int status) {
		_commerceOrderPayment.setStatus(status);
	}

	/**
	* Sets the user ID of this commerce order payment.
	*
	* @param userId the user ID of this commerce order payment
	*/
	@Override
	public void setUserId(long userId) {
		_commerceOrderPayment.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce order payment.
	*
	* @param userName the user name of this commerce order payment
	*/
	@Override
	public void setUserName(String userName) {
		_commerceOrderPayment.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce order payment.
	*
	* @param userUuid the user uuid of this commerce order payment
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceOrderPayment.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceOrderPayment> toCacheModel() {
		return _commerceOrderPayment.toCacheModel();
	}

	@Override
	public CommerceOrderPayment toEscapedModel() {
		return new CommerceOrderPaymentWrapper(_commerceOrderPayment.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceOrderPayment.toString();
	}

	@Override
	public CommerceOrderPayment toUnescapedModel() {
		return new CommerceOrderPaymentWrapper(_commerceOrderPayment.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceOrderPayment.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceOrderPaymentWrapper)) {
			return false;
		}

		CommerceOrderPaymentWrapper commerceOrderPaymentWrapper = (CommerceOrderPaymentWrapper)obj;

		if (Objects.equals(_commerceOrderPayment,
					commerceOrderPaymentWrapper._commerceOrderPayment)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceOrderPayment getWrappedModel() {
		return _commerceOrderPayment;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceOrderPayment.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceOrderPayment.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceOrderPayment.resetOriginalValues();
	}

	private final CommerceOrderPayment _commerceOrderPayment;
}