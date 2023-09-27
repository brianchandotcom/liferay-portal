/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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