/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.taglib.internal.permission.util;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.saved.content.security.permission.SavedContentPermission;

/**
 * @author Alicia García
 */
public class SavedContentPermissionUtil {

	public static SavedContentPermission getSavedContentPermission() {
		return _savedContentPermissionSnapshot.get();
	}

	private static final Snapshot<SavedContentPermission>
		_savedContentPermissionSnapshot = new Snapshot<>(
			SavedContentPermissionUtil.class, SavedContentPermission.class);

}