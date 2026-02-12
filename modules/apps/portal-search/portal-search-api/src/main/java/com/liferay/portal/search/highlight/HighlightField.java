/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.highlight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class HighlightField {

	public HighlightField() {
	}

	public HighlightField(HighlightField highlightField) {
		_fragments.addAll(highlightField._fragments);
		_name = highlightField._name;
	}

	public List<String> getFragments() {
		return Collections.unmodifiableList(_fragments);
	}

	public String getName() {
		return _name;
	}

	public static final class HighlightFieldBuilderImpl
		implements HighlightFieldBuilder {

		@Override
		public HighlightFieldBuilder addFragment(String fragment) {
			_highlightField._fragments.add(fragment);

			return this;
		}

		@Override
		public HighlightField build() {
			return new HighlightField(_highlightField);
		}

		@Override
		public HighlightFieldBuilder fragments(List<String> fragments) {
			_highlightField._fragments = fragments;

			return this;
		}

		@Override
		public HighlightFieldBuilder name(String name) {
			_highlightField._name = name;

			return this;
		}

		private final HighlightField _highlightField = new HighlightField();

	}

	private List<String> _fragments = new ArrayList<>();
	private String _name;

}