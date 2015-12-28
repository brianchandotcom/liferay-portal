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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Group}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Group
 * @generated
 */
@ProviderType
public class GroupWrapper implements Group, ModelWrapper<Group> {
	public GroupWrapper(Group group) {
		_group = group;
	}

	@Override
	public Class<?> getModelClass() {
		return Group.class;
	}

	@Override
	public String getModelClassName() {
		return Group.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("creatorUserId", getCreatorUserId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("parentGroupId", getParentGroupId());
		attributes.put("liveGroupId", getLiveGroupId());
		attributes.put("treePath", getTreePath());
		attributes.put("groupKey", getGroupKey());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("manualMembership", getManualMembership());
		attributes.put("membershipRestriction", getMembershipRestriction());
		attributes.put("friendlyURL", getFriendlyURL());
		attributes.put("site", getSite());
		attributes.put("remoteStagingGroupCount", getRemoteStagingGroupCount());
		attributes.put("inheritContent", getInheritContent());
		attributes.put("active", getActive());

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

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long creatorUserId = (Long)attributes.get("creatorUserId");

		if (creatorUserId != null) {
			setCreatorUserId(creatorUserId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long parentGroupId = (Long)attributes.get("parentGroupId");

		if (parentGroupId != null) {
			setParentGroupId(parentGroupId);
		}

		Long liveGroupId = (Long)attributes.get("liveGroupId");

		if (liveGroupId != null) {
			setLiveGroupId(liveGroupId);
		}

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
		}

		String groupKey = (String)attributes.get("groupKey");

		if (groupKey != null) {
			setGroupKey(groupKey);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Boolean manualMembership = (Boolean)attributes.get("manualMembership");

		if (manualMembership != null) {
			setManualMembership(manualMembership);
		}

		Integer membershipRestriction = (Integer)attributes.get(
				"membershipRestriction");

		if (membershipRestriction != null) {
			setMembershipRestriction(membershipRestriction);
		}

		String friendlyURL = (String)attributes.get("friendlyURL");

		if (friendlyURL != null) {
			setFriendlyURL(friendlyURL);
		}

		Boolean site = (Boolean)attributes.get("site");

		if (site != null) {
			setSite(site);
		}

		Integer remoteStagingGroupCount = (Integer)attributes.get(
				"remoteStagingGroupCount");

		if (remoteStagingGroupCount != null) {
			setRemoteStagingGroupCount(remoteStagingGroupCount);
		}

		Boolean inheritContent = (Boolean)attributes.get("inheritContent");

		if (inheritContent != null) {
			setInheritContent(inheritContent);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	@Override
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _group.buildTreePath();
	}

	@Override
	public void clearStagingGroup() {
		_group.clearStagingGroup();
	}

	@Override
	public java.lang.Object clone() {
		return new GroupWrapper((Group)_group.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Group group) {
		return _group.compareTo(group);
	}

	/**
	* Returns the active of this group.
	*
	* @return the active of this group
	*/
	@Override
	public boolean getActive() {
		return _group.getActive();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _group.getAncestors();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _group.getAvailableLanguageIds();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getChildren(
		boolean site) {
		return _group.getChildren(site);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#getChildrenWithLayouts(boolean, int, int,
	OrderByComparator)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portal.model.Group> getChildrenWithLayouts(
		boolean site, int start, int end) {
		return _group.getChildrenWithLayouts(site, start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getChildrenWithLayouts(
		boolean site, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Group> obc) {
		return _group.getChildrenWithLayouts(site, start, end, obc);
	}

	@Override
	public int getChildrenWithLayoutsCount(boolean site) {
		return _group.getChildrenWithLayoutsCount(site);
	}

	/**
	* Returns the fully qualified class name of this group.
	*
	* @return the fully qualified class name of this group
	*/
	@Override
	public java.lang.String getClassName() {
		return _group.getClassName();
	}

	/**
	* Returns the class name ID of this group.
	*
	* @return the class name ID of this group
	*/
	@Override
	public long getClassNameId() {
		return _group.getClassNameId();
	}

	/**
	* Returns the class p k of this group.
	*
	* @return the class p k of this group
	*/
	@Override
	public long getClassPK() {
		return _group.getClassPK();
	}

	/**
	* Returns the company ID of this group.
	*
	* @return the company ID of this group
	*/
	@Override
	public long getCompanyId() {
		return _group.getCompanyId();
	}

	/**
	* Returns the creator user ID of this group.
	*
	* @return the creator user ID of this group
	*/
	@Override
	public long getCreatorUserId() {
		return _group.getCreatorUserId();
	}

	/**
	* Returns the creator user uuid of this group.
	*
	* @return the creator user uuid of this group
	*/
	@Override
	public java.lang.String getCreatorUserUuid() {
		return _group.getCreatorUserUuid();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _group.getDefaultLanguageId();
	}

	@Override
	public long getDefaultPrivatePlid() {
		return _group.getDefaultPrivatePlid();
	}

	@Override
	public long getDefaultPublicPlid() {
		return _group.getDefaultPublicPlid();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getDescendants(
		boolean site) {
		return _group.getDescendants(site);
	}

	/**
	* Returns the description of this group.
	*
	* @return the description of this group
	*/
	@Override
	public java.lang.String getDescription() {
		return _group.getDescription();
	}

	/**
	* Returns the localized description of this group in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this group
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _group.getDescription(languageId);
	}

	/**
	* Returns the localized description of this group in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this group
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _group.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this group in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this group
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _group.getDescription(locale);
	}

	/**
	* Returns the localized description of this group in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this group. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _group.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _group.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _group.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this group.
	*
	* @return the locales and localized descriptions of this group
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _group.getDescriptionMap();
	}

	@Override
	public java.lang.String getDescriptiveName()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _group.getDescriptiveName();
	}

	@Override
	public java.lang.String getDescriptiveName(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _group.getDescriptiveName(locale);
	}

	@Override
	public java.lang.String getDisplayURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _group.getDisplayURL(themeDisplay);
	}

	@Override
	public java.lang.String getDisplayURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		boolean privateLayout) {
		return _group.getDisplayURL(themeDisplay, privateLayout);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _group.getExpandoBridge();
	}

	/**
	* Returns the friendly u r l of this group.
	*
	* @return the friendly u r l of this group
	*/
	@Override
	public java.lang.String getFriendlyURL() {
		return _group.getFriendlyURL();
	}

	/**
	* Returns the group ID of this group.
	*
	* @return the group ID of this group
	*/
	@Override
	public long getGroupId() {
		return _group.getGroupId();
	}

	/**
	* Returns the group key of this group.
	*
	* @return the group key of this group
	*/
	@Override
	public java.lang.String getGroupKey() {
		return _group.getGroupKey();
	}

	@Override
	public java.lang.String getIconCssClass() {
		return _group.getIconCssClass();
	}

	@Override
	public java.lang.String getIconURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _group.getIconURL(themeDisplay);
	}

	/**
	* Returns the inherit content of this group.
	*
	* @return the inherit content of this group
	*/
	@Override
	public boolean getInheritContent() {
		return _group.getInheritContent();
	}

	@Override
	public java.lang.String getLayoutRootNodeName(boolean privateLayout,
		java.util.Locale locale) {
		return _group.getLayoutRootNodeName(privateLayout, locale);
	}

	@Override
	public com.liferay.portal.model.Group getLiveGroup() {
		return _group.getLiveGroup();
	}

	/**
	* Returns the live group ID of this group.
	*
	* @return the live group ID of this group
	*/
	@Override
	public long getLiveGroupId() {
		return _group.getLiveGroupId();
	}

	@Override
	public java.lang.String getLiveParentTypeSettingsProperty(
		java.lang.String key) {
		return _group.getLiveParentTypeSettingsProperty(key);
	}

	@Override
	public java.lang.String getLogoURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay, boolean useDefault) {
		return _group.getLogoURL(themeDisplay, useDefault);
	}

	/**
	* Returns the manual membership of this group.
	*
	* @return the manual membership of this group
	*/
	@Override
	public boolean getManualMembership() {
		return _group.getManualMembership();
	}

	/**
	* Returns the membership restriction of this group.
	*
	* @return the membership restriction of this group
	*/
	@Override
	public int getMembershipRestriction() {
		return _group.getMembershipRestriction();
	}

	/**
	* Returns the mvcc version of this group.
	*
	* @return the mvcc version of this group
	*/
	@Override
	public long getMvccVersion() {
		return _group.getMvccVersion();
	}

	/**
	* Returns the name of this group.
	*
	* @return the name of this group
	*/
	@Override
	public java.lang.String getName() {
		return _group.getName();
	}

	/**
	* Returns the localized name of this group in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this group
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _group.getName(languageId);
	}

	/**
	* Returns the localized name of this group in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this group
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _group.getName(languageId, useDefault);
	}

	/**
	* Returns the localized name of this group in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this group
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _group.getName(locale);
	}

	/**
	* Returns the localized name of this group in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this group. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _group.getName(locale, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _group.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _group.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this group.
	*
	* @return the locales and localized names of this group
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _group.getNameMap();
	}

	@Override
	public long getOrganizationId() {
		return _group.getOrganizationId();
	}

	@Override
	public com.liferay.portal.model.Group getParentGroup()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _group.getParentGroup();
	}

	/**
	* Returns the parent group ID of this group.
	*
	* @return the parent group ID of this group
	*/
	@Override
	public long getParentGroupId() {
		return _group.getParentGroupId();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getParentLiveGroupTypeSettingsProperties() {
		return _group.getParentLiveGroupTypeSettingsProperties();
	}

	@Override
	public java.lang.String getPathFriendlyURL(boolean privateLayout,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _group.getPathFriendlyURL(privateLayout, themeDisplay);
	}

	/**
	* Returns the primary key of this group.
	*
	* @return the primary key of this group
	*/
	@Override
	public long getPrimaryKey() {
		return _group.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _group.getPrimaryKeyObj();
	}

	@Override
	public com.liferay.portal.model.LayoutSet getPrivateLayoutSet() {
		return _group.getPrivateLayoutSet();
	}

	@Override
	public int getPrivateLayoutsPageCount() {
		return _group.getPrivateLayoutsPageCount();
	}

	@Override
	public com.liferay.portal.model.LayoutSet getPublicLayoutSet() {
		return _group.getPublicLayoutSet();
	}

	@Override
	public int getPublicLayoutsPageCount() {
		return _group.getPublicLayoutsPageCount();
	}

	@Override
	public long getRemoteLiveGroupId() {
		return _group.getRemoteLiveGroupId();
	}

	/**
	* Returns the remote staging group count of this group.
	*
	* @return the remote staging group count of this group
	*/
	@Override
	public int getRemoteStagingGroupCount() {
		return _group.getRemoteStagingGroupCount();
	}

	@Override
	public java.lang.String getScopeDescriptiveName(
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _group.getScopeDescriptiveName(themeDisplay);
	}

	@Override
	public java.lang.String getScopeLabel(
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _group.getScopeLabel(themeDisplay);
	}

	/**
	* Returns the site of this group.
	*
	* @return the site of this group
	*/
	@Override
	public boolean getSite() {
		return _group.getSite();
	}

	@Override
	public com.liferay.portal.model.Group getStagingGroup() {
		return _group.getStagingGroup();
	}

	/**
	* Returns the tree path of this group.
	*
	* @return the tree path of this group
	*/
	@Override
	public java.lang.String getTreePath() {
		return _group.getTreePath();
	}

	/**
	* Returns the type of this group.
	*
	* @return the type of this group
	*/
	@Override
	public int getType() {
		return _group.getType();
	}

	@Override
	public java.lang.String getTypeLabel() {
		return _group.getTypeLabel();
	}

	/**
	* Returns the type settings of this group.
	*
	* @return the type settings of this group
	*/
	@Override
	public java.lang.String getTypeSettings() {
		return _group.getTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _group.getTypeSettingsProperties();
	}

	@Override
	public java.lang.String getTypeSettingsProperty(java.lang.String key) {
		return _group.getTypeSettingsProperty(key);
	}

	@Override
	public java.lang.String getUnambiguousName(java.lang.String name,
		java.util.Locale locale) {
		return _group.getUnambiguousName(name, locale);
	}

	/**
	* Returns the uuid of this group.
	*
	* @return the uuid of this group
	*/
	@Override
	public java.lang.String getUuid() {
		return _group.getUuid();
	}

	@Override
	public boolean hasAncestor(long groupId) {
		return _group.hasAncestor(groupId);
	}

	@Override
	public boolean hasLocalOrRemoteStagingGroup() {
		return _group.hasLocalOrRemoteStagingGroup();
	}

	@Override
	public boolean hasPrivateLayouts() {
		return _group.hasPrivateLayouts();
	}

	@Override
	public boolean hasPublicLayouts() {
		return _group.hasPublicLayouts();
	}

	@Override
	public boolean hasRemoteStagingGroup() {
		return _group.hasRemoteStagingGroup();
	}

	@Override
	public boolean hasStagingGroup() {
		return _group.hasStagingGroup();
	}

	@Override
	public int hashCode() {
		return _group.hashCode();
	}

	/**
	* Returns <code>true</code> if this group is active.
	*
	* @return <code>true</code> if this group is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _group.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _group.isCachedModel();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #hasAncestor}
	*/
	@Deprecated
	@Override
	public boolean isChild(long groupId) {
		return _group.isChild(groupId);
	}

	/**
	* @deprecated As of 6.1.0, renamed to {@link #isRegularSite}
	*/
	@Deprecated
	@Override
	public boolean isCommunity() {
		return _group.isCommunity();
	}

	@Override
	public boolean isCompany() {
		return _group.isCompany();
	}

	@Override
	public boolean isCompanyStagingGroup() {
		return _group.isCompanyStagingGroup();
	}

	@Override
	public boolean isControlPanel() {
		return _group.isControlPanel();
	}

	@Override
	public boolean isEscapedModel() {
		return _group.isEscapedModel();
	}

	@Override
	public boolean isGuest() {
		return _group.isGuest();
	}

	@Override
	public boolean isInStagingPortlet(java.lang.String portletId) {
		return _group.isInStagingPortlet(portletId);
	}

	/**
	* Returns <code>true</code> if this group is inherit content.
	*
	* @return <code>true</code> if this group is inherit content; <code>false</code> otherwise
	*/
	@Override
	public boolean isInheritContent() {
		return _group.isInheritContent();
	}

	@Override
	public boolean isLayout() {
		return _group.isLayout();
	}

	@Override
	public boolean isLayoutPrototype() {
		return _group.isLayoutPrototype();
	}

	@Override
	public boolean isLayoutSetPrototype() {
		return _group.isLayoutSetPrototype();
	}

	@Override
	public boolean isLimitedToParentSiteMembers() {
		return _group.isLimitedToParentSiteMembers();
	}

	/**
	* Returns <code>true</code> if this group is manual membership.
	*
	* @return <code>true</code> if this group is manual membership; <code>false</code> otherwise
	*/
	@Override
	public boolean isManualMembership() {
		return _group.isManualMembership();
	}

	@Override
	public boolean isNew() {
		return _group.isNew();
	}

	@Override
	public boolean isOrganization() {
		return _group.isOrganization();
	}

	@Override
	public boolean isRegularSite() {
		return _group.isRegularSite();
	}

	@Override
	public boolean isRoot() {
		return _group.isRoot();
	}

	@Override
	public boolean isShowSite(
		com.liferay.portal.security.permission.PermissionChecker permissionChecker,
		boolean privateSite)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _group.isShowSite(permissionChecker, privateSite);
	}

	/**
	* Returns <code>true</code> if this group is site.
	*
	* @return <code>true</code> if this group is site; <code>false</code> otherwise
	*/
	@Override
	public boolean isSite() {
		return _group.isSite();
	}

	@Override
	public boolean isStaged() {
		return _group.isStaged();
	}

	@Override
	public boolean isStagedPortlet(java.lang.String portletId) {
		return _group.isStagedPortlet(portletId);
	}

	@Override
	public boolean isStagedRemotely() {
		return _group.isStagedRemotely();
	}

	@Override
	public boolean isStagingGroup() {
		return _group.isStagingGroup();
	}

	@Override
	public boolean isUser() {
		return _group.isUser();
	}

	@Override
	public boolean isUserGroup() {
		return _group.isUserGroup();
	}

	@Override
	public boolean isUserPersonalSite() {
		return _group.isUserPersonalSite();
	}

	@Override
	public void persist() {
		_group.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_group.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_group.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets whether this group is active.
	*
	* @param active the active of this group
	*/
	@Override
	public void setActive(boolean active) {
		_group.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_group.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_group.setClassName(className);
	}

	/**
	* Sets the class name ID of this group.
	*
	* @param classNameId the class name ID of this group
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_group.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this group.
	*
	* @param classPK the class p k of this group
	*/
	@Override
	public void setClassPK(long classPK) {
		_group.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this group.
	*
	* @param companyId the company ID of this group
	*/
	@Override
	public void setCompanyId(long companyId) {
		_group.setCompanyId(companyId);
	}

	/**
	* Sets the creator user ID of this group.
	*
	* @param creatorUserId the creator user ID of this group
	*/
	@Override
	public void setCreatorUserId(long creatorUserId) {
		_group.setCreatorUserId(creatorUserId);
	}

	/**
	* Sets the creator user uuid of this group.
	*
	* @param creatorUserUuid the creator user uuid of this group
	*/
	@Override
	public void setCreatorUserUuid(java.lang.String creatorUserUuid) {
		_group.setCreatorUserUuid(creatorUserUuid);
	}

	/**
	* Sets the description of this group.
	*
	* @param description the description of this group
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_group.setDescription(description);
	}

	/**
	* Sets the localized description of this group in the language.
	*
	* @param description the localized description of this group
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_group.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this group in the language, and sets the default locale.
	*
	* @param description the localized description of this group
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_group.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_group.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this group from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this group
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_group.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this group from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this group
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_group.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_group.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_group.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_group.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the friendly u r l of this group.
	*
	* @param friendlyURL the friendly u r l of this group
	*/
	@Override
	public void setFriendlyURL(java.lang.String friendlyURL) {
		_group.setFriendlyURL(friendlyURL);
	}

	/**
	* Sets the group ID of this group.
	*
	* @param groupId the group ID of this group
	*/
	@Override
	public void setGroupId(long groupId) {
		_group.setGroupId(groupId);
	}

	/**
	* Sets the group key of this group.
	*
	* @param groupKey the group key of this group
	*/
	@Override
	public void setGroupKey(java.lang.String groupKey) {
		_group.setGroupKey(groupKey);
	}

	/**
	* Sets whether this group is inherit content.
	*
	* @param inheritContent the inherit content of this group
	*/
	@Override
	public void setInheritContent(boolean inheritContent) {
		_group.setInheritContent(inheritContent);
	}

	/**
	* Sets the live group ID of this group.
	*
	* @param liveGroupId the live group ID of this group
	*/
	@Override
	public void setLiveGroupId(long liveGroupId) {
		_group.setLiveGroupId(liveGroupId);
	}

	/**
	* Sets whether this group is manual membership.
	*
	* @param manualMembership the manual membership of this group
	*/
	@Override
	public void setManualMembership(boolean manualMembership) {
		_group.setManualMembership(manualMembership);
	}

	/**
	* Sets the membership restriction of this group.
	*
	* @param membershipRestriction the membership restriction of this group
	*/
	@Override
	public void setMembershipRestriction(int membershipRestriction) {
		_group.setMembershipRestriction(membershipRestriction);
	}

	/**
	* Sets the mvcc version of this group.
	*
	* @param mvccVersion the mvcc version of this group
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_group.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this group.
	*
	* @param name the name of this group
	*/
	@Override
	public void setName(java.lang.String name) {
		_group.setName(name);
	}

	/**
	* Sets the localized name of this group in the language.
	*
	* @param name the localized name of this group
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_group.setName(name, locale);
	}

	/**
	* Sets the localized name of this group in the language, and sets the default locale.
	*
	* @param name the localized name of this group
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_group.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_group.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this group from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this group
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_group.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this group from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this group
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_group.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_group.setNew(n);
	}

	/**
	* Sets the parent group ID of this group.
	*
	* @param parentGroupId the parent group ID of this group
	*/
	@Override
	public void setParentGroupId(long parentGroupId) {
		_group.setParentGroupId(parentGroupId);
	}

	/**
	* Sets the primary key of this group.
	*
	* @param primaryKey the primary key of this group
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_group.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_group.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the remote staging group count of this group.
	*
	* @param remoteStagingGroupCount the remote staging group count of this group
	*/
	@Override
	public void setRemoteStagingGroupCount(int remoteStagingGroupCount) {
		_group.setRemoteStagingGroupCount(remoteStagingGroupCount);
	}

	/**
	* Sets whether this group is site.
	*
	* @param site the site of this group
	*/
	@Override
	public void setSite(boolean site) {
		_group.setSite(site);
	}

	/**
	* Sets the tree path of this group.
	*
	* @param treePath the tree path of this group
	*/
	@Override
	public void setTreePath(java.lang.String treePath) {
		_group.setTreePath(treePath);
	}

	/**
	* Sets the type of this group.
	*
	* @param type the type of this group
	*/
	@Override
	public void setType(int type) {
		_group.setType(type);
	}

	/**
	* Sets the type settings of this group.
	*
	* @param typeSettings the type settings of this group
	*/
	@Override
	public void setTypeSettings(java.lang.String typeSettings) {
		_group.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_group.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	* Sets the uuid of this group.
	*
	* @param uuid the uuid of this group
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_group.setUuid(uuid);
	}

	@Override
	public CacheModel<com.liferay.portal.model.Group> toCacheModel() {
		return _group.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Group toEscapedModel() {
		return new GroupWrapper(_group.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _group.toString();
	}

	@Override
	public com.liferay.portal.model.Group toUnescapedModel() {
		return new GroupWrapper(_group.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _group.toXmlString();
	}

	@Override
	public void updateTreePath(java.lang.String treePath) {
		_group.updateTreePath(treePath);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof GroupWrapper)) {
			return false;
		}

		GroupWrapper groupWrapper = (GroupWrapper)obj;

		if (Validator.equals(_group, groupWrapper._group)) {
			return true;
		}

		return false;
	}

	@Override
	public Group getWrappedModel() {
		return _group;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _group.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _group.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_group.resetOriginalValues();
	}

	private final Group _group;
}