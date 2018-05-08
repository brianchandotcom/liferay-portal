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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy.base;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

/**
 * @author Julien Castelain
 */
public class BaseClayCardTag extends BaseClayTag {

	public BaseClayCardTag(String componentBaseName) {
		super("card", componentBaseName, true);
	}

	@Override
	public int doStartTag() {
		Map<String, Object> context = getContext();

		if (Validator.isNotNull(_rowChecker)) {
			if (Validator.isNull(context.get("inputName"))) {
				setInputName(_rowChecker.getRowIds());
			}

			if (Validator.isNull(context.get("inputValue"))) {
				setInputValue(_resultRow.getPrimaryKey());
			}

			if (Validator.isNull(context.get("selectable"))) {
				setSelectable(true);
			}

			if (Validator.isNotNull(_resultRow)) {
				if (Validator.isNull(context.get("disabled"))) {
					setDisabled(_rowChecker.isDisabled(_resultRow.getObject()));
				}

				if (Validator.isNull(context.get("selected"))) {
					setSelected(_rowChecker.isChecked(_resultRow.getObject()));
				}
			}
		}

		return super.doStartTag();
	}

	public void setActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		putValue("actionItems", actionDropdownItems);
	}

	public void setDisabled(Boolean disabled) {
		putValue("disabled", disabled);
	}

	public void setGroupName(String groupName) {
		putValue("groupName", groupName);
	}

	public void setHref(String href) {
		putValue("href", href);
	}

	public void setInputName(String inputName) {
		putValue("inputName", inputName);
	}

	public void setInputValue(String inputValue) {
		putValue("inputValue", inputValue);
	}

	public void setResultRow(ResultRow resultRow) {
		_resultRow = resultRow;
	}

	public void setRowChecker(RowChecker rowChecker) {
		_rowChecker = rowChecker;
	}

	public void setSelectable(Boolean selectable) {
		putValue("selectable", selectable);
	}

	public void setSelected(Boolean selected) {
		putValue("selected", selected);
	}

	private ResultRow _resultRow;
	private RowChecker _rowChecker;

}