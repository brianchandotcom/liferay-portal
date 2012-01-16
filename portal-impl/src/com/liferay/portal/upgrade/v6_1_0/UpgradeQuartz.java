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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.NoSuchReleaseException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalServiceUtil;

/**
 * @author Shuyang Zhou
 */
public class UpgradeQuartz extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		int buildNumber = ReleaseLocalServiceUtil.getBuildNumberOrCreate();

		try {
			ReleaseLocalServiceUtil.getRelease(
				_SERVLET_CONTEXT_NAME, buildNumber);

			if (_log.isInfoEnabled()) {
				_log.info(
					"After portal upgrade process, please user Quartz Upgrade" +
					" plugin to import previously exported quartz jobs.");
			}
		}
		catch (NoSuchReleaseException nsre) {
			throw new Exception(
				"Can not find Quartz jobs exported mark. Please use Quartz " +
				"Upgrade plugin to export scheduled jobs, before proceeding " +
				"portal upgrade process");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeQuartz.class);

	private static final String _SERVLET_CONTEXT_NAME = "quartz_export";

}