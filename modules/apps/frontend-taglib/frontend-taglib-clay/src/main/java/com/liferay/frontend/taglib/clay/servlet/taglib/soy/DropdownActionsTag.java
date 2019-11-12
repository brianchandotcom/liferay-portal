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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

/**
 * @author Chema Balsas
 */
public class DropdownActionsTag extends BaseClayTag {

	@Override
	public int doEndTag() throws JspException {
		Map<String, Object> context = getContext();

		List<DropdownItem> dropdownItems = (List<DropdownItem>)context.get(
			"items");

		if (ListUtil.isEmpty(dropdownItems)) {
			return EVAL_PAGE;
		}

		return super.doEndTag();
	}

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayActionsDropdown");
		setHydrate(true);
		setModuleBaseName("dropdown");

		Map<String, Object> context = getContext();

		if (Validator.isNotNull(context.get("buttonLabel"))) {
			Map<String, String> button = HashMapBuilder.put(
				"label", (String)context.get("buttonLabel")
			).put(
				"style", (String)context.get("buttonStyle")
			).put(
				"type", (String)context.get("buttonType")
			).build();

			putValue("button", button);
		}

		if (PortalUtil.isRightToLeft(request)) {
			putValue("preferredAlign", "BottomRight");
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

	public void setCaption(String caption) {
		putValue("caption", caption);
	}

	public void setDropdownItems(List<DropdownItem> dropdownItems) {
		putValue("items", dropdownItems);
	}

	public void setExpanded(Boolean expanded) {
		putValue("expanded", expanded);
	}

	public void setHelpText(String helpText) {
		putValue("helpText", helpText);
	}

	public void setItemsIconAlignment(String itemsIconAlignment) {
		putValue("itemsIconAlignment", itemsIconAlignment);
	}

	public void setTriggerCssClasses(String triggerCssClasses) {
		putValue("triggerClasses", triggerCssClasses);
	}

}