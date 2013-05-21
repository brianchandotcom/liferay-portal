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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Karthik Sudarshan
 * @author Julio Camarero
 * @author Eduardo Lundgren
 */
public class JSONServiceAction extends JSONAction {

	public JSONServiceAction() {
		_invalidClassNames = SetUtil.fromArray(
			PropsValues.JSON_SERVICE_INVALID_CLASS_NAMES);

		if (_log.isDebugEnabled()) {
			for (String invalidClassName : _invalidClassNames) {
				_log.debug("Invalid class name " + invalidClassName);
			}
		}
	}

	@Override
	public String getJSON(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String className = ParamUtil.getString(request, "serviceClassName");
		String methodName = ParamUtil.getString(request, "serviceMethodName");

		String[] serviceParameters = getStringArrayFromJSON(
			request, "serviceParameters");
		String[] serviceParameterTypes = getStringArrayFromJSON(
			request, "serviceParameterTypes");

		if (!isValidRequest(request)) {
			return null;
		}

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		Class<?> clazz = contextClassLoader.loadClass(className);

		Object[] methodAndParameterTypes = getMethodAndParameterTypes(
			clazz, methodName, serviceParameters, serviceParameterTypes);

		if (methodAndParameterTypes == null) {
			return null;
		}

		Method method = (Method)methodAndParameterTypes[0];
		Type[] parameterTypes = (Type[])methodAndParameterTypes[1];
		Object[] args = new Object[serviceParameters.length];

		for (int i = 0; i < serviceParameters.length; i++) {
			args[i] = getArgValue(
				request, clazz, methodName, serviceParameters[i],
				parameterTypes[i]);
		}

		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Invoking " + clazz + " on method " + method.getName() +
						" with args " + Arrays.toString(args));
			}

			Object returnObj = method.invoke(clazz, args);

			if (returnObj != null) {
				return getReturnValue(returnObj);
			}
			else {
				return JSONFactoryUtil.getNullJSON();
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Invoked " + clazz + " on method " + method.getName() +
						" with args " + Arrays.toString(args),
					e);
			}

			return JSONFactoryUtil.serializeException(e);
		}
	}

	protected Object getArgValue(
			HttpServletRequest request, Class<?> clazz, String methodName,
			String parameter, Type parameterType)
		throws Exception {

		String typeNameOrClassDescriptor = getTypeNameOrClassDescriptor(
			parameterType);

		String value = ParamUtil.getString(request, parameter);

		if (Validator.isNull(value) &&
			!typeNameOrClassDescriptor.equals("[Ljava.lang.String;")) {

			return null;
		}
		else if (typeNameOrClassDescriptor.equals("boolean") ||
				 typeNameOrClassDescriptor.equals(Boolean.class.getName())) {

			return Boolean.valueOf(ParamUtil.getBoolean(request, parameter));
		}
		else if (typeNameOrClassDescriptor.equals("double") ||
				 typeNameOrClassDescriptor.equals(Double.class.getName())) {

			return new Double(ParamUtil.getDouble(request, parameter));
		}
		else if (typeNameOrClassDescriptor.equals("int") ||
				 typeNameOrClassDescriptor.equals(Integer.class.getName())) {

			return new Integer(ParamUtil.getInteger(request, parameter));
		}
		else if (typeNameOrClassDescriptor.equals("long") ||
				 typeNameOrClassDescriptor.equals(Long.class.getName())) {

			return new Long(ParamUtil.getLong(request, parameter));
		}
		else if (typeNameOrClassDescriptor.equals("short") ||
				 typeNameOrClassDescriptor.equals(Short.class.getName())) {

			return new Short(ParamUtil.getShort(request, parameter));
		}
		else if (typeNameOrClassDescriptor.equals(Date.class.getName())) {
			return new Date(ParamUtil.getLong(request, parameter));
		}
		else if (typeNameOrClassDescriptor.equals(
					ServiceContext.class.getName())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			jsonObject.put("javaClass", ServiceContext.class.getName());

			return ServiceContextUtil.deserialize(jsonObject);
		}
		else if (typeNameOrClassDescriptor.equals(String.class.getName())) {
			return value;
		}
		else if (typeNameOrClassDescriptor.equals("[Z")) {
			return ParamUtil.getBooleanValues(request, parameter);
		}
		else if (typeNameOrClassDescriptor.equals("[D")) {
			return ParamUtil.getDoubleValues(request, parameter);
		}
		else if (typeNameOrClassDescriptor.equals("[F")) {
			return ParamUtil.getFloatValues(request, parameter);
		}
		else if (typeNameOrClassDescriptor.equals("[I")) {
			return ParamUtil.getIntegerValues(request, parameter);
		}
		else if (typeNameOrClassDescriptor.equals("[J")) {
			return ParamUtil.getLongValues(request, parameter);
		}
		else if (typeNameOrClassDescriptor.equals("[S")) {
			return ParamUtil.getShortValues(request, parameter);
		}
		else if (typeNameOrClassDescriptor.equals("[Ljava.lang.String;")) {
			return ParamUtil.getParameterValues(request, parameter);
		}
		else if (typeNameOrClassDescriptor.equals("[[Z")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				boolean[][] doubleArray =
					new boolean[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					String[] curValues = StringUtil.split(values[i]);

					for (int j = 0; j < curValues.length; j++) {
						doubleArray[i][j] = GetterUtil.getBoolean(curValues[j]);
					}
				}

				return doubleArray;
			}
			else {
				return new boolean[0][0];
			}
		}
		else if (typeNameOrClassDescriptor.equals("[[D")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				double[][] doubleArray =
					new double[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					String[] curValues = StringUtil.split(values[i]);

					for (int j = 0; j < curValues.length; j++) {
						doubleArray[i][j] = GetterUtil.getDouble(curValues[j]);
					}
				}

				return doubleArray;
			}
			else {
				return new double[0][0];
			}
		}
		else if (typeNameOrClassDescriptor.equals("[[F")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				float[][] doubleArray =
					new float[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					String[] curValues = StringUtil.split(values[i]);

					for (int j = 0; j < curValues.length; j++) {
						doubleArray[i][j] = GetterUtil.getFloat(curValues[j]);
					}
				}

				return doubleArray;
			}
			else {
				return new float[0][0];
			}
		}
		else if (typeNameOrClassDescriptor.equals("[[I")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				int[][] doubleArray = new int[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					String[] curValues = StringUtil.split(values[i]);

					for (int j = 0; j < curValues.length; j++) {
						doubleArray[i][j] = GetterUtil.getInteger(curValues[j]);
					}
				}

				return doubleArray;
			}
			else {
				return new int[0][0];
			}
		}
		else if (typeNameOrClassDescriptor.equals("[[J")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				long[][] doubleArray = new long[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					String[] curValues = StringUtil.split(values[i]);

					for (int j = 0; j < curValues.length; j++) {
						doubleArray[i][j] = GetterUtil.getLong(curValues[j]);
					}
				}

				return doubleArray;
			}
			else {
				return new long[0][0];
			}
		}
		else if (typeNameOrClassDescriptor.equals("[[S")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				short[][] doubleArray =
					new short[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					String[] curValues = StringUtil.split(values[i]);

					for (int j = 0; j < curValues.length; j++) {
						doubleArray[i][j] = GetterUtil.getShort(curValues[j]);
					}
				}

				return doubleArray;
			}
			else {
				return new short[0][0];
			}
		}
		else if (typeNameOrClassDescriptor.equals("[[Ljava.lang.String")) {
			String[] values = request.getParameterValues(parameter);

			if ((values != null) && (values.length > 0)) {
				String[] values0 = StringUtil.split(values[0]);

				String[][] doubleArray =
					new String[values.length][values0.length];

				for (int i = 0; i < values.length; i++) {
					doubleArray[i] = StringUtil.split(values[i]);
				}

				return doubleArray;
			}
			else {
				return new String[0][0];
			}
		}
		else if (typeNameOrClassDescriptor.equals(
					"java.util.Map<java.util.Locale, java.lang.String>")) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			return LocalizationUtil.deserialize(jsonObject);
		}
		else {
			try {
				return JSONFactoryUtil.looseDeserializeSafe(value);
			}
			catch (Exception e) {
				_log.error(
					"Unsupported parameter type for class " + clazz +
						", method " + methodName + ", parameter " + parameter +
							", and type " + typeNameOrClassDescriptor);

				return null;
			}
		}
	}

	protected Object[] getMethodAndParameterTypes(
			Class<?> clazz, String methodName, String[] parameters,
			String[] parameterTypes)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append(clazz.getName());
		sb.append("_METHOD_NAME_");
		sb.append(methodName);
		sb.append("_PARAMETERS_");

		String parameterTypesNames = StringUtil.merge(parameterTypes);

		if (Validator.isNull(parameterTypesNames)) {
			sb.append(parameters.length);
		}
		else {
			sb.append(parameterTypesNames);
		}

		String key = sb.toString();

		Object[] methodAndParameterTypes = _methodCache.get(key);

		if (methodAndParameterTypes != null) {
			return methodAndParameterTypes;
		}

		Method method = null;
		Type[] methodParameterTypes = null;

		Method[] methods = clazz.getMethods();

		for (Method curMethod : methods) {
			if (curMethod.getName().equals(methodName)) {
				Type[] curParameterTypes = curMethod.getGenericParameterTypes();

				if (curParameterTypes.length == parameters.length) {
					if ((parameterTypes.length > 0) &&
						(parameterTypes.length == curParameterTypes.length)) {

						boolean match = true;

						for (int j = 0; j < parameterTypes.length; j++) {
							String t1 = parameterTypes[j];
							String t2 = getTypeNameOrClassDescriptor(
								curParameterTypes[j]);

							if (!t1.equals(t2)) {
								match = false;
							}
						}

						if (match) {
							method = curMethod;
							methodParameterTypes = curParameterTypes;

							break;
						}
					}
					else if (method != null) {
						String parametersString = StringUtil.merge(parameters);

						_log.error(
							"Obscure method name for class " + clazz +
								", method " + methodName + ", and parameters " +
									parametersString);

						return null;
					}
					else {
						method = curMethod;
						methodParameterTypes = curParameterTypes;
					}
				}
			}
		}

		if (method != null) {
			methodAndParameterTypes = new Object[] {
				method, methodParameterTypes
			};

			_methodCache.put(key, methodAndParameterTypes);

			return methodAndParameterTypes;
		}
		else {
			String parametersString = StringUtil.merge(parameters);

			_log.error(
				"No method found for class " + clazz + ", method " +
					methodName + ", and parameters " + parametersString);

			return null;
		}
	}

	@Override
	protected String getReroutePath() {
		return _REROUTE_PATH;
	}

	protected String getReturnValue(Object returnObj) throws Exception {
		if (returnObj instanceof JSONSerializable) {
			JSONSerializable jsonSerializable = (JSONSerializable)returnObj;

			return jsonSerializable.toJSONString();
		}

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		jsonSerializer.exclude("*.class");

		return jsonSerializer.serialize(returnObj);
	}

	protected String[] getStringArrayFromJSON(
			HttpServletRequest request, String param)
		throws JSONException {

		String json = ParamUtil.getString(request, param, "[]");

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		return ArrayUtil.toStringArray(jsonArray);
	}

	protected String getTypeNameOrClassDescriptor(Type type) {
		String typeName = type.toString();

		if (typeName.contains("class ")) {
			return typeName.substring(6);
		}

		Matcher matcher = _fieldDescriptorPattern.matcher(typeName);

		while (matcher.find()) {
			String dimensions = matcher.group(2);
			String fieldDescriptor = matcher.group(1);

			if (Validator.isNull(dimensions)) {
				return fieldDescriptor;
			}

			dimensions = dimensions.replace(
				StringPool.CLOSE_BRACKET, StringPool.BLANK);

			if (fieldDescriptor.equals("boolean")) {
				fieldDescriptor = "Z";
			}
			else if (fieldDescriptor.equals("byte")) {
				fieldDescriptor = "B";
			}
			else if (fieldDescriptor.equals("char")) {
				fieldDescriptor = "C";
			}
			else if (fieldDescriptor.equals("double")) {
				fieldDescriptor = "D";
			}
			else if (fieldDescriptor.equals("float")) {
				fieldDescriptor = "F";
			}
			else if (fieldDescriptor.equals("int")) {
				fieldDescriptor = "I";
			}
			else if (fieldDescriptor.equals("long")) {
				fieldDescriptor = "J";
			}
			else if (fieldDescriptor.equals("short")) {
				fieldDescriptor = "S";
			}
			else {
				fieldDescriptor = "L".concat(fieldDescriptor).concat(
					StringPool.SEMICOLON);
			}

			return dimensions.concat(fieldDescriptor);
		}

		throw new IllegalArgumentException(type.toString() + " is invalid");
	}

	protected boolean isValidRequest(HttpServletRequest request) {
		String className = ParamUtil.getString(request, "serviceClassName");

		if (className.contains(".service.") &&
			className.endsWith("ServiceUtil") &&
			!className.endsWith("LocalServiceUtil") &&
			!_invalidClassNames.contains(className)) {

			return true;
		}
		else {
			return false;
		}
	}

	private static final String _REROUTE_PATH = "/api/json";

	private static Log _log = LogFactoryUtil.getLog(JSONServiceAction.class);

	private static Pattern _fieldDescriptorPattern = Pattern.compile(
		"^(.*?)((\\[\\])*)$", Pattern.DOTALL);

	private Set<String> _invalidClassNames;
	private Map<String, Object[]> _methodCache =
		new HashMap<String, Object[]>();

}