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

package com.liferay.announcements.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Roberto Díaz
 */
@ExtendedObjectClassDefinition(
	category = "announcements",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.announcements.web.internal.configuration.AnnouncementsPortletInstanceConfiguration",
	localization = "content/Language",
	name = "announcements-portlet-instance-configuration-name"
)
public interface AnnouncementsPortletInstanceConfiguration {

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/search.container.page.default.delta}",
		description = "maximum-items-to-display", name = "page-delta",
		required = false
	)
	public String pageDelta();

}