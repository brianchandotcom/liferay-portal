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

package com.liferay.portal.model;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Group}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Group
 * @generated
 */
public class GroupWrapper implements Group, ModelWrapper<Group> {
	public GroupWrapper(Group group) {
		_group = group;
	}

	public Class<?> getModelClass() {
		return Group.class;
	}

	public String getModelClassName() {
		return Group.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("creatorUserId", getCreatorUserId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("parentGroupId", getParentGroupId());
		attributes.put("liveGroupId", getLiveGroupId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("friendlyURL", getFriendlyURL());
		attributes.put("site", getSite());
		attributes.put("active", getActive());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
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

		String friendlyURL = (String)attributes.get("friendlyURL");

		if (friendlyURL != null) {
			setFriendlyURL(friendlyURL);
		}

		Boolean site = (Boolean)attributes.get("site");

		if (site != null) {
			setSite(site);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	/**
	* Returns the primary key of this group.
	*
	* @return the primary key of this group
	*/
	public long getPrimaryKey() {
		return _group.getPrimaryKey();
	}

	/**
	* Sets the primary key of this group.
	*
	* @param primaryKey the primary key of this group
	*/
	public void setPrimaryKey(long primaryKey) {
		_group.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the group ID of this group.
	*
	* @return the group ID of this group
	*/
	public long getGroupId() {
		return _group.getGroupId();
	}

	/**
	* Sets the group ID of this group.
	*
	* @param groupId the group ID of this group
	*/
	public void setGroupId(long groupId) {
		_group.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this group.
	*
	* @return the company ID of this group
	*/
	public long getCompanyId() {
		return _group.getCompanyId();
	}

	/**
	* Sets the company ID of this group.
	*
	* @param companyId the company ID of this group
	*/
	public void setCompanyId(long companyId) {
		_group.setCompanyId(companyId);
	}

	/**
	* Returns the creator user ID of this group.
	*
	* @return the creator user ID of this group
	*/
	public long getCreatorUserId() {
		return _group.getCreatorUserId();
	}

	/**
	* Sets the creator user ID of this group.
	*
	* @param creatorUserId the creator user ID of this group
	*/
	public void setCreatorUserId(long creatorUserId) {
		_group.setCreatorUserId(creatorUserId);
	}

	/**
	* Returns the creator user uuid of this group.
	*
	* @return the creator user uuid of this group
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getCreatorUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _group.getCreatorUserUuid();
	}

	/**
	* Sets the creator user uuid of this group.
	*
	* @param creatorUserUuid the creator user uuid of this group
	*/
	public void setCreatorUserUuid(java.lang.String creatorUserUuid) {
		_group.setCreatorUserUuid(creatorUserUuid);
	}

	/**
	* Returns the fully qualified class name of this group.
	*
	* @return the fully qualified class name of this group
	*/
	public java.lang.String getClassName() {
		return _group.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_group.setClassName(className);
	}

	/**
	* Returns the class name ID of this group.
	*
	* @return the class name ID of this group
	*/
	public long getClassNameId() {
		return _group.getClassNameId();
	}

	/**
	* Sets the class name ID of this group.
	*
	* @param classNameId the class name ID of this group
	*/
	public void setClassNameId(long classNameId) {
		_group.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this group.
	*
	* @return the class p k of this group
	*/
	public long getClassPK() {
		return _group.getClassPK();
	}

	/**
	* Sets the class p k of this group.
	*
	* @param classPK the class p k of this group
	*/
	public void setClassPK(long classPK) {
		_group.setClassPK(classPK);
	}

	/**
	* Returns the parent group ID of this group.
	*
	* @return the parent group ID of this group
	*/
	public long getParentGroupId() {
		return _group.getParentGroupId();
	}

	/**
	* Sets the parent group ID of this group.
	*
	* @param parentGroupId the parent group ID of this group
	*/
	public void setParentGroupId(long parentGroupId) {
		_group.setParentGroupId(parentGroupId);
	}

	/**
	* Returns the live group ID of this group.
	*
	* @return the live group ID of this group
	*/
	public long getLiveGroupId() {
		return _group.getLiveGroupId();
	}

	/**
	* Sets the live group ID of this group.
	*
	* @param liveGroupId the live group ID of this group
	*/
	public void setLiveGroupId(long liveGroupId) {
		_group.setLiveGroupId(liveGroupId);
	}

	/**
	* Returns the name of this group.
	*
	* @return the name of this group
	*/
	public java.lang.String getName() {
		return _group.getName();
	}

	/**
	* Sets the name of this group.
	*
	* @param name the name of this group
	*/
	public void setName(java.lang.String name) {
		_group.setName(name);
	}

	/**
	* Returns the description of this group.
	*
	* @return the description of this group
	*/
	public java.lang.String getDescription() {
		return _group.getDescription();
	}

	/**
	* Sets the description of this group.
	*
	* @param description the description of this group
	*/
	public void setDescription(java.lang.String description) {
		_group.setDescription(description);
	}

	/**
	* Returns the type of this group.
	*
	* @return the type of this group
	*/
	public int getType() {
		return _group.getType();
	}

	/**
	* Sets the type of this group.
	*
	* @param type the type of this group
	*/
	public void setType(int type) {
		_group.setType(type);
	}

	/**
	* Returns the type settings of this group.
	*
	* @return the type settings of this group
	*/
	public java.lang.String getTypeSettings() {
		return _group.getTypeSettings();
	}

	/**
	* Sets the type settings of this group.
	*
	* @param typeSettings the type settings of this group
	*/
	public void setTypeSettings(java.lang.String typeSettings) {
		_group.setTypeSettings(typeSettings);
	}

	/**
	* Returns the friendly u r l of this group.
	*
	* @return the friendly u r l of this group
	*/
	public java.lang.String getFriendlyURL() {
		return _group.getFriendlyURL();
	}

	/**
	* Sets the friendly u r l of this group.
	*
	* @param friendlyURL the friendly u r l of this group
	*/
	public void setFriendlyURL(java.lang.String friendlyURL) {
		_group.setFriendlyURL(friendlyURL);
	}

	/**
	* Returns the site of this group.
	*
	* @return the site of this group
	*/
	public boolean getSite() {
		return _group.getSite();
	}

	/**
	* Returns <code>true</code> if this group is site.
	*
	* @return <code>true</code> if this group is site; <code>false</code> otherwise
	*/
	public boolean isSite() {
		return _group.isSite();
	}

	/**
	* Sets whether this group is site.
	*
	* @param site the site of this group
	*/
	public void setSite(boolean site) {
		_group.setSite(site);
	}

	/**
	* Returns the active of this group.
	*
	* @return the active of this group
	*/
	public boolean getActive() {
		return _group.getActive();
	}

	/**
	* Returns <code>true</code> if this group is active.
	*
	* @return <code>true</code> if this group is active; <code>false</code> otherwise
	*/
	public boolean isActive() {
		return _group.isActive();
	}

	/**
	* Sets whether this group is active.
	*
	* @param active the active of this group
	*/
	public void setActive(boolean active) {
		_group.setActive(active);
	}

	public boolean isNew() {
		return _group.isNew();
	}

	public void setNew(boolean n) {
		_group.setNew(n);
	}

	public boolean isCachedModel() {
		return _group.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_group.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _group.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _group.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_group.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _group.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_group.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new GroupWrapper((Group)_group.clone());
	}

	public int compareTo(com.liferay.portal.model.Group group) {
		return _group.compareTo(group);
	}

	@Override
	public int hashCode() {
		return _group.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Group> toCacheModel() {
		return _group.toCacheModel();
	}

	public com.liferay.portal.model.Group toEscapedModel() {
		return new GroupWrapper(_group.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _group.toString();
	}

	public java.lang.String toXmlString() {
		return _group.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_group.persist();
	}

	public java.util.List<com.liferay.portal.model.Group> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.getAncestors();
	}

	public java.util.List<com.liferay.portal.model.Group> getChildren(
		boolean site)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _group.getChildren(site);
	}

	public java.util.List<com.liferay.portal.model.Group> getChildrenWithLayouts(
		boolean site, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _group.getChildrenWithLayouts(site, start, end);
	}

	public int getChildrenWithLayoutsCount(boolean site)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _group.getChildrenWithLayoutsCount(site);
	}

	public long getDefaultPrivatePlid() {
		return _group.getDefaultPrivatePlid();
	}

	public long getDefaultPublicPlid() {
		return _group.getDefaultPublicPlid();
	}

	public java.lang.String getDescriptiveName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.getDescriptiveName();
	}

	public java.lang.String getDescriptiveName(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.getDescriptiveName(locale);
	}

	public com.liferay.portal.model.Group getLiveGroup() {
		return _group.getLiveGroup();
	}

	public long getOrganizationId() {
		return _group.getOrganizationId();
	}

	public com.liferay.portal.model.Group getParentGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.getParentGroup();
	}

	public java.lang.String getPathFriendlyURL(boolean privateLayout,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _group.getPathFriendlyURL(privateLayout, themeDisplay);
	}

	public com.liferay.portal.model.LayoutSet getPrivateLayoutSet() {
		return _group.getPrivateLayoutSet();
	}

	public int getPrivateLayoutsPageCount() {
		return _group.getPrivateLayoutsPageCount();
	}

	public com.liferay.portal.model.LayoutSet getPublicLayoutSet() {
		return _group.getPublicLayoutSet();
	}

	public int getPublicLayoutsPageCount() {
		return _group.getPublicLayoutsPageCount();
	}

	public com.liferay.portal.model.Group getStagingGroup() {
		return _group.getStagingGroup();
	}

	public java.lang.String getTypeLabel() {
		return _group.getTypeLabel();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _group.getTypeSettingsProperties();
	}

	public java.lang.String getTypeSettingsProperty(java.lang.String key) {
		return _group.getTypeSettingsProperty(key);
	}

	public boolean hasPrivateLayouts() {
		return _group.hasPrivateLayouts();
	}

	public boolean hasPublicLayouts() {
		return _group.hasPublicLayouts();
	}

	public boolean hasStagingGroup() {
		return _group.hasStagingGroup();
	}

	/**
	* @deprecated As of 6.1, renamed to {@link #isRegularSite}
	*/
	public boolean isCommunity() {
		return _group.isCommunity();
	}

	public boolean isCompany() {
		return _group.isCompany();
	}

	public boolean isControlPanel() {
		return _group.isControlPanel();
	}

	public boolean isGuest() {
		return _group.isGuest();
	}

	public boolean isInStagingPortlet(java.lang.String portletId) {
		return _group.isInStagingPortlet(portletId);
	}

	public boolean isLayout() {
		return _group.isLayout();
	}

	public boolean isLayoutPrototype() {
		return _group.isLayoutPrototype();
	}

	public boolean isLayoutSetPrototype() {
		return _group.isLayoutSetPrototype();
	}

	public boolean isOrganization() {
		return _group.isOrganization();
	}

	public boolean isRegularSite() {
		return _group.isRegularSite();
	}

	public boolean isRoot() {
		return _group.isRoot();
	}

	public boolean isShowSite(
		com.liferay.portal.security.permission.PermissionChecker permissionChecker,
		boolean privateSite)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _group.isShowSite(permissionChecker, privateSite);
	}

	public boolean isStaged() {
		return _group.isStaged();
	}

	public boolean isStagedPortlet(java.lang.String portletId) {
		return _group.isStagedPortlet(portletId);
	}

	public boolean isStagedRemotely() {
		return _group.isStagedRemotely();
	}

	public boolean isStagingGroup() {
		return _group.isStagingGroup();
	}

	public boolean isUser() {
		return _group.isUser();
	}

	public boolean isUserGroup() {
		return _group.isUserGroup();
	}

	public boolean isUserPersonalSite() {
		return _group.isUserPersonalSite();
	}

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_group.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Group getWrappedGroup() {
		return _group;
	}

	public Group getWrappedModel() {
		return _group;
	}

	public void resetOriginalValues() {
		_group.resetOriginalValues();
	}

	private Group _group;
}