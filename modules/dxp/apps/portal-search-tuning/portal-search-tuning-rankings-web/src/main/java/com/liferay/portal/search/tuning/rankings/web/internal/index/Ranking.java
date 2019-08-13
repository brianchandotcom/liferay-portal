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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Bryan Engler
 */
public class Ranking {

	public Ranking(Ranking ranking) {
		_blockIds = new LinkedHashSet<>(ranking._blockIds);
		_id = ranking._id;
		_inactive = ranking._inactive;
		_index = ranking._index;
		_name = ranking._name;
		_pinIds = new HashSet<>(ranking._pinIds);
		_pins = new ArrayList<>(ranking._pins);
		_queryStrings = new ArrayList<>(ranking._queryStrings);
	}

	public List<String> getBlockIds() {
		return new ArrayList<>(_blockIds);
	}

	public String getId() {
		return _id;
	}

	public String getIndex() {
		return _index;
	}

	public String getName() {
		return _name;
	}

	public List<Pin> getPins() {
		return Collections.unmodifiableList(_pins);
	}

	public List<String> getQueryStrings() {
		return Collections.unmodifiableList(_queryStrings);
	}

	public boolean isInactive() {
		return _inactive;
	}

	public boolean isPinned(String id) {
		return _pinIds.contains(id);
	}

	public static class Pin {

		public Pin(int position, String id) {
			_position = position;
			_id = id;
		}

		public String getId() {
			return _id;
		}

		public int getPosition() {
			return _position;
		}

		private final String _id;
		private final int _position;

	}

	public static class RankingBuilder {

		public RankingBuilder() {
			_ranking = new Ranking();
		}

		public RankingBuilder(Ranking ranking) {
			_ranking = ranking;
		}

		public RankingBuilder blocks(List<String> hiddenIds) {
			_ranking._blockIds = new LinkedHashSet<>(toList(hiddenIds));

			return this;
		}

		public Ranking build() {
			return new Ranking(_ranking);
		}

		public RankingBuilder id(String id) {
			_ranking._id = id;

			return this;
		}

		public RankingBuilder inactive(boolean inactive) {
			_ranking._inactive = inactive;

			return this;
		}

		public RankingBuilder index(String index) {
			_ranking._index = index;

			return this;
		}

		public RankingBuilder name(String name) {
			_ranking._name = name;

			return this;
		}

		public RankingBuilder pins(List<Pin> pins) {
			if (pins != null) {
				Stream<Pin> stream = pins.stream();

				_ranking._pinIds = new LinkedHashSet<>(
					stream.map(
						Pin::getId
					).collect(
						Collectors.toSet()
					));

				_ranking._pins = pins;
			}
			else {
				_ranking._pinIds.clear();

				_ranking._pins.clear();
			}

			return this;
		}

		public RankingBuilder queryStrings(List<String> queryStrings) {
			_ranking._queryStrings = queryStrings;

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

	private Set<String> _blockIds = new LinkedHashSet<>();
	private String _id;
	private boolean _inactive;
	private String _index;
	private String _name;
	private Set<String> _pinIds = new LinkedHashSet<>();
	private List<Pin> _pins = new ArrayList<>();
	private List<String> _queryStrings = new ArrayList<>();

}