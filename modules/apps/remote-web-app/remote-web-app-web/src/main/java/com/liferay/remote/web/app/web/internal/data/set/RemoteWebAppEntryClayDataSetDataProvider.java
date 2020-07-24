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

package com.liferay.remote.web.app.web.internal.data.set;

import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.remote.web.app.model.RemoteWebAppEntry;
import com.liferay.remote.web.app.service.RemoteWebAppEntryLocalService;
import com.liferay.remote.web.app.web.internal.constants.DataSetDisplayConstants;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = "clay.data.provider.key=" + DataSetDisplayConstants.REMOTE_WEB_APP_ENTRY_DATA_SET_DISPLAY,
	service = ClayDataSetDataProvider.class
)
public class RemoteWebAppEntryClayDataSetDataProvider
	implements ClayDataSetDataProvider<RemoteWebAppClayDataSetEntry> {

	@Override
	public List<RemoteWebAppClayDataSetEntry> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<RemoteWebAppEntry> entries =
			_remoteWebAppEntryLocalService.getRemoteWebAppEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<RemoteWebAppEntry> stream = entries.stream();

		return stream.map(
			entry -> new RemoteWebAppClayDataSetEntry(
				entry, themeDisplay.getLocale())
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		return _remoteWebAppEntryLocalService.getRemoteWebAppEntriesCount();
	}

	@Reference
	private RemoteWebAppEntryLocalService _remoteWebAppEntryLocalService;

}