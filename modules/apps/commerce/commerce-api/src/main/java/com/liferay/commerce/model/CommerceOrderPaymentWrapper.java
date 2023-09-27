/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceOrderPayment}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPayment
 * @generated
 */
public class CommerceOrderPaymentWrapper
	extends BaseModelWrapper<CommerceOrderPayment>
	implements CommerceOrderPayment, ModelWrapper<CommerceOrderPayment> {

	public CommerceOrderPaymentWrapper(
		CommerceOrderPayment commerceOrderPayment) {

		super(commerceOrderPayment);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("commerceOrderPaymentId", getCommerceOrderPaymentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put(
			"commercePaymentMethodKey", getCommercePaymentMethodKey());
		attributes.put("content", getContent());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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

		String commercePaymentMethodKey = (String)attributes.get(
			"commercePaymentMethodKey");

		if (commercePaymentMethodKey != null) {
			setCommercePaymentMethodKey(commercePaymentMethodKey);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public CommerceOrderPayment cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the commerce order ID of this commerce order payment.
	 *
	 * @return the commerce order ID of this commerce order payment
	 */
	@Override
	public long getCommerceOrderId() {
		return model.getCommerceOrderId();
	}

	/**
	 * Returns the commerce order payment ID of this commerce order payment.
	 *
	 * @return the commerce order payment ID of this commerce order payment
	 */
	@Override
	public long getCommerceOrderPaymentId() {
		return model.getCommerceOrderPaymentId();
	}

	/**
	 * Returns the commerce payment method key of this commerce order payment.
	 *
	 * @return the commerce payment method key of this commerce order payment
	 */
	@Override
	public String getCommercePaymentMethodKey() {
		return model.getCommercePaymentMethodKey();
	}

	/**
	 * Returns the company ID of this commerce order payment.
	 *
	 * @return the company ID of this commerce order payment
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this commerce order payment.
	 *
	 * @return the content of this commerce order payment
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the create date of this commerce order payment.
	 *
	 * @return the create date of this commerce order payment
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this commerce order payment.
	 *
	 * @return the group ID of this commerce order payment
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce order payment.
	 *
	 * @return the modified date of this commerce order payment
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce order payment.
	 *
	 * @return the mvcc version of this commerce order payment
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce order payment.
	 *
	 * @return the primary key of this commerce order payment
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this commerce order payment.
	 *
	 * @return the status of this commerce order payment
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the user ID of this commerce order payment.
	 *
	 * @return the user ID of this commerce order payment
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce order payment.
	 *
	 * @return the user name of this commerce order payment
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce order payment.
	 *
	 * @return the user uuid of this commerce order payment
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
	 * Sets the commerce order ID of this commerce order payment.
	 *
	 * @param commerceOrderId the commerce order ID of this commerce order payment
	 */
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		model.setCommerceOrderId(commerceOrderId);
	}

	/**
	 * Sets the commerce order payment ID of this commerce order payment.
	 *
	 * @param commerceOrderPaymentId the commerce order payment ID of this commerce order payment
	 */
	@Override
	public void setCommerceOrderPaymentId(long commerceOrderPaymentId) {
		model.setCommerceOrderPaymentId(commerceOrderPaymentId);
	}

	/**
	 * Sets the commerce payment method key of this commerce order payment.
	 *
	 * @param commercePaymentMethodKey the commerce payment method key of this commerce order payment
	 */
	@Override
	public void setCommercePaymentMethodKey(String commercePaymentMethodKey) {
		model.setCommercePaymentMethodKey(commercePaymentMethodKey);
	}

	/**
	 * Sets the company ID of this commerce order payment.
	 *
	 * @param companyId the company ID of this commerce order payment
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this commerce order payment.
	 *
	 * @param content the content of this commerce order payment
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the create date of this commerce order payment.
	 *
	 * @param createDate the create date of this commerce order payment
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this commerce order payment.
	 *
	 * @param groupId the group ID of this commerce order payment
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce order payment.
	 *
	 * @param modifiedDate the modified date of this commerce order payment
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce order payment.
	 *
	 * @param mvccVersion the mvcc version of this commerce order payment
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce order payment.
	 *
	 * @param primaryKey the primary key of this commerce order payment
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this commerce order payment.
	 *
	 * @param status the status of this commerce order payment
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the user ID of this commerce order payment.
	 *
	 * @param userId the user ID of this commerce order payment
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce order payment.
	 *
	 * @param userName the user name of this commerce order payment
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce order payment.
	 *
	 * @param userUuid the user uuid of this commerce order payment
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CommerceOrderPaymentWrapper wrap(
		CommerceOrderPayment commerceOrderPayment) {

		return new CommerceOrderPaymentWrapper(commerceOrderPayment);
	}

}