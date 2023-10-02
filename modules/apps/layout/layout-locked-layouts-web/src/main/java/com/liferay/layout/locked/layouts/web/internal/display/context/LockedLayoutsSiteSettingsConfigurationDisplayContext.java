/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.locked.layouts.web.internal.display.context;

import com.liferay.layout.configuration.LockedLayoutsGroupConfiguration;

/**
 * @author Mikel Lorza
 */
public class LockedLayoutsSiteSettingsConfigurationDisplayContext
	implements LockedLayoutsConfigurationDisplayContext {

	public LockedLayoutsSiteSettingsConfigurationDisplayContext(
		boolean hasConfiguration,
		LockedLayoutsGroupConfiguration lockedLayoutsGroupConfiguration) {

		_hasConfiguration = hasConfiguration;
		_lockedLayoutsGroupConfiguration = lockedLayoutsGroupConfiguration;
	}

	@Override
	public int getAutosaveMinutes() {
		return _lockedLayoutsGroupConfiguration.autosaveMinutes();
	}

	@Override
	public boolean hasConfiguration() {
		return _hasConfiguration;
	}

	@Override
	public boolean isAllowAutomaticUnlockingProcess() {
		return _lockedLayoutsGroupConfiguration.
			allowAutomaticUnlockingProcess();
	}

	private final boolean _hasConfiguration;
	private final LockedLayoutsGroupConfiguration
		_lockedLayoutsGroupConfiguration;

}