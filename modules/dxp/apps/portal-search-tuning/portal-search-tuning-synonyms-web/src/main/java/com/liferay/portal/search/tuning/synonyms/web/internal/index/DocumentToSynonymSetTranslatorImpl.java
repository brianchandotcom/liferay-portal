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

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adam Brandizzi
 */
@Component(service = DocumentToSynonymSetTranslator.class)
public class DocumentToSynonymSetTranslatorImpl
	implements DocumentToSynonymSetTranslator {

	@Override
	public SynonymSet translate(
		Document document, String synonymSetDocumentId) {

		return builder(
		).synonymSetDocumentId(
			synonymSetDocumentId
		).synonyms(
			document.getString(SynonymSetFields.SYNONYMS)
		).build();
	}

	@Override
	public SynonymSet translate(SearchHit searchHit) {
		return translate(searchHit.getDocument(), searchHit.getId());
	}

	@Override
	public List<SynonymSet> translateAll(List<SearchHit> searchHits) {
		Stream<SearchHit> stream = searchHits.stream();

		return stream.map(
			this::translate
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public List<SynonymSet> translateAll(SearchHits searchHits) {
		return translateAll(searchHits.getSearchHits());
	}

	protected SynonymSet.SynonymSetBuilder builder() {
		return new SynonymSet.SynonymSetBuilder();
	}

}