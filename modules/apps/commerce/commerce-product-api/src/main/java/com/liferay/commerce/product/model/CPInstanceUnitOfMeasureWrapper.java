/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CPInstanceUnitOfMeasure}.
 * </p>
 *
 * @author Marco Leo
 * @see CPInstanceUnitOfMeasure
 * @generated
 */
public class CPInstanceUnitOfMeasureWrapper
	extends BaseModelWrapper<CPInstanceUnitOfMeasure>
	implements CPInstanceUnitOfMeasure, ModelWrapper<CPInstanceUnitOfMeasure> {

	public CPInstanceUnitOfMeasureWrapper(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		super(cpInstanceUnitOfMeasure);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put(
			"CPInstanceUnitOfMeasureId", getCPInstanceUnitOfMeasureId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPInstanceId", getCPInstanceId());
		attributes.put("active", isActive());
		attributes.put(
			"incrementalOrderQuantity", getIncrementalOrderQuantity());
		attributes.put("key", getKey());
		attributes.put("name", getName());
		attributes.put("precision", getPrecision());
		attributes.put("primary", isPrimary());
		attributes.put("priority", getPriority());
		attributes.put("rate", getRate());
		attributes.put("sku", getSku());

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

		Long CPInstanceUnitOfMeasureId = (Long)attributes.get(
			"CPInstanceUnitOfMeasureId");

		if (CPInstanceUnitOfMeasureId != null) {
			setCPInstanceUnitOfMeasureId(CPInstanceUnitOfMeasureId);
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

		Long CPInstanceId = (Long)attributes.get("CPInstanceId");

		if (CPInstanceId != null) {
			setCPInstanceId(CPInstanceId);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		BigDecimal incrementalOrderQuantity = (BigDecimal)attributes.get(
			"incrementalOrderQuantity");

		if (incrementalOrderQuantity != null) {
			setIncrementalOrderQuantity(incrementalOrderQuantity);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer precision = (Integer)attributes.get("precision");

		if (precision != null) {
			setPrecision(precision);
		}

		Boolean primary = (Boolean)attributes.get("primary");

		if (primary != null) {
			setPrimary(primary);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		BigDecimal rate = (BigDecimal)attributes.get("rate");

		if (rate != null) {
			setRate(rate);
		}

		String sku = (String)attributes.get("sku");

		if (sku != null) {
			setSku(sku);
		}
	}

	@Override
	public CPInstanceUnitOfMeasure cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this cp instance unit of measure.
	 *
	 * @return the active of this cp instance unit of measure
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this cp instance unit of measure.
	 *
	 * @return the company ID of this cp instance unit of measure
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp instance ID of this cp instance unit of measure.
	 *
	 * @return the cp instance ID of this cp instance unit of measure
	 */
	@Override
	public long getCPInstanceId() {
		return model.getCPInstanceId();
	}

	/**
	 * Returns the cp instance unit of measure ID of this cp instance unit of measure.
	 *
	 * @return the cp instance unit of measure ID of this cp instance unit of measure
	 */
	@Override
	public long getCPInstanceUnitOfMeasureId() {
		return model.getCPInstanceUnitOfMeasureId();
	}

	/**
	 * Returns the create date of this cp instance unit of measure.
	 *
	 * @return the create date of this cp instance unit of measure
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this cp instance unit of measure.
	 *
	 * @return the ct collection ID of this cp instance unit of measure
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the incremental order quantity of this cp instance unit of measure.
	 *
	 * @return the incremental order quantity of this cp instance unit of measure
	 */
	@Override
	public BigDecimal getIncrementalOrderQuantity() {
		return model.getIncrementalOrderQuantity();
	}

	/**
	 * Returns the key of this cp instance unit of measure.
	 *
	 * @return the key of this cp instance unit of measure
	 */
	@Override
	public String getKey() {
		return model.getKey();
	}

	/**
	 * Returns the modified date of this cp instance unit of measure.
	 *
	 * @return the modified date of this cp instance unit of measure
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp instance unit of measure.
	 *
	 * @return the mvcc version of this cp instance unit of measure
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this cp instance unit of measure.
	 *
	 * @return the name of this cp instance unit of measure
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this cp instance unit of measure in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this cp instance unit of measure
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this cp instance unit of measure in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this cp instance unit of measure. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this cp instance unit of measure in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this cp instance unit of measure
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this cp instance unit of measure in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this cp instance unit of measure
	 */
	@Override
	public String getName(String languageId, boolean useDefault) {
		return model.getName(languageId, useDefault);
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
	 * Returns a map of the locales and localized names of this cp instance unit of measure.
	 *
	 * @return the locales and localized names of this cp instance unit of measure
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the precision of this cp instance unit of measure.
	 *
	 * @return the precision of this cp instance unit of measure
	 */
	@Override
	public int getPrecision() {
		return model.getPrecision();
	}

	/**
	 * Returns the primary of this cp instance unit of measure.
	 *
	 * @return the primary of this cp instance unit of measure
	 */
	@Override
	public boolean getPrimary() {
		return model.getPrimary();
	}

	/**
	 * Returns the primary key of this cp instance unit of measure.
	 *
	 * @return the primary key of this cp instance unit of measure
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this cp instance unit of measure.
	 *
	 * @return the priority of this cp instance unit of measure
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the rate of this cp instance unit of measure.
	 *
	 * @return the rate of this cp instance unit of measure
	 */
	@Override
	public BigDecimal getRate() {
		return model.getRate();
	}

	/**
	 * Returns the sku of this cp instance unit of measure.
	 *
	 * @return the sku of this cp instance unit of measure
	 */
	@Override
	public String getSku() {
		return model.getSku();
	}

	/**
	 * Returns the user ID of this cp instance unit of measure.
	 *
	 * @return the user ID of this cp instance unit of measure
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp instance unit of measure.
	 *
	 * @return the user name of this cp instance unit of measure
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp instance unit of measure.
	 *
	 * @return the user uuid of this cp instance unit of measure
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp instance unit of measure.
	 *
	 * @return the uuid of this cp instance unit of measure
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this cp instance unit of measure is active.
	 *
	 * @return <code>true</code> if this cp instance unit of measure is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this cp instance unit of measure is primary.
	 *
	 * @return <code>true</code> if this cp instance unit of measure is primary; <code>false</code> otherwise
	 */
	@Override
	public boolean isPrimary() {
		return model.isPrimary();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	 * Sets whether this cp instance unit of measure is active.
	 *
	 * @param active the active of this cp instance unit of measure
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the company ID of this cp instance unit of measure.
	 *
	 * @param companyId the company ID of this cp instance unit of measure
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp instance ID of this cp instance unit of measure.
	 *
	 * @param CPInstanceId the cp instance ID of this cp instance unit of measure
	 */
	@Override
	public void setCPInstanceId(long CPInstanceId) {
		model.setCPInstanceId(CPInstanceId);
	}

	/**
	 * Sets the cp instance unit of measure ID of this cp instance unit of measure.
	 *
	 * @param CPInstanceUnitOfMeasureId the cp instance unit of measure ID of this cp instance unit of measure
	 */
	@Override
	public void setCPInstanceUnitOfMeasureId(long CPInstanceUnitOfMeasureId) {
		model.setCPInstanceUnitOfMeasureId(CPInstanceUnitOfMeasureId);
	}

	/**
	 * Sets the create date of this cp instance unit of measure.
	 *
	 * @param createDate the create date of this cp instance unit of measure
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this cp instance unit of measure.
	 *
	 * @param ctCollectionId the ct collection ID of this cp instance unit of measure
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the incremental order quantity of this cp instance unit of measure.
	 *
	 * @param incrementalOrderQuantity the incremental order quantity of this cp instance unit of measure
	 */
	@Override
	public void setIncrementalOrderQuantity(
		BigDecimal incrementalOrderQuantity) {

		model.setIncrementalOrderQuantity(incrementalOrderQuantity);
	}

	/**
	 * Sets the key of this cp instance unit of measure.
	 *
	 * @param key the key of this cp instance unit of measure
	 */
	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	/**
	 * Sets the modified date of this cp instance unit of measure.
	 *
	 * @param modifiedDate the modified date of this cp instance unit of measure
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp instance unit of measure.
	 *
	 * @param mvccVersion the mvcc version of this cp instance unit of measure
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this cp instance unit of measure.
	 *
	 * @param name the name of this cp instance unit of measure
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this cp instance unit of measure in the language.
	 *
	 * @param name the localized name of this cp instance unit of measure
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this cp instance unit of measure in the language, and sets the default locale.
	 *
	 * @param name the localized name of this cp instance unit of measure
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setName(
		String name, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized names of this cp instance unit of measure from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this cp instance unit of measure
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this cp instance unit of measure from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this cp instance unit of measure
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the precision of this cp instance unit of measure.
	 *
	 * @param precision the precision of this cp instance unit of measure
	 */
	@Override
	public void setPrecision(int precision) {
		model.setPrecision(precision);
	}

	/**
	 * Sets whether this cp instance unit of measure is primary.
	 *
	 * @param primary the primary of this cp instance unit of measure
	 */
	@Override
	public void setPrimary(boolean primary) {
		model.setPrimary(primary);
	}

	/**
	 * Sets the primary key of this cp instance unit of measure.
	 *
	 * @param primaryKey the primary key of this cp instance unit of measure
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this cp instance unit of measure.
	 *
	 * @param priority the priority of this cp instance unit of measure
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the rate of this cp instance unit of measure.
	 *
	 * @param rate the rate of this cp instance unit of measure
	 */
	@Override
	public void setRate(BigDecimal rate) {
		model.setRate(rate);
	}

	/**
	 * Sets the sku of this cp instance unit of measure.
	 *
	 * @param sku the sku of this cp instance unit of measure
	 */
	@Override
	public void setSku(String sku) {
		model.setSku(sku);
	}

	/**
	 * Sets the user ID of this cp instance unit of measure.
	 *
	 * @param userId the user ID of this cp instance unit of measure
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp instance unit of measure.
	 *
	 * @param userName the user name of this cp instance unit of measure
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp instance unit of measure.
	 *
	 * @param userUuid the user uuid of this cp instance unit of measure
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp instance unit of measure.
	 *
	 * @param uuid the uuid of this cp instance unit of measure
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<CPInstanceUnitOfMeasure, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPInstanceUnitOfMeasure, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPInstanceUnitOfMeasureWrapper wrap(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		return new CPInstanceUnitOfMeasureWrapper(cpInstanceUnitOfMeasure);
	}

}