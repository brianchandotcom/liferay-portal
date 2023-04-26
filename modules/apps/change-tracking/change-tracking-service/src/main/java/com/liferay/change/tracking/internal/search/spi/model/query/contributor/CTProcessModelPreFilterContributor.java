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

package com.liferay.change.tracking.internal.search.spi.model.query.contributor;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "indexer.class.name=com.liferay.change.tracking.model.CTProcess",
	service = ModelPreFilterContributor.class
)
public class CTProcessModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		int[] statuses = GetterUtil.getIntegerValues(
			searchContext.getAttribute("statuses"));

		if (ArrayUtil.isNotEmpty(statuses)) {
			TermsFilter termsFilter = new TermsFilter("status");

			termsFilter.addValues(ArrayUtil.toStringArray(statuses));

			booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}

		int type = GetterUtil.getInteger(
			searchContext.getAttribute(Field.TYPE),
			CTConstants.CT_PROCESS_PUBLISH);

		if (type > -1) {
			booleanFilter.addRequiredTerm(Field.TYPE, type);
		}

		long userId = GetterUtil.getLong(
			searchContext.getAttribute(Field.USER_ID));

		if (userId > 0) {
			booleanFilter.addRequiredTerm(Field.USER_ID, userId);
		}
	}

}