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
public class LinkTag extends BaseClayTag {

	public LinkTag() {
		super("link", "ClayLink");
	}

	public void setAriaLabel(String ariaLabel) {
		putValue("ariaLabel", ariaLabel);
	}

	public void setButtonStyle(String buttonStyle) {
		putValue("buttonStyle", buttonStyle);
	}

	public void setDownload(String download) {
		putValue("download", download);
	}

	public void setHref(String href) {
		putValue("href", href);
	}

	public void setIcon(String icon) {
		putValue("icon", icon);
	}

	public void setIconAlignment(String iconAlignment) {
		putValue("iconAlignment", iconAlignment);
	}

	public void setLabel(String label) {
		putValue("label", label);
	}

	public void setStyle(String style) {
		putValue("style", style);
	}

	public void setTarget(String target) {
		putValue("target", target);
	}

	public void setTitle(String title) {
		putValue("title", title);
	}

}