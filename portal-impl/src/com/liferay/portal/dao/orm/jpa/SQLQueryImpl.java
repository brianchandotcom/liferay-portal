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

package com.liferay.portal.dao.orm.jpa;

import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnmodifiableList;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SQLQueryImpl extends QueryImpl implements SQLQuery {

	public SQLQueryImpl(
		SessionImpl sessionImpl, String queryString, boolean strictName) {

		super(sessionImpl, queryString, strictName);

		sqlQuery = true;
	}

	@Override
	public SQLQuery addEntity(String alias, Class<?> entityClass) {
		String columnAliases = null;

		try {
			String[] columnNames = _getColumns(entityClass);

			if (columnNames.length == 0) {
				columnAliases = StringPool.BLANK;
			}
			else {
				StringBundler sb = new StringBundler(
					columnNames.length * 4 - 1);

				int i = 0;

				for (String column : columnNames) {
					sb.append(alias);
					sb.append(StringPool.PERIOD);
					sb.append(column);

					if ((i + 1) < columnNames.length) {
						sb.append(StringPool.COMMA_AND_SPACE);
					}

					i++;
				}

				columnAliases = sb.toString();
			}
		}
		catch (Exception e) {
			throw new ORMException(e.getMessage());
		}

		String escapedAlias = Pattern.quote("{" + alias + ".*}");

		queryString = queryString.replaceAll(escapedAlias, columnAliases);

		this.entityClass = entityClass;

		return this;
	}

	@Override
	public SQLQuery addScalar(String columnAlias, Type type) {
		columnAlias = columnAlias.toLowerCase();

		String q = queryString.toLowerCase();

		int fromIndex = q.indexOf("from");

		if (fromIndex == -1) {
			return this;
		}

		String selectExpression = q.substring(0, fromIndex);

		String[] selectTokens = selectExpression.split(StringPool.COMMA);

		for (int pos = 0; pos < selectTokens.length; pos++) {
			String s = selectTokens[pos];

			if (s.contains(columnAlias)) {
				_scalars.add(pos);

				_scalarTypes.add(type);
			}
		}

		return this;
	}

	@Override
	public List<?> list(boolean copy, boolean unmodifiable)
		throws ORMException {

		try {
			List<?> list = sessionImpl.list(
				queryString, positionalParameterMap, namedParameterMap,
				strictName, firstResult, maxResults, flushModeType,
				lockModeType, sqlQuery, entityClass);

			if ((entityClass == null) && !list.isEmpty()) {
				list = _transformList(list);
			}

			if (unmodifiable) {
				list = new UnmodifiableList<Object>(list);
			}
			else if (copy) {
				list = ListUtil.copy(list);
			}

			return list;
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public Object uniqueResult() throws ORMException {
		try {
			Object object = sessionImpl.uniqueResult(
				queryString, positionalParameterMap, namedParameterMap,
				strictName, firstResult, maxResults, flushModeType,
				lockModeType, sqlQuery, entityClass);

			if (object instanceof Collection<?>) {
				Collection<Object> collection = (Collection<Object>)object;

				if (collection.size() == 1) {
					object = collection.iterator().next();
				}
			}

			if (_scalars.size() == 1) {
				object = _transformType(object, _scalarTypes.get(0));
			}

			return object;
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	private String[] _getColumns(Class<?> entityClass) throws Exception {
		String[] columns = _entityColumns.get(entityClass);

		if (columns != null) {
			return columns;
		}

		Field field = entityClass.getField("TABLE_COLUMNS");

		Object[][] tableColumns = (Object[][])field.get(null);

		columns = new String[tableColumns.length];

		int i = 0;

		for (Object[] row : tableColumns) {
			String name = (String)row[0];

			columns[i++] = name.toUpperCase();
		}

		_entityColumns.put(entityClass, columns);

		return columns;
	}

	private List<?> _transformList(List<?> list) throws Exception {
		if (!_scalars.isEmpty()) {
			Collections.sort(_scalars);

			if (list.get(0) instanceof Collection<?>) {
				List<Object> newList = new ArrayList<Object>();

				for (Collection<Object> collection :
						(List<Collection<Object>>)list) {

					Object[] array = collection.toArray();

					if (_scalars.size() > 1) {
						Object[] values = new Object[_scalars.size()];

						for (int i = 0; i < _scalars.size(); i++) {
							values[i] = array[_scalars.get(i)];
						}

						newList.add(values);
					}
					else {
						newList.add(array[_scalars.get(0)]);
					}
				}

				list = newList;
			}
			else if (list.get(0) instanceof Object[]) {
				List<Object> newList = new ArrayList<Object>();

				for (Object[] array : (List<Object[]>)list) {
					if (_scalars.size() > 1) {
						Object[] values = new Object[_scalars.size()];

						for (int i = 0; i < _scalars.size(); i++) {
							values[i] = array[_scalars.get(i)];
						}

						newList.add(values);
					}
					else {
						newList.add(array[_scalars.get(0)]);
					}
				}

				list = newList;
			}
			else if (_scalars.size() == 1) {
				List<Object> newList = new ArrayList<Object>();

				for (Object value : list) {
					value = _transformType(value, _scalarTypes.get(0));

					newList.add(value);
				}

				list = newList;
			}
		}
		else if (list.get(0) instanceof Collection<?>) {
			List<Object> newList = new ArrayList<Object>();

			for (Collection<Object> collection :
					(List<Collection<Object>>)list) {

				if (collection.size() == 1) {
					newList.add(collection.iterator().next());
				}
				else {
					newList.add(collection.toArray());
				}
			}

			list = newList;
		}

		return list;
	}

	private Object _transformType(Object object, Type type) {
		Object result = object;

		if (type.equals(Type.LONG)) {
			if (object instanceof Integer) {
				result = new Long((((Integer)object).longValue()));
			}
		}
		else if (type.equals(Type.STRING)) {
			result = object.toString();
		}
		else {
			throw new UnsupportedOperationException(
				"Type conversion from " + object.getClass().getName() + " to " +
					type + " is not supported");
		}

		return result;
	}

	private static Map<Class<?>, String[]> _entityColumns =
		new ConcurrentHashMap<Class<?>, String[]>();

	private List<Integer> _scalars = new ArrayList<Integer>();
	private List<Type> _scalarTypes = new ArrayList<Type>();

}