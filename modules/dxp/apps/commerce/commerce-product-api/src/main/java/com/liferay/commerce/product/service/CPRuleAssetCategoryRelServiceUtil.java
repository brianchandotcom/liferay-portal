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

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPRuleAssetCategoryRel. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPRuleAssetCategoryRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPRuleAssetCategoryRelService
 * @see com.liferay.commerce.product.service.base.CPRuleAssetCategoryRelServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPRuleAssetCategoryRelServiceImpl
 * @generated
 */
@ProviderType
public class CPRuleAssetCategoryRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPRuleAssetCategoryRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPRuleAssetCategoryRel addCPRuleAssetCategoryRel(
		long cpRuleId, long assetCategoryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPRuleAssetCategoryRel(cpRuleId, assetCategoryId,
			serviceContext);
	}

	public static void deleteCPRuleAssetCategoryRel(
		long cpRuleAssetCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPRuleAssetCategoryRel(cpRuleAssetCategoryRelId);
	}

	public static long[] getAssetCategoryIds(long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetCategoryIds(cpRuleId);
	}

	public static com.liferay.commerce.product.model.CPRuleAssetCategoryRel getCPRuleAssetCategoryRel(
		long cpRuleAssetCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPRuleAssetCategoryRel(cpRuleAssetCategoryRelId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPRuleAssetCategoryRel> getCPRuleAssetCategoryRels(
		long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPRuleAssetCategoryRels(cpRuleId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPRuleAssetCategoryRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPRuleAssetCategoryRelService, CPRuleAssetCategoryRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPRuleAssetCategoryRelService.class);

		ServiceTracker<CPRuleAssetCategoryRelService, CPRuleAssetCategoryRelService> serviceTracker =
			new ServiceTracker<CPRuleAssetCategoryRelService, CPRuleAssetCategoryRelService>(bundle.getBundleContext(),
				CPRuleAssetCategoryRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}