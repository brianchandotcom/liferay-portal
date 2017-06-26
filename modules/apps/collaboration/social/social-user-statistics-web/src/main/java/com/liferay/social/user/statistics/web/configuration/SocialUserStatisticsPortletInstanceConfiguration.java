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

package com.liferay.social.user.statistics.web.configuration;

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
	id = "com.liferay.social.user.statistics.web.configuration.SocialUserStatisticsPortletInstanceConfiguration",
	localization = "content/Language",
	name = "social-user-statistics-portlet-instance-configuration-name"
)
public interface SocialUserStatisticsPortletInstanceConfiguration {

	@Meta.AD(deflt = "user.achievements", required = false)
	public String[] displayActivityCounterName();

	@Meta.AD(deflt = "true", required = false)
	public boolean displayAdditionalActivityCounters();

	@Meta.AD(deflt = "true", required = false)
	public boolean rankByContribution();

	@Meta.AD(deflt = "true", required = false)
	public boolean rankByParticipation();

	@Meta.AD(deflt = "true", required = false)
	public boolean showHeaderText();

	@Meta.AD(deflt = "true", required = false)
	public boolean showTotals();

}