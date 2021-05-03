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

package com.liferay.password.policies.admin.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jonathan McCann
 */
@ExtendedObjectClassDefinition(category = "users")
@Meta.OCD(
	id = "com.liferay.password.policies.admin.web.internal.configuration.PasswordPoliciesConfiguration",
	localization = "content/Language",
	name = "password-policies-configuration-name"
)
public interface PasswordPoliciesConfiguration {

	@Meta.AD(
		deflt = "43200, 86400, 172800, 259200, 345600, 432000, 518400, 604800, 1209600",
		description = "expiration-warning-time-durations-description",
		name = "expiration-warning-time-durations", required = false
	)
	public long[] expirationWarningTimeDurations();

	@Meta.AD(
		deflt = "300, 600, 1800, 3600, 7200, 10800, 21600, 43200, 86400, 172800, 259200, 345600, 432000, 518400, 604800",
		description = "lockout-durations-description",
		name = "lockout-durations", required = false
	)
	public long[] lockoutDurations();

	@Meta.AD(
		deflt = "1209600, 1814400, 2419200, 4838400, 7862400, 15724800, 31449600",
		description = "maximum-age-durations-description",
		name = "maximum-age-durations", required = false
	)
	public long[] maximumAgeDurations();

	@Meta.AD(
		deflt = "300, 600, 1800, 3600, 7200, 10800, 21600, 43200, 86400, 172800, 259200, 345600, 432000, 518400, 604800",
		description = "minimum-age-durations-description",
		name = "minimum-age-durations", required = false
	)
	public long[] minimumAgeDurations();

	@Meta.AD(
		deflt = "300, 600, 1800, 3600, 7200, 10800, 21600, 43200, 86400, 172800, 259200, 345600, 432000, 518400, 604800",
		description = "reset-ticket-max-age-durations-description",
		name = "reset-ticket-max-age-durations", required = false
	)
	public long[] resetTicketMaxAgeDurations();

	@Meta.AD(
		deflt = "300, 600, 1800, 3600, 7200, 10800, 21600, 43200, 86400, 172800, 259200, 345600, 432000, 518400, 604800",
		description = "reset-failure-durations-description",
		name = "reset-failure-durations", required = false
	)
	public long[] resetFailureDurations();

}