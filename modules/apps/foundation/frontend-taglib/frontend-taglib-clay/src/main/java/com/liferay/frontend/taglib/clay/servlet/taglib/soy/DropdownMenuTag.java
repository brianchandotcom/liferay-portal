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
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chema Balsas
 */
public class DropdownMenuTag extends BaseClayTag {

	public DropdownMenuTag() {
		super("dropdown", "ClayDropdown", true);
	}

	@Override
	public int doStartTag() {
		Map<String, Object> context = getContext();

		if (Validator.isNotNull(context.get("buttonLabel"))) {
			Map<String, String> button = new HashMap();

			button.put("label", (String)context.get("buttonLabel"));
			button.put("style", (String)context.get("buttonStyle"));
			button.put("type", (String)context.get("buttonType"));

			putValue("button", button);
		}

		return super.doStartTag();
	}

	public void setButtonLabel(String buttonLabel) {
		putValue("buttonLabel", buttonLabel);
	}

	public void setButtonStyle(String buttonStyle) {
		putValue("buttonStyle", buttonStyle);
	}

	public void setButtonType(String buttonType) {
		putValue("buttonType", buttonType);
	}

	public void setExpanded(Boolean expanded) {
		putValue("expanded", expanded);
	}

	public void setItems(Object items) {
		putValue("items", items);
	}

	public void setItemsIconAlignment(String itemsIconAlignment) {
		putValue("itemsIconAlignment", itemsIconAlignment);
	}

	public void setLabel(String label) {
		putValue("label", label);
	}

	public void setSearchable(Boolean searchable) {
		putValue("searchable", searchable);
	}

	public void setStyle(String style) {
		putValue("style", style);
	}

	public void setType(String type) {
		putValue("type", type);
	}

}