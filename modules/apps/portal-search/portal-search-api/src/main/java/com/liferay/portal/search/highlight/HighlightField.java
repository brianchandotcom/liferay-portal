/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.highlight;

import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class HighlightField {

	public List<String> getFragments() {
		return _fragments;
	}

	public String getName() {
		return _name;
	}

	protected HighlightField(List<String> fragments, String name) {
		_fragments = Collections.unmodifiableList(fragments);
		_name = name;
	}

	private final List<String> _fragments;
	private final String _name;

}