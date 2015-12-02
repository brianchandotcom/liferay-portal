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

package com.liferay.portlet.configuration.icon.netvibes;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLFactoryUtil;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 */
public class NetvibesPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public NetvibesPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "add-to-netvibes";
	}

	@Override
	public String getMethod() {
		return "get";
	}

	@Override
	public String getURL() {
		try {
			Portlet portlet = (Portlet)portletRequest.getAttribute(
				WebKeys.RENDER_PORTLET);

			PortletURL basePortletURL = PortletURLFactoryUtil.create(
				portletRequest, PortletKeys.PORTLET_SHARING,
				themeDisplay.getPlid(), PortletRequest.RESOURCE_PHASE);

			StringBundler sb = new StringBundler(5);

			sb.append("javascript:Liferay.PortletSharing.showNetvibesInfo('");
			sb.append(PortalUtil.getNetvibesURL(portlet, themeDisplay));
			sb.append("', '");
			sb.append(basePortletURL);
			sb.append("');");

			return sb.toString();
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return StringPool.BLANK;
		}
	}

	@Override
	public boolean isLabel() {
		return true;
	}

	@Override
	public boolean isShow() {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
				themeDisplay.getLayout(), portletDisplay.getId());

		boolean lfrNetvibesShowAddAppLink = GetterUtil.getBoolean(
			portletSetup.getValue(
				"lfrNetvibesShowAddAppLink", StringPool.BLANK));

		if (lfrNetvibesShowAddAppLink) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NetvibesPortletConfigurationIcon.class);

}