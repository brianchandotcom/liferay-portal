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

import graphql.annotations.processor.ProcessingElementsContainer;
import graphql.annotations.processor.graphQLProcessors.GraphQLInputProcessor;
import graphql.annotations.processor.graphQLProcessors.GraphQLOutputProcessor;
import graphql.annotations.processor.retrievers.GraphQLExtensionsHandler;
import graphql.annotations.processor.retrievers.GraphQLFieldRetriever;
import graphql.annotations.processor.retrievers.GraphQLInterfaceRetriever;
import graphql.annotations.processor.retrievers.GraphQLObjectHandler;
import graphql.annotations.processor.retrievers.GraphQLObjectInfoRetriever;
import graphql.annotations.processor.retrievers.GraphQLTypeRetriever;
import graphql.annotations.processor.searchAlgorithms.BreadthFirstSearch;
import graphql.annotations.processor.searchAlgorithms.ParentalSearch;
import graphql.annotations.processor.typeFunctions.DefaultTypeFunction;

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
		GraphQLFieldRetriever graphQLFieldRetriever =
			new GraphQLFieldRetriever();

		GraphQLObjectInfoRetriever graphQLObjectInfoRetriever =
			new GraphQLObjectInfoRetriever();

		BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(
			graphQLObjectInfoRetriever);
		ParentalSearch parentalSearch = new ParentalSearch(
			graphQLObjectInfoRetriever);

		GraphQLExtensionsHandler graphQLExtensionsHandler =
			new GraphQLExtensionsHandler();

		graphQLExtensionsHandler.setGraphQLObjectInfoRetriever(
			graphQLObjectInfoRetriever);
		graphQLExtensionsHandler.setFieldRetriever(graphQLFieldRetriever);
		graphQLExtensionsHandler.setFieldSearchAlgorithm(parentalSearch);
		graphQLExtensionsHandler.setMethodSearchAlgorithm(breadthFirstSearch);

		GraphQLInterfaceRetriever graphQLInterfaceRetriever =
			new GraphQLInterfaceRetriever();

		GraphQLTypeRetriever graphQLTypeRetriever = new GraphQLTypeRetriever();

		// Handle Circular reference between GraphQLInterfaceRetriever and
		// GraphQLTypeRetriever

		graphQLInterfaceRetriever.setGraphQLTypeRetriever(graphQLTypeRetriever);

		graphQLTypeRetriever.setGraphQLInterfaceRetriever(
			graphQLInterfaceRetriever);

		graphQLTypeRetriever.setExtensionsHandler(graphQLExtensionsHandler);
		graphQLTypeRetriever.setFieldSearchAlgorithm(parentalSearch);
		graphQLTypeRetriever.setGraphQLFieldRetriever(graphQLFieldRetriever);
		graphQLTypeRetriever.setGraphQLObjectInfoRetriever(
			graphQLObjectInfoRetriever);
		graphQLTypeRetriever.setMethodSearchAlgorithm(breadthFirstSearch);

		_graphQLObjectHandler = new GraphQLObjectHandler();

		_graphQLObjectHandler.setTypeRetriever(graphQLTypeRetriever);

		GraphQLInputProcessor graphQLInputProcessor =
			new GraphQLInputProcessor();

		graphQLInputProcessor.setGraphQLTypeRetriever(graphQLTypeRetriever);

		GraphQLOutputProcessor graphQLOutputProcessor =
			new GraphQLOutputProcessor();

		graphQLOutputProcessor.setGraphQLTypeRetriever(graphQLTypeRetriever);

		_defaultTypeFunction = new DefaultTypeFunction(
			graphQLInputProcessor, graphQLOutputProcessor);

		_serviceTracker = new ServiceTracker<>(
			bundleContext, ServletData.class,
			new ServletDataServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private DefaultTypeFunction _defaultTypeFunction;
	private GraphQLObjectHandler _graphQLObjectHandler;
	private ServiceTracker<?, ?> _serviceTracker;

	private class ServletDataServiceTrackerCustomizer
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

			ProcessingElementsContainer processingElementsContainer =
				new ProcessingElementsContainer(_defaultTypeFunction);

			GraphQLSchema.Builder schemaBuilder = GraphQLSchema.newSchema();

			schemaBuilder.mutation(
				_graphQLObjectHandler.getObject(
					mutation.getClass(), processingElementsContainer));

			schemaBuilder.query(
				_graphQLObjectHandler.getObject(
					query.getClass(), processingElementsContainer));

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