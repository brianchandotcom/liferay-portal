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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceProductDefinition. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CommerceProductDefinitionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionService
 * @see com.liferay.commerce.product.service.base.CommerceProductDefinitionServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductDefinitionServiceImpl
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductDefinitionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CommerceProductDefinition addCommerceProductDefinition(
		java.lang.String baseSKU,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String productTypeName, java.lang.String ddmStructureKey,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceProductDefinition(baseSKU, titleMap,
			descriptionMap, productTypeName, ddmStructureKey, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinition deleteCommerceProductDefinition(
		com.liferay.commerce.product.model.CommerceProductDefinition commerceProductDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductDefinition(commerceProductDefinition);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinition deleteCommerceProductDefinition(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductDefinition(commerceProductDefinitionId);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinition getCommerceProductDefinition(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductDefinition(commerceProductDefinitionId);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinition updateCommerceProductDefinition(
		long commerceProductDefinitionId, java.lang.String baseSKU,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String productTypeName, java.lang.String ddmStructureKey,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceProductDefinition(commerceProductDefinitionId,
			baseSKU, titleMap, descriptionMap, productTypeName,
			ddmStructureKey, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinition updateStatus(
		long userId, long commerceProductDefinitionId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatus(userId, commerceProductDefinitionId, status,
			serviceContext, workflowContext);
	}

	public static int getCommerceProductDefinitionsCount(long groupId) {
		return getService().getCommerceProductDefinitionsCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinition> getCommerceProductDefinitions(
		long groupId, int start, int end) {
		return getService().getCommerceProductDefinitions(groupId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinition> getCommerceProductDefinitions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinition> orderByComparator) {
		return getService()
				   .getCommerceProductDefinitions(groupId, start, end,
			orderByComparator);
	}

	public static CommerceProductDefinitionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductDefinitionService, CommerceProductDefinitionService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductDefinitionService.class);
}