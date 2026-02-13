/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.site.cms.site.initializer.internal.constants.CMSSiteInitializerFDSNames;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuel Rives
 */
@Component(
	property = {
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.STRUCTURES_SECTION,
		"service.ranking:Integer=" + Integer.MAX_VALUE
	},
	service = FDSFilter.class
)
public class StructureSpaceSelectionFDSFilter extends BaseSelectionFDSFilter {

	@Override
	public String getAPIURL() {
		return "/o/headless-asset-library/v1.0/asset-libraries?filter=type " +
			"eq 'space'";
	}

	@Override
	public String getId() {
		return "acceptedGroupIds";
	}

	@Override
	public String getItemKey() {
		return "siteId";
	}

	@Override
	public String getItemLabel() {
		return "assetLibraryKey";
	}

	@Override
	public String getLabel() {
		return "space";
	}

	@Override
	public boolean isAutocompleteEnabled() {
		return true;
	}

}