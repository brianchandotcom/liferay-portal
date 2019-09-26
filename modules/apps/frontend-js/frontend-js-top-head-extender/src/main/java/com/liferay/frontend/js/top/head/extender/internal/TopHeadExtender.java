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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = {})
public class TopHeadExtender
	implements ServiceTrackerCustomizer
		<ServletContext, ServiceRegistration<TopHeadResources>> {

	@Override
	public ServiceRegistration<TopHeadResources> addingService(
		ServiceReference<ServletContext> serviceReference) {

		Bundle bundle = serviceReference.getBundle();

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		Map.Entry<List<String>, List<String>> entry = _scanTopHeadResources(
			headers);

		if (entry == null) {
			return null;
		}

		ServletContext servletContext = _bundleContext.getService(
			serviceReference);

		return _bundleContext.registerService(
			TopHeadResources.class,
			new TopHeadResourcesImpl(
				servletContext.getContextPath(), entry.getKey(),
				entry.getValue()),
			MapUtil.singletonDictionary(
				"service.ranking",
				GetterUtil.getInteger(headers.get("Liferay-Top-Head-Weight"))));
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

		_bundleContext.ungetService(serviceReference);
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				StringBundler.concat(
					"(&(objectClass=", ServletContext.class.getName(),
					")(osgi.web.symbolicname=*))")),
			this);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static Map.Entry<List<String>, List<String>> _scanTopHeadResources(
		Dictionary<String, String> headers) {

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

		return new AbstractMap.SimpleImmutableEntry<>(
			jsResourcePaths, authenticatedJsResourcePaths);
	}

	private BundleContext _bundleContext;
	private ServiceTracker
		<ServletContext, ServiceRegistration<TopHeadResources>> _serviceTracker;

}