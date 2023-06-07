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

package com.liferay.change.tracking.service;

import com.liferay.change.tracking.model.CTRemote;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CTRemote. This utility wraps
 * <code>com.liferay.change.tracking.service.impl.CTRemoteServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see CTRemoteService
 * @generated
 */
public class CTRemoteServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTRemoteServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CTRemote addCTRemote(
			String name, String description, String url)
		throws PortalException {

		return getService().addCTRemote(name, description, url);
	}

	public static CTRemote deleteCTRemote(CTRemote ctRemote)
		throws PortalException {

		return getService().deleteCTRemote(ctRemote);
	}

	public static CTRemote deleteCTRemote(long ctRemoteId)
		throws PortalException {

		return getService().deleteCTRemote(ctRemoteId);
	}

	public static List<CTRemote> getCTRemotes(
		String keywords, int start, int end,
		OrderByComparator<CTRemote> orderByComparator) {

		return getService().getCTRemotes(
			keywords, start, end, orderByComparator);
	}

	public static int getCTRemotesCount(String keywords) {
		return getService().getCTRemotesCount(keywords);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CTRemote updateCTRemote(
			long ctRemoteId, String name, String description, String url)
		throws PortalException {

		return getService().updateCTRemote(ctRemoteId, name, description, url);
	}

	public static CTRemoteService getService() {
		return _service;
	}

	public static void setService(CTRemoteService service) {
		_service = service;
	}

	private static volatile CTRemoteService _service;

}