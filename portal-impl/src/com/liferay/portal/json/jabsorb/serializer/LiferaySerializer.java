/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.Iterator;

import org.jabsorb.JSONSerializer;
import org.jabsorb.serializer.AbstractSerializer;
import org.jabsorb.serializer.MarshallException;
import org.jabsorb.serializer.ObjectMatch;
import org.jabsorb.serializer.SerializerState;
import org.jabsorb.serializer.UnmarshallException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Raymond Augé
 */
public class LiferaySerializer extends AbstractSerializer {

	@Override
	public boolean canSerialize(Class clazz, Class jsonClass) {
		Constructor publicDefaultConstructor = null;

		try {
			publicDefaultConstructor = clazz.getConstructor();
		}
		catch (Exception e) {
		}

		if ((jsonClass == null || jsonClass == JSONObject.class) &&
			Serializable.class.isAssignableFrom(clazz) &&
			(publicDefaultConstructor != null)) {

			return true;
		}

		return false;
	}

	public Class[] getJSONClasses() {
		return _JSON_CLASSES;
	}

	public Class[] getSerializableClasses() {
		return _SERIALIZABLE_CLASSES;
	}

	public Object marshall(
			SerializerState serializerState, Object parentObject, Object object)
		throws MarshallException {

		Class javaClass = object.getClass();

		JSONObject jsonObject = new JSONObject();
		JSONObject serializableData = new JSONObject();

		if (ser.getMarshallClassHints()) {
			try {
				jsonObject.put("javaClass", object.getClass().getName());
			}
			catch (JSONException e) {
				throw new MarshallException("javaClass not found", e);
			}
		}

		try {
			jsonObject.put("serializable", serializableData);

			serializerState.push(object, serializableData, "serializable");
		}
		catch (JSONException e) {
			throw new MarshallException(
				"Could not add serializable to object: " + e.getMessage(), e);
		}

		String fieldName = null;

		try {
			while (javaClass != null) {
				Field[] declaredFields = javaClass.getDeclaredFields();

				for (Field field : declaredFields) {
					int modifiers = field.getModifiers();

					// Only marshall fields that are not final, static or
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

					fieldName = field.getName();

					if (fieldName.startsWith("_")) {
						fieldName = fieldName.substring(1);
					}

					Object json = ser.marshall(
						serializerState, serializableData, field.get(object),
						fieldName);

					// Omit the object entirely if it's a circular reference or
					// duplicate it will be regenerated in the fixups phase

					if (JSONSerializer.CIRC_REF_OR_DUPLICATE != json) {
						serializableData.put(fieldName, json);
					}
				}

				javaClass = javaClass.getSuperclass();
			}
		}
		catch (Exception e) {
			throw new MarshallException(
				"field " + fieldName + " " + e.getMessage(), e);
		}
		finally {
			serializerState.pop();
		}

		return jsonObject;
	}

	public ObjectMatch tryUnmarshall(
			SerializerState serializerState, Class clazz, Object object)
		throws UnmarshallException {

		JSONObject jsonObject = (JSONObject)object;

		String className;

		try {
			className = jsonObject.getString("javaClass");
		}
		catch (JSONException e) {
			throw new UnmarshallException("could not read javaClass", e);
		}

		if (className == null) {
			throw new UnmarshallException("no javaClass hint");
		}

		try {
			Class javaClass = Class.forName(className);

			Serializable.class.isAssignableFrom(javaClass);
		}
		catch (Exception e) {
			throw new UnmarshallException(
				"error handling javaClass hint " + e.getMessage(), e);
		}

		JSONObject jsonSerializable;

		try {
			jsonSerializable = jsonObject.getJSONObject("serializable");
		}
		catch (JSONException e) {
			throw new UnmarshallException(
				"could not read serializable: " + e.getMessage(), e);
		}

		if (jsonSerializable == null) {
			throw new UnmarshallException("serializable missing");
		}

		ObjectMatch objectMatch = new ObjectMatch(-1);

		Iterator iterator = jsonSerializable.keys();

		String fieldName = null;

		serializerState.setSerialized(object, objectMatch);

		try {
			while (iterator.hasNext()) {
				fieldName = (String) iterator.next();

				ObjectMatch curObjectMatch = ser.tryUnmarshall(
					serializerState, null, jsonSerializable.get(fieldName));

				int mismatch = curObjectMatch.max(objectMatch).getMismatch();

				objectMatch.setMismatch(mismatch);
			}
		}
		catch (Exception e) {
			throw new UnmarshallException(
				"field " + fieldName + " " + e.getMessage(), e);
		}

		return objectMatch;
	}

	public Object unmarshall(
			SerializerState serializerState, Class clazz, Object object)
		throws UnmarshallException {

		JSONObject jsonObject = (JSONObject)object;

		String className;

		try {
			className = jsonObject.getString("javaClass");
		}
		catch (JSONException e) {
			throw new UnmarshallException("could not read javaClass", e);
		}

		if (className == null) {
			throw new UnmarshallException("no javaClass hint");
		}

		Class javaClass = null;
		Object serializedObject = null;

		try {
			javaClass = Class.forName(className);

			serializedObject = javaClass.newInstance();
		}
		catch (Exception e) {
			throw new UnmarshallException(
				"error handling javaClass hints " + e.getMessage(), e);
		}

		JSONObject jsonSerializable;

		try {
			jsonSerializable = jsonObject.getJSONObject("serializable");
		}
		catch (JSONException e) {
			throw new UnmarshallException(
				"could not read serializable: " + e.getMessage(), e);
		}

		if (jsonSerializable == null) {
			throw new UnmarshallException("serializable missing");
		}

		serializerState.setSerialized(object, serializedObject);

		String fieldName = null;

		try {
			while (javaClass != null) {
				Field[] declaredFields = javaClass.getDeclaredFields();

				for (Field field : declaredFields) {
					int modifiers = field.getModifiers();

					// Only unmarshall fields that are not final, static or
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

					fieldName = field.getName();

					if (fieldName.startsWith("_")) {
						fieldName = fieldName.substring(1);
					}

					Object value = null;

					try {
						value = ser.unmarshall(
							serializerState, null,
							jsonSerializable.get(fieldName));
					}
					catch (UnmarshallException me) {
					}

					if (value != null) {
						field.set(serializedObject, value);
					}
				}

				javaClass = javaClass.getSuperclass();
			}
		}
		catch (Exception e) {
			throw new UnmarshallException(
				"field " + fieldName + " " + e.getMessage(), e);
		}

		return serializedObject;
	}

	private static Class[] _JSON_CLASSES = new Class[] { JSONObject.class };
	private static Class[] _SERIALIZABLE_CLASSES = new Class[] {
		Serializable.class};

}