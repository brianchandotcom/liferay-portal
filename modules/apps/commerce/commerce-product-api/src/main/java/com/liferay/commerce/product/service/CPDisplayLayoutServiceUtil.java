/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for CPDisplayLayout. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPDisplayLayoutServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDisplayLayoutService
 * @generated
 */
public class CPDisplayLayoutServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPDisplayLayoutServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPDisplayLayout addCPDisplayLayout(
			long groupId, Class<?> clazz, long classPK, String layoutUuid)
		throws PortalException {

		return getService().addCPDisplayLayout(
			groupId, clazz, classPK, layoutUuid);
	}

	public static void deleteCPDisplayLayout(long cpDisplayLayoutId)
		throws PortalException {

		getService().deleteCPDisplayLayout(cpDisplayLayoutId);
	}

	public static CPDisplayLayout fetchCPDisplayLayout(long cpDisplayLayoutId)
		throws PortalException {

		return getService().fetchCPDisplayLayout(cpDisplayLayoutId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CPDisplayLayout> searchCPDisplayLayout(
				long companyId, long groupId, String className, String keywords,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchCPDisplayLayout(
			companyId, groupId, className, keywords, start, end, sort);
	}

	public static CPDisplayLayout updateCPDisplayLayout(
			long cpDisplayLayoutId, long classPK, String layoutUuid)
		throws PortalException {

		return getService().updateCPDisplayLayout(
			cpDisplayLayoutId, classPK, layoutUuid);
	}

	public static CPDisplayLayoutService getService() {
		return _service;
	}

	private static volatile CPDisplayLayoutService _service;

}