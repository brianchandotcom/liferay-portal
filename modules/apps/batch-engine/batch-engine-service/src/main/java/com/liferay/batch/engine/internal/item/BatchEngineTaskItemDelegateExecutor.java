/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.item;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.jaxrs.uri.BatchEngineUriInfo;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.batch.engine.strategy.BatchEngineImportStrategy;
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

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class BatchEngineTaskItemDelegateExecutor {

	public BatchEngineTaskItemDelegateExecutor(
		BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate,
		Company company, ExpressionConvert<Filter> expressionConvert,
		FilterParserProvider filterParserProvider,
		Map<String, Serializable> parameters,
		SortParserProvider sortParserProvider, User user) {

		_batchEngineTaskItemDelegate =
			(BatchEngineTaskItemDelegate<Object>)batchEngineTaskItemDelegate;
		_company = company;
		_expressionConvert = expressionConvert;
		_filterParserProvider = filterParserProvider;
		_parameters = parameters;
		_sortParserProvider = sortParserProvider;
		_user = user;
	}

	public Page<?> getItems(int page, int pageSize) throws Exception {
		_setContextFields(_batchEngineTaskItemDelegate);

		return _batchEngineTaskItemDelegate.read(
			_getFilter(), Pagination.of(page, pageSize), _getSorts(),
			_getFilteredParameters(), (String)_parameters.get("search"));
	}

	public void saveItems(
			BatchEngineImportStrategy batchEngineImportStrategy,
			BatchEngineTaskOperation batchEngineTaskOperation,
			Collection<Object> items)
		throws Exception {

		_setContextFields(_batchEngineTaskItemDelegate);

		_batchEngineTaskItemDelegate.setBatchEngineImportStrategy(
			batchEngineImportStrategy);

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

		batchEngineTaskItemDelegate.setContextCompany(_company);

		BatchEngineUriInfo.Builder builder = new BatchEngineUriInfo.Builder();

		for (Map.Entry<String, Serializable> entry : _parameters.entrySet()) {
			builder.queryParameter(
				entry.getKey(), String.valueOf(entry.getValue()));
		}

		batchEngineTaskItemDelegate.setContextUriInfo(builder.build());

		batchEngineTaskItemDelegate.setContextUser(_user);
		batchEngineTaskItemDelegate.setLanguageId(_user.getLanguageId());
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
	private final SortParserProvider _sortParserProvider;
	private final User _user;

}