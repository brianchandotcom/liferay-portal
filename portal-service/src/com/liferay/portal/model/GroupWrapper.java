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

		attributes.put("uuid", getUuid());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("creatorUserId", getCreatorUserId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("parentGroupId", getParentGroupId());
		attributes.put("liveGroupId", getLiveGroupId());
		attributes.put("treePath", getTreePath());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("manualMembership", getManualMembership());
		attributes.put("membershipRestriction", getMembershipRestriction());
		attributes.put("friendlyURL", getFriendlyURL());
		attributes.put("site", getSite());
		attributes.put("remoteStagingGroupCount", getRemoteStagingGroupCount());
		attributes.put("active", getActive());
		attributes.put("mvccVersion", getMvccVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}
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

	/**
	* Sets the primary key of this group.
	*
	* @param primaryKey the primary key of this group
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_group.setPrimaryKey(primaryKey);
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

	/**
	* Sets the uuid of this group.
	*
	* @param uuid the uuid of this group
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_group.setUuid(uuid);
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
	* Sets the group ID of this group.
	*
	* @param groupId the group ID of this group
	*/
	@Override
	public void setGroupId(long groupId) {
		_group.setGroupId(groupId);
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
	* Sets the company ID of this group.
	*
	* @param companyId the company ID of this group
	*/
	@Override
	public void setCompanyId(long companyId) {
		_group.setCompanyId(companyId);
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
	* Sets the creator user ID of this group.
	*
	* @param creatorUserId the creator user ID of this group
	*/
	@Override
	public void setCreatorUserId(long creatorUserId) {
		_group.setCreatorUserId(creatorUserId);
	}

	/**
	* Returns the creator user uuid of this group.
	*
	* @return the creator user uuid of this group
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getCreatorUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _group.getCreatorUserUuid();
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
	* Returns the fully qualified class name of this group.
	*
	* @return the fully qualified class name of this group
	*/
	@Override
	public java.lang.String getClassName() {
		return _group.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_group.setClassName(className);
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
	* Sets the class name ID of this group.
	*
	* @param classNameId the class name ID of this group
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_group.setClassNameId(classNameId);
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
	* Sets the class p k of this group.
	*
	* @param classPK the class p k of this group
	*/
	@Override
	public void setClassPK(long classPK) {
		_group.setClassPK(classPK);
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
	* Returns the live group ID of this group.
	*
	* @return the live group ID of this group
	*/
	@Override
	public long getLiveGroupId() {
		return _group.getLiveGroupId();
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
	* Returns the tree path of this group.
	*
	* @return the tree path of this group
	*/
	@Override
	public java.lang.String getTreePath() {
		return _group.getTreePath();
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
	* Returns the name of this group.
	*
	* @return the name of this group
	*/
	@Override
	public java.lang.String getName() {
		return _group.getName();
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
	* Returns the description of this group.
	*
	* @return the description of this group
	*/
	@Override
	public java.lang.String getDescription() {
		return _group.getDescription();
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
	* Returns the type of this group.
	*
	* @return the type of this group
	*/
	@Override
	public int getType() {
		return _group.getType();
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
	* Returns the type settings of this group.
	*
	* @return the type settings of this group
	*/
	@Override
	public java.lang.String getTypeSettings() {
		return _group.getTypeSettings();
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
	* Returns <code>true</code> if this group is manual membership.
	*
	* @return <code>true</code> if this group is manual membership; <code>false</code> otherwise
	*/
	@Override
	public boolean isManualMembership() {
		return _group.isManualMembership();
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
	* Returns the membership restriction of this group.
	*
	* @return the membership restriction of this group
	*/
	@Override
	public int getMembershipRestriction() {
		return _group.getMembershipRestriction();
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
	* Returns the friendly u r l of this group.
	*
	* @return the friendly u r l of this group
	*/
	@Override
	public java.lang.String getFriendlyURL() {
		return _group.getFriendlyURL();
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
	* Returns the site of this group.
	*
	* @return the site of this group
	*/
	@Override
	public boolean getSite() {
		return _group.getSite();
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
	* Returns the remote staging group count of this group.
	*
	* @return the remote staging group count of this group
	*/
	@Override
	public int getRemoteStagingGroupCount() {
		return _group.getRemoteStagingGroupCount();
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
	* Returns the active of this group.
	*
	* @return the active of this group
	*/
	@Override
	public boolean getActive() {
		return _group.getActive();
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

	/**
	* Sets whether this group is active.
	*
	* @param active the active of this group
	*/
	@Override
	public void setActive(boolean active) {
		_group.setActive(active);
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
	* Sets the mvcc version of this group.
	*
	* @param mvccVersion the mvcc version of this group
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_group.setMvccVersion(mvccVersion);
	}

	@Override
	public boolean isNew() {
		return _group.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_group.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _group.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_group.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _group.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _group.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_group.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _group.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
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

	@Override
	public java.lang.Object clone() {
		return new GroupWrapper((Group)_group.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Group group) {
		return _group.compareTo(group);
	}

	@Override
	public int hashCode() {
		return _group.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Group> toCacheModel() {
		return _group.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Group toEscapedModel() {
		return new GroupWrapper(_group.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.Group toUnescapedModel() {
		return new GroupWrapper(_group.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _group.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _group.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_group.persist();
	}

	@Override
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.buildTreePath();
	}

	@Override
	public void updateTreePath(java.lang.String treePath)
		throws com.liferay.portal.kernel.exception.SystemException {
		_group.updateTreePath(treePath);
	}

	@Override
	public void clearStagingGroup() {
		_group.clearStagingGroup();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.getAncestors();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getChildren(
		boolean site)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _group.getChildren(site);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getChildrenWithLayouts(
		boolean site, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _group.getChildrenWithLayouts(site, start, end);
	}

	@Override
	public int getChildrenWithLayoutsCount(boolean site)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _group.getChildrenWithLayoutsCount(site);
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
	public java.lang.String getDescriptiveName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.getDescriptiveName();
	}

	@Override
	public java.lang.String getDescriptiveName(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.getDescriptiveName(locale);
	}

	@Override
	public java.lang.String getIconURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _group.getIconURL(themeDisplay);
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

	@Override
	public java.lang.String getLiveParentTypeSettingsProperty(
		java.lang.String key) {
		return _group.getLiveParentTypeSettingsProperty(key);
	}

	@Override
	public long getOrganizationId() {
		return _group.getOrganizationId();
	}

	@Override
	public com.liferay.portal.model.Group getParentGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.getParentGroup();
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
	public java.lang.String getScopeDescriptiveName(
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.getScopeDescriptiveName(themeDisplay);
	}

	@Override
	public java.lang.String getScopeLabel(
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _group.getScopeLabel(themeDisplay);
	}

	@Override
	public com.liferay.portal.model.Group getStagingGroup() {
		return _group.getStagingGroup();
	}

	@Override
	public java.lang.String getTypeLabel() {
		return _group.getTypeLabel();
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
	public boolean hasStagingGroup() {
		return _group.hasStagingGroup();
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
	public boolean isGuest() {
		return _group.isGuest();
	}

	@Override
	public boolean isInStagingPortlet(java.lang.String portletId) {
		return _group.isInStagingPortlet(portletId);
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
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.isShowSite(permissionChecker, privateSite);
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
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_group.setTypeSettingsProperties(typeSettingsProperties);
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

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public Group getWrappedGroup() {
		return _group;
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

	private Group _group;
}