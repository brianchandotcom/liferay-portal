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

package com.liferay.webhook.web.internal.frontend.taglib.clay.data.set.provider;

import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.webhook.model.WebhookEntry;
import com.liferay.webhook.service.WebhookEntryLocalService;
import com.liferay.webhook.web.internal.constants.WebhookClayDataSetDisplayNames;
import com.liferay.webhook.web.internal.frontend.taglib.clay.data.set.WebhookClayDataSetEntry;

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
	property = "clay.data.provider.key=" + WebhookClayDataSetDisplayNames.WEBHOOKS,
	service = ClayDataSetDataProvider.class
)
public class WebhookEntryClayDataSetDataProvider
	implements ClayDataSetDataProvider<WebhookClayDataSetEntry> {

	@Override
	public List<WebhookClayDataSetEntry> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<WebhookEntry> webhookEntries = _webhookEntryLocalService.search(
			themeDisplay.getCompanyId(), filter.getKeywords(),
			pagination.getStartPosition(), pagination.getEndPosition(), sort);

		Stream<WebhookEntry> stream = webhookEntries.stream();

		return stream.map(
			WebhookEntry -> new WebhookClayDataSetEntry(WebhookEntry)
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

		return _webhookEntryLocalService.searchCount(
			themeDisplay.getCompanyId(), filter.getKeywords());
	}

	@Reference
	private WebhookEntryLocalService _webhookEntryLocalService;

}