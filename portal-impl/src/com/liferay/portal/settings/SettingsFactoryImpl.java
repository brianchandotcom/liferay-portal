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

	@Override
	public void clearCache() {
		_portletPropertiesMap.clear();
		_propertiesMap.clear();
	}

	@Override
	public Settings getCompanyServiceSettings(
		long companyId, String serviceName) {

		return applyFallbackKeys(
			serviceName, getCompanySettings(companyId, serviceName));
	}

	@Override
	public Settings getGroupServiceCompanyDefaultSettings(
		long companyId, String serviceName) {

		return applyFallbackKeys(
			serviceName,
			new PortletPreferencesSettings(
				getCompanyPortletPreferences(companyId, serviceName)));
	}

	@Override
	public Settings getGroupServiceSettings(long groupId, String serviceName)
		throws PortalException {

		return applyFallbackKeys(
			serviceName, getGroupSettings(groupId, serviceName));
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

		List<ArchivedSettings> archivedSettingsList =
			new ArrayList<ArchivedSettings>();

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
	public Settings getPortletInstanceCompanyDefaultSettings(
		long companyId, String portletId) {

		return applyFallbackKeys(
			PortletConstants.getRootPortletId(portletId),
			new PortletPreferencesSettings(
				getCompanyPortletPreferences(companyId, portletId)));
	}

	@Override
	public Settings getPortletInstanceGroupDefaultSettings(
			long groupId, String portletId)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		return applyFallbackKeys(
			PortletConstants.getRootPortletId(portletId),
			new PortletPreferencesSettings(
				getGroupPortletPreferences(
					group.getCompanyId(), groupId, portletId)));
	}

	@Override
	public Settings getPortletInstanceSettings(Layout layout, String portletId)
		throws PortalException {

		return applyFallbackKeys(
			PortletConstants.getRootPortletId(portletId),
			new PortletPreferencesSettings(
				getPortletInstancePortletPreferences(layout, portletId),
				getGroupSettings(layout.getGroupId(), portletId)));
	}

	@Override
	public void registerSettingsMetadata(
		String settingsId, FallbackKeys fallbackKeys,
		String[] multiValuedKeysArray, ResourceManager resourceManager) {

		settingsId = PortletConstants.getRootPortletId(settingsId);

		if (_multiValuedKeysMap.get(settingsId) != null) {
			throw new IllegalStateException(
				"Unable to overwrite multi valued keys for " + settingsId);
		}

		_fallbackKeysMap.put(settingsId, fallbackKeys);

		List<String> multiValuedKeysList = new ArrayList<String>();

		Collections.addAll(multiValuedKeysList, multiValuedKeysArray);

		multiValuedKeysList = Collections.unmodifiableList(multiValuedKeysList);

		_multiValuedKeysMap.put(settingsId, multiValuedKeysList);

		_resourceManagers.put(settingsId, resourceManager);
	}

	protected Settings applyFallbackKeys(String settingsId, Settings settings) {
		if (settings instanceof FallbackKeys) {
			return settings;
		}

		FallbackKeys fallbackKeys = _fallbackKeysMap.get(settingsId);

		if (fallbackKeys != null) {
			settings = new FallbackSettings(settings, fallbackKeys);
		}

		return settings;
	}

	protected PortletPreferences getCompanyPortletPreferences(
		long companyId, String settingsId) {

		return PortletPreferencesLocalServiceUtil.getStrictPreferences(
			companyId, companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0,
			settingsId);
	}

	protected Settings getCompanySettings(long companyId, String settingsId) {
		return new PortletPreferencesSettings(
			getCompanyPortletPreferences(companyId, settingsId),
			getPortalPreferencesSettings(companyId, settingsId));
	}

	protected PortletPreferences getGroupPortletPreferences(
		long companyId, long groupId, String settingsId) {

		return PortletPreferencesLocalServiceUtil.getStrictPreferences(
			companyId, groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP, 0,
			settingsId);
	}

	protected Settings getGroupSettings(long groupId, String settingsId)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		long companyId = group.getCompanyId();

		return new PortletPreferencesSettings(
			getGroupPortletPreferences(companyId, groupId, settingsId),
			getCompanySettings(companyId, settingsId));
	}

	protected PortletPreferences getPortalPreferences(long companyId) {
		return PortalPreferencesLocalServiceUtil.getPreferences(
			companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY);
	}

	protected PortletPreferencesSettings getPortalPreferencesSettings(
		long companyId, String settingsId) {

		return new PortletPreferencesSettings(
			getPortalPreferences(companyId),
			getPortletPropertiesSettings(settingsId));
	}

	protected Properties getPortalProperties(String settingsId) {
		Properties portalProperties = _propertiesMap.get(settingsId);

		if (portalProperties != null) {
			return portalProperties;
		}

		portalProperties = PropsUtil.getProperties();

		_propertiesMap.put(settingsId, portalProperties);

		return portalProperties;
	}

	protected PropertiesSettings getPortalPropertiesSettings(
		String settingsId) {

		return new PropertiesSettings(
			getPortalProperties(settingsId),
			new ClassLoaderResourceManager(
				PortalClassLoaderUtil.getClassLoader()));
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

	protected Properties getPortletProperties(
		String settingsId, ResourceManager resourceManager) {

		Properties properties = _portletPropertiesMap.get(settingsId);

		if (properties != null) {
			return properties;
		}

		properties = new Properties();

		if (resourceManager == null) {
			return properties;
		}

		ResourceRetriever resourceRetriever =
			resourceManager.getResourceRetriever("portlet.properties");

		InputStream inputStream = resourceRetriever.getInputStream();

		try {
			properties.load(inputStream);
		}
		catch (Exception e) {
		}

		_portletPropertiesMap.put(settingsId, properties);

		return properties;
	}

	protected PropertiesSettings getPortletPropertiesSettings(
		String settingsId) {

		ResourceManager resourceManager = getResourceManager(settingsId);

		return new PropertiesSettings(
			getPortletProperties(settingsId, resourceManager), resourceManager,
			getPortalPropertiesSettings(settingsId));
	}

	protected ResourceManager getResourceManager(String settingsId) {
		settingsId = PortletConstants.getRootPortletId(settingsId);

		return _resourceManagers.get(settingsId);
	}

	private final ConcurrentMap<String, FallbackKeys> _fallbackKeysMap =
		new ConcurrentHashMap<String, FallbackKeys>();
	private final ConcurrentMap<String, List<String>> _multiValuedKeysMap =
		new ConcurrentHashMap<String, List<String>>();
	private final Map<String, Properties> _portletPropertiesMap =
		new ConcurrentHashMap<String, Properties>();
	private final Map<String, Properties> _propertiesMap =
		new ConcurrentHashMap<String, Properties>();
	private final ConcurrentMap<String, ResourceManager> _resourceManagers =
		new ConcurrentHashMap<String, ResourceManager>();

}