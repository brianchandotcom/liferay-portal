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

import com.liferay.frontend.taglib.clay.internal.servlet.taglib.BaseContainerTag;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;

import javax.servlet.jsp.JspException;

/**
 * @author Chema Balsas
 */
public class SheetTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public String getSize() {
		return _size;
	}

	public void setSize(String size) {
		_size = size;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_size = "lg";
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("sheet");

		if (Validator.isNotNull(_size)) {
			cssClasses.add("sheet-" + _size);
		}

		return super.processCssClasses(cssClasses);
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:sheet:";

	private String _size = "lg";

}