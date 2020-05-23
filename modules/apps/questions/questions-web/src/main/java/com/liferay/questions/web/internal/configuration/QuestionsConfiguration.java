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

package com.liferay.questions.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Javier Gamarra
 */
@ExtendedObjectClassDefinition(
	category = "third-party", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.questions.web.internal.configuration.QuestionsConfiguration",
	localization = "content/Language", name = "questions-configuration-name"
)
public interface QuestionsConfiguration {

	@Meta.AD(
		deflt = "false", name = "enable-redirect-to-login", required = false
	)
	public boolean enableRedirectToLogin();

}