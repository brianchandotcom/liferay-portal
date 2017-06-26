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

package com.liferay.social.group.statistics.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Iván Zaera
 */
@ExtendedObjectClassDefinition(
	category = "collaboration",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.social.group.statistics.web.configuration.SocialGroupStatisticsPortletInstanceConfiguration",
	localization = "content/Language",
	name = "social-group-statistics-portlet-instance-configuration-name"
)
public interface SocialGroupStatisticsPortletInstanceConfiguration {

	@Meta.AD(required = false)
	public String[] chartType();

	@Meta.AD(required = false)
	public String[] chartWidth();

	@Meta.AD(required = false)
	public String[] dataRange();

	@Meta.AD(required = false)
	public String[] displayActivityCounterName();

}