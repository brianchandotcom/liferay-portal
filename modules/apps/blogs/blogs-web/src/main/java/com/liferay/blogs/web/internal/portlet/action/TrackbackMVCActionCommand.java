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

package com.liferay.blogs.web.internal.portlet.action;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.exception.NoSuchEntryException;
import com.liferay.blogs.exception.TrackbackValidationException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.web.internal.trackback.Trackback;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContextFunction;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 */
@Component(
	immediate = true,
	property = {
		"auth.token.ignore.mvc.action=true",
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_AGGREGATOR,
		"mvc.command.name=/blogs/trackback"
	},
	service = MVCActionCommand.class
)
public class TrackbackMVCActionCommand extends BaseMVCActionCommand {

	public void addTrackback(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			BlogsEntry entry = getBlogsEntry(actionRequest);

			validate(entry);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(actionRequest);

			HttpServletRequest originalHttpServletRequest =
				_portal.getOriginalServletRequest(httpServletRequest);

			String excerpt = ParamUtil.getString(
				originalHttpServletRequest, "excerpt");
			String url = ParamUtil.getString(originalHttpServletRequest, "url");
			String blogName = ParamUtil.getString(
				originalHttpServletRequest, "blog_name");
			String title = ParamUtil.getString(
				originalHttpServletRequest, "title");

			validate(actionRequest, httpServletRequest.getRemoteAddr(), url);

			_trackback.addTrackback(
				entry, themeDisplay, excerpt, url, blogName, title,
				new ServiceContextFunction(actionRequest));
		}
		catch (TrackbackValidationException tve) {
			sendError(actionRequest, actionResponse, tve.getMessage());

			return;
		}

		sendSuccess(actionRequest, actionResponse);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			addTrackback(actionRequest, actionResponse);
		}
		catch (NoSuchEntryException nsee) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsee, nsee);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected BlogsEntry getBlogsEntry(ActionRequest actionRequest)
		throws Exception {

		try {
			return ActionUtil.getEntry(actionRequest);
		}
		catch (PrincipalException pe) {
			throw new TrackbackValidationException(
				"Blog entry must have guest view permissions to enable " +
					"trackbacks",
				pe);
		}
	}

	protected boolean isCommentsEnabled(ActionRequest actionRequest)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getExistingPortletSetup(
				actionRequest);

		if (portletPreferences == null) {
			portletPreferences = actionRequest.getPreferences();
		}

		return GetterUtil.getBoolean(
			portletPreferences.getValue("enableComments", null), true);
	}

	protected void sendError(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String msg)
		throws Exception {

		sendResponse(actionRequest, actionResponse, msg, false);
	}

	protected void sendResponse(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String msg, boolean success)
		throws Exception {

		StringBundler sb = new StringBundler(7);

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<response>");

		if (success) {
			sb.append("<error>0</error>");
		}
		else {
			sb.append("<error>1</error>");
			sb.append("<message>");
			sb.append(msg);
			sb.append("</message>");
		}

		sb.append("</response>");

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		String s = sb.toString();

		ServletResponseUtil.sendFile(
			httpServletRequest, httpServletResponse, null,
			s.getBytes(StringPool.UTF8), ContentTypes.TEXT_XML_UTF8);
	}

	protected void sendSuccess(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		sendResponse(actionRequest, actionResponse, null, true);
	}

	protected void validate(
			ActionRequest actionRequest, String remoteIP, String url)
		throws Exception {

		if (!isCommentsEnabled(actionRequest)) {
			throw new TrackbackValidationException("Comments are disabled");
		}

		if (Validator.isNull(url)) {
			throw new TrackbackValidationException(
				"Trackback requires a valid permanent URL");
		}

		String trackbackIP = _http.getIpAddress(url);

		if (!remoteIP.equals(trackbackIP)) {
			throw new TrackbackValidationException(
				"Remote IP does not match the trackback URL's IP");
		}
	}

	protected void validate(BlogsEntry entry)
		throws TrackbackValidationException {

		if (!entry.isAllowTrackbacks()) {
			throw new TrackbackValidationException(
				"Trackbacks are not enabled");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TrackbackMVCActionCommand.class);

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

	@Reference
	private Trackback _trackback;

}