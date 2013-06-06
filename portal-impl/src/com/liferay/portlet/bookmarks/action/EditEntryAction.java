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

package com.liferay.portlet.bookmarks.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.bookmarks.EntryURLException;
import com.liferay.portlet.bookmarks.NoSuchEntryException;
import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.BookmarksEntryServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Levente Hudák
 */
public class EditEntryAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			BookmarksEntry entry = null;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				entry = updateEntry(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEntry(
					(LiferayPortletConfig)portletConfig, actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteEntry(
					(LiferayPortletConfig)portletConfig, actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreEntryFromTrash(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeEntry(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeEntry(actionRequest);
			}

			WindowState windowState = actionRequest.getWindowState();

			if (!windowState.equals(LiferayWindowState.POP_UP)) {
				sendRedirect(actionRequest, actionResponse);
			}
			else {
				String redirect = PortalUtil.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					if (cmd.equals(Constants.ADD) && (entry != null)) {
						redirect = HttpUtil.addParameter(
							redirect, "className",
							BookmarksEntry.class.getName());
						redirect = HttpUtil.addParameter(
							redirect, "classPK", entry.getEntryId());
					}

					actionResponse.sendRedirect(redirect);
				}
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.bookmarks.error");
			}
			else if (e instanceof EntryURLException ||
					 e instanceof NoSuchFolderException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
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
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.bookmarks.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.bookmarks.edit_entry"));
	}

	protected void deleteEntry(
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
				BookmarksEntryServiceUtil.moveEntryToTrash(deleteEntryId);
			}
			else {
				BookmarksEntryServiceUtil.deleteEntry(deleteEntryId);
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

	protected void restoreEntryFromTrash(ActionRequest actionRequest)
		throws PortalException, SystemException {

		long[] restoreFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreFolderIds"), 0L);

		for (long restoreFolderId : restoreFolderIds) {
			BookmarksFolderServiceUtil.restoreFolderFromTrash(restoreFolderId);
		}

		long[] restoreEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreEntryIds"), 0L);

		for (long restoreEntryId : restoreEntryIds) {
			BookmarksEntryServiceUtil.restoreEntryFromTrash(restoreEntryId);
		}
	}

	protected void subscribeEntry(ActionRequest actionRequest)
		throws Exception {

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		BookmarksEntryServiceUtil.subscribeEntry(entryId);
	}

	protected void unsubscribeEntry(ActionRequest actionRequest)
		throws Exception {

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		BookmarksEntryServiceUtil.unsubscribeEntry(entryId);
	}

	protected BookmarksEntry updateEntry(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		long groupId = themeDisplay.getScopeGroupId();
		long folderId = ParamUtil.getLong(actionRequest, "folderId");
		String name = ParamUtil.getString(actionRequest, "name");
		String url = ParamUtil.getString(actionRequest, "url");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			BookmarksEntry.class.getName(), actionRequest);

		BookmarksEntry entry = null;

		if (entryId <= 0) {

			// Add entry

			entry = BookmarksEntryServiceUtil.addEntry(
				groupId, folderId, name, url, description, serviceContext);

			AssetPublisherUtil.addAndStoreSelection(
				actionRequest, BookmarksEntry.class.getName(),
				entry.getEntryId(), -1);
		}
		else {

			// Update entry

			entry = BookmarksEntryServiceUtil.updateEntry(
				entryId, groupId, folderId, name, url, description,
				serviceContext);
		}

		AssetPublisherUtil.addRecentFolderId(
			actionRequest, BookmarksEntry.class.getName(), folderId);

		return entry;
	}

}