/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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