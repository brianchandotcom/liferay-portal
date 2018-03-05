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

package com.liferay.commerce.price.list.qualification.type.internal;

import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;
import com.liferay.commerce.price.CommercePriceListQualificationType;
import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;
import com.liferay.commerce.price.list.qualification.type.service.CommercePriceListUserRelLocalService;
import com.liferay.commerce.service.CommercePriceListQualificationTypeRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
public abstract class BaseCommercePriceListQualificationType
	implements CommercePriceListQualificationType {

	@Override
	public void contributeToDocument(
		CommercePriceList commercePriceList, Document document) {

		CommercePriceListQualificationTypeRel
			commercePriceListQualificationTypeRel =
				commercePriceListQualificationTypeRelLocalService.
					fetchCommercePriceListQualificationTypeRel(
						getKey(), commercePriceList.getCommercePriceListId());

		if (commercePriceListQualificationTypeRel == null) {
			return;
		}

		List<CommercePriceListUserRel> commercePriceListUserRels =
			commercePriceListUserRelLocalService.getCommercePriceListUserRels(
				commercePriceListQualificationTypeRel.
					getCommercePriceListQualificationTypeRelId(),
				getClassName());

		Stream<CommercePriceListUserRel> stream =
			commercePriceListUserRels.stream();

		long[] classPKs = stream.mapToLong(
			CommercePriceListUserRel::getClassPK).toArray();

		if (classPKs.length == 0) {
			classPKs = new long[] {-1};
		}

		document.addKeyword(getIndexerFieldName(), classPKs);
	}

	@Override
	public boolean isQualified(
			long commercePriceListQualificationTypeRelId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		long commercePriceListUserRelsCount =
			commercePriceListUserRelLocalService.
				getCommercePriceListUserRelsCount(
					commercePriceListQualificationTypeRelId, getClassName(),
					getUserClassPKs(user));

		if (commercePriceListUserRelsCount > 0) {
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

		BooleanFilter pksBooleanFilter = new BooleanFilter();

		for (long classPK : classPKs) {
			TermFilter termFilter = new TermFilter(
				getIndexerFieldName(), String.valueOf(classPK));

			pksBooleanFilter.add(termFilter, BooleanClauseOccur.SHOULD);
		}

		Filter existFilter = new ExistsFilter(getIndexerFieldName());

		BooleanFilter existBooleanFilter = new BooleanFilter();

		existBooleanFilter.add(existFilter, BooleanClauseOccur.MUST_NOT);

		BooleanFilter fieldBooleanFilter = new BooleanFilter();

		fieldBooleanFilter.add(existBooleanFilter, BooleanClauseOccur.SHOULD);
		fieldBooleanFilter.add(pksBooleanFilter, BooleanClauseOccur.SHOULD);

		contextBooleanFilter.add(fieldBooleanFilter, BooleanClauseOccur.MUST);
	}

	protected abstract String getClassName();

	protected String getIndexerFieldName() {
		return "qualificationType_" + getKey();
	}

	protected abstract long[] getUserClassPKs(User user) throws PortalException;

	@Reference
	protected CommercePriceListQualificationTypeRelLocalService
		commercePriceListQualificationTypeRelLocalService;

	@Reference
	protected CommercePriceListUserRelLocalService
		commercePriceListUserRelLocalService;

	@Reference
	protected UserLocalService userLocalService;

}