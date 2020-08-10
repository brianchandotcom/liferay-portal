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
 * Provides the remote service utility for CPOptionValue. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPOptionValueServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPOptionValueService
 * @generated
 */
public class CPOptionValueServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPOptionValueServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPOptionValue
			addCPOptionValue(
				long cpOptionId,
				java.util.Map<java.util.Locale, String> titleMap,
				double priority, String key,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCPOptionValue(
			cpOptionId, titleMap, priority, key, serviceContext);
	}

	public static void deleteCPOptionValue(long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCPOptionValue(cpOptionValueId);
	}

	public static com.liferay.commerce.product.model.CPOptionValue
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.product.model.CPOptionValue
			fetchCPOptionValue(long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCPOptionValue(cpOptionValueId);
	}

	public static com.liferay.commerce.product.model.CPOptionValue
			getCPOptionValue(long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPOptionValue(cpOptionValueId);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
				long cpOptionId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPOptionValues(cpOptionId, start, end);
	}

	public static int getCPOptionValuesCount(long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPOptionValuesCount(cpOptionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.model.CPOptionValue
			updateCPOptionValue(
				long cpOptionValueId,
				java.util.Map<java.util.Locale, String> titleMap,
				double priority, String key,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCPOptionValue(
			cpOptionValueId, titleMap, priority, key, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPOptionValue
			upsertCPOptionValue(
				long cpOptionId,
				java.util.Map<java.util.Locale, String> nameMap,
				double priority, String key, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCPOptionValue(
			cpOptionId, nameMap, priority, key, externalReferenceCode,
			serviceContext);
	}

	public static CPOptionValueService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPOptionValueService, CPOptionValueService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPOptionValueService.class);

		ServiceTracker<CPOptionValueService, CPOptionValueService>
			serviceTracker =
				new ServiceTracker<CPOptionValueService, CPOptionValueService>(
					bundle.getBundleContext(), CPOptionValueService.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}