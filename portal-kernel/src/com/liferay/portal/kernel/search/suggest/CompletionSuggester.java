/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.suggest;

/**
 * @author Michael C. Han
 */
public class CompletionSuggester extends BaseSuggester {

	public CompletionSuggester(String name, String field, String value) {
		super(name, field, value);
	}

	@Override
	public <T> T accept(SuggesterVisitor<T> suggesterVisitor) {
		return suggesterVisitor.visit(this);
	}

	public String getAnalyzer() {
		return _analyzer;
	}

	public Integer getShardSize() {
		return _shardSize;
	}

	public Integer getSize() {
		return _size;
	}

	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	public void setShardSize(Integer shardSize) {
		_shardSize = shardSize;
	}

	public void setSize(Integer size) {
		_size = size;
	}

	private String _analyzer;
	private Integer _shardSize;
	private Integer _size;

}