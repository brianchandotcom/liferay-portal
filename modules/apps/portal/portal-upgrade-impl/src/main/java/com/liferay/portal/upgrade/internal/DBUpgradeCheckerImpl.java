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

package com.liferay.portal.upgrade.internal;

import com.liferay.portal.kernel.upgrade.util.DBUpgradeChecker;
import com.liferay.portal.osgi.debug.SystemChecker;
import com.liferay.portal.upgrade.internal.release.osgi.commands.ReleaseManagerOSGiCommands;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Ortiz
 */
@Component(service = {DBUpgradeChecker.class, DBUpgradeCheckerImpl.class})
public class DBUpgradeCheckerImpl implements DBUpgradeChecker {

	@Override
	public boolean check() {
		String check = _releaseManagerOSGiCommands.check();

		if (!check.isEmpty()) {
			return false;
		}

		check = _systemChecker.check();

		if (check.contains("UpgradeStepRegistrator")) {
			return false;
		}

		return true;
	}

	@Reference
	private volatile ReleaseManagerOSGiCommands _releaseManagerOSGiCommands;

	@Reference(
		target = "(component.name=com.liferay.portal.osgi.debug.declarative.service.internal.DeclarativeServiceUnsatisfiedComponentSystemChecker)"
	)
	private volatile SystemChecker _systemChecker;

}