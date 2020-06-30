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
 * Provides the remote service utility for CommerceAccountOrganizationRel. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountOrganizationRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceAccountOrganizationRelService
 * @generated
 */
public class CommerceAccountOrganizationRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountOrganizationRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static
		com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				addCommerceAccountOrganizationRel(
					long commerceAccountId, long organizationId,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceAccountOrganizationRel(
			commerceAccountId, organizationId, serviceContext);
	}

	public static void addCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addCommerceAccountOrganizationRels(
			commerceAccountId, organizationIds, serviceContext);
	}

	public static void deleteCommerceAccountOrganizationRel(
			long commerceAccountId, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceAccountOrganizationRel(
			commerceAccountId, organizationId);
	}

	public static void deleteCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceAccountOrganizationRels(
			commerceAccountId, organizationIds);
	}

	public static
		com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				fetchCommerceAccountOrganizationRel(
					com.liferay.commerce.account.service.persistence.
						CommerceAccountOrganizationRelPK
							commerceAccountOrganizationRelPK)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceAccountOrganizationRel(
			commerceAccountOrganizationRelPK);
	}

	public static
		com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				getCommerceAccountOrganizationRel(
					com.liferay.commerce.account.service.persistence.
						CommerceAccountOrganizationRelPK
							commerceAccountOrganizationRelPK)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountOrganizationRel(
			commerceAccountOrganizationRelPK);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
				getCommerceAccountOrganizationRels(long commerceAccountId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountOrganizationRels(
			commerceAccountId);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
				getCommerceAccountOrganizationRels(
					long commerceAccountId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountOrganizationRels(
			commerceAccountId, start, end);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
				getCommerceAccountOrganizationRelsByOrganizationId(
					long organizationId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountOrganizationRelsByOrganizationId(
			organizationId, start, end);
	}

	public static int getCommerceAccountOrganizationRelsByOrganizationIdCount(
			long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().
			getCommerceAccountOrganizationRelsByOrganizationIdCount(
				organizationId);
	}

	public static int getCommerceAccountOrganizationRelsCount(
			long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountOrganizationRelsCount(
			commerceAccountId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceAccountOrganizationRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAccountOrganizationRelService,
		 CommerceAccountOrganizationRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountOrganizationRelService.class);

		ServiceTracker
			<CommerceAccountOrganizationRelService,
			 CommerceAccountOrganizationRelService> serviceTracker =
				new ServiceTracker
					<CommerceAccountOrganizationRelService,
					 CommerceAccountOrganizationRelService>(
						 bundle.getBundleContext(),
						 CommerceAccountOrganizationRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}