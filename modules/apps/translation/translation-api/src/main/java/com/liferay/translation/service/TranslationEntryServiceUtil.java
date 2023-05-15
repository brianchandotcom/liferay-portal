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

package com.liferay.translation.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.translation.model.TranslationEntry;

/**
 * Provides the remote service utility for TranslationEntry. This utility wraps
 * <code>com.liferay.translation.service.impl.TranslationEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see TranslationEntryService
 * @generated
 */
public class TranslationEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.translation.service.impl.TranslationEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static TranslationEntry addOrUpdateTranslationEntry(
			long groupId,
			com.liferay.info.item.InfoItemReference infoItemReference,
			String content, String contentType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateTranslationEntry(
			groupId, infoItemReference, content, contentType, serviceContext);
	}

	public static TranslationEntry addOrUpdateTranslationEntry(
			long groupId, String languageId,
			com.liferay.info.item.InfoItemReference infoItemReference,
			com.liferay.info.item.InfoItemFieldValues infoItemFieldValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateTranslationEntry(
			groupId, languageId, infoItemReference, infoItemFieldValues,
			serviceContext);
	}

	public static TranslationEntry deleteTranslationEntry(
			long translationEntryId)
		throws PortalException {

		return getService().deleteTranslationEntry(translationEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static TranslationEntryService getService() {
		return _service;
	}

	public static void setService(TranslationEntryService service) {
		_service = service;
	}

	private static volatile TranslationEntryService _service;

}