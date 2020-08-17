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

package com.liferay.frontend.taglib.clay.servlet.taglib;

import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;

/**
 * @author Chema Balsas
 */
public class DropdownActionsTag extends DropdownMenuTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setDisplayType("unstyled");
		setIcon("ellipsis-v");
		setMonospaced(true);

		return super.doStartTag();
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("actionsDropdown", true);

		return super.prepareProps(props);
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("component-action");

		return super.processCssClasses(cssClasses);
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:dropdown-actions:";

}