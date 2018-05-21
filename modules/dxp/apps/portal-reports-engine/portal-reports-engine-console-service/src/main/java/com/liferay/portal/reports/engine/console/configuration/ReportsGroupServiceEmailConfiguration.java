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