/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Prathima Shreenath
 */
@ExtendedObjectClassDefinition(
	category = "reports", scope = ExtendedObjectClassDefinition.Scope.GROUP
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