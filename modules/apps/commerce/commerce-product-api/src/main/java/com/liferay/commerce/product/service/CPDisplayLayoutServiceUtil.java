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

package com.liferay.commerce.product.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPDisplayLayout. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPDisplayLayoutServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDisplayLayoutService
 * @generated
 */
public class CPDisplayLayoutServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPDisplayLayoutServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.product.model.CPDisplayLayout
			addCPDisplayLayout(
				Class<?> clazz, long classPK, String layoutUuid,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCPDisplayLayout(
			clazz, classPK, layoutUuid, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDisplayLayout
			addCPDisplayLayout(
				long userId, long groupId, Class<?> clazz, long classPK,
				String layoutUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCPDisplayLayout(
			userId, groupId, clazz, classPK, layoutUuid);
	}

	public static void deleteCPDisplayLayout(Class<?> clazz, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCPDisplayLayout(clazz, classPK);
	}

	public static void deleteCPDisplayLayout(long cpDisplayLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCPDisplayLayout(cpDisplayLayoutId);
	}

	public static com.liferay.commerce.product.model.CPDisplayLayout
			fetchCPDisplayLayout(long cpDisplayLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCPDisplayLayout(cpDisplayLayoutId);
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
		<com.liferay.commerce.product.model.CPDisplayLayout>
				searchCPDisplayLayout(
					long companyId, long groupId, String className,
					String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCPDisplayLayout(
			companyId, groupId, className, keywords, start, end, sort);
	}

	public static com.liferay.commerce.product.model.CPDisplayLayout
			updateCPDisplayLayout(long cpDisplayLayoutId, String layoutUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCPDisplayLayout(
			cpDisplayLayoutId, layoutUuid);
	}

	public static CPDisplayLayoutService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CPDisplayLayoutService, CPDisplayLayoutService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDisplayLayoutService.class);

		ServiceTracker<CPDisplayLayoutService, CPDisplayLayoutService>
			serviceTracker =
				new ServiceTracker
					<CPDisplayLayoutService, CPDisplayLayoutService>(
						bundle.getBundleContext(), CPDisplayLayoutService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}