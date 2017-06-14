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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.resource.manager.ClassLoaderResourceManager;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.settings.ConfigurationBeanSettings;
import com.liferay.portal.kernel.settings.LocationVariableResolver;
import com.liferay.portal.kernel.settings.PortletPreferencesSettings;
import com.liferay.portal.kernel.settings.PropertiesSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.util.PrefsPropsUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.portlet.PortletPreferences;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Iván Zaera
 * @author Jorge Ferrer
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = SettingsLocatorHelper.class)
@DoPrivileged
public class SettingsLocatorHelperImpl implements SettingsLocatorHelper {

	public PortletPreferences getCompanyPortletPreferences(
		long companyId, String settingsId) {

		return _portletPreferencesLocalService.getStrictPreferences(
			companyId, companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0,
			settingsId);
	}

	@Override
	public Settings getCompanyPortletPreferencesSettings(
		long companyId, String settingsId, Settings parentSettings) {

		return new PortletPreferencesSettings(
			getCompanyPortletPreferences(companyId, settingsId),
			parentSettings);
	}

	@Override
	public Settings getConfigurationBeanSettings(String configurationPid) {
		Class<?> configurationBeanClass = _configurationBeanClasses.get(
			configurationPid);

		if (configurationBeanClass == null) {
			return _portalPropertiesSettings;
		}

		Settings configurationBeanSettings = _configurationBeanSettings.get(
			configurationBeanClass);

		if (configurationBeanSettings == null) {
			return _portalPropertiesSettings;
		}

		return configurationBeanSettings;
	}

	/**
	 * @deprecated As of 2.0.0, replaced by {@link
	 *             #getConfigurationBeanSettings(String)}
	 */
	@Deprecated
	@Override
	public Settings getConfigurationBeanSettings(
		String configurationPid, Settings parentSettings) {

		return getConfigurationBeanSettings(configurationPid);
	}

	public PortletPreferences getGroupPortletPreferences(
		long groupId, String settingsId) {

		try {
			Group group = _groupLocalService.getGroup(groupId);

			return _portletPreferencesLocalService.getStrictPreferences(
				group.getCompanyId(), groupId,
				PortletKeys.PREFS_OWNER_TYPE_GROUP, 0, settingsId);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	@Override
	public Settings getGroupPortletPreferencesSettings(
		long groupId, String settingsId, Settings parentSettings) {

		return new PortletPreferencesSettings(
			getGroupPortletPreferences(groupId, settingsId), parentSettings);
	}

	@Override
	public Settings getPortalPreferencesSettings(
		long companyId, Settings parentSettings) {

		return new PortletPreferencesSettings(
			PrefsPropsUtil.getPreferences(companyId), parentSettings);
	}

	/**
	 * @deprecated As of 2.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public Settings getPortalPropertiesSettings() {
		return _portalPropertiesSettings;
	}

	public PortletPreferences getPortletInstancePortletPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId) {

		if (PortletIdCodec.hasUserId(portletId)) {
			ownerId = PortletIdCodec.decodeUserId(portletId);
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		return _portletPreferencesLocalService.getStrictPreferences(
			companyId, ownerId, ownerType, plid, portletId);
	}

	public PortletPreferences getPortletInstancePortletPreferences(
		long companyId, long plid, String portletId) {

		return getPortletInstancePortletPreferences(
			companyId, PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId);
	}

	@Override
	public Settings getPortletInstancePortletPreferencesSettings(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, Settings parentSettings) {

		return new PortletPreferencesSettings(
			getPortletInstancePortletPreferences(
				companyId, ownerId, ownerType, plid, portletId),
			parentSettings);
	}

	@Override
	public Settings getPortletInstancePortletPreferencesSettings(
		long companyId, long plid, String portletId, Settings parentSettings) {

		return new PortletPreferencesSettings(
			getPortletInstancePortletPreferences(companyId, plid, portletId),
			parentSettings);
	}

	@Override
	public Settings getServerSettings(String settingsId) {
		return getConfigurationBeanSettings(settingsId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_configurationBeanDeclarationServiceTracker =
			new ConfigurationBeanDeclarationServiceTracker(bundleContext);

		_configurationBeanDeclarationServiceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_configurationBeanDeclarationServiceTracker.close();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setConfigurationPidMapping(
		ConfigurationPidMapping configurationPidMapping) {

		_configurationBeanClasses.put(
			configurationPidMapping.getConfigurationPid(),
			configurationPidMapping.getConfigurationBeanClass());
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_portalPropertiesSettings = new PropertiesSettings(
			new LocationVariableResolver(
				new ClassLoaderResourceManager(
					PortalClassLoaderUtil.getClassLoader()),
				this),
			props.getProperties());
	}

	protected void unsetConfigurationPidMapping(
		ConfigurationPidMapping configurationPidMapping) {

		_configurationBeanClasses.remove(
			configurationPidMapping.getConfigurationPid());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SettingsLocatorHelperImpl.class);

	private final ConcurrentMap<String, Class<?>> _configurationBeanClasses =
		new ConcurrentHashMap<>();
	private ServiceTracker
		<ConfigurationBeanDeclaration, ConfigurationBeanManagedService>
			_configurationBeanDeclarationServiceTracker;
	private final Map<Class<?>, Settings> _configurationBeanSettings =
		new ConcurrentHashMap<>();
	private GroupLocalService _groupLocalService;
	private Settings _portalPropertiesSettings;
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	private class ConfigurationBeanDeclarationServiceTracker
		extends ServiceTracker
			<ConfigurationBeanDeclaration, ConfigurationBeanManagedService> {

		@Override
		public ConfigurationBeanManagedService addingService(
			ServiceReference<ConfigurationBeanDeclaration> serviceReference) {

			ConfigurationBeanDeclaration configurationBeanDeclaration =
				context.getService(serviceReference);

			Class<?> configurationBeanClass =
				configurationBeanDeclaration.getConfigurationBeanClass();

			ConfigurationBeanManagedService configurationBeanManagedService =
				new ConfigurationBeanManagedService(
					context, configurationBeanClass,
					(configurationBean) -> {
						ClassLoader classLoader =
							configurationBeanClass.getClassLoader();

						LocationVariableResolver locationVariableResolver =
							new LocationVariableResolver(
								new ClassLoaderResourceManager(classLoader),
								SettingsLocatorHelperImpl.this);

						_configurationBeanSettings.put(
							configurationBeanClass,
							new ConfigurationBeanSettings(
								locationVariableResolver, configurationBean,
								_portalPropertiesSettings));
					});

			_configurationBeanClasses.put(
				configurationBeanManagedService.getConfigurationPid(),
				configurationBeanClass);

			configurationBeanManagedService.register();

			return configurationBeanManagedService;
		}

		@Override
		public void removedService(
			ServiceReference<ConfigurationBeanDeclaration> serviceReference,
			ConfigurationBeanManagedService configurationBeanManagedService) {

			context.ungetService(serviceReference);

			configurationBeanManagedService.unregister();

			_configurationBeanClasses.remove(
				configurationBeanManagedService.getConfigurationPid());

			_configurationBeanSettings.remove(
				configurationBeanManagedService.getConfigurationPid());
		}

		private ConfigurationBeanDeclarationServiceTracker(
			BundleContext context) {

			super(context, ConfigurationBeanDeclaration.class, null);
		}

	}

}