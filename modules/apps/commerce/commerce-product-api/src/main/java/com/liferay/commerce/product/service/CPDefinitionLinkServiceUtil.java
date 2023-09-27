/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CPDefinitionLink. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPDefinitionLinkServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionLinkService
 * @generated
 */
public class CPDefinitionLinkServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPDefinitionLinkServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPDefinitionLink addCPDefinitionLink(
			long cpDefinitionId, long cProductId, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, double priority, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCPDefinitionLink(
			cpDefinitionId, cProductId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, priority,
			type, serviceContext);
	}

	public static void deleteCPDefinitionLink(long cpDefinitionLinkId)
		throws PortalException {

		getService().deleteCPDefinitionLink(cpDefinitionLinkId);
	}

	public static CPDefinitionLink fetchCPDefinitionLink(
			long cpDefinitionLinkId)
		throws PortalException {

		return getService().fetchCPDefinitionLink(cpDefinitionLinkId);
	}

	public static CPDefinitionLink fetchCPDefinitionLink(
			long cpDefinitionId, long cProductId, String type)
		throws PortalException {

		return getService().fetchCPDefinitionLink(
			cpDefinitionId, cProductId, type);
	}

	public static CPDefinitionLink getCPDefinitionLink(long cpDefinitionLinkId)
		throws PortalException {

		return getService().getCPDefinitionLink(cpDefinitionLinkId);
	}

	public static List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId)
		throws PortalException {

		return getService().getCPDefinitionLinks(cpDefinitionId);
	}

	public static List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, int status)
		throws PortalException {

		return getService().getCPDefinitionLinks(cpDefinitionId, status);
	}

	public static List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, int start, int end)
		throws PortalException {

		return getService().getCPDefinitionLinks(cpDefinitionId, start, end);
	}

	public static List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, int status, int start, int end)
		throws PortalException {

		return getService().getCPDefinitionLinks(
			cpDefinitionId, status, start, end);
	}

	public static List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, String type)
		throws PortalException {

		return getService().getCPDefinitionLinks(cpDefinitionId, type);
	}

	public static List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, String type, int status)
		throws PortalException {

		return getService().getCPDefinitionLinks(cpDefinitionId, type, status);
	}

	public static List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, String type, int status, int start, int end,
			OrderByComparator<CPDefinitionLink> orderByComparator)
		throws PortalException {

		return getService().getCPDefinitionLinks(
			cpDefinitionId, type, status, start, end, orderByComparator);
	}

	public static List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, String type, int start, int end,
			OrderByComparator<CPDefinitionLink> orderByComparator)
		throws PortalException {

		return getService().getCPDefinitionLinks(
			cpDefinitionId, type, start, end, orderByComparator);
	}

	public static int getCPDefinitionLinksCount(long cpDefinitionId)
		throws PortalException {

		return getService().getCPDefinitionLinksCount(cpDefinitionId);
	}

	public static int getCPDefinitionLinksCount(long cpDefinitionId, int status)
		throws PortalException {

		return getService().getCPDefinitionLinksCount(cpDefinitionId, status);
	}

	public static int getCPDefinitionLinksCount(
			long cpDefinitionId, String type)
		throws PortalException {

		return getService().getCPDefinitionLinksCount(cpDefinitionId, type);
	}

	public static int getCPDefinitionLinksCount(
			long cpDefinitionId, String type, int status)
		throws PortalException {

		return getService().getCPDefinitionLinksCount(
			cpDefinitionId, type, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDefinitionLink updateCPDefinitionLink(
			long cpDefinitionLinkId, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, double priority,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCPDefinitionLink(
			cpDefinitionLinkId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, priority,
			serviceContext);
	}

	public static void updateCPDefinitionLinks(
			long cpDefinitionId, long[] cpDefinitionIds2, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().updateCPDefinitionLinks(
			cpDefinitionId, cpDefinitionIds2, type, serviceContext);
	}

	public static CPDefinitionLinkService getService() {
		return _service;
	}

	public static void setService(CPDefinitionLinkService service) {
		_service = service;
	}

	private static volatile CPDefinitionLinkService _service;

}