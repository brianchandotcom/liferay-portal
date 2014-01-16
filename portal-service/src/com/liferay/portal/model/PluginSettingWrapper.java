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
 * This class is a wrapper for {@link PluginSetting}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PluginSetting
 * @generated
 */
@ProviderType
public class PluginSettingWrapper implements PluginSetting,
	ModelWrapper<PluginSetting> {
	public PluginSettingWrapper(PluginSetting pluginSetting) {
		_pluginSetting = pluginSetting;
	}

	@Override
	public Class<?> getModelClass() {
		return PluginSetting.class;
	}

	@Override
	public String getModelClassName() {
		return PluginSetting.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("pluginSettingId", getPluginSettingId());
		attributes.put("companyId", getCompanyId());
		attributes.put("pluginId", getPluginId());
		attributes.put("pluginType", getPluginType());
		attributes.put("roles", getRoles());
		attributes.put("active", getActive());
		attributes.put("mvccVersion", getMvccVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long pluginSettingId = (Long)attributes.get("pluginSettingId");

		if (pluginSettingId != null) {
			setPluginSettingId(pluginSettingId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String pluginId = (String)attributes.get("pluginId");

		if (pluginId != null) {
			setPluginId(pluginId);
		}

		String pluginType = (String)attributes.get("pluginType");

		if (pluginType != null) {
			setPluginType(pluginType);
		}

		String roles = (String)attributes.get("roles");

		if (roles != null) {
			setRoles(roles);
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
	* Returns the primary key of this plugin setting.
	*
	* @return the primary key of this plugin setting
	*/
	@Override
	public long getPrimaryKey() {
		return _pluginSetting.getPrimaryKey();
	}

	/**
	* Sets the primary key of this plugin setting.
	*
	* @param primaryKey the primary key of this plugin setting
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_pluginSetting.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the plugin setting ID of this plugin setting.
	*
	* @return the plugin setting ID of this plugin setting
	*/
	@Override
	public long getPluginSettingId() {
		return _pluginSetting.getPluginSettingId();
	}

	/**
	* Sets the plugin setting ID of this plugin setting.
	*
	* @param pluginSettingId the plugin setting ID of this plugin setting
	*/
	@Override
	public void setPluginSettingId(long pluginSettingId) {
		_pluginSetting.setPluginSettingId(pluginSettingId);
	}

	/**
	* Returns the company ID of this plugin setting.
	*
	* @return the company ID of this plugin setting
	*/
	@Override
	public long getCompanyId() {
		return _pluginSetting.getCompanyId();
	}

	/**
	* Sets the company ID of this plugin setting.
	*
	* @param companyId the company ID of this plugin setting
	*/
	@Override
	public void setCompanyId(long companyId) {
		_pluginSetting.setCompanyId(companyId);
	}

	/**
	* Returns the plugin ID of this plugin setting.
	*
	* @return the plugin ID of this plugin setting
	*/
	@Override
	public java.lang.String getPluginId() {
		return _pluginSetting.getPluginId();
	}

	/**
	* Sets the plugin ID of this plugin setting.
	*
	* @param pluginId the plugin ID of this plugin setting
	*/
	@Override
	public void setPluginId(java.lang.String pluginId) {
		_pluginSetting.setPluginId(pluginId);
	}

	/**
	* Returns the plugin type of this plugin setting.
	*
	* @return the plugin type of this plugin setting
	*/
	@Override
	public java.lang.String getPluginType() {
		return _pluginSetting.getPluginType();
	}

	/**
	* Sets the plugin type of this plugin setting.
	*
	* @param pluginType the plugin type of this plugin setting
	*/
	@Override
	public void setPluginType(java.lang.String pluginType) {
		_pluginSetting.setPluginType(pluginType);
	}

	/**
	* Returns the roles of this plugin setting.
	*
	* @return the roles of this plugin setting
	*/
	@Override
	public java.lang.String getRoles() {
		return _pluginSetting.getRoles();
	}

	/**
	* Sets the roles of this plugin setting.
	*
	* @param roles the roles of this plugin setting
	*/
	@Override
	public void setRoles(java.lang.String roles) {
		_pluginSetting.setRoles(roles);
	}

	/**
	* Returns the active of this plugin setting.
	*
	* @return the active of this plugin setting
	*/
	@Override
	public boolean getActive() {
		return _pluginSetting.getActive();
	}

	/**
	* Returns <code>true</code> if this plugin setting is active.
	*
	* @return <code>true</code> if this plugin setting is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _pluginSetting.isActive();
	}

	/**
	* Sets whether this plugin setting is active.
	*
	* @param active the active of this plugin setting
	*/
	@Override
	public void setActive(boolean active) {
		_pluginSetting.setActive(active);
	}

	/**
	* Returns the mvcc version of this plugin setting.
	*
	* @return the mvcc version of this plugin setting
	*/
	@Override
	public long getMvccVersion() {
		return _pluginSetting.getMvccVersion();
	}

	/**
	* Sets the mvcc version of this plugin setting.
	*
	* @param mvccVersion the mvcc version of this plugin setting
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_pluginSetting.setMvccVersion(mvccVersion);
	}

	@Override
	public boolean isNew() {
		return _pluginSetting.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_pluginSetting.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _pluginSetting.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_pluginSetting.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _pluginSetting.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _pluginSetting.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_pluginSetting.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _pluginSetting.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_pluginSetting.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_pluginSetting.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_pluginSetting.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PluginSettingWrapper((PluginSetting)_pluginSetting.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.PluginSetting pluginSetting) {
		return _pluginSetting.compareTo(pluginSetting);
	}

	@Override
	public int hashCode() {
		return _pluginSetting.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.PluginSetting> toCacheModel() {
		return _pluginSetting.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.PluginSetting toEscapedModel() {
		return new PluginSettingWrapper(_pluginSetting.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.PluginSetting toUnescapedModel() {
		return new PluginSettingWrapper(_pluginSetting.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _pluginSetting.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _pluginSetting.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_pluginSetting.persist();
	}

	/**
	* Adds a role to the list of roles.
	*/
	@Override
	public void addRole(java.lang.String role) {
		_pluginSetting.addRole(role);
	}

	/**
	* Returns an array of required roles of the plugin.
	*
	* @return an array of required roles of the plugin
	*/
	@Override
	public java.lang.String[] getRolesArray() {
		return _pluginSetting.getRolesArray();
	}

	/**
	* Returns <code>true</code> if the user has permission to use this plugin
	*
	* @param userId the primary key of the user
	* @return <code>true</code> if the user has permission to use this plugin
	*/
	@Override
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
	@Override
	public boolean hasRoleWithName(java.lang.String roleName) {
		return _pluginSetting.hasRoleWithName(roleName);
	}

	/**
	* Sets an array of required roles of the plugin.
	*/
	@Override
	public void setRolesArray(java.lang.String[] rolesArray) {
		_pluginSetting.setRolesArray(rolesArray);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PluginSettingWrapper)) {
			return false;
		}

		PluginSettingWrapper pluginSettingWrapper = (PluginSettingWrapper)obj;

		if (Validator.equals(_pluginSetting, pluginSettingWrapper._pluginSetting)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public PluginSetting getWrappedPluginSetting() {
		return _pluginSetting;
	}

	@Override
	public PluginSetting getWrappedModel() {
		return _pluginSetting;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _pluginSetting.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _pluginSetting.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_pluginSetting.resetOriginalValues();
	}

	private PluginSetting _pluginSetting;
}