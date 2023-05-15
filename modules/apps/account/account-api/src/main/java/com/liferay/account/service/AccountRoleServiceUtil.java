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

import com.liferay.account.model.AccountRole;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Map;

/**
 * Provides the remote service utility for AccountRole. This utility wraps
 * <code>com.liferay.account.service.impl.AccountRoleServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AccountRoleService
 * @generated
 */
public class AccountRoleServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountRoleServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static AccountRole addAccountRole(
			long accountEntryId, String name,
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap)
		throws PortalException {

		return getService().addAccountRole(
			accountEntryId, name, titleMap, descriptionMap);
	}

	public static void associateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws PortalException {

		getService().associateUser(accountEntryId, accountRoleId, userId);
	}

	public static void associateUser(
			long accountEntryId, long[] accountRoleIds, long userId)
		throws PortalException {

		getService().associateUser(accountEntryId, accountRoleIds, userId);
	}

	public static AccountRole deleteAccountRole(AccountRole accountRole)
		throws PortalException {

		return getService().deleteAccountRole(accountRole);
	}

	public static AccountRole deleteAccountRole(long accountRoleId)
		throws PortalException {

		return getService().deleteAccountRole(accountRoleId);
	}

	public static AccountRole getAccountRoleByRoleId(long roleId)
		throws PortalException {

		return getService().getAccountRoleByRoleId(roleId);
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
		<AccountRole> searchAccountRoles(
				long companyId, long[] accountEntryIds, String keywords,
				java.util.LinkedHashMap<String, Object> params, int start,
				int end, OrderByComparator<?> orderByComparator)
			throws PortalException {

		return getService().searchAccountRoles(
			companyId, accountEntryIds, keywords, params, start, end,
			orderByComparator);
	}

	public static void setUserAccountRoles(
			long accountEntryId, long[] accountRoleIds, long userId)
		throws PortalException {

		getService().setUserAccountRoles(
			accountEntryId, accountRoleIds, userId);
	}

	public static void unassociateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws PortalException {

		getService().unassociateUser(accountEntryId, accountRoleId, userId);
	}

	public static AccountRoleService getService() {
		return _service;
	}

	public static void setService(AccountRoleService service) {
		_service = service;
	}

	private static volatile AccountRoleService _service;

}