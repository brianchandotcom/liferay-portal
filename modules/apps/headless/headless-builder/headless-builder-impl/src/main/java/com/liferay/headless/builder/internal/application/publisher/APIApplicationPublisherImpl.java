/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.application.publisher;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.publisher.APIApplicationPublisher;
import com.liferay.headless.builder.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.helper.EndpointHelper;
import com.liferay.headless.builder.internal.resource.HeadlessBuilderResourceImpl;
import com.liferay.headless.builder.internal.resource.OpenAPIResourceImpl;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.ws.rs.core.Application;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = APIApplicationPublisher.class)
public class APIApplicationPublisherImpl implements APIApplicationPublisher {

	@Override
	public void publish(APIApplication apiApplication) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-186757")) {
			throw new UnsupportedOperationException(
				"APIApplicationPublisher not available");
		}

		String osgiJaxRsName = _getOSGiJaxRsName(apiApplication);

		HeadlessBuilderResourceImpl headlessBuilderResourceImpl =
			_headlessBuilderResourceImpls.get(osgiJaxRsName);

		if (headlessBuilderResourceImpl != null) {
			headlessBuilderResourceImpl.setApiApplication(apiApplication);

			return;
		}

		_serviceRegistrationsMap.computeIfAbsent(
			osgiJaxRsName,
			key -> new ArrayList<ServiceRegistration<?>>() {
				{
					add(_registerApplication(apiApplication, osgiJaxRsName));
					add(
						_registerResource(
							osgiJaxRsName, HeadlessBuilderResourceImpl.class,
							() -> {
								HeadlessBuilderResourceImpl
									headlessBuilderResourceImpl =
										new HeadlessBuilderResourceImpl(
											apiApplication, _endpointHelper);

								_headlessBuilderResourceImpls.put(
									osgiJaxRsName, headlessBuilderResourceImpl);

								return headlessBuilderResourceImpl;
							}));
					add(
						_registerResource(
							osgiJaxRsName, OpenAPIResourceImpl.class,
							() -> new OpenAPIResourceImpl(_openAPIResource)));
				}
			});
	}

	@Override
	public void unpublish(String baseURL, long companyId) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-186757")) {
			throw new UnsupportedOperationException(
				"APIApplicationPublisher not available");
		}

		_headlessBuilderResourceImpls.remove(
			_getOSGiJaxRsName(baseURL, companyId));

		List<ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrationsMap.remove(
				_getOSGiJaxRsName(baseURL, companyId));

		if (serviceRegistrations != null) {
			_unregisterServiceRegistrations(serviceRegistrations);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		for (List<ServiceRegistration<?>> serviceRegistrations :
				_serviceRegistrationsMap.values()) {

			_unregisterServiceRegistrations(serviceRegistrations);
		}

		_serviceRegistrationsMap.clear();
		_headlessBuilderResourceImpls.clear();
	}

	private String _getOSGiJaxRsName(APIApplication apiApplication) {
		return _getOSGiJaxRsName(
			apiApplication.getBaseURL(), apiApplication.getCompanyId());
	}

	private String _getOSGiJaxRsName(String baseURL, long companyId) {
		return baseURL + companyId;
	}

	private ServiceRegistration<Application> _registerApplication(
		APIApplication apiApplication, String osgiJaxRsName) {

		return _bundleContext.registerService(
			Application.class, new Application(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", apiApplication.getCompanyId()
			).put(
				"liferay.filter.disabled", true
			).put(
				"liferay.headless.builder.application", true
			).put(
				"liferay.jackson", false
			).put(
				"osgi.jaxrs.application.base",
				HeadlessBuilderConstants.BASE_PATH_SUFFIX +
					apiApplication.getBaseURL()
			).put(
				"osgi.jaxrs.extension.select",
				"(osgi.jaxrs.name=Liferay.Vulcan)"
			).put(
				"osgi.jaxrs.name", osgiJaxRsName
			).build());
	}

	private <T> ServiceRegistration<T> _registerResource(
		String osgiJaxRsName, Class<T> resourceClass,
		Supplier<T> resourceSupplier) {

		return _bundleContext.registerService(
			resourceClass,
			new PrototypeServiceFactory<T>() {

				@Override
				public T getService(
					Bundle bundle, ServiceRegistration<T> serviceRegistration) {

					return resourceSupplier.get();
				}

				@Override
				public void ungetService(
					Bundle bundle, ServiceRegistration<T> serviceRegistration,
					T t) {
				}

			},
			HashMapDictionaryBuilder.<String, Object>put(
				"api.version", "v1.0"
			).put(
				"osgi.jaxrs.application.select",
				"(osgi.jaxrs.name=" + osgiJaxRsName + ")"
			).put(
				"osgi.jaxrs.resource", "true"
			).build());
	}

	private void _unregisterServiceRegistrations(
		List<ServiceRegistration<?>> serviceRegistrations) {

		for (ServiceRegistration<?> serviceRegistration :
				serviceRegistrations) {

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
	}

	private static BundleContext _bundleContext;

	@Reference
	private EndpointHelper _endpointHelper;

	private final Map<String, HeadlessBuilderResourceImpl>
		_headlessBuilderResourceImpls = new HashMap<>();

	@Reference
	private OpenAPIResource _openAPIResource;

	private final Map<String, List<ServiceRegistration<?>>>
		_serviceRegistrationsMap = new HashMap<>();

}