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

package com.liferay.enterprise.product.notification.web.internal.manager;

import com.liferay.enterprise.product.notification.web.internal.entry.CommerceEPNEntry;
import com.liferay.enterprise.product.notification.web.internal.entry.EPNEntry;
import com.liferay.enterprise.product.notification.web.internal.entry.LiferayEnterpriseSearchEPNEntry;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = EPNManager.class)
public class EPNManager {

	public void confirm(long userId) {
		_saveInPortalPreferences(
			userId,
			TransformUtil.transform(
				ArrayUtil.filter(_epnEntries, EPNEntry::isShow),
				EPNEntry::getKey, String.class));
	}

	public String getBodyHTML(Locale locale, long userId) {
		if (!_isShowEPN(userId)) {
			return null;
		}

		StringBundler sb = new StringBundler();

		for (EPNEntry epnEntry : _getFilteredEPNEntries(userId)) {
			sb.append("<div>");
			sb.append("<h4>");
			sb.append(epnEntry.getDisplayName(locale));
			sb.append("</h4>");
			sb.append("<div>");
			sb.append(epnEntry.getBodyHTML(locale));
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</br>");
		}

		return sb.toString();
	}

	private String[] _getConfirmedEPNKeys(long userId) {
		PortalPreferences portalPreferences = _getPortalPreferences(userId);

		return GetterUtil.getStringValues(
			portalPreferences.getValues(_NAMESPACE, _KEY));
	}

	private EPNEntry[] _getFilteredEPNEntries(long userId) {
		String[] confirmedEPNKeys = _getConfirmedEPNKeys(userId);

		return ArrayUtil.filter(
			_epnEntries,
			epnEntry -> {
				if (!epnEntry.isShow() ||
					ArrayUtil.contains(confirmedEPNKeys, epnEntry.getKey())) {

					return false;
				}

				return true;
			});
	}

	private PortalPreferences _getPortalPreferences(long userId) {
		return _portletPreferencesFactory.getPortalPreferences(userId, true);
	}

	private boolean _isShowEPN(long userId) {
		if (PortalRunMode.isTestMode() ||
			!PropsValues.ENTERPRISE_PRODUCT_NOTIFICATION_ENABLED ||
			(userId == 0L)) {

			return false;
		}

		User user = _userLocalService.fetchUser(userId);

		if ((user == null) || !user.isSetupComplete()) {
			return false;
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		if (!permissionChecker.isOmniadmin()) {
			return false;
		}

		return true;
	}

	private void _saveInPortalPreferences(long userId, String[] epnKeys) {
		PortalPreferences portalPreferences = _getPortalPreferences(userId);

		portalPreferences.resetValues(_NAMESPACE);

		portalPreferences.setValues(_NAMESPACE, _KEY, epnKeys);
	}

	private static final String _KEY = "CONFIRMED";

	private static final String _NAMESPACE = "ENTERPRISE_PRODUCT_NOTIFICATION";

	private final EPNEntry[] _epnEntries = {
		new CommerceEPNEntry(), new LiferayEnterpriseSearchEPNEntry()
	};

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private UserLocalService _userLocalService;

}