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

package com.liferay.trash.web.internal.portlet;

import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.exception.TrashPermissionException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.kernel.exception.RestoreEntryException;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.model.TrashEntryConstants;
import com.liferay.trash.service.TrashEntryLocalService;
import com.liferay.trash.service.TrashEntryService;
import com.liferay.trash.web.internal.constants.TrashPortletKeys;
import com.liferay.trash.web.internal.constants.TrashWebKeys;
import com.liferay.trash.web.internal.util.TrashUndoUtil;
import com.liferay.trash.web.internal.util.TrashUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the Recycle Bin implementation of the <code>Portlet</code> interface
 * (in <code>javax.portlet</code>). If the Recycle Bin is enabled, this portlet
 * moves assets into the Recycle Bin instead of deleting them directly. The site
 * administrator is able to browse the list of removed asset entries, restore
 * selected entries, and empty the Recycle Bin.
 *
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
		"com.liferay.portlet.scopeable=true",
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

	public void changeDisplayStyle(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		hideDefaultSuccessMessage(actionRequest);

		String displayStyle = ParamUtil.getString(
			actionRequest, "displayStyle");

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(actionRequest);

		portalPreferences.setValue(
			TrashPortletKeys.TRASH, "display-style", displayStyle);
	}

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

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(TrashWebKeys.TRASH_HELPER, _trashHelper);
		renderRequest.setAttribute(TrashWebKeys.TRASH_UTIL, _trashUtil);

		super.render(renderRequest, renderResponse);
	}

	public void restoreEntries(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		checkEntry(actionRequest, actionResponse);

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
			checkEntry(actionRequest, actionResponse);

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

			newName = _trashHelper.getNewName(themeDisplay, null, 0, oldName);
		}

		TrashEntry entry = _trashEntryService.restoreEntry(
			trashEntryId, 0, newName);

		TrashUndoUtil.addRestoreData(
			actionRequest, entry.getClassName(), entry.getClassPK());

		sendRedirect(actionRequest, actionResponse);
	}

	protected void checkEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long trashEntryId = ParamUtil.getLong(actionRequest, "trashEntryId");

		String newName = ParamUtil.getString(actionRequest, "newName");

		TrashEntry entry = _trashEntryLocalService.fetchTrashEntry(
			trashEntryId);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			entry.getClassName());

		try {
			trashHandler.checkRestorableEntry(
				ModelAdapterUtil.adapt(
					com.liferay.trash.kernel.model.TrashEntry.class, entry),
				TrashEntryConstants.DEFAULT_CONTAINER_ID, newName);
		}
		catch (RestoreEntryException ree) {
			String redirect = ParamUtil.getString(actionRequest, "redirect");

			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(actionResponse);

			PortletURL renderURL = liferayPortletResponse.createRenderURL();

			renderURL.setParameter("mvcPath", "/restore_entry.jsp");
			renderURL.setParameter("redirect", redirect);
			renderURL.setParameter(
				"trashEntryId", String.valueOf(ree.getTrashEntryId()));
			renderURL.setParameter(
				"duplicateEntryId", String.valueOf(ree.getDuplicateEntryId()));
			renderURL.setParameter("oldName", ree.getOldName());
			renderURL.setParameter(
				"overridable", String.valueOf(ree.isOverridable()));

			actionRequest.setAttribute(WebKeys.REDIRECT, renderURL.toString());

			hideDefaultErrorMessage(actionRequest);

			sendRedirect(actionRequest, actionResponse);

			throw ree;
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
	protected void setTrashEntryLocalService(
		TrashEntryLocalService trashEntryLocalService) {

		_trashEntryLocalService = trashEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	@Reference
	private Portal _portal;

	private TrashEntryLocalService _trashEntryLocalService;
	private TrashEntryService _trashEntryService;

	@Reference
	private TrashHelper _trashHelper;

	@Reference
	private TrashUtil _trashUtil;

}