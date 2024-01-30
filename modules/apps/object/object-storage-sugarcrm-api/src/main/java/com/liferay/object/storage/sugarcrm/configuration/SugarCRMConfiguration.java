/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.storage.sugarcrm.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Maurice Sepe
 */
@ExtendedObjectClassDefinition(
	category = "third-party", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.object.storage.sugarcrm.configuration.SugarCRMConfiguration",
	localization = "content/Language", name = "sugarcrm-configuration-name"
)
public interface SugarCRMConfiguration {

	@Meta.AD(name = "access-token-url", required = false)
	public String accessTokenURL();

	@Meta.AD(name = "base-url", required = false)
	public String baseURL();

	@Meta.AD(name = "client-id", required = false)
	public String clientId();

	@Meta.AD(name = "grant-type", required = false)
	public String grantType();

	@Meta.AD(name = "password", required = false, type = Meta.Type.Password)
	public String password();

	@Meta.AD(name = "username", required = false)
	public String username();

}