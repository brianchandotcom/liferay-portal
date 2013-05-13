/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.json.jabsorb.serializer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jabsorb.JSONSerializer;
import org.jabsorb.serializer.AbstractSerializer;
import org.jabsorb.serializer.MarshallException;
import org.jabsorb.serializer.ObjectMatch;
import org.jabsorb.serializer.SerializerState;
import org.jabsorb.serializer.UnmarshallException;

import org.json.JSONObject;

/**
 * @author Raymond Augé
 */
public class LiferaySerializer extends AbstractSerializer {

	@Override
	public boolean canSerialize(
		@SuppressWarnings("rawtypes") Class clazz,
		@SuppressWarnings("rawtypes") Class jsonClass) {

		Constructor<?> constructor = null;

		try {
			constructor = clazz.getConstructor();
		}
		catch (Exception e) {
		}

		if (Serializable.class.isAssignableFrom(clazz) &&
			((jsonClass == null) || (jsonClass == JSONObject.class)) &&
			(constructor != null)) {

			return true;
		}

		return false;
	}

	@Override
	public Class<?>[] getJSONClasses() {
		return _JSON_CLASSES;
	}

	@Override
	public Class<?>[] getSerializableClasses() {
		return _SERIALIZABLE_CLASSES;
	}

	@Override
	public Object marshall(
			SerializerState serializerState, Object parentObject, Object object)
		throws MarshallException {

		JSONObject jsonObject = new JSONObject();

		Class<?> javaClass = object.getClass();

		if (ser.getMarshallClassHints()) {
			try {
				jsonObject.put("javaClass", javaClass.getName());
			}
			catch (Exception e) {
				throw new MarshallException("Unable to put javaClass", e);
			}
		}

		JSONObject serializableJSONObject = new JSONObject();

		try {
			jsonObject.put("serializable", serializableJSONObject);

			serializerState.push(
				object, serializableJSONObject, "serializable");
		}
		catch (Exception e) {
			throw new MarshallException("Unable to put serializable", e);
		}

		String fieldName = null;

		try {
			Set<String> processedFieldNames = new HashSet<String>();

			while (javaClass != null) {
				Field[] declaredFields = javaClass.getDeclaredFields();

				for (Field field : declaredFields) {
					fieldName = field.getName();

					// Avoid processing overridden fields of super classes

					if (processedFieldNames.contains(fieldName)) {
						continue;
					}

					processedFieldNames.add(fieldName);

					int modifiers = field.getModifiers();

					// Only marshall fields that are not final, static, or
					// transient

					if (((modifiers & Modifier.FINAL) == Modifier.FINAL) ||
						((modifiers & Modifier.STATIC) == Modifier.STATIC) ||
						((modifiers & Modifier.TRANSIENT) ==
							Modifier.TRANSIENT)) {

						continue;
					}

					if (!field.isAccessible()) {
						field.setAccessible(true);
					}

					if (fieldName.startsWith("_")) {
						fieldName = fieldName.substring(1);
					}

					Object fieldObject = ser.marshall(
						serializerState, serializableJSONObject,
						field.get(object), fieldName);

					// Omit the object entirely if it is a circular reference or
					// duplicate. It will be regenerated in the fixups phase.

					if (JSONSerializer.CIRC_REF_OR_DUPLICATE != fieldObject) {
						serializableJSONObject.put(fieldName, fieldObject);
					}
				}

				javaClass = javaClass.getSuperclass();
			}
		}
		catch (Exception e) {
			throw new MarshallException(
				"Unable to match field " + fieldName, e);
		}
		finally {
			serializerState.pop();
		}

		return jsonObject;
	}

	@Override
	public ObjectMatch tryUnmarshall(
			SerializerState serializerState,
			@SuppressWarnings("rawtypes") Class clazz, Object object)
		throws UnmarshallException {

		JSONObject jsonObject = (JSONObject)object;

		String javaClassName = null;

		try {
			javaClassName = jsonObject.getString("javaClass");
		}
		catch (Exception e) {
			throw new UnmarshallException("Unable to get javaClass", e);
		}

		if (javaClassName == null) {
			throw new UnmarshallException("javaClass is undefined");
		}

		try {
			Class<?> javaClass = Class.forName(javaClassName);

			Serializable.class.isAssignableFrom(javaClass);
		}
		catch (Exception e) {
			throw new UnmarshallException(
				"Unable to load javaClass " + javaClassName, e);
		}

		JSONObject serializableJSONObject = null;

		try {
			serializableJSONObject = jsonObject.getJSONObject("serializable");
		}
		catch (Exception e) {
			throw new UnmarshallException("Unable to get serializable", e);
		}

		if (serializableJSONObject == null) {
			throw new UnmarshallException("serializable is undefined");
		}

		ObjectMatch objectMatch = new ObjectMatch(-1);

		serializerState.setSerialized(object, objectMatch);

		String fieldName = null;

		try {
			Iterator<?> iterator = serializableJSONObject.keys();

			while (iterator.hasNext()) {
				fieldName = (String)iterator.next();

				ObjectMatch fieldObjectMatch = ser.tryUnmarshall(
					serializerState, null,
					serializableJSONObject.get(fieldName));

				ObjectMatch maxFieldObjectMatch = fieldObjectMatch.max(
					objectMatch);

				objectMatch.setMismatch(maxFieldObjectMatch.getMismatch());
			}
		}
		catch (Exception e) {
			throw new UnmarshallException(
				"Unable to match field " + fieldName, e);
		}

		return objectMatch;
	}

	@Override
	public Object unmarshall(
			SerializerState serializerState,
			@SuppressWarnings("rawtypes") Class clazz, Object object)
		throws UnmarshallException {

		JSONObject jsonObject = (JSONObject)object;

		String javaClassName = null;

		try {
			javaClassName = jsonObject.getString("javaClass");
		}
		catch (Exception e) {
			throw new UnmarshallException("Unable to get javaClass", e);
		}

		if (javaClassName == null) {
			throw new UnmarshallException("javaClass is undefined");
		}

		Class<?> javaClass = null;

		Object javaClassInstance = null;

		try {
			javaClass = Class.forName(javaClassName);

			javaClassInstance = javaClass.newInstance();
		}
		catch (Exception e) {
			throw new UnmarshallException(
				"Unable to load javaClass " + javaClassName, e);
		}

		JSONObject serializableJSONObject = null;

		try {
			serializableJSONObject = jsonObject.getJSONObject("serializable");
		}
		catch (Exception e) {
			throw new UnmarshallException("Unable to get serializable", e);
		}

		if (serializableJSONObject == null) {
			throw new UnmarshallException("serializable is undefined");
		}

		serializerState.setSerialized(object, javaClassInstance);

		String fieldName = null;

		try {
			Set<String> processedFieldNames = new HashSet<String>();

			while (javaClass != null) {
				Field[] fields = javaClass.getDeclaredFields();

				for (Field field : fields) {
					fieldName = field.getName();

					// Avoid processing overridden fields of super classes

					if (processedFieldNames.contains(fieldName)) {
						continue;
					}

					processedFieldNames.add(fieldName);

					int modifiers = field.getModifiers();

					// Only unmarshall fields that are not final, static, or
					// transient

					if (((modifiers & Modifier.FINAL) == Modifier.FINAL) ||
						((modifiers & Modifier.STATIC) == Modifier.STATIC) ||
						((modifiers & Modifier.TRANSIENT) ==
							Modifier.TRANSIENT)) {

						continue;
					}

					if (!field.isAccessible()) {
						field.setAccessible(true);
					}

					if (fieldName.startsWith("_")) {
						fieldName = fieldName.substring(1);
					}

					Object value = null;

					try {
						value = ser.unmarshall(
							serializerState, field.getType(),
							serializableJSONObject.get(fieldName));
					}
					catch (Exception e) {
					}

					if (value != null) {
						try {
							field.set(javaClassInstance, value);
						}
						catch (Exception e) {
							_log.error(e, e);
						}
					}
				}

				javaClass = javaClass.getSuperclass();
			}
		}
		catch (Exception e) {
			throw new UnmarshallException(
				"Unable to match field " + fieldName, e);
		}

		return javaClassInstance;
	}

	private static final Class<?>[] _JSON_CLASSES = {JSONObject.class};

	private static final Class<?>[] _SERIALIZABLE_CLASSES =
		{Serializable.class};

	private static Log _log = LogFactoryUtil.getLog(LiferaySerializer.class);

}