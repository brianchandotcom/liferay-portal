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

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContextThreadLocal;
import com.liferay.portal.vulcan.internal.fields.servlet.NestedFieldsHttpServletRequestWrapper;
import com.liferay.portal.vulcan.pagination.Page;

import java.io.IOException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.jaxrs.provider.ProviderFactory;
import org.apache.cxf.message.Message;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Ivica Cardic
 */
@Provider
public class NestedFieldsWriterInterceptor implements WriterInterceptor {

	public NestedFieldsWriterInterceptor(BundleContext bundleContext) {
		this(bundleContext, null);
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
			_setFieldValues(
				writerInterceptorContext.getEntity(),
				nestedFieldsContext.getFieldNames(), nestedFieldsContext);
		}
		catch (Exception exception) {
			_log.error(exception.getMessage(), exception);

			throw new WebApplicationException(exception);
		}

		writerInterceptorContext.proceed();
	}

	public void destroy() {
		_serviceTracker.close();
	}

	protected NestedFieldsWriterInterceptor(
		BundleContext bundleContext,
		NestedFieldServiceTrackerCustomizer
			nestedFieldServiceTrackerCustomizer) {

		if (nestedFieldServiceTrackerCustomizer == null) {
			nestedFieldServiceTrackerCustomizer =
				new NestedFieldServiceTrackerCustomizer(bundleContext);
		}

		_nestedFieldServiceTrackerCustomizer =
			nestedFieldServiceTrackerCustomizer;

		_serviceTracker = new ServiceTracker<>(
			bundleContext, NestedFieldSupport.class.getName(),
			_nestedFieldServiceTrackerCustomizer);

		_serviceTracker.open();
	}

	protected static class NestedFieldServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, List<FactoryKey>> {

		@Override
		public List<FactoryKey> addingService(
			ServiceReference<Object> serviceReference) {

			Object resource = _bundleContext.getService(serviceReference);

			Class<?> resourceClass = resource.getClass();

			List<FactoryKey> factoryKeys = null;

			for (Method resourceMethod : resourceClass.getDeclaredMethods()) {
				NestedField nestedField = resourceMethod.getAnnotation(
					NestedField.class);

				if (nestedField == null) {
					continue;
				}

				Class<?> parentClass = nestedField.parentClass();

				FactoryKey factoryKey = new FactoryKey(
					nestedField.value(), parentClass,
					_getAPIVersion(resourceClass.getSuperclass()));

				ServiceObjects<Object> serviceObjects =
					_bundleContext.getServiceObjects(serviceReference);

				_unsafeTriFunctions.put(
					factoryKey,
					(fieldName, item, nestedFieldsContext) ->
						_getNestedFieldValue(
							fieldName, item, nestedFieldsContext,
							resourceMethod,
							_getResourceMethodArgNameTypeEntries(
								resourceClass, resourceMethod),
							serviceObjects));

				if (factoryKeys == null) {
					factoryKeys = new ArrayList<>();
				}

				factoryKeys.add(factoryKey);
			}

			return factoryKeys;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference,
			List<FactoryKey> factoryKeys) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference,
			List<FactoryKey> factoryKeys) {

			factoryKeys.forEach(_unsafeTriFunctions::remove);

			_bundleContext.ungetService(serviceReference);
		}

		protected NestedFieldServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		protected HttpServletRequest getHttpServletRequest(Message message) {
			return (HttpServletRequest)message.getContextualProperty(
				"HTTP.REQUEST");
		}

		protected ProviderFactory getProviderFactory(Message message) {
			return ProviderFactory.getInstance(message);
		}

		private Object _convert(String value, Class<?> type) {
			if (value == null) {
				return null;
			}

			return _objectMapper.convertValue(value, type);
		}

		private String _getAPIVersion(Class<?> resourceBaseClass) {
			Annotation[] annotations = resourceBaseClass.getAnnotations();

			for (Annotation annotation : annotations) {
				if (annotation instanceof Path) {
					Path path = (Path)annotation;

					String resourceVersion = path.value();

					return resourceVersion.substring(1);
				}
			}

			return null;
		}

		private <T> Object _getContext(Class<T> contextClass, Message message) {
			if (contextClass.equals(UriInfo.class)) {
				return new UriInfoImpl(message);
			}

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

		private Object[] _getMethodArgs(
				String fieldName, Object item,
				NestedFieldsContext nestedFieldsContext, Method resourceMethod,
				Map.Entry<String, Class<?>>[] resourceMethodArgNameTypeEntries)
			throws Exception {

			Object[] args = new Object[resourceMethod.getParameterCount()];

			MultivaluedMap<String, String> pathParameters =
				nestedFieldsContext.getPathParameters();

			MultivaluedMap<String, String> queryParameters =
				nestedFieldsContext.getQueryParameters();

			for (int i = 0; i < resourceMethod.getParameterCount(); i++) {
				if (resourceMethodArgNameTypeEntries[i] == null) {
					continue;
				}

				args[i] = _getMethodArgValueFromItem(
					item, resourceMethodArgNameTypeEntries[i]);

				if (args[i] == null) {
					args[i] = _getMethodArgValueFromRequest(
						fieldName, nestedFieldsContext, pathParameters,
						queryParameters, resourceMethodArgNameTypeEntries[i]);
				}
			}

			return args;
		}

		private Object _getMethodArgValueFromItem(
				Object item,
				Map.Entry<String, Class<?>> resourceMethodArgNameTypeEntry)
			throws Exception {

			List<Class<?>> itemClasses = new ArrayList<>();

			Class<?> itemClass = item.getClass();

			itemClasses.add(itemClass);

			itemClasses.add(itemClass.getSuperclass());

			for (Class<?> curItemClass : itemClasses) {
				try {
					Field itemField = curItemClass.getDeclaredField(
						resourceMethodArgNameTypeEntry.getKey());

					if (itemField != null) {
						itemField.setAccessible(true);

						return itemField.get(item);
					}
				}
				catch (NoSuchFieldException noSuchFieldException) {
					if (_log.isDebugEnabled()) {
						_log.debug(noSuchFieldException.getMessage());
					}
				}
			}

			return null;
		}

		private Object _getMethodArgValueFromRequest(
			String fieldName, NestedFieldsContext nestedFieldsContext,
			MultivaluedMap<String, String> pathParameters,
			MultivaluedMap<String, String> queryParameters,
			Map.Entry<String, Class<?>> resourceMethodArgNameTypeEntry) {

			Object argValue = null;

			Class<?> resourceMethodArgType =
				resourceMethodArgNameTypeEntry.getValue();

			Message message = _getNestedFieldsAwareMessage(
				fieldName, nestedFieldsContext.getMessage());

			Object context = _getContext(resourceMethodArgType, message);

			if (context != null) {
				argValue = context;

				_resetNestedAwareMessage(message);
			}
			else {
				argValue = _convert(
					pathParameters.getFirst(
						resourceMethodArgNameTypeEntry.getKey()),
					resourceMethodArgType);

				if (argValue == null) {
					argValue = _convert(
						queryParameters.getFirst(
							fieldName + StringPool.PERIOD +
								resourceMethodArgNameTypeEntry.getKey()),
						resourceMethodArgType);
				}
			}

			return argValue;
		}

		private Message _getNestedFieldsAwareMessage(
			String fieldName, Message message) {

			message.put(
				"HTTP.REQUEST",
				new NestedFieldsHttpServletRequestWrapper(
					fieldName, getHttpServletRequest(message)));

			return message;
		}

		private Object _getNestedFieldValue(
				String fieldName, Object item,
				NestedFieldsContext nestedFieldsContext, Method resourceMethod,
				Map.Entry<String, Class<?>>[] resourceMethodArgNameTypeEntries,
				ServiceObjects<Object> serviceObjects)
			throws Exception {

			Object resource = serviceObjects.getService();

			try {
				_setResourceContexts(
					nestedFieldsContext.getMessage(), resource);

				Object[] args = _getMethodArgs(
					fieldName, item, nestedFieldsContext, resourceMethod,
					resourceMethodArgNameTypeEntries);

				return resourceMethod.invoke(resource, args);
			}
			finally {
				serviceObjects.ungetService(resource);
			}
		}

		private Map.Entry<String, Class<?>>[]
			_getResourceMethodArgNameTypeEntries(
				Class<?> resourceClass, Method resourceMethod) {

			Parameter[] resourceMethodParameters =
				resourceMethod.getParameters();

			Map.Entry<String, Class<?>>[] resourceMethodArgNameTypeEntries =
				new Map.Entry[resourceMethodParameters.length];

			Parameter[] parentParameters = null;

			try {
				Class<?> parentResourceClass = resourceClass.getSuperclass();

				Method parentResourceMethod = parentResourceClass.getMethod(
					resourceMethod.getName(),
					resourceMethod.getParameterTypes());

				parentParameters = parentResourceMethod.getParameters();
			}
			catch (NoSuchMethodException noSuchMethodException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchMethodException.getMessage());
				}
			}

			for (int i = 0; i < resourceMethodParameters.length; i++) {
				Parameter parameter = resourceMethodParameters[i];

				NestedFieldId nestedFieldId = _getAnnotation(
					NestedFieldId.class, parameter, parentParameters, i);

				Class<?> parameterType = parameter.getType();

				if (nestedFieldId == null) {
					Context context = _getAnnotation(
						Context.class, parameter, parentParameters, i);

					if (context != null) {
						resourceMethodArgNameTypeEntries[i] =
							new AbstractMap.SimpleImmutableEntry<>(
								parameter.getName(), parameterType);
					}

					PathParam pathParam = _getAnnotation(
						PathParam.class, parameter, parentParameters, i);

					if (pathParam != null) {
						resourceMethodArgNameTypeEntries[i] =
							new AbstractMap.SimpleImmutableEntry<>(
								pathParam.value(), parameterType);
					}

					QueryParam queryParam = _getAnnotation(
						QueryParam.class, parameter, parentParameters, i);

					if (queryParam != null) {
						resourceMethodArgNameTypeEntries[i] =
							new AbstractMap.SimpleImmutableEntry<>(
								queryParam.value(), parameterType);
					}
				}
				else {
					resourceMethodArgNameTypeEntries[i] =
						new AbstractMap.SimpleImmutableEntry<>(
							nestedFieldId.value(), parameterType);
				}
			}

			return resourceMethodArgNameTypeEntries;
		}

		private void _resetNestedAwareMessage(Message message) {
			NestedFieldsHttpServletRequestWrapper
				nestedFieldsHttpServletRequestWrapper =
					(NestedFieldsHttpServletRequestWrapper)
						message.getContextualProperty("HTTP.REQUEST");

			message.put(
				"HTTP.REQUEST",
				nestedFieldsHttpServletRequestWrapper.getRequest());
		}

		private void _setContextFields(
				Field[] fields, Message message, Object resource)
			throws Exception {

			for (Field field : fields) {
				String name = field.getName();

				if (name.startsWith("context") ||
					(field.getAnnotation(Context.class) != null)) {

					field.setAccessible(true);

					field.set(resource, _getContext(field.getType(), message));
				}
			}
		}

		private void _setResourceContexts(Message message, Object resource)
			throws Exception {

			Class<?> resourceClass = resource.getClass();

			_setContextFields(
				resourceClass.getDeclaredFields(), message, resource);

			Class<?> superClass = resourceClass.getSuperclass();

			_setContextFields(
				superClass.getDeclaredFields(), message, resource);
		}

		private static final ObjectMapper _objectMapper = new ObjectMapper();

		private final BundleContext _bundleContext;
		private final Map
			<FactoryKey,
			 UnsafeTriFunction<String, Object, NestedFieldsContext, Object>>
				_unsafeTriFunctions = new ConcurrentHashMap<>();

	}

	@FunctionalInterface
	protected interface UnsafeTriFunction<A, B, C, R> {

		public R apply(A a, B b, C c) throws Exception;

	}

	private static <A extends Annotation> A _getAnnotation(
		Class<A> annotationClass, Parameter parameter,
		Parameter[] parentParameters, int i) {

		A annotation = parameter.getAnnotation(annotationClass);

		if ((annotation == null) && (parentParameters != null)) {
			return parentParameters[i].getAnnotation(annotationClass);
		}

		return annotation;
	}

	private Object _adaptToFieldType(Class<?> fieldType, Object value) {
		if (value instanceof Page) {
			Page<?> page = (Page)value;

			value = page.getItems();
		}

		if (fieldType.isArray() && (value instanceof Collection)) {
			Collection<Object> collection = (Collection)value;

			value = Array.newInstance(
				fieldType.getComponentType(), collection.size());

			int i = 0;

			for (Object object : collection) {
				Array.set(value, i++, object);
			}
		}

		return value;
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

	private List<Object> _getItems(Object entity) {
		List<Object> items = new ArrayList<>();

		if (entity instanceof Collection) {
			items.addAll((Collection)entity);
		}
		else if (entity instanceof Page) {
			Page<?> page = (Page)entity;

			items.addAll(page.getItems());
		}
		else if (_isArray(entity)) {
			Collections.addAll(items, (Object[])entity);
		}
		else {
			items.add(entity);
		}

		return items;
	}

	private UnsafeTriFunction<String, Object, NestedFieldsContext, Object>
		_getUnsafeTriFunction(
			String fieldName, Class<?> itemClass,
			NestedFieldsContext nestedFieldsContext) {

		Class<?>[] parentClasses = new Class<?>[] {
			Void.class, itemClass, itemClass.getSuperclass()
		};

		for (Class<?> parentClass : parentClasses) {
			FactoryKey factoryKey = new FactoryKey(
				fieldName, parentClass,
				nestedFieldsContext.getResourceVersion());

			UnsafeTriFunction<String, Object, NestedFieldsContext, Object>
				unsafeTriFunction =
					_nestedFieldServiceTrackerCustomizer._unsafeTriFunctions.
						get(factoryKey);

			if (unsafeTriFunction != null) {
				return unsafeTriFunction;
			}
		}

		return null;
	}

	private boolean _isArray(Object object) {
		Class<?> objectClass = object.getClass();

		return objectClass.isArray();
	}

	private void _setFieldValues(
			Object entity, List<String> fieldNames,
			NestedFieldsContext nestedFieldsContext)
		throws Exception {

		List<Object> items = _getItems(entity);

		for (String fieldName : fieldNames) {
			String nestedField = null;

			int index = fieldName.indexOf(".");

			if (index != -1) {
				nestedField = fieldName.substring(index + 1);

				fieldName = fieldName.substring(0, index);
			}

			for (Object item : items) {
				Class<?> itemClass = item.getClass();

				Field field = _getField(itemClass, fieldName);

				if (field == null) {
					continue;
				}

				field.setAccessible(true);

				UnsafeTriFunction<String, Object, NestedFieldsContext, Object>
					unsafeTriFunction = _getUnsafeTriFunction(
						fieldName, itemClass, nestedFieldsContext);

				if (unsafeTriFunction == null) {
					continue;
				}

				Object value = _adaptToFieldType(
					field.getType(),
					unsafeTriFunction.apply(
						fieldName, item, nestedFieldsContext));

				field.set(item, value);

				if (nestedField != null) {
					_setFieldValues(
						value, Collections.singletonList(nestedField),
						nestedFieldsContext);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NestedFieldsWriterInterceptor.class);

	private final NestedFieldServiceTrackerCustomizer
		_nestedFieldServiceTrackerCustomizer;
	private final ServiceTracker<Object, List<FactoryKey>> _serviceTracker;

	private static class FactoryKey {

		@Override
		public boolean equals(Object object) {
			FactoryKey factoryKey = (FactoryKey)object;

			if (Objects.equals(factoryKey._nestedFieldName, _nestedFieldName) &&
				Objects.equals(factoryKey._parentClass, _parentClass) &&
				Objects.equals(factoryKey._resourceVersion, _resourceVersion)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _nestedFieldName);

			hashCode = HashUtil.hash(hashCode, _parentClass);
			hashCode = HashUtil.hash(hashCode, _resourceVersion);

			return hashCode;
		}

		private FactoryKey(
			String nestedFieldName, Class<?> parentClass,
			String resourceVersion) {

			_nestedFieldName = nestedFieldName;
			_parentClass = parentClass;
			_resourceVersion = resourceVersion;
		}

		private final String _nestedFieldName;
		private final Class<?> _parentClass;
		private final String _resourceVersion;

	}

}