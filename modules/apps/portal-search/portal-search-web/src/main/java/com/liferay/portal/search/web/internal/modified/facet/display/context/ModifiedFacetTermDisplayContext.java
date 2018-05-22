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

package com.liferay.portal.search.web.internal.modified.facet.display.context;

import java.io.Serializable;

/**
 * @author Lino Alves
 */
public class ModifiedFacetTermDisplayContext implements Serializable {

	public int getFrequency() {
		return _frequency;
	}

	public String getLabel() {
		return _label;
	}

	public String getRange() {
		return _range;
	}

	public String getRangeURL() {
		return _rangeURL;
	}

	public boolean isSelected() {
		return _selected;
	}

	public void setFrequency(int frequency) {
		_frequency = frequency;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setRange(String range) {
		_range = range;
	}

	public void setRangeURL(String rangeURL) {
		_rangeURL = rangeURL;
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	private int _frequency;
	private String _label;
	private String _range;
	private String _rangeURL;
	private boolean _selected;

}