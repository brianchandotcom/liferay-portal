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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.util.HashMap;

/**
 * @author Bruno Basto
 */
public class Sticker extends HashMap<String, Object> {

	public Sticker() {
		setShape("circle");
		setSize("xl");
	}

	public void setCssClass(String cssClass) {
		put("className", cssClass);
	}

	public void setDisplayType(String displayType) {
		put("displayType", displayType);
	}

	public void setInline(boolean inline) {
		put("inline", inline);
	}

	public void setOutside(boolean outside) {
		put("outside", outside);
	}

	public void setPosition(String position) {
		put("position", position);
	}

	public void setShape(String shape) {
		put("shape", shape);
	}

	public void setSize(String size) {
		put("size", size);
	}

}