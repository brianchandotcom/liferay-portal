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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <p>
 * This class is a wrapper for {@link LayoutVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutVersion
 * @generated
 */
@ProviderType
public class LayoutVersionWrapper
	extends BaseModelWrapper<LayoutVersion>
	implements LayoutVersion, ModelWrapper<LayoutVersion> {

	public LayoutVersionWrapper(LayoutVersion layoutVersion) {
		super(layoutVersion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("layoutVersionId", getLayoutVersionId());
		attributes.put("version", getVersion());
		attributes.put("uuid", getUuid());
		attributes.put("plid", getPlid());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentPlid", getParentPlid());
		attributes.put("privateLayout", isPrivateLayout());
		attributes.put("layoutId", getLayoutId());
		attributes.put("parentLayoutId", getParentLayoutId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("name", getName());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("keywords", getKeywords());
		attributes.put("robots", getRobots());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("hidden", isHidden());
		attributes.put("system", isSystem());
		attributes.put("friendlyURL", getFriendlyURL());
		attributes.put("iconImageId", getIconImageId());
		attributes.put("themeId", getThemeId());
		attributes.put("colorSchemeId", getColorSchemeId());
		attributes.put("css", getCss());
		attributes.put("priority", getPriority());
		attributes.put("layoutPrototypeUuid", getLayoutPrototypeUuid());
		attributes.put(
			"layoutPrototypeLinkEnabled", isLayoutPrototypeLinkEnabled());
		attributes.put(
			"sourcePrototypeLayoutUuid", getSourcePrototypeLayoutUuid());
		attributes.put("publishDate", getPublishDate());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long layoutVersionId = (Long)attributes.get("layoutVersionId");

		if (layoutVersionId != null) {
			setLayoutVersionId(layoutVersionId);
		}

		Integer version = (Integer)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

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

		Long parentPlid = (Long)attributes.get("parentPlid");

		if (parentPlid != null) {
			setParentPlid(parentPlid);
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

		Boolean system = (Boolean)attributes.get("system");

		if (system != null) {
			setSystem(system);
		}

		String friendlyURL = (String)attributes.get("friendlyURL");

		if (friendlyURL != null) {
			setFriendlyURL(friendlyURL);
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

		Date publishDate = (Date)attributes.get("publishDate");

		if (publishDate != null) {
			setPublishDate(publishDate);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the fully qualified class name of this layout version.
	 *
	 * @return the fully qualified class name of this layout version
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this layout version.
	 *
	 * @return the class name ID of this layout version
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this layout version.
	 *
	 * @return the class pk of this layout version
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the color scheme ID of this layout version.
	 *
	 * @return the color scheme ID of this layout version
	 */
	@Override
	public String getColorSchemeId() {
		return model.getColorSchemeId();
	}

	/**
	 * Returns the company ID of this layout version.
	 *
	 * @return the company ID of this layout version
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this layout version.
	 *
	 * @return the create date of this layout version
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the css of this layout version.
	 *
	 * @return the css of this layout version
	 */
	@Override
	public String getCss() {
		return model.getCss();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the description of this layout version.
	 *
	 * @return the description of this layout version
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the localized description of this layout version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this layout version
	 */
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	 * Returns the localized description of this layout version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this layout version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	 * Returns the localized description of this layout version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this layout version
	 */
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	 * Returns the localized description of this layout version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this layout version
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
	 * Returns a map of the locales and localized descriptions of this layout version.
	 *
	 * @return the locales and localized descriptions of this layout version
	 */
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	 * Returns the friendly url of this layout version.
	 *
	 * @return the friendly url of this layout version
	 */
	@Override
	public String getFriendlyURL() {
		return model.getFriendlyURL();
	}

	/**
	 * Returns the group ID of this layout version.
	 *
	 * @return the group ID of this layout version
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the hidden of this layout version.
	 *
	 * @return the hidden of this layout version
	 */
	@Override
	public boolean getHidden() {
		return model.getHidden();
	}

	/**
	 * Returns the icon image ID of this layout version.
	 *
	 * @return the icon image ID of this layout version
	 */
	@Override
	public long getIconImageId() {
		return model.getIconImageId();
	}

	/**
	 * Returns the keywords of this layout version.
	 *
	 * @return the keywords of this layout version
	 */
	@Override
	public String getKeywords() {
		return model.getKeywords();
	}

	/**
	 * Returns the localized keywords of this layout version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized keywords of this layout version
	 */
	@Override
	public String getKeywords(java.util.Locale locale) {
		return model.getKeywords(locale);
	}

	/**
	 * Returns the localized keywords of this layout version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized keywords of this layout version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getKeywords(java.util.Locale locale, boolean useDefault) {
		return model.getKeywords(locale, useDefault);
	}

	/**
	 * Returns the localized keywords of this layout version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized keywords of this layout version
	 */
	@Override
	public String getKeywords(String languageId) {
		return model.getKeywords(languageId);
	}

	/**
	 * Returns the localized keywords of this layout version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized keywords of this layout version
	 */
	@Override
	public String getKeywords(String languageId, boolean useDefault) {
		return model.getKeywords(languageId, useDefault);
	}

	@Override
	public String getKeywordsCurrentLanguageId() {
		return model.getKeywordsCurrentLanguageId();
	}

	@Override
	public String getKeywordsCurrentValue() {
		return model.getKeywordsCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized keywordses of this layout version.
	 *
	 * @return the locales and localized keywordses of this layout version
	 */
	@Override
	public Map<java.util.Locale, String> getKeywordsMap() {
		return model.getKeywordsMap();
	}

	/**
	 * Returns the last publish date of this layout version.
	 *
	 * @return the last publish date of this layout version
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the layout ID of this layout version.
	 *
	 * @return the layout ID of this layout version
	 */
	@Override
	public long getLayoutId() {
		return model.getLayoutId();
	}

	/**
	 * Returns the layout prototype link enabled of this layout version.
	 *
	 * @return the layout prototype link enabled of this layout version
	 */
	@Override
	public boolean getLayoutPrototypeLinkEnabled() {
		return model.getLayoutPrototypeLinkEnabled();
	}

	/**
	 * Returns the layout prototype uuid of this layout version.
	 *
	 * @return the layout prototype uuid of this layout version
	 */
	@Override
	public String getLayoutPrototypeUuid() {
		return model.getLayoutPrototypeUuid();
	}

	/**
	 * Returns the layout version ID of this layout version.
	 *
	 * @return the layout version ID of this layout version
	 */
	@Override
	public long getLayoutVersionId() {
		return model.getLayoutVersionId();
	}

	/**
	 * Returns the modified date of this layout version.
	 *
	 * @return the modified date of this layout version
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this layout version.
	 *
	 * @return the name of this layout version
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this layout version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this layout version
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this layout version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this layout version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this layout version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this layout version
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this layout version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this layout version
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
	 * Returns a map of the locales and localized names of this layout version.
	 *
	 * @return the locales and localized names of this layout version
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the parent layout ID of this layout version.
	 *
	 * @return the parent layout ID of this layout version
	 */
	@Override
	public long getParentLayoutId() {
		return model.getParentLayoutId();
	}

	/**
	 * Returns the parent plid of this layout version.
	 *
	 * @return the parent plid of this layout version
	 */
	@Override
	public long getParentPlid() {
		return model.getParentPlid();
	}

	/**
	 * Returns the plid of this layout version.
	 *
	 * @return the plid of this layout version
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the primary key of this layout version.
	 *
	 * @return the primary key of this layout version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this layout version.
	 *
	 * @return the priority of this layout version
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the private layout of this layout version.
	 *
	 * @return the private layout of this layout version
	 */
	@Override
	public boolean getPrivateLayout() {
		return model.getPrivateLayout();
	}

	/**
	 * Returns the publish date of this layout version.
	 *
	 * @return the publish date of this layout version
	 */
	@Override
	public Date getPublishDate() {
		return model.getPublishDate();
	}

	/**
	 * Returns the robots of this layout version.
	 *
	 * @return the robots of this layout version
	 */
	@Override
	public String getRobots() {
		return model.getRobots();
	}

	/**
	 * Returns the localized robots of this layout version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized robots of this layout version
	 */
	@Override
	public String getRobots(java.util.Locale locale) {
		return model.getRobots(locale);
	}

	/**
	 * Returns the localized robots of this layout version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized robots of this layout version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getRobots(java.util.Locale locale, boolean useDefault) {
		return model.getRobots(locale, useDefault);
	}

	/**
	 * Returns the localized robots of this layout version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized robots of this layout version
	 */
	@Override
	public String getRobots(String languageId) {
		return model.getRobots(languageId);
	}

	/**
	 * Returns the localized robots of this layout version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized robots of this layout version
	 */
	@Override
	public String getRobots(String languageId, boolean useDefault) {
		return model.getRobots(languageId, useDefault);
	}

	@Override
	public String getRobotsCurrentLanguageId() {
		return model.getRobotsCurrentLanguageId();
	}

	@Override
	public String getRobotsCurrentValue() {
		return model.getRobotsCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized robotses of this layout version.
	 *
	 * @return the locales and localized robotses of this layout version
	 */
	@Override
	public Map<java.util.Locale, String> getRobotsMap() {
		return model.getRobotsMap();
	}

	/**
	 * Returns the source prototype layout uuid of this layout version.
	 *
	 * @return the source prototype layout uuid of this layout version
	 */
	@Override
	public String getSourcePrototypeLayoutUuid() {
		return model.getSourcePrototypeLayoutUuid();
	}

	/**
	 * Returns the system of this layout version.
	 *
	 * @return the system of this layout version
	 */
	@Override
	public boolean getSystem() {
		return model.getSystem();
	}

	/**
	 * Returns the theme ID of this layout version.
	 *
	 * @return the theme ID of this layout version
	 */
	@Override
	public String getThemeId() {
		return model.getThemeId();
	}

	/**
	 * Returns the title of this layout version.
	 *
	 * @return the title of this layout version
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the localized title of this layout version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized title of this layout version
	 */
	@Override
	public String getTitle(java.util.Locale locale) {
		return model.getTitle(locale);
	}

	/**
	 * Returns the localized title of this layout version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this layout version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getTitle(java.util.Locale locale, boolean useDefault) {
		return model.getTitle(locale, useDefault);
	}

	/**
	 * Returns the localized title of this layout version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized title of this layout version
	 */
	@Override
	public String getTitle(String languageId) {
		return model.getTitle(languageId);
	}

	/**
	 * Returns the localized title of this layout version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this layout version
	 */
	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return model.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return model.getTitleCurrentLanguageId();
	}

	@Override
	public String getTitleCurrentValue() {
		return model.getTitleCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized titles of this layout version.
	 *
	 * @return the locales and localized titles of this layout version
	 */
	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return model.getTitleMap();
	}

	/**
	 * Returns the type of this layout version.
	 *
	 * @return the type of this layout version
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the type settings of this layout version.
	 *
	 * @return the type settings of this layout version
	 */
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
	}

	/**
	 * Returns the user ID of this layout version.
	 *
	 * @return the user ID of this layout version
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this layout version.
	 *
	 * @return the user name of this layout version
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this layout version.
	 *
	 * @return the user uuid of this layout version
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this layout version.
	 *
	 * @return the uuid of this layout version
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this layout version.
	 *
	 * @return the version of this layout version
	 */
	@Override
	public int getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns <code>true</code> if this layout version is hidden.
	 *
	 * @return <code>true</code> if this layout version is hidden; <code>false</code> otherwise
	 */
	@Override
	public boolean isHidden() {
		return model.isHidden();
	}

	/**
	 * Returns <code>true</code> if this layout version is layout prototype link enabled.
	 *
	 * @return <code>true</code> if this layout version is layout prototype link enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isLayoutPrototypeLinkEnabled() {
		return model.isLayoutPrototypeLinkEnabled();
	}

	/**
	 * Returns <code>true</code> if this layout version is private layout.
	 *
	 * @return <code>true</code> if this layout version is private layout; <code>false</code> otherwise
	 */
	@Override
	public boolean isPrivateLayout() {
		return model.isPrivateLayout();
	}

	/**
	 * Returns <code>true</code> if this layout version is system.
	 *
	 * @return <code>true</code> if this layout version is system; <code>false</code> otherwise
	 */
	@Override
	public boolean isSystem() {
		return model.isSystem();
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

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this layout version.
	 *
	 * @param classNameId the class name ID of this layout version
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this layout version.
	 *
	 * @param classPK the class pk of this layout version
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the color scheme ID of this layout version.
	 *
	 * @param colorSchemeId the color scheme ID of this layout version
	 */
	@Override
	public void setColorSchemeId(String colorSchemeId) {
		model.setColorSchemeId(colorSchemeId);
	}

	/**
	 * Sets the company ID of this layout version.
	 *
	 * @param companyId the company ID of this layout version
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this layout version.
	 *
	 * @param createDate the create date of this layout version
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the css of this layout version.
	 *
	 * @param css the css of this layout version
	 */
	@Override
	public void setCss(String css) {
		model.setCss(css);
	}

	/**
	 * Sets the description of this layout version.
	 *
	 * @param description the description of this layout version
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the localized description of this layout version in the language.
	 *
	 * @param description the localized description of this layout version
	 * @param locale the locale of the language
	 */
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	 * Sets the localized description of this layout version in the language, and sets the default locale.
	 *
	 * @param description the localized description of this layout version
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescription(
		String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		model.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized descriptions of this layout version from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this layout version
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		model.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the localized descriptions of this layout version from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this layout version
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {

		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	 * Sets the friendly url of this layout version.
	 *
	 * @param friendlyURL the friendly url of this layout version
	 */
	@Override
	public void setFriendlyURL(String friendlyURL) {
		model.setFriendlyURL(friendlyURL);
	}

	/**
	 * Sets the group ID of this layout version.
	 *
	 * @param groupId the group ID of this layout version
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets whether this layout version is hidden.
	 *
	 * @param hidden the hidden of this layout version
	 */
	@Override
	public void setHidden(boolean hidden) {
		model.setHidden(hidden);
	}

	/**
	 * Sets the icon image ID of this layout version.
	 *
	 * @param iconImageId the icon image ID of this layout version
	 */
	@Override
	public void setIconImageId(long iconImageId) {
		model.setIconImageId(iconImageId);
	}

	/**
	 * Sets the keywords of this layout version.
	 *
	 * @param keywords the keywords of this layout version
	 */
	@Override
	public void setKeywords(String keywords) {
		model.setKeywords(keywords);
	}

	/**
	 * Sets the localized keywords of this layout version in the language.
	 *
	 * @param keywords the localized keywords of this layout version
	 * @param locale the locale of the language
	 */
	@Override
	public void setKeywords(String keywords, java.util.Locale locale) {
		model.setKeywords(keywords, locale);
	}

	/**
	 * Sets the localized keywords of this layout version in the language, and sets the default locale.
	 *
	 * @param keywords the localized keywords of this layout version
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setKeywords(
		String keywords, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setKeywords(keywords, locale, defaultLocale);
	}

	@Override
	public void setKeywordsCurrentLanguageId(String languageId) {
		model.setKeywordsCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized keywordses of this layout version from the map of locales and localized keywordses.
	 *
	 * @param keywordsMap the locales and localized keywordses of this layout version
	 */
	@Override
	public void setKeywordsMap(Map<java.util.Locale, String> keywordsMap) {
		model.setKeywordsMap(keywordsMap);
	}

	/**
	 * Sets the localized keywordses of this layout version from the map of locales and localized keywordses, and sets the default locale.
	 *
	 * @param keywordsMap the locales and localized keywordses of this layout version
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setKeywordsMap(
		Map<java.util.Locale, String> keywordsMap,
		java.util.Locale defaultLocale) {

		model.setKeywordsMap(keywordsMap, defaultLocale);
	}

	/**
	 * Sets the last publish date of this layout version.
	 *
	 * @param lastPublishDate the last publish date of this layout version
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the layout ID of this layout version.
	 *
	 * @param layoutId the layout ID of this layout version
	 */
	@Override
	public void setLayoutId(long layoutId) {
		model.setLayoutId(layoutId);
	}

	/**
	 * Sets whether this layout version is layout prototype link enabled.
	 *
	 * @param layoutPrototypeLinkEnabled the layout prototype link enabled of this layout version
	 */
	@Override
	public void setLayoutPrototypeLinkEnabled(
		boolean layoutPrototypeLinkEnabled) {

		model.setLayoutPrototypeLinkEnabled(layoutPrototypeLinkEnabled);
	}

	/**
	 * Sets the layout prototype uuid of this layout version.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid of this layout version
	 */
	@Override
	public void setLayoutPrototypeUuid(String layoutPrototypeUuid) {
		model.setLayoutPrototypeUuid(layoutPrototypeUuid);
	}

	/**
	 * Sets the layout version ID of this layout version.
	 *
	 * @param layoutVersionId the layout version ID of this layout version
	 */
	@Override
	public void setLayoutVersionId(long layoutVersionId) {
		model.setLayoutVersionId(layoutVersionId);
	}

	/**
	 * Sets the modified date of this layout version.
	 *
	 * @param modifiedDate the modified date of this layout version
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this layout version.
	 *
	 * @param name the name of this layout version
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this layout version in the language.
	 *
	 * @param name the localized name of this layout version
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this layout version in the language, and sets the default locale.
	 *
	 * @param name the localized name of this layout version
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
	 * Sets the localized names of this layout version from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this layout version
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this layout version from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this layout version
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the parent layout ID of this layout version.
	 *
	 * @param parentLayoutId the parent layout ID of this layout version
	 */
	@Override
	public void setParentLayoutId(long parentLayoutId) {
		model.setParentLayoutId(parentLayoutId);
	}

	/**
	 * Sets the parent plid of this layout version.
	 *
	 * @param parentPlid the parent plid of this layout version
	 */
	@Override
	public void setParentPlid(long parentPlid) {
		model.setParentPlid(parentPlid);
	}

	/**
	 * Sets the plid of this layout version.
	 *
	 * @param plid the plid of this layout version
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the primary key of this layout version.
	 *
	 * @param primaryKey the primary key of this layout version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this layout version.
	 *
	 * @param priority the priority of this layout version
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets whether this layout version is private layout.
	 *
	 * @param privateLayout the private layout of this layout version
	 */
	@Override
	public void setPrivateLayout(boolean privateLayout) {
		model.setPrivateLayout(privateLayout);
	}

	/**
	 * Sets the publish date of this layout version.
	 *
	 * @param publishDate the publish date of this layout version
	 */
	@Override
	public void setPublishDate(Date publishDate) {
		model.setPublishDate(publishDate);
	}

	/**
	 * Sets the robots of this layout version.
	 *
	 * @param robots the robots of this layout version
	 */
	@Override
	public void setRobots(String robots) {
		model.setRobots(robots);
	}

	/**
	 * Sets the localized robots of this layout version in the language.
	 *
	 * @param robots the localized robots of this layout version
	 * @param locale the locale of the language
	 */
	@Override
	public void setRobots(String robots, java.util.Locale locale) {
		model.setRobots(robots, locale);
	}

	/**
	 * Sets the localized robots of this layout version in the language, and sets the default locale.
	 *
	 * @param robots the localized robots of this layout version
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setRobots(
		String robots, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setRobots(robots, locale, defaultLocale);
	}

	@Override
	public void setRobotsCurrentLanguageId(String languageId) {
		model.setRobotsCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized robotses of this layout version from the map of locales and localized robotses.
	 *
	 * @param robotsMap the locales and localized robotses of this layout version
	 */
	@Override
	public void setRobotsMap(Map<java.util.Locale, String> robotsMap) {
		model.setRobotsMap(robotsMap);
	}

	/**
	 * Sets the localized robotses of this layout version from the map of locales and localized robotses, and sets the default locale.
	 *
	 * @param robotsMap the locales and localized robotses of this layout version
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setRobotsMap(
		Map<java.util.Locale, String> robotsMap,
		java.util.Locale defaultLocale) {

		model.setRobotsMap(robotsMap, defaultLocale);
	}

	/**
	 * Sets the source prototype layout uuid of this layout version.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid of this layout version
	 */
	@Override
	public void setSourcePrototypeLayoutUuid(String sourcePrototypeLayoutUuid) {
		model.setSourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid);
	}

	/**
	 * Sets whether this layout version is system.
	 *
	 * @param system the system of this layout version
	 */
	@Override
	public void setSystem(boolean system) {
		model.setSystem(system);
	}

	/**
	 * Sets the theme ID of this layout version.
	 *
	 * @param themeId the theme ID of this layout version
	 */
	@Override
	public void setThemeId(String themeId) {
		model.setThemeId(themeId);
	}

	/**
	 * Sets the title of this layout version.
	 *
	 * @param title the title of this layout version
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the localized title of this layout version in the language.
	 *
	 * @param title the localized title of this layout version
	 * @param locale the locale of the language
	 */
	@Override
	public void setTitle(String title, java.util.Locale locale) {
		model.setTitle(title, locale);
	}

	/**
	 * Sets the localized title of this layout version in the language, and sets the default locale.
	 *
	 * @param title the localized title of this layout version
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitle(
		String title, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		model.setTitleCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized titles of this layout version from the map of locales and localized titles.
	 *
	 * @param titleMap the locales and localized titles of this layout version
	 */
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		model.setTitleMap(titleMap);
	}

	/**
	 * Sets the localized titles of this layout version from the map of locales and localized titles, and sets the default locale.
	 *
	 * @param titleMap the locales and localized titles of this layout version
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitleMap(
		Map<java.util.Locale, String> titleMap,
		java.util.Locale defaultLocale) {

		model.setTitleMap(titleMap, defaultLocale);
	}

	/**
	 * Sets the type of this layout version.
	 *
	 * @param type the type of this layout version
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the type settings of this layout version.
	 *
	 * @param typeSettings the type settings of this layout version
	 */
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
	}

	/**
	 * Sets the user ID of this layout version.
	 *
	 * @param userId the user ID of this layout version
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this layout version.
	 *
	 * @param userName the user name of this layout version
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this layout version.
	 *
	 * @param userUuid the user uuid of this layout version
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this layout version.
	 *
	 * @param uuid the uuid of this layout version
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this layout version.
	 *
	 * @param version the version of this layout version
	 */
	@Override
	public void setVersion(int version) {
		model.setVersion(version);
	}

	@Override
	public long getVersionedModelId() {
		return model.getVersionedModelId();
	}

	@Override
	public void setVersionedModelId(long id) {
		model.setVersionedModelId(id);
	}

	@Override
	public void populateVersionedModel(Layout layout) {
		model.populateVersionedModel(layout);
	}

	@Override
	public Layout toVersionedModel() {
		return model.toVersionedModel();
	}

	@Override
	protected LayoutVersionWrapper wrap(LayoutVersion layoutVersion) {
		return new LayoutVersionWrapper(layoutVersion);
	}

}