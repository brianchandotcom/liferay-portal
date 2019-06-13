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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;

import graphql.annotations.GraphQLFieldDefinitionWrapper;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.processor.ProcessingElementsContainer;
import graphql.annotations.processor.graphQLProcessors.GraphQLInputProcessor;
import graphql.annotations.processor.graphQLProcessors.GraphQLOutputProcessor;
import graphql.annotations.processor.retrievers.GraphQLExtensionsHandler;
import graphql.annotations.processor.retrievers.GraphQLFieldRetriever;
import graphql.annotations.processor.retrievers.GraphQLInterfaceRetriever;
import graphql.annotations.processor.retrievers.GraphQLObjectInfoRetriever;
import graphql.annotations.processor.retrievers.GraphQLTypeRetriever;
import graphql.annotations.processor.retrievers.fieldBuilders.ArgumentBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.DeprecateBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.DescriptionBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.method.MethodNameBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.method.MethodTypeBuilder;
import graphql.annotations.processor.searchAlgorithms.BreadthFirstSearch;
import graphql.annotations.processor.searchAlgorithms.ParentalSearch;
import graphql.annotations.processor.typeFunctions.DefaultTypeFunction;
import graphql.annotations.processor.util.NamingKit;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLSchema;

import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLHttpServlet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class GraphQLServletExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_graphQLFieldRetriever = new LiferayGraphQLFieldRetriever();

		GraphQLInterfaceRetriever graphQLInterfaceRetriever =
			new GraphQLInterfaceRetriever();

		GraphQLObjectInfoRetriever graphQLObjectInfoRetriever =
			new GraphQLObjectInfoRetriever() {

				@Override
				public String getTypeName(Class<?> objectClass) {
					GraphQLName graphQLName = objectClass.getAnnotation(
						GraphQLName.class);

					if (graphQLName == null) {
						return NamingKit.toGraphqlName(objectClass.getName());
					}

					return NamingKit.toGraphqlName(graphQLName.value());
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
							setFieldRetriever(_graphQLFieldRetriever);
							setFieldSearchAlgorithm(parentalSearch);
							setGraphQLObjectInfoRetriever(
								graphQLObjectInfoRetriever);
							setMethodSearchAlgorithm(breadthFirstSearch);
						}
					});
				setFieldSearchAlgorithm(parentalSearch);
				setGraphQLFieldRetriever(_graphQLFieldRetriever);
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

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, "GraphQL");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, "/graphql");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET, "GraphQL");

		_servletContextHelperServiceRegistration =
			bundleContext.registerService(
				ServletContextHelper.class,
				new ServletContextHelper(bundleContext.getBundle()) {
				},
				properties);

		_servletDataServiceTracker = new ServiceTracker<>(
			bundleContext, ServletData.class,
			new ServletDataServiceTrackerCustomizer());

		_servletDataServiceTracker.open();

		properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT, "GraphQL");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "GraphQL");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*");

		_servletServiceRegistration = _bundleContext.registerService(
			Servlet.class,
			(Servlet)ProxyUtil.newProxyInstance(
				GraphQLServletExtender.class.getClassLoader(),
				new Class<?>[] {Servlet.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws Throwable {

						String methodName = method.getName();

						if (methodName.equals("destroy")) {
							return null;
						}

						if (methodName.equals("getServletInfo")) {
							return StringPool.BLANK;
						}

						if (methodName.equals("hashCode")) {
							return hashCode();
						}

						if (methodName.equals("init") && (args.length > 0)) {
							_servletConfig = (ServletConfig)args[0];

							return null;
						}

						Servlet servlet = _getServlet();

						servlet.init(_servletConfig);

						try {
							return method.invoke(servlet, args);
						}
						catch (InvocationTargetException ite) {
							throw ite.getCause();
						}
					}

					private ServletConfig _servletConfig;

				}),
			properties);
	}

	@Deactivate
	protected void deactivate() {
		_servletDataServiceTracker.close();

		_servletServiceRegistration.unregister();

		_servletContextHelperServiceRegistration.unregister();
	}

	private void _collectObjectFields(
		ProcessingElementsContainer processingElementsContainer,
		GraphQLObjectType.Builder builder, Object object) {

		Class<?> clazz = object.getClass();

		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(GraphQLField.class)) {
				builder.field(
					_graphQLFieldRetriever.getField(
						method, processingElementsContainer));
			}
		}
	}

	private Object _get(
			DataFetchingEnvironment dataFetchingEnvironment, Method method)
		throws Exception {

		Class<?> clazz = method.getDeclaringClass();

		Object instance = clazz.newInstance();

		GraphQLContext graphQLContext = dataFetchingEnvironment.getContext();

		Optional<HttpServletRequest> httpServletRequestOptional =
			graphQLContext.getHttpServletRequest();

		for (Field field : clazz.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers()) ||
				Modifier.isFinal(field.getModifiers())) {

				continue;
			}

			Class<?> fieldType = field.getType();

			if (fieldType.isAssignableFrom(AcceptLanguage.class)) {
				field.setAccessible(true);

				field.set(
					instance,
					new AcceptLanguageImpl(
						httpServletRequestOptional.orElse(null), _language,
						_portal));
			}
			else if (fieldType.isAssignableFrom(Company.class)) {
				field.setAccessible(true);

				field.set(
					instance,
					_companyLocalService.getCompany(
						CompanyThreadLocal.getCompanyId()));
			}
		}

		Parameter[] parameters = method.getParameters();

		Map<String, Object> arguments = dataFetchingEnvironment.getArguments();

		Object[] args = new Object[arguments.size()];

		for (int i = 0; i < args.length; i++) {
			Parameter parameter = parameters[i];

			String parameterName = null;

			GraphQLName graphQLName = parameter.getAnnotation(
				GraphQLName.class);

			if (graphQLName == null) {
				parameterName = NamingKit.toGraphqlName(parameter.getName());
			}
			else {
				parameterName = NamingKit.toGraphqlName(graphQLName.value());
			}

			args[i] = arguments.get(parameterName);
		}

		return method.invoke(instance, args);
	}

	private Servlet _getServlet() {
		Servlet servlet = _servlet;

		if (servlet != null) {
			return servlet;
		}

		synchronized (_servletDataList) {
			if (_servlet != null) {
				return _servlet;
			}

			ProcessingElementsContainer processingElementsContainer =
				new ProcessingElementsContainer(_defaultTypeFunction);

			GraphQLSchema.Builder schemaBuilder = GraphQLSchema.newSchema();

			GraphQLObjectType.Builder mutationBuilder =
				GraphQLObjectType.newObject();

			mutationBuilder.name("mutation");

			GraphQLObjectType.Builder queryBuilder =
				GraphQLObjectType.newObject();

			queryBuilder.name("query");

			for (ServletData servletData : _servletDataList) {
				_collectObjectFields(
					processingElementsContainer, mutationBuilder,
					servletData.getMutation());

				_collectObjectFields(
					processingElementsContainer, queryBuilder,
					servletData.getQuery());
			}

			schemaBuilder.mutation(mutationBuilder.build());
			schemaBuilder.query(queryBuilder.build());

			SimpleGraphQLHttpServlet.Builder servletBuilder =
				SimpleGraphQLHttpServlet.newBuilder(schemaBuilder.build());

			_servlet = servletBuilder.build();

			return _servlet;
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	private DefaultTypeFunction _defaultTypeFunction;
	private GraphQLFieldRetriever _graphQLFieldRetriever;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private volatile Servlet _servlet;
	private ServiceRegistration<ServletContextHelper>
		_servletContextHelperServiceRegistration;
	private final List<ServletData> _servletDataList = new ArrayList<>();
	private ServiceTracker<ServletData, ServletData> _servletDataServiceTracker;
	private ServiceRegistration<Servlet> _servletServiceRegistration;

	private class LiferayGraphQLFieldRetriever extends GraphQLFieldRetriever {

		@Override
		public GraphQLFieldDefinition getField(
			Method method,
			ProcessingElementsContainer processingElementsContainer) {

			GraphQLFieldDefinition.Builder builder =
				GraphQLFieldDefinition.newFieldDefinition();

			MethodTypeBuilder methodTypeBuilder = new MethodTypeBuilder(
				method, processingElementsContainer.getDefaultTypeFunction(),
				processingElementsContainer, false);

			GraphQLOutputType graphQLOutputType =
				(GraphQLOutputType)methodTypeBuilder.build();

			ArgumentBuilder argumentBuilder = new ArgumentBuilder(
				method, processingElementsContainer.getDefaultTypeFunction(),
				builder, processingElementsContainer, graphQLOutputType);

			builder.argument(argumentBuilder.build());

			builder.dataFetcher(new LiferayMethodDataFetcher(method));

			DeprecateBuilder deprecateBuilder = new DeprecateBuilder(method);

			builder.deprecate(deprecateBuilder.build());

			DescriptionBuilder descriptionBuilder = new DescriptionBuilder(
				method);

			builder.description(descriptionBuilder.build());

			MethodNameBuilder methodNameBuilder = new MethodNameBuilder(method);

			builder.name(methodNameBuilder.build());

			builder.type(graphQLOutputType);

			return new GraphQLFieldDefinitionWrapper(builder.build());
		}

	}

	private class LiferayMethodDataFetcher implements DataFetcher<Object> {

		@Override
		public Object get(DataFetchingEnvironment dataFetchingEnvironment) {
			try {
				return _get(dataFetchingEnvironment, _method);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		private LiferayMethodDataFetcher(Method method) {
			_method = method;
		}

		private final Method _method;

	}

	private class ServletDataServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<ServletData, ServletData> {

		@Override
		public ServletData addingService(
			ServiceReference<ServletData> serviceReference) {

			ServletData servletData = _bundleContext.getService(
				serviceReference);

			synchronized (_servletDataList) {
				_servletDataList.add(servletData);

				_servlet = null;
			}

			return servletData;
		}

		@Override
		public void modifiedService(
			ServiceReference<ServletData> serviceReference,
			ServletData servletData) {
		}

		@Override
		public void removedService(
			ServiceReference<ServletData> serviceReference,
			ServletData servletData) {

			synchronized (_servletDataList) {
				_servletDataList.remove(servletData);

				_servlet = null;
			}

			_bundleContext.ungetService(serviceReference);
		}

	}

}