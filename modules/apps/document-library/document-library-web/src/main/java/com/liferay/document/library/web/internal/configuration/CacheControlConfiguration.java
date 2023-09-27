/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.document.library.web.internal.configuration.CacheControlConfiguration",
	localization = "content/Language", name = "cache-control-configuration-name"
)
public interface CacheControlConfiguration {

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "private", description = "cache-control-description",
		name = "cache-control", optionLabels = {"private", "public"},
		optionValues = {"private", "public"}, required = false
	)
	public String cacheControl();

	@Meta.AD(
		deflt = "0", description = "max-age-description", name = "max-age",
		required = false
	)
	public int maxAge();

	@Meta.AD(
		description = "uncacheable-mime-types-description",
		name = "uncacheable-mime-types", required = false
	)
	public String[] notCacheableMimeTypes();

}