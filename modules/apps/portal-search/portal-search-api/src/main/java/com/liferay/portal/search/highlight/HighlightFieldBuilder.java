/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.highlight;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class HighlightFieldBuilder {

	public HighlightFieldBuilder addFragment(String fragment) {
		_fragments.add(fragment);

		return this;
	}

	public HighlightField build() {
		return new HighlightField(_fragments, _name);
	}

	public HighlightFieldBuilder fragments(List<String> fragments) {
		_fragments = fragments;

		return this;
	}

	public HighlightFieldBuilder name(String name) {
		_name = name;

		return this;
	}

	private List<String> _fragments = new ArrayList<>();
	private String _name;

}