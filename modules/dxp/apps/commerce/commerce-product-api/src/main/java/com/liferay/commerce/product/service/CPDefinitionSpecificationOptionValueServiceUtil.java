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
 * Provides the remote service utility for CPDefinitionSpecificationOptionValue. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValueService
 * @see com.liferay.commerce.product.service.base.CPDefinitionSpecificationOptionValueServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionSpecificationOptionValueServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue addCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId,
		long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> valueMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionSpecificationOptionValue(cpDefinitionId,
			cpSpecificationOptionId, cpOptionCategoryId, valueMap, priority,
			serviceContext);
	}

	public static void deleteCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue fetchCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue fetchCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId) {
		return getService()
				   .fetchCPDefinitionSpecificationOptionValue(cpDefinitionId,
			cpSpecificationOptionId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue getCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionSpecificationOptionValues(cpDefinitionId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		long cpDefinitionId, long cpOptionCategoryId) {
		return getService()
				   .getCPDefinitionSpecificationOptionValues(cpDefinitionId,
			cpOptionCategoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue updateCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId, long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> valueMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId,
			cpOptionCategoryId, valueMap, priority, serviceContext);
	}

	public static CPDefinitionSpecificationOptionValueService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionSpecificationOptionValueService, CPDefinitionSpecificationOptionValueService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionSpecificationOptionValueService.class);

		ServiceTracker<CPDefinitionSpecificationOptionValueService, CPDefinitionSpecificationOptionValueService> serviceTracker =
			new ServiceTracker<CPDefinitionSpecificationOptionValueService, CPDefinitionSpecificationOptionValueService>(bundle.getBundleContext(),
				CPDefinitionSpecificationOptionValueService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}