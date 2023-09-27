/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging;

import com.liferay.osgi.util.service.Snapshot;

/**
 * @author Máté Thurzó
 */
public class StagingGroupHelperUtil {

	public static StagingGroupHelper getStagingGroupHelper() {
		return _stagingGroupHelperSnapshot.get();
	}

	private static final Snapshot<StagingGroupHelper>
		_stagingGroupHelperSnapshot = new Snapshot<>(
			StagingGroupHelperUtil.class, StagingGroupHelper.class);

}