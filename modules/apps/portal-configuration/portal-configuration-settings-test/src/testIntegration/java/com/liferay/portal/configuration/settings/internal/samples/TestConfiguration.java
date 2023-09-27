/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.settings.internal.samples;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.settings.internal.constants.SettingsLocatorTestConstants;

/**
 * @author Drew Brokke
 */
@ExtendedObjectClassDefinition(category = "other")
@Meta.OCD(id = SettingsLocatorTestConstants.TEST_CONFIGURATION_PID)
public interface TestConfiguration {

	@Meta.AD(
		deflt = SettingsLocatorTestConstants.TEST_DEFAULT_VALUE,
		name = "settings-locator-test-key", required = false
	)
	public String settingsLocatorTestKey();

	@Meta.AD(
		deflt = "variantKey", name = "factory-alternate-key", required = false
	)
	public String factoryAlternateKey();

}