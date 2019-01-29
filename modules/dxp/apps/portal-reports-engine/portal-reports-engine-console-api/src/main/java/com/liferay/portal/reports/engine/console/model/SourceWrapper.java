/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Source}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Source
 * @generated
 */
@ProviderType
public class SourceWrapper extends BaseModelWrapper<Source> implements Source,
	ModelWrapper<Source> {
	public SourceWrapper(Source source) {
		super(source);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("sourceId", getSourceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("name", getName());
		attributes.put("driverClassName", getDriverClassName());
		attributes.put("driverUrl", getDriverUrl());
		attributes.put("driverUserName", getDriverUserName());
		attributes.put("driverPassword", getDriverPassword());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long sourceId = (Long)attributes.get("sourceId");

		if (sourceId != null) {
			setSourceId(sourceId);
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

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String driverClassName = (String)attributes.get("driverClassName");

		if (driverClassName != null) {
			setDriverClassName(driverClassName);
		}

		String driverUrl = (String)attributes.get("driverUrl");

		if (driverUrl != null) {
			setDriverUrl(driverUrl);
		}

		String driverUserName = (String)attributes.get("driverUserName");

		if (driverUserName != null) {
			setDriverUserName(driverUserName);
		}

		String driverPassword = (String)attributes.get("driverPassword");

		if (driverPassword != null) {
			setDriverPassword(driverPassword);
		}
	}

	@Override
	public String getAttachmentsDir() {
		return model.getAttachmentsDir();
	}

	@Override
	public String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getAttachmentsFiles();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this source.
	*
	* @return the company ID of this source
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this source.
	*
	* @return the create date of this source
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
	* Returns the driver class name of this source.
	*
	* @return the driver class name of this source
	*/
	@Override
	public String getDriverClassName() {
		return model.getDriverClassName();
	}

	/**
	* Returns the driver password of this source.
	*
	* @return the driver password of this source
	*/
	@Override
	public String getDriverPassword() {
		return model.getDriverPassword();
	}

	/**
	* Returns the driver url of this source.
	*
	* @return the driver url of this source
	*/
	@Override
	public String getDriverUrl() {
		return model.getDriverUrl();
	}

	/**
	* Returns the driver user name of this source.
	*
	* @return the driver user name of this source
	*/
	@Override
	public String getDriverUserName() {
		return model.getDriverUserName();
	}

	/**
	* Returns the group ID of this source.
	*
	* @return the group ID of this source
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the last publish date of this source.
	*
	* @return the last publish date of this source
	*/
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	* Returns the modified date of this source.
	*
	* @return the modified date of this source
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the name of this source.
	*
	* @return the name of this source
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the localized name of this source in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this source
	*/
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	* Returns the localized name of this source in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this source. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this source in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this source
	*/
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	* Returns the localized name of this source in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this source
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
	* Returns a map of the locales and localized names of this source.
	*
	* @return the locales and localized names of this source
	*/
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	* Returns the primary key of this source.
	*
	* @return the primary key of this source
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the source ID of this source.
	*
	* @return the source ID of this source
	*/
	@Override
	public long getSourceId() {
		return model.getSourceId();
	}

	/**
	* Returns the user ID of this source.
	*
	* @return the user ID of this source
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this source.
	*
	* @return the user name of this source
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this source.
	*
	* @return the user uuid of this source
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this source.
	*
	* @return the uuid of this source
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
	* Sets the company ID of this source.
	*
	* @param companyId the company ID of this source
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this source.
	*
	* @param createDate the create date of this source
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the driver class name of this source.
	*
	* @param driverClassName the driver class name of this source
	*/
	@Override
	public void setDriverClassName(String driverClassName) {
		model.setDriverClassName(driverClassName);
	}

	/**
	* Sets the driver password of this source.
	*
	* @param driverPassword the driver password of this source
	*/
	@Override
	public void setDriverPassword(String driverPassword) {
		model.setDriverPassword(driverPassword);
	}

	/**
	* Sets the driver url of this source.
	*
	* @param driverUrl the driver url of this source
	*/
	@Override
	public void setDriverUrl(String driverUrl) {
		model.setDriverUrl(driverUrl);
	}

	/**
	* Sets the driver user name of this source.
	*
	* @param driverUserName the driver user name of this source
	*/
	@Override
	public void setDriverUserName(String driverUserName) {
		model.setDriverUserName(driverUserName);
	}

	/**
	* Sets the group ID of this source.
	*
	* @param groupId the group ID of this source
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this source.
	*
	* @param lastPublishDate the last publish date of this source
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this source.
	*
	* @param modifiedDate the modified date of this source
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this source.
	*
	* @param name the name of this source
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the localized name of this source in the language.
	*
	* @param name the localized name of this source
	* @param locale the locale of the language
	*/
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	* Sets the localized name of this source in the language, and sets the default locale.
	*
	* @param name the localized name of this source
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		model.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this source from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this source
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this source from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this source
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap,
		java.util.Locale defaultLocale) {
		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Sets the primary key of this source.
	*
	* @param primaryKey the primary key of this source
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the source ID of this source.
	*
	* @param sourceId the source ID of this source
	*/
	@Override
	public void setSourceId(long sourceId) {
		model.setSourceId(sourceId);
	}

	/**
	* Sets the user ID of this source.
	*
	* @param userId the user ID of this source
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this source.
	*
	* @param userName the user name of this source
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this source.
	*
	* @param userUuid the user uuid of this source
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this source.
	*
	* @param uuid the uuid of this source
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
	protected SourceWrapper wrap(Source source) {
		return new SourceWrapper(source);
	}
}