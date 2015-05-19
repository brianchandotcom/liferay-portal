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
import com.liferay.portal.kernel.portlet.configuration.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.PortletConfigurationIcon;
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

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = PortletConfigurationIcon.class)
public class NetvibesPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getIconCssClass() {
		return "icon-plus-sign";
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
			Portlet portlet = (Portlet)_request.getAttribute(
				WebKeys.RENDER_PORTLET);

			PortletURL basePortletURL = PortletURLFactoryUtil.create(
				_request, PortletKeys.PORTLET_SHARING, _themeDisplay.getPlid(),
				PortletRequest.RESOURCE_PHASE);

			StringBundler sb = new StringBundler(5);

			sb.append("javascript:Liferay.PortletSharing.showNetvibesInfo('");
			sb.append(PortalUtil.getNetvibesURL(portlet, _themeDisplay));
			sb.append("', '");
			sb.append(basePortletURL);
			sb.append("');");

			return sb.toString();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public double getWeight() {
		return 2.0;
	}

	@Override
	public boolean isLabel() {
		return true;
	}

	@Override
	public boolean isShow() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
				_themeDisplay.getLayout(), portletDisplay.getId());

		boolean lfrNetvibesShowAddAppLink = GetterUtil.getBoolean(
			portletSetup.getValue(
				"lfrNetvibesShowAddAppLink", StringPool.BLANK));

		if (lfrNetvibesShowAddAppLink) {
			return true;
		}

		return false;
	}

}