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

package com.liferay.commerce.currency.test.util;

import com.liferay.commerce.currency.constants.CommerceCurrencyConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.math.BigDecimal;

/**
 * @author Alec Sloan
 */
public class CommerceCurrencyTestUtil {

	public static CommerceCurrency addCommerceCurrency(long companyId)
		throws PortalException {

		return addCommerceCurrency(companyId, RandomTestUtil.randomString());
	}

	public static CommerceCurrency addCommerceCurrency(
			long companyId, String code)
		throws PortalException {

		Company company = CompanyLocalServiceUtil.getCompany(companyId);

		User user = company.getDefaultUser();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				company.getCompanyId(), company.getGroupId(), user.getUserId());

		return CommerceCurrencyLocalServiceUtil.addCommerceCurrency(
			serviceContext.getUserId(), code,
			RandomTestUtil.randomLocaleStringMap(), StringPool.DOLLAR,
			BigDecimal.ONE,
			HashMapBuilder.put(
				LocaleUtil.US, CommerceCurrencyConstants.DEFAULT_FORMAT_PATTERN
			).build(),
			2, 2, StringPool.BLANK, false, RandomTestUtil.randomDouble(), true);
	}

}