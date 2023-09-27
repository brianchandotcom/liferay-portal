/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.facet.collector;

import com.liferay.petra.string.StringBundler;

/**
 * @author Michael C. Han
 */
public class DefaultTermCollector implements TermCollector {

	public DefaultTermCollector(String term, int frequency) {
		_term = term;
		_frequency = frequency;
	}

	@Override
	public int getFrequency() {
		return _frequency;
	}

	@Override
	public String getTerm() {
		return _term;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{frequency=", _frequency, ", term=", _term, "}");
	}

	private final int _frequency;
	private final String _term;

}