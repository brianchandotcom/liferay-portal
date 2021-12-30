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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.search.filter.DateRangeFilterBuilder;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoInstanceQuery;

import java.text.Format;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

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

		BooleanFilter innerBooleanFilter = new BooleanFilter();

		appendAssetTitleTerm(
			innerBooleanFilter, kaleoInstanceQuery, searchContext);
		appendAssetDescriptionTerm(
			innerBooleanFilter, kaleoInstanceQuery, searchContext);
		_appendClassName(innerBooleanFilter, kaleoInstanceQuery);
		appendCurrentKaleoNodeNameTerm(innerBooleanFilter, kaleoInstanceQuery);
		appendKaleoDefinitionNameTerm(innerBooleanFilter, kaleoInstanceQuery);

		if (innerBooleanFilter.hasClauses()) {
			booleanFilter.add(innerBooleanFilter, BooleanClauseOccur.MUST);
		}

		_appendActiveTerm(booleanFilter, kaleoInstanceQuery);
		appendClassNameIdsTerm(booleanFilter, kaleoInstanceQuery);
		_appendClassPKTerm(booleanFilter, kaleoInstanceQuery);
		appendCompletedTerm(booleanFilter, kaleoInstanceQuery);
		appendCompletionDateRangeTerm(booleanFilter, kaleoInstanceQuery);
		_appendKaleoDefinitionVersionIdTerm(booleanFilter, kaleoInstanceQuery);
		_appendKaleoDefinitionVersionTerm(booleanFilter, kaleoInstanceQuery);
		appendKaleoInstanceIdTerm(booleanFilter, kaleoInstanceQuery);
		_appendRootKaleoInstanceTokenIdTerm(booleanFilter, kaleoInstanceQuery);
		appendUserIdTerm(booleanFilter, kaleoInstanceQuery);
	}

	protected void appendAssetDescriptionTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery,
		SearchContext searchContext) {

		String assetDescription = kaleoInstanceQuery.getAssetDescription();

		if (Validator.isNull(assetDescription)) {
			return;
		}

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		try {
			booleanQuery.addTerm(
				LocalizationUtil.getLocalizedName(
					"assetDescription", searchContext.getLanguageId()),
				assetDescription);
		}
		catch (ParseException parseException) {
			throw new RuntimeException(parseException);
		}

		booleanFilter.add(new QueryFilter(booleanQuery));
	}

	protected void appendAssetTitleTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery,
		SearchContext searchContext) {

		String assetTitle = kaleoInstanceQuery.getAssetTitle();

		if (Validator.isNull(assetTitle)) {
			return;
		}

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		try {
			booleanQuery.addTerm(
				LocalizationUtil.getLocalizedName(
					"assetTitle", searchContext.getLanguageId()),
				assetTitle);
		}
		catch (ParseException parseException) {
			throw new RuntimeException(parseException);
		}

		booleanFilter.add(new QueryFilter(booleanQuery));
	}

	protected void appendClassNameIdsTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		if (!kaleoInstanceQuery.isSearchByActiveWorkflowHandlers()) {
			return;
		}

		TermsFilter classNameIdsTermsFilter = new TermsFilter(
			Field.CLASS_NAME_ID);

		classNameIdsTermsFilter.addValues(
			Stream.of(
				WorkflowHandlerRegistryUtil.getWorkflowHandlers()
			).flatMap(
				List::stream
			).map(
				workflowHandler -> portal.getClassNameId(
					workflowHandler.getClassName())
			).map(
				String::valueOf
			).toArray(
				String[]::new
			));

		booleanFilter.add(classNameIdsTermsFilter, BooleanClauseOccur.MUST);
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

	protected void appendKaleoInstanceIdTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Long kaleoInstanceId = kaleoInstanceQuery.getKaleoInstanceId();

		if (kaleoInstanceId == null) {
			return;
		}

		booleanFilter.addRequiredTerm("kaleoInstanceId", kaleoInstanceId);
	}

	protected void appendUserIdTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Long userId = kaleoInstanceQuery.getUserId();

		if (userId == null) {
			return;
		}

		booleanFilter.addRequiredTerm(Field.USER_ID, userId);
	}

	@Reference
	protected FilterBuilders filterBuilders;

	@Reference
	protected Portal portal;

	private void _appendActiveTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Boolean active = kaleoInstanceQuery.isActive();

		if (active == null) {
			return;
		}

		booleanFilter.addRequiredTerm("active", active);
	}

	private void _appendClassName(
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

	private void _appendClassPKTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Long classPK = kaleoInstanceQuery.getClassPK();

		if (classPK == null) {
			return;
		}

		booleanFilter.addRequiredTerm("classPK", classPK);
	}

	private void _appendKaleoDefinitionVersionIdTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Long kaleoDefinitionVersionId =
			kaleoInstanceQuery.getKaleoDefinitionVersionId();

		if (kaleoDefinitionVersionId == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			"kaleoDefinitionVersionId", kaleoDefinitionVersionId);
	}

	private void _appendKaleoDefinitionVersionTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Integer kaleoDefinitionVersion =
			kaleoInstanceQuery.getKaleoDefinitionVersion();

		if (kaleoDefinitionVersion == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			"kaleoDefinitionVersion", kaleoDefinitionVersion);
	}

	private void _appendRootKaleoInstanceTokenIdTerm(
		BooleanFilter booleanFilter, KaleoInstanceQuery kaleoInstanceQuery) {

		Long rootKaleoInstanceTokenId =
			kaleoInstanceQuery.getRootKaleoInstanceTokenId();

		if (rootKaleoInstanceTokenId == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			"rootKaleoInstanceTokenId", rootKaleoInstanceTokenId);
	}

}