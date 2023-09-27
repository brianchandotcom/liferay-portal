/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search.spi.model.query.contributor;

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
	property = "indexer.class.name=com.liferay.change.tracking.model.CTEntry",
	service = ModelPreFilterContributor.class
)
public class CTEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		int[] changeTypes = GetterUtil.getIntegerValues(
			searchContext.getAttribute("changeType"));

		_addTermsFilter(
			booleanFilter, "changeType", ArrayUtil.toStringArray(changeTypes));

		long ctCollectionId = GetterUtil.getLong(
			searchContext.getAttribute("ctCollectionId"));

		booleanFilter.addRequiredTerm("ctCollectionId", ctCollectionId);

		long[] groupIds = GetterUtil.getLongValues(
			searchContext.getAttribute(Field.GROUP_ID));

		_addTermsFilter(
			booleanFilter, Field.GROUP_ID, ArrayUtil.toStringArray(groupIds));

		long[] modelClassNameIds = GetterUtil.getLongValues(
			searchContext.getAttribute("modelClassNameId"));

		_addTermsFilter(
			booleanFilter, "modelClassNameId",
			ArrayUtil.toStringArray(modelClassNameIds));

		long[] userIds = GetterUtil.getLongValues(
			searchContext.getAttribute(Field.USER_ID));

		_addTermsFilter(
			booleanFilter, Field.USER_ID, ArrayUtil.toStringArray(userIds));
	}

	private void _addTermsFilter(
		BooleanFilter booleanFilter, String field, String[] fieldValues) {

		if (ArrayUtil.isEmpty(fieldValues)) {
			return;
		}

		TermsFilter termsFilter = new TermsFilter(field);

		termsFilter.addValues(fieldValues);

		booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
	}

}