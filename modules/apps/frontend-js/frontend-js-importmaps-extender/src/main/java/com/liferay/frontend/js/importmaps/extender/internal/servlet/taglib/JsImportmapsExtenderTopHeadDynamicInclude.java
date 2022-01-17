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

package com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	service = {
		DynamicInclude.class, JsImportmapsExtenderTopHeadDynamicInclude.class
	}
)
public class JsImportmapsExtenderTopHeadDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		if (_globalImportmaps.isEmpty() && _scopedImportmaps.isEmpty()) {
			return;
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.println("<script type=\"importmap\">");

		printWriter.println(_importmaps.get());

		printWriter.println("</script>");

		printWriter.print("<script src=\"");

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		printWriter.print(
			absolutePortalURLBuilder.forModuleScript(
				_bundleContext.getBundle(),
				"/es-module-shims/es-module-shims.js"
			).build());

		printWriter.println("\"></script>\n");
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	public JsImportmapsRegistration register(
		String scope, JSONObject jsonObject) {

		if (scope == null) {
			long globalId = _nextGlobalId.getAndIncrement();

			_globalImportmaps.put(globalId, jsonObject);

			_rebuildImportmaps();

			return new JsImportmapsRegistration() {

				@Override
				public void unregister() {
					_globalImportmaps.remove(globalId);
				}

			};
		}

		_scopedImportmaps.put(scope, jsonObject);

		_getScopesJSONObject();

		return new JsImportmapsRegistration() {

			@Override
			public void unregister() {
				_scopedImportmaps.remove(scope);
			}

		};
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_rebuildImportmaps();
	}

	private JSONObject _getGlobalJSONObject() {
		JSONObject globalJSONObject = _jsonFactory.createJSONObject();

		for (JSONObject jsonObject : _globalImportmaps.values()) {
			for (String key : jsonObject.keySet()) {
				globalJSONObject.put(key, jsonObject.getString(key));
			}
		}

		return globalJSONObject;
	}

	private JSONObject _getScopesJSONObject() {
		JSONObject scopesJSONObject = _jsonFactory.createJSONObject();

		for (Map.Entry<String, JSONObject> entry :
				_scopedImportmaps.entrySet()) {

			scopesJSONObject.put(entry.getKey(), entry.getValue());
		}

		return scopesJSONObject;
	}

	private synchronized void _rebuildImportmaps() {
		JSONObject globalJSONObject = _getGlobalJSONObject();

		globalJSONObject.put("scopes", _getScopesJSONObject());

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("imports", globalJSONObject);

		_importmaps.set(_jsonFactory.looseSerializeDeep(jsonObject));
	}

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private BundleContext _bundleContext;
	private final ConcurrentMap<Long, JSONObject> _globalImportmaps =
		new ConcurrentHashMap<>();
	private final AtomicReference<String> _importmaps = new AtomicReference<>();

	@Reference
	private JSONFactory _jsonFactory;

	private final AtomicLong _nextGlobalId = new AtomicLong();
	private final ConcurrentMap<String, JSONObject> _scopedImportmaps =
		new ConcurrentHashMap<>();

}