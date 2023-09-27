/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.service;

import com.liferay.commerce.notification.model.CommerceNotificationTemplateCommerceAccountGroupRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceNotificationTemplateCommerceAccountGroupRel. This utility wraps
 * <code>com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateCommerceAccountGroupRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateCommerceAccountGroupRelService
 * @generated
 */
public class CommerceNotificationTemplateCommerceAccountGroupRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateCommerceAccountGroupRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
			addCommerceNotificationTemplateCommerceAccountGroupRel(
				long commerceNotificationTemplateId,
				long commerceAccountGroupId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().
			addCommerceNotificationTemplateCommerceAccountGroupRel(
				commerceNotificationTemplateId, commerceAccountGroupId,
				serviceContext);
	}

	public static void
			deleteCommerceNotificationTemplateCommerceAccountGroupRel(
				long commerceNotificationTemplateCommerceAccountGroupRelId)
		throws PortalException {

		getService().deleteCommerceNotificationTemplateCommerceAccountGroupRel(
			commerceNotificationTemplateCommerceAccountGroupRelId);
	}

	public static CommerceNotificationTemplateCommerceAccountGroupRel
			fetchCommerceNotificationTemplateCommerceAccountGroupRel(
				long commerceNotificationTemplateId,
				long commerceAccountGroupId)
		throws PortalException {

		return getService().
			fetchCommerceNotificationTemplateCommerceAccountGroupRel(
				commerceNotificationTemplateId, commerceAccountGroupId);
	}

	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
			getCommerceNotificationTemplateCommerceAccountGroupRels(
				long commerceNotificationTemplateId, int start, int end,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws PortalException {

		return getService().
			getCommerceNotificationTemplateCommerceAccountGroupRels(
				commerceNotificationTemplateId, start, end, orderByComparator);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceNotificationTemplateCommerceAccountGroupRelService
		getService() {

		return _service;
	}

	public static void setService(
		CommerceNotificationTemplateCommerceAccountGroupRelService service) {

		_service = service;
	}

	private static volatile
		CommerceNotificationTemplateCommerceAccountGroupRelService _service;

}