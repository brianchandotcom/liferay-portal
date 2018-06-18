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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPRuleUserSegmentRel. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPRuleUserSegmentRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPRuleUserSegmentRelService
 * @see com.liferay.commerce.product.service.base.CPRuleUserSegmentRelServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPRuleUserSegmentRelServiceImpl
 * @generated
 */
@ProviderType
public class CPRuleUserSegmentRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPRuleUserSegmentRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPRuleUserSegmentRel addCPRuleUserSegmentRel(
		long cpRuleId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPRuleUserSegmentRel(cpRuleId,
			commerceUserSegmentEntryId, serviceContext);
	}

	public static void deleteCPRuleUserSegmentRel(long cpRuleUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPRuleUserSegmentRel(cpRuleUserSegmentRelId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPRuleUserSegmentRel> getCPRuleUserSegmentRels(
		long cpRuleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPRuleUserSegmentRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPRuleUserSegmentRels(cpRuleId, start, end,
			orderByComparator);
	}

	public static int getCPRuleUserSegmentRelsCount(long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPRuleUserSegmentRelsCount(cpRuleId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPRuleUserSegmentRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPRuleUserSegmentRelService, CPRuleUserSegmentRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPRuleUserSegmentRelService.class);

		ServiceTracker<CPRuleUserSegmentRelService, CPRuleUserSegmentRelService> serviceTracker =
			new ServiceTracker<CPRuleUserSegmentRelService, CPRuleUserSegmentRelService>(bundle.getBundleContext(),
				CPRuleUserSegmentRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}