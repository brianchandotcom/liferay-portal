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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Address}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Address
 * @generated
 */
@ProviderType
public class AddressWrapper implements Address, ModelWrapper<Address> {
	public AddressWrapper(Address address) {
		_address = address;
	}

	@Override
	public Class<?> getModelClass() {
		return Address.class;
	}

	@Override
	public String getModelClassName() {
		return Address.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("addressId", getAddressId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("street1", getStreet1());
		attributes.put("street2", getStreet2());
		attributes.put("street3", getStreet3());
		attributes.put("city", getCity());
		attributes.put("zip", getZip());
		attributes.put("regionId", getRegionId());
		attributes.put("countryId", getCountryId());
		attributes.put("typeId", getTypeId());
		attributes.put("mailing", getMailing());
		attributes.put("primary", getPrimary());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long addressId = (Long)attributes.get("addressId");

		if (addressId != null) {
			setAddressId(addressId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
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

		Long regionId = (Long)attributes.get("regionId");

		if (regionId != null) {
			setRegionId(regionId);
		}

		Long countryId = (Long)attributes.get("countryId");

		if (countryId != null) {
			setCountryId(countryId);
		}

		Long typeId = (Long)attributes.get("typeId");

		if (typeId != null) {
			setTypeId(typeId);
		}

		Boolean mailing = (Boolean)attributes.get("mailing");

		if (mailing != null) {
			setMailing(mailing);
		}

		Boolean primary = (Boolean)attributes.get("primary");

		if (primary != null) {
			setPrimary(primary);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new AddressWrapper((Address)_address.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Address address) {
		return _address.compareTo(address);
	}

	/**
	* Returns the address ID of this address.
	*
	* @return the address ID of this address
	*/
	@Override
	public long getAddressId() {
		return _address.getAddressId();
	}

	/**
	* Returns the city of this address.
	*
	* @return the city of this address
	*/
	@Override
	public java.lang.String getCity() {
		return _address.getCity();
	}

	/**
	* Returns the fully qualified class name of this address.
	*
	* @return the fully qualified class name of this address
	*/
	@Override
	public java.lang.String getClassName() {
		return _address.getClassName();
	}

	/**
	* Returns the class name ID of this address.
	*
	* @return the class name ID of this address
	*/
	@Override
	public long getClassNameId() {
		return _address.getClassNameId();
	}

	/**
	* Returns the class p k of this address.
	*
	* @return the class p k of this address
	*/
	@Override
	public long getClassPK() {
		return _address.getClassPK();
	}

	/**
	* Returns the company ID of this address.
	*
	* @return the company ID of this address
	*/
	@Override
	public long getCompanyId() {
		return _address.getCompanyId();
	}

	@Override
	public com.liferay.portal.model.Country getCountry() {
		return _address.getCountry();
	}

	/**
	* Returns the country ID of this address.
	*
	* @return the country ID of this address
	*/
	@Override
	public long getCountryId() {
		return _address.getCountryId();
	}

	/**
	* Returns the create date of this address.
	*
	* @return the create date of this address
	*/
	@Override
	public Date getCreateDate() {
		return _address.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _address.getExpandoBridge();
	}

	/**
	* Returns the mailing of this address.
	*
	* @return the mailing of this address
	*/
	@Override
	public boolean getMailing() {
		return _address.getMailing();
	}

	/**
	* Returns the modified date of this address.
	*
	* @return the modified date of this address
	*/
	@Override
	public Date getModifiedDate() {
		return _address.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this address.
	*
	* @return the mvcc version of this address
	*/
	@Override
	public long getMvccVersion() {
		return _address.getMvccVersion();
	}

	/**
	* Returns the primary of this address.
	*
	* @return the primary of this address
	*/
	@Override
	public boolean getPrimary() {
		return _address.getPrimary();
	}

	/**
	* Returns the primary key of this address.
	*
	* @return the primary key of this address
	*/
	@Override
	public long getPrimaryKey() {
		return _address.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _address.getPrimaryKeyObj();
	}

	@Override
	public com.liferay.portal.model.Region getRegion() {
		return _address.getRegion();
	}

	/**
	* Returns the region ID of this address.
	*
	* @return the region ID of this address
	*/
	@Override
	public long getRegionId() {
		return _address.getRegionId();
	}

	/**
	* Returns the street1 of this address.
	*
	* @return the street1 of this address
	*/
	@Override
	public java.lang.String getStreet1() {
		return _address.getStreet1();
	}

	/**
	* Returns the street2 of this address.
	*
	* @return the street2 of this address
	*/
	@Override
	public java.lang.String getStreet2() {
		return _address.getStreet2();
	}

	/**
	* Returns the street3 of this address.
	*
	* @return the street3 of this address
	*/
	@Override
	public java.lang.String getStreet3() {
		return _address.getStreet3();
	}

	@Override
	public com.liferay.portal.model.ListType getType() {
		return _address.getType();
	}

	/**
	* Returns the type ID of this address.
	*
	* @return the type ID of this address
	*/
	@Override
	public long getTypeId() {
		return _address.getTypeId();
	}

	/**
	* Returns the user ID of this address.
	*
	* @return the user ID of this address
	*/
	@Override
	public long getUserId() {
		return _address.getUserId();
	}

	/**
	* Returns the user name of this address.
	*
	* @return the user name of this address
	*/
	@Override
	public java.lang.String getUserName() {
		return _address.getUserName();
	}

	/**
	* Returns the user uuid of this address.
	*
	* @return the user uuid of this address
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _address.getUserUuid();
	}

	/**
	* Returns the uuid of this address.
	*
	* @return the uuid of this address
	*/
	@Override
	public java.lang.String getUuid() {
		return _address.getUuid();
	}

	/**
	* Returns the zip of this address.
	*
	* @return the zip of this address
	*/
	@Override
	public java.lang.String getZip() {
		return _address.getZip();
	}

	@Override
	public int hashCode() {
		return _address.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _address.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _address.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this address is mailing.
	*
	* @return <code>true</code> if this address is mailing; <code>false</code> otherwise
	*/
	@Override
	public boolean isMailing() {
		return _address.isMailing();
	}

	@Override
	public boolean isNew() {
		return _address.isNew();
	}

	/**
	* Returns <code>true</code> if this address is primary.
	*
	* @return <code>true</code> if this address is primary; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrimary() {
		return _address.isPrimary();
	}

	@Override
	public void persist() {
		_address.persist();
	}

	/**
	* Sets the address ID of this address.
	*
	* @param addressId the address ID of this address
	*/
	@Override
	public void setAddressId(long addressId) {
		_address.setAddressId(addressId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_address.setCachedModel(cachedModel);
	}

	/**
	* Sets the city of this address.
	*
	* @param city the city of this address
	*/
	@Override
	public void setCity(java.lang.String city) {
		_address.setCity(city);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_address.setClassName(className);
	}

	/**
	* Sets the class name ID of this address.
	*
	* @param classNameId the class name ID of this address
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_address.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this address.
	*
	* @param classPK the class p k of this address
	*/
	@Override
	public void setClassPK(long classPK) {
		_address.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this address.
	*
	* @param companyId the company ID of this address
	*/
	@Override
	public void setCompanyId(long companyId) {
		_address.setCompanyId(companyId);
	}

	/**
	* Sets the country ID of this address.
	*
	* @param countryId the country ID of this address
	*/
	@Override
	public void setCountryId(long countryId) {
		_address.setCountryId(countryId);
	}

	/**
	* Sets the create date of this address.
	*
	* @param createDate the create date of this address
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_address.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_address.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_address.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_address.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets whether this address is mailing.
	*
	* @param mailing the mailing of this address
	*/
	@Override
	public void setMailing(boolean mailing) {
		_address.setMailing(mailing);
	}

	/**
	* Sets the modified date of this address.
	*
	* @param modifiedDate the modified date of this address
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_address.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this address.
	*
	* @param mvccVersion the mvcc version of this address
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_address.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_address.setNew(n);
	}

	/**
	* Sets whether this address is primary.
	*
	* @param primary the primary of this address
	*/
	@Override
	public void setPrimary(boolean primary) {
		_address.setPrimary(primary);
	}

	/**
	* Sets the primary key of this address.
	*
	* @param primaryKey the primary key of this address
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_address.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_address.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the region ID of this address.
	*
	* @param regionId the region ID of this address
	*/
	@Override
	public void setRegionId(long regionId) {
		_address.setRegionId(regionId);
	}

	/**
	* Sets the street1 of this address.
	*
	* @param street1 the street1 of this address
	*/
	@Override
	public void setStreet1(java.lang.String street1) {
		_address.setStreet1(street1);
	}

	/**
	* Sets the street2 of this address.
	*
	* @param street2 the street2 of this address
	*/
	@Override
	public void setStreet2(java.lang.String street2) {
		_address.setStreet2(street2);
	}

	/**
	* Sets the street3 of this address.
	*
	* @param street3 the street3 of this address
	*/
	@Override
	public void setStreet3(java.lang.String street3) {
		_address.setStreet3(street3);
	}

	/**
	* Sets the type ID of this address.
	*
	* @param typeId the type ID of this address
	*/
	@Override
	public void setTypeId(long typeId) {
		_address.setTypeId(typeId);
	}

	/**
	* Sets the user ID of this address.
	*
	* @param userId the user ID of this address
	*/
	@Override
	public void setUserId(long userId) {
		_address.setUserId(userId);
	}

	/**
	* Sets the user name of this address.
	*
	* @param userName the user name of this address
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_address.setUserName(userName);
	}

	/**
	* Sets the user uuid of this address.
	*
	* @param userUuid the user uuid of this address
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_address.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this address.
	*
	* @param uuid the uuid of this address
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_address.setUuid(uuid);
	}

	/**
	* Sets the zip of this address.
	*
	* @param zip the zip of this address
	*/
	@Override
	public void setZip(java.lang.String zip) {
		_address.setZip(zip);
	}

	@Override
	public CacheModel<com.liferay.portal.model.Address> toCacheModel() {
		return _address.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Address toEscapedModel() {
		return new AddressWrapper(_address.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _address.toString();
	}

	@Override
	public com.liferay.portal.model.Address toUnescapedModel() {
		return new AddressWrapper(_address.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _address.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AddressWrapper)) {
			return false;
		}

		AddressWrapper addressWrapper = (AddressWrapper)obj;

		if (Validator.equals(_address, addressWrapper._address)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _address.getStagedModelType();
	}

	@Override
	public Address getWrappedModel() {
		return _address;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _address.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _address.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_address.resetOriginalValues();
	}

	private final Address _address;
}