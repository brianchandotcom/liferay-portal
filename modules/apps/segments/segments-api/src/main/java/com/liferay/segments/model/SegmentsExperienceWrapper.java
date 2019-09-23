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

package com.liferay.segments.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SegmentsExperience}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsExperience
 * @generated
 */
public class SegmentsExperienceWrapper
	extends BaseModelWrapper<SegmentsExperience>
	implements ModelWrapper<SegmentsExperience>, SegmentsExperience {

	public SegmentsExperienceWrapper(SegmentsExperience segmentsExperience) {
		super(segmentsExperience);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("segmentsExperienceId", getSegmentsExperienceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("segmentsEntryId", getSegmentsEntryId());
		attributes.put("segmentsExperienceKey", getSegmentsExperienceKey());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("name", getName());
		attributes.put("priority", getPriority());
		attributes.put("active", isActive());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Long segmentsExperienceId = (Long)attributes.get(
			"segmentsExperienceId");

		if (segmentsExperienceId != null) {
			setSegmentsExperienceId(segmentsExperienceId);
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

		Long segmentsEntryId = (Long)attributes.get("segmentsEntryId");

		if (segmentsEntryId != null) {
			setSegmentsEntryId(segmentsEntryId);
		}

		String segmentsExperienceKey = (String)attributes.get(
			"segmentsExperienceKey");

		if (segmentsExperienceKey != null) {
			setSegmentsExperienceKey(segmentsExperienceKey);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	 * Returns the active of this segments experience.
	 *
	 * @return the active of this segments experience
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
	 * Returns the fully qualified class name of this segments experience.
	 *
	 * @return the fully qualified class name of this segments experience
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this segments experience.
	 *
	 * @return the class name ID of this segments experience
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this segments experience.
	 *
	 * @return the class pk of this segments experience
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this segments experience.
	 *
	 * @return the company ID of this segments experience
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this segments experience.
	 *
	 * @return the create date of this segments experience
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
	 * Returns the group ID of this segments experience.
	 *
	 * @return the group ID of this segments experience
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this segments experience.
	 *
	 * @return the last publish date of this segments experience
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this segments experience.
	 *
	 * @return the modified date of this segments experience
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this segments experience.
	 *
	 * @return the mvcc version of this segments experience
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this segments experience.
	 *
	 * @return the name of this segments experience
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this segments experience in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this segments experience
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this segments experience in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this segments experience. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this segments experience in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this segments experience
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this segments experience in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this segments experience
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
	 * Returns a map of the locales and localized names of this segments experience.
	 *
	 * @return the locales and localized names of this segments experience
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the primary key of this segments experience.
	 *
	 * @return the primary key of this segments experience
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this segments experience.
	 *
	 * @return the priority of this segments experience
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the segments entry ID of this segments experience.
	 *
	 * @return the segments entry ID of this segments experience
	 */
	@Override
	public long getSegmentsEntryId() {
		return model.getSegmentsEntryId();
	}

	/**
	 * Returns the segments experience ID of this segments experience.
	 *
	 * @return the segments experience ID of this segments experience
	 */
	@Override
	public long getSegmentsExperienceId() {
		return model.getSegmentsExperienceId();
	}

	/**
	 * Returns the segments experience key of this segments experience.
	 *
	 * @return the segments experience key of this segments experience
	 */
	@Override
	public String getSegmentsExperienceKey() {
		return model.getSegmentsExperienceKey();
	}

	/**
	 * Returns the user ID of this segments experience.
	 *
	 * @return the user ID of this segments experience
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this segments experience.
	 *
	 * @return the user name of this segments experience
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this segments experience.
	 *
	 * @return the user uuid of this segments experience
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this segments experience.
	 *
	 * @return the uuid of this segments experience
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public boolean hasSegmentsExperiment() {
		return model.hasSegmentsExperiment();
	}

	/**
	 * Returns <code>true</code> if this segments experience is active.
	 *
	 * @return <code>true</code> if this segments experience is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a segments experience model instance should use the <code>SegmentsExperience</code> interface instead.
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
	 * Sets whether this segments experience is active.
	 *
	 * @param active the active of this segments experience
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this segments experience.
	 *
	 * @param classNameId the class name ID of this segments experience
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this segments experience.
	 *
	 * @param classPK the class pk of this segments experience
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this segments experience.
	 *
	 * @param companyId the company ID of this segments experience
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this segments experience.
	 *
	 * @param createDate the create date of this segments experience
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this segments experience.
	 *
	 * @param groupId the group ID of this segments experience
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this segments experience.
	 *
	 * @param lastPublishDate the last publish date of this segments experience
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this segments experience.
	 *
	 * @param modifiedDate the modified date of this segments experience
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this segments experience.
	 *
	 * @param mvccVersion the mvcc version of this segments experience
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this segments experience.
	 *
	 * @param name the name of this segments experience
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this segments experience in the language.
	 *
	 * @param name the localized name of this segments experience
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this segments experience in the language, and sets the default locale.
	 *
	 * @param name the localized name of this segments experience
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
	 * Sets the localized names of this segments experience from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this segments experience
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this segments experience from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this segments experience
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the primary key of this segments experience.
	 *
	 * @param primaryKey the primary key of this segments experience
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this segments experience.
	 *
	 * @param priority the priority of this segments experience
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the segments entry ID of this segments experience.
	 *
	 * @param segmentsEntryId the segments entry ID of this segments experience
	 */
	@Override
	public void setSegmentsEntryId(long segmentsEntryId) {
		model.setSegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Sets the segments experience ID of this segments experience.
	 *
	 * @param segmentsExperienceId the segments experience ID of this segments experience
	 */
	@Override
	public void setSegmentsExperienceId(long segmentsExperienceId) {
		model.setSegmentsExperienceId(segmentsExperienceId);
	}

	/**
	 * Sets the segments experience key of this segments experience.
	 *
	 * @param segmentsExperienceKey the segments experience key of this segments experience
	 */
	@Override
	public void setSegmentsExperienceKey(String segmentsExperienceKey) {
		model.setSegmentsExperienceKey(segmentsExperienceKey);
	}

	/**
	 * Sets the user ID of this segments experience.
	 *
	 * @param userId the user ID of this segments experience
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this segments experience.
	 *
	 * @param userName the user name of this segments experience
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this segments experience.
	 *
	 * @param userUuid the user uuid of this segments experience
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this segments experience.
	 *
	 * @param uuid the uuid of this segments experience
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
	protected SegmentsExperienceWrapper wrap(
		SegmentsExperience segmentsExperience) {

		return new SegmentsExperienceWrapper(segmentsExperience);
	}

}