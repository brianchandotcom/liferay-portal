/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request.helper;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.hashed.files.request.AbstractRequestHelper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.net.URL;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera Avellón
 */
public class LanguageRequestHelper
	extends AbstractRequestHelper<LanguageRequestInfo> {

	public static final String LANGUAGE_MODULE_PREFIX = "@liferay/language/";

	public static final String LANGUAGE_URI_PREFIX = "/o/js/language/";

	public LanguageRequestHelper(
		ConfigurationProvider configurationProvider, JSONFactory jsonFactory,
		Language language, Portal portal,
		ServiceTrackerMap<String, ServletContext> serviceTrackerMap) {

		_configurationProvider = configurationProvider;
		_jsonFactory = jsonFactory;
		_language = language;
		_portal = portal;
		_serviceTrackerMap = serviceTrackerMap;
	}

	@Override
	public boolean isAcceptableRequest(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		return requestURI.startsWith(LANGUAGE_URI_PREFIX);
	}

	@Override
	protected LanguageRequestInfo getRequestInfo(
		HttpServletRequest httpServletRequest) {

		String requestURI = httpServletRequest.getRequestURI();

		requestURI = requestURI.substring(LANGUAGE_URI_PREFIX.length());

		String[] parts = requestURI.split(StringPool.SLASH);

		if ((parts.length != 3) || !parts[2].equals("all.js")) {
			return null;
		}

		long maxAge = 3600;
		boolean sendNoCache = false;

		long companyId = _portal.getCompanyId(httpServletRequest);

		try {
			FrontendCachingConfiguration frontendCachingConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FrontendCachingConfiguration.class, companyId);

			maxAge = frontendCachingConfiguration.labelsModulesMaxAge();
			sendNoCache =
				frontendCachingConfiguration.sendNoCacheForLabelsModules();
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to get labels modules frontend caching " +
					"configuration: will use reasonable defaults instead",
				configurationException);
		}

		Long lastMaxAgeUpdate = _lastMaxAgeUpdate.get(companyId);
		long now = System.currentTimeMillis();

		if ((lastMaxAgeUpdate == null) ||
			((now - lastMaxAgeUpdate) > (maxAge * 1000))) {

			lastMaxAgeUpdate = now;

			_lastMaxAgeUpdate.put(companyId, lastMaxAgeUpdate);
		}

		return new LanguageRequestInfo(
			parts[0], maxAge - ((now - lastMaxAgeUpdate) / 1000), sendNoCache,
			parts[1]);
	}

	@Override
	protected void sendContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			LanguageRequestInfo languageRequestInfo)
		throws IOException {

		// Check if resource exists

		ServletContext servletContext = _serviceTrackerMap.getService(
			Portal.PATH_MODULE + StringPool.SLASH +
				languageRequestInfo.getWebContextPath());

		if (servletContext == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		// Send resource

		Locale locale = LocaleUtil.fromLanguageId(
			languageRequestInfo.getLanguageId());

		String content = _getContent(locale, servletContext);

		if (content == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		httpServletResponse.setCharacterEncoding(StringPool.UTF8);
		httpServletResponse.setContentType(ContentTypes.TEXT_JAVASCRIPT_UTF8);

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write(content);
	}

	private static String _loadTemplate(String name) {
		try (InputStream inputStream =
				LanguageRequestHelper.class.getResourceAsStream(
					"dependencies/" + name)) {

			return StringUtil.read(inputStream);
		}
		catch (Exception exception) {
			_log.error("Unable to read template " + name, exception);
		}

		return StringPool.BLANK;
	}

	private String _getContent(Locale locale, ServletContext servletContext)
		throws IOException {

		JSONArray languageKeysJSONArray = _getLanguageKeysJSONArray(
			servletContext);

		if (languageKeysJSONArray == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < languageKeysJSONArray.length(); i++) {
			String key = languageKeysJSONArray.getString(i);

			String label = _language.get(locale, key);

			sb.append(StringPool.APOSTROPHE);
			sb.append(key.replaceAll("'", "\\\\'"));
			sb.append("':'");
			sb.append(label.replaceAll("'", "\\\\'"));
			sb.append("',\n");
		}

		return StringUtil.replace(
			_TPL_JAVA_SCRIPT, new String[] {"[$LABELS$]"},
			new String[] {sb.toString()});
	}

	private JSONArray _getLanguageKeysJSONArray(ServletContext servletContext)
		throws IOException {

		URL url = servletContext.getResource("/language.json");

		if (url == null) {
			return null;
		}

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				URLUtil.toString(url));

			return jsonObject.getJSONArray("keys");
		}
		catch (JSONException jsonException) {
			throw new IOException(
				"Invalid language JSON file " + url, jsonException);
		}
	}

	private static final String _TPL_JAVA_SCRIPT;

	private static final Log _log = LogFactoryUtil.getLog(
		LanguageRequestHelper.class);

	static {
		_TPL_JAVA_SCRIPT = _loadTemplate("all.js.tpl");
	}

	private final ConfigurationProvider _configurationProvider;
	private final JSONFactory _jsonFactory;
	private final Language _language;
	private final Map<Long, Long> _lastMaxAgeUpdate = new ConcurrentHashMap<>();
	private final Portal _portal;
	private final ServiceTrackerMap<String, ServletContext> _serviceTrackerMap;

}