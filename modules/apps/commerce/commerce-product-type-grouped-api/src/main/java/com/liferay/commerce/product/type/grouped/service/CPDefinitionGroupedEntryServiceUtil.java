/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.service;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CPDefinitionGroupedEntry. This utility wraps
 * <code>com.liferay.commerce.product.type.grouped.service.impl.CPDefinitionGroupedEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntryService
 * @generated
 */
public class CPDefinitionGroupedEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.type.grouped.service.impl.CPDefinitionGroupedEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addCPDefinitionGroupedEntries(
			long cpDefinitionId, long[] entryCPDefinitionIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().addCPDefinitionGroupedEntries(
			cpDefinitionId, entryCPDefinitionIds, serviceContext);
	}

	public static CPDefinitionGroupedEntry addCPDefinitionGroupedEntry(
			long cpDefinitionId, long entryCProductId, double priority,
			int quantity,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCPDefinitionGroupedEntry(
			cpDefinitionId, entryCProductId, priority, quantity,
			serviceContext);
	}

	public static CPDefinitionGroupedEntry deleteCPDefinitionGroupedEntry(
			long cpDefinitionGroupedEntryId)
		throws PortalException {

		return getService().deleteCPDefinitionGroupedEntry(
			cpDefinitionGroupedEntryId);
	}

	public static List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
			long cpDefinitionId, int start, int end,
			OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws PortalException {

		return getService().getCPDefinitionGroupedEntries(
			cpDefinitionId, start, end, orderByComparator);
	}

	public static List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
			long companyId, long cpDefinitionId, String keywords, int start,
			int end, com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().getCPDefinitionGroupedEntries(
			companyId, cpDefinitionId, keywords, start, end, sort);
	}

	public static int getCPDefinitionGroupedEntriesCount(long cpDefinitionId)
		throws PortalException {

		return getService().getCPDefinitionGroupedEntriesCount(cpDefinitionId);
	}

	public static int getCPDefinitionGroupedEntriesCount(
			long companyId, long cpDefinitionId, String keywords)
		throws PortalException {

		return getService().getCPDefinitionGroupedEntriesCount(
			companyId, cpDefinitionId, keywords);
	}

	public static CPDefinitionGroupedEntry getCPDefinitionGroupedEntry(
			long cpDefinitionGroupedEntryId)
		throws PortalException {

		return getService().getCPDefinitionGroupedEntry(
			cpDefinitionGroupedEntryId);
	}

	public static List<CPDefinitionGroupedEntry>
			getEntryCProductCPDefinitionGroupedEntries(
				long entryCProductId, int start, int end,
				OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws PortalException {

		return getService().getEntryCProductCPDefinitionGroupedEntries(
			entryCProductId, start, end, orderByComparator);
	}

	public static int getEntryCProductCPDefinitionGroupedEntriesCount(
			long entryCProductId)
		throws PortalException {

		return getService().getEntryCProductCPDefinitionGroupedEntriesCount(
			entryCProductId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDefinitionGroupedEntry updateCPDefinitionGroupedEntry(
			long cpDefinitionGroupedEntryId, double priority, int quantity)
		throws PortalException {

		return getService().updateCPDefinitionGroupedEntry(
			cpDefinitionGroupedEntryId, priority, quantity);
	}

	public static CPDefinitionGroupedEntryService getService() {
		return _service;
	}

	public static void setService(CPDefinitionGroupedEntryService service) {
		_service = service;
	}

	private static volatile CPDefinitionGroupedEntryService _service;

}