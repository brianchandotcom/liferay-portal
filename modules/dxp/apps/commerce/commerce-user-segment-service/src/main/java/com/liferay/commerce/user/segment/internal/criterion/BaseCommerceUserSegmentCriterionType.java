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

package com.liferay.commerce.user.segment.internal.criterion;

import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionType;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.filter.TermsSetFilterBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
public abstract class BaseCommerceUserSegmentCriterionType
	implements CommerceUserSegmentCriterionType {

	@Override
	public void contributeToDocument(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion,
		Document document) {

		if (commerceUserSegmentCriterion == null) {
			return;
		}

		if (Validator.isNull(commerceUserSegmentCriterion.getValue())) {
			return;
		}

		long[] classPKs = StringUtil.split(
			commerceUserSegmentCriterion.getValue(), 0L);

		document.addKeyword(getIndexerFieldName(), classPKs);
		document.addKeyword(
			getIndexerFieldName() + "required_matches", classPKs.length);
	}

	@Override
	public List<String> getConditions() {
		List<String> conditions = new ArrayList<>();

		conditions.add("contains");
		conditions.add("not_contains");

		return Collections.unmodifiableList(conditions);
	}

	@Override
	public boolean isSatisfied(
			long commerceUserSegmentCriterionId, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommerceUserSegmentCriterion commerceUserSegmentCriterion =
			commerceUserSegmentCriterionLocalService.
				getCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);

		long[] classPKs = StringUtil.split(
			commerceUserSegmentCriterion.getValue(), 0L);

		if (ArrayUtil.containsAll(classPKs, getUserClassPKs(user))) {
			return true;
		}

		return false;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws PortalException {

		User user = userLocalService.getUser(searchContext.getUserId());

		long[] classPKs = getUserClassPKs(user);

		TermsSetFilterBuilder termsSetFilterBuilder =
			filterBuilders.termsSetFilterBuilder();

		termsSetFilterBuilder.setFieldName(getIndexerFieldName());
		termsSetFilterBuilder.setMinimumShouldMatchField(
			getIndexerFieldName() + "required_matches");

		List<String> values = new ArrayList<>(classPKs.length);

		for (long classPK : classPKs) {
			values.add(String.valueOf(classPK));
		}

		termsSetFilterBuilder.setValues(values);

		Filter existFilter = new ExistsFilter(getIndexerFieldName());

		BooleanFilter existBooleanFilter = new BooleanFilter();

		existBooleanFilter.add(existFilter, BooleanClauseOccur.MUST_NOT);

		BooleanFilter fieldBooleanFilter = new BooleanFilter();

		fieldBooleanFilter.add(existBooleanFilter, BooleanClauseOccur.SHOULD);
		fieldBooleanFilter.add(
			termsSetFilterBuilder.build(), BooleanClauseOccur.SHOULD);

		contextBooleanFilter.add(fieldBooleanFilter, BooleanClauseOccur.MUST);
	}

	protected String getIndexerFieldName() {
		return "criterionType_" + getKey();
	}

	protected abstract long[] getUserClassPKs(User user) throws PortalException;

	@Reference
	protected CommerceUserSegmentCriterionLocalService
		commerceUserSegmentCriterionLocalService;

	@Reference
	protected FilterBuilders filterBuilders;

	@Reference
	protected UserLocalService userLocalService;

}