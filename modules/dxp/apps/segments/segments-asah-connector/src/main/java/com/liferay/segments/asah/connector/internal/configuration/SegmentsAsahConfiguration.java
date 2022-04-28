/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		name = "check-interval-name", required = false
	)
	public int checkInterval();

}