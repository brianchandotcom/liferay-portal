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

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;
import com.liferay.portal.workflow.kaleo.internal.search.KaleoInstanceTokenField;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoInstanceTokenQuery;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author István András Dézsi
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken",
	service = KeywordQueryContributor.class
)
public class KaleoInstanceTokenKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		KaleoInstanceTokenQuery kaleoInstanceTokenQuery =
			getKaleoInstanceTokenQuery(keywordQueryContributorHelper);

		if (kaleoInstanceTokenQuery == null) {
			return;
		}

		appendAssetDescriptionTerm(
			booleanQuery, kaleoInstanceTokenQuery.getAssetDescription(),
			keywordQueryContributorHelper);
		appendAssetTitleTerm(
			booleanQuery, kaleoInstanceTokenQuery.getAssetTitle(),
			keywordQueryContributorHelper);
		appendClassNameTerm(
			booleanQuery, kaleoInstanceTokenQuery.getClassName(),
			keywordQueryContributorHelper);
		appendCurrentKaleoNodeNameTerm(
			booleanQuery, kaleoInstanceTokenQuery.getCurrentKaleoNodeName(),
			keywordQueryContributorHelper);
		appendKaleoDefinitionNameTerm(
			booleanQuery, kaleoInstanceTokenQuery.getKaleoDefinitionName(),
			keywordQueryContributorHelper);
	}

	protected void appendAssetDescriptionTerm(
		BooleanQuery booleanQuery, String assetDescription,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		if (Validator.isNull(assetDescription)) {
			return;
		}

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		String assetDescriptionLocalizedName =
			LocalizationUtil.getLocalizedName(
				KaleoInstanceTokenField.ASSET_DESCRIPTION,
				searchContext.getLanguageId());

		searchContext.setAttribute(
			assetDescriptionLocalizedName, assetDescription);

		queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext,
			KaleoInstanceTokenField.ASSET_DESCRIPTION, true);
	}

	protected void appendAssetTitleTerm(
		BooleanQuery booleanQuery, String assetTitle,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		if (Validator.isNull(assetTitle)) {
			return;
		}

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		String assetTitleLocalizedName = LocalizationUtil.getLocalizedName(
			KaleoInstanceTokenField.ASSET_TITLE, searchContext.getLanguageId());

		searchContext.setAttribute(assetTitleLocalizedName, assetTitle);

		queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, KaleoInstanceTokenField.ASSET_TITLE,
			false);
	}

	protected void appendClassNameTerm(
		BooleanQuery booleanQuery, String className,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		if (Validator.isNull(className)) {
			return;
		}

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		searchContext.setAttribute(
			KaleoInstanceTokenField.CLASS_NAME, className);

		queryHelper.addSearchTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			KaleoInstanceTokenField.CLASS_NAME, false);
	}

	protected void appendCurrentKaleoNodeNameTerm(
		BooleanQuery booleanQuery, String currentKaleoNodeName,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		if (Validator.isNull(currentKaleoNodeName)) {
			return;
		}

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		searchContext.setAttribute(
			KaleoInstanceTokenField.CURRENT_KALEO_NODE_NAME,
			currentKaleoNodeName);

		queryHelper.addSearchTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			KaleoInstanceTokenField.CURRENT_KALEO_NODE_NAME, false);
	}

	protected void appendKaleoDefinitionNameTerm(
		BooleanQuery booleanQuery, String kaleoDefinitionName,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		if (Validator.isNull(kaleoDefinitionName)) {
			return;
		}

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		searchContext.setAttribute(
			KaleoInstanceTokenField.KALEO_DEFINITION_NAME, kaleoDefinitionName);

		queryHelper.addSearchTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			KaleoInstanceTokenField.KALEO_DEFINITION_NAME, false);
	}

	protected KaleoInstanceTokenQuery getKaleoInstanceTokenQuery(
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		return (KaleoInstanceTokenQuery)searchContext.getAttribute(
			"kaleoInstanceTokenQuery");
	}

	@Reference
	protected QueryHelper queryHelper;

}