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

package com.liferay.message.boards.service;

import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBSuspiciousActivityService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBSuspiciousActivityService
 * @generated
 */
public class MBSuspiciousActivityServiceWrapper
	implements MBSuspiciousActivityService,
			   ServiceWrapper<MBSuspiciousActivityService> {

	public MBSuspiciousActivityServiceWrapper() {
		this(null);
	}

	public MBSuspiciousActivityServiceWrapper(
		MBSuspiciousActivityService mbSuspiciousActivityService) {

		_mbSuspiciousActivityService = mbSuspiciousActivityService;
	}

	@Override
	public MBSuspiciousActivity addOrUpdateSuspiciousActivity(
			long userId, long messageId, String description, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbSuspiciousActivityService.addOrUpdateSuspiciousActivity(
			userId, messageId, description, type);
	}

	@Override
	public int countAll() {
		return _mbSuspiciousActivityService.countAll();
	}

	@Override
	public java.util.List<MBSuspiciousActivity> findAll() {
		return _mbSuspiciousActivityService.findAll();
	}

	@Override
	public MBSuspiciousActivity findByPrimaryKey(long suspiciousActivityId)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return _mbSuspiciousActivityService.findByPrimaryKey(
			suspiciousActivityId);
	}

	@Override
	public MBSuspiciousActivity findByU_M(long userId, long messageId)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return _mbSuspiciousActivityService.findByU_M(userId, messageId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mbSuspiciousActivityService.getOSGiServiceIdentifier();
	}

	@Override
	public MBSuspiciousActivity remove(long suspiciousActivityId)
		throws com.liferay.message.boards.exception.
			NoSuchSuspiciousActivityException {

		return _mbSuspiciousActivityService.remove(suspiciousActivityId);
	}

	@Override
	public MBSuspiciousActivityService getWrappedService() {
		return _mbSuspiciousActivityService;
	}

	@Override
	public void setWrappedService(
		MBSuspiciousActivityService mbSuspiciousActivityService) {

		_mbSuspiciousActivityService = mbSuspiciousActivityService;
	}

	private MBSuspiciousActivityService _mbSuspiciousActivityService;

}