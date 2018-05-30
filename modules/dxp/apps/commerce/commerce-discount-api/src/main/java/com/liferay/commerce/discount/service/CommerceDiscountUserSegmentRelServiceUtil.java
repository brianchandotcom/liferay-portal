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

package com.liferay.commerce.discount.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceDiscountUserSegmentRel. This utility wraps
 * {@link com.liferay.commerce.discount.service.impl.CommerceDiscountUserSegmentRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceDiscountUserSegmentRelService
 * @see com.liferay.commerce.discount.service.base.CommerceDiscountUserSegmentRelServiceBaseImpl
 * @see com.liferay.commerce.discount.service.impl.CommerceDiscountUserSegmentRelServiceImpl
 * @generated
 */
@ProviderType
public class CommerceDiscountUserSegmentRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.discount.service.impl.CommerceDiscountUserSegmentRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel addCommerceDiscountUserSegmentRel(
		long commerceDiscountId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceDiscountUserSegmentRel(commerceDiscountId,
			commerceUserSegmentEntryId, serviceContext);
	}

	public static void deleteCommerceDiscountUserSegmentRel(
		long commerceDiscountUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommerceDiscountUserSegmentRel(commerceDiscountUserSegmentRelId);
	}

	public static java.util.List<com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel> getCommerceDiscountUserSegmentRels(
		long commerceDiscountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceDiscountUserSegmentRels(commerceDiscountId,
			start, end, orderByComparator);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceDiscountUserSegmentRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceDiscountUserSegmentRelService, CommerceDiscountUserSegmentRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceDiscountUserSegmentRelService.class);

		ServiceTracker<CommerceDiscountUserSegmentRelService, CommerceDiscountUserSegmentRelService> serviceTracker =
			new ServiceTracker<CommerceDiscountUserSegmentRelService, CommerceDiscountUserSegmentRelService>(bundle.getBundleContext(),
				CommerceDiscountUserSegmentRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}