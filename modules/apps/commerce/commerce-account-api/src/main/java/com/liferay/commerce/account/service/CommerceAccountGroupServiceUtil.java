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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceAccountGroup. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupService
 * @generated
 */
public class CommerceAccountGroupServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroup
			addCommerceAccountGroup(
				long companyId, String name, int type,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceAccountGroup(
			companyId, name, type, externalReferenceCode, serviceContext);
	}

	public static void deleteCommerceAccountGroup(long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceAccountGroup(commerceAccountGroupId);
	}

	public static com.liferay.commerce.account.model.CommerceAccountGroup
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.account.model.CommerceAccountGroup
			getCommerceAccountGroup(long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountGroup(commerceAccountGroupId);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
				getCommerceAccountGroups(
					long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.account.model.
							CommerceAccountGroup> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountGroups(
			companyId, start, end, orderByComparator);
	}

	public static int getCommerceAccountGroupsCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountGroupsCount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
				searchCommerceAccountGroups(
					long companyId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceAccountGroups(
			companyId, keywords, start, end, sort);
	}

	public static int searchCommerceAccountsGroupCount(
			long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceAccountsGroupCount(
			companyId, keywords);
	}

	public static com.liferay.commerce.account.model.CommerceAccountGroup
			updateCommerceAccountGroup(
				long commerceAccountGroupId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceAccountGroup(
			commerceAccountGroupId, name, serviceContext);
	}

	public static CommerceAccountGroupService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAccountGroupService, CommerceAccountGroupService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountGroupService.class);

		ServiceTracker<CommerceAccountGroupService, CommerceAccountGroupService>
			serviceTracker =
				new ServiceTracker
					<CommerceAccountGroupService, CommerceAccountGroupService>(
						bundle.getBundleContext(),
						CommerceAccountGroupService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}