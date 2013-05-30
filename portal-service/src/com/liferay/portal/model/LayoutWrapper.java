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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Layout}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Layout
 * @generated
 */
public class LayoutWrapper implements Layout, ModelWrapper<Layout> {
	public LayoutWrapper(Layout layout) {
		_layout = layout;
	}

	@Override
	public Class<?> getModelClass() {
		return Layout.class;
	}

	@Override
	public String getModelClassName() {
		return Layout.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("plid", getPlid());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("privateLayout", getPrivateLayout());
		attributes.put("layoutId", getLayoutId());
		attributes.put("parentLayoutId", getParentLayoutId());
		attributes.put("name", getName());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("keywords", getKeywords());
		attributes.put("robots", getRobots());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("hidden", getHidden());
		attributes.put("friendlyURL", getFriendlyURL());
		attributes.put("iconImage", getIconImage());
		attributes.put("iconImageId", getIconImageId());
		attributes.put("themeId", getThemeId());
		attributes.put("colorSchemeId", getColorSchemeId());
		attributes.put("wapThemeId", getWapThemeId());
		attributes.put("wapColorSchemeId", getWapColorSchemeId());
		attributes.put("css", getCss());
		attributes.put("priority", getPriority());
		attributes.put("layoutPrototypeUuid", getLayoutPrototypeUuid());
		attributes.put("layoutPrototypeLinkEnabled",
			getLayoutPrototypeLinkEnabled());
		attributes.put("sourcePrototypeLayoutUuid",
			getSourcePrototypeLayoutUuid());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
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

		Boolean privateLayout = (Boolean)attributes.get("privateLayout");

		if (privateLayout != null) {
			setPrivateLayout(privateLayout);
		}

		Long layoutId = (Long)attributes.get("layoutId");

		if (layoutId != null) {
			setLayoutId(layoutId);
		}

		Long parentLayoutId = (Long)attributes.get("parentLayoutId");

		if (parentLayoutId != null) {
			setParentLayoutId(parentLayoutId);
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

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Boolean hidden = (Boolean)attributes.get("hidden");

		if (hidden != null) {
			setHidden(hidden);
		}

		String friendlyURL = (String)attributes.get("friendlyURL");

		if (friendlyURL != null) {
			setFriendlyURL(friendlyURL);
		}

		Boolean iconImage = (Boolean)attributes.get("iconImage");

		if (iconImage != null) {
			setIconImage(iconImage);
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

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		String layoutPrototypeUuid = (String)attributes.get(
				"layoutPrototypeUuid");

		if (layoutPrototypeUuid != null) {
			setLayoutPrototypeUuid(layoutPrototypeUuid);
		}

		Boolean layoutPrototypeLinkEnabled = (Boolean)attributes.get(
				"layoutPrototypeLinkEnabled");

		if (layoutPrototypeLinkEnabled != null) {
			setLayoutPrototypeLinkEnabled(layoutPrototypeLinkEnabled);
		}

		String sourcePrototypeLayoutUuid = (String)attributes.get(
				"sourcePrototypeLayoutUuid");

		if (sourcePrototypeLayoutUuid != null) {
			setSourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid);
		}
	}

	/**
	* Returns the primary key of this layout.
	*
	* @return the primary key of this layout
	*/
	@Override
	public long getPrimaryKey() {
		return _layout.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout.
	*
	* @param primaryKey the primary key of this layout
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_layout.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this layout.
	*
	* @return the uuid of this layout
	*/
	@Override
	public java.lang.String getUuid() {
		return _layout.getUuid();
	}

	/**
	* Sets the uuid of this layout.
	*
	* @param uuid the uuid of this layout
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_layout.setUuid(uuid);
	}

	/**
	* Returns the plid of this layout.
	*
	* @return the plid of this layout
	*/
	@Override
	public long getPlid() {
		return _layout.getPlid();
	}

	/**
	* Sets the plid of this layout.
	*
	* @param plid the plid of this layout
	*/
	@Override
	public void setPlid(long plid) {
		_layout.setPlid(plid);
	}

	/**
	* Returns the group ID of this layout.
	*
	* @return the group ID of this layout
	*/
	@Override
	public long getGroupId() {
		return _layout.getGroupId();
	}

	/**
	* Sets the group ID of this layout.
	*
	* @param groupId the group ID of this layout
	*/
	@Override
	public void setGroupId(long groupId) {
		_layout.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this layout.
	*
	* @return the company ID of this layout
	*/
	@Override
	public long getCompanyId() {
		return _layout.getCompanyId();
	}

	/**
	* Sets the company ID of this layout.
	*
	* @param companyId the company ID of this layout
	*/
	@Override
	public void setCompanyId(long companyId) {
		_layout.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this layout.
	*
	* @return the user ID of this layout
	*/
	@Override
	public long getUserId() {
		return _layout.getUserId();
	}

	/**
	* Sets the user ID of this layout.
	*
	* @param userId the user ID of this layout
	*/
	@Override
	public void setUserId(long userId) {
		_layout.setUserId(userId);
	}

	/**
	* Returns the user uuid of this layout.
	*
	* @return the user uuid of this layout
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layout.getUserUuid();
	}

	/**
	* Sets the user uuid of this layout.
	*
	* @param userUuid the user uuid of this layout
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_layout.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this layout.
	*
	* @return the user name of this layout
	*/
	@Override
	public java.lang.String getUserName() {
		return _layout.getUserName();
	}

	/**
	* Sets the user name of this layout.
	*
	* @param userName the user name of this layout
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_layout.setUserName(userName);
	}

	/**
	* Returns the create date of this layout.
	*
	* @return the create date of this layout
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _layout.getCreateDate();
	}

	/**
	* Sets the create date of this layout.
	*
	* @param createDate the create date of this layout
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_layout.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this layout.
	*
	* @return the modified date of this layout
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _layout.getModifiedDate();
	}

	/**
	* Sets the modified date of this layout.
	*
	* @param modifiedDate the modified date of this layout
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_layout.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the private layout of this layout.
	*
	* @return the private layout of this layout
	*/
	@Override
	public boolean getPrivateLayout() {
		return _layout.getPrivateLayout();
	}

	/**
	* Returns <code>true</code> if this layout is private layout.
	*
	* @return <code>true</code> if this layout is private layout; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrivateLayout() {
		return _layout.isPrivateLayout();
	}

	/**
	* Sets whether this layout is private layout.
	*
	* @param privateLayout the private layout of this layout
	*/
	@Override
	public void setPrivateLayout(boolean privateLayout) {
		_layout.setPrivateLayout(privateLayout);
	}

	/**
	* Returns the layout ID of this layout.
	*
	* @return the layout ID of this layout
	*/
	@Override
	public long getLayoutId() {
		return _layout.getLayoutId();
	}

	/**
	* Sets the layout ID of this layout.
	*
	* @param layoutId the layout ID of this layout
	*/
	@Override
	public void setLayoutId(long layoutId) {
		_layout.setLayoutId(layoutId);
	}

	/**
	* Returns the parent layout ID of this layout.
	*
	* @return the parent layout ID of this layout
	*/
	@Override
	public long getParentLayoutId() {
		return _layout.getParentLayoutId();
	}

	/**
	* Sets the parent layout ID of this layout.
	*
	* @param parentLayoutId the parent layout ID of this layout
	*/
	@Override
	public void setParentLayoutId(long parentLayoutId) {
		_layout.setParentLayoutId(parentLayoutId);
	}

	/**
	* Returns the name of this layout.
	*
	* @return the name of this layout
	*/
	@Override
	public java.lang.String getName() {
		return _layout.getName();
	}

	/**
	* Returns the localized name of this layout in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this layout
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _layout.getName(locale);
	}

	/**
	* Returns the localized name of this layout in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _layout.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this layout in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this layout
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _layout.getName(languageId);
	}

	/**
	* Returns the localized name of this layout in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this layout
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _layout.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _layout.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _layout.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this layout.
	*
	* @return the locales and localized names of this layout
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _layout.getNameMap();
	}

	/**
	* Sets the name of this layout.
	*
	* @param name the name of this layout
	*/
	@Override
	public void setName(java.lang.String name) {
		_layout.setName(name);
	}

	/**
	* Sets the localized name of this layout in the language.
	*
	* @param name the localized name of this layout
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_layout.setName(name, locale);
	}

	/**
	* Sets the localized name of this layout in the language, and sets the default locale.
	*
	* @param name the localized name of this layout
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layout.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_layout.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this layout from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this layout
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_layout.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this layout from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this layout
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_layout.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the title of this layout.
	*
	* @return the title of this layout
	*/
	@Override
	public java.lang.String getTitle() {
		return _layout.getTitle();
	}

	/**
	* Returns the localized title of this layout in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this layout
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _layout.getTitle(locale);
	}

	/**
	* Returns the localized title of this layout in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this layout. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _layout.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this layout in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this layout
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _layout.getTitle(languageId);
	}

	/**
	* Returns the localized title of this layout in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this layout
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _layout.getTitle(languageId, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _layout.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _layout.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this layout.
	*
	* @return the locales and localized titles of this layout
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _layout.getTitleMap();
	}

	/**
	* Sets the title of this layout.
	*
	* @param title the title of this layout
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_layout.setTitle(title);
	}

	/**
	* Sets the localized title of this layout in the language.
	*
	* @param title the localized title of this layout
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_layout.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this layout in the language, and sets the default locale.
	*
	* @param title the localized title of this layout
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layout.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_layout.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this layout from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this layout
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_layout.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this layout from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this layout
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_layout.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Returns the description of this layout.
	*
	* @return the description of this layout
	*/
	@Override
	public java.lang.String getDescription() {
		return _layout.getDescription();
	}

	/**
	* Returns the localized description of this layout in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this layout
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _layout.getDescription(locale);
	}

	/**
	* Returns the localized description of this layout in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this layout. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _layout.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this layout in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this layout
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _layout.getDescription(languageId);
	}

	/**
	* Returns the localized description of this layout in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this layout
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _layout.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _layout.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _layout.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this layout.
	*
	* @return the locales and localized descriptions of this layout
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _layout.getDescriptionMap();
	}

	/**
	* Sets the description of this layout.
	*
	* @param description the description of this layout
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_layout.setDescription(description);
	}

	/**
	* Sets the localized description of this layout in the language.
	*
	* @param description the localized description of this layout
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_layout.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this layout in the language, and sets the default locale.
	*
	* @param description the localized description of this layout
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_layout.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_layout.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this layout from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this layout
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_layout.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this layout from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this layout
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_layout.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the keywords of this layout.
	*
	* @return the keywords of this layout
	*/
	@Override
	public java.lang.String getKeywords() {
		return _layout.getKeywords();
	}

	/**
	* Returns the localized keywords of this layout in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized keywords of this layout
	*/
	@Override
	public java.lang.String getKeywords(java.util.Locale locale) {
		return _layout.getKeywords(locale);
	}

	/**
	* Returns the localized keywords of this layout in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized keywords of this layout. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getKeywords(java.util.Locale locale,
		boolean useDefault) {
		return _layout.getKeywords(locale, useDefault);
	}

	/**
	* Returns the localized keywords of this layout in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized keywords of this layout
	*/
	@Override
	public java.lang.String getKeywords(java.lang.String languageId) {
		return _layout.getKeywords(languageId);
	}

	/**
	* Returns the localized keywords of this layout in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized keywords of this layout
	*/
	@Override
	public java.lang.String getKeywords(java.lang.String languageId,
		boolean useDefault) {
		return _layout.getKeywords(languageId, useDefault);
	}

	@Override
	public java.lang.String getKeywordsCurrentLanguageId() {
		return _layout.getKeywordsCurrentLanguageId();
	}

	@Override
	public java.lang.String getKeywordsCurrentValue() {
		return _layout.getKeywordsCurrentValue();
	}

	/**
	* Returns a map of the locales and localized keywordses of this layout.
	*
	* @return the locales and localized keywordses of this layout
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getKeywordsMap() {
		return _layout.getKeywordsMap();
	}

	/**
	* Sets the keywords of this layout.
	*
	* @param keywords the keywords of this layout
	*/
	@Override
	public void setKeywords(java.lang.String keywords) {
		_layout.setKeywords(keywords);
	}

	/**
	* Sets the localized keywords of this layout in the language.
	*
	* @param keywords the localized keywords of this layout
	* @param locale the locale of the language
	*/
	@Override
	public void setKeywords(java.lang.String keywords, java.util.Locale locale) {
		_layout.setKeywords(keywords, locale);
	}

	/**
	* Sets the localized keywords of this layout in the language, and sets the default locale.
	*
	* @param keywords the localized keywords of this layout
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setKeywords(java.lang.String keywords, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layout.setKeywords(keywords, locale, defaultLocale);
	}

	@Override
	public void setKeywordsCurrentLanguageId(java.lang.String languageId) {
		_layout.setKeywordsCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized keywordses of this layout from the map of locales and localized keywordses.
	*
	* @param keywordsMap the locales and localized keywordses of this layout
	*/
	@Override
	public void setKeywordsMap(
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap) {
		_layout.setKeywordsMap(keywordsMap);
	}

	/**
	* Sets the localized keywordses of this layout from the map of locales and localized keywordses, and sets the default locale.
	*
	* @param keywordsMap the locales and localized keywordses of this layout
	* @param defaultLocale the default locale
	*/
	@Override
	public void setKeywordsMap(
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap,
		java.util.Locale defaultLocale) {
		_layout.setKeywordsMap(keywordsMap, defaultLocale);
	}

	/**
	* Returns the robots of this layout.
	*
	* @return the robots of this layout
	*/
	@Override
	public java.lang.String getRobots() {
		return _layout.getRobots();
	}

	/**
	* Returns the localized robots of this layout in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized robots of this layout
	*/
	@Override
	public java.lang.String getRobots(java.util.Locale locale) {
		return _layout.getRobots(locale);
	}

	/**
	* Returns the localized robots of this layout in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized robots of this layout. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getRobots(java.util.Locale locale,
		boolean useDefault) {
		return _layout.getRobots(locale, useDefault);
	}

	/**
	* Returns the localized robots of this layout in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized robots of this layout
	*/
	@Override
	public java.lang.String getRobots(java.lang.String languageId) {
		return _layout.getRobots(languageId);
	}

	/**
	* Returns the localized robots of this layout in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized robots of this layout
	*/
	@Override
	public java.lang.String getRobots(java.lang.String languageId,
		boolean useDefault) {
		return _layout.getRobots(languageId, useDefault);
	}

	@Override
	public java.lang.String getRobotsCurrentLanguageId() {
		return _layout.getRobotsCurrentLanguageId();
	}

	@Override
	public java.lang.String getRobotsCurrentValue() {
		return _layout.getRobotsCurrentValue();
	}

	/**
	* Returns a map of the locales and localized robotses of this layout.
	*
	* @return the locales and localized robotses of this layout
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getRobotsMap() {
		return _layout.getRobotsMap();
	}

	/**
	* Sets the robots of this layout.
	*
	* @param robots the robots of this layout
	*/
	@Override
	public void setRobots(java.lang.String robots) {
		_layout.setRobots(robots);
	}

	/**
	* Sets the localized robots of this layout in the language.
	*
	* @param robots the localized robots of this layout
	* @param locale the locale of the language
	*/
	@Override
	public void setRobots(java.lang.String robots, java.util.Locale locale) {
		_layout.setRobots(robots, locale);
	}

	/**
	* Sets the localized robots of this layout in the language, and sets the default locale.
	*
	* @param robots the localized robots of this layout
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setRobots(java.lang.String robots, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_layout.setRobots(robots, locale, defaultLocale);
	}

	@Override
	public void setRobotsCurrentLanguageId(java.lang.String languageId) {
		_layout.setRobotsCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized robotses of this layout from the map of locales and localized robotses.
	*
	* @param robotsMap the locales and localized robotses of this layout
	*/
	@Override
	public void setRobotsMap(
		java.util.Map<java.util.Locale, java.lang.String> robotsMap) {
		_layout.setRobotsMap(robotsMap);
	}

	/**
	* Sets the localized robotses of this layout from the map of locales and localized robotses, and sets the default locale.
	*
	* @param robotsMap the locales and localized robotses of this layout
	* @param defaultLocale the default locale
	*/
	@Override
	public void setRobotsMap(
		java.util.Map<java.util.Locale, java.lang.String> robotsMap,
		java.util.Locale defaultLocale) {
		_layout.setRobotsMap(robotsMap, defaultLocale);
	}

	/**
	* Returns the type of this layout.
	*
	* @return the type of this layout
	*/
	@Override
	public java.lang.String getType() {
		return _layout.getType();
	}

	/**
	* Sets the type of this layout.
	*
	* @param type the type of this layout
	*/
	@Override
	public void setType(java.lang.String type) {
		_layout.setType(type);
	}

	/**
	* Returns the type settings of this layout.
	*
	* @return the type settings of this layout
	*/
	@Override
	public java.lang.String getTypeSettings() {
		return _layout.getTypeSettings();
	}

	/**
	* Sets the type settings of this layout.
	*
	* @param typeSettings the type settings of this layout
	*/
	@Override
	public void setTypeSettings(java.lang.String typeSettings) {
		_layout.setTypeSettings(typeSettings);
	}

	/**
	* Returns the hidden of this layout.
	*
	* @return the hidden of this layout
	*/
	@Override
	public boolean getHidden() {
		return _layout.getHidden();
	}

	/**
	* Returns <code>true</code> if this layout is hidden.
	*
	* @return <code>true</code> if this layout is hidden; <code>false</code> otherwise
	*/
	@Override
	public boolean isHidden() {
		return _layout.isHidden();
	}

	/**
	* Sets whether this layout is hidden.
	*
	* @param hidden the hidden of this layout
	*/
	@Override
	public void setHidden(boolean hidden) {
		_layout.setHidden(hidden);
	}

	/**
	* Returns the friendly u r l of this layout.
	*
	* @return the friendly u r l of this layout
	*/
	@Override
	public java.lang.String getFriendlyURL() {
		return _layout.getFriendlyURL();
	}

	/**
	* Sets the friendly u r l of this layout.
	*
	* @param friendlyURL the friendly u r l of this layout
	*/
	@Override
	public void setFriendlyURL(java.lang.String friendlyURL) {
		_layout.setFriendlyURL(friendlyURL);
	}

	/**
	* Returns the icon image of this layout.
	*
	* @return the icon image of this layout
	*/
	@Override
	public boolean getIconImage() {
		return _layout.getIconImage();
	}

	/**
	* Returns <code>true</code> if this layout is icon image.
	*
	* @return <code>true</code> if this layout is icon image; <code>false</code> otherwise
	*/
	@Override
	public boolean isIconImage() {
		return _layout.isIconImage();
	}

	/**
	* Sets whether this layout is icon image.
	*
	* @param iconImage the icon image of this layout
	*/
	@Override
	public void setIconImage(boolean iconImage) {
		_layout.setIconImage(iconImage);
	}

	/**
	* Returns the icon image ID of this layout.
	*
	* @return the icon image ID of this layout
	*/
	@Override
	public long getIconImageId() {
		return _layout.getIconImageId();
	}

	/**
	* Sets the icon image ID of this layout.
	*
	* @param iconImageId the icon image ID of this layout
	*/
	@Override
	public void setIconImageId(long iconImageId) {
		_layout.setIconImageId(iconImageId);
	}

	/**
	* Returns the theme ID of this layout.
	*
	* @return the theme ID of this layout
	*/
	@Override
	public java.lang.String getThemeId() {
		return _layout.getThemeId();
	}

	/**
	* Sets the theme ID of this layout.
	*
	* @param themeId the theme ID of this layout
	*/
	@Override
	public void setThemeId(java.lang.String themeId) {
		_layout.setThemeId(themeId);
	}

	/**
	* Returns the color scheme ID of this layout.
	*
	* @return the color scheme ID of this layout
	*/
	@Override
	public java.lang.String getColorSchemeId() {
		return _layout.getColorSchemeId();
	}

	/**
	* Sets the color scheme ID of this layout.
	*
	* @param colorSchemeId the color scheme ID of this layout
	*/
	@Override
	public void setColorSchemeId(java.lang.String colorSchemeId) {
		_layout.setColorSchemeId(colorSchemeId);
	}

	/**
	* Returns the wap theme ID of this layout.
	*
	* @return the wap theme ID of this layout
	*/
	@Override
	public java.lang.String getWapThemeId() {
		return _layout.getWapThemeId();
	}

	/**
	* Sets the wap theme ID of this layout.
	*
	* @param wapThemeId the wap theme ID of this layout
	*/
	@Override
	public void setWapThemeId(java.lang.String wapThemeId) {
		_layout.setWapThemeId(wapThemeId);
	}

	/**
	* Returns the wap color scheme ID of this layout.
	*
	* @return the wap color scheme ID of this layout
	*/
	@Override
	public java.lang.String getWapColorSchemeId() {
		return _layout.getWapColorSchemeId();
	}

	/**
	* Sets the wap color scheme ID of this layout.
	*
	* @param wapColorSchemeId the wap color scheme ID of this layout
	*/
	@Override
	public void setWapColorSchemeId(java.lang.String wapColorSchemeId) {
		_layout.setWapColorSchemeId(wapColorSchemeId);
	}

	/**
	* Returns the css of this layout.
	*
	* @return the css of this layout
	*/
	@Override
	public java.lang.String getCss() {
		return _layout.getCss();
	}

	/**
	* Sets the css of this layout.
	*
	* @param css the css of this layout
	*/
	@Override
	public void setCss(java.lang.String css) {
		_layout.setCss(css);
	}

	/**
	* Returns the priority of this layout.
	*
	* @return the priority of this layout
	*/
	@Override
	public int getPriority() {
		return _layout.getPriority();
	}

	/**
	* Sets the priority of this layout.
	*
	* @param priority the priority of this layout
	*/
	@Override
	public void setPriority(int priority) {
		_layout.setPriority(priority);
	}

	/**
	* Returns the layout prototype uuid of this layout.
	*
	* @return the layout prototype uuid of this layout
	*/
	@Override
	public java.lang.String getLayoutPrototypeUuid() {
		return _layout.getLayoutPrototypeUuid();
	}

	/**
	* Sets the layout prototype uuid of this layout.
	*
	* @param layoutPrototypeUuid the layout prototype uuid of this layout
	*/
	@Override
	public void setLayoutPrototypeUuid(java.lang.String layoutPrototypeUuid) {
		_layout.setLayoutPrototypeUuid(layoutPrototypeUuid);
	}

	/**
	* Returns the layout prototype link enabled of this layout.
	*
	* @return the layout prototype link enabled of this layout
	*/
	@Override
	public boolean getLayoutPrototypeLinkEnabled() {
		return _layout.getLayoutPrototypeLinkEnabled();
	}

	/**
	* Returns <code>true</code> if this layout is layout prototype link enabled.
	*
	* @return <code>true</code> if this layout is layout prototype link enabled; <code>false</code> otherwise
	*/
	@Override
	public boolean isLayoutPrototypeLinkEnabled() {
		return _layout.isLayoutPrototypeLinkEnabled();
	}

	/**
	* Sets whether this layout is layout prototype link enabled.
	*
	* @param layoutPrototypeLinkEnabled the layout prototype link enabled of this layout
	*/
	@Override
	public void setLayoutPrototypeLinkEnabled(
		boolean layoutPrototypeLinkEnabled) {
		_layout.setLayoutPrototypeLinkEnabled(layoutPrototypeLinkEnabled);
	}

	/**
	* Returns the source prototype layout uuid of this layout.
	*
	* @return the source prototype layout uuid of this layout
	*/
	@Override
	public java.lang.String getSourcePrototypeLayoutUuid() {
		return _layout.getSourcePrototypeLayoutUuid();
	}

	/**
	* Sets the source prototype layout uuid of this layout.
	*
	* @param sourcePrototypeLayoutUuid the source prototype layout uuid of this layout
	*/
	@Override
	public void setSourcePrototypeLayoutUuid(
		java.lang.String sourcePrototypeLayoutUuid) {
		_layout.setSourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid);
	}

	@Override
	public boolean isNew() {
		return _layout.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_layout.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _layout.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layout.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _layout.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _layout.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_layout.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layout.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_layout.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_layout.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layout.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_layout.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutWrapper((Layout)_layout.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Layout layout) {
		return _layout.compareTo(layout);
	}

	@Override
	public int hashCode() {
		return _layout.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Layout> toCacheModel() {
		return _layout.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Layout toEscapedModel() {
		return new LayoutWrapper(_layout.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.Layout toUnescapedModel() {
		return new LayoutWrapper(_layout.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layout.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _layout.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_layout.persist();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Layout> getAllChildren()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layout.getAllChildren();
	}

	@Override
	public long getAncestorLayoutId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getAncestorLayoutId();
	}

	@Override
	public long getAncestorPlid()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getAncestorPlid();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Layout> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getAncestors();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Layout> getChildren()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layout.getChildren();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Layout> getChildren(
		com.liferay.portal.security.permission.PermissionChecker permissionChecker)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getChildren(permissionChecker);
	}

	@Override
	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getColorScheme();
	}

	@Override
	public java.lang.String getCssText()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getCssText();
	}

	@Override
	public java.lang.String getFriendlyURL(java.util.Locale locale) {
		return _layout.getFriendlyURL(locale);
	}

	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getFriendlyURLMap()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layout.getFriendlyURLMap();
	}

	@Override
	public java.lang.String getFriendlyURLsXML()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layout.getFriendlyURLsXML();
	}

	@Override
	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getGroup();
	}

	@Override
	public java.lang.String getHTMLTitle(java.util.Locale locale) {
		return _layout.getHTMLTitle(locale);
	}

	@Override
	public java.lang.String getHTMLTitle(java.lang.String localeLanguageId) {
		return _layout.getHTMLTitle(localeLanguageId);
	}

	@Override
	public com.liferay.portal.model.LayoutSet getLayoutSet()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getLayoutSet();
	}

	@Override
	public com.liferay.portal.model.LayoutType getLayoutType() {
		return _layout.getLayoutType();
	}

	@Override
	public long getParentPlid()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getParentPlid();
	}

	@Override
	public java.lang.String getRegularURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getRegularURL(request);
	}

	@Override
	public java.lang.String getResetLayoutURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getResetLayoutURL(request);
	}

	@Override
	public java.lang.String getResetMaxStateURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getResetMaxStateURL(request);
	}

	@Override
	public com.liferay.portal.model.Group getScopeGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getScopeGroup();
	}

	@Override
	public java.lang.String getTarget() {
		return _layout.getTarget();
	}

	@Override
	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getTheme();
	}

	@Override
	public java.lang.String getThemeSetting(java.lang.String key,
		java.lang.String device) {
		return _layout.getThemeSetting(key, device);
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _layout.getTypeSettingsProperties();
	}

	@Override
	public java.lang.String getTypeSettingsProperty(java.lang.String key) {
		return _layout.getTypeSettingsProperty(key);
	}

	@Override
	public java.lang.String getTypeSettingsProperty(java.lang.String key,
		java.lang.String defaultValue) {
		return _layout.getTypeSettingsProperty(key, defaultValue);
	}

	@Override
	public com.liferay.portal.model.ColorScheme getWapColorScheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getWapColorScheme();
	}

	@Override
	public com.liferay.portal.model.Theme getWapTheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.getWapTheme();
	}

	@Override
	public boolean hasAncestor(long layoutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.hasAncestor(layoutId);
	}

	@Override
	public boolean hasChildren()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layout.hasChildren();
	}

	@Override
	public boolean hasScopeGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.hasScopeGroup();
	}

	@Override
	public boolean isChildSelected(boolean selectable,
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layout.isChildSelected(selectable, layout);
	}

	@Override
	public boolean isContentDisplayPage() {
		return _layout.isContentDisplayPage();
	}

	@Override
	public boolean isFirstChild() {
		return _layout.isFirstChild();
	}

	@Override
	public boolean isFirstParent() {
		return _layout.isFirstParent();
	}

	@Override
	public boolean isInheritLookAndFeel() {
		return _layout.isInheritLookAndFeel();
	}

	@Override
	public boolean isInheritWapLookAndFeel() {
		return _layout.isInheritWapLookAndFeel();
	}

	@Override
	public boolean isLayoutPrototypeLinkActive() {
		return _layout.isLayoutPrototypeLinkActive();
	}

	@Override
	public boolean isPublicLayout() {
		return _layout.isPublicLayout();
	}

	@Override
	public boolean isRootLayout() {
		return _layout.isRootLayout();
	}

	@Override
	public boolean isSelected(boolean selectable,
		com.liferay.portal.model.Layout layout, long ancestorPlid) {
		return _layout.isSelected(selectable, layout, ancestorPlid);
	}

	@Override
	public boolean isSupportsEmbeddedPortlets() {
		return _layout.isSupportsEmbeddedPortlets();
	}

	@Override
	public boolean isTypeArticle() {
		return _layout.isTypeArticle();
	}

	@Override
	public boolean isTypeControlPanel() {
		return _layout.isTypeControlPanel();
	}

	@Override
	public boolean isTypeEmbedded() {
		return _layout.isTypeEmbedded();
	}

	@Override
	public boolean isTypeLinkToLayout() {
		return _layout.isTypeLinkToLayout();
	}

	@Override
	public boolean isTypePanel() {
		return _layout.isTypePanel();
	}

	@Override
	public boolean isTypePortlet() {
		return _layout.isTypePortlet();
	}

	@Override
	public boolean isTypeURL() {
		return _layout.isTypeURL();
	}

	@Override
	public void setLayoutSet(com.liferay.portal.model.LayoutSet layoutSet) {
		_layout.setLayoutSet(layoutSet);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_layout.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Layout getWrappedLayout() {
		return _layout;
	}

	@Override
	public Layout getWrappedModel() {
		return _layout;
	}

	@Override
	public void resetOriginalValues() {
		_layout.resetOriginalValues();
	}

	private Layout _layout;
}