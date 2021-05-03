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

package com.liferay.commerce.account.service;

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the local service utility for CommerceAccountGroup. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupLocalService
 * @generated
 */
public class CommerceAccountGroupLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceAccountGroup addCommerceAccountGroup(
			long companyId, String name, int type, boolean system,
			String externalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAccountGroup(
			companyId, name, type, system, externalReferenceCode,
			serviceContext);
	}

	public static void checkGuestCommerceAccountGroup(long companyId)
		throws PortalException {

		getService().checkGuestCommerceAccountGroup(companyId);
	}

	public static CommerceAccountGroup deleteCommerceAccountGroup(
			CommerceAccountGroup commerceAccountGroup)
		throws PortalException {

		return getService().deleteCommerceAccountGroup(commerceAccountGroup);
	}

	public static CommerceAccountGroup deleteCommerceAccountGroup(
			long commerceAccountGroupId)
		throws PortalException {

		return getService().deleteCommerceAccountGroup(commerceAccountGroupId);
	}

	public static CommerceAccountGroup fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static CommerceAccountGroup fetchCommerceAccountGroup(
		long commerceAccountGroupId) {

		return getService().fetchCommerceAccountGroup(commerceAccountGroupId);
	}

	public static CommerceAccountGroup getCommerceAccountGroup(
			long commerceAccountGroupId)
		throws PortalException {

		return getService().getCommerceAccountGroup(commerceAccountGroupId);
	}

	public static List<CommerceAccountGroup> getCommerceAccountGroups(
		long companyId, int start, int end,
		OrderByComparator<CommerceAccountGroup> orderByComparator) {

		return getService().getCommerceAccountGroups(
			companyId, start, end, orderByComparator);
	}

	public static List<CommerceAccountGroup>
		getCommerceAccountGroupsByCommerceAccountId(long commerceAccountId) {

		return getService().getCommerceAccountGroupsByCommerceAccountId(
			commerceAccountId);
	}

	public static int getCommerceAccountGroupsCount(long companyId) {
		return getService().getCommerceAccountGroupsCount(companyId);
	}

	public static List<Long> getCommerceAccountUserIdsFromAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end) {

		return getService().getCommerceAccountUserIdsFromAccountGroupIds(
			commerceAccountGroupIds, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<CommerceAccountGroup> searchCommerceAccountGroups(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().searchCommerceAccountGroups(
			companyId, keywords, start, end, sort);
	}

	public static int searchCommerceAccountsGroupCount(
			long companyId, String keywords)
		throws PortalException {

		return getService().searchCommerceAccountsGroupCount(
			companyId, keywords);
	}

	public static CommerceAccountGroup updateCommerceAccountGroup(
			long commerceAccountGroupId, String name,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceAccountGroup(
			commerceAccountGroupId, name, serviceContext);
	}

	public static CommerceAccountGroupLocalService getService() {
		return _service;
	}

	private static volatile CommerceAccountGroupLocalService _service;

}