/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceChannelRel. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CommerceChannelRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceChannelRelService
 * @generated
 */
public class CommerceChannelRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CommerceChannelRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceChannelRel addCommerceChannelRel(
			String className, long classPK, long commerceChannelId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceChannelRel(
			className, classPK, commerceChannelId, serviceContext);
	}

	public static void deleteCommerceChannelRel(long commerceChannelRelId)
		throws PortalException {

		getService().deleteCommerceChannelRel(commerceChannelRelId);
	}

	public static void deleteCommerceChannelRels(String className, long classPK)
		throws PortalException {

		getService().deleteCommerceChannelRels(className, classPK);
	}

	public static CommerceChannelRel fetchCommerceChannelRel(
			String className, long classPK, long commerceChannelId)
		throws PortalException {

		return getService().fetchCommerceChannelRel(
			className, classPK, commerceChannelId);
	}

	public static CommerceChannelRel getCommerceChannelRel(
			long commerceChannelRelId)
		throws PortalException {

		return getService().getCommerceChannelRel(commerceChannelRelId);
	}

	public static List<CommerceChannelRel> getCommerceChannelRels(
			long commerceChannelId, int start, int end,
			OrderByComparator<CommerceChannelRel> orderByComparator)
		throws PortalException {

		return getService().getCommerceChannelRels(
			commerceChannelId, start, end, orderByComparator);
	}

	public static List<CommerceChannelRel> getCommerceChannelRels(
			String className, long classPK, String name, int start, int end)
		throws PortalException {

		return getService().getCommerceChannelRels(
			className, classPK, name, start, end);
	}

	public static int getCommerceChannelRelsCount(long commerceChannelId)
		throws PortalException {

		return getService().getCommerceChannelRelsCount(commerceChannelId);
	}

	public static int getCommerceChannelRelsCount(
			String className, long classPK)
		throws PortalException {

		return getService().getCommerceChannelRelsCount(className, classPK);
	}

	public static int getCommerceChannelRelsCount(
			String className, long classPK, String name)
		throws PortalException {

		return getService().getCommerceChannelRelsCount(
			className, classPK, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceChannelRelService getService() {
		return _service;
	}

	public static void setService(CommerceChannelRelService service) {
		_service = service;
	}

	private static volatile CommerceChannelRelService _service;

}