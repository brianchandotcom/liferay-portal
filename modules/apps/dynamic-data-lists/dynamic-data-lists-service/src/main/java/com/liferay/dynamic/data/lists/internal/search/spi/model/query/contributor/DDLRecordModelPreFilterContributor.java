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

package com.liferay.dynamic.data.lists.internal.search.spi.model.query.contributor;

import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.lists.model.DDLRecord",
	service = ModelPreFilterContributor.class
)
public class DDLRecordModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			booleanFilter.addRequiredTerm(Field.STATUS, status);
		}

		long recordSetId = GetterUtil.getLong(
			searchContext.getAttribute("recordSetId"));

		if (recordSetId > 0) {
			booleanFilter.addRequiredTerm("recordSetId", recordSetId);
		}

		long recordSetScope = GetterUtil.getLong(
			searchContext.getAttribute("recordSetScope"),
			DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);

		booleanFilter.addRequiredTerm("recordSetScope", recordSetScope);

		addSearchClassTypeIds(booleanFilter, searchContext);

		String ddmStructureFieldName = (String)searchContext.getAttribute(
			"ddmStructureFieldName");
		Serializable ddmStructureFieldValue = searchContext.getAttribute(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue)) {

			try {
				QueryFilter queryFilter =
					ddmIndexer.createFieldValueQueryFilter(
						ddmStructureFieldName, ddmStructureFieldValue,
						searchContext.getLocale());

				booleanFilter.add(queryFilter, BooleanClauseOccur.MUST);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}
	}

	protected Filter addSearchClassTypeIds(
		BooleanFilter contextBooleanFilter, SearchContext searchContext) {

		long[] classTypeIds = searchContext.getClassTypeIds();

		if (ArrayUtil.isEmpty(classTypeIds)) {
			return null;
		}

		TermsFilter termsFilter = new TermsFilter(Field.CLASS_TYPE_ID);

		termsFilter.addValues(ArrayUtil.toStringArray(classTypeIds));

		return contextBooleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
	}

	@Reference
	protected DDMIndexer ddmIndexer;

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordModelPreFilterContributor.class);

}