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

package com.liferay.bookmarks.web.portlet.action;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.exception.EntryURLException;
import com.liferay.bookmarks.exception.NoSuchEntryException;
import com.liferay.bookmarks.exception.NoSuchFolderException;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryService;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.trash.service.TrashEntryService;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Levente Hudák
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BookmarksPortletKeys.BOOKMARKS,
		"javax.portlet.name=" + BookmarksPortletKeys.BOOKMARKS_ADMIN,
		"mvc.command.name=/bookmarks/edit_entry"
	},
	service = MVCActionCommand.class
)
public class EditEntryMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteEntry(ActionRequest actionRequest, boolean moveToTrash)
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

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteEntryId : deleteEntryIds) {
			if (moveToTrash) {
				BookmarksEntry entry = _bookmarksEntryService.moveEntryToTrash(
					deleteEntryId);

				trashedModels.add(entry);
			}
			else {
				_bookmarksEntryService.deleteEntry(deleteEntryId);
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			BookmarksEntry entry = null;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				entry = updateEntry(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEntry(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteEntry(actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreTrashEntries(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeEntry(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeEntry(actionRequest);
			}

			WindowState windowState = actionRequest.getWindowState();

			if (windowState.equals(LiferayWindowState.POP_UP)) {
				String redirect = PortalUtil.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					if (cmd.equals(Constants.ADD) && (entry != null)) {
						String portletId = HttpUtil.getParameter(
							redirect, "p_p_id", false);

						String namespace = PortalUtil.getPortletNamespace(
							portletId);

						redirect = HttpUtil.addParameter(
							redirect, namespace + "className",
							BookmarksEntry.class.getName());
						redirect = HttpUtil.addParameter(
							redirect, namespace + "classPK",
							entry.getEntryId());
					}

					actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
				}
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcPath", "/bookmarks/error.jsp");
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

	protected void restoreTrashEntries(ActionRequest actionRequest)
		throws Exception {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			_trashEntryService.restoreEntry(restoreTrashEntryId);
		}
	}

	@Reference(unbind = "-")
	protected void setBookmarksEntryService(
		BookmarksEntryService bookmarksEntryService) {

		_bookmarksEntryService = bookmarksEntryService;
	}

	@Reference(unbind = "-")
	protected void setTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	protected void subscribeEntry(ActionRequest actionRequest)
		throws Exception {

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		_bookmarksEntryService.subscribeEntry(entryId);
	}

	protected void unsubscribeEntry(ActionRequest actionRequest)
		throws Exception {

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		_bookmarksEntryService.unsubscribeEntry(entryId);
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

			entry = _bookmarksEntryService.addEntry(
				groupId, folderId, name, url, description, serviceContext);
		}
		else {

			// Update entry

			entry = _bookmarksEntryService.updateEntry(
				entryId, groupId, folderId, name, url, description,
				serviceContext);
		}

		return entry;
	}

	private volatile BookmarksEntryService _bookmarksEntryService;
	private volatile TrashEntryService _trashEntryService;

}