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
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONSerializer;
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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jodd.bean.BeanUtil;

import jodd.servlet.ServletUtil;

import jodd.util.KeyValue;

/**
 * @author Igor Spasic
 * @author Eduardo Lundgren
 */
public class JSONWebServiceInvokerAction implements JSONWebServiceAction {

	public JSONWebServiceInvokerAction(HttpServletRequest request) {
		_request = request;

		_command = request.getParameter("cmd");

		if (_command == null) {
			try {
				_command = ServletUtil.readRequestBody(request);
			}
			catch (IOException ioe) {
				throw new IllegalArgumentException(ioe);
			}
		}
	}

	public JSONWebServiceActionMapping getJSONWebServiceActionMapping() {
		return null;
	}

	public Object invoke() throws Exception {
		Object command = JSONFactoryUtil.looseDeserializeSafe(_command);

		List<Object> list = null;

		boolean batchMode = false;

		if (command instanceof List) {
			list = (List<Object>)command;

			batchMode = true;
		}
		else if (command instanceof Map) {
			list = new ArrayList<Object>(1);

			list.add(command);

			batchMode = false;
		}
		else {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < list.size(); i++) {
			Map<String, Map<String, Object>> map =
				(Map<String, Map<String, Object>>)list.get(i);

			if (map.isEmpty()) {
				throw new IllegalArgumentException();
			}

			Set<Map.Entry<String, Map<String, Object>>> entrySet =
				map.entrySet();

			Iterator<Map.Entry<String, Map<String, Object>>> iterator =
				entrySet.iterator();

			Map.Entry<String, Map<String, Object>> entry = iterator.next();

			Statement statement = _parseStatement(
				entry.getKey(), entry.getValue());

			Object result = _executeStatement(statement);

			list.set(i, result);
		}

		Object result = null;

		if (batchMode == false) {
			result = list.get(0);
		}
		else {
			result = list;
		}

		return new InvokerResult(result);
	}

	public class InvokerResult implements JSONSerializable {

		public String toJSONString() {
			if (_result == null) {
				return JSONFactoryUtil.getNullJSON();
			}

			JSONSerializer jsonSerializer =
				JSONFactoryUtil.createJSONSerializer();

			jsonSerializer.exclude("*.class");

			for (Statement statement : _statements) {
				String name = statement.getName();

				if (name == null) {
					continue;
				}

				jsonSerializer.include(name.substring(1));
			}

			return jsonSerializer.serialize(_result);
		}

		public Object getResult() {
			return _result;
		}

		private InvokerResult(Object result) {
			_result = result;
		}

		private Object _result;

	}

	private Object _addVariableStatement(
			Statement statement, Statement variableStatement, Object result)
		throws Exception {

		result = _populateFlags(statement, result);

		String name = variableStatement.getName();

		Object variableResult = _executeStatement(variableStatement);

		Map<String, Object> map = _convertObjectToMap(result);

		map.put(name.substring(1), variableResult);

		return map;
	}

	private Object _addVariableStatementList(
			Statement statement, Statement variableStatement, Object result,
			List<Object> results)
		throws Exception {

		List<Object> list = _convertObjectToList(result);

		for (Object object : list) {
			if (object instanceof List) {
				Object value = _addVariableStatementList(statement,
					variableStatement, object, results);

				results.add(value);
			}
			else {
				Object value = _addVariableStatement(statement,
					variableStatement, object);

				results.add(value);
			}
		}

		return results;
	}

	private List<Object> _convertObjectToList(Object object) {
		if (!(object instanceof List)) {
			String json = JSONFactoryUtil.looseSerialize(object);

			object = JSONFactoryUtil.looseDeserialize(json, ArrayList.class);
		}

		return (List<Object>)object;
	}

	private Map<String, Object> _convertObjectToMap(Object object) {
		if (!(object instanceof Map)) {
			String json = JSONFactoryUtil.looseSerialize(object);

			object = JSONFactoryUtil.looseDeserialize(json, HashMap.class);
		}

		return (Map<String, Object>)object;
	}

	private Object _executeStatement(Statement statement) throws Exception {
		JSONWebServiceAction jsonWebServiceAction =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceAction(
				_request, statement.getMethod(), null,
				statement.getParameterMap());

		Object result = jsonWebServiceAction.invoke();

		result = _filterResult(statement, result);

		List<Statement> variableStatements = statement.getVariableStatements();

		if (variableStatements != null) {
			for (Statement variableStatement : variableStatements) {
				if (result instanceof List) {
					result = _addVariableStatementList(
						statement, variableStatement, result,
						new ArrayList<Object>());
				}
				else {
					result = _addVariableStatement(
						statement, variableStatement, result);
				}
			}
		}

		return result;
	}

	private Object _filterResult(
		Statement statement, Object result) {

		if (result instanceof List) {
			result = _filterResultList(
				statement, result, new ArrayList<Object>());
		}
		else {
			result = _filterResultObject(statement, result);
		}
		return result;
	}

	private Object _filterResultObject(Statement statement, Object result) {
		if (result == null) {
			return result;
		}

		String[] whitelist = statement.getWhitelist();

		if (whitelist == null) {
			return result;
		}

		Map<String, Object> map = _convertObjectToMap(result);

		Map<String, Object> whitelistMap = new HashMap<String, Object>(
			whitelist.length);

		for (String key : whitelist) {
			Object value = map.get(key);

			whitelistMap.put(key, value);
		}

		return whitelistMap;
	}

	private Object _filterResultList(
		Statement statement, Object result, List<Object> results) {

		List<Object> list = _convertObjectToList(result);

		for (Object object : list) {
			Object value = _filterResultObject(statement, object);

			results.add(value);
		}

		return results;
	}

	private Statement _parseStatement(
		String assignment, Map<String, Object> parameterMap) {

		Statement statement = new Statement();

		_statements.add(statement);

		int x = assignment.indexOf(StringPool.EQUAL);

		if (x == -1) {
			statement.setMethod(assignment.trim());
		}
		else {
			String name = assignment.substring(0, x).trim();

			int y = name.indexOf(StringPool.OPEN_BRACKET);

			if (y != -1) {
				String whitelistString = name.substring(
					y + 1, name.length() - 1);

				String[] whiteList = StringUtil.split(whitelistString);

				for (int i = 0; i < whiteList.length; i++) {
					whiteList[i] = whiteList[i].trim();
				}

				statement.setWhitelist(whiteList);

				name = name.substring(0, y);
			}

			statement.setName(name);

			statement.setMethod(assignment.substring(x + 1).trim());
		}

		statement.setParameterMap(parameterMap);

		Set<String> keySet = parameterMap.keySet();

		Iterator<String> iterator = keySet.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (key.startsWith(StringPool.AT)) {
				String value = (String)parameterMap.get(key);

				iterator.remove();

				List<Flag> flags = statement.getFlags();

				if (flags == null) {
					flags = new ArrayList<Flag>();

					statement.setFlags(flags);
				}

				Flag flag = new Flag();

				flag.setKey(key.substring(1));
				flag.setValue(value);

				flags.add(flag);
			}
			else if (key.startsWith(StringPool.DOLLAR)) {
				Map<String, Object> map = (Map<String, Object>)parameterMap.get(
					key);

				iterator.remove();

				List<Statement> variableStatements =
					statement.getVariableStatements();

				if (variableStatements == null) {
					variableStatements = new ArrayList<Statement>();

					statement.setVariableStatements(variableStatements);
				}

				Statement variableStatement = _parseStatement(key, map);

				variableStatements.add(variableStatement);
			}
		}

		return statement;
	}

	private Object _populateFlags(
		Statement statement, Object result) {

		if (result instanceof List) {
			result = _populateFlagsList(
				statement.getName(), result, new ArrayList<Object>());
		}
		else {
			_populateFlagsObject(statement.getName(), result);
		}
		return result;
	}

	private void _populateFlagsObject(String name, Object object) {
		if (name == null) {
			return;
		}

		name = name.concat(StringPool.PERIOD);

		for (Statement statement : _statements) {
			List<Flag> flags = statement.getFlags();

			if (flags == null) {
				continue;
			}

			for (Flag flag : flags) {
				String value = flag.getValue();

				if ((value == null) || !value.startsWith(name)) {
					continue;
				}

				Map<String, Object> parameterMap = statement.getParameterMap();

				Object propertyValue = BeanUtil.getDeclaredProperty(
					object, value.substring(name.length()));

				parameterMap.put(flag.getKey(), propertyValue);

			}
		}
	}

	private List<Object> _populateFlagsList(
		String name, Object result, List<Object> results) {

		List<Object> list = _convertObjectToList(result);

		for (Object object : list) {
			if (object instanceof List) {
				Object value = _populateFlagsList(name, object, results);

				results.add(value);
			}
			else {
				_populateFlagsObject(name, object);

				results.add(object);
			}
		}

		return results;
	}

	private String _command;
	private HttpServletRequest _request;
	private List<Statement> _statements = new ArrayList<Statement>();

	private class Flag extends KeyValue<String, String> {
	}

	private class Statement {

		public List<Flag> getFlags() {
			return _flags;
		}

		public String getMethod() {
			return _method;
		}

		public String getName() {
			return _name;
		}

		public Map<String, Object> getParameterMap() {
			return _parameterMap;
		}

		public List<Statement> getVariableStatements() {
			return _variableStatements;
		}

		public String[] getWhitelist() {
			return _whitelist;
		}

		public void setFlags(List<Flag> flags) {
			_flags = flags;
		}

		public void setMethod(String method) {
			_method = method;
		}

		public void setName(String name) {
			_name = name;
		}

		public void setParameterMap(Map<String, Object> parameterMap) {
			_parameterMap = parameterMap;
		}

		public void setVariableStatements(List<Statement> variableStatements) {
			_variableStatements = variableStatements;
		}

		public void setWhitelist(String[] whitelist) {
			_whitelist = whitelist;
		}

		private List<Flag> _flags;
		private String _method;
		private String _name;
		private Map<String, Object> _parameterMap;
		private List<Statement> _variableStatements;
		private String[] _whitelist;

	}

}