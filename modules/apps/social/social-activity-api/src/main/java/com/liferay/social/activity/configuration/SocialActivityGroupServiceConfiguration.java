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

package com.liferay.social.activity.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Roberto Díaz
 */
@ConfigurationAdmin(category = "collaboration")
@Meta.OCD(
	id = "com.liferay.social.activity.configuration.SocialActivityGroupServiceConfiguration"
)
public interface SocialActivityGroupServiceConfiguration {

	@Meta.AD(deflt = "0|1|2|3|4|5|10|20|50|100", required = false)
	public String[] contributionIncrements();

	@Meta.AD(deflt = "0|1|2|3|4|5|10|20", required = false)
	public String[] contributionLimitValues();

	@Meta.AD(deflt = "0|1|2|3|4|5|10|20|50|100", required = false)
	public String[] participationIncrements();

	@Meta.AD(deflt = "0|1|2|3|4|5|10|20", required = false)
	public String[] participationLimitValues();

}