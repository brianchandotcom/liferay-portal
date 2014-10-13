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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.TrackbackValidationException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.trackback.Trackback;
import com.liferay.portlet.blogs.trackback.TrackbackImpl;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Alexander Chow
 */
public class TrackbackAction extends PortletAction {

	public TrackbackAction() {
		_trackback = new TrackbackImpl();
	}

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
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

		setForward(actionRequest, ActionConstants.COMMON_NULL);
	}

	protected TrackbackAction(Trackback trackback) {
		_trackback = trackback;
	}

	protected void addTrackback(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			BlogsEntry entry = getBlogsEntry(actionRequest);

			validate(entry);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);

			HttpServletRequest originalRequest =
				PortalUtil.getOriginalServletRequest(request);

			String excerpt = ParamUtil.getString(originalRequest, "excerpt");
			String url = ParamUtil.getString(originalRequest, "url");
			String blogName = ParamUtil.getString(originalRequest, "blog_name");
			String title = ParamUtil.getString(originalRequest, "title");

			validate(actionRequest, request.getRemoteAddr(), url);

			_trackback.addTrackback(
				entry, themeDisplay, excerpt, url, blogName, title,
				new TrackbackServiceContextFunction(actionRequest));
		}
		catch (TrackbackValidationException tve) {
			sendError(actionRequest, actionResponse, tve.getMessage());

			return;
		}

		sendSuccess(actionRequest, actionResponse);
	}

	protected BlogsEntry getBlogsEntry(ActionRequest actionRequest)
		throws Exception {

		try {
			ActionUtil.getEntry(actionRequest);
		}
		catch (PrincipalException pe) {
			throw new TrackbackValidationException(
				"Blog entry must have guest view permissions to enable " +
					"trackbacks");
		}

		return (BlogsEntry)actionRequest.getAttribute(WebKeys.BLOGS_ENTRY);
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected boolean isCommentsEnabled(ActionRequest actionRequest)
		throws Exception {

		PortletPreferences portletPreferences = getStrictPortletSetup(
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

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		ServletResponseUtil.sendFile(
			request, response, null, sb.toString().getBytes(StringPool.UTF8),
			ContentTypes.TEXT_XML_UTF8);
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

		String trackbackIP = HttpUtil.getIpAddress(url);

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

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private static final Log _log = LogFactoryUtil.getLog(
		TrackbackAction.class);

	private final Trackback _trackback;

}