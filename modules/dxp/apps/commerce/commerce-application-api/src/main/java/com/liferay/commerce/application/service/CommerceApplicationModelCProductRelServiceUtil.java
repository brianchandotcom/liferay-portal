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

import com.liferay.commerce.application.model.CommerceApplicationModelCProductRel;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CommerceApplicationModelCProductRel. This utility wraps
 * <code>com.liferay.commerce.application.service.impl.CommerceApplicationModelCProductRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModelCProductRelService
 * @generated
 */
public class CommerceApplicationModelCProductRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.application.service.impl.CommerceApplicationModelCProductRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceApplicationModelCProductRel
			addCommerceApplicationModelCProductRel(
				long userId, long commerceApplicationModelId, long cProductId)
		throws PortalException {

		return getService().addCommerceApplicationModelCProductRel(
			userId, commerceApplicationModelId, cProductId);
	}

	public static void deleteCommerceApplicationModelCProductRel(
			long commerceApplicationModelCProductRelId)
		throws PortalException {

		getService().deleteCommerceApplicationModelCProductRel(
			commerceApplicationModelCProductRelId);
	}

	public static List<CommerceApplicationModelCProductRel>
			getCommerceApplicationModelCProductRels(
				long commerceApplicationModelId, int start, int end)
		throws PortalException {

		return getService().getCommerceApplicationModelCProductRels(
			commerceApplicationModelId, start, end);
	}

	public static int getCommerceApplicationModelCProductRelsCount(
			long commerceApplicationModelId)
		throws PortalException {

		return getService().getCommerceApplicationModelCProductRelsCount(
			commerceApplicationModelId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceApplicationModelCProductRelService getService() {
		return _service;
	}

	private static volatile CommerceApplicationModelCProductRelService _service;

}