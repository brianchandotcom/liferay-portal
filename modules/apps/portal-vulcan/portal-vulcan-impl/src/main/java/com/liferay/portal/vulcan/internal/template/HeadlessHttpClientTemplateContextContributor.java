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

package com.liferay.portal.vulcan.internal.template;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.url.URLBuilder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "type=" + TemplateContextContributor.TYPE_GLOBAL,
	service = TemplateContextContributor.class
)
public class HeadlessHttpClientTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		contextObjects.put(
			"headlessHttpClient", new HeadlessHttpClient(httpServletRequest));
	}

	public class HeadlessHttpClient {

		public HeadlessHttpClient(HttpServletRequest httpServletRequest) {
			_httpServletRequest = httpServletRequest;
		}

		public Object get(String path) throws Exception {
			Http.Options options = new Http.Options();

			options.addHeader(
				HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);

			String jSessionId = CookieKeys.getCookie(
				_httpServletRequest, CookieKeys.JSESSIONID);

			options.addHeader(
				HttpHeaders.COOKIE, CookieKeys.JSESSIONID + "=" + jSessionId);

			options.addHeader(
				PropsValues.WEB_SERVER_FORWARDED_HOST_HEADER,
				PortalUtil.getForwardedHost(_httpServletRequest));
			options.addHeader(
				PropsValues.WEB_SERVER_FORWARDED_PORT_HEADER,
				String.valueOf(
					PortalUtil.getForwardedPort(_httpServletRequest)));

			options.setLocation(
				URLBuilder.create(
					StringBundler.concat(
						Http.HTTP, "://localhost:",
						_httpServletRequest.getServerPort(), Portal.PATH_MODULE,
						path)
				).setParameter(
					"p_auth", AuthTokenUtil.getToken(_httpServletRequest)
				).build());

			return JSONFactoryUtil.looseDeserialize(
				HttpUtil.URLtoString(options));
		}

		private final HttpServletRequest _httpServletRequest;

	}

}