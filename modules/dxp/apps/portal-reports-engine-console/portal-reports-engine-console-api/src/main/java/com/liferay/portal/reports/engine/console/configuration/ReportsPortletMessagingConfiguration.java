/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Prathima Shreenath
 */
@ExtendedObjectClassDefinition(category = "reports")
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