/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib;

import com.liferay.frontend.js.importmaps.extender.JSImportMapsContributor;
import com.liferay.frontend.js.importmaps.extender.internal.configuration.JSImportMapsConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.content.security.policy.ContentSecurityPolicyNonceProviderUtil;
import com.liferay.portal.kernel.frontend.esm.FrontendESMUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	configurationPid = "com.liferay.frontend.js.importmaps.extender.internal.configuration.JSImportMapsConfiguration",
	property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = DynamicInclude.class
)
public class JSImportMapsExtenderTopHeadDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		String importMaps = _importMapsMap.get(
			_portal.getCompanyId(httpServletRequest));

		PrintWriter printWriter = httpServletResponse.getWriter();

		if (_jsImportMapsConfiguration.enableImportMaps() &&
			!Validator.isBlank(importMaps)) {

			printWriter.print("<script");
			printWriter.write(
				ContentSecurityPolicyNonceProviderUtil.getNonceAttribute(
					httpServletRequest));
			printWriter.print(" type=\"");

			if (_jsImportMapsConfiguration.enableESModuleShims()) {
				printWriter.print("importmap-shim");
			}
			else {
				printWriter.print("importmap");
			}

			printWriter.print("\">");
			printWriter.print(importMaps);
			printWriter.print("</script>");
		}

		if (_jsImportMapsConfiguration.enableESModuleShims()) {
			printWriter.print("<script");
			printWriter.write(
				ContentSecurityPolicyNonceProviderUtil.getNonceAttribute(
					httpServletRequest));
			printWriter.print(" type=\"esms-options\">{\"shimMode\": ");
			printWriter.print("true}</script><script");
			printWriter.write(
				ContentSecurityPolicyNonceProviderUtil.getNonceAttribute(
					httpServletRequest));
			printWriter.print(" src=\"");

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

	public void rebuildImportMaps() {
		_rebuildImportMaps(_COMPANY_ID_ALL);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		modified();

		_rebuildImportMaps(_COMPANY_ID_ALL);

		_serviceTracker = new ServiceTracker<>(
			bundleContext, JSImportMapsContributor.class,
			_serviceTrackerCustomizer);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;

		_serviceTracker.close();

		_serviceTracker = null;
	}

	@Modified
	protected void modified() {

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

	private Map<Long, JSONObject> _getGlobalImportMapsJSONObjects(
		Long companyId) {

		Map<Long, JSONObject> globalImportMapsJSONObjects1 =
			_globalImportMapsJSONObjectsMap.get(companyId);

		if (globalImportMapsJSONObjects1 != null) {
			return globalImportMapsJSONObjects1;
		}

		Map<Long, JSONObject> globalImportMapsJSONObjects2 =
			new ConcurrentHashMap<>();

		globalImportMapsJSONObjects1 =
			_globalImportMapsJSONObjectsMap.putIfAbsent(
				companyId, globalImportMapsJSONObjects2);

		if (globalImportMapsJSONObjects1 != null) {
			return globalImportMapsJSONObjects1;
		}

		return globalImportMapsJSONObjects2;
	}

	private Map<String, JSONObject> _getScopedImportMapsJSONObjects(
		Long companyId) {

		Map<String, JSONObject> scopedImportMapsJSONObjects1 =
			_scopedImportMapsJSONObjectsMap.get(companyId);

		if (scopedImportMapsJSONObjects1 != null) {
			return scopedImportMapsJSONObjects1;
		}

		Map<String, JSONObject> scopedImportMapsJSONObjects2 =
			new ConcurrentHashMap<>();

		scopedImportMapsJSONObjects1 =
			_scopedImportMapsJSONObjectsMap.putIfAbsent(
				companyId, scopedImportMapsJSONObjects2);

		if (scopedImportMapsJSONObjects1 != null) {
			return scopedImportMapsJSONObjects1;
		}

		return scopedImportMapsJSONObjects2;
	}

	private void _putImports(
		JSONObject importsJSONObject,
		Map<Long, JSONObject> globalImportMapsJSONObjects) {

		for (JSONObject jsonObject : globalImportMapsJSONObjects.values()) {
			for (String key : jsonObject.keySet()) {
				importsJSONObject.put(key, jsonObject.getString(key));
			}
		}
	}

	private void _putScopes(
		JSONObject scopesJSONObject,
		Map<String, JSONObject> scopedImportMapsJSONObjects) {

		for (Map.Entry<String, JSONObject> entry :
				scopedImportMapsJSONObjects.entrySet()) {

			scopesJSONObject.put(entry.getKey(), entry.getValue());
		}
	}

	private synchronized void _rebuildImportMaps(long companyId) {
		if (companyId == _COMPANY_ID_ALL) {
			_companyLocalService.forEachCompanyId(this::_rebuildImportMaps);

			return;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"imports",
			() -> {
				JSONObject importsJSONObject = _jsonFactory.createJSONObject();

				_putImports(
					importsJSONObject,
					_getGlobalImportMapsJSONObjects(_COMPANY_ID_ALL));
				_putImports(
					importsJSONObject,
					_getGlobalImportMapsJSONObjects(companyId));

				return importsJSONObject;
			}
		).put(
			"scopes",
			() -> {
				JSONObject scopesJSONObject = _jsonFactory.createJSONObject();

				_putScopes(
					scopesJSONObject,
					_getScopedImportMapsJSONObjects(_COMPANY_ID_ALL));
				_putScopes(
					scopesJSONObject,
					_getScopedImportMapsJSONObjects(companyId));

				return scopesJSONObject;
			}
		);

		_importMapsMap.put(
			companyId, _jsonFactory.looseSerializeDeep(jsonObject));
	}

	private JSImportMapsRegistration _register(
		long companyId, String scope, JSONObject jsonObject) {

		if (scope == null) {
			Map<Long, JSONObject> globalImportMapsJSONObjects =
				_getGlobalImportMapsJSONObjects(companyId);

			long globalId = _nextGlobalId.getAndIncrement();

			globalImportMapsJSONObjects.put(globalId, jsonObject);

			_rebuildImportMaps(companyId);

			return () -> globalImportMapsJSONObjects.remove(globalId);
		}

		Map<String, JSONObject> scopedImportMapsJSONObjects =
			_getScopedImportMapsJSONObjects(companyId);

		scopedImportMapsJSONObjects.put(scope, jsonObject);

		_rebuildImportMaps(companyId);

		return () -> scopedImportMapsJSONObjects.remove(scope);
	}

	private static final long _COMPANY_ID_ALL = 0;

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private volatile BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	private final Map<Long, Map<Long, JSONObject>>
		_globalImportMapsJSONObjectsMap = new ConcurrentHashMap<>();
	private final Map<Long, String> _importMapsMap = new ConcurrentHashMap<>();
	private volatile JSImportMapsConfiguration _jsImportMapsConfiguration;

	@Reference
	private JSONFactory _jsonFactory;

	private final AtomicLong _nextGlobalId = new AtomicLong();

	@Reference
	private Portal _portal;

	private final Map<Long, Map<String, JSONObject>>
		_scopedImportMapsJSONObjectsMap = new ConcurrentHashMap<>();
	private ServiceTracker<JSImportMapsContributor, JSImportMapsRegistration>
		_serviceTracker;

	private final ServiceTrackerCustomizer
		<JSImportMapsContributor, JSImportMapsRegistration>
			_serviceTrackerCustomizer =
				new ServiceTrackerCustomizer
					<JSImportMapsContributor, JSImportMapsRegistration>() {

					@Override
					public JSImportMapsRegistration addingService(
						ServiceReference<JSImportMapsContributor>
							serviceReference) {

						Long companyId = (Long)serviceReference.getProperty(
							"com.liferay.frontend.js.importmaps.company.id");

						if (companyId == null) {
							companyId = Long.valueOf(_COMPANY_ID_ALL);
						}

						JSImportMapsContributor jsImportMapsContributor =
							_bundleContext.getService(serviceReference);

						return _register(
							companyId, jsImportMapsContributor.getScope(),
							jsImportMapsContributor.getImportMapsJSONObject());
					}

					@Override
					public void modifiedService(
						ServiceReference serviceReference,
						JSImportMapsRegistration jsImportMapsRegistration) {
					}

					@Override
					public void removedService(
						ServiceReference serviceReference,
						JSImportMapsRegistration jsImportMapsRegistration) {

						jsImportMapsRegistration.unregister();
					}

				};

}