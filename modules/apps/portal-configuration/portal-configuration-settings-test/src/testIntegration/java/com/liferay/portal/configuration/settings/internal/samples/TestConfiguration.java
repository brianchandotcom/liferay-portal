/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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