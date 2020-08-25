/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.pricing.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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
	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			addCommercePriceModifier(
				long userId, long groupId, String title, String target,
				long commercePriceListId, String modifierType,
				java.math.BigDecimal modifierAmount, double priority,
				boolean active, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceModifier(
			userId, groupId, title, target, commercePriceListId, modifierType,
			modifierAmount, priority, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			deleteCommercePriceModifier(long commercePriceModifierId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommercePriceModifier(
			commercePriceModifierId);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			fetchCommercePriceModifier(long commercePriceModifierId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommercePriceModifier(commercePriceModifierId);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			getCommercePriceModifier(long commercePriceModifierId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceModifier(commercePriceModifierId);
	}

	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifier>
				getCommercePriceModifiers(
					long commercePriceListId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.pricing.model.
							CommercePriceModifier> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceModifiers(
			commercePriceListId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifier>
				getCommercePriceModifiers(long companyId, String target)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceModifiers(companyId, target);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static int getCommercePriceModifiersCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceModifiersCount();
	}

	public static int getCommercePriceModifiersCount(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

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

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.pricing.model.CommercePriceModifier>
				searchCommercePriceModifiers(
					long companyId, String keywords, int status, int start,
					int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommercePriceModifiers(
			companyId, keywords, status, start, end, sort);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			updateCommercePriceModifier(
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

		return getService().updateCommercePriceModifier(
			commercePriceModifierId, groupId, title, target,
			commercePriceListId, modifierType, modifierAmount, priority, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			upsertCommercePriceModifier(
				long userId, long commercePriceModifierId, long groupId,
				String title, String target, long commercePriceListId,
				String modifierType, java.math.BigDecimal modifierAmount,
				double priority, boolean active, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				String externalReferenceCode, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePriceModifier(
			userId, commercePriceModifierId, groupId, title, target,
			commercePriceListId, modifierType, modifierAmount, priority, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	public static CommercePriceModifierService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePriceModifierService, CommercePriceModifierService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePriceModifierService.class);

		ServiceTracker
			<CommercePriceModifierService, CommercePriceModifierService>
				serviceTracker =
					new ServiceTracker
						<CommercePriceModifierService,
						 CommercePriceModifierService>(
							 bundle.getBundleContext(),
							 CommercePriceModifierService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}