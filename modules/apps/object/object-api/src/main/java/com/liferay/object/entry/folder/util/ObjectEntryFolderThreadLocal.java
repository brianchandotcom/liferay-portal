/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.entry.folder.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Adolfo Pérez
 */
public class ObjectEntryFolderThreadLocal {

	public static boolean isSkipSystemObjectEntryFolderProtection() {
		return _skipSystemObjectEntryFolderProtection.get();
	}

	public static SafeCloseable
		setSkipSystemObjectEntryFolderProtectionWithSafeCloseable(
			boolean skipSystemObjectEntryFolderProtection) {

		return _skipSystemObjectEntryFolderProtection.setWithSafeCloseable(
			skipSystemObjectEntryFolderProtection);
	}

	private static final CentralizedThreadLocal<Boolean>
		_skipSystemObjectEntryFolderProtection = new CentralizedThreadLocal<>(
			ObjectEntryFolderThreadLocal.class +
				"._skipSystemObjectEntryFolderProtection",
			() -> false);

}