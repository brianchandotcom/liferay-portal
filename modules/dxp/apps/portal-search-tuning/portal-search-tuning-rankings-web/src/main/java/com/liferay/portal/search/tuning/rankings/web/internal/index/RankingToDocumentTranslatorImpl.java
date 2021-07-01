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

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilderFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = RankingToDocumentTranslator.class)
public class RankingToDocumentTranslatorImpl
	implements RankingToDocumentTranslator {

	@Override
	public Document translate(Ranking ranking) {
		return _documentBuilderFactory.builder(
		).setStrings(
			RankingFields.ALIASES, ArrayUtil.toStringArray(ranking.getAliases())
		).setStrings(
			RankingFields.BLOCKS,
			ArrayUtil.toStringArray(ranking.getHiddenDocumentIds())
		).setBoolean(
			RankingFields.INACTIVE, ranking.isInactive()
		).setString(
			RankingFields.INDEX, ranking.getIndexName()
		).setString(
			RankingFields.NAME, ranking.getName()
		).setValues(
			RankingFields.PINS, _toMaps(ranking.getPins())
		).setString(
			RankingFields.QUERY_STRING, ranking.getQueryString()
		).setStrings(
			RankingFields.QUERY_STRINGS,
			ArrayUtil.toStringArray(ranking.getQueryStrings())
		).setString(
			RankingFields.UID, ranking.getRankingDocumentId()
		).build();
	}

	@Reference(unbind = "-")
	protected void setDocumentBuilderFactory(
		DocumentBuilderFactory documentBuilderFactory) {

		_documentBuilderFactory = documentBuilderFactory;
	}

	private Collection<Object> _toMaps(List<Ranking.Pin> pins) {
		Stream<Ranking.Pin> stream = pins.stream();

		return stream.map(
			pin -> LinkedHashMapBuilder.put(
				RankingFields.POSITION, String.valueOf(pin.getPosition())
			).put(
				RankingFields.UID, pin.getDocumentId()
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private DocumentBuilderFactory _documentBuilderFactory;

}