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

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletInstance;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = ConfigurationProvider.class)
public class ConfigurationProviderImpl implements ConfigurationProvider {

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
			Class<?> configurationOverrideClass = getOverrideClass(clazz);

			Object configurationOverrideInstance = null;

			Settings settings = SettingsFactoryUtil.getSettings(
				settingsLocator);

			TypedSettings typedSettings = new TypedSettings(settings);

			if (configurationOverrideClass != null) {
				Constructor<?> constructor =
					configurationOverrideClass.getConstructor(
						TypedSettings.class);

				configurationOverrideInstance = constructor.newInstance(
					typedSettings);
			}

			ConfigurationInvocationHandler<T> configurationInvocationHandler =
				new ConfigurationInvocationHandler<>(
					clazz, configurationOverrideInstance, typedSettings);

			return configurationInvocationHandler.createProxy();
		}
		catch (NoSuchMethodException | InvocationTargetException |
			   InstantiationException | IllegalAccessException |
			   SettingsException e) {

			throw new ConfigurationException(
				"Unable to load configuration of type " + clazz.getName(), e);
		}
	}

	public <T> T getGroupConfiguration(Class<T> clazz, long groupId)
		throws ConfigurationException {

		String settingsId = _getSettingsId(clazz);
		String configurationPid = _getConfigurationPid(clazz);

		return getConfiguration(
			clazz,
			new GroupServiceSettingsLocator(
				groupId, settingsId, configurationPid));
	}

	public <T> T getPortletInstanceConfiguration(
			Class<T> clazz, Layout layout, PortletInstance portletInstance)
		throws ConfigurationException {

		String configurationPid = _getConfigurationPid(clazz);

		String portletInstanceKey = portletInstance.getPortletInstanceKey();

		if (Validator.isNotNull(configurationPid)) {
			return getConfiguration(
				clazz,
				new PortletInstanceSettingsLocator(
					layout, portletInstanceKey, configurationPid));
		}
		else {
			return getConfiguration(
				clazz,
				new PortletInstanceSettingsLocator(layout, portletInstanceKey));
		}
	}

	protected <T> Class<?> getOverrideClass(Class<T> clazz) {
		Settings.OverrideClass overrideClass = clazz.getAnnotation(
			Settings.OverrideClass.class);

		if (overrideClass == null) {
			return null;
		}

		if (overrideClass.value() == Object.class) {
			return null;
		}

		return overrideClass.value();
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

}