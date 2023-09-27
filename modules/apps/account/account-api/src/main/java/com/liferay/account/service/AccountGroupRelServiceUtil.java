/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service;

import com.liferay.account.model.AccountGroupRel;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for AccountGroupRel. This utility wraps
 * <code>com.liferay.account.service.impl.AccountGroupRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupRelService
 * @generated
 */
public class AccountGroupRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountGroupRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static AccountGroupRel addAccountGroupRel(
			long accountGroupId, String className, long classPK)
		throws PortalException {

		return getService().addAccountGroupRel(
			accountGroupId, className, classPK);
	}

	public static void addAccountGroupRels(
			long accountGroupId, String className, long[] classPKs)
		throws PortalException {

		getService().addAccountGroupRels(accountGroupId, className, classPKs);
	}

	public static AccountGroupRel deleteAccountGroupRel(long accountGroupRelId)
		throws PortalException {

		return getService().deleteAccountGroupRel(accountGroupRelId);
	}

	public static void deleteAccountGroupRels(
			long accountGroupId, String className, long[] classPKs)
		throws PortalException {

		getService().deleteAccountGroupRels(
			accountGroupId, className, classPKs);
	}

	public static AccountGroupRel fetchAccountGroupRel(
			long accountGroupId, String className, long classPK)
		throws PortalException {

		return getService().fetchAccountGroupRel(
			accountGroupId, className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static AccountGroupRelService getService() {
		return _service;
	}

	public static void setService(AccountGroupRelService service) {
		_service = service;
	}

	private static volatile AccountGroupRelService _service;

}