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

package com.liferay.app.builder.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AppBuilderApp}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderApp
 * @generated
 */
public class AppBuilderAppWrapper
	extends BaseModelWrapper<AppBuilderApp>
	implements AppBuilderApp, ModelWrapper<AppBuilderApp> {

	public AppBuilderAppWrapper(AppBuilderApp appBuilderApp) {
		super(appBuilderApp);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("appBuilderAppId", getAppBuilderAppId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("ddmStructureId", getDdmStructureId());
		attributes.put("ddmStructureLayoutId", getDdmStructureLayoutId());
		attributes.put("deDataListViewId", getDeDataListViewId());
		attributes.put("name", getName());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long appBuilderAppId = (Long)attributes.get("appBuilderAppId");

		if (appBuilderAppId != null) {
			setAppBuilderAppId(appBuilderAppId);
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

		Long ddmStructureId = (Long)attributes.get("ddmStructureId");

		if (ddmStructureId != null) {
			setDdmStructureId(ddmStructureId);
		}

		Long ddmStructureLayoutId = (Long)attributes.get(
			"ddmStructureLayoutId");

		if (ddmStructureLayoutId != null) {
			setDdmStructureLayoutId(ddmStructureLayoutId);
		}

		Long deDataListViewId = (Long)attributes.get("deDataListViewId");

		if (deDataListViewId != null) {
			setDeDataListViewId(deDataListViewId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	 * Returns the app builder app ID of this app builder app.
	 *
	 * @return the app builder app ID of this app builder app
	 */
	@Override
	public long getAppBuilderAppId() {
		return model.getAppBuilderAppId();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this app builder app.
	 *
	 * @return the company ID of this app builder app
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this app builder app.
	 *
	 * @return the create date of this app builder app
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ddm structure ID of this app builder app.
	 *
	 * @return the ddm structure ID of this app builder app
	 */
	@Override
	public long getDdmStructureId() {
		return model.getDdmStructureId();
	}

	/**
	 * Returns the ddm structure layout ID of this app builder app.
	 *
	 * @return the ddm structure layout ID of this app builder app
	 */
	@Override
	public long getDdmStructureLayoutId() {
		return model.getDdmStructureLayoutId();
	}

	/**
	 * Returns the de data list view ID of this app builder app.
	 *
	 * @return the de data list view ID of this app builder app
	 */
	@Override
	public long getDeDataListViewId() {
		return model.getDeDataListViewId();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the group ID of this app builder app.
	 *
	 * @return the group ID of this app builder app
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this app builder app.
	 *
	 * @return the modified date of this app builder app
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this app builder app.
	 *
	 * @return the name of this app builder app
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this app builder app in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this app builder app
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this app builder app in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this app builder app. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this app builder app in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this app builder app
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this app builder app in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this app builder app
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
	 * Returns a map of the locales and localized names of this app builder app.
	 *
	 * @return the locales and localized names of this app builder app
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the primary key of this app builder app.
	 *
	 * @return the primary key of this app builder app
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this app builder app.
	 *
	 * @return the status of this app builder app
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the user ID of this app builder app.
	 *
	 * @return the user ID of this app builder app
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this app builder app.
	 *
	 * @return the user name of this app builder app
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this app builder app.
	 *
	 * @return the user uuid of this app builder app
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this app builder app.
	 *
	 * @return the uuid of this app builder app
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a app builder app model instance should use the <code>AppBuilderApp</code> interface instead.
	 */
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
	 * Sets the app builder app ID of this app builder app.
	 *
	 * @param appBuilderAppId the app builder app ID of this app builder app
	 */
	@Override
	public void setAppBuilderAppId(long appBuilderAppId) {
		model.setAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Sets the company ID of this app builder app.
	 *
	 * @param companyId the company ID of this app builder app
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this app builder app.
	 *
	 * @param createDate the create date of this app builder app
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ddm structure ID of this app builder app.
	 *
	 * @param ddmStructureId the ddm structure ID of this app builder app
	 */
	@Override
	public void setDdmStructureId(long ddmStructureId) {
		model.setDdmStructureId(ddmStructureId);
	}

	/**
	 * Sets the ddm structure layout ID of this app builder app.
	 *
	 * @param ddmStructureLayoutId the ddm structure layout ID of this app builder app
	 */
	@Override
	public void setDdmStructureLayoutId(long ddmStructureLayoutId) {
		model.setDdmStructureLayoutId(ddmStructureLayoutId);
	}

	/**
	 * Sets the de data list view ID of this app builder app.
	 *
	 * @param deDataListViewId the de data list view ID of this app builder app
	 */
	@Override
	public void setDeDataListViewId(long deDataListViewId) {
		model.setDeDataListViewId(deDataListViewId);
	}

	/**
	 * Sets the group ID of this app builder app.
	 *
	 * @param groupId the group ID of this app builder app
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this app builder app.
	 *
	 * @param modifiedDate the modified date of this app builder app
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this app builder app.
	 *
	 * @param name the name of this app builder app
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this app builder app in the language.
	 *
	 * @param name the localized name of this app builder app
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this app builder app in the language, and sets the default locale.
	 *
	 * @param name the localized name of this app builder app
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
	 * Sets the localized names of this app builder app from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this app builder app
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this app builder app from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this app builder app
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the primary key of this app builder app.
	 *
	 * @param primaryKey the primary key of this app builder app
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this app builder app.
	 *
	 * @param status the status of this app builder app
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the user ID of this app builder app.
	 *
	 * @param userId the user ID of this app builder app
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this app builder app.
	 *
	 * @param userName the user name of this app builder app
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this app builder app.
	 *
	 * @param userUuid the user uuid of this app builder app
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this app builder app.
	 *
	 * @param uuid the uuid of this app builder app
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
	protected AppBuilderAppWrapper wrap(AppBuilderApp appBuilderApp) {
		return new AppBuilderAppWrapper(appBuilderApp);
	}

}