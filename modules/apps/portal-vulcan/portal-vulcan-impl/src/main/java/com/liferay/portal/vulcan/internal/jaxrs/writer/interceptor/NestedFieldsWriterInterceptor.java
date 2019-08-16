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

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.jaxrs.provider.ProviderFactory;
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

	protected HttpServletRequest getHttpServletRequest(Message message) {
		return (HttpServletRequest)message.getContextualProperty(
			"HTTP.REQUEST");
	}

	protected ProviderFactory getProviderFactory(Message message) {
		return ProviderFactory.getInstance(message);
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

	private <T> Object _getContext(Class<T> contextClass, Message message) {
		ContextProvider<?> contextProvider = _getContextProvider(
			contextClass, message);

		if (contextProvider != null) {
			return contextProvider.createContext(message);
		}

		return null;
	}

	private <T> ContextProvider<T> _getContextProvider(
		Class<T> contextClass, Message message) {

		ProviderFactory providerFactory = getProviderFactory(message);

		return providerFactory.createContextProvider(contextClass, message);
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

	private Object _getFieldValue(String fieldName, Object item)
		throws Exception {

		List<Class> itemClasses = new ArrayList<>();

		Class<?> itemClass = item.getClass();

		itemClasses.add(itemClass);

		itemClasses.add(itemClass.getSuperclass());

		for (Class curItemClass : itemClasses) {
			try {
				Field itemField = curItemClass.getDeclaredField(fieldName);

				if (itemField != null) {
					itemField.setAccessible(true);

					return itemField.get(item);
				}
			}
			catch (NoSuchFieldException nsfe) {
				if (_log.isDebugEnabled()) {
					_log.debug(nsfe.getMessage());
				}
			}
		}

		return null;
	}

	private List<Object> _getItems(Object entity) {
		List<Object> items = new ArrayList<>();

		if (entity instanceof Collection) {
			items.addAll((Collection)entity);
		}
		else if (entity instanceof Page) {
			Page page = (Page)entity;

			items.addAll(page.getItems());
		}
		else {
			items.add(entity);
		}

		return items;
	}

	private Object[] _getMethodArgs(
			String fieldName, Object item,
			NestedFieldsContext nestedFieldsContext, Parameter[] parameters)
		throws Exception {

		Object[] args = new Object[parameters.length];

		MultivaluedMap<String, String> pathParameters =
			nestedFieldsContext.getPathParameters();
		MultivaluedMap<String, String> queryParameters =
			nestedFieldsContext.getQueryParameters();

		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];

			args[i] = _getMethodArgValueFromRequest(
				fieldName, nestedFieldsContext, pathParameters, queryParameters,
				parameter);

			if (args[i] == null) {
				args[i] = _getMethodArgValueFromItem(
					parameter.getAnnotations(), item);
			}
		}

		return args;
	}

	private Object _getMethodArgValueFromItem(
			Annotation[] annotations, Object item)
		throws Exception {

		if (annotations.length == 0) {
			return null;
		}

		Object fieldValue = null;

		for (Annotation annotation : annotations) {
			if (annotation instanceof PathParam) {
				PathParam pathParam = (PathParam)annotation;

				fieldValue = _getFieldValue(pathParam.value(), item);

				break;
			}
		}

		return fieldValue;
	}

	private Object _getMethodArgValueFromRequest(
		String fieldName, NestedFieldsContext nestedFieldsContext,
		MultivaluedMap<String, String> pathParameters,
		MultivaluedMap<String, String> queryParameters, Parameter parameter) {

		Annotation[] annotations = parameter.getAnnotations();

		if (annotations.length == 0) {
			return null;
		}

		Object argValue = null;

		for (Annotation annotation : annotations) {
			if (annotation instanceof Context) {
				Message message = _getNestedAwareMessage(
					fieldName, nestedFieldsContext.getMessage());

				argValue = _getContext(parameter.getType(), message);

				_resetNestedAwareMessage(message);

				break;
			}
			else if (annotation instanceof PathParam) {
				PathParam pathParam = (PathParam)annotation;

				argValue = _convert(
					pathParameters.getFirst(pathParam.value()),
					parameter.getType());

				break;
			}
			else if (annotation instanceof QueryParam) {
				QueryParam queryParam = (QueryParam)annotation;

				argValue = _convert(
					queryParameters.getFirst(
						fieldName + "." + queryParam.value()),
					parameter.getType());

				break;
			}
		}

		return argValue;
	}

	private Message _getNestedAwareMessage(String fieldName, Message message) {
		message.put(
			"HTTP.REQUEST",
			new NestedFieldsHttpServletRequestWrapper(
				fieldName, getHttpServletRequest(message)));

		return message;
	}

	private Object _getNestedFieldValue(
			String fieldName, Object item,
			NestedFieldsContext nestedFieldsContext)
		throws Exception {

		for (Object resource : getResources()) {
			Class<?> resourceClass = resource.getClass();

			Method method = _getAnnotatedMethod(resourceClass, fieldName);

			if (method == null) {
				continue;
			}

			if (!Objects.equals(
					nestedFieldsContext.getResourceVersion(),
					_getResourceVersion(resourceClass.getSuperclass()))) {

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
				fieldName, item, nestedFieldsContext, parameters);

			return method.invoke(resource, args);
		}

		return null;
	}

	private String _getResourceVersion(Class resourceBaseClass) {
		Annotation[] annotations = resourceBaseClass.getAnnotations();

		for (Annotation annotation : annotations) {
			if (annotation instanceof Path) {
				Path path = (Path)annotation;

				String resourceVersion = path.value();

				return resourceVersion.substring(1);
			}
		}

		throw new IllegalStateException(
			"No defined version for resource " + resourceBaseClass);
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

		List<Object> items = _getItems(entity);

		for (String fieldName : nestedFieldsContext.getFieldNames()) {
			for (Object item : items) {
				Field field = _getField(item.getClass(), fieldName);

				if (field == null) {
					continue;
				}

				field.setAccessible(true);

				field.set(
					item,
					_getReturnObject(
						field.getType(),
						_getNestedFieldValue(
							fieldName, item, nestedFieldsContext)));
			}
		}
	}

	private void _setResourceContexts(Object resource, Message message)
		throws Exception {

		Class<?> resourceClass = resource.getClass();

		_setResourceFields(
			resource, message, resourceClass.getDeclaredFields());

		Class<?> superClass = resourceClass.getSuperclass();

		_setResourceFields(resource, message, superClass.getDeclaredFields());
	}

	private void _setResourceFields(
			Object resource, Message message, Field[] fields)
		throws IllegalAccessException {

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