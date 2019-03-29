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

package com.liferay.portal.async.advice.internal;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.messaging.DestinationNames;

/**
 * @author Preston Crary
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.portal.async.advice.internal.AsyncAdviceConfiguration",
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