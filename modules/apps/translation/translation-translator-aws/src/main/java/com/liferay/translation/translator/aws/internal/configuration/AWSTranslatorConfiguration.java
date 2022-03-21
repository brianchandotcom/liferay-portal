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

package com.liferay.translation.translator.aws.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "translation",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.translation.translator.aws.internal.configuration.AWSTranslatorConfiguration",
	localization = "content/Language",
	name = "aws-translator-configuration-name"
)
public interface AWSTranslatorConfiguration {

	@Meta.AD(
		deflt = "false", description = "enabled-description[aws-translation]",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(deflt = "", name = "access-key", required = false)
	public String accessKey();

	@Meta.AD(deflt = "", name = "secret-key", required = false)
	public String secretKey();

	@Meta.AD(deflt = "us-west-1", name = "region", required = false)
	public String region();

}