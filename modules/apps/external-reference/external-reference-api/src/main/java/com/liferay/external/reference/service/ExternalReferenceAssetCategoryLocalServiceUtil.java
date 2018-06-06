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

package com.liferay.external.reference.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ExternalReferenceAssetCategory. This utility wraps
 * {@link com.liferay.external.reference.service.impl.ExternalReferenceAssetCategoryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalReferenceAssetCategoryLocalService
 * @see com.liferay.external.reference.service.base.ExternalReferenceAssetCategoryLocalServiceBaseImpl
 * @see com.liferay.external.reference.service.impl.ExternalReferenceAssetCategoryLocalServiceImpl
 * @generated
 */
@ProviderType
public class ExternalReferenceAssetCategoryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.external.reference.service.impl.ExternalReferenceAssetCategoryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.asset.kernel.model.AssetCategory upsertCategory(
		long userId, long groupId, long parentCategoryId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		long vocabularyId, String[] categoryProperties,
		String externalReferenceCode,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .upsertCategory(userId, groupId, parentCategoryId, titleMap,
			descriptionMap, vocabularyId, categoryProperties,
			externalReferenceCode, serviceContext);
	}

	public static ExternalReferenceAssetCategoryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ExternalReferenceAssetCategoryLocalService, ExternalReferenceAssetCategoryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ExternalReferenceAssetCategoryLocalService.class);

		ServiceTracker<ExternalReferenceAssetCategoryLocalService, ExternalReferenceAssetCategoryLocalService> serviceTracker =
			new ServiceTracker<ExternalReferenceAssetCategoryLocalService, ExternalReferenceAssetCategoryLocalService>(bundle.getBundleContext(),
				ExternalReferenceAssetCategoryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}