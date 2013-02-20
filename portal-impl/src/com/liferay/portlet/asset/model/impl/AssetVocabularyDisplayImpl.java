/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.model.AssetVocabularyDisplay;

import java.util.List;

/**
 * @author Igor Spasic
 */
public class AssetVocabularyDisplayImpl implements AssetVocabularyDisplay {

	public AssetVocabularyDisplayImpl(
		List<AssetVocabulary> vocabularies, int page, int total) {

		_vocabularies = vocabularies;
		_page = page;
		_total = total;
	}

	public int getPage() {
		return _page;
	}

	public int getTotal() {
		return _total;
	}

	public List<AssetVocabulary> getVocabularies() {
		return _vocabularies;
	}

	private int _page;
	private int _total;
	@JSON
	private List<AssetVocabulary> _vocabularies;

}