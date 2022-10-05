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

import com.liferay.frontend.js.importmaps.extender.internal.configuration.JSImportmapsConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.importmaps.ImportMapsShimUtil;
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
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	configurationPid = "com.liferay.frontend.js.importmaps.extender.internal.configuration.JSImportmapsConfiguration",
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = {
		DynamicInclude.class, JSImportmapsExtenderTopHeadDynamicInclude.class
	}
)
public class JSImportmapsExtenderTopHeadDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		if (_jsImportmapsConfiguration.enableImportmaps() &&
			(!_globalImportmaps.isEmpty() || !_scopedImportmaps.isEmpty())) {

			String type = "importmap";

			if (_jsImportmapsConfiguration.enableESModuleShims()) {
				type = "importmap-shim";
			}

			printWriter.print("<script type=\"");
			printWriter.print(type);
			printWriter.println("\">");
			printWriter.println(_importmaps.get());
			printWriter.println("</script>");
		}

		if (_jsImportmapsConfiguration.enableESModuleShims()) {
			printWriter.println("<script type=\"esms-options\">");
			printWriter.println("{");
			printWriter.println("  \"shimMode\": true");
			printWriter.println("}");
			printWriter.println("</script>");

			printWriter.print("<script src=\"");

			AbsolutePortalURLBuilder absolutePortalURLBuilder =
				_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
					httpServletRequest);

			printWriter.print(
				absolutePortalURLBuilder.forBundleScript(
					_bundleContext.getBundle(),
					"/es-module-shims/es-module-shims.js"
				).build());

			printWriter.println("\"></script>\n");
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	public JSImportmapsRegistration register(
		String scope, JSONObject jsonObject) {

		if (scope == null) {
			long globalId = _nextGlobalId.getAndIncrement();

			_globalImportmaps.put(globalId, jsonObject);

			_rebuildImportmaps();

			return new JSImportmapsRegistration() {

				@Override
				public void unregister() {
					_globalImportmaps.remove(globalId);
				}

			};
		}

		_scopedImportmaps.put(scope, jsonObject);

		_getScopesJSONObject();

		return new JSImportmapsRegistration() {

			@Override
			public void unregister() {
				_scopedImportmaps.remove(scope);
			}

		};
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_rebuildImportmaps();

		modified(properties);
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_jsImportmapsConfiguration = ConfigurableUtil.createConfigurable(
			JSImportmapsConfiguration.class, properties);

		ImportMapsShimUtil.setModuleType(
			_jsImportmapsConfiguration.enableESModuleShims() ? "module-shim" :
				"module");
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
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"imports", _getGlobalJSONObject()
		).put(
			"scopes", _getScopesJSONObject()
		);

		_importmaps.set(_jsonFactory.looseSerializeDeep(jsonObject));
	}

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private volatile BundleContext _bundleContext;
	private final ConcurrentMap<Long, JSONObject> _globalImportmaps =
		new ConcurrentHashMap<>();
	private final AtomicReference<String> _importmaps = new AtomicReference<>();
	private volatile JSImportmapsConfiguration _jsImportmapsConfiguration;

	@Reference
	private JSONFactory _jsonFactory;

	private final AtomicLong _nextGlobalId = new AtomicLong();
	private final ConcurrentMap<String, JSONObject> _scopedImportmaps =
		new ConcurrentHashMap<>();

}