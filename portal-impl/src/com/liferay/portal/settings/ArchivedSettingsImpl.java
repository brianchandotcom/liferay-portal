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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.settings.BaseModifiableSettings;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.PortletPreferencesSettings;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesServiceUtil;
import com.liferay.portal.util.PortletKeys;

import java.io.IOException;

import java.util.Collection;
import java.util.Date;

import javax.portlet.PortletPreferences;
import javax.portlet.ValidatorException;

/**
 * @author Iván Zaera
 */
public class ArchivedSettingsImpl
	extends BaseModifiableSettings implements ArchivedSettings {

	public ArchivedSettingsImpl(PortletItem portletItem) {
		_portletItem = portletItem;
	}

	@Override
	public void delete() throws IOException {
		try {
			PortletPreferencesServiceUtil.deleteArchivedPreferences(
				_portletItem.getPortletItemId());
		}
		catch (PortalException pe) {
			throw new IOException("Unable to delete archived settings", pe);
		}
		catch (SystemException se) {
			throw new IOException("Unable to delete archived settings", se);
		}
	}

	@Override
	public Date getModifiedDate() {
		return _portletItem.getModifiedDate();
	}

	@Override
	public Collection<String> getModifiedKeys() {
		ModifiableSettings modifiableSettings = _getModifiableSettings();

		return modifiableSettings.getModifiedKeys();
	}

	@Override
	public String getName() {
		return _portletItem.getName();
	}

	@Override
	public String getUserName() {
		return _portletItem.getUserName();
	}

	@Override
	public void reset(String key) {
		ModifiableSettings modifiableSettings = _getModifiableSettings();

		modifiableSettings.reset(key);
	}

	@Override
	public ModifiableSettings setValue(String key, String value) {
		ModifiableSettings modifiableSettings = _getModifiableSettings();

		modifiableSettings.setValue(key, value);

		return this;
	}

	@Override
	public ModifiableSettings setValues(String key, String[] values) {
		ModifiableSettings modifiableSettings = _getModifiableSettings();

		modifiableSettings.setValues(key, values);

		return this;
	}

	@Override
	public void store() throws IOException, ValidatorException {
		ModifiableSettings modifiableSettings = _getModifiableSettings();

		modifiableSettings.store();
	}

	@Override
	protected String doGetValue(String key) {
		ModifiableSettings modifiableSettings = _getModifiableSettings();

		return modifiableSettings.getValue(key, null);
	}

	@Override
	protected String[] doGetValues(String key) {
		ModifiableSettings modifiableSettings = _getModifiableSettings();

		return modifiableSettings.getValues(key, null);
	}

	private ModifiableSettings _getModifiableSettings() {
		if (_portletPreferencesSettings != null) {
			return _portletPreferencesSettings;
		}

		PortletPreferences portletPreferences = null;

		try {
			long ownerId = _portletItem.getPortletItemId();
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_ARCHIVED;
			long plid = 0;
			String portletId = _portletItem.getPortletId();

			portletPreferences =
				PortletPreferencesLocalServiceUtil.getPreferences(
					_portletItem.getCompanyId(), ownerId, ownerType, plid,
					PortletConstants.getRootPortletId(portletId));
		}
		catch (SystemException se) {
			throw new RuntimeException("Unable to load settings", se);
		}

		_portletPreferencesSettings = new PortletPreferencesSettings(
			portletPreferences);

		return _portletPreferencesSettings;
	}

	private final PortletItem _portletItem;
	private PortletPreferencesSettings _portletPreferencesSettings;

}