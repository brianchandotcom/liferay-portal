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

package com.liferay.commerce.internal.country;

import com.liferay.commerce.country.CommerceCountryManager;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseTable;
import com.liferay.commerce.product.model.CommerceChannelRelTable;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.CountryTable;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceCountryManager"
	},
	service = CommerceCountryManager.class
)
@JSONWebService
public class CommerceCountryManagerImpl implements CommerceCountryManager {

	public List<Country> getBillingCountries(
		long companyId, boolean active, boolean billingAllowed) {

		return _countryLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				CountryTable.INSTANCE
			).from(
				CountryTable.INSTANCE
			).where(
				() -> {
					Predicate predicate = CountryTable.INSTANCE.companyId.eq(
						companyId);

					predicate = predicate.and(
						CountryTable.INSTANCE.active.eq(active));

					return predicate.and(
						CountryTable.INSTANCE.billingAllowed.eq(
							billingAllowed));
				}
			));
	}

	public List<Country> getBillingCountriesByChannelId(
		long channelId, int start, int end) {

		return _countryLocalService.dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(CountryTable.INSTANCE),
				channelId, true, false
			).orderBy(
				CountryTable.INSTANCE,
				OrderByComparatorFactoryUtil.create("Country", "position", true)
			).limit(
				start, end
			));
	}

	public List<Country> getShippingCountries(
		long companyId, boolean active, boolean shippingAllowed) {

		return _countryLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				CountryTable.INSTANCE
			).from(
				CountryTable.INSTANCE
			).where(
				() -> {
					Predicate predicate = CountryTable.INSTANCE.companyId.eq(
						companyId);

					predicate = predicate.and(
						CountryTable.INSTANCE.active.eq(active));

					return predicate.and(
						CountryTable.INSTANCE.shippingAllowed.eq(
							shippingAllowed));
				}
			));
	}

	public List<Country> getShippingCountriesByChannelId(
		long channelId, int start, int end) {

		return _countryLocalService.dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(CountryTable.INSTANCE),
				channelId, false, true
			).orderBy(
				CountryTable.INSTANCE,
				OrderByComparatorFactoryUtil.create("Country", "position", true)
			).limit(
				start, end
			));
	}

	public List<Country> getWarehouseCountries(long companyId, boolean all) {
		return _countryLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				CountryTable.INSTANCE
			).from(
				CountryTable.INSTANCE
			).innerJoinON(
				CommerceInventoryWarehouseTable.INSTANCE,
				CountryTable.INSTANCE.a2.eq(
					CommerceInventoryWarehouseTable.INSTANCE.
						countryTwoLettersISOCode)
			).where(
				() -> {
					Predicate predicate =
						CommerceInventoryWarehouseTable.INSTANCE.companyId.eq(
							companyId);

					if (!all) {
						predicate = predicate.and(
							CommerceInventoryWarehouseTable.INSTANCE.active.eq(
								true));
					}

					return predicate;
				}
			).orderBy(
				CountryTable.INSTANCE,
				OrderByComparatorFactoryUtil.create("Country", "position", true)
			));
	}

	private GroupByStep _getGroupByStep(
		FromStep fromStep, long commerceChannelId, boolean billingAllowed,
		boolean shippingAllowed) {

		JoinStep joinStep = fromStep.from(
			CountryTable.INSTANCE
		).leftJoinOn(
			CommerceChannelRelTable.INSTANCE,
			CountryTable.INSTANCE.countryId.eq(
				CommerceChannelRelTable.INSTANCE.classPK)
		);

		return joinStep.where(
			() -> {
				Predicate predicate = CountryTable.INSTANCE.active.eq(true);

				Predicate groupFilterPredicate =
					CountryTable.INSTANCE.groupFilterEnabled.eq(false);

				Predicate channelFilterPredicate =
					CountryTable.INSTANCE.groupFilterEnabled.eq(true);

				channelFilterPredicate = channelFilterPredicate.and(
					CommerceChannelRelTable.INSTANCE.classNameId.eq(
						_classNameLocalService.getClassNameId(Country.class)));
				channelFilterPredicate = channelFilterPredicate.and(
					CommerceChannelRelTable.INSTANCE.commerceChannelId.eq(
						commerceChannelId));

				groupFilterPredicate = groupFilterPredicate.or(
					channelFilterPredicate);

				predicate = predicate.and(groupFilterPredicate);

				predicate = predicate.and(
					CountryTable.INSTANCE.billingAllowed.eq(billingAllowed));
				predicate = predicate.and(
					CountryTable.INSTANCE.shippingAllowed.eq(shippingAllowed));

				return predicate;
			});
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

}