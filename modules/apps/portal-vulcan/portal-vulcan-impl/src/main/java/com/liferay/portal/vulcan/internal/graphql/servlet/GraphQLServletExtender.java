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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOContributor;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOProperty;
import com.liferay.portal.vulcan.graphql.dto.v1_0.Creator;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.portal.vulcan.internal.configuration.VulcanConfiguration;
import com.liferay.portal.vulcan.internal.configuration.util.ConfigurationUtil;
import com.liferay.portal.vulcan.internal.graphql.data.fetcher.GraphQLDTOContributorDataFetcher;
import com.liferay.portal.vulcan.internal.graphql.data.fetcher.LiferayMethodDataFetcher;
import com.liferay.portal.vulcan.internal.graphql.data.processor.GraphQLDTOContributorDataFetchingProcessor;
import com.liferay.portal.vulcan.internal.graphql.data.processor.LiferayMethodDataFetchingProcessor;
import com.liferay.portal.vulcan.internal.graphql.util.GraphQLUtil;
import com.liferay.portal.vulcan.internal.multipart.MultipartUtil;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.Scalars;
import graphql.TypeResolutionEnvironment;

import graphql.annotations.annotationTypes.GraphQLTypeResolver;
import graphql.annotations.annotationTypes.GraphQLUnion;
import graphql.annotations.processor.ProcessingElementsContainer;
import graphql.annotations.processor.exceptions.CannotCastMemberException;
import graphql.annotations.processor.exceptions.GraphQLAnnotationsException;
import graphql.annotations.processor.graphQLProcessors.GraphQLInputProcessor;
import graphql.annotations.processor.graphQLProcessors.GraphQLOutputProcessor;
import graphql.annotations.processor.retrievers.GraphQLExtensionsHandler;
import graphql.annotations.processor.retrievers.GraphQLFieldRetriever;
import graphql.annotations.processor.retrievers.GraphQLInterfaceRetriever;
import graphql.annotations.processor.retrievers.GraphQLObjectInfoRetriever;
import graphql.annotations.processor.retrievers.GraphQLTypeRetriever;
import graphql.annotations.processor.retrievers.fieldBuilders.ArgumentBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.DeprecateBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.DirectivesBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.field.FieldNameBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.method.MethodNameBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.method.MethodTypeBuilder;
import graphql.annotations.processor.searchAlgorithms.BreadthFirstSearch;
import graphql.annotations.processor.searchAlgorithms.ParentalSearch;
import graphql.annotations.processor.typeBuilders.EnumBuilder;
import graphql.annotations.processor.typeBuilders.InputObjectBuilder;
import graphql.annotations.processor.typeBuilders.InterfaceBuilder;
import graphql.annotations.processor.typeBuilders.OutputObjectBuilder;
import graphql.annotations.processor.typeBuilders.UnionBuilder;
import graphql.annotations.processor.typeFunctions.DefaultTypeFunction;
import graphql.annotations.processor.typeFunctions.TypeFunction;
import graphql.annotations.processor.util.NamingKit;

import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.execution.ExecutionStrategy;

import graphql.kickstart.execution.GraphQLObjectMapper;
import graphql.kickstart.execution.GraphQLQueryInvoker;
import graphql.kickstart.execution.config.DefaultExecutionStrategyProvider;
import graphql.kickstart.execution.config.ExecutionStrategyProvider;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.kickstart.servlet.GraphQLHttpServlet;
import graphql.kickstart.servlet.apollo.ApolloScalars;

import graphql.language.ArrayValue;
import graphql.language.BooleanValue;
import graphql.language.EnumValue;
import graphql.language.FloatValue;
import graphql.language.IntValue;
import graphql.language.NullValue;
import graphql.language.ObjectField;
import graphql.language.ObjectValue;
import graphql.language.StringValue;
import graphql.language.Value;

import graphql.scalars.ExtendedScalars;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingEnvironmentImpl;
import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNamedType;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;
import graphql.schema.GraphQLTypeUtil;
import graphql.schema.GraphQLTypeVisitorStub;
import graphql.schema.PropertyDataFetcher;
import graphql.schema.SchemaTransformer;
import graphql.schema.TypeResolver;

import graphql.util.TraversalControl;
import graphql.util.TraverserContext;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import javax.xml.bind.DatatypeConverter;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
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
					String graphQLName = GraphQLUtil.getGraphQLNameValue(
						objectClass);

					if (graphQLName == null) {
						return NamingKit.toGraphqlName(objectClass.getName());
					}

					return NamingKit.toGraphqlName(graphQLName);
				}

				public Boolean isGraphQLField(AnnotatedElement element) {
					GraphQLField graphQLField = element.getAnnotation(
						GraphQLField.class);

					if (graphQLField == null) {
						return false;
					}

					return graphQLField.value();
				}

			};

		BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(
			graphQLObjectInfoRetriever);
		ParentalSearch parentalSearch = new ParentalSearch(
			graphQLObjectInfoRetriever);

		GraphQLExtensionsHandler graphQLExtensionsHandler =
			new GraphQLExtensionsHandler() {
				{
					setFieldRetriever(_graphQLFieldRetriever);
					setFieldSearchAlgorithm(parentalSearch);
					setGraphQLObjectInfoRetriever(graphQLObjectInfoRetriever);
					setMethodSearchAlgorithm(breadthFirstSearch);
				}
			};

		GraphQLTypeRetriever graphQLTypeRetriever = new GraphQLTypeRetriever() {
			{
				setExtensionsHandler(graphQLExtensionsHandler);
				setFieldSearchAlgorithm(parentalSearch);
				setGraphQLFieldRetriever(_graphQLFieldRetriever);
				setGraphQLInterfaceRetriever(graphQLInterfaceRetriever);
				setGraphQLObjectInfoRetriever(graphQLObjectInfoRetriever);
				setMethodSearchAlgorithm(breadthFirstSearch);
			}

			public GraphQLType getGraphQLType(
					Class<?> clazz,
					ProcessingElementsContainer processingElementsContainer,
					boolean input)
				throws CannotCastMemberException, GraphQLAnnotationsException {

				Map<String, GraphQLType> graphQLTypes =
					processingElementsContainer.getTypeRegistry();

				String typeName = _getTypeName(
					input, processingElementsContainer,
					graphQLObjectInfoRetriever.getTypeName(clazz));

				GraphQLType graphQLType = graphQLTypes.get(typeName);

				String registeredClassNamesKey = clazz.getName() + "_" + input;

				if (_registeredClassNames.containsKey(
						registeredClassNamesKey)) {

					typeName = _registeredClassNames.get(
						registeredClassNamesKey);
				}
				else if (graphQLType != null) {
					String name = clazz.getName();

					name = name.replaceAll("\\.", "_");

					typeName = _getTypeName(
						input, processingElementsContainer,
						StringUtil.replace(name, '$', '_'));
				}

				Stack<String> processingStack =
					processingElementsContainer.getProcessing();

				if (processingStack.contains(typeName)) {
					return new GraphQLTypeReference(typeName);
				}

				graphQLType = graphQLTypes.get(typeName);

				if (graphQLType != null) {
					return graphQLType;
				}

				processingStack.push(typeName);

				_registeredClassNames.put(registeredClassNamesKey, typeName);

				if (clazz.getAnnotation(GraphQLUnion.class) != null) {
					graphQLType = new UnionBuilder(
						graphQLObjectInfoRetriever
					).getUnionBuilder(
						clazz, processingElementsContainer
					).build();
				}
				else if (clazz.isAnnotationPresent(GraphQLTypeResolver.class)) {
					graphQLType = new InterfaceBuilder(
						graphQLObjectInfoRetriever, _graphQLFieldRetriever,
						graphQLExtensionsHandler
					).getInterfaceBuilder(
						clazz, processingElementsContainer
					).build();
				}
				else if (Enum.class.isAssignableFrom(clazz)) {
					graphQLType = new EnumBuilder(
						graphQLObjectInfoRetriever
					).getEnumBuilder(
						clazz
					).build();
				}
				else {
					if (input) {
						graphQLType = new InputObjectBuilder(
							graphQLObjectInfoRetriever, parentalSearch,
							breadthFirstSearch, _graphQLFieldRetriever
						).getInputObjectBuilder(
							clazz, processingElementsContainer
						).build();
					}
					else {
						GraphQLObjectType.Builder outputObjectBuilder =
							new OutputObjectBuilder(
								graphQLObjectInfoRetriever, parentalSearch,
								breadthFirstSearch, _graphQLFieldRetriever,
								graphQLInterfaceRetriever,
								graphQLExtensionsHandler
							).getOutputObjectBuilder(
								clazz, processingElementsContainer
							);

						GraphQLName graphQLName = clazz.getAnnotation(
							GraphQLName.class);

						if (graphQLName != null) {
							outputObjectBuilder.description(
								graphQLName.description());
						}

						graphQLType = outputObjectBuilder.build();
					}
				}

				if (!StringUtil.equals(
						GraphQLTypeUtil.simplePrint(graphQLType), typeName)) {

					if (!_equals(
							graphQLTypes.get(
								GraphQLTypeUtil.simplePrint(graphQLType)),
							graphQLType)) {

						try {
							Class<? extends GraphQLType> graphQLTypeClass =
								graphQLType.getClass();

							Field field = graphQLTypeClass.getDeclaredField(
								"name");

							field.setAccessible(true);

							field.set(graphQLType, typeName);
						}
						catch (Exception exception) {
							if (_log.isDebugEnabled()) {
								_log.debug(exception);
							}
						}
					}
					else {
						graphQLType = graphQLTypes.get(
							GraphQLTypeUtil.simplePrint(graphQLType));
					}
				}

				graphQLTypes.put(
					GraphQLTypeUtil.simplePrint(graphQLType), graphQLType);

				processingStack.pop();

				return graphQLType;
			}

			private boolean _equals(
				GraphQLSchemaElement graphQLSchemaElement1,
				GraphQLSchemaElement graphQLSchemaElement2) {

				List<GraphQLSchemaElement> childrenGraphQLSchemaElement1 =
					graphQLSchemaElement1.getChildren();
				List<GraphQLSchemaElement> childrenGraphQLSchemaElement2 =
					graphQLSchemaElement2.getChildren();

				for (GraphQLSchemaElement childGraphQLSchemaElement1 :
						childrenGraphQLSchemaElement1) {

					boolean found = false;

					for (GraphQLSchemaElement childGraphQLSchemaElement2 :
							childrenGraphQLSchemaElement2) {

						if (StringUtil.equals(
								GraphQLTypeUtil.simplePrint(
									childGraphQLSchemaElement1),
								GraphQLTypeUtil.simplePrint(
									childGraphQLSchemaElement2)) &&
							_equals(
								childGraphQLSchemaElement1,
								childGraphQLSchemaElement2)) {

							found = true;

							break;
						}
					}

					if (!found) {
						return false;
					}
				}

				if (childrenGraphQLSchemaElement1.size() ==
						childrenGraphQLSchemaElement2.size()) {

					return true;
				}

				return false;
			}

			private String _getTypeName(
				boolean input,
				ProcessingElementsContainer processingElementsContainer,
				String typeName) {

				if (input) {
					typeName =
						processingElementsContainer.getInputPrefix() +
							typeName +
								processingElementsContainer.getInputSuffix();
				}

				return typeName;
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
			}) {

			@Override
			public GraphQLType buildType(
				boolean input, Class<?> clazz, AnnotatedType annotatedType,
				ProcessingElementsContainer processingElementsContainer) {

				GraphQLType graphQLType = super.buildType(
					input, clazz, annotatedType, processingElementsContainer);

				if ((annotatedType != null) &&
					(annotatedType.isAnnotationPresent(NotEmpty.class) ||
					 annotatedType.isAnnotationPresent(NotNull.class))) {

					graphQLType = new GraphQLNonNull(graphQLType);
				}

				return graphQLType;
			}

		};

		_defaultTypeFunction.register(new DateTypeFunction());
		_defaultTypeFunction.register(new MapTypeFunction());
		_defaultTypeFunction.register(new ObjectTypeFunction());

		_graphQLDTOContributorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, GraphQLDTOContributor.class, "dto.name",
				new ServiceTrackerMapListener
					<String, GraphQLDTOContributor, GraphQLDTOContributor>() {

					@Override
					public void keyEmitted(
						ServiceTrackerMap<String, GraphQLDTOContributor>
							serviceTrackerMap,
						String key,
						GraphQLDTOContributor serviceGraphQLDTOContributor,
						GraphQLDTOContributor contentGraphQLDTOContributor) {

						_servlets.clear();
					}

					@Override
					public void keyRemoved(
						ServiceTrackerMap<String, GraphQLDTOContributor>
							serviceTrackerMap,
						String key,
						GraphQLDTOContributor serviceGraphQLDTOContributor,
						GraphQLDTOContributor contentGraphQLDTOContributor) {

						_servlets.clear();
					}

				});
		_servletContextHelperServiceRegistration =
			bundleContext.registerService(
				ServletContextHelper.class,
				new ServletContextHelper(bundleContext.getBundle()) {
				},
				HashMapDictionaryBuilder.<String, Object>put(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
					"GraphQL"
				).put(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
					"/graphql"
				).put(
					HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET,
					"GraphQL"
				).build());

		_servletDataServiceTracker = new ServiceTracker<>(
			bundleContext, ServletData.class,
			new ServletDataServiceTrackerCustomizer());

		_servletDataServiceTracker.open();

		_servletServiceRegistration = _bundleContext.registerService(
			Servlet.class,
			(Servlet)ProxyUtil.newProxyInstance(
				GraphQLServletExtender.class.getClassLoader(),
				new Class<?>[] {Servlet.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] arguments)
						throws Throwable {

						String methodName = method.getName();

						if (methodName.equals("destroy")) {
							return null;
						}

						if (methodName.equals("getServletConfig")) {
							return _servletConfig;
						}

						if (methodName.equals("getServletInfo")) {
							return StringPool.BLANK;
						}

						if (methodName.equals("hashCode")) {
							return hashCode();
						}

						if (methodName.equals("init") &&
							(arguments.length > 0)) {

							_servletConfig = (ServletConfig)arguments[0];

							return null;
						}

						HttpServletRequest httpServletRequest =
							(HttpServletRequest)arguments[0];

						arguments[0] = new HttpServletRequestWrapper(
							httpServletRequest) {

							@Override
							public boolean isAsyncSupported() {
								return false;
							}

						};

						Servlet servlet = _createServlet(
							_portal.getCompanyId(
								(HttpServletRequest)arguments[0]));

						servlet.init(_servletConfig);

						try {
							return method.invoke(servlet, arguments);
						}
						catch (InvocationTargetException
									invocationTargetException) {

							throw invocationTargetException.getCause();
						}
					}

					private ServletConfig _servletConfig;

				}),
			HashMapDictionaryBuilder.<String, Object>put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				"GraphQL"
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "GraphQL"
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*"
			).build());
	}

	@Deactivate
	protected void deactivate() {
		_servletDataServiceTracker.close();

		_servletServiceRegistration.unregister();

		_servletContextHelperServiceRegistration.unregister();
	}

	private static GraphQLFieldDefinition _addField(
		GraphQLOutputType graphQLOutputType, String name,
		GraphQLArgument... graphQLArguments) {

		GraphQLFieldDefinition.Builder graphQLFieldDefinitionBuilder =
			GraphQLFieldDefinition.newFieldDefinition();

		if (graphQLArguments != null) {
			for (GraphQLArgument graphQLArgument : graphQLArguments) {
				graphQLFieldDefinitionBuilder.argument(graphQLArgument);
			}
		}

		return graphQLFieldDefinitionBuilder.name(
			name
		).type(
			graphQLOutputType
		).build();
	}

	private GraphQLArgument _addGraphQLArgument(
		GraphQLInputType graphQLInputType, String name) {

		return _addGraphQLArgument(null, graphQLInputType, name);
	}

	private GraphQLArgument _addGraphQLArgument(
		Object defaultValue, GraphQLInputType graphQLInputType, String name) {

		GraphQLArgument.Builder graphQLArgumentBuilder =
			GraphQLArgument.newArgument();

		if (defaultValue != null) {
			graphQLArgumentBuilder.defaultValue(defaultValue);
		}

		graphQLArgumentBuilder.name(
			name
		).type(
			graphQLInputType
		);

		return graphQLArgumentBuilder.build();
	}

	private GraphQLInputObjectField _addInputField(
		GraphQLInputType type, String name) {

		GraphQLInputObjectField.Builder graphQLInputObjectFieldBuilder =
			GraphQLInputObjectField.newInputObjectField();

		return graphQLInputObjectFieldBuilder.name(
			name
		).type(
			type
		).build();
	}

	private void _collectObjectFields(
		Function<ServletData, Object> function,
		GraphQLObjectType.Builder graphQLObjectTypeBuilder,
		ProcessingElementsContainer processingElementsContainer,
		List<ServletData> servletDatas) {

		Stream<ServletData> stream = servletDatas.stream();

		Map<String, Optional<Method>> methods = stream.filter(
			servletData -> servletData.getGraphQLNamespace() == null
		).flatMap(
			servletData -> Stream.of(
				function.apply(servletData)
			).filter(
				Objects::nonNull
			).map(
				Object::getClass
			).map(
				Class::getMethods
			).flatMap(
				Arrays::stream
			).filter(
				method -> _isMethodEnabled(method, servletData.getPath())
			)
		).collect(
			Collectors.groupingBy(
				Method::getName,
				Collectors.maxBy(Comparator.comparingInt(this::_getVersion)))
		);

		for (Optional<Method> methodOptional : methods.values()) {
			if (methodOptional.isPresent()) {
				Method method = methodOptional.get();

				Class<?> clazz = method.getDeclaringClass();

				graphQLObjectTypeBuilder.field(
					_graphQLFieldRetriever.getField(
						clazz.getSimpleName(), method,
						processingElementsContainer));
			}
		}
	}

	private GraphQLFieldDefinition _createNodeGraphQLFieldDefinition(
		GraphQLOutputType graphQLOutputType) {

		return _addField(
			graphQLOutputType, "graphQLNode",
			_addGraphQLArgument(Scalars.GraphQLString, "dataType"),
			_addGraphQLArgument(ExtendedScalars.GraphQLLong, "id"));
	}

	private GraphQLInterfaceType _createNodeGraphQLInterfaceType() {
		GraphQLInterfaceType.Builder interfaceBuilder =
			GraphQLInterfaceType.newInterface();

		interfaceBuilder.field(_addField(ExtendedScalars.GraphQLLong, "id"));
		interfaceBuilder.name("GraphQLNode");

		return interfaceBuilder.build();
	}

	private Servlet _createServlet(long companyId) throws Exception {
		Servlet servlet = _servlets.get(companyId);

		if (servlet != null) {
			return servlet;
		}

		synchronized (_servletDataList) {
			if (_servlets.containsKey(companyId)) {
				return _servlets.get(companyId);
			}

			PropertyDataFetcher.clearReflectionCache();

			_registeredClassNames.clear();

			GraphQLObjectType.Builder mutationGraphQLObjectTypeBuilder =
				GraphQLObjectType.newObject();

			mutationGraphQLObjectTypeBuilder.name("mutation");

			ProcessingElementsContainer processingElementsContainer =
				new ProcessingElementsContainer(_defaultTypeFunction);

			Map<Class<?>, Set<Class<?>>> classesMap =
				processingElementsContainer.getExtensionsTypeRegistry();

			List<ServletData> servletDatas = new ArrayList<>();

			for (ServletData servletData : _servletDataList) {
				if (_isGraphQLEnabled(servletData.getPath())) {
					servletDatas.add(servletData);
				}

				Object query = servletData.getQuery();

				Class<?> queryClass = query.getClass();

				for (Class<?> innerClasses : queryClass.getClasses()) {
					if (innerClasses.isAnnotationPresent(
							GraphQLTypeExtension.class)) {

						GraphQLTypeExtension graphQLTypeExtension =
							innerClasses.getAnnotation(
								GraphQLTypeExtension.class);

						Class<?> clazz = graphQLTypeExtension.value();

						if (!classesMap.containsKey(clazz)) {
							classesMap.put(clazz, new HashSet<>());
						}

						Set<Class<?>> classes = classesMap.get(clazz);

						classes.add(innerClasses);
					}
				}
			}

			_collectObjectFields(
				ServletData::getMutation, mutationGraphQLObjectTypeBuilder,
				processingElementsContainer, servletDatas);

			GraphQLObjectType.Builder queryGraphQLObjectTypeBuilder =
				GraphQLObjectType.newObject();

			queryGraphQLObjectTypeBuilder.name("query");

			_collectObjectFields(
				ServletData::getQuery, queryGraphQLObjectTypeBuilder,
				processingElementsContainer, servletDatas);

			GraphQLSchema.Builder graphQLSchemaBuilder =
				GraphQLSchema.newSchema();

			_registerCustomTypes(processingElementsContainer);
			_registerGraphQLDTOContributors(
				companyId, graphQLSchemaBuilder, processingElementsContainer,
				mutationGraphQLObjectTypeBuilder,
				queryGraphQLObjectTypeBuilder);

			GraphQLInterfaceType graphQLInterfaceType = _registerInterfaces(
				graphQLSchemaBuilder, processingElementsContainer,
				queryGraphQLObjectTypeBuilder);

			_registerNamespace(
				ServletData::getMutation, mutationGraphQLObjectTypeBuilder,
				graphQLSchemaBuilder, true, processingElementsContainer,
				servletDatas);
			_registerNamespace(
				ServletData::getQuery, queryGraphQLObjectTypeBuilder,
				graphQLSchemaBuilder, false, processingElementsContainer,
				servletDatas);

			graphQLSchemaBuilder.mutation(
				mutationGraphQLObjectTypeBuilder.build());
			graphQLSchemaBuilder.query(queryGraphQLObjectTypeBuilder.build());

			GraphQLConfiguration.Builder graphQLConfigurationBuilder =
				GraphQLConfiguration.with(
					SchemaTransformer.transformSchema(
						graphQLSchemaBuilder.build(),
						new LiferayGraphQLTypeVisitor(graphQLInterfaceType)));

			ExecutionStrategy executionStrategy = new AsyncExecutionStrategy(
				new SanitizedDataFetcherExceptionHandler());

			ExecutionStrategyProvider executionStrategyProvider =
				new DefaultExecutionStrategyProvider(executionStrategy);

			GraphQLQueryInvoker graphQLQueryInvoker =
				GraphQLQueryInvoker.newBuilder(
				).withExecutionStrategyProvider(
					executionStrategyProvider
				).build();

			graphQLConfigurationBuilder.with(graphQLQueryInvoker);

			GraphQLObjectMapper.Builder objectMapperBuilder =
				GraphQLObjectMapper.newBuilder();

			objectMapperBuilder.withGraphQLErrorHandler(
				new LiferayGraphQLErrorHandler());
			objectMapperBuilder.withObjectMapperProvider(
				() -> {
					ObjectMapper objectMapper = new ObjectMapper();

					objectMapper.setFilterProvider(
						new SimpleFilterProvider() {
							{
								addFilter(
									"Liferay.Vulcan",
									SimpleBeanPropertyFilter.serializeAll());
							}
						});

					return objectMapper;
				});

			graphQLConfigurationBuilder.with(objectMapperBuilder.build());

			servlet = GraphQLHttpServlet.with(
				graphQLConfigurationBuilder.build());

			_servlets.put(companyId, servlet);

			return servlet;
		}
	}

	private GraphQLInputObjectType _getGraphQLInputObjectType(
		GraphQLDTOContributor<?, ?> graphQLDTOContributor,
		Map<String, GraphQLType> graphQLTypes) {

		GraphQLInputObjectType.Builder graphQLInputObjectTypeBuilder =
			new GraphQLInputObjectType.Builder();

		graphQLInputObjectTypeBuilder.name(
			"Input" + graphQLDTOContributor.getTypeName());

		for (GraphQLDTOProperty graphQLDTOProperty :
				graphQLDTOContributor.getGraphQLDTOProperties()) {

			if (graphQLDTOProperty.isReadOnly()) {
				continue;
			}

			GraphQLInputType graphQLInputType =
				(GraphQLInputType)_toGraphQLType(
					graphQLDTOProperty.getTypeClass(), graphQLTypes, true);

			graphQLInputObjectTypeBuilder.field(
				_addInputField(graphQLInputType, graphQLDTOProperty.getName()));
		}

		return graphQLInputObjectTypeBuilder.build();
	}

	private GraphQLObjectType _getGraphQLObjectType(
		GraphQLCodeRegistry.Builder graphQLCodeRegistryBuilder,
		GraphQLDTOContributor<?, ?> graphQLDTOContributor,
		GraphQLSchema.Builder graphQLSchemaBuilder,
		Map<String, GraphQLType> graphQLTypes) {

		GraphQLObjectType.Builder graphQLObjectTypeBuilder =
			new GraphQLObjectType.Builder();

		graphQLObjectTypeBuilder.field(
			_addField(
				ExtendedScalars.GraphQLLong,
				graphQLDTOContributor.getIdName()));
		graphQLObjectTypeBuilder.name(graphQLDTOContributor.getTypeName());

		for (GraphQLDTOProperty graphQLDTOProperty :
				graphQLDTOContributor.getGraphQLDTOProperties()) {

			GraphQLOutputType graphQLOutputType =
				(GraphQLOutputType)_toGraphQLType(
					graphQLDTOProperty.getTypeClass(), graphQLTypes, false);

			graphQLObjectTypeBuilder.field(
				_addField(graphQLOutputType, graphQLDTOProperty.getName()));
		}

		for (GraphQLDTOProperty relationshipGraphQLDTOProperty :
				graphQLDTOContributor.getRelationshipGraphQLDTOProperties()) {

			graphQLObjectTypeBuilder.field(
				_addField(
					(GraphQLOutputType)_toGraphQLType(
						relationshipGraphQLDTOProperty.getTypeClass(),
						graphQLTypes, false),
					relationshipGraphQLDTOProperty.getName()));

			graphQLSchemaBuilder.codeRegistry(
				graphQLCodeRegistryBuilder.dataFetcher(
					FieldCoordinates.coordinates(
						graphQLDTOContributor.getTypeName(),
						relationshipGraphQLDTOProperty.getName()),
					new GraphQLDTOContributorDataFetcher(
						graphQLDTOContributor,
						_graphQLDTOContributorDataFetchingProcessor,
						relationshipGraphQLDTOProperty,
						GraphQLDTOContributorDataFetcher.Operation.
							GET_RELATIONSHIP)
				).build());
		}

		return graphQLObjectTypeBuilder.build();
	}

	private GraphQLObjectType _getPageGraphQLObjectType(
		GraphQLType facetGraphQLType, GraphQLType objectGraphQLType,
		String name) {

		GraphQLObjectType.Builder graphQLObjectTypeBuilder =
			new GraphQLObjectType.Builder();

		graphQLObjectTypeBuilder.field(
			_addField(_mapGraphQLScalarType, "actions"));
		graphQLObjectTypeBuilder.field(
			_addField(GraphQLList.list(facetGraphQLType), "facets"));
		graphQLObjectTypeBuilder.field(
			_addField(GraphQLList.list(objectGraphQLType), "items"));
		graphQLObjectTypeBuilder.field(
			_addField(ExtendedScalars.GraphQLLong, "lastPage"));
		graphQLObjectTypeBuilder.field(
			_addField(ExtendedScalars.GraphQLLong, "page"));
		graphQLObjectTypeBuilder.field(
			_addField(ExtendedScalars.GraphQLLong, "pageSize"));
		graphQLObjectTypeBuilder.field(
			_addField(ExtendedScalars.GraphQLLong, "totalCount"));
		graphQLObjectTypeBuilder.name(name + "Page");

		return graphQLObjectTypeBuilder.build();
	}

	private Integer _getVersion(Method method) {
		Class<?> clazz = method.getDeclaringClass();

		String packageString = String.valueOf(clazz.getPackage());

		String[] packageNames = packageString.split("\\.");

		String version = packageNames[packageNames.length - 1];

		return Integer.valueOf(version.replaceAll("\\D", ""));
	}

	private boolean _isGraphQLEnabled(String path) throws Exception {
		String filterString = String.format(
			"(&(path=%s)(service.factoryPid=%s))",
			path.substring(0, path.indexOf("-graphql")),
			VulcanConfiguration.class.getName());

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (!ArrayUtil.isEmpty(configurations)) {
			Dictionary<String, Object> dictionary =
				configurations[0].getProperties();

			return (Boolean)dictionary.get("graphQLEnabled");
		}

		return true;
	}

	private boolean _isMethodEnabled(Method method, String path) {
		Set<String> excludedOperationIds =
			ConfigurationUtil.getExcludedOperationIds(
				_configurationAdmin,
				path.substring(0, path.indexOf("-graphql")));

		if (excludedOperationIds.contains(method.getName())) {
			return false;
		}

		return GraphQLUtil.isGraphQLFieldValue(method);
	}

	private void _registerCustomTypes(
		ProcessingElementsContainer processingElementsContainer) {

		Map<String, GraphQLType> graphQLTypes =
			processingElementsContainer.getTypeRegistry();

		GraphQLObjectType.Builder graphQLObjectTypeBuilder =
			new GraphQLObjectType.Builder();
		String graphQLTypeName = StringUtil.replace(
			Creator.class.getName(), '.', '_');

		graphQLTypes.put(
			graphQLTypeName,
			graphQLObjectTypeBuilder.name(
				graphQLTypeName
			).field(
				_addField(Scalars.GraphQLString, "additionalName")
			).field(
				_addField(Scalars.GraphQLString, "contentType")
			).field(
				_addField(Scalars.GraphQLString, "familyName")
			).field(
				_addField(ExtendedScalars.GraphQLLong, "id")
			).field(
				_addField(Scalars.GraphQLString, "image")
			).field(
				_addField(Scalars.GraphQLString, "name")
			).field(
				_addField(Scalars.GraphQLString, "profileURL")
			).build());

		graphQLObjectTypeBuilder = new GraphQLObjectType.Builder();

		graphQLTypes.put(
			"FileEntry",
			graphQLObjectTypeBuilder.name(
				"FileEntry"
			).field(
				_addField(ExtendedScalars.GraphQLLong, "id")
			).field(
				_addField(Scalars.GraphQLString, "name")
			).build());

		GraphQLInputObjectType.Builder graphQLInputObjectTypeBuilder =
			new GraphQLInputObjectType.Builder();

		graphQLTypes.put(
			"InputListEntry",
			graphQLInputObjectTypeBuilder.name(
				"InputListEntry"
			).field(
				_addInputField(Scalars.GraphQLString, "key")
			).field(
				_addInputField(Scalars.GraphQLString, "name")
			).field(
				_addInputField(_mapGraphQLScalarType, "name_i18n")
			).build());

		graphQLObjectTypeBuilder = new GraphQLObjectType.Builder();

		graphQLTypes.put(
			"ListEntry",
			graphQLObjectTypeBuilder.name(
				"ListEntry"
			).field(
				_addField(Scalars.GraphQLString, "key")
			).field(
				_addField(Scalars.GraphQLString, "name")
			).field(
				_addField(_mapGraphQLScalarType, "name_i18n")
			).build());
	}

	private void _registerGraphQLDTOContributor(
		GraphQLDTOContributor graphQLDTOContributor,
		GraphQLSchema.Builder graphQLSchemaBuilder,
		GraphQLObjectType.Builder mutationGraphQLObjectTypeBuilder,
		String mutationNamespace, String namespace,
		ProcessingElementsContainer processingElementsContainer,
		GraphQLObjectType.Builder queryGraphQLObjectTypeBuilder) {

		// Create

		GraphQLCodeRegistry.Builder graphQLCodeRegistryBuilder =
			processingElementsContainer.getCodeRegistryBuilder();

		Map<String, GraphQLType> graphQLTypes =
			processingElementsContainer.getTypeRegistry();

		GraphQLObjectType graphQLObjectType = _getGraphQLObjectType(
			graphQLCodeRegistryBuilder, graphQLDTOContributor,
			graphQLSchemaBuilder, graphQLTypes);

		String resourceName = graphQLDTOContributor.getResourceName();

		String createName = "create" + resourceName;

		List<GraphQLArgument> graphQLArguments = new ArrayList<>();

		GraphQLInputObjectType graphQLInputType = _getGraphQLInputObjectType(
			graphQLDTOContributor, graphQLTypes);

		graphQLArguments.add(
			_addGraphQLArgument(graphQLInputType, resourceName));

		if (graphQLDTOContributor.hasScope()) {
			graphQLArguments.add(
				_addGraphQLArgument(Scalars.GraphQLString, "scopeKey"));
		}

		mutationGraphQLObjectTypeBuilder.field(
			_addField(
				graphQLObjectType, createName,
				graphQLArguments.toArray(new GraphQLArgument[0])));

		graphQLSchemaBuilder.codeRegistry(
			graphQLCodeRegistryBuilder.dataFetcher(
				FieldCoordinates.coordinates(mutationNamespace, createName),
				new GraphQLDTOContributorDataFetcher(
					graphQLDTOContributor,
					_graphQLDTOContributorDataFetchingProcessor,
					GraphQLDTOContributorDataFetcher.Operation.CREATE)
			).build());

		// Delete

		String deleteName = "delete" + resourceName;

		String idName = graphQLDTOContributor.getIdName();

		mutationGraphQLObjectTypeBuilder.field(
			_addField(
				Scalars.GraphQLBoolean, deleteName,
				_addGraphQLArgument(ExtendedScalars.GraphQLLong, idName)));

		graphQLSchemaBuilder.codeRegistry(
			graphQLCodeRegistryBuilder.dataFetcher(
				FieldCoordinates.coordinates(mutationNamespace, deleteName),
				new GraphQLDTOContributorDataFetcher(
					graphQLDTOContributor,
					_graphQLDTOContributorDataFetchingProcessor,
					GraphQLDTOContributorDataFetcher.Operation.DELETE)
			).build());

		// Get

		String getName = StringUtil.lowerCaseFirstLetter(resourceName);

		queryGraphQLObjectTypeBuilder.field(
			_addField(
				graphQLObjectType, getName,
				_addGraphQLArgument(ExtendedScalars.GraphQLLong, idName)));
		graphQLSchemaBuilder.codeRegistry(
			graphQLCodeRegistryBuilder.dataFetcher(
				FieldCoordinates.coordinates(namespace, getName),
				new GraphQLDTOContributorDataFetcher(
					graphQLDTOContributor,
					_graphQLDTOContributorDataFetchingProcessor,
					GraphQLDTOContributorDataFetcher.Operation.GET)
			).build());

		// List

		String listName = StringUtil.lowerCaseFirstLetter(
			TextFormatter.formatPlural(resourceName));

		graphQLArguments = ListUtil.fromArray(
			_addGraphQLArgument(
				GraphQLList.list(Scalars.GraphQLString), "aggregation"),
			_addGraphQLArgument(Scalars.GraphQLString, "filter"),
			_addGraphQLArgument(1, Scalars.GraphQLInt, "page"),
			_addGraphQLArgument(20, Scalars.GraphQLInt, "pageSize"),
			_addGraphQLArgument(Scalars.GraphQLString, "search"),
			_addGraphQLArgument(Scalars.GraphQLString, "sort"));

		if (graphQLDTOContributor.hasScope()) {
			graphQLArguments.add(
				_addGraphQLArgument(Scalars.GraphQLString, "scopeKey"));
		}

		queryGraphQLObjectTypeBuilder.field(
			_addField(
				_getPageGraphQLObjectType(
					graphQLTypes.get("Facet"), graphQLObjectType,
					graphQLDTOContributor.getTypeName()),
				listName, graphQLArguments.toArray(new GraphQLArgument[0])));

		graphQLSchemaBuilder.codeRegistry(
			graphQLCodeRegistryBuilder.dataFetcher(
				FieldCoordinates.coordinates(namespace, listName),
				new GraphQLDTOContributorDataFetcher(
					graphQLDTOContributor,
					_graphQLDTOContributorDataFetchingProcessor,
					GraphQLDTOContributorDataFetcher.Operation.LIST)
			).build());

		// Update

		String updateName = "update" + resourceName;

		mutationGraphQLObjectTypeBuilder.field(
			_addField(
				graphQLObjectType, updateName,
				_addGraphQLArgument(graphQLInputType, resourceName),
				_addGraphQLArgument(ExtendedScalars.GraphQLLong, idName)));

		graphQLSchemaBuilder.codeRegistry(
			graphQLCodeRegistryBuilder.dataFetcher(
				FieldCoordinates.coordinates(mutationNamespace, updateName),
				new GraphQLDTOContributorDataFetcher(
					graphQLDTOContributor,
					_graphQLDTOContributorDataFetchingProcessor,
					GraphQLDTOContributorDataFetcher.Operation.UPDATE)
			).build());
	}

	private void _registerGraphQLDTOContributors(
		long companyId, GraphQLSchema.Builder graphQLSchemaBuilder,
		ProcessingElementsContainer processingElementsContainer,
		GraphQLObjectType.Builder rootMutationGraphQLObjectTypeBuilder,
		GraphQLObjectType.Builder rootQueryGraphQLObjectTypeBuilder) {

		Collection<GraphQLDTOContributor> graphQLDTOContributors =
			_graphQLDTOContributorServiceTrackerMap.values();

		if (graphQLDTOContributors.isEmpty()) {
			return;
		}

		String namespace = "c";

		GraphQLObjectType.Builder queryGraphQLObjectTypeBuilder =
			new GraphQLObjectType.Builder();

		queryGraphQLObjectTypeBuilder.name(namespace);

		GraphQLObjectType.Builder mutationGraphQLObjectTypeBuilder =
			new GraphQLObjectType.Builder();

		mutationGraphQLObjectTypeBuilder.name("Mutation" + namespace);

		for (GraphQLDTOContributor graphQLDTOContributor :
				graphQLDTOContributors) {

			if (companyId != graphQLDTOContributor.getCompanyId()) {
				continue;
			}

			_registerGraphQLDTOContributor(
				graphQLDTOContributor, graphQLSchemaBuilder,
				mutationGraphQLObjectTypeBuilder, "Mutation" + namespace,
				namespace, processingElementsContainer,
				queryGraphQLObjectTypeBuilder);
		}

		rootQueryGraphQLObjectTypeBuilder.field(
			_addField(queryGraphQLObjectTypeBuilder.build(), namespace));
		rootMutationGraphQLObjectTypeBuilder.field(
			_addField(mutationGraphQLObjectTypeBuilder.build(), namespace));

		GraphQLCodeRegistry.Builder graphQLCodeRegistryBuilder =
			processingElementsContainer.getCodeRegistryBuilder();

		graphQLSchemaBuilder.codeRegistry(
			graphQLCodeRegistryBuilder.dataFetcher(
				FieldCoordinates.coordinates("query", namespace),
				(DataFetcher<Object>)dataFetchingEnvironment -> new Object()
			).build());
		graphQLSchemaBuilder.codeRegistry(
			graphQLCodeRegistryBuilder.dataFetcher(
				FieldCoordinates.coordinates("mutation", namespace),
				(DataFetcher<Object>)dataFetchingEnvironment -> new Object()
			).build());
	}

	private GraphQLInterfaceType _registerInterfaces(
		GraphQLSchema.Builder graphQLSchemaBuilder,
		ProcessingElementsContainer processingElementsContainer,
		GraphQLObjectType.Builder queryGraphQLObjectTypeBuilder) {

		try {
			Map<String, GraphQLType> graphQLTypes =
				processingElementsContainer.getTypeRegistry();

			GraphQLInterfaceType graphQLInterfaceType =
				_createNodeGraphQLInterfaceType();

			graphQLTypes.put("GraphQLNode", graphQLInterfaceType);

			queryGraphQLObjectTypeBuilder.field(
				_createNodeGraphQLFieldDefinition(graphQLInterfaceType));

			GraphQLCodeRegistry.Builder graphQLCodeRegistryBuilder =
				processingElementsContainer.getCodeRegistryBuilder();

			graphQLSchemaBuilder.codeRegistry(
				graphQLCodeRegistryBuilder.dataFetcher(
					FieldCoordinates.coordinates("query", "graphQLNode"),
					new NodeDataFetcher()
				).typeResolver(
					"GraphQLNode", new GraphQLNodeTypeResolver()
				).build());

			return graphQLInterfaceType;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void _registerNamespace(
		Function<ServletData, Object> function,
		GraphQLObjectType.Builder graphQLObjectTypeBuilder,
		GraphQLSchema.Builder graphQLSchemaBuilder, boolean mutation,
		ProcessingElementsContainer processingElementsContainer,
		List<ServletData> servletDatas) {

		for (ServletData servletData : servletDatas) {
			String graphQLNamespace = servletData.getGraphQLNamespace();

			if (graphQLNamespace == null) {
				continue;
			}

			Object query = function.apply(servletData);

			Class<?> clazz = query.getClass();

			List<Method> methods = TransformUtil.transformToList(
				clazz.getMethods(),
				method -> {
					if (_isMethodEnabled(method, servletData.getPath())) {
						return method;
					}

					return null;
				});

			if (ListUtil.isEmpty(methods)) {
				continue;
			}

			GraphQLObjectType.Builder builder = new GraphQLObjectType.Builder();

			String prefix = "";

			if (mutation) {
				prefix = "Mutation";
			}

			builder.name(
				prefix + StringUtil.upperCaseFirstLetter(graphQLNamespace));

			GraphQLCodeRegistry.Builder graphQLCodeRegistryBuilder =
				processingElementsContainer.getCodeRegistryBuilder();

			for (Method method : methods) {
				builder.field(
					_graphQLFieldRetriever.getField(
						clazz.getSimpleName(), method,
						processingElementsContainer));

				graphQLSchemaBuilder.codeRegistry(
					graphQLCodeRegistryBuilder.dataFetcher(
						FieldCoordinates.coordinates(
							graphQLNamespace, method.getName()),
						new LiferayMethodDataFetcher(
							_liferayMethodDataFetchingProcessor, method)
					).build());
			}

			graphQLObjectTypeBuilder.field(
				_addField(builder.build(), graphQLNamespace));

			String parentField = "query";

			if (mutation) {
				parentField = "mutation";
			}

			graphQLSchemaBuilder.codeRegistry(
				graphQLCodeRegistryBuilder.dataFetcher(
					FieldCoordinates.coordinates(parentField, graphQLNamespace),
					(DataFetcher<Object>)dataFetchingEnvironment -> new Object()
				).build());
		}
	}

	private void _replaceFieldDefinition(
		GraphQLInterfaceType graphQLInterfaceType,
		GraphQLObjectType graphQLObjectType,
		GraphQLObjectType.Builder graphQLObjectTypeBuilder) {

		for (GraphQLFieldDefinition graphQLFieldDefinition :
				graphQLObjectType.getFieldDefinitions()) {

			GraphQLOutputType graphQLOutputType =
				graphQLFieldDefinition.getType();

			String typeName = GraphQLTypeUtil.simplePrint(graphQLOutputType);

			if ((typeName != null) && typeName.equals("Object") &&
				StringUtil.endsWith(graphQLFieldDefinition.getName(), "Id")) {

				GraphQLFieldDefinition.Builder
					newGraphQLFieldDefinitionBuilder =
						GraphQLFieldDefinition.newFieldDefinition(
							graphQLFieldDefinition);

				graphQLObjectTypeBuilder.field(
					newGraphQLFieldDefinitionBuilder.type(
						graphQLInterfaceType
					).build());
			}
		}
	}

	private void _replaceFieldNodes(
		GraphQLCodeRegistry.Builder graphQLCodeRegistryBuilder,
		GraphQLInterfaceType graphQLInterfaceType,
		GraphQLObjectType graphQLObjectType,
		GraphQLObjectType.Builder graphQLObjectTypeBuilder) {

		GraphQLFieldDefinition graphQLFieldDefinition =
			graphQLObjectType.getFieldDefinition("contentType");

		if (graphQLFieldDefinition == null) {
			return;
		}

		GraphQLFieldDefinition.Builder graphQLFieldDefinitionBuilder =
			GraphQLFieldDefinition.newFieldDefinition(
			).name(
				"graphQLNode"
			).type(
				graphQLInterfaceType
			);

		graphQLObjectTypeBuilder.field(graphQLFieldDefinitionBuilder.build());

		graphQLCodeRegistryBuilder.dataFetcher(
			FieldCoordinates.coordinates(
				graphQLObjectType.getName(), "graphQLNode"),
			new GraphQLNodePropertyDataFetcher()
		).typeResolver(
			"GraphQLNode", new GraphQLNodeTypeResolver()
		).build();
	}

	private GraphQLType _toGraphQLType(
		Class<?> clazz, Map<String, GraphQLType> graphQLTypes, boolean input) {

		if (Boolean.class.equals(clazz)) {
			return Scalars.GraphQLBoolean;
		}
		else if (Date.class.equals(clazz)) {
			return _dateGraphQLScalarType;
		}
		else if (Integer.class.equals(clazz)) {
			return Scalars.GraphQLInt;
		}
		else if (Long.class.equals(clazz)) {
			return ExtendedScalars.GraphQLLong;
		}
		else if (Map.class.equals(clazz)) {
			return _mapGraphQLScalarType;
		}
		else if (String.class.equals(clazz)) {
			return Scalars.GraphQLString;
		}

		String key = (input ? "Input" : "") + clazz.getSimpleName();

		if (graphQLTypes.containsKey(key)) {
			return graphQLTypes.get(key);
		}

		key =
			(input ? "Input" : "") +
				StringUtil.replace(clazz.getName(), '.', '_');

		if (graphQLTypes.containsKey(key)) {
			return graphQLTypes.get(key);
		}

		return _mapGraphQLScalarType;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GraphQLServletExtender.class);

	private static final GraphQLScalarType _dateGraphQLScalarType;
	private static final GraphQLScalarType _mapGraphQLScalarType;
	private static final GraphQLScalarType _objectGraphQLScalarType;

	static {
		GraphQLScalarType.Builder dateBuilder = new GraphQLScalarType.Builder();

		_dateGraphQLScalarType = dateBuilder.name(
			"Date"
		).description(
			"An RFC-3339 compliant date time scalar"
		).coercing(
			new Coercing<Date, String>() {

				@Override
				public Date parseLiteral(Object value)
					throws CoercingParseLiteralException {

					return _toDate(value);
				}

				@Override
				public Date parseValue(Object value)
					throws CoercingParseValueException {

					return _toDate(value);
				}

				@Override
				public String serialize(Object value)
					throws CoercingSerializeException {

					if (value instanceof Date) {
						SimpleDateFormat simpleDateFormat =
							new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

						return simpleDateFormat.format((Date)value);
					}

					return value.toString();
				}

				private Date _toDate(Object value) {
					if (value instanceof Date) {
						return (Date)value;
					}

					if (value instanceof StringValue) {
						StringValue stringValue = (StringValue)value;

						Calendar calendar = DatatypeConverter.parseDateTime(
							stringValue.getValue());

						return calendar.getTime();
					}

					Calendar calendar = DatatypeConverter.parseDateTime(
						value.toString());

					return calendar.getTime();
				}

			}
		).build();

		GraphQLScalarType.Builder objectBuilder =
			new GraphQLScalarType.Builder();

		_mapGraphQLScalarType = objectBuilder.name(
			"Map"
		).description(
			"Any kind of object supported by a Map"
		).coercing(
			new Coercing<Object, Object>() {

				@Override
				public Object parseLiteral(Object value)
					throws CoercingParseLiteralException {

					return value;
				}

				@Override
				public Object parseValue(Object value)
					throws CoercingParseValueException {

					return value;
				}

				@Override
				public Object serialize(Object value)
					throws CoercingSerializeException {

					return value;
				}

			}
		).build();

		_objectGraphQLScalarType = objectBuilder.name(
			"Object"
		).description(
			"Any kind of object supported by basic scalar types"
		).coercing(
			new Coercing<Object, Object>() {

				@Override
				public Object parseLiteral(Object value)
					throws CoercingParseLiteralException {

					if (value instanceof ArrayValue) {
						ArrayValue arrayValue = (ArrayValue)value;

						List<Value> values = arrayValue.getValues();

						Stream<Value> stream = values.stream();

						return stream.map(
							this::parseLiteral
						).collect(
							Collectors.toList()
						);
					}
					else if (value instanceof BooleanValue) {
						BooleanValue booleanValue = (BooleanValue)value;

						return booleanValue.isValue();
					}
					else if (value instanceof EnumValue) {
						EnumValue enumValue = (EnumValue)value;

						return enumValue.getName();
					}
					else if (value instanceof FloatValue) {
						FloatValue floatValue = (FloatValue)value;

						return floatValue.getValue();
					}
					else if (value instanceof IntValue) {
						IntValue intValue = (IntValue)value;

						return intValue.getValue();
					}
					else if (value instanceof NullValue) {
						return null;
					}
					else if (value instanceof ObjectValue) {
						ObjectValue objectValue = (ObjectValue)value;

						List<ObjectField> objectFields =
							objectValue.getObjectFields();

						Stream<ObjectField> stream = objectFields.stream();

						return stream.collect(
							Collectors.toMap(
								ObjectField::getName,
								objectField -> parseLiteral(
									objectField.getValue())));
					}
					else if (value instanceof StringValue) {
						StringValue stringValue = (StringValue)value;

						return stringValue.getValue();
					}

					throw new CoercingSerializeException(
						"Unable to parse " + value);
				}

				@Override
				public Object parseValue(Object value)
					throws CoercingParseValueException {

					return value;
				}

				@Override
				public Object serialize(Object value)
					throws CoercingSerializeException {

					return value;
				}

			}
		).build();
	}

	private BundleContext _bundleContext;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private DefaultTypeFunction _defaultTypeFunction;

	@Reference
	private GraphQLDTOContributorDataFetchingProcessor
		_graphQLDTOContributorDataFetchingProcessor;

	private ServiceTrackerMap<String, GraphQLDTOContributor>
		_graphQLDTOContributorServiceTrackerMap;
	private GraphQLFieldRetriever _graphQLFieldRetriever;

	@Reference
	private LiferayMethodDataFetchingProcessor
		_liferayMethodDataFetchingProcessor;

	@Reference
	private Portal _portal;

	private final Map<String, String> _registeredClassNames = new HashMap<>();
	private ServiceRegistration<ServletContextHelper>
		_servletContextHelperServiceRegistration;
	private final List<ServletData> _servletDataList = new ArrayList<>();
	private ServiceTracker<ServletData, ServletData> _servletDataServiceTracker;
	private final Map<Long, Servlet> _servlets = new ConcurrentHashMap<>();
	private ServiceRegistration<Servlet> _servletServiceRegistration;

	private static class DateTypeFunction implements TypeFunction {

		@Override
		public GraphQLType buildType(
			boolean input, Class<?> clazz, AnnotatedType annotatedType,
			ProcessingElementsContainer processingElementsContainer) {

			return _dateGraphQLScalarType;
		}

		@Override
		public boolean canBuildType(
			Class<?> clazz, AnnotatedType annotatedType) {

			if (clazz == Date.class) {
				return true;
			}

			return false;
		}

	}

	private static class GraphQLNodePropertyDataFetcher
		implements DataFetcher<Object> {

		@Override
		public Object get(DataFetchingEnvironment dataFetchingEnvironment)
			throws Exception {

			GraphQLSchema graphQLSchema =
				dataFetchingEnvironment.getGraphQLSchema();

			GraphQLCodeRegistry graphQLCodeRegistry =
				graphQLSchema.getCodeRegistry();

			Map<String, GraphQLNamedType> graphQLNamedTypes =
				graphQLSchema.getTypeMap();

			Object source = dataFetchingEnvironment.getSource();

			Class<?> clazz = source.getClass();

			Method getContentTypeMethod = clazz.getMethod("getContentType");

			String fieldName = StringUtil.lowerCaseFirstLetter(
				(String)getContentTypeMethod.invoke(source));

			GraphQLFieldDefinition graphQLFieldDefinition =
				dataFetchingEnvironment.getFieldDefinition();

			DataFetcher<?> dataFetcher = graphQLCodeRegistry.getDataFetcher(
				(GraphQLFieldsContainer)graphQLNamedTypes.get("query"),
				_addField(graphQLFieldDefinition.getType(), fieldName));

			DataFetchingEnvironmentImpl.Builder dataFetchingEnvironmentBuilder =
				DataFetchingEnvironmentImpl.newDataFetchingEnvironment(
					dataFetchingEnvironment);

			Method method = clazz.getMethod("getId");

			Method[] methods = clazz.getMethods();

			for (Method existingMethod : methods) {
				if (StringUtil.equals(
						existingMethod.getName(), "getContentId")) {

					method = existingMethod;
				}
			}

			return dataFetcher.get(
				dataFetchingEnvironmentBuilder.arguments(
					Collections.singletonMap(
						fieldName + "Id", method.invoke(source))
				).build());
		}

	}

	private static class GraphQLNodeTypeResolver implements TypeResolver {

		@Override
		public GraphQLObjectType getType(
			TypeResolutionEnvironment typeResolutionEnvironment) {

			GraphQLSchema graphQLSchema = typeResolutionEnvironment.getSchema();

			Map<String, GraphQLNamedType> graphQLNamedTypes =
				graphQLSchema.getTypeMap();

			GraphQLNamedType graphQLNamedType = graphQLNamedTypes.get(
				_getClassName(typeResolutionEnvironment.getObject()));

			return (GraphQLObjectType)graphQLNamedType;
		}

		private String _getClassName(Object object) {
			Class<?> clazz = object.getClass();

			String name = clazz.getName();

			if (!name.contains("$")) {
				return clazz.getSimpleName();
			}

			Class<?> parentClass = clazz.getSuperclass();

			return parentClass.getSimpleName();
		}

	}

	private static class LiferayArgumentBuilder extends ArgumentBuilder {

		public LiferayArgumentBuilder(
			GraphQLFieldDefinition.Builder graphQLFieldDefinitionBuilder,
			GraphQLOutputType graphQLOutputType, Method method,
			ProcessingElementsContainer processingElementsContainer,
			TypeFunction typeFunction) {

			super(
				method, typeFunction, graphQLFieldDefinitionBuilder,
				processingElementsContainer, graphQLOutputType);

			_method = method;
			_processingElementsContainer = processingElementsContainer;
			_typeFunction = typeFunction;
		}

		@Override
		public List<GraphQLArgument> build() {
			Stream<Parameter> stream = Arrays.stream(_method.getParameters());

			return stream.filter(
				parameter -> !DataFetchingEnvironment.class.isAssignableFrom(
					parameter.getType())
			).map(
				parameter -> {
					if (MultipartUtil.isMultipartBody(parameter)) {
						GraphQLArgument.Builder graphQLArgumentBuilder =
							new GraphQLArgument.Builder();

						return graphQLArgumentBuilder.type(
							new GraphQLList(ApolloScalars.Upload)
						).name(
							"multipartBody"
						).build();
					}

					return _createGraphQLArgument(
						parameter,
						(GraphQLInputType)_typeFunction.buildType(
							true, parameter.getType(),
							parameter.getAnnotatedType(),
							_processingElementsContainer));
				}
			).collect(
				Collectors.toList()
			);
		}

		private GraphQLArgument _createGraphQLArgument(
				Parameter parameter, GraphQLInputType graphQLInputType)
			throws GraphQLAnnotationsException {

			GraphQLArgument.Builder graphQLArgumentBuilder =
				GraphQLArgument.newArgument();

			String graphQLName = GraphQLUtil.getGraphQLNameValue(parameter);

			if (graphQLName != null) {
				graphQLArgumentBuilder.name(
					NamingKit.toGraphqlName(graphQLName));
			}
			else {
				graphQLArgumentBuilder.name(
					NamingKit.toGraphqlName(parameter.getName()));
			}

			graphQLArgumentBuilder.type(graphQLInputType);

			DirectivesBuilder directivesBuilder = new DirectivesBuilder(
				parameter, _processingElementsContainer);

			graphQLArgumentBuilder.withDirectives(directivesBuilder.build());

			return graphQLArgumentBuilder.build();
		}

		private final Method _method;
		private final ProcessingElementsContainer _processingElementsContainer;
		private final TypeFunction _typeFunction;

	}

	private static class LiferayDeprecateBuilder extends DeprecateBuilder {

		public LiferayDeprecateBuilder(AccessibleObject accessibleObject) {
			super(accessibleObject);

			_accessibleObject = accessibleObject;
		}

		public String build() {
			Deprecated deprecated = _accessibleObject.getAnnotation(
				Deprecated.class);

			if (deprecated != null) {
				return "Deprecated";
			}

			return null;
		}

		private final AccessibleObject _accessibleObject;

	}

	private static class LiferayGraphQLErrorHandler
		implements GraphQLErrorHandler {

		@Override
		public List<GraphQLError> processErrors(
			List<GraphQLError> graphQLErrors) {

			Stream<GraphQLError> stream = graphQLErrors.stream();

			return stream.filter(
				graphQLError ->
					!_isNotFoundException(graphQLError) ||
					_isRequiredField(graphQLError)
			).map(
				graphQLError -> {
					String message = graphQLError.getMessage();

					if (message.contains("SecurityException")) {
						return _getExtendedGraphQLError(
							graphQLError, Response.Status.UNAUTHORIZED);
					}
					else if (_isNotFoundException(graphQLError)) {
						return _getExtendedGraphQLError(
							graphQLError, Response.Status.NOT_FOUND);
					}

					if (!_isClientErrorException(graphQLError)) {
						return _getExtendedGraphQLError(
							graphQLError,
							Response.Status.INTERNAL_SERVER_ERROR);
					}

					return _getExtendedGraphQLError(
						graphQLError, Response.Status.BAD_REQUEST);
				}
			).collect(
				Collectors.toList()
			);
		}

		private GraphQLError _getExtendedGraphQLError(
			GraphQLError graphQLError, Response.Status status) {

			GraphqlErrorBuilder graphqlErrorBuilder =
				GraphqlErrorBuilder.newError();

			return graphqlErrorBuilder.message(
				StringUtil.removeSubstring(graphQLError.getMessage(), "%")
			).extensions(
				HashMapBuilder.put(
					"code", (Object)status.getReasonPhrase()
				).put(
					"exception",
					HashMapBuilder.put(
						"errno", status.getStatusCode()
					).build()
				).build()
			).build();
		}

		private boolean _isClientErrorException(GraphQLError graphQLError) {
			if (graphQLError instanceof ExceptionWhileDataFetching) {
				ExceptionWhileDataFetching exceptionWhileDataFetching =
					(ExceptionWhileDataFetching)graphQLError;

				return exceptionWhileDataFetching.getException() instanceof
					GraphQLError;
			}

			String message = graphQLError.getMessage();

			if (!(graphQLError instanceof Throwable) ||
				message.contains("ClientErrorException")) {

				return true;
			}

			return false;
		}

		private boolean _isNotFoundException(GraphQLError graphQLError) {
			if (!(graphQLError instanceof ExceptionWhileDataFetching)) {
				return false;
			}

			ExceptionWhileDataFetching exceptionWhileDataFetching =
				(ExceptionWhileDataFetching)graphQLError;

			Throwable throwable = exceptionWhileDataFetching.getException();

			if ((throwable != null) &&
				(throwable.getCause() instanceof NotFoundException ||
				 throwable.getCause() instanceof NoSuchModelException)) {

				return true;
			}

			return false;
		}

		private boolean _isRequiredField(GraphQLError graphQLError) {
			List<Object> path = Optional.ofNullable(
				graphQLError.getPath()
			).orElse(
				Collections.emptyList()
			);

			if (path.size() <= 1) {
				return true;
			}

			return StringUtil.containsIgnoreCase(
				(String)path.get(path.size() - 1), "parent");
		}

	}

	private static class MapTypeFunction implements TypeFunction {

		@Override
		public GraphQLType buildType(
			boolean input, Class<?> clazz, AnnotatedType annotatedType,
			ProcessingElementsContainer processingElementsContainer) {

			return _mapGraphQLScalarType;
		}

		@Override
		public boolean canBuildType(
			Class<?> clazz, AnnotatedType annotatedType) {

			if (clazz == Map.class) {
				return true;
			}

			return false;
		}

	}

	private static class NodeDataFetcher implements DataFetcher<Object> {

		@Override
		public Object get(DataFetchingEnvironment dataFetchingEnvironment)
			throws Exception {

			GraphQLSchema graphQLSchema =
				dataFetchingEnvironment.getGraphQLSchema();

			GraphQLCodeRegistry graphQLCodeRegistry =
				graphQLSchema.getCodeRegistry();

			GraphQLFieldDefinition graphQLFieldDefinition =
				dataFetchingEnvironment.getFieldDefinition();

			String fieldName = _getFieldName(
				dataFetchingEnvironment, graphQLSchema);

			DataFetcher<?> dataFetcher = graphQLCodeRegistry.getDataFetcher(
				(GraphQLFieldsContainer)dataFetchingEnvironment.getParentType(),
				_addField(
					graphQLFieldDefinition.getType(), fieldName,
					graphQLFieldDefinition.getArgument("id")));

			DataFetchingEnvironmentImpl.Builder dataFetchingEnvironmentBuilder =
				DataFetchingEnvironmentImpl.newDataFetchingEnvironment(
					dataFetchingEnvironment);

			return dataFetcher.get(
				dataFetchingEnvironmentBuilder.arguments(
					Collections.singletonMap(
						fieldName + "Id",
						dataFetchingEnvironment.getArgument("id"))
				).build());
		}

		private String _getFieldName(
			DataFetchingEnvironment dataFetchingEnvironment,
			GraphQLSchema graphQLSchema) {

			Map<String, GraphQLNamedType> graphQLNamedTypes =
				graphQLSchema.getTypeMap();

			GraphQLNamedType graphQLNamedType = graphQLNamedTypes.get(
				dataFetchingEnvironment.getArgument("dataType"));

			return StringUtil.lowerCaseFirstLetter(graphQLNamedType.getName());
		}

	}

	private static class ObjectTypeFunction implements TypeFunction {

		@Override
		public GraphQLType buildType(
			boolean input, Class<?> clazz, AnnotatedType annotatedType,
			ProcessingElementsContainer processingElementsContainer) {

			return _objectGraphQLScalarType;
		}

		@Override
		public boolean canBuildType(
			Class<?> clazz, AnnotatedType annotatedType) {

			if ((clazz == Float.class) || (clazz == MultipartBody.class) ||
				(clazz == Number.class) || (clazz == Object.class) ||
				(clazz == Response.class)) {

				return true;
			}

			return false;
		}

	}

	private static class SanitizedDataFetcherExceptionHandler
		implements DataFetcherExceptionHandler {

		@Override
		public DataFetcherExceptionHandlerResult onException(
			DataFetcherExceptionHandlerParameters
				dataFetcherExceptionHandlerParameters) {

			DataFetcherExceptionHandlerResult.Builder
				dataFetcherExceptionHandlerResultBuilder =
					DataFetcherExceptionHandlerResult.newResult();

			return dataFetcherExceptionHandlerResultBuilder.error(
				new ExceptionWhileDataFetching(
					dataFetcherExceptionHandlerParameters.getPath(),
					dataFetcherExceptionHandlerParameters.getException(),
					dataFetcherExceptionHandlerParameters.getSourceLocation())
			).build();
		}

	}

	private class LiferayGraphQLFieldRetriever extends GraphQLFieldRetriever {

		@Override
		public GraphQLFieldDefinition getField(
				String parentName, Field field,
				ProcessingElementsContainer processingElementsContainer)
			throws GraphQLAnnotationsException {

			GraphQLFieldDefinition.Builder graphQLFieldDefinitionBuilder =
				GraphQLFieldDefinition.newFieldDefinition();

			graphQLFieldDefinitionBuilder.deprecate(
				new DeprecateBuilder(
					field
				).build());

			GraphQLField graphQLField = field.getAnnotation(GraphQLField.class);

			if (graphQLField != null) {
				graphQLFieldDefinitionBuilder.description(
					graphQLField.description());
			}

			graphQLFieldDefinitionBuilder.name(
				new FieldNameBuilder(
					field
				).build());
			graphQLFieldDefinitionBuilder.type(
				(GraphQLOutputType)_defaultTypeFunction.buildType(
					field.getType(), field.getAnnotatedType(),
					processingElementsContainer));

			return graphQLFieldDefinitionBuilder.build();
		}

		@Override
		public GraphQLFieldDefinition getField(
			String parentName, Method method,
			ProcessingElementsContainer processingElementsContainer) {

			GraphQLFieldDefinition.Builder graphQLFieldDefinitionBuilder =
				GraphQLFieldDefinition.newFieldDefinition();

			MethodTypeBuilder methodTypeBuilder = new MethodTypeBuilder(
				method, processingElementsContainer.getDefaultTypeFunction(),
				processingElementsContainer, false);

			GraphQLOutputType graphQLOutputType =
				(GraphQLOutputType)methodTypeBuilder.build();

			ArgumentBuilder argumentBuilder = new LiferayArgumentBuilder(
				graphQLFieldDefinitionBuilder, graphQLOutputType, method,
				processingElementsContainer,
				processingElementsContainer.getDefaultTypeFunction());

			graphQLFieldDefinitionBuilder.arguments(argumentBuilder.build());

			graphQLFieldDefinitionBuilder.dataFetcher(
				new LiferayMethodDataFetcher(
					_liferayMethodDataFetchingProcessor, method));

			DeprecateBuilder deprecateBuilder = new LiferayDeprecateBuilder(
				method);

			graphQLFieldDefinitionBuilder.deprecate(deprecateBuilder.build());

			GraphQLField graphQLField = method.getAnnotation(
				GraphQLField.class);

			if (graphQLField != null) {
				graphQLFieldDefinitionBuilder.description(
					graphQLField.description());
			}

			MethodNameBuilder methodNameBuilder = new MethodNameBuilder(method);

			graphQLFieldDefinitionBuilder.name(methodNameBuilder.build());

			graphQLFieldDefinitionBuilder.type(graphQLOutputType);

			return graphQLFieldDefinitionBuilder.build();
		}

	}

	private class LiferayGraphQLTypeVisitor extends GraphQLTypeVisitorStub {

		public LiferayGraphQLTypeVisitor(
			GraphQLInterfaceType graphQLInterfaceType) {

			_graphQLInterfaceType = graphQLInterfaceType;
		}

		@Override
		public TraversalControl visitGraphQLObjectType(
			GraphQLObjectType graphQLObjectType,
			TraverserContext<GraphQLSchemaElement> traverserContext) {

			GraphQLFieldDefinition idGraphQLFieldDefinition =
				graphQLObjectType.getFieldDefinition("id");

			if ((idGraphQLFieldDefinition == null) ||
				(idGraphQLFieldDefinition.getType() !=
					ExtendedScalars.GraphQLLong)) {

				return TraversalControl.CONTINUE;
			}

			return changeNode(
				traverserContext,
				graphQLObjectType.transform(
					graphQLObjectTypeBuilder -> {
						_replaceFieldDefinition(
							_graphQLInterfaceType, graphQLObjectType,
							graphQLObjectTypeBuilder);
						_replaceFieldNodes(
							traverserContext.getVarFromParents(
								GraphQLCodeRegistry.Builder.class),
							_graphQLInterfaceType, graphQLObjectType,
							graphQLObjectTypeBuilder);

						graphQLObjectTypeBuilder.withInterface(
							_graphQLInterfaceType);
					}));
		}

		private final GraphQLInterfaceType _graphQLInterfaceType;

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

				_servlets.clear();
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

				_servlets.clear();
			}

			_bundleContext.ungetService(serviceReference);
		}

	}

}