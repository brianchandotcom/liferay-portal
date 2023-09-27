/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.async.advice.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.messaging.DestinationNames;

/**
 * @author Preston Crary
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.portal.async.advice.internal.configuration.AsyncAdviceConfiguration",
	localization = "content/Language",
	name = "portal-async-advice-configuration-name"
)
public interface AsyncAdviceConfiguration {

	@Meta.AD(
		deflt = DestinationNames.ASYNC_SERVICE,
		name = "default-destination-name", required = false
	)
	public String defaultDestinationName();

	@Meta.AD(
		description = "target-class-names-to-destination-names-key-description",
		name = "target-class-names-to-destination-names", required = false
	)
	public String[] targetClassNamesToDestinationNames();

}