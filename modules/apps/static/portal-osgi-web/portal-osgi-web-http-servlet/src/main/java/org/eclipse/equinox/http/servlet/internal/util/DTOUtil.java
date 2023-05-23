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

package org.eclipse.equinox.http.servlet.internal.util;

import java.lang.reflect.Array;
import java.util.*;
import org.osgi.dto.DTO;
import org.osgi.service.http.runtime.dto.*;

/**
 * @author Raymond Augé
 */
public class DTOUtil {

	public static ErrorPageDTO clone(ErrorPageDTO original) {
		ErrorPageDTO clone = new ErrorPageDTO();

		clone.asyncSupported = copy(original.asyncSupported);
		clone.errorCodes = copy(original.errorCodes);
		clone.exceptions = copy(original.exceptions);
		clone.initParams = copyStringMap(original.initParams);
		clone.name = copy(original.name);
		clone.serviceId = copy(original.serviceId);
		clone.servletContextId = copy(original.servletContextId);
		clone.servletInfo = copy(original.servletInfo);

		return clone;
	}

	public static FailedFilterDTO clone(FailedFilterDTO original) {
		FailedFilterDTO clone = new FailedFilterDTO();

		clone.asyncSupported = copy(original.asyncSupported);
		clone.dispatcher = copy(original.dispatcher);
		clone.failureReason = copy(original.failureReason);
		clone.initParams = copyStringMap(original.initParams);
		clone.name = copy(original.name);
		clone.patterns = copy(original.patterns);
		clone.regexs = copy(original.regexs);
		clone.serviceId = copy(original.serviceId);
		clone.servletContextId = copy(original.servletContextId);
		clone.servletNames = copy(original.servletNames);

		return clone;
	}

	public static FailedListenerDTO clone(FailedListenerDTO original) {
		FailedListenerDTO clone = new FailedListenerDTO();

		clone.failureReason = copy(original.failureReason);
		clone.serviceId = copy(original.serviceId);
		clone.servletContextId = copy(original.servletContextId);
		clone.types = copy(original.types);

		return clone;
	}

	public static FailedResourceDTO clone(FailedResourceDTO original) {
		FailedResourceDTO clone = new FailedResourceDTO();

		clone.failureReason = copy(original.failureReason);
		clone.patterns = copy(original.patterns);
		clone.prefix = copy(original.prefix);
		clone.serviceId = copy(original.serviceId);
		clone.servletContextId = copy(original.servletContextId);

		return clone;
	}

	public static FailedServletContextDTO clone(FailedServletContextDTO original) {
		FailedServletContextDTO clone = new FailedServletContextDTO();

		clone.attributes = copyGenericMap(original.attributes);
		clone.contextPath = copy(original.contextPath);
		clone.errorPageDTOs = copy(original.errorPageDTOs);
		clone.failureReason = copy(original.failureReason);
		clone.filterDTOs = copy(original.filterDTOs);
		clone.initParams = copyStringMap(original.initParams);
		clone.listenerDTOs = copy(original.listenerDTOs);
		clone.name = copy(original.name);
		clone.resourceDTOs = copy(original.resourceDTOs);
		clone.serviceId = copy(original.serviceId);
		clone.servletDTOs = copy(original.servletDTOs);

		return clone;
	}

	public static FailedServletDTO clone(FailedServletDTO original) {
		FailedServletDTO clone = new FailedServletDTO();

		clone.asyncSupported = copy(original.asyncSupported);
		clone.failureReason = copy(original.failureReason);
		clone.initParams = copyStringMap(clone.initParams);
		clone.name = copy(original.name);
		clone.patterns = copy(original.patterns);
		clone.serviceId = copy(original.serviceId);
		clone.servletContextId = copy(original.servletContextId);
		clone.servletInfo = copy(original.servletInfo);

		return clone;
	}

	public static FilterDTO clone(FilterDTO original) {
		FilterDTO clone = new FilterDTO();

		clone.asyncSupported = copy(original.asyncSupported);
		clone.dispatcher = copy(original.dispatcher);
		clone.initParams = copyStringMap(original.initParams);
		clone.name = copy(original.name);
		clone.patterns = copy(original.patterns);
		clone.regexs = copy(original.regexs);
		clone.serviceId = copy(original.serviceId);
		clone.servletContextId = copy(original.servletContextId);
		clone.servletNames = copy(original.servletNames);

		return clone;
	}

	public static ListenerDTO clone(ListenerDTO original) {
		ListenerDTO clone = new ListenerDTO();

		clone.serviceId = copy(original.serviceId);
		clone.servletContextId = copy(original.servletContextId);
		clone.types = copy(original.types);

		return clone;
	}

	public static ResourceDTO clone(ResourceDTO original) {
		ResourceDTO clone = new ResourceDTO();

		clone.patterns = copy(original.patterns);
		clone.prefix = copy(original.prefix);
		clone.serviceId = copy(original.serviceId);
		clone.servletContextId = copy(original.servletContextId);

		return clone;
	}

	public static ServletDTO clone(ServletDTO original) {
		ServletDTO clone = new ServletDTO();

		clone.asyncSupported = copy(original.asyncSupported);
		clone.initParams = copyStringMap(original.initParams);
		clone.name = copy(original.name);
		clone.patterns = copy(original.patterns);
		clone.serviceId = copy(original.serviceId);
		clone.servletContextId = copy(original.servletContextId);
		clone.servletInfo = copy(original.servletInfo);

		return clone;
	}

	private static long[] copy(long[] array) {
		if (array == null) {
			return null;
		}
		if (array.length == 0) {
			return array;
		}
		return Arrays.copyOf(array, array.length);
	}


	private static String[] copy(String[] array) {
		if (array == null) {
			return null;
		}
		if (array.length == 0) {
			return array;
		}
		return Arrays.copyOf(array, array.length);
	}

	private static <T> T[] copy(T[] array) {
		if (array == null) {
			return null;
		}
		if (array.length == 0) {
			return array;
		}
		return Arrays.copyOf(array, array.length);
	}

	private static int copy(int value) {
		return value;
	}

	private static long copy(long value) {
		return value;
	}

	private static boolean copy(boolean value) {
		return value;
	}

	private static String copy(String value) {
		return value;
	}

	private static Map<String, String> copyStringMap(Map<String, String> initParams) {
		return new HashMap<String, String>(initParams);
	}

	public static <V> Map<String, Object> copyGenericMap(Map<String, V> value) {
		if (value == null) {
			return null;
		}
		if (value.isEmpty()) {
			return Collections.emptyMap();
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		for (Map.Entry<String, V> entry : value.entrySet()) {
			result.put(entry.getKey(), mapValue(entry.getValue()));
		}
		return result;
	}

	public static Object mapValue(Object v) {
		if ((v == null)
				|| v instanceof Number
				|| v instanceof Boolean
				|| v instanceof Character
				|| v instanceof String
				|| v instanceof DTO) {
			return v;
		}
		if (v instanceof Map) {
			Map<?, ?> m = (Map<?, ?>) v;
			Map<Object, Object> map = newMap(m.size());
			for (Map.Entry<?, ?> e : m.entrySet()) {
				map.put(mapValue(e.getKey()), mapValue(e.getValue()));
			}
			return map;
		}
		if (v instanceof List) {
			List<?> c = (List<?>) v;
			List<Object> list = newList(c.size());
			for (Object o : c) {
				list.add(mapValue(o));
			}
			return list;
		}
		if (v instanceof Set) {
			Set<?> c = (Set<?>) v;
			Set<Object> set = newSet(c.size());
			for (Object o : c) {
				set.add(mapValue(o));
			}
			return set;
		}
		if (v.getClass().isArray()) {
			final int length = Array.getLength(v);
			final Class<?> componentType = mapComponentType(v.getClass().getComponentType());
			Object array = Array.newInstance(componentType, length);
			for (int i = 0; i < length; i++) {
				Array.set(array, i, mapValue(Array.get(v, i)));
			}
			return array;
		}
		return String.valueOf(v);
	}

	private static Class<?> mapComponentType(Class<?> componentType) {
		if (componentType.isPrimitive()
				|| componentType.isArray()
				|| Object.class.equals(componentType)
				|| Number.class.isAssignableFrom(componentType)
				|| Boolean.class.isAssignableFrom(componentType)
				|| Character.class.isAssignableFrom(componentType)
				|| String.class.isAssignableFrom(componentType)
				|| DTO.class.isAssignableFrom(componentType)) {
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

	private static <E> List<E> newList(int size) {
		return new ArrayList<E>(size);
	}

	private static <E> Set<E> newSet(int size) {
		return new HashSet<E>(size);
	}

	private static <K, V> Map<K, V> newMap(int size) {
		return new HashMap<K, V>(size);
	}
}
