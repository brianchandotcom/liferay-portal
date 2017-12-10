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

package com.liferay.commerce.currency.service.impl;

import com.liferay.commerce.currency.exception.CommerceCurrencyCodeException;
import com.liferay.commerce.currency.exception.CommerceCurrencyNameException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.base.CommerceCurrencyLocalServiceBaseImpl;
import com.liferay.commerce.currency.util.comparator.CommerceCurrencyPriorityComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCurrencyLocalServiceImpl
	extends CommerceCurrencyLocalServiceBaseImpl {

	@Override
	public CommerceCurrency addCommerceCurrency(
			String code, Map<Locale, String> nameMap, double rate,
			String roundingType, boolean primary, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		if (primary) {
			rate = 1;
		}

		validate(0, groupId, code, nameMap, primary);

		long commerceCurrencyId = counterLocalService.increment();

		CommerceCurrency commerceCurrency = commerceCurrencyPersistence.create(
			commerceCurrencyId);

		commerceCurrency.setUuid(serviceContext.getUuid());
		commerceCurrency.setGroupId(groupId);
		commerceCurrency.setCompanyId(user.getCompanyId());
		commerceCurrency.setUserId(user.getUserId());
		commerceCurrency.setUserName(user.getFullName());
		commerceCurrency.setCode(code);
		commerceCurrency.setNameMap(nameMap);
		commerceCurrency.setRate(rate);
		commerceCurrency.setRoundingType(roundingType);
		commerceCurrency.setPrimary(primary);
		commerceCurrency.setPriority(priority);
		commerceCurrency.setActive(active);

		commerceCurrencyPersistence.update(commerceCurrency);

		return commerceCurrency;
	}

	@Override
	public void deleteCommerceCurrencies(long groupId) {
		commerceCurrencyPersistence.removeByGroupId(groupId);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceCurrency deleteCommerceCurrency(
		CommerceCurrency commerceCurrency) {

		return commerceCurrencyPersistence.remove(commerceCurrency);
	}

	@Override
	public CommerceCurrency deleteCommerceCurrency(long commerceCurrencyId)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		return commerceCurrencyLocalService.deleteCommerceCurrency(
			commerceCurrency);
	}

	@Override
	public CommerceCurrency fetchPrimaryCommerceCurrency(long groupId) {
		return commerceCurrencyPersistence.fetchByG_P_A_First(
			groupId, true, true, new CommerceCurrencyPriorityComparator());
	}

	@Override
	public List<CommerceCurrency> getCommerceCurrencies(
		long groupId, boolean active) {

		return commerceCurrencyPersistence.findByG_A(groupId, active);
	}

	@Override
	public List<CommerceCurrency> getCommerceCurrencies(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return commerceCurrencyPersistence.findByG_A(
			groupId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceCurrency> getCommerceCurrencies(
		long groupId, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return commerceCurrencyPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCurrenciesCount(long groupId) {
		return commerceCurrencyPersistence.countByGroupId(groupId);
	}

	@Override
	public int getCommerceCurrenciesCount(long groupId, boolean active) {
		return commerceCurrencyPersistence.countByG_A(groupId, active);
	}

	@Override
	public CommerceCurrency updateCommerceCurrency(
			long commerceCurrencyId, String code, Map<Locale, String> nameMap,
			double rate, String roundingType, boolean primary, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		if (primary) {
			rate = 1;
		}

		validate(
			commerceCurrency.getCommerceCurrencyId(),
			commerceCurrency.getGroupId(), code, nameMap, primary);

		commerceCurrency.setCode(code);
		commerceCurrency.setNameMap(nameMap);
		commerceCurrency.setRate(rate);
		commerceCurrency.setRoundingType(roundingType);
		commerceCurrency.setPrimary(primary);
		commerceCurrency.setPriority(priority);
		commerceCurrency.setActive(active);

		commerceCurrencyPersistence.update(commerceCurrency);

		return commerceCurrency;
	}

	protected void validate(
			long commerceCurrencyId, long groupId, String code,
			Map<Locale, String> nameMap, boolean primary)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if (Validator.isNull(code)) {
			throw new CommerceCurrencyCodeException();
		}

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new CommerceCurrencyNameException();
		}

		if (primary) {
			List<CommerceCurrency> commerceCurrencies =
				commerceCurrencyPersistence.findByG_P(groupId, primary);

			for (CommerceCurrency commerceCurrency : commerceCurrencies) {
				if (commerceCurrency.getCommerceCurrencyId() !=
						commerceCurrencyId) {

					commerceCurrency.setPrimary(false);

					commerceCurrencyPersistence.update(commerceCurrency);
				}
			}
		}
	}

}