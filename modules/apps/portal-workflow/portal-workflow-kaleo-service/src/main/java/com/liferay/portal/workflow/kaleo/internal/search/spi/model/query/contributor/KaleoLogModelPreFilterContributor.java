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
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;
import com.liferay.portal.workflow.kaleo.definition.util.KaleoLogUtil;

import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoLog",
	service = ModelPreFilterContributor.class
)
public class KaleoLogModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		long kaleoInstanceId = GetterUtil.getLong(
			searchContext.getAttribute("kaleoInstanceId"));

		if (kaleoInstanceId > 0) {
			booleanFilter.addRequiredTerm("kaleoInstanceId", kaleoInstanceId);
		}

		long kaleoTaskInstanceTokenId = GetterUtil.getLong(
			searchContext.getAttribute("kaleoTaskInstanceTokenId"));

		if (kaleoTaskInstanceTokenId > 0) {
			booleanFilter.addRequiredTerm(
				"kaleoTaskInstanceTokenId", kaleoTaskInstanceTokenId);
		}

		Integer[] logTypes = (Integer[])searchContext.getAttribute("logTypes");

		if (ArrayUtil.isNotEmpty(logTypes)) {
			TermsFilter termsFilter = new TermsFilter("type");

			termsFilter.addValues(
				Stream.of(
					logTypes
				).map(
					KaleoLogUtil::convert
				).toArray(
					String[]::new
				));

			booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}
	}

}