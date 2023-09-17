/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.upgrade.internal;

import com.liferay.portal.kernel.upgrade.ReleaseManager;
import com.liferay.portal.osgi.debug.SystemChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = SystemChecker.class)
public class PendingUpgradeSystemChecker implements SystemChecker {

	@Override
	public String check() {
		return PendingUpgradeUtil.scan(_releaseManager);
	}

	@Override
	public String getName() {
		return "Pending Upgrade Scanner";
	}

	@Override
	public String getOSGiCommand() {
		return "upgrade:pending";
	}

	@Override
	public String toString() {
		return getName();
	}

	@Reference
	private ReleaseManager _releaseManager;

}