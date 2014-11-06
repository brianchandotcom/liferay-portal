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

package com.liferay.portal.kernel.search;

/**
 * @author Michael C. Han
 */
public class WeightedWord implements Comparable<WeightedWord> {

	public WeightedWord(String word, float weight) {
		_word = word;
		_weight = weight;
	}

	@Override
	public int compareTo(WeightedWord weightedWord) {
		if (getWeight() < weightedWord.getWeight()) {
			return -1;
		}
		else if (getWeight() == weightedWord.getWeight()) {
			return 0;
		}

		return 1;
	}

	public float getWeight() {
		return _weight;
	}

	public String getWord() {
		return _word;
	}

	public void setWeight(float weight) {
		_weight = weight;
	}

	private float _weight;
	private final String _word;

}