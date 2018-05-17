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
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.user.segment.criterion.type.key=" + CommerceUserSegmentCriterionConstants.TYPE_ORGANIZATION,
		"commerce.user.segment.criterion.type.order:Integer=20"
	},
	service = CommerceUserSegmentCriterionType.class
)
public class OrganizationCommerceUserSegmentCriterionTypeImpl
	extends BaseCommerceUserSegmentCriterionType {

	@Override
	public String getKey() {
		return CommerceUserSegmentCriterionConstants.TYPE_ORGANIZATION;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "organizations");
	}

	@Override
	public String getPreview(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion, int length) {

		if (length <= 0) {
			return StringPool.BLANK;
		}

		List<String> organizationNames = new ArrayList<>();

		String[] organizationIds = StringUtil.split(
			commerceUserSegmentCriterion.getTypeSettings());

		for (String organizationId : organizationIds) {
			Organization organization =
				_organizationLocalService.fetchOrganization(
					GetterUtil.getLong(organizationId));

			if (organization != null) {
				organizationNames.add(organization.getName());
			}
		}

		String preview = StringUtil.merge(
			organizationNames, StringPool.COMMA_AND_SPACE);

		return StringUtil.shorten(preview, length, StringPool.TRIPLE_PERIOD);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws PortalException {

		long organizationId = GetterUtil.getLong(
			searchContext.getAttribute("organizationId"));

		if (organizationId > 0) {
			organizationPostProcessContextBooleanFilter(
				contextBooleanFilter, organizationId);
		}
		else {
			super.postProcessContextBooleanFilter(
				contextBooleanFilter, searchContext);
		}
	}

	@Override
	protected long[] getUserClassPKs(User user) throws PortalException {
		List<Long> userClassPKsList = new ArrayList<>();

		long[] organizationIds = user.getOrganizationIds();

		for (long organizationId : organizationIds) {
			Organization organization =
				_organizationLocalService.getOrganization(organizationId);

			long[] ancestorOrganizationIds =
				organization.getAncestorOrganizationIds();

			userClassPKsList.addAll(ListUtil.toList(ancestorOrganizationIds));

			userClassPKsList.add(organizationId);
		}

		Stream<Long> stream = userClassPKsList.stream();

		return stream.mapToLong(field -> field.longValue()).toArray();
	}

	protected void organizationPostProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, long organizationId)
		throws PortalException {

		Organization organization = _organizationLocalService.getOrganization(
			organizationId);

		long[] ancestorOrganizationIds =
			organization.getAncestorOrganizationIds();

		Filter existFilter = new TermFilter(
			getIndexerFieldName() + "_required_matches", "0");

		TermsFilter organizationFilter = new TermsFilter(getIndexerFieldName());

		organizationFilter.addValues(
			ArrayUtil.toStringArray(ancestorOrganizationIds));

		organizationFilter.addValue(String.valueOf(organizationId));

		BooleanFilter fieldBooleanFilter = new BooleanFilter();

		fieldBooleanFilter.add(existFilter, BooleanClauseOccur.SHOULD);
		fieldBooleanFilter.add(organizationFilter, BooleanClauseOccur.SHOULD);

		contextBooleanFilter.add(fieldBooleanFilter, BooleanClauseOccur.MUST);
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

}