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

package com.liferay.commerce.currency.internal.model.listener;

import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Shuyang Zhou
 */
public class ImportDefaultValuesUtil {

	public static void importDefaultValues(
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		Company company) {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCompanyId(company.getCompanyId());
				serviceContext.setLanguageId(
					LocaleUtil.toLanguageId(company.getLocale()));

				User defaultUser = company.getDefaultUser();

				serviceContext.setUserId(defaultUser.getUserId());

				commerceCurrencyLocalService.importDefaultValues(
					serviceContext);

				return null;
			});
	}

}