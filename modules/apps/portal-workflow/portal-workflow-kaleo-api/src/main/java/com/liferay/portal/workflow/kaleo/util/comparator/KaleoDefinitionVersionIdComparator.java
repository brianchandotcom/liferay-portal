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

package com.liferay.portal.workflow.kaleo.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

/**
 * @author Inácio Nery
 */
public class KaleoDefinitionVersionIdComparator
	extends OrderByComparator<KaleoDefinitionVersion> {

	public KaleoDefinitionVersionIdComparator() {
		this(false);
	}

	public KaleoDefinitionVersionIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		KaleoDefinitionVersion kaleoDefinitionVersion1,
		KaleoDefinitionVersion kaleoDefinitionVersion2) {

		int value = Long.compare(
			kaleoDefinitionVersion1.getKaleoDefinitionVersionId(),
			kaleoDefinitionVersion2.getKaleoDefinitionVersionId());

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return _ORDER_BY_ASC;
		}

		return _ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return _ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private static final String _ORDER_BY_ASC =
		"KaleoDefinitionVersion.kaleoDefinitionVersionId ASC";

	private static final String _ORDER_BY_DESC =
		"KaleoDefinitionVersion.kaleoDefinitionVersionId DESC";

	private static final String[] _ORDER_BY_FIELDS =
		{"kaleoDefinitionVersionId"};

	private final boolean _ascending;

}