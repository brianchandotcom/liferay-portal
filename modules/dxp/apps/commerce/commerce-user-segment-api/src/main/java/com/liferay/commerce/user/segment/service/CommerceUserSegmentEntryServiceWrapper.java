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

package com.liferay.commerce.user.segment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceUserSegmentEntryService}.
 *
 * @author Marco Leo
 * @see CommerceUserSegmentEntryService
 * @generated
 */
@ProviderType
public class CommerceUserSegmentEntryServiceWrapper
	implements CommerceUserSegmentEntryService,
		ServiceWrapper<CommerceUserSegmentEntryService> {
	public CommerceUserSegmentEntryServiceWrapper(
		CommerceUserSegmentEntryService commerceUserSegmentEntryService) {
		_commerceUserSegmentEntryService = commerceUserSegmentEntryService;
	}

	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry addCommerceUserSegmentEntry(
		java.util.Map<java.util.Locale, String> nameMap, String key,
		boolean active, boolean system, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryService.addCommerceUserSegmentEntry(nameMap,
			key, active, system, priority, serviceContext);
	}

	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryService.deleteCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryService.getCommerceUserSegmentEntries(groupId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceUserSegmentEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryService.getCommerceUserSegmentEntriesCount(groupId);
	}

	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry getCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryService.getCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceUserSegmentEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> searchCommerceUserSegmentEntries(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryService.searchCommerceUserSegmentEntries(companyId,
			groupId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> searchCommerceUserSegmentEntries(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryService.searchCommerceUserSegmentEntries(searchContext);
	}

	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry updateCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId,
		java.util.Map<java.util.Locale, String> nameMap, String key,
		boolean active, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryService.updateCommerceUserSegmentEntry(commerceUserSegmentEntryId,
			nameMap, key, active, priority, serviceContext);
	}

	@Override
	public CommerceUserSegmentEntryService getWrappedService() {
		return _commerceUserSegmentEntryService;
	}

	@Override
	public void setWrappedService(
		CommerceUserSegmentEntryService commerceUserSegmentEntryService) {
		_commerceUserSegmentEntryService = commerceUserSegmentEntryService;
	}

	private CommerceUserSegmentEntryService _commerceUserSegmentEntryService;
}