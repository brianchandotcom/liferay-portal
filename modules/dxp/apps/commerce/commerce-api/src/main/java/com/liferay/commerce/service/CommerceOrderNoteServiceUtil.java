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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceOrderNote. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceOrderNoteServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderNoteService
 * @see com.liferay.commerce.service.base.CommerceOrderNoteServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceOrderNoteServiceImpl
 * @generated
 */
@ProviderType
public class CommerceOrderNoteServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceOrderNoteServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceOrderNote addCommerceOrderNote(
		long commerceOrderId, java.lang.String content, boolean restricted,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceOrderNote(commerceOrderId, content, restricted,
			serviceContext);
	}

	public static void deleteCommerceOrderNote(long commerceOrderNoteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceOrderNote(commerceOrderNoteId);
	}

	public static com.liferay.commerce.model.CommerceOrderNote getCommerceOrderNote(
		long commerceOrderNoteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderNote(commerceOrderNoteId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderNote> getCommerceOrderNotes(
		long commerceOrderId, boolean restricted)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderNotes(commerceOrderId, restricted);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderNote> getCommerceOrderNotes(
		long commerceOrderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderNotes(commerceOrderId, start, end);
	}

	public static int getCommerceOrderNotesCount(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderNotesCount(commerceOrderId);
	}

	public static int getCommerceOrderNotesCount(long commerceOrderId,
		boolean restricted)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceOrderNotesCount(commerceOrderId, restricted);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceOrderNote updateCommerceOrderNote(
		long commerceOrderNoteId, java.lang.String content, boolean restricted)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceOrderNote(commerceOrderNoteId, content,
			restricted);
	}

	public static CommerceOrderNoteService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderNoteService, CommerceOrderNoteService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceOrderNoteService.class);
}