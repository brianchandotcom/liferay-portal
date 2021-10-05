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

package com.liferay.commerce.account.web.internal.terms.of.use.confirmation.manager;

import com.liferay.commerce.account.web.internal.constants.CommerceAccountPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceTermsOfUseConfirmationManager.class
)
public class CommerceTermsOfUseConfirmationManager {

	public void confirm(long userId) {
		_saveInPortalPreferences(userId);
	}

	public boolean isShowTermsOfUse(long userId) {
		if (userId == 0L) {
			return false;
		}

		User user = _userLocalService.fetchUser(userId);

		if ((user == null) || !user.isSetupComplete() || !_isAdminUser(user) ||
			_isConfirmedInPortalPreferences(userId)) {

			return false;
		}

		return true;
	}

	private PortalPreferences _getPortalPreferences(long userId) {
		return _portletPreferencesFactory.getPortalPreferences(userId, true);
	}

	private boolean _isAdminUser(User user) {
		try {
			Role role = _roleLocalService.getRole(
				user.getCompanyId(), RoleConstants.ADMINISTRATOR);

			long[] roleIds = _userLocalService.getRolePrimaryKeys(
				user.getUserId());

			if (ArrayUtil.contains(roleIds, role.getRoleId())) {
				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return false;
	}

	private boolean _isConfirmedInPortalPreferences(long userId) {
		PortalPreferences portalPreferences = _getPortalPreferences(userId);

		return GetterUtil.getBoolean(
			portalPreferences.getValue(
				_NAMESPACE, _COMMERCE_TERMS_OF_USE_CONFIRMED));
	}

	private void _saveInPortalPreferences(long userId) {
		PortalPreferences portalPreferences = _getPortalPreferences(userId);

		portalPreferences.setValue(
			_NAMESPACE, _COMMERCE_TERMS_OF_USE_CONFIRMED,
			Boolean.TRUE.toString());

		_portalPreferencesLocalService.updatePreferences(
			userId, PortletKeys.PREFS_OWNER_TYPE_USER, portalPreferences);
	}

	private static final String _COMMERCE_TERMS_OF_USE_CONFIRMED =
		"_COMMERCE_TERMS_OF_USE_CONFIRMED";

	private static final String _NAMESPACE =
		CommerceAccountPortletKeys.COMMERCE_ACCOUNT;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTermsOfUseConfirmationManager.class);

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}