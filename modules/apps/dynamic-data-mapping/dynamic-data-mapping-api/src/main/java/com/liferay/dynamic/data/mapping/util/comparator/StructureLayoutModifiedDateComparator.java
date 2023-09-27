/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util.comparator;

import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;

/**
 * @author Gabriel Albuquerque
 */
public class StructureLayoutModifiedDateComparator
	extends StagedModelModifiedDateComparator<DDMStructureLayout> {

	public StructureLayoutModifiedDateComparator() {
		this(false);
	}

	public StructureLayoutModifiedDateComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public String getOrderBy() {
		return "DDMStructureLayout." + super.getOrderBy();
	}

}