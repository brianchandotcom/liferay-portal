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
import graphql.annotations.annotationTypes.GraphQLName;
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
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.servlet.SimpleGraphQLHttpServlet;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import javax.servlet.Servlet;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import static graphql.annotations.processor.util.NamingKit.toGraphqlName;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class GraphQLServletExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		GraphQLFieldRetriever graphQLFieldRetriever =
			new GraphQLFieldRetriever();
		GraphQLInterfaceRetriever graphQLInterfaceRetriever =
			new GraphQLInterfaceRetriever();

		GraphQLObjectInfoRetriever graphQLObjectInfoRetriever =
			new GraphQLObjectInfoRetriever() {

				@Override
				public String getTypeName(Class<?> objectClass) {
					GraphQLName name = objectClass.getAnnotation(
						GraphQLName.class);

					if (name == null) {
						return toGraphqlName(objectClass.getName());
					}

					return toGraphqlName(name.value());
				}

			};

		BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(
			graphQLObjectInfoRetriever);
		ParentalSearch parentalSearch = new ParentalSearch(
			graphQLObjectInfoRetriever);

		GraphQLTypeRetriever graphQLTypeRetriever = new GraphQLTypeRetriever() {
			{
				setExtensionsHandler(
					new GraphQLExtensionsHandler() {
						{
							setFieldRetriever(graphQLFieldRetriever);
							setFieldSearchAlgorithm(parentalSearch);
							setGraphQLObjectInfoRetriever(
								graphQLObjectInfoRetriever);
							setMethodSearchAlgorithm(breadthFirstSearch);
						}
					});
				setFieldSearchAlgorithm(parentalSearch);
				setGraphQLFieldRetriever(graphQLFieldRetriever);
				setGraphQLInterfaceRetriever(graphQLInterfaceRetriever);
				setGraphQLObjectInfoRetriever(graphQLObjectInfoRetriever);
				setMethodSearchAlgorithm(breadthFirstSearch);
			}
		};

		// Handle Circular reference between GraphQLInterfaceRetriever and
		// GraphQLTypeRetriever

		graphQLInterfaceRetriever.setGraphQLTypeRetriever(graphQLTypeRetriever);

		_defaultTypeFunction = new DefaultTypeFunction(
			new GraphQLInputProcessor() {
				{
					setGraphQLTypeRetriever(graphQLTypeRetriever);
				}
			},
			new GraphQLOutputProcessor() {
				{
					setGraphQLTypeRetriever(graphQLTypeRetriever);
				}
			});
		_graphQLObjectHandler = new GraphQLObjectHandler() {
			{
				setTypeRetriever(graphQLTypeRetriever);
			}
		};

		Dictionary<String, Object> helperProperties = new HashMapDictionary<>();

		helperProperties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, "GraphQL");
		helperProperties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, "/graphql");
		helperProperties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET, "GraphQL");

		_servletContextHelperServiceRegistration =
			bundleContext.registerService(
				ServletContextHelper.class,
				new ServletContextHelper(bundleContext.getBundle()) {
				},
				helperProperties);

		_activated = true;

		rewire();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected synchronized void addServletData(ServletData servletData) {
		_servletDataList.add(servletData);

		rewire();
	}

	@Deactivate
	protected void deactivate() {
		_activated = false;

		if (_servletServiceRegistration != null) {
			try {
				_servletServiceRegistration.unregister();
			}
			catch (Exception e) {
			}
		}

		try {
			_servletContextHelperServiceRegistration.unregister();
		}
		catch (Exception e) {
		}
	}

	protected void registerServlet(GraphQLSchema.Builder schemaBuilder) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "GraphQL");

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*");

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT, "GraphQL");

		// Servlet

		SimpleGraphQLHttpServlet.Builder servletBuilder =
			SimpleGraphQLHttpServlet.newBuilder(schemaBuilder.build());

		Servlet servlet = servletBuilder.build();

		if (_servletServiceRegistration != null) {
			try {
				_servletServiceRegistration.unregister();
			}
			catch (Exception e) {
			}
		}

		_servletServiceRegistration = _bundleContext.registerService(
			Servlet.class, servlet, properties);
	}

	protected synchronized void removeServletData(ServletData servletData) {
		_servletDataList.remove(servletData);

		rewire();
	}

	protected synchronized void rewire() {
		if (!_activated) {
			return;
		}

		ProcessingElementsContainer processingElementsContainer =
			new ProcessingElementsContainer(_defaultTypeFunction);

		GraphQLSchema.Builder schemaBuilder = GraphQLSchema.newSchema();

		GraphQLObjectType.Builder mutationBuilder =
			GraphQLObjectType.newObject();

		mutationBuilder = mutationBuilder.name("mutation");

		GraphQLObjectType.Builder queryBuilder = GraphQLObjectType.newObject();

		queryBuilder = queryBuilder.name("query");

		for (ServletData servletData : _servletDataList) {

			Object mutation = servletData.getMutation();

			GraphQLObjectType mutationGraphQLObjectType =
				_graphQLObjectHandler.getObject(
					mutation.getClass(), processingElementsContainer);

			mutationBuilder.field(
				GraphQLFieldDefinition.newFieldDefinition(
				).name(
					servletData.getPath()
				).type(
					mutationGraphQLObjectType
				).dataFetcherFactory(
					StaticDataFetcher::new
				)
			).build();

			Object query = servletData.getQuery();

			GraphQLObjectType queryGraphQLObjectType =
				_graphQLObjectHandler.getObject(
					query.getClass(), processingElementsContainer);

			queryBuilder.field(
				GraphQLFieldDefinition.newFieldDefinition(
				).name(
					servletData.getPath()
				).type(
					queryGraphQLObjectType
				).dataFetcherFactory(
					StaticDataFetcher::new
				)
			).build();
		}

		schemaBuilder.mutation(mutationBuilder.build());
		schemaBuilder.query(queryBuilder.build());

		registerServlet(schemaBuilder);
	}

	private volatile boolean _activated;
	private BundleContext _bundleContext;
	private DefaultTypeFunction _defaultTypeFunction;
	private GraphQLObjectHandler _graphQLObjectHandler;

	private ServiceRegistration<ServletContextHelper>
		_servletContextHelperServiceRegistration;
	private final List<ServletData> _servletDataList = new ArrayList<>();
	private volatile ServiceRegistration<Servlet> _servletServiceRegistration;

}