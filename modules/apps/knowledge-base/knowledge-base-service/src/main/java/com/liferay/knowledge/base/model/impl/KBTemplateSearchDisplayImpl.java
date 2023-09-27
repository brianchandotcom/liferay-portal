/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.model.impl;

import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.model.KBTemplateSearchDisplay;

import java.util.List;

/**
 * @author Peter Shin
 */
public class KBTemplateSearchDisplayImpl implements KBTemplateSearchDisplay {

	public KBTemplateSearchDisplayImpl(
		List<KBTemplate> results, int total, int[] curStartValues) {

		_results = results;
		_total = total;
		_curStartValues = curStartValues;
	}

	@Override
	public int[] getCurStartValues() {
		return _curStartValues;
	}

	@Override
	public List<KBTemplate> getResults() {
		return _results;
	}

	@Override
	public int getTotal() {
		return _total;
	}

	@Override
	public void setCurStartValues(int[] curStartValues) {
		_curStartValues = curStartValues;
	}

	@Override
	public void setResults(List<KBTemplate> results) {
		_results = results;
	}

	@Override
	public void setTotal(int total) {
		_total = total;
	}

	private int[] _curStartValues;
	private List<KBTemplate> _results;
	private int _total;

}