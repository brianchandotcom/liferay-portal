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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

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
public class AddressWrapper extends BaseModelWrapper<Address> implements Address,
	ModelWrapper<Address> {
	public AddressWrapper(Address address) {
		super(address);
	}

	/**
	* Returns the address ID of this address.
	*
	* @return the address ID of this address
	*/
	@Override
	public long getAddressId() {
		return model.getAddressId();
	}

	/**
	* Returns the city of this address.
	*
	* @return the city of this address
	*/
	@Override
	public String getCity() {
		return model.getCity();
	}

	/**
	* Returns the fully qualified class name of this address.
	*
	* @return the fully qualified class name of this address
	*/
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	* Returns the class name ID of this address.
	*
	* @return the class name ID of this address
	*/
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	* Returns the class pk of this address.
	*
	* @return the class pk of this address
	*/
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	* Returns the company ID of this address.
	*
	* @return the company ID of this address
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public Country getCountry() {
		return model.getCountry();
	}

	/**
	* Returns the country ID of this address.
	*
	* @return the country ID of this address
	*/
	@Override
	public long getCountryId() {
		return model.getCountryId();
	}

	/**
	* Returns the create date of this address.
	*
	* @return the create date of this address
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the mailing of this address.
	*
	* @return the mailing of this address
	*/
	@Override
	public boolean getMailing() {
		return model.getMailing();
	}

	/**
	* Returns the modified date of this address.
	*
	* @return the modified date of this address
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this address.
	*
	* @return the mvcc version of this address
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the primary of this address.
	*
	* @return the primary of this address
	*/
	@Override
	public boolean getPrimary() {
		return model.getPrimary();
	}

	/**
	* Returns the primary key of this address.
	*
	* @return the primary key of this address
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public Region getRegion() {
		return model.getRegion();
	}

	/**
	* Returns the region ID of this address.
	*
	* @return the region ID of this address
	*/
	@Override
	public long getRegionId() {
		return model.getRegionId();
	}

	/**
	* Returns the street1 of this address.
	*
	* @return the street1 of this address
	*/
	@Override
	public String getStreet1() {
		return model.getStreet1();
	}

	/**
	* Returns the street2 of this address.
	*
	* @return the street2 of this address
	*/
	@Override
	public String getStreet2() {
		return model.getStreet2();
	}

	/**
	* Returns the street3 of this address.
	*
	* @return the street3 of this address
	*/
	@Override
	public String getStreet3() {
		return model.getStreet3();
	}

	@Override
	public ListType getType() {
		return model.getType();
	}

	/**
	* Returns the type ID of this address.
	*
	* @return the type ID of this address
	*/
	@Override
	public long getTypeId() {
		return model.getTypeId();
	}

	/**
	* Returns the user ID of this address.
	*
	* @return the user ID of this address
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this address.
	*
	* @return the user name of this address
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this address.
	*
	* @return the user uuid of this address
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this address.
	*
	* @return the uuid of this address
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	* Returns the zip of this address.
	*
	* @return the zip of this address
	*/
	@Override
	public String getZip() {
		return model.getZip();
	}

	/**
	* Returns <code>true</code> if this address is mailing.
	*
	* @return <code>true</code> if this address is mailing; <code>false</code> otherwise
	*/
	@Override
	public boolean isMailing() {
		return model.isMailing();
	}

	/**
	* Returns <code>true</code> if this address is primary.
	*
	* @return <code>true</code> if this address is primary; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrimary() {
		return model.isPrimary();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the address ID of this address.
	*
	* @param addressId the address ID of this address
	*/
	@Override
	public void setAddressId(long addressId) {
		model.setAddressId(addressId);
	}

	/**
	* Sets the city of this address.
	*
	* @param city the city of this address
	*/
	@Override
	public void setCity(String city) {
		model.setCity(city);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	* Sets the class name ID of this address.
	*
	* @param classNameId the class name ID of this address
	*/
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this address.
	*
	* @param classPK the class pk of this address
	*/
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this address.
	*
	* @param companyId the company ID of this address
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the country ID of this address.
	*
	* @param countryId the country ID of this address
	*/
	@Override
	public void setCountryId(long countryId) {
		model.setCountryId(countryId);
	}

	/**
	* Sets the create date of this address.
	*
	* @param createDate the create date of this address
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets whether this address is mailing.
	*
	* @param mailing the mailing of this address
	*/
	@Override
	public void setMailing(boolean mailing) {
		model.setMailing(mailing);
	}

	/**
	* Sets the modified date of this address.
	*
	* @param modifiedDate the modified date of this address
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this address.
	*
	* @param mvccVersion the mvcc version of this address
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets whether this address is primary.
	*
	* @param primary the primary of this address
	*/
	@Override
	public void setPrimary(boolean primary) {
		model.setPrimary(primary);
	}

	/**
	* Sets the primary key of this address.
	*
	* @param primaryKey the primary key of this address
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the region ID of this address.
	*
	* @param regionId the region ID of this address
	*/
	@Override
	public void setRegionId(long regionId) {
		model.setRegionId(regionId);
	}

	/**
	* Sets the street1 of this address.
	*
	* @param street1 the street1 of this address
	*/
	@Override
	public void setStreet1(String street1) {
		model.setStreet1(street1);
	}

	/**
	* Sets the street2 of this address.
	*
	* @param street2 the street2 of this address
	*/
	@Override
	public void setStreet2(String street2) {
		model.setStreet2(street2);
	}

	/**
	* Sets the street3 of this address.
	*
	* @param street3 the street3 of this address
	*/
	@Override
	public void setStreet3(String street3) {
		model.setStreet3(street3);
	}

	/**
	* Sets the type ID of this address.
	*
	* @param typeId the type ID of this address
	*/
	@Override
	public void setTypeId(long typeId) {
		model.setTypeId(typeId);
	}

	/**
	* Sets the user ID of this address.
	*
	* @param userId the user ID of this address
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this address.
	*
	* @param userName the user name of this address
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this address.
	*
	* @param userUuid the user uuid of this address
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this address.
	*
	* @param uuid the uuid of this address
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	* Sets the zip of this address.
	*
	* @param zip the zip of this address
	*/
	@Override
	public void setZip(String zip) {
		model.setZip(zip);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected AddressWrapper wrap(Address address) {
		return new AddressWrapper(address);
	}
}