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

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
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
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
@Component(service = SettingsFactory.class)
public class SettingsFactoryImpl implements SettingsFactory {

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

		return _settingsDescriptorServiceTrackerMap.getService(settingsId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_fallbackKeysServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, FallbackKeys.class, "settingsId");

		_settingsDescriptorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ConfigurationPidMapping.class, null,
				ServiceReferenceMapperFactory.createFromFunction(
					bundleContext,
					ConfigurationPidMapping::getConfigurationPid),
				new ServiceTrackerCustomizer
					<ConfigurationPidMapping, SettingsDescriptor>() {

					@Override
					public SettingsDescriptor addingService(
						ServiceReference<ConfigurationPidMapping>
							serviceReference) {

						ConfigurationPidMapping configurationPidMapping =
							bundleContext.getService(serviceReference);

						Class<?> clazz =
							configurationPidMapping.getConfigurationBeanClass();

						if (clazz.getAnnotation(Settings.Config.class) ==
								null) {

							return new ConfigurationBeanClassSettingsDescriptor(
								clazz);
						}

						return new AnnotatedSettingsDescriptor(clazz);
					}

					@Override
					public void modifiedService(
						ServiceReference<ConfigurationPidMapping>
							serviceReference,
						SettingsDescriptor settingsDescriptor) {
					}

					@Override
					public void removedService(
						ServiceReference<ConfigurationPidMapping>
							serviceReference,
						SettingsDescriptor settingsDescriptor) {

						bundleContext.ungetService(serviceReference);
					}

				});
	}

	@Deactivate
	protected void deactivate() {
		_settingsDescriptorServiceTrackerMap.close();

		_fallbackKeysServiceTrackerMap.close();
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

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletItemLocalService(
		PortletItemLocalService portletItemLocalService) {

		_portletItemLocalService = portletItemLocalService;
	}

	private Settings _applyFallbackKeys(String settingsId, Settings settings) {
		if (settings instanceof FallbackKeys) {
			return settings;
		}

		settingsId = PortletIdCodec.decodePortletName(settingsId);

		FallbackKeys fallbackKeys = _fallbackKeysServiceTrackerMap.getService(
			settingsId);

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
				_log.debug(noSuchPortletItemException);
			}

			portletItem = _portletItemLocalService.updatePortletItem(
				PrincipalThreadLocal.getUserId(), groupId, name, portletId,
				PortletPreferences.class.getName());
		}

		return portletItem;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SettingsFactoryImpl.class);

	private ServiceTrackerMap<String, FallbackKeys>
		_fallbackKeysServiceTrackerMap;
	private GroupLocalService _groupLocalService;
	private PortletItemLocalService _portletItemLocalService;
	private ServiceTrackerMap<String, SettingsDescriptor>
		_settingsDescriptorServiceTrackerMap;

}