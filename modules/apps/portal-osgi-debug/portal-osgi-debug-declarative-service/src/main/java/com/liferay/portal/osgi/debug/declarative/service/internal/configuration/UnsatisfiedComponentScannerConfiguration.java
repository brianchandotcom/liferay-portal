/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.declarative.service.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tina Tian
 */
@ExtendedObjectClassDefinition(category = "module-container")
@Meta.OCD(
	id = "com.liferay.portal.osgi.debug.declarative.service.internal.configuration.UnsatisfiedComponentScannerConfiguration",
	localization = "content/Language",
	name = "unsatisfied-component-scanner-configuration-name"
)
public interface UnsatisfiedComponentScannerConfiguration {

	@Meta.AD(
		deflt = "-1",
		description = "unsatisfied-component-scanning-interval-help",
		name = "unsatisfied-component-scanning-interval", required = false
	)
	public int unsatisfiedComponentScanningInterval();

}