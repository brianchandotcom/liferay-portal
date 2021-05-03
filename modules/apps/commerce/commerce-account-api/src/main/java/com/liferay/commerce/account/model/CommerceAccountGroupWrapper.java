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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceAccountGroup}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountGroup
 * @generated
 */
public class CommerceAccountGroupWrapper
	extends BaseModelWrapper<CommerceAccountGroup>
	implements CommerceAccountGroup, ModelWrapper<CommerceAccountGroup> {

	public CommerceAccountGroupWrapper(
		CommerceAccountGroup commerceAccountGroup) {

		super(commerceAccountGroup);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commerceAccountGroupId", getCommerceAccountGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("system", isSystem());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceAccountGroupId = (Long)attributes.get(
			"commerceAccountGroupId");

		if (commerceAccountGroupId != null) {
			setCommerceAccountGroupId(commerceAccountGroupId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean system = (Boolean)attributes.get("system");

		if (system != null) {
			setSystem(system);
		}
	}

	/**
	 * Returns the commerce account group ID of this commerce account group.
	 *
	 * @return the commerce account group ID of this commerce account group
	 */
	@Override
	public long getCommerceAccountGroupId() {
		return model.getCommerceAccountGroupId();
	}

	/**
	 * Returns the company ID of this commerce account group.
	 *
	 * @return the company ID of this commerce account group
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce account group.
	 *
	 * @return the create date of this commerce account group
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return model.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this commerce account group.
	 *
	 * @return the external reference code of this commerce account group
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the modified date of this commerce account group.
	 *
	 * @return the modified date of this commerce account group
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce account group.
	 *
	 * @return the name of this commerce account group
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this commerce account group.
	 *
	 * @return the primary key of this commerce account group
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the system of this commerce account group.
	 *
	 * @return the system of this commerce account group
	 */
	@Override
	public boolean getSystem() {
		return model.getSystem();
	}

	/**
	 * Returns the type of this commerce account group.
	 *
	 * @return the type of this commerce account group
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this commerce account group.
	 *
	 * @return the user ID of this commerce account group
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce account group.
	 *
	 * @return the user name of this commerce account group
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce account group.
	 *
	 * @return the user uuid of this commerce account group
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce account group is system.
	 *
	 * @return <code>true</code> if this commerce account group is system; <code>false</code> otherwise
	 */
	@Override
	public boolean isSystem() {
		return model.isSystem();
	}

	/**
	 * Sets the commerce account group ID of this commerce account group.
	 *
	 * @param commerceAccountGroupId the commerce account group ID of this commerce account group
	 */
	@Override
	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		model.setCommerceAccountGroupId(commerceAccountGroupId);
	}

	/**
	 * Sets the company ID of this commerce account group.
	 *
	 * @param companyId the company ID of this commerce account group
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce account group.
	 *
	 * @param createDate the create date of this commerce account group
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external reference code of this commerce account group.
	 *
	 * @param externalReferenceCode the external reference code of this commerce account group
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the modified date of this commerce account group.
	 *
	 * @param modifiedDate the modified date of this commerce account group
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce account group.
	 *
	 * @param name the name of this commerce account group
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this commerce account group.
	 *
	 * @param primaryKey the primary key of this commerce account group
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this commerce account group is system.
	 *
	 * @param system the system of this commerce account group
	 */
	@Override
	public void setSystem(boolean system) {
		model.setSystem(system);
	}

	/**
	 * Sets the type of this commerce account group.
	 *
	 * @param type the type of this commerce account group
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this commerce account group.
	 *
	 * @param userId the user ID of this commerce account group
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce account group.
	 *
	 * @param userName the user name of this commerce account group
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce account group.
	 *
	 * @param userUuid the user uuid of this commerce account group
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceAccountGroupWrapper wrap(
		CommerceAccountGroup commerceAccountGroup) {

		return new CommerceAccountGroupWrapper(commerceAccountGroup);
	}

}