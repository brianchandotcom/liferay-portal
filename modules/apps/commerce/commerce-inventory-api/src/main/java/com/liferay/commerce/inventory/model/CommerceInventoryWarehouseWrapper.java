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

package com.liferay.commerce.inventory.model;

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
 * This class is a wrapper for {@link CommerceInventoryWarehouse}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouse
 * @generated
 */
public class CommerceInventoryWarehouseWrapper
	implements CommerceInventoryWarehouse,
			   ModelWrapper<CommerceInventoryWarehouse> {

	public CommerceInventoryWarehouseWrapper(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		_commerceInventoryWarehouse = commerceInventoryWarehouse;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceInventoryWarehouse.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceInventoryWarehouse.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put(
			"commerceInventoryWarehouseId", getCommerceInventoryWarehouseId());
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
		attributes.put("commerceRegionCode", getCommerceRegionCode());
		attributes.put(
			"countryTwoLettersISOCode", getCountryTwoLettersISOCode());
		attributes.put("latitude", getLatitude());
		attributes.put("longitude", getLongitude());
		attributes.put("type", getType());

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

		Long commerceInventoryWarehouseId = (Long)attributes.get(
			"commerceInventoryWarehouseId");

		if (commerceInventoryWarehouseId != null) {
			setCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
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

		String commerceRegionCode = (String)attributes.get(
			"commerceRegionCode");

		if (commerceRegionCode != null) {
			setCommerceRegionCode(commerceRegionCode);
		}

		String countryTwoLettersISOCode = (String)attributes.get(
			"countryTwoLettersISOCode");

		if (countryTwoLettersISOCode != null) {
			setCountryTwoLettersISOCode(countryTwoLettersISOCode);
		}

		Double latitude = (Double)attributes.get("latitude");

		if (latitude != null) {
			setLatitude(latitude);
		}

		Double longitude = (Double)attributes.get("longitude");

		if (longitude != null) {
			setLongitude(longitude);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public Object clone() {
		return new CommerceInventoryWarehouseWrapper(
			(CommerceInventoryWarehouse)_commerceInventoryWarehouse.clone());
	}

	@Override
	public int compareTo(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		return _commerceInventoryWarehouse.compareTo(
			commerceInventoryWarehouse);
	}

	/**
	 * Returns the active of this commerce inventory warehouse.
	 *
	 * @return the active of this commerce inventory warehouse
	 */
	@Override
	public boolean getActive() {
		return _commerceInventoryWarehouse.getActive();
	}

	/**
	 * Returns the city of this commerce inventory warehouse.
	 *
	 * @return the city of this commerce inventory warehouse
	 */
	@Override
	public String getCity() {
		return _commerceInventoryWarehouse.getCity();
	}

	/**
	 * Returns the commerce inventory warehouse ID of this commerce inventory warehouse.
	 *
	 * @return the commerce inventory warehouse ID of this commerce inventory warehouse
	 */
	@Override
	public long getCommerceInventoryWarehouseId() {
		return _commerceInventoryWarehouse.getCommerceInventoryWarehouseId();
	}

	@Override
	public java.util.List<CommerceInventoryWarehouseItem>
		getCommerceInventoryWarehouseItems() {

		return _commerceInventoryWarehouse.getCommerceInventoryWarehouseItems();
	}

	/**
	 * Returns the commerce region code of this commerce inventory warehouse.
	 *
	 * @return the commerce region code of this commerce inventory warehouse
	 */
	@Override
	public String getCommerceRegionCode() {
		return _commerceInventoryWarehouse.getCommerceRegionCode();
	}

	/**
	 * Returns the company ID of this commerce inventory warehouse.
	 *
	 * @return the company ID of this commerce inventory warehouse
	 */
	@Override
	public long getCompanyId() {
		return _commerceInventoryWarehouse.getCompanyId();
	}

	/**
	 * Returns the country two letters iso code of this commerce inventory warehouse.
	 *
	 * @return the country two letters iso code of this commerce inventory warehouse
	 */
	@Override
	public String getCountryTwoLettersISOCode() {
		return _commerceInventoryWarehouse.getCountryTwoLettersISOCode();
	}

	/**
	 * Returns the create date of this commerce inventory warehouse.
	 *
	 * @return the create date of this commerce inventory warehouse
	 */
	@Override
	public Date getCreateDate() {
		return _commerceInventoryWarehouse.getCreateDate();
	}

	/**
	 * Returns the description of this commerce inventory warehouse.
	 *
	 * @return the description of this commerce inventory warehouse
	 */
	@Override
	public String getDescription() {
		return _commerceInventoryWarehouse.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceInventoryWarehouse.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this commerce inventory warehouse.
	 *
	 * @return the external reference code of this commerce inventory warehouse
	 */
	@Override
	public String getExternalReferenceCode() {
		return _commerceInventoryWarehouse.getExternalReferenceCode();
	}

	/**
	 * Returns the latitude of this commerce inventory warehouse.
	 *
	 * @return the latitude of this commerce inventory warehouse
	 */
	@Override
	public double getLatitude() {
		return _commerceInventoryWarehouse.getLatitude();
	}

	/**
	 * Returns the longitude of this commerce inventory warehouse.
	 *
	 * @return the longitude of this commerce inventory warehouse
	 */
	@Override
	public double getLongitude() {
		return _commerceInventoryWarehouse.getLongitude();
	}

	/**
	 * Returns the modified date of this commerce inventory warehouse.
	 *
	 * @return the modified date of this commerce inventory warehouse
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceInventoryWarehouse.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce inventory warehouse.
	 *
	 * @return the mvcc version of this commerce inventory warehouse
	 */
	@Override
	public long getMvccVersion() {
		return _commerceInventoryWarehouse.getMvccVersion();
	}

	/**
	 * Returns the name of this commerce inventory warehouse.
	 *
	 * @return the name of this commerce inventory warehouse
	 */
	@Override
	public String getName() {
		return _commerceInventoryWarehouse.getName();
	}

	/**
	 * Returns the primary key of this commerce inventory warehouse.
	 *
	 * @return the primary key of this commerce inventory warehouse
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceInventoryWarehouse.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceInventoryWarehouse.getPrimaryKeyObj();
	}

	/**
	 * Returns the street1 of this commerce inventory warehouse.
	 *
	 * @return the street1 of this commerce inventory warehouse
	 */
	@Override
	public String getStreet1() {
		return _commerceInventoryWarehouse.getStreet1();
	}

	/**
	 * Returns the street2 of this commerce inventory warehouse.
	 *
	 * @return the street2 of this commerce inventory warehouse
	 */
	@Override
	public String getStreet2() {
		return _commerceInventoryWarehouse.getStreet2();
	}

	/**
	 * Returns the street3 of this commerce inventory warehouse.
	 *
	 * @return the street3 of this commerce inventory warehouse
	 */
	@Override
	public String getStreet3() {
		return _commerceInventoryWarehouse.getStreet3();
	}

	/**
	 * Returns the type of this commerce inventory warehouse.
	 *
	 * @return the type of this commerce inventory warehouse
	 */
	@Override
	public String getType() {
		return _commerceInventoryWarehouse.getType();
	}

	/**
	 * Returns the user ID of this commerce inventory warehouse.
	 *
	 * @return the user ID of this commerce inventory warehouse
	 */
	@Override
	public long getUserId() {
		return _commerceInventoryWarehouse.getUserId();
	}

	/**
	 * Returns the user name of this commerce inventory warehouse.
	 *
	 * @return the user name of this commerce inventory warehouse
	 */
	@Override
	public String getUserName() {
		return _commerceInventoryWarehouse.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce inventory warehouse.
	 *
	 * @return the user uuid of this commerce inventory warehouse
	 */
	@Override
	public String getUserUuid() {
		return _commerceInventoryWarehouse.getUserUuid();
	}

	/**
	 * Returns the zip of this commerce inventory warehouse.
	 *
	 * @return the zip of this commerce inventory warehouse
	 */
	@Override
	public String getZip() {
		return _commerceInventoryWarehouse.getZip();
	}

	@Override
	public int hashCode() {
		return _commerceInventoryWarehouse.hashCode();
	}

	/**
	 * Returns <code>true</code> if this commerce inventory warehouse is active.
	 *
	 * @return <code>true</code> if this commerce inventory warehouse is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return _commerceInventoryWarehouse.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceInventoryWarehouse.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceInventoryWarehouse.isEscapedModel();
	}

	@Override
	public boolean isGeolocated() {
		return _commerceInventoryWarehouse.isGeolocated();
	}

	@Override
	public boolean isNew() {
		return _commerceInventoryWarehouse.isNew();
	}

	@Override
	public void persist() {
		_commerceInventoryWarehouse.persist();
	}

	/**
	 * Sets whether this commerce inventory warehouse is active.
	 *
	 * @param active the active of this commerce inventory warehouse
	 */
	@Override
	public void setActive(boolean active) {
		_commerceInventoryWarehouse.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceInventoryWarehouse.setCachedModel(cachedModel);
	}

	/**
	 * Sets the city of this commerce inventory warehouse.
	 *
	 * @param city the city of this commerce inventory warehouse
	 */
	@Override
	public void setCity(String city) {
		_commerceInventoryWarehouse.setCity(city);
	}

	/**
	 * Sets the commerce inventory warehouse ID of this commerce inventory warehouse.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID of this commerce inventory warehouse
	 */
	@Override
	public void setCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		_commerceInventoryWarehouse.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
	}

	/**
	 * Sets the commerce region code of this commerce inventory warehouse.
	 *
	 * @param commerceRegionCode the commerce region code of this commerce inventory warehouse
	 */
	@Override
	public void setCommerceRegionCode(String commerceRegionCode) {
		_commerceInventoryWarehouse.setCommerceRegionCode(commerceRegionCode);
	}

	/**
	 * Sets the company ID of this commerce inventory warehouse.
	 *
	 * @param companyId the company ID of this commerce inventory warehouse
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceInventoryWarehouse.setCompanyId(companyId);
	}

	/**
	 * Sets the country two letters iso code of this commerce inventory warehouse.
	 *
	 * @param countryTwoLettersISOCode the country two letters iso code of this commerce inventory warehouse
	 */
	@Override
	public void setCountryTwoLettersISOCode(String countryTwoLettersISOCode) {
		_commerceInventoryWarehouse.setCountryTwoLettersISOCode(
			countryTwoLettersISOCode);
	}

	/**
	 * Sets the create date of this commerce inventory warehouse.
	 *
	 * @param createDate the create date of this commerce inventory warehouse
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceInventoryWarehouse.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this commerce inventory warehouse.
	 *
	 * @param description the description of this commerce inventory warehouse
	 */
	@Override
	public void setDescription(String description) {
		_commerceInventoryWarehouse.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceInventoryWarehouse.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceInventoryWarehouse.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceInventoryWarehouse.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the external reference code of this commerce inventory warehouse.
	 *
	 * @param externalReferenceCode the external reference code of this commerce inventory warehouse
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_commerceInventoryWarehouse.setExternalReferenceCode(
			externalReferenceCode);
	}

	/**
	 * Sets the latitude of this commerce inventory warehouse.
	 *
	 * @param latitude the latitude of this commerce inventory warehouse
	 */
	@Override
	public void setLatitude(double latitude) {
		_commerceInventoryWarehouse.setLatitude(latitude);
	}

	/**
	 * Sets the longitude of this commerce inventory warehouse.
	 *
	 * @param longitude the longitude of this commerce inventory warehouse
	 */
	@Override
	public void setLongitude(double longitude) {
		_commerceInventoryWarehouse.setLongitude(longitude);
	}

	/**
	 * Sets the modified date of this commerce inventory warehouse.
	 *
	 * @param modifiedDate the modified date of this commerce inventory warehouse
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceInventoryWarehouse.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce inventory warehouse.
	 *
	 * @param mvccVersion the mvcc version of this commerce inventory warehouse
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		_commerceInventoryWarehouse.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this commerce inventory warehouse.
	 *
	 * @param name the name of this commerce inventory warehouse
	 */
	@Override
	public void setName(String name) {
		_commerceInventoryWarehouse.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceInventoryWarehouse.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce inventory warehouse.
	 *
	 * @param primaryKey the primary key of this commerce inventory warehouse
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceInventoryWarehouse.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceInventoryWarehouse.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the street1 of this commerce inventory warehouse.
	 *
	 * @param street1 the street1 of this commerce inventory warehouse
	 */
	@Override
	public void setStreet1(String street1) {
		_commerceInventoryWarehouse.setStreet1(street1);
	}

	/**
	 * Sets the street2 of this commerce inventory warehouse.
	 *
	 * @param street2 the street2 of this commerce inventory warehouse
	 */
	@Override
	public void setStreet2(String street2) {
		_commerceInventoryWarehouse.setStreet2(street2);
	}

	/**
	 * Sets the street3 of this commerce inventory warehouse.
	 *
	 * @param street3 the street3 of this commerce inventory warehouse
	 */
	@Override
	public void setStreet3(String street3) {
		_commerceInventoryWarehouse.setStreet3(street3);
	}

	/**
	 * Sets the type of this commerce inventory warehouse.
	 *
	 * @param type the type of this commerce inventory warehouse
	 */
	@Override
	public void setType(String type) {
		_commerceInventoryWarehouse.setType(type);
	}

	/**
	 * Sets the user ID of this commerce inventory warehouse.
	 *
	 * @param userId the user ID of this commerce inventory warehouse
	 */
	@Override
	public void setUserId(long userId) {
		_commerceInventoryWarehouse.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce inventory warehouse.
	 *
	 * @param userName the user name of this commerce inventory warehouse
	 */
	@Override
	public void setUserName(String userName) {
		_commerceInventoryWarehouse.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce inventory warehouse.
	 *
	 * @param userUuid the user uuid of this commerce inventory warehouse
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceInventoryWarehouse.setUserUuid(userUuid);
	}

	/**
	 * Sets the zip of this commerce inventory warehouse.
	 *
	 * @param zip the zip of this commerce inventory warehouse
	 */
	@Override
	public void setZip(String zip) {
		_commerceInventoryWarehouse.setZip(zip);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceInventoryWarehouse> toCacheModel() {

		return _commerceInventoryWarehouse.toCacheModel();
	}

	@Override
	public CommerceInventoryWarehouse toEscapedModel() {
		return new CommerceInventoryWarehouseWrapper(
			_commerceInventoryWarehouse.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceInventoryWarehouse.toString();
	}

	@Override
	public CommerceInventoryWarehouse toUnescapedModel() {
		return new CommerceInventoryWarehouseWrapper(
			_commerceInventoryWarehouse.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceInventoryWarehouse.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceInventoryWarehouseWrapper)) {
			return false;
		}

		CommerceInventoryWarehouseWrapper commerceInventoryWarehouseWrapper =
			(CommerceInventoryWarehouseWrapper)object;

		if (Objects.equals(
				_commerceInventoryWarehouse,
				commerceInventoryWarehouseWrapper.
					_commerceInventoryWarehouse)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceInventoryWarehouse getWrappedModel() {
		return _commerceInventoryWarehouse;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceInventoryWarehouse.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceInventoryWarehouse.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceInventoryWarehouse.resetOriginalValues();
	}

	private final CommerceInventoryWarehouse _commerceInventoryWarehouse;

}