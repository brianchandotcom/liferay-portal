/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class NGramHolder {

	public void addNGram(int number, String gram) {
		String gramKey = "gram".concat(String.valueOf(number));

		List<String> grams = _nGrams.get(gramKey);

		if (grams == null) {
			grams = new ArrayList<String>();

			_nGrams.put(gramKey, grams);
		}

		grams.add(gram);
	}

	public void addNGramEnd(int number, String gram) {
		String endKey = "end".concat(String.valueOf(number));

		_nGramEnds.put(endKey, gram);
	}

	public void addNGramStart(int number, String gram) {
		String startKey = "start".concat(String.valueOf(number));

		_nGramStarts.put(startKey, gram);
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

	private Map<String, String> _nGramEnds = new HashMap<String, String>();
	private Map<String, List<String>> _nGrams =
		new HashMap<String, List<String>>();
	private Map<String, String> _nGramStarts = new HashMap<String, String>();
	private Set<String> _nGramStrings = new HashSet<String>();

}