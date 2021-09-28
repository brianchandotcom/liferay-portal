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

package com.liferay.source.formatter.checkstyle.checks;

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
				"NavigationItem", "NavigationItemBuilder", "put", "putData",
				"setActive", "setData", "setDisabled", "setHref", "setLabel"),
			new BaseBuilderCheck.BuilderInformation(
				"DropdownItem", "DropdownItemBuilder", "put", "putData",
				"setActive", "setData", "setDisabled", "setHref", "setLabel",
				"setIcon", "setQuickAction", "setSeparator", "setTarget",
				"setType"),
			new BaseBuilderCheck.BuilderInformation(
				"LabelItemList", "LabelItemListBuilder", "put", "putData",
				"setData", "setDismissable", "setDisplay", "setLabel",
				"setLarge", "setStatus"));
	}

	@Override
	protected String getAssignClassName(DetailAST assignDetailAST) {
		return getNewInstanceTypeName(assignDetailAST);
	}

	@Override
	protected List<String> getSupportsFunctionMethodNames() {
		return ListUtil.fromArray(
			"put", "putData", "setActive", "setDisabled", "setHref", "setLabel",
			"setIcon", "setQuickAction", "setSeparator", "setTarget", "setType",
			"setDismissable", "setDisplay", "setLabel", "setLarge",
			"setStatus");
	}

	@Override
	protected boolean isSupportsNestedMethodCalls() {
		return true;
	}

}