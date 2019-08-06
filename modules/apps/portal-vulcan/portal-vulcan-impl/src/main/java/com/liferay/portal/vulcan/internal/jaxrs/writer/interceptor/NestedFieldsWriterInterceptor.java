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

package com.liferay.portal.vulcan.internal.jaxrs.writer.interceptor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.internal.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.internal.fields.NestedFieldsContextThreadLocal;
import com.liferay.portal.vulcan.internal.fields.servlet.NestedFieldsHttpServletRequestWrapper;
import com.liferay.portal.vulcan.internal.param.converter.DateParamConverter;
import com.liferay.portal.vulcan.pagination.Page;

import java.io.IOException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * @author Ivica Cardic
 */
@Provider
public class NestedFieldsWriterInterceptor implements WriterInterceptor {

	public NestedFieldsWriterInterceptor(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext)
		throws IOException, WebApplicationException {

		NestedFieldsContext nestedFieldsContext =
			NestedFieldsContextThreadLocal.getNestedFieldsContext();

		if (nestedFieldsContext == null) {
			writerInterceptorContext.proceed();

			return;
		}

		try {
			_setFieldValue(
				writerInterceptorContext.getEntity(), nestedFieldsContext);
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);

			throw new WebApplicationException(e);
		}

		writerInterceptorContext.proceed();
	}

	protected List<ContextProvider> getContextProviders()
		throws InvalidSyntaxException {

		List<ContextProvider> contextProviders = new ArrayList<>();

		Collection<ServiceReference<ContextProvider>> serviceReferences =
			_bundleContext.getServiceReferences(ContextProvider.class, null);

		for (ServiceReference<ContextProvider> serviceReference :
				serviceReferences) {

			ContextProvider contextProvider = _bundleContext.getService(
				serviceReference);

			contextProviders.add(contextProvider);
		}

		return contextProviders;
	}

	protected HttpServletRequest getHttpServletRequest(Message message) {
		return (HttpServletRequest)message.getContextualProperty(
			"HTTP.REQUEST");
	}

	protected List<Object> getResources() throws InvalidSyntaxException {
		List<Object> resources = new ArrayList<>();

		ServiceReference<?>[] serviceReferences =
			_bundleContext.getAllServiceReferences(
				null, "(osgi.jaxrs.resource=true)");

		for (ServiceReference<?> serviceReference : serviceReferences) {
			resources.add(_bundleContext.getService(serviceReference));
		}

		return resources;
	}

	private Object _convert(String value, Class<?> type) {
		if (value == null) {
			return null;
		}

		if (type == BigDecimal.class) {
			return new BigDecimal(value);
		}
		else if (type == Boolean.class) {
			return Boolean.valueOf(value);
		}
		else if (type == Byte.class) {
			return Byte.valueOf(value);
		}
		else if (type == Character.class) {
			return value.charAt(0);
		}
		else if (type == Date.class) {
			return _dateParamConverter.fromString(value);
		}
		else if (type == Double.class) {
			return Double.valueOf(value);
		}
		else if (type == Float.class) {
			return Float.valueOf(value);
		}
		else if (type == Integer.class) {
			return Integer.valueOf(value);
		}
		else if (type == LocalDate.class) {
			return LocalDate.parse(value);
		}
		else if (type == LocalDateTime.class) {
			return LocalDateTime.parse(value);
		}
		else if (type == Long.class) {
			return Long.valueOf(value);
		}
		else if (type == Short.class) {
			return Short.valueOf(value);
		}
		else if (type == String.class) {
			return value;
		}
		else {
			throw new IllegalArgumentException(
				String.format(
					"value %s cannot be converted to %s", value, type));
		}
	}

	private Method _getAnnotatedMethod(Class<?> clazz, String fieldName) {
		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods) {
			if (method.isAnnotationPresent(NestedField.class)) {
				NestedField nestedField = method.getAnnotation(
					NestedField.class);

				if (Objects.equals(fieldName, nestedField.value())) {
					return method;
				}
			}
		}

		return null;
	}

	private Parameter[] _getBaseResourceClassMethodParameters(
			Class<?> clazz, Method implMethod)
		throws NoSuchMethodException {

		Class<?> baseResourceClass = clazz.getSuperclass();

		Method interfaceMethod = baseResourceClass.getMethod(
			implMethod.getName(), implMethod.getParameterTypes());

		return interfaceMethod.getParameters();
	}

	private Object _getContext(Class<?> contextClass, Message message)
		throws InvalidSyntaxException {

		ContextProvider contextProvider = _getContextProvider(contextClass);

		if (contextProvider != null) {
			return contextProvider.createContext(message);
		}

		return null;
	}

	private ContextProvider _getContextProvider(Class<?> contextClass)
		throws InvalidSyntaxException {

		for (ContextProvider contextProvider : getContextProviders()) {
			if (_isRelevantContextProvider(contextClass, contextProvider)) {
				return contextProvider;
			}
		}

		return null;
	}

	private Field _getField(Class<?> entityClass, String fieldName) {
		List<Field> fields = new ArrayList<>(
			Arrays.asList(entityClass.getDeclaredFields()));

		Class<?> superClass = entityClass.getSuperclass();

		if (superClass != null) {
			Collections.addAll(fields, superClass.getDeclaredFields());
		}

		for (Field field : fields) {
			if (Objects.equals(field.getName(), fieldName) ||
				Objects.equals(field.getName(), "_" + fieldName)) {

				return field;
			}
		}

		return null;
	}

	private Object _getFieldValue(
			String fieldName, NestedFieldsContext nestedFieldsContext)
		throws Exception {

		for (Object resource : getResources()) {
			Method method = _getAnnotatedMethod(resource.getClass(), fieldName);

			if (method == null) {
				continue;
			}

			_setResourceContexts(resource, nestedFieldsContext.getMessage());

			if (!Modifier.isPublic(method.getModifiers())) {
				throw new IllegalAccessException(
					"Method with the NestedField annotation must be defined " +
						"in an abstract class");
			}

			Parameter[] parameters = _getBaseResourceClassMethodParameters(
				resource.getClass(), method);

			Object[] args = _getMethodArgs(
				fieldName, nestedFieldsContext, parameters);

			return method.invoke(resource, args);
		}

		return null;
	}

	private Object[] _getMethodArgs(
			String fieldName, NestedFieldsContext nestedFieldsContext,
			Parameter[] parameters)
		throws Exception {

		Object[] args = new Object[parameters.length];

		MultivaluedMap<String, String> pathParameters =
			nestedFieldsContext.getPathParameters();
		MultivaluedMap<String, String> queryParameters =
			nestedFieldsContext.getQueryParameters();

		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];

			Annotation[] annotations = parameter.getAnnotations();

			if (annotations.length == 0) {
				continue;
			}

			args[i] = null;

			for (Annotation annotation : annotations) {
				if (annotation instanceof Context) {
					Message message = _getNestedAwareMessage(
						fieldName, nestedFieldsContext.getMessage());

					args[i] = _getContext(parameter.getType(), message);

					_resetNestedAwareMessage(message);

					break;
				}
				else if (annotation instanceof PathParam) {
					PathParam pathParam = (PathParam)annotation;

					args[i] = _convert(
						pathParameters.getFirst(pathParam.value()),
						parameter.getType());

					break;
				}
				else if (annotation instanceof QueryParam) {
					QueryParam queryParam = (QueryParam)annotation;

					args[i] = _convert(
						queryParameters.getFirst(
							fieldName + "." + queryParam.value()),
						parameter.getType());

					break;
				}
			}
		}

		return args;
	}

	private Message _getNestedAwareMessage(String fieldName, Message message) {
		message.put(
			"HTTP.REQUEST",
			new NestedFieldsHttpServletRequestWrapper(
				fieldName, getHttpServletRequest(message)));

		return message;
	}

	private Object _getReturnObject(Class<?> fieldType, Object result) {
		if (result instanceof Page) {
			Page page = (Page)result;

			result = page.getItems();
		}

		if (fieldType.isArray() && (result instanceof Collection)) {
			Collection collection = (Collection)result;

			result = Array.newInstance(
				fieldType.getComponentType(), collection.size());

			Iterator iterator = collection.iterator();

			int i = 0;

			while (iterator.hasNext()) {
				Array.set(result, i++, iterator.next());
			}
		}

		return result;
	}

	private <T> boolean _isRelevantContextProvider(
		Class<T> contextClass, ContextProvider contextProvider) {

		Class<? extends ContextProvider> contextProviderClass =
			contextProvider.getClass();

		Type[] genericInterfaceTypes =
			contextProviderClass.getGenericInterfaces();

		for (Type type : genericInterfaceTypes) {
			if (!(type instanceof ParameterizedType)) {
				continue;
			}

			ParameterizedType parameterizedType = (ParameterizedType)type;

			Type[] typeArguments = parameterizedType.getActualTypeArguments();

			if (typeArguments[0] == contextClass) {
				return true;
			}
		}

		return false;
	}

	private void _resetNestedAwareMessage(Message message) {
		NestedFieldsHttpServletRequestWrapper
			nestedFieldsHttpServletRequestWrapper =
				(NestedFieldsHttpServletRequestWrapper)
					message.getContextualProperty("HTTP.REQUEST");

		message.put(
			"HTTP.REQUEST", nestedFieldsHttpServletRequestWrapper.getRequest());
	}

	private void _setFieldValue(
			Object entity, NestedFieldsContext nestedFieldsContext)
		throws Exception {

		for (String fieldName : nestedFieldsContext.getFieldNames()) {
			Field field = _getField(entity.getClass(), fieldName);

			if (field == null) {
				continue;
			}

			field.setAccessible(true);

			field.set(
				entity,
				_getReturnObject(
					field.getType(),
					_getFieldValue(fieldName, nestedFieldsContext)));
		}
	}

	private void _setResourceContexts(Object resource, Message message)
		throws Exception {

		Class<?> resourceClass = resource.getClass();

		Field[] fields = resourceClass.getDeclaredFields();

		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();

			if (annotations.length == 0) {
				continue;
			}

			if (annotations[0] instanceof Context) {
				field.setAccessible(true);

				field.set(resource, _getContext(field.getType(), message));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NestedFieldsWriterInterceptor.class);

	private final BundleContext _bundleContext;
	private final DateParamConverter _dateParamConverter =
		new DateParamConverter();

}