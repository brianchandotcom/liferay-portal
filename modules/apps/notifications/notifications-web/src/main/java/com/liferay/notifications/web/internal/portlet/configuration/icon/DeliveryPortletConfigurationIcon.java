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

package com.liferay.notifications.web.internal.portlet.configuration.icon;

import com.liferay.notifications.web.internal.constants.NotificationsPortletKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + NotificationsPortletKeys.NOTIFICATIONS,
	service = PortletConfigurationIcon.class
)
public class DeliveryPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "configuration");
	}

	@Override
	public String getMethod() {
		return "get";
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		StringBundler sb = new StringBundler(11);

		sb.append("Liferay.Portlet.openModal({namespace: '");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		sb.append(portletDisplay.getNamespace());

		sb.append("', portletSelector: '#p_p_id_");
		sb.append(portletDisplay.getId());
		sb.append("_', portletId: '");
		sb.append(portletDisplay.getId());
		sb.append("', title: '");
		sb.append(LanguageUtil.get(themeDisplay.getLocale(), "configuration"));
		sb.append("', url: '");
		sb.append(
			HtmlUtil.escapeJS(String.valueOf(_getDeliveryURL(portletRequest))));
		sb.append("'}); return false;");

		return sb.toString();
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return String.valueOf(_getDeliveryURL(portletRequest));
	}

	@Override
	public double getWeight() {
		return 100;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return true;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	private PortletURL _getDeliveryURL(PortletRequest portletRequest) {
		PortletURL portletURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				portletRequest, NotificationsPortletKeys.NOTIFICATIONS,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/notifications/configuration.jsp"
		).buildPortletURL();

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			if (_log.isDebugEnabled()) {
				_log.debug(windowStateException, windowStateException);
			}
		}

		return portletURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeliveryPortletConfigurationIcon.class);

}