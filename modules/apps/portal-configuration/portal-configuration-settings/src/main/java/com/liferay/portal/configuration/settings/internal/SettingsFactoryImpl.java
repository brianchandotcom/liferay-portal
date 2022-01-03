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

package com.liferay.portal.configuration.settings.internal;

import com.liferay.portal.configuration.settings.internal.util.ConfigurationPidUtil;
import com.liferay.portal.kernel.exception.NoSuchPortletItemException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PortletItemLocalService;
import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.FallbackSettings;
import com.liferay.portal.kernel.settings.PortalSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = SettingsFactory.class)
public class SettingsFactoryImpl implements SettingsFactory {

	public SettingsFactoryImpl() {
		registerSettingsMetadata(PortalSettings.class, null, null);
	}

	@Override
	public ArchivedSettings getPortletInstanceArchivedSettings(
			long groupId, String portletId, String name)
		throws SettingsException {

		try {
			return new ArchivedSettingsImpl(
				_getPortletItem(groupId, portletId, name));
		}
		catch (PortalException portalException) {
			throw new SettingsException(portalException);
		}
	}

	@Override
	public List<ArchivedSettings> getPortletInstanceArchivedSettingsList(
		long groupId, String portletId) {

		List<ArchivedSettings> archivedSettingsList = new ArrayList<>();

		List<PortletItem> portletItems =
			_portletItemLocalService.getPortletItems(
				groupId, portletId,
				com.liferay.portal.kernel.model.PortletPreferences.class.
					getName());

		for (PortletItem portletItem : portletItems) {
			archivedSettingsList.add(new ArchivedSettingsImpl(portletItem));
		}

		return archivedSettingsList;
	}

	@Override
	public Settings getSettings(SettingsLocator settingsLocator)
		throws SettingsException {

		return _applyFallbackKeys(
			settingsLocator.getSettingsId(), settingsLocator.getSettings());
	}

	@Override
	public SettingsDescriptor getSettingsDescriptor(String settingsId) {
		settingsId = PortletIdCodec.decodePortletName(settingsId);

		return _settingsDescriptors.get(settingsId);
	}

	@Override
	public void registerSettingsMetadata(
		Class<?> settingsClass, Object configurationBean,
		FallbackKeys fallbackKeys) {

		SettingsDescriptor settingsDescriptor = new AnnotatedSettingsDescriptor(
			settingsClass);

		Settings.Config settingsConfig = settingsClass.getAnnotation(
			Settings.Config.class);

		for (String settingsId : settingsConfig.settingsIds()) {
			register(settingsId, settingsDescriptor, fallbackKeys);
		}
	}

	protected long getCompanyId(long groupId) throws SettingsException {
		try {
			Group group = _groupLocalService.getGroup(groupId);

			return group.getCompanyId();
		}
		catch (PortalException portalException) {
			throw new SettingsException(portalException);
		}
	}

	protected void register(
		String settingsId, SettingsDescriptor settingsDescriptor,
		FallbackKeys fallbackKeys) {

		_settingsDescriptors.put(settingsId, settingsDescriptor);

		if (fallbackKeys != null) {
			_fallbackKeysMap.put(settingsId, fallbackKeys);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setConfigurationBeanDeclaration(
		ConfigurationBeanDeclaration configurationBeanDeclaration) {

		Class<?> configurationBeanClass =
			configurationBeanDeclaration.getConfigurationBeanClass();

		String settingsId = ConfigurationPidUtil.getConfigurationPid(
			configurationBeanClass);

		ConfigurationBeanClassSettingsDescriptor
			configurationBeanClassSettingsDescriptor =
				new ConfigurationBeanClassSettingsDescriptor(
					configurationBeanClass);

		register(settingsId, configurationBeanClassSettingsDescriptor, null);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setConfigurationPidMapping(
		ConfigurationPidMapping configurationPidMapping) {

		String settingsId = configurationPidMapping.getConfigurationPid();

		ConfigurationBeanClassSettingsDescriptor
			configurationBeanClassSettingsDescriptor =
				new ConfigurationBeanClassSettingsDescriptor(
					configurationPidMapping.getConfigurationBeanClass());

		register(settingsId, configurationBeanClassSettingsDescriptor, null);
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletItemLocalService(
		PortletItemLocalService portletItemLocalService) {

		_portletItemLocalService = portletItemLocalService;
	}

	protected void unregister(String settingsId) {
		_fallbackKeysMap.remove(settingsId);

		_settingsDescriptors.remove(settingsId);
	}

	protected void unsetConfigurationBeanDeclaration(
		ConfigurationBeanDeclaration configurationBeanDeclaration) {

		String settingsId = ConfigurationPidUtil.getConfigurationPid(
			configurationBeanDeclaration.getConfigurationBeanClass());

		unregister(settingsId);
	}

	protected void unsetConfigurationPidMapping(
		ConfigurationPidMapping configurationPidMapping) {

		unregister(configurationPidMapping.getConfigurationPid());
	}

	private Settings _applyFallbackKeys(String settingsId, Settings settings) {
		if (settings instanceof FallbackKeys) {
			return settings;
		}

		settingsId = PortletIdCodec.decodePortletName(settingsId);

		FallbackKeys fallbackKeys = _fallbackKeysMap.get(settingsId);

		if (fallbackKeys != null) {
			settings = new FallbackSettings(settings, fallbackKeys);
		}

		return settings;
	}

	private PortletItem _getPortletItem(
			long groupId, String portletId, String name)
		throws PortalException {

		PortletItem portletItem = null;

		try {
			portletItem = _portletItemLocalService.getPortletItem(
				groupId, name, portletId, PortletPreferences.class.getName());
		}
		catch (NoSuchPortletItemException noSuchPortletItemException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(
					noSuchPortletItemException, noSuchPortletItemException);
			}

			portletItem = _portletItemLocalService.updatePortletItem(
				PrincipalThreadLocal.getUserId(), groupId, name, portletId,
				PortletPreferences.class.getName());
		}

		return portletItem;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SettingsFactoryImpl.class);

	private final ConcurrentMap<String, FallbackKeys> _fallbackKeysMap =
		new ConcurrentHashMap<>();
	private GroupLocalService _groupLocalService;
	private PortletItemLocalService _portletItemLocalService;
	private final Map<String, SettingsDescriptor> _settingsDescriptors =
		new ConcurrentHashMap<>();

}