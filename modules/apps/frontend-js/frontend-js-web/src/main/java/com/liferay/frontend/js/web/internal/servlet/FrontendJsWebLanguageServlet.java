/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.servlet;

import com.liferay.frontend.js.web.internal.language.LanguageState;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;

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
		_servletContextServiceTracker = new ServiceTracker<>(
			bundleContext, ServletContext.class,
			new ServletContextServiceTrackerCustomizer(
				bundleContext, _jsonFactory, _language));

		_servletContextServiceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_servletContextServiceTracker.close();

		_servletContextServiceTracker = null;
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

	private static final String _TPL_JAVA_SCRIPT;

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendJsWebLanguageServlet.class);

	static {
		_TPL_JAVA_SCRIPT = _loadTemplate("all.js.tpl");
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	private ServiceTracker<ServletContext, String>
		_servletContextServiceTracker;

}