/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link COREntryService}.
 *
 * @author Luca Pellizzon
 * @see COREntryService
 * @generated
 */
public class COREntryServiceWrapper
	implements COREntryService, ServiceWrapper<COREntryService> {

	public COREntryServiceWrapper() {
		this(null);
	}

	public COREntryServiceWrapper(COREntryService corEntryService) {
		_corEntryService = corEntryService;
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntry addCOREntry(
			String externalReferenceCode, boolean active, String description,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String name, int priority, String type,
			String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryService.addCOREntry(
			externalReferenceCode, active, description, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, name,
			priority, type, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntry deleteCOREntry(
			long corEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryService.deleteCOREntry(corEntryId);
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntry
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntry fetchCOREntry(
			long corEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryService.fetchCOREntry(corEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
			getCOREntries(long companyId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryService.getCOREntries(companyId, active, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
			getCOREntries(
				long companyId, boolean active, String type, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryService.getCOREntries(
			companyId, active, type, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
			getCOREntries(long companyId, String type, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryService.getCOREntries(companyId, type, start, end);
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntry getCOREntry(
			long corEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryService.getCOREntry(corEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _corEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntry updateCOREntry(
			long corEntryId, boolean active, String description,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String name, int priority, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryService.updateCOREntry(
			corEntryId, active, description, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, name,
			priority, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntry
			updateCOREntryExternalReferenceCode(
				String externalReferenceCode, long corEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryService.updateCOREntryExternalReferenceCode(
			externalReferenceCode, corEntryId);
	}

	@Override
	public COREntryService getWrappedService() {
		return _corEntryService;
	}

	@Override
	public void setWrappedService(COREntryService corEntryService) {
		_corEntryService = corEntryService;
	}

	private COREntryService _corEntryService;

}