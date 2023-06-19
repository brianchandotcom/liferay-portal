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

package com.liferay.headless.builder.internal.publisher;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.publisher.APIApplicationPublisher;
import com.liferay.headless.builder.internal.jaxrs.application.HeadlessBuilderApplication;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Application;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

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

		_headlessBuilderApplicationServiceRegistrationMap.putIfAbsent(
			apiApplication.getOSGiJaxRsName(),
			_registerHeadlessBuilderApplication(apiApplication));
	}

	@Override
	public void unpublish(APIApplication apiApplication) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-186757")) {
			throw new UnsupportedOperationException(
				"APIApplicationPublisher not available");
		}

		ServiceRegistration<Application> applicationServiceRegistration =
			_headlessBuilderApplicationServiceRegistrationMap.remove(
				apiApplication.getOSGiJaxRsName());

		if (applicationServiceRegistration != null) {
			_unregisterServiceRegistration(applicationServiceRegistration);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		_unpublishAll();
	}

	private ServiceRegistration<Application>
		_registerHeadlessBuilderApplication(APIApplication apiApplication) {

		return _bundleContext.registerService(
			Application.class, new HeadlessBuilderApplication(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", apiApplication.getCompanyId()
			).put(
				"liferay.filter.disabled", true
			).put(
				"liferay.headless.builder.application", true
			).put(
				"liferay.jackson", false
			).put(
				"osgi.jaxrs.application.base", apiApplication.getBaseURL()
			).put(
				"osgi.jaxrs.extension.select",
				"(osgi.jaxrs.name=Liferay.Vulcan)"
			).put(
				"osgi.jaxrs.name", apiApplication.getOSGiJaxRsName()
			).build());
	}

	private void _unpublishAll() {
		for (ServiceRegistration<Application> serviceRegistration :
				_headlessBuilderApplicationServiceRegistrationMap.values()) {

			_unregisterServiceRegistration(serviceRegistration);
		}

		_headlessBuilderApplicationServiceRegistrationMap.clear();
	}

	private void _unregisterServiceRegistration(
		ServiceRegistration<?> serviceRegistration) {

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static BundleContext _bundleContext;

	private final Map<String, ServiceRegistration<Application>>
		_headlessBuilderApplicationServiceRegistrationMap = new HashMap<>();

}