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

package com.liferay.commerce.frontend.model;

/**
 * @author Alessio Antonio Rendina
 */
public class LabelField {

	public LabelField(String label) {
		_label = label;
	}

	public LabelField(String displayStyle, String label) {
		_displayStyle = displayStyle;
		_label = label;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public String getLabel() {
		return _label;
	}

	private String _displayStyle;
	private final String _label;

}