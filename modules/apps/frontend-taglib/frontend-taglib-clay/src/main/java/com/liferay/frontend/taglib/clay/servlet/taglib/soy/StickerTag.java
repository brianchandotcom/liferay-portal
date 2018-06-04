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
 * @author Carlos Lancha
 */
public class StickerTag extends BaseClayTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClaySticker");
		setModuleBaseName("sticker");

		return super.doStartTag();
	}

	public void setIcon(String icon) {
		putValue("icon", icon);
	}

	public void setLabel(String label) {
		putValue("label", label);
	}

	public void setOutside(Boolean outside) {
		putValue("outside", outside);
	}

	public void setPosition(String position) {
		putValue("position", position);
	}

	public void setShape(String shape) {
		putValue("shape", shape);
	}

	public void setSize(String size) {
		putValue("size", size);
	}

	public void setStyle(String style) {
		putValue("style", style);
	}

}