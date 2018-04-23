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

package com.liferay.commerce.product.type.virtual.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPDefinitionVirtualSetting. This utility wraps
 * {@link com.liferay.commerce.product.type.virtual.service.impl.CPDefinitionVirtualSettingServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingService
 * @see com.liferay.commerce.product.type.virtual.service.base.CPDefinitionVirtualSettingServiceBaseImpl
 * @see com.liferay.commerce.product.type.virtual.service.impl.CPDefinitionVirtualSettingServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionVirtualSettingServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.type.virtual.service.impl.CPDefinitionVirtualSettingServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
		long cpDefinitionId, long fileEntryId, java.lang.String url,
		java.lang.String activationStatus, long duration, int maxUsages,
		boolean useSample, long sampleFileEntryId, java.lang.String sampleUrl,
		boolean termsOfUseRequired,
		java.util.Map<java.util.Locale, java.lang.String> termsOfUseContentMap,
		long termsOfUseJournalArticleResourcePrimKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionVirtualSetting(cpDefinitionId, fileEntryId,
			url, activationStatus, duration, maxUsages, useSample,
			sampleFileEntryId, sampleUrl, termsOfUseRequired,
			termsOfUseContentMap, termsOfUseJournalArticleResourcePrimKey,
			serviceContext);
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting fetchCPDefinitionVirtualSettingByCPDefinitionId(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchCPDefinitionVirtualSettingByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
		long cpDefinitionVirtualSettingId, long fileEntryId,
		java.lang.String url, java.lang.String activationStatus, long duration,
		int maxUsages, boolean useSample, long sampleFileEntryId,
		java.lang.String sampleUrl, boolean termsOfUseRequired,
		java.util.Map<java.util.Locale, java.lang.String> termsOfUseContentMap,
		long termsOfUseJournalArticleResourcePrimKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionVirtualSetting(cpDefinitionVirtualSettingId,
			fileEntryId, url, activationStatus, duration, maxUsages, useSample,
			sampleFileEntryId, sampleUrl, termsOfUseRequired,
			termsOfUseContentMap, termsOfUseJournalArticleResourcePrimKey,
			serviceContext);
	}

	public static CPDefinitionVirtualSettingService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionVirtualSettingService, CPDefinitionVirtualSettingService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionVirtualSettingService.class);

		ServiceTracker<CPDefinitionVirtualSettingService, CPDefinitionVirtualSettingService> serviceTracker =
			new ServiceTracker<CPDefinitionVirtualSettingService, CPDefinitionVirtualSettingService>(bundle.getBundleContext(),
				CPDefinitionVirtualSettingService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}