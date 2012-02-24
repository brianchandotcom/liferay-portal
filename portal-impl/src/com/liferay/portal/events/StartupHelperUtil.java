/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.events;

import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.verify.VerifyException;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 * @author Mate Thurzo
 */
public class StartupHelperUtil {

	public static StartupHelper getStartupHelper() {
		return _startupHelper;
	}

	public static boolean isUpgraded() {
		return getStartupHelper().isUpgraded();
	}

	public static boolean isVerified() {
		return getStartupHelper().isVerified();
	}

	public static void setDropIndexes(boolean dropIndexes) {
		getStartupHelper().setDropIndexes(dropIndexes);
	}

	public static void updateIndexes() {
		getStartupHelper().updateIndexes();
	}

	public static void upgradeProcess(int buildNumber, int newBuildNumber)
		throws UpgradeException {

		getStartupHelper().upgradeProcess(buildNumber, newBuildNumber);
	}

	public static void verifyProcess(boolean verified) throws VerifyException {
		getStartupHelper().verifyProcess(verified);
	}

	public void setStartupHelper(StartupHelper startupHelper) {
		_startupHelper = startupHelper;
	}

	private static StartupHelper _startupHelper;

}