/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.service;

import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceModifierService}.
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifierService
 * @generated
 */
public class CommercePriceModifierServiceWrapper
	implements CommercePriceModifierService,
			   ServiceWrapper<CommercePriceModifierService> {

	public CommercePriceModifierServiceWrapper() {
		this(null);
	}

	public CommercePriceModifierServiceWrapper(
		CommercePriceModifierService commercePriceModifierService) {

		_commercePriceModifierService = commercePriceModifierService;
	}

	@Override
	public CommercePriceModifier addCommercePriceModifier(
			long groupId, String title, String target, long commercePriceListId,
			String modifierType, java.math.BigDecimal modifierAmount,
			double priority, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierService.addCommercePriceModifier(
			groupId, title, target, commercePriceListId, modifierType,
			modifierAmount, priority, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommercePriceModifier addOrUpdateCommercePriceModifier(
			String externalReferenceCode, long commercePriceModifierId,
			long groupId, String title, String target, long commercePriceListId,
			String modifierType, java.math.BigDecimal modifierAmount,
			double priority, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierService.addOrUpdateCommercePriceModifier(
			externalReferenceCode, commercePriceModifierId, groupId, title,
			target, commercePriceListId, modifierType, modifierAmount, priority,
			active, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public CommercePriceModifier deleteCommercePriceModifier(
			long commercePriceModifierId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierService.deleteCommercePriceModifier(
			commercePriceModifierId);
	}

	@Override
	public CommercePriceModifier fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierService.fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public CommercePriceModifier fetchCommercePriceModifier(
			long commercePriceModifierId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierService.fetchCommercePriceModifier(
			commercePriceModifierId);
	}

	@Override
	public CommercePriceModifier getCommercePriceModifier(
			long commercePriceModifierId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierService.getCommercePriceModifier(
			commercePriceModifierId);
	}

	@Override
	public java.util.List<CommercePriceModifier> getCommercePriceModifiers(
			long commercePriceListId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierService.getCommercePriceModifiers(
			commercePriceListId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public java.util.List<CommercePriceModifier> getCommercePriceModifiers(
			long companyId, String target)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierService.getCommercePriceModifiers(
			companyId, target);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommercePriceModifiersCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierService.getCommercePriceModifiersCount();
	}

	@Override
	public int getCommercePriceModifiersCount(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierService.getCommercePriceModifiersCount(
			commercePriceListId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceModifierService.getOSGiServiceIdentifier();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommercePriceModifier> searchCommercePriceModifiers(
				long companyId, String keywords, int status, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierService.searchCommercePriceModifiers(
			companyId, keywords, status, start, end, sort);
	}

	@Override
	public CommercePriceModifier updateCommercePriceModifier(
			long commercePriceModifierId, long groupId, String title,
			String target, long commercePriceListId, String modifierType,
			java.math.BigDecimal modifierAmount, double priority,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierService.updateCommercePriceModifier(
			commercePriceModifierId, groupId, title, target,
			commercePriceListId, modifierType, modifierAmount, priority, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public CommercePriceModifierService getWrappedService() {
		return _commercePriceModifierService;
	}

	@Override
	public void setWrappedService(
		CommercePriceModifierService commercePriceModifierService) {

		_commercePriceModifierService = commercePriceModifierService;
	}

	private CommercePriceModifierService _commercePriceModifierService;

}