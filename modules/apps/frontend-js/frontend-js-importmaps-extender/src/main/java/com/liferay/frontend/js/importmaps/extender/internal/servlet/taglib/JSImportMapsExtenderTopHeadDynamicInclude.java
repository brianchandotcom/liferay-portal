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
import java.util.concurrent.ConcurrentMap;
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

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		modified();

		_rebuildImportMaps(_ALL_COMPANIES);

		_serviceTracker = new ServiceTracker<>(
			bundleContext, JSImportMapsContributor.class,
			_serviceTrackerCustomizer);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;

		_bundleContext = null;
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

	private ConcurrentMap<Long, JSONObject> _getGlobalImportMapsJSONObjects(
		Long companyId) {

		ConcurrentMap<Long, JSONObject> globalImportMapsJSONObjects1 =
			_globalImportMapJSONObjectsMap.get(companyId);

		if (globalImportMapsJSONObjects1 == null) {
			ConcurrentMap<Long, JSONObject> globalImportMapsJSONObjects2 =
				new ConcurrentHashMap<>();

			globalImportMapsJSONObjects1 =
				_globalImportMapJSONObjectsMap.putIfAbsent(
					companyId, globalImportMapsJSONObjects2);

			if (globalImportMapsJSONObjects1 == null) {
				return globalImportMapsJSONObjects2;
			}
		}

		return globalImportMapsJSONObjects1;
	}

	private ConcurrentMap<String, JSONObject> _getScopedImportMapJSONObjects(
		Long companyId) {

		ConcurrentMap<String, JSONObject> scopedImportMapsJSONObjects1 =
			_scopedImportMapJSONObjectsMap.get(companyId);

		if (scopedImportMapsJSONObjects1 == null) {
			ConcurrentMap<String, JSONObject> scopedImportMapsJSONObjects2 =
				new ConcurrentHashMap<>();

			scopedImportMapsJSONObjects1 =
				_scopedImportMapJSONObjectsMap.putIfAbsent(
					companyId, scopedImportMapsJSONObjects2);

			if (scopedImportMapsJSONObjects1 == null) {
				return scopedImportMapsJSONObjects2;
			}
		}

		return scopedImportMapsJSONObjects1;
	}

	private synchronized void _rebuildImportMaps(long companyId) {
		if (companyId == _ALL_COMPANIES) {
			_companyLocalService.forEachCompanyId(this::_rebuildImportMaps);

			return;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		ConcurrentMap<Long, JSONObject> allCompaniesGlobalImportMapJSONObjects =
			_getGlobalImportMapsJSONObjects(_ALL_COMPANIES);
		ConcurrentMap<Long, JSONObject> companyGlobalImportMapJSONObjects =
			_getGlobalImportMapsJSONObjects(companyId);

		ConcurrentMap<String, JSONObject>
			allCompaniesScopedImportMapJSONObjects =
				_getScopedImportMapJSONObjects(_ALL_COMPANIES);
		ConcurrentMap<String, JSONObject> companyScopedImportMapJSONObjects =
			_getScopedImportMapJSONObjects(companyId);

		jsonObject.put(
			"imports",
			() -> {
				JSONObject importsJSONObject = _jsonFactory.createJSONObject();

				for (JSONObject allCompaniesGlobalImportMapJSONObject :
						allCompaniesGlobalImportMapJSONObjects.values()) {

					for (String key :
							allCompaniesGlobalImportMapJSONObject.keySet()) {

						importsJSONObject.put(
							key,
							allCompaniesGlobalImportMapJSONObject.getString(
								key));
					}
				}

				for (JSONObject companyGlobalImportMapJSONObject :
						companyGlobalImportMapJSONObjects.values()) {

					for (String key :
							companyGlobalImportMapJSONObject.keySet()) {

						importsJSONObject.put(
							key,
							companyGlobalImportMapJSONObject.getString(key));
					}
				}

				return importsJSONObject;
			}
		).put(
			"scopes",
			() -> {
				JSONObject scopesJSONObject = _jsonFactory.createJSONObject();

				for (Map.Entry<String, JSONObject> entry :
						allCompaniesScopedImportMapJSONObjects.entrySet()) {

					scopesJSONObject.put(entry.getKey(), entry.getValue());
				}

				for (Map.Entry<String, JSONObject> entry :
						companyScopedImportMapJSONObjects.entrySet()) {

					scopesJSONObject.put(entry.getKey(), entry.getValue());
				}

				return scopesJSONObject;
			}
		);

		_importMapsMap.put(
			companyId, _jsonFactory.looseSerializeDeep(jsonObject));
	}

	private JSImportMapsRegistration _register(
		long companyId, String scope, JSONObject jsonObject) {

		if (scope == null) {
			final ConcurrentMap<Long, JSONObject> globalImportMapsJSONObjects =
				_getGlobalImportMapsJSONObjects(companyId);

			long globalId = _nextGlobalId.getAndIncrement();

			globalImportMapsJSONObjects.put(globalId, jsonObject);

			_rebuildImportMaps(companyId);

			return new JSImportMapsRegistration() {

				@Override
				public void unregister() {
					globalImportMapsJSONObjects.remove(globalId);
				}

			};
		}

		final ConcurrentMap<String, JSONObject> scopedImportMapJSONObjects =
			_getScopedImportMapJSONObjects(companyId);

		scopedImportMapJSONObjects.put(scope, jsonObject);

		_rebuildImportMaps(companyId);

		return new JSImportMapsRegistration() {

			@Override
			public void unregister() {
				scopedImportMapJSONObjects.remove(scope);
			}

		};
	}

	private static final long _ALL_COMPANIES = 0;

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private volatile BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	private final ConcurrentMap<Long, ConcurrentMap<Long, JSONObject>>
		_globalImportMapJSONObjectsMap = new ConcurrentHashMap<>();
	private final ConcurrentMap<Long, String> _importMapsMap =
		new ConcurrentHashMap<>();
	private volatile JSImportMapsConfiguration _jsImportMapsConfiguration;

	@Reference
	private JSONFactory _jsonFactory;

	private final AtomicLong _nextGlobalId = new AtomicLong();

	@Reference
	private Portal _portal;

	private final ConcurrentMap<Long, ConcurrentMap<String, JSONObject>>
		_scopedImportMapJSONObjectsMap = new ConcurrentHashMap<>();
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
							"com.liferay.importmaps.company");

						if (companyId == null) {
							companyId = Long.valueOf(_ALL_COMPANIES);
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