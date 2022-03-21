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
 * This class is a wrapper for {@link ObjectViewColumn}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectViewColumn
 * @generated
 */
public class ObjectViewColumnWrapper
	extends BaseModelWrapper<ObjectViewColumn>
	implements ModelWrapper<ObjectViewColumn>, ObjectViewColumn {

	public ObjectViewColumnWrapper(ObjectViewColumn objectViewColumn) {
		super(objectViewColumn);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectViewColumnId", getObjectViewColumnId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectViewId", getObjectViewId());
		attributes.put("label", getLabel());
		attributes.put("objectFieldName", getObjectFieldName());
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

		Long objectViewColumnId = (Long)attributes.get("objectViewColumnId");

		if (objectViewColumnId != null) {
			setObjectViewColumnId(objectViewColumnId);
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

		Long objectViewId = (Long)attributes.get("objectViewId");

		if (objectViewId != null) {
			setObjectViewId(objectViewId);
		}

		String label = (String)attributes.get("label");

		if (label != null) {
			setLabel(label);
		}

		String objectFieldName = (String)attributes.get("objectFieldName");

		if (objectFieldName != null) {
			setObjectFieldName(objectFieldName);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public ObjectViewColumn cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this object view column.
	 *
	 * @return the company ID of this object view column
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object view column.
	 *
	 * @return the create date of this object view column
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
	 * Returns the label of this object view column.
	 *
	 * @return the label of this object view column
	 */
	@Override
	public String getLabel() {
		return model.getLabel();
	}

	/**
	 * Returns the localized label of this object view column in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized label of this object view column
	 */
	@Override
	public String getLabel(java.util.Locale locale) {
		return model.getLabel(locale);
	}

	/**
	 * Returns the localized label of this object view column in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this object view column. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getLabel(java.util.Locale locale, boolean useDefault) {
		return model.getLabel(locale, useDefault);
	}

	/**
	 * Returns the localized label of this object view column in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized label of this object view column
	 */
	@Override
	public String getLabel(String languageId) {
		return model.getLabel(languageId);
	}

	/**
	 * Returns the localized label of this object view column in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this object view column
	 */
	@Override
	public String getLabel(String languageId, boolean useDefault) {
		return model.getLabel(languageId, useDefault);
	}

	@Override
	public String getLabelCurrentLanguageId() {
		return model.getLabelCurrentLanguageId();
	}

	@Override
	public String getLabelCurrentValue() {
		return model.getLabelCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized labels of this object view column.
	 *
	 * @return the locales and localized labels of this object view column
	 */
	@Override
	public Map<java.util.Locale, String> getLabelMap() {
		return model.getLabelMap();
	}

	/**
	 * Returns the modified date of this object view column.
	 *
	 * @return the modified date of this object view column
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object view column.
	 *
	 * @return the mvcc version of this object view column
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the object field name of this object view column.
	 *
	 * @return the object field name of this object view column
	 */
	@Override
	public String getObjectFieldName() {
		return model.getObjectFieldName();
	}

	/**
	 * Returns the object view column ID of this object view column.
	 *
	 * @return the object view column ID of this object view column
	 */
	@Override
	public long getObjectViewColumnId() {
		return model.getObjectViewColumnId();
	}

	/**
	 * Returns the object view ID of this object view column.
	 *
	 * @return the object view ID of this object view column
	 */
	@Override
	public long getObjectViewId() {
		return model.getObjectViewId();
	}

	/**
	 * Returns the primary key of this object view column.
	 *
	 * @return the primary key of this object view column
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this object view column.
	 *
	 * @return the priority of this object view column
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the user ID of this object view column.
	 *
	 * @return the user ID of this object view column
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object view column.
	 *
	 * @return the user name of this object view column
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object view column.
	 *
	 * @return the user uuid of this object view column
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object view column.
	 *
	 * @return the uuid of this object view column
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
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
	 * Sets the company ID of this object view column.
	 *
	 * @param companyId the company ID of this object view column
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object view column.
	 *
	 * @param createDate the create date of this object view column
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the label of this object view column.
	 *
	 * @param label the label of this object view column
	 */
	@Override
	public void setLabel(String label) {
		model.setLabel(label);
	}

	/**
	 * Sets the localized label of this object view column in the language.
	 *
	 * @param label the localized label of this object view column
	 * @param locale the locale of the language
	 */
	@Override
	public void setLabel(String label, java.util.Locale locale) {
		model.setLabel(label, locale);
	}

	/**
	 * Sets the localized label of this object view column in the language, and sets the default locale.
	 *
	 * @param label the localized label of this object view column
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabel(
		String label, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setLabel(label, locale, defaultLocale);
	}

	@Override
	public void setLabelCurrentLanguageId(String languageId) {
		model.setLabelCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized labels of this object view column from the map of locales and localized labels.
	 *
	 * @param labelMap the locales and localized labels of this object view column
	 */
	@Override
	public void setLabelMap(Map<java.util.Locale, String> labelMap) {
		model.setLabelMap(labelMap);
	}

	/**
	 * Sets the localized labels of this object view column from the map of locales and localized labels, and sets the default locale.
	 *
	 * @param labelMap the locales and localized labels of this object view column
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabelMap(
		Map<java.util.Locale, String> labelMap,
		java.util.Locale defaultLocale) {

		model.setLabelMap(labelMap, defaultLocale);
	}

	/**
	 * Sets the modified date of this object view column.
	 *
	 * @param modifiedDate the modified date of this object view column
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object view column.
	 *
	 * @param mvccVersion the mvcc version of this object view column
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the object field name of this object view column.
	 *
	 * @param objectFieldName the object field name of this object view column
	 */
	@Override
	public void setObjectFieldName(String objectFieldName) {
		model.setObjectFieldName(objectFieldName);
	}

	/**
	 * Sets the object view column ID of this object view column.
	 *
	 * @param objectViewColumnId the object view column ID of this object view column
	 */
	@Override
	public void setObjectViewColumnId(long objectViewColumnId) {
		model.setObjectViewColumnId(objectViewColumnId);
	}

	/**
	 * Sets the object view ID of this object view column.
	 *
	 * @param objectViewId the object view ID of this object view column
	 */
	@Override
	public void setObjectViewId(long objectViewId) {
		model.setObjectViewId(objectViewId);
	}

	/**
	 * Sets the primary key of this object view column.
	 *
	 * @param primaryKey the primary key of this object view column
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this object view column.
	 *
	 * @param priority the priority of this object view column
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the user ID of this object view column.
	 *
	 * @param userId the user ID of this object view column
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object view column.
	 *
	 * @param userName the user name of this object view column
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object view column.
	 *
	 * @param userUuid the user uuid of this object view column
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object view column.
	 *
	 * @param uuid the uuid of this object view column
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
	protected ObjectViewColumnWrapper wrap(ObjectViewColumn objectViewColumn) {
		return new ObjectViewColumnWrapper(objectViewColumn);
	}

}