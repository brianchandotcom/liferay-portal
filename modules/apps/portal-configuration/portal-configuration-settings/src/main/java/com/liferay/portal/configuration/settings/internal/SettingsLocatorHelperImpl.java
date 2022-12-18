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

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeInformation;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeService;
import com.liferay.portal.configuration.settings.internal.scoped.configuration.admin.service.ScopedConfigurationManagedServiceFactory;
import com.liferay.portal.configuration.settings.internal.util.ConfigurationPidUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.resource.manager.ClassLoaderResourceManager;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.settings.ConfigurationBeanSettings;
import com.liferay.portal.kernel.settings.LocationVariableResolver;
import com.liferay.portal.kernel.settings.PortletPreferencesSettings;
import com.liferay.portal.kernel.settings.PropertiesSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.Props;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.portlet.PortletPreferences;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Iván Zaera
 * @author Jorge Ferrer
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = SettingsLocatorHelper.class)
public class SettingsLocatorHelperImpl implements SettingsLocatorHelper {

	@Override
	public Settings getCompanyConfigurationBeanSettings(
		long companyId, String configurationPid, Settings parentSettings) {

		return _getScopedConfigurationBeanSettings(
			ExtendedObjectClassDefinition.Scope.COMPANY, companyId,
			configurationPid, parentSettings);
	}

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

	@Override
	public Settings getGroupConfigurationBeanSettings(
		long groupId, String configurationPid, Settings parentSettings) {

		return _getScopedConfigurationBeanSettings(
			ExtendedObjectClassDefinition.Scope.GROUP, groupId,
			configurationPid, parentSettings);
	}

	public PortletPreferences getGroupPortletPreferences(
		long groupId, String settingsId) {

		try {
			Group group = _groupLocalService.getGroup(groupId);

			return _portletPreferencesLocalService.getStrictPreferences(
				group.getCompanyId(), groupId,
				PortletKeys.PREFS_OWNER_TYPE_GROUP, 0, settingsId);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
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
			_prefsProps.getPreferences(companyId), parentSettings);
	}

	@Override
	public Settings getPortletInstanceConfigurationBeanSettings(
		String portletId, String configurationPid, Settings parentSettings) {

		return _getScopedConfigurationBeanSettings(
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE, portletId,
			configurationPid, parentSettings);
	}

	public PortletPreferences getPortletInstancePortletPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId) {

		if (plid != LayoutConstants.DEFAULT_PLID) {
			Layout layout = _layoutLocalService.fetchLayout(plid);

			if (layout != null) {
				return _portletPreferencesFactory.getStrictPortletSetup(
					layout, portletId);
			}
		}

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

	public SafeCloseable registerConfigurationBeanClass(
		Class<?> configurationBeanClass) {

		if (configurationBeanClass.getAnnotation(Meta.OCD.class) == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping registration for class because Meta.OCD is " +
						"missing: " + configurationBeanClass.getName());
			}

			return null;
		}

		for (Method methods : configurationBeanClass.getMethods()) {
			Meta.AD annotation = methods.getAnnotation(Meta.AD.class);

			if (annotation == null) {
				continue;
			}

			if (annotation.required()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Skipping registration for class because Meta.AD is " +
							"required: " + configurationBeanClass.getName());
				}

				return null;
			}
		}

		String configurationPid = ConfigurationPidUtil.getConfigurationPid(
			configurationBeanClass);

		if (_configurationBeanClasses.containsKey(configurationPid)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping registration for class because it is already " +
						"registered: " + configurationPid);
			}

			return null;
		}

		LocationVariableResolver locationVariableResolver =
			new LocationVariableResolver(
				new ClassLoaderResourceManager(
					configurationBeanClass.getClassLoader()),
				SettingsLocatorHelperImpl.this);

		ConfigurationBeanManagedService configurationBeanManagedService =
			new ConfigurationBeanManagedService(
				_bundleContext, configurationBeanClass,
				configurationBean -> _configurationBeanSettings.put(
					configurationBeanClass,
					new ConfigurationBeanSettings(
						locationVariableResolver, configurationBean,
						_portalPropertiesSettings)));

		configurationBeanManagedService.register();

		ScopedConfigurationManagedServiceFactory
			scopedConfigurationManagedServiceFactory =
				new ScopedConfigurationManagedServiceFactory(
					_bundleContext, configurationBeanClass,
					locationVariableResolver);

		scopedConfigurationManagedServiceFactory.register();

		_scopedConfigurationManagedServiceFactories.put(
			scopedConfigurationManagedServiceFactory.getName(),
			scopedConfigurationManagedServiceFactory);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Registering configuration class: " +
					configurationBeanClass.getName());
		}

		_configurationBeanClasses.put(
			configurationBeanManagedService.getConfigurationPid(),
			configurationBeanClass);

		_settingsFactoryImpl.registerConfigurationBeanClass(
			configurationBeanClass);

		return () -> {
			_settingsFactoryImpl.unregisterConfigurationBeanClass(
				configurationBeanClass);

			_configurationBeanClasses.remove(configurationPid);

			_scopedConfigurationManagedServiceFactories.remove(
				configurationPid);
			scopedConfigurationManagedServiceFactory.unregister();

			_configurationBeanSettings.remove(configurationBeanClass);
			configurationBeanManagedService.unregister();
		};
	}

	public class ConfigurationBeanClassBundleTrackerCustomizer
		implements BundleTrackerCustomizer<List<SafeCloseable>> {

		@Override
		public List<SafeCloseable> addingBundle(
			Bundle bundle, BundleEvent bundleEvent) {

			String bundleSymbolicName = bundle.getSymbolicName();

			if (bundleSymbolicName.endsWith(".test")) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Skipping bundle (do not check test modules): " +
							bundleSymbolicName);
				}

				return null;
			}

			ExtendedMetaTypeInformation metaTypeInformation =
				_extendedMetaTypeService.getMetaTypeInformation(bundle);

			if (metaTypeInformation == null) {
				return null;
			}

			List<SafeCloseable> autoCloseables = new ArrayList<>();

			for (String pid :
					ArrayUtil.append(
						metaTypeInformation.getPids(),
						metaTypeInformation.getFactoryPids())) {

				Class<?> configurationBeanClass;

				try {
					configurationBeanClass = bundle.loadClass(pid);
				}
				catch (ClassNotFoundException classNotFoundException) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Class not found: " +
								classNotFoundException.getMessage());
					}

					continue;
				}

				SafeCloseable safeCloseable = registerConfigurationBeanClass(
					configurationBeanClass);

				if (safeCloseable != null) {
					autoCloseables.add(safeCloseable);
				}
			}

			if (ListUtil.isEmpty(autoCloseables)) {
				return null;
			}

			return autoCloseables;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent bundleEvent,
			List<SafeCloseable> autoCloseables) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent bundleEvent,
			List<SafeCloseable> safeCloseables) {

			if (ListUtil.isEmpty(safeCloseables)) {
				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Un-registering configuration classes for bundle: " +
						bundle.getSymbolicName());
			}

			for (SafeCloseable safeCloseable : safeCloseables) {
				safeCloseable.close();
			}
		}

	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE,
			new ConfigurationBeanClassBundleTrackerCustomizer());

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();

		_bundleTracker = null;

		_bundleContext = null;
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
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesFactory(
		PortletPreferencesFactory portletPreferencesFactory) {

		_portletPreferencesFactory = portletPreferencesFactory;
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

	private Settings _getScopedConfigurationBeanSettings(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK,
		String configurationPid, Settings parentSettings) {

		ScopedConfigurationManagedServiceFactory
			scopedConfigurationManagedServiceFactory =
				_scopedConfigurationManagedServiceFactories.get(
					configurationPid);

		if (scopedConfigurationManagedServiceFactory == null) {
			return parentSettings;
		}

		Object configurationBean =
			scopedConfigurationManagedServiceFactory.getConfiguration(
				scope, scopePK);

		if (configurationBean == null) {
			return parentSettings;
		}

		return new ConfigurationBeanSettings(
			scopedConfigurationManagedServiceFactory.
				getLocationVariableResolver(),
			configurationBean, parentSettings);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SettingsLocatorHelperImpl.class);

	private BundleContext _bundleContext;
	private BundleTracker<List<SafeCloseable>> _bundleTracker;
	private final ConcurrentMap<String, Class<?>> _configurationBeanClasses =
		new ConcurrentHashMap<>();
	private final Map<Class<?>, Settings> _configurationBeanSettings =
		new ConcurrentHashMap<>();

	@Reference
	private ExtendedMetaTypeService _extendedMetaTypeService;

	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private Settings _portalPropertiesSettings;
	private PortletPreferencesFactory _portletPreferencesFactory;
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PrefsProps _prefsProps;

	private final Map<String, ScopedConfigurationManagedServiceFactory>
		_scopedConfigurationManagedServiceFactories = new ConcurrentHashMap<>();

	@Reference
	private SettingsFactoryImpl _settingsFactoryImpl;

}