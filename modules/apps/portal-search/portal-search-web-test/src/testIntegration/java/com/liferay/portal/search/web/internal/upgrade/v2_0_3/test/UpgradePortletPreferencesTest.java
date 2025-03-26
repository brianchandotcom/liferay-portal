/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.upgrade.v2_0_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.constants.SearchResultsPortletKeys;
import com.liferay.portal.search.web.internal.category.facet.constants.CategoryFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.facet.constants.CustomFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.filter.constants.CustomFilterPortletKeys;
import com.liferay.portal.search.web.internal.folder.facet.constants.FolderFacetPortletKeys;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;
import com.liferay.portal.search.web.internal.site.facet.constants.SiteFacetPortletKeys;
import com.liferay.portal.search.web.internal.sort.constants.SortPortletKeys;
import com.liferay.portal.search.web.internal.tag.facet.constants.TagFacetPortletKeys;
import com.liferay.portal.search.web.internal.type.facet.constants.TypeFacetPortletKeys;
import com.liferay.portal.search.web.internal.user.facet.constants.UserFacetPortletKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class UpgradePortletPreferencesTest
	extends BaseUpgradePortletPreferencesTestCase {

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			CategoryFacetPortletKeys.CATEGORY_FACET + "_INSTANCE_%",
			CustomFacetPortletKeys.CUSTOM_FACET + "_INSTANCE_%",
			CustomFilterPortletKeys.CUSTOM_FILTER + "_INSTANCE_%",
			FolderFacetPortletKeys.FOLDER_FACET + "_INSTANCE_%",
			ModifiedFacetPortletKeys.MODIFIED_FACET + "_INSTANCE_%",
			SearchBarPortletKeys.SEARCH_BAR + "_INSTANCE_%",
			SearchResultsPortletKeys.SEARCH_RESULTS + "_INSTANCE_%",
			SiteFacetPortletKeys.SITE_FACET + "_INSTANCE_%",
			SortPortletKeys.SORT + "_INSTANCE_%",
			TagFacetPortletKeys.TAG_FACET + "_INSTANCE_%",
			TypeFacetPortletKeys.TYPE_FACET + "_INSTANCE_%",
			UserFacetPortletKeys.USER_FACET + "_INSTANCE_%"
		};
	}

	@Override
	protected UpgradeStepRegistrator getUpgradeStepRegistrator() {
		return _upgradeStepRegistrator;
	}

	@Override
	protected Version getVersion() {
		return _VERSION;
	}

	private static final Version _VERSION = new Version(2, 0, 3);

	@Inject(
		filter = "component.name=com.liferay.portal.search.web.internal.upgrade.registry.SearchWebUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}