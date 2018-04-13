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

package com.liferay.portal.template.soy.internal;

import com.google.template.soy.data.LoggingAdvisingAppendable;
import com.google.template.soy.data.SoyAbstractValue;
import com.google.template.soy.data.SoyData;
import com.google.template.soy.data.SoyDataException;
import com.google.template.soy.data.SoyListData;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.data.SoyRecord;
import com.google.template.soy.data.SoyValue;
import com.google.template.soy.data.SoyValueProvider;
import com.google.template.soy.data.restricted.BooleanData;
import com.google.template.soy.data.restricted.FloatData;
import com.google.template.soy.data.restricted.IntegerData;
import com.google.template.soy.data.restricted.NullData;
import com.google.template.soy.data.restricted.StringData;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.template.soy.utils.SoyRawData;

import java.io.IOException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.ClassUtils;

/**
 * Provides a <code>SoyRecord</code> implementation that tries to be modestly
 * lazy by converting context objects only as they are used by the template.
 * Further laziness can be implemented using Java Futures or by binding
 * sub-elements to Soy types as late as possible.
 *
 * @author Raymond Augé
 */
public class SoyTemplateRecord extends SoyAbstractValue implements SoyRecord {

	public Object add(String key, Object value) {
		return _map.put(key, value);
	}

	@Override
	public boolean coerceToBoolean() {
		return true;
	}

	@Override
	public String coerceToString() {
		LoggingAdvisingAppendable appendable =
			LoggingAdvisingAppendable.buffering();

		try {
			render(appendable);
		}
		catch (IOException ioe) {
			throw new AssertionError(ioe);
		}

		return appendable.toString();
	}

	@Override
	public final boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		return false;
	}

	public Object get(String key) {
		return _map.get(key);
	}

	@Override
	public SoyValue getField(String name) {
		SoyValueProvider soyValueProvider = getFieldProvider(name);

		if (soyValueProvider != null) {
			return soyValueProvider.resolve();
		}

		return NullData.INSTANCE;
	}

	@Override
	public SoyValueProvider getFieldProvider(String name) {
		SoyValueProvider soyValueProvider = _computedValues.get(name);

		if (soyValueProvider != null) {
			return soyValueProvider;
		}

		Object object = _map.get(name);

		soyValueProvider = _toSoyValue(object);

		_computedValues.put(name, soyValueProvider);

		return soyValueProvider;
	}

	@Override
	public boolean hasField(String name) {
		SoyValueProvider soyValueProvider = getFieldProvider(name);

		if (soyValueProvider != null) {
			return true;
		}

		return false;
	}

	public Set<String> keys() {
		return _map.keySet();
	}

	public void remove(String key) {
		_map.remove(key);
	}

	@Override
	public void render(LoggingAdvisingAppendable appendable)
		throws IOException {

		appendable.append('{');

		boolean first = true;

		for (Entry<String, SoyValueProvider> entry :
				_computedValues.entrySet()) {

			SoyValueProvider soyValueProvider = entry.getValue();

			SoyValue soyValue = soyValueProvider.resolve();

			if (first) {
				first = false;
			}
			else {
				appendable.append(", ");
			}

			String key = entry.getKey();

			StringData stringData = StringData.forValue(key);

			stringData.render(appendable);

			appendable.append(": ");

			soyValue.render(appendable);
		}

		appendable.append('}');
	}

	private Object _propertyName(String methodName) {
		if (methodName.startsWith("get") || methodName.startsWith("is")) {
			methodName = methodName.replaceFirst("^(?:get|is)", "");
		}

		return StringUtil.lowerCaseFirstLetter(methodName);
	}

	private SoyValue _toSoyValue(Object object) {
		if (object == null) {
			return NullData.INSTANCE;
		}
		else if (object instanceof SoyData) {
			return (SoyData)object;
		}
		else if (object instanceof SoyRawData) {
			SoyRawData soyRawData = (SoyRawData)object;

			return _toSoyValue(soyRawData.getValue());
		}
		else if (object instanceof String) {
			return StringData.forValue((String)object);
		}
		else if (object instanceof Boolean) {
			return BooleanData.forValue((Boolean)object);
		}
		else if (object instanceof Integer) {
			return IntegerData.forValue((Integer)object);
		}
		else if (object instanceof Long) {
			return IntegerData.forValue((Long)object);
		}
		else if (object instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			Map<String, ?> map = (Map<String, ?>)object;

			SoyMapData soyMapData = new SoyMapData();

			Set<? extends Entry<String, ?>> entries = map.entrySet();

			entries.forEach(
				entry -> soyMapData.put(
					entry.getKey(), _toSoyValue(entry.getValue())));

			return soyMapData;
		}
		else if (object instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)object;

			SoyMapData soyMapData = new SoyMapData();

			Iterator<String> it = jsonObject.keys();

			it.forEachRemaining(
				key -> soyMapData.put(key, _toSoyValue(jsonObject.get(key))));

			return soyMapData;
		}
		else if (object instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)object;

			SoyListData soyListData = new SoyListData();

			Iterator it = jsonArray.iterator();

			it.forEachRemaining(value -> soyListData.add(_toSoyValue(value)));

			return soyListData;
		}
		else if (object instanceof Iterable<?>) {
			SoyListData soyListData = new SoyListData();

			Iterable<?> iterable = (Iterable<?>)object;

			iterable.forEach(entry -> soyListData.add(_toSoyValue(entry)));

			return soyListData;
		}
		else if (object instanceof Double) {
			return FloatData.forValue((Double)object);
		}
		else if (object instanceof Float) {
			return FloatData.forValue((Float)object);
		}

		SoyMapData soyMapData = new SoyMapData();

		Class<?> clazz = object.getClass();

		try {
			for (Field field : clazz.getFields()) {
				Object fieldValue = field.get(object);

				if (fieldValue == null) {
					soyMapData.put(
						_propertyName(field.getName()), NullData.INSTANCE);
				}
				else if (ClassUtils.isPrimitiveOrWrapper(
							fieldValue.getClass()) ||
						 String.class.isInstance(fieldValue)) {

					soyMapData.put(field.getName(), fieldValue);
				}
			}

			for (Method method : clazz.getMethods()) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				Class<?> returnType = method.getReturnType();
				Class<?> declaringClass = method.getDeclaringClass();

				if ((parameterTypes.length == 0) &&
					!returnType.equals(Void.class) &&
					!declaringClass.equals(Object.class) &&
					!declaringClass.equals(Annotation.class)) {

					Object methodValue = method.invoke(object);

					if (methodValue == null) {
						soyMapData.put(
							_propertyName(method.getName()), NullData.INSTANCE);
					}
					else if (ClassUtils.isPrimitiveOrWrapper(
								methodValue.getClass()) ||
							 String.class.isInstance(methodValue)) {

						soyMapData.put(
							_propertyName(method.getName()), methodValue);
					}
				}
			}
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new SoyDataException(e.getMessage(), e);
		}

		return soyMapData;
	}

	private final Map<String, SoyValueProvider> _computedValues =
		new ConcurrentHashMap<>();
	private final Map<String, Object> _map = new ConcurrentHashMap<>();

}