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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.trash.DuplicateTrashEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

import java.text.Format;

import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
			if (cmd.equals("checkEntry")) {
				checkEntry(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEntries(actionRequest);
			}
			else if (cmd.equals(Constants.EMPTY_TRASH)) {
				emptyTrash(actionRequest);
			}
			else if (cmd.equals(Constants.RENAME)) {
				restoreRename(actionRequest);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreEntries(actionRequest);
			}
			else if (cmd.equals(Constants.OVERRIDE)) {
				restoreOverride(actionRequest);
			}

			if (!cmd.equals("checkEntry")) {
				sendRedirect(actionRequest, actionResponse);
			}
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

	protected void checkEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		String newName = ParamUtil.getString(actionRequest, "newName");

		TrashEntry entry = TrashEntryLocalServiceUtil.getTrashEntry(entryId);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			entry.getClassName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			trashHandler.checkDuplicateEntry(entry, newName);

			jsonObject.put("success", true);
		}
		catch (DuplicateTrashEntryException dtee) {
			jsonObject.put("duplicateEntryId", dtee.getDuplicateEntryId());
			jsonObject.put("oldName", dtee.getOldName());
			jsonObject.put("trashEntryId", dtee.getTrashEntryId());

			jsonObject.put("success", false);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
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

		trashHandler.checkDuplicateEntry(entry, StringPool.BLANK);

		trashHandler.restoreTrashEntry(entry.getClassPK());
	}

	protected void restoreOverride(ActionRequest actionRequest)
		throws Exception {

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");
		long duplicateEntryId = ParamUtil.getLong(
			actionRequest, "duplicateEntryId");

		TrashEntry entry = TrashEntryLocalServiceUtil.getTrashEntry(
			trashEntryId);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			entry.getClassName());

		trashHandler.deleteTrashEntries(new long[]{duplicateEntryId});

		trashHandler.restoreTrashEntry(entry.getClassPK());
	}

	protected void restoreRename(ActionRequest actionRequest) throws Exception {
		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		TrashEntry entry = TrashEntryLocalServiceUtil.getTrashEntry(
			trashEntryId);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			entry.getClassName());

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String newName = ParamUtil.getString(actionRequest, "newName");

		if (Validator.isNull(newName)) {
			String oldName = ParamUtil.getString(actionRequest, "oldName");

			Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
				themeDisplay.getLocale(), themeDisplay.getTimeZone());

			StringBundler sb = new StringBundler(5);

			sb.append(oldName);
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(dateFormatDateTime.format(new Date()).replace('/', '.'));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			newName = sb.toString();
		}

		trashHandler.updateEntryTitle(entry.getClassPK(), newName);

		trashHandler.restoreTrashEntry(entry.getClassPK());
	}

}