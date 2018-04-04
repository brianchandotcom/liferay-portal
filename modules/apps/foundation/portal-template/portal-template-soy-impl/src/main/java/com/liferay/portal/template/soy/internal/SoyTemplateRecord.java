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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.ClassUtils;

import com.google.template.soy.data.LoggingAdvisingAppendable;
import com.google.template.soy.data.SoyAbstractValue;
import com.google.template.soy.data.SoyData;
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

/**
 * This implementation of SoyRecored tries to be modestly lazy in that
 * conversion of context objects only occurs if they are used by the
 * template. Further laziness could be implemented using either java
 * Futures or by simply binding sub-elements to Soy types as late as
 * possible.
 *
 * @author Raymond Augé
 */
class SoyTemplateRecord extends SoyAbstractValue implements SoyRecord {

	public Object add(String key, Object value) {
		return _map.put(key, value);
	}

	@Override
	public boolean coerceToBoolean() {
		return true;
	}

	@Override
	public String coerceToString() {
		LoggingAdvisingAppendable mapStr = LoggingAdvisingAppendable.buffering();

		try {
			render(mapStr);
		}
		catch (IOException e) {
			throw new AssertionError(e);
		}

		return mapStr.toString();
	}

	@Override
	public final boolean equals(Object other) {
		return this == other;
	}

	public Object get(String key) {
		return _map.get(key);
	}

	@Override
	public SoyValue getField(String name) {
		SoyValueProvider fieldProvider = getFieldProvider(name);

		if (fieldProvider != null) {
			return fieldProvider.resolve();
		}

		return NullData.INSTANCE;
	}

	@Override
	public SoyValueProvider getFieldProvider(String name) {
		SoyValueProvider soyValue = _computedValues.get(name);

		if (soyValue == null) {
			Object object = _map.get(name);

			if (object == null) {
				soyValue = NullData.INSTANCE;
			}
			else if (object instanceof SoyData) {
				soyValue = (SoyData) object;
			}
			else if (object instanceof String) {
				soyValue = StringData.forValue((String) object);
			}
			else if (object instanceof Boolean) {
				soyValue = BooleanData.forValue((Boolean) object);
			}
			else if (object instanceof Integer) {
				soyValue = IntegerData.forValue((Integer) object);
			}
			else if (object instanceof Long) {
				soyValue = IntegerData.forValue((Long) object);
			}
			else if (object instanceof Map<?, ?>) {
				@SuppressWarnings("unchecked")
				Map<String, ?> objCast = (Map<String, ?>) object;

				SoyMapData soyMD = new SoyMapData();

				objCast.entrySet().forEach(
					entry ->
						soyMD.put(entry.getKey(), _populateCollectionData(entry.getValue()))
				);

				soyValue = soyMD;
			}
			else if (object instanceof Iterable<?>) {
				SoyListData soyMD = new SoyListData();

				Iterable<?> iterable = (Iterable<?>)object;

				iterable.forEach(
					entry -> soyMD.add(_populateCollectionData(entry))
				);

				soyValue = soyMD;
			}
			else if (object instanceof Double) {
				soyValue = FloatData.forValue((Double) object);
			}
			else if (object instanceof Float) {
				soyValue = FloatData.forValue((Float) object);
			}
			// TODO Futures?
			else {
				soyValue = _populateCollectionData(object);
			}

			_computedValues.put(name, soyValue);
		}

		return soyValue;
	}

	@Override
	public boolean hasField(String name) {
		SoyValueProvider fieldProvider = getFieldProvider(name);

		if (fieldProvider != null) {
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
	public void render(LoggingAdvisingAppendable appendable) throws IOException {
		appendable.append('{');

		boolean isFirst = true;

		for (Entry<String, SoyValueProvider> entry : _computedValues.entrySet()) {
			SoyValue value = entry.getValue().resolve();

			if (isFirst) {
				isFirst = false;
			}
			else {
				appendable.append(", ");
			}

			StringData.forValue(entry.getKey()).render(appendable);

			appendable.append(": ");

			value.render(appendable);
		}

		appendable.append('}');
	}

	private String _decapitalize(String string) {
		if (string == null || string.length() == 0) {
			return string;
		}
		char c[] = string.toCharArray();
		c[0] = Character.toLowerCase(c[0]);
		return new String(c);
	}

	private SoyValue _populateCollectionData(Object object) {
		if (object == null) {
			return NullData.INSTANCE;
		}
		else if (object instanceof SoyData) {
			return (SoyData) object;
		}
		else if (object instanceof String) {
			return StringData.forValue((String) object);
		}
		else if (object instanceof Boolean) {
			return BooleanData.forValue((Boolean) object);
		}
		else if (object instanceof Integer) {
			return IntegerData.forValue((Integer) object);
		}
		else if (object instanceof Long) {
			return IntegerData.forValue((Long) object);
		}
		else if (object instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			Map<String, ?> objCast = (Map<String, ?>) object;

			SoyMapData soyMD = new SoyMapData();

			objCast.entrySet().forEach(
				entry ->
					soyMD.put(entry.getKey(), _populateCollectionData(entry.getValue()))
			);

			return soyMD;
		}
		else if (object instanceof Iterable<?>) {
			SoyListData soyMD = new SoyListData();

			Iterable<?> iterable = (Iterable<?>)object;

			iterable.forEach(
				entry -> soyMD.add(_populateCollectionData(entry))
			);

			return soyMD;
		}
		else if (object instanceof Double) {
			return FloatData.forValue((Double) object);
		}
		else if (object instanceof Float) {
			return FloatData.forValue((Float) object);
		}

		SoyMapData smd = new SoyMapData();

		Class<?> clazz = object.getClass();

		for (Field field : clazz.getFields()) {
			try {
				Object fieldValue = field.get(object);
				if (fieldValue == null) {
					smd.put(_propertyName(field.getName()), NullData.INSTANCE);
				}
				else if (ClassUtils.isPrimitiveOrWrapper(fieldValue.getClass()) ||
					String.class.isInstance(fieldValue)) {

					smd.put(field.getName(), fieldValue);
				}
			}
			catch (IllegalArgumentException | IllegalAccessException e) {
				System.err.println("==========> ERROR on " + field + ": " + e.getMessage());
			}
		}
		for (Method method : clazz.getMethods()) {
			if (method.getParameterTypes().length == 0 &&
				!method.getReturnType().equals(Void.class) &&
				!method.getDeclaringClass().equals(Object.class) &&
				!method.getDeclaringClass().equals(Annotation.class)	) {

				try {
					Object methodValue = method.invoke(object);
					if (methodValue == null) {
						smd.put(_propertyName(method.getName()), NullData.INSTANCE);
					}
					else if (ClassUtils.isPrimitiveOrWrapper(methodValue.getClass()) ||
						String.class.isInstance(methodValue)) {

						smd.put(_propertyName(method.getName()), methodValue);
					}
				}
				catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
					System.err.println("==========> ERROR on " + method + ": " + e.getMessage());
				}
			}
		}

		return smd;
	}

	private Object _propertyName(String methodName) {
		if (methodName.startsWith("get")) {
			methodName = methodName.replaceFirst("^get", "");
		}
		else if (methodName.startsWith("is")) {
			methodName = methodName.replaceFirst("^is", "");
		}
		return _decapitalize(methodName);
	}

	private final Map<String, Object> _map = new ConcurrentHashMap<>();
	private final Map<String, SoyValueProvider> _computedValues = new ConcurrentHashMap<>();

}