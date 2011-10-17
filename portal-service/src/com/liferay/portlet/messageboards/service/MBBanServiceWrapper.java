/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link MBBanService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBBanService
 * @generated
 */
public class MBBanServiceWrapper implements MBBanService,
	ServiceWrapper<MBBanService> {
	public MBBanServiceWrapper(MBBanService mbBanService) {
		_mbBanService = mbBanService;
	}

	public com.liferay.portlet.messageboards.model.MBBan addBan(
		long banUserId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbBanService.addBan(banUserId, serviceContext);
	}

	public void deleteBan(long banUserId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbBanService.deleteBan(banUserId, serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public MBBanService getWrappedMBBanService() {
		return _mbBanService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedMBBanService(MBBanService mbBanService) {
		_mbBanService = mbBanService;
	}

	public MBBanService getWrappedService() {
		return _mbBanService;
	}

	public void setWrappedService(MBBanService mbBanService) {
		_mbBanService = mbBanService;
	}

	private MBBanService _mbBanService;
}