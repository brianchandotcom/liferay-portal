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

package com.liferay.commerce.test.util;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelLocalServiceUtil;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

/**
 * @author Riccardo Alberti
 */
public class CommerceAccountGroupTestUtil {

	public static CommerceAccountGroup addCommerceAccountGroup(long groupId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return CommerceAccountGroupLocalServiceUtil.addCommerceAccountGroup(
			serviceContext.getCompanyId(), RandomTestUtil.randomString(), 0,
			false, null, serviceContext);
	}

	public static CommerceAccountGroup addCommerceAccountToAccountGroup(
			CommerceAccount commerceAccount)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		CommerceAccountGroup commerceAccountGroup =
			CommerceAccountGroupLocalServiceUtil.addCommerceAccountGroup(
				serviceContext.getCompanyId(), RandomTestUtil.randomString(), 0,
				false, null, serviceContext);

		CommerceAccountGroupCommerceAccountRelLocalServiceUtil.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroup.getCommerceAccountGroupId(),
				commerceAccount.getCommerceAccountId(), serviceContext);

		return commerceAccountGroup;
	}

	public static CommerceAccountGroup addCommerceAccountToAccountGroup(
			long groupId, CommerceAccount commerceAccount)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceAccountGroup commerceAccountGroup =
			CommerceAccountGroupLocalServiceUtil.addCommerceAccountGroup(
				serviceContext.getCompanyId(), RandomTestUtil.randomString(), 0,
				false, null, serviceContext);

		CommerceAccountGroupCommerceAccountRelLocalServiceUtil.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroup.getCommerceAccountGroupId(),
				commerceAccount.getCommerceAccountId(), serviceContext);

		return commerceAccountGroup;
	}

}