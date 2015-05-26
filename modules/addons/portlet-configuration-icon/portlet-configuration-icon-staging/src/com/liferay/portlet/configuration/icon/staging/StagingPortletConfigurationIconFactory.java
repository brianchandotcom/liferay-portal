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

package com.liferay.portlet.configuration.icon.staging;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.PortletConfigurationIconFactory;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = PortletConfigurationIconFactory.class)
public class StagingPortletConfigurationIconFactory
	implements PortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(HttpServletRequest request) {
		return new StagingPortletConfigurationIcon(request);
	}

	@Override
	public double getWeight() {
		return 10.0;
	}

	private class StagingPortletConfigurationIcon
		extends BasePortletConfigurationIcon {

		public StagingPortletConfigurationIcon(HttpServletRequest request) {
			_themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);
		}

		@Override
		public String getCssClass() {
			return "portlet-export-import portlet-export-import-icon";
		}

		@Override
		public String getImage() {
			return "../aui/share";
		}

		@Override
		public String getMessage() {
			return "staging";
		}

		@Override
		public String getMethod() {
			return "get";
		}

		@Override
		public String getOnClick() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			StringBundler sb = new StringBundler(11);

			sb.append("Liferay.Portlet.openWindow('#p_p_id_");
			sb.append(portletDisplay.getId());
			sb.append("_', '");
			sb.append(portletDisplay.getId());
			sb.append("', '");
			sb.append(HtmlUtil.escapeJS(portletDisplay.getURLStaging()));
			sb.append("', '");
			sb.append(portletDisplay.getNamespace());
			sb.append("', '");
			sb.append(LanguageUtil.get(_themeDisplay.getLocale(), "staging"));
			sb.append("'); return false;");

			return sb.toString();
		}

		@Override
		public String getURL() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return portletDisplay.getURLStaging();
		}

		@Override
		public boolean isShow() {
			PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

			return portletDisplay.isShowStagingIcon();
		}

		@Override
		public boolean isToolTip() {
			return false;
		}

		private final ThemeDisplay _themeDisplay;

	}

}