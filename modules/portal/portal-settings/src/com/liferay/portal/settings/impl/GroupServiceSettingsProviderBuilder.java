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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.GroupServiceSettings;
import com.liferay.portal.kernel.settings.GroupServiceSettingsProvider;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.settings.definition.SettingsDefinition;

import java.lang.reflect.Constructor;

import java.util.Map;

/**
 * @author Iván Zaera
 */
public class GroupServiceSettingsProviderBuilder
		<S extends GroupServiceSettings, C>
	implements GroupServiceSettingsProvider<S>,
		SettingsProviderBuilder<GroupServiceSettingsProvider<S>> {

	public GroupServiceSettingsProviderBuilder(
		SettingsDefinition<S, C> settingsDefinition,
		SettingsFactory settingsFactory) {

		_validateSettingsDefinition(settingsDefinition);

		_settingsDefinition = settingsDefinition;
		_settingsFactory = settingsFactory;
	}

	@Override
	public S getGroupServiceSettings(long groupId) throws PortalException {
		Settings settings = _settingsFactory.getGroupServiceSettings(
			groupId, _getSettingsId());

		try {
			return _getSettings(settings);
		}
		catch (Exception e) {
			throw new PortalException(
				"Unable to create proxy for group service settings", e);
		}
	}

	@Override
	public S getGroupServiceSettings(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = _settingsFactory.getGroupServiceSettings(
			groupId, _getSettingsId());

		try {
			return _getSettings(
				new ParameterMapSettings(parameterMap, settings));
		}
		catch (Exception e) {
			throw new PortalException(
				"Unable to create proxy for group service settings", e);
		}
	}

	@Override
	public GroupServiceSettingsProvider<S> getSettingsProvider() {
		return this;
	}

	@Override
	public Class<GroupServiceSettingsProvider<S>>
		getSettingsProviderServiceClass() {

		return (Class)GroupServiceSettingsProvider.class;
	}

	@SuppressWarnings("unchecked")
	private S _getSettings(Settings settings) throws Exception {
		Class<?> settingsExtraClass =
			_settingsDefinition.getSettingsExtraClass();

		Constructor<?> constructor = settingsExtraClass.getConstructor(
			TypedSettings.class);

		TypedSettings typedSettings = new TypedSettings(settings);

		Object settingsExtraImplementation = constructor.newInstance(
			typedSettings);

		SettingsInvocationHandler<S, C> settingsInvocationHandler =
			new SettingsInvocationHandler<>(
				_settingsDefinition.getSettingsClass(),
				settingsExtraImplementation, typedSettings);

		return settingsInvocationHandler.createProxy();
	}

	private String _getSettingsId() {
		String[] settingsIds = _settingsDefinition.getSettingsIds();

		return settingsIds[0];
	}

	private void _validateSettingsDefinition(
		SettingsDefinition settingsDefinition) {

		Class<?> settingsClass = settingsDefinition.getSettingsClass();

		if (!GroupServiceSettings.class.isAssignableFrom(settingsClass)) {
			throw new IllegalArgumentException(
				"Settings class " + settingsClass.getName() + " does not " +
					"implement GroupServiceSettings");
		}

		String[] settingsIds = settingsDefinition.getSettingsIds();

		if (settingsIds.length != 1) {
			throw new IllegalArgumentException(
				"GroupServiceSettings definitions must return a single " +
					"settingsId");
		}
	}

	private final SettingsDefinition<S, C> _settingsDefinition;
	private final SettingsFactory _settingsFactory;

}