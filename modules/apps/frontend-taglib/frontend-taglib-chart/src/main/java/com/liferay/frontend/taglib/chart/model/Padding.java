/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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