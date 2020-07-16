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

package com.liferay.layout.util.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov
 */
public class LayoutStructureItemStyleSet {

	public LayoutStructureItemStyleSet(String label) {
		_label = label;
		_styles = new ArrayList<>();
	}

	public void addStyle(LayoutStructureItemStyle style) {
		_styles.add(style);
	}

	public String getLabel() {
		return _label;
	}

	public List<LayoutStructureItemStyle> getStyles() {
		return _styles;
	}

	private final String _label;
	private final List<LayoutStructureItemStyle> _styles;

}