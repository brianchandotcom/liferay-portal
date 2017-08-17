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

package com.liferay.asset.display.template.util.comparator;

import com.liferay.asset.display.template.model.AssetDisplayTemplate;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Pavel Savinov
 */
public class AssetDisplayTemplateClassNameIdComparator
	extends OrderByComparator<AssetDisplayTemplate> {

	public static final String ORDER_BY_ASC =
		"AssetDisplayTemplate.classNameId ASC";

	public static final String ORDER_BY_DESC =
		"AssetDisplayTemplate.classNameId DESC";

	public static final String[] ORDER_BY_FIELDS = {"classNameId"};

	public AssetDisplayTemplateClassNameIdComparator() {
		this(false);
	}

	public AssetDisplayTemplateClassNameIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		AssetDisplayTemplate assetDisplayTemplate1,
		AssetDisplayTemplate assetDisplayTemplate2) {

		long classNameId1 = assetDisplayTemplate1.getClassNameId();
		long classNameId2 = assetDisplayTemplate2.getClassNameId();

		int value = 1;

		if (classNameId1 <= classNameId2) {
			value = -1;
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}