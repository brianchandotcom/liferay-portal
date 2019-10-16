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

package com.liferay.segments.asah.connector.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author David Arques
 */
@ExtendedObjectClassDefinition(category = "segments")
@Meta.OCD(
	id = "com.liferay.segments.asah.connector.internal.configuration.SegmentsAsahConfiguration",
	localization = "content/Language",
	name = "segments-asah-connector-configuration-name"
)
public interface SegmentsAsahConfiguration {

	@Meta.AD(
		deflt = "86400",
		description = "anonymous-user-segments-cache-expiration-time-description",
		name = "anonymous-user-segments-cache-expiration-time-name",
		required = false
	)
	public int anonymousUserSegmentsCacheExpirationTime();

	@Meta.AD(
		deflt = "86400",
		description = "interest-terms-cache-expiration-time-description",
		name = "interest-terms-cache-expiration-time-name", required = false
	)
	public int interestTermsCacheExpirationTime();

	@Meta.AD(
		deflt = "15", description = "check-interval-description",
		name = "check-interval", required = false
	)
	public int checkInterval();

}