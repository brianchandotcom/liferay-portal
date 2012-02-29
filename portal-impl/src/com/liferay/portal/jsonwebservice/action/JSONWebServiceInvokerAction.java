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

package com.liferay.portal.jsonwebservice.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.bean.BeanUtil;

import jodd.servlet.ServletUtil;

import jodd.util.KeyValue;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceInvokerAction implements JSONWebServiceAction {

	public JSONWebServiceInvokerAction(HttpServletRequest request) {
		_request = request;
		_command = request.getParameter("cmd");

		if (_command == null) {
			try {
				_command = ServletUtil.readRequestBody(request);
			}
			catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	public JSONWebServiceActionMapping getJSONWebServiceActionMapping() {
		return null;
	}

	@SuppressWarnings("unchecked")
	public Object invoke() throws Exception {
		Object deserializedCommand = JSONFactoryUtil.looseDeserialize(_command);

		boolean batchMode = false;
		List list = null;

		if (deserializedCommand instanceof List) {
			list = (List) deserializedCommand;

			batchMode = true;
		} else if (deserializedCommand instanceof Map) {
			list = new ArrayList(1);

			list.add(deserializedCommand);

			batchMode = false;
		} else {
			throw new IllegalArgumentException("Invalid argument type");
		}

		for (int i = 0; i < list.size(); i++) {
			Map<String, Map<String, Object>> map =
				(Map<String, Map<String, Object>>) list.get(i);

			if (map.isEmpty()) {
				throw new IllegalArgumentException("Invalid input");
			}

			Map.Entry<String, Map<String, Object>> entry =
				map.entrySet().iterator().next();

			Statement statement =
				_parseStatement(entry.getKey(), entry.getValue());

			Object result = _executeStatement(statement);

			list.set(i, result);
		}

		if (batchMode == false) {
			return list.get(0);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _convertObjectToMap(Object object) {
		if (object instanceof Map == false) {
			// this is a potential performance pitfall

			String json = JSONFactoryUtil.looseSerialize(object);

			object = JSONFactoryUtil.looseDeserialize(json, HashMap.class);
		}

		return (Map<String, Object>) object;
	}

	private Object _executeStatement(Statement statement) throws Exception {

		JSONWebServiceAction action =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
				_request, statement.method, null, statement.parameters);

		Object result = action.invoke();

		_populateFlags(statement.name, result);

		result = _filterResult(statement, result);

		if (statement.variables != null) {
			for (Statement variable : statement.variables) {
				Object variableValue = _executeStatement(variable);

				Map<String, Object> target = _convertObjectToMap(result);

				target.put(variable.name.substring(1), variableValue);

				result = target;
			}
		}

		return result;
	}

	private Object _filterResult(Statement statement, Object result) {
		if (result == null) {
			return result;
		}

		if (statement.whitelist == null) {
			return result;
		}

		Map<String, Object> map = _convertObjectToMap(result);

		Map<String, Object> whitelistedMap =
			new HashMap<String, Object>(statement.whitelist.length);

		for (int i = 0; i < statement.whitelist.length; i++) {
			String key = statement.whitelist[i];

			Object value = map.get(key);

			whitelistedMap.put(key, value);
		}

		return whitelistedMap;
	}

	@SuppressWarnings("unchecked")
	private Statement _parseStatement(
		String assignment, Map<String, Object> value) {

		Statement statement = new Statement();

		statements.add(statement);

		// name and method

		int index = assignment.indexOf('=');

		if (index == -1) {
			statement.method = assignment.trim();
		}
		else {
			String name = assignment.substring(0, index).trim();

			int bracketIndex = name.indexOf('[');

			if (bracketIndex != -1) {

				String whiteSpaceList =
					name.substring(bracketIndex + 1, name.length() - 1);

				statement.whitelist = StringUtil.split(whiteSpaceList);

				name = name.substring(0, bracketIndex);
			}

			statement.name = name;

			statement.method = assignment.substring(index + 1).trim();
		}

		statement.parameters = value;

		// variables

		Iterator<String> iterator =
			statement.parameters.keySet().iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (key.startsWith(StringPool.DOLLAR)) {	// variable
				Map<String, Object> innerMap =
					(Map<String, Object>) statement.parameters.get(key);

				iterator.remove();

				if (statement.variables == null) {
					statement.variables = new ArrayList<Statement>();
				}

				Statement variableStatement = _parseStatement(key, innerMap);

				statement.variables.add(variableStatement);

				continue;
			}

			if (key.startsWith(StringPool.AT)) {		// flag
				String innerValue = (String)statement.parameters.get(key);

				iterator.remove();

				KeyValue<String, String> flag = new KeyValue<String, String>();

				flag.setKey(key.substring(1));
				flag.setValue(innerValue);

				if (statement.flags == null) {
					statement.flags =
						new ArrayList<KeyValue<String, String>>();
				}

				statement.flags.add(flag);

				continue;
			}
		}

		return statement;
	}

	private void _populateFlags(String name, Object object) {

		if (name == null) {
			return;
		}

		name += '.';

		int nameLength = name.length();

		for (Statement statement : statements) {
			List<KeyValue<String, String>> statementFlags = statement.flags;

			if (statementFlags == null) {
				continue;
			}

			for (KeyValue<String, String> flag : statementFlags) {

				String flagReference = flag.getValue();

				if (flagReference == null) {
					continue;
				}

				if (flagReference.startsWith(name)) {
					Object flagValue = BeanUtil.getDeclaredProperty(
						object, flagReference.substring(nameLength));

					statement.parameters.put(flag.getKey(), flagValue);

					flag.setValue(null);
				}
			}
		}
	}

	static class Statement {
		List<KeyValue<String, String>> flags;
		String method;
		String name;
		Map<String, Object> parameters;
		List<Statement> variables;
		String[] whitelist;
	}

	private String _command;
	private HttpServletRequest _request;
	private List<Statement> statements = new ArrayList<Statement>();

}