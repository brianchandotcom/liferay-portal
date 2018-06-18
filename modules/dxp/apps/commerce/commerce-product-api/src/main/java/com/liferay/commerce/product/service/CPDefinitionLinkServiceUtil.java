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
 * Provides the remote service utility for CPDefinitionLink. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPDefinitionLinkServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionLinkService
 * @see com.liferay.commerce.product.service.base.CPDefinitionLinkServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionLinkServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionLinkServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionLinkServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteCPDefinitionLink(long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPDefinitionLink(cpDefinitionLinkId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionLink fetchCPDefinitionLink(
		long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPDefinitionLink(cpDefinitionLinkId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionLink getCPDefinitionLink(
		long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionLink(cpDefinitionLinkId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionLink> getCPDefinitionLinks(
		long cpDefinitionId1, String type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionLinks(cpDefinitionId1, type);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionLink> getCPDefinitionLinks(
		long cpDefinitionId1, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionLink> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionLinks(cpDefinitionId1, type, start, end,
			orderByComparator);
	}

	public static int getCPDefinitionLinksCount(long cpDefinitionId1,
		String type) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionLinksCount(cpDefinitionId1, type);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.model.CPDefinitionLink updateCPDefinitionLink(
		long cpDefinitionLinkId, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionLink(cpDefinitionLinkId, priority,
			serviceContext);
	}

	public static void updateCPDefinitionLinks(long cpDefinitionId1,
		long[] cpDefinitionIds2, String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateCPDefinitionLinks(cpDefinitionId1, cpDefinitionIds2, type,
			serviceContext);
	}

	public static CPDefinitionLinkService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionLinkService, CPDefinitionLinkService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionLinkService.class);

		ServiceTracker<CPDefinitionLinkService, CPDefinitionLinkService> serviceTracker =
			new ServiceTracker<CPDefinitionLinkService, CPDefinitionLinkService>(bundle.getBundleContext(),
				CPDefinitionLinkService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}