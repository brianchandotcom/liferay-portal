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

package com.liferay.portal.verify.extender.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Miguel Pastor
 */
@ExtendedObjectClassDefinition(category = "upgrades")
@Meta.OCD(
	id = "com.liferay.portal.verify.extender.internal.configuration.VerifyProcessTrackerConfiguration",
	localization = "content/Language",
	name = "verify-process-configuration-name"
)
public interface VerifyProcessTrackerConfiguration {

	@Meta.AD(deflt = "true", name = "auto-verify", required = false)
	public boolean autoVerify();

	@Meta.AD(deflt = "true", name = "index-read-only", required = false)
	public boolean indexReadOnly();

}