/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.settings;

import java.util.Set;

/**
 * @author Iván Zaera
 */
public interface SettingsDescriptor {

	public Set<String> getAllKeys();

	public Set<String> getMultiValuedKeys();

}