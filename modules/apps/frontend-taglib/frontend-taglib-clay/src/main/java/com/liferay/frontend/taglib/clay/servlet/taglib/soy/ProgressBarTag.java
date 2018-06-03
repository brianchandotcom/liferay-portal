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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;

/**
 * @author Chema Balsas
 */
public class ProgressBarTag extends BaseClayTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayProgressBar");
		setHydrate(true);
		setModuleBaseName("progress-bar");

		return super.doStartTag();
	}

	public void setMaxValue(int maxValue) {
		putValue("maxValue", maxValue);
	}

	public void setMinValue(int minValue) {
		putValue("minValue", minValue);
	}

	public void setStatus(String status) {
		putValue("status", status);
	}

	public void setValue(int value) {
		putValue("value", value);
	}

}