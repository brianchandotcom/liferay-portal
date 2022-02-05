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
public class UnicodePropertiesBuilderCheck extends BaseBuilderCheck {

	@Override
	protected boolean allowNullValues() {
		return false;
	}

	@Override
	protected List<BaseBuilderCheck.BuilderInformation>
		doGetBuilderInformationList() {

		return ListUtil.fromArray(
			new BaseBuilderCheck.BuilderInformation(
				"UnicodeProperties", "UnicodePropertiesBuilder", "fastLoad",
				"load", "put", "putAll", "setProperty"));
	}

	@Override
	protected String getAssignClassName(DetailAST assignDetailAST) {
		return getNewInstanceTypeName(assignDetailAST);
	}

	@Override
	protected List<String> getSupportsFunctionMethodNames() {
		return ListUtil.fromArray("put");
	}

	@Override
	protected boolean isSupportsNestedMethodCalls() {
		return true;
	}

}