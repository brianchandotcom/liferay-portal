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

package com.liferay.terms.of.use.web.internal.manager;

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
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.terms.of.use.web.internal.entry.CommerceTermsOfUseEntry;
import com.liferay.terms.of.use.web.internal.entry.LiferayEnterpriseSearchTermsOfUseEntry;
import com.liferay.terms.of.use.web.internal.entry.TermsOfUseEntry;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = TermsOfUseManager.class)
public class TermsOfUseManager {

	public void confirm(long userId) {
		_saveInPortalPreferences(userId);
	}

	public String getBodyHTML(Locale locale) {
		StringBundler sb = new StringBundler();

		for (TermsOfUseEntry termsOfUseEntry :
				_getFilteredTermsOfUseEntries()) {

			sb.append("<div>");
			sb.append("<h4>");
			sb.append(termsOfUseEntry.getDisplayName(locale));
			sb.append("</h3>");
			sb.append("<div>");
			sb.append(termsOfUseEntry.getBodyHTML(locale));
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</br>");
		}

		return sb.toString();
	}

	public boolean isShowTermsOfUse(long userId) {
		if (PortalRunMode.isTestMode() ||
			!GetterUtil.getBoolean(
				PropsUtil.get("enterprise.terms.of.use.enabled")) ||
			(userId == 0L)) {

			return false;
		}

		User user = _userLocalService.fetchUser(userId);

		if ((user == null) || !user.isSetupComplete() || !_isAdminUser(user)) {
			return false;
		}

		TermsOfUseEntry[] termsOfUseEntries = _getFilteredTermsOfUseEntries();

		if ((termsOfUseEntries.length == 0) ||
			_isConfirmedInPortalPreferences(userId)) {

			return false;
		}

		return true;
	}

	private TermsOfUseEntry[] _getFilteredTermsOfUseEntries() {
		return ArrayUtil.filter(_termsOfUseEntries, TermsOfUseEntry::isShow);
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
			portalPreferences.getValue(_NAMESPACE, _KEY));
	}

	private void _saveInPortalPreferences(long userId) {
		PortalPreferences portalPreferences = _getPortalPreferences(userId);

		portalPreferences.setValue(_NAMESPACE, _KEY, Boolean.TRUE.toString());

		_portalPreferencesLocalService.updatePreferences(
			userId, PortletKeys.PREFS_OWNER_TYPE_USER, portalPreferences);
	}

	private static final String _KEY = "_TERMS_OF_USE_CONFIRMED";

	private static final String _NAMESPACE = "TERMS_OF_USE";

	private static final Log _log = LogFactoryUtil.getLog(
		TermsOfUseManager.class);

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private RoleLocalService _roleLocalService;

	private final TermsOfUseEntry[] _termsOfUseEntries = {
		new CommerceTermsOfUseEntry(),
		new LiferayEnterpriseSearchTermsOfUseEntry()
	};

	@Reference
	private UserLocalService _userLocalService;

}