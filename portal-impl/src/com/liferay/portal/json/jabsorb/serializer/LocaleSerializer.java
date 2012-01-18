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

import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

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
public class LocaleSerializer extends AbstractSerializer {

	@Override
	public boolean canSerialize(Class clazz, Class jsonClazz) {
		if ((jsonClazz == null || jsonClazz == JSONObject.class) &&
			Locale.class.isAssignableFrom(clazz)) {

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

		Locale locale = (Locale)object;

		JSONObject jsonObject = new JSONObject();
		JSONObject localeData = new JSONObject();

		if (ser.getMarshallClassHints()) {
			try {
				jsonObject.put("javaClass", object.getClass().getName());
			}
			catch (JSONException e) {
				throw new MarshallException("javaClass not found", e);
			}
		}

		try {
			jsonObject.put("locale", localeData);

			serializerState.push(object, localeData, "locale");
		}
		catch (JSONException e) {
			throw new MarshallException(
				"Could not add locale to object: " + e.getMessage(), e);
		}

		try {
			localeData.put("language", locale.getLanguage());
			localeData.put("country", locale.getCountry());
			localeData.put("variant", locale.getVariant());
		}
		catch (Exception e) {
			throw new MarshallException("locale " + e.getMessage(), e);
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

			Locale.class.isAssignableFrom(javaClass);
		}
		catch (Exception e) {
			throw new UnmarshallException(
				"error handling javaClass hint " + e.getMessage(), e);
		}

		JSONObject jsonLocale;

		try {
			jsonLocale = jsonObject.getJSONObject("locale");
		}
		catch (JSONException e) {
			throw new UnmarshallException(
				"could not read locale: " + e.getMessage(), e);
		}

		if (jsonLocale == null) {
			throw new UnmarshallException("locale missing");
		}

		ObjectMatch objectMatch = ObjectMatch.ROUGHLY_SIMILAR;

		if (jsonLocale.has("language")) {
			objectMatch = ObjectMatch.OKAY;
		}

		serializerState.setSerialized(object, objectMatch);

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

		try {
			Class javaClass = Class.forName(className);

			Locale.class.isAssignableFrom(javaClass);
		}
		catch (Exception e) {
			throw new UnmarshallException(
				"error handling javaClass hints " + e.getMessage(), e);
		}

		JSONObject jsonLocale;

		try {
			jsonLocale = jsonObject.getJSONObject("locale");
		}
		catch (JSONException e) {
			throw new UnmarshallException(
				"could not read locale: " + e.getMessage(), e);
		}

		if (jsonLocale == null) {
			throw new UnmarshallException("locale missing");
		}

		String language = null;

		try {
			language = jsonLocale.getString("language");
		}
		catch (JSONException e) {
			throw new UnmarshallException("language missing");
		}

		String country = null;

		try {
			country = jsonLocale.getString("country");
		}
		catch (JSONException e) {
		}

		String variant = null;

		try {
			variant = jsonLocale.getString("variant");
		}
		catch (JSONException e) {
		}

		Locale localeObject = null;

		if (Validator.isNotNull(language) && Validator.isNotNull(country) &&
			Validator.isNotNull(variant)) {

			localeObject = new Locale(language, country, variant);
		}
		else if (Validator.isNotNull(language) &&
				 Validator.isNotNull(country)) {

			localeObject = new Locale(language, country);
		}
		else {
			localeObject = new Locale(language);
		}

		serializerState.setSerialized(object, localeObject);

		return localeObject;
	}

	private static Class[] _JSON_CLASSES = new Class[] { JSONObject.class };
	private static Class[] _SERIALIZABLE_CLASSES = new Class[] {Locale.class};

}