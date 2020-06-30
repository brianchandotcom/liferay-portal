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
 * Provides the remote service utility for CommerceAccountGroupCommerceAccountRel. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupCommerceAccountRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupCommerceAccountRelService
 * @generated
 */
public class CommerceAccountGroupCommerceAccountRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupCommerceAccountRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					addCommerceAccountGroupCommerceAccountRel(
						long commerceAccountGroupId, long commerceAccountId,
						com.liferay.portal.kernel.service.ServiceContext
							serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceAccountGroupCommerceAccountRel(
			commerceAccountGroupId, commerceAccountId, serviceContext);
	}

	public static void deleteCommerceAccountGroupCommerceAccountRel(
			long commerceAccountGroupCommerceAccountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceAccountGroupCommerceAccountRel(
			commerceAccountGroupCommerceAccountRelId);
	}

	public static
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					getCommerceAccountGroupCommerceAccountRel(
						long commerceAccountGroupId, long commerceAccountId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountGroupCommerceAccountRel(
			commerceAccountGroupId, commerceAccountId);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel>
					getCommerceAccountGroupCommerceAccountRels(
						long commerceAccountGroupId, int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountGroupCommerceAccountRels(
			commerceAccountGroupId, start, end);
	}

	public static int getCommerceAccountGroupCommerceAccountRelsCount(
			long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountGroupCommerceAccountRelsCount(
			commerceAccountGroupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceAccountGroupCommerceAccountRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAccountGroupCommerceAccountRelService,
		 CommerceAccountGroupCommerceAccountRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountGroupCommerceAccountRelService.class);

		ServiceTracker
			<CommerceAccountGroupCommerceAccountRelService,
			 CommerceAccountGroupCommerceAccountRelService> serviceTracker =
				new ServiceTracker
					<CommerceAccountGroupCommerceAccountRelService,
					 CommerceAccountGroupCommerceAccountRelService>(
						 bundle.getBundleContext(),
						 CommerceAccountGroupCommerceAccountRelService.class,
						 null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}