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

package com.liferay.object.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ObjectLayoutBox}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutBox
 * @generated
 */
public class ObjectLayoutBoxWrapper
	extends BaseModelWrapper<ObjectLayoutBox>
	implements ModelWrapper<ObjectLayoutBox>, ObjectLayoutBox {

	public ObjectLayoutBoxWrapper(ObjectLayoutBox objectLayoutBox) {
		super(objectLayoutBox);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectLayoutBoxId", getObjectLayoutBoxId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectLayoutTabId", getObjectLayoutTabId());
		attributes.put("collapsable", isCollapsable());
		attributes.put("name", getName());
		attributes.put("priority", getPriority());

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

		Long objectLayoutBoxId = (Long)attributes.get("objectLayoutBoxId");

		if (objectLayoutBoxId != null) {
			setObjectLayoutBoxId(objectLayoutBoxId);
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

		Long objectLayoutTabId = (Long)attributes.get("objectLayoutTabId");

		if (objectLayoutTabId != null) {
			setObjectLayoutTabId(objectLayoutTabId);
		}

		Boolean collapsable = (Boolean)attributes.get("collapsable");

		if (collapsable != null) {
			setCollapsable(collapsable);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public ObjectLayoutBox cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the collapsable of this object layout box.
	 *
	 * @return the collapsable of this object layout box
	 */
	@Override
	public boolean getCollapsable() {
		return model.getCollapsable();
	}

	/**
	 * Returns the company ID of this object layout box.
	 *
	 * @return the company ID of this object layout box
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object layout box.
	 *
	 * @return the create date of this object layout box
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the modified date of this object layout box.
	 *
	 * @return the modified date of this object layout box
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object layout box.
	 *
	 * @return the mvcc version of this object layout box
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this object layout box.
	 *
	 * @return the name of this object layout box
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this object layout box in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this object layout box
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this object layout box in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this object layout box. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this object layout box in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this object layout box
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this object layout box in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this object layout box
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
	 * Returns a map of the locales and localized names of this object layout box.
	 *
	 * @return the locales and localized names of this object layout box
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the object layout box ID of this object layout box.
	 *
	 * @return the object layout box ID of this object layout box
	 */
	@Override
	public long getObjectLayoutBoxId() {
		return model.getObjectLayoutBoxId();
	}

	@Override
	public java.util.List<ObjectLayoutRow> getObjectLayoutRows() {
		return model.getObjectLayoutRows();
	}

	/**
	 * Returns the object layout tab ID of this object layout box.
	 *
	 * @return the object layout tab ID of this object layout box
	 */
	@Override
	public long getObjectLayoutTabId() {
		return model.getObjectLayoutTabId();
	}

	/**
	 * Returns the primary key of this object layout box.
	 *
	 * @return the primary key of this object layout box
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this object layout box.
	 *
	 * @return the priority of this object layout box
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the user ID of this object layout box.
	 *
	 * @return the user ID of this object layout box
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object layout box.
	 *
	 * @return the user name of this object layout box
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object layout box.
	 *
	 * @return the user uuid of this object layout box
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object layout box.
	 *
	 * @return the uuid of this object layout box
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this object layout box is collapsable.
	 *
	 * @return <code>true</code> if this object layout box is collapsable; <code>false</code> otherwise
	 */
	@Override
	public boolean isCollapsable() {
		return model.isCollapsable();
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
	 * Sets whether this object layout box is collapsable.
	 *
	 * @param collapsable the collapsable of this object layout box
	 */
	@Override
	public void setCollapsable(boolean collapsable) {
		model.setCollapsable(collapsable);
	}

	/**
	 * Sets the company ID of this object layout box.
	 *
	 * @param companyId the company ID of this object layout box
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object layout box.
	 *
	 * @param createDate the create date of this object layout box
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this object layout box.
	 *
	 * @param modifiedDate the modified date of this object layout box
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object layout box.
	 *
	 * @param mvccVersion the mvcc version of this object layout box
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this object layout box.
	 *
	 * @param name the name of this object layout box
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this object layout box in the language.
	 *
	 * @param name the localized name of this object layout box
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this object layout box in the language, and sets the default locale.
	 *
	 * @param name the localized name of this object layout box
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
	 * Sets the localized names of this object layout box from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this object layout box
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this object layout box from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this object layout box
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the object layout box ID of this object layout box.
	 *
	 * @param objectLayoutBoxId the object layout box ID of this object layout box
	 */
	@Override
	public void setObjectLayoutBoxId(long objectLayoutBoxId) {
		model.setObjectLayoutBoxId(objectLayoutBoxId);
	}

	@Override
	public void setObjectLayoutRows(
		java.util.List<ObjectLayoutRow> objectLayoutRows) {

		model.setObjectLayoutRows(objectLayoutRows);
	}

	/**
	 * Sets the object layout tab ID of this object layout box.
	 *
	 * @param objectLayoutTabId the object layout tab ID of this object layout box
	 */
	@Override
	public void setObjectLayoutTabId(long objectLayoutTabId) {
		model.setObjectLayoutTabId(objectLayoutTabId);
	}

	/**
	 * Sets the primary key of this object layout box.
	 *
	 * @param primaryKey the primary key of this object layout box
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this object layout box.
	 *
	 * @param priority the priority of this object layout box
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the user ID of this object layout box.
	 *
	 * @param userId the user ID of this object layout box
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object layout box.
	 *
	 * @param userName the user name of this object layout box
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object layout box.
	 *
	 * @param userUuid the user uuid of this object layout box
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object layout box.
	 *
	 * @param uuid the uuid of this object layout box
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
	protected ObjectLayoutBoxWrapper wrap(ObjectLayoutBox objectLayoutBox) {
		return new ObjectLayoutBoxWrapper(objectLayoutBox);
	}

}