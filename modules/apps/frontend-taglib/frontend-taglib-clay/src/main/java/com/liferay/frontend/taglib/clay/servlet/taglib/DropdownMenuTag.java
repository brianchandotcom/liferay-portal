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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownGroupItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

/**
 * @author Chema Balsas
 */
public class DropdownMenuTag extends ButtonTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		if (_isEmpty(_dropdownItems)) {
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
	}

	@Override
	protected String getHydratedModuleName() {
		if (_isEmpty(_dropdownItems)) {
			return null;
		}

		return "frontend-taglib-clay/DropdownMenu";
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("items", _dropdownItems);

		return super.prepareProps(props);
	}

	private boolean _isEmpty(List<DropdownItem> dropdownItems) {
		if (ListUtil.isEmpty(_dropdownItems)) {
			return true;
		}

		for (DropdownItem dropdownItem : dropdownItems) {
			if (!(dropdownItem instanceof DropdownGroupItem)) {
				return false;
			}

			Object items = dropdownItem.get("items");

			if ((items instanceof List) &&
				ListUtil.isNotEmpty((List<?>)items)) {

				return false;
			}
		}

		return true;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:dropdown-menu:";

	private String _buttonType;
	private List<DropdownItem> _dropdownItems;

}