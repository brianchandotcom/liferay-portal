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

package com.liferay.notifications.web.portlet.configuration.icon;

import com.liferay.notifications.web.constants.NotificationsPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portlet.PortletURLFactoryUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * @author Sergio González
 */
public class DeliveryPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public DeliveryPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "configuration";
	}

	@Override
	public String getMethod() {
		return "get";
	}

	@Override
	public String getOnClick() {
		PortletURL deliveryURL = getDeliveryURL();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		StringBundler sb = new StringBundler(11);

		sb.append("Liferay.Portlet.openWindow({namespace: '");
		sb.append(portletDisplay.getNamespace());
		sb.append("', portlet: '#p_p_id_");
		sb.append(portletDisplay.getId());
		sb.append("_', portletId: '");
		sb.append(portletDisplay.getId());
		sb.append("', title: '");
		sb.append(LanguageUtil.get(themeDisplay.getLocale(), "configuration"));
		sb.append("', uri: '");
		sb.append(HtmlUtil.escapeJS(deliveryURL.toString()));
		sb.append("'}); return false;");

		return sb.toString();
	}

	@Override
	public String getURL() {
		PortletURL deliveryURL = getDeliveryURL();

		return deliveryURL.toString();
	}

	@Override
	public boolean isShow() {
		return true;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	protected PortletURL getDeliveryURL() {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, NotificationsPortletKeys.NOTIFICATIONS,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/notifications/configuration.jsp");

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException wse) {
		}

		return portletURL;
	}

}