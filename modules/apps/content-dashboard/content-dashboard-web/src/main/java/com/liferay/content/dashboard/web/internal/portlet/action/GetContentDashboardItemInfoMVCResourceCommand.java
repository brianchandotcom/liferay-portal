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

package com.liferay.content.dashboard.web.internal.portlet.action;

import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.content.dashboard.web.internal.searcher.ContentDashboardSearchRequestBuilderFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.searcher.Searcher;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN,
		"mvc.command.name=/content_dashboard/get_content_dashboard_item_info"
	},
	service = MVCResourceCommand.class
)
public class GetContentDashboardItemInfoMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Locale locale = _portal.getLocale(resourceRequest);

		try {
			String className = ParamUtil.getString(
				resourceRequest, "className");

			long classPK = GetterUtil.getLong(
				ParamUtil.getLong(resourceRequest, "classPK"));

			Optional<ContentDashboardItemFactory<?>>
				contentDashboardItemFactoryOptional =
					_contentDashboardItemFactoryTracker.
						getContentDashboardItemFactoryOptional(className);

			JSONObject jsonObject = contentDashboardItemFactoryOptional.flatMap(
				contentDashboardItemFactory -> {
					try {
						return Optional.of(
							contentDashboardItemFactory.create(classPK));
					}
					catch (PortalException portalException) {
						_log.error(portalException, portalException);

						return Optional.empty();
					}
				}
			).map(
				contentDashboardItem -> JSONUtil.put(
					"modifiedDate",
					_toString(contentDashboardItem.getModifiedDate())
				).put(
					"subType", _getSubtype(contentDashboardItem, locale)
				).put(
					"title", contentDashboardItem.getTitle(locale)
				).put(
					"userId", contentDashboardItem.getUserId()
				).put(
					"userName", contentDashboardItem.getUserName()
				)
			).orElseGet(
				JSONFactoryUtil::createJSONObject
			);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(exception, exception);
			}

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				locale, getClass());

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					ResourceBundleUtil.getString(
						resourceBundle, "an-unexpected-error-occurred")));
		}
	}

	private String _getSubtype(
		ContentDashboardItem contentDashboardItem, Locale locale) {

		ContentDashboardItemType contentDashboardItemType =
			contentDashboardItem.getContentDashboardItemType();

		return contentDashboardItemType.getLabel(locale);
	}

	private String _toString(Date date) {
		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

		return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetContentDashboardItemInfoMVCResourceCommand.class);

	@Reference
	private ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;

	@Reference
	private ContentDashboardSearchRequestBuilderFactory
		_contentDashboardSearchRequestBuilderFactory;

	@Reference
	private Portal _portal;

	@Reference
	private Searcher _searcher;

}