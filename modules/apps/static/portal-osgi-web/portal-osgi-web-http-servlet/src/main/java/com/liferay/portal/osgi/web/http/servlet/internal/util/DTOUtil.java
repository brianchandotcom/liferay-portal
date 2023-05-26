/*******************************************************************************
 * Copyright (c) Feb 23, 2015 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Raymond Augé <raymond.auge@liferay.com> - Bug 460639
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.util;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.dto.DTO;
import org.osgi.service.http.runtime.dto.ErrorPageDTO;
import org.osgi.service.http.runtime.dto.FailedFilterDTO;
import org.osgi.service.http.runtime.dto.FailedListenerDTO;
import org.osgi.service.http.runtime.dto.FailedResourceDTO;
import org.osgi.service.http.runtime.dto.FailedServletContextDTO;
import org.osgi.service.http.runtime.dto.FailedServletDTO;
import org.osgi.service.http.runtime.dto.FilterDTO;
import org.osgi.service.http.runtime.dto.ListenerDTO;
import org.osgi.service.http.runtime.dto.ResourceDTO;
import org.osgi.service.http.runtime.dto.ServletDTO;

/**
 * @author Raymond Augé
 */
public class DTOUtil {

	public static ErrorPageDTO clone(ErrorPageDTO original) {
		ErrorPageDTO clone = new ErrorPageDTO();

		clone.asyncSupported = _copy(original.asyncSupported);
		clone.errorCodes = _copy(original.errorCodes);
		clone.exceptions = _copy(original.exceptions);
		clone.initParams = _copyStringMap(original.initParams);
		clone.name = _copy(original.name);
		clone.serviceId = _copy(original.serviceId);
		clone.servletContextId = _copy(original.servletContextId);
		clone.servletInfo = _copy(original.servletInfo);

		return clone;
	}

	public static FailedFilterDTO clone(FailedFilterDTO original) {
		FailedFilterDTO clone = new FailedFilterDTO();

		clone.asyncSupported = _copy(original.asyncSupported);
		clone.dispatcher = _copy(original.dispatcher);
		clone.failureReason = _copy(original.failureReason);
		clone.initParams = _copyStringMap(original.initParams);
		clone.name = _copy(original.name);
		clone.patterns = _copy(original.patterns);
		clone.regexs = _copy(original.regexs);
		clone.serviceId = _copy(original.serviceId);
		clone.servletContextId = _copy(original.servletContextId);
		clone.servletNames = _copy(original.servletNames);

		return clone;
	}

	public static FailedListenerDTO clone(FailedListenerDTO original) {
		FailedListenerDTO clone = new FailedListenerDTO();

		clone.failureReason = _copy(original.failureReason);
		clone.serviceId = _copy(original.serviceId);
		clone.servletContextId = _copy(original.servletContextId);
		clone.types = _copy(original.types);

		return clone;
	}

	public static FailedResourceDTO clone(FailedResourceDTO original) {
		FailedResourceDTO clone = new FailedResourceDTO();

		clone.failureReason = _copy(original.failureReason);
		clone.patterns = _copy(original.patterns);
		clone.prefix = _copy(original.prefix);
		clone.serviceId = _copy(original.serviceId);
		clone.servletContextId = _copy(original.servletContextId);

		return clone;
	}

	public static FailedServletContextDTO clone(
		FailedServletContextDTO original) {

		FailedServletContextDTO clone = new FailedServletContextDTO();

		clone.attributes = copyGenericMap(original.attributes);
		clone.contextPath = _copy(original.contextPath);
		clone.errorPageDTOs = _copy(original.errorPageDTOs);
		clone.failureReason = _copy(original.failureReason);
		clone.filterDTOs = _copy(original.filterDTOs);
		clone.initParams = _copyStringMap(original.initParams);
		clone.listenerDTOs = _copy(original.listenerDTOs);
		clone.name = _copy(original.name);
		clone.resourceDTOs = _copy(original.resourceDTOs);
		clone.serviceId = _copy(original.serviceId);
		clone.servletDTOs = _copy(original.servletDTOs);

		return clone;
	}

	public static FailedServletDTO clone(FailedServletDTO original) {
		FailedServletDTO clone = new FailedServletDTO();

		clone.asyncSupported = _copy(original.asyncSupported);
		clone.failureReason = _copy(original.failureReason);
		clone.initParams = _copyStringMap(clone.initParams);
		clone.name = _copy(original.name);
		clone.patterns = _copy(original.patterns);
		clone.serviceId = _copy(original.serviceId);
		clone.servletContextId = _copy(original.servletContextId);
		clone.servletInfo = _copy(original.servletInfo);

		return clone;
	}

	public static FilterDTO clone(FilterDTO original) {
		FilterDTO clone = new FilterDTO();

		clone.asyncSupported = _copy(original.asyncSupported);
		clone.dispatcher = _copy(original.dispatcher);
		clone.initParams = _copyStringMap(original.initParams);
		clone.name = _copy(original.name);
		clone.patterns = _copy(original.patterns);
		clone.regexs = _copy(original.regexs);
		clone.serviceId = _copy(original.serviceId);
		clone.servletContextId = _copy(original.servletContextId);
		clone.servletNames = _copy(original.servletNames);

		return clone;
	}

	public static ListenerDTO clone(ListenerDTO original) {
		ListenerDTO clone = new ListenerDTO();

		clone.serviceId = _copy(original.serviceId);
		clone.servletContextId = _copy(original.servletContextId);
		clone.types = _copy(original.types);

		return clone;
	}

	public static ResourceDTO clone(ResourceDTO original) {
		ResourceDTO clone = new ResourceDTO();

		clone.patterns = _copy(original.patterns);
		clone.prefix = _copy(original.prefix);
		clone.serviceId = _copy(original.serviceId);
		clone.servletContextId = _copy(original.servletContextId);

		return clone;
	}

	public static ServletDTO clone(ServletDTO original) {
		ServletDTO clone = new ServletDTO();

		clone.asyncSupported = _copy(original.asyncSupported);
		clone.initParams = _copyStringMap(original.initParams);
		clone.name = _copy(original.name);
		clone.patterns = _copy(original.patterns);
		clone.serviceId = _copy(original.serviceId);
		clone.servletContextId = _copy(original.servletContextId);
		clone.servletInfo = _copy(original.servletInfo);

		return clone;
	}

	public static <V> Map<String, Object> copyGenericMap(Map<String, V> value) {
		if (value == null) {
			return null;
		}

		if (value.isEmpty()) {
			return Collections.emptyMap();
		}

		HashMap<String, Object> map = new HashMap<>();

		for (Map.Entry<String, V> entry : value.entrySet()) {
			map.put(entry.getKey(), mapValue(entry.getValue()));
		}

		return map;
	}

	public static Object mapValue(Object v) {
		if ((v == null) || (v instanceof Number) || (v instanceof Boolean) ||
			(v instanceof Character) || (v instanceof String) ||
			(v instanceof DTO)) {

			return v;
		}

		if (v instanceof Map) {
			Map<?, ?> m = (Map<?, ?>)v;

			Map<Object, Object> map = _newMap(m.size());

			for (Map.Entry<?, ?> e : m.entrySet()) {
				map.put(mapValue(e.getKey()), mapValue(e.getValue()));
			}

			return map;
		}

		if (v instanceof List) {
			List<?> c = (List<?>)v;

			List<Object> list = _newList(c.size());

			for (Object object : c) {
				list.add(mapValue(object));
			}

			return list;
		}

		if (v instanceof Set) {
			Set<?> c = (Set<?>)v;

			Set<Object> set = _newSet(c.size());

			for (Object object : c) {
				set.add(mapValue(object));
			}

			return set;
		}

		Class<?> clazz = v.getClass();

		if (clazz.isArray()) {
			int length = Array.getLength(v);

			Object array = Array.newInstance(
				_mapComponentType(clazz.getComponentType()), length);

			for (int i = 0; i < length; i++) {
				Array.set(array, i, mapValue(Array.get(v, i)));
			}

			return array;
		}

		return String.valueOf(v);
	}

	private static boolean _copy(boolean value) {
		return value;
	}

	private static int _copy(int value) {
		return value;
	}

	private static long _copy(long value) {
		return value;
	}

	private static long[] _copy(long[] array) {
		if (array == null) {
			return null;
		}

		if (array.length == 0) {
			return array;
		}

		return Arrays.copyOf(array, array.length);
	}

	private static String _copy(String value) {
		return value;
	}

	private static String[] _copy(String[] array) {
		if (array == null) {
			return null;
		}

		if (array.length == 0) {
			return array;
		}

		return Arrays.copyOf(array, array.length);
	}

	private static <T> T[] _copy(T[] array) {
		if (array == null) {
			return null;
		}

		if (array.length == 0) {
			return array;
		}

		return Arrays.copyOf(array, array.length);
	}

	private static Map<String, String> _copyStringMap(
		Map<String, String> initParams) {

		return new HashMap<>(initParams);
	}

	private static Class<?> _mapComponentType(Class<?> componentType) {
		if (componentType.isPrimitive() || componentType.isArray() ||
			Object.class.equals(componentType) ||
			Number.class.isAssignableFrom(componentType) ||
			Boolean.class.isAssignableFrom(componentType) ||
			Character.class.isAssignableFrom(componentType) ||
			String.class.isAssignableFrom(componentType) ||
			DTO.class.isAssignableFrom(componentType)) {

			return componentType;
		}

		if (Map.class.isAssignableFrom(componentType)) {
			return Map.class;
		}

		if (List.class.isAssignableFrom(componentType)) {
			return List.class;
		}

		if (Set.class.isAssignableFrom(componentType)) {
			return Set.class;
		}

		return String.class;
	}

	private static <E> List<E> _newList(int size) {
		return new ArrayList<>(size);
	}

	private static <K, V> Map<K, V> _newMap(int size) {
		return new HashMap<>(size);
	}

	private static <E> Set<E> _newSet(int size) {
		return new HashSet<>(size);
	}

}