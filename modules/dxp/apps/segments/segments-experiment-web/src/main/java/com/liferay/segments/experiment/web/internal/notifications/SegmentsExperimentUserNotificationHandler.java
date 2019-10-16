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

package com.liferay.segments.experiment.web.internal.notifications;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperimentLocalService;

import java.util.Optional;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS_EXPERIMENT,
	service = UserNotificationHandler.class
)
public class SegmentsExperimentUserNotificationHandler
	extends BaseUserNotificationHandler {

	public SegmentsExperimentUserNotificationHandler() {
		setPortletId(SegmentsPortletKeys.SEGMENTS_EXPERIMENT);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long segmentsExperimentId = jsonObject.getLong("classPK");

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.fetchSegmentsExperiment(
				segmentsExperimentId);

		if (segmentsExperiment == null) {
			_userNotificationEventLocalService.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		Optional<SegmentsExperimentConstants.Status> statusOptional =
			SegmentsExperimentConstants.Status.parse(
				segmentsExperiment.getStatus());

		if (!statusOptional.isPresent()) {
			return null;
		}

		SegmentsExperimentConstants.Status status = statusOptional.get();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", serviceContext.getLocale(), getClass());

		String title = ResourceBundleUtil.getString(
			resourceBundle, "ab-test-has-changed-status-to-x",
			status.getLabel());

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {
				HtmlUtil.escape(
					StringUtil.shorten(
						HtmlUtil.escape(segmentsExperiment.getName()))),
				title
			});
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long referrerClassNameId = jsonObject.getLong("referrerClassNameId");

		if (referrerClassNameId != _portal.getClassNameId(
				Layout.class.getName())) {

			return StringPool.BLANK;
		}

		long referrerClassPK = jsonObject.getLong("referrerClassPK");

		Layout layout = _layoutLocalService.fetchLayout(referrerClassPK);

		if (layout == null) {
			return StringPool.BLANK;
		}

		String segmentsExperimentKey = jsonObject.getString(
			"segmentsExperimentKey");

		return _getLayoutURL(layout, segmentsExperimentKey, serviceContext);
	}

	private String _getLayoutURL(
		Layout layout, String segmentsExperimentKey,
		ServiceContext serviceContext) {

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		if (httpServletRequest == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			String layoutURL = _portal.getLayoutURL(
				layout, themeDisplay, false);

			return _http.addParameter(
				layoutURL, "segmentsExperimentKey", segmentsExperimentKey);
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	@Reference
	private Http _http;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}