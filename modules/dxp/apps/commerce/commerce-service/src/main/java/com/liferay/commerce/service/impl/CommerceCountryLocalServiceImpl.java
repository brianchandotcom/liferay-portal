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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.CommerceCountryNameException;
import com.liferay.commerce.exception.CommerceCountryThreeLettersISOCodeException;
import com.liferay.commerce.exception.CommerceCountryTwoLettersISOCodeException;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.base.CommerceCountryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 * @author Marco Leo
 */
public class CommerceCountryLocalServiceImpl
	extends CommerceCountryLocalServiceBaseImpl {

	@Override
	public CommerceCountry addCommerceCountry(
			Map<Locale, String> nameMap, boolean billingAllowed,
			boolean shippingAllowed, String twoLettersISOCode,
			String threeLettersISOCode, int numericISOCode,
			boolean subjectToVAT, double priority, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(nameMap, twoLettersISOCode, threeLettersISOCode);

		long commerceCountryId = counterLocalService.increment();

		CommerceCountry commerceCountry = commerceCountryPersistence.create(
			commerceCountryId);

		commerceCountry.setUuid(serviceContext.getUuid());
		commerceCountry.setGroupId(groupId);
		commerceCountry.setCompanyId(user.getCompanyId());
		commerceCountry.setUserId(user.getUserId());
		commerceCountry.setUserName(user.getFullName());
		commerceCountry.setNameMap(nameMap);
		commerceCountry.setBillingAllowed(billingAllowed);
		commerceCountry.setShippingAllowed(shippingAllowed);
		commerceCountry.setTwoLettersISOCode(twoLettersISOCode);
		commerceCountry.setThreeLettersISOCode(threeLettersISOCode);
		commerceCountry.setNumericISOCode(numericISOCode);
		commerceCountry.setSubjectToVAT(subjectToVAT);
		commerceCountry.setPriority(priority);
		commerceCountry.setActive(active);

		commerceCountryPersistence.update(commerceCountry);

		return commerceCountry;
	}

	@Override
	public void deleteCommerceCountries(long groupId) throws PortalException {
		List<CommerceCountry> commerceCountries =
			commerceCountryPersistence.findByGroupId(groupId);

		for (CommerceCountry commerceCountry : commerceCountries) {
			commerceCountryLocalService.deleteCommerceCountry(commerceCountry);
		}
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceCountry deleteCommerceCountry(
			CommerceCountry commerceCountry)
		throws PortalException {

		// Commerce country

		commerceCountryPersistence.remove(commerceCountry);

		// Commerce regions

		commerceRegionLocalService.deleteCommerceRegions(
			commerceCountry.getCommerceCountryId());

		// Commerce addresses

		commerceAddressLocalService.deleteCountryCommerceAddresses(
			commerceCountry.getCommerceCountryId());

		// Commerce address restrictions

		commerceAddressRestrictionLocalService.
			deleteCommerceAddressRestrictions(
				commerceCountry.getCommerceCountryId());

		return commerceCountry;
	}

	@Override
	public CommerceCountry deleteCommerceCountry(long commerceCountryId)
		throws PortalException {

		CommerceCountry commerceCountry =
			commerceCountryPersistence.findByPrimaryKey(commerceCountryId);

		return commerceCountryLocalService.deleteCommerceCountry(
			commerceCountry);
	}

	@Override
	public CommerceCountry fetchCommerceCountry(
			long groupId, int numericISOCode)
		throws PortalException {

		return commerceCountryPersistence.fetchByG_N(groupId, numericISOCode);
	}

	@Override
	public List<CommerceCountry> getBillingCommerceCountries(
		long groupId, boolean billingAllowed, boolean active) {

		return commerceCountryPersistence.findByG_B_A(
			groupId, billingAllowed, active);
	}

	@Override
	public List<CommerceCountry> getCommerceCountries(
		long groupId, boolean active) {

		return commerceCountryPersistence.findByG_A(groupId, active);
	}

	@Override
	public List<CommerceCountry> getCommerceCountries(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return commerceCountryPersistence.findByG_A(
			groupId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceCountry> getCommerceCountries(
		long groupId, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return commerceCountryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCountriesCount(long groupId) {
		return commerceCountryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getCommerceCountriesCount(long groupId, boolean active) {
		return commerceCountryPersistence.countByG_A(groupId, active);
	}

	@Override
	public List<CommerceCountry> getShippingCommerceCountries(
		long groupId, boolean shippingAllowed, boolean active) {

		return commerceCountryPersistence.findByG_S_A(
			groupId, shippingAllowed, active);
	}

	@Override
	public List<CommerceCountry> getWarehouseCommerceCountries(long groupId) {
		return commerceCountryFinder.findByCommerceWarehouses(groupId);
	}

	@Override
	public void importDefaultCountries(ServiceContext serviceContext)
		throws Exception {

		Class<?> clazz = getClass();

		String countriesPath = "com/liferay/commerce/internal/countries.json";

		String countriesJSON = StringUtil.read(
			clazz.getClassLoader(), countriesPath, false);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(countriesJSON);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String name = jsonObject.getString("name");
			int numericISOCode = jsonObject.getInt("numericISOCode");
			double priority = jsonObject.getDouble("priority");
			String threeLettersISOCode = jsonObject.getString(
				"threeLettersISOCode");
			String twoLettersISOCode = jsonObject.getString(
				"twoLettersISOCode");

			String localizedName = LanguageUtil.get(
				serviceContext.getLocale(), "country." + name);

			Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(serviceContext.getLocale(), localizedName);

			addCommerceCountry(
				nameMap, true, true, twoLettersISOCode, threeLettersISOCode,
				numericISOCode, false, priority, true, serviceContext);
		}
	}

	@Override
	public CommerceCountry updateCommerceCountry(
			long commerceCountryId, Map<Locale, String> nameMap,
			boolean billingAllowed, boolean shippingAllowed,
			String twoLettersISOCode, String threeLettersISOCode,
			int numericISOCode, boolean subjectToVAT, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommerceCountry commerceCountry =
			commerceCountryPersistence.findByPrimaryKey(commerceCountryId);

		validate(nameMap, twoLettersISOCode, threeLettersISOCode);

		commerceCountry.setNameMap(nameMap);
		commerceCountry.setBillingAllowed(billingAllowed);
		commerceCountry.setShippingAllowed(shippingAllowed);
		commerceCountry.setTwoLettersISOCode(twoLettersISOCode);
		commerceCountry.setThreeLettersISOCode(threeLettersISOCode);
		commerceCountry.setNumericISOCode(numericISOCode);
		commerceCountry.setSubjectToVAT(subjectToVAT);
		commerceCountry.setPriority(priority);
		commerceCountry.setActive(active);

		commerceCountryPersistence.update(commerceCountry);

		return commerceCountry;
	}

	protected void validate(
			Map<Locale, String> nameMap, String twoLettersISOCode,
			String threeLettersISOCode)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new CommerceCountryNameException();
		}

		if (Validator.isNotNull(twoLettersISOCode) &&
			(twoLettersISOCode.length() != 2)) {

			throw new CommerceCountryTwoLettersISOCodeException();
		}

		if (Validator.isNotNull(threeLettersISOCode) &&
			(threeLettersISOCode.length() != 3)) {

			throw new CommerceCountryThreeLettersISOCodeException();
		}
	}

}