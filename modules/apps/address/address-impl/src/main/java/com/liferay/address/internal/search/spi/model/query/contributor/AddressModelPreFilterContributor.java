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

package com.liferay.address.internal.search.spi.model.query.contributor;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Address",
	service = ModelPreFilterContributor.class
)
public class AddressModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_filterByClass(booleanFilter, searchContext);
		_filterByListTypeId(booleanFilter, searchContext);
	}

	private void _filterByClass(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long classNameId = GetterUtil.getLong(
			searchContext.getAttribute(Field.CLASS_NAME_ID));

		if (classNameId > 0) {
			booleanFilter.addTerm(
				Field.CLASS_NAME_ID, String.valueOf(classNameId),
				BooleanClauseOccur.MUST);
		}

		long classPK = GetterUtil.getLong(
			searchContext.getAttribute(Field.CLASS_PK), Long.MIN_VALUE);

		if (classPK > Long.MIN_VALUE) {
			booleanFilter.addTerm(
				Field.CLASS_PK, String.valueOf(classPK),
				BooleanClauseOccur.MUST);
		}
	}

	private void _filterByListTypeId(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		long[] listTypeIds = (long[])params.getOrDefault(
			"listTypeIds", new long[0]);
		String[] typeNames = (String[])params.get("typeNames");

		if (ArrayUtil.isNotEmpty(typeNames)) {
			listTypeIds = ArrayUtil.unique(
				ArrayUtil.append(
					listTypeIds, _getTypeIds(searchContext, typeNames)));
		}

		if (ArrayUtil.isNotEmpty(listTypeIds)) {
			TermsFilter termsFilter = new TermsFilter("listTypeId");

			termsFilter.addValues(ArrayUtil.toStringArray(listTypeIds));

			booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}
	}

	private long[] _getTypeIds(
		SearchContext searchContext, String[] typeNames) {

		long classNameId = GetterUtil.getLong(
			searchContext.getAttribute(Field.CLASS_NAME_ID));

		if (classNameId <= 0) {
			return new long[0];
		}

		ClassName className = _classNameLocalService.fetchByClassNameId(
			classNameId);

		if (className == null) {
			return new long[0];
		}

		Stream<String> typeNamesStream = Arrays.stream(typeNames);

		return typeNamesStream.mapToLong(
			typeName -> {
				String listTypeType =
					className.getClassName() + ListTypeConstants.ADDRESS;

				ListType listType = _listTypeLocalService.getListType(
					typeName, listTypeType);

				if (listType == null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"No list type found for ", listTypeType,
								" with the name: ", typeName));
					}

					return -1;
				}

				return listType.getListTypeId();
			}
		).toArray();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddressModelPreFilterContributor.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

}