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
 * Provides a wrapper for {@link CommercePriceEntryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryService
 * @generated
 */
@ProviderType
public class CommercePriceEntryServiceWrapper
	implements CommercePriceEntryService,
		ServiceWrapper<CommercePriceEntryService> {
	public CommercePriceEntryServiceWrapper(
		CommercePriceEntryService commercePriceEntryService) {
		_commercePriceEntryService = commercePriceEntryService;
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry addCommercePriceEntry(
		long cpInstanceId, long commercePriceListId,
		java.math.BigDecimal price, java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryService.addCommercePriceEntry(cpInstanceId,
			commercePriceListId, price, promoPrice, serviceContext);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry addCommercePriceEntry(
		long cpInstanceId, long commercePriceListId,
		String externalReferenceCode, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryService.addCommercePriceEntry(cpInstanceId,
			commercePriceListId, externalReferenceCode, price, promoPrice,
			serviceContext);
	}

	@Override
	public void deleteCommercePriceEntry(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePriceEntryService.deleteCommercePriceEntry(commercePriceEntryId);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry fetchByExternalReferenceCode(
		String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryService.fetchByExternalReferenceCode(externalReferenceCode);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> fetchCommercePriceEntries(
		long groupId, int start, int end) {
		return _commercePriceEntryService.fetchCommercePriceEntries(groupId,
			start, end);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry fetchCommercePriceEntry(
		long commercePriceEntryId) {
		return _commercePriceEntryService.fetchCommercePriceEntry(commercePriceEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end) {
		return _commercePriceEntryService.getCommercePriceEntries(commercePriceListId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceEntry> orderByComparator) {
		return _commercePriceEntryService.getCommercePriceEntries(commercePriceListId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceEntriesCount(long commercePriceListId) {
		return _commercePriceEntryService.getCommercePriceEntriesCount(commercePriceListId);
	}

	@Override
	public int getCommercePriceEntriesCountByGroupId(long groupId) {
		return _commercePriceEntryService.getCommercePriceEntriesCountByGroupId(groupId);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end) {
		return _commercePriceEntryService.getInstanceCommercePriceEntries(cpInstanceId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceEntry> orderByComparator) {
		return _commercePriceEntryService.getInstanceCommercePriceEntries(cpInstanceId,
			start, end, orderByComparator);
	}

	@Override
	public int getInstanceCommercePriceEntriesCount(long cpInstanceId) {
		return _commercePriceEntryService.getInstanceCommercePriceEntriesCount(cpInstanceId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _commercePriceEntryService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.price.list.model.CommercePriceEntry> searchCommercePriceEntries(
		long companyId, long groupId, long commercePriceListId,
		String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryService.searchCommercePriceEntries(companyId,
			groupId, commercePriceListId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry updateCommercePriceEntry(
		long commercePriceEntryId, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryService.updateCommercePriceEntry(commercePriceEntryId,
			price, promoPrice, serviceContext);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry updateCommercePriceEntry(
		long commercePriceEntryId, String externalReferenceCode,
		java.math.BigDecimal price, java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryService.updateCommercePriceEntry(commercePriceEntryId,
			externalReferenceCode, price, promoPrice, serviceContext);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry upsertCommercePriceEntry(
		long commercePriceEntryId, long cpInstanceId, long commercePriceListId,
		String externalReferenceCode, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice, String skuExternalReferenceCode,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryService.upsertCommercePriceEntry(commercePriceEntryId,
			cpInstanceId, commercePriceListId, externalReferenceCode, price,
			promoPrice, skuExternalReferenceCode, serviceContext);
	}

	@Override
	public CommercePriceEntryService getWrappedService() {
		return _commercePriceEntryService;
	}

	@Override
	public void setWrappedService(
		CommercePriceEntryService commercePriceEntryService) {
		_commercePriceEntryService = commercePriceEntryService;
	}

	private CommercePriceEntryService _commercePriceEntryService;
}