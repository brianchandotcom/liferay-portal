/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Pedro Victor Silvestre
 */
@ExtendedObjectClassDefinition(
	generateUI = false, scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.captcha.internal.configuration.FunctionCaptchaImplConfiguration"
)
public interface FunctionCaptchaImplConfiguration {

	@Meta.AD
	public String captchaName();

	@Meta.AD
	public String captchaResponseParameterName();

	@Meta.AD
	public String customElementExternalReferenceCode();

	@Meta.AD
	public String oAuth2ApplicationExternalReferenceCode();

	@Meta.AD
	public String resourcePath();

}