/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.suggest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author     Michael C. Han
 * @deprecated As of Mueller (7.2.x), moved to {@link
 *             com.liferay.portal.search.engine.adapter.search.SuggestSearchResult}
 */
@Deprecated
public class SuggesterResult {

	public SuggesterResult(String name) {
		_name = name;
	}

	public void addEntry(Entry entry) {
		_entries.add(entry);
	}

	public List<Entry> getEntries() {
		return Collections.unmodifiableList(_entries);
	}

	public String getName() {
		return _name;
	}

	public static class Entry {

		public Entry(String text) {
			_text = text;
		}

		public void addOption(Option option) {
			_options.add(option);
		}

		public Float getCutoffScore() {
			return _cutoffScore;
		}

		public List<Option> getOptions() {
			return Collections.unmodifiableList(_options);
		}

		public String getText() {
			return _text;
		}

		public void setCutoffScore(Float cutoffScore) {
			_cutoffScore = cutoffScore;
		}

		public static class Option {

			public Option(String text, float score) {
				_text = text;
				_score = score;
			}

			public Integer getFrequency() {
				return _frequency;
			}

			public String getHighlightedText() {
				return _highlightedText;
			}

			public float getScore() {
				return _score;
			}

			public String getText() {
				return _text;
			}

			public void setFrequency(Integer frequency) {
				_frequency = frequency;
			}

			public void setHighlightedText(String highlightedText) {
				_highlightedText = highlightedText;
			}

			private Integer _frequency;
			private String _highlightedText;
			private final float _score;
			private String _text;

		}

		private Float _cutoffScore;
		private final List<Option> _options = new ArrayList<>();
		private String _text;

	}

	private final List<Entry> _entries = new ArrayList<>();
	private final String _name;

}