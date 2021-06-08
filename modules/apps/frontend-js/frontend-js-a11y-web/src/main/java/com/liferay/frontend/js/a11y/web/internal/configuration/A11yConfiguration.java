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

	@Meta.AD(deflt = "true", name = "control-menu-name", required = false)
	public boolean controlMenu();

	@Meta.AD(deflt = "true", name = "global-menu-name", required = false)
	public boolean globalMenu();

	@Meta.AD(deflt = "true", name = "product-menu-name", required = false)
	public boolean productMenu();

	@Meta.AD(
		deflt = ".a11y-overlay|#yui3-css-stamp|.dropdown-menu|.tooltip",
		description = "denylist-description", name = "denylist-name",
		required = false
	)
	public String[] denylist();

	@Meta.AD(
		deflt = ".alloy-editor-container|.cke|.cke_editable",
		description = "editors-description", name = "editors-name",
		required = false
	)
	public String[] editors();

	@Meta.AD(name = "axe-core-run-only", required = false)
	public String[] axeCoreRunOnly();

	@Meta.AD(
		deflt = "violations|inapplicable|passes|incomplete",
		name = "axe-core-result-types", required = false
	)
	public String[] axeCoreResultTypes();

	@Meta.AD(deflt = "false", name = "axe-core-iframes", required = false)
	public boolean axeCoreIframes();

	@Meta.AD(
		deflt = "60000", name = "axe-core-frame-wait-time", required = false
	)
	public int axeCoreFrameWaitTime();

	@Meta.AD(
		deflt = "false", name = "axe-core-performance-timer", required = false
	)
	public boolean axeCorePerformanceTimer();

}