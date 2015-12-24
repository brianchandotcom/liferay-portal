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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LayoutSetBranch}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetBranch
 * @generated
 */
@ProviderType
public class LayoutSetBranchWrapper implements LayoutSetBranch,
	ModelWrapper<LayoutSetBranch> {
	public LayoutSetBranchWrapper(LayoutSetBranch layoutSetBranch) {
		_layoutSetBranch = layoutSetBranch;
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutSetBranch.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutSetBranch.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("layoutSetBranchId", getLayoutSetBranchId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("privateLayout", getPrivateLayout());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("master", getMaster());
		attributes.put("logoId", getLogoId());
		attributes.put("themeId", getThemeId());
		attributes.put("colorSchemeId", getColorSchemeId());
		attributes.put("wapThemeId", getWapThemeId());
		attributes.put("wapColorSchemeId", getWapColorSchemeId());
		attributes.put("css", getCss());
		attributes.put("settings", getSettings());
		attributes.put("layoutSetPrototypeUuid", getLayoutSetPrototypeUuid());
		attributes.put("layoutSetPrototypeLinkEnabled",
			getLayoutSetPrototypeLinkEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long layoutSetBranchId = (Long)attributes.get("layoutSetBranchId");

		if (layoutSetBranchId != null) {
			setLayoutSetBranchId(layoutSetBranchId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Boolean master = (Boolean)attributes.get("master");

		if (master != null) {
			setMaster(master);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
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

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}

		String layoutSetPrototypeUuid = (String)attributes.get(
				"layoutSetPrototypeUuid");

		if (layoutSetPrototypeUuid != null) {
			setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
		}

		Boolean layoutSetPrototypeLinkEnabled = (Boolean)attributes.get(
				"layoutSetPrototypeLinkEnabled");

		if (layoutSetPrototypeLinkEnabled != null) {
			setLayoutSetPrototypeLinkEnabled(layoutSetPrototypeLinkEnabled);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutSetBranchWrapper((LayoutSetBranch)_layoutSetBranch.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.LayoutSetBranch layoutSetBranch) {
		return _layoutSetBranch.compareTo(layoutSetBranch);
	}

	@Override
	public com.liferay.portal.model.ColorScheme getColorScheme() {
		return _layoutSetBranch.getColorScheme();
	}

	/**
	* Returns the color scheme ID of this layout set branch.
	*
	* @return the color scheme ID of this layout set branch
	*/
	@Override
	public java.lang.String getColorSchemeId() {
		return _layoutSetBranch.getColorSchemeId();
	}

	/**
	* Returns the company ID of this layout set branch.
	*
	* @return the company ID of this layout set branch
	*/
	@Override
	public long getCompanyId() {
		return _layoutSetBranch.getCompanyId();
	}

	/**
	* Returns the create date of this layout set branch.
	*
	* @return the create date of this layout set branch
	*/
	@Override
	public Date getCreateDate() {
		return _layoutSetBranch.getCreateDate();
	}

	/**
	* Returns the css of this layout set branch.
	*
	* @return the css of this layout set branch
	*/
	@Override
	public java.lang.String getCss() {
		return _layoutSetBranch.getCss();
	}

	/**
	* Returns the description of this layout set branch.
	*
	* @return the description of this layout set branch
	*/
	@Override
	public java.lang.String getDescription() {
		return _layoutSetBranch.getDescription();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutSetBranch.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetBranch.getGroup();
	}

	/**
	* Returns the group ID of this layout set branch.
	*
	* @return the group ID of this layout set branch
	*/
	@Override
	public long getGroupId() {
		return _layoutSetBranch.getGroupId();
	}

	@Override
	public com.liferay.portal.model.LayoutSet getLayoutSet() {
		return _layoutSetBranch.getLayoutSet();
	}

	/**
	* Returns the layout set branch ID of this layout set branch.
	*
	* @return the layout set branch ID of this layout set branch
	*/
	@Override
	public long getLayoutSetBranchId() {
		return _layoutSetBranch.getLayoutSetBranchId();
	}

	/**
	* Returns the layout set prototype link enabled of this layout set branch.
	*
	* @return the layout set prototype link enabled of this layout set branch
	*/
	@Override
	public boolean getLayoutSetPrototypeLinkEnabled() {
		return _layoutSetBranch.getLayoutSetPrototypeLinkEnabled();
	}

	/**
	* Returns the layout set prototype uuid of this layout set branch.
	*
	* @return the layout set prototype uuid of this layout set branch
	*/
	@Override
	public java.lang.String getLayoutSetPrototypeUuid() {
		return _layoutSetBranch.getLayoutSetPrototypeUuid();
	}

	@Override
	public long getLiveLogoId() {
		return _layoutSetBranch.getLiveLogoId();
	}

	@Override
	public boolean getLogo() {
		return _layoutSetBranch.getLogo();
	}

	/**
	* Returns the logo ID of this layout set branch.
	*
	* @return the logo ID of this layout set branch
	*/
	@Override
	public long getLogoId() {
		return _layoutSetBranch.getLogoId();
	}

	/**
	* Returns the master of this layout set branch.
	*
	* @return the master of this layout set branch
	*/
	@Override
	public boolean getMaster() {
		return _layoutSetBranch.getMaster();
	}

	/**
	* Returns the modified date of this layout set branch.
	*
	* @return the modified date of this layout set branch
	*/
	@Override
	public Date getModifiedDate() {
		return _layoutSetBranch.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this layout set branch.
	*
	* @return the mvcc version of this layout set branch
	*/
	@Override
	public long getMvccVersion() {
		return _layoutSetBranch.getMvccVersion();
	}

	/**
	* Returns the name of this layout set branch.
	*
	* @return the name of this layout set branch
	*/
	@Override
	public java.lang.String getName() {
		return _layoutSetBranch.getName();
	}

	/**
	* Returns the primary key of this layout set branch.
	*
	* @return the primary key of this layout set branch
	*/
	@Override
	public long getPrimaryKey() {
		return _layoutSetBranch.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutSetBranch.getPrimaryKeyObj();
	}

	/**
	* Returns the private layout of this layout set branch.
	*
	* @return the private layout of this layout set branch
	*/
	@Override
	public boolean getPrivateLayout() {
		return _layoutSetBranch.getPrivateLayout();
	}

	/**
	* Returns the settings of this layout set branch.
	*
	* @return the settings of this layout set branch
	*/
	@Override
	public java.lang.String getSettings() {
		return _layoutSetBranch.getSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getSettingsProperties() {
		return _layoutSetBranch.getSettingsProperties();
	}

	@Override
	public java.lang.String getSettingsProperty(java.lang.String key) {
		return _layoutSetBranch.getSettingsProperty(key);
	}

	@Override
	public com.liferay.portal.model.Theme getTheme() {
		return _layoutSetBranch.getTheme();
	}

	/**
	* Returns the theme ID of this layout set branch.
	*
	* @return the theme ID of this layout set branch
	*/
	@Override
	public java.lang.String getThemeId() {
		return _layoutSetBranch.getThemeId();
	}

	@Override
	public java.lang.String getThemeSetting(java.lang.String key,
		java.lang.String device) {
		return _layoutSetBranch.getThemeSetting(key, device);
	}

	/**
	* Returns the user ID of this layout set branch.
	*
	* @return the user ID of this layout set branch
	*/
	@Override
	public long getUserId() {
		return _layoutSetBranch.getUserId();
	}

	/**
	* Returns the user name of this layout set branch.
	*
	* @return the user name of this layout set branch
	*/
	@Override
	public java.lang.String getUserName() {
		return _layoutSetBranch.getUserName();
	}

	/**
	* Returns the user uuid of this layout set branch.
	*
	* @return the user uuid of this layout set branch
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _layoutSetBranch.getUserUuid();
	}

	@Override
	public com.liferay.portal.model.ColorScheme getWapColorScheme() {
		return _layoutSetBranch.getWapColorScheme();
	}

	/**
	* Returns the wap color scheme ID of this layout set branch.
	*
	* @return the wap color scheme ID of this layout set branch
	*/
	@Override
	public java.lang.String getWapColorSchemeId() {
		return _layoutSetBranch.getWapColorSchemeId();
	}

	@Override
	public com.liferay.portal.model.Theme getWapTheme() {
		return _layoutSetBranch.getWapTheme();
	}

	/**
	* Returns the wap theme ID of this layout set branch.
	*
	* @return the wap theme ID of this layout set branch
	*/
	@Override
	public java.lang.String getWapThemeId() {
		return _layoutSetBranch.getWapThemeId();
	}

	@Override
	public int hashCode() {
		return _layoutSetBranch.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _layoutSetBranch.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutSetBranch.isEscapedModel();
	}

	@Override
	public boolean isLayoutSetPrototypeLinkActive() {
		return _layoutSetBranch.isLayoutSetPrototypeLinkActive();
	}

	/**
	* Returns <code>true</code> if this layout set branch is layout set prototype link enabled.
	*
	* @return <code>true</code> if this layout set branch is layout set prototype link enabled; <code>false</code> otherwise
	*/
	@Override
	public boolean isLayoutSetPrototypeLinkEnabled() {
		return _layoutSetBranch.isLayoutSetPrototypeLinkEnabled();
	}

	@Override
	public boolean isLogo() {
		return _layoutSetBranch.isLogo();
	}

	/**
	* Returns <code>true</code> if this layout set branch is master.
	*
	* @return <code>true</code> if this layout set branch is master; <code>false</code> otherwise
	*/
	@Override
	public boolean isMaster() {
		return _layoutSetBranch.isMaster();
	}

	@Override
	public boolean isNew() {
		return _layoutSetBranch.isNew();
	}

	/**
	* Returns <code>true</code> if this layout set branch is private layout.
	*
	* @return <code>true</code> if this layout set branch is private layout; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrivateLayout() {
		return _layoutSetBranch.isPrivateLayout();
	}

	@Override
	public void persist() {
		_layoutSetBranch.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutSetBranch.setCachedModel(cachedModel);
	}

	/**
	* Sets the color scheme ID of this layout set branch.
	*
	* @param colorSchemeId the color scheme ID of this layout set branch
	*/
	@Override
	public void setColorSchemeId(java.lang.String colorSchemeId) {
		_layoutSetBranch.setColorSchemeId(colorSchemeId);
	}

	/**
	* Sets the company ID of this layout set branch.
	*
	* @param companyId the company ID of this layout set branch
	*/
	@Override
	public void setCompanyId(long companyId) {
		_layoutSetBranch.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this layout set branch.
	*
	* @param createDate the create date of this layout set branch
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_layoutSetBranch.setCreateDate(createDate);
	}

	/**
	* Sets the css of this layout set branch.
	*
	* @param css the css of this layout set branch
	*/
	@Override
	public void setCss(java.lang.String css) {
		_layoutSetBranch.setCss(css);
	}

	/**
	* Sets the description of this layout set branch.
	*
	* @param description the description of this layout set branch
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_layoutSetBranch.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_layoutSetBranch.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_layoutSetBranch.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutSetBranch.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this layout set branch.
	*
	* @param groupId the group ID of this layout set branch
	*/
	@Override
	public void setGroupId(long groupId) {
		_layoutSetBranch.setGroupId(groupId);
	}

	/**
	* Sets the layout set branch ID of this layout set branch.
	*
	* @param layoutSetBranchId the layout set branch ID of this layout set branch
	*/
	@Override
	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutSetBranch.setLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Sets whether this layout set branch is layout set prototype link enabled.
	*
	* @param layoutSetPrototypeLinkEnabled the layout set prototype link enabled of this layout set branch
	*/
	@Override
	public void setLayoutSetPrototypeLinkEnabled(
		boolean layoutSetPrototypeLinkEnabled) {
		_layoutSetBranch.setLayoutSetPrototypeLinkEnabled(layoutSetPrototypeLinkEnabled);
	}

	/**
	* Sets the layout set prototype uuid of this layout set branch.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid of this layout set branch
	*/
	@Override
	public void setLayoutSetPrototypeUuid(
		java.lang.String layoutSetPrototypeUuid) {
		_layoutSetBranch.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	/**
	* Sets the logo ID of this layout set branch.
	*
	* @param logoId the logo ID of this layout set branch
	*/
	@Override
	public void setLogoId(long logoId) {
		_layoutSetBranch.setLogoId(logoId);
	}

	/**
	* Sets whether this layout set branch is master.
	*
	* @param master the master of this layout set branch
	*/
	@Override
	public void setMaster(boolean master) {
		_layoutSetBranch.setMaster(master);
	}

	/**
	* Sets the modified date of this layout set branch.
	*
	* @param modifiedDate the modified date of this layout set branch
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_layoutSetBranch.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this layout set branch.
	*
	* @param mvccVersion the mvcc version of this layout set branch
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_layoutSetBranch.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this layout set branch.
	*
	* @param name the name of this layout set branch
	*/
	@Override
	public void setName(java.lang.String name) {
		_layoutSetBranch.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_layoutSetBranch.setNew(n);
	}

	/**
	* Sets the primary key of this layout set branch.
	*
	* @param primaryKey the primary key of this layout set branch
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_layoutSetBranch.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_layoutSetBranch.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets whether this layout set branch is private layout.
	*
	* @param privateLayout the private layout of this layout set branch
	*/
	@Override
	public void setPrivateLayout(boolean privateLayout) {
		_layoutSetBranch.setPrivateLayout(privateLayout);
	}

	/**
	* Sets the settings of this layout set branch.
	*
	* @param settings the settings of this layout set branch
	*/
	@Override
	public void setSettings(java.lang.String settings) {
		_layoutSetBranch.setSettings(settings);
	}

	@Override
	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties) {
		_layoutSetBranch.setSettingsProperties(settingsProperties);
	}

	/**
	* Sets the theme ID of this layout set branch.
	*
	* @param themeId the theme ID of this layout set branch
	*/
	@Override
	public void setThemeId(java.lang.String themeId) {
		_layoutSetBranch.setThemeId(themeId);
	}

	/**
	* Sets the user ID of this layout set branch.
	*
	* @param userId the user ID of this layout set branch
	*/
	@Override
	public void setUserId(long userId) {
		_layoutSetBranch.setUserId(userId);
	}

	/**
	* Sets the user name of this layout set branch.
	*
	* @param userName the user name of this layout set branch
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_layoutSetBranch.setUserName(userName);
	}

	/**
	* Sets the user uuid of this layout set branch.
	*
	* @param userUuid the user uuid of this layout set branch
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_layoutSetBranch.setUserUuid(userUuid);
	}

	/**
	* Sets the wap color scheme ID of this layout set branch.
	*
	* @param wapColorSchemeId the wap color scheme ID of this layout set branch
	*/
	@Override
	public void setWapColorSchemeId(java.lang.String wapColorSchemeId) {
		_layoutSetBranch.setWapColorSchemeId(wapColorSchemeId);
	}

	/**
	* Sets the wap theme ID of this layout set branch.
	*
	* @param wapThemeId the wap theme ID of this layout set branch
	*/
	@Override
	public void setWapThemeId(java.lang.String wapThemeId) {
		_layoutSetBranch.setWapThemeId(wapThemeId);
	}

	@Override
	public CacheModel<com.liferay.portal.model.LayoutSetBranch> toCacheModel() {
		return _layoutSetBranch.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.LayoutSetBranch toEscapedModel() {
		return new LayoutSetBranchWrapper(_layoutSetBranch.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutSetBranch.toString();
	}

	@Override
	public com.liferay.portal.model.LayoutSetBranch toUnescapedModel() {
		return new LayoutSetBranchWrapper(_layoutSetBranch.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _layoutSetBranch.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutSetBranchWrapper)) {
			return false;
		}

		LayoutSetBranchWrapper layoutSetBranchWrapper = (LayoutSetBranchWrapper)obj;

		if (Validator.equals(_layoutSetBranch,
					layoutSetBranchWrapper._layoutSetBranch)) {
			return true;
		}

		return false;
	}

	@Override
	public LayoutSetBranch getWrappedModel() {
		return _layoutSetBranch;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _layoutSetBranch.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _layoutSetBranch.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_layoutSetBranch.resetOriginalValues();
	}

	private final LayoutSetBranch _layoutSetBranch;
}