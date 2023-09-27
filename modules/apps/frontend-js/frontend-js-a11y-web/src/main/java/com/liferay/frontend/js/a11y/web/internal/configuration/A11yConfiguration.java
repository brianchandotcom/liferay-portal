/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Meta.AD(deflt = "false", required = false)
	public boolean enable();

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