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

package com.liferay.portal.model.impl;

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetStagingHandler;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;

import java.io.IOException;

/**
 * @author Raymond Augé
 * @author Julio Camarero
 */
public class LayoutSetBranchImpl extends LayoutSetBranchBaseImpl {

	@Override
	public ColorScheme getColorScheme() {
		return ThemeLocalServiceUtil.getColorScheme(
			getCompanyId(), getTheme().getThemeId(), getColorSchemeId());
	}

	@Override
	public Group getGroup() throws PortalException {
		return GroupLocalServiceUtil.getGroup(getGroupId());
	}

	@Override
	public LayoutSet getLayoutSet() {
		if (_layoutSet != null) {
			return _layoutSet;
		}

		try {
			_layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				getGroupId(), getPrivateLayout());

			LayoutSetStagingHandler layoutSetStagingHandler =
				LayoutStagingUtil.getLayoutSetStagingHandler(_layoutSet);

			if (layoutSetStagingHandler == null) {
				return _layoutSet;
			}

			_layoutSet = layoutSetStagingHandler.getLayoutSet();

			return _layoutSet;
		}
		catch (PortalException | SystemException e) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return _layoutSet;
	}

	@Override
	public long getLiveLogoId() {
		long logoId = getLayoutSet().getLogoId();

		if (logoId == 0) {
			logoId = getLayoutSet().getLiveLogoId();
		}

		return logoId;
	}

	@Override
	public boolean getLogo() {
		if (getLogoId() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public String getSettings() {
		if (_settingsProperties == null) {
			return super.getSettings();
		}

		return _settingsProperties.toString();
	}

	@Override
	public UnicodeProperties getSettingsProperties() {
		if (_settingsProperties == null) {
			_settingsProperties = new UnicodeProperties(true);

			try {
				_settingsProperties.load(super.getSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return _settingsProperties;
	}

	@Override
	public String getSettingsProperty(String key) {
		UnicodeProperties settingsProperties = getSettingsProperties();

		return settingsProperties.getProperty(key);
	}

	@Override
	public Theme getTheme() {
		return ThemeLocalServiceUtil.getTheme(getCompanyId(), getThemeId());
	}

	@Override
	public String getThemeSetting(String key, String device) {
		UnicodeProperties settingsProperties = getSettingsProperties();

		String value = settingsProperties.getProperty(
			ThemeSettingImpl.namespaceProperty(device, key));

		if (value != null) {
			return value;
		}

		Theme theme = null;

		boolean controlPanel = false;

		try {
			Group group = getGroup();

			controlPanel = group.isControlPanel();
		}
		catch (Exception e) {
		}

		if (controlPanel) {
			String themeId = PrefsPropsUtil.getString(
				getCompanyId(),
				PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID);

			theme = ThemeLocalServiceUtil.getTheme(getCompanyId(), themeId);
		}
		else {
			theme = getTheme();
		}

		value = theme.getSetting(key);

		return value;
	}

	@Override
	public boolean isLayoutSetPrototypeLinkActive() {
		if (isLayoutSetPrototypeLinkEnabled() &&
			Validator.isNotNull(getLayoutSetPrototypeUuid())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isLogo() {
		return getLogo();
	}

	@Override
	public void setSettings(String settings) {
		_settingsProperties = null;

		super.setSettings(settings);
	}

	@Override
	public void setSettingsProperties(UnicodeProperties settingsProperties) {
		_settingsProperties = settingsProperties;

		super.setSettings(_settingsProperties.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetBranchImpl.class);

	private LayoutSet _layoutSet;
	private UnicodeProperties _settingsProperties;

}