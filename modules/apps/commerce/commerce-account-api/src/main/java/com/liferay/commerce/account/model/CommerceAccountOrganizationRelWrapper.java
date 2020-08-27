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
 * This class is a wrapper for {@link CommerceAccountOrganizationRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountOrganizationRel
 * @generated
 */
public class CommerceAccountOrganizationRelWrapper
	extends BaseModelWrapper<CommerceAccountOrganizationRel>
	implements CommerceAccountOrganizationRel,
			   ModelWrapper<CommerceAccountOrganizationRel> {

	public CommerceAccountOrganizationRelWrapper(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		super(commerceAccountOrganizationRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceAccountId", getCommerceAccountId());
		attributes.put("organizationId", getOrganizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceAccountId = (Long)attributes.get("commerceAccountId");

		if (commerceAccountId != null) {
			setCommerceAccountId(commerceAccountId);
		}

		Long organizationId = (Long)attributes.get("organizationId");

		if (organizationId != null) {
			setOrganizationId(organizationId);
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
	}

	/**
	 * Returns the commerce account ID of this commerce account organization rel.
	 *
	 * @return the commerce account ID of this commerce account organization rel
	 */
	@Override
	public long getCommerceAccountId() {
		return model.getCommerceAccountId();
	}

	/**
	 * Returns the company ID of this commerce account organization rel.
	 *
	 * @return the company ID of this commerce account organization rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce account organization rel.
	 *
	 * @return the create date of this commerce account organization rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this commerce account organization rel.
	 *
	 * @return the modified date of this commerce account organization rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	@Override
	public com.liferay.portal.kernel.model.Organization getOrganization()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getOrganization();
	}

	/**
	 * Returns the organization ID of this commerce account organization rel.
	 *
	 * @return the organization ID of this commerce account organization rel
	 */
	@Override
	public long getOrganizationId() {
		return model.getOrganizationId();
	}

	/**
	 * Returns the primary key of this commerce account organization rel.
	 *
	 * @return the primary key of this commerce account organization rel
	 */
	@Override
	public com.liferay.commerce.account.service.persistence.
		CommerceAccountOrganizationRelPK getPrimaryKey() {

		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce account organization rel.
	 *
	 * @return the user ID of this commerce account organization rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce account organization rel.
	 *
	 * @return the user name of this commerce account organization rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce account organization rel.
	 *
	 * @return the user uuid of this commerce account organization rel
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
	 * Sets the commerce account ID of this commerce account organization rel.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce account organization rel
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		model.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the company ID of this commerce account organization rel.
	 *
	 * @param companyId the company ID of this commerce account organization rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce account organization rel.
	 *
	 * @param createDate the create date of this commerce account organization rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this commerce account organization rel.
	 *
	 * @param modifiedDate the modified date of this commerce account organization rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the organization ID of this commerce account organization rel.
	 *
	 * @param organizationId the organization ID of this commerce account organization rel
	 */
	@Override
	public void setOrganizationId(long organizationId) {
		model.setOrganizationId(organizationId);
	}

	/**
	 * Sets the primary key of this commerce account organization rel.
	 *
	 * @param primaryKey the primary key of this commerce account organization rel
	 */
	@Override
	public void setPrimaryKey(
		com.liferay.commerce.account.service.persistence.
			CommerceAccountOrganizationRelPK primaryKey) {

		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce account organization rel.
	 *
	 * @param userId the user ID of this commerce account organization rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce account organization rel.
	 *
	 * @param userName the user name of this commerce account organization rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce account organization rel.
	 *
	 * @param userUuid the user uuid of this commerce account organization rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceAccountOrganizationRelWrapper wrap(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		return new CommerceAccountOrganizationRelWrapper(
			commerceAccountOrganizationRel);
	}

}