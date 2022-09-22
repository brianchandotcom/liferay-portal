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

package com.liferay.portal.upgrade.release;

import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Shuyang Zhou
 */
public class ReleaseRenamingUpgradeStep implements UpgradeStep {

	public ReleaseRenamingUpgradeStep(
		String newServletContextName, String oldServletContextName,
		ReleaseLocalService releaseLocalService) {

		_newServletContextName = newServletContextName;
		_oldServletContextName = oldServletContextName;
		_releaseLocalService = releaseLocalService;
	}

	@Override
	public void upgrade(DBProcessContext dbProcessContext) {
		Release oldRelease = _releaseLocalService.fetchRelease(
			_oldServletContextName);

		if (oldRelease != null) {
			_releaseLocalService.addRelease(
				_newServletContextName, oldRelease.getSchemaVersion());

			_releaseLocalService.deleteRelease(oldRelease);
		}
	}

	private final String _newServletContextName;
	private final String _oldServletContextName;
	private final ReleaseLocalService _releaseLocalService;

}