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
 * This class is a wrapper for {@link LayoutSetBranch}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetBranch
 * @generated
 */
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
		attributes.put("logo", getLogo());
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

		Boolean logo = (Boolean)attributes.get("logo");

		if (logo != null) {
			setLogo(logo);
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

	/**
	* Returns the primary key of this layout set branch.
	*
	* @return the primary key of this layout set branch
	*/
	@Override
	public long getPrimaryKey() {
		return _layoutSetBranch.getPrimaryKey();
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
	* Sets the layout set branch ID of this layout set branch.
	*
	* @param layoutSetBranchId the layout set branch ID of this layout set branch
	*/
	@Override
	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutSetBranch.setLayoutSetBranchId(layoutSetBranchId);
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
	* Returns the company ID of this layout set branch.
	*
	* @return the company ID of this layout set branch
	*/
	@Override
	public long getCompanyId() {
		return _layoutSetBranch.getCompanyId();
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
	* Returns the user ID of this layout set branch.
	*
	* @return the user ID of this layout set branch
	*/
	@Override
	public long getUserId() {
		return _layoutSetBranch.getUserId();
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
	* Returns the user uuid of this layout set branch.
	*
	* @return the user uuid of this layout set branch
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetBranch.getUserUuid();
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
	* Returns the user name of this layout set branch.
	*
	* @return the user name of this layout set branch
	*/
	@Override
	public java.lang.String getUserName() {
		return _layoutSetBranch.getUserName();
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
	* Returns the create date of this layout set branch.
	*
	* @return the create date of this layout set branch
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _layoutSetBranch.getCreateDate();
	}

	/**
	* Sets the create date of this layout set branch.
	*
	* @param createDate the create date of this layout set branch
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_layoutSetBranch.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this layout set branch.
	*
	* @return the modified date of this layout set branch
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _layoutSetBranch.getModifiedDate();
	}

	/**
	* Sets the modified date of this layout set branch.
	*
	* @param modifiedDate the modified date of this layout set branch
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_layoutSetBranch.setModifiedDate(modifiedDate);
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
	* Returns <code>true</code> if this layout set branch is private layout.
	*
	* @return <code>true</code> if this layout set branch is private layout; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrivateLayout() {
		return _layoutSetBranch.isPrivateLayout();
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
	* Returns the name of this layout set branch.
	*
	* @return the name of this layout set branch
	*/
	@Override
	public java.lang.String getName() {
		return _layoutSetBranch.getName();
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

	/**
	* Returns the description of this layout set branch.
	*
	* @return the description of this layout set branch
	*/
	@Override
	public java.lang.String getDescription() {
		return _layoutSetBranch.getDescription();
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
	* Returns <code>true</code> if this layout set branch is master.
	*
	* @return <code>true</code> if this layout set branch is master; <code>false</code> otherwise
	*/
	@Override
	public boolean isMaster() {
		return _layoutSetBranch.isMaster();
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
	* Returns the logo of this layout set branch.
	*
	* @return the logo of this layout set branch
	*/
	@Override
	public boolean getLogo() {
		return _layoutSetBranch.getLogo();
	}

	/**
	* Returns <code>true</code> if this layout set branch is logo.
	*
	* @return <code>true</code> if this layout set branch is logo; <code>false</code> otherwise
	*/
	@Override
	public boolean isLogo() {
		return _layoutSetBranch.isLogo();
	}

	/**
	* Sets whether this layout set branch is logo.
	*
	* @param logo the logo of this layout set branch
	*/
	@Override
	public void setLogo(boolean logo) {
		_layoutSetBranch.setLogo(logo);
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
	* Sets the logo ID of this layout set branch.
	*
	* @param logoId the logo ID of this layout set branch
	*/
	@Override
	public void setLogoId(long logoId) {
		_layoutSetBranch.setLogoId(logoId);
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
	* Returns the color scheme ID of this layout set branch.
	*
	* @return the color scheme ID of this layout set branch
	*/
	@Override
	public java.lang.String getColorSchemeId() {
		return _layoutSetBranch.getColorSchemeId();
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
	* Returns the wap theme ID of this layout set branch.
	*
	* @return the wap theme ID of this layout set branch
	*/
	@Override
	public java.lang.String getWapThemeId() {
		return _layoutSetBranch.getWapThemeId();
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

	/**
	* Returns the wap color scheme ID of this layout set branch.
	*
	* @return the wap color scheme ID of this layout set branch
	*/
	@Override
	public java.lang.String getWapColorSchemeId() {
		return _layoutSetBranch.getWapColorSchemeId();
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
	* Returns the css of this layout set branch.
	*
	* @return the css of this layout set branch
	*/
	@Override
	public java.lang.String getCss() {
		return _layoutSetBranch.getCss();
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
	* Returns the settings of this layout set branch.
	*
	* @return the settings of this layout set branch
	*/
	@Override
	public java.lang.String getSettings() {
		return _layoutSetBranch.getSettings();
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

	/**
	* Returns the layout set prototype uuid of this layout set branch.
	*
	* @return the layout set prototype uuid of this layout set branch
	*/
	@Override
	public java.lang.String getLayoutSetPrototypeUuid() {
		return _layoutSetBranch.getLayoutSetPrototypeUuid();
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
	* Returns the layout set prototype link enabled of this layout set branch.
	*
	* @return the layout set prototype link enabled of this layout set branch
	*/
	@Override
	public boolean getLayoutSetPrototypeLinkEnabled() {
		return _layoutSetBranch.getLayoutSetPrototypeLinkEnabled();
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

	@Override
	public boolean isNew() {
		return _layoutSetBranch.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_layoutSetBranch.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _layoutSetBranch.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutSetBranch.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutSetBranch.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutSetBranch.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_layoutSetBranch.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutSetBranch.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
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
	public int hashCode() {
		return _layoutSetBranch.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.LayoutSetBranch> toCacheModel() {
		return _layoutSetBranch.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.LayoutSetBranch toEscapedModel() {
		return new LayoutSetBranchWrapper(_layoutSetBranch.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.LayoutSetBranch toUnescapedModel() {
		return new LayoutSetBranchWrapper(_layoutSetBranch.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutSetBranch.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _layoutSetBranch.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_layoutSetBranch.persist();
	}

	@Override
	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetBranch.getColorScheme();
	}

	@Override
	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetBranch.getGroup();
	}

	@Override
	public com.liferay.portal.model.LayoutSet getLayoutSet() {
		return _layoutSetBranch.getLayoutSet();
	}

	@Override
	public long getLiveLogoId() {
		return _layoutSetBranch.getLiveLogoId();
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
	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetBranch.getTheme();
	}

	@Override
	public java.lang.String getThemeSetting(java.lang.String key,
		java.lang.String device)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetBranch.getThemeSetting(key, device);
	}

	@Override
	public com.liferay.portal.model.ColorScheme getWapColorScheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetBranch.getWapColorScheme();
	}

	@Override
	public com.liferay.portal.model.Theme getWapTheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetBranch.getWapTheme();
	}

	@Override
	public boolean isLayoutSetPrototypeLinkActive() {
		return _layoutSetBranch.isLayoutSetPrototypeLinkActive();
	}

	@Override
	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties) {
		_layoutSetBranch.setSettingsProperties(settingsProperties);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public LayoutSetBranch getWrappedLayoutSetBranch() {
		return _layoutSetBranch;
	}

	@Override
	public LayoutSetBranch getWrappedModel() {
		return _layoutSetBranch;
	}

	@Override
	public void resetOriginalValues() {
		_layoutSetBranch.resetOriginalValues();
	}

	private LayoutSetBranch _layoutSetBranch;
}