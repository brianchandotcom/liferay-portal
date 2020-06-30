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

package com.liferay.commerce.subscription.web.internal.model;

/**
 * @author Luca Pellizzon
 */
public class Label {

	public static final String DANGER = "danger";

	public static final String INFO = "info";

	public static final String SECONDARY = "secondary";

	public static final String SUCCESS = "success";

	public static final String WARNING = "warning";

	public Label(String label, String displayStyle) {
		_label = label;
		_displayStyle = displayStyle;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public String getLabel() {
		return _label;
	}

	private final String _displayStyle;
	private final String _label;

}