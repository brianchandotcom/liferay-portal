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

package com.liferay.dynamic.data.lists.util.comparator;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;

/**
 * Represents a OrderByComparator class used to order record sets according to its modified date during search operations.
 * The order could be ascending or descending and it's defined by the value specified in the class constructor.
 *
 * @see com.liferay.dynamic.data.lists.service.DDLRecordSetService#search(long, long, String, int, int, int, OrderByComparator)
 *
 * @author Rafael Praxedes
 */
public class DDLRecordSetModifiedDateComparator
	extends StagedModelModifiedDateComparator<DDLRecordSet> {

	public DDLRecordSetModifiedDateComparator() {
		this(false);
	}

	public DDLRecordSetModifiedDateComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public String getOrderBy() {
		return "DDLRecordSet." + super.getOrderBy();
	}

}