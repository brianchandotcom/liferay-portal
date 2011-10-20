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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
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
 */
public class EditEntryAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
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
				deleteEntries(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribe(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribe(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");
			boolean updateRedirect = false;

			if (redirect.contains("/blogs/" + oldUrlTitle + "/maximized")) {
				oldUrlTitle += "/maximized";
			}

			if ((entry != null) && (Validator.isNotNull(oldUrlTitle)) &&
				(redirect.endsWith("/blogs/" + oldUrlTitle) ||
				 redirect.contains("/blogs/" + oldUrlTitle + "?") ||
				 redirect.contains("/blog/" + oldUrlTitle + "?"))) {

				int pos = redirect.indexOf("?");

				if (pos == -1) {
					pos = redirect.length();
				}

				String newRedirect = redirect.substring(
					0, pos - oldUrlTitle.length());

				newRedirect += entry.getUrlTitle();

				if (oldUrlTitle.indexOf("/maximized") != -1) {
					newRedirect += "/maximized";
				}

				if (pos < redirect.length()) {
					newRedirect +=
						"?" + redirect.substring(pos + 1, redirect.length());
				}

				redirect = newRedirect;
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
						actionResponse.sendRedirect(redirect);
					}
				}
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.blogs.error");
			}
			else if (e instanceof EntryContentException ||
					 e instanceof EntryDisplayDateException ||
					 e instanceof EntrySmallImageNameException ||
					 e instanceof EntrySmallImageSizeException ||
					 e instanceof EntryTitleException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
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

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.blogs.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.blogs.edit_entry"));
	}

	protected void deleteEntries(ActionRequest actionRequest) throws Exception {
		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		if (entryId > 0) {
			BlogsEntryServiceUtil.deleteEntry(entryId);
		}
		else {
			long[] deleteEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteEntryIds"), 0L);

			for (int i = 0; i < deleteEntryIds.length; i++) {
				BlogsEntryServiceUtil.deleteEntry(deleteEntryIds[i]);
			}
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

		portletURL.setWindowState(actionRequest.getWindowState());

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

		return portletURL.toString();
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
				boolean attachments = ParamUtil.getBoolean(
					actionRequest, "attachments", false);

				if (attachments) {
					UploadPortletRequest uploadPortletRequest =
						PortalUtil.getUploadPortletRequest(actionRequest);

					smallImage = ParamUtil.getBoolean(
						uploadPortletRequest, "smallImage");
					smallImageURL = ParamUtil.getString(
						uploadPortletRequest, "smallImageURL");
					smallImageFileName = uploadPortletRequest.getFileName(
						"smallFile");
					smallImageInputStream =
						uploadPortletRequest.getFileAsStream("smallFile");
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