/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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