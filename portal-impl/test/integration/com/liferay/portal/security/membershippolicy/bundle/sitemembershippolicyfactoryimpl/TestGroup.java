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

package com.liferay.portal.security.membershippolicy.bundle.sitemembershippolicyfactoryimpl;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Peter Fellwock
 */
public class TestGroup implements Group {

	@Override
	public String buildTreePath() {
		return null;
	}

	@Override
	public void clearStagingGroup() {
	}

	@Override
	public Group clone() {
		return null;
	}

	@Override
	public int compareTo(Group group) {
		return 0;
	}

	@Override
	public boolean getActive() {
		return false;
	}

	@Override
	public List<Group> getAncestors() {
		return null;
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return null;
	}

	@Override
	public List<Group> getChildren(boolean site) {
		return null;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public List<Group> getChildrenWithLayouts(
		boolean site, int start, int end) {

		return null;
	}

	@Override
	public List<Group> getChildrenWithLayouts(
		boolean site, int start, int end, OrderByComparator<Group> obc) {

		return null;
	}

	@Override
	public int getChildrenWithLayoutsCount(boolean site) {
		return 0;
	}

	@Override
	public String getClassName() {
		return null;
	}

	@Override
	public long getClassNameId() {
		return 0;
	}

	@Override
	public long getClassPK() {
		return 0;
	}

	@Override
	public long getCompanyId() {
		return 0;
	}

	@Override
	public long getCreatorUserId() {
		return 0;
	}

	@Override
	public String getCreatorUserUuid() {
		return null;
	}

	@Override
	public String getDefaultLanguageId() {
		return null;
	}

	@Override
	public long getDefaultPrivatePlid() {
		return 0;
	}

	@Override
	public long getDefaultPublicPlid() {
		return 0;
	}

	@Override
	public List<Group> getDescendants(boolean site) {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getDescription(Locale locale) {
		return null;
	}

	@Override
	public String getDescription(Locale locale, boolean useDefault) {
		return null;
	}

	@Override
	public String getDescription(String languageId) {
		return null;
	}

	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return null;
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return null;
	}

	@Override
	public String getDescriptionCurrentValue() {
		return null;
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		return null;
	}

	@Override
	public String getDescriptiveName() {
		return null;
	}

	@Override
	public String getDescriptiveName(Locale locale) {
		return null;
	}

	@Override
	public String getDisplayURL(ThemeDisplay themeDisplay) {
		return null;
	}

	@Override
	public String getDisplayURL(
		ThemeDisplay themeDisplay, boolean privateLayout) {

		return null;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	@Override
	public String getFriendlyURL() {
		return null;
	}

	@Override
	public long getGroupId() {
		return 0;
	}

	@Override
	public String getGroupKey() {
		return null;
	}

	@Override
	public String getIconCssClass() {
		return null;
	}

	@Override
	public String getIconURL(ThemeDisplay themeDisplay) {
		return null;
	}

	@Override
	public boolean getInheritContent() {
		return false;
	}

	@Override
	public String getLayoutRootNodeName(boolean privateLayout, Locale locale) {
		return null;
	}

	@Override
	public Group getLiveGroup() {
		return null;
	}

	@Override
	public long getLiveGroupId() {
		return 0;
	}

	@Override
	public String getLiveParentTypeSettingsProperty(String key) {
		return null;
	}

	@Override
	public String getLogo(ThemeDisplay themeDisplay, boolean useDefault) {
		return null;
	}

	@Override
	public boolean getManualMembership() {
		return false;
	}

	@Override
	public int getMembershipRestriction() {
		return 0;
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		return null;
	}

	@Override
	public Class<?> getModelClass() {
		return null;
	}

	@Override
	public String getModelClassName() {
		return null;
	}

	@Override
	public long getMvccVersion() {
		return 0;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getName(Locale locale) {
		return null;
	}

	@Override
	public String getName(Locale locale, boolean useDefault) {
		return null;
	}

	@Override
	public String getName(String languageId) {
		return null;
	}

	@Override
	public String getName(String languageId, boolean useDefault) {
		return null;
	}

	@Override
	public String getNameCurrentLanguageId() {
		return null;
	}

	@Override
	public String getNameCurrentValue() {
		return null;
	}

	@Override
	public Map<Locale, String> getNameMap() {
		return null;
	}

	@Override
	public long getOrganizationId() {
		return 0;
	}

	@Override
	public Group getParentGroup() {
		return null;
	}

	@Override
	public long getParentGroupId() {
		return 0;
	}

	@Override
	public UnicodeProperties getParentLiveGroupTypeSettingsProperties() {
		return null;
	}

	@Override
	public String getPathFriendlyURL(
		boolean privateLayout, ThemeDisplay themeDisplay) {

		return null;
	}

	@Override
	public long getPrimaryKey() {
		return 0;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return null;
	}

	@Override
	public LayoutSet getPrivateLayoutSet() {
		return null;
	}

	@Override
	public int getPrivateLayoutsPageCount() {
		return 0;
	}

	@Override
	public LayoutSet getPublicLayoutSet() {
		return null;
	}

	@Override
	public int getPublicLayoutsPageCount() {
		return 0;
	}

	@Override
	public long getRemoteLiveGroupId() {
		return 0;
	}

	@Override
	public int getRemoteStagingGroupCount() {
		return 0;
	}

	@Override
	public String getScopeDescriptiveName(ThemeDisplay themeDisplay) {
		return null;
	}

	@Override
	public String getScopeLabel(ThemeDisplay themeDisplay) {
		return null;
	}

	@Override
	public boolean getSite() {
		return false;
	}

	@Override
	public Group getStagingGroup() {
		return null;
	}

	@Override
	public String getTreePath() {
		return null;
	}

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public String getTypeLabel() {
		return null;
	}

	@Override
	public String getTypeSettings() {
		return null;
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		return null;
	}

	@Override
	public String getTypeSettingsProperty(String key) {
		return null;
	}

	@Override
	public String getUnambiguousName(String name, Locale locale) {
		return null;
	}

	@Override
	public String getUuid() {
		return null;
	}

	@Override
	public boolean hasAncestor(long groupId) {
		return false;
	}

	@Override
	public boolean hasLocalOrRemoteStagingGroup() {
		return false;
	}

	@Override
	public boolean hasPrivateLayouts() {
		return false;
	}

	@Override
	public boolean hasPublicLayouts() {
		return false;
	}

	@Override
	public boolean hasRemoteStagingGroup() {
		return false;
	}

	@Override
	public boolean hasStagingGroup() {
		return false;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public boolean isCachedModel() {
		return false;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public boolean isChild(long groupId) {
		return false;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public boolean isCommunity() {
		return false;
	}

	@Override
	public boolean isCompany() {
		return false;
	}

	@Override
	public boolean isCompanyStagingGroup() {
		return false;
	}

	@Override
	public boolean isControlPanel() {
		return false;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return false;
	}

	@Override
	public boolean isEscapedModel() {
		return false;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return false;
	}

	@Override
	public boolean isGuest() {
		return false;
	}

	@Override
	public boolean isInheritContent() {
		return false;
	}

	@Override
	public boolean isInStagingPortlet(String portletId) {
		return false;
	}

	@Override
	public boolean isLayout() {
		return false;
	}

	@Override
	public boolean isLayoutPrototype() {
		return false;
	}

	@Override
	public boolean isLayoutSetPrototype() {
		return false;
	}

	@Override
	public boolean isLimitedToParentSiteMembers() {
		return false;
	}

	@Override
	public boolean isManualMembership() {
		return false;
	}

	@Override
	public boolean isNew() {
		return false;
	}

	@Override
	public boolean isOrganization() {
		return false;
	}

	@Override
	public boolean isRegularSite() {
		return false;
	}

	@Override
	public boolean isRoot() {
		return false;
	}

	@Override
	public boolean isShowSite(
		PermissionChecker permissionChecker, boolean privateSite) {

		return false;
	}

	@Override
	public boolean isSite() {
		return false;
	}

	@Override
	public boolean isStaged() {
		return false;
	}

	@Override
	public boolean isStagedPortlet(String portletId) {
		return false;
	}

	@Override
	public boolean isStagedRemotely() {
		return false;
	}

	@Override
	public boolean isStagingGroup() {
		return false;
	}

	@Override
	public boolean isUser() {
		return false;
	}

	@Override
	public boolean isUserGroup() {
		return false;
	}

	@Override
	public boolean isUserPersonalSite() {
		return false;
	}

	@Override
	public void persist() {
	}

	@Override
	public void prepareLocalizedFieldsForImport() {
	}

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale) {
	}

	@Override
	public void resetOriginalValues() {
	}

	@Override
	public void setActive(boolean active) {
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
	}

	@Override
	public void setClassName(String className) {
	}

	@Override
	public void setClassNameId(long classNameId) {
	}

	@Override
	public void setClassPK(long classPK) {
	}

	@Override
	public void setCompanyId(long companyId) {
	}

	@Override
	public void setCreatorUserId(long creatorUserId) {
	}

	@Override
	public void setCreatorUserUuid(String creatorUserUuid) {
	}

	@Override
	public void setDescription(String description) {
	}

	@Override
	public void setDescription(String description, Locale locale) {
	}

	@Override
	public void setDescription(
		String description, Locale locale, Locale defaultLocale) {
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
	}

	@Override
	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
	}

	@Override
	public void setDescriptionMap(
		Map<Locale, String> descriptionMap, Locale defaultLocale) {
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
	}

	@Override
	public void setFriendlyURL(String friendlyURL) {
	}

	@Override
	public void setGroupId(long groupId) {
	}

	@Override
	public void setGroupKey(String groupKey) {
	}

	@Override
	public void setInheritContent(boolean inheritContent) {
	}

	@Override
	public void setLiveGroupId(long liveGroupId) {
	}

	@Override
	public void setManualMembership(boolean manualMembership) {
	}

	@Override
	public void setMembershipRestriction(int membershipRestriction) {
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
	}

	@Override
	public void setName(String name) {
	}

	@Override
	public void setName(String name, Locale locale) {
	}

	@Override
	public void setName(String name, Locale locale, Locale defaultLocale) {
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
	}

	@Override
	public void setNameMap(Map<Locale, String> nameMap) {
	}

	@Override
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale) {
	}

	@Override
	public void setNew(boolean n) {
	}

	@Override
	public void setParentGroupId(long parentGroupId) {
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
	}

	@Override
	public void setRemoteStagingGroupCount(int remoteStagingGroupCount) {
	}

	@Override
	public void setSite(boolean site) {
	}

	@Override
	public void setTreePath(String treePath) {
	}

	@Override
	public void setType(int type) {
	}

	@Override
	public void setTypeSettings(String typeSettings) {
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {
	}

	@Override
	public void setUuid(String uuid) {
	}

	@Override
	public CacheModel<Group> toCacheModel() {
		return null;
	}

	@Override
	public Group toEscapedModel() {
		return null;
	}

	@Override
	public Group toUnescapedModel() {
		return null;
	}

	@Override
	public String toXmlString() {
		return null;
	}

	@Override
	public void updateTreePath(String treePath) {
	}

}