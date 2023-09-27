/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util.comparator;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;

/**
 * @author Rafael Praxedes
 */
public class DDMFormInstanceModifiedDateComparator
	extends StagedModelModifiedDateComparator<DDMFormInstance> {

	public DDMFormInstanceModifiedDateComparator() {
		this(false);
	}

	public DDMFormInstanceModifiedDateComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public String getOrderBy() {
		return "DDMFormInstance." + super.getOrderBy();
	}

}