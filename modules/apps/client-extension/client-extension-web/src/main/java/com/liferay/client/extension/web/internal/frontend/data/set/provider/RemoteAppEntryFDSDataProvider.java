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

package com.liferay.remote.app.web.internal.frontend.data.set.provider;

import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;
import com.liferay.remote.app.web.internal.constants.RemoteAppAdminFDSNames;
import com.liferay.remote.app.web.internal.frontend.data.set.model.RemoteAppFDSEntry;

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
	property = "fds.data.provider.key=" + RemoteAppAdminFDSNames.REMOTE_APP_ENTRIES,
	service = FDSDataProvider.class
)
public class RemoteAppEntryFDSDataProvider
	implements FDSDataProvider<RemoteAppFDSEntry> {

	@Override
	public List<RemoteAppFDSEntry> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<RemoteAppEntry> remoteAppEntries =
			_remoteAppEntryLocalService.search(
				themeDisplay.getCompanyId(), fdsKeywords.getKeywords(),
				fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition(), sort);

		Stream<RemoteAppEntry> stream = remoteAppEntries.stream();

		return stream.map(
			remoteAppEntry -> new RemoteAppFDSEntry(
				remoteAppEntry, themeDisplay.getLocale())
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _remoteAppEntryLocalService.searchCount(
			themeDisplay.getCompanyId(), fdsKeywords.getKeywords());
	}

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

}