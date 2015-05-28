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

package com.liferay.portal.kernel.search.suggest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class NGramHolder {

	public void addNGram(int number, String gram) {
		String key = "gram" + number;

		List<String> grams = _nGrams.get(key);

		if (grams == null) {
			grams = new ArrayList<>();

			_nGrams.put(key, grams);
		}

		grams.add(gram);
	}

	public void addNGramEnd(int number, String gram) {
		_nGramEnds.put("end" + number, gram);
	}

	public void addNGramStart(int number, String gram) {
		_nGramStarts.put("start" + number, gram);
	}

	public Map<String, String> getNGramEnds() {
		return _nGramEnds;
	}

	public Map<String, List<String>> getNGrams() {
		return _nGrams;
	}

	public Map<String, String> getNGramStarts() {
		return _nGramStarts;
	}

	private final Map<String, String> _nGramEnds = new HashMap<>();
	private final Map<String, List<String>> _nGrams = new HashMap<>();
	private final Map<String, String> _nGramStarts = new HashMap<>();

}