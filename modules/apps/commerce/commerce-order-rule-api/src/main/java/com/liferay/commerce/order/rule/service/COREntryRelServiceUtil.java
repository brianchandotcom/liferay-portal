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

package com.liferay.commerce.order.rule.service;

import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for COREntryRel. This utility wraps
 * <code>com.liferay.commerce.order.rule.service.impl.COREntryRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see COREntryRelService
 * @generated
 */
public class COREntryRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.order.rule.service.impl.COREntryRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static COREntryRel addCOREntryRel(
			String className, long classPK, long corEntryId)
		throws PortalException {

		return getService().addCOREntryRel(className, classPK, corEntryId);
	}

	public static void deleteCOREntryRel(long corEntryRelId)
		throws PortalException {

		getService().deleteCOREntryRel(corEntryRelId);
	}

	public static void deleteCOREntryRels(String className, long corEntryId)
		throws PortalException {

		getService().deleteCOREntryRels(className, corEntryId);
	}

	public static void deleteCOREntryRelsByCOREntryId(long corEntryId)
		throws PortalException {

		getService().deleteCOREntryRelsByCOREntryId(corEntryId);
	}

	public static COREntryRel fetchCOREntryRel(
			String className, long classPK, long corEntryId)
		throws PortalException {

		return getService().fetchCOREntryRel(className, classPK, corEntryId);
	}

	public static List<COREntryRel> getAccountEntryCOREntryRels(
			long corEntryId, String keywords, int start, int end)
		throws PortalException {

		return getService().getAccountEntryCOREntryRels(
			corEntryId, keywords, start, end);
	}

	public static int getAccountEntryCOREntryRelsCount(
			long corEntryId, String keywords)
		throws PortalException {

		return getService().getAccountEntryCOREntryRelsCount(
			corEntryId, keywords);
	}

	public static List<COREntryRel> getAccountGroupCOREntryRels(
			long corEntryId, String keywords, int start, int end)
		throws PortalException {

		return getService().getAccountGroupCOREntryRels(
			corEntryId, keywords, start, end);
	}

	public static int getAccountGroupCOREntryRelsCount(
			long corEntryId, String keywords)
		throws PortalException {

		return getService().getAccountGroupCOREntryRelsCount(
			corEntryId, keywords);
	}

	public static List<COREntryRel> getCommerceChannelCOREntryRels(
			long corEntryId, String keywords, int start, int end)
		throws PortalException {

		return getService().getCommerceChannelCOREntryRels(
			corEntryId, keywords, start, end);
	}

	public static int getCommerceChannelCOREntryRelsCount(
			long corEntryId, String keywords)
		throws PortalException {

		return getService().getCommerceChannelCOREntryRelsCount(
			corEntryId, keywords);
	}

	public static List<COREntryRel> getCommerceOrderTypeCOREntryRels(
			long corEntryId, String keywords, int start, int end)
		throws PortalException {

		return getService().getCommerceOrderTypeCOREntryRels(
			corEntryId, keywords, start, end);
	}

	public static int getCommerceOrderTypeCOREntryRelsCount(
			long corEntryId, String keywords)
		throws PortalException {

		return getService().getCommerceOrderTypeCOREntryRelsCount(
			corEntryId, keywords);
	}

	public static COREntryRel getCOREntryRel(long corEntryRelId)
		throws PortalException {

		return getService().getCOREntryRel(corEntryRelId);
	}

	public static List<COREntryRel> getCOREntryRels(long corEntryId)
		throws PortalException {

		return getService().getCOREntryRels(corEntryId);
	}

	public static List<COREntryRel> getCOREntryRels(
			long corEntryId, int start, int end,
			OrderByComparator<COREntryRel> orderByComparator)
		throws PortalException {

		return getService().getCOREntryRels(
			corEntryId, start, end, orderByComparator);
	}

	public static int getCOREntryRelsCount(long corEntryId)
		throws PortalException {

		return getService().getCOREntryRelsCount(corEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static COREntryRelService getService() {
		return _service;
	}

	private static volatile COREntryRelService _service;

}