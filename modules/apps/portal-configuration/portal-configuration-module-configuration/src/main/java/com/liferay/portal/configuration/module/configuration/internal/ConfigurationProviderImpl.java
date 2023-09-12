/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.module.configuration.internal;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;

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
@Component(service = ConfigurationProvider.class)
public class ConfigurationProviderImpl implements ConfigurationProvider {

	@Override
	public <T> void deleteCompanyConfiguration(Class<T> clazz, long companyId)
		throws ConfigurationException {

		_deleteFactoryConfiguration(
			_getConfigurationPid(clazz),
			ExtendedObjectClassDefinition.Scope.COMPANY, companyId);
	}

	@Override
	public <T> void deleteGroupConfiguration(Class<T> clazz, long groupId)
		throws ConfigurationException {

		_deleteFactoryConfiguration(
			_getConfigurationPid(clazz),
			ExtendedObjectClassDefinition.Scope.GROUP, groupId);
	}

	@Override
	public <T> void deletePortletInstanceConfiguration(
			Class<T> clazz, String portletId)
		throws ConfigurationException {

		_deleteFactoryConfiguration(
			_getConfigurationPid(clazz),
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE, portletId);
	}

	@Override
	public <T> void deleteSystemConfiguration(Class<T> clazz)
		throws ConfigurationException {

		_deleteConfiguration(_getConfigurationPid(clazz));
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
						FallbackKeysSettingsUtil.getSettings(settingsLocator)));

			return configurationInvocationHandler.createProxy();
		}
		catch (ReflectiveOperationException | SettingsException exception) {
			throw new ConfigurationException(
				"Unable to load configuration of type " + clazz.getName(),
				exception);
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
	public <T> T getPortletInstanceConfiguration(
			Class<T> clazz, ThemeDisplay themeDisplay)
		throws ConfigurationException {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletResource = portletDisplay.getPortletResource();

		if (Validator.isNull(portletResource)) {
			return getPortletInstanceConfiguration(
				clazz, themeDisplay.getLayout(), portletDisplay.getId());
		}

		return getPortletInstanceConfiguration(
			clazz, themeDisplay.getLayout(), portletResource);
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

		_saveFactoryConfiguration(
			_getConfigurationPid(clazz),
			ExtendedObjectClassDefinition.Scope.COMPANY, companyId, properties);
	}

	@Override
	public <T> void saveGroupConfiguration(
			Class<T> clazz, long groupId, Dictionary<String, Object> properties)
		throws ConfigurationException {

		_saveFactoryConfiguration(
			_getConfigurationPid(clazz),
			ExtendedObjectClassDefinition.Scope.GROUP, groupId, properties);
	}

	@Override
	public <T> void saveGroupConfiguration(
			long groupId, String pid, Dictionary<String, Object> properties)
		throws ConfigurationException {

		_saveFactoryConfiguration(
			pid, ExtendedObjectClassDefinition.Scope.GROUP, groupId,
			properties);
	}

	@Override
	public <T> void savePortletInstanceConfiguration(
			Class<T> clazz, String portletId,
			Dictionary<String, Object> properties)
		throws ConfigurationException {

		_saveFactoryConfiguration(
			_getConfigurationPid(clazz),
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE, portletId,
			properties);
	}

	@Override
	public <T> void saveSystemConfiguration(
			Class<T> clazz, Dictionary<String, Object> properties)
		throws ConfigurationException {

		_saveConfiguration(_getConfigurationPid(clazz), properties);
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
		catch (InvalidSyntaxException | IOException exception) {
			throw new ConfigurationException(
				"Unable to delete configuration " + pid, exception);
		}
	}

	private void _deleteFactoryConfiguration(
			String factoryPid, ExtendedObjectClassDefinition.Scope scope,
			Serializable scopePK)
		throws ConfigurationException {

		String scopedFactoryPid = factoryPid + ".scoped";

		try {
			Configuration configuration = _getFactoryConfiguration(
				scopedFactoryPid, scope, scopePK);

			if (configuration != null) {
				configuration.delete();
			}
		}
		catch (IOException ioException) {
			throw new ConfigurationException(
				"Unable to delete factory configuration " + scopedFactoryPid,
				ioException);
		}
	}

	private String _getConfigurationPid(Class<?> clazz) {
		Meta.OCD ocd = clazz.getAnnotation(Meta.OCD.class);

		if (ocd == null) {
			return null;
		}

		return ocd.id();
	}

	private Configuration _getFactoryConfiguration(
			String factoryPid, ExtendedObjectClassDefinition.Scope scope,
			Serializable scopePK)
		throws ConfigurationException {

		try {
			String filterString = StringBundler.concat(
				"(&(service.factoryPid=", factoryPid, ")(",
				scope.getPropertyKey(), "=", scopePK, "))");

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(filterString);

			if (configurations != null) {
				return configurations[0];
			}

			return null;
		}
		catch (InvalidSyntaxException | IOException exception) {
			throw new ConfigurationException(
				"Unable to retrieve factory configuration " + factoryPid,
				exception);
		}
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
		catch (IOException ioException) {
			throw new ConfigurationException(
				"Unable to save configuration " + pid, ioException);
		}
	}

	private void _saveFactoryConfiguration(
			String factoryPid, ExtendedObjectClassDefinition.Scope scope,
			Serializable scopePK, Dictionary<String, Object> properties)
		throws ConfigurationException {

		String scopedFactoryPid = factoryPid + ".scoped";

		try {
			Configuration configuration = _getFactoryConfiguration(
				scopedFactoryPid, scope, scopePK);

			if (configuration == null) {
				configuration = _configurationAdmin.createFactoryConfiguration(
					scopedFactoryPid, StringPool.QUESTION);
			}

			properties.put(scope.getPropertyKey(), scopePK);

			configuration.update(properties);
		}
		catch (IOException ioException) {
			throw new ConfigurationException(
				"Unable to save factory configuration " + scopedFactoryPid,
				ioException);
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}