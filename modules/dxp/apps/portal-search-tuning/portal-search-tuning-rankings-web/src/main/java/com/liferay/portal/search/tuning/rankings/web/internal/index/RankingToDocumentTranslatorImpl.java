/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilderFactory;

import java.util.Collection;
import java.util.List;

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
		).setString(
			RankingFields.GROUP_EXTERNAL_REFERENCE_CODE,
			ranking.getGroupExternalReferenceCode()
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
			RankingFields.SXP_BLUEPRINT_EXTERNAL_REFERENCE_CODE,
			ranking.getSXPBlueprintExternalReferenceCode()
		).setString(
			RankingFields.UID, ranking.getRankingDocumentId()
		).build();
	}

	private Collection<Object> _toMaps(List<Ranking.Pin> pins) {
		return TransformUtil.transform(
			pins,
			pin -> LinkedHashMapBuilder.put(
				RankingFields.POSITION, String.valueOf(pin.getPosition())
			).put(
				RankingFields.UID, pin.getDocumentId()
			).build());
	}

	@Reference
	private DocumentBuilderFactory _documentBuilderFactory;

}