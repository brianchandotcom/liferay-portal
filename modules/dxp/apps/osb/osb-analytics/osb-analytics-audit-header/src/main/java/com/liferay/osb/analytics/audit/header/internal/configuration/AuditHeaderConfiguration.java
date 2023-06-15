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

package com.liferay.osb.analytics.audit.header.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alvaro Saugar
 */
@ExtendedObjectClassDefinition(category = "audit")
@Meta.OCD(
	id = "com.liferay.osb.analytics.audit.header.internal.configuration.AuditHeaderConfiguration",
	localization = "content/Language", name = "audit-header-configuration-name"
)
public interface AuditHeaderConfiguration {

	@Meta.AD(
		deflt = "false", description = "audit-header-malu-help",
		name = "audit-header-malu-enabled", required = false
	)
	public boolean enabledMALU();

	@Meta.AD(
		deflt = "false", description = "audit-header-apv-help",
		name = "audit-header-apv-enabled", required = false
	)
	public boolean enabledAPV();

	@Meta.AD(
		deflt = "false", description = "audit-header-scope-help",
		name = "audit-header-scope-enabled", required = false
	)
	public boolean enabledScope();

}