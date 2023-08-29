/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = DocumentToRankingTranslator.class)
public class DocumentToRankingTranslatorImpl
	implements DocumentToRankingTranslator {

	@Override
	public Ranking translate(Document document, String rankingDocumentId) {
		return builder(
		).aliases(
			_getAliases(document)
		).groupExternalReferenceCode(
			document.getString(RankingFields.GROUP_EXTERNAL_REFERENCE_CODE)
		).hiddenDocumentIds(
			document.getStrings(RankingFields.BLOCKS)
		).inactive(
			document.getBoolean(RankingFields.INACTIVE)
		).indexName(
			document.getString("index")
		).name(
			_getName(document)
		).pins(
			_getPins(document)
		).queryString(
			_getQueryString(document)
		).rankingDocumentId(
			rankingDocumentId
		).sxpBlueprintExternalReferenceCode(
			document.getString(
				RankingFields.SXP_BLUEPRINT_EXTERNAL_REFERENCE_CODE)
		).build();
	}

	protected Ranking.RankingBuilder builder() {
		return new Ranking.RankingBuilder();
	}

	private List<String> _getAliases(Document document) {
		List<String> aliases = document.getStrings(RankingFields.ALIASES);

		if (ListUtil.isEmpty(aliases)) {
			List<String> queryStrings = document.getStrings(
				RankingFields.QUERY_STRINGS);

			queryStrings.remove(document.getString(RankingFields.QUERY_STRING));

			return queryStrings;
		}

		return aliases;
	}

	private String _getName(Document document) {
		String string = document.getString(RankingFields.NAME);

		if (Validator.isBlank(string)) {
			return _getQueryString(document);
		}

		return string;
	}

	private List<Ranking.Pin> _getPins(Document document) {
		List<?> values = document.getValues(RankingFields.PINS);

		if (ListUtil.isEmpty(values)) {
			return Collections.emptyList();
		}

		return TransformUtil.transform(
			(List<Map<String, String>>)values, this::_toPin);
	}

	private String _getQueryString(Document document) {
		String string = document.getString(RankingFields.QUERY_STRING);

		if (Validator.isBlank(string)) {
			List<String> strings = _getAliases(document);

			if (ListUtil.isNotEmpty(strings)) {
				return strings.get(0);
			}
		}

		return string;
	}

	private Ranking.Pin _toPin(Map<String, String> map) {
		return new Ranking.Pin(
			GetterUtil.getInteger(map.get("position")), map.get("uid"));
	}

}