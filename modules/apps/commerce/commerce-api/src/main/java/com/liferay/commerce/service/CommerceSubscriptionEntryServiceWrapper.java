/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceSubscriptionEntryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceSubscriptionEntryService
 * @generated
 */
public class CommerceSubscriptionEntryServiceWrapper
	implements CommerceSubscriptionEntryService,
			   ServiceWrapper<CommerceSubscriptionEntryService> {

	public CommerceSubscriptionEntryServiceWrapper() {
		this(null);
	}

	public CommerceSubscriptionEntryServiceWrapper(
		CommerceSubscriptionEntryService commerceSubscriptionEntryService) {

		_commerceSubscriptionEntryService = commerceSubscriptionEntryService;
	}

	@Override
	public void deleteCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceSubscriptionEntryService.deleteCommerceSubscriptionEntry(
			commerceSubscriptionEntryId);
	}

	@Override
	public com.liferay.commerce.model.CommerceSubscriptionEntry
			fetchCommerceSubscriptionEntry(long commerceSubscriptionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceSubscriptionEntryService.fetchCommerceSubscriptionEntry(
			commerceSubscriptionEntryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceSubscriptionEntry>
			getCommerceSubscriptionEntries(
				long companyId, long userId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceSubscriptionEntry>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceSubscriptionEntryService.getCommerceSubscriptionEntries(
			companyId, userId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceSubscriptionEntry>
			getCommerceSubscriptionEntries(
				long companyId, long groupId, long userId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceSubscriptionEntry>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceSubscriptionEntryService.getCommerceSubscriptionEntries(
			companyId, groupId, userId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommerceSubscriptionEntriesCount(long companyId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceSubscriptionEntryService.
			getCommerceSubscriptionEntriesCount(companyId, userId);
	}

	@Override
	public int getCommerceSubscriptionEntriesCount(
			long companyId, long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceSubscriptionEntryService.
			getCommerceSubscriptionEntriesCount(companyId, groupId, userId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceSubscriptionEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceSubscriptionEntry>
				searchCommerceSubscriptionEntries(
					long companyId, Long maxSubscriptionCycles,
					Integer subscriptionStatus, String keywords, int start,
					int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceSubscriptionEntryService.
			searchCommerceSubscriptionEntries(
				companyId, maxSubscriptionCycles, subscriptionStatus, keywords,
				start, end, sort);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceSubscriptionEntry>
				searchCommerceSubscriptionEntries(
					long companyId, long[] groupIds, Long maxSubscriptionCycles,
					Integer subscriptionStatus, String keywords, int start,
					int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceSubscriptionEntryService.
			searchCommerceSubscriptionEntries(
				companyId, groupIds, maxSubscriptionCycles, subscriptionStatus,
				keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.model.CommerceSubscriptionEntry
			updateCommerceSubscriptionEntry(
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
				long deliveryMaxSubscriptionCycles,
				int deliverySubscriptionStatus,
				int deliveryNextIterationDateMonth,
				int deliveryNextIterationDateDay,
				int deliveryNextIterationDateYear,
				int deliveryNextIterationDateHour,
				int deliveryNextIterationDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceSubscriptionEntryService.
			updateCommerceSubscriptionEntry(
				commerceSubscriptionEntryId, subscriptionLength,
				subscriptionType, subscriptionTypeSettingsUnicodeProperties,
				maxSubscriptionCycles, subscriptionStatus,
				nextIterationDateMonth, nextIterationDateDay,
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
	@Override
	public com.liferay.commerce.model.CommerceSubscriptionEntry
			updateSubscriptionStatus(
				long commerceSubscriptionEntryId, int subscriptionStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceSubscriptionEntryService.updateSubscriptionStatus(
			commerceSubscriptionEntryId, subscriptionStatus);
	}

	@Override
	public CommerceSubscriptionEntryService getWrappedService() {
		return _commerceSubscriptionEntryService;
	}

	@Override
	public void setWrappedService(
		CommerceSubscriptionEntryService commerceSubscriptionEntryService) {

		_commerceSubscriptionEntryService = commerceSubscriptionEntryService;
	}

	private CommerceSubscriptionEntryService _commerceSubscriptionEntryService;

}