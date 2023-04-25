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

import com.liferay.frontend.js.importmaps.extender.internal.configuration.JSImportMapsConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.frontend.esm.FrontendESMUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.HashMapBuilder;
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
	configurationPid = "com.liferay.frontend.js.importmaps.extender.internal.configuration.JSImportMapsConfiguration",
	property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = {
		DynamicInclude.class, JSImportMapsExtenderTopHeadDynamicInclude.class
	}
)
public class JSImportMapsExtenderTopHeadDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		if (_jsImportMapsConfiguration.enableImportMaps() &&
			(!_globalImportMaps.isEmpty() || !_scopedImportMaps.isEmpty())) {

			printWriter.print("<script type=\"");

			if (_jsImportMapsConfiguration.enableESModuleShims()) {
				printWriter.print("importmap-shim");
			}
			else {
				printWriter.print("importmap");
			}

			printWriter.print("\">");
			printWriter.print(_importMaps.get());
			printWriter.print("</script>");
		}

		if (_jsImportMapsConfiguration.enableESModuleShims()) {
			printWriter.print("<script type=\"esms-options\">{\"shimMode\": ");
			printWriter.print("true}</script><script src=\"");

			AbsolutePortalURLBuilder absolutePortalURLBuilder =
				_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
					httpServletRequest);

			printWriter.print(
				absolutePortalURLBuilder.forBundleScript(
					_bundleContext.getBundle(),
					"/es-module-shims/es-module-shims.js"
				).build());

			printWriter.print("\"></script>\n");
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	public JSImportMapsRegistration register(
		String scope, JSONObject jsonObject) {

		if (scope == null) {
			long globalId = _nextGlobalId.getAndIncrement();

			_globalImportMaps.put(globalId, jsonObject);

			_rebuildImportMaps();

			return new JSImportMapsRegistration() {

				@Override
				public void unregister() {
					_globalImportMaps.remove(globalId);
				}

			};
		}

		_scopedImportMaps.put(scope, jsonObject);

		return new JSImportMapsRegistration() {

			@Override
			public void unregister() {
				_scopedImportMaps.remove(scope);
			}

		};
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_rebuildImportMaps();

		modified(properties);
	}

	@Modified
	protected void modified(Map<String, Object> properties) {

		// See LPS-165021

		_jsImportMapsConfiguration = ConfigurableUtil.createConfigurable(
			JSImportMapsConfiguration.class,
			HashMapBuilder.put(
				"enable-es-module-shims", false
			).put(
				"enable-import-maps", true
			).build());

		FrontendESMUtil.setScriptType(
			_jsImportMapsConfiguration.enableESModuleShims() ? "module-shim" :
				"module");
	}

	private synchronized void _rebuildImportMaps() {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"imports",
			() -> {
				JSONObject importsJSONObject = _jsonFactory.createJSONObject();

				for (JSONObject jsonObject1 : _globalImportMaps.values()) {
					for (String key : jsonObject1.keySet()) {
						importsJSONObject.put(key, jsonObject1.getString(key));
					}
				}

				return importsJSONObject;
			}
		).put(
			"scopes",
			() -> {
				JSONObject scopesJSONObject = _jsonFactory.createJSONObject();

				for (Map.Entry<String, JSONObject> entry :
						_scopedImportMaps.entrySet()) {

					scopesJSONObject.put(entry.getKey(), entry.getValue());
				}

				return scopesJSONObject;
			}
		);

		_importMaps.set(_jsonFactory.looseSerializeDeep(jsonObject));
	}

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private volatile BundleContext _bundleContext;
	private final ConcurrentMap<Long, JSONObject> _globalImportMaps =
		new ConcurrentHashMap<>();
	private final AtomicReference<String> _importMaps = new AtomicReference<>();
	private volatile JSImportMapsConfiguration _jsImportMapsConfiguration;

	@Reference
	private JSONFactory _jsonFactory;

	private final AtomicLong _nextGlobalId = new AtomicLong();
	private final ConcurrentMap<String, JSONObject> _scopedImportMaps =
		new ConcurrentHashMap<>();

}