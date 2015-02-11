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
import com.liferay.portal.kernel.resource.ResourceRetriever;
import com.liferay.portal.kernel.resource.manager.ClassLoaderResourceManager;
import com.liferay.portal.kernel.resource.manager.ResourceManager;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.FallbackSettings;
import com.liferay.portal.kernel.settings.PortletPreferencesSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.PortletItemLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
		registerSettingsMetadata(
			"com.liferay.portal", null, null, null,
			new ClassLoaderResourceManager(
				PortalClassLoaderUtil.getClassLoader()));
	}

	@Override
	public void clearCache() {
		_portletPropertiesMap.clear();
	}

	@Override
	public Settings getCompanyServiceSettings(
		long companyId, String serviceName) {

		Settings portalPropertiesSettings = getPortalPropertiesSettings();

		Settings serviceConfigurationBeanSettings =
			getServiceConfigurationBeanSettings(
				serviceName, portalPropertiesSettings);

		Settings portalPreferencesSettings = getPortalPreferencesSettings(
			companyId, serviceConfigurationBeanSettings);

		Settings companyPortletPreferencesSettings =
			getCompanyPortletPreferencesSettings(
				companyId, serviceName, portalPreferencesSettings);

		return applyFallbackKeys(
			serviceName, companyPortletPreferencesSettings);
	}

	@Override
	public Settings getGroupServiceSettings(long groupId, String serviceName)
		throws PortalException {

		long companyId = getCompanyId(groupId);

		Settings portalPropertiesSettings = getPortalPropertiesSettings();

		Settings serviceConfigurationBeanSettings =
			getServiceConfigurationBeanSettings(
				serviceName, portalPropertiesSettings);

		Settings portalPreferencesSettings = getPortalPreferencesSettings(
			companyId, serviceConfigurationBeanSettings);

		Settings companyPortletPreferencesSettings =
			getCompanyPortletPreferencesSettings(
				companyId, serviceName, portalPreferencesSettings);

		Settings groupPortletPreferencesSettings =
			getGroupPortletPreferencesSettings(
				groupId, serviceName, companyPortletPreferencesSettings);

		return applyFallbackKeys(serviceName, groupPortletPreferencesSettings);
	}

	@Override
	public List<String> getMultiValuedKeys(String settingsId) {
		settingsId = PortletConstants.getRootPortletId(settingsId);

		List<String> multiValuedKeys = _multiValuedKeysMap.get(settingsId);

		if (multiValuedKeys == null) {
			throw new IllegalStateException(
				"No multi valued keys found for settings ID " + settingsId);
		}

		return multiValuedKeys;
	}

	@Override
	public ArchivedSettings getPortletInstanceArchivedSettings(
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

		return new ArchivedSettingsImpl(portletItem);
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
	public Settings getPortletInstanceSettings(Layout layout, String portletId)
		throws PortalException {

		long companyId = getCompanyId(layout.getGroupId());

		Settings portalPropertiesSettings = getPortalPropertiesSettings();

		Settings serviceConfigurationBeanSettings =
			getServiceConfigurationBeanSettings(
				portletId, portalPropertiesSettings);

		Settings portalPreferencesSettings = getPortalPreferencesSettings(
			companyId, serviceConfigurationBeanSettings);

		Settings companyPortletPreferencesSettings =
			getCompanyPortletPreferencesSettings(
				companyId, portletId, portalPreferencesSettings);

		Settings groupPortletPreferencesSettings =
			getGroupPortletPreferencesSettings(
				layout.getGroupId(), portletId,
				companyPortletPreferencesSettings);

		Settings portletInstancePortletPreferencesSettings =
			getPortletInstancePortletPreferencesSettings(
				layout, portletId, groupPortletPreferencesSettings);

		return applyFallbackKeys(
			portletId, portletInstancePortletPreferencesSettings);
	}

	@Override
	public Settings getServerSettings(String settingsId) {
		Settings portalPropertiesSettings = getPortalPropertiesSettings();

		return getServiceConfigurationBeanSettings(
			settingsId, portalPropertiesSettings);
	}

	@Override
	public void registerSettingsMetadata(
		String settingsId, FallbackKeys fallbackKeys,
		String[] multiValuedKeysArray, Object serviceConfigurationBean,
		ResourceManager resourceManager) {

		settingsId = PortletConstants.getRootPortletId(settingsId);

		if (fallbackKeys != null) {
			_fallbackKeysMap.put(settingsId, fallbackKeys);
		}

		if (multiValuedKeysArray != null) {
			List<String> multiValuedKeysList = new ArrayList<>();

			Collections.addAll(multiValuedKeysList, multiValuedKeysArray);

			multiValuedKeysList = Collections.unmodifiableList(
				multiValuedKeysList);

			_multiValuedKeysMap.put(settingsId, multiValuedKeysList);
		}

		if (serviceConfigurationBean != null) {
			_serviceConfigurationBeans.put(
				settingsId, serviceConfigurationBean);
		}

		_resourceManagers.put(settingsId, resourceManager);
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

	protected long getCompanyId(long groupId) throws PortalException {
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		return group.getCompanyId();
	}

	protected PortletPreferences getCompanyPortletPreferences(
		long companyId, String settingsId) {

		return PortletPreferencesLocalServiceUtil.getStrictPreferences(
			companyId, companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0,
			settingsId);
	}

	protected Settings getCompanyPortletPreferencesSettings(
		long companyId, String settingsId, Settings parentSettings) {

		return new PortletPreferencesSettings(
			getCompanyPortletPreferences(companyId, settingsId),
			parentSettings);
	}

	protected PortletPreferences getGroupPortletPreferences(
			long groupId, String settingsId)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		long companyId = group.getCompanyId();

		return PortletPreferencesLocalServiceUtil.getStrictPreferences(
			companyId, groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP, 0,
			settingsId);
	}

	protected Settings getGroupPortletPreferencesSettings(
			long groupId, String settingsId, Settings parentSettings)
		throws PortalException {

		return new PortletPreferencesSettings(
			getGroupPortletPreferences(groupId, settingsId), parentSettings);
	}

	protected PortletPreferences getPortalPreferences(long companyId) {
		return PortalPreferencesLocalServiceUtil.getPreferences(
			companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY);
	}

	protected Settings getPortalPreferencesSettings(
		long companyId, Settings parentSettings) {

		return new PortletPreferencesSettings(
			getPortalPreferences(companyId), parentSettings);
	}

	protected Properties getPortalProperties() {
		return PropsUtil.getProperties();
	}

	protected Settings getPortalPropertiesSettings() {
		return new PropertiesSettings(
			new LocationVariableResolver(
				new ClassLoaderResourceManager(
					PortalClassLoaderUtil.getClassLoader()),
				this),
			getPortalProperties());
	}

	protected PortletPreferences getPortletInstancePortletPreferences(
		Layout layout, String portletId) {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		if (PortletConstants.hasUserId(portletId)) {
			ownerId = PortletConstants.getUserId(portletId);
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		return PortletPreferencesLocalServiceUtil.getStrictPreferences(
			layout.getCompanyId(), ownerId, ownerType, layout.getPlid(),
			portletId);
	}

	protected Settings getPortletInstancePortletPreferencesSettings(
		Layout layout, String portletId, Settings parentSettings) {

		return new PortletPreferencesSettings(
			getPortletInstancePortletPreferences(layout, portletId),
			parentSettings);
	}

	protected Properties getPortletProperties(String serviceName) {
		Properties properties = _portletPropertiesMap.get(serviceName);

		if (properties == null) {
			properties = new Properties();

			ResourceManager resourceManager = getResourceManager(serviceName);

			if (resourceManager != null) {
				ResourceRetriever resourceRetriever =
					resourceManager.getResourceRetriever("portlet.properties");

				InputStream inputStream = resourceRetriever.getInputStream();

				try {
					properties.load(inputStream);
				}
				catch (Exception e) {
				}

				_portletPropertiesMap.put(serviceName, properties);
			}
		}

		return properties;
	}

	protected ResourceManager getResourceManager(String settingsId) {
		settingsId = PortletConstants.getRootPortletId(settingsId);

		return _resourceManagers.get(settingsId);
	}

	private Object getServiceConfigurationBean(String settingsId) {
		settingsId = PortletConstants.getRootPortletId(settingsId);

		return _serviceConfigurationBeans.get(settingsId);
	}

	private Settings getServiceConfigurationBeanSettings(
		String settingsId, Settings parentSettings) {

		return new ServiceConfigurationBeanSettings(
			getServiceConfigurationBean(settingsId),
			getResourceManager(settingsId), this, parentSettings);
	}

	private final ConcurrentMap<String, FallbackKeys> _fallbackKeysMap =
		new ConcurrentHashMap<>();
	private final ConcurrentMap<String, List<String>> _multiValuedKeysMap =
		new ConcurrentHashMap<>();
	private final Map<String, Properties> _portletPropertiesMap =
		new ConcurrentHashMap<>();
	private final ConcurrentMap<String, ResourceManager> _resourceManagers =
		new ConcurrentHashMap<>();
	private final ConcurrentMap<String, Object> _serviceConfigurationBeans =
		new ConcurrentHashMap<>();

}