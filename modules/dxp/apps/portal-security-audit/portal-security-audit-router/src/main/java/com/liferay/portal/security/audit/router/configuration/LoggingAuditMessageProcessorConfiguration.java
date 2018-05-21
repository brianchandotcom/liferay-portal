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

package com.liferay.portal.security.audit.router.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Brian Greenwald
 * @author Prathima Shreenath
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.portal.security.audit.router.configuration.LoggingAuditMessageProcessorConfiguration",
	localization = "content/Language",
	name = "logging-audit-message-processor-configuration-name"
)
public interface LoggingAuditMessageProcessorConfiguration {

	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

	@Meta.AD(
		deflt = "CSV", name = "log-message-format",
		optionValues = {"CSV", "JSON"}, required = false
	)
	public String logMessageFormat();

	@Meta.AD(deflt = "false", name = "output-to-console", required = false)
	public boolean outputToConsole();

}