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

/**
 * <p>
 * This class is a wrapper for {@link PluginSetting}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PluginSetting
 * @generated
 */
public class PluginSettingWrapper implements PluginSetting,
	ModelWrapper<PluginSetting> {
	public PluginSettingWrapper(PluginSetting pluginSetting) {
		_pluginSetting = pluginSetting;
	}

	public Class<?> getModelClass() {
		return PluginSetting.class;
	}

	public String getModelClassName() {
		return PluginSetting.class.getName();
	}

	/**
	* Returns the primary key of this plugin setting.
	*
	* @return the primary key of this plugin setting
	*/
	public long getPrimaryKey() {
		return _pluginSetting.getPrimaryKey();
	}

	/**
	* Sets the primary key of this plugin setting.
	*
	* @param primaryKey the primary key of this plugin setting
	*/
	public void setPrimaryKey(long primaryKey) {
		_pluginSetting.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the plugin setting ID of this plugin setting.
	*
	* @return the plugin setting ID of this plugin setting
	*/
	public long getPluginSettingId() {
		return _pluginSetting.getPluginSettingId();
	}

	/**
	* Sets the plugin setting ID of this plugin setting.
	*
	* @param pluginSettingId the plugin setting ID of this plugin setting
	*/
	public void setPluginSettingId(long pluginSettingId) {
		_pluginSetting.setPluginSettingId(pluginSettingId);
	}

	/**
	* Returns the company ID of this plugin setting.
	*
	* @return the company ID of this plugin setting
	*/
	public long getCompanyId() {
		return _pluginSetting.getCompanyId();
	}

	/**
	* Sets the company ID of this plugin setting.
	*
	* @param companyId the company ID of this plugin setting
	*/
	public void setCompanyId(long companyId) {
		_pluginSetting.setCompanyId(companyId);
	}

	/**
	* Returns the plugin ID of this plugin setting.
	*
	* @return the plugin ID of this plugin setting
	*/
	public java.lang.String getPluginId() {
		return _pluginSetting.getPluginId();
	}

	/**
	* Sets the plugin ID of this plugin setting.
	*
	* @param pluginId the plugin ID of this plugin setting
	*/
	public void setPluginId(java.lang.String pluginId) {
		_pluginSetting.setPluginId(pluginId);
	}

	/**
	* Returns the plugin type of this plugin setting.
	*
	* @return the plugin type of this plugin setting
	*/
	public java.lang.String getPluginType() {
		return _pluginSetting.getPluginType();
	}

	/**
	* Sets the plugin type of this plugin setting.
	*
	* @param pluginType the plugin type of this plugin setting
	*/
	public void setPluginType(java.lang.String pluginType) {
		_pluginSetting.setPluginType(pluginType);
	}

	/**
	* Returns the roles of this plugin setting.
	*
	* @return the roles of this plugin setting
	*/
	public java.lang.String getRoles() {
		return _pluginSetting.getRoles();
	}

	/**
	* Sets the roles of this plugin setting.
	*
	* @param roles the roles of this plugin setting
	*/
	public void setRoles(java.lang.String roles) {
		_pluginSetting.setRoles(roles);
	}

	/**
	* Returns the active of this plugin setting.
	*
	* @return the active of this plugin setting
	*/
	public boolean getActive() {
		return _pluginSetting.getActive();
	}

	/**
	* Returns <code>true</code> if this plugin setting is active.
	*
	* @return <code>true</code> if this plugin setting is active; <code>false</code> otherwise
	*/
	public boolean isActive() {
		return _pluginSetting.isActive();
	}

	/**
	* Sets whether this plugin setting is active.
	*
	* @param active the active of this plugin setting
	*/
	public void setActive(boolean active) {
		_pluginSetting.setActive(active);
	}

	public boolean isNew() {
		return _pluginSetting.isNew();
	}

	public void setNew(boolean n) {
		_pluginSetting.setNew(n);
	}

	public boolean isCachedModel() {
		return _pluginSetting.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_pluginSetting.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _pluginSetting.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _pluginSetting.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_pluginSetting.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _pluginSetting.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_pluginSetting.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PluginSettingWrapper((PluginSetting)_pluginSetting.clone());
	}

	public int compareTo(com.liferay.portal.model.PluginSetting pluginSetting) {
		return _pluginSetting.compareTo(pluginSetting);
	}

	@Override
	public int hashCode() {
		return _pluginSetting.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.PluginSetting> toCacheModel() {
		return _pluginSetting.toCacheModel();
	}

	public com.liferay.portal.model.PluginSetting toEscapedModel() {
		return new PluginSettingWrapper(_pluginSetting.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _pluginSetting.toString();
	}

	public java.lang.String toXmlString() {
		return _pluginSetting.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_pluginSetting.persist();
	}

	/**
	* Adds a role to the list of roles.
	*/
	public void addRole(java.lang.String role) {
		_pluginSetting.addRole(role);
	}

	/**
	* Returns an array of required roles of the plugin.
	*
	* @return an array of required roles of the plugin
	*/
	public java.lang.String[] getRolesArray() {
		return _pluginSetting.getRolesArray();
	}

	/**
	* Returns <code>true</code> if the user has permission to use this plugin
	*
	* @param userId the primary key of the user
	* @return <code>true</code> if the user has permission to use this plugin
	*/
	public boolean hasPermission(long userId) {
		return _pluginSetting.hasPermission(userId);
	}

	/**
	* Returns <code>true</code> if the plugin has a role with the specified
	* name.
	*
	* @param roleName the role name
	* @return <code>true</code> if the plugin has a role with the specified
	name
	*/
	public boolean hasRoleWithName(java.lang.String roleName) {
		return _pluginSetting.hasRoleWithName(roleName);
	}

	/**
	* Sets an array of required roles of the plugin.
	*/
	public void setRolesArray(java.lang.String[] rolesArray) {
		_pluginSetting.setRolesArray(rolesArray);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public PluginSetting getWrappedPluginSetting() {
		return _pluginSetting;
	}

	public PluginSetting getWrappedModel() {
		return _pluginSetting;
	}

	public void resetOriginalValues() {
		_pluginSetting.resetOriginalValues();
	}

	private PluginSetting _pluginSetting;
}