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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceRegion}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegion
 * @generated
 */
public class CommerceRegionWrapper
	extends BaseModelWrapper<CommerceRegion>
	implements CommerceRegion, ModelWrapper<CommerceRegion> {

	public CommerceRegionWrapper(CommerceRegion commerceRegion) {
		super(commerceRegion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceRegionId", getCommerceRegionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceCountryId", getCommerceCountryId());
		attributes.put("name", getName());
		attributes.put("code", getCode());
		attributes.put("priority", getPriority());
		attributes.put("active", isActive());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceRegionId = (Long)attributes.get("commerceRegionId");

		if (commerceRegionId != null) {
			setCommerceRegionId(commerceRegionId);
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

		Long commerceCountryId = (Long)attributes.get("commerceCountryId");

		if (commerceCountryId != null) {
			setCommerceCountryId(commerceCountryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String code = (String)attributes.get("code");

		if (code != null) {
			setCode(code);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	 * Returns the active of this commerce region.
	 *
	 * @return the active of this commerce region
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the code of this commerce region.
	 *
	 * @return the code of this commerce region
	 */
	@Override
	public String getCode() {
		return model.getCode();
	}

	@Override
	public CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceCountry();
	}

	/**
	 * Returns the commerce country ID of this commerce region.
	 *
	 * @return the commerce country ID of this commerce region
	 */
	@Override
	public long getCommerceCountryId() {
		return model.getCommerceCountryId();
	}

	/**
	 * Returns the commerce region ID of this commerce region.
	 *
	 * @return the commerce region ID of this commerce region
	 */
	@Override
	public long getCommerceRegionId() {
		return model.getCommerceRegionId();
	}

	/**
	 * Returns the company ID of this commerce region.
	 *
	 * @return the company ID of this commerce region
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce region.
	 *
	 * @return the create date of this commerce region
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the last publish date of this commerce region.
	 *
	 * @return the last publish date of this commerce region
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce region.
	 *
	 * @return the modified date of this commerce region
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce region.
	 *
	 * @return the name of this commerce region
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this commerce region.
	 *
	 * @return the primary key of this commerce region
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this commerce region.
	 *
	 * @return the priority of this commerce region
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the user ID of this commerce region.
	 *
	 * @return the user ID of this commerce region
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce region.
	 *
	 * @return the user name of this commerce region
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce region.
	 *
	 * @return the user uuid of this commerce region
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce region.
	 *
	 * @return the uuid of this commerce region
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce region is active.
	 *
	 * @return <code>true</code> if this commerce region is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this commerce region is active.
	 *
	 * @param active the active of this commerce region
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the code of this commerce region.
	 *
	 * @param code the code of this commerce region
	 */
	@Override
	public void setCode(String code) {
		model.setCode(code);
	}

	/**
	 * Sets the commerce country ID of this commerce region.
	 *
	 * @param commerceCountryId the commerce country ID of this commerce region
	 */
	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		model.setCommerceCountryId(commerceCountryId);
	}

	/**
	 * Sets the commerce region ID of this commerce region.
	 *
	 * @param commerceRegionId the commerce region ID of this commerce region
	 */
	@Override
	public void setCommerceRegionId(long commerceRegionId) {
		model.setCommerceRegionId(commerceRegionId);
	}

	/**
	 * Sets the company ID of this commerce region.
	 *
	 * @param companyId the company ID of this commerce region
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce region.
	 *
	 * @param createDate the create date of this commerce region
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the last publish date of this commerce region.
	 *
	 * @param lastPublishDate the last publish date of this commerce region
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce region.
	 *
	 * @param modifiedDate the modified date of this commerce region
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce region.
	 *
	 * @param name the name of this commerce region
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this commerce region.
	 *
	 * @param primaryKey the primary key of this commerce region
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this commerce region.
	 *
	 * @param priority the priority of this commerce region
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the user ID of this commerce region.
	 *
	 * @param userId the user ID of this commerce region
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce region.
	 *
	 * @param userName the user name of this commerce region
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce region.
	 *
	 * @param userUuid the user uuid of this commerce region
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce region.
	 *
	 * @param uuid the uuid of this commerce region
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CommerceRegionWrapper wrap(CommerceRegion commerceRegion) {
		return new CommerceRegionWrapper(commerceRegion);
	}

}