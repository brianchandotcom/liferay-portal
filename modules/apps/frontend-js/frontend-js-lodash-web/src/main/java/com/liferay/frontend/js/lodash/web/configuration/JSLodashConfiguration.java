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

package com.liferay.frontend.js.lodash.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Julien Castelain
 */
@ExtendedObjectClassDefinition(category = "third-party")
@Meta.OCD(
	description = "frontend-js-lodash-description",
	id = "com.liferay.frontend.js.lodash.web.configuration.JSLodashConfiguration",
	localization = "content/Language",
	name = "frontend-js-lodash-configuration-name"
)
public interface JSLodashConfiguration {

	/**
	 * Set this to <code>true</code> to enable lodash usage.
	 *
	 * @return <code>true</code> if lodash is enabled.
	 * @review
	 */
	@Meta.AD(deflt = "false", name = "enable-lodash", required = false)
	public boolean enableLodash();

}