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
public class PluginSettingWrapper
	extends BaseModelWrapper<PluginSetting>
	implements ModelWrapper<PluginSetting>, PluginSetting {

	public PluginSettingWrapper(PluginSetting pluginSetting) {
		super(pluginSetting);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("pluginSettingId", getPluginSettingId());
		attributes.put("companyId", getCompanyId());
		attributes.put("pluginId", getPluginId());
		attributes.put("pluginType", getPluginType());
		attributes.put("roles", getRoles());
		attributes.put("active", isActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	}

	/**
	 * Adds a role to the list of roles.
	 */
	@Override
	public void addRole(String role) {
		model.addRole(role);
	}

	/**
	 * Returns the active of this plugin setting.
	 *
	 * @return the active of this plugin setting
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the company ID of this plugin setting.
	 *
	 * @return the company ID of this plugin setting
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this plugin setting.
	 *
	 * @return the mvcc version of this plugin setting
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the plugin ID of this plugin setting.
	 *
	 * @return the plugin ID of this plugin setting
	 */
	@Override
	public String getPluginId() {
		return model.getPluginId();
	}

	/**
	 * Returns the plugin setting ID of this plugin setting.
	 *
	 * @return the plugin setting ID of this plugin setting
	 */
	@Override
	public long getPluginSettingId() {
		return model.getPluginSettingId();
	}

	/**
	 * Returns the plugin type of this plugin setting.
	 *
	 * @return the plugin type of this plugin setting
	 */
	@Override
	public String getPluginType() {
		return model.getPluginType();
	}

	/**
	 * Returns the primary key of this plugin setting.
	 *
	 * @return the primary key of this plugin setting
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the roles of this plugin setting.
	 *
	 * @return the roles of this plugin setting
	 */
	@Override
	public String getRoles() {
		return model.getRoles();
	}

	/**
	 * Returns an array of required roles of the plugin.
	 *
	 * @return an array of required roles of the plugin
	 */
	@Override
	public String[] getRolesArray() {
		return model.getRolesArray();
	}

	/**
	 * Returns <code>true</code> if the user has permission to use this plugin
	 *
	 * @param userId the primary key of the user
	 * @return <code>true</code> if the user has permission to use this plugin
	 */
	@Override
	public boolean hasPermission(long userId) {
		return model.hasPermission(userId);
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
	public boolean hasRoleWithName(String roleName) {
		return model.hasRoleWithName(roleName);
	}

	/**
	 * Returns <code>true</code> if this plugin setting is active.
	 *
	 * @return <code>true</code> if this plugin setting is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a plugin setting model instance should use the <code>PluginSetting</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this plugin setting is active.
	 *
	 * @param active the active of this plugin setting
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the company ID of this plugin setting.
	 *
	 * @param companyId the company ID of this plugin setting
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this plugin setting.
	 *
	 * @param mvccVersion the mvcc version of this plugin setting
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the plugin ID of this plugin setting.
	 *
	 * @param pluginId the plugin ID of this plugin setting
	 */
	@Override
	public void setPluginId(String pluginId) {
		model.setPluginId(pluginId);
	}

	/**
	 * Sets the plugin setting ID of this plugin setting.
	 *
	 * @param pluginSettingId the plugin setting ID of this plugin setting
	 */
	@Override
	public void setPluginSettingId(long pluginSettingId) {
		model.setPluginSettingId(pluginSettingId);
	}

	/**
	 * Sets the plugin type of this plugin setting.
	 *
	 * @param pluginType the plugin type of this plugin setting
	 */
	@Override
	public void setPluginType(String pluginType) {
		model.setPluginType(pluginType);
	}

	/**
	 * Sets the primary key of this plugin setting.
	 *
	 * @param primaryKey the primary key of this plugin setting
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the roles of this plugin setting.
	 *
	 * @param roles the roles of this plugin setting
	 */
	@Override
	public void setRoles(String roles) {
		model.setRoles(roles);
	}

	/**
	 * Sets an array of required roles of the plugin.
	 */
	@Override
	public void setRolesArray(String[] rolesArray) {
		model.setRolesArray(rolesArray);
	}

	@Override
	protected PluginSettingWrapper wrap(PluginSetting pluginSetting) {
		return new PluginSettingWrapper(pluginSetting);
	}

}