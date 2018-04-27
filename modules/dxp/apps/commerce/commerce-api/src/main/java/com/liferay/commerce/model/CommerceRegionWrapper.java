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

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceRegion}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegion
 * @generated
 */
@ProviderType
public class CommerceRegionWrapper implements CommerceRegion,
	ModelWrapper<CommerceRegion> {
	public CommerceRegionWrapper(CommerceRegion commerceRegion) {
		_commerceRegion = commerceRegion;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceRegion.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceRegion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceRegionId", getCommerceRegionId());
		attributes.put("groupId", getGroupId());
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

	@Override
	public Object clone() {
		return new CommerceRegionWrapper((CommerceRegion)_commerceRegion.clone());
	}

	@Override
	public int compareTo(CommerceRegion commerceRegion) {
		return _commerceRegion.compareTo(commerceRegion);
	}

	/**
	* Returns the active of this commerce region.
	*
	* @return the active of this commerce region
	*/
	@Override
	public boolean getActive() {
		return _commerceRegion.getActive();
	}

	/**
	* Returns the code of this commerce region.
	*
	* @return the code of this commerce region
	*/
	@Override
	public String getCode() {
		return _commerceRegion.getCode();
	}

	@Override
	public CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegion.getCommerceCountry();
	}

	/**
	* Returns the commerce country ID of this commerce region.
	*
	* @return the commerce country ID of this commerce region
	*/
	@Override
	public long getCommerceCountryId() {
		return _commerceRegion.getCommerceCountryId();
	}

	/**
	* Returns the commerce region ID of this commerce region.
	*
	* @return the commerce region ID of this commerce region
	*/
	@Override
	public long getCommerceRegionId() {
		return _commerceRegion.getCommerceRegionId();
	}

	/**
	* Returns the company ID of this commerce region.
	*
	* @return the company ID of this commerce region
	*/
	@Override
	public long getCompanyId() {
		return _commerceRegion.getCompanyId();
	}

	/**
	* Returns the create date of this commerce region.
	*
	* @return the create date of this commerce region
	*/
	@Override
	public Date getCreateDate() {
		return _commerceRegion.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceRegion.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce region.
	*
	* @return the group ID of this commerce region
	*/
	@Override
	public long getGroupId() {
		return _commerceRegion.getGroupId();
	}

	/**
	* Returns the last publish date of this commerce region.
	*
	* @return the last publish date of this commerce region
	*/
	@Override
	public Date getLastPublishDate() {
		return _commerceRegion.getLastPublishDate();
	}

	/**
	* Returns the modified date of this commerce region.
	*
	* @return the modified date of this commerce region
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceRegion.getModifiedDate();
	}

	/**
	* Returns the name of this commerce region.
	*
	* @return the name of this commerce region
	*/
	@Override
	public String getName() {
		return _commerceRegion.getName();
	}

	/**
	* Returns the primary key of this commerce region.
	*
	* @return the primary key of this commerce region
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceRegion.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceRegion.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this commerce region.
	*
	* @return the priority of this commerce region
	*/
	@Override
	public double getPriority() {
		return _commerceRegion.getPriority();
	}

	/**
	* Returns the user ID of this commerce region.
	*
	* @return the user ID of this commerce region
	*/
	@Override
	public long getUserId() {
		return _commerceRegion.getUserId();
	}

	/**
	* Returns the user name of this commerce region.
	*
	* @return the user name of this commerce region
	*/
	@Override
	public String getUserName() {
		return _commerceRegion.getUserName();
	}

	/**
	* Returns the user uuid of this commerce region.
	*
	* @return the user uuid of this commerce region
	*/
	@Override
	public String getUserUuid() {
		return _commerceRegion.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce region.
	*
	* @return the uuid of this commerce region
	*/
	@Override
	public String getUuid() {
		return _commerceRegion.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceRegion.hashCode();
	}

	/**
	* Returns <code>true</code> if this commerce region is active.
	*
	* @return <code>true</code> if this commerce region is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _commerceRegion.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceRegion.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceRegion.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceRegion.isNew();
	}

	@Override
	public void persist() {
		_commerceRegion.persist();
	}

	/**
	* Sets whether this commerce region is active.
	*
	* @param active the active of this commerce region
	*/
	@Override
	public void setActive(boolean active) {
		_commerceRegion.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceRegion.setCachedModel(cachedModel);
	}

	/**
	* Sets the code of this commerce region.
	*
	* @param code the code of this commerce region
	*/
	@Override
	public void setCode(String code) {
		_commerceRegion.setCode(code);
	}

	/**
	* Sets the commerce country ID of this commerce region.
	*
	* @param commerceCountryId the commerce country ID of this commerce region
	*/
	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		_commerceRegion.setCommerceCountryId(commerceCountryId);
	}

	/**
	* Sets the commerce region ID of this commerce region.
	*
	* @param commerceRegionId the commerce region ID of this commerce region
	*/
	@Override
	public void setCommerceRegionId(long commerceRegionId) {
		_commerceRegion.setCommerceRegionId(commerceRegionId);
	}

	/**
	* Sets the company ID of this commerce region.
	*
	* @param companyId the company ID of this commerce region
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceRegion.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce region.
	*
	* @param createDate the create date of this commerce region
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceRegion.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceRegion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceRegion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceRegion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce region.
	*
	* @param groupId the group ID of this commerce region
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceRegion.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this commerce region.
	*
	* @param lastPublishDate the last publish date of this commerce region
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commerceRegion.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this commerce region.
	*
	* @param modifiedDate the modified date of this commerce region
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceRegion.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce region.
	*
	* @param name the name of this commerce region
	*/
	@Override
	public void setName(String name) {
		_commerceRegion.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceRegion.setNew(n);
	}

	/**
	* Sets the primary key of this commerce region.
	*
	* @param primaryKey the primary key of this commerce region
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceRegion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceRegion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce region.
	*
	* @param priority the priority of this commerce region
	*/
	@Override
	public void setPriority(double priority) {
		_commerceRegion.setPriority(priority);
	}

	/**
	* Sets the user ID of this commerce region.
	*
	* @param userId the user ID of this commerce region
	*/
	@Override
	public void setUserId(long userId) {
		_commerceRegion.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce region.
	*
	* @param userName the user name of this commerce region
	*/
	@Override
	public void setUserName(String userName) {
		_commerceRegion.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce region.
	*
	* @param userUuid the user uuid of this commerce region
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceRegion.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce region.
	*
	* @param uuid the uuid of this commerce region
	*/
	@Override
	public void setUuid(String uuid) {
		_commerceRegion.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceRegion> toCacheModel() {
		return _commerceRegion.toCacheModel();
	}

	@Override
	public CommerceRegion toEscapedModel() {
		return new CommerceRegionWrapper(_commerceRegion.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceRegion.toString();
	}

	@Override
	public CommerceRegion toUnescapedModel() {
		return new CommerceRegionWrapper(_commerceRegion.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceRegion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceRegionWrapper)) {
			return false;
		}

		CommerceRegionWrapper commerceRegionWrapper = (CommerceRegionWrapper)obj;

		if (Objects.equals(_commerceRegion,
					commerceRegionWrapper._commerceRegion)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceRegion.getStagedModelType();
	}

	@Override
	public CommerceRegion getWrappedModel() {
		return _commerceRegion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceRegion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceRegion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceRegion.resetOriginalValues();
	}

	private final CommerceRegion _commerceRegion;
}