/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.jabsorb.serializer;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import org.jabsorb.serializer.AbstractSerializer;
import org.jabsorb.serializer.MarshallException;
import org.jabsorb.serializer.ObjectMatch;
import org.jabsorb.serializer.SerializerState;
import org.jabsorb.serializer.UnmarshallException;

import org.json.JSONObject;

/**
 * @author Dante Wang
 */
public class EnumSerializer extends AbstractSerializer {

	@Override
	public boolean canSerialize(Class clazz, Class jsonClass) {
		if (clazz.isEnum() &&
			((jsonClass == null) || (jsonClass == JSONObject.class))) {

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
				Class<?> clazz = object.getClass();

				jsonObject.put("javaClass", clazz.getName());

				String contextName = ClassLoaderPool.getContextName(
					clazz.getClassLoader());

				if (Validator.isNotNull(contextName)) {
					jsonObject.put("contextName", contextName);
				}
			}
			catch (Exception exception) {
				throw new MarshallException(
					"Unable to put javaClass", exception);
			}
		}

		try {
			serializerState.push(object, StringPool.BLANK, "enumValue");

			Enum<?> enumObject = (Enum<?>)object;

			jsonObject.put("enumValue", enumObject.name());
		}
		finally {
			serializerState.pop();
		}

		return jsonObject;
	}

	@Override
	public ObjectMatch tryUnmarshall(
			SerializerState serializerState, Class clazz, Object object)
		throws UnmarshallException {

		JSONObject jsonObject = (JSONObject)object;

		_getJavaClass(jsonObject);

		ObjectMatch objectMatch = ObjectMatch.ROUGHLY_SIMILAR;

		if (jsonObject.has("enumValue")) {
			objectMatch = ObjectMatch.OKAY;
		}

		serializerState.setSerialized(object, objectMatch);

		return objectMatch;
	}

	@Override
	public Object unmarshall(
			SerializerState serializerState, Class clazz, Object object)
		throws UnmarshallException {

		JSONObject jsonObject = (JSONObject)object;

		Class<?> javaClass = _getJavaClass(jsonObject);

		String enumValue = _getString(jsonObject, "enumValue");

		try {
			Method valueOfMethod = ReflectionUtil.getDeclaredMethod(
				javaClass, "valueOf", String.class);

			Object enumObject = valueOfMethod.invoke(null, enumValue);

			serializerState.setSerialized(object, enumObject);

			return enumObject;
		}
		catch (Exception exception) {
			throw new UnmarshallException(
				StringBundler.concat(
					"Unable to deserialize ", javaClass, " with value ",
					enumValue),
				exception);
		}
	}

	private Class<?> _getJavaClass(JSONObject jsonObject)
		throws UnmarshallException {

		String javaClassName = _getString(jsonObject, "javaClass");

		try {
			String contextName = jsonObject.optString("contextName");

			if (Validator.isNull(contextName)) {
				return Class.forName(javaClassName);
			}

			ClassLoader classLoader = ClassLoaderPool.getClassLoader(
				contextName);

			if (classLoader != null) {
				return classLoader.loadClass(javaClassName);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to get class loader for class ", javaClassName,
						" in context ", contextName));
			}

			return Class.forName(javaClassName);
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new UnmarshallException(
				"Unable to load enum class", classNotFoundException);
		}
	}

	private String _getString(JSONObject jsonObject, String key)
		throws UnmarshallException {

		String string = jsonObject.optString(key);

		if (Validator.isNull(string)) {
			throw new UnmarshallException(key + " is undefined");
		}

		return string;
	}

	private static final Class<?>[] _JSON_CLASSES = {JSONObject.class};

	private static final Class<?>[] _SERIALIZABLE_CLASSES = {Enum.class};

	private static final Log _log = LogFactoryUtil.getLog(EnumSerializer.class);

}