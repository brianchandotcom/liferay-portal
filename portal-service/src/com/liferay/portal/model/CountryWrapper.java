/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Country}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Country
 * @generated
 */
public class CountryWrapper implements Country, ModelWrapper<Country> {
	public CountryWrapper(Country country) {
		_country = country;
	}

	public Class<?> getModelClass() {
		return Country.class;
	}

	public String getModelClassName() {
		return Country.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("countryId", getCountryId());
		attributes.put("name", getName());
		attributes.put("a2", getA2());
		attributes.put("a3", getA3());
		attributes.put("number", getNumber());
		attributes.put("idd", getIdd());
		attributes.put("zipRequired", getZipRequired());
		attributes.put("active", getActive());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long countryId = (Long)attributes.get("countryId");

		if (countryId != null) {
			setCountryId(countryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String a2 = (String)attributes.get("a2");

		if (a2 != null) {
			setA2(a2);
		}

		String a3 = (String)attributes.get("a3");

		if (a3 != null) {
			setA3(a3);
		}

		String number = (String)attributes.get("number");

		if (number != null) {
			setNumber(number);
		}

		String idd = (String)attributes.get("idd");

		if (idd != null) {
			setIdd(idd);
		}

		Boolean zipRequired = (Boolean)attributes.get("zipRequired");

		if (zipRequired != null) {
			setZipRequired(zipRequired);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	/**
	* Returns the primary key of this country.
	*
	* @return the primary key of this country
	*/
	public long getPrimaryKey() {
		return _country.getPrimaryKey();
	}

	/**
	* Sets the primary key of this country.
	*
	* @param primaryKey the primary key of this country
	*/
	public void setPrimaryKey(long primaryKey) {
		_country.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the country ID of this country.
	*
	* @return the country ID of this country
	*/
	public long getCountryId() {
		return _country.getCountryId();
	}

	/**
	* Sets the country ID of this country.
	*
	* @param countryId the country ID of this country
	*/
	public void setCountryId(long countryId) {
		_country.setCountryId(countryId);
	}

	/**
	* Returns the name of this country.
	*
	* @return the name of this country
	*/
	public java.lang.String getName() {
		return _country.getName();
	}

	/**
	* Sets the name of this country.
	*
	* @param name the name of this country
	*/
	public void setName(java.lang.String name) {
		_country.setName(name);
	}

	/**
	* Returns the a2 of this country.
	*
	* @return the a2 of this country
	*/
	public java.lang.String getA2() {
		return _country.getA2();
	}

	/**
	* Sets the a2 of this country.
	*
	* @param a2 the a2 of this country
	*/
	public void setA2(java.lang.String a2) {
		_country.setA2(a2);
	}

	/**
	* Returns the a3 of this country.
	*
	* @return the a3 of this country
	*/
	public java.lang.String getA3() {
		return _country.getA3();
	}

	/**
	* Sets the a3 of this country.
	*
	* @param a3 the a3 of this country
	*/
	public void setA3(java.lang.String a3) {
		_country.setA3(a3);
	}

	/**
	* Returns the number of this country.
	*
	* @return the number of this country
	*/
	public java.lang.String getNumber() {
		return _country.getNumber();
	}

	/**
	* Sets the number of this country.
	*
	* @param number the number of this country
	*/
	public void setNumber(java.lang.String number) {
		_country.setNumber(number);
	}

	/**
	* Returns the idd of this country.
	*
	* @return the idd of this country
	*/
	public java.lang.String getIdd() {
		return _country.getIdd();
	}

	/**
	* Sets the idd of this country.
	*
	* @param idd the idd of this country
	*/
	public void setIdd(java.lang.String idd) {
		_country.setIdd(idd);
	}

	/**
	* Returns the zip required of this country.
	*
	* @return the zip required of this country
	*/
	public boolean getZipRequired() {
		return _country.getZipRequired();
	}

	/**
	* Returns <code>true</code> if this country is zip required.
	*
	* @return <code>true</code> if this country is zip required; <code>false</code> otherwise
	*/
	public boolean isZipRequired() {
		return _country.isZipRequired();
	}

	/**
	* Sets whether this country is zip required.
	*
	* @param zipRequired the zip required of this country
	*/
	public void setZipRequired(boolean zipRequired) {
		_country.setZipRequired(zipRequired);
	}

	/**
	* Returns the active of this country.
	*
	* @return the active of this country
	*/
	public boolean getActive() {
		return _country.getActive();
	}

	/**
	* Returns <code>true</code> if this country is active.
	*
	* @return <code>true</code> if this country is active; <code>false</code> otherwise
	*/
	public boolean isActive() {
		return _country.isActive();
	}

	/**
	* Sets whether this country is active.
	*
	* @param active the active of this country
	*/
	public void setActive(boolean active) {
		_country.setActive(active);
	}

	public boolean isNew() {
		return _country.isNew();
	}

	public void setNew(boolean n) {
		_country.setNew(n);
	}

	public boolean isCachedModel() {
		return _country.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_country.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _country.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _country.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_country.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _country.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_country.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new CountryWrapper((Country)_country.clone());
	}

	public int compareTo(com.liferay.portal.model.Country country) {
		return _country.compareTo(country);
	}

	@Override
	public int hashCode() {
		return _country.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Country> toCacheModel() {
		return _country.toCacheModel();
	}

	public com.liferay.portal.model.Country toEscapedModel() {
		return new CountryWrapper(_country.toEscapedModel());
	}

	public com.liferay.portal.model.Country toUnescapedModel() {
		return new CountryWrapper(_country.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _country.toString();
	}

	public java.lang.String toXmlString() {
		return _country.toXmlString();
	}

	public java.lang.String getName(java.util.Locale locale) {
		return _country.getName(locale);
	}

	public java.lang.String getNameCurrentLanguageId() {
		return _country.getNameCurrentLanguageId();
	}

	public java.lang.String getNameCurrentValue() {
		return _country.getNameCurrentValue();
	}

	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_country.setNameCurrentLanguageId(languageId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Country getWrappedCountry() {
		return _country;
	}

	public Country getWrappedModel() {
		return _country;
	}

	public void resetOriginalValues() {
		_country.resetOriginalValues();
	}

	private Country _country;
}