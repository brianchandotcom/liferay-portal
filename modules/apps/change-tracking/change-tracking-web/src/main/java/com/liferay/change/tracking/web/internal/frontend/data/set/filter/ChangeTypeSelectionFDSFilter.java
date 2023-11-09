/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.frontend.data.set.filter;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Noor Najjar
 */
@Component(service = FDSFilter.class)
public class ChangeTypeSelectionFDSFilter extends BaseSelectionFDSFilter {

	@Override
	public String getId() {
		return "changeType";
	}

	@Override
	public String getLabel() {
		return "changed";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return ListUtil.fromArray(
			new SelectionFDSFilterItem(
				"added",
				CTConstants.getCTChangeTypeLabel(
					CTConstants.CT_CHANGE_TYPE_ADDITION)),
			new SelectionFDSFilterItem(
				"modified",
				CTConstants.getCTChangeTypeLabel(
					CTConstants.CT_CHANGE_TYPE_MODIFICATION)),
			new SelectionFDSFilterItem(
				"deleted",
				CTConstants.getCTChangeTypeLabel(
					CTConstants.CT_CHANGE_TYPE_DELETION)));
	}

}