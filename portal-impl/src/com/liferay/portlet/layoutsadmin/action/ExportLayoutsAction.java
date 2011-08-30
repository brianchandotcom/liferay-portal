/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.sites.action.ActionUtil;

import java.io.File;
import java.io.FileInputStream;

import java.util.Calendar;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class ExportLayoutsAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");
			long[] layoutIds = ParamUtil.getLongValues(
				actionRequest, "layoutIds");
			String fileName = ParamUtil.getString(
				actionRequest, "exportFileName");
			String range = ParamUtil.getString(actionRequest, "range");

			Date startDate = null;
			Date endDate = null;

			if (range.equals("dateRange")) {
				int startDateMonth = ParamUtil.getInteger(
					actionRequest, "startDateMonth");
				int startDateDay = ParamUtil.getInteger(
					actionRequest, "startDateDay");
				int startDateYear = ParamUtil.getInteger(
					actionRequest, "startDateYear");
				int startDateHour = ParamUtil.getInteger(
					actionRequest, "startDateHour");
				int startDateMinute = ParamUtil.getInteger(
					actionRequest, "startDateMinute");
				int startDateAmPm = ParamUtil.getInteger(
					actionRequest, "startDateAmPm");

				if (startDateAmPm == Calendar.PM) {
					startDateHour += 12;
				}

				startDate = PortalUtil.getDate(
					startDateMonth, startDateDay, startDateYear, startDateHour,
					startDateMinute, themeDisplay.getTimeZone(),
					new PortalException());

				int endDateMonth = ParamUtil.getInteger(
					actionRequest, "endDateMonth");
				int endDateDay = ParamUtil.getInteger(
					actionRequest, "endDateDay");
				int endDateYear = ParamUtil.getInteger(
					actionRequest, "endDateYear");
				int endDateHour = ParamUtil.getInteger(
					actionRequest, "endDateHour");
				int endDateMinute = ParamUtil.getInteger(
					actionRequest, "endDateMinute");
				int endDateAmPm = ParamUtil.getInteger(
					actionRequest, "endDateAmPm");

				if (endDateAmPm == Calendar.PM) {
					endDateHour += 12;
				}

				endDate = PortalUtil.getDate(
					endDateMonth, endDateDay, endDateYear, endDateHour,
					endDateMinute, themeDisplay.getTimeZone(),
					new PortalException());
			}
			else if (range.equals("fromLastPublishDate")) {
				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					groupId, privateLayout);

				UnicodeProperties settingsProperties =
					layoutSet.getSettingsProperties();

				long lastPublishDate = GetterUtil.getLong(
					settingsProperties.getProperty("last-publish-date"));

				if (lastPublishDate > 0) {
					Calendar cal = Calendar.getInstance(
						themeDisplay.getTimeZone(), themeDisplay.getLocale());

					endDate = cal.getTime();

					cal.setTimeInMillis(lastPublishDate);

					startDate = cal.getTime();
				}
			}
			else if (range.equals("last")) {
				int rangeLast = ParamUtil.getInteger(actionRequest, "last");

				Date now = new Date();

				startDate = new Date(now.getTime() - (rangeLast * Time.HOUR));

				endDate = now;
			}

			File file = LayoutServiceUtil.exportLayoutsAsFile(
				groupId, privateLayout, layoutIds,
				actionRequest.getParameterMap(), startDate, endDate);

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			ServletResponseUtil.sendFile(
				request, response, fileName, new FileInputStream(file),
				ContentTypes.APPLICATION_ZIP);

			FileUtil.delete(file);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			_log.error(e, e);

			SessionErrors.add(actionRequest, e.getClass().getName());

			String pagesRedirect = ParamUtil.getString(
				actionRequest, "pagesRedirect");

			sendRedirect(actionRequest, actionResponse, pagesRedirect);
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.layouts_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.layouts_admin.export_layouts"));
	}

	private static Log _log = LogFactoryUtil.getLog(ExportLayoutsAction.class);

}