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

package com.liferay.portlet.messageboards.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBBanService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBBanService
 * @generated
 */
@ProviderType
public class MBBanServiceWrapper implements MBBanService,
	ServiceWrapper<MBBanService> {
	public MBBanServiceWrapper(MBBanService mbBanService) {
		_mbBanService = mbBanService;
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBBan addBan(
		long banUserId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbBanService.addBan(banUserId, serviceContext);
	}

	@Override
	public void deleteBan(long banUserId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbBanService.deleteBan(banUserId, serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _mbBanService.getOSGiServiceIdentifier();
	}

	@Override
	public MBBanService getWrappedService() {
		return _mbBanService;
	}

	@Override
	public void setWrappedService(MBBanService mbBanService) {
		_mbBanService = mbBanService;
	}

	private MBBanService _mbBanService;
}