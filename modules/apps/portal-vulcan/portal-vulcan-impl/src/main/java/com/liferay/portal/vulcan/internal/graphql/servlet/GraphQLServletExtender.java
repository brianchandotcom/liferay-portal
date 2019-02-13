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

package com.liferay.portal.vulcan.internal.graphql.servlet;

import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import graphql.annotations.processor.GraphQLAnnotations;

import graphql.schema.GraphQLSchema;

import graphql.servlet.SimpleGraphQLHttpServlet;

import java.util.Dictionary;

import javax.servlet.Servlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class GraphQLServletExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, ServletData.class,
			new ServletDataServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<?, ?> _serviceTracker;

	private static class ServletDataServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ServletData, ServiceRegistration<Servlet>> {

		@Override
		public ServiceRegistration<Servlet> addingService(
			ServiceReference<ServletData> serviceReference) {

			ServletData servletData = _bundleContext.getService(
				serviceReference);

			Object mutation = servletData.getMutation();
			String path = servletData.getPath();
			Object query = servletData.getQuery();

			GraphQLSchema.Builder schemaBuilder = GraphQLSchema.newSchema();

			schemaBuilder.mutation(
				GraphQLAnnotations.object(mutation.getClass()));

			schemaBuilder.query(GraphQLAnnotations.object(query.getClass()));

			SimpleGraphQLHttpServlet.Builder servletBuilder =
				SimpleGraphQLHttpServlet.newBuilder(schemaBuilder.build());

			Servlet servlet = servletBuilder.build();

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put("osgi.http.whiteboard.context.path", path);
			properties.put(
				"osgi.http.whiteboard.servlet.pattern", path.concat("/*"));

			Class<? extends ServletData> clazz = servletData.getClass();

			properties.put(
				"osgi.http.whiteboard.servlet.name", clazz.getName());

			return _bundleContext.registerService(
				Servlet.class, servlet, properties);
		}

		@Override
		public void modifiedService(
			ServiceReference<ServletData> serviceReference,
			ServiceRegistration<Servlet> serviceRegistration) {
		}

		@Override
		public void removedService(
			ServiceReference<ServletData> serviceReference,
			ServiceRegistration<Servlet> serviceRegistration) {

			serviceRegistration.unregister();

			_bundleContext.ungetService(serviceReference);
		}

		private ServletDataServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}