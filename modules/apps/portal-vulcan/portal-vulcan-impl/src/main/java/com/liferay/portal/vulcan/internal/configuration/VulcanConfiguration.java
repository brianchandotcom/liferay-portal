/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Javier Gamarra
 */
@ExtendedObjectClassDefinition(
	category = "web-api", factoryInstanceLabelAttribute = "path",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.vulcan.internal.configuration.VulcanConfiguration",
	localization = "content/Language", name = "headless-api-configuration-name"
)
public interface VulcanConfiguration {

	@Meta.AD(deflt = "/api", name = "path")
	public String path();

	@Meta.AD(deflt = "true", name = "graphql-api", required = false)
	public boolean graphQLEnabled();

	@Meta.AD(deflt = "true", name = "rest-api", required = false)
	public boolean restEnabled();

	@Meta.AD(name = "excluded-operation-ids", required = false)
	public String excludedOperationIds();

}