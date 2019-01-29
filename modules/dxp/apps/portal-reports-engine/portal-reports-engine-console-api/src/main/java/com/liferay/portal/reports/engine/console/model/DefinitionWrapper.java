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
 * This class is a wrapper for {@link Definition}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Definition
 * @generated
 */
@ProviderType
public class DefinitionWrapper extends BaseModelWrapper<Definition>
	implements Definition, ModelWrapper<Definition> {
	public DefinitionWrapper(Definition definition) {
		super(definition);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("definitionId", getDefinitionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("sourceId", getSourceId());
		attributes.put("reportName", getReportName());
		attributes.put("reportParameters", getReportParameters());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long definitionId = (Long)attributes.get("definitionId");

		if (definitionId != null) {
			setDefinitionId(definitionId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Long sourceId = (Long)attributes.get("sourceId");

		if (sourceId != null) {
			setSourceId(sourceId);
		}

		String reportName = (String)attributes.get("reportName");

		if (reportName != null) {
			setReportName(reportName);
		}

		String reportParameters = (String)attributes.get("reportParameters");

		if (reportParameters != null) {
			setReportParameters(reportParameters);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
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
	* Returns the company ID of this definition.
	*
	* @return the company ID of this definition
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this definition.
	*
	* @return the create date of this definition
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
	* Returns the definition ID of this definition.
	*
	* @return the definition ID of this definition
	*/
	@Override
	public long getDefinitionId() {
		return model.getDefinitionId();
	}

	/**
	* Returns the description of this definition.
	*
	* @return the description of this definition
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the localized description of this definition in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this definition
	*/
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	* Returns the localized description of this definition in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this definition. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this definition in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this definition
	*/
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	* Returns the localized description of this definition in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this definition
	*/
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return model.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return model.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return model.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this definition.
	*
	* @return the locales and localized descriptions of this definition
	*/
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	* Returns the group ID of this definition.
	*
	* @return the group ID of this definition
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the last publish date of this definition.
	*
	* @return the last publish date of this definition
	*/
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	* Returns the modified date of this definition.
	*
	* @return the modified date of this definition
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the name of this definition.
	*
	* @return the name of this definition
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the localized name of this definition in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this definition
	*/
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	* Returns the localized name of this definition in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this definition. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this definition in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this definition
	*/
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	* Returns the localized name of this definition in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this definition
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
	* Returns a map of the locales and localized names of this definition.
	*
	* @return the locales and localized names of this definition
	*/
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	* Returns the primary key of this definition.
	*
	* @return the primary key of this definition
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the report name of this definition.
	*
	* @return the report name of this definition
	*/
	@Override
	public String getReportName() {
		return model.getReportName();
	}

	/**
	* Returns the report parameters of this definition.
	*
	* @return the report parameters of this definition
	*/
	@Override
	public String getReportParameters() {
		return model.getReportParameters();
	}

	/**
	* Returns the source ID of this definition.
	*
	* @return the source ID of this definition
	*/
	@Override
	public long getSourceId() {
		return model.getSourceId();
	}

	/**
	* Returns the user ID of this definition.
	*
	* @return the user ID of this definition
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this definition.
	*
	* @return the user name of this definition
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this definition.
	*
	* @return the user uuid of this definition
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this definition.
	*
	* @return the uuid of this definition
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
	* Sets the company ID of this definition.
	*
	* @param companyId the company ID of this definition
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this definition.
	*
	* @param createDate the create date of this definition
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the definition ID of this definition.
	*
	* @param definitionId the definition ID of this definition
	*/
	@Override
	public void setDefinitionId(long definitionId) {
		model.setDefinitionId(definitionId);
	}

	/**
	* Sets the description of this definition.
	*
	* @param description the description of this definition
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the localized description of this definition in the language.
	*
	* @param description the localized description of this definition
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this definition in the language, and sets the default locale.
	*
	* @param description the localized description of this definition
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		model.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		model.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this definition from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this definition
	*/
	@Override
	public void setDescriptionMap(Map<java.util.Locale, String> descriptionMap) {
		model.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this definition from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this definition
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {
		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Sets the group ID of this definition.
	*
	* @param groupId the group ID of this definition
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this definition.
	*
	* @param lastPublishDate the last publish date of this definition
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this definition.
	*
	* @param modifiedDate the modified date of this definition
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this definition.
	*
	* @param name the name of this definition
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the localized name of this definition in the language.
	*
	* @param name the localized name of this definition
	* @param locale the locale of the language
	*/
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	* Sets the localized name of this definition in the language, and sets the default locale.
	*
	* @param name the localized name of this definition
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
	* Sets the localized names of this definition from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this definition
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this definition from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this definition
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap,
		java.util.Locale defaultLocale) {
		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Sets the primary key of this definition.
	*
	* @param primaryKey the primary key of this definition
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the report name of this definition.
	*
	* @param reportName the report name of this definition
	*/
	@Override
	public void setReportName(String reportName) {
		model.setReportName(reportName);
	}

	/**
	* Sets the report parameters of this definition.
	*
	* @param reportParameters the report parameters of this definition
	*/
	@Override
	public void setReportParameters(String reportParameters) {
		model.setReportParameters(reportParameters);
	}

	/**
	* Sets the source ID of this definition.
	*
	* @param sourceId the source ID of this definition
	*/
	@Override
	public void setSourceId(long sourceId) {
		model.setSourceId(sourceId);
	}

	/**
	* Sets the user ID of this definition.
	*
	* @param userId the user ID of this definition
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this definition.
	*
	* @param userName the user name of this definition
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this definition.
	*
	* @param userUuid the user uuid of this definition
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this definition.
	*
	* @param uuid the uuid of this definition
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
	protected DefinitionWrapper wrap(Definition definition) {
		return new DefinitionWrapper(definition);
	}
}