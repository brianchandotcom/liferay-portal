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

package com.liferay.frontend.taglib.clay.internal.jaxrs.context.provider;

import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = ThemeDisplayContextProvider.class)
@Provider
public class ThemeDisplayContextProvider
	implements ContextProvider<ThemeDisplay> {

	@Override
	public ThemeDisplay createContext(Message message) {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)message.getContextualProperty("HTTP.REQUEST");

		try {
			return _getThemeDisplay(httpServletRequest);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return null;
	}

	private ThemeDisplay _getThemeDisplay(HttpServletRequest httpServletRequest)
		throws Exception {

		ServicePreAction servicePreAction = new ServicePreAction();

		HttpServletResponse httpServletResponse =
			new DummyHttpServletResponse();

		servicePreAction.servicePre(
			httpServletRequest, httpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(httpServletRequest, httpServletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String acceptLanguage = httpServletRequest.getHeader("Accept-Language");

		if (Validator.isNotNull(acceptLanguage)) {
			List<Locale> locales = Locale.filter(
				Locale.LanguageRange.parse(acceptLanguage),
				_language.getCompanyAvailableLocales(
					themeDisplay.getCompanyId()));

			if (ListUtil.isNotEmpty(locales)) {
				themeDisplay.setLocale(locales.get(0));
			}
		}

		long plid = ParamUtil.getLong(httpServletRequest, "plid");

		if (plid > 0) {
			Layout layout = _layoutLocalService.fetchLayout(plid);

			themeDisplay.setLayout(layout);
			themeDisplay.setPlid(layout.getPlid());

			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");

			themeDisplay.setScopeGroupId(groupId);
			themeDisplay.setSiteGroupId(groupId);
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletId = ParamUtil.getString(httpServletRequest, "portletId");

		portletDisplay.setId(portletId);

		return themeDisplay;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeDisplayContextProvider.class);

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

}