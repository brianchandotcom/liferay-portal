/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link Address}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Address
 * @generated
 */
public class AddressWrapper
	extends BaseModelWrapper<Address>
	implements Address, ModelWrapper<Address> {

	public AddressWrapper(Address address) {
		super(address);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("addressId", getAddressId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("countryId", getCountryId());
		attributes.put("listTypeId", getListTypeId());
		attributes.put("regionId", getRegionId());
		attributes.put("city", getCity());
		attributes.put("description", getDescription());
		attributes.put("latitude", getLatitude());
		attributes.put("longitude", getLongitude());
		attributes.put("mailing", isMailing());
		attributes.put("name", getName());
		attributes.put("primary", isPrimary());
		attributes.put("street1", getStreet1());
		attributes.put("street2", getStreet2());
		attributes.put("street3", getStreet3());
		attributes.put("validationDate", getValidationDate());
		attributes.put("validationStatus", getValidationStatus());
		attributes.put("zip", getZip());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
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

		Long countryId = (Long)attributes.get("countryId");

		if (countryId != null) {
			setCountryId(countryId);
		}

		Long listTypeId = (Long)attributes.get("listTypeId");

		if (listTypeId != null) {
			setListTypeId(listTypeId);
		}

		Long regionId = (Long)attributes.get("regionId");

		if (regionId != null) {
			setRegionId(regionId);
		}

		String city = (String)attributes.get("city");

		if (city != null) {
			setCity(city);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Double latitude = (Double)attributes.get("latitude");

		if (latitude != null) {
			setLatitude(latitude);
		}

		Double longitude = (Double)attributes.get("longitude");

		if (longitude != null) {
			setLongitude(longitude);
		}

		Boolean mailing = (Boolean)attributes.get("mailing");

		if (mailing != null) {
			setMailing(mailing);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Boolean primary = (Boolean)attributes.get("primary");

		if (primary != null) {
			setPrimary(primary);
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

		Date validationDate = (Date)attributes.get("validationDate");

		if (validationDate != null) {
			setValidationDate(validationDate);
		}

		Integer validationStatus = (Integer)attributes.get("validationStatus");

		if (validationStatus != null) {
			setValidationStatus(validationStatus);
		}

		String zip = (String)attributes.get("zip");

		if (zip != null) {
			setZip(zip);
		}
	}

	@Override
	public Address cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
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
	 * Returns the ct collection ID of this address.
	 *
	 * @return the ct collection ID of this address
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the description of this address.
	 *
	 * @return the description of this address
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the external reference code of this address.
	 *
	 * @return the external reference code of this address
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the latitude of this address.
	 *
	 * @return the latitude of this address
	 */
	@Override
	public double getLatitude() {
		return model.getLatitude();
	}

	@Override
	public ListType getListType() {
		return model.getListType();
	}

	/**
	 * Returns the list type ID of this address.
	 *
	 * @return the list type ID of this address
	 */
	@Override
	public long getListTypeId() {
		return model.getListTypeId();
	}

	/**
	 * Returns the longitude of this address.
	 *
	 * @return the longitude of this address
	 */
	@Override
	public double getLongitude() {
		return model.getLongitude();
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
	 * Returns the name of this address.
	 *
	 * @return the name of this address
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public String getPhoneNumber() {
		return model.getPhoneNumber();
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
	 * Returns the validation date of this address.
	 *
	 * @return the validation date of this address
	 */
	@Override
	public Date getValidationDate() {
		return model.getValidationDate();
	}

	/**
	 * Returns the validation status of this address.
	 *
	 * @return the validation status of this address
	 */
	@Override
	public int getValidationStatus() {
		return model.getValidationStatus();
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
	 * Sets the ct collection ID of this address.
	 *
	 * @param ctCollectionId the ct collection ID of this address
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this address.
	 *
	 * @param description the description of this address
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the external reference code of this address.
	 *
	 * @param externalReferenceCode the external reference code of this address
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the latitude of this address.
	 *
	 * @param latitude the latitude of this address
	 */
	@Override
	public void setLatitude(double latitude) {
		model.setLatitude(latitude);
	}

	/**
	 * Sets the list type ID of this address.
	 *
	 * @param listTypeId the list type ID of this address
	 */
	@Override
	public void setListTypeId(long listTypeId) {
		model.setListTypeId(listTypeId);
	}

	/**
	 * Sets the longitude of this address.
	 *
	 * @param longitude the longitude of this address
	 */
	@Override
	public void setLongitude(double longitude) {
		model.setLongitude(longitude);
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
	 * Sets the name of this address.
	 *
	 * @param name the name of this address
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
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
	 * Sets the validation date of this address.
	 *
	 * @param validationDate the validation date of this address
	 */
	@Override
	public void setValidationDate(Date validationDate) {
		model.setValidationDate(validationDate);
	}

	/**
	 * Sets the validation status of this address.
	 *
	 * @param validationStatus the validation status of this address
	 */
	@Override
	public void setValidationStatus(int validationStatus) {
		model.setValidationStatus(validationStatus);
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
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<Address, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<Address, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
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