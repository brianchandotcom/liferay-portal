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

import static graphql.annotations.processor.util.NamingKit.toGraphqlName;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;

import graphql.annotations.GraphQLFieldDefinitionWrapper;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.processor.ProcessingElementsContainer;
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
import graphql.annotations.processor.util.NamingKit;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLOutputType;

import graphql.servlet.GraphQLContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class GraphQLServletExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		GraphQLFieldRetriever graphQLFieldRetriever =
			new LiferayGraphQLFieldRetriever();
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
	}

	@Deactivate
	protected void deactivate() {
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

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

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

}