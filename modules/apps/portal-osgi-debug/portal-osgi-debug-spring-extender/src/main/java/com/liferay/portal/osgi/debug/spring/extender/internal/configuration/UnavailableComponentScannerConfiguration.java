/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.spring.extender.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Shuyang Zhou
 */
@ExtendedObjectClassDefinition(category = "module-container")
@Meta.OCD(
	id = "com.liferay.portal.osgi.debug.spring.extender.internal.configuration.UnavailableComponentScannerConfiguration",
	localization = "content/Language",
	name = "unavailable-component-scanner-configuration-name"
)
public interface UnavailableComponentScannerConfiguration {

	@Meta.AD(
		deflt = "-1",
		description = "unavailable-component-scanning-interval-help",
		name = "unavailable-component-scanning-interval", required = false
	)
	public long unavailableComponentScanningInterval();

}