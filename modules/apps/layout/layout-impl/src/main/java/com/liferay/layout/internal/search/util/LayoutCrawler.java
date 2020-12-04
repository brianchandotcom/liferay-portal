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

package com.liferay.layout.internal.search.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.net.InetAddress;

import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = LayoutCrawler.class)
public class LayoutCrawler {

	public String getLayoutContent(Layout layout, Locale locale) {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		HttpClient httpClient = httpClientBuilder.setUserAgent(
			_USER_AGENT
		).build();

		try {
			InetAddress inetAddress = _portal.getPortalServerInetAddress(false);

			ThemeDisplay themeDisplay = new ThemeDisplay();

			Company company = _companyLocalService.getCompany(
				layout.getCompanyId());

			themeDisplay.setCompany(company);

			themeDisplay.setLanguageId(LocaleUtil.toLanguageId(locale));
			themeDisplay.setLayout(layout);
			themeDisplay.setLayoutSet(layout.getLayoutSet());
			themeDisplay.setLocale(locale);
			themeDisplay.setScopeGroupId(layout.getGroupId());
			themeDisplay.setServerName(inetAddress.getHostName());
			themeDisplay.setServerPort(_portal.getPortalServerPort(false));
			themeDisplay.setSiteGroupId(layout.getGroupId());

			HttpGet httpGet = new HttpGet(
				_portal.getLayoutFullURL(layout, themeDisplay));

			HttpClientContext httpContext = new HttpClientContext();

			CookieStore cookieStore = new BasicCookieStore();

			BasicClientCookie cookie = new BasicClientCookie(
				CookieKeys.GUEST_LANGUAGE_ID, LocaleUtil.toLanguageId(locale));

			cookie.setDomain(inetAddress.getHostName());

			cookieStore.addCookie(cookie);

			httpContext.setCookieStore(cookieStore);

			HttpResponse httpResponse = httpClient.execute(
				httpGet, httpContext);

			StatusLine statusLine = httpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(httpResponse.getEntity());
			}

			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get layout content");
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get layout content", exception);
			}
		}

		return StringPool.BLANK;
	}

	private static final String _USER_AGENT = "Liferay Page Crawler";

	private static final Log _log = LogFactoryUtil.getLog(LayoutCrawler.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Portal _portal;

}