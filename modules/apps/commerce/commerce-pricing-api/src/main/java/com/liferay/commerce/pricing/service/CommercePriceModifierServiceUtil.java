/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.service;

import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommercePriceModifier. This utility wraps
 * <code>com.liferay.commerce.pricing.service.impl.CommercePriceModifierServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifierService
 * @generated
 */
public class CommercePriceModifierServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.pricing.service.impl.CommercePriceModifierServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommercePriceModifier addCommercePriceModifier(
			String externalReferenceCode, long groupId,
			long commercePriceListId, String title, String target,
			java.math.BigDecimal modifierAmount, String modifierType,
			double priority, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommercePriceModifier(
			externalReferenceCode, groupId, commercePriceListId, title, target,
			modifierAmount, modifierType, priority, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static CommercePriceModifier addOrUpdateCommercePriceModifier(
			String externalReferenceCode, long commercePriceModifierId,
			long groupId, long commercePriceListId, String title, String target,
			java.math.BigDecimal modifierAmount, String modifierType,
			double priority, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateCommercePriceModifier(
			externalReferenceCode, commercePriceModifierId, groupId,
			commercePriceListId, title, target, modifierAmount, modifierType,
			priority, active, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static CommercePriceModifier deleteCommercePriceModifier(
			long commercePriceModifierId)
		throws PortalException {

		return getService().deleteCommercePriceModifier(
			commercePriceModifierId);
	}

	public static CommercePriceModifier fetchCommercePriceModifier(
			long commercePriceModifierId)
		throws PortalException {

		return getService().fetchCommercePriceModifier(commercePriceModifierId);
	}

	public static CommercePriceModifier
			fetchCommercePriceModifierByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().fetchCommercePriceModifierByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static CommercePriceModifier getCommercePriceModifier(
			long commercePriceModifierId)
		throws PortalException {

		return getService().getCommercePriceModifier(commercePriceModifierId);
	}

	public static List<CommercePriceModifier> getCommercePriceModifiers(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceModifier> orderByComparator)
		throws PortalException {

		return getService().getCommercePriceModifiers(
			commercePriceListId, start, end, orderByComparator);
	}

	public static int getCommercePriceModifiersCount(long commercePriceListId)
		throws PortalException {

		return getService().getCommercePriceModifiersCount(commercePriceListId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommercePriceModifier updateCommercePriceModifier(
			long commercePriceModifierId, long groupId,
			long commercePriceListId, String title, String target,
			java.math.BigDecimal modifierAmount, String modifierType,
			double priority, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommercePriceModifier(
			commercePriceModifierId, groupId, commercePriceListId, title,
			target, modifierAmount, modifierType, priority, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static CommercePriceModifierService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<CommercePriceModifierService>
		_serviceSnapshot = new Snapshot<>(
			CommercePriceModifierServiceUtil.class,
			CommercePriceModifierService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:2046486278