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

package com.liferay.portal.service.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Plugin;
import com.liferay.portal.kernel.model.PluginSetting;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.LayoutTemplateLocalService;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.model.impl.PluginSettingImpl;
import com.liferay.portal.service.base.PluginSettingLocalServiceBaseImpl;

/**
 * @author Jorge Ferrer
 */
public class PluginSettingLocalServiceImpl
	extends PluginSettingLocalServiceBaseImpl {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkPermission(long userId, String pluginId, String pluginType)
		throws PortalException {

		if (!hasPermission(userId, pluginId, pluginType)) {
			throw new PrincipalException.MustHavePermission(
				userId, pluginType, pluginId);
		}
	}

	@Override
	@Transactional(enabled = false)
	public PluginSetting getDefaultPluginSetting() {
		PluginSettingImpl pluginSettingImpl = new PluginSettingImpl();

		pluginSettingImpl.setRoles(StringPool.BLANK);
		pluginSettingImpl.setActive(true);

		return pluginSettingImpl;
	}

	@Override
	public PluginSetting getPluginSetting(
		long companyId, String pluginId, String pluginType) {

		PluginSetting pluginSetting = pluginSettingPersistence.fetchByC_P_P(
			companyId, pluginId, pluginType);

		if (pluginSetting != null) {
			return pluginSetting;
		}

		Plugin plugin = null;

		if (pluginType.equals(Plugin.TYPE_LAYOUT_TEMPLATE)) {
			plugin = _layoutTemplateLocalService.getLayoutTemplate(
				pluginId, false, null);
		}
		else if (pluginType.equals(Plugin.TYPE_THEME)) {
			plugin = _themeLocalService.getTheme(companyId, pluginId);
		}

		if ((plugin == null) || (plugin.getDefaultPluginSetting() == null)) {
			pluginSetting = getDefaultPluginSetting();

			pluginSetting.setCompanyId(companyId);
		}
		else {
			pluginSetting = plugin.getDefaultPluginSetting(companyId);
		}

		return pluginSetting;
	}

	@Override
	public boolean hasPermission(
		long userId, String pluginId, String pluginType) {

		try {
			User user = _userPersistence.findByPrimaryKey(userId);

			PluginSetting pluginSetting = getPluginSetting(
				user.getCompanyId(), pluginId, pluginType);

			if (!pluginSetting.hasPermission(userId)) {
				return false;
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Could not check permissions for " + pluginId, exception);
			}

			return false;
		}
	}

	@Override
	public PluginSetting updatePluginSetting(
		long companyId, String pluginId, String pluginType, String roles,
		boolean active) {

		pluginId = PortalUtil.getJsSafePortletId(pluginId);

		PluginSetting pluginSetting = pluginSettingPersistence.fetchByC_P_P(
			companyId, pluginId, pluginType);

		if (pluginSetting == null) {
			long pluginSettingId = counterLocalService.increment();

			pluginSetting = pluginSettingPersistence.create(pluginSettingId);

			pluginSetting.setCompanyId(companyId);
			pluginSetting.setPluginId(pluginId);
			pluginSetting.setPluginType(pluginType);
		}

		pluginSetting.setRoles(roles);
		pluginSetting.setActive(active);

		return pluginSettingPersistence.update(pluginSetting);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PluginSettingLocalServiceImpl.class);

	@BeanReference(type = LayoutTemplateLocalService.class)
	private LayoutTemplateLocalService _layoutTemplateLocalService;

	@BeanReference(type = ThemeLocalService.class)
	private ThemeLocalService _themeLocalService;

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

}