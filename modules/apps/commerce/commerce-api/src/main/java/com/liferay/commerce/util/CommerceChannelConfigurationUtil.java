/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util;

import com.liferay.commerce.configuration.CommerceOrderConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;

/**
 * @author Gianmarco Brunialti Masera
 */
public class CommerceChannelConfigurationUtil {

	public static String getOpenCommerceOrderVisibilityScope(long groupId) {
		return getOpenCommerceOrderVisibilityScope(groupId, false);
	}

	public static String getOpenCommerceOrderVisibilityScope(
		long groupId, boolean skipAdminPermissionCheck) {

		if (!skipAdminPermissionCheck) {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker.isCompanyAdmin(
					permissionChecker.getCompanyId()) ||
				permissionChecker.isGroupAdmin(groupId)) {

				return StringPool.BLANK;
			}
		}

		try {
			CommerceOrderConfiguration commerceOrderConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					CommerceOrderConfiguration.class,
					new GroupServiceSettingsLocator(
						groupId,
						CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

			return commerceOrderConfiguration.openOrdersVisibilityScope();
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException);
			}
		}

		return StringPool.BLANK;
	}

	public static String getPlacedCommerceOrderVisibilityScope(long groupId) {
		return getPlacedCommerceOrderVisibilityScope(groupId, false);
	}

	public static String getPlacedCommerceOrderVisibilityScope(
		long groupId, boolean skipAdminPermissionCheck) {

		if (!skipAdminPermissionCheck) {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker.isCompanyAdmin(
					permissionChecker.getCompanyId()) ||
				permissionChecker.isGroupAdmin(groupId)) {

				return StringPool.BLANK;
			}
		}

		try {
			CommerceOrderConfiguration commerceOrderConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					CommerceOrderConfiguration.class,
					new GroupServiceSettingsLocator(
						groupId,
						CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

			return commerceOrderConfiguration.placedOrdersVisibilityScope();
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException);
			}
		}

		return StringPool.BLANK;
	}

	public static boolean isUserNotificationScopeEnabled(long groupId) {
		try {
			CommerceOrderConfiguration commerceOrderConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					CommerceOrderConfiguration.class,
					new GroupServiceSettingsLocator(
						groupId,
						CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

			return commerceOrderConfiguration.userNotificationScopeEnabled();
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceChannelConfigurationUtil.class);

}