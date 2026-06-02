/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.filter.BaseDateTimeRangeFDSFilter;
import com.liferay.frontend.data.set.filter.DateTimeFDSFilterItem;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.site.cms.site.initializer.internal.constants.CMSSiteInitializerFDSNames;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.ALL_RELATED_ASSETS_SECTION,
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.ALL_SECTION,
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.CONTENTS_SECTION,
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.FILES_SECTION,
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.RECYCLE_BIN_SECTION,
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.STRUCTURE_USAGES,
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.VIEW_CONTENTS_FOLDER,
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.VIEW_FILES_FOLDER,
		"service.ranking:Integer=93"
	},
	service = FDSFilter.class
)
public class DisplayDateTimeRangeFDSFilter extends BaseDateTimeRangeFDSFilter {

	@Override
	public String getId() {
		return "dateDisplay";
	}

	@Override
	public String getLabel() {
		return "display-date";
	}

	@Override
	public DateTimeFDSFilterItem getMaxDateTimeFDSFilterItem() {
		return new DateTimeFDSFilterItem(0, 0, 0, 0, 0);
	}

	@Override
	public DateTimeFDSFilterItem getMinDateTimeFDSFilterItem() {
		return new DateTimeFDSFilterItem(0, 0, 0, 0, 0);
	}

}