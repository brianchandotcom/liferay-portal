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

package com.liferay.frontend.js.aui.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Iván Zaera Avellón
 */
@ExtendedObjectClassDefinition(category = "third-party", generateUI = false)
@Meta.OCD(
	description = "frontend-js-aui-configuration-description",
	id = "com.liferay.frontend.js.aui.web.internal.configuration.AUIConfiguration",
	localization = "content/Language",
	name = "frontend-js-aui-configuration-name"
)
public interface AUIConfiguration {

	/**
	 * Set this to <code>true</code> to enable AUI usage.
	 *
	 * @return <code>true</code> if AUI is enabled.
	 * @review
	 */
	@Meta.AD(
		deflt = "true", description = "enable-aui-description",
		name = "enable-aui", required = false
	)
	public boolean enableAui();

	/**
	 * Set this to <code>true</code> to preload widely used AUI modules.
	 *
	 * @return <code>true</code> if widely used AUI modules should be preloaded.
	 * @review
	 */
	@Meta.AD(
		deflt = "true", description = "enable-aui-preload-description",
		name = "enable-aui-preload", required = false
	)
	public boolean enableAuiPreload();

}