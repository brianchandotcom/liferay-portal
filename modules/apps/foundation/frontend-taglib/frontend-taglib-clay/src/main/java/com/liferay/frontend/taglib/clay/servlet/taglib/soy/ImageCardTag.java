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

/**
 * @author Julien Castelain
 */
public class ImageCardTag extends BaseClayCardTag {

	public ImageCardTag() {
		super("ClayImageCard");
	}

	public void setFileType(String fileType) {
		putValue("fileType", fileType);
	}

	public void setFileTypeStyle(String fileTypeStyle) {
		putValue("fileTypeStyle", fileTypeStyle);
	}

	public void setIcon(String icon) {
		putValue("icon", icon);
	}

	public void setImageAlt(String imageAlt) {
		putValue("imageAlt", imageAlt);
	}

	public void setImageSrc(String imageSrc) {
		putValue("imageSrc", imageSrc);
	}

	public void setLabels(Object labels) {
		putValue("labels", labels);
	}

	public void setLabelStylesMap(Object labelStylesMap) {
		putValue("labelStylesMap", labelStylesMap);
	}

	public void setSubtitle(String subtitle) {
		putValue("subtitle", subtitle);
	}

	public void setTitle(String title) {
		putValue("title", title);
	}

}