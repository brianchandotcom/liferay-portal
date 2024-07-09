/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.servlet;

import com.liferay.frontend.js.web.internal.language.LanguageState;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.net.URL;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=Language Resources Servlet",
		"osgi.http.whiteboard.servlet.pattern=/js/language/*",
		"service.ranking:Integer=" + (Integer.MAX_VALUE - 1000)
	},
	service = Servlet.class
)
public class FrontendJsWebLanguageServlet extends HttpServlet {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, ServletContext.class,
			new ServiceTrackerCustomizer<ServletContext, String>() {

				@Override
				public String addingService(
					ServiceReference<ServletContext> serviceReference) {

					ServletContext servletContext = bundleContext.getService(
						serviceReference);

					try {
						String contextPath = servletContext.getContextPath();

						if (!contextPath.startsWith(_WEB_CONTEXT_PATH_PREFIX)) {
							return null;
						}

						String webContextPath = contextPath.substring(
							_WEB_CONTEXT_PATH_PREFIX.length());

						List<String> keys = _getLanguageKeys(servletContext);

						if (keys != null) {
							if (_log.isDebugEnabled()) {
								_log.debug(
									StringBundler.concat(
										"Web context path '", webContextPath,
										"' added, contains ", keys.size(),
										" keys"));
							}

							synchronized (this) {
								_webContextPathKeysMap.put(
									webContextPath, keys);

								LanguageState.set(
									new LanguageState(
										_webContextPathKeysMap, _language));
							}
						}

						return webContextPath;
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				}

				@Override
				public void modifiedService(
					ServiceReference<ServletContext> serviceReference,
					String webContextPath) {
				}

				@Override
				public void removedService(
					ServiceReference<ServletContext> serviceReference,
					String webContextPath) {

					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"Web context path '", webContextPath,
								"' removed"));
					}

					synchronized (this) {
						_webContextPathKeysMap.remove(webContextPath);

						LanguageState.set(
							new LanguageState(
								_webContextPathKeysMap, _language));
					}
				}

			});

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;
	}

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		String pathInfo = httpServletRequest.getPathInfo();

		// Check if path is valid

		String[] parts = pathInfo.split(StringPool.SLASH);

		if ((parts.length != 5) || !parts[4].equals("all.js")) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		// Send response

		LanguageState languageState = LanguageState.get();
		Locale locale = LocaleUtil.fromLanguageId(parts[2]);
		String webContextPath = parts[3];

		String content = _getContent(languageState, locale, webContextPath);

		if (content == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		httpServletResponse.setCharacterEncoding(StringPool.UTF8);
		httpServletResponse.setContentType(ContentTypes.TEXT_JAVASCRIPT_UTF8);

		String cacheControl = "max-age=315360000, public, immutable";

		// If the hash is different from the current hash we are using return
		// a the current translations as fallback, but tell agents not to cache
		// it since that would break HTTP semantics.

		if (!parts[1].equals(languageState.getHash())) {
			cacheControl = HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE;

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Invalid hash received in language servlet: got '",
						parts[1], "' but expected '", languageState.getHash(),
						StringPool.APOSTROPHE));
			}
		}

		httpServletResponse.setHeader(HttpHeaders.CACHE_CONTROL, cacheControl);

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write(content);
	}

	private static String _loadTemplate(String name) {
		try (InputStream inputStream =
				FrontendJsWebLanguageServlet.class.getResourceAsStream(
					"dependencies/" + name)) {

			return StringUtil.read(inputStream);
		}
		catch (Exception exception) {
			_log.error("Unable to read template " + name, exception);
		}

		return StringPool.BLANK;
	}

	private String _getContent(
		LanguageState languageState, Locale locale, String webContextPath) {

		Collection<String> keys = languageState.getKeys(webContextPath);

		if (keys == null) {
			return null;
		}

		Map<String, String> labels = languageState.getLabels(locale);

		if (labels == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		for (String key : keys) {
			String label = labels.get(key);

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

	private List<String> _getLanguageKeys(ServletContext servletContext) {
		try {
			URL url = servletContext.getResource("/language.json");

			if (url == null) {
				return null;
			}

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				URLUtil.toString(url));

			return JSONUtil.toStringList(jsonObject.getJSONArray("keys"));
		}
		catch (Exception exception) {
			_log.error(
				"Unable to get language.json keys from servlet context " +
					servletContext.getContextPath(),
				exception);

			return null;
		}
	}

	private static final String _TPL_JAVA_SCRIPT;

	private static final String _WEB_CONTEXT_PATH_PREFIX =
		Portal.PATH_MODULE + StringPool.SLASH;

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendJsWebLanguageServlet.class);

	static {
		_TPL_JAVA_SCRIPT = _loadTemplate("all.js.tpl");
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	private ServiceTracker<ServletContext, String> _serviceTracker;
	private final Map<String, List<String>> _webContextPathKeysMap =
		new HashMap<>();

}