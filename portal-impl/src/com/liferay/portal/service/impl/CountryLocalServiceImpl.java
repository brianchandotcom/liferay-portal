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

package com.liferay.portal.service.impl;

import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.CountryA2Exception;
import com.liferay.portal.kernel.exception.CountryA3Exception;
import com.liferay.portal.kernel.exception.CountryIddException;
import com.liferay.portal.kernel.exception.CountryNameException;
import com.liferay.portal.kernel.exception.CountryNumberException;
import com.liferay.portal.kernel.exception.DuplicateCountryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.CountryLocalizationTable;
import com.liferay.portal.kernel.model.CountryTable;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.CountryLocalServiceBaseImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @see CountryLocalServiceBaseImpl
 */
public class CountryLocalServiceImpl extends CountryLocalServiceBaseImpl {

	@Override
	public Country addCountry(
			String a2, String a3, boolean active, boolean billingAllowed,
			String idd, String name, String number, double position,
			boolean shippingAllowed, boolean subjectToVAT, boolean zipRequired,
			ServiceContext serviceContext)
		throws PortalException {

		if (fetchCountryByA2(serviceContext.getCompanyId(), a2) != null) {
			throw new DuplicateCountryException();
		}

		validate(a2, a3, idd, name, number);

		long countryId = counterLocalService.increment();

		Country country = countryPersistence.create(countryId);

		User user = userLocalService.getUser(serviceContext.getUserId());

		country.setCompanyId(user.getCompanyId());
		country.setUserId(user.getUserId());
		country.setUserName(user.getFullName());

		country.setA2(a2);
		country.setA3(a3);
		country.setActive(active);
		country.setBillingAllowed(billingAllowed);
		country.setDefaultLanguageId(
			LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
		country.setGroupFilterEnabled(false);
		country.setIdd(idd);
		country.setName(name);
		country.setNumber(number);
		country.setPosition(position);
		country.setShippingAllowed(shippingAllowed);
		country.setSubjectToVAT(subjectToVAT);
		country.setZipRequired(zipRequired);

		return countryPersistence.update(country);
	}

	@Override
	public void deleteCompanyCountries(long companyId) throws PortalException {
		List<Country> countries = countryPersistence.findByCompanyId(companyId);

		for (Country country : countries) {
			countryLocalService.deleteCountry(country);
		}
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public Country deleteCountry(Country country) throws PortalException {

		// Country

		countryPersistence.remove(country);

		// Addresses

		addressLocalService.deleteCountryAddresses(country.getCountryId());

		// Country Localizations

		countryLocalizationPersistence.removeByCountryId(
			country.getCountryId());

		// Organizations

		_updateOrganizations(country.getCountryId());

		// Regions

		regionPersistence.removeByCountryId(country.getCountryId());

		return country;
	}

	@Override
	public Country deleteCountry(long countryId) throws PortalException {
		Country country = countryPersistence.findByPrimaryKey(countryId);

		return countryLocalService.deleteCountry(country);
	}

	@Override
	public Country fetchCountryByA2(long companyId, String a2) {
		return countryPersistence.fetchByC_A2(companyId, a2);
	}

	@Override
	public Country fetchCountryByA3(long companyId, String a3) {
		return countryPersistence.fetchByC_A3(companyId, a3);
	}

	@Override
	public Country fetchCountryByName(long companyId, String name) {
		return countryPersistence.fetchByC_Name(companyId, name);
	}

	@Override
	public Country fetchCountryByNumber(long companyId, String number) {
		return countryPersistence.fetchByC_Number(companyId, number);
	}

	@Override
	public List<Country> getCompanyCountries(long companyId) {
		return countryPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<Country> getCompanyCountries(long companyId, boolean active) {
		return countryPersistence.findByC_Active(companyId, active);
	}

	@Override
	public List<Country> getCompanyCountries(
		long companyId, boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return countryPersistence.findByC_Active(
			companyId, active, start, end, orderByComparator);
	}

	@Override
	public List<Country> getCompanyCountries(
		long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return countryPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCompanyCountriesCount(long companyId) {
		return countryPersistence.countByCompanyId(companyId);
	}

	@Override
	public int getCompanyCountriesCount(long companyId, boolean active) {
		return countryPersistence.countByC_Active(companyId, active);
	}

	@Override
	public Country getCountryByA2(long companyId, String a2)
		throws PortalException {

		return countryPersistence.findByC_A2(companyId, a2);
	}

	@Override
	public Country getCountryByA3(long companyId, String a3)
		throws PortalException {

		return countryPersistence.findByC_A3(companyId, a3);
	}

	@Override
	public Country getCountryByName(long companyId, String name)
		throws PortalException {

		return countryPersistence.findByC_Name(companyId, name);
	}

	@Override
	public Country getCountryByNumber(long companyId, String number)
		throws PortalException {

		return countryPersistence.findByC_Number(companyId, number);
	}

	@Override
	public BaseModelSearchResult<Country> searchCountries(
			long companyId, Boolean active, String keywords, int start, int end,
			OrderByComparator<Country> orderByComparator)
		throws PortalException {

		List<Country> list = countryLocalService.dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(CountryTable.INSTANCE),
				companyId, active, keywords
			).orderBy(
				CountryTable.INSTANCE, orderByComparator
			).limit(
				start, end
			));

		Long count = countryLocalService.dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					CountryTable.INSTANCE.countryId),
				companyId, active, keywords));

		return new BaseModelSearchResult<>(list, count.intValue());
	}

	@Override
	public Country updateActive(long countryId, boolean active)
		throws PortalException {

		Country country = countryPersistence.findByPrimaryKey(countryId);

		country.setActive(active);

		return countryPersistence.update(country);
	}

	@Override
	public Country updateCountry(
			long countryId, String a2, String a3, boolean active,
			boolean billingAllowed, String idd, String name, String number,
			double position, boolean shippingAllowed, boolean subjectToVAT)
		throws PortalException {

		Country country = countryPersistence.findByPrimaryKey(countryId);

		validate(a2, a3, idd, number, idd);

		country.setA2(a2);
		country.setA3(a3);
		country.setActive(active);
		country.setBillingAllowed(billingAllowed);
		country.setIdd(idd);
		country.setName(name);
		country.setNumber(number);
		country.setPosition(position);
		country.setShippingAllowed(shippingAllowed);
		country.setSubjectToVAT(subjectToVAT);

		return countryPersistence.update(country);
	}

	@Override
	public Country updateGroupFilterEnabled(
			long countryId, boolean groupFilterEnabled)
		throws PortalException {

		Country country = countryLocalService.getCountry(countryId);

		country.setGroupFilterEnabled(groupFilterEnabled);

		return countryPersistence.update(country);
	}

	protected void validate(
			String a2, String a3, String idd, String name, String number)
		throws PortalException {

		if (Validator.isNull(a2) || (a2.length() != 2)) {
			throw new CountryA2Exception();
		}

		if (Validator.isNull(a3) || (a3.length() != 3)) {
			throw new CountryA3Exception();
		}

		if (Validator.isNull(idd)) {
			throw new CountryIddException();
		}

		if (Validator.isNull(name)) {
			throw new CountryNameException();
		}

		if (Validator.isNull(number)) {
			throw new CountryNumberException();
		}
	}

	private GroupByStep _getGroupByStep(
			FromStep fromStep, long companyId, boolean active, String keywords)
		throws PortalException {

		JoinStep joinStep = fromStep.from(
			CountryTable.INSTANCE
		).leftJoinOn(
			CountryLocalizationTable.INSTANCE,
			CountryTable.INSTANCE.countryId.eq(
				CountryLocalizationTable.INSTANCE.countryId)
		);

		return joinStep.where(
			() -> {
				Predicate predicate = CountryTable.INSTANCE.companyId.eq(
					companyId);

				predicate = predicate.and(
					CountryTable.INSTANCE.active.eq(active));

				if (Validator.isNotNull(keywords)) {
					String[] terms = CustomSQLUtil.keywords(keywords, true);

					Predicate keywordsPredicate = null;

					for (String term : terms) {
						Predicate namePredicate = DSLFunctionFactoryUtil.lower(
							CountryTable.INSTANCE.name
						).like(
							term
						);

						Predicate titlePredicate = DSLFunctionFactoryUtil.lower(
							CountryLocalizationTable.INSTANCE.title
						).like(
							term
						);

						Predicate termPredicate = namePredicate.or(
							titlePredicate);

						if (keywordsPredicate == null) {
							keywordsPredicate = termPredicate;
						}
						else {
							keywordsPredicate = keywordsPredicate.or(
								termPredicate);
						}
					}

					if (keywordsPredicate != null) {
						predicate = predicate.and(
							keywordsPredicate.withParentheses());
					}
				}

				return predicate;
			});
	}

	private void _updateOrganizations(long countryId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			organizationLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property countryIdProperty = PropertyFactoryUtil.forName(
					"countryId");

				dynamicQuery.add(countryIdProperty.eq(countryId));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(Organization organization) -> {
				organization.setCountryId(0);
				organization.setRegionId(0);

				organizationLocalService.updateOrganization(organization);
			});

		actionableDynamicQuery.performActions();
	}

}