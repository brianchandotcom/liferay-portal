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

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;

/**
 * Used to order records according to their modified dates during listing
 * operations. The order can be ascending or descending and is defined by the
 * value specified in the class constructor.
 *
 * @author Leonardo Barros
 * @see    com.liferay.dynamic.data.lists.service.DDLRecordLocalService#getRecords(
 *         long, int, int, int, OrderByComparator)
 */
public class DDLRecordModifiedDateComparator
	extends StagedModelModifiedDateComparator<DDLRecord> {

	public DDLRecordModifiedDateComparator() {
		this(false);
	}

	public DDLRecordModifiedDateComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public String getOrderBy() {
		return "DDLRecord." + super.getOrderBy();
	}

}