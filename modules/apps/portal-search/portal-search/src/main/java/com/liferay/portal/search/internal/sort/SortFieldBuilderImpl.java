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

package com.liferay.portal.search.internal.sort;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.contributor.ContributorConstants;
import com.liferay.portal.search.contributor.sort.SortFieldNameTranslator;
import com.liferay.portal.search.sort.SortFieldBuilder;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SortFieldBuilder.class)
public class SortFieldBuilderImpl implements SortFieldBuilder {

	@Override
	public String getSortField(String entityClassName, String orderByCol) {
		String sortField = doGetSortField(entityClassName, orderByCol);

		if (_defaultSortableTextFields.contains(sortField)) {
			return Field.getSortableFieldName(sortField);
		}

		return sortField;
	}

	@Override
	public String getSortField(
		String entityClassName, String orderByCol, int sortType) {

		if ((sortType == Sort.DOUBLE_TYPE) || (sortType == Sort.FLOAT_TYPE) ||
			(sortType == Sort.INT_TYPE) || (sortType == Sort.LONG_TYPE)) {

			return Field.getSortableFieldName(orderByCol);
		}

		return getSortField(entityClassName, orderByCol);
	}

	@Activate
	protected void activate() {
		_defaultSortableTextFields = SetUtil.fromArray(
			props.getArray(PropsKeys.INDEX_SORTABLE_TEXT_FIELDS));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addSortFieldNameTranslator(
		SortFieldNameTranslator sortFieldNameTranslator,
		Map<String, Object> properties) {

		String entryClassName = GetterUtil.getString(
			properties.get(ContributorConstants.ENTRY_CLASS_NAME_PROPERTY_KEY));

		if (Validator.isNull(entryClassName)) {
			throw new IllegalArgumentException("No entry.class.name provided");
		}

		_sortFieldNameTranslators.put(entryClassName, sortFieldNameTranslator);
	}

	protected String doGetSortField(String entityClassName, String orderByCol) {
		SortFieldNameTranslator sortFieldNameTranslator =
			_sortFieldNameTranslators.get(entityClassName);

		String sortFieldName = orderByCol;

		if (sortFieldNameTranslator == null) {
			Indexer<?> indexer = indexerRegistry.getIndexer(entityClassName);

			return indexer.getSortField(orderByCol);
		}

		sortFieldName = sortFieldNameTranslator.getSortFieldName(orderByCol);

		return sortFieldName;
	}

	protected void removeSortFieldNameTranslator(
		SortFieldNameTranslator sortFieldNameTranslator,
		Map<String, Object> properties) {

		String entryClassName = GetterUtil.getString(
			properties.get(ContributorConstants.ENTRY_CLASS_NAME_PROPERTY_KEY));

		if (Validator.isNull(entryClassName)) {
			return;
		}

		_sortFieldNameTranslators.remove(entryClassName);
	}

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference
	protected Props props;

	private Set<String> _defaultSortableTextFields;
	private final Map<String, SortFieldNameTranslator>
		_sortFieldNameTranslators = new ConcurrentHashMap<>();

}