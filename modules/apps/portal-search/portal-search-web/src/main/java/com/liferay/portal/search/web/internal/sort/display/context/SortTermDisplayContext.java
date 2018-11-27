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

package com.liferay.portal.search.web.internal.sort.display.context;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class SortTermDisplayContext {

	public String getField() {
		return _field;
	}

	public String getLabel() {
		return _label;
	}

	public boolean isSelected() {
		return _selected;
	}

	public void setField(String field) {
		_field = field;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	private String _field;
	private String _label;
	private boolean _selected;

}