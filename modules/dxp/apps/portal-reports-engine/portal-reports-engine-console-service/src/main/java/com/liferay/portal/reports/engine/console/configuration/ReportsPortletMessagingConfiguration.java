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

package com.liferay.portal.reports.engine.console.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Prathima Shreenath
 */
@ExtendedObjectClassDefinition(category = "forms-and-workflow")
@Meta.OCD(
	id = "com.liferay.portal.reports.engine.console.configuration.ReportsPortletMessagingConfiguration",
	localization = "content/Language",
	name = "reports-portlet-configuration-name"
)
public interface ReportsPortletMessagingConfiguration {

	@Meta.AD(
		deflt = "200", name = "report-message-queue-size", required = false
	)
	public int reportMessageQueueSize();

	@Meta.AD(deflt = "true", name = "enabled", required = false)
	public boolean enabled();

}