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

import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.jabsorb.serializer.AbstractSerializer;
import org.jabsorb.serializer.MarshallException;
import org.jabsorb.serializer.ObjectMatch;
import org.jabsorb.serializer.SerializerState;
import org.jabsorb.serializer.UnmarshallException;

import org.json.JSONObject;

/**
 * @author Raymond Augé
 */
public class LocaleSerializer extends AbstractSerializer {

	@Override
	public boolean canSerialize(
		@SuppressWarnings("rawtypes") Class clazz,
		@SuppressWarnings("rawtypes") Class jsonClazz) {

		if (Locale.class.isAssignableFrom(clazz) &&
			((jsonClazz == null) || (jsonClazz == JSONObject.class))) {

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

		if (ser.getMarshallClassHints()) {
			try {
				Class<?> javaClass = object.getClass();

				jsonObject.put("javaClass", javaClass.getName());
			}
			catch (Exception e) {
				throw new MarshallException("Unable to put javaClass", e);
			}
		}

		JSONObject localeJSONObject = new JSONObject();

		try {
			jsonObject.put("locale", localeJSONObject);

			serializerState.push(object, localeJSONObject, "locale");
		}
		catch (Exception e) {
			throw new MarshallException("Unable to put locale", e);
		}

		try {
			Locale locale = (Locale)object;

			localeJSONObject.put("country", locale.getCountry());
			localeJSONObject.put("language", locale.getLanguage());
			localeJSONObject.put("variant", locale.getVariant());
		}
		catch (Exception e) {
			throw new MarshallException(
				"Unable to put country, language, and variant", e);
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

		JSONObject localeJSONObject = getLocaleJSONObject(object);

		ObjectMatch objectMatch = ObjectMatch.ROUGHLY_SIMILAR;

		if (localeJSONObject.has("language")) {
			objectMatch = ObjectMatch.OKAY;
		}

		serializerState.setSerialized(object, objectMatch);

		return objectMatch;
	}

	@Override
	public Object unmarshall(
			SerializerState serializerState,
			@SuppressWarnings("rawtypes") Class clazz, Object object)
		throws UnmarshallException {

		JSONObject localeJSONObject = getLocaleJSONObject(object);

		String country = null;

		try {
			country = localeJSONObject.getString("country");
		}
		catch (Exception e) {
		}

		String language = null;

		try {
			language = localeJSONObject.getString("language");
		}
		catch (Exception e) {
			throw new UnmarshallException("language is undefined");
		}

		String variant = null;

		try {
			variant = localeJSONObject.getString("variant");
		}
		catch (Exception e) {
		}

		Locale locale = null;

		if (Validator.isNotNull(country) && Validator.isNotNull(language) &&
			Validator.isNotNull(variant)) {

			locale = new Locale(language, country, variant);
		}
		else if (Validator.isNotNull(country) &&
				 Validator.isNotNull(language)) {

			locale = new Locale(language, country);
		}
		else {
			locale = new Locale(language);
		}

		serializerState.setSerialized(object, locale);

		return locale;
	}

	protected JSONObject getLocaleJSONObject(Object object)
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

			Locale.class.isAssignableFrom(javaClass);
		}
		catch (Exception e) {
			throw new UnmarshallException(
				"Unable to load javaClass " + javaClassName, e);
		}

		JSONObject localeJSONObject = null;

		try {
			localeJSONObject = jsonObject.getJSONObject("locale");
		}
		catch (Exception e) {
			throw new UnmarshallException("Unable to get locale", e);
		}

		if (localeJSONObject == null) {
			throw new UnmarshallException("locale is undefined");
		}

		return localeJSONObject;
	}

	private static final Class<?>[] _JSON_CLASSES = {JSONObject.class};

	private static final Class<?>[] _SERIALIZABLE_CLASSES = {Locale.class};

}