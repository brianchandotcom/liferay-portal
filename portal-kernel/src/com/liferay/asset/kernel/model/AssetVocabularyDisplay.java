/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.json.JSON;

import java.io.Serializable;

import java.util.List;

/**
 * @author Igor Spasic
 */
public class AssetVocabularyDisplay implements Serializable {

	public AssetVocabularyDisplay() {
	}

	public AssetVocabularyDisplay(
		List<AssetVocabulary> vocabularies, int total, int start, int end) {

		_vocabularies = vocabularies;
		_total = total;
		_start = start;
		_end = end;
	}

	public int getEnd() {
		return _end;
	}

	public int getPage() {
		if ((_end > 0) && (_start >= 0)) {
			return _end / (_end - _start);
		}

		return 0;
	}

	public int getStart() {
		return _start;
	}

	public int getTotal() {
		return _total;
	}

	public List<AssetVocabulary> getVocabularies() {
		return _vocabularies;
	}

	public void setEnd(int end) {
		_end = end;
	}

	public void setStart(int start) {
		_start = start;
	}

	public void setTotal(int total) {
		_total = total;
	}

	public void setVocabularies(List<AssetVocabulary> vocabularies) {
		_vocabularies = vocabularies;
	}

	private int _end;
	private int _start;
	private int _total;

	@JSON
	private List<AssetVocabulary> _vocabularies;

}