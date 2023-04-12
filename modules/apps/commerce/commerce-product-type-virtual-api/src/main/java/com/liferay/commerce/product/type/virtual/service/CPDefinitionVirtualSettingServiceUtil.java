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

import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * Provides the remote service utility for CPDefinitionVirtualSetting. This utility wraps
 * <code>com.liferay.commerce.product.type.virtual.service.impl.CPDefinitionVirtualSettingServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingService
 * @generated
 */
public class CPDefinitionVirtualSettingServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.type.virtual.service.impl.CPDefinitionVirtualSettingServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
			String className, long classPK, long fileEntryId, String url,
			int activationStatus, long duration, int maxUsages,
			boolean useSample, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<java.util.Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey, boolean override,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCPDefinitionVirtualSetting(
			className, classPK, fileEntryId, url, activationStatus, duration,
			maxUsages, useSample, sampleFileEntryId, sampleUrl,
			termsOfUseRequired, termsOfUseContentMap,
			termsOfUseJournalArticleResourcePrimKey, override, serviceContext);
	}

	public static CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
			String className, long classPK, long fileEntryId, String url,
			int activationStatus, long duration, int maxUsages,
			boolean useSample, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<java.util.Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCPDefinitionVirtualSetting(
			className, classPK, fileEntryId, url, activationStatus, duration,
			maxUsages, useSample, sampleFileEntryId, sampleUrl,
			termsOfUseRequired, termsOfUseContentMap,
			termsOfUseJournalArticleResourcePrimKey, serviceContext);
	}

	public static CPDefinitionVirtualSetting deleteCPDefinitionVirtualSetting(
			String className, long classPK)
		throws PortalException {

		return getService().deleteCPDefinitionVirtualSetting(
			className, classPK);
	}

	public static CPDefinitionVirtualSetting fetchCPDefinitionVirtualSetting(
			String className, long classPK)
		throws PortalException {

		return getService().fetchCPDefinitionVirtualSetting(className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
			long cpDefinitionVirtualSettingId, long fileEntryId, String url,
			int activationStatus, long duration, int maxUsages,
			boolean useSample, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<java.util.Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey, boolean override,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCPDefinitionVirtualSetting(
			cpDefinitionVirtualSettingId, fileEntryId, url, activationStatus,
			duration, maxUsages, useSample, sampleFileEntryId, sampleUrl,
			termsOfUseRequired, termsOfUseContentMap,
			termsOfUseJournalArticleResourcePrimKey, override, serviceContext);
	}

	public static CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
			long cpDefinitionVirtualSettingId, long fileEntryId, String url,
			int activationStatus, long duration, int maxUsages,
			boolean useSample, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<java.util.Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCPDefinitionVirtualSetting(
			cpDefinitionVirtualSettingId, fileEntryId, url, activationStatus,
			duration, maxUsages, useSample, sampleFileEntryId, sampleUrl,
			termsOfUseRequired, termsOfUseContentMap,
			termsOfUseJournalArticleResourcePrimKey, serviceContext);
	}

	public static CPDefinitionVirtualSettingService getService() {
		return _service;
	}

	private static volatile CPDefinitionVirtualSettingService _service;

}