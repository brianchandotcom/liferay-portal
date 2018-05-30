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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

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
		long commerceOrderId, String content, boolean restricted,
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
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceOrderNote updateCommerceOrderNote(
		long commerceOrderNoteId, String content, boolean restricted)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceOrderNote(commerceOrderNoteId, content,
			restricted);
	}

	public static CommerceOrderNoteService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderNoteService, CommerceOrderNoteService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceOrderNoteService.class);

		ServiceTracker<CommerceOrderNoteService, CommerceOrderNoteService> serviceTracker =
			new ServiceTracker<CommerceOrderNoteService, CommerceOrderNoteService>(bundle.getBundleContext(),
				CommerceOrderNoteService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}