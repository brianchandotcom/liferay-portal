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

package com.liferay.amazon.rankings.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Jorge Ferrer
 */
@ConfigurationAdmin(category = "other")
@Meta.OCD(
	id = "com.liferay.amazon.rankings.web.configuration.AmazonRankingsConfiguration"
)
public interface AmazonRankingsConfiguration {

	@Meta.AD(required = false)
	public String amazonAccessKeyId();

	@Meta.AD(required = false)
	public String amazonAssociateTag();

	@Meta.AD(required = false)
	public String amazonSecretAccessKey();

	@Meta.AD(
		deflt = "0066620996|0131412752|0201633612|0310205719|0310241448",
		required = false
	)
	public String[] isbns();

}