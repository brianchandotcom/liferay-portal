/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.depot.web.internal.search.bar.portlet.shared.search;

import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.model.DepotEntryGroupRelModel;
import com.liferay.depot.model.DepotEntryModel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.util.DepotSupportChecker;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
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

		if (!_depotSupportChecker.isEnabled()) {
			return;
		}

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		long[] groupIds = searchContext.getGroupIds();

		if (ArrayUtil.isEmpty(groupIds)) {
			return;
		}

		for (long groupId : groupIds) {
			List<DepotEntryGroupRel> depotEntryGroupRels =
				_depotEntryGroupRelLocalService.
					getSearchableDepotEntryGroupRels(
						groupId, 0,
						_depotEntryGroupRelLocalService.
							getSearchableDepotEntryGroupRelsCount(groupId));

			Stream<DepotEntryGroupRel> stream = depotEntryGroupRels.stream();

			List<Long> depotEntryGroupIds = stream.map(
				DepotEntryGroupRelModel::getDepotEntryId
			).map(
				_depotEntryLocalService::fetchDepotEntry
			).map(
				DepotEntryModel::getGroupId
			).collect(
				Collectors.toList()
			);

			searchContext.setGroupIds(
				ArrayUtil.append(
					searchContext.getGroupIds(),
					ArrayUtil.toLongArray(depotEntryGroupIds)));
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

	@Reference
	private DepotSupportChecker _depotSupportChecker;

}