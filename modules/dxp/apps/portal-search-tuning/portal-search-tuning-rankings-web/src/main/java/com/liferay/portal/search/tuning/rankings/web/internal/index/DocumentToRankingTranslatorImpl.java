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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = DocumentToRankingTranslator.class)
public class DocumentToRankingTranslatorImpl
	implements DocumentToRankingTranslator {

	@Override
	public Ranking translate(Document document, String id) {
		return builder(
		).blocks(
			document.getStrings(RankingFields.BLOCKS)
		).id(
			id
		).inactive(
			document.getBoolean(RankingFields.INACTIVE)
		).index(
			document.getString("index")
		).name(
			_getName(document)
		).pins(
			_getPins(document)
		).queryStrings(
			_getQueryStrings(document)
		).build();
	}

	protected Ranking.RankingBuilder builder() {
		return new Ranking.RankingBuilder();
	}

	private String _getName(Document document) {
		String string = document.getString(RankingFields.NAME);

		if (Validator.isBlank(string)) {
			return document.getString(RankingFields.QUERY_STRING);
		}

		return string;
	}

	private List<Ranking.Pin> _getPins(Document document) {
		List<?> values = document.getValues(RankingFields.PINS);

		if (ListUtil.isEmpty(values)) {
			return Collections.emptyList();
		}

		List<Map<String, String>> maps = (List<Map<String, String>>)values;

		Stream<Map<String, String>> stream = maps.stream();

		Stream<Ranking.Pin> pinStream = stream.map(this::_toPin);

		return pinStream.collect(Collectors.toList());
	}

	private List<String> _getQueryStrings(Document document) {
		List<String> strings = document.getStrings(RankingFields.QUERY_STRINGS);

		if (ListUtil.isEmpty(strings)) {
			return document.getStrings(RankingFields.ALIASES);
		}

		return strings;
	}

	private Ranking.Pin _toPin(Map<String, String> map) {
		return new Ranking.Pin(
			GetterUtil.getInteger(map.get("position")), map.get("uid"));
	}

}