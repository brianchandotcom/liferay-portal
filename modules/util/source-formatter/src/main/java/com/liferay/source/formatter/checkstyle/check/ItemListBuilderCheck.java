/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.ListUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import java.util.Collections;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ItemListBuilderCheck extends BaseBuilderCheck {

	@Override
	protected boolean allowNullValues() {
		return true;
	}

	@Override
	protected List<BaseBuilderCheck.BuilderInformation>
		doGetBuilderInformationList() {

		return ListUtil.fromArray(
			new BaseBuilderCheck.BuilderInformation(
				"DropdownItemList", "DropdownItemListBuilder", "add",
				"addCheckbox", "addGroup", "addRadio", "addRadioGroup"),
			new BaseBuilderCheck.BuilderInformation(
				"LabelItemList", "LabelItemListBuilder", "add"),
			new BaseBuilderCheck.BuilderInformation(
				"MultiselectItemList", "MultiselectItemListBuilder", "add"),
			new BaseBuilderCheck.BuilderInformation(
				"NavigationItemList", "NavigationItemListBuilder", "add"),
			new BaseBuilderCheck.BuilderInformation(
				"SortItemList", "SortItemListBuilder", "add"));
	}

	@Override
	protected String getAssignClassName(DetailAST assignDetailAST) {
		return getNewInstanceTypeName(assignDetailAST);
	}

	@Override
	protected List<String> getSupportsFunctionMethodNames() {
		return Collections.emptyList();
	}

	@Override
	protected boolean isSupportsNestedMethodCalls() {
		return false;
	}

}