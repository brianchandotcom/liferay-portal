/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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