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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.model.cache.CacheField;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

/**
 * Represents a portal layout set, providing access to the layout set's color
 * schemes, groups, prototypes, themes, and more.
 *
 * <p>
 * Each {@link Group} in Liferay can have a public and a private layout set.
 * This keeps information common to all layouts (pages) in the layout set.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class LayoutSetImpl extends LayoutSetBaseImpl {

	/**
	 * Returns the layout set's color scheme.
	 *
	 * <p>
	 * Just like themes, color schemes can be configured on the layout set
	 * level. The layout set's color scheme can be overridden on the layout
	 * level.
	 * </p>
	 *
	 * @return the layout set's color scheme
	 */
	@Override
	public ColorScheme getColorScheme() {
		return ThemeLocalServiceUtil.getColorScheme(
			getCompanyId(), getThemeId(), getColorSchemeId());
	}

	@Override
	public String getCompanyFallbackVirtualHostname() {
		if (_companyFallbackVirtualHostname != null) {
			return _companyFallbackVirtualHostname;
		}

		_companyFallbackVirtualHostname = StringPool.BLANK;

		if (Validator.isNotNull(PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME) &&
			!isPrivateLayout()) {

			Group group = GroupLocalServiceUtil.fetchGroup(
				getCompanyId(), PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

			if ((group != null) && (getGroupId() == group.getGroupId())) {
				Company company = CompanyLocalServiceUtil.fetchCompany(
					getCompanyId());

				if (company != null) {
					_companyFallbackVirtualHostname =
						company.getVirtualHostname();
				}
			}
		}

		return _companyFallbackVirtualHostname;
	}

	/**
	 * Returns the layout set's group.
	 *
	 * @return the layout set's group
	 */
	@Override
	public Group getGroup() throws PortalException {
		return GroupLocalServiceUtil.getGroup(getGroupId());
	}

	/**
	 * Returns the layout set prototype's ID, or <code>0</code> if it has no
	 * layout set prototype.
	 *
	 * <p>
	 * Prototype is Liferay's technical name for a site template.
	 * </p>
	 *
	 * @return the layout set prototype's ID, or <code>0</code> if it has no
	 *         layout set prototype
	 */
	@Override
	public long getLayoutSetPrototypeId() throws PortalException {
		String layoutSetPrototypeUuid = getLayoutSetPrototypeUuid();

		if (Validator.isNull(layoutSetPrototypeUuid)) {
			return 0;
		}

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSetPrototypeUuid, getCompanyId());

		return layoutSetPrototype.getLayoutSetPrototypeId();
	}

	@Override
	public long getLiveLogoId() {
		long logoId = 0;

		Group group = null;

		try {
			group = getGroup();

			if (!group.isStagingGroup()) {
				return logoId;
			}
		}
		catch (Exception e) {
			return logoId;
		}

		Group liveGroup = group.getLiveGroup();

		LayoutSet liveLayoutSet = null;

		if (isPrivateLayout()) {
			liveLayoutSet = liveGroup.getPrivateLayoutSet();
		}
		else {
			liveLayoutSet = liveGroup.getPublicLayoutSet();
		}

		return liveLayoutSet.getLogoId();
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
		else {
			return _settingsProperties.toString();
		}
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

		Theme theme = getTheme(device);

		value = theme.getSetting(key);

		return value;
	}

	/**
	 * Returns the name of the layout set's virtual host.
	 *
	 * <p>
	 * When accessing a layout set that has a the virtual host, the URL elements
	 * "/web/sitename" or "/group/sitename" can be omitted.
	 * </p>
	 *
	 * @return the layout set's virtual host name, or an empty string if the
	 *         layout set has no virtual host configured
	 */
	@Override
	public String getVirtualHostname() {
		if (_virtualHostname != null) {
			return _virtualHostname;
		}

		VirtualHost virtualHost = VirtualHostLocalServiceUtil.fetchVirtualHost(
			getCompanyId(), getLayoutSetId());

		if (virtualHost == null) {
			_virtualHostname = StringPool.BLANK;
		}
		else {
			_virtualHostname = virtualHost.getHostname();
		}

		return _virtualHostname;
	}

	@Override
	public boolean hasSetModifiedDate() {
		return true;
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
	public void setCompanyFallbackVirtualHostname(
		String companyFallbackVirtualHostname) {

		_companyFallbackVirtualHostname = companyFallbackVirtualHostname;
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

	/**
	 * Sets the name of the layout set's virtual host.
	 *
	 * @param virtualHostname the name of the layout set's virtual host
	 * @see   #getVirtualHostname()
	 */
	@Override
	public void setVirtualHostname(String virtualHostname) {
		_virtualHostname = virtualHostname;
	}

	protected Theme getTheme(String device) {
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

			return ThemeLocalServiceUtil.getTheme(getCompanyId(), themeId);
		}
		else {
			return getTheme();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(LayoutSetImpl.class);

	@CacheField(propagateToInterface = true)
	private String _companyFallbackVirtualHostname;

	private UnicodeProperties _settingsProperties;

	@CacheField(propagateToInterface = true)
	private String _virtualHostname;

}