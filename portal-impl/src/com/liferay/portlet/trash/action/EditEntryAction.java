/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.trash.DuplicateTrashEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Manuel de la Peña
 */
public class EditEntryAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteEntries(actionRequest);
			}
			else if (cmd.equals(Constants.EMPTY_TRASH)) {
				emptyTrash(actionRequest);
			}
			else if (cmd.equals(Constants.FIX_RESTORE)) {
				fixRestoredEntry(actionRequest);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreEntries(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (DuplicateTrashEntryException dtee) {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);

			SessionErrors.add(request, dtee.getClass(), dtee);
			SessionErrors.add(actionRequest, dtee.getClass());

			setForward(actionRequest, "portlet.trash.restore_error");
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass());
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		return mapping.findForward(
			getForward(renderRequest, "portlet.trash.view"));
	}

	protected void deleteEntries(ActionRequest actionRequest) throws Exception {
		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		if (entryId > 0) {
			deleteEntry(entryId);
		}
		else {
			long[] deleteEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteEntryIds"), 0L);

			for (int i = 0; i < deleteEntryIds.length; i++) {
				deleteEntry(deleteEntryIds[i]);
			}
		}
	}

	protected void deleteEntry(long entryId) throws Exception {
		TrashEntry entry = TrashEntryLocalServiceUtil.getTrashEntry(entryId);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			entry.getClassName());

		trashHandler.deleteTrashEntry(entry.getClassPK());
	}

	protected void emptyTrash(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		TrashEntryServiceUtil.deleteEntries(themeDisplay.getScopeGroupId());
	}

	protected void fixRestoredEntry(ActionRequest actionRequest)
		throws Exception {

		long entryId = ParamUtil.getLong(actionRequest, "entryId");
		String newName = ParamUtil.getString(actionRequest, "new-name");

		if (Validator.isNotNull(newName)) {
			try {
				TrashEntry entry = TrashEntryLocalServiceUtil.getTrashEntry(
					entryId);

				TrashHandler trashHandler =
					TrashHandlerRegistryUtil.getTrashHandler(
						entry.getClassName());

				trashHandler.updateEntryTitle(entry.getClassPK(), newName);

				trashHandler.restoreTrashEntry(entry.getClassPK());
			}
			catch (Exception e) {
			}
		}

	}

	protected void restoreEntries(ActionRequest actionRequest)
		throws Exception {

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		if (entryId > 0) {
			restoreEntry(entryId);
		}
		else {
			long[] restoreEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "restoreEntryIds"), 0L);

			for (int i = 0; i < restoreEntryIds.length; i++) {
				restoreEntry(restoreEntryIds[i]);
			}
		}
	}

	protected void restoreEntry(long entryId) throws Exception {
		TrashEntry entry = TrashEntryLocalServiceUtil.getTrashEntry(entryId);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			entry.getClassName());

		trashHandler.checkDuplicateEntry(entry);

		trashHandler.restoreTrashEntry(entry.getClassPK());
	}

}