/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.query.contributor;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;
import com.liferay.portal.workflow.kaleo.definition.util.KaleoLogUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
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
				TransformUtil.transform(
					logTypes, KaleoLogUtil::convert, String.class));

			booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}
	}

}