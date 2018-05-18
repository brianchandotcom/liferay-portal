/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.filter.TermsSetFilterBuilder;

import java.util.ArrayList;
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

		if (Validator.isNull(commerceUserSegmentCriterion.getTypeSettings())) {
			return;
		}

		long[] classPKs = StringUtil.split(
			commerceUserSegmentCriterion.getTypeSettings(), 0L);

		document.addKeyword(getIndexerFieldName(), classPKs);
		document.addKeyword(
			getIndexerFieldName() + "_required_matches", classPKs.length);
	}

	@Override
	public void contributeToDocument(Document document) {
		document.addKeyword(getIndexerFieldName(), new long[0]);
		document.addKeyword(getIndexerFieldName() + "_required_matches", 0);
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
			commerceUserSegmentCriterion.getTypeSettings(), 0L);

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
			getIndexerFieldName() + "_required_matches");

		List<String> values = new ArrayList<>(classPKs.length);

		for (long classPK : classPKs) {
			values.add(String.valueOf(classPK));
		}

		termsSetFilterBuilder.setValues(values);

		Filter existFilter = new TermFilter(
			getIndexerFieldName() + "_required_matches", "0");

		BooleanFilter fieldBooleanFilter = new BooleanFilter();

		fieldBooleanFilter.add(existFilter, BooleanClauseOccur.SHOULD);
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