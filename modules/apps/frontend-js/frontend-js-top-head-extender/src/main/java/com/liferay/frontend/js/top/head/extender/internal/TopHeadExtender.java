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

package com.liferay.frontend.js.top.head.extender.internal;

import com.liferay.frontend.js.top.head.extender.TopHeadResources;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = {})
public class TopHeadExtender
	implements BundleTrackerCustomizer
		<ServiceTracker
			<ServletContext, ServiceRegistration<TopHeadResources>>> {

	@Override
	public ServiceTracker<ServletContext, ServiceRegistration<TopHeadResources>>
		addingBundle(Bundle bundle, BundleEvent bundleEvent) {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String liferayJsResourcesTopHead = headers.get(
			"Liferay-JS-Resources-Top-Head");

		String liferayJsResourcesTopHeadAuthenticated = headers.get(
			"Liferay-JS-Resources-Top-Head-Authenticated");

		if (Validator.isBlank(liferayJsResourcesTopHead) &&
			Validator.isBlank(liferayJsResourcesTopHeadAuthenticated)) {

			return null;
		}

		List<String> jsResourcePaths = null;

		if (Validator.isNull(liferayJsResourcesTopHead)) {
			jsResourcePaths = Collections.emptyList();
		}
		else {
			jsResourcePaths = Arrays.asList(
				liferayJsResourcesTopHead.split(StringPool.COMMA));
		}

		List<String> authenticatedJsResourcePaths = null;

		if (Validator.isNull(liferayJsResourcesTopHeadAuthenticated)) {
			authenticatedJsResourcePaths = Collections.emptyList();
		}
		else {
			authenticatedJsResourcePaths = Arrays.asList(
				liferayJsResourcesTopHeadAuthenticated.split(StringPool.COMMA));
		}

		TopHeadResourcesImpl topHeadResourcesImpl = new TopHeadResourcesImpl(
			jsResourcePaths, authenticatedJsResourcePaths);

		int liferayTopHeadWeight = GetterUtil.getInteger(
			headers.get("Liferay-Top-Head-Weight"));

		BundleContext bundleContext = bundle.getBundleContext();

		String filterString = StringBundler.concat(
			"(&(objectClass=", ServletContext.class.getName(),
			")(osgi.web.symbolicname=", bundle.getSymbolicName(), "))");

		Dictionary<String, Object> properties = MapUtil.singletonDictionary(
			"service.ranking", liferayTopHeadWeight);

		return ServiceTrackerFactory.open(
			bundleContext, filterString,
			new ServiceTrackerCustomizer
				<ServletContext, ServiceRegistration<TopHeadResources>>() {

				@Override
				public ServiceRegistration<TopHeadResources> addingService(
					ServiceReference<ServletContext> serviceReference) {

					ServletContext servletContext = bundleContext.getService(
						serviceReference);

					topHeadResourcesImpl.setServletContextPath(
						servletContext.getContextPath());

					return bundleContext.registerService(
						TopHeadResources.class, topHeadResourcesImpl,
						properties);
				}

				@Override
				public void modifiedService(
					ServiceReference<ServletContext> serviceReference,
					ServiceRegistration<TopHeadResources> serviceRegistration) {
				}

				@Override
				public void removedService(
					ServiceReference<ServletContext> serviceReference,
					ServiceRegistration<TopHeadResources> serviceRegistration) {

					serviceRegistration.unregister();

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		ServiceTracker<ServletContext, ServiceRegistration<TopHeadResources>>
			serviceTracker) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		ServiceTracker<ServletContext, ServiceRegistration<TopHeadResources>>
			serviceTracker) {

		serviceTracker.close();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE | Bundle.STARTING, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private BundleTracker
		<ServiceTracker<ServletContext, ServiceRegistration<TopHeadResources>>>
			_bundleTracker;

}