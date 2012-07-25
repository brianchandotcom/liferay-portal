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

package com.liferay.portlet.dynamicdatamapping.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMContent}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMContent
 * @generated
 */
public class DDMContentWrapper implements DDMContent, ModelWrapper<DDMContent> {
	public DDMContentWrapper(DDMContent ddmContent) {
		_ddmContent = ddmContent;
	}

	public Class<?> getModelClass() {
		return DDMContent.class;
	}

	public String getModelClassName() {
		return DDMContent.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("contentId", getContentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("xml", getXml());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long contentId = (Long)attributes.get("contentId");

		if (contentId != null) {
			setContentId(contentId);
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

		String xml = (String)attributes.get("xml");

		if (xml != null) {
			setXml(xml);
		}
	}

	/**
	* Returns the primary key of this d d m content.
	*
	* @return the primary key of this d d m content
	*/
	public long getPrimaryKey() {
		return _ddmContent.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m content.
	*
	* @param primaryKey the primary key of this d d m content
	*/
	public void setPrimaryKey(long primaryKey) {
		_ddmContent.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this d d m content.
	*
	* @return the uuid of this d d m content
	*/
	public java.lang.String getUuid() {
		return _ddmContent.getUuid();
	}

	/**
	* Sets the uuid of this d d m content.
	*
	* @param uuid the uuid of this d d m content
	*/
	public void setUuid(java.lang.String uuid) {
		_ddmContent.setUuid(uuid);
	}

	/**
	* Returns the content ID of this d d m content.
	*
	* @return the content ID of this d d m content
	*/
	public long getContentId() {
		return _ddmContent.getContentId();
	}

	/**
	* Sets the content ID of this d d m content.
	*
	* @param contentId the content ID of this d d m content
	*/
	public void setContentId(long contentId) {
		_ddmContent.setContentId(contentId);
	}

	/**
	* Returns the group ID of this d d m content.
	*
	* @return the group ID of this d d m content
	*/
	public long getGroupId() {
		return _ddmContent.getGroupId();
	}

	/**
	* Sets the group ID of this d d m content.
	*
	* @param groupId the group ID of this d d m content
	*/
	public void setGroupId(long groupId) {
		_ddmContent.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this d d m content.
	*
	* @return the company ID of this d d m content
	*/
	public long getCompanyId() {
		return _ddmContent.getCompanyId();
	}

	/**
	* Sets the company ID of this d d m content.
	*
	* @param companyId the company ID of this d d m content
	*/
	public void setCompanyId(long companyId) {
		_ddmContent.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this d d m content.
	*
	* @return the user ID of this d d m content
	*/
	public long getUserId() {
		return _ddmContent.getUserId();
	}

	/**
	* Sets the user ID of this d d m content.
	*
	* @param userId the user ID of this d d m content
	*/
	public void setUserId(long userId) {
		_ddmContent.setUserId(userId);
	}

	/**
	* Returns the user uuid of this d d m content.
	*
	* @return the user uuid of this d d m content
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmContent.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d m content.
	*
	* @param userUuid the user uuid of this d d m content
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ddmContent.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this d d m content.
	*
	* @return the user name of this d d m content
	*/
	public java.lang.String getUserName() {
		return _ddmContent.getUserName();
	}

	/**
	* Sets the user name of this d d m content.
	*
	* @param userName the user name of this d d m content
	*/
	public void setUserName(java.lang.String userName) {
		_ddmContent.setUserName(userName);
	}

	/**
	* Returns the create date of this d d m content.
	*
	* @return the create date of this d d m content
	*/
	public java.util.Date getCreateDate() {
		return _ddmContent.getCreateDate();
	}

	/**
	* Sets the create date of this d d m content.
	*
	* @param createDate the create date of this d d m content
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ddmContent.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this d d m content.
	*
	* @return the modified date of this d d m content
	*/
	public java.util.Date getModifiedDate() {
		return _ddmContent.getModifiedDate();
	}

	/**
	* Sets the modified date of this d d m content.
	*
	* @param modifiedDate the modified date of this d d m content
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ddmContent.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the name of this d d m content.
	*
	* @return the name of this d d m content
	*/
	public java.lang.String getName() {
		return _ddmContent.getName();
	}

	/**
	* Returns the localized name of this d d m content in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this d d m content
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _ddmContent.getName(locale);
	}

	/**
	* Returns the localized name of this d d m content in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m content. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmContent.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this d d m content in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this d d m content
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _ddmContent.getName(languageId);
	}

	/**
	* Returns the localized name of this d d m content in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m content
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddmContent.getName(languageId, useDefault);
	}

	public java.lang.String getNameCurrentLanguageId() {
		return _ddmContent.getNameCurrentLanguageId();
	}

	public java.lang.String getNameCurrentValue() {
		return _ddmContent.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this d d m content.
	*
	* @return the locales and localized names of this d d m content
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmContent.getNameMap();
	}

	/**
	* Sets the name of this d d m content.
	*
	* @param name the name of this d d m content
	*/
	public void setName(java.lang.String name) {
		_ddmContent.setName(name);
	}

	/**
	* Sets the localized name of this d d m content in the language.
	*
	* @param name the localized name of this d d m content
	* @param locale the locale of the language
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddmContent.setName(name, locale);
	}

	/**
	* Sets the localized name of this d d m content in the language, and sets the default locale.
	*
	* @param name the localized name of this d d m content
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmContent.setName(name, locale, defaultLocale);
	}

	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_ddmContent.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this d d m content from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d m content
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmContent.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this d d m content from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this d d m content
	* @param defaultLocale the default locale
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmContent.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this d d m content.
	*
	* @return the description of this d d m content
	*/
	public java.lang.String getDescription() {
		return _ddmContent.getDescription();
	}

	/**
	* Sets the description of this d d m content.
	*
	* @param description the description of this d d m content
	*/
	public void setDescription(java.lang.String description) {
		_ddmContent.setDescription(description);
	}

	/**
	* Returns the xml of this d d m content.
	*
	* @return the xml of this d d m content
	*/
	public java.lang.String getXml() {
		return _ddmContent.getXml();
	}

	/**
	* Sets the xml of this d d m content.
	*
	* @param xml the xml of this d d m content
	*/
	public void setXml(java.lang.String xml) {
		_ddmContent.setXml(xml);
	}

	public boolean isNew() {
		return _ddmContent.isNew();
	}

	public void setNew(boolean n) {
		_ddmContent.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddmContent.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddmContent.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddmContent.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmContent.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmContent.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmContent.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmContent.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DDMContentWrapper((DDMContent)_ddmContent.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMContent ddmContent) {
		return _ddmContent.compareTo(ddmContent);
	}

	@Override
	public int hashCode() {
		return _ddmContent.hashCode();
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_ddmContent.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.dynamicdatamapping.model.DDMContent> toCacheModel() {
		return _ddmContent.toCacheModel();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMContent toEscapedModel() {
		return new DDMContentWrapper(_ddmContent.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmContent.toString();
	}

	public java.lang.String toXmlString() {
		return _ddmContent.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddmContent.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public DDMContent getWrappedDDMContent() {
		return _ddmContent;
	}

	public DDMContent getWrappedModel() {
		return _ddmContent;
	}

	public void resetOriginalValues() {
		_ddmContent.resetOriginalValues();
	}

	private DDMContent _ddmContent;
}