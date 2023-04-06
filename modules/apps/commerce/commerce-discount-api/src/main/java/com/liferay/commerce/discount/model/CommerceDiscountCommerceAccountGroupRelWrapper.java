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

package com.liferay.commerce.discount.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceDiscountCommerceAccountGroupRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountCommerceAccountGroupRel
 * @generated
 */
public class CommerceDiscountCommerceAccountGroupRelWrapper
	extends BaseModelWrapper<CommerceDiscountCommerceAccountGroupRel>
	implements CommerceDiscountCommerceAccountGroupRel,
			   ModelWrapper<CommerceDiscountCommerceAccountGroupRel> {

	public CommerceDiscountCommerceAccountGroupRelWrapper(
		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel) {

		super(commerceDiscountCommerceAccountGroupRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"commerceDiscountCommerceAccountGroupRelId",
			getCommerceDiscountCommerceAccountGroupRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceDiscountId", getCommerceDiscountId());
		attributes.put("commerceAccountGroupId", getCommerceAccountGroupId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceDiscountCommerceAccountGroupRelId = (Long)attributes.get(
			"commerceDiscountCommerceAccountGroupRelId");

		if (commerceDiscountCommerceAccountGroupRelId != null) {
			setCommerceDiscountCommerceAccountGroupRelId(
				commerceDiscountCommerceAccountGroupRelId);
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

		Long commerceDiscountId = (Long)attributes.get("commerceDiscountId");

		if (commerceDiscountId != null) {
			setCommerceDiscountId(commerceDiscountId);
		}

		Long commerceAccountGroupId = (Long)attributes.get(
			"commerceAccountGroupId");

		if (commerceAccountGroupId != null) {
			setCommerceAccountGroupId(commerceAccountGroupId);
		}
	}

	@Override
	public CommerceDiscountCommerceAccountGroupRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public com.liferay.account.model.AccountGroup getAccountGroup()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAccountGroup();
	}

	/**
	 * Returns the commerce account group ID of this commerce discount commerce account group rel.
	 *
	 * @return the commerce account group ID of this commerce discount commerce account group rel
	 */
	@Override
	public long getCommerceAccountGroupId() {
		return model.getCommerceAccountGroupId();
	}

	@Override
	public CommerceDiscount getCommerceDiscount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceDiscount();
	}

	/**
	 * Returns the commerce discount commerce account group rel ID of this commerce discount commerce account group rel.
	 *
	 * @return the commerce discount commerce account group rel ID of this commerce discount commerce account group rel
	 */
	@Override
	public long getCommerceDiscountCommerceAccountGroupRelId() {
		return model.getCommerceDiscountCommerceAccountGroupRelId();
	}

	/**
	 * Returns the commerce discount ID of this commerce discount commerce account group rel.
	 *
	 * @return the commerce discount ID of this commerce discount commerce account group rel
	 */
	@Override
	public long getCommerceDiscountId() {
		return model.getCommerceDiscountId();
	}

	/**
	 * Returns the company ID of this commerce discount commerce account group rel.
	 *
	 * @return the company ID of this commerce discount commerce account group rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce discount commerce account group rel.
	 *
	 * @return the create date of this commerce discount commerce account group rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this commerce discount commerce account group rel.
	 *
	 * @return the modified date of this commerce discount commerce account group rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce discount commerce account group rel.
	 *
	 * @return the mvcc version of this commerce discount commerce account group rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce discount commerce account group rel.
	 *
	 * @return the primary key of this commerce discount commerce account group rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce discount commerce account group rel.
	 *
	 * @return the user ID of this commerce discount commerce account group rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce discount commerce account group rel.
	 *
	 * @return the user name of this commerce discount commerce account group rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce discount commerce account group rel.
	 *
	 * @return the user uuid of this commerce discount commerce account group rel
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
	 * Sets the commerce account group ID of this commerce discount commerce account group rel.
	 *
	 * @param commerceAccountGroupId the commerce account group ID of this commerce discount commerce account group rel
	 */
	@Override
	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		model.setCommerceAccountGroupId(commerceAccountGroupId);
	}

	/**
	 * Sets the commerce discount commerce account group rel ID of this commerce discount commerce account group rel.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the commerce discount commerce account group rel ID of this commerce discount commerce account group rel
	 */
	@Override
	public void setCommerceDiscountCommerceAccountGroupRelId(
		long commerceDiscountCommerceAccountGroupRelId) {

		model.setCommerceDiscountCommerceAccountGroupRelId(
			commerceDiscountCommerceAccountGroupRelId);
	}

	/**
	 * Sets the commerce discount ID of this commerce discount commerce account group rel.
	 *
	 * @param commerceDiscountId the commerce discount ID of this commerce discount commerce account group rel
	 */
	@Override
	public void setCommerceDiscountId(long commerceDiscountId) {
		model.setCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Sets the company ID of this commerce discount commerce account group rel.
	 *
	 * @param companyId the company ID of this commerce discount commerce account group rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce discount commerce account group rel.
	 *
	 * @param createDate the create date of this commerce discount commerce account group rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this commerce discount commerce account group rel.
	 *
	 * @param modifiedDate the modified date of this commerce discount commerce account group rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce discount commerce account group rel.
	 *
	 * @param mvccVersion the mvcc version of this commerce discount commerce account group rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce discount commerce account group rel.
	 *
	 * @param primaryKey the primary key of this commerce discount commerce account group rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce discount commerce account group rel.
	 *
	 * @param userId the user ID of this commerce discount commerce account group rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce discount commerce account group rel.
	 *
	 * @param userName the user name of this commerce discount commerce account group rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce discount commerce account group rel.
	 *
	 * @param userUuid the user uuid of this commerce discount commerce account group rel
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
	protected CommerceDiscountCommerceAccountGroupRelWrapper wrap(
		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel) {

		return new CommerceDiscountCommerceAccountGroupRelWrapper(
			commerceDiscountCommerceAccountGroupRel);
	}

}