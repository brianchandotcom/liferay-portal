/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.suggest;

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