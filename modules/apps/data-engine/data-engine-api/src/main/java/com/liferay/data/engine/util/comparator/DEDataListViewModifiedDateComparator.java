/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.util.comparator;

import com.liferay.data.engine.model.DEDataListView;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;

/**
 * @author Gabriel Albuquerque
 */
public class DEDataListViewModifiedDateComparator
	extends StagedModelModifiedDateComparator<DEDataListView> {

	public DEDataListViewModifiedDateComparator() {
		this(false);
	}

	public DEDataListViewModifiedDateComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public String getOrderBy() {
		return "DEDataListView." + super.getOrderBy();
	}

}