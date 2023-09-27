/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.tuning.rankings.web.internal.util.RankingUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Bryan Engler
 */
public class Ranking {

	public Ranking(Ranking ranking) {
		_aliases = new ArrayList<>(ranking._aliases);
		_groupExternalReferenceCode = ranking._groupExternalReferenceCode;
		_hiddenDocumentIds = new LinkedHashSet<>(ranking._hiddenDocumentIds);
		_inactive = ranking._inactive;
		_indexName = ranking._indexName;
		_name = ranking._name;
		_pinnedDocumentIds = new HashSet<>(ranking._pinnedDocumentIds);
		_pins = new ArrayList<>(ranking._pins);
		_queryString = ranking._queryString;
		_rankingDocumentId = ranking._rankingDocumentId;
		_sxpBlueprintExternalReferenceCode =
			ranking._sxpBlueprintExternalReferenceCode;
	}

	public List<String> getAliases() {
		return Collections.unmodifiableList(_aliases);
	}

	public String getGroupExternalReferenceCode() {
		return _groupExternalReferenceCode;
	}

	public List<String> getHiddenDocumentIds() {
		return new ArrayList<>(_hiddenDocumentIds);
	}

	public String getIndexName() {
		return _indexName;
	}

	public String getName() {
		return _name;
	}

	public String getNameForDisplay() {
		StringBundler sb = new StringBundler(4);

		sb.append(_name);

		if (!Objects.equals(_name, _queryString)) {
			sb.append(CharPool.OPEN_BRACKET);
			sb.append(_queryString);
			sb.append(CharPool.CLOSE_BRACKET);
		}

		return sb.toString();
	}

	public List<Pin> getPins() {
		return Collections.unmodifiableList(_pins);
	}

	public String getQueryString() {
		return _queryString;
	}

	public Collection<String> getQueryStrings() {
		return RankingUtil.getQueryStrings(_queryString, _aliases);
	}

	public String getRankingDocumentId() {
		return _rankingDocumentId;
	}

	public String getSXPBlueprintExternalReferenceCode() {
		return _sxpBlueprintExternalReferenceCode;
	}

	public boolean isInactive() {
		return _inactive;
	}

	public boolean isPinned(String documentId) {
		return _pinnedDocumentIds.contains(documentId);
	}

	public static class Pin {

		public Pin(int position, String documentId) {
			_position = position;
			_documentId = documentId;
		}

		public String getDocumentId() {
			return _documentId;
		}

		public int getPosition() {
			return _position;
		}

		private final String _documentId;
		private final int _position;

	}

	public static class RankingBuilder {

		public RankingBuilder() {
			_ranking = new Ranking();
		}

		public RankingBuilder(Ranking ranking) {
			_ranking = ranking;
		}

		public RankingBuilder aliases(List<String> aliases) {
			_ranking._aliases = aliases;

			return this;
		}

		public Ranking build() {
			return new Ranking(_ranking);
		}

		public RankingBuilder groupExternalReferenceCode(
			String groupExternalReferenceCode) {

			_ranking._groupExternalReferenceCode = groupExternalReferenceCode;

			return this;
		}

		public RankingBuilder hiddenDocumentIds(
			List<String> hiddenDocumentIds) {

			_ranking._hiddenDocumentIds = new LinkedHashSet<>(
				toList(hiddenDocumentIds));

			return this;
		}

		public RankingBuilder inactive(boolean inactive) {
			_ranking._inactive = inactive;

			return this;
		}

		public RankingBuilder indexName(String indexName) {
			_ranking._indexName = indexName;

			return this;
		}

		public RankingBuilder name(String name) {
			_ranking._name = name;

			return this;
		}

		public RankingBuilder pins(List<Pin> pins) {
			if (pins != null) {
				Set<String> documentIds = new LinkedHashSet<>();

				pins.forEach(pin -> documentIds.add(pin.getDocumentId()));

				_ranking._pinnedDocumentIds = documentIds;

				_ranking._pins = pins;
			}
			else {
				_ranking._pinnedDocumentIds.clear();

				_ranking._pins.clear();
			}

			return this;
		}

		public RankingBuilder queryString(String queryString) {
			_ranking._queryString = queryString;

			return this;
		}

		public RankingBuilder rankingDocumentId(String rankingDocumentId) {
			_ranking._rankingDocumentId = rankingDocumentId;

			return this;
		}

		public RankingBuilder sxpBlueprintExternalReferenceCode(
			String sxpBlueprintExternalReferenceCode) {

			_ranking._sxpBlueprintExternalReferenceCode =
				sxpBlueprintExternalReferenceCode;

			return this;
		}

		protected static <T, V extends T> List<T> toList(List<V> list) {
			if (list != null) {
				return new ArrayList<>(list);
			}

			return new ArrayList<>();
		}

		private final Ranking _ranking;

	}

	private Ranking() {
	}

	private List<String> _aliases = new ArrayList<>();
	private String _groupExternalReferenceCode;
	private Set<String> _hiddenDocumentIds = new LinkedHashSet<>();
	private boolean _inactive;
	private String _indexName;
	private String _name;
	private Set<String> _pinnedDocumentIds = new LinkedHashSet<>();
	private List<Pin> _pins = new ArrayList<>();
	private String _queryString;
	private String _rankingDocumentId;
	private String _sxpBlueprintExternalReferenceCode;

}