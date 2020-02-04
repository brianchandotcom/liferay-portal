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

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskOperation;
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

import java.io.Closeable;
import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.ServiceObjects;

/**
 * @author Ivica Cardic
 */
public class BatchEngineTaskItemDelegateExecutor implements Closeable {

	public BatchEngineTaskItemDelegateExecutor(
		Company company, ExpressionConvert<Filter> expressionConvert,
		FilterParserProvider filterParserProvider,
		Map<String, Serializable> parameters,
		ServiceObjects<BatchEngineTaskItemDelegate<Object>> serviceObjects,
		SortParserProvider sortParserProvider, User user) {

		_company = company;
		_expressionConvert = expressionConvert;
		_filterParserProvider = filterParserProvider;
		_parameters = parameters;
		_serviceObjects = serviceObjects;
		_sortParserProvider = sortParserProvider;
		_user = user;

		_batchEngineTaskItemDelegate = _serviceObjects.getService();
	}

	@Override
	public void close() {
		_serviceObjects.ungetService(_batchEngineTaskItemDelegate);
	}

	public Page<?> getItems(int page, int pageSize) throws Exception {
		_setContextFields(_batchEngineTaskItemDelegate);

		return _batchEngineTaskItemDelegate.read(
			_getFilter(), Pagination.of(page, pageSize), _getSorts(),
			_getFilteredParameters(), (String)_parameters.get("search"));
	}

	public void saveItems(
			BatchEngineTaskOperation batchEngineTaskOperation,
			Collection<Object> items)
		throws Exception {

		_setContextFields(_batchEngineTaskItemDelegate);

		if (batchEngineTaskOperation == BatchEngineTaskOperation.CREATE) {
			_batchEngineTaskItemDelegate.create(items, _parameters);
		}
		else if (batchEngineTaskOperation == BatchEngineTaskOperation.DELETE) {
			_batchEngineTaskItemDelegate.delete(items, _parameters);
		}
		else {
			_batchEngineTaskItemDelegate.update(items, _parameters);
		}
	}

	private Filter _getFilter() throws Exception {
		String filterString = (String)_parameters.get("filter");

		if (Validator.isNull(filterString)) {
			return null;
		}

		EntityModel entityModel = _batchEngineTaskItemDelegate.getEntityModel(
			_toMultivaluedMap(_parameters));

		if (entityModel == null) {
			return null;
		}

		FilterParser filterParser = _filterParserProvider.provide(entityModel);

		com.liferay.portal.odata.filter.Filter oDataFilter =
			new com.liferay.portal.odata.filter.Filter(
				filterParser.parse(filterString));

		return _expressionConvert.convert(
			oDataFilter.getExpression(),
			LocaleUtil.fromLanguageId(_user.getLanguageId()), entityModel);
	}

	private Map<String, Serializable> _getFilteredParameters() {
		Map<String, Serializable> filteredParameters = new HashMap<>(
			_parameters);

		filteredParameters.remove("filter");
		filteredParameters.remove("search");
		filteredParameters.remove("sort");

		return filteredParameters;
	}

	private Sort[] _getSorts() throws Exception {
		String sortString = (String)_parameters.get("sort");

		if (Validator.isNull(sortString)) {
			return null;
		}

		EntityModel entityModel = _batchEngineTaskItemDelegate.getEntityModel(
			_toMultivaluedMap(_parameters));

		if (entityModel == null) {
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
					LocaleUtil.fromLanguageId(_user.getLanguageId())),
				!sortField.isAscending());
		}

		return sorts;
	}

	private void _setContextFields(
		BatchEngineTaskItemDelegate<Object> batchEngineTaskItemDelegate) {

		batchEngineTaskItemDelegate.setContextAcceptLanguage(
			new AcceptLanguage() {

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

			});
		batchEngineTaskItemDelegate.setContextCompany(_company);
		batchEngineTaskItemDelegate.setContextUser(_user);
	}

	private Map<String, List<String>> _toMultivaluedMap(
		Map<String, Serializable> parameterMap) {

		Map<String, List<String>> multivaluedMap = new HashMap<>();

		parameterMap.forEach(
			(key, value) -> multivaluedMap.put(
				key, Collections.singletonList(String.valueOf(value))));

		return multivaluedMap;
	}

	private final BatchEngineTaskItemDelegate<Object>
		_batchEngineTaskItemDelegate;
	private final Company _company;
	private final ExpressionConvert<Filter> _expressionConvert;
	private final FilterParserProvider _filterParserProvider;
	private final Map<String, Serializable> _parameters;
	private final ServiceObjects<BatchEngineTaskItemDelegate<Object>>
		_serviceObjects;
	private final SortParserProvider _sortParserProvider;
	private final User _user;

}