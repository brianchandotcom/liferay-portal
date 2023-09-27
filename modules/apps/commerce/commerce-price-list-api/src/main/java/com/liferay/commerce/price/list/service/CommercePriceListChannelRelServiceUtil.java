/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.service;

import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommercePriceListChannelRel. This utility wraps
 * <code>com.liferay.commerce.price.list.service.impl.CommercePriceListChannelRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListChannelRelService
 * @generated
 */
public class CommercePriceListChannelRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceListChannelRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommercePriceListChannelRel addCommercePriceListChannelRel(
			long commercePriceListId, long commerceChannelId, int order,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommercePriceListChannelRel(
			commercePriceListId, commerceChannelId, order, serviceContext);
	}

	public static void deleteCommercePriceListChannelRel(
			long commercePriceListChannelRelId)
		throws PortalException {

		getService().deleteCommercePriceListChannelRel(
			commercePriceListChannelRelId);
	}

	public static void deleteCommercePriceListChannelRelsByCommercePriceListId(
			long commercePriceListId)
		throws PortalException {

		getService().deleteCommercePriceListChannelRelsByCommercePriceListId(
			commercePriceListId);
	}

	public static CommercePriceListChannelRel fetchCommercePriceListChannelRel(
			long commerceChannelId, long commercePriceListId)
		throws PortalException {

		return getService().fetchCommercePriceListChannelRel(
			commerceChannelId, commercePriceListId);
	}

	public static CommercePriceListChannelRel getCommercePriceListChannelRel(
			long commercePriceListChannelRelId)
		throws PortalException {

		return getService().getCommercePriceListChannelRel(
			commercePriceListChannelRelId);
	}

	public static List<CommercePriceListChannelRel>
			getCommercePriceListChannelRels(long commercePriceListId)
		throws PortalException {

		return getService().getCommercePriceListChannelRels(
			commercePriceListId);
	}

	public static List<CommercePriceListChannelRel>
			getCommercePriceListChannelRels(
				long commercePriceListId, int start, int end,
				OrderByComparator<CommercePriceListChannelRel>
					orderByComparator)
		throws PortalException {

		return getService().getCommercePriceListChannelRels(
			commercePriceListId, start, end, orderByComparator);
	}

	public static List<CommercePriceListChannelRel>
		getCommercePriceListChannelRels(
			long commercePriceListId, String name, int start, int end) {

		return getService().getCommercePriceListChannelRels(
			commercePriceListId, name, start, end);
	}

	public static int getCommercePriceListChannelRelsCount(
			long commercePriceListId)
		throws PortalException {

		return getService().getCommercePriceListChannelRelsCount(
			commercePriceListId);
	}

	public static int getCommercePriceListChannelRelsCount(
		long commercePriceListId, String name) {

		return getService().getCommercePriceListChannelRelsCount(
			commercePriceListId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommercePriceListChannelRelService getService() {
		return _service;
	}

	public static void setService(CommercePriceListChannelRelService service) {
		_service = service;
	}

	private static volatile CommercePriceListChannelRelService _service;

}