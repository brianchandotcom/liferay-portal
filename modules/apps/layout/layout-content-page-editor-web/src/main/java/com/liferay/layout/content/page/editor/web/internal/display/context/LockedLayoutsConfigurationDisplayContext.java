/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.display.context;

import com.liferay.layout.content.page.editor.web.internal.configuration.LockedLayoutsConfiguration;

/**
 * @author Lourdes Fernández Besada
 */
public class LockedLayoutsConfigurationDisplayContext {

	public LockedLayoutsConfigurationDisplayContext(
		LockedLayoutsConfiguration lockedLayoutsConfiguration) {

		_lockedLayoutsConfiguration = lockedLayoutsConfiguration;
	}

	public int getLockReviewFrequency() {
		return _lockedLayoutsConfiguration.lockReviewFrequency();
	}

	public int getTimeWithoutAutosave() {
		return _lockedLayoutsConfiguration.timeWithoutAutosave();
	}

	public boolean isAllowAutomaticUnlockingProcess() {
		return _lockedLayoutsConfiguration.allowAutomaticUnlockingProcess();
	}

	private final LockedLayoutsConfiguration _lockedLayoutsConfiguration;

}