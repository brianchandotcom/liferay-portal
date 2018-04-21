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
 * This class is a wrapper for {@link CommerceWarehouse}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouse
 * @generated
 */
@ProviderType
public class CommerceWarehouseWrapper implements CommerceWarehouse,
	ModelWrapper<CommerceWarehouse> {
	public CommerceWarehouseWrapper(CommerceWarehouse commerceWarehouse) {
		_commerceWarehouse = commerceWarehouse;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceWarehouse.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceWarehouse.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceWarehouseId", getCommerceWarehouseId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("active", isActive());
		attributes.put("street1", getStreet1());
		attributes.put("street2", getStreet2());
		attributes.put("street3", getStreet3());
		attributes.put("city", getCity());
		attributes.put("zip", getZip());
		attributes.put("commerceRegionId", getCommerceRegionId());
		attributes.put("commerceCountryId", getCommerceCountryId());
		attributes.put("latitude", getLatitude());
		attributes.put("longitude", getLongitude());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceWarehouseId = (Long)attributes.get("commerceWarehouseId");

		if (commerceWarehouseId != null) {
			setCommerceWarehouseId(commerceWarehouseId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String street1 = (String)attributes.get("street1");

		if (street1 != null) {
			setStreet1(street1);
		}

		String street2 = (String)attributes.get("street2");

		if (street2 != null) {
			setStreet2(street2);
		}

		String street3 = (String)attributes.get("street3");

		if (street3 != null) {
			setStreet3(street3);
		}

		String city = (String)attributes.get("city");

		if (city != null) {
			setCity(city);
		}

		String zip = (String)attributes.get("zip");

		if (zip != null) {
			setZip(zip);
		}

		Long commerceRegionId = (Long)attributes.get("commerceRegionId");

		if (commerceRegionId != null) {
			setCommerceRegionId(commerceRegionId);
		}

		Long commerceCountryId = (Long)attributes.get("commerceCountryId");

		if (commerceCountryId != null) {
			setCommerceCountryId(commerceCountryId);
		}

		Double latitude = (Double)attributes.get("latitude");

		if (latitude != null) {
			setLatitude(latitude);
		}

		Double longitude = (Double)attributes.get("longitude");

		if (longitude != null) {
			setLongitude(longitude);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceWarehouseWrapper((CommerceWarehouse)_commerceWarehouse.clone());
	}

	@Override
	public int compareTo(CommerceWarehouse commerceWarehouse) {
		return _commerceWarehouse.compareTo(commerceWarehouse);
	}

	/**
	* Returns the active of this commerce warehouse.
	*
	* @return the active of this commerce warehouse
	*/
	@Override
	public boolean getActive() {
		return _commerceWarehouse.getActive();
	}

	/**
	* Returns the city of this commerce warehouse.
	*
	* @return the city of this commerce warehouse
	*/
	@Override
	public java.lang.String getCity() {
		return _commerceWarehouse.getCity();
	}

	@Override
	public CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouse.getCommerceCountry();
	}

	/**
	* Returns the commerce country ID of this commerce warehouse.
	*
	* @return the commerce country ID of this commerce warehouse
	*/
	@Override
	public long getCommerceCountryId() {
		return _commerceWarehouse.getCommerceCountryId();
	}

	@Override
	public CommerceRegion getCommerceRegion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouse.getCommerceRegion();
	}

	/**
	* Returns the commerce region ID of this commerce warehouse.
	*
	* @return the commerce region ID of this commerce warehouse
	*/
	@Override
	public long getCommerceRegionId() {
		return _commerceWarehouse.getCommerceRegionId();
	}

	/**
	* Returns the commerce warehouse ID of this commerce warehouse.
	*
	* @return the commerce warehouse ID of this commerce warehouse
	*/
	@Override
	public long getCommerceWarehouseId() {
		return _commerceWarehouse.getCommerceWarehouseId();
	}

	@Override
	public java.util.List<CommerceWarehouseItem> getCommerceWarehouseItems() {
		return _commerceWarehouse.getCommerceWarehouseItems();
	}

	/**
	* Returns the company ID of this commerce warehouse.
	*
	* @return the company ID of this commerce warehouse
	*/
	@Override
	public long getCompanyId() {
		return _commerceWarehouse.getCompanyId();
	}

	/**
	* Returns the create date of this commerce warehouse.
	*
	* @return the create date of this commerce warehouse
	*/
	@Override
	public Date getCreateDate() {
		return _commerceWarehouse.getCreateDate();
	}

	/**
	* Returns the description of this commerce warehouse.
	*
	* @return the description of this commerce warehouse
	*/
	@Override
	public java.lang.String getDescription() {
		return _commerceWarehouse.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceWarehouse.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce warehouse.
	*
	* @return the group ID of this commerce warehouse
	*/
	@Override
	public long getGroupId() {
		return _commerceWarehouse.getGroupId();
	}

	/**
	* Returns the latitude of this commerce warehouse.
	*
	* @return the latitude of this commerce warehouse
	*/
	@Override
	public double getLatitude() {
		return _commerceWarehouse.getLatitude();
	}

	/**
	* Returns the longitude of this commerce warehouse.
	*
	* @return the longitude of this commerce warehouse
	*/
	@Override
	public double getLongitude() {
		return _commerceWarehouse.getLongitude();
	}

	/**
	* Returns the modified date of this commerce warehouse.
	*
	* @return the modified date of this commerce warehouse
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceWarehouse.getModifiedDate();
	}

	/**
	* Returns the name of this commerce warehouse.
	*
	* @return the name of this commerce warehouse
	*/
	@Override
	public java.lang.String getName() {
		return _commerceWarehouse.getName();
	}

	/**
	* Returns the primary key of this commerce warehouse.
	*
	* @return the primary key of this commerce warehouse
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceWarehouse.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceWarehouse.getPrimaryKeyObj();
	}

	/**
	* Returns the street1 of this commerce warehouse.
	*
	* @return the street1 of this commerce warehouse
	*/
	@Override
	public java.lang.String getStreet1() {
		return _commerceWarehouse.getStreet1();
	}

	/**
	* Returns the street2 of this commerce warehouse.
	*
	* @return the street2 of this commerce warehouse
	*/
	@Override
	public java.lang.String getStreet2() {
		return _commerceWarehouse.getStreet2();
	}

	/**
	* Returns the street3 of this commerce warehouse.
	*
	* @return the street3 of this commerce warehouse
	*/
	@Override
	public java.lang.String getStreet3() {
		return _commerceWarehouse.getStreet3();
	}

	/**
	* Returns the user ID of this commerce warehouse.
	*
	* @return the user ID of this commerce warehouse
	*/
	@Override
	public long getUserId() {
		return _commerceWarehouse.getUserId();
	}

	/**
	* Returns the user name of this commerce warehouse.
	*
	* @return the user name of this commerce warehouse
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceWarehouse.getUserName();
	}

	/**
	* Returns the user uuid of this commerce warehouse.
	*
	* @return the user uuid of this commerce warehouse
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceWarehouse.getUserUuid();
	}

	/**
	* Returns the zip of this commerce warehouse.
	*
	* @return the zip of this commerce warehouse
	*/
	@Override
	public java.lang.String getZip() {
		return _commerceWarehouse.getZip();
	}

	@Override
	public int hashCode() {
		return _commerceWarehouse.hashCode();
	}

	/**
	* Returns <code>true</code> if this commerce warehouse is active.
	*
	* @return <code>true</code> if this commerce warehouse is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _commerceWarehouse.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceWarehouse.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceWarehouse.isEscapedModel();
	}

	@Override
	public boolean isGeolocated() {
		return _commerceWarehouse.isGeolocated();
	}

	@Override
	public boolean isNew() {
		return _commerceWarehouse.isNew();
	}

	@Override
	public void persist() {
		_commerceWarehouse.persist();
	}

	/**
	* Sets whether this commerce warehouse is active.
	*
	* @param active the active of this commerce warehouse
	*/
	@Override
	public void setActive(boolean active) {
		_commerceWarehouse.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceWarehouse.setCachedModel(cachedModel);
	}

	/**
	* Sets the city of this commerce warehouse.
	*
	* @param city the city of this commerce warehouse
	*/
	@Override
	public void setCity(java.lang.String city) {
		_commerceWarehouse.setCity(city);
	}

	/**
	* Sets the commerce country ID of this commerce warehouse.
	*
	* @param commerceCountryId the commerce country ID of this commerce warehouse
	*/
	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		_commerceWarehouse.setCommerceCountryId(commerceCountryId);
	}

	/**
	* Sets the commerce region ID of this commerce warehouse.
	*
	* @param commerceRegionId the commerce region ID of this commerce warehouse
	*/
	@Override
	public void setCommerceRegionId(long commerceRegionId) {
		_commerceWarehouse.setCommerceRegionId(commerceRegionId);
	}

	/**
	* Sets the commerce warehouse ID of this commerce warehouse.
	*
	* @param commerceWarehouseId the commerce warehouse ID of this commerce warehouse
	*/
	@Override
	public void setCommerceWarehouseId(long commerceWarehouseId) {
		_commerceWarehouse.setCommerceWarehouseId(commerceWarehouseId);
	}

	/**
	* Sets the company ID of this commerce warehouse.
	*
	* @param companyId the company ID of this commerce warehouse
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceWarehouse.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce warehouse.
	*
	* @param createDate the create date of this commerce warehouse
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceWarehouse.setCreateDate(createDate);
	}

	/**
	* Sets the description of this commerce warehouse.
	*
	* @param description the description of this commerce warehouse
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_commerceWarehouse.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceWarehouse.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceWarehouse.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceWarehouse.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce warehouse.
	*
	* @param groupId the group ID of this commerce warehouse
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceWarehouse.setGroupId(groupId);
	}

	/**
	* Sets the latitude of this commerce warehouse.
	*
	* @param latitude the latitude of this commerce warehouse
	*/
	@Override
	public void setLatitude(double latitude) {
		_commerceWarehouse.setLatitude(latitude);
	}

	/**
	* Sets the longitude of this commerce warehouse.
	*
	* @param longitude the longitude of this commerce warehouse
	*/
	@Override
	public void setLongitude(double longitude) {
		_commerceWarehouse.setLongitude(longitude);
	}

	/**
	* Sets the modified date of this commerce warehouse.
	*
	* @param modifiedDate the modified date of this commerce warehouse
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceWarehouse.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce warehouse.
	*
	* @param name the name of this commerce warehouse
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceWarehouse.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceWarehouse.setNew(n);
	}

	/**
	* Sets the primary key of this commerce warehouse.
	*
	* @param primaryKey the primary key of this commerce warehouse
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceWarehouse.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceWarehouse.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the street1 of this commerce warehouse.
	*
	* @param street1 the street1 of this commerce warehouse
	*/
	@Override
	public void setStreet1(java.lang.String street1) {
		_commerceWarehouse.setStreet1(street1);
	}

	/**
	* Sets the street2 of this commerce warehouse.
	*
	* @param street2 the street2 of this commerce warehouse
	*/
	@Override
	public void setStreet2(java.lang.String street2) {
		_commerceWarehouse.setStreet2(street2);
	}

	/**
	* Sets the street3 of this commerce warehouse.
	*
	* @param street3 the street3 of this commerce warehouse
	*/
	@Override
	public void setStreet3(java.lang.String street3) {
		_commerceWarehouse.setStreet3(street3);
	}

	/**
	* Sets the user ID of this commerce warehouse.
	*
	* @param userId the user ID of this commerce warehouse
	*/
	@Override
	public void setUserId(long userId) {
		_commerceWarehouse.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce warehouse.
	*
	* @param userName the user name of this commerce warehouse
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceWarehouse.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce warehouse.
	*
	* @param userUuid the user uuid of this commerce warehouse
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceWarehouse.setUserUuid(userUuid);
	}

	/**
	* Sets the zip of this commerce warehouse.
	*
	* @param zip the zip of this commerce warehouse
	*/
	@Override
	public void setZip(java.lang.String zip) {
		_commerceWarehouse.setZip(zip);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceWarehouse> toCacheModel() {
		return _commerceWarehouse.toCacheModel();
	}

	@Override
	public CommerceWarehouse toEscapedModel() {
		return new CommerceWarehouseWrapper(_commerceWarehouse.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceWarehouse.toString();
	}

	@Override
	public CommerceWarehouse toUnescapedModel() {
		return new CommerceWarehouseWrapper(_commerceWarehouse.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceWarehouse.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceWarehouseWrapper)) {
			return false;
		}

		CommerceWarehouseWrapper commerceWarehouseWrapper = (CommerceWarehouseWrapper)obj;

		if (Objects.equals(_commerceWarehouse,
					commerceWarehouseWrapper._commerceWarehouse)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceWarehouse getWrappedModel() {
		return _commerceWarehouse;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceWarehouse.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceWarehouse.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceWarehouse.resetOriginalValues();
	}

	private final CommerceWarehouse _commerceWarehouse;
}