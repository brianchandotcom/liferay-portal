/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.jabsorb.serializer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
			catch (Exception exception) {
				throw new MarshallException(
					"Unable to put javaClass", exception);
			}
		}

		JSONObject localeJSONObject = new JSONObject();

		try {
			jsonObject.put("locale", localeJSONObject);

			serializerState.push(object, localeJSONObject, "locale");
		}
		catch (Exception exception) {
			throw new MarshallException("Unable to put locale", exception);
		}

		try {
			Locale locale = (Locale)object;

			localeJSONObject.put(
				"country", locale.getCountry()
			).put(
				"language", locale.getLanguage()
			).put(
				"variant", locale.getVariant()
			);
		}
		catch (Exception exception) {
			throw new MarshallException(
				"Unable to put country, language, and variant", exception);
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		String language = null;

		try {
			language = localeJSONObject.getString("language");
		}
		catch (Exception exception) {
			throw new UnmarshallException("language is undefined", exception);
		}

		String variant = null;

		try {
			variant = localeJSONObject.getString("variant");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
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
		catch (Exception exception) {
			throw new UnmarshallException("Unable to get javaClass", exception);
		}

		if (javaClassName == null) {
			throw new UnmarshallException("javaClass is undefined");
		}

		try {
			Class.forName(javaClassName);
		}
		catch (Exception exception) {
			throw new UnmarshallException(
				"Unable to load javaClass " + javaClassName, exception);
		}

		JSONObject localeJSONObject = null;

		try {
			localeJSONObject = jsonObject.getJSONObject("locale");
		}
		catch (Exception exception) {
			throw new UnmarshallException("Unable to get locale", exception);
		}

		if (localeJSONObject == null) {
			throw new UnmarshallException("locale is undefined");
		}

		return localeJSONObject;
	}

	private static final Class<?>[] _JSON_CLASSES = {JSONObject.class};

	private static final Class<?>[] _SERIALIZABLE_CLASSES = {Locale.class};

	private static final Log _log = LogFactoryUtil.getLog(
		LocaleSerializer.class);

}