/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tibor Lipusz
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.tuning.synonyms.web.internal.configuration.SynonymsConfiguration",
	localization = "content/Language", name = "synonyms-configuration-name"
)
public interface SynonymsConfiguration {

	@Meta.AD(
		deflt = "liferay_filter_synonym_en|liferay_filter_synonym_es",
		description = "synonym-filter-names-help",
		name = "synonym-filter-names", required = false
	)
	public String[] filterNames();

}