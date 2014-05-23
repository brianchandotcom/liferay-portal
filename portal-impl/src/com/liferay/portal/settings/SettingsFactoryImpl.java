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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.FallbackSettings;
import com.liferay.portal.kernel.settings.PortletPreferencesSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
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

import java.util.ArrayList;
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
		_propertiesMap.clear();
	}

	@Override
	public Settings getCompanyServiceSettings(
			long companyId, String serviceName)
		throws SystemException {

		return applyFallbackKeys(
			getCompanySettings(companyId, serviceName), serviceName);
	}

	@Override
	public Settings getGroupServiceCompanyDefaultSettings(
			long companyId, String serviceName)
		throws SystemException {

		return applyFallbackKeys(
			new PortletPreferencesSettings(
				getCompanyPortletPreferences(companyId, serviceName)),
			serviceName
		);
	}

	@Override
	public Settings getGroupServiceSettings(long groupId, String serviceName)
		throws PortalException, SystemException {

		return applyFallbackKeys(
			getGroupSettings(groupId, serviceName), serviceName);
	}

	@Override
	public ArchivedSettings getPortletInstanceArchivedSettings(
			long groupId, String portletId, String name)
		throws PortalException, SystemException {

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
			long groupId, String portletId)
		throws SystemException {

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
			long companyId, String portletId)
		throws SystemException {

		return applyFallbackKeys(
			new PortletPreferencesSettings(
				getCompanyPortletPreferences(companyId, portletId)),
			PortletConstants.getRootPortletId(portletId)
		);
	}

	@Override
	public Settings getPortletInstanceGroupDefaultSettings(
			long groupId, String portletId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		return applyFallbackKeys(
			new PortletPreferencesSettings(
				getGroupPortletPreferences(
					group.getCompanyId(), groupId, portletId)),
				PortletConstants.getRootPortletId(portletId)
		);
	}

	@Override
	public Settings getPortletInstanceSettings(Layout layout, String portletId)
		throws PortalException, SystemException {

		return applyFallbackKeys(
			new PortletPreferencesSettings(
				getPortletInstancePortletPreferences(layout, portletId),
				getGroupSettings(layout.getGroupId(), portletId)),
			PortletConstants.getRootPortletId(portletId)
		);
	}

	@Override
	public void registerFallbackKeys(
		String settingsId, FallbackKeys fallbackKeys) {

		_fallbackKeysMap.put(settingsId, fallbackKeys);
	}

	protected Settings applyFallbackKeys(Settings settings, String settingsId) {
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
			long companyId, String settingsId)
		throws SystemException {

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0,
			settingsId);
	}

	protected Settings getCompanySettings(long companyId, String settingsId)
		throws SystemException {

		return new PortletPreferencesSettings(
			getCompanyPortletPreferences(companyId, settingsId),
			getPortalPreferencesSettings(companyId, settingsId));
	}

	protected PortletPreferences getGroupPortletPreferences(
			long companyId, long groupId, String settingsId)
		throws SystemException {

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP, 0,
			settingsId);
	}

	protected Settings getGroupSettings(long groupId, String settingsId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		long companyId = group.getCompanyId();

		return new PortletPreferencesSettings(
			getGroupPortletPreferences(companyId, groupId, settingsId),
			getCompanySettings(companyId, settingsId));
	}

	protected PortletPreferences getPortalPreferences(long companyId)
		throws SystemException {

		return PortalPreferencesLocalServiceUtil.getPreferences(
			companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY);
	}

	protected PortletPreferencesSettings getPortalPreferencesSettings(
			long companyId, String settingsId)
		throws SystemException {

		return new PortletPreferencesSettings(
			getPortalPreferences(companyId),
			getPortalPropertiesSettings(settingsId));
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

		return new PropertiesSettings(getPortalProperties(settingsId));
	}

	protected PortletPreferences getPortletInstancePortletPreferences(
			Layout layout, String portletId)
		throws SystemException {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		if (PortletConstants.hasUserId(portletId)) {
			ownerId = PortletConstants.getUserId(portletId);
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		return PortletPreferencesLocalServiceUtil.getPreferences(
			layout.getCompanyId(), ownerId, ownerType, layout.getPlid(),
			portletId);
	}

	private ConcurrentMap<String, FallbackKeys> _fallbackKeysMap =
		new ConcurrentHashMap<String, FallbackKeys>();
	private Map<String, Properties> _propertiesMap =
		new ConcurrentHashMap<String, Properties>();

}