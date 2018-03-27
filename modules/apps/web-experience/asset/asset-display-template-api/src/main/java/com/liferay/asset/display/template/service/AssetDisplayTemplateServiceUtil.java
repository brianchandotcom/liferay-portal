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

package com.liferay.asset.display.template.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for AssetDisplayTemplate. This utility wraps
 * {@link com.liferay.asset.display.template.service.impl.AssetDisplayTemplateServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayTemplateService
 * @see com.liferay.asset.display.template.service.base.AssetDisplayTemplateServiceBaseImpl
 * @see com.liferay.asset.display.template.service.impl.AssetDisplayTemplateServiceImpl
 * @generated
 */
@ProviderType
public class AssetDisplayTemplateServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.asset.display.template.service.impl.AssetDisplayTemplateServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.asset.display.template.model.AssetDisplayTemplate addAssetDisplayTemplate(
		long groupId, long userId, java.lang.String name, long classNameId,
		java.lang.String language, java.lang.String scriptContent,
		boolean main,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addAssetDisplayTemplate(groupId, userId, name, classNameId,
			language, scriptContent, main, serviceContext);
	}

	public static com.liferay.asset.display.template.model.AssetDisplayTemplate deleteAssetDisplayTemplate(
		long assetDisplayTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteAssetDisplayTemplate(assetDisplayTemplateId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.asset.display.template.model.AssetDisplayTemplate updateAssetDisplayTemplate(
		long assetDisplayTemplateId, java.lang.String name, long classNameId,
		java.lang.String language, java.lang.String scriptContent,
		boolean main,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateAssetDisplayTemplate(assetDisplayTemplateId, name,
			classNameId, language, scriptContent, main, serviceContext);
	}

	public static AssetDisplayTemplateService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetDisplayTemplateService, AssetDisplayTemplateService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetDisplayTemplateService.class);

		ServiceTracker<AssetDisplayTemplateService, AssetDisplayTemplateService> serviceTracker =
			new ServiceTracker<AssetDisplayTemplateService, AssetDisplayTemplateService>(bundle.getBundleContext(),
				AssetDisplayTemplateService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}