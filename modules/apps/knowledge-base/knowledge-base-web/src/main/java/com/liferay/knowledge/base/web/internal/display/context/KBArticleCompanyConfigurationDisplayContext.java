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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.knowledge.base.configuration.KBServiceConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class KBArticleCompanyConfigurationDisplayContext {

	public KBArticleCompanyConfigurationDisplayContext(
		HttpServletRequest httpServletRequest,
		KBServiceConfigurationProvider kbServiceConfigurationProvider,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_kbServiceConfigurationProvider = kbServiceConfigurationProvider;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public int getCheckInterval() throws ConfigurationException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _kbServiceConfigurationProvider.getCheckInterval(
			themeDisplay.getCompanyId());
	}

	public String getEditKBArticleConfigurationURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/system_settings/edit_kb_article_expiration_date_configuration"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).buildString();
	}

	public int getExpirationDateNotificationDateWeeks()
		throws ConfigurationException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _kbServiceConfigurationProvider.
			getExpirationDateNotificationDateWeeks(themeDisplay.getCompanyId());
	}

	private final HttpServletRequest _httpServletRequest;
	private final KBServiceConfigurationProvider
		_kbServiceConfigurationProvider;
	private final LiferayPortletResponse _liferayPortletResponse;

}