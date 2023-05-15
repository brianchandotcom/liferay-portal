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

package com.liferay.commerce.term.service;

import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceTermEntryRel. This utility wraps
 * <code>com.liferay.commerce.term.service.impl.CommerceTermEntryRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntryRelService
 * @generated
 */
public class CommerceTermEntryRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.term.service.impl.CommerceTermEntryRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceTermEntryRel addCommerceTermEntryRel(
			String className, long classPK, long commerceTermEntryId)
		throws PortalException {

		return getService().addCommerceTermEntryRel(
			className, classPK, commerceTermEntryId);
	}

	public static void deleteCommerceTermEntryRel(long commerceTermEntryRelId)
		throws PortalException {

		getService().deleteCommerceTermEntryRel(commerceTermEntryRelId);
	}

	public static void deleteCommerceTermEntryRels(
			String className, long commerceTermEntryId)
		throws PortalException {

		getService().deleteCommerceTermEntryRels(
			className, commerceTermEntryId);
	}

	public static void deleteCommerceTermEntryRelsByCommerceTermEntryId(
			long commerceTermEntryId)
		throws PortalException {

		getService().deleteCommerceTermEntryRelsByCommerceTermEntryId(
			commerceTermEntryId);
	}

	public static CommerceTermEntryRel fetchCommerceTermEntryRel(
			String className, long classPK, long commerceTermEntryId)
		throws PortalException {

		return getService().fetchCommerceTermEntryRel(
			className, classPK, commerceTermEntryId);
	}

	public static List<CommerceTermEntryRel>
			getCommerceOrderTypeCommerceTermEntryRels(
				long commerceTermEntryId, String keywords, int start, int end)
		throws PortalException {

		return getService().getCommerceOrderTypeCommerceTermEntryRels(
			commerceTermEntryId, keywords, start, end);
	}

	public static int getCommerceOrderTypeCommerceTermEntryRelsCount(
			long commerceTermEntryId, String keywords)
		throws PortalException {

		return getService().getCommerceOrderTypeCommerceTermEntryRelsCount(
			commerceTermEntryId, keywords);
	}

	public static CommerceTermEntryRel getCommerceTermEntryRel(
			long commerceTermEntryRelId)
		throws PortalException {

		return getService().getCommerceTermEntryRel(commerceTermEntryRelId);
	}

	public static List<CommerceTermEntryRel> getCommerceTermEntryRels(
			long commerceTermEntryId)
		throws PortalException {

		return getService().getCommerceTermEntryRels(commerceTermEntryId);
	}

	public static List<CommerceTermEntryRel> getCommerceTermEntryRels(
			long commerceTermEntryId, int start, int end,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws PortalException {

		return getService().getCommerceTermEntryRels(
			commerceTermEntryId, start, end, orderByComparator);
	}

	public static int getCommerceTermEntryRelsCount(long commerceTermEntryId)
		throws PortalException {

		return getService().getCommerceTermEntryRelsCount(commerceTermEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceTermEntryRelService getService() {
		return _service;
	}

	public static void setService(CommerceTermEntryRelService service) {
		_service = service;
	}

	private static volatile CommerceTermEntryRelService _service;

}