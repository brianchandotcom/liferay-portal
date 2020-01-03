/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.batch.engine.internal.item;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortField;
import com.liferay.portal.odata.sort.SortParser;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.io.Closeable;
import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.MultivaluedHashMap;

import org.osgi.framework.ServiceObjects;

/**
 * @author Ivica Cardic
 */
public class BatchEngineTaskItemResourceDelegate implements Closeable {

	public BatchEngineTaskItemResourceDelegate(
			Company company, ExpressionConvert<Filter> expressionConvert,
			Map<String, Field> fieldMap,
			FilterParserProvider filterParserProvider,
			Map<String, Serializable> parameters, Method resourceMethod,
			Map.Entry<String, Class<?>>[] resourceMethodArgNameTypeEntries,
			ServiceObjects<Object> resourceServiceObjects,
			SortParserProvider sortParserProvider, User user)
		throws ReflectiveOperationException {

		_company = company;
		_expressionConvert = expressionConvert;
		_fieldMap = fieldMap;
		_filterParserProvider = filterParserProvider;
		_parameters = parameters;
		_resourceMethod = resourceMethod;
		_resourceMethodArgNameTypeEntries = resourceMethodArgNameTypeEntries;
		_resourceServiceObjects = resourceServiceObjects;
		_sortParserProvider = sortParserProvider;
		_user = user;

		_acceptLanguage = _getAcceptLanguage();
		_resource = _getResource();
	}

	public void addItems(List<?> items) throws Exception {
		for (Object item : items) {
			Object[] args = new Object[_resourceMethod.getParameterCount()];

			Parameter[] parameters = _resourceMethod.getParameters();

			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];

				if (parameter.getType() == item.getClass()) {
					args[i] = item;
				}
				else {
					Map.Entry<String, Class<?>> resourceMethodArgNameTypeEntry =
						_resourceMethodArgNameTypeEntries[i];

					if (resourceMethodArgNameTypeEntry == null) {
						throw new IllegalArgumentException(
							"Unable to find method argument name");
					}

					args[i] = _getMethodArgValue(
						item, resourceMethodArgNameTypeEntry.getKey(),
						resourceMethodArgNameTypeEntry.getValue());
				}
			}

			_resourceMethod.invoke(_resource, args);
		}
	}

	@Override
	public void close() {
		_resourceServiceObjects.ungetService(_resource);
	}

	public Page<?> getItems(int page, int pageSize) throws Exception {
		Object[] args = new Object[_resourceMethod.getParameterCount()];

		for (int i = 0; i < _resourceMethodArgNameTypeEntries.length; i++) {
			Map.Entry<String, Class<?>> resourceMethodArgNameTypeEntry =
				_resourceMethodArgNameTypeEntries[i];

			if (resourceMethodArgNameTypeEntry == null) {
				continue;
			}

			Class<?> resourceMethodArgType =
				resourceMethodArgNameTypeEntry.getValue();

			if (resourceMethodArgType == Filter.class) {
				args[i] = _getFilter(
					_acceptLanguage, _getEntityModel(_resource, _parameters),
					(String)_parameters.get("filter"));
			}
			else if (resourceMethodArgType == Pagination.class) {
				args[i] = Pagination.of(page, pageSize);
			}
			else if (resourceMethodArgType == Sort[].class) {
				args[i] = _getSorts(
					_acceptLanguage, _getEntityModel(_resource, _parameters),
					(String)_parameters.get("sort"));
			}
			else {
				args[i] = _getMethodArgValue(
					null, resourceMethodArgNameTypeEntry.getKey(),
					resourceMethodArgType);
			}
		}

		return (Page<?>)_resourceMethod.invoke(_resource, args);
	}

	private AcceptLanguage _getAcceptLanguage() {
		return new AcceptLanguage() {

			@Override
			public List<Locale> getLocales() {
				return null;
			}

			@Override
			public String getPreferredLanguageId() {
				return _user.getLanguageId();
			}

			@Override
			public Locale getPreferredLocale() {
				return LocaleUtil.fromLanguageId(_user.getLanguageId());
			}

		};
	}

	private EntityModel _getEntityModel(
			Object resource, Map<String, Serializable> parameterMap)
		throws Exception {

		EntityModelResource entityModelResource = (EntityModelResource)resource;

		return entityModelResource.getEntityModel(
			_getMultivaluedHashMap(parameterMap));
	}

	private Filter _getFilter(
			AcceptLanguage acceptLanguage, EntityModel entityModel,
			String filterString)
		throws Exception {

		if (Validator.isNull(filterString)) {
			return null;
		}

		if (entityModel == null) {
			return null;
		}

		FilterParser filterParser = _filterParserProvider.provide(entityModel);

		com.liferay.portal.odata.filter.Filter oDataFilter =
			new com.liferay.portal.odata.filter.Filter(
				filterParser.parse(filterString));

		return _expressionConvert.convert(
			oDataFilter.getExpression(), acceptLanguage.getPreferredLocale(),
			entityModel);
	}

	private Object _getMethodArgValue(
			Object item, String resourceMethodArgName,
			Class<?> resourceMethodArgType)
		throws IllegalAccessException {

		Serializable parameter = null;

		if (_parameters != null) {
			parameter = _parameters.get(resourceMethodArgName);
		}

		if ((item != null) && (parameter == null)) {
			Field field = _fieldMap.get(resourceMethodArgName);

			if (field == null) {
				return null;
			}

			return field.get(item);
		}
		else if (parameter != null) {
			return _objectMapper.convertValue(parameter, resourceMethodArgType);
		}

		return null;
	}

	private MultivaluedHashMap<String, String> _getMultivaluedHashMap(
		Map<String, Serializable> parameterMap) {

		return new MultivaluedHashMap<String, String>() {
			{
				for (Entry<String, Serializable> entry :
						parameterMap.entrySet()) {

					Object value = entry.getValue();

					put(
						entry.getKey(),
						Collections.singletonList(value.toString()));
				}
			}
		};
	}

	private Object _getResource() throws ReflectiveOperationException {
		Object resource = _resourceServiceObjects.getService();

		_setFieldValue(resource, "contextAcceptLanguage", _acceptLanguage);
		_setFieldValue(resource, "contextCompany", _company);
		_setFieldValue(resource, "contextUser", _user);

		return resource;
	}

	private Sort[] _getSorts(
		AcceptLanguage acceptLanguage, EntityModel entityModel,
		String sortString) {

		if (Validator.isNull(sortString)) {
			return null;
		}

		SortParser sortParser = _sortParserProvider.provide(entityModel);

		if (sortParser == null) {
			return null;
		}

		com.liferay.portal.odata.sort.Sort oDataSort =
			new com.liferay.portal.odata.sort.Sort(
				sortParser.parse(sortString));

		List<SortField> sortFields = oDataSort.getSortFields();

		Sort[] sorts = new Sort[sortFields.size()];

		for (int i = 0; i < sortFields.size(); i++) {
			SortField sortField = sortFields.get(i);

			sorts[i] = new Sort(
				sortField.getSortableFieldName(
					acceptLanguage.getPreferredLocale()),
				!sortField.isAscending());
		}

		return sorts;
	}

	private void _setFieldValue(Object resource, String fieldName, Object value)
		throws ReflectiveOperationException {

		Class<?> resourceClass = resource.getClass();

		Class<?> resourceSuperClass = resourceClass.getSuperclass();

		Field field = resourceSuperClass.getDeclaredField(fieldName);

		field.setAccessible(true);

		field.set(resource, value);
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	private final AcceptLanguage _acceptLanguage;
	private final Company _company;
	private final ExpressionConvert<Filter> _expressionConvert;
	private final Map<String, Field> _fieldMap;
	private final FilterParserProvider _filterParserProvider;
	private final Map<String, Serializable> _parameters;
	private final Object _resource;
	private final Method _resourceMethod;
	private final Map.Entry<String, Class<?>>[]
		_resourceMethodArgNameTypeEntries;
	private final ServiceObjects<Object> _resourceServiceObjects;
	private final SortParserProvider _sortParserProvider;
	private final User _user;

}