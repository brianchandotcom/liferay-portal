/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.configuration;

import com.liferay.portal.kernel.settings.Settings;
import com.liferay.wiki.configuration.definition.WikiGroupServiceConfigurationOverrideImpl;

/**
 * @author Iván Zaera
 */
@Settings.OverrideClass(WikiGroupServiceConfigurationOverrideImpl.class)
public interface WikiGroupServiceOverriddenConfiguration
	extends WikiGroupServiceConfiguration,
			WikiGroupServiceConfigurationOverride {
}