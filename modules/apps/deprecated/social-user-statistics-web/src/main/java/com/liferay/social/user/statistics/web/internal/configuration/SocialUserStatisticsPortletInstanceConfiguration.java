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

package com.liferay.social.user.statistics.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Iván Zaera
 */
@ExtendedObjectClassDefinition(
	category = "user-activity",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.social.user.statistics.web.internal.configuration.SocialUserStatisticsPortletInstanceConfiguration",
	localization = "content/Language",
	name = "social-user-statistics-portlet-instance-configuration-name"
)
public interface SocialUserStatisticsPortletInstanceConfiguration {

	@Meta.AD(
		deflt = "user.achievements", name = "display-activity-counter-name",
		required = false
	)
	public String[] displayActivityCounterName();

	@Meta.AD(
		deflt = "true", name = "display-additional-activity-counters",
		required = false
	)
	public boolean displayAdditionalActivityCounters();

	@Meta.AD(deflt = "true", name = "rank-by-contribution", required = false)
	public boolean rankByContribution();

	@Meta.AD(deflt = "true", name = "rank-by-participation", required = false)
	public boolean rankByParticipation();

	@Meta.AD(deflt = "true", name = "show-header-text", required = false)
	public boolean showHeaderText();

	@Meta.AD(deflt = "true", name = "show-totals", required = false)
	public boolean showTotals();

}