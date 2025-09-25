/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.search.facet.collector.DefaultTermCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class TermCollectorHolder {

	public TermCollectorHolder(int size) {
		_termCollectors = new ArrayList<>(size);
		_termCollectorsByName = new HashMap<>(size);
	}

	public void add(String term, int frequency) {
		TermCollector termCollector = new DefaultTermCollector(term, frequency);

		_termCollectors.add(termCollector);

		_termCollectorsByName.put(term, termCollector);
	}

	public TermCollector getTermCollector(String term) {
		TermCollector termCollector = _termCollectorsByName.get(term);

		if (termCollector != null) {
			return termCollector;
		}

		return new DefaultTermCollector(term, 0);
	}

	public List<TermCollector> getTermCollectors() {
		return _termCollectors;
	}

	private final List<TermCollector> _termCollectors;
	private final Map<String, TermCollector> _termCollectorsByName;

}