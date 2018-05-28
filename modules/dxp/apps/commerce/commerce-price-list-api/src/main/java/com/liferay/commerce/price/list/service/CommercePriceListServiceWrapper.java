/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.price.list.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListService
 * @generated
 */
@ProviderType
public class CommercePriceListServiceWrapper implements CommercePriceListService,
	ServiceWrapper<CommercePriceListService> {
	public CommercePriceListServiceWrapper(
		CommercePriceListService commercePriceListService) {
		_commercePriceListService = commercePriceListService;
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceList addCommercePriceList(
		long commerceCurrencyId, String name, double priority,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.addCommercePriceList(commerceCurrencyId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceList addCommercePriceList(
		long commerceCurrencyId, String name, double priority,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, String externalReferenceCode,
		boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.addCommercePriceList(commerceCurrencyId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, externalReferenceCode, neverExpire,
			serviceContext);
	}

	@Override
	public void deleteCommercePriceList(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePriceListService.deleteCommercePriceList(commercePriceListId);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceList fetchByExternalReferenceCode(
		String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.fetchByExternalReferenceCode(externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceList fetchCommercePriceList(
		long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.fetchCommercePriceList(commercePriceListId);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceList> getCommercePriceLists(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.getCommercePriceLists(groupId, start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceList> getCommercePriceLists(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceList> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.getCommercePriceLists(groupId, status,
			start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListsCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.getCommercePriceListsCount(groupId,
			status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceListService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _commercePriceListService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.price.list.model.CommercePriceList> searchCommercePriceLists(
		long companyId, long groupId, String keywords, int status, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.searchCommercePriceLists(companyId,
			groupId, keywords, status, start, end, sort);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceList updateCommercePriceList(
		long commercePriceListId, long commerceCurrencyId, String name,
		double priority, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.updateCommercePriceList(commercePriceListId,
			commerceCurrencyId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceList updateCommercePriceList(
		long commercePriceListId, long commerceCurrencyId, String name,
		double priority, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute,
		String externalReferenceCode, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.updateCommercePriceList(commercePriceListId,
			commerceCurrencyId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceList upsertCommercePriceList(
		long commercePriceListId, long commerceCurrencyId, String name,
		double priority, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute,
		String externalReferenceCode, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.upsertCommercePriceList(commercePriceListId,
			commerceCurrencyId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	@Override
	public CommercePriceListService getWrappedService() {
		return _commercePriceListService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListService commercePriceListService) {
		_commercePriceListService = commercePriceListService;
	}

	private CommercePriceListService _commercePriceListService;
}