/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LayoutRevision}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevision
 * @generated
 */
@ProviderType
public class LayoutRevisionWrapper implements LayoutRevision,
	ModelWrapper<LayoutRevision> {
	public LayoutRevisionWrapper(LayoutRevision layoutRevision) {
		_layoutRevision = layoutRevision;
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutRevision.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutRevision.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("layoutRevisionId", getLayoutRevisionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("layoutSetBranchId", getLayoutSetBranchId());
		attributes.put("layoutBranchId", getLayoutBranchId());
		attributes.put("parentLayoutRevisionId", getParentLayoutRevisionId());
		attributes.put("head", getHead());
		attributes.put("major", getMajor());
		attributes.put("plid", getPlid());
		attributes.put("privateLayout", getPrivateLayout());
		attributes.put("name", getName());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("keywords", getKeywords());
		attributes.put("robots", getRobots());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("iconImageId", getIconImageId());
		attributes.put("themeId", getThemeId());
		attributes.put("colorSchemeId", getColorSchemeId());
		attributes.put("wapThemeId", getWapThemeId());
		attributes.put("wapColorSchemeId", getWapColorSchemeId());
		attributes.put("css", getCss());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("mvccVersion", getMvccVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long layoutRevisionId = (Long)attributes.get("layoutRevisionId");

		if (layoutRevisionId != null) {
			setLayoutRevisionId(layoutRevisionId);
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

		Long layoutSetBranchId = (Long)attributes.get("layoutSetBranchId");

		if (layoutSetBranchId != null) {
			setLayoutSetBranchId(layoutSetBranchId);
		}

		Long layoutBranchId = (Long)attributes.get("layoutBranchId");

		if (layoutBranchId != null) {
			setLayoutBranchId(layoutBranchId);
		}

		Long parentLayoutRevisionId = (Long)attributes.get(
				"parentLayoutRevisionId");

		if (parentLayoutRevisionId != null) {
			setParentLayoutRevisionId(parentLayoutRevisionId);
		}

		Boolean head = (Boolean)attributes.get("head");

		if (head != null) {
			setHead(head);
		}

		Boolean major = (Boolean)attributes.get("major");

		if (major != null) {
			setMajor(major);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		Boolean privateLayout = (Boolean)attributes.get("privateLayout");

		if (privateLayout != null) {
			setPrivateLayout(privateLayout);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String keywords = (String)attributes.get("keywords");

		if (keywords != null) {
			setKeywords(keywords);
		}

		String robots = (String)attributes.get("robots");

		if (robots != null) {
			setRobots(robots);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Long iconImageId = (Long)attributes.get("iconImageId");

		if (iconImageId != null) {
			setIconImageId(iconImageId);
		}

		String themeId = (String)attributes.get("themeId");

		if (themeId != null) {
			setThemeId(themeId);
		}

		String colorSchemeId = (String)attributes.get("colorSchemeId");

		if (colorSchemeId != null) {
			setColorSchemeId(colorSchemeId);
		}

		String wapThemeId = (String)attributes.get("wapThemeId");

		if (wapThemeId != null) {
			setWapThemeId(wapThemeId);
		}

		String wapColorSchemeId = (String)attributes.get("wapColorSchemeId");

		if (wapColorSchemeId != null) {
			setWapColorSchemeId(wapColorSchemeId);
		}

		String css = (String)attributes.get("css");

		if (css != null) {
			setCss(css);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}

		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}
	}

	/**
	* Returns the primary key of this layout revision.
	*
	* @return the primary key of this layout revision
	*/
	@Override
	public long getPrimaryKey() {
		return _layoutRevision.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout revision.
	*
	* @param primaryKey the primary key of this layout revision
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_layoutRevision.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the layout revision ID of this layout revision.
	*
	* @return the layout revision ID of this layout revision
	*/
	@Override
	public long getLayoutRevisionId() {
		return _layoutRevision.getLayoutRevisionId();
	}

	/**
	* Sets the layout revision ID of this layout revision.
	*
	* @param layoutRevisionId the layout revision ID of this layout revision
	*/
	@Override
	public void setLayoutRevisionId(long layoutRevisionId) {
		_layoutRevision.setLayoutRevisionId(layoutRevisionId);
	}

	/**
	* Returns the group ID of this layout revision.
	*
	* @return the group ID of this layout revision
	*/
	@Override
	public long getGroupId() {
		return _layoutRevision.getGroupId();
	}

	/**
	* Sets the group ID of this layout revision.
	*
	* @param groupId the group ID of this layout revision
	*/
	@Override
	public void setGroupId(long groupId) {
		_layoutRevision.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this layout revision.
	*
	* @return the company ID of this layout revision
	*/
	@Override
	public long getCompanyId() {
		return _layoutRevision.getCompanyId();
	}

	/**
	* Sets the company ID of this layout revision.
	*
	* @param companyId the company ID of this layout revision
	*/
	@Override
	public void setCompanyId(long companyId) {
		_layoutRevision.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this layout revision.
	*
	* @return the user ID of this layout revision
	*/
	@Override
	public long getUserId() {
		return _layoutRevision.getUserId();
	}

	/**
	* Sets the user ID of this layout revision.
	*
	* @param userId the user ID of this layout revision
	*/
	@Override
	public void setUserId(long userId) {
		_layoutRevision.setUserId(userId);
	}

	/**
	* Returns the user uuid of this layout revision.
	*
	* @return the user uuid of this layout revision
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getUserUuid();
	}

	/**
	* Sets the user uuid of this layout revision.
	*
	* @param userUuid the user uuid of this layout revision
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_layoutRevision.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this layout revision.
	*
	* @return the user name of this layout revision
	*/
	@Override
	public java.lang.String getUserName() {
		return _layoutRevision.getUserName();
	}

	/**
	* Sets the user name of this layout revision.
	*
	* @param userName the user name of this layout revision
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_layoutRevision.setUserName(userName);
	}

	/**
	* Returns the create date of this layout revision.
	*
	* @return the create date of this layout revision
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _layoutRevision.getCreateDate();
	}

	/**
	* Sets the create date of this layout revision.
	*
	* @param createDate the create date of this layout revision
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_layoutRevision.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this layout revision.
	*
	* @return the modified date of this layout revision
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _layoutRevision.getModifiedDate();
	}

	/**
	* Sets the modified date of this layout revision.
	*
	* @param modifiedDate the modified date of this layout revision
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_layoutRevision.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the layout set branch ID of this layout revision.
	*
	* @return the layout set branch ID of this layout revision
	*/
	@Override
	public long getLayoutSetBranchId() {
		return _layoutRevision.getLayoutSetBranchId();
	}

	/**
	* Sets the layout set branch ID of this layout revision.
	*
	* @param layoutSetBranchId the layout set branch ID of this layout revision
	*/
	@Override
	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutRevision.setLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Returns the layout branch ID of this layout revision.
	*
	* @return the layout branch ID of this layout revision
	*/
	@Override
	public long getLayoutBranchId() {
		return _layoutRevision.getLayoutBranchId();
	}

	/**
	* Sets the layout branch ID of this layout revision.
	*
	* @param layoutBranchId the layout branch ID of this layout revision
	*/
	@Override
	public void setLayoutBranchId(long layoutBranchId) {
		_layoutRevision.setLayoutBranchId(layoutBranchId);
	}

	/**
	* Returns the parent layout revision ID of this layout revision.
	*
	* @return the parent layout revision ID of this layout revision
	*/
	@Override
	public long getParentLayoutRevisionId() {
		return _layoutRevision.getParentLayoutRevisionId();
	}

	/**
	* Sets the parent layout revision ID of this layout revision.
	*
	* @param parentLayoutRevisionId the parent layout revision ID of this layout revision
	*/
	@Override
	public void setParentLayoutRevisionId(long parentLayoutRevisionId) {
		_layoutRevision.setParentLayoutRevisionId(parentLayoutRevisionId);
	}

	/**
	* Returns the head of this layout revision.
	*
	* @return the head of this layout revision
	*/
	@Override
	public boolean getHead() {
		return _layoutRevision.getHead();
	}

	/**
	* Returns <code>true</code> if this layout revision is head.
	*
	* @return <code>true</code> if this layout revision is head; <code>false</code> otherwise
	*/
	@Override
	public boolean isHead() {
		return _layoutRevision.isHead();
	}

	/**
	* Sets whether this layout revision is head.
	*
	* @param head the head of this layout revision
	*/
	@Override
	public void setHead(boolean head) {
		_layoutRevision.setHead(head);
	}

	/**
	* Returns the major of this layout revision.
	*
	* @return the major of this layout revision
	*/
	@Override
	public boolean getMajor() {
		return _layoutRevision.getMajor();
	}

	/**
	* Returns <code>true</code> if this layout revision is major.
	*
	* @return <code>true</code> if this layout revision is major; <code>false</code> otherwise
	*/
	@Override
	public boolean isMajor() {
		return _layoutRevision.isMajor();
	}

	/**
	* Sets whether this layout revision is major.
	*
	* @param major the major of this layout revision
	*/
	@Override
	public void setMajor(boolean major) {
		_layoutRevision.setMajor(major);
	}

	/**
	* Returns the plid of this layout revision.
	*
	* @return the plid of this layout revision
	*/
	@Override
	public long getPlid() {
		return _layoutRevision.getPlid();
	}

	/**
	* Sets the plid of this layout revision.
	*
	* @param plid the plid of this layout revision
	*/
	@Override
	public void setPlid(long plid) {
		_layoutRevision.setPlid(plid);
	}

	/**
	* Returns the private layout of this layout revision.
	*
	* @return the private layout of this layout revision
	*/
	@Override
	public boolean getPrivateLayout() {
		return _layoutRevision.getPrivateLayout();
	}

	/**
	* Returns <code>true</code> if this layout revision is private layout.
	*
	* @return <code>true</code> if this layout revision is private layout; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrivateLayout() {
		return _layoutRevision.isPrivateLayout();
	}

	/**
	* Sets whether this layout revision is private layout.
	*
	* @param privateLayout the private layout of this layout revision
	*/
	@Override
	public void setPrivateLayout(boolean privateLayout) {
		_layoutRevision.setPrivateLayout(privateLayout);
	}

	/**
	* Returns the name of this layout revision.
	*
	* @return the name of this layout revision
	*/
	@Override
	public java.lang.String getName() {
		return _layoutRevision.getName();
	}

	/**
	* Returns the localized name of this layout revision in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this layout revision
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _layoutRevision.getName(locale);
	}

	/**
	* Returns the localized name of this layout revision in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout revision. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _layoutRevision.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this layout revision in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this layout revision
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _layoutRevision.getName(languageId);
	}

	/**
	* Returns the localized name of this layout revision in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout revision
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _layoutRevision.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _layoutRevision.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _layoutRevision.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this layout revision.
	*
	* @return the locales and localized names of this layout revision
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _layoutRevision.getNameMap();
	}

	/**
	* Sets the name of this layout revision.
	*
	* @param name the name of this layout revision
	*/
	@Override
	public void setName(java.lang.String name) {
		_layoutRevision.setName(name);
	}

	/**
	* Sets the localized name of this layout revision in the language.
	*
	* @param name the localized name of this layout revision
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_layoutRevision.setName(name, locale);
	}

	/**
	* Sets the localized name of this layout revision in the language, and sets the default locale.
	*
	* @param name the localized name of this layout revision
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layoutRevision.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_layoutRevision.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this layout revision from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this layout revision
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_layoutRevision.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this layout revision from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this layout revision
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_layoutRevision.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the title of this layout revision.
	*
	* @return the title of this layout revision
	*/
	@Override
	public java.lang.String getTitle() {
		return _layoutRevision.getTitle();
	}

	/**
	* Returns the localized title of this layout revision in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this layout revision
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _layoutRevision.getTitle(locale);
	}

	/**
	* Returns the localized title of this layout revision in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this layout revision. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _layoutRevision.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this layout revision in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this layout revision
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _layoutRevision.getTitle(languageId);
	}

	/**
	* Returns the localized title of this layout revision in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this layout revision
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _layoutRevision.getTitle(languageId, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _layoutRevision.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _layoutRevision.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this layout revision.
	*
	* @return the locales and localized titles of this layout revision
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _layoutRevision.getTitleMap();
	}

	/**
	* Sets the title of this layout revision.
	*
	* @param title the title of this layout revision
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_layoutRevision.setTitle(title);
	}

	/**
	* Sets the localized title of this layout revision in the language.
	*
	* @param title the localized title of this layout revision
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_layoutRevision.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this layout revision in the language, and sets the default locale.
	*
	* @param title the localized title of this layout revision
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layoutRevision.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_layoutRevision.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this layout revision from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this layout revision
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_layoutRevision.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this layout revision from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this layout revision
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_layoutRevision.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Returns the description of this layout revision.
	*
	* @return the description of this layout revision
	*/
	@Override
	public java.lang.String getDescription() {
		return _layoutRevision.getDescription();
	}

	/**
	* Returns the localized description of this layout revision in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this layout revision
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _layoutRevision.getDescription(locale);
	}

	/**
	* Returns the localized description of this layout revision in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this layout revision. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _layoutRevision.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this layout revision in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this layout revision
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _layoutRevision.getDescription(languageId);
	}

	/**
	* Returns the localized description of this layout revision in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this layout revision
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _layoutRevision.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _layoutRevision.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _layoutRevision.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this layout revision.
	*
	* @return the locales and localized descriptions of this layout revision
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _layoutRevision.getDescriptionMap();
	}

	/**
	* Sets the description of this layout revision.
	*
	* @param description the description of this layout revision
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_layoutRevision.setDescription(description);
	}

	/**
	* Sets the localized description of this layout revision in the language.
	*
	* @param description the localized description of this layout revision
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_layoutRevision.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this layout revision in the language, and sets the default locale.
	*
	* @param description the localized description of this layout revision
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_layoutRevision.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_layoutRevision.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this layout revision from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this layout revision
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_layoutRevision.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this layout revision from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this layout revision
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_layoutRevision.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the keywords of this layout revision.
	*
	* @return the keywords of this layout revision
	*/
	@Override
	public java.lang.String getKeywords() {
		return _layoutRevision.getKeywords();
	}

	/**
	* Returns the localized keywords of this layout revision in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized keywords of this layout revision
	*/
	@Override
	public java.lang.String getKeywords(java.util.Locale locale) {
		return _layoutRevision.getKeywords(locale);
	}

	/**
	* Returns the localized keywords of this layout revision in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized keywords of this layout revision. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getKeywords(java.util.Locale locale,
		boolean useDefault) {
		return _layoutRevision.getKeywords(locale, useDefault);
	}

	/**
	* Returns the localized keywords of this layout revision in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized keywords of this layout revision
	*/
	@Override
	public java.lang.String getKeywords(java.lang.String languageId) {
		return _layoutRevision.getKeywords(languageId);
	}

	/**
	* Returns the localized keywords of this layout revision in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized keywords of this layout revision
	*/
	@Override
	public java.lang.String getKeywords(java.lang.String languageId,
		boolean useDefault) {
		return _layoutRevision.getKeywords(languageId, useDefault);
	}

	@Override
	public java.lang.String getKeywordsCurrentLanguageId() {
		return _layoutRevision.getKeywordsCurrentLanguageId();
	}

	@Override
	public java.lang.String getKeywordsCurrentValue() {
		return _layoutRevision.getKeywordsCurrentValue();
	}

	/**
	* Returns a map of the locales and localized keywordses of this layout revision.
	*
	* @return the locales and localized keywordses of this layout revision
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getKeywordsMap() {
		return _layoutRevision.getKeywordsMap();
	}

	/**
	* Sets the keywords of this layout revision.
	*
	* @param keywords the keywords of this layout revision
	*/
	@Override
	public void setKeywords(java.lang.String keywords) {
		_layoutRevision.setKeywords(keywords);
	}

	/**
	* Sets the localized keywords of this layout revision in the language.
	*
	* @param keywords the localized keywords of this layout revision
	* @param locale the locale of the language
	*/
	@Override
	public void setKeywords(java.lang.String keywords, java.util.Locale locale) {
		_layoutRevision.setKeywords(keywords, locale);
	}

	/**
	* Sets the localized keywords of this layout revision in the language, and sets the default locale.
	*
	* @param keywords the localized keywords of this layout revision
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setKeywords(java.lang.String keywords, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layoutRevision.setKeywords(keywords, locale, defaultLocale);
	}

	@Override
	public void setKeywordsCurrentLanguageId(java.lang.String languageId) {
		_layoutRevision.setKeywordsCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized keywordses of this layout revision from the map of locales and localized keywordses.
	*
	* @param keywordsMap the locales and localized keywordses of this layout revision
	*/
	@Override
	public void setKeywordsMap(
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap) {
		_layoutRevision.setKeywordsMap(keywordsMap);
	}

	/**
	* Sets the localized keywordses of this layout revision from the map of locales and localized keywordses, and sets the default locale.
	*
	* @param keywordsMap the locales and localized keywordses of this layout revision
	* @param defaultLocale the default locale
	*/
	@Override
	public void setKeywordsMap(
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap,
		java.util.Locale defaultLocale) {
		_layoutRevision.setKeywordsMap(keywordsMap, defaultLocale);
	}

	/**
	* Returns the robots of this layout revision.
	*
	* @return the robots of this layout revision
	*/
	@Override
	public java.lang.String getRobots() {
		return _layoutRevision.getRobots();
	}

	/**
	* Returns the localized robots of this layout revision in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized robots of this layout revision
	*/
	@Override
	public java.lang.String getRobots(java.util.Locale locale) {
		return _layoutRevision.getRobots(locale);
	}

	/**
	* Returns the localized robots of this layout revision in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized robots of this layout revision. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getRobots(java.util.Locale locale,
		boolean useDefault) {
		return _layoutRevision.getRobots(locale, useDefault);
	}

	/**
	* Returns the localized robots of this layout revision in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized robots of this layout revision
	*/
	@Override
	public java.lang.String getRobots(java.lang.String languageId) {
		return _layoutRevision.getRobots(languageId);
	}

	/**
	* Returns the localized robots of this layout revision in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized robots of this layout revision
	*/
	@Override
	public java.lang.String getRobots(java.lang.String languageId,
		boolean useDefault) {
		return _layoutRevision.getRobots(languageId, useDefault);
	}

	@Override
	public java.lang.String getRobotsCurrentLanguageId() {
		return _layoutRevision.getRobotsCurrentLanguageId();
	}

	@Override
	public java.lang.String getRobotsCurrentValue() {
		return _layoutRevision.getRobotsCurrentValue();
	}

	/**
	* Returns a map of the locales and localized robotses of this layout revision.
	*
	* @return the locales and localized robotses of this layout revision
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getRobotsMap() {
		return _layoutRevision.getRobotsMap();
	}

	/**
	* Sets the robots of this layout revision.
	*
	* @param robots the robots of this layout revision
	*/
	@Override
	public void setRobots(java.lang.String robots) {
		_layoutRevision.setRobots(robots);
	}

	/**
	* Sets the localized robots of this layout revision in the language.
	*
	* @param robots the localized robots of this layout revision
	* @param locale the locale of the language
	*/
	@Override
	public void setRobots(java.lang.String robots, java.util.Locale locale) {
		_layoutRevision.setRobots(robots, locale);
	}

	/**
	* Sets the localized robots of this layout revision in the language, and sets the default locale.
	*
	* @param robots the localized robots of this layout revision
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setRobots(java.lang.String robots, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layoutRevision.setRobots(robots, locale, defaultLocale);
	}

	@Override
	public void setRobotsCurrentLanguageId(java.lang.String languageId) {
		_layoutRevision.setRobotsCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized robotses of this layout revision from the map of locales and localized robotses.
	*
	* @param robotsMap the locales and localized robotses of this layout revision
	*/
	@Override
	public void setRobotsMap(
		java.util.Map<java.util.Locale, java.lang.String> robotsMap) {
		_layoutRevision.setRobotsMap(robotsMap);
	}

	/**
	* Sets the localized robotses of this layout revision from the map of locales and localized robotses, and sets the default locale.
	*
	* @param robotsMap the locales and localized robotses of this layout revision
	* @param defaultLocale the default locale
	*/
	@Override
	public void setRobotsMap(
		java.util.Map<java.util.Locale, java.lang.String> robotsMap,
		java.util.Locale defaultLocale) {
		_layoutRevision.setRobotsMap(robotsMap, defaultLocale);
	}

	/**
	* Returns the type settings of this layout revision.
	*
	* @return the type settings of this layout revision
	*/
	@Override
	public java.lang.String getTypeSettings() {
		return _layoutRevision.getTypeSettings();
	}

	/**
	* Sets the type settings of this layout revision.
	*
	* @param typeSettings the type settings of this layout revision
	*/
	@Override
	public void setTypeSettings(java.lang.String typeSettings) {
		_layoutRevision.setTypeSettings(typeSettings);
	}

	/**
	* Returns the icon image ID of this layout revision.
	*
	* @return the icon image ID of this layout revision
	*/
	@Override
	public long getIconImageId() {
		return _layoutRevision.getIconImageId();
	}

	/**
	* Sets the icon image ID of this layout revision.
	*
	* @param iconImageId the icon image ID of this layout revision
	*/
	@Override
	public void setIconImageId(long iconImageId) {
		_layoutRevision.setIconImageId(iconImageId);
	}

	/**
	* Returns the theme ID of this layout revision.
	*
	* @return the theme ID of this layout revision
	*/
	@Override
	public java.lang.String getThemeId() {
		return _layoutRevision.getThemeId();
	}

	/**
	* Sets the theme ID of this layout revision.
	*
	* @param themeId the theme ID of this layout revision
	*/
	@Override
	public void setThemeId(java.lang.String themeId) {
		_layoutRevision.setThemeId(themeId);
	}

	/**
	* Returns the color scheme ID of this layout revision.
	*
	* @return the color scheme ID of this layout revision
	*/
	@Override
	public java.lang.String getColorSchemeId() {
		return _layoutRevision.getColorSchemeId();
	}

	/**
	* Sets the color scheme ID of this layout revision.
	*
	* @param colorSchemeId the color scheme ID of this layout revision
	*/
	@Override
	public void setColorSchemeId(java.lang.String colorSchemeId) {
		_layoutRevision.setColorSchemeId(colorSchemeId);
	}

	/**
	* Returns the wap theme ID of this layout revision.
	*
	* @return the wap theme ID of this layout revision
	*/
	@Override
	public java.lang.String getWapThemeId() {
		return _layoutRevision.getWapThemeId();
	}

	/**
	* Sets the wap theme ID of this layout revision.
	*
	* @param wapThemeId the wap theme ID of this layout revision
	*/
	@Override
	public void setWapThemeId(java.lang.String wapThemeId) {
		_layoutRevision.setWapThemeId(wapThemeId);
	}

	/**
	* Returns the wap color scheme ID of this layout revision.
	*
	* @return the wap color scheme ID of this layout revision
	*/
	@Override
	public java.lang.String getWapColorSchemeId() {
		return _layoutRevision.getWapColorSchemeId();
	}

	/**
	* Sets the wap color scheme ID of this layout revision.
	*
	* @param wapColorSchemeId the wap color scheme ID of this layout revision
	*/
	@Override
	public void setWapColorSchemeId(java.lang.String wapColorSchemeId) {
		_layoutRevision.setWapColorSchemeId(wapColorSchemeId);
	}

	/**
	* Returns the css of this layout revision.
	*
	* @return the css of this layout revision
	*/
	@Override
	public java.lang.String getCss() {
		return _layoutRevision.getCss();
	}

	/**
	* Sets the css of this layout revision.
	*
	* @param css the css of this layout revision
	*/
	@Override
	public void setCss(java.lang.String css) {
		_layoutRevision.setCss(css);
	}

	/**
	* Returns the status of this layout revision.
	*
	* @return the status of this layout revision
	*/
	@Override
	public int getStatus() {
		return _layoutRevision.getStatus();
	}

	/**
	* Sets the status of this layout revision.
	*
	* @param status the status of this layout revision
	*/
	@Override
	public void setStatus(int status) {
		_layoutRevision.setStatus(status);
	}

	/**
	* Returns the status by user ID of this layout revision.
	*
	* @return the status by user ID of this layout revision
	*/
	@Override
	public long getStatusByUserId() {
		return _layoutRevision.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this layout revision.
	*
	* @param statusByUserId the status by user ID of this layout revision
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_layoutRevision.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this layout revision.
	*
	* @return the status by user uuid of this layout revision
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this layout revision.
	*
	* @param statusByUserUuid the status by user uuid of this layout revision
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_layoutRevision.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this layout revision.
	*
	* @return the status by user name of this layout revision
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _layoutRevision.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this layout revision.
	*
	* @param statusByUserName the status by user name of this layout revision
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_layoutRevision.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this layout revision.
	*
	* @return the status date of this layout revision
	*/
	@Override
	public java.util.Date getStatusDate() {
		return _layoutRevision.getStatusDate();
	}

	/**
	* Sets the status date of this layout revision.
	*
	* @param statusDate the status date of this layout revision
	*/
	@Override
	public void setStatusDate(java.util.Date statusDate) {
		_layoutRevision.setStatusDate(statusDate);
	}

	/**
	* Returns the mvcc version of this layout revision.
	*
	* @return the mvcc version of this layout revision
	*/
	@Override
	public long getMvccVersion() {
		return _layoutRevision.getMvccVersion();
	}

	/**
	* Sets the mvcc version of this layout revision.
	*
	* @param mvccVersion the mvcc version of this layout revision
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_layoutRevision.setMvccVersion(mvccVersion);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #isApproved()}
	*/
	@Deprecated
	@Override
	public boolean getApproved() {
		return _layoutRevision.getApproved();
	}

	/**
	* Returns <code>true</code> if this layout revision is approved.
	*
	* @return <code>true</code> if this layout revision is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _layoutRevision.isApproved();
	}

	/**
	* Returns <code>true</code> if this layout revision is denied.
	*
	* @return <code>true</code> if this layout revision is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _layoutRevision.isDenied();
	}

	/**
	* Returns <code>true</code> if this layout revision is a draft.
	*
	* @return <code>true</code> if this layout revision is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _layoutRevision.isDraft();
	}

	/**
	* Returns <code>true</code> if this layout revision is expired.
	*
	* @return <code>true</code> if this layout revision is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _layoutRevision.isExpired();
	}

	/**
	* Returns <code>true</code> if this layout revision is inactive.
	*
	* @return <code>true</code> if this layout revision is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _layoutRevision.isInactive();
	}

	/**
	* Returns <code>true</code> if this layout revision is incomplete.
	*
	* @return <code>true</code> if this layout revision is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _layoutRevision.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this layout revision is pending.
	*
	* @return <code>true</code> if this layout revision is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _layoutRevision.isPending();
	}

	/**
	* Returns <code>true</code> if this layout revision is scheduled.
	*
	* @return <code>true</code> if this layout revision is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _layoutRevision.isScheduled();
	}

	@Override
	public boolean isNew() {
		return _layoutRevision.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_layoutRevision.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _layoutRevision.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutRevision.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutRevision.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutRevision.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_layoutRevision.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutRevision.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_layoutRevision.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_layoutRevision.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutRevision.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _layoutRevision.getAvailableLanguageIds();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _layoutRevision.getDefaultLanguageId();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_layoutRevision.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_layoutRevision.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutRevisionWrapper((LayoutRevision)_layoutRevision.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.LayoutRevision layoutRevision) {
		return _layoutRevision.compareTo(layoutRevision);
	}

	@Override
	public int hashCode() {
		return _layoutRevision.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.LayoutRevision> toCacheModel() {
		return _layoutRevision.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.LayoutRevision toEscapedModel() {
		return new LayoutRevisionWrapper(_layoutRevision.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.LayoutRevision toUnescapedModel() {
		return new LayoutRevisionWrapper(_layoutRevision.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutRevision.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _layoutRevision.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_layoutRevision.persist();
	}

	@Override
	public java.util.List<com.liferay.portal.model.LayoutRevision> getChildren()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getChildren();
	}

	@Override
	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getColorScheme();
	}

	@Override
	public java.lang.String getCssText()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getCssText();
	}

	@Override
	public java.lang.String getHTMLTitle(java.util.Locale locale) {
		return _layoutRevision.getHTMLTitle(locale);
	}

	@Override
	public java.lang.String getHTMLTitle(java.lang.String localeLanguageId) {
		return _layoutRevision.getHTMLTitle(localeLanguageId);
	}

	@Override
	public boolean getIconImage() {
		return _layoutRevision.getIconImage();
	}

	@Override
	public com.liferay.portal.model.LayoutBranch getLayoutBranch()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getLayoutBranch();
	}

	@Override
	public com.liferay.portal.model.LayoutSet getLayoutSet()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getLayoutSet();
	}

	@Override
	public java.lang.String getRegularURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getRegularURL(request);
	}

	@Override
	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getTheme();
	}

	@Override
	public java.lang.String getThemeSetting(java.lang.String key,
		java.lang.String device) {
		return _layoutRevision.getThemeSetting(key, device);
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _layoutRevision.getTypeSettingsProperties();
	}

	@Override
	public com.liferay.portal.model.ColorScheme getWapColorScheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getWapColorScheme();
	}

	@Override
	public com.liferay.portal.model.Theme getWapTheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.getWapTheme();
	}

	@Override
	public boolean hasChildren()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevision.hasChildren();
	}

	@Override
	public boolean isContentDisplayPage() {
		return _layoutRevision.isContentDisplayPage();
	}

	@Override
	public boolean isIconImage() {
		return _layoutRevision.isIconImage();
	}

	@Override
	public boolean isInheritLookAndFeel() {
		return _layoutRevision.isInheritLookAndFeel();
	}

	@Override
	public boolean isInheritWapLookAndFeel() {
		return _layoutRevision.isInheritWapLookAndFeel();
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_layoutRevision.setTypeSettingsProperties(typeSettingsProperties);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutRevisionWrapper)) {
			return false;
		}

		LayoutRevisionWrapper layoutRevisionWrapper = (LayoutRevisionWrapper)obj;

		if (Validator.equals(_layoutRevision,
					layoutRevisionWrapper._layoutRevision)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public LayoutRevision getWrappedLayoutRevision() {
		return _layoutRevision;
	}

	@Override
	public LayoutRevision getWrappedModel() {
		return _layoutRevision;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _layoutRevision.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _layoutRevision.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_layoutRevision.resetOriginalValues();
	}

	private LayoutRevision _layoutRevision;
}