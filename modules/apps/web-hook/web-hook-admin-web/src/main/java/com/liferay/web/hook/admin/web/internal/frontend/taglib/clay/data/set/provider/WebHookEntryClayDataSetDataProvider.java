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

package com.liferay.web.hook.admin.web.internal.frontend.taglib.clay.data.set.provider;

import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.web.hook.admin.web.internal.constants.WebHookAdminConstants;
import com.liferay.web.hook.admin.web.internal.frontend.taglib.clay.data.set.WebHookClayDataSetEntry;
import com.liferay.web.hook.model.WebHookEntry;
import com.liferay.web.hook.service.WebHookEntryLocalService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "clay.data.provider.key=" + WebHookAdminConstants.WEB_HOOK_ENTRY_DATA_SET_DISPLAY,
	service = ClayDataSetDataProvider.class
)
public class WebHookEntryClayDataSetDataProvider
	implements ClayDataSetDataProvider<WebHookClayDataSetEntry> {

	@Override
	public List<WebHookClayDataSetEntry> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<WebHookEntry> webHookEntries = _webHookEntryLocalService.search(
			themeDisplay.getCompanyId(), filter.getKeywords(),
			pagination.getStartPosition(), pagination.getEndPosition(), sort);

		Stream<WebHookEntry> stream = webHookEntries.stream();

		return stream.map(
			webHookEntry -> new WebHookClayDataSetEntry(
				webHookEntry, themeDisplay.getLocale())
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _webHookEntryLocalService.searchCount(
			themeDisplay.getCompanyId(), filter.getKeywords());
	}

	@Reference
	private WebHookEntryLocalService _webHookEntryLocalService;

}