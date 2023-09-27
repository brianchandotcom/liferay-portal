/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.release;

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
	public void upgrade() {
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