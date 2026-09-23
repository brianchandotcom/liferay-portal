/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseDateRangeFDSFilter;
import com.liferay.frontend.data.set.filter.DateFDSFilterItem;

/**
 * @author José Abelenda
 */
public class CreateDateFDSFilter extends BaseDateRangeFDSFilter {

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.DATE_TIME;
	}

	@Override
	public String getId() {
		return "dateCreated";
	}

	@Override
	public String getLabel() {
		return "create-date";
	}

	@Override
	public DateFDSFilterItem getMaxDateFDSFilterItem() {
		return new DateFDSFilterItem(0, 0, 0);
	}

	@Override
	public DateFDSFilterItem getMinDateFDSFilterItem() {
		return new DateFDSFilterItem(0, 0, 0);
	}

}