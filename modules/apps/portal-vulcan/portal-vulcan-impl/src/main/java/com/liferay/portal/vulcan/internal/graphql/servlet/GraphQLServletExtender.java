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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import graphql.GraphQLError;

import graphql.annotations.directives.DirectiveWirer;
import graphql.annotations.directives.DirectiveWiringMapRetriever;
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
import graphql.annotations.processor.retrievers.fieldBuilders.DescriptionBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.DirectivesBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.method.MethodNameBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.method.MethodTypeBuilder;
import graphql.annotations.processor.searchAlgorithms.BreadthFirstSearch;
import graphql.annotations.processor.searchAlgorithms.ParentalSearch;
import graphql.annotations.processor.typeFunctions.DefaultTypeFunction;
import graphql.annotations.processor.typeFunctions.TypeFunction;
import graphql.annotations.processor.util.NamingKit;

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
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;

import graphql.servlet.DefaultGraphQLErrorHandler;
import graphql.servlet.GenericGraphQLError;
import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLObjectMapper;
import graphql.servlet.SimpleGraphQLHttpServlet;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Response;

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
					String graphQLName = _getGraphQLNameValue(objectClass);

					if (graphQLName == null) {
						return NamingKit.toGraphqlName(objectClass.getName());
					}

					return NamingKit.toGraphqlName(graphQLName);
				}

			};

		BreadthFirstSearch breadthFirstSearch = new LiferayBreadthFirstSearch(
			graphQLObjectInfoRetriever);
		ParentalSearch parentalSearch = new LiferayParentalSearch(
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
		GraphQLObjectType.Builder builder,
		Function<ServletData, Object> function,
		ProcessingElementsContainer processingElementsContainer) {

		Map<String, Optional<Method>> methods = _servletDataList.stream(
		).map(
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

			String graphQLName = _getGraphQLNameValue(parameter);

			if (graphQLName == null) {
				parameterName = NamingKit.toGraphqlName(parameter.getName());
			}
			else {
				parameterName = NamingKit.toGraphqlName(graphQLName);
			}

			args[i] = arguments.get(parameterName);
		}

		return method.invoke(instance, args);
	}

	private Object _getAnnotationValue(
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

	private String _getGraphQLNameValue(AnnotatedElement annotatedElement) {
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

			_collectObjectFields(
				mutationBuilder, ServletData::getMutation,
				processingElementsContainer);

			_collectObjectFields(
				queryBuilder, ServletData::getQuery,
				processingElementsContainer);

			schemaBuilder.mutation(mutationBuilder.build());
			schemaBuilder.query(queryBuilder.build());

			SimpleGraphQLHttpServlet.Builder servletBuilder =
				SimpleGraphQLHttpServlet.newBuilder(schemaBuilder.build());

			GraphQLObjectMapper.Builder objectMapperBuilder =
				GraphQLObjectMapper.newBuilder();

			objectMapperBuilder.withGraphQLErrorHandler(
				new LiferayGraphQLErrorHandler());

			servletBuilder.withObjectMapper(objectMapperBuilder.build());

			_servlet = servletBuilder.build();

			return _servlet;
		}
	}

	private Integer _getVersion(Method method) {
		Class<?> clazz = method.getDeclaringClass();

		Package pkg = clazz.getPackage();

		String packageString = pkg.toString();

		String[] packageNames = packageString.split("\\.");

		String version = packageNames[packageNames.length - 1];

		return Integer.valueOf(version.replaceAll("\\D", ""));
	}

	private static final GraphQLScalarType _dateGraphQLScalarType =
		new GraphQLScalarType(
			"Date", "An RFC-3339 compliant date time scalar",
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

			});

	private static final GraphQLScalarType _objectGraphQLScalarType =
		new GraphQLScalarType(
			"Object", "Any kind of object supported by basic scalar types",
			new Coercing<Object, Object>() {

				@Override
				public Object parseLiteral(Object value)
					throws CoercingParseLiteralException {

					if (value instanceof ArrayValue) {
						ArrayValue arrayValue = (ArrayValue)value;

						List<Value> values = arrayValue.getValues();

						return values.stream(
						).map(
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

						return objectFields.stream(
						).collect(
							Collectors.toMap(
								ObjectField::getName,
								objectField -> parseLiteral(
									objectField.getValue()))
						);
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

			});

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

	private class DateTypeFunction implements TypeFunction {

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

	private class LiferayArgumentBuilder extends ArgumentBuilder {

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
			return Arrays.stream(
				_method.getParameters()
			).filter(
				parameter -> !DataFetchingEnvironment.class.isAssignableFrom(
					parameter.getType())
			).map(
				parameter -> _getArgument(
					parameter,
					(GraphQLInputType)_typeFunction.buildType(
						true, parameter.getType(), parameter.getAnnotatedType(),
						_processingElementsContainer))
			).collect(
				Collectors.toList()
			);
		}

		private GraphQLArgument _getArgument(
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

	private class LiferayBreadthFirstSearch extends BreadthFirstSearch {

		@Override
		public boolean isFound(Member member) throws CannotCastMemberException {
			Method method = _toMethod(member);

			List<Class<?>> classes = new LinkedList<>();

			Class<?>[] parameterTypes = method.getParameterTypes();

			classes.add(method.getDeclaringClass());

			do {
				Class<?> clazz = classes.remove(0);

				try {
					method = clazz.getDeclaredMethod(
						method.getName(), parameterTypes);

					Boolean graphQLField = _getGraphQLFieldValue(method);

					if (graphQLField != null) {
						return graphQLField;
					}
				}
				catch (NoSuchMethodException nsme) {

					// Continue searching by class

				}

				Boolean graphQLField = _getGraphQLFieldValue(clazz);

				if (graphQLField != null) {
					return graphQLField;
				}

				Collections.addAll(classes, clazz.getInterfaces());

				Class<?> nextClass = clazz.getSuperclass();

				if (nextClass != null) {
					classes.add(nextClass);
				}
			}
			while (!classes.isEmpty());

			return false;
		}

		private LiferayBreadthFirstSearch(
			GraphQLObjectInfoRetriever graphQLObjectInfoRetriever) {

			super(graphQLObjectInfoRetriever);
		}

		private Method _toMethod(Member member)
			throws CannotCastMemberException {

			if (!(member instanceof Method)) {
				throw new CannotCastMemberException(member.getName(), "Method");
			}

			return (Method)member;
		}

	}

	private class LiferayDeprecateBuilder extends DeprecateBuilder {

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

	private class LiferayGraphQLErrorHandler
		extends DefaultGraphQLErrorHandler {

		@Override
		protected List<GraphQLError> filterGraphQLErrors(
			List<GraphQLError> graphQLErrors) {

			return graphQLErrors.stream(
			).map(
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
				return _get(dataFetchingEnvironment, _method);
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

	private class LiferayParentalSearch extends ParentalSearch {

		public LiferayParentalSearch(
			GraphQLObjectInfoRetriever graphQLObjectInfoRetriever) {

			super(graphQLObjectInfoRetriever);
		}

		@Override
		public boolean isFound(Member member) throws CannotCastMemberException {
			Field field = _toField(member);

			Boolean graphQLField = _getGraphQLFieldValue(field);

			if (graphQLField != null) {
				return graphQLField;
			}

			Class<?> clazz = field.getDeclaringClass();

			do {
				graphQLField = _getGraphQLFieldValue(clazz);

				if (graphQLField != null) {
					return graphQLField;
				}

				clazz = clazz.getSuperclass();
			}
			while (clazz != null);

			return false;
		}

		private Field _toField(Member member) throws CannotCastMemberException {
			if (!(member instanceof Field)) {
				throw new CannotCastMemberException(member.getName(), "Field");
			}

			return (Field)member;
		}

	}

	private class ObjectTypeFunction implements TypeFunction {

		@Override
		public GraphQLType buildType(
			boolean input, Class<?> clazz, AnnotatedType annotatedType,
			ProcessingElementsContainer processingElementsContainer) {

			return _objectGraphQLScalarType;
		}

		@Override
		public boolean canBuildType(
			Class<?> clazz, AnnotatedType annotatedType) {

			if ((clazz == Filter.class) || (clazz == Map.class) ||
				(clazz == MultipartBody.class) || (clazz == Object.class) ||
				(clazz == Response.class) || (clazz == Sort[].class)) {

				return true;
			}

			return false;
		}

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