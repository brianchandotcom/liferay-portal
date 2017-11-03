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

package com.liferay.message.boards.internal.service;

import com.liferay.message.boards.kernel.model.MBBan;
import com.liferay.message.boards.kernel.service.MBBanService;
import com.liferay.message.boards.kernel.service.MBBanServiceWrapper;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularMBBanServiceWrapper extends MBBanServiceWrapper {

	public ModularMBBanServiceWrapper() {
		super(null);
	}

	public ModularMBBanServiceWrapper(MBBanService mbBanService) {
		super(mbBanService);
	}

	@Override
	public MBBan addBan(long banUserId, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBBan.class, _mbBanService.addBan(banUserId, serviceContext));
	}

	@Override
	public void deleteBan(long banUserId, ServiceContext serviceContext)
		throws PortalException {

		_mbBanService.deleteBan(banUserId, serviceContext);
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _mbBanService.getOSGiServiceIdentifier();
	}

	@Override
	public MBBanService getWrappedService() {
		return super.getWrappedService();
	}

	@Override
	public void setWrappedService(MBBanService mbBanService) {
		super.setWrappedService(mbBanService);
	}

	@Reference
	private com.liferay.message.boards.service.MBBanService _mbBanService;

}