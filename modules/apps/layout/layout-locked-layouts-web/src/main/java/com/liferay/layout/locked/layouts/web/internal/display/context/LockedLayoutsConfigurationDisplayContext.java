/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.locked.layouts.web.internal.display.context;

import com.liferay.layout.locked.layouts.web.internal.configuration.LockedLayoutsConfiguration;

/**
 * @author Lourdes Fernández Besada
 */
public class LockedLayoutsConfigurationDisplayContext {

	public LockedLayoutsConfigurationDisplayContext(
		boolean hasConfiguration,
		LockedLayoutsConfiguration lockedLayoutsConfiguration) {

		_hasConfiguration = hasConfiguration;
		_lockedLayoutsConfiguration = lockedLayoutsConfiguration;
	}

	public int getTimeWithoutAutosave() {
		return _lockedLayoutsConfiguration.timeWithoutAutosave();
	}

	public boolean hasConfiguration() {
		return _hasConfiguration;
	}

	public boolean isAllowAutomaticUnlockingProcess() {
		return _lockedLayoutsConfiguration.allowAutomaticUnlockingProcess();
	}

	private final boolean _hasConfiguration;
	private final LockedLayoutsConfiguration _lockedLayoutsConfiguration;

}