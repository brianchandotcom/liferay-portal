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

package com.liferay.portal.search.tuning.blueprints.keyword.index.configuration;

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
	id = "com.liferay.portal.search.tuning.blueprints.keyword.index.configuration.KeywordIndexConfiguration",
	localization = "content/Language", name = "keyword-index-configuration-name"
)
public interface KeywordIndexConfiguration {

	@Meta.AD(
		deflt = "false", name = "enable-keyword-indexing", required = false
	)
	public boolean enableKeywordIndexing();

	@Meta.AD(
		deflt = "true", name = "enable-spellcheck-provider", required = false
	)
	public boolean enableSpellcheckDataProvider();

	@Meta.AD(
		deflt = "1", description = "spellcheck-provider-weight-description",
		name = "spellcheck-provider-weight-name", required = false
	)
	public int spellCheckDataProviderWeight();

	@Meta.AD(
		deflt = "true", name = "enable-typeahead-provider", required = false
	)
	public boolean enableTypeaheadDataProvider();

	@Meta.AD(
		deflt = "1", description = "typeahead-provider-weight-description",
		name = "typeahead-provider-weight-name", required = false
	)
	public int typeaheadDataProviderWeight();

	@Meta.AD(
		deflt = "2",
		description = "typeahead-provider-hitcount-threshold-description",
		name = "typeahead-provider-hitcount-threshold-name", required = false
	)
	public int typeAheadProviderHitCountThreshold();

}