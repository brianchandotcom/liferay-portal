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

package com.liferay.portal.search.web.internal.facet.display.context;

/**
 * @author André de Oliveira
 */
public class AssetTagsSearchFacetTermDisplayContext {

	public AssetTagsSearchFacetTermDisplayContext(
		String value, int frequency, int popularity, boolean selected,
		boolean showFrequency) {

		_value = value;
		_frequency = frequency;
		_popularity = popularity;
		_selected = selected;
		_showFrequency = showFrequency;
	}

	public String getDisplayName() {
		return _value;
	}

	public int getFrequency() {
		return _frequency;
	}

	public int getPopularity() {
		return _popularity;
	}

	public String getValue() {
		return _value;
	}

	public boolean isSelected() {
		return _selected;
	}

	public boolean isShowFrequency() {
		return _showFrequency;
	}

	private final int _frequency;
	private final int _popularity;
	private final boolean _selected;
	private final boolean _showFrequency;
	private final String _value;

}