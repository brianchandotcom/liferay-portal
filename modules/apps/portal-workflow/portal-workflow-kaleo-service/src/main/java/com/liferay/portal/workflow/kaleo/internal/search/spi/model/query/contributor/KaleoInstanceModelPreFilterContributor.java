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
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.filter.DateRangeFilterBuilder;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoInstanceQuery;

import java.text.Format;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoInstance",
	service = ModelPreFilterContributor.class
)
public class KaleoInstanceModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		KaleoInstanceQuery kaleoInstanceQuery =
			(KaleoInstanceQuery)searchContext.getAttribute(
				"kaleoInstanceQuery");

		if (kaleoInstanceQuery == null) {
			return;
		}

		appendClassName(booleanFilter, kaleoInstanceQuery);
		appendClassPKTerm(booleanFilter, kaleoInstanceQuery);
		appendCompletedTerm(booleanFilter, kaleoInstanceQuery);
		appendCompletionDateRangeTerm(booleanFilter, kaleoInstanceQuery);
		appendCurrentKaleoNodeNameTerm(booleanFilter, kaleoInstanceQuery);
		appendKaleoDefinitionNameTerm(booleanFilter, kaleoInstanceQuery);
		appendKaleoDefinitionVersionIdTerm(booleanFilter, kaleoInstanceQuery);
		appendKaleoDefinitionVersionTerm(booleanFilter, kaleoInstanceQuery);
		appendKaleoInstanceIdTerm(booleanFilter, kaleoInstanceQuery);
		appendRootKaleoInstanceTokenIdTerm(booleanFilter, kaleoInstanceQuery);
	}

	protected void appendClassName(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		String[] classNames = kaleoInstanceQuery.getClassNames();

		if (ListUtil.isNull(ListUtil.fromArray(classNames))) {
			return;
		}

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		for (String className : classNames) {
			try {
				booleanQuery.addTerm("className", className);
			}
			catch (ParseException parseException) {
				throw new RuntimeException(parseException);
			}
		}

		booleanFilter.add(new QueryFilter(booleanQuery));
	}

	protected void appendClassPKTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Long classPK = kaleoInstanceQuery.getClassPK();

		if (classPK == null) {
			return;
		}

		booleanFilter.addRequiredTerm("classPK", classPK);
	}

	protected void appendCompletedTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Boolean completed = kaleoInstanceQuery.isCompleted();

		if (completed == null) {
			return;
		}

		booleanFilter.addRequiredTerm("completed", completed);
	}

	protected void appendCompletionDateRangeTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Date completionDateGT = kaleoInstanceQuery.getCompletionDateGT();
		Date completionDateLT = kaleoInstanceQuery.getCompletionDateLT();

		if ((completionDateGT == null) && (completionDateLT == null)) {
			return;
		}

		String formatPattern = PropsUtil.get(
			PropsKeys.INDEX_DATE_FORMAT_PATTERN);

		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			formatPattern);

		DateRangeFilterBuilder dueDateRangeFilterBuilder =
			filterBuilders.dateRangeFilterBuilder();

		dueDateRangeFilterBuilder.setFieldName("completionDate");

		if (completionDateGT != null) {
			dueDateRangeFilterBuilder.setFrom(
				dateFormat.format(completionDateGT));
		}

		if (completionDateLT != null) {
			dueDateRangeFilterBuilder.setTo(
				dateFormat.format(completionDateLT));
		}

		booleanFilter.add(
			dueDateRangeFilterBuilder.build(), BooleanClauseOccur.MUST);
	}

	protected void appendCurrentKaleoNodeNameTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		String currentKaleoNodeName =
			kaleoInstanceQuery.getCurrentKaleoNodeName();

		if (Validator.isNull(currentKaleoNodeName)) {
			return;
		}

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		try {
			booleanQuery.addTerm("currentKaleoNodeName", currentKaleoNodeName);
		}
		catch (ParseException parseException) {
			throw new RuntimeException(parseException);
		}

		booleanFilter.add(new QueryFilter(booleanQuery));
	}

	protected void appendKaleoDefinitionNameTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		String kaleoDefinitionName =
			kaleoInstanceQuery.getKaleoDefinitionName();

		if (Validator.isNull(kaleoDefinitionName)) {
			return;
		}

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		try {
			booleanQuery.addTerm("kaleoDefinitionName", kaleoDefinitionName);
		}
		catch (ParseException parseException) {
			throw new RuntimeException(parseException);
		}

		booleanFilter.add(new QueryFilter(booleanQuery));
	}

	protected void appendKaleoDefinitionVersionIdTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Long kaleoDefinitionVersionId =
			kaleoInstanceQuery.getKaleoDefinitionVersionId();

		if (kaleoDefinitionVersionId == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			"kaleoDefinitionVersionId", kaleoDefinitionVersionId);
	}

	protected void appendKaleoDefinitionVersionTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Integer kaleoDefinitionVersion =
			kaleoInstanceQuery.getKaleoDefinitionVersion();

		if (kaleoDefinitionVersion == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			"kaleoDefinitionVersion", kaleoDefinitionVersion);
	}

	protected void appendKaleoInstanceIdTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Long kaleoInstanceId = kaleoInstanceQuery.getKaleoInstanceId();

		if (kaleoInstanceId == null) {
			return;
		}

		booleanFilter.addRequiredTerm("kaleoInstanceId", kaleoInstanceId);
	}

	protected void appendRootKaleoInstanceTokenIdTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Long rootKaleoInstanceTokenId =
			kaleoInstanceQuery.getRootKaleoInstanceTokenId();

		if (rootKaleoInstanceTokenId == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			"rootKaleoInstanceTokenId", rootKaleoInstanceTokenId);
	}

	@Reference
	protected FilterBuilders filterBuilders;

}