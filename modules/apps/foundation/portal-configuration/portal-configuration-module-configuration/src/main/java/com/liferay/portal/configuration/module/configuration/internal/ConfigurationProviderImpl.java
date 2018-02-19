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

package com.liferay.portal.configuration.module.configuration.internal;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition.Scope;
import com.liferay.portal.configuration.metatype.util.ConfigurationScopedPidUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletInstance;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Dictionary;

import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = ConfigurationProvider.class)
public class ConfigurationProviderImpl implements ConfigurationProvider {

	@Override
	public <T> void deleteCompanyConfiguration(Class<T> clazz, long companyId)
		throws ConfigurationException {

		String configurationPid = _getConfigurationPid(clazz);

		String scopedPid =
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				configurationPid, Scope.COMPANY, String.valueOf(companyId));

		_deleteConfiguration(scopedPid);
	}

	@Override
	public <T> void deleteGroupConfiguration(Class<T> clazz, long groupId)
		throws ConfigurationException {

		String configurationPid = _getConfigurationPid(clazz);

		String scopedPid =
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				configurationPid, Scope.GROUP, String.valueOf(groupId));

		_deleteConfiguration(scopedPid);
	}

	@Override
	public <T> void deletePortletInstanceConfiguration(
			Class<T> clazz, String portletId)
		throws ConfigurationException {

		String configurationPid = _getConfigurationPid(clazz);

		String scopedPid =
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				configurationPid, Scope.PORTLET_INSTANCE, portletId);

		_deleteConfiguration(scopedPid);
	}

	@Override
	public <T> void deleteSystemConfiguration(Class<T> clazz)
		throws ConfigurationException {

		String configurationPid = _getConfigurationPid(clazz);

		_deleteConfiguration(configurationPid);
	}

	@Override
	public <T> T getCompanyConfiguration(Class<T> clazz, long companyId)
		throws ConfigurationException {

		String settingsId = _getSettingsId(clazz);
		String configurationPid = _getConfigurationPid(clazz);

		return getConfiguration(
			clazz,
			new CompanyServiceSettingsLocator(
				companyId, settingsId, configurationPid));
	}

	@Override
	public <T> T getConfiguration(
			Class<T> clazz, SettingsLocator settingsLocator)
		throws ConfigurationException {

		try {
			ConfigurationInvocationHandler<T> configurationInvocationHandler =
				new ConfigurationInvocationHandler<>(
					clazz,
					new TypedSettings(
						_settingsFactory.getSettings(settingsLocator)));

			return configurationInvocationHandler.createProxy();
		}
		catch (ReflectiveOperationException | SettingsException e) {
			throw new ConfigurationException(
				"Unable to load configuration of type " + clazz.getName(), e);
		}
	}

	@Override
	public <T> T getGroupConfiguration(Class<T> clazz, long groupId)
		throws ConfigurationException {

		String settingsId = _getSettingsId(clazz);
		String configurationPid = _getConfigurationPid(clazz);

		return getConfiguration(
			clazz,
			new GroupServiceSettingsLocator(
				groupId, settingsId, configurationPid));
	}

	/**
	 * @deprecated As of 2.0.0, replaced by {@link
	 *             #getPortletInstanceConfiguration(Class, Layout, String)}
	 */
	@Deprecated
	@Override
	public <T> T getPortletInstanceConfiguration(
			Class<T> clazz, Layout layout, PortletInstance portletInstance)
		throws ConfigurationException {

		return getPortletInstanceConfiguration(
			clazz, layout, portletInstance.getPortletInstanceKey());
	}

	@Override
	public <T> T getPortletInstanceConfiguration(
			Class<T> clazz, Layout layout, String portletId)
		throws ConfigurationException {

		String configurationPid = _getConfigurationPid(clazz);

		if (Validator.isNull(configurationPid)) {
			return getConfiguration(
				clazz, new PortletInstanceSettingsLocator(layout, portletId));
		}

		return getConfiguration(
			clazz,
			new PortletInstanceSettingsLocator(
				layout, portletId, configurationPid));
	}

	@Override
	public <T> T getSystemConfiguration(Class<T> clazz)
		throws ConfigurationException {

		String configurationPid = _getConfigurationPid(clazz);

		return getConfiguration(
			clazz, new SystemSettingsLocator(configurationPid));
	}

	@Override
	public <T> void saveCompanyConfiguration(
			Class<T> clazz, long companyId,
			Dictionary<String, Object> properties)
		throws ConfigurationException {

		String configurationPid = _getConfigurationPid(clazz);

		String scopedPid =
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				configurationPid, Scope.COMPANY, String.valueOf(companyId));

		_saveConfiguration(scopedPid, properties);
	}

	@Override
	public <T> void saveGroupConfiguration(
			Class<T> clazz, long groupId, Dictionary<String, Object> properties)
		throws ConfigurationException {

		String configurationPid = _getConfigurationPid(clazz);

		String scopedPid =
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				configurationPid, Scope.GROUP, String.valueOf(groupId));

		_saveConfiguration(scopedPid, properties);
	}

	@Override
	public <T> void savePortletInstanceConfiguration(
			Class<T> clazz, String portletId,
			Dictionary<String, Object> properties)
		throws ConfigurationException {

		String configurationPid = _getConfigurationPid(clazz);

		String scopedPid =
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				configurationPid, Scope.PORTLET_INSTANCE, portletId);

		_saveConfiguration(scopedPid, properties);
	}

	@Override
	public <T> void saveSystemConfiguration(
			Class<T> clazz, Dictionary<String, Object> properties)
		throws ConfigurationException {

		String configurationPid = _getConfigurationPid(clazz);

		_saveConfiguration(configurationPid, properties);
	}

	private void _deleteConfiguration(String pid)
		throws ConfigurationException {

		try {
			String pidFilter = StringBundler.concat(
				StringPool.OPEN_PARENTHESIS, Constants.SERVICE_PID,
				StringPool.EQUAL, pid, StringPool.CLOSE_PARENTHESIS);

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(pidFilter);

			if (configurations != null) {
				configurations[0].delete();
			}
		}
		catch (InvalidSyntaxException | IOException e) {
			throw new ConfigurationException(
				"Unable to delete configuration " + pid, e);
		}
	}

	private String _getConfigurationPid(Class<?> clazz) {
		Meta.OCD ocd = clazz.getAnnotation(Meta.OCD.class);

		if (ocd == null) {
			return null;
		}

		return ocd.id();
	}

	private <T> String _getSettingsId(Class<T> clazz) {
		ExtendedObjectClassDefinition eocd = clazz.getAnnotation(
			ExtendedObjectClassDefinition.class);

		String settingsId = null;

		if (eocd != null) {
			settingsId = eocd.settingsId();
		}

		if (Validator.isNull(settingsId)) {
			settingsId = _getConfigurationPid(clazz);
		}

		return settingsId;
	}

	private void _saveConfiguration(
			String pid, Dictionary<String, Object> properties)
		throws ConfigurationException {

		try {
			Configuration configuration = _configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION);

			configuration.update(properties);
		}
		catch (IOException ioe) {
			throw new ConfigurationException(
				"Unable to save configuration " + pid, ioe);
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private SettingsFactory _settingsFactory;

}