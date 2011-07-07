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
 * The extended model interface for the PluginSetting service. Represents a row in the &quot;PluginSetting&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PluginSettingModel
 * @see com.liferay.portal.model.impl.PluginSettingImpl
 * @see com.liferay.portal.model.impl.PluginSettingModelImpl
 * @generated
 */
public interface PluginSetting extends PluginSettingModel, SaveableModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.PluginSettingImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds a role to the list of roles.
	*/
	public void addRole(java.lang.String role);

	/**
	* Sets a string of ordered comma delimited plugin IDs.
	*/
	public void setRoles(java.lang.String roles);

	/**
	* Returns an array of required roles of the plugin.
	*
	* @return an array of required roles of the plugin
	*/
	public java.lang.String[] getRolesArray();

	/**
	* Sets an array of required roles of the plugin.
	*/
	public void setRolesArray(java.lang.String[] rolesArray);

	/**
	* Returns <code>true</code> if the plugin has a role with the specified
	* name.
	*
	* @return <code>true</code> if the plugin has a role with the specified
	name
	*/
	public boolean hasRoleWithName(java.lang.String roleName);

	/**
	* Returns <code>true</code> if the user has permission to use this plugin
	*
	* @return <code>true</code> if the user has permission to use this plugin
	*/
	public boolean hasPermission(long userId);
}