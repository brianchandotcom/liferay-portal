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

package com.liferay.commerce.punchout.portal.security.auto.login.internal.module.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jaclyn Ong
 */
@ExtendedObjectClassDefinition(category = "api-authentication")
@Meta.OCD(
	id = "com.liferay.commerce.punchout.portal.security.auto.login.internal.module.configuration.PunchOutAccessTokenAutoLoginConfiguration",
	localization = "content/Language",
	name = "punch-out-access-token-auto-login-configuration-name"
)
public interface PunchOutAccessTokenAutoLoginConfiguration {

	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

}