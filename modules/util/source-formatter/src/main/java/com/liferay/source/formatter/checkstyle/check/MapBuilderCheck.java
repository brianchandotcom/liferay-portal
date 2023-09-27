/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.ListUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class MapBuilderCheck extends BaseBuilderCheck {

	@Override
	protected boolean allowNullValues() {
		return false;
	}

	@Override
	protected List<BaseBuilderCheck.BuilderInformation>
		doGetBuilderInformationList() {

		return ListUtil.fromArray(
			new BaseBuilderCheck.BuilderInformation(
				"ConcurrentHashMap", "ConcurrentHashMapBuilder", "put",
				"putAll"),
			new BaseBuilderCheck.BuilderInformation(
				"HashMap", "HashMapBuilder", "put", "putAll"),
			new BaseBuilderCheck.BuilderInformation(
				"HashMapDictionary", "HashMapDictionaryBuilder", "put",
				"putAll"),
			new BaseBuilderCheck.BuilderInformation(
				"LinkedHashMap", "LinkedHashMapBuilder", "put", "putAll"),
			new BaseBuilderCheck.BuilderInformation(
				"TreeMap", "TreeMapBuilder", "put", "putAll"));
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