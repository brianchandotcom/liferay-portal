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

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Julien Castelain
 */
public class HorizontalCardTag extends BaseClayTag {

	public HorizontalCardTag() {
		super("card", "ClayHorizontalCard", true);
	}

	@Override
	public int doStartTag() {
		Map<String, Object> context = getContext();

		if (Validator.isNotNull(_rowChecker)) {			
			if (Validator.isNull(context.get("selectable"))) {
				putValue("selectable", true);
			}
			
			if (Validator.isNull(context.get("inputName"))) {
				putValue("inputName", _rowChecker.getRowIds());
			}
			
			if (Validator.isNull(context.get("inputValue"))) {
				putValue("inputValue", _resultRow.getPrimaryKey());
			}
			
			if (Validator.isNotNull(_resultRow)) {
				if (Validator.isNull(context.get("disabled"))) {
					putValue("disabled", _rowChecker.isDisabled(_resultRow.getObject()));
				}
				
				if (Validator.isNull(context.get("selected"))) {
					putValue("selected", _rowChecker.isChecked(_resultRow.getObject()));
				}
			}
		}

		return super.doStartTag();
	}

	public void setActionItems(Object actionItems) {
		putValue("actionItems", actionItems);
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

	public void setIcon(String icon) {
		putValue("icon", icon);
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

	public void setTitle(String title) {
		putValue("title", title);
	}

	private ResultRow _resultRow;
	private RowChecker _rowChecker;

}