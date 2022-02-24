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

import com.liferay.frontend.taglib.clay.internal.servlet.taglib.util.DropdownItemListUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

/**
 * @author Chema Balsas
 */
public class DropdownMenuTag extends ButtonTag {

	@Override
	public int doEndTag() throws JspException {
		if (_empty) {
			return EVAL_PAGE;
		}

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		_empty = DropdownItemListUtil.isEmpty(_dropdownItems);

		if (_empty) {
			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public List<DropdownItem> getDropdownItems() {
		return _dropdownItems;
	}

	public void setDropdownItems(List<DropdownItem> dropdownItems) {
		_dropdownItems = dropdownItems;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_buttonType = null;
		_dropdownItems = null;
		_empty = null;
	}

	@Override
	protected String getHydratedModuleName() {
		if (DropdownItemListUtil.isEmpty(_dropdownItems)) {
			return null;
		}

		return "frontend-taglib-clay/DropdownMenu";
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("items", _dropdownItems);

		return super.prepareProps(props);
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:dropdown-menu:";

	private String _buttonType;
	private List<DropdownItem> _dropdownItems;
	private Boolean _empty;

}