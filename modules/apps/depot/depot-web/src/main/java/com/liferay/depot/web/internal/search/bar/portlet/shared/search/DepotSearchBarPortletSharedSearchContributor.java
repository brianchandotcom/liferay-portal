/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.search.bar.portlet.shared.search;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + SearchBarPortletKeys.SEARCH_BAR,
		"service.ranking:Integer=100"
	},
	service = PortletSharedSearchContributor.class
)
public class DepotSearchBarPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		_defaultSearchBarPortletSharedSearchContributor.contribute(
			portletSharedSearchSettings);

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		long[] groupIds = searchContext.getGroupIds();

		if (ArrayUtil.isEmpty(groupIds)) {
			return;
		}

		for (long groupId : groupIds) {
			searchContext.setGroupIds(
				ArrayUtil.append(
					searchContext.getGroupIds(),
					TransformUtil.transformToLongArray(
						_depotEntryGroupRelLocalService.
							getSearchableDepotEntryGroupRels(
								groupId, 0,
								_depotEntryGroupRelLocalService.
									getSearchableDepotEntryGroupRelsCount(
										groupId)),
						depotEntryGroupRel -> {
							DepotEntry depotEntry =
								_depotEntryLocalService.fetchDepotEntry(
									depotEntryGroupRel.getDepotEntryId());

							return depotEntry.getGroupId();
						})));
		}
	}

	@Reference(
		target = "(&(javax.portlet.name=" + SearchBarPortletKeys.SEARCH_BAR + ")(!(component.name=com.liferay.depot.web.internal.search.bar.portlet.shared.search.DepotSearchBarPortletSharedSearchContributor)))"
	)
	private PortletSharedSearchContributor
		_defaultSearchBarPortletSharedSearchContributor;

	@Reference
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

}