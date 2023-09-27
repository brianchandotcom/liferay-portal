/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util.comparator;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;

/**
 * @author Leonardo Barros
 */
public class DDMFormInstanceRecordModifiedDateComparator
	extends StagedModelModifiedDateComparator<DDMFormInstanceRecord> {

	public DDMFormInstanceRecordModifiedDateComparator() {
		this(false);
	}

	public DDMFormInstanceRecordModifiedDateComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public String getOrderBy() {
		return "DDMFormInstanceRecord." + super.getOrderBy();
	}

}