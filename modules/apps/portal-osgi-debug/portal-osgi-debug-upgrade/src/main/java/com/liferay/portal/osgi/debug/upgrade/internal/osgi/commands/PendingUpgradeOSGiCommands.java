/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.upgrade.internal.osgi.commands;

import com.liferay.portal.kernel.upgrade.ReleaseManager;
import com.liferay.portal.osgi.debug.upgrade.internal.PendingUpgradeUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = {"osgi.command.function=pending", "osgi.command.scope=upgrade"},
	service = PendingUpgradeOSGiCommands.class
)
public class PendingUpgradeOSGiCommands {

	public void pending() {
		System.out.println(PendingUpgradeUtil.scan(_releaseManager));
	}

	@Reference
	private ReleaseManager _releaseManager;

}