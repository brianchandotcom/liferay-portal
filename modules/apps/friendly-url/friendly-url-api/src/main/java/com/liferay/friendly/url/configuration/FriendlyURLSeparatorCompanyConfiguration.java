/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Mikel Lorza
 */
@ExtendedObjectClassDefinition(
	category = "seo", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.friendly.url.configuration.FriendlyURLSeparatorCompanyConfiguration"
)
public interface FriendlyURLSeparatorCompanyConfiguration {

	@Meta.AD(deflt = "{}", required = false)
	public String friendlyURLSeparatorsJSON();

}