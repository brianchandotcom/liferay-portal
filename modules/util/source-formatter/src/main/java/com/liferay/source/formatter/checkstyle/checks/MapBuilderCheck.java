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

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class MapBuilderCheck extends BaseBuilderCheck {

	protected boolean allowNullValues() {
		return false;
	}

	protected List<BaseBuilderCheck.BuilderInformation>
		getBuilderInformationList() {

		return ListUtil.fromArray(
			new BaseBuilderCheck.BuilderInformation(
				"ConcurrentHashMap", "ConcurrentHashMapBuilder", "put"),
			new BaseBuilderCheck.BuilderInformation(
				"HashMap", "HashMapBuilder", "put"),
			new BaseBuilderCheck.BuilderInformation(
				"LinkedHashMap", "LinkedHashMapBuilder", "put"),
			new BaseBuilderCheck.BuilderInformation(
				"TreeMap", "TreeMapBuilder", "put"));
	}

	protected boolean isSupportsNestedMethodCalls() {
		return true;
	}

}