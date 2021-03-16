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

package com.liferay.portal.search.tuning.blueprints.synonyms.suggestions.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Petteri Karttunen
 */
@ExtendedObjectClassDefinition(
	category = "search-experiences",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.portal.search.tuning.blueprints.synonyms.suggestions.configuration.SynonymSuggestionsConfiguration",
	localization = "content/Language",
	name = "synonyms-suggestions-configuration-name"
)
public interface SynonymSuggestionsConfiguration {

	@Meta.AD(
		deflt = "true", name = "enable-typeahead-data-provider",
		required = false
	)
	public boolean enableTypeaheadDataProvider();

	@Meta.AD(
		deflt = "1",
		description = "synonyms-typeahead-data-provider-weight-description",
		name = "synonyms-typeahead-data-provider-weight-name", required = false
	)
	public int typeaheadDataProviderWeight();

}