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

package com.liferay.commerce.application.service;

import com.liferay.commerce.application.model.CommerceApplicationBrand;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CommerceApplicationBrand. This utility wraps
 * <code>com.liferay.commerce.application.service.impl.CommerceApplicationBrandServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationBrandService
 * @generated
 */
public class CommerceApplicationBrandServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.application.service.impl.CommerceApplicationBrandServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceApplicationBrand addCommerceApplicationBrand(
			long userId, String name, boolean logo, byte[] logoBytes)
		throws PortalException {

		return getService().addCommerceApplicationBrand(
			userId, name, logo, logoBytes);
	}

	public static void deleteCommerceApplicationBrand(
			long commerceApplicationBrandId)
		throws PortalException {

		getService().deleteCommerceApplicationBrand(commerceApplicationBrandId);
	}

	public static CommerceApplicationBrand getCommerceApplicationBrand(
			long commerceApplicationBrandId)
		throws PortalException {

		return getService().getCommerceApplicationBrand(
			commerceApplicationBrandId);
	}

	public static List<CommerceApplicationBrand> getCommerceApplicationBrands(
		long companyId, int start, int end) {

		return getService().getCommerceApplicationBrands(companyId, start, end);
	}

	public static int getCommerceApplicationBrandsCount(long companyId) {
		return getService().getCommerceApplicationBrandsCount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceApplicationBrand updateCommerceApplicationBrand(
			long commerceApplicationBrandId, String name, boolean logo,
			byte[] logoBytes)
		throws PortalException {

		return getService().updateCommerceApplicationBrand(
			commerceApplicationBrandId, name, logo, logoBytes);
	}

	public static CommerceApplicationBrandService getService() {
		return _service;
	}

	private static volatile CommerceApplicationBrandService _service;

}