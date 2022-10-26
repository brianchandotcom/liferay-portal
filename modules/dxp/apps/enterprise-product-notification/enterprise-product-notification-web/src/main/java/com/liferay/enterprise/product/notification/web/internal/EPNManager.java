/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.enterprise.product.notification.web.internal;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PropsValues;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = EPNManager.class)
public class EPNManager {

	public void confirm(long userId) {
		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(userId, true);

		portalPreferences.resetValues(_NAMESPACE);

		portalPreferences.setValues(
			_NAMESPACE, "confirmedKeys",
			TransformUtil.transform(
				ArrayUtil.filter(
					_keyValuePairs,
					keyValuePair -> _isEnabled(keyValuePair.getKey())),
				keyValuePair -> keyValuePair.getKey(), String.class));
	}

	public String getBodyHTML(Locale locale, long userId) {
		if (PortalRunMode.isTestMode() ||
			!PropsValues.ENTERPRISE_PRODUCT_NOTIFICATION_ENABLED ||
			(userId == 0L)) {

			return null;
		}

		User user = _userLocalService.fetchUser(userId);

		if ((user == null) || !user.isSetupComplete()) {
			return null;
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		if (!permissionChecker.isOmniadmin()) {
			return null;
		}

		StringBundler sb = new StringBundler();

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(userId, true);

		String[] confirmedKeys = GetterUtil.getStringValues(
			portalPreferences.getValues(_NAMESPACE, "confirmedKeys"));

		for (KeyValuePair keyValuePair : _keyValuePairs) {
			String key = keyValuePair.getKey();

			if (!_isEnabled(key) || ArrayUtil.contains(confirmedKeys, key)) {
				continue;
			}

			sb.append("<div><h4>");
			sb.append(
				_language.get(
					locale,
					"enterprise-product-notification-title[" + key + "]"));
			sb.append("</h4><div>");
			sb.append(
				_language.format(
					locale, "enterprise-product-notification-body[" + key + "]",
					new String[] {
						String.format(
							"<a href=\"%s\" target=\"_blank\">",
							"https://learn.liferay.com/" +
								keyValuePair.getValue()),
						"</a>",
						"<a href=\"mailto:sales@liferay.com\">" +
							"sales@liferay.com</a>"
					}));
			sb.append("</div></div><br />");
		}

		return sb.toString();
	}

	private boolean _isEnabled(String key) {
		return GetterUtil.getBoolean(
			PropsUtil.get("enterprise.product." + key + ".enabled"));
	}

	private static final String _NAMESPACE =
		"com.liferay.enterprise.product.notification.web";

	private final KeyValuePair[] _keyValuePairs = {
		new KeyValuePair(
			"enterprise.search",
			"dxp/latest/en/using-search/liferay-enterprise-search" +
				"/activating-liferay-enterprise-search.html")
	};

	@Reference
	private Language _language;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private UserLocalService _userLocalService;

}