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

package com.liferay.frontend.js.bundle.config.extender.internal.servlet.taglib;

import com.liferay.frontend.js.bundle.config.extender.internal.JSBundleConfigTracker;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.URL;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MIN_VALUE,
	service = DynamicInclude.class
)
public class JSBundleConfigTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		if (!_isStale()) {
			_writeResponse(httpServletResponse, _objectValuePair.getValue());

			return;
		}

		StringWriter stringWriter = new StringWriter();

		Collection<JSBundleConfigTracker.JSConfig> jsConfigs =
			_jsBundleConfigTracker.getJSConfigs();

		if (!jsConfigs.isEmpty()) {
			stringWriter.write("<script data-senna-track=\"temporary\" ");
			stringWriter.write("type=\"");
			stringWriter.write(ContentTypes.TEXT_JAVASCRIPT);
			stringWriter.write("\">");

			for (JSBundleConfigTracker.JSConfig jsConfig : jsConfigs) {
				URL url = jsConfig.getURL();

				try (InputStream inputStream = url.openStream()) {
					stringWriter.write("try {");

					ServletContext servletContext =
						jsConfig.getServletContext();

					stringWriter.write(
						StringBundler.concat(
							"var MODULE_MAIN='", _getModuleMain(jsConfig),
							"';"));

					stringWriter.write(
						StringBundler.concat(
							"var MODULE_PATH='", _portal.getPathProxy(),
							servletContext.getContextPath(), "';"));

					stringWriter.write(
						StringUtil.removeSubstring(
							StringUtil.read(inputStream),
							"//# sourceMappingURL=config.js.map"));

					stringWriter.write(
						"} catch(error) {console.error(error);}");
				}
				catch (Exception exception) {
					_log.error("Unable to open resource", exception);
				}
			}

			stringWriter.write("</script>");
		}

		String bundleConfig = stringWriter.toString();

		_objectValuePair = new ObjectValuePair<>(
			_jsBundleConfigTracker.getLastModified(), bundleConfig);

		_writeResponse(httpServletResponse, bundleConfig);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_js.jspf#resources");
	}

	@Reference(unbind = "-")
	protected void setJSBundleConfigTracker(
		JSBundleConfigTracker jsBundleConfigTracker) {

		_jsBundleConfigTracker = jsBundleConfigTracker;
	}

	private JSBundleConfigTracker _getJSBundleConfigTracker() {
		return _jsBundleConfigTracker;
	}

	private String _getModuleMain(JSBundleConfigTracker.JSConfig jsConfig) {
		try {
			ServletContext servletContext = jsConfig.getServletContext();

			URL url = servletContext.getResource("package.json");

			if (url == null) {
				return null;
			}

			try (InputStream inputStream = url.openStream()) {
				JSONObject jsonObject = _jsonFactory.createJSONObject(
					StringUtil.read(inputStream));

				String moduleName = jsonObject.getString("name");
				String moduleVersion = jsonObject.getString("version");

				String moduleMain = jsonObject.getString("main");

				if (Validator.isNull(moduleMain)) {
					moduleMain = "index.js";
				}

				return StringBundler.concat(
					moduleName, "@", moduleVersion, "/",
					ModuleNameUtil.toModuleName(moduleMain));
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private boolean _isStale() {
		if (_jsBundleConfigTracker.getLastModified() >
				_objectValuePair.getKey()) {

			return true;
		}

		return false;
	}

	private void _writeResponse(
			HttpServletResponse httpServletResponse, String content)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.println(content);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSBundleConfigTopHeadDynamicInclude.class);

	private JSBundleConfigTracker _jsBundleConfigTracker;

	@Reference
	private JSONFactory _jsonFactory;

	private volatile ObjectValuePair<Long, String> _objectValuePair =
		new ObjectValuePair<>(0L, null);

	@Reference
	private Portal _portal;

}