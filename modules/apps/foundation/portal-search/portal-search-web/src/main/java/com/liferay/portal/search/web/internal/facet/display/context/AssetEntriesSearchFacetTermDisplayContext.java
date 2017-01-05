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

import java.io.Serializable;

/**
 * @author Lino Alves
 */
public class AssetEntriesSearchFacetTermDisplayContext implements Serializable {

	public String getAssetType() {
		return _assetType;
	}

	public int getFrequency() {
		return _frequency;
	}

	public String getTypeName() {
		return _typeName;
	}

	public boolean isFrequencyVisible() {
		return _frequencyVisible;
	}

	public boolean isSelected() {
		return _selected;
	}

	public void setAssetType(String assetType) {
		_assetType = assetType;
	}

	public void setFrequency(int frequency) {
		_frequency = frequency;
	}

	public void setFrequencyVisible(boolean frequencyVisible) {
		_frequencyVisible = frequencyVisible;
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	public void setTypeName(String typeName) {
		_typeName = typeName;
	}

	private String _assetType;
	private int _frequency;
	private boolean _frequencyVisible;
	private boolean _selected;
	private String _typeName;

}