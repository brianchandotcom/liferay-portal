/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Wade Cao
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.TitleFieldQueryBuilderConfiguration",
	localization = "content/Language",
	name = "title-field-query-builder-configuration-name"
)
@ProviderType
public interface TitleFieldQueryBuilderConfiguration {

	@Meta.AD(
		deflt = "2.0", description = "exact-match-boost-help",
		name = "exact-match-boost", required = false
	)
	public float exactMatchBoost();

	@Meta.AD(
		deflt = "50", description = "max-expansions-help",
		name = "max-expansions", required = false
	)
	public int maxExpansions();

}