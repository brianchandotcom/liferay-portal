/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.locked.layouts.web.internal.display.context;

/**
 * @author Lourdes Fernández Besada
 */
public interface LockedLayoutsConfigurationDisplayContext {

	public int getAutosaveMinutes();

	public boolean hasConfiguration();

	public boolean isAllowAutomaticUnlockingProcess();

}