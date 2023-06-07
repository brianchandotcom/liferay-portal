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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CTRemoteService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTRemoteService
 * @generated
 */
public class CTRemoteServiceWrapper
	implements CTRemoteService, ServiceWrapper<CTRemoteService> {

	public CTRemoteServiceWrapper() {
		this(null);
	}

	public CTRemoteServiceWrapper(CTRemoteService ctRemoteService) {
		_ctRemoteService = ctRemoteService;
	}

	@Override
	public com.liferay.change.tracking.model.CTRemote addCTRemote(
			String name, String description, String url)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctRemoteService.addCTRemote(name, description, url);
	}

	@Override
	public com.liferay.change.tracking.model.CTRemote deleteCTRemote(
			com.liferay.change.tracking.model.CTRemote ctRemote)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctRemoteService.deleteCTRemote(ctRemote);
	}

	@Override
	public com.liferay.change.tracking.model.CTRemote deleteCTRemote(
			long ctRemoteId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctRemoteService.deleteCTRemote(ctRemoteId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTRemote>
		getCTRemotes(
			String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.change.tracking.model.CTRemote>
					orderByComparator) {

		return _ctRemoteService.getCTRemotes(
			keywords, start, end, orderByComparator);
	}

	@Override
	public int getCTRemotesCount(String keywords) {
		return _ctRemoteService.getCTRemotesCount(keywords);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctRemoteService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.change.tracking.model.CTRemote updateCTRemote(
			long ctRemoteId, String name, String description, String url)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctRemoteService.updateCTRemote(
			ctRemoteId, name, description, url);
	}

	@Override
	public CTRemoteService getWrappedService() {
		return _ctRemoteService;
	}

	@Override
	public void setWrappedService(CTRemoteService ctRemoteService) {
		_ctRemoteService = ctRemoteService;
	}

	private CTRemoteService _ctRemoteService;

}