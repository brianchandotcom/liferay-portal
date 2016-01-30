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

package com.liferay.trash.web.portlet;

import com.liferay.portal.TrashPermissionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.trash.exception.RestoreEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryService;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.taglib.util.RestoreEntryUtil;
import com.liferay.taglib.util.TrashUndoUtil;
import com.liferay.trash.web.constants.TrashPortletKeys;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-trash",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.icon=/icons/trash.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Trash",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + TrashPortletKeys.TRASH,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class, TrashPortlet.class}
)
public class TrashPortlet extends MVCPortlet {

	public void deleteEntries(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		if (trashEntryId > 0) {
			_trashEntryService.deleteEntry(trashEntryId);

			return;
		}

		long[] deleteEntryIds = ParamUtil.getLongValues(
			actionRequest, "rowIds");

		if (deleteEntryIds.length > 0) {
			for (int i = 0; i < deleteEntryIds.length; i++) {
				_trashEntryService.deleteEntry(deleteEntryIds[i]);
			}

			return;
		}

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		if (Validator.isNotNull(className) && (classPK > 0)) {
			_trashEntryService.deleteEntry(className, classPK);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	public void emptyTrash(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(
			actionRequest, "groupId", themeDisplay.getScopeGroupId());

		_trashEntryService.deleteEntries(groupId);
	}

	public void moveEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long containerModelId = ParamUtil.getLong(
			actionRequest, "containerModelId");
		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			className, actionRequest);

		_trashEntryService.moveEntry(
			className, classPK, containerModelId, serviceContext);

		TrashUndoUtil.addRestoreData(actionRequest, className, classPK);

		sendRedirect(actionRequest, actionResponse);
	}

	public void restoreEntries(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		List<ObjectValuePair<String, Long>> entries = new ArrayList<>();

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		if (trashEntryId > 0) {
			TrashEntry entry = _trashEntryService.restoreEntry(trashEntryId);

			entries.add(
				new ObjectValuePair<>(
					entry.getClassName(), entry.getClassPK()));
		}
		else {
			long[] restoreEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

			for (long restoreEntryId : restoreEntryIds) {
				TrashEntry entry = _trashEntryService.restoreEntry(
					restoreEntryId);

				entries.add(
					new ObjectValuePair<>(
						entry.getClassName(), entry.getClassPK()));
			}
		}

		TrashUndoUtil.addRestoreData(actionRequest, entries);

		sendRedirect(actionRequest, actionResponse);
	}

	public void restoreEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.RENAME)) {
			restoreRename(actionRequest, actionResponse);
		}
		else if (cmd.equals(Constants.OVERRIDE)) {
			restoreOverride(actionRequest, actionResponse);
		}
	}

	public void restoreOverride(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		long duplicateEntryId = ParamUtil.getLong(
			actionRequest, "duplicateEntryId");

		TrashEntry entry = _trashEntryService.restoreEntry(
			trashEntryId, duplicateEntryId, null);

		TrashUndoUtil.addRestoreData(
			actionRequest, entry.getClassName(), entry.getClassPK());

		sendRedirect(actionRequest, actionResponse);
	}

	public void restoreRename(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		String newName = ParamUtil.getString(actionRequest, "newName");

		if (Validator.isNull(newName)) {
			String oldName = ParamUtil.getString(actionRequest, "oldName");

			newName = TrashUtil.getNewName(themeDisplay, null, 0, oldName);
		}

		TrashEntry entry = _trashEntryService.restoreEntry(
			trashEntryId, 0, newName);

		TrashUndoUtil.addRestoreData(
			actionRequest, entry.getClassName(), entry.getClassPK());

		sendRedirect(actionRequest, actionResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String resourceID = GetterUtil.getString(
			resourceRequest.getResourceID());

		if (resourceID.equals("checkEntry")) {
			try {
				JSONObject jsonObject = RestoreEntryUtil.checkEntry(
					resourceRequest);

				writeJSON(resourceRequest, resourceResponse, jsonObject);
			}
			catch (PortalException pe) {
			}
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof RestoreEntryException ||
			cause instanceof TrashPermissionException) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	private TrashEntryService _trashEntryService;

}