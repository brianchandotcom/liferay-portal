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

package com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcelo Mello
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMStructureLayout",
	service = ModelPreFilterContributor.class
)
public class DDMStructureLayoutModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		long structureVersionId = GetterUtil.getLong(
			searchContext.getAttribute("structureVersionId"));

		if (structureVersionId > 0) {
			booleanFilter.addRequiredTerm(
				"structureVersionId", structureVersionId);
		}

		addWorkflowStatusFilter(
			booleanFilter, modelSearchSettings, searchContext);
	}

	protected void addRequiredTerm(
		BooleanFilter booleanFilter, SearchContext searchContext,
		String fieldName) {

		Serializable fieldValue = searchContext.getAttribute(fieldName);

		if (Validator.isNotNull(fieldValue)) {
			booleanFilter.addRequiredTerm(
				fieldName, String.valueOf(fieldValue));
		}
	}

	protected void addRequiredTerms(
		BooleanFilter booleanFilter, SearchContext searchContext,
		String fieldName, String contextFieldName) {

		long[] longValues = GetterUtil.getLongValues(
			searchContext.getAttribute(contextFieldName), null);

		if (ArrayUtil.isNotEmpty(longValues)) {
			TermsFilter termsFilter = new TermsFilter(fieldName);

			termsFilter.addValues(ArrayUtil.toStringArray(longValues));

			booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}
	}

	protected void addWorkflowStatusFilter(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		Serializable fieldValue = searchContext.getAttribute(Field.STATUS);

		if (Validator.isNull(fieldValue)) {
			return;
		}

		workflowStatusModelPreFilterContributor.contribute(
			booleanFilter, modelSearchSettings, searchContext);
	}

	@Reference(target = "(model.pre.filter.contributor.id=WorkflowStatus)")
	protected ModelPreFilterContributor workflowStatusModelPreFilterContributor;

}