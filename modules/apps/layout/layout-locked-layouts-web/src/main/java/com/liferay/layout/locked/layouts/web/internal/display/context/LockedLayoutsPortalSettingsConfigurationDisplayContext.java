/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.locked.layouts.web.internal.display.context;

import com.liferay.layout.locked.layouts.web.internal.configuration.LockedLayoutsCompanyConfiguration;

/**
 * @author Lourdes Fernández Besada
 */
public class LockedLayoutsPortalSettingsConfigurationDisplayContext
	implements LockedLayoutsConfigurationDisplayContext {

	public LockedLayoutsPortalSettingsConfigurationDisplayContext(
		boolean hasConfiguration,
		LockedLayoutsCompanyConfiguration lockedLayoutsCompanyConfiguration) {

		_hasConfiguration = hasConfiguration;
		_lockedLayoutsCompanyConfiguration = lockedLayoutsCompanyConfiguration;
	}

	@Override
	public int getTimeWithoutAutosave() {
		return _lockedLayoutsCompanyConfiguration.timeWithoutAutosave();
	}

	@Override
	public boolean hasConfiguration() {
		return _hasConfiguration;
	}

	@Override
	public boolean isAllowAutomaticUnlockingProcess() {
		return _lockedLayoutsCompanyConfiguration.
			allowAutomaticUnlockingProcess();
	}

	private final boolean _hasConfiguration;
	private final LockedLayoutsCompanyConfiguration
		_lockedLayoutsCompanyConfiguration;

}