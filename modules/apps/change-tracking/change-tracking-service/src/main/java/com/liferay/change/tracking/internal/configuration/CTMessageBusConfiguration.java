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

package com.liferay.change.tracking.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.messaging.DestinationNames;

/**
 * @author Preston Crary
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.change.tracking.internal.configuration.CTMessageBusConfiguration",
	localization = "content/Language",
	name = "publications-portal-message-bus-configuration-name"
)
public interface CTMessageBusConfiguration {

	@Meta.AD(
		deflt = DestinationNames.SUBSCRIPTION_SENDER,
		name = "intercepted-destination-names", required = false
	)
	public String[] interceptedDestinationNames();

}