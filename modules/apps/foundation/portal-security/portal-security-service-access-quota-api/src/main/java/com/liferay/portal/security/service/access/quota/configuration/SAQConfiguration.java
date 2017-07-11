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

package com.liferay.portal.security.service.access.quota.configuration;

import aQute.bnd.annotation.ProviderType;
import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Stian Sigvartsen
 */
@ExtendedObjectClassDefinition(
	category = "foundation", factoryInstanceLabelAttribute = "serviceSignature"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.security.service.access.quota.configuration.SAQConfiguration",
	localization = "content/Language", name = "saq-configuration-name"
)
@ProviderType
public interface SAQConfiguration {

	@Meta.AD(
		deflt = "", description = "saq-configuration-service-signature",
		required = false
	)
	public String serviceSignature();

	@Meta.AD(
		deflt = "60", description = "saq-configuration-service-max", min = "1",
		required = true
	)
	public int max();

	@Meta.AD(
		deflt = "60000",
		description = "saq-configuration-service-interval-millis",
		required = true
	)
	public long intervalMillis();

	@Meta.AD(
		deflt = "", description = "saq-configuration-service-metric",
		required = false
	)
	public String[] metrics();

}