/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.lodash.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Julien Castelain
 * @review
 */
@ExtendedObjectClassDefinition(category = "third-party")
@Meta.OCD(
	description = "frontend-js-lodash-description",
	id = "com.liferay.frontend.js.lodash.web.internal.configuration.JSLodashConfiguration",
	localization = "content/Language",
	name = "frontend-js-lodash-configuration-name"
)
public interface JSLodashConfiguration {

	/**
	 * Set this to <code>true</code> to enable Lodash usage.
	 *
	 * @return <code>true</code> if Lodash is enabled.
	 * @review
	 */
	@Meta.AD(deflt = "false", name = "enable-lodash", required = false)
	public boolean enableLodash();

}