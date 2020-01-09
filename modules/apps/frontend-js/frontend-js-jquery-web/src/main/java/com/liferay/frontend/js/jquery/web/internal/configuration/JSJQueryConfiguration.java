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

package com.liferay.frontend.js.jquery.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Julien Castelain
 * @review
 */
@ExtendedObjectClassDefinition(category = "third-party")
@Meta.OCD(
	description = "frontend-js-jquery-description",
	id = "com.liferay.frontend.js.jquery.web.internal.configuration.JSJQueryConfiguration",
	localization = "content/Language",
	name = "frontend-js-jquery-configuration-name"
)
public interface JSJQueryConfiguration {

	/**
	 * Set this to <code>true</code> to enable JQuery usage.
	 *
	 * @return <code>true</code> if JQuery is enabled.
	 * @review
	 */
	@Meta.AD(deflt = "false", name = "enable-jquery", required = false)
	public boolean enableJQuery();

}