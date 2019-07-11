/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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