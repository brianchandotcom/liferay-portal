/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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