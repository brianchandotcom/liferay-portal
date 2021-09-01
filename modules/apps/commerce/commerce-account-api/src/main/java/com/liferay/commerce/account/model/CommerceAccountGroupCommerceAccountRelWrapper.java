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

package com.liferay.commerce.account.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceAccountGroupCommerceAccountRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountGroupCommerceAccountRel
 * @generated
 */
public class CommerceAccountGroupCommerceAccountRelWrapper
	extends BaseModelWrapper<CommerceAccountGroupCommerceAccountRel>
	implements CommerceAccountGroupCommerceAccountRel,
			   ModelWrapper<CommerceAccountGroupCommerceAccountRel> {

	public CommerceAccountGroupCommerceAccountRelWrapper(
		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel) {

		super(commerceAccountGroupCommerceAccountRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put(
			"commerceAccountGroupCommerceAccountRelId",
			getCommerceAccountGroupCommerceAccountRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceAccountGroupId", getCommerceAccountGroupId());
		attributes.put("commerceAccountId", getCommerceAccountId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceAccountGroupCommerceAccountRelId = (Long)attributes.get(
			"commerceAccountGroupCommerceAccountRelId");

		if (commerceAccountGroupCommerceAccountRelId != null) {
			setCommerceAccountGroupCommerceAccountRelId(
				commerceAccountGroupCommerceAccountRelId);
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

		Long commerceAccountGroupId = (Long)attributes.get(
			"commerceAccountGroupId");

		if (commerceAccountGroupId != null) {
			setCommerceAccountGroupId(commerceAccountGroupId);
		}

		Long commerceAccountId = (Long)attributes.get("commerceAccountId");

		if (commerceAccountId != null) {
			setCommerceAccountId(commerceAccountId);
		}
	}

	@Override
	public CommerceAccountGroupCommerceAccountRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public CommerceAccount getCommerceAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceAccount();
	}

	/**
	 * Returns the commerce account group commerce account rel ID of this commerce account group commerce account rel.
	 *
	 * @return the commerce account group commerce account rel ID of this commerce account group commerce account rel
	 */
	@Override
	public long getCommerceAccountGroupCommerceAccountRelId() {
		return model.getCommerceAccountGroupCommerceAccountRelId();
	}

	/**
	 * Returns the commerce account group ID of this commerce account group commerce account rel.
	 *
	 * @return the commerce account group ID of this commerce account group commerce account rel
	 */
	@Override
	public long getCommerceAccountGroupId() {
		return model.getCommerceAccountGroupId();
	}

	/**
	 * Returns the commerce account ID of this commerce account group commerce account rel.
	 *
	 * @return the commerce account ID of this commerce account group commerce account rel
	 */
	@Override
	public long getCommerceAccountId() {
		return model.getCommerceAccountId();
	}

	/**
	 * Returns the company ID of this commerce account group commerce account rel.
	 *
	 * @return the company ID of this commerce account group commerce account rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce account group commerce account rel.
	 *
	 * @return the create date of this commerce account group commerce account rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the external reference code of this commerce account group commerce account rel.
	 *
	 * @return the external reference code of this commerce account group commerce account rel
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the modified date of this commerce account group commerce account rel.
	 *
	 * @return the modified date of this commerce account group commerce account rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce account group commerce account rel.
	 *
	 * @return the primary key of this commerce account group commerce account rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce account group commerce account rel.
	 *
	 * @return the user ID of this commerce account group commerce account rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce account group commerce account rel.
	 *
	 * @return the user name of this commerce account group commerce account rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce account group commerce account rel.
	 *
	 * @return the user uuid of this commerce account group commerce account rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Sets the commerce account group commerce account rel ID of this commerce account group commerce account rel.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the commerce account group commerce account rel ID of this commerce account group commerce account rel
	 */
	@Override
	public void setCommerceAccountGroupCommerceAccountRelId(
		long commerceAccountGroupCommerceAccountRelId) {

		model.setCommerceAccountGroupCommerceAccountRelId(
			commerceAccountGroupCommerceAccountRelId);
	}

	/**
	 * Sets the commerce account group ID of this commerce account group commerce account rel.
	 *
	 * @param commerceAccountGroupId the commerce account group ID of this commerce account group commerce account rel
	 */
	@Override
	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		model.setCommerceAccountGroupId(commerceAccountGroupId);
	}

	/**
	 * Sets the commerce account ID of this commerce account group commerce account rel.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce account group commerce account rel
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		model.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the company ID of this commerce account group commerce account rel.
	 *
	 * @param companyId the company ID of this commerce account group commerce account rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce account group commerce account rel.
	 *
	 * @param createDate the create date of this commerce account group commerce account rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external reference code of this commerce account group commerce account rel.
	 *
	 * @param externalReferenceCode the external reference code of this commerce account group commerce account rel
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the modified date of this commerce account group commerce account rel.
	 *
	 * @param modifiedDate the modified date of this commerce account group commerce account rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this commerce account group commerce account rel.
	 *
	 * @param primaryKey the primary key of this commerce account group commerce account rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce account group commerce account rel.
	 *
	 * @param userId the user ID of this commerce account group commerce account rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce account group commerce account rel.
	 *
	 * @param userName the user name of this commerce account group commerce account rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce account group commerce account rel.
	 *
	 * @param userUuid the user uuid of this commerce account group commerce account rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceAccountGroupCommerceAccountRelWrapper wrap(
		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel) {

		return new CommerceAccountGroupCommerceAccountRelWrapper(
			commerceAccountGroupCommerceAccountRel);
	}

}