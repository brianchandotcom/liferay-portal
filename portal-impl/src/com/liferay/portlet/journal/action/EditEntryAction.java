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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.journal.DuplicateArticleIdException;
import com.liferay.portlet.journal.DuplicateFolderNameException;
import com.liferay.portlet.journal.InvalidDDMStructureException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchFolderException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderServiceUtil;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.ArrayList;
import java.util.List;

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
 * @author Sergio González
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
			if (cmd.equals(Constants.DELETE)) {
				deleteEntries(actionRequest, false);
			}
			else if (cmd.equals(Constants.EXPIRE)) {
				expireEntries(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE)) {
				moveEntries(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteEntries(actionRequest, true);
			}

			String redirect = PortalUtil.escapeRedirect(
				ParamUtil.getString(actionRequest, "redirect"));

			WindowState windowState = actionRequest.getWindowState();

			if (!windowState.equals(LiferayWindowState.POP_UP)) {
				sendRedirect(actionRequest, actionResponse);
			}
			else if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchArticleException ||
				e instanceof NoSuchFolderException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.journal.error");
			}
			else if (e instanceof DuplicateArticleIdException ||
					 e instanceof DuplicateFolderNameException ||
					 e instanceof InvalidDDMStructureException) {

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
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getArticles(renderRequest);
			ActionUtil.getFolders(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchArticleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.journal.error");
			}
			else {
				throw e;
			}
		}

		String forward = "portlet.journal.edit_entry";

		return actionMapping.findForward(getForward(renderRequest, forward));
	}

	protected void deleteEntries(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<TrashedModel> trashedModels = new ArrayList<TrashedModel>();

		long[] deleteFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (long deleteFolderId : deleteFolderIds) {
			if (moveToTrash) {
				JournalFolder folder =
					JournalFolderServiceUtil.moveFolderToTrash(deleteFolderId);

				trashedModels.add(folder);
			}
			else {
				JournalFolderServiceUtil.deleteFolder(deleteFolderId);
			}
		}

		String[] deleteArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "articleIds"));

		for (String deleteArticleId : deleteArticleIds) {
			if (moveToTrash) {
				JournalArticle article =
					JournalArticleServiceUtil.moveArticleToTrash(
						themeDisplay.getScopeGroupId(),
						HtmlUtil.unescape(deleteArticleId));

				trashedModels.add(article);
			}
			else {
				ActionUtil.deleteArticle(
					actionRequest, HtmlUtil.unescape(deleteArticleId));
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	protected void expireEntries(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] expireFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		for (long expireFolderId : expireFolderIds) {
			ActionUtil.expireFolder(
				themeDisplay.getScopeGroupId(), expireFolderId, serviceContext);
		}

		String[] expireArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "articleIds"));

		for (String expireArticleId : expireArticleIds) {
			ActionUtil.expireArticle(
				actionRequest, HtmlUtil.unescape(expireArticleId));
		}
	}

	protected void moveEntries(ActionRequest actionRequest) throws Exception {
		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		long[] folderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		for (long folderId : folderIds) {
			JournalFolderServiceUtil.moveFolder(
				folderId, newFolderId, serviceContext);
		}

		List<String> invalidArticleIds = new ArrayList<String>();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] articleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "articleIds"));

		for (String articleId : articleIds) {
			try {
				JournalArticleServiceUtil.moveArticle(
					themeDisplay.getScopeGroupId(),
					HtmlUtil.unescape(articleId), newFolderId);
			}
			catch (InvalidDDMStructureException idse) {
				invalidArticleIds.add(articleId);
			}
		}

		if (!invalidArticleIds.isEmpty()) {
			throw new InvalidDDMStructureException();
		}
	}

}