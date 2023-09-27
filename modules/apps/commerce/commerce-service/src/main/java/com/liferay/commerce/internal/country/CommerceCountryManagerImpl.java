/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceCountryManager"
	},
	service = CommerceCountryManager.class
)
@JSONWebService
public class CommerceCountryManagerImpl implements CommerceCountryManager {

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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
				() -> CommerceInventoryWarehouseTable.INSTANCE.companyId.eq(
					companyId
				).and(
					() -> {
						if (!all) {
							return CommerceInventoryWarehouseTable.INSTANCE.
								active.eq(true);
						}

						return null;
					}
				)
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
					channelFilterPredicate.withParentheses());

				predicate = predicate.and(
					groupFilterPredicate.withParentheses());

				if (billingAllowed) {
					predicate = predicate.and(
						CountryTable.INSTANCE.billingAllowed.eq(
							billingAllowed));
				}

				if (shippingAllowed) {
					predicate = predicate.and(
						CountryTable.INSTANCE.shippingAllowed.eq(
							shippingAllowed));
				}

				return predicate;
			});
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

}