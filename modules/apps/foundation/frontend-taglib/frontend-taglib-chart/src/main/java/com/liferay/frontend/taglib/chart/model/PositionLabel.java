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

package com.liferay.frontend.taglib.chart.model;

/**
 * @author Iván Zaera Avellón
 */
public class PositionLabel extends ChartObject {

	public PositionLabel(Position position, String text) {
		setPosition(position);
		setText(text);
	}

	public void setPosition(Position position) {
		set("position", position._value);
	}

	public void setText(String text) {
		set("text", text);
	}

	public enum Position {

		INNER_BOTTOM("inner-bottom"), INNER_CENTER("inner-center"),
		INNER_LEFT("inner-left"), INNER_MIDDLE("inner-middle"),
		INNER_RIGHT("inner-right"), INNER_TOP("inner-top"),
		OUTER_BOTTOM("outer-bottom"), OUTER_CENTER("outer-center"),
		OUTER_LEFT("outer-left"), OUTER_MIDDLE("outer-middle"),
		OUTER_RIGHT("outer-right"), OUTER_TOP("outer-top");

		private Position(String value) {
			_value = value;
		}

		private final String _value;

	}

}