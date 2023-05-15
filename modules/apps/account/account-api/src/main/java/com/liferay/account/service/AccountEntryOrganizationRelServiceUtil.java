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

import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for AccountEntryOrganizationRel. This utility wraps
 * <code>com.liferay.account.service.impl.AccountEntryOrganizationRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryOrganizationRelService
 * @generated
 */
public class AccountEntryOrganizationRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountEntryOrganizationRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static AccountEntryOrganizationRel addAccountEntryOrganizationRel(
			long accountEntryId, long organizationId)
		throws PortalException {

		return getService().addAccountEntryOrganizationRel(
			accountEntryId, organizationId);
	}

	public static void addAccountEntryOrganizationRels(
			long accountEntryId, long[] organizationIds)
		throws PortalException {

		getService().addAccountEntryOrganizationRels(
			accountEntryId, organizationIds);
	}

	public static void deleteAccountEntryOrganizationRel(
			long accountEntryId, long organizationId)
		throws PortalException {

		getService().deleteAccountEntryOrganizationRel(
			accountEntryId, organizationId);
	}

	public static void deleteAccountEntryOrganizationRels(
			long accountEntryId, long[] organizationIds)
		throws PortalException {

		getService().deleteAccountEntryOrganizationRels(
			accountEntryId, organizationIds);
	}

	public static AccountEntryOrganizationRel fetchAccountEntryOrganizationRel(
			long accountEntryOrganizationRelId)
		throws PortalException {

		return getService().fetchAccountEntryOrganizationRel(
			accountEntryOrganizationRelId);
	}

	public static AccountEntryOrganizationRel fetchAccountEntryOrganizationRel(
			long accountEntryId, long organizationId)
		throws PortalException {

		return getService().fetchAccountEntryOrganizationRel(
			accountEntryId, organizationId);
	}

	public static AccountEntryOrganizationRel getAccountEntryOrganizationRel(
			long accountEntryId, long organizationId)
		throws PortalException {

		return getService().getAccountEntryOrganizationRel(
			accountEntryId, organizationId);
	}

	public static List<AccountEntryOrganizationRel>
			getAccountEntryOrganizationRels(
				long accountEntryId, int start, int end)
		throws PortalException {

		return getService().getAccountEntryOrganizationRels(
			accountEntryId, start, end);
	}

	public static int getAccountEntryOrganizationRelsCount(long accountEntryId)
		throws PortalException {

		return getService().getAccountEntryOrganizationRelsCount(
			accountEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static AccountEntryOrganizationRelService getService() {
		return _service;
	}

	public static void setService(AccountEntryOrganizationRelService service) {
		_service = service;
	}

	private static volatile AccountEntryOrganizationRelService _service;

}