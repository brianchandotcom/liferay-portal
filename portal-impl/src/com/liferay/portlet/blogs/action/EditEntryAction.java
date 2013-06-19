/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.blogs.EntryContentException;
import com.liferay.portlet.blogs.EntryDisplayDateException;
import com.liferay.portlet.blogs.EntrySmallImageNameException;
import com.liferay.portlet.blogs.EntrySmallImageSizeException;
import com.liferay.portlet.blogs.EntryTitleException;
import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;

import java.io.InputStream;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 * @author Thiago Moreira
 * @author Juan Fernández
 * @author Zsolt Berentey
 * @author Levente Hudák
 */
public class EditEntryAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			BlogsEntry entry = null;
			String oldUrlTitle = StringPool.BLANK;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Object[] returnValue = updateEntry(actionRequest);

				entry = (BlogsEntry)returnValue[0];
				oldUrlTitle = ((String)returnValue[1]);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEntries(
					(LiferayPortletConfig)portletConfig, actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteEntries(
					(LiferayPortletConfig)portletConfig, actionRequest, true);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribe(actionRequest);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreEntries(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribe(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE_CONTENT)) {
				updateContent(actionRequest, actionResponse);

				return;
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");
			boolean updateRedirect = false;

			if (Validator.isNotNull(oldUrlTitle)) {
				String portletId = HttpUtil.getParameter(
					redirect, "p_p_id", false);

				String oldRedirectParam =
					PortalUtil.getPortletNamespace(portletId) + "redirect";

				String oldRedirect = HttpUtil.getParameter(
					redirect, oldRedirectParam, false);

				if (Validator.isNotNull(oldRedirect)) {
					String newRedirect = HttpUtil.decodeURL(oldRedirect);

					newRedirect = StringUtil.replace(
						newRedirect, oldUrlTitle, entry.getUrlTitle());
					newRedirect = StringUtil.replace(
						newRedirect, oldRedirectParam, "redirect");

					redirect = StringUtil.replace(
						redirect, oldRedirect, newRedirect);
				}
				else if (redirect.endsWith("/blogs/" + oldUrlTitle) ||
						 redirect.contains("/blogs/" + oldUrlTitle + "?") ||
						 redirect.contains("/blog/" + oldUrlTitle + "?")) {

					redirect = StringUtil.replace(
						redirect, oldUrlTitle, entry.getUrlTitle());
				}

				updateRedirect = true;
			}

			int workflowAction = ParamUtil.getInteger(
				actionRequest, "workflowAction",
				WorkflowConstants.ACTION_SAVE_DRAFT);

			boolean ajax = ParamUtil.getBoolean(actionRequest, "ajax");

			if (ajax) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("entryId", entry.getEntryId());
				jsonObject.put("redirect", redirect);
				jsonObject.put("updateRedirect", updateRedirect);

				writeJSON(actionRequest, actionResponse, jsonObject);

				return;
			}

			if ((entry != null) &&
				(workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT)) {

				redirect = getSaveAndContinueRedirect(
					portletConfig, actionRequest, entry, redirect);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				WindowState windowState = actionRequest.getWindowState();

				if (!windowState.equals(LiferayWindowState.POP_UP)) {
					sendRedirect(actionRequest, actionResponse, redirect);
				}
				else {
					redirect = PortalUtil.escapeRedirect(redirect);

					if (Validator.isNotNull(redirect)) {
						if (cmd.equals(Constants.ADD) && (entry != null)) {
							redirect = HttpUtil.addParameter(
								redirect, "className",
								BlogsEntry.class.getName());
							redirect = HttpUtil.addParameter(
								redirect, "classPK", entry.getEntryId());
						}

						actionResponse.sendRedirect(redirect);
					}
				}
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.blogs.error");
			}
			else if (e instanceof EntryContentException ||
					 e instanceof EntryDisplayDateException ||
					 e instanceof EntrySmallImageNameException ||
					 e instanceof EntrySmallImageSizeException ||
					 e instanceof EntryTitleException ||
					 e instanceof SanitizerException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				Throwable cause = e.getCause();

				if (cause instanceof SanitizerException) {
					SessionErrors.add(actionRequest, SanitizerException.class);
				}
				else {
					throw e;
				}
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getEntry(renderRequest);

			if (PropsValues.BLOGS_PINGBACK_ENABLED) {
				BlogsEntry entry = (BlogsEntry)renderRequest.getAttribute(
					WebKeys.BLOGS_ENTRY);

				if ((entry != null) && entry.isAllowPingbacks()) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(renderResponse);

					response.addHeader(
						"X-Pingback",
						PortalUtil.getPortalURL(renderRequest) +
							"/xmlrpc/pingback");
				}
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.blogs.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.blogs.edit_entry"));
	}

	protected void deleteEntries(
			LiferayPortletConfig liferayPortletConfig,
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long[] deleteEntryIds = null;

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		if (entryId > 0) {
			deleteEntryIds = new long[] {entryId};
		}
		else {
			deleteEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteEntryIds"), 0L);
		}

		for (long deleteEntryId : deleteEntryIds) {
			if (moveToTrash) {
				BlogsEntryServiceUtil.moveEntryToTrash(deleteEntryId);
			}
			else {
				BlogsEntryServiceUtil.deleteEntry(deleteEntryId);
			}
		}

		if (moveToTrash && (deleteEntryIds.length > 0)) {
			Map<String, String[]> data = new HashMap<String, String[]>();

			data.put(
				"restoreEntryIds", ArrayUtil.toStringArray(deleteEntryIds));

			SessionMessages.add(
				actionRequest,
				liferayPortletConfig.getPortletId() +
					SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA, data);

			SessionMessages.add(
				actionRequest,
				liferayPortletConfig.getPortletId() +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
		}
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			BlogsEntry entry, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String backURL = ParamUtil.getString(actionRequest, "backURL");

		boolean preview = ParamUtil.getBoolean(actionRequest, "preview");

		PortletURLImpl portletURL = new PortletURLImpl(
			actionRequest, portletConfig.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		String portletName = portletConfig.getPortletName();

		if (portletName.equals(PortletKeys.BLOGS_ADMIN)) {
			portletURL.setParameter("struts_action", "/blogs_admin/edit_entry");
		}
		else {
			portletURL.setParameter("struts_action", "/blogs/edit_entry");
		}

		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("backURL", backURL, false);
		portletURL.setParameter(
			"groupId", String.valueOf(entry.getGroupId()), false);
		portletURL.setParameter(
			"entryId", String.valueOf(entry.getEntryId()), false);
		portletURL.setParameter("preview", String.valueOf(preview), false);
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	protected void restoreEntries(ActionRequest actionRequest)
		throws PortalException, SystemException {

		long[] restoreEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreEntryIds"), 0L);

		for (long restoreEntryId : restoreEntryIds) {
			BlogsEntryServiceUtil.restoreEntryFromTrash(restoreEntryId);
		}
	}

	protected void subscribe(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		BlogsEntryServiceUtil.subscribe(themeDisplay.getScopeGroupId());
	}

	protected void unsubscribe(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		BlogsEntryServiceUtil.unsubscribe(themeDisplay.getScopeGroupId());
	}

	protected void updateContent(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		String content = ParamUtil.getString(actionRequest, "content");

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(entryId);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		try {
			Calendar displayDateCal = CalendarFactoryUtil.getCalendar();

			displayDateCal.setTime(entry.getDisplayDate());

			int displayDateMonth = displayDateCal.get(Calendar.MONTH);
			int displayDateDay = displayDateCal.get(Calendar.DATE);
			int displayDateYear = displayDateCal.get(Calendar.YEAR);
			int displayDateHour = displayDateCal.get(Calendar.HOUR);
			int displayDateMinute = displayDateCal.get(Calendar.MINUTE);

			if (displayDateCal.get(Calendar.AM_PM) == Calendar.PM) {
				displayDateHour += 12;
			}

			BlogsEntryServiceUtil.updateEntry(
				entryId, entry.getTitle(), entry.getDescription(), content,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, entry.getAllowPingbacks(),
				entry.getAllowTrackbacks(), null, entry.getSmallImage(),
				entry.getSmallImageURL(), null, null, serviceContext);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("success", Boolean.TRUE);

			writeJSON(actionRequest, actionResponse, jsonObject);
		}
		catch (Exception e) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("success", Boolean.FALSE);

			jsonObject.putException(e);

			writeJSON(actionRequest, actionResponse, jsonObject);
		}
	}

	protected Object[] updateEntry(ActionRequest actionRequest)
		throws Exception {

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		String title = ParamUtil.getString(actionRequest, "title");
		String description = ParamUtil.getString(actionRequest, "description");
		String content = ParamUtil.getString(actionRequest, "content");

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		boolean allowPingbacks = ParamUtil.getBoolean(
			actionRequest, "allowPingbacks");
		boolean allowTrackbacks = ParamUtil.getBoolean(
			actionRequest, "allowTrackbacks");
		String[] trackbacks = StringUtil.split(
			ParamUtil.getString(actionRequest, "trackbacks"));

		boolean smallImage = false;
		String smallImageURL = null;
		String smallImageFileName = null;
		InputStream smallImageInputStream = null;

		BlogsEntry entry = null;
		String oldUrlTitle = null;

		try {
			boolean ajax = ParamUtil.getBoolean(actionRequest, "ajax");

			if (!ajax) {
				smallImage = ParamUtil.getBoolean(actionRequest, "smallImage");
				smallImageURL = ParamUtil.getString(
					actionRequest, "smallImageURL");

				if (smallImage && Validator.isNull(smallImageURL)) {
					boolean attachments = ParamUtil.getBoolean(
						actionRequest, "attachments");

					if (attachments) {
						UploadPortletRequest uploadPortletRequest =
							PortalUtil.getUploadPortletRequest(actionRequest);

						smallImageFileName = uploadPortletRequest.getFileName(
							"smallFile");
						smallImageInputStream =
							uploadPortletRequest.getFileAsStream("smallFile");
					}
				}
			}

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				BlogsEntry.class.getName(), actionRequest);

			entry = null;
			oldUrlTitle = StringPool.BLANK;

			if (entryId <= 0) {

				// Add entry

				entry = BlogsEntryServiceUtil.addEntry(
					title, description, content, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, allowPingbacks, allowTrackbacks,
					trackbacks, smallImage, smallImageURL, smallImageFileName,
					smallImageInputStream, serviceContext);

				AssetPublisherUtil.addAndStoreSelection(
					actionRequest, BlogsEntry.class.getName(),
					entry.getEntryId(), -1);
			}
			else {

				// Update entry

				entry = BlogsEntryLocalServiceUtil.getEntry(entryId);

				String tempOldUrlTitle = entry.getUrlTitle();

				entry = BlogsEntryServiceUtil.updateEntry(
					entryId, title, description, content, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, allowPingbacks, allowTrackbacks,
					trackbacks, smallImage, smallImageURL, smallImageFileName,
					smallImageInputStream, serviceContext);

				if (!tempOldUrlTitle.equals(entry.getUrlTitle())) {
					oldUrlTitle = tempOldUrlTitle;
				}

				AssetPublisherUtil.addAndStoreSelection(
					actionRequest, BlogsEntry.class.getName(),
					entry.getEntryId(), -1);
			}
		}
		finally {
			StreamUtil.cleanUp(smallImageInputStream);
		}

		return new Object[] {entry, oldUrlTitle};
	}

}