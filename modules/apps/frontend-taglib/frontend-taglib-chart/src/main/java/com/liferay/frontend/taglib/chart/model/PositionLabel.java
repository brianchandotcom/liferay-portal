/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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