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