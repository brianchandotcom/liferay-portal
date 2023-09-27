/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.walkthrough.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Matuzalem Teles
 */
@ExtendedObjectClassDefinition(
	category = "frontend-walkthrough",
	scope = ExtendedObjectClassDefinition.Scope.GROUP, strictScope = true
)
@Meta.OCD(
	id = "com.liferay.frontend.js.walkthrough.web.internal.configuration.WalkthroughConfiguration",
	localization = "content/Language", name = "walkthrough-configuration-name"
)
public @interface WalkthroughConfiguration {

	@Meta.AD(deflt = "false", name = "enable-walkthrough", required = false)
	public boolean enabled();

	@Meta.AD(name = "steps-walkthrough", required = false)
	public String steps();

}