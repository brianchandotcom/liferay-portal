/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link LayoutSet}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSet
 * @generated
 */
public class LayoutSetWrapper implements LayoutSet, ModelWrapper<LayoutSet> {
	public LayoutSetWrapper(LayoutSet layoutSet) {
		_layoutSet = layoutSet;
	}

	public Class<?> getModelClass() {
		return LayoutSet.class;
	}

	public String getModelClassName() {
		return LayoutSet.class.getName();
	}

	/**
	* Returns the primary key of this layout set.
	*
	* @return the primary key of this layout set
	*/
	public long getPrimaryKey() {
		return _layoutSet.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout set.
	*
	* @param primaryKey the primary key of this layout set
	*/
	public void setPrimaryKey(long primaryKey) {
		_layoutSet.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the layout set ID of this layout set.
	*
	* @return the layout set ID of this layout set
	*/
	public long getLayoutSetId() {
		return _layoutSet.getLayoutSetId();
	}

	/**
	* Sets the layout set ID of this layout set.
	*
	* @param layoutSetId the layout set ID of this layout set
	*/
	public void setLayoutSetId(long layoutSetId) {
		_layoutSet.setLayoutSetId(layoutSetId);
	}

	/**
	* Returns the group ID of this layout set.
	*
	* @return the group ID of this layout set
	*/
	public long getGroupId() {
		return _layoutSet.getGroupId();
	}

	/**
	* Sets the group ID of this layout set.
	*
	* @param groupId the group ID of this layout set
	*/
	public void setGroupId(long groupId) {
		_layoutSet.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this layout set.
	*
	* @return the company ID of this layout set
	*/
	public long getCompanyId() {
		return _layoutSet.getCompanyId();
	}

	/**
	* Sets the company ID of this layout set.
	*
	* @param companyId the company ID of this layout set
	*/
	public void setCompanyId(long companyId) {
		_layoutSet.setCompanyId(companyId);
	}

	/**
	* Returns the private layout of this layout set.
	*
	* @return the private layout of this layout set
	*/
	public boolean getPrivateLayout() {
		return _layoutSet.getPrivateLayout();
	}

	/**
	* Returns <code>true</code> if this layout set is private layout.
	*
	* @return <code>true</code> if this layout set is private layout; <code>false</code> otherwise
	*/
	public boolean isPrivateLayout() {
		return _layoutSet.isPrivateLayout();
	}

	/**
	* Sets whether this layout set is private layout.
	*
	* @param privateLayout the private layout of this layout set
	*/
	public void setPrivateLayout(boolean privateLayout) {
		_layoutSet.setPrivateLayout(privateLayout);
	}

	/**
	* Returns the logo of this layout set.
	*
	* @return the logo of this layout set
	*/
	public boolean getLogo() {
		return _layoutSet.getLogo();
	}

	/**
	* Returns <code>true</code> if this layout set is logo.
	*
	* @return <code>true</code> if this layout set is logo; <code>false</code> otherwise
	*/
	public boolean isLogo() {
		return _layoutSet.isLogo();
	}

	/**
	* Sets whether this layout set is logo.
	*
	* @param logo the logo of this layout set
	*/
	public void setLogo(boolean logo) {
		_layoutSet.setLogo(logo);
	}

	/**
	* Returns the logo ID of this layout set.
	*
	* @return the logo ID of this layout set
	*/
	public long getLogoId() {
		return _layoutSet.getLogoId();
	}

	/**
	* Sets the logo ID of this layout set.
	*
	* @param logoId the logo ID of this layout set
	*/
	public void setLogoId(long logoId) {
		_layoutSet.setLogoId(logoId);
	}

	/**
	* Returns the theme ID of this layout set.
	*
	* @return the theme ID of this layout set
	*/
	public java.lang.String getThemeId() {
		return _layoutSet.getThemeId();
	}

	/**
	* Sets the theme ID of this layout set.
	*
	* @param themeId the theme ID of this layout set
	*/
	public void setThemeId(java.lang.String themeId) {
		_layoutSet.setThemeId(themeId);
	}

	/**
	* Returns the color scheme ID of this layout set.
	*
	* @return the color scheme ID of this layout set
	*/
	public java.lang.String getColorSchemeId() {
		return _layoutSet.getColorSchemeId();
	}

	/**
	* Sets the color scheme ID of this layout set.
	*
	* @param colorSchemeId the color scheme ID of this layout set
	*/
	public void setColorSchemeId(java.lang.String colorSchemeId) {
		_layoutSet.setColorSchemeId(colorSchemeId);
	}

	/**
	* Returns the wap theme ID of this layout set.
	*
	* @return the wap theme ID of this layout set
	*/
	public java.lang.String getWapThemeId() {
		return _layoutSet.getWapThemeId();
	}

	/**
	* Sets the wap theme ID of this layout set.
	*
	* @param wapThemeId the wap theme ID of this layout set
	*/
	public void setWapThemeId(java.lang.String wapThemeId) {
		_layoutSet.setWapThemeId(wapThemeId);
	}

	/**
	* Returns the wap color scheme ID of this layout set.
	*
	* @return the wap color scheme ID of this layout set
	*/
	public java.lang.String getWapColorSchemeId() {
		return _layoutSet.getWapColorSchemeId();
	}

	/**
	* Sets the wap color scheme ID of this layout set.
	*
	* @param wapColorSchemeId the wap color scheme ID of this layout set
	*/
	public void setWapColorSchemeId(java.lang.String wapColorSchemeId) {
		_layoutSet.setWapColorSchemeId(wapColorSchemeId);
	}

	/**
	* Returns the css of this layout set.
	*
	* @return the css of this layout set
	*/
	public java.lang.String getCss() {
		return _layoutSet.getCss();
	}

	/**
	* Sets the css of this layout set.
	*
	* @param css the css of this layout set
	*/
	public void setCss(java.lang.String css) {
		_layoutSet.setCss(css);
	}

	/**
	* Returns the page count of this layout set.
	*
	* @return the page count of this layout set
	*/
	public int getPageCount() {
		return _layoutSet.getPageCount();
	}

	/**
	* Sets the page count of this layout set.
	*
	* @param pageCount the page count of this layout set
	*/
	public void setPageCount(int pageCount) {
		_layoutSet.setPageCount(pageCount);
	}

	/**
	* Returns the settings of this layout set.
	*
	* @return the settings of this layout set
	*/
	public java.lang.String getSettings() {
		return _layoutSet.getSettings();
	}

	/**
	* Sets the settings of this layout set.
	*
	* @param settings the settings of this layout set
	*/
	public void setSettings(java.lang.String settings) {
		_layoutSet.setSettings(settings);
	}

	/**
	* Returns the layout set prototype uuid of this layout set.
	*
	* @return the layout set prototype uuid of this layout set
	*/
	public java.lang.String getLayoutSetPrototypeUuid() {
		return _layoutSet.getLayoutSetPrototypeUuid();
	}

	/**
	* Sets the layout set prototype uuid of this layout set.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid of this layout set
	*/
	public void setLayoutSetPrototypeUuid(
		java.lang.String layoutSetPrototypeUuid) {
		_layoutSet.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	/**
	* Returns the layout set prototype link enabled of this layout set.
	*
	* @return the layout set prototype link enabled of this layout set
	*/
	public boolean getLayoutSetPrototypeLinkEnabled() {
		return _layoutSet.getLayoutSetPrototypeLinkEnabled();
	}

	/**
	* Returns <code>true</code> if this layout set is layout set prototype link enabled.
	*
	* @return <code>true</code> if this layout set is layout set prototype link enabled; <code>false</code> otherwise
	*/
	public boolean isLayoutSetPrototypeLinkEnabled() {
		return _layoutSet.isLayoutSetPrototypeLinkEnabled();
	}

	/**
	* Sets whether this layout set is layout set prototype link enabled.
	*
	* @param layoutSetPrototypeLinkEnabled the layout set prototype link enabled of this layout set
	*/
	public void setLayoutSetPrototypeLinkEnabled(
		boolean layoutSetPrototypeLinkEnabled) {
		_layoutSet.setLayoutSetPrototypeLinkEnabled(layoutSetPrototypeLinkEnabled);
	}

	public boolean isNew() {
		return _layoutSet.isNew();
	}

	public void setNew(boolean n) {
		_layoutSet.setNew(n);
	}

	public boolean isCachedModel() {
		return _layoutSet.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layoutSet.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layoutSet.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutSet.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_layoutSet.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutSet.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutSet.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutSetWrapper((LayoutSet)_layoutSet.clone());
	}

	public int compareTo(com.liferay.portal.model.LayoutSet layoutSet) {
		return _layoutSet.compareTo(layoutSet);
	}

	@Override
	public int hashCode() {
		return _layoutSet.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.LayoutSet> toCacheModel() {
		return _layoutSet.toCacheModel();
	}

	public com.liferay.portal.model.LayoutSet toEscapedModel() {
		return new LayoutSetWrapper(_layoutSet.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutSet.toString();
	}

	public java.lang.String toXmlString() {
		return _layoutSet.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_layoutSet.persist();
	}

	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getTheme();
	}

	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getColorScheme();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getGroup();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getSettingsProperties() {
		return _layoutSet.getSettingsProperties();
	}

	public java.lang.String getSettingsProperty(java.lang.String key) {
		return _layoutSet.getSettingsProperty(key);
	}

	public java.lang.String getThemeSetting(java.lang.String key,
		java.lang.String device)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getThemeSetting(key, device);
	}

	public java.lang.String getVirtualHostname() {
		return _layoutSet.getVirtualHostname();
	}

	public com.liferay.portal.model.Theme getWapTheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getWapTheme();
	}

	public com.liferay.portal.model.ColorScheme getWapColorScheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getWapColorScheme();
	}

	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties) {
		_layoutSet.setSettingsProperties(settingsProperties);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public LayoutSet getWrappedLayoutSet() {
		return _layoutSet;
	}

	public LayoutSet getWrappedModel() {
		return _layoutSet;
	}

	public void resetOriginalValues() {
		_layoutSet.resetOriginalValues();
	}

	private LayoutSet _layoutSet;
}