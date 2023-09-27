/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the PluginSetting service. Represents a row in the &quot;PluginSetting&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PluginSettingModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.PluginSettingImpl")
@ProviderType
public interface PluginSetting extends PersistedModel, PluginSettingModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.PluginSettingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PluginSetting, Long>
		PLUGIN_SETTING_ID_ACCESSOR = new Accessor<PluginSetting, Long>() {

			@Override
			public Long get(PluginSetting pluginSetting) {
				return pluginSetting.getPluginSettingId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<PluginSetting> getTypeClass() {
				return PluginSetting.class;
			}

		};

	/**
	 * Adds a role to the list of roles.
	 */
	public void addRole(String role);

	/**
	 * Returns an array of required roles of the plugin.
	 *
	 * @return an array of required roles of the plugin
	 */
	public String[] getRolesArray();

	/**
	 * Returns <code>true</code> if the user has permission to use this plugin
	 *
	 * @param userId the primary key of the user
	 * @return <code>true</code> if the user has permission to use this plugin
	 */
	public boolean hasPermission(long userId);

	/**
	 * Returns <code>true</code> if the user has permission to use this plugin
	 *
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @return <code>true</code> if the user has permission to use this plugin
	 */
	public boolean hasPermission(long userId, long groupId);

	/**
	 * Returns <code>true</code> if the plugin has a role with the specified
	 * name.
	 *
	 * @param roleName the role name
	 * @return <code>true</code> if the plugin has a role with the specified
	 name
	 */
	public boolean hasRoleWithName(String roleName);

	/**
	 * Sets an array of required roles of the plugin.
	 */
	public void setRolesArray(String[] rolesArray);

}