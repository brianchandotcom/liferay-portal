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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;
import com.liferay.portal.vulcan.internal.configuration.VulcanConfiguration;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.ContextProviderUtil;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.FilterContextProvider;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.SortContextProvider;
import com.liferay.portal.vulcan.internal.jaxrs.param.converter.provider.SiteParamConverterProvider;
import com.liferay.portal.vulcan.internal.jaxrs.validation.ValidationUtil;
import com.liferay.portal.vulcan.internal.multipart.MultipartUtil;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import graphql.GraphQLError;
import graphql.Scalars;
import graphql.TypeResolutionEnvironment;

import graphql.annotations.directives.DirectiveWirer;
import graphql.annotations.directives.DirectiveWiringMapRetriever;
import graphql.annotations.processor.ProcessingElementsContainer;
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
import graphql.annotations.processor.retrievers.fieldBuilders.DescriptionBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.DirectivesBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.method.MethodNameBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.method.MethodTypeBuilder;
import graphql.annotations.processor.searchAlgorithms.BreadthFirstSearch;
import graphql.annotations.processor.searchAlgorithms.ParentalSearch;
import graphql.annotations.processor.typeFunctions.DefaultTypeFunction;
import graphql.annotations.processor.typeFunctions.TypeFunction;
import graphql.annotations.processor.util.NamingKit;
import graphql.annotations.processor.util.ReflectionKit;

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
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import graphql.schema.TypeResolver;

import graphql.servlet.ApolloScalars;
import graphql.servlet.DefaultGraphQLErrorHandler;
import graphql.servlet.GenericGraphQLError;
import graphql.servlet.GraphQLConfiguration;
import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLHttpServlet;
import graphql.servlet.GraphQLObjectMapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import javax.validation.ValidationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;

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
					String graphQLName = _getGraphQLNameValue(objectClass);

					if (graphQLName == null) {
						return NamingKit.toGraphqlName(objectClass.getName());
					}

					return NamingKit.toGraphqlName(graphQLName);
				}

				public Boolean isGraphQLField(AnnotatedElement element) {
					GraphQLField graphQLField = element.getAnnotation(
						GraphQLField.class);

					if (graphQLField == null) {
						return null;
					}

					return graphQLField.value();
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
		_defaultTypeFunction.register(new ObjectTypeFunction());

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

						Servlet servlet = _createServlet();

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

	private static Object _getAnnotationValue(
		AnnotatedElement annotatedElement, Class clazz) {

		for (Annotation annotation :
				annotatedElement.getDeclaredAnnotations()) {

			Class<? extends Annotation> typeClass = annotation.annotationType();

			String name = typeClass.getName();

			if (name.equals(clazz.getName())) {
				try {
					Method method = typeClass.getMethod("value");

					return method.invoke(annotation);
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		return null;
	}

	private static String _getGraphQLNameValue(
		AnnotatedElement annotatedElement) {

		GraphQLName graphQLName = annotatedElement.getAnnotation(
			GraphQLName.class);

		if (graphQLName != null) {
			return graphQLName.value();
		}

		Object value = _getAnnotationValue(
			annotatedElement,
			graphql.annotations.annotationTypes.GraphQLName.class);

		if (value == null) {
			return null;
		}

		return (String)value;
	}

	private static boolean _isMultipartBody(Parameter parameter) {
		Class<?> clazz = parameter.getType();

		String typeName = clazz.getTypeName();

		return typeName.contains("MultipartBody");
	}

	private void _collectObjectFields(
		GraphQLObjectType.Builder builder,
		Function<ServletData, Object> function,
		ProcessingElementsContainer processingElementsContainer,
		List<ServletData> servletDatas) {

		Stream<ServletData> stream = servletDatas.stream();

		Map<String, Optional<Method>> methods = stream.map(
			function
		).map(
			Object::getClass
		).map(
			Class::getMethods
		).flatMap(
			Arrays::stream
		).filter(
			method -> Boolean.TRUE.equals(_getGraphQLFieldValue(method))
		).collect(
			Collectors.groupingBy(
				Method::getName,
				Collectors.maxBy(Comparator.comparingInt(this::_getVersion)))
		);

		for (Optional<Method> methodOptional : methods.values()) {
			if (methodOptional.isPresent()) {
				Method method = methodOptional.get();

				Class<?> clazz = method.getDeclaringClass();

				builder.field(
					_graphQLFieldRetriever.getField(
						clazz.getSimpleName(), method,
						processingElementsContainer));
			}
		}
	}

	private Message _createMessage(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		Message message = new MessageImpl();

		String requestURL = String.valueOf(httpServletRequest.getRequestURL());

		message.put(Message.ENDPOINT_ADDRESS, requestURL);

		String contextPath = GetterUtil.getString(
			httpServletRequest.getContextPath());
		String servletPath = GetterUtil.getString(
			httpServletRequest.getServletPath());

		message.put(
			Message.PATH_INFO,
			contextPath + servletPath + httpServletRequest.getPathInfo());

		message.put(Message.QUERY_STRING, httpServletRequest.getQueryString());
		message.put("Accept", httpServletRequest.getHeader("Accept"));
		message.put("Content-Type", httpServletRequest.getContentType());
		message.put("HTTP.REQUEST", httpServletRequest);
		message.put("HTTP.RESPONSE", httpServletResponse);
		message.put("org.apache.cxf.async.post.response.dispatch", true);
		message.put(
			"org.apache.cxf.request.method", httpServletRequest.getMethod());
		message.put(
			"org.apache.cxf.request.uri", httpServletRequest.getRequestURI());
		message.put("org.apache.cxf.request.url", requestURL);
		message.put(
			"http.base.path",
			_getBasePath(
				contextPath, httpServletRequest.getRequestURI(), requestURL,
				servletPath));

		message.setExchange(new ExchangeImpl());

		return message;
	}

	private GraphQLFieldDefinition _createNodeGraphQLFieldDefinition(
		GraphQLOutputType graphQLOutputType) {

		GraphQLFieldDefinition.Builder builder =
			GraphQLFieldDefinition.newFieldDefinition();

		builder.argument(
			GraphQLArgument.newArgument(
			).name(
				"dataType"
			).type(
				Scalars.GraphQLString
			).build());
		builder.argument(
			GraphQLArgument.newArgument(
			).name(
				"id"
			).type(
				Scalars.GraphQLLong
			).build());
		builder.name("graphQLNode");
		builder.type(graphQLOutputType);

		return builder.build();
	}

	private GraphQLInterfaceType _createNodeGraphQLInterfaceType() {
		GraphQLInterfaceType.Builder interfaceBuilder =
			GraphQLInterfaceType.newInterface();

		GraphQLFieldDefinition.Builder fieldBuilder =
			GraphQLFieldDefinition.newFieldDefinition();

		interfaceBuilder.field(
			fieldBuilder.name(
				"id"
			).type(
				Scalars.GraphQLLong
			).build());

		interfaceBuilder.name("GraphQLNode");

		return interfaceBuilder.build();
	}

	private Object _createObject(
			DataFetchingEnvironment dataFetchingEnvironment, Method method)
		throws Exception {

		Object instance = null;

		GraphQLFieldDefinition graphQLFieldDefinition =
			dataFetchingEnvironment.getFieldDefinition();

		if ((dataFetchingEnvironment.getRoot() ==
				dataFetchingEnvironment.getSource()) ||
			Objects.equals(graphQLFieldDefinition.getName(), "graphQLNode")) {

			instance = _createQueryInstance(
				method.getDeclaringClass(), dataFetchingEnvironment);
		}
		else {
			Class<?> declaringClass = method.getDeclaringClass();

			Field field = declaringClass.getDeclaredField("this$0");

			Object queryInstance = _createQueryInstance(
				field.getType(), dataFetchingEnvironment);

			Constructor<?>[] constructors = declaringClass.getConstructors();

			instance = ReflectionKit.constructNewInstance(
				constructors[0], queryInstance,
				dataFetchingEnvironment.getSource());
		}

		Parameter[] parameters = method.getParameters();

		Map<String, Object> arguments = dataFetchingEnvironment.getArguments();

		Object[] args = new Object[parameters.length];

		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];

			String parameterName = null;

			String graphQLName = _getGraphQLNameValue(parameter);

			if (graphQLName == null) {
				parameterName = NamingKit.toGraphqlName(parameter.getName());
			}
			else {
				parameterName = NamingKit.toGraphqlName(graphQLName);
			}

			Object argument = arguments.get(parameterName);

			if (argument == null) {
				if (parameter.isAnnotationPresent(NotNull.class)) {
					throw new ValidationException(parameterName + " is null");
				}
				else if (parameterName.equals("page")) {
					argument = 1;
				}
				else if (parameterName.equals("pageSize")) {
					argument = 20;
				}
			}

			if (parameterName.equals("siteKey") && (argument != null)) {
				SiteParamConverterProvider siteParamConverterProvider =
					new SiteParamConverterProvider(_groupLocalService);

				args[i - 1] = Long.valueOf(
					siteParamConverterProvider.getGroupId(
						CompanyThreadLocal.getCompanyId(), (String)argument));
			}

			if (_isMultipartBody(parameter)) {
				Map<String, BinaryFile> binaryFiles = new HashMap<>();

				List<Part> parts = (List<Part>)argument;

				if ((parts != null) && !parts.isEmpty()) {
					Part part = parts.get(0);

					binaryFiles.put(
						"file",
						new BinaryFile(
							part.getContentType(),
							MultipartUtil.getFileName(part),
							part.getInputStream(), part.getSize()));

					Map<String, String> values = new HashMap<>();

					if (parts.size() > 1) {
						Part metadataPart = parts.get(1);

						String metadata = StringUtil.read(
							metadataPart.getInputStream());

						int index = metadata.indexOf("=");

						if (index != -1) {
							values.put(
								metadata.substring(0, index),
								metadata.substring(index + 1));
						}
					}

					argument = MultipartBody.of(
						binaryFiles, __ -> _objectMapper, values);
				}
			}

			Class<? extends Parameter> parameterClass = parameter.getClass();

			if ((argument instanceof Map) &&
				!parameterClass.isAssignableFrom(Map.class)) {

				argument = _objectMapper.convertValue(
					argument, parameter.getType());

				ValidationUtil.validate(argument);
			}

			args[i] = argument;
		}

		ValidationUtil.validateArguments(instance, method, args);

		return method.invoke(instance, args);
	}

	private Object _createQueryInstance(
			Class clazz, DataFetchingEnvironment dataFetchingEnvironment)
		throws Exception {

		GraphQLContext graphQLContext = dataFetchingEnvironment.getContext();

		Optional<HttpServletRequest> httpServletRequestOptional =
			graphQLContext.getHttpServletRequest();

		HttpServletRequest httpServletRequest =
			httpServletRequestOptional.orElse(null);

		Optional<HttpServletResponse> httpServletResponseOptional =
			graphQLContext.getHttpServletResponse();

		HttpServletResponse httpServletResponse =
			httpServletResponseOptional.orElse(null);

		AcceptLanguage acceptLanguage = new AcceptLanguageImpl(
			httpServletRequest, _language, _portal);

		Object instance = clazz.newInstance();

		for (Field field : clazz.getDeclaredFields()) {
			if (Modifier.isFinal(field.getModifiers()) ||
				Modifier.isStatic(field.getModifiers())) {

				continue;
			}

			Class<?> fieldClass = field.getType();

			if (fieldClass.isAssignableFrom(AcceptLanguage.class)) {
				field.setAccessible(true);

				field.set(instance, acceptLanguage);
			}
			else if (fieldClass.isAssignableFrom(Company.class)) {
				field.setAccessible(true);

				field.set(
					instance,
					_companyLocalService.getCompany(
						CompanyThreadLocal.getCompanyId()));
			}
			else if (fieldClass.isAssignableFrom(HttpServletRequest.class)) {
				field.setAccessible(true);

				field.set(instance, httpServletRequest);
			}
			else if (fieldClass.isAssignableFrom(HttpServletResponse.class)) {
				field.setAccessible(true);

				field.set(instance, httpServletResponseOptional.orElse(null));
			}
			else if (fieldClass.isAssignableFrom(UriInfo.class)) {
				field.setAccessible(true);

				field.set(
					instance,
					new UriInfoImpl(
						_createMessage(
							httpServletRequest, httpServletResponse)));
			}
			else if (fieldClass.isAssignableFrom(User.class)) {
				field.setAccessible(true);

				field.set(
					instance,
					_portal.getUser(httpServletRequestOptional.orElse(null)));
			}
			else if (Objects.equals(field.getName(), "_filterBiFunction")) {
				field.setAccessible(true);

				BiFunction<Object, String, Filter> filterBiFunction =
					(resource, filterString) -> {
						try {
							FilterContextProvider filterContextProvider =
								new FilterContextProvider(
									_expressionConvert, _filterParserProvider,
									_language, _portal);

							return filterContextProvider.createContext(
								acceptLanguage,
								_getEntityModel(
									resource,
									httpServletRequest.getParameterMap()),
								filterString);
						}
						catch (Exception e) {
							throw new BadRequestException(e);
						}
					};

				field.set(instance, filterBiFunction);
			}
			else if (Objects.equals(field.getName(), "_sortsBiFunction")) {
				field.setAccessible(true);

				BiFunction<Object, String, Sort[]> sortsBiFunction =
					(resource, sortsString) -> {
						try {
							SortContextProvider sortContextProvider =
								new SortContextProvider(
									_language, _portal, _sortParserProvider);

							return sortContextProvider.createContext(
								acceptLanguage,
								_getEntityModel(
									resource,
									httpServletRequest.getParameterMap()),
								sortsString);
						}
						catch (Exception e) {
							throw new BadRequestException(e);
						}
					};

				field.set(instance, sortsBiFunction);
			}
		}

		return instance;
	}

	private Servlet _createServlet() throws Exception {
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

			GraphQLSchema.Builder graphQLSchemaBuilder =
				GraphQLSchema.newSchema();

			GraphQLObjectType.Builder mutationBuilder =
				GraphQLObjectType.newObject();

			mutationBuilder.name("mutation");

			GraphQLObjectType.Builder graphQLObjectTypeBuilder =
				GraphQLObjectType.newObject();

			graphQLObjectTypeBuilder.name("query");

			_collectObjectFields(
				mutationBuilder, ServletData::getMutation,
				processingElementsContainer, servletDatas);

			_collectObjectFields(
				graphQLObjectTypeBuilder, ServletData::getQuery,
				processingElementsContainer, servletDatas);

			_registerInterfaces(
				processingElementsContainer, graphQLObjectTypeBuilder,
				graphQLSchemaBuilder);

			graphQLSchemaBuilder.mutation(mutationBuilder.build());
			graphQLSchemaBuilder.query(graphQLObjectTypeBuilder.build());

			GraphQLConfiguration.Builder graphQLConfigurationBuilder =
				GraphQLConfiguration.with(graphQLSchemaBuilder.build());

			GraphQLObjectMapper.Builder objectMapperBuilder =
				GraphQLObjectMapper.newBuilder();

			objectMapperBuilder.withGraphQLErrorHandler(
				new LiferayGraphQLErrorHandler());

			graphQLConfigurationBuilder.with(objectMapperBuilder.build());

			_servlet = GraphQLHttpServlet.with(
				graphQLConfigurationBuilder.build());

			return _servlet;
		}
	}

	private String _getBasePath(
		String contextPath, String requestURI, String requestURL,
		String servletPath) {

		if (!StringUtils.isEmpty(requestURI)) {
			int index = requestURL.indexOf(requestURI);

			if (index > 0) {
				return requestURL.substring(0, index) + contextPath;
			}
		}
		else if (!StringUtils.isEmpty(servletPath) &&
				 requestURL.endsWith(servletPath)) {

			int index = requestURL.lastIndexOf(servletPath);

			if (index > 0) {
				return requestURL.substring(0, index);
			}
		}

		return null;
	}

	private EntityModel _getEntityModel(
			Object resource, Map<String, String[]> parameterMap)
		throws Exception {

		EntityModelResource entityModelResource = (EntityModelResource)resource;

		return entityModelResource.getEntityModel(
			ContextProviderUtil.getMultivaluedHashMap(parameterMap));
	}

	private Field _getFieldDefinitionsByNameField(
			GraphQLObjectType graphQLObjectType)
		throws NoSuchFieldException {

		Class<? extends GraphQLObjectType> clazz = graphQLObjectType.getClass();

		Field field = clazz.getDeclaredField("fieldDefinitionsByName");

		field.setAccessible(true);

		return field;
	}

	private Boolean _getGraphQLFieldValue(AnnotatedElement annotatedElement) {
		GraphQLField graphQLField = annotatedElement.getAnnotation(
			GraphQLField.class);

		if (graphQLField != null) {
			return graphQLField.value();
		}

		Object value = _getAnnotationValue(
			annotatedElement,
			graphql.annotations.annotationTypes.GraphQLField.class);

		if (value == null) {
			return null;
		}

		return (Boolean)value;
	}

	private Integer _getVersion(Method method) {
		Class<?> clazz = method.getDeclaringClass();

		Package pkg = clazz.getPackage();

		String packageString = pkg.toString();

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

	private void _registerInterfaces(
		ProcessingElementsContainer processingElementsContainer,
		GraphQLObjectType.Builder graphQLObjectTypeBuilder,
		GraphQLSchema.Builder graphQLSchemaBuilder) {

		try {
			Map<String, GraphQLType> graphQLTypes =
				processingElementsContainer.getTypeRegistry();

			GraphQLInterfaceType graphQLInterfaceType =
				_createNodeGraphQLInterfaceType();

			graphQLTypes.put("GraphQLNode", graphQLInterfaceType);

			graphQLObjectTypeBuilder.field(
				_createNodeGraphQLFieldDefinition(graphQLInterfaceType));

			GraphQLCodeRegistry.Builder builder =
				processingElementsContainer.getCodeRegistryBuilder();

			graphQLSchemaBuilder.codeRegistry(
				builder.dataFetcher(
					FieldCoordinates.coordinates("query", "graphQLNode"),
					new NodeDataFetcher()
				).typeResolver(
					"GraphQLNode", new GraphQLNodeTypeResolver()
				).build());

			for (Map.Entry<String, GraphQLType> entry :
					graphQLTypes.entrySet()) {

				GraphQLType graphQLType = entry.getValue();

				if (graphQLType instanceof GraphQLObjectType) {
					GraphQLObjectType graphQLObjectType =
						(GraphQLObjectType)graphQLType;

					GraphQLFieldDefinition graphQLFieldDefinition =
						graphQLObjectType.getFieldDefinition("id");

					if ((graphQLFieldDefinition == null) ||
						(graphQLFieldDefinition.getType() !=
							Scalars.GraphQLLong)) {

						continue;
					}

					_replaceFieldDefinition(
						graphQLInterfaceType, graphQLObjectType);
					_replaceFieldNodes(
						builder, graphQLInterfaceType, graphQLObjectType,
						graphQLSchemaBuilder);
					_replaceInterface(graphQLInterfaceType, graphQLObjectType);
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void _replaceFieldDefinition(
			GraphQLInterfaceType graphQLInterfaceType,
			GraphQLObjectType graphQLObjectType)
		throws Exception {

		Field field = _getFieldDefinitionsByNameField(graphQLObjectType);

		Map<String, GraphQLFieldDefinition> graphQLFieldDefinitions =
			(Map<String, GraphQLFieldDefinition>)field.get(graphQLObjectType);

		for (GraphQLFieldDefinition graphQLFieldDefinition :
				graphQLObjectType.getFieldDefinitions()) {

			GraphQLOutputType graphQLOutputType =
				graphQLFieldDefinition.getType();

			String typeName = graphQLOutputType.getName();

			if ((typeName != null) && typeName.equals("Object")) {
				Class<? extends GraphQLFieldDefinition> fieldClass =
					graphQLFieldDefinition.getClass();

				Method method = fieldClass.getDeclaredMethod(
					"replaceType", GraphQLOutputType.class);

				method.setAccessible(true);

				method.invoke(graphQLFieldDefinition, graphQLInterfaceType);

				graphQLFieldDefinitions.put(
					graphQLFieldDefinition.getName(), graphQLFieldDefinition);
			}
		}

		field.set(graphQLObjectType, graphQLFieldDefinitions);
	}

	private void _replaceFieldNodes(
			GraphQLCodeRegistry.Builder builder,
			GraphQLInterfaceType graphQLInterfaceType,
			GraphQLObjectType graphQLObjectType,
			GraphQLSchema.Builder graphQLSchemaBuilder)
		throws Exception {

		GraphQLFieldDefinition graphQLFieldDefinition =
			graphQLObjectType.getFieldDefinition("contentType");

		if (graphQLFieldDefinition != null) {
			Field field = _getFieldDefinitionsByNameField(graphQLObjectType);

			Map<String, GraphQLFieldDefinition> graphQLFieldDefinitions =
				(Map<String, GraphQLFieldDefinition>)field.get(
					graphQLObjectType);

			GraphQLFieldDefinition.Builder graphQLFieldDefinitionBuilder =
				GraphQLFieldDefinition.newFieldDefinition();

			graphQLFieldDefinitions.put(
				"graphQLNode",
				graphQLFieldDefinitionBuilder.name(
					"graphQLNode"
				).type(
					graphQLInterfaceType
				).build());

			graphQLSchemaBuilder.codeRegistry(
				builder.dataFetcher(
					FieldCoordinates.coordinates(
						graphQLObjectType.getName(), "graphQLNode"),
					new GraphQLNodePropertyDataFetcher()
				).typeResolver(
					"GraphQLNode", new GraphQLNodeTypeResolver()
				).build());

			field.set(graphQLObjectType, graphQLFieldDefinitions);
		}
	}

	private void _replaceInterface(
			GraphQLInterfaceType graphQLInterfaceType,
			GraphQLObjectType graphQLObjectType)
		throws Exception {

		Class<? extends GraphQLObjectType> clazz = graphQLObjectType.getClass();

		Method method = clazz.getDeclaredMethod(
			"replaceInterfaces", List.class);

		method.setAccessible(true);

		method.invoke(
			graphQLObjectType, Collections.singletonList(graphQLInterfaceType));
	}

	private static final GraphQLScalarType _dateGraphQLScalarType;
	private static final GraphQLScalarType _objectGraphQLScalarType;
	private static final ObjectMapper _objectMapper = new ObjectMapper();

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

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");

					return simpleDateFormat.format((Date)value);
				}

				private Date _toDate(Object value) {
					if (value instanceof Date) {
						return (Date)value;
					}

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");

					try {
						if (value instanceof StringValue) {
							StringValue stringValue = (StringValue)value;

							return simpleDateFormat.parse(
								stringValue.getValue());
						}

						return simpleDateFormat.parse(value.toString());
					}
					catch (ParseException pe) {
						throw new CoercingSerializeException(
							"Unable to parse " + value, pe);
					}
				}

			}
		).build();

		GraphQLScalarType.Builder objectBuilder =
			new GraphQLScalarType.Builder();

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
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private DefaultTypeFunction _defaultTypeFunction;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<Filter> _expressionConvert;

	@Reference
	private FilterParserProvider _filterParserProvider;

	private GraphQLFieldRetriever _graphQLFieldRetriever;

	@Reference
	private GroupLocalService _groupLocalService;

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

	@Reference
	private SortParserProvider _sortParserProvider;

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

			Map<String, GraphQLType> graphQLTypes = graphQLSchema.getTypeMap();

			GraphQLFieldDefinition.Builder builder =
				GraphQLFieldDefinition.newFieldDefinition();

			Object source = dataFetchingEnvironment.getSource();

			Class<?> clazz = source.getClass();

			Method getContentTypeMethod = clazz.getMethod("getContentType");

			String fieldName = StringUtil.lowerCaseFirstLetter(
				(String)getContentTypeMethod.invoke(source));

			GraphQLFieldDefinition graphQLFieldDefinition =
				dataFetchingEnvironment.getFieldDefinition();

			DataFetcher dataFetcher = graphQLCodeRegistry.getDataFetcher(
				(GraphQLFieldsContainer)graphQLTypes.get("query"),
				builder.name(
					fieldName
				).type(
					graphQLFieldDefinition.getType()
				).build());

			DataFetchingEnvironmentImpl.Builder dataFetchingEnvironmentBuilder =
				DataFetchingEnvironmentImpl.newDataFetchingEnvironment(
					dataFetchingEnvironment);

			Method getIdMethod = clazz.getMethod("getId");

			return dataFetcher.get(
				dataFetchingEnvironmentBuilder.arguments(
					Collections.singletonMap(
						fieldName + "Id", getIdMethod.invoke(source))
				).build());
		}

	}

	private static class GraphQLNodeTypeResolver implements TypeResolver {

		@Override
		public GraphQLObjectType getType(
			TypeResolutionEnvironment typeResolutionEnvironment) {

			GraphQLSchema graphQLSchema = typeResolutionEnvironment.getSchema();

			Map<String, GraphQLType> graphQLTypes = graphQLSchema.getTypeMap();

			GraphQLType graphQLType = graphQLTypes.get(
				_getClassName(typeResolutionEnvironment.getObject()));

			return (GraphQLObjectType)graphQLType;
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
			Method method, TypeFunction typeFunction,
			GraphQLFieldDefinition.Builder builder,
			ProcessingElementsContainer processingElementsContainer,
			GraphQLOutputType graphQLOutputType) {

			super(
				method, typeFunction, builder, processingElementsContainer,
				graphQLOutputType);

			_method = method;
			_typeFunction = typeFunction;
			_processingElementsContainer = processingElementsContainer;
		}

		@Override
		public List<GraphQLArgument> build() {
			Stream<Parameter> stream = Arrays.stream(_method.getParameters());

			return stream.filter(
				parameter -> !DataFetchingEnvironment.class.isAssignableFrom(
					parameter.getType())
			).map(
				parameter -> {
					if (_isMultipartBody(parameter)) {
						GraphQLArgument.Builder builder =
							new GraphQLArgument.Builder();

						return builder.type(
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

			DirectiveWirer directiveWirer = new DirectiveWirer();

			GraphQLArgument.Builder builder = GraphQLArgument.newArgument();

			String graphQLName = _getGraphQLNameValue(parameter);

			if (graphQLName != null) {
				builder.name(NamingKit.toGraphqlName(graphQLName));
			}
			else {
				builder.name(NamingKit.toGraphqlName(parameter.getName()));
			}

			builder.type(graphQLInputType);

			DirectivesBuilder directivesBuilder = new DirectivesBuilder(
				parameter, _processingElementsContainer);

			builder.withDirectives(directivesBuilder.build());

			DirectiveWiringMapRetriever directiveWiringMapRetriever =
				new DirectiveWiringMapRetriever();

			return (GraphQLArgument)directiveWirer.wire(
				builder.build(),
				directiveWiringMapRetriever.getDirectiveWiringMap(
					parameter, _processingElementsContainer),
				_processingElementsContainer.getCodeRegistryBuilder(),
				graphQLInputType.getName());
		}

		private Method _method;
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
		extends DefaultGraphQLErrorHandler {

		@Override
		protected List<GraphQLError> filterGraphQLErrors(
			List<GraphQLError> graphQLErrors) {

			Stream<GraphQLError> stream = graphQLErrors.stream();

			return stream.map(
				graphQLError -> {
					if (!isClientError(graphQLError)) {
						return new GenericGraphQLError(
							graphQLError.getMessage());
					}

					return graphQLError;
				}
			).collect(
				Collectors.toList()
			);
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

			GraphQLFieldDefinition.Builder builder =
				GraphQLFieldDefinition.newFieldDefinition();

			GraphQLFieldDefinition graphQLFieldDefinition =
				dataFetchingEnvironment.getFieldDefinition();

			String fieldName = _getFieldName(
				dataFetchingEnvironment, graphQLSchema);

			DataFetcher dataFetcher = graphQLCodeRegistry.getDataFetcher(
				(GraphQLFieldsContainer)dataFetchingEnvironment.getParentType(),
				builder.argument(
					graphQLFieldDefinition.getArgument("id")
				).name(
					fieldName
				).type(
					graphQLFieldDefinition.getType()
				).build());

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

			Map<String, GraphQLType> graphQLTypes = graphQLSchema.getTypeMap();

			GraphQLType graphQLType = graphQLTypes.get(
				dataFetchingEnvironment.getArgument("dataType"));

			return StringUtil.lowerCaseFirstLetter(graphQLType.getName());
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

			if ((clazz == Map.class) || (clazz == MultipartBody.class) ||
				(clazz == Object.class) || (clazz == Response.class)) {

				return true;
			}

			return false;
		}

	}

	private class LiferayGraphQLFieldRetriever extends GraphQLFieldRetriever {

		@Override
		public GraphQLFieldDefinition getField(
			String parentName, Method method,
			ProcessingElementsContainer processingElementsContainer) {

			GraphQLFieldDefinition.Builder builder =
				GraphQLFieldDefinition.newFieldDefinition();

			MethodTypeBuilder methodTypeBuilder = new MethodTypeBuilder(
				method, processingElementsContainer.getDefaultTypeFunction(),
				processingElementsContainer, false);

			GraphQLOutputType graphQLOutputType =
				(GraphQLOutputType)methodTypeBuilder.build();

			ArgumentBuilder argumentBuilder = new LiferayArgumentBuilder(
				method, processingElementsContainer.getDefaultTypeFunction(),
				builder, processingElementsContainer, graphQLOutputType);

			builder.arguments(argumentBuilder.build());

			builder.dataFetcher(new LiferayMethodDataFetcher(method));

			DeprecateBuilder deprecateBuilder = new LiferayDeprecateBuilder(
				method);

			builder.deprecate(deprecateBuilder.build());

			DescriptionBuilder descriptionBuilder = new DescriptionBuilder(
				method);

			builder.description(descriptionBuilder.build());

			MethodNameBuilder methodNameBuilder = new MethodNameBuilder(method);

			builder.name(methodNameBuilder.build());

			builder.type(graphQLOutputType);

			return builder.build();
		}

	}

	private class LiferayMethodDataFetcher implements DataFetcher<Object> {

		@Override
		public Object get(DataFetchingEnvironment dataFetchingEnvironment) {
			try {
				return _createObject(dataFetchingEnvironment, _method);
			}
			catch (InvocationTargetException ite) {
				throw new RuntimeException(ite.getTargetException());
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