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
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Prathima Shreenath
 */
@ExtendedObjectClassDefinition(
	category = "forms-and-workflow",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.portal.reports.engine.console.configuration.ReportsGroupServiceEmailConfiguration",
	localization = "content/Language",
	name = "reports-group-service-configuration-name"
)
public interface ReportsGroupServiceEmailConfiguration {

	@Meta.AD(
		deflt = "${resource:com/liferay/portal/reports/engine/console/admin/dependencies/email_delivery_body.tmpl}",
		name = "email-delivery-body", required = false
	)
	public LocalizedValuesMap emailDeliveryBody();

	@Meta.AD(
		deflt = "${resource:com/liferay/portal/reports/engine/console/admin/dependencies/email_delivery_subject.tmpl}",
		name = "email-delivery-subject", required = false
	)
	public LocalizedValuesMap emailDeliverySubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/portal/reports/engine/console/admin/dependencies/email_notifications_body.tmpl}",
		name = "email-notifications-body", required = false
	)
	public LocalizedValuesMap emailNotificationsBody();

	@Meta.AD(
		deflt = "${resource:com/liferay/portal/reports/engine/console/admin/dependencies/email_notifications_subject.tmpl}",
		name = "email-notifications-subject", required = false
	)
	public LocalizedValuesMap emailNotificationsSubject();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.address}",
		name = "email-from-address", required = false
	)
	public String emailFromAddress();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/admin.email.from.name}",
		name = "email-from-name", required = false
	)
	public String emailFromName();

}