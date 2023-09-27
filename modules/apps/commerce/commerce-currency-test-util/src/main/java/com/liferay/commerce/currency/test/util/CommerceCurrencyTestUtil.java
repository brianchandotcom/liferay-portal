/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		User user = company.getGuestUser();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				company.getCompanyId(), company.getGroupId(), user.getUserId());

		return CommerceCurrencyLocalServiceUtil.addCommerceCurrency(
			serviceContext.getUserId(), code,
			RandomTestUtil.randomLocaleStringMap(), StringPool.DOLLAR,
			BigDecimal.ONE,
			HashMapBuilder.put(
				LocaleUtil.US, CommerceCurrencyConstants.DECIMAL_FORMAT_PATTERN
			).build(),
			2, 2, StringPool.BLANK, false, RandomTestUtil.randomDouble(), true);
	}

}