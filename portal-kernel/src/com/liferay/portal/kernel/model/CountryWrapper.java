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

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Country}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Country
 * @generated
 */
public class CountryWrapper
	extends BaseModelWrapper<Country>
	implements Country, ModelWrapper<Country> {

	public CountryWrapper(Country country) {
		super(country);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("countryId", getCountryId());
		attributes.put("name", getName());
		attributes.put("a2", getA2());
		attributes.put("a3", getA3());
		attributes.put("number", getNumber());
		attributes.put("idd", getIdd());
		attributes.put("zipRequired", isZipRequired());
		attributes.put("active", isActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	 * Returns the a2 of this country.
	 *
	 * @return the a2 of this country
	 */
	@Override
	public String getA2() {
		return model.getA2();
	}

	/**
	 * Returns the a3 of this country.
	 *
	 * @return the a3 of this country
	 */
	@Override
	public String getA3() {
		return model.getA3();
	}

	/**
	 * Returns the active of this country.
	 *
	 * @return the active of this country
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the country ID of this country.
	 *
	 * @return the country ID of this country
	 */
	@Override
	public long getCountryId() {
		return model.getCountryId();
	}

	/**
	 * Returns the idd of this country.
	 *
	 * @return the idd of this country
	 */
	@Override
	public String getIdd() {
		return model.getIdd();
	}

	/**
	 * Returns the mvcc version of this country.
	 *
	 * @return the mvcc version of this country
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this country.
	 *
	 * @return the name of this country
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return model.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return model.getNameCurrentValue();
	}

	/**
	 * Returns the number of this country.
	 *
	 * @return the number of this country
	 */
	@Override
	public String getNumber() {
		return model.getNumber();
	}

	/**
	 * Returns the primary key of this country.
	 *
	 * @return the primary key of this country
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the zip required of this country.
	 *
	 * @return the zip required of this country
	 */
	@Override
	public boolean getZipRequired() {
		return model.getZipRequired();
	}

	/**
	 * Returns <code>true</code> if this country is active.
	 *
	 * @return <code>true</code> if this country is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this country is zip required.
	 *
	 * @return <code>true</code> if this country is zip required; <code>false</code> otherwise
	 */
	@Override
	public boolean isZipRequired() {
		return model.isZipRequired();
	}

	/**
	 * Sets the a2 of this country.
	 *
	 * @param a2 the a2 of this country
	 */
	@Override
	public void setA2(String a2) {
		model.setA2(a2);
	}

	/**
	 * Sets the a3 of this country.
	 *
	 * @param a3 the a3 of this country
	 */
	@Override
	public void setA3(String a3) {
		model.setA3(a3);
	}

	/**
	 * Sets whether this country is active.
	 *
	 * @param active the active of this country
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the country ID of this country.
	 *
	 * @param countryId the country ID of this country
	 */
	@Override
	public void setCountryId(long countryId) {
		model.setCountryId(countryId);
	}

	/**
	 * Sets the idd of this country.
	 *
	 * @param idd the idd of this country
	 */
	@Override
	public void setIdd(String idd) {
		model.setIdd(idd);
	}

	/**
	 * Sets the mvcc version of this country.
	 *
	 * @param mvccVersion the mvcc version of this country
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this country.
	 *
	 * @param name the name of this country
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the number of this country.
	 *
	 * @param number the number of this country
	 */
	@Override
	public void setNumber(String number) {
		model.setNumber(number);
	}

	/**
	 * Sets the primary key of this country.
	 *
	 * @param primaryKey the primary key of this country
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this country is zip required.
	 *
	 * @param zipRequired the zip required of this country
	 */
	@Override
	public void setZipRequired(boolean zipRequired) {
		model.setZipRequired(zipRequired);
	}

	@Override
	protected CountryWrapper wrap(Country country) {
		return new CountryWrapper(country);
	}

}