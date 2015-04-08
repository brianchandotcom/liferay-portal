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

package com.liferay.portal.settings;

import com.liferay.portal.NoSuchPortletItemException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.PortalSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.settings.definition.SettingsDefinition;
import com.liferay.portal.kernel.settings.impl.FallbackSettings;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortletItemLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.portlet.PortletPreferences;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
@DoPrivileged
public class SettingsFactoryImpl implements SettingsFactory {

	public SettingsFactoryImpl() {
		registerSettingsMetadata(PortalSettings.class, null, null);
	}

	@Override
	public ArchivedSettings getPortletInstanceArchivedSettings(
			long groupId, String portletId, String name)
		throws SettingsException {

		try {
			PortletItem portletItem = null;

			portletItem = getPortletItem(groupId, portletId, name);

			return new ArchivedSettingsImpl(portletItem);
		}
		catch (PortalException pe) {
			throw new SettingsException(pe);
		}
	}

	@Override
	public List<ArchivedSettings> getPortletInstanceArchivedSettingsList(
		long groupId, String portletId) {

		List<ArchivedSettings> archivedSettingsList = new ArrayList<>();

		List<PortletItem> portletItems =
			PortletItemLocalServiceUtil.getPortletItems(
				groupId, portletId,
				com.liferay.portal.model.PortletPreferences.class.getName());

		for (PortletItem portletItem : portletItems) {
			archivedSettingsList.add(new ArchivedSettingsImpl(portletItem));
		}

		return archivedSettingsList;
	}

	@Override
	public Settings getServerSettings(String settingsId) {
		Settings portalPropertiesSettings =
			_settingsLocatorHelper.getPortalPropertiesSettings();

		return _settingsLocatorHelper.getConfigurationBeanSettings(
			settingsId, portalPropertiesSettings);
	}

	public Settings getSettings(SettingsLocator settingsLocator)
		throws SettingsException {

		Settings settings = settingsLocator.retrieveSettings();

		return applyFallbackKeys(settingsLocator.getSettingsId(), settings);
	}

	@Override
	public SettingsDescriptor getSettingsDescriptor(String settingsId) {
		settingsId = PortletConstants.getRootPortletId(settingsId);

		return _settingsDescriptors.get(settingsId);
	}

	@Override
	public void registerSettingsDefinition(
		SettingsDefinition<?, ?> settingsDefinition, Object configurationBean) {

		SettingsDescriptor settingsDescriptor =
			new SettingsDefinitionSettingsDescriptor(
				settingsDefinition, configurationBean);

		_register(settingsDescriptor, null);
	}

	@Override
	public void registerSettingsMetadata(
		Class<?> settingsClass, Object configurationBean,
		FallbackKeys fallbackKeys) {

		SettingsDescriptor settingsDescriptor = new AnnotatedSettingsDescriptor(
			settingsClass, configurationBean);

		_register(settingsDescriptor, fallbackKeys);
	}

	@Override
	public void unregisterSettingsDefinition(
		SettingsDefinition<?, ?> settingsDefinition) {

		_unregister(settingsDefinition.getSettingsIds());
	}

	protected Settings applyFallbackKeys(String settingsId, Settings settings) {
		if (settings instanceof FallbackKeys) {
			return settings;
		}

		settingsId = PortletConstants.getRootPortletId(settingsId);

		FallbackKeys fallbackKeys = _fallbackKeysMap.get(settingsId);

		if (fallbackKeys != null) {
			settings = new FallbackSettings(settings, fallbackKeys);
		}

		return settings;
	}

	protected long getCompanyId(long groupId) throws SettingsException {
		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			return group.getCompanyId();
		}
		catch (PortalException pe) {
			throw new SettingsException(pe);
		}
	}

	protected PortletItem getPortletItem(
			long groupId, String portletId, String name)
		throws PortalException {

		PortletItem portletItem = null;

		try {
			portletItem = PortletItemLocalServiceUtil.getPortletItem(
				groupId, name, portletId, PortletPreferences.class.getName());
		}
		catch (NoSuchPortletItemException nspie) {
			long userId = PrincipalThreadLocal.getUserId();

			portletItem = PortletItemLocalServiceUtil.updatePortletItem(
				userId, groupId, name, portletId,
				PortletPreferences.class.getName());
		}

		return portletItem;
	}

	private void _register(
		SettingsDescriptor settingsDescriptor, FallbackKeys fallbackKeys) {

		for (String settingsId : settingsDescriptor.getSettingsIds()) {
			_settingsDescriptors.put(settingsId, settingsDescriptor);

			if (fallbackKeys != null) {
				_fallbackKeysMap.put(settingsId, fallbackKeys);
			}
		}
	}

	private void _unregister(String[] settingsIds) {
		for (String settingsId : settingsIds) {
			_fallbackKeysMap.remove(settingsId);

			_settingsDescriptors.remove(settingsId);
		}
	}

	private final ConcurrentMap<String, FallbackKeys> _fallbackKeysMap =
		new ConcurrentHashMap<>();
	private final Map<String, SettingsDescriptor> _settingsDescriptors =
		new ConcurrentHashMap<>();
	private final SettingsLocatorHelper _settingsLocatorHelper =
		new SettingsLocatorHelper();

}