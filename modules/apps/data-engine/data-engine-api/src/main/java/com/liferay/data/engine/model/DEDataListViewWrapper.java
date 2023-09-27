/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DEDataListView}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DEDataListView
 * @generated
 */
public class DEDataListViewWrapper
	extends BaseModelWrapper<DEDataListView>
	implements DEDataListView, ModelWrapper<DEDataListView> {

	public DEDataListViewWrapper(DEDataListView deDataListView) {
		super(deDataListView);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("deDataListViewId", getDeDataListViewId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("appliedFilters", getAppliedFilters());
		attributes.put("ddmStructureId", getDdmStructureId());
		attributes.put("fieldNames", getFieldNames());
		attributes.put("name", getName());
		attributes.put("sortField", getSortField());

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

		Long deDataListViewId = (Long)attributes.get("deDataListViewId");

		if (deDataListViewId != null) {
			setDeDataListViewId(deDataListViewId);
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

		String appliedFilters = (String)attributes.get("appliedFilters");

		if (appliedFilters != null) {
			setAppliedFilters(appliedFilters);
		}

		Long ddmStructureId = (Long)attributes.get("ddmStructureId");

		if (ddmStructureId != null) {
			setDdmStructureId(ddmStructureId);
		}

		String fieldNames = (String)attributes.get("fieldNames");

		if (fieldNames != null) {
			setFieldNames(fieldNames);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String sortField = (String)attributes.get("sortField");

		if (sortField != null) {
			setSortField(sortField);
		}
	}

	@Override
	public DEDataListView cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the applied filters of this de data list view.
	 *
	 * @return the applied filters of this de data list view
	 */
	@Override
	public String getAppliedFilters() {
		return model.getAppliedFilters();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this de data list view.
	 *
	 * @return the company ID of this de data list view
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this de data list view.
	 *
	 * @return the create date of this de data list view
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this de data list view.
	 *
	 * @return the ct collection ID of this de data list view
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the ddm structure ID of this de data list view.
	 *
	 * @return the ddm structure ID of this de data list view
	 */
	@Override
	public long getDdmStructureId() {
		return model.getDdmStructureId();
	}

	/**
	 * Returns the de data list view ID of this de data list view.
	 *
	 * @return the de data list view ID of this de data list view
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
	 * Returns the field names of this de data list view.
	 *
	 * @return the field names of this de data list view
	 */
	@Override
	public String getFieldNames() {
		return model.getFieldNames();
	}

	/**
	 * Returns the group ID of this de data list view.
	 *
	 * @return the group ID of this de data list view
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this de data list view.
	 *
	 * @return the modified date of this de data list view
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this de data list view.
	 *
	 * @return the mvcc version of this de data list view
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this de data list view.
	 *
	 * @return the name of this de data list view
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this de data list view in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this de data list view
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this de data list view in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this de data list view. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this de data list view in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this de data list view
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this de data list view in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this de data list view
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
	 * Returns a map of the locales and localized names of this de data list view.
	 *
	 * @return the locales and localized names of this de data list view
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the primary key of this de data list view.
	 *
	 * @return the primary key of this de data list view
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the sort field of this de data list view.
	 *
	 * @return the sort field of this de data list view
	 */
	@Override
	public String getSortField() {
		return model.getSortField();
	}

	/**
	 * Returns the user ID of this de data list view.
	 *
	 * @return the user ID of this de data list view
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this de data list view.
	 *
	 * @return the user name of this de data list view
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this de data list view.
	 *
	 * @return the user uuid of this de data list view
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this de data list view.
	 *
	 * @return the uuid of this de data list view
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
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
	 * Sets the applied filters of this de data list view.
	 *
	 * @param appliedFilters the applied filters of this de data list view
	 */
	@Override
	public void setAppliedFilters(String appliedFilters) {
		model.setAppliedFilters(appliedFilters);
	}

	/**
	 * Sets the company ID of this de data list view.
	 *
	 * @param companyId the company ID of this de data list view
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this de data list view.
	 *
	 * @param createDate the create date of this de data list view
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this de data list view.
	 *
	 * @param ctCollectionId the ct collection ID of this de data list view
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ddm structure ID of this de data list view.
	 *
	 * @param ddmStructureId the ddm structure ID of this de data list view
	 */
	@Override
	public void setDdmStructureId(long ddmStructureId) {
		model.setDdmStructureId(ddmStructureId);
	}

	/**
	 * Sets the de data list view ID of this de data list view.
	 *
	 * @param deDataListViewId the de data list view ID of this de data list view
	 */
	@Override
	public void setDeDataListViewId(long deDataListViewId) {
		model.setDeDataListViewId(deDataListViewId);
	}

	/**
	 * Sets the field names of this de data list view.
	 *
	 * @param fieldNames the field names of this de data list view
	 */
	@Override
	public void setFieldNames(String fieldNames) {
		model.setFieldNames(fieldNames);
	}

	/**
	 * Sets the group ID of this de data list view.
	 *
	 * @param groupId the group ID of this de data list view
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this de data list view.
	 *
	 * @param modifiedDate the modified date of this de data list view
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this de data list view.
	 *
	 * @param mvccVersion the mvcc version of this de data list view
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this de data list view.
	 *
	 * @param name the name of this de data list view
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this de data list view in the language.
	 *
	 * @param name the localized name of this de data list view
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this de data list view in the language, and sets the default locale.
	 *
	 * @param name the localized name of this de data list view
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
	 * Sets the localized names of this de data list view from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this de data list view
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this de data list view from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this de data list view
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the primary key of this de data list view.
	 *
	 * @param primaryKey the primary key of this de data list view
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the sort field of this de data list view.
	 *
	 * @param sortField the sort field of this de data list view
	 */
	@Override
	public void setSortField(String sortField) {
		model.setSortField(sortField);
	}

	/**
	 * Sets the user ID of this de data list view.
	 *
	 * @param userId the user ID of this de data list view
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this de data list view.
	 *
	 * @param userName the user name of this de data list view
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this de data list view.
	 *
	 * @param userUuid the user uuid of this de data list view
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this de data list view.
	 *
	 * @param uuid the uuid of this de data list view
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
	public Map<String, Function<DEDataListView, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DEDataListView, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DEDataListViewWrapper wrap(DEDataListView deDataListView) {
		return new DEDataListViewWrapper(deDataListView);
	}

}