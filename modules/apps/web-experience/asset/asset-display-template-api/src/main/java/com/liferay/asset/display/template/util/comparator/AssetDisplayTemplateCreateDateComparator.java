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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Pavel Savinov
 */
public class AssetDisplayTemplateCreateDateComparator
	extends OrderByComparator<AssetDisplayTemplate> {

	public static final String ORDER_BY_ASC =
		"AssetDisplayTemplate.createDate ASC";

	public static final String ORDER_BY_DESC =
		"AssetDisplayTemplate.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public AssetDisplayTemplateCreateDateComparator() {
		this(false);
	}

	public AssetDisplayTemplateCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		AssetDisplayTemplate assetDisplayTemplate1,
		AssetDisplayTemplate assetDisplayTemplate2) {

		int value = DateUtil.compareTo(
			assetDisplayTemplate1.getCreateDate(),
			assetDisplayTemplate2.getCreateDate());

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