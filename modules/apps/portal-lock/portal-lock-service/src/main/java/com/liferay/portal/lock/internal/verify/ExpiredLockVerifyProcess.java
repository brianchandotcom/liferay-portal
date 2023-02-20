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

package com.liferay.portal.lock.internal.verify;

import com.liferay.portal.lock.service.LockLocalService;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "verify.process.name=com.liferay.portal.lock.service",
	service = VerifyProcess.class
)
public class ExpiredLockVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		_lockLocalService.clear();
	}

	@Reference
	private LockLocalService _lockLocalService;

}