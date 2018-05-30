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

package com.liferay.commerce.tax.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceTaxFixedRateAddressRel. This utility wraps
 * {@link com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateAddressRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRelService
 * @see com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateAddressRelServiceBaseImpl
 * @see com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateAddressRelServiceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateAddressRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateAddressRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long cpTaxCategoryId, long commerceCountryId,
		long commerceRegionId, String zip, double rate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceTaxFixedRateAddressRel(commerceTaxMethodId,
			cpTaxCategoryId, commerceCountryId, commerceRegionId, zip, rate,
			serviceContext);
	}

	public static void deleteCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
	}

	public static java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> getCommerceTaxMethodFixedRateAddressRels(
		long groupId, long commerceTaxMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceTaxMethodFixedRateAddressRels(groupId,
			commerceTaxMethodId, start, end, orderByComparator);
	}

	public static int getCommerceTaxMethodFixedRateAddressRelsCount(
		long groupId, long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceTaxMethodFixedRateAddressRelsCount(groupId,
			commerceTaxMethodId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId, long commerceCountryId,
		long commerceRegionId, String zip, double rate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId,
			commerceCountryId, commerceRegionId, zip, rate);
	}

	public static CommerceTaxFixedRateAddressRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxFixedRateAddressRelService, CommerceTaxFixedRateAddressRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceTaxFixedRateAddressRelService.class);

		ServiceTracker<CommerceTaxFixedRateAddressRelService, CommerceTaxFixedRateAddressRelService> serviceTracker =
			new ServiceTracker<CommerceTaxFixedRateAddressRelService, CommerceTaxFixedRateAddressRelService>(bundle.getBundleContext(),
				CommerceTaxFixedRateAddressRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}