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

package com.liferay.commerce.qualifier.service;

import com.liferay.commerce.qualifier.model.CommerceQualifierEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CommerceQualifierEntry. This utility wraps
 * <code>com.liferay.commerce.qualifier.service.impl.CommerceQualifierEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Riccardo Alberti
 * @see CommerceQualifierEntryService
 * @generated
 */
public class CommerceQualifierEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.qualifier.service.impl.CommerceQualifierEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceQualifierEntry addCommerceQualifierEntry(
			String sourceClassName, long sourceClassPK,
			String sourceCommerceQualifierMetadataKey, String targetClassName,
			long targetClassPK, String targetCommerceQualifierMetadataKey)
		throws PortalException {

		return getService().addCommerceQualifierEntry(
			sourceClassName, sourceClassPK, sourceCommerceQualifierMetadataKey,
			targetClassName, targetClassPK, targetCommerceQualifierMetadataKey);
	}

	public static CommerceQualifierEntry deleteCommerceQualifierEntry(
			long commerceQualifierEntryId)
		throws PortalException {

		return getService().deleteCommerceQualifierEntry(
			commerceQualifierEntryId);
	}

	public static void deleteSourceCommerceQualifierEntries(
			String sourceClassName, long sourceClassPK)
		throws PortalException {

		getService().deleteSourceCommerceQualifierEntries(
			sourceClassName, sourceClassPK);
	}

	public static void deleteTargetCommerceQualifierEntries(
			String targetClassName, long targetClassPK)
		throws PortalException {

		getService().deleteTargetCommerceQualifierEntries(
			targetClassName, targetClassPK);
	}

	public static CommerceQualifierEntry fetchCommerceQualifierEntry(
			String sourceClassName, long sourceClassPK, String targetClassName,
			long targetClassPK)
		throws PortalException {

		return getService().fetchCommerceQualifierEntry(
			sourceClassName, sourceClassPK, targetClassName, targetClassPK);
	}

	public static CommerceQualifierEntry getCommerceQualifierEntry(
			long commerceQualifierEntryId)
		throws PortalException {

		return getService().getCommerceQualifierEntry(commerceQualifierEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<CommerceQualifierEntry>
			getSourceCommerceQualifierEntries(
				long companyId, String sourceClassName, long sourceClassPK,
				String targetCommerceQualifierMetadataKey, String keywords,
				int start, int end)
		throws PortalException {

		return getService().getSourceCommerceQualifierEntries(
			companyId, sourceClassName, sourceClassPK,
			targetCommerceQualifierMetadataKey, keywords, start, end);
	}

	public static int getSourceCommerceQualifierEntriesCount(
			long companyId, String sourceClassName, long sourceClassPK,
			String targetCommerceQualifierMetadataKey, String keywords)
		throws PortalException {

		return getService().getSourceCommerceQualifierEntriesCount(
			companyId, sourceClassName, sourceClassPK,
			targetCommerceQualifierMetadataKey, keywords);
	}

	public static List<CommerceQualifierEntry>
			getTargetCommerceQualifierEntries(
				long companyId, String sourceCommerceQualifierMetadataKey,
				String targetClassName, long targetClassPK, String keywords,
				int start, int end)
		throws PortalException {

		return getService().getTargetCommerceQualifierEntries(
			companyId, sourceCommerceQualifierMetadataKey, targetClassName,
			targetClassPK, keywords, start, end);
	}

	public static int getTargetCommerceQualifierEntriesCount(
			long companyId, String sourceCommerceQualifierMetadataKey,
			String targetClassName, long targetClassPK, String keywords)
		throws PortalException {

		return getService().getTargetCommerceQualifierEntriesCount(
			companyId, sourceCommerceQualifierMetadataKey, targetClassName,
			targetClassPK, keywords);
	}

	public static CommerceQualifierEntryService getService() {
		return _service;
	}

	public static void setService(CommerceQualifierEntryService service) {
		_service = service;
	}

	private static volatile CommerceQualifierEntryService _service;

}