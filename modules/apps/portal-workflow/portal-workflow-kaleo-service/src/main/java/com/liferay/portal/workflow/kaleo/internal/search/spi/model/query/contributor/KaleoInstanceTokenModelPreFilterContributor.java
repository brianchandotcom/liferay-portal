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

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.filter.DateRangeFilterBuilder;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;
import com.liferay.portal.workflow.kaleo.internal.search.KaleoInstanceTokenField;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoInstanceTokenQuery;

import java.text.Format;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author István András Dézsi
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken",
	service = ModelPreFilterContributor.class
)
public class KaleoInstanceTokenModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		KaleoInstanceTokenQuery kaleoInstanceTokenQuery =
			(KaleoInstanceTokenQuery)searchContext.getAttribute(
				"kaleoInstanceTokenQuery");

		if (kaleoInstanceTokenQuery == null) {
			return;
		}

		appendCompletedTerm(booleanFilter, kaleoInstanceTokenQuery);
		appendCompletionDateRangeTerm(booleanFilter, kaleoInstanceTokenQuery);
		appendKaleoInstanceIdTerm(booleanFilter, kaleoInstanceTokenQuery);
		appendKaleoInstanceTokenIdTerm(booleanFilter, kaleoInstanceTokenQuery);
		appendParentKaleoInstanceTokenIdTerm(
			booleanFilter, kaleoInstanceTokenQuery);
		appendUserIdTerm(booleanFilter, kaleoInstanceTokenQuery);
	}

	protected void appendCompletedTerm(
		BooleanFilter booleanFilter,
		KaleoInstanceTokenQuery kaleoInstanceTokenQuery) {

		Boolean completed = kaleoInstanceTokenQuery.isCompleted();

		if (completed == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			KaleoInstanceTokenField.COMPLETED, completed);
	}

	protected void appendCompletionDateRangeTerm(
		BooleanFilter booleanFilter,
		KaleoInstanceTokenQuery kaleoInstanceTokenQuery) {

		Date completionDateGT = kaleoInstanceTokenQuery.getCompletionDateGT();
		Date completionDateLT = kaleoInstanceTokenQuery.getCompletionDateLT();

		if ((completionDateGT == null) && (completionDateLT == null)) {
			return;
		}

		String formatPattern = PropsUtil.get(
			PropsKeys.INDEX_DATE_FORMAT_PATTERN);

		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			formatPattern);

		DateRangeFilterBuilder completionDateRangeFilterBuilder =
			filterBuilders.dateRangeFilterBuilder();

		completionDateRangeFilterBuilder.setFieldName(
			KaleoInstanceTokenField.COMPLETION_DATE);

		if (completionDateGT != null) {
			completionDateRangeFilterBuilder.setFrom(
				dateFormat.format(completionDateGT));
		}

		if (completionDateLT != null) {
			completionDateRangeFilterBuilder.setTo(
				dateFormat.format(completionDateLT));
		}

		booleanFilter.add(
			completionDateRangeFilterBuilder.build(), BooleanClauseOccur.MUST);
	}

	protected void appendKaleoInstanceIdTerm(
		BooleanFilter booleanFilter,
		KaleoInstanceTokenQuery kaleoInstanceTokenQuery) {

		Long kaleoInstanceId = kaleoInstanceTokenQuery.getKaleoInstanceId();

		if (kaleoInstanceId == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			KaleoInstanceTokenField.KALEO_INSTANCE_ID, kaleoInstanceId);
	}

	protected void appendKaleoInstanceTokenIdTerm(
		BooleanFilter booleanFilter,
		KaleoInstanceTokenQuery kaleoInstanceTokenQuery) {

		Long kaleoInstanceTokenId =
			kaleoInstanceTokenQuery.getKaleoInstanceTokenId();

		if (kaleoInstanceTokenId == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			KaleoInstanceTokenField.KALEO_INSTANCE_TOKEN_ID,
			kaleoInstanceTokenId);
	}

	protected void appendParentKaleoInstanceTokenIdTerm(
		BooleanFilter booleanFilter,
		KaleoInstanceTokenQuery kaleoInstanceTokenQuery) {

		Long parentKaleoInstanceTokenId =
			kaleoInstanceTokenQuery.getParentKaleoInstanceTokenId();

		if (parentKaleoInstanceTokenId == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			KaleoInstanceTokenField.PARENT_KALEO_INSTANCE_TOKEN_ID,
			parentKaleoInstanceTokenId);
	}

	protected void appendUserIdTerm(
		BooleanFilter booleanFilter,
		KaleoInstanceTokenQuery kaleoInstanceTokenQuery) {

		Long userId = kaleoInstanceTokenQuery.getUserId();

		if (userId == null) {
			return;
		}

		booleanFilter.addRequiredTerm(Field.USER_ID, userId);
	}

	@Reference
	protected FilterBuilders filterBuilders;

}