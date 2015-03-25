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

package com.liferay.portal.settings.impl;

import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.PortletInstanceSettings;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsProvider;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.settings.definition.SettingsDefinition;
import com.liferay.portal.model.Layout;

import java.lang.reflect.Constructor;

import java.util.Map;

/**
 * @author Iván Zaera
 */
public class PortletInstanceSettingsProviderBuilder
	<P extends PortletInstanceSettings, C>
	implements PortletInstanceSettingsProvider<P>,
			   SettingsProviderBuilder<PortletInstanceSettingsProvider<P>> {

	public PortletInstanceSettingsProviderBuilder(
		SettingsDefinition<P, C> settingsDefinition,
		SettingsFactory settingsFactory) {

		_validateSettingsDefinition(settingsDefinition);

		_settingsDefinition = settingsDefinition;
		_settingsFactory = settingsFactory;
	}

	@Override
	public P getPortletInstanceSettings(Layout layout, String portletId)
		throws SettingsException {

		Settings settings = _settingsFactory.getPortletInstanceSettings(
			layout, portletId);

		try {
			return _getSettings(settings);
		}
		catch (Exception e) {
			throw new SettingsException(
				"Unable to create proxy for group service settings", e);
		}
	}

	@Override
	public P getPortletInstanceSettings(
			Layout layout, String portletId, Map<String, String[]> parameterMap)
		throws SettingsException {

		Settings settings = _settingsFactory.getPortletInstanceSettings(
			layout, portletId);

		try {
			return _getSettings(
				new ParameterMapSettings(parameterMap, settings));
		}
		catch (Exception e) {
			throw new SettingsException(
				"Unable to create proxy for group service settings", e);
		}
	}

	@Override
	public PortletInstanceSettingsProvider<P> getSettingsProvider() {
		return this;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Class<PortletInstanceSettingsProvider<P>>
		getSettingsProviderServiceClass() {

		return (Class)PortletInstanceSettingsProvider.class;
	}

	private P _getSettings(Settings settings) throws Exception {
		Class<?> settingsExtraClass =
			_settingsDefinition.getSettingsExtraClass();

		Constructor<?> constructor = settingsExtraClass.getConstructor(
			TypedSettings.class);

		TypedSettings typedSettings = new TypedSettings(settings);

		Object settingsExtraImplementation = constructor.newInstance(
			typedSettings);

		SettingsInvocationHandler<P, C> settingsInvocationHandler =
			new SettingsInvocationHandler<>(
				_settingsDefinition.getSettingsClass(),
				settingsExtraImplementation, typedSettings);

		return settingsInvocationHandler.createProxy();
	}

	private void _validateSettingsDefinition(
		SettingsDefinition<P, C> settingsDefinition) {

		Class<?> settingsClass = settingsDefinition.getSettingsClass();

		if (!PortletInstanceSettings.class.isAssignableFrom(settingsClass)) {
			throw new IllegalArgumentException(
				"Settings class " + settingsClass.getName() + " is not a " +
					"portlet instance settings");
		}
	}

	private final SettingsDefinition<P, C> _settingsDefinition;
	private final SettingsFactory _settingsFactory;

}