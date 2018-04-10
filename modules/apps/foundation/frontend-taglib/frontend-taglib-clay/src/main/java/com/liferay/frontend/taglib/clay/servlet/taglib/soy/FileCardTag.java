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

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayCardTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;

import java.util.Map;

/**
 * @author Julien Castelain
 */
public class FileCardTag extends BaseClayCardTag {

	public FileCardTag() {
		super("ClayFileCard");
	}

	public void setIcon(String icon) {
		putValue("icon", icon);
	}

	public void setLabels(LabelItemList labelItemList) {
		putValue("labels", labelItemList);
	}

	public void setLabelStylesMap(Map<String, String> labelStylesMap) {
		putValue("labelStylesMap", labelStylesMap);
	}

	public void setStickerLabel(String stickerLabel) {
		putValue("stickerLabel", stickerLabel);
	}

	public void setStickerShape(String stickerShape) {
		putValue("stickerShape", stickerShape);
	}

	public void setStickerStyle(String stickerStyle) {
		putValue("stickerStyle", stickerStyle);
	}

	public void setSubtitle(String subtitle) {
		putValue("subtitle", subtitle);
	}

	public void setTitle(String title) {
		putValue("title", title);
	}

}