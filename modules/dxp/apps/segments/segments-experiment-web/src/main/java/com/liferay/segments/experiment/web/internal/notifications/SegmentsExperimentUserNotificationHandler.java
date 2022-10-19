/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.experiment.web.internal.notifications;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
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
			return HttpComponentsUtil.addParameter(
				_portal.getLayoutURL(layout, themeDisplay, false),
				"segmentsExperimentKey", segmentsExperimentKey);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentUserNotificationHandler.class);

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