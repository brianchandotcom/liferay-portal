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
public class Padding extends ChartObject {

	public Padding() {
		this(0, 0, 0, 0);
	}

	public Padding(int left, int top, int right, int bottom) {
		setLeft(left);
		setTop(top);
		setRight(right);
		setBottom(bottom);
	}

	public void setBottom(int bottom) {
		set("bottom", bottom);
	}

	public void setLeft(int left) {
		set("left", left);
	}

	public void setRight(int right) {
		set("right", right);
	}

	public void setTop(int top) {
		set("top", top);
	}

}