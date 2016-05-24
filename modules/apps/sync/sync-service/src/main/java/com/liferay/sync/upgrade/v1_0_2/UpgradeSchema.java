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

package com.liferay.sync.upgrade.v1_0_2;

import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Shinn Lok
 */
public class UpgradeSchema extends UpgradeProcess {

	public UpgradeSchema(ReleaseLocalService releaseLocalService) {
		_releaseLocalService = releaseLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String template = StringUtil.read(
			UpgradeSchema.class.getResourceAsStream("dependencies/update.sql"));

		runSQLTemplateString(template, false, false);

		Release release = _releaseLocalService.fetchRelease(
			"com.liferay.sync.service");

		release.setVerified(false);

		_releaseLocalService.updateRelease(release);
	}

	private final ReleaseLocalService _releaseLocalService;

}