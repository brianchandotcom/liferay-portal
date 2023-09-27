/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.util.comparator;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;

/**
 * Used to order record sets according to their modified date during search
 * operations. The order can be ascending or descending and is defined by the
 * value specified in the class constructor.
 *
 * @author Rafael Praxedes
 * @see    com.liferay.dynamic.data.lists.service.DDLRecordSetService#search(
 *         long, long, String, int, int, int, OrderByComparator)
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