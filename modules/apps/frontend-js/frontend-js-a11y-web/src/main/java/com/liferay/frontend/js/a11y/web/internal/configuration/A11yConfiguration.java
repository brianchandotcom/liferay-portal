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

package com.liferay.frontend.js.a11y.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Matuzalem Teles
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	description = "a11y-configuration-description",
	id = "com.liferay.frontend.js.a11y.web.internal.configuration.A11yConfiguration",
	localization = "content/Language", name = "a11y-configuration-name"
)
public @interface A11yConfiguration {

	@Meta.AD(
		deflt = "body", description = "target-description",
		name = "target-name", required = false
	)
	public String target();

	@Meta.AD(deflt = "false", name = "control-menu-name", required = false)
	public boolean enableControlMenu();

	@Meta.AD(deflt = "false", name = "global-menu-name", required = false)
	public boolean enableGlobalMenu();

	@Meta.AD(deflt = "false", name = "product-menu-name", required = false)
	public boolean enableProductMenu();

	@Meta.AD(deflt = "false", name = "editors-name", required = false)
	public boolean enableEditors();

	@Meta.AD(
		description = "denylist-description", name = "denylist-name",
		required = false
	)
	public String[] denylist();

	@Meta.AD(
		description = "axe-core-run-only-description",
		name = "axe-core-run-only-name", required = false
	)
	public String[] axeCoreRunOnly();

	@Meta.AD(deflt = "true", name = "axe-core-iframes-name", required = false)
	public boolean axeCoreIframes();

	@Meta.AD(
		deflt = "60000", description = "axe-core-frame-wait-time-description",
		name = "axe-core-frame-wait-time-name", required = false
	)
	public int axeCoreFrameWaitTime();

	@Meta.AD(
		deflt = "false", name = "axe-core-performance-timer-name",
		required = false
	)
	public boolean axeCorePerformanceTimer();

}