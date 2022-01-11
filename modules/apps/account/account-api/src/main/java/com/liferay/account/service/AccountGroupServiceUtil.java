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

package com.liferay.account.service;

import com.liferay.account.model.AccountGroup;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * Provides the remote service utility for AccountGroup. This utility wraps
 * <code>com.liferay.account.service.impl.AccountGroupServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupService
 * @generated
 */
public class AccountGroupServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountGroupServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static AccountGroup addAccountGroup(
			long userId, String description, String name)
		throws PortalException {

		return getService().addAccountGroup(userId, description, name);
	}

	public static AccountGroup deleteAccountGroup(long accountGroupId)
		throws PortalException {

		return getService().deleteAccountGroup(accountGroupId);
	}

	public static void deleteAccountGroups(long[] accountGroupIds)
		throws PortalException {

		getService().deleteAccountGroups(accountGroupIds);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<AccountGroup> searchAccountGroups(
				long companyId, String keywords, int start, int end,
				OrderByComparator<AccountGroup> orderByComparator)
			throws PortalException {

		return getService().searchAccountGroups(
			companyId, keywords, start, end, orderByComparator);
	}

	public static AccountGroup updateAccountGroup(
			long accountGroupId, String description, String name)
		throws PortalException {

		return getService().updateAccountGroup(
			accountGroupId, description, name);
	}

	public static AccountGroupService getService() {
		return _service;
	}

	private static volatile AccountGroupService _service;

}