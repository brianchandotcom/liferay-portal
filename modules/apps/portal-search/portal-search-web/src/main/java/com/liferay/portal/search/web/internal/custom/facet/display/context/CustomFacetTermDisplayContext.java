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

package com.liferay.portal.search.web.internal.custom.facet.display.context;

/**
 * @author Wade Cao
 */
public class CustomFacetTermDisplayContext {

	public String getFieldName() {
		return _fieldName;
	}

	public int getFrequency() {
		return _frequency;
	}

	public boolean isFrequencyVisible() {
		return _frequencyVisible;
	}

	public boolean isSelected() {
		return _selected;
	}

	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	public void setFrequency(int frequency) {
		_frequency = frequency;
	}

	public void setFrequencyVisible(boolean showFrequency) {
		_frequencyVisible = showFrequency;
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	private String _fieldName;
	private int _frequency;
	private boolean _frequencyVisible;
	private boolean _selected;

}