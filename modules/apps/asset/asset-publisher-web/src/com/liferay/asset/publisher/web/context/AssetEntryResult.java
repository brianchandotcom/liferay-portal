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

package com.liferay.asset.publisher.web.context;

import com.liferay.portlet.asset.model.AssetEntry;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class AssetEntryResult {

	public AssetEntryResult(List<AssetEntry> results) {
		_results = results;
	}

	public AssetEntryResult(String title, List<AssetEntry> results) {
		_title = title;
		_results = results;
	}

	public List<AssetEntry> getResults() {
		return _results;
	}

	public String getTitle() {
		return _title;
	}

	public void setResults(List<AssetEntry> results) {
		_results = results;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private List<AssetEntry> _results;
	private String _title;

}