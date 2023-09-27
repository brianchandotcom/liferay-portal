/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceSubscriptionEntry. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceSubscriptionEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceSubscriptionEntryService
 * @generated
 */
public class CommerceSubscriptionEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceSubscriptionEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId)
		throws PortalException {

		getService().deleteCommerceSubscriptionEntry(
			commerceSubscriptionEntryId);
	}

	public static CommerceSubscriptionEntry fetchCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId)
		throws PortalException {

		return getService().fetchCommerceSubscriptionEntry(
			commerceSubscriptionEntryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static List<CommerceSubscriptionEntry>
			getCommerceSubscriptionEntries(
				long companyId, long userId, int start, int end,
				OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws PortalException {

		return getService().getCommerceSubscriptionEntries(
			companyId, userId, start, end, orderByComparator);
	}

	public static List<CommerceSubscriptionEntry>
			getCommerceSubscriptionEntries(
				long companyId, long groupId, long userId, int start, int end,
				OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws PortalException {

		return getService().getCommerceSubscriptionEntries(
			companyId, groupId, userId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static int getCommerceSubscriptionEntriesCount(
			long companyId, long userId)
		throws PortalException {

		return getService().getCommerceSubscriptionEntriesCount(
			companyId, userId);
	}

	public static int getCommerceSubscriptionEntriesCount(
			long companyId, long groupId, long userId)
		throws PortalException {

		return getService().getCommerceSubscriptionEntriesCount(
			companyId, groupId, userId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommerceSubscriptionEntry> searchCommerceSubscriptionEntries(
				long companyId, Long maxSubscriptionCycles,
				Integer subscriptionStatus, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchCommerceSubscriptionEntries(
			companyId, maxSubscriptionCycles, subscriptionStatus, keywords,
			start, end, sort);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommerceSubscriptionEntry> searchCommerceSubscriptionEntries(
				long companyId, long[] groupIds, Long maxSubscriptionCycles,
				Integer subscriptionStatus, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchCommerceSubscriptionEntries(
			companyId, groupIds, maxSubscriptionCycles, subscriptionStatus,
			keywords, start, end, sort);
	}

	public static CommerceSubscriptionEntry updateCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId, int subscriptionLength,
			String subscriptionType,
			com.liferay.portal.kernel.util.UnicodeProperties
				subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, int subscriptionStatus,
			int nextIterationDateMonth, int nextIterationDateDay,
			int nextIterationDateYear, int nextIterationDateHour,
			int nextIterationDateMinute, int deliverySubscriptionLength,
			String deliverySubscriptionType,
			com.liferay.portal.kernel.util.UnicodeProperties
				deliverySubscriptionTypeSettingsUnicodeProperties,
			long deliveryMaxSubscriptionCycles, int deliverySubscriptionStatus,
			int deliveryNextIterationDateMonth,
			int deliveryNextIterationDateDay, int deliveryNextIterationDateYear,
			int deliveryNextIterationDateHour,
			int deliveryNextIterationDateMinute)
		throws PortalException {

		return getService().updateCommerceSubscriptionEntry(
			commerceSubscriptionEntryId, subscriptionLength, subscriptionType,
			subscriptionTypeSettingsUnicodeProperties, maxSubscriptionCycles,
			subscriptionStatus, nextIterationDateMonth, nextIterationDateDay,
			nextIterationDateYear, nextIterationDateHour,
			nextIterationDateMinute, deliverySubscriptionLength,
			deliverySubscriptionType,
			deliverySubscriptionTypeSettingsUnicodeProperties,
			deliveryMaxSubscriptionCycles, deliverySubscriptionStatus,
			deliveryNextIterationDateMonth, deliveryNextIterationDateDay,
			deliveryNextIterationDateYear, deliveryNextIterationDateHour,
			deliveryNextIterationDateMinute);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static CommerceSubscriptionEntry updateSubscriptionStatus(
			long commerceSubscriptionEntryId, int subscriptionStatus)
		throws PortalException {

		return getService().updateSubscriptionStatus(
			commerceSubscriptionEntryId, subscriptionStatus);
	}

	public static CommerceSubscriptionEntryService getService() {
		return _service;
	}

	public static void setService(CommerceSubscriptionEntryService service) {
		_service = service;
	}

	private static volatile CommerceSubscriptionEntryService _service;

}