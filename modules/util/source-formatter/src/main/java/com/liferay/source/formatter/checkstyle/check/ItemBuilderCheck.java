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

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.ListUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ItemBuilderCheck extends BaseBuilderCheck {

	@Override
	protected boolean allowNullValues() {
		return true;
	}

	@Override
	protected List<BaseBuilderCheck.BuilderInformation>
		doGetBuilderInformationList() {

		return ListUtil.fromArray(
			new BaseBuilderCheck.BuilderInformation(
				"DropdownCheckboxItem", "DropdownCheckboxItemBuilder", "put",
				"putData", "setActive", "setChecked", "setData", "setDisabled",
				"setHref", "setInputName", "setInputValue", "setLabel",
				"setIcon", "setQuickAction", "setSeparator", "setTarget",
				"setType"),
			new BaseBuilderCheck.BuilderInformation(
				"DropdownGroupItem", "DropdownGroupItemBuilder", "put",
				"putData", "setActive", "setData", "setDisabled",
				"setDropdownItems", "setHref", "setLabel", "setIcon",
				"setQuickAction", "setSeparator", "setTarget", "setType"),
			new BaseBuilderCheck.BuilderInformation(
				"DropdownItem", "DropdownItemBuilder", "put", "putData",
				"setActive", "setData", "setDisabled", "setHref", "setLabel",
				"setIcon", "setQuickAction", "setSeparator", "setTarget",
				"setType"),
			new BaseBuilderCheck.BuilderInformation(
				"DropdownRadioGroupItem", "DropdownRadioGroupItemBuilder",
				"put", "putData", "setActive", "setData", "setDisabled",
				"setDropdownItems", "setHref", "setInputName", "setLabel",
				"setIcon", "setQuickAction", "setSeparator", "setTarget",
				"setType"),
			new BaseBuilderCheck.BuilderInformation(
				"DropdownRadioItem", "DropdownRadioItemBuilder", "put",
				"putData", "setActive", "setChecked", "setData", "setDisabled",
				"setHref", "setInputValue", "setLabel", "setIcon",
				"setQuickAction", "setSeparator", "setTarget", "setType"),
			new BaseBuilderCheck.BuilderInformation(
				"LabelItemList", "LabelItemListBuilder", "put", "putData",
				"setData", "setDismissable", "setDisplay", "setLabel",
				"setLarge", "setStatus"),
			new BaseBuilderCheck.BuilderInformation(
				"MultiselectItem", "MultiselectItemBuilder", "put", "setLabel",
				"setValue"),
			new BaseBuilderCheck.BuilderInformation(
				"NavigationItem", "NavigationItemBuilder", "put", "putData",
				"setActive", "setData", "setDisabled", "setHref", "setLabel"),
			new BaseBuilderCheck.BuilderInformation(
				"SortItem", "SortItemBuilder", "put", "setDirection", "setKey"),
			new BaseBuilderCheck.BuilderInformation(
				"ViewTypeItem", "ViewTypeItemBuilder", "put", "putData",
				"setActive", "setData", "setDisabled", "setHref", "setIcon",
				"setLabel"));
	}

	@Override
	protected String getAssignClassName(DetailAST assignDetailAST) {
		return getNewInstanceTypeName(assignDetailAST);
	}

	@Override
	protected List<String> getSupportsFunctionMethodNames() {
		return ListUtil.fromArray(
			"put", "putData", "setActive", "setChecked", "setDirection",
			"setDisabled", "setHref", "setInputName", "setInputValue",
			"setLabel", "setIcon", "setKey", "setQuickAction", "setSeparator",
			"setTarget", "setType", "setDismissable", "setDisplay", "setLabel",
			"setLarge", "setStatus", "setValue");
	}

	@Override
	protected boolean isSupportsNestedMethodCalls() {
		return true;
	}

}