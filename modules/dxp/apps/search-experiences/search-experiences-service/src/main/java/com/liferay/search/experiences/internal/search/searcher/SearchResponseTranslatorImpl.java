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

package com.liferay.search.experiences.internal.search.searcher;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.search.experiences.rest.dto.v1_0.SearchResponse;
import com.liferay.search.experiences.rest.dto.v1_0.translator.SearchResponseTranslator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = SearchResponseTranslator.class)
public class SearchResponseTranslatorImpl implements SearchResponseTranslator {

	@Override
	public SearchResponse translate(
		com.liferay.portal.search.searcher.SearchResponse
			portalSearchResponse) {

		SearchResponseTranslation searchResponseTranslation =
			new SearchResponseTranslation(_jsonFactory, portalSearchResponse);

		return searchResponseTranslation.translate();
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private JSONFactory _jsonFactory;

}