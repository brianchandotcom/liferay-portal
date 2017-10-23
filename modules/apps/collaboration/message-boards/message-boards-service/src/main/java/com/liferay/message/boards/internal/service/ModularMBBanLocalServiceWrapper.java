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
import com.liferay.message.boards.kernel.service.MBBanLocalService;
import com.liferay.message.boards.kernel.service.MBBanLocalServiceWrapper;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularMBBanLocalServiceWrapper extends MBBanLocalServiceWrapper {

	public ModularMBBanLocalServiceWrapper() {
		super(null);
	}

	public ModularMBBanLocalServiceWrapper(
		MBBanLocalService mbBanLocalService) {

		super(mbBanLocalService);
	}

	@Override
	public MBBan addBan(
			long userId, long banUserId, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBBan.class,
			_mbBanLocalService.addBan(userId, banUserId, serviceContext));
	}

	@Override
	public void checkBan(long groupId, long banUserId) throws PortalException {
		_mbBanLocalService.checkBan(groupId, banUserId);
	}

	@Override
	public void deleteBan(long banId) throws PortalException {
		_mbBanLocalService.deleteBan(banId);
	}

	@Override
	public void deleteBan(long banUserId, ServiceContext serviceContext) {
		_mbBanLocalService.deleteBan(banUserId, serviceContext);
	}

	@Override
	public void deleteBan(MBBan ban) {
		_mbBanLocalService.deleteBan(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBBan.class, ban));
	}

	@Override
	public void deleteBansByBanUserId(long banUserId) {
		_mbBanLocalService.deleteBansByBanUserId(banUserId);
	}

	@Override
	public void deleteBansByGroupId(long groupId) {
		_mbBanLocalService.deleteBansByGroupId(groupId);
	}

	@Override
	public void expireBans() {
		_mbBanLocalService.expireBans();
	}

	@Override
	public List<MBBan> getBans(long groupId, int start, int end) {
		return ModelAdapterUtil.adapt(
			MBBan.class, _mbBanLocalService.getBans(groupId, start, end));
	}

	@Override
	public int getBansCount(long groupId) {
		return _mbBanLocalService.getBansCount(groupId);
	}

	@Override
	public boolean hasBan(long groupId, long banUserId) {
		return _mbBanLocalService.hasBan(groupId, banUserId);
	}

	@Reference
	private com.liferay.message.boards.service.MBBanLocalService
		_mbBanLocalService;

}